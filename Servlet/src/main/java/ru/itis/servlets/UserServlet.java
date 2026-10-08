package ru.itis.servlets;

import ru.itis.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/users")
public class UserServlet extends HttpServlet {

    private List<User> users;

    @Override
    public void init() throws ServletException {
        users = new ArrayList<>();
        User userOne = User.builder()
                .id(1L)
                .name("Danil")
                .surname("Smirnov")
                .age(40)
                .build();

        User userTwo = User.builder()
                .id(2L)
                .name("Alber")
                .surname("Garifeullin")
                .age(19)
                .build();

        User userThree = User.builder()
                .id(3L)
                .name("Marat")
                .surname("Vliuilin")
                .age(18)
                .build();

        users.add(userOne);
        users.add(userTwo);
        users.add(userThree);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("usersForJsp", users);
        request.getRequestDispatcher("/jsp/users.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

}
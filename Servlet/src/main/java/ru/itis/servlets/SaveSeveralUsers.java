package ru.itis.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet("/save")
public class SaveSeveralUsers extends HttpServlet {
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "postgres";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/oris";

    @Override
    public void init() throws ServletException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int count = getUsersCountFromDB();
        request.setAttribute("count", count);
        request.getRequestDispatcher("/jsp/saveSeveralUsers.jsp").forward(request, response);
    }

    private int getUsersCountFromDB() {
        String new_users_count_query = "SELECT new_users_count FROM settings LIMIT 1";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(new_users_count_query);
             ResultSet resultSet = statement.executeQuery()) {
            resultSet.next();
            return resultSet.getInt("new_users_count");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        int count = getUsersCountFromDB();

        String sql = "INSERT INTO drivers (first_name, last_name, age, city, email, phone) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            for (int i = 1; i <= count; i++) {
                String name = request.getParameter("name_" + i);
                String surname = request.getParameter("surname_" + i);
                int age = Integer.parseInt(request.getParameter("age_" + i));
                String city = request.getParameter("city_" + i);
                String email = request.getParameter("email_" + i);
                String phone = request.getParameter("phone_" + i);

                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, name);
                    statement.setString(2, surname);
                    statement.setInt(3, age);
                    statement.setString(4, city);
                    statement.setString(5, email);
                    statement.setString(6, phone);
                    statement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        response.sendRedirect("/html/users-added.html");
    }
}

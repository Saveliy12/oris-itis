package ru.itis.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet("/query")
public class QueryServlet extends HttpServlet {
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
        String field = request.getParameter("field");
        String value = request.getParameter("value");

        // /query?field=city|email|phone&value=X"
        if (field == null || value == null) { return; }

        switch (field) {
            case "city": selectByCity(value);    break;
            case "email": selectByEmail(value);  break;
            case "phone": selectByPhone(value);  break;
            default: System.out.println("unknown field: " + field);
        }
    }

    private void selectByCity(String city) {
        String sql_city_query = "SELECT * FROM drivers WHERE city = ?";
        executeAndPrintQuery(sql_city_query, city);
    }

    private void selectByEmail(String email) {
        String sql_email_query = "SELECT * FROM drivers WHERE email = ?";
        executeAndPrintQuery(sql_email_query, email);
    }

    private void selectByPhone(String phone) {
        String sql_phone_query = "SELECT * FROM drivers WHERE phone = ?";
        executeAndPrintQuery(sql_phone_query, phone);
    }

    private void executeAndPrintQuery(String sql, String value) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, value);
            try (ResultSet resultSet = statement.executeQuery()) {
                System.out.println("Заппрос: " + sql + " со значением: " + value);
                while (resultSet.next()) {
                    System.out.println(resultSet.getInt("id") + ", "
                            + resultSet.getString("first_name") + ", "
                            + resultSet.getString("last_name") + ", "
                            + resultSet.getInt("age") + ", "
                            + resultSet.getString("city") + ", "
                            + resultSet.getString("email") + ", "
                            + resultSet.getString("phone"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

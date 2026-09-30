package repo;

import entities.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {

    private Connection connection;

    private static final String SQL_SELECT_FROM_DRIVERS = "select id, first_name, last_name, age from drivers";

    public UserRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createAll(List<User> users) throws SQLException {
        String insert_query = "insert into drivers (first_name, last_name, age) values ";

        // insert into drivers (first_name, last_name, age) values ('Ivan', 'Ivanov', 20), ('Petr', 'Petrov', 21), ('Anna', 'Smirnova', 22), ('Olga', 'Kuznetsova', 20), ('Sergey', 'Volkov', 25), ('Mariya', 'Sokolova', 19)

        for (int i = 0; i < users.size(); i++) {
            User u = users.get(i);
            if (i > 0) {
                insert_query = insert_query + ", ";
            }
            insert_query = insert_query + "('" + u.getName() + "', '" + u.getSurname() + "', " + u.getAge() + ")";
        }

        Statement statement = connection.createStatement();
        statement.executeUpdate(insert_query);
    }

    @Override
    public List<User> findByFilter(String filter, String value) throws SQLException {
        String select_where_query = SQL_SELECT_FROM_DRIVERS + " where ";

        filter = filter.toLowerCase();

        if (filter.equals("age")) {
            select_where_query = select_where_query + "age = " + value;
        } else if (filter.equals("name")) {
            select_where_query = select_where_query + "first_name = '" + value + "'";
        } else if (filter.equals("surname")) {
            select_where_query = select_where_query + "last_name = '" + value + "'";
        } else {
            throw new IllegalArgumentException("Неизвестный фильтр: " + filter);
        }

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(select_where_query);

        List<User> result = new ArrayList<>();

        while (resultSet.next()) {
            User user = new User(
                    resultSet.getLong(1),
                    resultSet.getString(2),
                    resultSet.getString("last_name"),
                    resultSet.getInt("age")
            );
            result.add(user);
        }
        return result;
    }

    @Override
    public List<User> findAll() throws SQLException {

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(SQL_SELECT_FROM_DRIVERS);

        List<User> result = new ArrayList<>();

        while (resultSet.next()) {
            User user = new User(
                    resultSet.getLong(1),
                    resultSet.getString(2),
                    resultSet.getString("last_name"),
                    resultSet.getInt("age")
            );
            result.add(user);
        }
        return result;
    }

    @Override
    public Optional<User> findById(Long id) throws SQLException { return Optional.empty(); }

    @Override
    public void removeById(Long id) throws SQLException {}

    @Override
    public void create(User entity) {}

    @Override
    public void update(User entity) {}

    @Override
    public void remove(User entity) {}

}
import entities.User;
import repo.UserRepository;
import repo.UserRepositoryImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/oris", "postgres", "postgres");

        UserRepository userRepository = new UserRepositoryImpl(connection);

        List<User> users = new ArrayList<>();
        users.add(new User(null, "Ivan",   "Ivanov",     20));
        users.add(new User(null, "Petr",   "Petrov",     21));
        users.add(new User(null, "Anna",   "Smirnova",   22));
        users.add(new User(null, "Olga",   "Kuznetsova", 20));
        users.add(new User(null, "Sergey", "Volkov",     25));
        users.add(new User(null, "Mariya", "Sokolova",   19));

        userRepository.createAll(users);

        List<User> found = userRepository.findByFilter("age", "20");
        for (int i = 0; i < found.size(); i++) {
            System.out.println(found.get(i));
        }
    }
}
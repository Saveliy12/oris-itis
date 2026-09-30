package repo;

import entities.User;
import java.util.List;
import java.sql.SQLException;

public interface UserRepository extends Repository<User> {
    List<User> findByFilter(String filter, String value) throws SQLException;
}
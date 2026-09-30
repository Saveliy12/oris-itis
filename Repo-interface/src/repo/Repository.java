package repo;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface Repository<T> {
    List<T> findAll() throws SQLException;
    Optional<T> findById(Long id) throws SQLException;
    void create(T entity) throws SQLException;
    void createAll(List<T> entities) throws SQLException;
    void update(T entity) throws SQLException;
    void remove(T entity) throws SQLException;
    void removeById(Long id) throws SQLException;
}
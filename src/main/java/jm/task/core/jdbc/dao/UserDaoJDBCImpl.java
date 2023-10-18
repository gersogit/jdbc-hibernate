package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Util util;

    public UserDaoJDBCImpl(Util util) {
        this.util = util;
    }

    public void createUsersTable() {
        String SQLCreateTable = """
                CREATE TABLE IF NOT EXISTS "user" (
                      id SERIAL PRIMARY KEY,
                      name varchar(256) not null ,
                      last_name varchar(256) not null ,
                      age int not null
                )
                """;
        try (var connection = Util.open();
             var prepareStatement = connection.prepareStatement(SQLCreateTable)) {
            prepareStatement.execute();
            System.out.println("Таблица user создана");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        String SQLDropTable = """
                DROP TABLE IF EXISTS "user"
                """;
        try (var connection = Util.open();
             var preparedStatement = connection.prepareStatement(SQLDropTable)) {
            preparedStatement.execute();
            System.out.println("Таблица user удалена");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        String SQLInsert = """
                INSERT INTO "user" (name, last_name, age)
                VALUES (?, ?, ?);
                """;
        try (var connection = Util.open();
             var preparedStatement = connection.prepareStatement(SQLInsert)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            System.out.println("Был добавлен user именем: " + name + " в базу данных");
            var result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String SQLDelete = """
                DELETE FROM "user" WHERE id = ?;
                """;
        try (var connection = Util.open();
             var preparedStatement = connection.prepareStatement(SQLDelete)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Удален user с id: " + id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        String SQLSelect = """
                SELECT * FROM "user";
                """;
        List<User> resultList = new ArrayList<>();
        try (var connection = Util.open();
             var preparedStatement = connection.prepareStatement(SQLSelect)) {
            var result = preparedStatement.executeQuery();
            while (result.next()) {
                User user = new User();
                user.setId(result.getLong("id"));
                user.setName(result.getString("name"));
                user.setLastName(result.getString("last_name"));
                user.setAge(result.getByte("age"));
                resultList.add(user);
            }
            System.out.println("Список всех user'ов");
            System.out.println(resultList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultList;
    }

    public void cleanUsersTable() {
        String SQLTruncate = """
                TRUNCATE TABLE "user";
                """;
        try (var connection = Util.open();
             var preparedStatement = connection.prepareStatement(SQLTruncate)) {
            preparedStatement.executeUpdate();
            System.out.println("Отчистили таблицу: user");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

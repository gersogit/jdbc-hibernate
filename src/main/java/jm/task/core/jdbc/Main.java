package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {
        Util util = new Util();
        UserDao userDao = new UserDaoHibernateImpl(util);
        UserService userService = new UserServiceImpl(userDao);

        userService.createUsersTable();
        userService.saveUser("Azat", "Muratov", (byte) 23);
        userService.saveUser("John", "Bezos", (byte) 45);
        userService.saveUser("Kim", "Chi", (byte) 42);
        userService.saveUser("Mike", "Johnson", (byte) 28);
        userService.getAllUsers();
        userService.removeUserById(2);
        userService.cleanUsersTable();
        userService.dropUsersTable();

    }
}

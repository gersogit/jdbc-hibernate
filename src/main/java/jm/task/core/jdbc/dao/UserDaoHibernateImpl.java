package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final Util util;

    public UserDaoHibernateImpl(Util util) {
        this.util = util;
    }


    @Override
    public void createUsersTable() {
        try (Session session = Util.start()) {
            session.beginTransaction();
            String SQLCreateTable = """
                    CREATE TABLE IF NOT EXISTS public.User (
                          id BIGSERIAL PRIMARY KEY,
                          name varchar(256) not null ,
                          last_name varchar(256) not null ,
                          age smallint not null
                    )
                    """;
            session.createNativeQuery(SQLCreateTable).executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.start()) {
            session.beginTransaction();
            session.createNativeQuery("DROP TABLE IF EXISTS public.User")
                    .executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.start()) {
            session.beginTransaction();
            session.createNativeQuery("INSERT INTO public.User (name, last_name, age) VALUES (?,?,?)")
                    .setParameter(1, name)
                    .setParameter(2, lastName)
                    .setParameter(3, age).executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.start()) {
            session.beginTransaction();
//
            session.createNativeQuery("DELETE FROM public.User WHERE id = :userId")
                    .setParameter("userId", id)
                    .executeUpdate();
            System.out.println("Был удален с id: " + id);
            session.getTransaction().commit();
        }
    }
    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.start()) {
            session.beginTransaction();
            List<User> result = session.createQuery("FROM User", User.class).list();
            session.getTransaction().commit();
            for (User user: result) {
                System.out.println("id: " + user.getId() + " Name: " + user.getName()
                                   + " Last Name: " + user.getLastName() + " Age: " + user.getAge());
            }
            return result;
        }
    }
    @Override
    public void cleanUsersTable() {
        try (Session session = Util.start()) {
            session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            session.getTransaction().commit();
        }
    }
}

package pl.camp.it.book.store.database.hibernate;

import jakarta.persistence.NoResultException;
import jdk.jshell.spi.ExecutionControl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.camp.it.book.store.database.IUserDAO;
import pl.camp.it.book.store.exceptions.UserLoginExistException;
import pl.camp.it.book.store.model.User;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Optional;

@Repository
public class UserDAOImpl extends EntityManager implements IUserDAO {

    public UserDAOImpl(@Autowired SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Optional<User> getUserByLogin(String login) {
        Session session = this.sessionFactory.openSession();
        Query<User> query = session.createQuery(
                "FROM tuser WHERE login = :login", User.class);
        query.setParameter("login", login);
        Optional<User> result = Optional.empty();
        try {
            result = Optional.of(query.getSingleResult());
        } catch (NoResultException e){}
        session.close();
        return result;
    }

    @Override
    public void persistUser(User user) {
        super.persist(user);
    }

    @Override
    public Optional<User> getUserById(int id) throws ExecutionControl.NotImplementedException {
        Session session = this.sessionFactory.openSession();

        Query<User> query = session.createQuery(
                "FROM tuser WHERE id = :id",
                User.class
        );
        query.setParameter("id", id);
        Optional<User> result = Optional.empty();
        try {
            result = Optional.of(query.getSingleResult());
        } catch (NoResultException e) {}
        session.close();
        return result;
    }

    @Override
    public void updateUser(User user) throws ExecutionControl.NotImplementedException {
        super.update(user);
    }
}

package pl.camp.it.book.store.database.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pl.camp.it.book.store.exceptions.UserLoginExistException;
import pl.camp.it.book.store.model.Saveable;


public abstract class EntityManager {

    public final SessionFactory sessionFactory;

    protected EntityManager(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void persist(Saveable o) throws UserLoginExistException {
        try {
            Session session = this.sessionFactory.openSession();
            session.beginTransaction();
            session.persist(o);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            throw new UserLoginExistException();
        }
    }

    public void update(Saveable o) {
        Session session = this.sessionFactory.openSession();
        session.beginTransaction();
        session.merge(o);
        session.getTransaction().commit();
        session.close();
    }
}

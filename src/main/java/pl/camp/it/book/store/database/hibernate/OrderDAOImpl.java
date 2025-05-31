package pl.camp.it.book.store.database.hibernate;

import jakarta.persistence.NoResultException;
import jdk.jshell.spi.ExecutionControl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.camp.it.book.store.database.IOrderDAO;
import pl.camp.it.book.store.model.Order;
import pl.camp.it.book.store.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderDAOImpl implements IOrderDAO {

    @Autowired
    SessionFactory sessionFactory;
    @Override
    public void persistOrder(Order order) {
        Session session = this.sessionFactory.openSession();
        session.beginTransaction();
        session.persist(order);
        session.refresh(order.getUser());
        order.getUser().getOrders().add(order);
        session.merge(order.getUser());
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void updateOrder(Order order) throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Niepotrzebna metoda !!");
    }

    @Override
    public List<Order> getOrdersByUserId(int userId) {

        Session session = this.sessionFactory.openSession();
        Query<User> query = session.createQuery(
                "FROM pl.camp.it.book.store.model.User WHERE id = :id",
                User.class);
        query.setParameter("id", userId);
        ArrayList<Order> result = new ArrayList<>();
        try {
            User user = query.getSingleResult();
            result = new ArrayList<>(user.getOrders());
        } catch (NoResultException e) {}
        session.close();
        return result;
    }

    @Override
    public Optional<Order> getOrderById(int id) {
        Session session = this.sessionFactory.openSession();
        Query<Order> query = session.createQuery(
                "FROM pl.camp.it.book.store.model.Order WHERE id = :id",
                Order.class
        );
        query.setParameter("id", id);
        Optional<Order> result = Optional.empty();
        try {
            result = Optional.of(query.getSingleResult());
        } catch (NoResultException e) {}
        session.close();
        return result;
    }
}

package pl.camp.it.book.store.database.hibernate;

import jakarta.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.camp.it.book.store.database.IOrderPositionDAO;
import pl.camp.it.book.store.model.OrderPosition;

import java.util.List;
import java.util.Optional;

@Repository
public class OrderPositionDAOImpl implements IOrderPositionDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void persistOrderPosition(OrderPosition orderPosition, int orderId) {

    }

    @Override
    public List<OrderPosition> getOrderPositionByOrderId(int id) {
        return null;
    }

    @Override
    public Optional<OrderPosition> getOrderPositionById(int id) {
        Session session = this.sessionFactory.openSession();
        Query<OrderPosition> query = session.createQuery(
                "FROM pl.camp.it.book.store.model.OrderPosition WHERE id = :id",
                OrderPosition.class
        );
        query.setParameter("id", id);
        Optional<OrderPosition> result = Optional.empty();
        try {
            result = Optional.of(query.getSingleResult());
        } catch (NoResultException e) {}
        session.close();
        return result;
    }
}

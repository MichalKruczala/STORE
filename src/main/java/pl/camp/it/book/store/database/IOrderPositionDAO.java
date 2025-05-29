package pl.camp.it.book.store.database;

import pl.camp.it.book.store.model.OrderPosition;

import java.util.List;
import java.util.Optional;

public interface IOrderPositionDAO {
    void persistOrderPosition(OrderPosition orderPosition, int orderId);
    List<OrderPosition> getOrderPositionByOrderId(int id);
    Optional<OrderPosition> getOrderPositionById(int id);
}

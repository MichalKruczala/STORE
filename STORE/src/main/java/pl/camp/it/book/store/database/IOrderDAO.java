package pl.camp.it.book.store.database;

import jdk.jshell.spi.ExecutionControl;
import pl.camp.it.book.store.model.Order;
import pl.camp.it.book.store.model.User;

import java.util.List;
import java.util.Optional;

public interface IOrderDAO {
    void persistOrder(Order order);
    void updateOrder(Order order) throws ExecutionControl.NotImplementedException;
    List<Order> getOrdersByUserId(int userId);

    Optional<Order> getOrderById(int id);
}

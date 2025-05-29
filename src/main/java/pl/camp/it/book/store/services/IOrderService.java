package pl.camp.it.book.store.services;

import jdk.jshell.spi.ExecutionControl;
import pl.camp.it.book.store.model.Order;
import pl.camp.it.book.store.model.dto.SaveOrderRequest;

import java.util.List;
import java.util.Optional;

public interface IOrderService {
    void confirmOrder();
    List<Order> getOrderForCurrentUser();

    void persistOrder(Order order);
    List<Order> getOrderByUserId(int userId);

    Order persistOrder(SaveOrderRequest saveOrderRequest) throws ExecutionControl.NotImplementedException;

    Optional<Order> getOrderById(int id);
}

package pl.camp.it.book.store.services;

import jdk.jshell.spi.ExecutionControl;
import pl.camp.it.book.store.model.Order;
import pl.camp.it.book.store.model.dto.SaveOrderRequest;

import java.util.List;
import java.util.Optional;

public interface IOrderService {
    Order persistOrder(SaveOrderRequest saveOrderRequest) throws ExecutionControl.NotImplementedException;
    List<Order> getOrderByUserId(int userId);
    Optional<Order> getOrderById(int id);
}
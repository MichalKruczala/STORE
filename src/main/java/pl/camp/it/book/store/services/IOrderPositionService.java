package pl.camp.it.book.store.services;

import pl.camp.it.book.store.model.OrderPosition;

import java.util.Optional;

public interface IOrderPositionService {
    Optional<OrderPosition> getOrderPositionById(int id);
}

package pl.camp.it.book.store.model.dto;

import java.util.ArrayList;
import java.util.List;

public class OrdersDTO {
    private final List<OrderDTO> orders = new ArrayList<>();

    public List<OrderDTO> getOrders() {
        return orders;
    }
}

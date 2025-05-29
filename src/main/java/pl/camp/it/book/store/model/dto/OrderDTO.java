package pl.camp.it.book.store.model.dto;

import pl.camp.it.book.store.model.Order;
import pl.camp.it.book.store.model.OrderPosition;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class OrderDTO {
    private int id;
    private String user;
    private List<String> positions = new LinkedList<>();
    private LocalDateTime date;
    private Order.State state;
    private double total;

    public OrderDTO(Order order) {
        this.id = order.getId();
        this.user = "http://localhost:8085/api/v1/user/" + order.getUser().getId();
        this.state = order.getState();
        this.date = order.getDate();
        this.total = order.getTotal();
        for (OrderPosition orderPosition : order.getPositions()) {
            this.positions.add("http://localhost:8085/api/v1/orderposition/"
                            + orderPosition.getId());
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public List<String> getPositions() {
        return positions;
    }

    public void setPositions(List<String> positions) {
        this.positions = positions;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Order.State getState() {
        return state;
    }

    public void setState(Order.State state) {
        this.state = state;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}

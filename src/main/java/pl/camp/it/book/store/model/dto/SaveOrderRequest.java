package pl.camp.it.book.store.model.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import pl.camp.it.book.store.model.Order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SaveOrderRequest {
    private int userId;
    private List<OrderPosition> orderPositions = new ArrayList<>();
    private LocalDateTime date;
    private Order.State state;

    public SaveOrderRequest(int userId, List<OrderPosition> orderPositions, LocalDateTime date, Order.State state) {
        this.userId = userId;
        this.orderPositions = orderPositions;
        this.date = date;
        this.state = state;
    }

    public SaveOrderRequest() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<OrderPosition> getOrderPositions() {
        return orderPositions;
    }

    public void setOrderPositions(List<OrderPosition> orderPositions) {
        this.orderPositions = orderPositions;
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

    public static class OrderPosition {
        private int bookId;
        private int quantity;

        public OrderPosition(int bookId, int quantity) {
            this.bookId = bookId;
            this.quantity = quantity;
        }

        public OrderPosition() {
        }

        public int getBookId() {
            return bookId;
        }

        public void setBookId(int bookId) {
            this.bookId = bookId;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }
}

package pl.camp.it.book.store.model.dto;

import pl.camp.it.book.store.model.OrderPosition;

public class OrderPositionDTO {
    private int id;
    private String book;
    private int quantity;

    public OrderPositionDTO(OrderPosition orderPosition) {
        this.id = orderPosition.getId();
        this.book = "http://localhost:8085/api/v1/book/" + orderPosition.getBook().getId();
        this.quantity = orderPosition.getQuantity();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

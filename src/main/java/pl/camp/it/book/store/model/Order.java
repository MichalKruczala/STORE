package pl.camp.it.book.store.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Entity(name = "torder")
public class Order implements Saveable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OrderPosition> positions = new LinkedList<>();
    private LocalDateTime date;
    @Enumerated(EnumType.STRING)
    private State state;
    private double total;

    public Order(int id, User user, List<OrderPosition> positions, LocalDateTime date, State state, double total) {
        this.id = id;
        this.user = user;
        this.positions = positions;
        this.date = date;
        this.state = state;
        this.total = total;
    }

    public Order() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OrderPosition> getPositions() {
        return positions;
    }

    public void setPositions(List<OrderPosition> positions) {
        this.positions = positions;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public enum State {
        NEW,
        PAID,
        CONFIRMED,
        SENT,
        DONE
    }
}

package pl.camp.it.book.store.services.impl;

import jakarta.annotation.Resource;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.camp.it.book.store.database.IBookDAO;
import pl.camp.it.book.store.database.IOrderDAO;
import pl.camp.it.book.store.database.IUserDAO;
import pl.camp.it.book.store.exceptions.NotEnoughBookException;
import pl.camp.it.book.store.exceptions.PersistOrderException;
import pl.camp.it.book.store.model.Book;
import pl.camp.it.book.store.model.Order;
import pl.camp.it.book.store.model.OrderPosition;
import pl.camp.it.book.store.model.User;
import pl.camp.it.book.store.model.dto.SaveOrderRequest;
import pl.camp.it.book.store.services.ICartService;
import pl.camp.it.book.store.services.IOrderService;
import pl.camp.it.book.store.session.SessionObject;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class OrderServiceImpl implements IOrderService {

    @Resource
    SessionObject sessionObject;

    @Autowired
    ICartService cartService;

    @Autowired
    IOrderDAO orderDAO;

    @Autowired
    IBookDAO bookDAO;

    @Autowired
    IUserDAO userDAO;

    @Override
    public void confirmOrder() {
        Collection<OrderPosition> orderPositions = this.sessionObject.getCart().values();

        for(OrderPosition orderPosition : orderPositions) {
            Book book = orderPosition.getBook();
            int newBookQuantity = book.getQuantity() - orderPosition.getQuantity();
            if(newBookQuantity < 0) {
                throw new NotEnoughBookException();
            }
            book.setQuantity(newBookQuantity);
            this.bookDAO.updateBook(book);
        }

        /*Order order = new Order(this.sessionObject.getUser().getId(),
                new ArrayList<>(orderPositions),
                LocalDateTime.now(),
                Order.State.NEW,
                this.cartService.calculateCartSum());*/

        Order order = new Order(0,
                this.sessionObject.getUser(),
                new ArrayList<>(orderPositions),
                LocalDateTime.now(),
                Order.State.NEW,
                this.cartService.calculateCartSum());

        this.orderDAO.persistOrder(order);

        this.cartService.clearCart();
    }

    @Override
    public List<Order> getOrderForCurrentUser() {
        return this.orderDAO.getOrdersByUserId(this.sessionObject.getUser().getId());
    }

    @Override
    public void persistOrder(Order order) {
        this.orderDAO.persistOrder(order);
    }

    @Override
    public List<Order> getOrderByUserId(int userId) {
        List<Order> ordersByUserId = this.orderDAO.getOrdersByUserId(userId);
        ordersByUserId.stream().map(Order::getUser).forEach(u -> {
            u.setOrders(null);
            u.setPassword("*****");
        });
        return ordersByUserId;
    }

    @Override
    public Order persistOrder(SaveOrderRequest saveOrderRequest) throws ExecutionControl.NotImplementedException {
        Optional<User> user = this.userDAO.getUserById(saveOrderRequest.getUserId());
        if(user.isEmpty()) {
            throw new PersistOrderException();
        }

        Order order = new Order();
        order.setUser(user.get());

        for(SaveOrderRequest.OrderPosition requestPosition :
                saveOrderRequest.getOrderPositions()) {
            Optional<Book> book = this.bookDAO.getBookById(requestPosition.getBookId());
            if(book.isEmpty()) {
                throw new PersistOrderException();
            }
            OrderPosition orderPosition = new OrderPosition();
            orderPosition.setBook(book.get());
            orderPosition.setQuantity(requestPosition.getQuantity());
            order.getPositions().add(orderPosition);
        }

        order.setDate(saveOrderRequest.getDate());
        order.setState(saveOrderRequest.getState());
        double total = order.getPositions().stream()
                .mapToDouble(op -> op.getQuantity() * op.getBook().getPrice())
                .sum();
        order.setTotal(total);

        user.get().getOrders().add(order);
        this.orderDAO.persistOrder(order);

        user.get().setOrders(null);
        user.get().setPassword("*****");

        return order;
    }

    @Override
    public Optional<Order> getOrderById(int id) {
        return this.orderDAO.getOrderById(id);
    }
}

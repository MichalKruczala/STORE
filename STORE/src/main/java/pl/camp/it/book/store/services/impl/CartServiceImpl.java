package pl.camp.it.book.store.services.impl;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.camp.it.book.store.database.IBookDAO;
import pl.camp.it.book.store.exceptions.NotEnoughBookException;
import pl.camp.it.book.store.model.Book;
import pl.camp.it.book.store.model.OrderPosition;
import pl.camp.it.book.store.services.ICartService;
import pl.camp.it.book.store.session.SessionObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CartServiceImpl implements ICartService {

    @Autowired
    IBookDAO bookDAO;
    @Resource
    SessionObject sessionObject;

    @Override
    public void addBookToCart(int bookId) {
        Map<Integer, OrderPosition> cart = this.sessionObject.getCart();
        Optional<Book> bookBox = this.bookDAO.getBookById(bookId);
        if(bookBox.isEmpty()) {
            return;
        }
        if(cart.get(bookId) == null && bookBox.get().getQuantity() > 0) {
            cart.put(bookId, new OrderPosition(bookBox.get(), 1));
        } else if(bookBox.get().getQuantity() > cart.get(bookId).getQuantity()) {
            cart.get(bookId).incrementQuantity();
        } else {
            throw new NotEnoughBookException();
        }
    }

    @Override
    public void clearCart() {
        this.sessionObject.getCart().clear();
    }

    @Override
    public void removeFromCart(int bookId) {
        this.sessionObject.getCart().remove(bookId);
    }

    @Override
    public List<OrderPosition> getCart() {
        return new ArrayList<>(this.sessionObject.getCart().values());
    }

    @Override
    public double calculateCartSum() {
        /*double sum = 0.0;
        for(OrderPosition position : this.sessionObject.getCart().values()) {
            sum += position.getQuantity() * position.getBook().getPrice();
        }
        return sum;*/
        return this.sessionObject.getCart().values().stream()
                .mapToDouble(op -> op.getQuantity() * op.getBook().getPrice())
                .sum();
                //.reduce(0.0, Double::sum);
    }
}

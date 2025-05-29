package pl.camp.it.book.store.database.memory;

import jdk.jshell.spi.ExecutionControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.camp.it.book.store.database.IOrderDAO;
import pl.camp.it.book.store.database.sequence.IOrderIdSequence;
import pl.camp.it.book.store.model.Order;
import pl.camp.it.book.store.model.User;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class OrderDAO implements IOrderDAO {

    @Autowired
    IOrderIdSequence orderIdSequence;

    private final List<Order> orders = new ArrayList<>();

    @Override
    public void persistOrder(Order order) {
        order.setId(this.orderIdSequence.getId());
        this.orders.add(order);
    }

    @Override
    public void updateOrder(Order order) {
        Iterator<Order> iterator = this.orders.iterator();
        while(iterator.hasNext()) {
            Order orderFromDb = iterator.next();
            if(orderFromDb.getId() == order.getId()) {
                iterator.remove();
                this.orders.add(order);
                break;
            }
        }
    }

    @Override
    public List<Order> getOrdersByUserId(final int userId) {
        /*List<Order> result = new ArrayList<>();
        for(Order order : this.orders) {
            if(order.getUserId() == userId) {
                result.add(order);
            }
        }
        return result;*/
        return this.orders.stream().filter(o -> o.getUser().getId() == userId).toList();
    }

    @Override
    public Optional<Order> getOrderById(int id) {
        return Optional.empty();
    }
}

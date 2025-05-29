package pl.camp.it.book.store;

import pl.camp.it.book.store.database.memory.OrderDAO;
import pl.camp.it.book.store.model.Order;
import pl.camp.it.book.store.model.User;
import pl.camp.it.book.store.model.dto.SaveOrderRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        /*OrderDAO orderDAO = new OrderDAO();
        Order order1 = new Order();
        order1.setId(1);
        order1.setDate(LocalDateTime.now());

        Order order2 = new Order();
        order2.setId(2);
        order2.setDate(LocalDateTime.now());

        orderDAO.persistOrder(order1);
        orderDAO.persistOrder(order2);

        Order order3 = new Order();
        order3.setId(2);
        order3.setDate(LocalDateTime.now());

        orderDAO.updateOrder(order3);

        System.out.println();*/

        User.Role role = User.Role.USER;
        System.out.println(role.toString());

        SaveOrderRequest saveOrderRequest = new SaveOrderRequest();
        SaveOrderRequest.OrderPosition op = new SaveOrderRequest.OrderPosition();
    }
}

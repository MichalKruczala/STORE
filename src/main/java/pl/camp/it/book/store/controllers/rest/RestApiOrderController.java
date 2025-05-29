package pl.camp.it.book.store.controllers.rest;

import jdk.jshell.spi.ExecutionControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.camp.it.book.store.exceptions.PersistOrderException;
import pl.camp.it.book.store.model.Order;
import pl.camp.it.book.store.model.OrderPosition;
import pl.camp.it.book.store.model.dto.OrderDTO;
import pl.camp.it.book.store.model.dto.OrdersDTO;
import pl.camp.it.book.store.model.dto.SaveOrderRequest;
import pl.camp.it.book.store.services.IOrderService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/order")
public class RestApiOrderController {

    @Autowired
    IOrderService orderService;

    @RequestMapping(path = "", method = RequestMethod.GET)
    public OrdersDTO getOrdersByUserId(@RequestParam int userId) {
        OrdersDTO ordersDTO = new OrdersDTO();
        List<Order> orders = orderService.getOrderByUserId(userId);
        for (Order order : orders) {
            ordersDTO.getOrders().add(new OrderDTO(order));
        }
        return ordersDTO;
    }

    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<Order> saveOrder(@RequestBody SaveOrderRequest orderRequest) {
        try {
            Order order = this.orderService.persistOrder(orderRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(order);
        } catch (ExecutionControl.NotImplementedException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (PersistOrderException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable int id) {
        Optional<Order> orderBox = this.orderService.getOrderById(id);
        return orderBox
                .map(order -> ResponseEntity.status(HttpStatus.OK).body(new OrderDTO(order)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}

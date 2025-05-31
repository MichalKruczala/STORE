package pl.camp.it.book.store.controllers.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.camp.it.book.store.model.Order;
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

    @Operation(summary = "Get all orders by user ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Orders returned successfully")
    })
    @GetMapping("")
    public OrdersDTO getOrdersByUserId(@RequestParam int userId) {
        OrdersDTO ordersDTO = new OrdersDTO();
        List<Order> orders = orderService.getOrderByUserId(userId);
        for (Order order : orders) {
            ordersDTO.getOrders().add(new OrderDTO(order));
        }
        return ordersDTO;
    }

    @Operation(summary = "Save a new order")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Order created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid order data")
    })
    @PostMapping("")
    public ResponseEntity<Order> saveOrder(@RequestBody SaveOrderRequest orderRequest) {
        try {
            Order order = this.orderService.persistOrder(orderRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(order);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Operation(summary = "Get order by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order found"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable int id) {
        Optional<Order> orderBox = this.orderService.getOrderById(id);
        return orderBox
                .map(order -> ResponseEntity.status(HttpStatus.OK).body(new OrderDTO(order)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}

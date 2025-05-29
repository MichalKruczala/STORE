package pl.camp.it.book.store.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.camp.it.book.store.model.OrderPosition;
import pl.camp.it.book.store.model.dto.OrderPositionDTO;
import pl.camp.it.book.store.services.IOrderPositionService;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/orderposition")
public class RestApiOrderPositionController {

    @Autowired
    IOrderPositionService orderPositionService;

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<OrderPositionDTO> getOrderPositionById(@PathVariable int id) {
        Optional<OrderPosition> orderPositionBox =
                this.orderPositionService.getOrderPositionById(id);
        return orderPositionBox
                .map(op -> ResponseEntity.status(HttpStatus.OK)
                        .body(new OrderPositionDTO(op)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}

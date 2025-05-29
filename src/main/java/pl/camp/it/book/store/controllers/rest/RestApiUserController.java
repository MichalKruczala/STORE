package pl.camp.it.book.store.controllers.rest;

import jdk.jshell.spi.ExecutionControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.camp.it.book.store.exceptions.UserLoginExistException;
import pl.camp.it.book.store.exceptions.UserNotExistException;
import pl.camp.it.book.store.model.User;
import pl.camp.it.book.store.model.dto.UserDTO;
import pl.camp.it.book.store.model.dto.UserResponseDTO;
import pl.camp.it.book.store.services.IUserService;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/user")
public class RestApiUserController {

    @Autowired
    IUserService userService;
    @RequestMapping(path = "", method = RequestMethod.GET)
    public ResponseEntity<UserResponseDTO> getUserByLogin(@RequestParam String login) {
        Optional<User> userBox = this.userService.getUserByLogin(login);
        return userBox
                .map(user -> ResponseEntity.status(HttpStatus.OK)
                        .body(new UserResponseDTO(user)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<User> saveUser(@RequestBody UserDTO userDTO) {
        try {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(this.userService.persistUser(userDTO));
        } catch (UserLoginExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable int id) throws ExecutionControl.NotImplementedException {
        Optional<User> userBox = this.userService.getUserById(id);
        return userBox
                .map(user -> ResponseEntity.status(HttpStatus.OK)
                        .body(new UserResponseDTO(user)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@RequestBody UserDTO userDTO,
                                           @PathVariable int id) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(this.userService.updateUser(userDTO, id));
        } catch (UserNotExistException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (ExecutionControl.NotImplementedException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

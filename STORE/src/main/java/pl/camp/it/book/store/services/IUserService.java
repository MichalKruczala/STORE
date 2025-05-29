package pl.camp.it.book.store.services;

import jdk.jshell.spi.ExecutionControl;
import pl.camp.it.book.store.model.User;
import pl.camp.it.book.store.model.dto.UserDTO;

import java.util.Optional;

public interface IUserService {
    Optional<User> getUserByLogin(String login);
    User persistUser(UserDTO userDTO);
    Optional<User> getUserById(int id) throws ExecutionControl.NotImplementedException;
    User updateUser(UserDTO userDTO, int id) throws ExecutionControl.NotImplementedException;
}

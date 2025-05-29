package pl.camp.it.book.store.services;

import pl.camp.it.book.store.model.User;

import java.sql.SQLIntegrityConstraintViolationException;

public interface IAuthenticationService {
    void authenticate(String login, String password);
    void logout();
    void registerUser(User user);
}

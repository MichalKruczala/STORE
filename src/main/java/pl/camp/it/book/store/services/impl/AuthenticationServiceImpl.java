package pl.camp.it.book.store.services.impl;

import jakarta.annotation.Resource;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.camp.it.book.store.database.IUserDAO;
import pl.camp.it.book.store.model.User;
import pl.camp.it.book.store.services.IAuthenticationService;
import pl.camp.it.book.store.session.SessionObject;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements IAuthenticationService {

    @Autowired
    IUserDAO userDAO;

    @Resource
    SessionObject sessionObject;

    @Override
    public void authenticate(String login, String password) {
        Optional<User> userBox = this.userDAO.getUserByLogin(login);
        if(userBox.isPresent() && userBox.get().getPassword().equals(DigestUtils.md5Hex(password))) {
            this.sessionObject.setUser(
                    new User.UserBuilder()
                            .clone(userBox.get())
                            .password(null)
                            .build()
            );
        }
    }

    @Override
    public void logout() {
        this.sessionObject.setUser(null);
    }

    @Override
    public void registerUser(User user) {
        user.setRole(User.Role.USER);
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        this.userDAO.persistUser(user);
    }
}

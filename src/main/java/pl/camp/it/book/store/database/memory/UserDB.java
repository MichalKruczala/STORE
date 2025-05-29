package pl.camp.it.book.store.database.memory;

import jdk.jshell.spi.ExecutionControl;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.camp.it.book.store.database.sequence.IIdSequence;
import pl.camp.it.book.store.database.IUserDAO;
import pl.camp.it.book.store.database.sequence.IUserIdSequence;
import pl.camp.it.book.store.exceptions.UserLoginExistException;
import pl.camp.it.book.store.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDB implements IUserDAO {
    private final IIdSequence userIdSequence;
    private final List<User> users = new ArrayList<>();

    public UserDB(@Autowired IUserIdSequence userIdSequence) {
        this.userIdSequence = userIdSequence;
        this.users.add(new User(this.userIdSequence.getId(),
                "Mateusz", "Bereda",
                "admin", "21232f297a57a5a743894a0e4a801fc3",
                User.Role.ADMIN));

        this.users.add(new User(this.userIdSequence.getId(),
                "Jan", "Kowalski",
                "janek123", "8f6de86901ba906047425ff9f71550dd",
                User.Role.USER));
    }

    @Override
    public Optional<User> getUserByLogin(final String login) {
        /*for(User user : this.users) {
            if(user.getLogin().equals(login)) {
                return Optional.of(user);
            }
        }

        return Optional.empty();*/

        return this.users.stream().filter(u -> u.getLogin().equals(login)).findFirst();
    }

    @Override
    public void persistUser(User user) {
        if(getUserByLogin(user.getLogin()).isPresent()) {
            throw new UserLoginExistException();
        }
        user.setId(this.userIdSequence.getId());
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        this.users.add(user);
    }

    @Override
    public Optional<User> getUserById(int id) throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Not implemented");
    }

    @Override
    public void updateUser(User user) throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Not implemented");
    }
}

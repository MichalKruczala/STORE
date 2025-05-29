package pl.camp.it.book.store.services.impl;

import jakarta.persistence.NoResultException;
import jdk.jshell.spi.ExecutionControl;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.camp.it.book.store.database.IUserDAO;
import pl.camp.it.book.store.exceptions.UserNotExistException;
import pl.camp.it.book.store.model.User;
import pl.camp.it.book.store.model.dto.UserDTO;
import pl.camp.it.book.store.services.IUserService;

import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    IUserDAO userDAO;

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public Optional<User> getUserByLogin(String login) {
        return this.userDAO.getUserByLogin(login);
    }

    @Override
    public User persistUser(UserDTO userDTO) {
        User user = new User();
        user.setLogin(userDTO.getLogin());
        user.setPassword(DigestUtils.md5Hex(userDTO.getPassword()));
        user.setSurname(userDTO.getSurname());
        user.setName(userDTO.getName());
        user.setRole(userDTO.getRole());
        this.userDAO.persistUser(user);
        return user;
    }

    @Override
    public Optional<User> getUserById(int id) throws ExecutionControl.NotImplementedException {
        return this.userDAO.getUserById(id);
    }

    @Override
    public User updateUser(UserDTO userDTO, int id) throws ExecutionControl.NotImplementedException {
        Session session = this.sessionFactory.openSession();
        Query<User> query = session.createQuery(
                "FROM pl.camp.it.book.store.model.User WHERE id = :id",
                User.class
        );
        query.setParameter("id", id);
        User user = null;
        try {
            user = query.getSingleResult();
        } catch (NoResultException e) {
            session.close();
            throw new UserNotExistException();
        }
        user.setRole(userDTO.getRole());
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setLogin(userDTO.getLogin());
        user.setPassword(DigestUtils.md5Hex(userDTO.getPassword()));

        session.beginTransaction();
        session.merge(user);
        session.getTransaction().commit();
        session.close();

        user.getOrders().forEach(order -> order.setUser(null));

        return user;
    }
}

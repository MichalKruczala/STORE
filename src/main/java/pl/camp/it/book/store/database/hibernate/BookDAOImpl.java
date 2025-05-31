package pl.camp.it.book.store.database.hibernate;

import jakarta.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.camp.it.book.store.database.IBookDAO;
import pl.camp.it.book.store.model.Book;

import java.util.List;
import java.util.Optional;

@Repository
public class BookDAOImpl extends EntityManager implements IBookDAO {

    public BookDAOImpl(@Autowired SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<Book> getBooks() {
        Session session = this.sessionFactory.openSession();
        Query<Book> query = session.createQuery(
                "FROM tbook ", Book.class);
        List<Book> books = query.getResultList();
        session.close();
        return books;
    }

    @Override
    public List<Book> getBooksByPattern(String pattern) {
        Session session = this.sessionFactory.openSession();
        Query<Book> query = session.createQuery(
                "FROM pl.camp.it.book.store.model.Book WHERE title LIKE :pattern OR author like :pattern",
                Book.class);
        query.setParameter("pattern", "%" + pattern + "%");
        List<Book> books = query.getResultList();
        session.close();
        return books;
    }

    @Override
    public void persistBook(Book book) {
        super.persist(book);
    }

    @Override
    public Optional<Book> getBookById(int id) {
        Session session = this.sessionFactory.openSession();
        Query<Book> query = session.createQuery(
                "FROM pl.camp.it.book.store.model.Book WHERE id = :id",
                Book.class);
        query.setParameter("id", id);
        Optional<Book> result = Optional.empty();
        try {
            result = Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
        }
        session.close();
        return result;
    }

    @Override
    public void updateBook(Book book) {
        super.update(book);
    }

    @Override
    public void deleteBook(Book book) {
        Session session = this.sessionFactory.openSession();
        session.beginTransaction();
        session.remove(book);
        session.getTransaction().commit();
        session.close();
    }
}

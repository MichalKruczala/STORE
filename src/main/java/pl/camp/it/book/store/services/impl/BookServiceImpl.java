package pl.camp.it.book.store.services.impl;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.camp.it.book.store.database.IBookDAO;
import pl.camp.it.book.store.model.Book;
import pl.camp.it.book.store.services.IBookService;
import pl.camp.it.book.store.session.SessionObject;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements IBookService {

    @Autowired
    IBookDAO bookDAO;

    @Resource
    SessionObject sessionObject;

    @Override
    public List<Book> getBooks() {
        Optional<String> patternBox = this.sessionObject.getPattern();

        if(patternBox.isEmpty()) {
            return this.bookDAO.getBooks();
        } else {
            return this.bookDAO.getBooksByPattern(patternBox.get());
        }
    }

    @Override
    public List<Book> getBooksByPattern(String pattern) {
        return this.bookDAO.getBooksByPattern(pattern);
    }

    @Override
    public void persistBook(Book book) {
        this.bookDAO.persistBook(book);
    }

    @Override
    public Optional<Book> getBookById(int id) {
        return this.bookDAO.getBookById(id);
    }

    @Override
    public void updateBook(Book book, int id) {
        book.setId(id);
        this.bookDAO.updateBook(book);
    }
}

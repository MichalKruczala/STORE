package pl.camp.it.book.store.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.camp.it.book.store.database.IBookDAO;
import pl.camp.it.book.store.model.Book;
import pl.camp.it.book.store.services.IBookService;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements IBookService {

    @Autowired
    IBookDAO bookDAO;

    @Override
    public List<Book> getBooks() {
        return this.bookDAO.getBooks();
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
    @Override
    public void deleteBook(int bookId) {
        Optional<Book> bookBox = this.bookDAO.getBookById(bookId);
        bookBox.ifPresent(bookDAO::deleteBook);
    }
}
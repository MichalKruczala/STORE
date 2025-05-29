package pl.camp.it.book.store.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.camp.it.book.store.model.Book;
import pl.camp.it.book.store.model.dto.BooksDTO;
import pl.camp.it.book.store.services.IBookService;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/book")
public class RestApiBookController {

    @Autowired
    IBookService bookService;

    @RequestMapping(path = "", method = RequestMethod.GET)
    public BooksDTO getBooks(@RequestParam(required = false) String pattern) {
        BooksDTO response = new BooksDTO();
        if(pattern == null) {
            response.getBooks().addAll(this.bookService.getBooks());
        } else {
            response.getBooks().addAll(this.bookService.getBooksByPattern(pattern));
        }
        return response;
    }

    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<Book> saveBook(@RequestBody Book book) throws SQLIntegrityConstraintViolationException {
        this.bookService.persistBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

    @RequestMapping(path = "/{bookId}", method = RequestMethod.GET)
    public ResponseEntity<Book> getBookById(@PathVariable int bookId) {
        Optional<Book> bookBox = this.bookService.getBookById(bookId);
        return bookBox
                .map(book -> ResponseEntity.status(HttpStatus.OK).body(book))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @RequestMapping(path = "/{bookId}", method = RequestMethod.PUT)
    public Book updateBook(@PathVariable int bookId,
                           @RequestBody Book book) {
        this.bookService.updateBook(book, bookId);
        return book;
    }
}

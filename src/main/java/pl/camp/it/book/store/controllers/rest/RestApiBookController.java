package pl.camp.it.book.store.controllers.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Get all books or books matching a pattern")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Books returned successfully")
    })
    @GetMapping("")
    public BooksDTO getBooks(@RequestParam(required = false) String pattern) {
        BooksDTO response = new BooksDTO();
        if (pattern == null) {
            response.getBooks().addAll(this.bookService.getBooks());
        } else {
            response.getBooks().addAll(this.bookService.getBooksByPattern(pattern));
        }
        return response;
    }

    @Operation(summary = "Save a new book")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Book created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid book data")
    })
    @PostMapping("")
    public ResponseEntity<Book> saveBook(@RequestBody Book book) throws SQLIntegrityConstraintViolationException {
        this.bookService.persistBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

    @Operation(summary = "Get book by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Book found"),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    @GetMapping("/{bookId}")
    public ResponseEntity<Book> getBookById(@PathVariable int bookId) {
        Optional<Book> bookBox = this.bookService.getBookById(bookId);
        return bookBox
                .map(book -> ResponseEntity.status(HttpStatus.OK).body(book))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(summary = "Update book by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Book updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid book data")
    })
    @PutMapping("/{bookId}")
    public Book updateBook(@PathVariable int bookId, @RequestBody Book book) {
        this.bookService.updateBook(book, bookId);
        return book;
    }

    @Operation(summary = "Delete book by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Book deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable int bookId) {
        this.bookService.deleteBook(bookId);
        return ResponseEntity.ok().build();
    }
}
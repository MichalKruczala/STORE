package pl.camp.it.book.store.controllers;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.camp.it.book.store.exceptions.BookValidationException;
import pl.camp.it.book.store.model.Book;
import pl.camp.it.book.store.services.IBookService;
import pl.camp.it.book.store.session.SessionObject;
import pl.camp.it.book.store.validators.BookValidator;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Optional;

@Controller
public class BookController {

    @Resource
    SessionObject sessionObject;

    @Autowired
    IBookService bookService;

    @RequestMapping(path = "/book/add", method = RequestMethod.GET)
    public String addBook(Model model) {
        model.addAttribute("sessionObject", this.sessionObject);
        model.addAttribute("book", new Book());
        return "book-form";
    }

    @RequestMapping(path = "/book/add", method = RequestMethod.POST)
    public String addBook(@ModelAttribute Book book) throws SQLIntegrityConstraintViolationException {
        try {
            BookValidator.validateBook(book);
        } catch (BookValidationException e) {
            e.printStackTrace();
            return "redirect:/book/add";
        }
        this.bookService.persistBook(book);
        return "redirect:/";
    }

    @RequestMapping(path = "/book/edit/{bookId}", method = RequestMethod.GET)
    public String editBook(@PathVariable int bookId, Model model) {
        Optional<Book> bookBox = this.bookService.getBookById(bookId);
        if(bookBox.isEmpty()) {
            return "redirect:/";
        }
        model.addAttribute("sessionObject", this.sessionObject);
        model.addAttribute("book", bookBox.get());
        return "book-form";
    }

    @RequestMapping(path = "/book/edit/{bookId}", method = RequestMethod.POST)
    public String editBook(@ModelAttribute Book book,
                           @PathVariable int bookId) {
        try {
            BookValidator.validateBook(book);
        } catch (BookValidationException e) {
            e.printStackTrace();
            return "redirect:/book/edit/"+bookId;
        }
        this.bookService.updateBook(book, bookId);
        return "redirect:/";
    }
}

package pl.camp.it.book.store.model.dto;

import pl.camp.it.book.store.model.Book;

import java.util.ArrayList;
import java.util.List;

public class BooksDTO {
    private final List<Book> books = new ArrayList<>();

    public List<Book> getBooks() {
        return books;
    }
}

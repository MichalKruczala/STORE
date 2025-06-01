package controllersTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import pl.camp.it.book.store.database.IBookDAO;
import pl.camp.it.book.store.model.Book;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class RestApiBookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IBookDAO bookDAO;

    @BeforeEach
    void setUp() {
        bookDAO.getBooks().forEach(bookDAO::deleteBook);
    }

    @Test
    @DisplayName("Should create a new book")
    void shouldCreateBook() throws Exception {
        Book book = new Book(0, "New Book", "Author Name", 59.99, 10, "123-456");

        mockMvc.perform(post("/api/v1/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", is("New Book")))
                .andExpect(jsonPath("$.author", is("Author Name")))
                .andExpect(jsonPath("$.isbn", is("123-456")));
    }

    @Test
    @DisplayName("Should return all books")
    void shouldReturnAllBooks() throws Exception {
        Book book1 = new Book(0, "Title1", "Author1", 10.0, 5, "111");
        Book book2 = new Book(0, "Title2", "Author2", 20.0, 8, "222");
        bookDAO.persistBook(book1);
        bookDAO.persistBook(book2);

        mockMvc.perform(get("/api/v1/book"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.books", hasSize(2)))
                .andExpect(jsonPath("$.books[*].title", containsInAnyOrder("Title1", "Title2")));
    }

    @Test
    @DisplayName("Should get book by ID")
    void shouldGetBookById() throws Exception {
        Book book = new Book(0, "ById", "Author", 15.0, 1, "333");
        bookDAO.persistBook(book);
        int id = bookDAO.getBooksByPattern("ById").get(0).getId();

        mockMvc.perform(get("/api/v1/book/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("ById")));
    }

    @Test
    @DisplayName("Should update a book")
    void shouldUpdateBook() throws Exception {
        Book book = new Book(0, "Old Title", "Old Author", 9.99, 2, "444");
        bookDAO.persistBook(book);
        int id = bookDAO.getBooksByPattern("Old").get(0).getId();

        book.setTitle("Updated Title");
        book.setAuthor("Updated Author");

        mockMvc.perform(put("/api/v1/book/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Updated Title")))
                .andExpect(jsonPath("$.author", is("Updated Author")));
    }

    @Test
    @DisplayName("Should delete a book")
    void shouldDeleteBook() throws Exception {
        Book book = new Book(0, "ToDelete", "Author", 30.0, 4, "555");
        bookDAO.persistBook(book);
        int id = bookDAO.getBooksByPattern("ToDelete").get(0).getId();

        mockMvc.perform(delete("/api/v1/book/" + id))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/v1/book/" + id))
                .andExpect(status().isNotFound());
    }

    @Configuration
    static class TestConfig {
        @Bean
        public ObjectMapper objectMapper() {
            return new ObjectMapper();
        }
    }
}
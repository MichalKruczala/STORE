import org.junit.jupiter.api.Test;
import pl.camp.it.book.store.exceptions.BookValidationException;
import pl.camp.it.book.store.validators.BookValidator;

import static org.junit.jupiter.api.Assertions.*;

public class BookValidatorTest {

    @Test
    void testValidateTitle_valid() {
        assertDoesNotThrow(() -> BookValidator.validateTitle("Tytuł"));
    }

    @Test
    void testValidateTitle_invalid() {
        assertThrows(BookValidationException.class, () -> BookValidator.validateTitle("tytuł"));
    }

    @Test
    void testValidateAuthor_valid() {
        assertDoesNotThrow(() -> BookValidator.validateAuthor("Jan Kowalski"));
    }

    @Test
    void testValidateAuthor_invalid() {
        assertThrows(BookValidationException.class, () -> BookValidator.validateAuthor("janek"));
    }

    @Test
    void testValidateIsbn_valid() {
        assertDoesNotThrow(() -> BookValidator.validateIsbn("978-83-01-12345-6"));
    }

    @Test
    void testValidateIsbn_invalid() {
        assertThrows(BookValidationException.class, () -> BookValidator.validateIsbn("123-45-678"));
    }
}

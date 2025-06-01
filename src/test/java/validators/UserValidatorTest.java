import org.junit.jupiter.api.Test;
import pl.camp.it.book.store.exceptions.UserValidationException;
import pl.camp.it.book.store.validators.UserValidator;

import static org.junit.jupiter.api.Assertions.*;

public class UserValidatorTest {

    @Test
    void testValidateLogin_valid() {
        assertDoesNotThrow(() -> UserValidator.validateLogin("Login123"));
    }

    @Test
    void testValidateLogin_invalid() {
        assertThrows(UserValidationException.class, () -> UserValidator.validateLogin("abc"));
    }

    @Test
    void testValidatePassword_valid() {
        assertDoesNotThrow(() -> UserValidator.validatePassword("Password1"));
    }

    @Test
    void testValidatePassword_invalid() {
        assertThrows(UserValidationException.class, () -> UserValidator.validatePassword("12"));
    }

    @Test
    void testValidateName_valid() {
        assertDoesNotThrow(() -> UserValidator.validateName("Marek"));
    }

    @Test
    void testValidateName_invalid() {
        assertThrows(UserValidationException.class, () -> UserValidator.validateName("marek"));
    }

    @Test
    void testValidateSurname_valid() {
        assertDoesNotThrow(() -> UserValidator.validateSurname("Nowak"));
    }

    @Test
    void testValidateSurname_withDash_valid() {
        assertDoesNotThrow(() -> UserValidator.validateSurname("Nowak-Kowalski"));
    }

    @Test
    void testValidateSurname_invalid() {
        assertThrows(UserValidationException.class, () -> UserValidator.validateSurname("kowalski"));
    }

    @Test
    void testValidatePasswordsEquality_valid() {
        assertDoesNotThrow(() -> UserValidator.validatePasswordsEquality("12345", "12345"));
    }

    @Test
    void testValidatePasswordsEquality_invalid() {
        assertThrows(UserValidationException.class, () -> UserValidator.validatePasswordsEquality("12345", "123456"));
    }
}
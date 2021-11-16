package C868.Test;

import C868.Helper.DataValidation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DataValidationTest {

    @Test
    void isValidPhoneNumber() {
        Assertions.assertTrue(DataValidation.isValidPhoneNumber("775-688-9650"));
        Assertions.assertFalse(DataValidation.isValidPhoneNumber("77-688-9650"));
    }

    @Test
    void isValidName() {
        Assertions.assertTrue(DataValidation.isValidName("Patrick Denney"));
        Assertions.assertFalse(DataValidation.isValidName("PatrickDenney"));

    }

    @Test
    void isValidAddress() {
        Assertions.assertTrue(DataValidation.isValidAddress("123 Any St. , Atlanta, GA"));
        Assertions.assertFalse(DataValidation.isValidAddress("Any St. , Atlanta, GA"));

    }

    @Test
    void isValidPostalCode() {
        Assertions.assertTrue(DataValidation.isValidPostalCode("36251"));
        Assertions.assertFalse(DataValidation.isValidPostalCode("3625155"));
    }

    @Test
    void isValidTime() {
        Assertions.assertTrue(DataValidation.isValidTime("12:01"));
        Assertions.assertFalse(DataValidation.isValidTime("1201"));

    }

    @Test
    void isValidDate() {
        Assertions.assertTrue(DataValidation.isValidDate("2021-12-01"));
        Assertions.assertFalse(DataValidation.isValidDate("20211201"));
    }

    @Test
    void isValidLocation() {
        Assertions.assertTrue(DataValidation.isValidLocation("Location"));
        Assertions.assertFalse(DataValidation.isValidLocation(""));
    }

    @Test
    void isValidTitle() {
        Assertions.assertTrue(DataValidation.isValidTitle("Title"));
        Assertions.assertFalse(DataValidation.isValidTitle(""));


    }
}
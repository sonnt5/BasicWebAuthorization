package util.controller;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ValidationHelperTest {
    @Test
    public void testValidStringWithinRangeReturnsTrue() {
        assertTrue(ValidationHelper.validateStringLength("hello", 1, 10));
    }

    @Test
    public void testStringOutsideRangeReturnsFalse() {
        assertFalse(ValidationHelper.validateStringLength("this string is too long", 1, 5));
    }

    @Test
    public void testNullInputThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> ValidationHelper.validateStringLength(null, 1, 5));
    }
}

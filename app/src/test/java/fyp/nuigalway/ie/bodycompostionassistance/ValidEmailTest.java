package fyp.nuigalway.ie.bodycompostionassistance;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by fergalbroderick on 03/04/2017.
 */

public class ValidEmailTest {

    @Test
    public void EmailValidTest_Simple_ReturnsTrue() {

        assertTrue(RegisterActivity.isValidEmail("test@email.com"));
    }


    @Test
    public void EmailValidTest_Long_ReturnsTrue() {
        assertTrue(RegisterActivity.isValidEmail("test@email.co.uk"));
    }


    @Test
    public void EmailValidTest_DoubleDot_ReturnsFalse() {
        assertFalse(RegisterActivity.isValidEmail("test@email..com"));
    }

    @Test
    public void EmailValidTest_Null_ReturnFalse() {
        assertFalse(RegisterActivity.isValidEmail(null));
    }

}

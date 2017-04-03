package fyp.nuigalway.ie.bodycompostionassistance;

import android.support.v4.media.MediaMetadataCompat;

import org.junit.Test;

import static org.junit.Assert.*;
/**
 * Created by fergalbroderick on 03/04/2017.
 */

public class ValidPasswordTest
{

    @Test
    public void PasswordValidTest_ReturnsTrue(){
        assertTrue(RegisterActivity.isValidPassword("abcdef"));
    }

    @Test
    public void PasswordValidTest_Short_ReturnsFalse(){
        assertFalse(RegisterActivity.isValidPassword("abcd"));
    }

    @Test
    public void PasswordValidTest_Null_ReturnsFalse(){
        assertFalse(RegisterActivity.isValidPassword(null));
    }
}

package com.tstasks.sanchellios.navicostores;

import com.tstasks.sanchellios.navicostores.phone_calls.PhoneFormatter;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by alex on 06.08.16.
 */
public class PhoneFormatterTest {
    @Test
    public void testPhoneFormatter(){
        PhoneFormatter phoneFormatter = new PhoneFormatter();
        assertEquals("00009990099", phoneFormatter.getReformattedPhone("0 (000) 999-00-99"));
    }
}

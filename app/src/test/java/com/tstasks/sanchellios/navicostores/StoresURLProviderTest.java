package com.tstasks.sanchellios.navicostores;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by alex on 04.08.16.
 */
public class StoresURLProviderTest {

    StoresURLProvider provider;

    @Before
    public void setUp() throws Exception {
        provider = new StoresURLProvider();
    }

    @Test
    public void testGetStoresURL() throws Exception {
        assertEquals("http://aschoolapi.appspot.com/stores", provider.getStoresURL());
    }

    @Test
    public void testGetStoreInstruments() throws Exception {
        assertEquals("http://aschoolapi.appspot.com/stores/1/instruments", provider.getStoreInstruments(1));
    }
}
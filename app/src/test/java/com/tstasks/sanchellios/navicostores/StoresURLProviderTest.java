package com.tstasks.sanchellios.navicostores;

import com.tstasks.sanchellios.navicostores.networking.StoresURLProvider;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StoresURLProviderTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testGetStoresURL() throws Exception {
        assertEquals("http://aschoolapi.appspot.com/stores", StoresURLProvider.getStoresURL());
    }

    @Test
    public void testGetStoreInstruments() throws Exception {
        assertEquals("http://aschoolapi.appspot.com/stores/1/instruments", StoresURLProvider.getStoreInstruments(1));
    }
}
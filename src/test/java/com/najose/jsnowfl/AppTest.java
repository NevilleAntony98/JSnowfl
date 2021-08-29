package com.najose.jsnowfl;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AppTest {
    @Test
    public void testApp() {
        Snowfl snowfl = new Snowfl();
        assertTrue(snowfl.search("Ubuntu").length != 0);
    }

    @Test
    public void testMagnetFetch() {
        Snowfl snowfl = new Snowfl();
        for (SearchResult searchResult : snowfl.search("Ubuntu")) {
            if (searchResult.magnet == null) {
                searchResult.fetchMagnet(snowfl.getToken());
                assertTrue(searchResult.magnet != null);

                return;
            }
        }
    }
}

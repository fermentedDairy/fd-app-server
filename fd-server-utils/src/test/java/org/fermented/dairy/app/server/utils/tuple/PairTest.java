package org.fermented.dairy.app.server.utils.tuple;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PairTest {

    @Test
    void testPairCreation() {
        Pair<String, Integer> pair = new Pair<>("test", 123);
        assertEquals("test", pair.left());
        assertEquals(123, pair.right());
    }

    @Test
    void testMapLeft() {
        Pair<String, Integer> pair = new Pair<>("test", 123);
        Pair<Integer, Integer> mappedPair = pair.mapLeft(String::length);
        assertEquals(4, mappedPair.left());
        assertEquals(123, mappedPair.right());
    }

    @Test
    void testMapRight() {
        Pair<String, Integer> pair = new Pair<>("test", 123);
        Pair<String, String> mappedPair = pair.mapRight(String::valueOf);
        assertEquals("test", mappedPair.left());
        assertEquals("123", mappedPair.right());
    }
}

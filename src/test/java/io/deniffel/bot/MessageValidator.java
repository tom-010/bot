package io.deniffel.bot;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class MessageValidator {

    @Test
    public void null_notValid() {
        assertFalse(new Message(null).valid());
    }

    @Test
    public void randomString_valid() {
        assertTrue(new Message("a string").valid());
    }

    @Test
    public void emptyString_notValid() {
        assertFalse(new Message("").valid());
    }

    @Test
    public void containsOnlySpaces_notValid() {
        assertFalse(new Message(" ").valid());
        assertFalse(new Message("  ").valid());
        assertFalse(new Message("   ").valid());
        assertFalse(new Message("    ").valid());
        assertFalse(new Message("     ").valid());
    }
}

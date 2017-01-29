package com.codeheadsystems.shash;

import org.junit.Test;

import java.util.Random;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class HashHolderTest {

    private Random random = new Random();

    private boolean isSame(byte[] a1, byte[] a2) {
        if (a1.length != a2.length) {
            return false;
        }
        for (int i = 0; i < a1.length; i++) {
            if (a1[i] != a2[i]) {
                return false;
            }
        }
        return true;
    }

    private byte[] getRandomBytes(int size) {
        byte[] result = new byte[size];
        random.nextBytes(result);
        return result;
    }

    @Test
    public void testStringManipulation() throws BadHashHolderException {
        byte[] salt = getRandomBytes(8);
        byte[] hash = getRandomBytes(32);
        HashHolder hashHolder1 = new HashHolder(salt, hash);
        String hashString = hashHolder1.getAsString();
        HashHolder hashHolder2 = new HashHolder(hashString);
        assertTrue(isSame(hashHolder1.getSalt(), hashHolder2.getSalt()));
        assertTrue(isSame(hashHolder1.getHash(), hashHolder2.getHash()));
    }

    @Test
    public void testStringManipulationNoSalt() throws BadHashHolderException {
        byte[] salt = getRandomBytes(0);
        byte[] hash = getRandomBytes(32);
        HashHolder hashHolder1 = new HashHolder(salt, hash);
        String hashString = hashHolder1.getAsString();
        HashHolder hashHolder2 = new HashHolder(hashString);
        assertEquals(0, hashHolder1.getSalt().length);
        assertEquals(0, hashHolder2.getSalt().length);
        assertTrue(isSame(hashHolder1.getHash(), hashHolder2.getHash()));
    }

}

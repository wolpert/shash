package com.codeheadsystems.shash;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static com.codeheadsystems.shash.StringManipulator.toBytes;
import static org.junit.Assert.*;

public class HasherBuilderTest {

    private HasherBuilder hasherBuilder;

    @Before
    public void setupDefaults() {
        hasherBuilder = new HasherBuilder().randomProvider(Random::new);
    }

    @Test
    public void testDefaultUsecase() {
        Hasher hasher = hasherBuilder.hashAlgorithm(SupportedHashAlgorithm.getMinSCryptAlgo()).build();
        String text = "blah";
        HashHolder hash = hasher.hash(text);
        assertTrue(hasher.isSame(hash, text));
        assertNotSame(hash, toBytes(text));
    }

    @Test
    public void testDifferentHashersNotTheSame() {
        Hasher hasher1 = hasherBuilder.hashAlgorithm(SupportedHashAlgorithm.getMinSCryptAlgo()).build();
        Hasher hasher2 = hasherBuilder.hashAlgorithm(SupportedHashAlgorithm.getSCryptAlgo()).build();
        String text = "blah";
        HashHolder hash = hasher1.hash(text);
        assertTrue(hasher1.isSame(hash, text));
        assertFalse(hasher2.isSame(hash, text));

        // check against new hasher
        hash = hasher2.hash(text);
        assertTrue(hasher2.isSame(hash, text));
    }

    @Test
    public void testMiss() {
        Hasher hasher = hasherBuilder.hashAlgorithm(SupportedHashAlgorithm.getMinSCryptAlgo()).build();
        String text = "blah";
        HashHolder hash = hasher.hash(text);
        assertFalse(hasher.isSame(hash, text + "1"));
    }

    @Test
    public void testByteHashing() {
        Hasher hasher = hasherBuilder.hashAlgorithm(SupportedHashAlgorithm.getMinSCryptAlgo()).build();
        byte[] startBytes = new byte[512];
        Random random = new Random();
        random.nextBytes(startBytes);
        HashHolder hashHolder = hasher.hash(startBytes);

        assertEquals(32, hashHolder.getHash().length);
        assertEquals(32, hashHolder.getSalt().length);
        assertTrue(hasher.isSame(hashHolder, startBytes));

        //force it changed
        random.nextBytes(startBytes);
        assertFalse(hasher.isSame(hashHolder, startBytes));
    }

}

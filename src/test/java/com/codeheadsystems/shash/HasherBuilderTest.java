package com.codeheadsystems.shash;

import org.junit.Before;
import org.junit.Test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
        assertTrue(hasher.isSame(hash.getSalt(), hash.getHash(), text));
        assertNotSame(hash, toBytes(text));
    }

    private byte[] digest(byte[] salt, byte[] hash) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(salt);
            return md5.digest(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Test
    public void testCustomizeHasher() {
        Hasher hasher = hasherBuilder
                .saltSize(16)
                .hashAlgorithm((bytes, salt) -> this.digest(salt, bytes)).build();
        String text = "blah";
        HashHolder hash = hasher.hash(text);
        assertTrue(hasher.isSame(hash, text));
        assertFalse(hasher.isSame(hash, text + "1"));
        assertNotSame(hash, toBytes(text));
    }

    @Test
    public void noSaltTestLikeForHMac() {
        Hasher hasher = hasherBuilder
                .saltSize(0)
                .hashAlgorithm((bytes, salt) -> this.digest(salt, bytes)).build();
        String text = "blah";
        HashHolder hash = hasher.hash(text);
        assertTrue(hasher.isSame(hash, text));
        assertFalse(hasher.isSame(hash, text + "1"));
        assertNotSame(hash, toBytes(text));
        assertEquals(0, hash.getSalt().length);
    }

    @Test
    public void testCustomSaltSize() {
        Hasher hasher = hasherBuilder
                .saltSize(8)
                .hashAlgorithm(SupportedHashAlgorithm.getMinSCryptAlgo())
                .build();
        String text = "blah";
        HashHolder hash = hasher.hash(text);
        assertTrue(hasher.isSame(hash, text));
        assertFalse(hasher.isSame(hash, text + "1"));
        assertEquals(8, hash.getSalt().length);
    }

    /**
     * This is a slow test because of the default SCryptAlgo in play
     */
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

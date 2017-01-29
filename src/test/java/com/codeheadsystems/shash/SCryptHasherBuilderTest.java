package com.codeheadsystems.shash;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static com.codeheadsystems.shash.StringManipulator.toBytes;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

public class SCryptHasherBuilderTest {

    private HasherBuilder hasherBuilder;

    @Before
    public void setupDefaults() {
        hasherBuilder = new HasherBuilder().randomProvider(Random::new);
    }

    @Test
    public void testDefaultUsecase() {
        Hasher hasher = hasherBuilder
                .hashAlgorithm(SupportedHashAlgorithm.getMinSCryptAlgo())
                .build();
        String text = "blah";
        HashHolder hash = hasher.hash(text);
        assertTrue(hasher.isSame(hash, text));
        assertNotSame(hash, toBytes(text));
    }

}

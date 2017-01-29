package com.codeheadsystems.shash.impl;

import com.codeheadsystems.shash.Hasher;
import org.bouncycastle.crypto.generators.SCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeheadsystems.shash.StringManipulator.CHARSET;
import static com.codeheadsystems.shash.StringManipulator.toBytes;
import static java.lang.System.arraycopy;

public abstract class AbstractHasher implements Hasher {

    private Logger logger = LoggerFactory.getLogger(AbstractHasher.class);

    private final RandomProvider randomProvider;
    private final int saltSize;

    AbstractHasher(int saltSize, RandomProvider randomProvider) {
        this.saltSize = saltSize;
        this.randomProvider = randomProvider;
    }

    private static byte[] add(byte[] a1, byte[] a2) {
        byte[] result = new byte[a1.length + a2.length];
        arraycopy(a1, 0, result, 0, a1.length);
        arraycopy(a2, 0, result, a1.length, a2.length);
        return result;
    }

    @Override
    public byte[] hash(byte[] bytes) {
        byte[] salt = generateSalt();
        logger.debug("internalGenerateHash({},{})", bytes.length, salt.length);
        return add(salt, internalHash(bytes, salt));
    }

    protected abstract byte[] internalHash(byte[] bytes, byte[] salt);

    @Override
    public byte[] hash(String hashingWord) {
        return hash(toBytes(hashingWord));
    }

    private byte[] getSalt(byte[] hashedBytes) {
        byte[] salt = new byte[saltSize];
        arraycopy(hashedBytes, 0, salt, 0, saltSize);
        return salt;
    }

    @Override
    public byte[] generateSalt() {
        return randomProvider.getRandomBytes(saltSize);
    }

    @Override
    public boolean isSame(byte[] hashedBytes, byte[] payload) {
        byte[] salt = getSalt(hashedBytes);
        byte[] payloadHash = internalHash(payload, salt);
        boolean same = true;
        for(int i=saltSize; i < hashedBytes.length; i++) {
            same = payloadHash[i-saltSize] == hashedBytes[i] && same; // TODO: optimizing compiler may make this faster then we want.
        }
        return same;
    }

    @Override
    public boolean isSame(byte[] hashedBytes, String payload) {
        return isSame(hashedBytes, payload.getBytes(CHARSET));
    }
}

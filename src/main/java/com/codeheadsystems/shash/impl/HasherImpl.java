package com.codeheadsystems.shash.impl;

import com.codeheadsystems.shash.HashAlgorithm;
import com.codeheadsystems.shash.HashHolder;
import com.codeheadsystems.shash.Hasher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeheadsystems.shash.StringManipulator.CHARSET;
import static com.codeheadsystems.shash.StringManipulator.toBytes;
import static java.lang.System.arraycopy;

public class HasherImpl implements Hasher {

    private static Logger logger = LoggerFactory.getLogger(HasherImpl.class);
    private final RandomProvider randomProvider;
    private final int saltSize;
    private final HashAlgorithm hashAlgorithm;

    public HasherImpl(int saltSize, RandomProvider randomProvider, HashAlgorithm hashAlgorithm) {
        this.saltSize = saltSize;
        this.randomProvider = randomProvider;
        this.hashAlgorithm = hashAlgorithm;
    }

    @Override
    public HashHolder hash(byte[] bytes) {
        byte[] salt = generateSalt();
        logger.trace("hashing({},{},{})", bytes.length, salt.length, hashAlgorithm.getClass());
        return new HashHolder(salt, hashAlgorithm.hash(bytes, salt));
    }

    @Override
    public HashHolder hash(String hashingWord) {
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
    public boolean isSame(HashHolder hashedBytes, byte[] payload) {
        byte[] salt = hashedBytes.getSalt();
        byte[] payloadHash = hashAlgorithm.hash(payload, salt);
        boolean same = true;
        byte[] previouslyHash = hashedBytes.getHash();
        for (int i = saltSize; i < previouslyHash.length; i++) {
            same = payloadHash[i - saltSize] == previouslyHash[i] && same; // TODO: optimizing compiler may make this faster then we want.
        }
        return same;
    }

    @Override
    public boolean isSame(HashHolder hashedBytes, String payload) {
        return isSame(hashedBytes, payload.getBytes(CHARSET));
    }
}

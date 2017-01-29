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
    public boolean isSame(HashHolder hashHolder, byte[] payload) {
        byte[] salt = hashHolder.getSalt();
        byte[] previousHash = hashHolder.getHash();
        return isSame(salt, previousHash, payload);
    }

    @Override
    public boolean isSame(HashHolder hashHolder, String payload) {
        return isSame(hashHolder, payload.getBytes(CHARSET));
    }

    @Override
    public boolean isSame(byte[] salt, byte[] hash, String payload) {
        return isSame(salt, hash, payload.getBytes(CHARSET));
    }

    @Override
    public boolean isSame(byte[] salt, byte[] previousHash, byte[] payload) {
        byte[] payloadHash = hashAlgorithm.hash(payload, salt);

        // now we generate 2 hashes from these, to make it harder for attacks to occur if this code becomes
        // optimized by the compiler.
        byte[] h1 = hashAlgorithm.hash(previousHash, salt);
        byte[] h2 = hashAlgorithm.hash(payloadHash, salt);

        // this should never happen, but we have to check
        if (h1.length != h2.length) {
            logger.warn("Something wicked this way comes: hash gave 2 difference sizes... should never happen");
            return false;
        }

        boolean same = true;
        for (int i = 0; i < previousHash.length; i++) {
            same = payloadHash[i] == previousHash[i] && same; // TODO: optimizing compiler may make this faster then we want.
        }
        return same;
    }
}

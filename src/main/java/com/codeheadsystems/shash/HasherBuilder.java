package com.codeheadsystems.shash;

import com.codeheadsystems.shash.impl.HasherImpl;
import com.codeheadsystems.shash.random.RandomProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.function.Supplier;

public class HasherBuilder {

    private static Logger logger = LoggerFactory.getLogger(HasherBuilder.class);

    private RandomProvider randomProvider;
    private int saltSize = 256 / 8; // default byte size 32 bytes
    private HashAlgorithm hashAlgorithm;

    public HasherBuilder saltSize(int saltSize) {
        this.saltSize = saltSize;
        return this;
    }

    public HasherBuilder randomProvider(Supplier<Random> supplier) {
        this.randomProvider = RandomProvider.generate(supplier);
        return this;
    }

    public HasherBuilder randomProvider(RandomProvider randomProvider) {
        this.randomProvider = randomProvider;
        return this;
    }

    public HasherBuilder hashAlgorithm(HashAlgorithm hashAlgorithm) {
        this.hashAlgorithm = hashAlgorithm;
        return this;
    }

    public Hasher build() {
        if (hashAlgorithm == null) {
            hashAlgorithm(SupportedHashAlgorithm.getSCryptAlgo());
        }
        if (randomProvider == null) {
            randomProvider = RandomProvider.generate();
        }
        logger.trace("Building new hasher ({},{},{})", randomProvider, saltSize, hashAlgorithm);
        return new HasherImpl(saltSize, randomProvider, hashAlgorithm);
    }
}

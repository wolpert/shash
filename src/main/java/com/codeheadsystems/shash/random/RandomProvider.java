package com.codeheadsystems.shash.random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.SecureRandom;
import java.util.Random;
import java.util.function.Supplier;

public class RandomProvider {

    private static Logger logger = LoggerFactory.getLogger(RandomProvider.class);

    private final ThreadLocal<Random> randomThreadLocal;

    private RandomProvider(Supplier<Random> supplier) {
        this.randomThreadLocal = ThreadLocal.withInitial(supplier);
    }

    public static RandomProvider generate(Supplier<Random> supplier) {
        logger.trace("generate({})", supplier);
        return new RandomProvider(supplier);
    }

    public static RandomProvider generate() {
        return generate(() -> {
            SecureRandom secureRandom = new SecureRandom();
            secureRandom.setSeed(secureRandom.generateSeed(20));
            return secureRandom;
        });
    }

    public byte[] getRandomBytes(byte[] storeArray) {
        randomThreadLocal.get().nextBytes(storeArray);
        return storeArray;
    }

    public byte[] getRandomBytes(int biteSize) {
        return getRandomBytes(new byte[biteSize]);
    }

}

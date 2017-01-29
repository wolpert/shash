package com.codeheadsystems.shash.impl;

import java.security.SecureRandom;

public class RandomProvider {

    private final ThreadLocal<SecureRandom> secureRandomThreadLocal;

    public RandomProvider() {
        this.secureRandomThreadLocal = ThreadLocal.withInitial(() -> {
            SecureRandom secureRandom = new SecureRandom();
            secureRandom.setSeed(secureRandom.generateSeed(20));
            return secureRandom;
        });
    }

    public byte[] getRandomBytes(byte[] storeArray) {
        secureRandomThreadLocal.get().nextBytes(storeArray);
        return storeArray;
    }

    public byte[] getRandomBytes(int biteSize) {
        return getRandomBytes(new byte[biteSize]);
    }

}

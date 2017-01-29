package com.codeheadsystems.shash;

import com.codeheadsystems.shash.impl.RandomProvider;
import com.codeheadsystems.shash.impl.SCryptHasher;

/**
 * Created by wolpert on 1/28/17.
 */
public class DefaultHarsherBuilder extends AbstractHarsherBuilder {

    private int iterations = (int) Math.pow(2, 20); //
    private int p = 1;
    private int dkLen = 32;
    private int r = 8;

    public DefaultHarsherBuilder dkLen(int dkLen) {
        this.dkLen = dkLen;
        return this;
    }

    public DefaultHarsherBuilder iterations(int iterations) {
        this.iterations = iterations;
        if (iterations < (int) Math.pow(2, 14)) {
            throw new IllegalArgumentException("Min num of iterations is 16384, found: " + iterations);
        }
        return this;
    }

    public DefaultHarsherBuilder p(int p) {
        this.p = p;
        return this;
    }

    public DefaultHarsherBuilder r(int r) {
        this.r = r;
        return this;
    }

    @Override
    public Hasher build() {
        if (randomProvider == null) {
            randomProvider = new RandomProvider();
        }
        return new SCryptHasher(saltSize,
                iterations,
                r,
                p,
                dkLen,
                randomProvider);
    }
}

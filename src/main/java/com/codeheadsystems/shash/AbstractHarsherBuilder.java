package com.codeheadsystems.shash;

import com.codeheadsystems.shash.impl.RandomProvider;

public abstract class AbstractHarsherBuilder {

    protected RandomProvider randomProvider;
    protected int saltSize = 256/8; // default byte size 32 bytes

    public AbstractHarsherBuilder saltSize(int saltSize) {
        this.saltSize = saltSize;
        return this;
    }

    public AbstractHarsherBuilder randomProvider(RandomProvider randomProvider) {
        this.randomProvider = randomProvider;
        return this;
    }

    public abstract Hasher build();
}

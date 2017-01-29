package com.codeheadsystems.shash.impl;

import org.bouncycastle.crypto.generators.SCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by wolpert on 1/28/17.
 */
public class SCryptHasher extends AbstractHasher {

    private static final Logger logger = LoggerFactory.getLogger(SCryptHasher.class);

    private final int iterations;
    private final int p;
    private final int dkLen;
    private int r;

    public SCryptHasher(int saltSize, int iterations, int r, int p, int dkLen, RandomProvider randomProvider) {
        super(saltSize, randomProvider);
        this.iterations = iterations;
        this.r = r;
        this.p = p;
        this.dkLen = dkLen;
        if (iterations < 16384) {
            throw new IllegalArgumentException("Unable to have an iteration count less then 16384 (2^14): found " + iterations);
        }
        logger.debug("Paranoid scrypt: n=" + iterations + " r=" + this.r + " p=" + this.p + " dkLen=" + this.dkLen);
    }

    protected byte[] internalHash(byte[] bytes, byte[] salt) {
        return SCrypt.generate(bytes, salt, iterations, r, p, dkLen);
    }

}

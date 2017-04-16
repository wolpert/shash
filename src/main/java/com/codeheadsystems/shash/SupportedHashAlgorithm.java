package com.codeheadsystems.shash;

import org.bouncycastle.crypto.generators.SCrypt;

public class SupportedHashAlgorithm {

    private static final int SCRYPT_ITERATIONS = (int) Math.pow(2, 20);  // 1048576
    private static final int MIN_SCRYPT_ITERATIONS = (int) Math.pow(2, 14); // 16384
    private static final int P = 1;
    private static final int R = 8;
    private static final int DKLEN = 32; // 256-bit

    /*
     * SCRYPT NOTES: Default p=1, dkLen=32 (bytes) r=8. Iterations min is 2^14, 2^20 is suggested
     */

    /**
     * Returns a SCrypt generator that uses 2^20 iterations, r=8, p=1 and dkLen = 32
     *
     * @return an instance of HashAlgorithm that supports strong SCrypt hashing
     */
    public static HashAlgorithm getMaxSCryptAlgo() {
        return ((salt, bytes) -> SCrypt.generate(bytes, salt, SCRYPT_ITERATIONS, R, P, DKLEN)); // bytes before salt here
    }

    /**
     * Returns a SCrypt generator that uses the max SCryptAlgo method
     *
     * @return an instance of HashAlgorithm that supports strong SCrypt hashing
     */
    public static HashAlgorithm getSCryptAlgo() {
        return getMaxSCryptAlgo();
    }

    /**
     * Returns a SCrypt generator that uses 2^20 iterations, r=8, p=1 and dkLen = 32
     *
     * @param iterations the number of iterations to use. Must be &gt;= 2^14
     * @return an instance of HashAlgorithm that supports strong SCrypt hashing
     */
    public static HashAlgorithm getSCryptAlgo(final int iterations) {
        if (iterations < MIN_SCRYPT_ITERATIONS) {
            throw new IllegalArgumentException("Min number of iterations allowed is " + MIN_SCRYPT_ITERATIONS);
        }
        return ((salt, bytes) -> SCrypt.generate(bytes, salt, iterations, R, P, DKLEN)); // bytes before salt here
    }

    /**
     * Returns a SCrypt generator that uses 2^14 iterations, r=8, p=1 and dkLen = 32
     *
     * @return an instance of HashAlgorithm that supports fast SCrypt hashing, but is not as strong (via iterations)
     */
    public static HashAlgorithm getMinSCryptAlgo() {
        return ((salt, bytes) -> SCrypt.generate(bytes, salt, MIN_SCRYPT_ITERATIONS, R, P, DKLEN));  // bytes before salt here
    }

}

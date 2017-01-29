package com.codeheadsystems.shash;

/**
 * Used to define something that implements a hasher.
 */
@FunctionalInterface
public interface HashAlgorithm {

    byte[] hash(byte[] salt, byte[] bytes);

}

package com.codeheadsystems.shash;

/**
 * Provides access to standard hasher structure
 */
public interface Hasher {

    /**
     * Will hash the bytes given and return a 'sized' salted hash depending on the algorithm picked on creation.
     * Salt is automatically generated.
     *
     * @param bytes from the payload.
     * @return byte[] of a fixed size depending on how this was created, plus the salt.
     */
    byte[] hash(byte[] bytes);

    /**
     * Will hash the bytes given and return a 'sized' salted hash depending on the algorithm picked on creation.
     * Salt is automatically generated.
     *
     * @param hashingWord for the payload, converted to bytes in a consistent way for this hasher.
     * @return byte[] of a fixed size depending on how this was created, plus the salt.
     */
    byte[] hash(String hashingWord);

    /**
     * Provides access to a salt for this specific hasher.
     *
     * @return byte[] with the salt
     */
    byte[] generateSalt();

    /**
     * Compares a previously hashed set of bytes with a new payload. The salt is taken from the hashedBytes.
     *
     * @param hashedBytes the previously hashed word with salt.
     * @param payload The payload to hash with the salt and check against the hashedBytes given
     * @return boolean to say if they are the same or not
     */
    boolean isSame(byte[] hashedBytes, byte[] payload);

    /**
     * Compares a previously hashed set of bytes with a new payload. The salt is taken from the hashedBytes.
     * Note this will convert the payload to a byte[] in a consistent manner.
     *
     * @param hashedBytes the previously hashed word with salt.
     * @param payload The payload to hash with the salt and check against the hashedBytes given
     * @return boolean to say if they are the same or not
     */
    boolean isSame(byte[] hashedBytes, String payload);

}

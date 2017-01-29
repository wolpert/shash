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
     * @return HashHolder of a fixed size depending on how this was created, plus the salt.
     */
    HashHolder hash(byte[] bytes);

    /**
     * Will hash the bytes given and return a 'sized' salted hash depending on the algorithm picked on creation.
     * Salt is automatically generated.
     *
     * @param hashingWord for the payload, converted to bytes in a consistent way for this hasher.
     * @return HashHolder of a fixed size depending on how this was created, plus the salt.
     */
    HashHolder hash(String hashingWord);

    /**
     * Provides access to a salt for this specific hasher.
     *
     * @return byte[] with the salt
     */
    byte[] generateSalt();

    /**
     * Compares a previously hashed set of bytes with a new payload. The salt is taken from the hashedBytes.
     *
     * @param hashHolder the previously hashed word with salt.
     * @param payload    The payload to hash with the salt and check against the hashedBytes given
     * @return boolean to say if they are the same or not
     */
    boolean isSame(HashHolder hashHolder, byte[] payload);

    /**
     * Compares a previously hashed set of bytes with a new payload. The salt is taken from the hashedBytes.
     * Note this will convert the payload to a byte[] in a consistent manner.
     *
     * @param hashHolder the previously hashed word with salt.
     * @param payload    The payload to hash with the salt and check against the hashedBytes given
     * @return boolean to say if they are the same or not
     */
    boolean isSame(HashHolder hashHolder, String payload);

    /**
     * Compares a previously hashed set of bytes with a new payload.
     * Note this will convert the payload to a byte[] in a consistent manner.
     *
     * @param salt         that was used originally to generate the hash
     * @param previousHash generated from the original text/bytes
     * @param payload      The incoming payload you want to check
     * @return boolean to say if they are the same or not
     */
    boolean isSame(byte[] salt, byte[] previousHash, String payload);

    /**
     * Compares a previously hashed set of bytes with a new payload.
     * Note this will convert the payload to a byte[] in a consistent manner.
     *
     * @param salt         that was used originally to generate the hash
     * @param previousHash generated from the original text/bytes
     * @param payload      The incoming payload you want to check
     * @return boolean to say if they are the same or not
     */
    boolean isSame(byte[] salt, byte[] previousHash, byte[] payload);

}

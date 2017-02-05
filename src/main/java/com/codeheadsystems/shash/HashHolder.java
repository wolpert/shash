package com.codeheadsystems.shash;

import com.codeheadsystems.shash.text.StringManipulator;

import static com.codeheadsystems.shash.text.StringManipulator.toHex;
import static java.util.Objects.requireNonNull;

public class HashHolder {

    private final byte[] salt;
    private final byte[] hash;

    public HashHolder(byte[] salt, byte[] hash) {
        this.salt = requireNonNull(salt);
        this.hash = requireNonNull(hash);
    }

    public HashHolder(String fromString) throws BadHashHolderException {
        String[] hashStringArray = fromString.split(":");
        if (hashStringArray.length != 2) {
            throw new BadHashHolderException("Invalid token count: " + hashStringArray.length + " instead of 2");
        }
        this.salt = StringManipulator.fromHex(hashStringArray[0]);
        this.hash = StringManipulator.fromHex(hashStringArray[1]);
    }

    public String getAsString() {
        return String.format("%s:%s", toHex(salt), toHex(hash));
    }

    public byte[] getSalt() {
        return salt;
    }

    public byte[] getHash() {
        return hash;
    }
}

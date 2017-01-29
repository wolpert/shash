package com.codeheadsystems.shash;

import org.bouncycastle.util.encoders.Hex;

import java.nio.charset.Charset;

public class StringManipulator {

    public static Charset CHARSET = Charset.forName("UTF-16LE");

    public static byte[] toBytes(String string) {
        return string.getBytes(CHARSET);
    }

    public static String toString(byte[] bytes) {
        return new String(bytes, CHARSET);
    }

    public static String toHex(byte[] hex) {
        return Hex.toHexString(hex);
    }

    public static byte[] fromHex(String string) {
        return Hex.decode(string);
    }

}

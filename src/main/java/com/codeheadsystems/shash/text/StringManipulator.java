package com.codeheadsystems.shash.text;

import org.bouncycastle.util.encoders.Hex;

import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Base64.Encoder;

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

    public static String toBase64(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static byte[] fromBase64(String string) {
        return Base64.getDecoder().decode(string);
    }

}

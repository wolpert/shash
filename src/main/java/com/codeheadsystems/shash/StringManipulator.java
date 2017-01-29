package com.codeheadsystems.shash;

import java.nio.charset.Charset;

public class StringManipulator {

    public static Charset CHARSET = Charset.forName("UTF-16LE");

    public static byte[] toBytes(String string) {
        return string.getBytes(CHARSET);
    }

    public static String toString(byte[] bytes) {
        return new String(bytes, CHARSET);
    }

    public static String toHex(byte[] hex){
        return null;
    }

    public static byte[] fromHex(String string){
        return null;
    }

}

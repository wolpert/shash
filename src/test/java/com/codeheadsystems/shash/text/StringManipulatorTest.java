package com.codeheadsystems.shash.text;

import com.codeheadsystems.shash.text.StringManipulator;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotSame;

/**
 * Created by wolpert on 1/29/17.
 */
public class StringManipulatorTest {

    private static final String[] TEST_STRINGS = new String[]{
            "Just a standard string",
            "Some mixed char sets: עברית, 中國哲學書電子化計劃"};

    @Test
    public void testBytes() {
        for (String str : TEST_STRINGS) { // just test the toBytes by itself
            byte[] array = StringManipulator.toBytes(str);
            String decode = StringManipulator.toString(array);
            assertEquals(str, decode);
        }
    }

    @Test
    public void testHexWithStrings() {
        for (String str : TEST_STRINGS) {// Use strings to get bytes
            byte[] array1 = StringManipulator.toBytes(str);
            String hexString = StringManipulator.toHex(array1);
            assertNotSame(str, hexString);
            byte[] array2 = StringManipulator.fromHex(hexString);
            assertEquals(array1.length, array2.length);
            for(int i=0;i<array2.length;i++){
                assertEquals(array1[i], array2[i]);
            }
        }
    }

}

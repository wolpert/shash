package com.codeheadsystems.shash.random;

import com.codeheadsystems.shash.random.RandomProvider;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class RandomProviderTest {

    private RandomProvider randomProvider;
    @Before
    public void setup() {
        randomProvider = RandomProvider.generate();
    }

    @Test
    public void testGetRandomBytes() {
        byte[] array = randomProvider.getRandomBytes(10);
        assertEquals(10, array.length);
        int sameCount=0;
        for(int i=0;i<9;i++){
            if(array[i]==array[i+1]) {
                sameCount++; // its possible... shouldn't really happen 5 times say... :-/
            }
        }
        assertTrue( sameCount < 5);
    }

    @Test
    public void testArrayFilled() {
        byte[] array = new byte[10];
        byte[] array2 = randomProvider.getRandomBytes(array);
        assertTrue( array == array2);
    }
}

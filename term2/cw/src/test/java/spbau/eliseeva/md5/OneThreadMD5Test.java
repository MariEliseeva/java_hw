package spbau.eliseeva.md5;

import org.junit.Test;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ForkJoinPool;

import static org.junit.Assert.*;

public class OneThreadMD5Test {
    @Test
    public void Test1() throws NoSuchAlgorithmException {
        Byte[] bytes = new ForkJoinPool(4).invoke(new ForkJoinMD5Task(new File("src/main/resources")));
        Byte[] result = {48, 35, -36, 5, -124, 85, -127, -80, -47, 24, 0, -32, -60, -113, -84, 48};
        assertArrayEquals(result, bytes);
    }

    @Test
    public void Test2() throws NoSuchAlgorithmException {
        Byte[] bytes = new ForkJoinPool(4).invoke(new ForkJoinMD5Task(new File("src/main/resources/dir1")));
        Byte[] result = {-25, -57, 39, 18, -119, -63, 29, -71, 65, -56, -108, 45, -75, 33, 82, 31};
        assertArrayEquals(result, bytes);
    }
}
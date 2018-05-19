package spbau.eliseeva.XUnit;

import spbau.eliseeva.XUnit.annotations.*;

import java.io.IOException;

public class ExpectedTest {
    @Test(expected = RuntimeException.class)
    public void test1() {
        throw new RuntimeException();
    }
    @Test(expected = IOException.class)
    public void test2() {
        throw new RuntimeException();
    }
}

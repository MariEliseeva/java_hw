package spbau.eliseeva.XUnit;

import spbau.eliseeva.XUnit.annotations.*;

import java.io.IOException;

public class BadExceptionTest {
    @Test(expected = IOException.class)
    public void test2() {
        throw new RuntimeException();
    }
}

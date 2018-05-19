package spbau.eliseeva.XUnit;

import spbau.eliseeva.XUnit.annotations.Test;

import java.io.IOException;

public class NoExceptionTest {
    @Test(expected = IOException.class)
    public void test2() {
    }
}

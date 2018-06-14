package spbau.eliseeva.XUnit;

import spbau.eliseeva.XUnit.annotations.Test;

public class IgnoreTest {
    @Test(ignore = "reason")
    public void test1() {
    }
}

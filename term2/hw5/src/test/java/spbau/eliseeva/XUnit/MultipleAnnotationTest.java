package spbau.eliseeva.XUnit;

import spbau.eliseeva.XUnit.annotations.After;
import spbau.eliseeva.XUnit.annotations.Before;

public class MultipleAnnotationTest {
    @After@Before
    public void afterBefore(){
    }
}

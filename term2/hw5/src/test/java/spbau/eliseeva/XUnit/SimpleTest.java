package spbau.eliseeva.XUnit;

import spbau.eliseeva.XUnit.annotations.*;

public class SimpleTest {
    @Before
    public void before() {
        System.out.println("Before");
    }
    @Test
    public void test1() {
        System.out.println("test1");
    }
    @Test
    public void test2() {
        System.out.println("test2");
    }
    @After
    public void after() {
        System.out.println("After");
    }
    @AfterClass
    public void afterClass() {
        System.out.println("AfterClass");
    }
    @BeforeClass
    public void beforeClass() {
        System.out.println("BeforeClass");
    }
}

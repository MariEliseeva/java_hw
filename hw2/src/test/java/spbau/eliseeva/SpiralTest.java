package spbau.eliseeva.test;

import org.junit.Test;
import spbau.eliseeva.spiral.Spiral;

public class SpiralTest {
    @Test
    public void sortColsTest() throws Exception {
        Spiral spiral = new Spiral();
        spiral.printMatrix();
        System.out.println();
        spiral.sortCols();
        spiral.printMatrix();
        System.out.println();
    }

    @Test
    public void printTest() throws Exception {
        Spiral spiral = new Spiral();
        spiral.printMatrix();
        System.out.println();
        spiral.printSpiral();
        System.out.println();
    }

}
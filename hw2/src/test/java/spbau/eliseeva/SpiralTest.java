package spbau.eliseeva;

import org.junit.Test;
import spbau.eliseeva.spiral.Spiral;
import static org.junit.Assert.*;

/**
 * This class tests Spiral class's methods.
 * Tests two public methods - sortCols() and getSpiral().
 */
public class SpiralTest {
    @Test
    public void sortColsTest() throws Exception {
        int [][] matrix = {{10, 5, 7}, {2, 7, 6}, {11, 2, 7}};
        Spiral spiral = new Spiral(3, matrix);
        matrix = spiral.sortCols();
        int [][] expectedResult = {{5, 7, 10}, {7, 6, 2}, {2, 7, 11}};
        assertArrayEquals(expectedResult, matrix);
    }

    @Test
    public void getSpiralTest() throws Exception {
        int [][] matrix = new int[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                matrix[i][j] = i * 5 + j + 1;
            }
        }
        Spiral spiral = new Spiral(5, matrix);
        int [] spiralString = spiral.getSpiral();
        int [] expectedResult = {13, 12, 17, 18, 19, 14, 9, 8, 7,
                6, 11, 16, 21, 22, 23, 24, 25, 20, 15, 10, 5, 4, 3, 2, 1};
        assertArrayEquals(expectedResult, spiralString);
    }
}
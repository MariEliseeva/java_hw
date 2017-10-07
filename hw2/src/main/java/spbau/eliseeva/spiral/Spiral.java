package spbau.eliseeva.spiral;

import java.util.Comparator;
import java.util.Arrays;

/**
 * This class generates matrix with random odd size
 * and fills it with random numbers. The matrix can be returned,
 * sorted by cols and returned in spiral-way (spiral, which begins in the center).
 */
public class Spiral {
    /** Array with numbers*/
    private int [][] data;
    /** Size - width and height (they are equal)*/
    private final int size;
    /** Array with numbers in spiral-way*/
    private int[] spiral;

    /**
     * Constructor.
     * Makes data and size same as given values.
     * @param spiralSize size
     * @param spiralData matrix's numbers in an array
     */
    public Spiral(int spiralSize, int[][] spiralData) {
        size = spiralSize;
        data = spiralData;
        spiral = new int[size * size];
    }

    /**
     * Changes rows and cols of matrix.
     * Is used for simpler sorting of matrix.
     */
    private void transpose() {
        int[][] newData = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                newData[i][j] = data[j][i];
            }
        }
        data = newData;
    }

    /**
     * Sorts matrix cols.
     * Transposes matrix, sorts its rows and transposes back.
     * @return matrix's numbers in an array
     */
    public int[][] sortCols() {
        transpose();
        Arrays.sort(data, Comparator.comparingInt(row -> row[0]));
        transpose();
        return data;
    }

    /**
     * Adds 4 borders in spiral array and goes to smaller matrix.
     * @param length length of border which should be printed
     * @param index index in spiral array
     */
    private void makeSpiral(int length, int index) {
        int dataIndex = (size - length - 1) / 2;
        if (length == 0) {
            spiral[index] = data[dataIndex][dataIndex];
            return;
        }
        for (int i = dataIndex; i < dataIndex + length; i++) {
            spiral[index] = data[dataIndex][i];
            index--;
        }
        for (int i = dataIndex; i < dataIndex + length; i++) {
            spiral[index] = data[i][dataIndex + length];
            index--;
        }
        for (int i = dataIndex + length; i > dataIndex; i--) {
            spiral[index] = data[dataIndex + length][i];
            index--;
        }
        for (int i = dataIndex + length; i > dataIndex; i--) {
            spiral[index] = data[i][dataIndex];
            index--;
        }
        makeSpiral(length - 2, index);
    }

    /**
     * Returns spiral array.
     * Calls recursive method print, which fills spiral array.
     * @return spiral in an array
     */
    public int[] getSpiral() {
        makeSpiral(size - 1, size * size - 1);
        return spiral;
    }
}

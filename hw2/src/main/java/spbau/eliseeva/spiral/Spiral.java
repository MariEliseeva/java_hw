package spbau.eliseeva.spiral;

import java.util.Random;
import java.util.Arrays;

/**
 * This class generates matrix with random odd size
 * and fills it with random numbers. The matrix can be printed,
 * sorted by cols and printed in spiral-way (spiral, which begins in the center).
 */
public class Spiral {
    /** Array with numbers*/
    private int [][] data;
    /** Size - width and height (they are equal)*/
    private int size;
    /** Array with numbers in spiral-way*/
    private int[] spiral;

    /**
     * Constructor.
     * Chooses random size and fills data with random numbers.
     */
    public Spiral() {
        Random random = new Random();
        size = random.nextInt() % 10;
        if (size < 0) size += 10;
        if (size % 2 == 0) size += 1;
        data = new int[size][size];
        spiral = new int[size * size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                data[i][j] = random.nextInt() % 10;
            }
        }
    }

    /**
     * Changes rows and cols of matrix.
     * Is used for simpler sorting of matrix
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

    /** Prints matrix*/
    public void printMatrix() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(data[i][j]);
                System.out.print(' ');
            }
            System.out.println();
        }
    }

    /**
     * Sorts matrix cols.
     * Transposes matrix, sorts its rows and transposes back.
     */
    public void sortCols() {
        transpose();
        for (int i = 0; i < size; i++) {
            Arrays.sort(data[i]);
        }
        transpose();
    }

    /**
     * Adds 4 borders in spiral array and goes to smaller matrix.
     * @param length length of border which should be printed
     * @param index index in spiral array
     */
    private void print(int length, int index) {
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
        print(length - 2, index);
    }

    /**
     * Prints spiral array.
     * Calls recursive method print, which fills spiral array.
     */
    public void printSpiral() {
        print(size - 1, size * size - 1);
        for (int i = 0; i < size * size; i++) {
            System.out.print(spiral[i]);
            System.out.print(" ");
        }
        System.out.println();
    }
}

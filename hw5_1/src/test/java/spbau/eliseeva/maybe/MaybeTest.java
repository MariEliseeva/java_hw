package spbau.eliseeva.maybe;

import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.StrictMath.cos;
import static org.junit.Assert.*;
import static spbau.eliseeva.maybe.Maybe.*;

/**
 * Tests Maybe class's methods and tests
 * working the class in general by reading and writing
 * numbers from file to file */
public class MaybeTest {
    /** Tests the ability to create just with right value.*/
    @Test
    public void justTest() throws Exception {
        assertEquals(17, (int)just(17).get());
    }

    /** Tests the ability to create nothing. */
    @Test(expected = NullObjectException.class)
    public void nothingTest() throws Exception {
       nothing().get();
    }

    /** Tests if get method works right.*/
    @Test
    public void getTest() throws Exception {
        assertEquals("theString", just("theString").get());
        assertEquals(32.123, just(32.123).get(), 0.001);
    }

    /** Tests isPresent method*/
    @Test
    public void isPresentTest() throws Exception {
        assertEquals(true, just("sad").isPresent());
        assertEquals(false, nothing().isPresent());
    }

    /** Tests map method - applies multiplication by 2 and cos.*/
    @Test
    public void mapTest() throws Exception {
        assertEquals(10, (int)just(5).map(x -> 2 * x).get());
        assertEquals(1.0, just(0).map(x -> cos(x)).get(), 0.1);
    }

    /**
     * Read numbers from given file and put them
     * in the arrayList of Maybe objects. If there
     * is not a number in the line - puts nothing.
     * @param fileName name of file to read numbers from
     * @return arrayList of taken objects
     */
    private ArrayList<Maybe<Integer>> readIntegers(String fileName) {
        File file = new File (fileName);
        ArrayList<Maybe<Integer>> arrayList = null;
        try {
            Scanner scanner = new Scanner(new File(fileName));
            arrayList = new ArrayList<>();
            while (scanner.hasNextLine()) {
                try {
                    arrayList.add(just(Integer.parseInt(scanner.nextLine())));
                } catch (NumberFormatException e) {
                    arrayList.add(nothing());
                }
            }
        } catch (FileNotFoundException e) {
        }
        return arrayList;
    }

    /**
     * Writes numbers from arrayList to the file with the given name.
     * If nothing - writes "null" in the file.
     * @param fileName file to write to
     * @param arrayList objects to write
     */
    private void writeIntegers(String fileName, ArrayList<Maybe<Integer>> arrayList) {
        try {
            FileWriter fileWriter = new FileWriter(new File(fileName));
            for (Maybe<Integer> element : arrayList) {
                if (element.isPresent()) {
                    fileWriter.write(element.get() + "\n");
                } else {
                    fileWriter.write("null\n");
                }
            }
            fileWriter.close();
        } catch (IOException | NullObjectException e) {
        }
    }

    /**
     * Reads numbers from file "input.txt", applies square
     * function to all of them and writes to "output.txt".
     * If there was not a number in the line - writes "null" on its place.
     */
    @Test
    public void testReadWriteIntegers() throws Exception {
        ArrayList<Maybe<Integer>> arrayListIn = readIntegers("input.txt");
        ArrayList<Maybe<Integer>> arrayListOut = new ArrayList<>();
        for (Maybe<Integer> element : arrayListIn) {
            arrayListOut.add(element.map(x -> x * x));
        }
        writeIntegers("output.txt", arrayListOut);
    }
}
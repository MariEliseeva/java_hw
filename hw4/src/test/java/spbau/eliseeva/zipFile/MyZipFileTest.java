package spbau.eliseeva.zipFile;

import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.Assert.*;
import static spbau.eliseeva.zipFile.MyZipFile.extractFilesByPattern;

/**
 * The class for testing ZipFile class's method extractFilesByPattern.
 * Using one recursive private method to compare directories and to delete created files.
 */
public class MyZipFileTest {
    /**
     * Walks in two file lists ands compares them.
     * Compares names, if directories - recursively goes inside,
     * if files - compares byte by byte.
     * Delete files after comparision.
     * @param expected file list which is an expected result
     * @param real file list which came from tested method
     * @throws IOException
     */
    private void compare(File[] expected, File[] real) throws IOException {
        for (int i = 0; i < expected.length; i++) {
            real[i].deleteOnExit();
            assertEquals(expected[i].getName(), real[i].getName());
            if (expected[i].isDirectory()) {
                compare(expected[i].listFiles(), real[i].listFiles());
            } else {
                FileReader readerA = new FileReader(expected[i]);
                FileReader readerB = new FileReader(real[i]);
                int byteExpected;
                int byteReal;
                while ((byteExpected = readerA.read()) > 0) {
                    byteReal = readerB.read();
                    assertEquals(byteExpected, byteReal);
                }
            }
        }
    }

    /**
     * Tests a method using private method compare,
     * directory to try to archive and
     * a directory with an expected results.
     * @throws Exception
     */
    @Test
    public void extractFilesByPatternTest() throws Exception {
        extractFilesByPattern("./forTesting", ".*17.*");
        File expectedResult = new File("./forTesting/123Results");
        File realResult = new File("./forTesting/12345/123");
        realResult.deleteOnExit();
        assertEquals(expectedResult.listFiles().length,
                realResult.listFiles().length);
        compare(expectedResult.listFiles(), realResult.listFiles());

        expectedResult = new File("./forTesting/12Results");
        realResult = new File("./forTesting/12345/qwerty/12");
        realResult.deleteOnExit();
        assertEquals(expectedResult.listFiles().length,
                realResult.listFiles().length);
        compare(expectedResult.listFiles(), realResult.listFiles());
    }
}
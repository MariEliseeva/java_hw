package spbau.eliseeva.XO.Util;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/** Reads and writes the statistics.*/
public class StatisticsReaderWriter {
    /**
     * Reads the statistics from resources
     * @return ArrayList with the statistics
     */
    public static ArrayList<String> read() {
        ArrayList<String> results = new ArrayList<>();
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File("src/main/resources/statistics"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert scanner != null;
        while (scanner.hasNext()) {
            results.add(scanner.nextLine());
        }
        return results;
    }

    /**
     * Writes the statistics to the needed file.
     * @param results ArrayList with the statistics
     */
    public static void write(ArrayList<String> results) {
        Writer writer = null;
        try {
            writer = new OutputStreamWriter(new FileOutputStream("src/main/resources/statistics"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            for (String string : results) {
                assert writer != null;
                writer.write(string + "\n");
            }
            assert writer != null;
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

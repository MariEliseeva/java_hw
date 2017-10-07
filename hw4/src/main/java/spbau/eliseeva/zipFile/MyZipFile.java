package spbau.eliseeva.zipFile;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * This class can find and unarchive a file, which name matches given pattern.
 * The class has one public static method, which completes
 * the task using several private methods.
 */
public class MyZipFile {
    /**
     * Unarchive current file from Zip-archive.
     * Given a path to archive and archive's entry and stream.
     * The method creates a file, which zipEntry is connected to,
     * a file is put in needed directory, saving hierarchy from archive.
     * @param zipInputStream stream of archive to read from
     * @param zipEntry entry, a file should be done from
     * @param pathToZip path to archive
     * @throws IOException
     */
    private static void extractFile(ZipInputStream zipInputStream,
                                ZipEntry zipEntry, String pathToZip) throws IOException {
        File file = new File(pathToZip + '/' + zipEntry.getName());
        if (zipEntry.isDirectory()) {
            file.mkdirs();
            return;
        }
        new File(file.getParent()).mkdirs();
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        int length;
        byte[] buffer = new byte[1024];
        while ((length = zipInputStream.read(buffer)) != -1) {
            fileOutputStream.write(buffer, 0, length);
        }
        fileOutputStream.close();
    }

    /**
     * Find files in a given archive,
     * which names are matched with the given pattern.
     * @param file zip-archive, where it finds "good" files
     * @param regularExpression regular expression, which it compared to a file's name
     * @throws IOException
     */
    private static void findInZip(File file, String regularExpression) throws IOException {
        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(file.toString()));
        ZipEntry zipEntry;
        while ((zipEntry = zipInputStream.getNextEntry()) != null) {
            Pattern pattern = Pattern.compile(regularExpression);
            Matcher matcher = pattern.matcher(new File(zipEntry.getName()).getName());
            if (matcher.matches()) {
                extractFile(zipInputStream, zipEntry,
                        file.getParentFile().getAbsolutePath());
            }
        }
        zipInputStream.close();
    }

    /**
     * Walks in a given files array.
     * Recursively goes into subdirectories
     * and looks at file if it is a zip-archive.
     * @param files files to loot at
     * @param regularExpression regular expression for seeking a file in a zip-archives
     * @throws IOException
     */
    private static void walk(File[] files, String regularExpression) throws IOException {
        for (File file : files) {
            if (file.isDirectory()) {
                walk(file.listFiles(), regularExpression);
            } else {
                if (file.toString().endsWith(".zip")) {
                    findInZip(file, regularExpression);
                }
            }
        }
    }

    /**
     * Walks in a given directory using inner methods to unarchive needed files
     * @param path path to start a search from
     * @param regularExpression regular expression for seeking a file in a zip-archives
     * @throws IOException
     */
    public static void extractFilesByPattern(String path,
                               String regularExpression) throws IOException {
       walk(new File(path).listFiles(), regularExpression);
    }
}

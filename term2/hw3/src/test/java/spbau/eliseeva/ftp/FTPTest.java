package spbau.eliseeva.ftp;

import org.junit.Test;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/** Tests for FTPClient and FTPServer.*/
public class FTPTest {
    /** Checks if the server works as expected. */
    @Test
    public void testServer() {
        InputStream serverInputStream = new ByteArrayInputStream(("1234\n\n").getBytes());
        System.setIn(serverInputStream);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteArrayOutputStream));
        FTPServer.main(new String[0]);
        assertArrayEquals(("Write port number.\nPress enter to end.\n\n").getBytes(), byteArrayOutputStream.toByteArray());
    }

    /**
     * Checks some list requests.
     * @throws InterruptedException thrown if problems with threads.
     * @throws IOException if problems with files
     */
    @Test
    public void testList() throws InterruptedException, IOException {
        runServer();
        Map<String, Boolean> result = new FTPClient(1234, "localhost").listAnswer("src/test/resources/dir1");
        Map<String, Boolean> expected = new HashMap<>();
        expected.put("dir2", true);
        expected.put("dir4", true);
        expected.put("dir5", true);
        assertEquals(expected, result);
        expected.clear();
        result = new FTPClient(1234, "localhost").listAnswer("src/test/resources/dir1/dir2");
        expected.put("dir3", true);
        expected.put("file1", false);
        assertEquals(expected, result);
        expected.clear();
        delete(new File("results"));
    }

    /**
     * Checks some get requests.
     * @throws InterruptedException thrown if problems with threads.
     * @throws IOException if problems with files
     */
    @Test
    public void testGet() throws InterruptedException, IOException {
        runServer();
        new FTPClient(1234, "localhost").getAnswer("src/test/resources/dir1/dir4/file3", "get0");
        byte[] bytes = new byte[5];
        (new FileInputStream(new File("results/get0"))).read(bytes);
        assertArrayEquals(("file3").getBytes(),bytes);
        delete(new File("results"));
    }

    /**
     * Checks if two clients can work with same server.
     * @throws InterruptedException thrown if problems with threads.
     */
    @Test
    public void testTwoClients() throws InterruptedException {
        runServer();
        Thread thread1 = new Thread(() -> {
            Map<String, Boolean> result = null;
            try {
                result = new FTPClient(1234, "localhost").listAnswer("src/test/resources/dir1/dir2/dir3");
            } catch (IOException ignored) {
            }
            Map<String, Boolean> expected = new HashMap<>();
            expected.put("file2", false);
            expected.put("file5", false);
            assertEquals(expected, result);
            delete(new File("results"));
        });
        Thread thread2 = new Thread(() -> {
            try {
                new FTPClient(1234, "localhost").getAnswer("src/test/resources/dir1/dir2/dir3/file5", "get0");
            } catch (IOException ignored) {
            }
            byte[] bytes = new byte[5];
            try {
                (new FileInputStream(new File("results/get0"))).read(bytes);
            } catch (IOException ignored) {
            }
            assertArrayEquals(("file5").getBytes(),bytes);

            delete(new File("results"));
        });
        thread1.start();
        thread2.start();
    }

    /**
     * Starts working of server.
     * @throws InterruptedException thrown if problems with threads.
     */
    private void runServer() throws InterruptedException {
        InputStream serverInputStream = new ByteArrayInputStream(("1234\n\n").getBytes());
        Thread thread = new Thread(() -> {
            System.setIn(serverInputStream);
            System.setOut(new PrintStream(new ByteArrayOutputStream()));
            FTPServer.main(new String[0]);
        });
        thread.setDaemon(true);
        thread.start();
        Thread.sleep(1000);
    }

    /**
     * Delete file  or directory.
     * @param file file to delete
     */
    private void delete(File file) {
        if (!file.exists()) {
            return;
        }
        if (file.isDirectory()) {
            for(File f : file.listFiles()) {
                delete(f);
            }
            file.delete();
        } else {
            file.delete();
        }
    }

}
package spbau.eliseeva.ftp;

import org.junit.Test;

import java.io.*;
import static org.junit.Assert.*;

/** Tests for FTPClient and FTPServer.*/
public class FTPTest {
    private static final String endOfLine = System.lineSeparator();
    /** Checks if the server works as expected. */
    @Test
    public void testServer() {
        InputStream serverInputStream = new ByteArrayInputStream(("1234" + endOfLine + endOfLine).getBytes());
        System.setIn(serverInputStream);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteArrayOutputStream));
        FTPServer.main(new String[0]);
        assertArrayEquals(("Write port number." + endOfLine + "Press enter to end." + endOfLine + endOfLine).getBytes(), byteArrayOutputStream.toByteArray());
    }

    /**
     * Checks if the client can connect to the server
     * @throws InterruptedException thrown if problems with threads.
     */
    @Test
    public void testConnection() throws InterruptedException {
        runServer();
        InputStream clientInputStream = new ByteArrayInputStream(("localhost" + endOfLine + "1234" + endOfLine +"exit" + endOfLine).getBytes());
        assertArrayEquals(("Write host name." + endOfLine + "Write port number." + endOfLine + "connected" + endOfLine).getBytes(),
                runClient(clientInputStream).toByteArray());
    }

    /**
     * Checks some list and get requests.
     * @throws InterruptedException thrown if problems with threads.
     * @throws IOException if problems with files
     */
    @Test
    public void testListAndGet() throws InterruptedException, IOException {
        runServer();
        InputStream clientInputStream = new ByteArrayInputStream(("localhost" + endOfLine + "1234" + endOfLine
                + "1 src/test/resources/dir1" + endOfLine + "1 src/test/resources/dir1/dir2" + endOfLine +
                "2 src/test/resources/dir1/dir4/file3" + endOfLine + "1 src/test/resources/dir1/dir2/dir3" + endOfLine + "exit" + endOfLine).getBytes());
        assertArrayEquals(("Write host name." + endOfLine + "Write port number." + endOfLine + "connected" + endOfLine +
                "3 (dir2 true)(dir4 true)(dir5 true)" + endOfLine + "2 (dir3 true)(file1 false)" + endOfLine +
                "5" + endOfLine + "2 (file2 false)(file5 false)" + endOfLine).getBytes(), runClient(clientInputStream).toByteArray());
        byte[] bytes = new byte[5];
        (new FileInputStream(new File("results/get0"))).read(bytes);
        System.out.write(bytes);
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
            InputStream clientInputStream1 = new ByteArrayInputStream(("localhost" + endOfLine + "1234" + endOfLine +
                    "1 src/test/resources/dir1/dir5" + endOfLine + "exit" + endOfLine).getBytes());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
            assertArrayEquals(("Write host name." + endOfLine + "Write port number." + endOfLine + "connected" + endOfLine + "1 (file4 false)").getBytes(),
                    runClient(clientInputStream1).toByteArray());
        });
        Thread thread2 = new Thread(() -> {
            InputStream clientInputStream2 = new ByteArrayInputStream(("localhost" + endOfLine + "1234" + endOfLine +
                    "2 src/test/resources/dir1/dir2/file1" + endOfLine + "exit" + endOfLine).getBytes());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
            assertArrayEquals(("Write host name." + endOfLine + "Write port number." + endOfLine + "connected" + endOfLine + "5" + endOfLine).getBytes(),
                    runClient(clientInputStream2).toByteArray());

            byte[] bytes = new byte[5];
            try {
                (new FileInputStream(new File("results/get0"))).read(bytes);
            } catch (IOException ignored) {
            }
            assertArrayEquals(("file1").getBytes(), bytes);
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
        InputStream serverInputStream = new ByteArrayInputStream(("1234" + endOfLine + endOfLine).getBytes());
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
     * Runs the client with given input stream and returns printed answer.
     * @param clientInputStream stream which replaces "System.in" for the client
     * @return stream which replaces "System.out" for the client
     */
    private ByteArrayOutputStream runClient(InputStream clientInputStream) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        System.setIn(clientInputStream);
        System.setOut(new PrintStream(byteArrayOutputStream));
        FTPClient.main(new String[0]);
        return byteArrayOutputStream;
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

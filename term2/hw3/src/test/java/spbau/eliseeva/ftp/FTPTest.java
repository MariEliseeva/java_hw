package spbau.eliseeva.ftp;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.*;

import static org.junit.Assert.*;

/** Tests for FTPClient and FTPServer.*/
public class FTPTest {
    private static final String END_OF_LINE = System.lineSeparator();
    @Rule
    public TemporaryFolder FOLDER = new TemporaryFolder();
    /** Checks if the server works as expected. */
    @Test
    public void testServer() {
        InputStream serverInputStream = new ByteArrayInputStream(("1234" + END_OF_LINE + END_OF_LINE).getBytes());
        System.setIn(serverInputStream);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteArrayOutputStream));
        FTPServer.main(new String[0]);
        assertArrayEquals(("Write port number." + END_OF_LINE + "Press enter to end." + END_OF_LINE + END_OF_LINE).getBytes(), byteArrayOutputStream.toByteArray());
    }

    /**
     * Checks if the client can connect to the server
     * @throws InterruptedException thrown if problems with threads.
     */
    @Test
    public void testConnection() throws InterruptedException {
        runServer();
        InputStream clientInputStream = new ByteArrayInputStream(("localhost" + END_OF_LINE + "1234" + END_OF_LINE +"exit" + END_OF_LINE).getBytes());
        assertArrayEquals(("Write host name." + END_OF_LINE + "Write port number." + END_OF_LINE + "connected" + END_OF_LINE).getBytes(),
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
        FOLDER.newFolder("results");
        InputStream clientInputStream = new ByteArrayInputStream(("localhost" + END_OF_LINE + "1234" + END_OF_LINE
                + "list src/test/resources/dir1" + END_OF_LINE + "list src/test/resources/dir1/dir2" + END_OF_LINE +
                "get src/test/resources/dir1/dir4/file3" + END_OF_LINE + "list src/test/resources/dir1/dir2/dir3" + END_OF_LINE + "exit" + END_OF_LINE).getBytes());
        assertArrayEquals(("Write host name." + END_OF_LINE + "Write port number." + END_OF_LINE + "connected" + END_OF_LINE +
                "3 (dir2 true)(dir4 true)(dir5 true)" + END_OF_LINE + "2 (dir3 true)(file1 false)" + END_OF_LINE +
                "5" + END_OF_LINE + "2 (file2 false)(file5 false)" + END_OF_LINE).getBytes(), runClient(clientInputStream).toByteArray());
        byte[] bytes = new byte[5];
        (new FileInputStream(new File("results/src/test/resources/dir1/dir4/file3"))).read(bytes);
        System.out.write(bytes);
        assertArrayEquals(("file3").getBytes(),bytes);
    }

    /**
     * Checks if two clients can work with same server.
     * @throws InterruptedException thrown if problems with threads.
     * @throws IOException if problems with files
     */
    @Test
    public void testTwoClients() throws InterruptedException, IOException {
        runServer();
        FOLDER.newFolder("results");
        Thread thread1 = new Thread(() -> {
            InputStream clientInputStream1 = new ByteArrayInputStream(("localhost" + END_OF_LINE + "1234" + END_OF_LINE +
                    "list src/test/resources/dir1/dir5" + END_OF_LINE + "exit" + END_OF_LINE).getBytes());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
            assertArrayEquals(("Write host name." + END_OF_LINE + "Write port number." + END_OF_LINE + "connected" + END_OF_LINE + "list (file4 false)").getBytes(),
                    runClient(clientInputStream1).toByteArray());
        });
        Thread thread2 = new Thread(() -> {
            InputStream clientInputStream2 = new ByteArrayInputStream(("localhost" + END_OF_LINE + "1234" + END_OF_LINE +
                    "get src/test/resources/dir1/dir2/file1" + END_OF_LINE + "exit" + END_OF_LINE).getBytes());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
            assertArrayEquals(("Write host name." + END_OF_LINE + "Write port number." + END_OF_LINE + "connected" + END_OF_LINE + "5" + END_OF_LINE).getBytes(),
                    runClient(clientInputStream2).toByteArray());

            byte[] bytes = new byte[5];
            try {
                (new FileInputStream(new File("results/src/test/resources/dir1/dir2/file1"))).read(bytes);
            } catch (IOException ignored) {
            }
            assertArrayEquals(("file1").getBytes(), bytes);
        });
        thread1.start();
        thread2.start();
    }

    /**
     * Starts working of server.
     * @throws InterruptedException thrown if problems with threads.
     */
    private void runServer() throws InterruptedException {
        InputStream serverInputStream = new ByteArrayInputStream(("1234" + END_OF_LINE + END_OF_LINE).getBytes());
        Thread thread = new Thread(() -> {
            System.setIn(serverInputStream);
            System.setOut(new PrintStream(new ByteArrayOutputStream()));
            FTPServer.main(new String[0]);
        });
        thread.setDaemon(true);
        thread.start();
        Thread.sleep(2000);
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
}

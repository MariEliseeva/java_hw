package spbau.eliseeva.ftp;

import org.junit.Test;

import java.io.*;
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
     * Checks if the client can connect to the server
     * @throws InterruptedException thrown if problems with threads.
     */
    @Test
    public void testConnection() throws InterruptedException {
        runServer();
        InputStream clientInputStream = new ByteArrayInputStream(("localhost\n1234\nexit\n").getBytes());
        assertArrayEquals(("Write host name.\nWrite port number.\nconnected\n").getBytes(),
                runClient(clientInputStream).toByteArray());
    }

    /**
     * Checks some list and get requests.
     * @throws InterruptedException thrown if problems with threads.
     */
    @Test
    public void testListAndGet() throws InterruptedException {
        runServer();
        InputStream clientInputStream = new ByteArrayInputStream(("localhost\n1234\n1 src/test/resources/dir1\n1 src/test/resources/dir1/dir2" +
                "\n2 src/test/resources/dir1/dir4/file3\n1 src/test/resources/dir1/dir2/dir3\nexit\n").getBytes());
        assertArrayEquals(("Write host name.\nWrite port number.\nconnected\n3 (dir2 true)(dir4 true)(dir5 true)\n2 (dir3 true)(file1 false)" +
                        "\n5 file3\n2 (file2 false)(file5 false)\n").getBytes(),
                runClient(clientInputStream).toByteArray());
    }

    /**
     * Checks if two clients can work with same server.
     * @throws InterruptedException thrown if problems with threads.
     */
    @Test
    public void testTwoClients() throws InterruptedException {
        runServer();
        Thread thread1 = new Thread(() -> {
            InputStream clientInputStream1 = new ByteArrayInputStream(("localhost\n1234\n1 src/test/resources/dir1/dir5\nexit\n").getBytes());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
            assertArrayEquals(("Write host name.\nWrite port number.\nconnected\n1 (file4 false)").getBytes(),
                    runClient(clientInputStream1).toByteArray());
        });
        Thread thread2 = new Thread(() -> {
            InputStream clientInputStream2 = new ByteArrayInputStream(("localhost\n1234\n2 src/test/resources/dir1/dir2/file1\nexit\n").getBytes());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
            assertArrayEquals(("Write host name.\nWrite port number.\nconnected\n5 file1\n").getBytes(),
                    runClient(clientInputStream2).toByteArray());
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

package spbau.eliseeva.ftp;

import org.junit.Test;

import java.io.*;
import static org.junit.Assert.*;

public class FTPTest {
    @Test
    public void test1() throws InterruptedException {
        runServer();
        InputStream clientInputStream = new ByteArrayInputStream(("localhost\n1234\nexit\n").getBytes());
        assertArrayEquals(("Write host name.\nWrite port number.\nconnected\n").getBytes(),
                runClient(clientInputStream).toByteArray());
    }

    @Test
    public void test2() throws InterruptedException {
        runServer();
        InputStream clientInputStream = new ByteArrayInputStream(("localhost\n1234\n1 src/test/resources/dir1\nexit\n").getBytes());
        assertArrayEquals(("Write host name.\nWrite port number.\nconnected\n3 (dir2 true)(dir4 true)(dir5 true)\n").getBytes(),
                runClient(clientInputStream).toByteArray());
    }

    @Test
    public void test3() throws InterruptedException {
        runServer();
        InputStream clientInputStream = new ByteArrayInputStream(("localhost\n1234\n1 src/test/resources/dir1/dir2" +
                "\n2 src/test/resources/dir1/dir4/file3\nexit\n").getBytes());
        assertArrayEquals(("Write host name.\nWrite port number.\nconnected\n2 (dir3 true)(file1 false)\n5 file3\n").getBytes(),
                runClient(clientInputStream).toByteArray());
    }

    @Test
    public void test4() throws InterruptedException {
        runServer();
        InputStream clientInputStream = new ByteArrayInputStream(("localhost\n1234\n1 src/test/resources/dir1/dir2/dir3\nexit\n").getBytes());
        assertArrayEquals(("Write host name.\nWrite port number.\nconnected\n2 (file2 false)(file5 false)\n").getBytes(),
                runClient(clientInputStream).toByteArray());

    }

    private void runServer() throws InterruptedException {
        InputStream serverInputStream = new ByteArrayInputStream(("1234\n\n").getBytes());
        Thread thread = new Thread(() -> {
            System.setIn(serverInputStream);
            PrintStream clientOutputStream = new PrintStream(new ByteArrayOutputStream());
            System.setOut(clientOutputStream);
            FTPServer.main(new String[0]);
        });
        thread.setDaemon(true);
        thread.start();
        Thread.sleep(1000);
    }

    private ByteArrayOutputStream runClient(InputStream clientInputStream) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream clientOutputStream = new PrintStream(byteArrayOutputStream);
        System.setIn(clientInputStream);
        System.setOut(clientOutputStream);
        FTPClient.main(new String[0]);
        return byteArrayOutputStream;
    }
}

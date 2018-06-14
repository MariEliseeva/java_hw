package spbau.eliseeva.XUnit;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class MainTest {
    private static final String END_OF_LINE = System.lineSeparator();

    @Test
    public void SimpleTest() {
        System.setIn(new ByteArrayInputStream(("build/classes/java/test\nspbau.eliseeva.XUnit.SimpleTest\n").getBytes()));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        Main.main(new String[0]);
        assertArrayEquals(("Write directory name:" + END_OF_LINE +
                "Write class name:" + END_OF_LINE +
                "BeforeClass" + END_OF_LINE +
                "Before" + END_OF_LINE +
                "test1" + END_OF_LINE +
                "After" + END_OF_LINE +
                "Before" + END_OF_LINE +
                "test2" + END_OF_LINE +
                "After" + END_OF_LINE +
                "AfterClass" + END_OF_LINE +
                "Test: test1" + END_OF_LINE +
                "Message: Done" + END_OF_LINE +
                "Time: 0" + END_OF_LINE +
                "Success: true" + END_OF_LINE + END_OF_LINE +
                "Test: test2" + END_OF_LINE +
                "Message: Done" + END_OF_LINE +
                "Time: 0" + END_OF_LINE +
                "Success: true" + END_OF_LINE + END_OF_LINE).getBytes(), outputStream.toByteArray());
    }

    @Test
    public void BadExceptionTest() {
        System.setIn(new ByteArrayInputStream(("build/classes/java/test\nspbau.eliseeva.XUnit.BadExceptionTest\n").getBytes()));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        Main.main(new String[0]);
        assertArrayEquals(("Write directory name:" + END_OF_LINE +
                "Write class name:" + END_OF_LINE  +
                "Test: test2" + END_OF_LINE +
                "Message: Exception: java.io.IOException" + END_OF_LINE +
                "Time: 0" + END_OF_LINE +
                "Success: false" + END_OF_LINE + END_OF_LINE).getBytes(), outputStream.toByteArray());
    }

    @Test
    public void NoExceptionTest() {
        System.setIn(new ByteArrayInputStream(("build/classes/java/test\nspbau.eliseeva.XUnit.NoExceptionTest\n").getBytes()));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        Main.main(new String[0]);
        assertArrayEquals(("Write directory name:" + END_OF_LINE +
                "Write class name:" + END_OF_LINE  +
                "Test: test2" + END_OF_LINE +
                "Message: No exception, was waited: java.io.IOException" + END_OF_LINE +
                "Time: 0" + END_OF_LINE +
                "Success: false" + END_OF_LINE + END_OF_LINE).getBytes(), outputStream.toByteArray());
    }

    @Test
    public void IgnoreTest() {
        System.setIn(new ByteArrayInputStream(("build/classes/java/test\nspbau.eliseeva.XUnit.IgnoreTest\n").getBytes()));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        Main.main(new String[0]);
        assertArrayEquals(("Write directory name:" + END_OF_LINE +
                "Write class name:" + END_OF_LINE  +
                "Test: test1" + END_OF_LINE +
                "Message: Ignored: reason" + END_OF_LINE +
                "Time: 0" + END_OF_LINE +
                "Success: true" + END_OF_LINE + END_OF_LINE).getBytes(), outputStream.toByteArray());
    }

    @Test
    public void ManyAfterTest() {
        System.setIn(new ByteArrayInputStream(("build/classes/java/test\nspbau.eliseeva.XUnit.ManyAfterTest\n").getBytes()));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        Main.main(new String[0]);
        assertArrayEquals(("Write directory name:" + END_OF_LINE +
                "Write class name:" + END_OF_LINE  +
                "Too many methods with same annotations." + END_OF_LINE).getBytes(), outputStream.toByteArray());
    }

    @Test
    public void MultipleAnnotationTest() {
        System.setIn(new ByteArrayInputStream(("build/classes/java/test\nspbau.eliseeva.XUnit.MultipleAnnotationTest\n").getBytes()));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        Main.main(new String[0]);
        assertArrayEquals(("Write directory name:" + END_OF_LINE +
                "Write class name:" + END_OF_LINE  +
                "Too many annotations for afterBefore" + END_OF_LINE).getBytes(), outputStream.toByteArray());
    }
}

package spbau.eliseeva.XUnit;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class MainTest {
    private static final String END_OF_LINE = "\n";//System.lineSeparator();

    @Test
    public void SimpleTest() {
        System.setIn(new ByteArrayInputStream(("src/test/resources\nsimpleTest\n").getBytes()));
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
                "Time: 1" + END_OF_LINE +
                "Success: true" + END_OF_LINE + END_OF_LINE +
                "Test: test2" + END_OF_LINE +
                "Message: Done" + END_OF_LINE +
                "Time: 1" + END_OF_LINE +
                "Success: true" + END_OF_LINE + END_OF_LINE).getBytes(), outputStream.toByteArray());
    }
}
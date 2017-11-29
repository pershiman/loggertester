import dao.LoggerDAO;
import model.LoggerFactory;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TxtLoggerDaoImplTest {
    private static String loggerFile = "logger.txt";

    @Before
    public void setup() {
        deleteFile();
    }

    @Test
    public void shouldCreateAndWriteATxtLog() throws FileNotFoundException {
        LoggerFactory.hasDBConnection = false;
        final String logMessage = "Logging something";
        File file = getLogFile(logMessage);
        assertTrue(file.exists());
        Scanner scanner = new Scanner(file);
        assertTrue(hasLogLine(scanner, logMessage));
    }

    @Test
    public void shouldNotCreateTxtLogWhenDBConnectionExit() {
        LoggerFactory.hasDBConnection = true;
        File file = getLogFile("Logging something");
        assertFalse(file.exists());
    }

    private File getLogFile(String message) {
        LoggerDAO logger = LoggerFactory.getInstance();
        logger.log(message);
        return new File(loggerFile);
    }

    private boolean hasLogLine(Scanner scanner, String logLine) {
        while (scanner.hasNextLine()) {
            final String lineFromFile = scanner.nextLine();
            if(lineFromFile.contains(logLine)) {
                return true;
            }
        }
        return false;
    }

    private static void deleteFile() {
        File file = new File(loggerFile);
        if(file.exists()) {
            file.delete();
        }
    }

    @AfterClass
    public static void tearDown() {
        deleteFile();
    }
}

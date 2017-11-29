package dao;
import java.io.File;
import java.io.FileWriter;
import java.lang.String;

public class TxtLoggerDAOImpl implements LoggerDAO {
    private static final String loggerName = "logger.txt";

    public void log(String logMessage) {
        try {
            File file = new File(loggerName);
            FileWriter writer = new FileWriter(file, true);
            writer.write(logMessage);
            writer.write(System.getProperty("line.separator"));
            writer.flush();
            writer.close();
      } catch(Exception e) {
          e.printStackTrace();
      }
    }
}

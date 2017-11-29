package model;

import dao.DBLoggerDAOImpl;
import dao.LoggerDAO;
import dao.TxtLoggerDAOImpl;

public class LoggerFactory {

    public static boolean hasDBConnection;  // Should be replaced with external config
    private static LoggerDAO logger;

    private LoggerFactory() {
    }

    public static LoggerDAO getInstance() {
      if(logger == null) {
          createLogger();
      }
      return logger;
    }
    private static void createLogger() {
        if(hasDBConnection) {
            logger = new DBLoggerDAOImpl();
        } else {
            logger = new TxtLoggerDAOImpl();
        }
    }
}

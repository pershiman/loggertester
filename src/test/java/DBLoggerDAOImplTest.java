import dao.DBLoggerDAOImpl;
import model.LoggerFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBLoggerDAOImplTest {

    DBLoggerDAOImpl dbLoggerDAOImpl;
    Connection conn = mock(Connection.class);
    PreparedStatement preparedStatement = mock(PreparedStatement.class);

    String sqlStatement;
    @Before
    public void before() throws SQLException {
      sqlStatement = "";
      when(conn.prepareStatement(any())).thenReturn( preparedStatement);
      dbLoggerDAOImpl = new DBLoggerDAOImpl() {
          @Override
          protected void writeToDB(PreparedStatement stmt) {
            sqlStatement = stmt.toString();
          }

          @Override
          protected Connection setupDB() {
             return conn;
          }
      };
    }

    @Test
    public void shouldInputCorrecLogMessageIntoSQLStatement() throws SQLException {
        LoggerFactory.hasDBConnection = true;
        String message = "message";
        dbLoggerDAOImpl.log( message );
        verify(conn).prepareStatement(anyString());
        ArgumentCaptor<String> logMessageCaptor = ArgumentCaptor.forClass( String.class );
        verify( preparedStatement, times(2)).setString(anyInt(), logMessageCaptor.capture());
        assertEquals(message, logMessageCaptor.getValue());
    }
}

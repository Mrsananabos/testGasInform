package annabos.ru.dao;

import annabos.ru.model.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class DBStoreImplTest {
    private DBStoreImpl store;

    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockPrStatement;
    @Mock
    private ResultSet mockResultSet;

    @Before
    public void doBefore() throws SQLException {
        MockitoAnnotations.initMocks(this);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPrStatement);
        this.store = new DBStoreImpl(mockConnection);
    }

    @Test
    public void whenFindByFirstNameThenReturnNotNullUser() throws SQLException {
        when(mockPrStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("last_name")).thenReturn("Graf");
        User expected = new User("Eva", "Graf");
        User result = store.findUserByName("Eva");
        assertEquals(expected, result);
        verify(mockConnection).prepareStatement("SELECT last_name FROM users WHERE first_name = ?");
        verify(mockPrStatement).setString(1, "Eva");
        verify(mockPrStatement).executeQuery();
        verify(mockResultSet).next();
        verify(mockResultSet).getString("last_name");
        verify(mockResultSet).close();
        verify(mockPrStatement).close();
        verifyNoMoreInteractions(mockConnection, mockPrStatement, mockResultSet);
    }

    @Test
    public void whenFindByFirstNameThenReturnNullUser() throws SQLException {
        when(mockPrStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);
        User result = store.findUserByName("Eva");
        assertNull(result);
        verify(mockConnection).prepareStatement("SELECT last_name FROM users WHERE first_name = ?");
        verify(mockPrStatement).setString(1, "Eva");
        verify(mockPrStatement).executeQuery();
        verify(mockResultSet).next();
        verify(mockResultSet).close();
        verify(mockPrStatement).close();
        verifyNoMoreInteractions(mockConnection, mockPrStatement, mockResultSet);
    }

    @Test
    public void whenUpdateLastNameThenReturnNewUser() throws SQLException {
        when(mockPrStatement.executeUpdate()).thenReturn(1);
        User expected = new User("1", "Enzel");
        User result = store.updateLastName(expected);
        assertEquals(expected, result);
        verify(mockConnection).prepareStatement("UPDATE users SET last_name = ? WHERE first_name = ?");
        verify(mockPrStatement).setString(1, "Enzel");
        verify(mockPrStatement).setString(2, "1");
        verify(mockPrStatement).executeUpdate();
        verify(mockPrStatement).close();
        verifyNoMoreInteractions(mockConnection, mockPrStatement);
    }

    @Test
    public void whenUpdateLastNameThenReturnNullUser() throws SQLException {
        when(mockPrStatement.executeUpdate()).thenReturn(0);
        User result = store.updateLastName(new User("Eva", "Enzel"));
        assertNull(result);
        verify(mockConnection).prepareStatement("UPDATE users SET last_name = ? WHERE first_name = ?");
        verify(mockPrStatement).setString(1, "Enzel");
        verify(mockPrStatement).setString(2, "Eva");
        verify(mockPrStatement).executeUpdate();
        verify(mockPrStatement).close();
        verifyNoMoreInteractions(mockConnection, mockPrStatement);
    }

    @Test
    public void whenUpdateLastNameThenThrowSQLException() throws SQLException {
        when(mockPrStatement.executeUpdate()).thenThrow(new SQLException());
        store.updateLastName(new User("Eva", "Enzel"));
        verify(mockConnection).prepareStatement("UPDATE users SET last_name = ? WHERE first_name = ?");
        verify(mockPrStatement).setString(1, "Enzel");
        verify(mockPrStatement).setString(2, "Eva");
        verify(mockPrStatement).executeUpdate();
        verify(mockPrStatement).close();
        verifyNoMoreInteractions(mockConnection, mockPrStatement);
    }

}
package annabos.ru.dao;

import annabos.ru.model.User;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBStoreImpl implements DBStore {
    private static final Logger LOGGER = Logger.getLogger(DBStoreImpl.class);
    private static final Settings SETTINGS = Settings.getInstance();
    private static final String FIND_USER_BY_FN = SETTINGS.value("FIND_USER_BY_FN");
    private static final String UPDATE_LAST_NAME = SETTINGS.value("UPDATE_LAST_NAME");
    private Connection connection;

    public DBStoreImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public User findUserByName(String firstName) {
        String lastName = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_BY_FN)) {
            preparedStatement.setString(1, firstName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    lastName = resultSet.getString("last_name");
                }
            }
            return lastName == null ? null : new User(firstName, lastName);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public User updateLastName(User user) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_LAST_NAME)) {
            preparedStatement.setString(1, user.getLastName());
            preparedStatement.setString(2, user.getFirstName());
            return preparedStatement.executeUpdate() == 1 ? user : null;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

}

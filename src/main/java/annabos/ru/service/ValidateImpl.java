package annabos.ru.service;

import annabos.ru.dao.ConnectionProducer;
import annabos.ru.dao.DBStoreImpl;
import annabos.ru.model.User;
import org.apache.log4j.Logger;

import java.sql.Connection;

public class ValidateImpl implements Validate {
    private final Connection connection;
    private final DBStoreImpl store;
    private static final Logger LOGGER = Logger.getLogger(ValidateImpl.class);

    public ValidateImpl() {
        this.connection = ConnectionProducer.getInstance().getConnection();
        this.store = new DBStoreImpl(this.connection);
    }

    @Override
    public User findUserByName(String firstName) {
        User result = null;
        if (valueIsValid(firstName)) {
            result = store.findUserByName(firstName);
            if (result == null) {
                LOGGER.info("User(" + firstName + ") is not found");
            }
        } else {
            LOGGER.info("Enter a valid first name");
        }
        return result;
    }

    @Override
    public User updateLastName(User user) {
        User result = null;
        if (valueIsValid(user.getFirstName()) && valueIsValid(user.getLastName())) {
            result = store.updateLastName(user);
            if (result == null) {
                LOGGER.info("User with this first name(" + user.getFirstName() + ") is not found");
            }
        } else {
            LOGGER.info("Enter a valid first data");
        }
        return result;
    }

    private boolean valueIsValid(String value) {
        return ((value != null) && (!value.isEmpty()));
    }

}

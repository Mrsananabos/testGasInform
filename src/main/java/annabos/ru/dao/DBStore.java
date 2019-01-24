package annabos.ru.dao;

import annabos.ru.model.User;

public interface DBStore {
    User findUserByName(String firstName);

    User updateLastName(User user);
}

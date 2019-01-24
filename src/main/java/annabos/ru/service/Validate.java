package annabos.ru.service;

import annabos.ru.model.User;

public interface Validate {
    User findUserByName(String firstName);

    User updateLastName(User user);
}

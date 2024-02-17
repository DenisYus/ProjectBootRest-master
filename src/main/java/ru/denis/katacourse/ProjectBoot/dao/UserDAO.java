package ru.denis.katacourse.ProjectBoot.dao;


import ru.denis.katacourse.ProjectBoot.model.User;

import java.util.List;

public interface UserDAO {
    void saveUser(User user);

    void updateUser(User updateUser);

    void removeUserById(int id);

    List<User> getAllUsers();

    User getUserById(int id);

    User findByUserEmail(String email);

}
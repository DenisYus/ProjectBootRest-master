package ru.denis.katacourse.ProjectBoot.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.stereotype.Service;
import ru.denis.katacourse.ProjectBoot.dao.UserDAO;
import ru.denis.katacourse.ProjectBoot.model.User;


import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;

    private final PasswordEncoder passwordEncoder;


    @Lazy
    public UserServiceImpl(UserDAO userDAO, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDAO.saveUser(user);
    }

    @Override
    @Transactional
    public void updateUser(User updateUser, int id) {
        User user = getUserById(id);
        if (!(user.getPassword()).equals(updateUser.getPassword())) {
            updateUser.setPassword(passwordEncoder.encode(updateUser.getPassword()));

        }
        userDAO.updateUser(updateUser);
    }

    @Override
    @Transactional
    public void removeUserById(int id) {
        userDAO.removeUserById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Override
    public User getUserById(int id) {
        return userDAO.getUserById(id);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if (userDAO.findByUserEmail(email) == null)
            throw new UsernameNotFoundException("User not found");

        return userDAO.findByUserEmail(email);

    }







}

package ru.denis.katacourse.ProjectBoot.dao;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import ru.denis.katacourse.ProjectBoot.model.User;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    @PersistenceContext()
    private EntityManager entityManager;

    @Override
    public void saveUser(User user) {
        entityManager.persist(user);

    }

    @Override
    public void updateUser(User updateUser) {
        entityManager.merge(updateUser);
    }

    @Override
    public void removeUserById(int id) {
        try {
            User user = entityManager.find(User.class, id);
            entityManager.remove(user);
        } catch (NullPointerException e) {
            throw new UsernameNotFoundException("User not found");
        }

    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("from User", User.class).getResultList();
    }

    @Override
    public User getUserById(int id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User findByUserEmail(String email) {

        return entityManager.createQuery("select u from User u where u.email = :email", User.class)
                .setParameter("email", email)
                .getSingleResult();
    }
}

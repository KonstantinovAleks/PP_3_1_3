package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {

    List<User> showAllUsers();

    User showUserById(Long id);

    void saveUser(User user, String role);

    void updateUser(Long id, User user, String role);

    void deleteUser(Long id);
}

package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(PasswordEncoder passwordEncoder, UserDao userDao) {
        this.passwordEncoder = passwordEncoder;
        this.userDao = userDao;
    }

    @Override
    public List<User> showAllUsers() {
        return userDao.showAllUsers();
    }

    @Override
    public User showUserById(Long id) {
        return userDao.showUserById(id);
    }

    @Override
    public void saveUser(User user, String role) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        if (role.equals("ROLE_ADMIN")) {
            roles.add(new Role(2L, "ROLE_ADMIN"));
        }
        roles.add(new Role(1L, "ROLE_USER"));
        user.setRoles(roles);
        userDao.saveUser(user);
    }

    @Override
    public void updateUser(Long id, User user, String role) {
        user.setId(id);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        if (role.equals("ROLE_ADMIN")) {
            roles.add(new Role(2L, "ROLE_ADMIN"));
        }
        roles.add(new Role(1L, "ROLE_USER"));
        user.setRoles(roles);
        userDao.updateUser(id, user);
    }

    @Override
    public void deleteUser(Long id) {
        userDao.deleteUserById(id);
    }
}

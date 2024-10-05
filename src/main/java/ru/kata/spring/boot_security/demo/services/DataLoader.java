package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {

    private final RoleDao roleDao;

    private final UserDao userDao;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataLoader(RoleDao roleDao, UserDao userDao, PasswordEncoder passwordEncoder) {
        this.roleDao = roleDao;
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        roleDao.addRole(new Role(1L, "ROLE_USER"));
        roleDao.addRole(new Role(2L, "ROLE_ADMIN"));
        userDao.addUser(new User("admin", "admin", 35, "admin@mail.ru", "admin"));
        userDao.addUser(new User("user", "user", 30, "user@mail.ru", "user"));
        userDao.addUser(new User("Chandler", "Bing", 26, "chan@mail.ru", "chan"));
        Set<Role> rolesAdmin = new HashSet<>();
        rolesAdmin.add(new Role(1L, "ROLE_USER"));
        rolesAdmin.add(new Role(2L, "ROLE_ADMIN"));
        User admin = userDao.findByEmail("admin@mail.ru");
        admin.setRoles(rolesAdmin);
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        userDao.updateUser(admin);
        Set<Role> rolesUser = new HashSet<>();
        rolesUser.add(new Role(1L, "ROLE_USER"));
        User user = userDao.findByEmail("user@mail.ru");
        user.setRoles(rolesUser);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.updateUser(user);
        User ann = userDao.findByEmail("chan@mail.ru");
        ann.setRoles(rolesUser);
        ann.setPassword(passwordEncoder.encode(ann.getPassword()));
        userDao.updateUser(ann);
    }
}

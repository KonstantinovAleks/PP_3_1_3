package ru.kata.spring.boot_security.demo.initialization;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Component
public class DBInit implements ApplicationListener<ContextRefreshedEvent> {

    private final RoleDao roleDao;

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    public DBInit(RoleDao roleDao, UserService userService, PasswordEncoder passwordEncoder) {
        this.roleDao = roleDao;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        Role userRole = new Role();
        userRole.setName("ROLE_USER");
        roleDao.addRole(userRole);

        Role adminRole = new Role();
        adminRole.setName("ROLE_ADMIN");
        roleDao.addRole(adminRole);

        List<Role> userRoles = List.of(userRole);
        List<Role> adminRoles = Arrays.asList(adminRole, userRole);

        User admin = new User();
        admin.setName("admin");
        admin.setSurName("admin");
        admin.setUsername("admin");
        admin.setPassword("admin");
        admin.setRoles(new HashSet<>(adminRoles));
        userService.saveUser(admin, "ROLE_ADMIN");

        User user = new User();
        user.setName("user");
        user.setSurName("user");
        user.setUsername("user");
        user.setPassword("user");
        user.setRoles(new HashSet<>(userRoles));
        userService.saveUser(user, "ROLE_USER");
    }
}

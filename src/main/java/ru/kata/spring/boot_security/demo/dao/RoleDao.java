package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.Role;

public interface RoleDao {

    Role getRoleById(Long id);

    void addRole(Role role);
}

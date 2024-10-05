package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.List;

@Controller
public class MainController {

    private final UserService userService;

    @Autowired
    public MainController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/page")
    public String showAdminPage(@ModelAttribute("newUser") User newUser,
                                Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authenticatedUser = (User) authentication.getPrincipal();
        String roles = authenticatedUser.getRoles().toString();
        if (roles.contains("ROLE_ADMIN")) {
            model.addAttribute("admin", authenticatedUser);
            model.addAttribute("user", authenticatedUser);
            List<User> users = userService.listUsers();
            model.addAttribute("users", users);
        } else {
            model.addAttribute("user", authenticatedUser);
        }
        return "_page";
    }

    @PostMapping("/create")
    public String createUser(@ModelAttribute("newUser") User newUser,
                             @RequestParam("role") String role) {
        userService.create(newUser, role);
        return "redirect:/page";
    }

    @PostMapping("/edit")
    public String updateUser(@ModelAttribute("user") User user,
                             @RequestParam("role") String role) {
        System.out.println(user);
        userService.edit(user, role);
        return "redirect:/page";
    }

    @PostMapping("/delete")
    public String deleteUser(@ModelAttribute("user") User user) {
        userService.delete(user);
        return "redirect:/page";
    }
}

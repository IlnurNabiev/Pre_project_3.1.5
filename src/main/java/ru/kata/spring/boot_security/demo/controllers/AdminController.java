package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.services.UserBusinesService;
import ru.kata.spring.boot_security.demo.services.UserService;
import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

        private RoleRepository roleRepository;
    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    private UserBusinesService userBusinesService;
    @Autowired
    public void setUserBusinesService(UserBusinesService userBusinesService) {
        this.userBusinesService = userBusinesService;
    }

    private UserService userService;
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public String getAllUsers(Model model, Principal principal) {
        model.addAttribute("user", userService.findByUsername(principal.getName()));
        model.addAttribute("allRoles", roleRepository.findAll());
        model.addAttribute("allUsers", userBusinesService.index());
        model.addAttribute("emptyUser", new User());
        return "admin";
    }

    @PostMapping
    public String saveUser(@ModelAttribute("emptyUser") User user) {
        userBusinesService.save(user);
        return "redirect:/admin";
    }

    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") User user, @PathVariable("id") Long id) {
        userBusinesService.update(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userBusinesService.delete(id);
        return "redirect:/admin";
    }
}
package ru.greatstep.spring_sqlite.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.greatstep.spring_sqlite.models.User;
import ru.greatstep.spring_sqlite.repositories.RoleRepository;
import ru.greatstep.spring_sqlite.service.UserService;

import java.security.Principal;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    private final RoleRepository roleRepository;


    private final UserService userService;


    @Autowired
    public RestController(RoleRepository roleRepository, UserService userService) {
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    @GetMapping("/rest")
    public List<User> findAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/rest/{id}")
    public User findOneUser(@PathVariable long id) {
        User user = userService.findUserById(id);
        return user;
    }

    @PostMapping("/rest")
    public User addNewUser(@RequestBody User user) {
        roleRepository.saveAll(user.getRoles());
        userService.save(user);
        return user;
    }

    @PutMapping("/rest/{id}")
    public User updateUser(@RequestBody User user, @PathVariable("id") long id) {
        roleRepository.saveAll(user.getRoles());
        userService.saveAndFlush(user);
        return user;
    }

    @DeleteMapping("/rest/{id}")
    public void deleteUser(@PathVariable long id) {
        userService.deleteById(id);
    }

}

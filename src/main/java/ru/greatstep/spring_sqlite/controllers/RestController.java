package ru.greatstep.spring_sqlite.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.greatstep.spring_sqlite.models.User;
import ru.greatstep.spring_sqlite.service.UserService;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    private final UserService userService;

    private List<LocalDate> totalDates;

    @Autowired
    public RestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/rest")
    public List<User> findAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/rest/{id}")
    public User findOneUser(@PathVariable long id) {
        return userService.findUserById(id);
    }

    @PostMapping("/rest")
    public User addNewUser(@RequestBody User user) {
        parseVacation(user);

        userService.save(user);
        return user;
    }

    @PutMapping("/rest/{id}")
    public User updateUser(@RequestBody User user) {
        parseVacation(user);
        userService.saveAndFlush(user);
        return user;
    }

    private void parseVacation(User user) {
        String[] vacation = user.getVacation().split(" - ");
        user.setVacationStart(vacation[0]);
        user.setVacationEnd(vacation[1]);
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate date = LocalDate.parse(vacation[0], dateFormat);
        LocalDate date2 = LocalDate.parse(vacation[1],dateFormat);
        long days = ChronoUnit.DAYS.between(date, date2) + 1;
        user.setVacationDaysCount((int) days);
    }

    @DeleteMapping("/rest/{id}")
    public void deleteUser(@PathVariable long id) {
        userService.deleteById(id);
    }

}

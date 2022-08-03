package ru.greatstep.spring_sqlite.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ContainerNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.greatstep.spring_sqlite.models.User;
import ru.greatstep.spring_sqlite.service.UserService;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    private final UserService userService;

    private List<LocalDate> totalDates;

    private List<LocalDate> deleteDates;

    private List<String> totalDatesToString = new ArrayList<>();

    ObjectMapper objectMapper = new ObjectMapper();

    ObjectNode jsonNode = objectMapper.createObjectNode();


    @Autowired
    public RestController(UserService userService, List<LocalDate> totalDates, List<LocalDate> deleteDates) {
        this.userService = userService;
        this.totalDates = totalDates;
        this.deleteDates = deleteDates;
    }

    @GetMapping("/rest")
    public List<User> findAllUsers() {
        return userService.findAll();
    }

    @RequestMapping(value = "/rest/invalid-dates",
            method = RequestMethod.GET,
            produces = "application/json")
    public ContainerNode<ObjectNode> getInvalidDates() {
        String[] totalInvalidDates = new String[totalDatesToString.size()];
        totalDatesToString.toArray(totalInvalidDates);
        ArrayNode arrayNode = jsonNode.putArray("invalid");
        Arrays.stream(totalInvalidDates).forEach(arrayNode::add);
        return jsonNode;
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
        LocalDate date2 = LocalDate.parse(vacation[1], dateFormat);
        long days = ChronoUnit.DAYS.between(date, date2) + 1;
        user.setVacationDaysCount((int) days);


        while (!date.isAfter(date2)) {
            totalDates.add(date);
            date = date.plusDays(1);
        }
//        System.out.println(totalDates);

        for (int i = 0; i < totalDates.size(); i++) {
//        System.out.println(totalDates.get(i));
            totalDatesToString.add(String.valueOf(totalDates.get(i)));
            System.out.println(totalDatesToString);
        }
    }

    @DeleteMapping("/rest/{id}")
    public void deleteUser(@PathVariable long id) {
        User user = userService.findUserById(id);
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate date = LocalDate.parse(user.getVacationStart(), dateFormat);
        LocalDate date2 = LocalDate.parse(user.getVacationEnd(), dateFormat);

        while (!date.isAfter(date2)) {
            deleteDates.add(date);
            date = date.plusDays(1);
        }
//        System.out.println(deleteDates);

        for (int i = 0; i < deleteDates.size(); i++) {
//        System.out.println(totalDates.get(i));
            totalDatesToString.remove(String.valueOf(totalDates.get(i)));
            totalDates.remove(deleteDates.get(i));
            System.out.println(totalDatesToString);
        }


        userService.deleteById(id);
    }

}

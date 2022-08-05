package ru.greatstep.spring_sqlite.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ContainerNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.greatstep.spring_sqlite.models.InvalidDate;
import ru.greatstep.spring_sqlite.models.SelectedDate;
import ru.greatstep.spring_sqlite.models.User;
import ru.greatstep.spring_sqlite.models.Worker;
import ru.greatstep.spring_sqlite.service.absctract.InvalidDateService;
import ru.greatstep.spring_sqlite.service.absctract.SelectedDateService;
import ru.greatstep.spring_sqlite.service.absctract.UserService;
import ru.greatstep.spring_sqlite.service.absctract.WorkerService;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@org.springframework.web.bind.annotation.RestController
public class RestController {

    private final UserService userService;

    private final SelectedDateService dateService;

    private final InvalidDateService invalidDateService;

    private final WorkerService workerService;

    ObjectMapper objectMapper = new ObjectMapper();

    ObjectNode jsonNode = objectMapper.createObjectNode();

    @Autowired
    public RestController(UserService userService, SelectedDateService dateService,
                          InvalidDateService invalidDateService,WorkerService workerService) {
        this.userService = userService;
        this.dateService = dateService;
        this.invalidDateService = invalidDateService;
        this.workerService = workerService;
    }

    @GetMapping("/rest")
    public List<User> findAllUsers() {
        return userService.findAll();
    }

    @RequestMapping(value = "/rest/invalid-dates",
            method = RequestMethod.GET,
            produces = "application/json")
    public ContainerNode<ObjectNode> getInvalidDates() {

        ArrayNode arrayNode = jsonNode.putArray("invalid");
        Arrays.stream(invalidDateService.findAllDates()).forEach(arrayNode::add);
        return jsonNode;
    }

    @GetMapping("/rest/{id}")
    public User findOneUser(@PathVariable long id) {
        return userService.findUserById(id);
    }

    @PostMapping("/rest/new-worker")
    public Worker addNewWorker(@RequestBody Worker worker) {
        workerService.save(worker);
        return worker;
    }

    @PostMapping("/rest")
    public User addNewUser(@RequestBody User user) {
        userService.parseVacation(user);
        userService.save(user);
        return user;
    }

    @PutMapping("/rest/{id}")
    public User updateUser(@RequestBody User user) {
        userService.parseVacation(user);
        userService.saveAndFlush(user);
        return user;
    }

//    private void parseVacation(User user) {
//        String[] vacation = user.getVacation().split(" - ");
//        user.setVacationStart(vacation[0]);
//        user.setVacationEnd(vacation[1]);
//        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
//        LocalDate date = LocalDate.parse(vacation[0], dateFormat);
//        LocalDate date2 = LocalDate.parse(vacation[1], dateFormat);
//        long days = ChronoUnit.DAYS.between(date, date2) + 1;
//        user.setVacationDaysCount((int) days);
//        List<SelectedDate> selectedDates = new ArrayList<>();
//        while (!date.isAfter(date2)) {
//            SelectedDate selectedDate = new SelectedDate();
//            selectedDate.setDate(date.toString());
//            selectedDates.add(selectedDate);
//            InvalidDate invalidDate = new InvalidDate();
//            invalidDate.setDate(selectedDate.getDate());
//            if (dateService.countSelectedDateByDate(selectedDate.getDate()) + 1 > 1) {
//                invalidDateService.save(invalidDate);
//            }
//            date = date.plusDays(1);
//        }
//        user.setSelectedDates(selectedDates);
//
//    }

    @DeleteMapping("/rest/{id}")
    public void deleteUser(@PathVariable long id) {
        User user = userService.findUserById(id);
        List<SelectedDate> selectedDates = user.getSelectedDates();
        for (SelectedDate selectedDate : selectedDates) {
            String reference = selectedDate.getDate();
            if (invalidDateService.findByDate(reference) != null) {
                invalidDateService.deleteByDate(reference);
            }
        }

        userService.deleteById(id);
    }

    @DeleteMapping("/rest/delete-all")
    public void deleteAllUsers() {
        invalidDateService.deleteAll();
        userService.deleteAll();
    }

}

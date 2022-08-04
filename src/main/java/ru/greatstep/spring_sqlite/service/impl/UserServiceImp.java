package ru.greatstep.spring_sqlite.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.greatstep.spring_sqlite.models.InvalidDate;
import ru.greatstep.spring_sqlite.models.SelectedDate;
import ru.greatstep.spring_sqlite.models.User;
import ru.greatstep.spring_sqlite.repositories.UserRepository;
import ru.greatstep.spring_sqlite.service.absctract.InvalidDateService;
import ru.greatstep.spring_sqlite.service.absctract.SelectedDateService;
import ru.greatstep.spring_sqlite.service.absctract.UserService;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


@Service
public class UserServiceImp implements UserService {

    private UserRepository userRepository;

    private SelectedDateService dateService;

    private InvalidDateService invalidDateService;

    @Autowired
    public void setUserRepository(UserRepository userRepository, SelectedDateService dateService, InvalidDateService invalidDateService) {
        this.userRepository = userRepository;
        this.dateService = dateService;
        this.invalidDateService = invalidDateService;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByFullName(String fullName) {
        return userRepository.findByFullName(fullName);
    }

    @Override
    public User findUserById(long id) {
        return userRepository.findUserById(id);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void saveAndFlush(User user) {
        userRepository.saveAndFlush(user);
    }

    @Override
    public void deleteById(long id) {
        userRepository.deleteById(id);
    }

    public void parseVacation(User user) {
        String[] vacation = user.getVacation().split(" - ");

        user.setVacationStart(vacation[0]);
        user.setVacationEnd(vacation[1]);

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate date = LocalDate.parse(vacation[0], dateFormat);
        LocalDate date2 = LocalDate.parse(vacation[1], dateFormat);

        long days = ChronoUnit.DAYS.between(date, date2) + 1;

        user.setVacationDaysCount((int) days);
        List<SelectedDate> selectedDates = new ArrayList<>();

        while (!date.isAfter(date2)) {
            SelectedDate selectedDate = new SelectedDate();
            selectedDate.setDate(date.toString());
            selectedDates.add(selectedDate);
            InvalidDate invalidDate = new InvalidDate();
            invalidDate.setDate(selectedDate.getDate());
            if (dateService.countSelectedDateByDate(selectedDate.getDate()) + 1 > 1) {
                invalidDateService.save(invalidDate);
            }
            date = date.plusDays(1);
        }
        user.setSelectedDates(selectedDates);
    }


    @Override
    public void initializationTestUsers() {
        for (int i = 1; i <= 12; i++) {
            User user = new User();
            user.setFullName("Работник" + i);
            user.setPosition("СОП");
            List<SelectedDate> selectedDates = new ArrayList<>();
            for (int j = 1; j <= 28; j++) {
                SelectedDate selectedDate = new SelectedDate();
                String dateOfString;
                if (i < 10 && j < 10) {
                    dateOfString = "2022" + "-" + "0" + i + "-" + "0" + j;
                } else if (i < 10) {
                    dateOfString = "2022" + "-" + "0" + i + "-" + j;
                } else if (j < 10) {
                    dateOfString = "2022" + "-" + i + "-" + "0" + j;
                } else {
                    dateOfString = "2022" + "-" + i + "-" + j;
                }

                Date date = Date.valueOf(dateOfString);
                selectedDate.setDate(date.toString());
                selectedDates.add(selectedDate);
            }
            user.setSelectedDates(selectedDates);
            user.setVacationDaysCount(selectedDates.size());

            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(selectedDates.get(0).getDate(), dateFormat);
            LocalDate date2 = LocalDate.parse(selectedDates.get(selectedDates.size() - 1).getDate(), dateFormat);

            String start = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            String end = date2.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

            user.setVacationStart(start);
            user.setVacationEnd(end);

            String vacation = start + " - " + end;
            user.setVacation(vacation);
            userRepository.save(user);

        }
    }

}

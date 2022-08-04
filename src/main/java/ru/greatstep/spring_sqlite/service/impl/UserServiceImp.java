package ru.greatstep.spring_sqlite.service.impl;


import org.hibernate.sql.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.greatstep.spring_sqlite.models.SelectedDate;
import ru.greatstep.spring_sqlite.models.User;
import ru.greatstep.spring_sqlite.repositories.SelectedDatesRepository;
import ru.greatstep.spring_sqlite.repositories.UserRepository;
import ru.greatstep.spring_sqlite.service.absctract.UserService;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Service
public class UserServiceImp implements UserService {

    private UserRepository userRepository;

    private SelectedDatesRepository datesRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository, SelectedDatesRepository datesRepository) {
        this.userRepository = userRepository;
        this.datesRepository = datesRepository;
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

    @Override
    public void initializationTestUsers() {
        for (int i = 8; i <= 8; i++) {
            User user = new User();
            user.setFullName("Работник" + i);
            user.setPosition("СОП");
            List<SelectedDate> selectedDates = new ArrayList<>();
            for (int j = 1; j <= 5; j++) {
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
//                datesRepository.save(selectedDate);
            }
            user.setSelectedDates(selectedDates);
            user.setVacationDaysCount(selectedDates.size());
//            user.setVacationStart(selectedDates.get(0).getDate());
//            user.setVacationEnd(selectedDates.get(selectedDates.size() - 1).getDate());

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

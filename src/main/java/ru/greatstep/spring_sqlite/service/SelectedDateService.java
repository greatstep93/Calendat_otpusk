package ru.greatstep.spring_sqlite.service;

import ru.greatstep.spring_sqlite.models.SelectedDate;
import ru.greatstep.spring_sqlite.models.User;

import java.time.LocalDate;
import java.util.List;

public interface SelectedDateService {

    SelectedDate findSelectedDateByLocalDate(SelectedDate selectedDate);

    SelectedDate findSelectedDateById(Long id);

    void removeById(Long id);

    void save(SelectedDate selectedDate);

    void saveAndFlush(SelectedDate selectedDate);

    List<SelectedDate> findAll();

    void saveAll(List<SelectedDate> selectedDates);

    void deleteAll(List<SelectedDate> selectedDates);

    void removeByLocalDate(LocalDate localDate);
}

package ru.greatstep.spring_sqlite.service.absctract;

import ru.greatstep.spring_sqlite.models.SelectedDate;

import java.util.List;

public interface SelectedDateService {

    List<SelectedDate> findAll();

    SelectedDate findByDate(String date);

    SelectedDate findSelectedDateById(Long id);

    void save(SelectedDate selectedDate);

    void saveAndFlush(SelectedDate selectedDate);

    void deleteById(Long id);

    String[] findAllDates();

    boolean existsSelectedDateByDate(String date);

    int countSelectedDateByDate(String date);
}

package ru.greatstep.spring_sqlite.service.absctract;


import ru.greatstep.spring_sqlite.models.InvalidDate;

import java.util.List;

public interface InvalidDateService {

    List<InvalidDate> findAll();

    InvalidDate findByDate(String date);

    InvalidDate findInvalidDateById(Long id);

    void save(InvalidDate selectedDate);

    void saveAndFlush(InvalidDate selectedDate);

    void deleteById(Long id);

    String[] findAllDates();

    boolean existsInvalidDateByDate(String date);

    int countInvalidDateByDate(String date);
}

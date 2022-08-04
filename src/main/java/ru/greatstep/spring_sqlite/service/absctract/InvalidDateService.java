package ru.greatstep.spring_sqlite.service.absctract;


import ru.greatstep.spring_sqlite.models.InvalidDate;

import java.util.List;

public interface InvalidDateService {

    List<InvalidDate> findAll();

    InvalidDate findByDate(String date);

    InvalidDate findInvalidDateById(Long id);

    void save(InvalidDate invalidDate);

    void saveAndFlush(InvalidDate invalidDate);

    void deleteById(Long id);

    String[] findAllDates();

    boolean existsInvalidDateByDate(String date);

    int countInvalidDateByDate(String date);

    void deleteByDate(String date);
}

package ru.greatstep.spring_sqlite.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.greatstep.spring_sqlite.models.InvalidDate;
import ru.greatstep.spring_sqlite.repositories.InvalidDateRepository;
import ru.greatstep.spring_sqlite.service.absctract.InvalidDateService;

import java.util.List;

@Service
public class InvalidDateServiceImpl implements InvalidDateService {

    InvalidDateRepository invalidRepository;

    @Autowired
    public InvalidDateServiceImpl(InvalidDateRepository invalidRepository) {
        this.invalidRepository = invalidRepository;
    }


    @Override
    public List<InvalidDate> findAll() {
        return invalidRepository.findAll();
    }

    @Override
    public InvalidDate findByDate(String date) {
        return invalidRepository.findByDate(date);
    }

    @Override
    public InvalidDate findInvalidDateById(Long id) {
        return invalidRepository.findInvalidDateById(id);
    }

    @Override
    public void save(InvalidDate invalidDate) {
        invalidRepository.save(invalidDate);
    }

    @Override
    public void saveAndFlush(InvalidDate invalidDate) {
        invalidRepository.saveAndFlush(invalidDate);
    }

    @Override
    public void deleteById(Long id) {
        invalidRepository.deleteById(id);
    }

    @Override
    public String[] findAllDates() {
        return invalidRepository.findAllDates();
    }

    @Override
    public boolean existsInvalidDateByDate(String date) {
        return invalidRepository.existsInvalidDateByDate(date);
    }

    @Override
    public int countInvalidDateByDate(String date) {
        return invalidRepository.countInvalidDateByDate(date);
    }
    @Override
    public void deleteByDate(String date) {
        invalidRepository.deleteByDate(date);
    }
    @Override
    public void deleteAll(){ invalidRepository.deleteAll();}
}

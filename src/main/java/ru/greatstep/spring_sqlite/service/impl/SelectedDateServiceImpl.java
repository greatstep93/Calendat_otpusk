package ru.greatstep.spring_sqlite.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.greatstep.spring_sqlite.models.SelectedDate;
import ru.greatstep.spring_sqlite.repositories.SelectedDatesRepository;
import ru.greatstep.spring_sqlite.service.absctract.SelectedDateService;

import java.util.List;

@Service
public class SelectedDateServiceImpl implements SelectedDateService {

    SelectedDatesRepository dateRepository;

    @Autowired
    public SelectedDateServiceImpl(SelectedDatesRepository dateRepository) {
        this.dateRepository = dateRepository;
    }


    @Override
    public List<SelectedDate> findAll() {
        return dateRepository.findAll();
    }

    @Override
    public SelectedDate findByDate(String date) {
        return dateRepository.findByDate(date);
    }

    @Override
    public SelectedDate findSelectedDateById(Long id) {
        return dateRepository.findSelectedDateById(id);
    }

    @Override
    public void save(SelectedDate selectedDate) {
        dateRepository.save(selectedDate);
    }

    @Override
    public void saveAndFlush(SelectedDate selectedDate) {
        dateRepository.saveAndFlush(selectedDate);
    }

    @Override
    public void deleteById(Long id) {
        dateRepository.deleteById(id);
    }

    @Override
    public String[] findAllDates() {
        return dateRepository.findAllDates();
    }

    @Override
    public boolean existsSelectedDateByDate(String date) {
        return dateRepository.existsSelectedDateByDate(date);
    }
    @Override
    public int countSelectedDateByDate(String date){
        return dateRepository.countSelectedDateByDate(date);
    }

}

package ru.greatstep.spring_sqlite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.greatstep.spring_sqlite.models.SelectedDate;
import ru.greatstep.spring_sqlite.repositories.SelectedDateRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class SelectedDateServiceImpl implements SelectedDateService {

    SelectedDateRepository selectedDateRepository;

    @Autowired
    public SelectedDateServiceImpl(SelectedDateRepository selectedDateRepository) {
        this.selectedDateRepository = selectedDateRepository;
    }

    @Override
    public SelectedDate findSelectedDateByLocalDate(SelectedDate selectedDate) {
        return selectedDateRepository.findSelectedDateByLocalDate(selectedDate);
    }

    @Override
    public SelectedDate findSelectedDateById(Long id) {
        return selectedDateRepository.findSelectedDateById(id);
    }

    @Override
    public void removeById(Long id) {
        selectedDateRepository.removeById(id);
    }

    @Override
    public void save(SelectedDate selectedDate) {
        selectedDateRepository.save(selectedDate);
    }

    @Override
    public void saveAndFlush(SelectedDate selectedDate) {
        selectedDateRepository.save(selectedDate);
    }

    @Override
    public List<SelectedDate> findAll() {
        return selectedDateRepository.findAll();
    }

    @Override
    public void saveAll(List<SelectedDate> selectedDates) {
        selectedDateRepository.saveAll(selectedDates);
    }

    @Override
    public void deleteAll(List<SelectedDate> selectedDates) {
        selectedDateRepository.deleteAll(selectedDates);
    }

    @Override
    public void removeByLocalDate(LocalDate localDate) {
        selectedDateRepository.removeByLocalDate(localDate);
    }
}

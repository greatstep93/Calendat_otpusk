package ru.greatstep.spring_sqlite.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.greatstep.spring_sqlite.models.SelectedDate;

import java.util.List;

@Repository
public interface SelectedDatesRepository extends JpaRepository<SelectedDate, Long> {

    SelectedDate findByDate(String date);

    SelectedDate findSelectedDateById(Long id);

    @Query(value = "select date from SelectedDate")
    String[] findAllDates();
}

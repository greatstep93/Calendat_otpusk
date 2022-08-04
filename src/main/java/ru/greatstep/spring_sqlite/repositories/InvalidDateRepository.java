package ru.greatstep.spring_sqlite.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.greatstep.spring_sqlite.models.InvalidDate;

@Repository
public interface InvalidDateRepository extends JpaRepository<InvalidDate, Long> {

    InvalidDate findByDate(String date);

    InvalidDate findInvalidDateById(Long id);

    @Query(value = "select date from InvalidDate")
    String[] findAllDates();

    boolean existsInvalidDateByDate(String date);

    int countInvalidDateByDate(String date);

    @Transactional
    @Modifying
    @Query("delete from InvalidDate i where i.date = :date")
    void deleteByDate(@Param("date") String date);
}

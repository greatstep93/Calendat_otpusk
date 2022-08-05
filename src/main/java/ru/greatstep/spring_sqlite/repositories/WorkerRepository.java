package ru.greatstep.spring_sqlite.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.greatstep.spring_sqlite.models.Worker;

@Repository
public interface WorkerRepository extends JpaRepository<Worker, Long> {
}

package ru.greatstep.spring_sqlite.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.greatstep.spring_sqlite.models.Worker;
import ru.greatstep.spring_sqlite.repositories.WorkerRepository;
import ru.greatstep.spring_sqlite.service.absctract.WorkerService;

@Service
public class WorkerServiceImpl implements WorkerService {

    private WorkerRepository workerRepository;

    @Autowired
    public WorkerServiceImpl(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }

    @Override
    public void save(Worker worker) {
        workerRepository.save(worker);
    }
}

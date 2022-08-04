package ru.greatstep.spring_sqlite.service.absctract;

import ru.greatstep.spring_sqlite.models.User;
import java.util.List;

public interface UserService {


    List<User> findAll();

    User findByFullName(String fullName);

    User findUserById(long id);

    void save(User user);

    void saveAndFlush(User user);

    void deleteById(long id);
    void initializationTestUsers();





}

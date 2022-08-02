package ru.greatstep.spring_sqlite.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.greatstep.spring_sqlite.models.User;
import ru.greatstep.spring_sqlite.repositories.UserRepository;
import java.util.List;


@Service
public class UserServiceImp implements UserService {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {this.userRepository = userRepository;}

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
    @Override
    public User findByFullName(String fullName){
        return userRepository.findByFullName(fullName);
    }

    @Override
    public User findUserById(long id) {
        return userRepository.findUserById(id);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void saveAndFlush(User user) {
        userRepository.saveAndFlush(user);
    }

    @Override
    public void deleteById(long id) {
        userRepository.deleteById(id);
    }

}

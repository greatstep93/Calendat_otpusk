package ru.greatstep.spring_sqlite.service;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.greatstep.spring_sqlite.models.Role;
import ru.greatstep.spring_sqlite.models.User;
import ru.greatstep.spring_sqlite.repositories.RoleRepository;
import ru.greatstep.spring_sqlite.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserServiceImp implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    @Autowired
    public void setUserRepository(UserRepository userRepository) {this.userRepository = userRepository;}
    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {this.roleRepository = roleRepository;}




    @Transactional
    public User loadUserByUsername(String username) throws Exception {
        User user = findByUsername(username);
        if(user == null){
            throw new Exception("User not found");
        }

        return user;
    }



    public void addDefaultAdmin(){
        User admin = new User("Dmitriy","Tkachenko");
        admin.setUsername("admin@mail.ru");
        admin.setPassword("$2a$12$SOnZ9kd8ptoQbrTc6whqU.t/gtkmlJe3fNeWE6htnNmNgberD8I4S"); // admin
        admin.setAge(29);
        List<Role> rolesList = new ArrayList<>();

        Role roleOne = new Role();
        roleOne.setName("ROLE_ADMIN");
        roleRepository.save(roleOne);
        rolesList.add(roleOne);

        Role roleTwo = new Role();
        roleTwo.setName("ROLE_USER");
        roleRepository.save(roleTwo);
        rolesList.add(roleTwo);


        admin.setRoles(rolesList);

        userRepository.save(admin);

    }


    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
    @Override
    public User findByUsername(String username){
        return userRepository.findByUsername(username);
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

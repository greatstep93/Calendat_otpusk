package ru.greatstep.spring_sqlite.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.greatstep.spring_sqlite.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByFullName(String fullName);

    User findUserById(Long id);

}

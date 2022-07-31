package ru.greatstep.spring_sqlite.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.greatstep.spring_sqlite.models.Role;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}

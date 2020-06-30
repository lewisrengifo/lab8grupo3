package sw2.lab6.teletok.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sw2.lab6.teletok.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
}

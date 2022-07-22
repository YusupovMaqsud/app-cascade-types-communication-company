package uz.pdp.appcascadetypescommunicationcompany.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appcascadetypescommunicationcompany.entity.Role;
import uz.pdp.appcascadetypescommunicationcompany.enums.RoleEnum;

import java.util.Collection;
import java.util.Set;

public interface RoleRepository extends JpaRepository<Role,Integer> {
    Role findByRoleEnum(RoleEnum roleEnum);
    Set<Role> findAllByIdIn(Collection<Integer> id);
}

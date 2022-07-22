package uz.pdp.appcascadetypescommunicationcompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appcascadetypescommunicationcompany.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndEmailCode(String email, Integer emailCode);

    List<User> findAllByCompanyId(UUID company_id);
}

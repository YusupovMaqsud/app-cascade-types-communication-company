package uz.pdp.appcascadetypescommunicationcompany.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.appcascadetypescommunicationcompany.entity.Simcard;
import uz.pdp.appcascadetypescommunicationcompany.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RepositoryRestResource(path = "simcard")
public interface SimcardRepository extends JpaRepository<Simcard, UUID> {
    Optional<Simcard> findBySimCardBackNumber(String simCardBackNumber);

    Optional<Simcard> findByClient(User client);

    Optional<Simcard> findByClientId(UUID client_id);

    Simcard getByClientId(UUID client_id);

    @Query(value = "insert into simcard_current_package(simcard_id, current_package_id) " +
            "VALUES (?1,?2)", nativeQuery = true)
    void insertPackage(Integer packageId, UUID simcardId);



}

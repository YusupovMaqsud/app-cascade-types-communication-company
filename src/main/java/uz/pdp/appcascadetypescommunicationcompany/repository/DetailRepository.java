package uz.pdp.appcascadetypescommunicationcompany.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.appcascadetypescommunicationcompany.entity.Detail;

import java.util.UUID;
@RepositoryRestResource(path = "detail")
public interface DetailRepository extends JpaRepository<Detail, UUID> {
}

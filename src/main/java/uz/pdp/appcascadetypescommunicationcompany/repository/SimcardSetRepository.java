package uz.pdp.appcascadetypescommunicationcompany.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.appcascadetypescommunicationcompany.entity.Simcard;
import uz.pdp.appcascadetypescommunicationcompany.entity.SimcardSet;
import uz.pdp.appcascadetypescommunicationcompany.entity.User;

import java.util.Optional;
import java.util.UUID;

@RepositoryRestResource(path = "simcardset")
public interface SimcardSetRepository extends JpaRepository<SimcardSet, UUID> {

}

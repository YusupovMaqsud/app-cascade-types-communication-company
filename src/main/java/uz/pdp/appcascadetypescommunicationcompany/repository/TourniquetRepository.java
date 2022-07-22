package uz.pdp.appcascadetypescommunicationcompany.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.appcascadetypescommunicationcompany.entity.SimcardSet;
import uz.pdp.appcascadetypescommunicationcompany.entity.Tourniquet;

import java.util.UUID;

@RepositoryRestResource(path = "tourniquet")
public interface TourniquetRepository extends JpaRepository<Tourniquet, UUID> {

}

package uz.pdp.appcascadetypescommunicationcompany.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.appcascadetypescommunicationcompany.entity.Filial;
import uz.pdp.appcascadetypescommunicationcompany.entity.Region;

@RepositoryRestResource(path = "region")
public interface RegionRepository extends JpaRepository<Region,Integer> {
}

package uz.pdp.appcascadetypescommunicationcompany.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.appcascadetypescommunicationcompany.entity.Filial;

@RepositoryRestResource(path = "filial")
public interface FilialRepository extends JpaRepository<Filial,Integer> {
}

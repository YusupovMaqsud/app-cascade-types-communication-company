package uz.pdp.appcascadetypescommunicationcompany.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.appcascadetypescommunicationcompany.entity.Tariff;
import uz.pdp.appcascadetypescommunicationcompany.entity.Ussd;

@RepositoryRestResource(path = "ussd")
public interface UssdRepository extends JpaRepository<Ussd, Integer> {

}

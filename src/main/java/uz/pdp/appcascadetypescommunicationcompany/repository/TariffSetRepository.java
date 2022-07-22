package uz.pdp.appcascadetypescommunicationcompany.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.appcascadetypescommunicationcompany.entity.Tariff;
import uz.pdp.appcascadetypescommunicationcompany.entity.TariffSet;

@RepositoryRestResource(path = "tariffset")
public interface TariffSetRepository extends JpaRepository<TariffSet, Integer> {

}

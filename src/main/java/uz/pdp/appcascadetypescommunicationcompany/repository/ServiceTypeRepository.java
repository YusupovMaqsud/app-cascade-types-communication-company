package uz.pdp.appcascadetypescommunicationcompany.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appcascadetypescommunicationcompany.entity.PurchaseType;
import uz.pdp.appcascadetypescommunicationcompany.entity.ServiceType;

public interface ServiceTypeRepository extends JpaRepository<ServiceType, Integer> {

}

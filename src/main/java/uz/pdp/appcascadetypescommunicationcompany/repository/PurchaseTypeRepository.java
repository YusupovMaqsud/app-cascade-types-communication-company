package uz.pdp.appcascadetypescommunicationcompany.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appcascadetypescommunicationcompany.entity.PaymentType;
import uz.pdp.appcascadetypescommunicationcompany.entity.PurchaseType;
import uz.pdp.appcascadetypescommunicationcompany.enums.PaymentTypeEnum;

public interface PurchaseTypeRepository extends JpaRepository<PurchaseType, Integer> {

}

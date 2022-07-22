package uz.pdp.appcascadetypescommunicationcompany.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appcascadetypescommunicationcompany.entity.PaymentType;
import uz.pdp.appcascadetypescommunicationcompany.enums.PaymentTypeEnum;

public interface PaymentTypeRepository extends JpaRepository<PaymentType, Integer> {
    PaymentType findByPaymentTypeName(PaymentTypeEnum paymentTypeEnum);
}

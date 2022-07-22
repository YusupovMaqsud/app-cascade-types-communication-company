package uz.pdp.appcascadetypescommunicationcompany.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.appcascadetypescommunicationcompany.enums.PurchaseTypeEnum;
import uz.pdp.appcascadetypescommunicationcompany.enums.ServiceTypeEnum;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PurchaseType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private PurchaseTypeEnum purchaseTypeEnum;

    private Integer durationDays;
}

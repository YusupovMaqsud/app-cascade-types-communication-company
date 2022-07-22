package uz.pdp.appcascadetypescommunicationcompany.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimcardDto {
    @NotNull
    private String companyCode;

    @NotNull
    private String number;

    private Double balance = 0.0;

    private boolean status = true;

    @NotNull
    private UUID clientId;

    @NotNull
    private Integer tariffId;

    private Integer paymentTypeId;

    private Double price;
}

package uz.pdp.appcascadetypescommunicationcompany.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {

    @NotNull
    @Size(min = 3,max = 50)
    private String firstname;

    @NotNull
    @Length(min = 3,max = 50)
    private String lastname;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String password;
}

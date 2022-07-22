package uz.pdp.appcascadetypescommunicationcompany.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import uz.pdp.appcascadetypescommunicationcompany.enums.RoleEnum;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; //lavozimi(Role) chegaralanganligi uchun Integer tipida

    @Enumerated(EnumType.STRING) //mana shuni yozsak databaseda string sifatida fazifa bajaradi (default holatda ORDENAL yani number)
    private RoleEnum roleName;

    @Override
    public String getAuthority() {
        return roleName.name(); //.name string qaytaradi  (User return this.roles uchun)
    }
}

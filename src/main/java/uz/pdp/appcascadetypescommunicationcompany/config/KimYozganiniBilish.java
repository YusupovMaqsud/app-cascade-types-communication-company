package uz.pdp.appcascadetypescommunicationcompany.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import uz.pdp.appcascadetypescommunicationcompany.entity.User;

import java.util.Optional;
import java.util.UUID;

public class KimYozganiniBilish implements AuditorAware<UUID> {
    @Override
    public Optional<UUID> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null &&  //null teng bo'lmasin
                authentication.isAuthenticated() && //sistemaga kirib turgan bo'lsin yani true bo'lsin
                !authentication.equals("anonymousUser")) {    //anonimUser bo'lmasin
            User user = (User) authentication.getPrincipal();
            return Optional.of(user.getId()); //userni idsini qaytaradi
        }
        return Optional.empty(); //Bo'sh qaytaradi
    }
}

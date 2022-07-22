package uz.pdp.appcascadetypescommunicationcompany.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.appcascadetypescommunicationcompany.entity.Company;
import uz.pdp.appcascadetypescommunicationcompany.entity.User;
import uz.pdp.appcascadetypescommunicationcompany.enums.RoleEnum;
import uz.pdp.appcascadetypescommunicationcompany.payload.ApiResponse;
import uz.pdp.appcascadetypescommunicationcompany.payload.UserDto;
import uz.pdp.appcascadetypescommunicationcompany.payload.LoginDto;
import uz.pdp.appcascadetypescommunicationcompany.repository.CompanyRepository;
import uz.pdp.appcascadetypescommunicationcompany.repository.UserRepository;
import uz.pdp.appcascadetypescommunicationcompany.repository.RoleRepository;
import uz.pdp.appcascadetypescommunicationcompany.security.JwtProvider;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService implements UserDetailsService {
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    CompanyRepository companyRepository;



    public ApiResponse addDirector(UserDto employeeDto){
        boolean existsByEmail = userRepository.existsByEmail(employeeDto.getEmail());
        if (existsByEmail) {
            return new ApiResponse("Bunday email allaqachon mavjud", false);
        }


        Optional<Company> optionalCompany = companyRepository.findById(employeeDto.getCompanyId());
        if(!optionalCompany.isPresent()){
            return new ApiResponse("Kompaniya topilmadi", false);
        }

        User user=new User();
        user.setRoles(Collections.singleton(roleRepository.findByRoleEnum(RoleEnum.ROLE_DIRECTOR)));
        user.setCompany(optionalCompany.get());
        userRepository.save(user);

        sendEmail(user.getEmail(), user.getEmailCode());

        return new ApiResponse("Director saqlandi", true);
    }




    public ApiResponse addManager(UserDto employeeDto){
        Boolean exists = userRepository.existsByEmail(employeeDto.getEmail());
        if (exists)
            return new ApiResponse("Employee has already exists", false);
        User user=new User();
        user.setRoles(Collections.singleton(roleRepository.findByRoleEnum(RoleEnum.ROLE_FILIAL_MANAGER)));
        user.setRoles(Collections.singleton(roleRepository.findByRoleEnum(RoleEnum.ROLE_NUMBERS_MANAGER)));
        user.setRoles(Collections.singleton(roleRepository.findByRoleEnum(RoleEnum.ROLE_PERSONNEL_MANAGER)));
        userRepository.save(user);

        sendEmail(user.getEmail(), user.getEmailCode());

        return new ApiResponse("Manager saqlandi", true);
    }





    public ApiResponse addWorker(UserDto userDto){
        Boolean exists = userRepository.existsByEmail(userDto.getEmail());
        if (exists)
            return new ApiResponse("Worker allaqachon mavjud", false);
        User user=new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmailCode(UUID.randomUUID().toString());
        user.setRoles(Collections.singleton(roleRepository.findByRoleEnum(RoleEnum.ROLE_WORKER)));
        userRepository.save(user);

        sendEmail(user.getEmail(), user.getEmailCode());

        return new ApiResponse("Worker saqlandi", true);
    }





    public User createUser(UserDto dto){
        User user=new User();
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmailCode(UUID.randomUUID().toString());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication!=null
                &&authentication.isAuthenticated()
                &&!authentication.getPrincipal().equals("anonymousUser")){
            User authUser = (User) authentication.getPrincipal();
            user.setCompany(authUser.getCompany());
        }
        return user;
    }





    public List<User> getWorkeres() {
        return userRepository.findAll();
    }



    public ApiResponse getWorker(UUID id){
        Optional<User> optionalEmployee = userRepository.findById(id);
        if(!optionalEmployee.isPresent())
            return new ApiResponse("Bunday employee yoq", false);
        User user = optionalEmployee.get();
        user.setEnabled(false);
        userRepository.save(user);
        return new ApiResponse("Employee mavjud", true);
    }



    public ApiResponse deleteWorker(UUID id) {
        userRepository.deleteById(id);
        return new  ApiResponse("Worker Ochirildi",true);

    }



    public ApiResponse editWorker(UserDto userDto, UUID id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());
            user.setEmail(userDto.getEmail());
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            user.setEmailCode(UUID.randomUUID().toString());
            user.setRoles(Collections.singleton(roleRepository.findByRoleEnum(RoleEnum.ROLE_WORKER)));
            userRepository.save(user);

            sendEmail(user.getEmail(), user.getEmailCode());

            return new ApiResponse("Worker edited", true);
        }
        return null;
    }



    public boolean sendEmail(String sendingEmail, String emailCode) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("Test@pdp.com"); //kimdan kelganligi
            mailMessage.setTo(sendingEmail);
            mailMessage.setSubject("Accauntni tasdiqlash"); //tekst
            mailMessage.setText("<a href='http://localhost:8080/api/auth/verifyEmail?emailCode="
                    + emailCode + "+&email=" + sendingEmail + "'>Tasdiqlang</a>");//tasdiqlashni bossa shu yulga
            //http://localhost:8080/api/auth/verifyEmail?emailCode=2666511111g8kkk&email=Test@Pdp.com mana shunday bo'ladi
            javaMailSender.send(mailMessage);
            return true;
        } catch (Exception e) {
            return false;
        }
    }





    public ApiResponse verifyEmail(String sendingEmail, Integer emailCode, HttpServletRequest request){
        Optional<User> optionalEmployee = userRepository.findByEmailAndEmailCode(sendingEmail, emailCode);
        if (optionalEmployee.isPresent()){
            User user = optionalEmployee.get();
            user.setEnabled(true);
            user.setPassword(passwordEncoder.encode(request.getParameter("password")));
            user.setEmailCode(null);
            userRepository.save(user);
            return new ApiResponse("User saqlandi", true);
        }
        return new ApiResponse("Account tasdiqlanmadi", false);
    }




    public ApiResponse login(LoginDto loginDto){
        try{
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()
                    ));
              User user = (User) authenticate.getPrincipal();
            String token = jwtProvider.generateToken(loginDto.getEmail(), user.getRoles());

            return new ApiResponse("Token", true, token);

        }catch (BadCredentialsException badCredentialsException){
            return new ApiResponse("Parol yoki Login xato!", false);
        }
    }




    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(username);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        throw new UsernameNotFoundException(username + "topilmadi");
        //yoki
        //return userRepository.findByEmail(username).orElseThrow(()->new UsernameNotFoundException(username+"topilmadi"))
    }



}

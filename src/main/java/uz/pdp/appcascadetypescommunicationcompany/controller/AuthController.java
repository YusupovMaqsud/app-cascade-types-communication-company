package uz.pdp.appcascadetypescommunicationcompany.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appcascadetypescommunicationcompany.entity.User;
import uz.pdp.appcascadetypescommunicationcompany.payload.ApiResponse;
import uz.pdp.appcascadetypescommunicationcompany.payload.UserDto;
import uz.pdp.appcascadetypescommunicationcompany.payload.LoginDto;
import uz.pdp.appcascadetypescommunicationcompany.service.AuthService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthService authService;


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addDirector")
    public HttpEntity<?> addDirector(@Valid @RequestBody UserDto userDto){
        ApiResponse apiResponse = authService.addDirector(userDto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }




    @PreAuthorize("hasRole('DIRECTOR')")
    @PostMapping("/addManager")
    public HttpEntity<?> addManager(@Valid @RequestBody UserDto userDto){
        ApiResponse apiResponse = authService.addManager(userDto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }




    @PreAuthorize("hasAnyRole('DIRECTOR', 'FILIAL_DIRECTOR','FILIAL_MANAGER')")
    @PostMapping("/addWorker")
    public HttpEntity<?> addWorker(@Valid @RequestBody UserDto userDto){
        ApiResponse apiResponse = authService.addWorker(userDto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }



    @PreAuthorize("hasAnyRole('DIRECTOR', 'FILIAL_DIRECTOR','FILIAL_MANAGER','WORKER')")
    @GetMapping("/worker/{id}")
    public HttpEntity<?> getWorker(@PathVariable UUID id){
        ApiResponse apiResponse = authService.getWorker(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }


    @PreAuthorize("hasAnyRole('DIRECTOR', 'FILIAL_DIRECTOR','FILIAL_MANAGER')")
    @GetMapping("/worker")
    public HttpEntity<List<User>> getWorkeres(){
        List<User> userList = authService.getWorkeres();

        return ResponseEntity.ok(userList);
    }



    @PreAuthorize("hasAnyRole('DIRECTOR', 'FILIAL_DIRECTOR','FILIAL_MANAGER')")
    @GetMapping("/worker/{id}")
    public HttpEntity<?> deleteWorker(@PathVariable UUID id){
        ApiResponse apiResponse = authService.deleteWorker(id);
        return ResponseEntity.ok(apiResponse);
    }




    @PreAuthorize("hasAnyRole('DIRECTOR', 'FILIAL_DIRECTOR','FILIAL_MANAGER','WORKER')")
    @PostMapping("/Worker/{id}")
    public HttpEntity<?> editWorker(@Valid @RequestBody UserDto userDto, @PathVariable UUID id){
        ApiResponse apiResponse = authService.editWorker(userDto,id);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }


    @PostMapping("/verify")
    public HttpEntity<?> verifyEmail(@RequestParam Integer emailCode, @RequestParam String sendingEmail, HttpServletRequest request){
        ApiResponse apiResponse = authService.verifyEmail(sendingEmail, emailCode, request);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }



    @PostMapping("/login")
    public HttpEntity<?> login(@RequestBody LoginDto loginDto){
        ApiResponse login = authService.login(loginDto);
        return ResponseEntity.status(login.isSuccess()?200:401).body(login);
    }
}

package uz.pdp.appcascadetypescommunicationcompany.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.appcascadetypescommunicationcompany.payload.ApiResponse;
import uz.pdp.appcascadetypescommunicationcompany.service.ClientService;

@RestController
@RequestMapping("/api/client")
public class ClientController {
    @Autowired
    ClientService clientService;

    @GetMapping("/checkBalance")
    public HttpEntity<?> checkBalance() {
        ApiResponse response = clientService.checkBalance();
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @GetMapping("/sentMessage")
    public HttpEntity<?> sentMessage(@RequestParam Integer numberOfSms) {
        ApiResponse response = clientService.sendMessage(numberOfSms);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @GetMapping("/call")
    public HttpEntity<?> call(@RequestParam Integer minutes) {
        ApiResponse response = clientService.call(minutes);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @GetMapping("/useInternet")
    public HttpEntity<?> useInternet(@RequestParam Double amountOfMb) {
        ApiResponse response = clientService.useInternet(amountOfMb);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

}

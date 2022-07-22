package uz.pdp.appcascadetypescommunicationcompany.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.appcascadetypescommunicationcompany.entity.Tariff;
import uz.pdp.appcascadetypescommunicationcompany.entity.Ussd;
import uz.pdp.appcascadetypescommunicationcompany.payload.ApiResponse;
import uz.pdp.appcascadetypescommunicationcompany.service.UssdService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/ussd")
public class UssdController {
    @Autowired
    UssdService ussdService;


    @GetMapping
    public HttpEntity<?> getAllUssdCodes() {
        List<Ussd> allUssdCodes = ussdService.getAllUssdCodes();
        return ResponseEntity.ok(allUssdCodes);
    }

    @GetMapping("/tariffByClientId")
    public HttpEntity<?> getTariffByUserId(@RequestParam String userId) {
        Tariff tariff = ussdService.getTariffByUserId(UUID.fromString(userId));
        return ResponseEntity.ok(tariff);
    }


    @GetMapping("/changeTariff")
    public HttpEntity<?> changeTariff(@RequestParam String simcardId, @RequestParam Integer tariffId) {
        ApiResponse response = ussdService.changeTariff(tariffId, UUID.fromString(simcardId));
        return ResponseEntity.status(response.isSuccess() ? 202 : 409).body(response);
    }



    @GetMapping("/buyExtraService")
    public HttpEntity<?> buyExtraService(@RequestParam String simcardId, @RequestParam Integer serviceId) {
        ApiResponse response = ussdService.buyExtraService(serviceId, UUID.fromString(simcardId));
        return ResponseEntity.status(response.isSuccess() ? 202 : 409).body(response);
    }
}

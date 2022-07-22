package uz.pdp.appcascadetypescommunicationcompany.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appcascadetypescommunicationcompany.payload.ApiResponse;
import uz.pdp.appcascadetypescommunicationcompany.payload.SimcardDto;
import uz.pdp.appcascadetypescommunicationcompany.repository.IncomeRepository;
import uz.pdp.appcascadetypescommunicationcompany.service.FilialService;
import uz.pdp.appcascadetypescommunicationcompany.service.IncomeService;

@RestController
@RequestMapping("/api/info")
public class InfoController {
    @Autowired
    FilialService filialService;
    @Autowired
    IncomeService incomeService;

    @PostMapping("/buySimcard")
    public HttpEntity<?> buySimcard(@RequestBody SimcardDto simcardDto) {
        ApiResponse response = filialService.buySimcard(simcardDto);
        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }



    @GetMapping("/getIncomesByDaily")
    public HttpEntity<?> getIncomesByDaily() {
        ApiResponse response = incomeService.getIncomesByDaily();
        return ResponseEntity.status(response.isSuccess() ? 200 : 403).body(response);
    }

    @GetMapping("/getIncomesByMonthly")
    public HttpEntity<?> getIncomesByMonthly() {
        ApiResponse response = incomeService.getIncomesByMonthly();
        return ResponseEntity.status(response.isSuccess() ? 200 : 403).body(response);
    }
}

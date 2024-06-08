package com.example.Project1.Controller;

import com.example.Project1.Service.NumberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/numbers")
public class AverageCalculatorController {

    private final NumberService numberService;

    public AverageCalculatorController(NumberService numberService) {
        this.numberService = numberService;
    }

    @GetMapping("/{numberId}")
    public ResponseEntity<AverageResponse> getNumbers(@PathVariable String numberId) {
        try {
            AverageResponse response = numberService.getNumbers(numberId);
            return ResponseEntity.ok(response);
        } catch (TimeoutException e) {
            return ResponseEntity.status(504).build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}

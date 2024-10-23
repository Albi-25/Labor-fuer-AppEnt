package com.example.books.v1;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/v1")
public class ResultController {

    @PostMapping("/result")
    public ResponseEntity<?> submitResult(@RequestBody Map<String, Object> result) {
        // Ergebnisse verarbeiten
        System.out.println("Ergebnis erhalten: " + result);
        return ResponseEntity.ok("Ergebnis erfolgreich erhalten");
    }
}

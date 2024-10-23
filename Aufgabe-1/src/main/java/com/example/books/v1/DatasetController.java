package com.example.books.v1;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/v1")
public class DatasetController {

    @GetMapping("/dataset")
    public ResponseEntity<?> getDataset() {
        // Beispiel: JSON-Daten als Antwort zurückgeben
        String dataset = "{\"events\": [{\"customerId\": \"123\", \"timestamp\": 1625247600000, \"eventType\": \"start\"}]}";
        return ResponseEntity.ok(dataset);
    }
}
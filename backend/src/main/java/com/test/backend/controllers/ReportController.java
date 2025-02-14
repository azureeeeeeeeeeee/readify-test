package com.test.backend.controllers;

import com.test.backend.DTO.JsonResponse;
import com.test.backend.models.Report;
import com.test.backend.services.ReportServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/report")
public class ReportController {
    private final ReportServices reportServices;

    public ReportController(ReportServices reportServices) {
        this.reportServices = reportServices;
    }

    @PostMapping("/{id}")
    public ResponseEntity<JsonResponse<Object>> createReport(@PathVariable Integer id, @RequestBody Report report) {
        return reportServices.createReport(id, report);
    }

    @GetMapping
    public ResponseEntity<JsonResponse<Object>> getAllReport() {
        return reportServices.getAllReport();
    }

    @PutMapping("/{id}")
    public ResponseEntity<JsonResponse<Object>> resolveReport(@PathVariable Integer id) {
        return reportServices.resolvedReport(id);
    }
}

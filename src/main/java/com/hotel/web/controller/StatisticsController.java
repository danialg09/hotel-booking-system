package com.hotel.web.controller;

import com.hotel.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;

@RestController
@RequiredArgsConstructor
@RequestMapping("/statistics")
public class StatisticsController {

    private final StatisticsService service;

    @GetMapping("/csv")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Resource> downloadCsv() throws Exception {
        ByteArrayInputStream csvStream = service.generateCsv();
        InputStreamResource file = new InputStreamResource(csvStream);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=statistics.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(file);
    }
}

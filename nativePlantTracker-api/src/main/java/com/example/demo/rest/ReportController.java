package com.example.demo.rest;


import com.example.demo.dto.GardenReportDto;
import com.example.demo.dto.PlantReportDTO;
import com.example.demo.services.GardenService;
import com.example.demo.services.PlantService;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    final ApplicationContext context;
    final GardenService gardenService;
    final PlantService plantService;

    @Autowired
    public ReportController(ApplicationContext context,
                            GardenService gardenService,
                            PlantService plantService) {
        this.context = context;
        this.gardenService = gardenService;
        this.plantService = plantService;
    }

    // 11. GET a generated plant report object for the user
    @GetMapping("/plant")
    public ResponseEntity<List<PlantReportDTO>> getPlantReport(Authentication authentication) {
        String currentUsername = authentication.getName();
        List<PlantReportDTO> report = gardenService.getPlantReport(currentUsername);
        return ResponseEntity.ok(report);
    }

    // 12. GET a generated garden report object for the user
    @GetMapping("/garden")
    public ResponseEntity<List<GardenReportDto>> getGardenReport(Authentication authentication) {
        String currentUsername = authentication.getName();
        List<GardenReportDto> report = gardenService.getGardenReport(currentUsername);
        return ResponseEntity.ok(report);
    }
}

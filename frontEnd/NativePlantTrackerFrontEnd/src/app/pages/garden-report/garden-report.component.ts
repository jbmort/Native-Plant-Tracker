  import { Component, OnInit } from '@angular/core';
import { CommonModule, NgFor } from '@angular/common'; // Import CommonModule
import { GardenReport } from '../../models/garden-report'; // Import your new interface
import { GardenService } from '../../services/garden.service'; // You can reuse your GardenService


@Component({
  selector: 'app-garden-report',
  standalone: true,
  imports: [CommonModule, NgFor],
  templateUrl: './garden-report.component.html',
  styleUrls: ['./garden-report.component.css']
})
export class GardenReportComponent implements OnInit {

  gardenReport: GardenReport[] = [];
  errorMessage: string | null = null;
  isLoading = true; 

  constructor(private gardenService: GardenService) {}

  ngOnInit(): void {
    this.loadGardenReport();
  }

  loadGardenReport(): void {
    this.isLoading = true;
    this.gardenService.getGardenReport().subscribe({
      next: (report) => {
        this.errorMessage = null;
        this.gardenReport = report;
        this.isLoading = false;
        console.log(this.gardenReport)
      },
      error: (err) => {
        console.error("Error loading garden report: ", err);
        this.errorMessage = "Error loading garden report. Please try again later.";
        this.isLoading = false;
      }
    });
  }
}



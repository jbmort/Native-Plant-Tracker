import { Component, OnInit } from '@angular/core';
import { PlantReport } from '../../models/plant-report';
import { GardenService } from '../../services/garden.service';
import { NgFor, NgIf } from '@angular/common';

@Component({
  selector: 'app-plant-report',
  standalone: true,
  imports: [NgIf, NgFor],
  templateUrl: './plant-report.component.html',
  styleUrl: './plant-report.component.css'
})
export class PlantReportComponent implements OnInit  {
  constructor(private gardenService: GardenService){}


  ngOnInit(): void {
    this.loadPlantReport();
  }


  plantReport: PlantReport[] = [];

  errorMessage: String | null = null;
  isLoading = true;


  loadPlantReport() {
    this.isLoading = true;

    this.gardenService.getPlantReport().subscribe({
      next: (report) => {
        this.errorMessage = null;
        this.plantReport = report;
        this.isLoading = false;
      },
      error: (err) => {
        console.log("error loading report: " + err)
        this.errorMessage = "Error loading plant report."
        this.isLoading = false;
      }
    })
  }

  // TODO: implement write report to XLSX file 
  // must determine an adeqate module for angular 17 support for XLSX files


}


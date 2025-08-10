import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { PlantDto } from '../../models/plant-dto';
import { GardenService } from '../../services/garden.service'; 
import { Plant } from '../../models/plant';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-add-plant-modal',
  standalone: true,
  imports: [NgIf, ReactiveFormsModule],
  templateUrl: './add-plant-modal.component.html',
  styleUrl: './add-plant-modal.component.css'
})

export class AddPlantModalComponent implements OnInit {
  plantForm!: FormGroup;
  errorMessage: string | null = null;
  
  // This will be passed in from the GardenDetailComponent
  @Input() gardenId!: number;
  @Input() plant: Plant | null = null;
  
  // To notify the parent component that a plant was added
  @Output() plantAdded = new EventEmitter<void>();

  isLoadingPlants = true; // For a loading indicator on the dropdown

  constructor(
    public activeModal: NgbActiveModal,
    private fb: FormBuilder,
    private gardenService: GardenService
  ) {

  }

  ngOnInit(): void {


    if (this.plant) {
      // EDIT MODE: Populate form with existing plant data
      this.plantForm = this.fb.group({
        plantID: [this.plant.id], 
        name: [this.plant.commonName, [Validators.required]],
        sciName: [this.plant.sciName],
        description: [this.plant.description]
      });
    } else {
      // ADD MODE: Create an empty form
      this.plantForm = this.fb.group({
        plantID: [null],
        name: ['', [Validators.required]],
        sciName: [''],
        description: ['']
      });

    }
  }

  onSubmit(): void {
    if (this.plantForm.invalid) {
      return;
    }
    
    this.errorMessage = null;
    if(this.plantForm.value.plantID === null){
    const plantData: PlantDto = {
        common_name: this.plantForm.value.name,
        sci_name: this.plantForm.value.sciName,
        description: this.plantForm.value.description
    } 
    console.log(plantData)

    this.gardenService.addPlantToGarden(this.gardenId, plantData).subscribe({
      next: (newPlant) => {
        console.log('Plant added successfully:', newPlant);
        this.plantAdded.emit();
        this.activeModal.close('Plant Added');
      },
      error: (err) => {
        console.error('Error adding plant:', err);
        this.errorMessage = 'Failed to add plant. Please try again.';
      }
    });
  }
  else if(this.plantForm.value.plantID > 0){
    
     const plantData: PlantDto = {
        common_name: this.plantForm.value.name,
        sci_name: this.plantForm.value.sciName,
        description: this.plantForm.value.description
    } 
        console.log(plantData)


    if(this.plant != null)
    this.gardenService.updatePlant(this.gardenId, this.plant.id, plantData).subscribe({
      next: (newPlant) => {
        // console.log('Plant updated successfully:', newPlant);
        this.plantAdded.emit();
        this.activeModal.close('Plant Updated');
      },
      error: (err) => {
        console.error('Error adding plant:', err);
        this.errorMessage = 'Failed to add plant. Please try again.';
      }

    })
  }
  }
}


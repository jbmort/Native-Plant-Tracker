import { NgFor, NgIf } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { GardenService } from '../../services/garden.service';
import { PlantSearchDto } from '../../models/plant-search-dto';
import { ApiService } from '../../services/api.service';
import { switchMap } from 'rxjs';



@Component({
  selector: 'app-add-plant-api-modal',
  standalone: true,
  imports: [NgIf, NgFor, ReactiveFormsModule],
  templateUrl: './add-plant-api-modal.component.html',
  styleUrl: './add-plant-api-modal.component.css'
})
export class AddPlantApiModalComponent implements OnInit {

  plantForm!: FormGroup;
  dateForm!: FormGroup;
  errorMessage: string | null = null;
  plantData: Array<PlantSearchDto> = [];
  searchPerformed: boolean = false;

// This will be passed in from the GardenDetailComponent
  @Input() gardenId: Number = 0;
  @Input() Ids: Set<Number> = new Set();
  
  // To notify the parent component that a plant was added
  @Output() plantAdded = new EventEmitter<void>();

  isLoadingPlants = false; 
  selectedPlant: PlantSearchDto | null = null;

  constructor(
      public activeModal: NgbActiveModal,
      private fb: FormBuilder,
      private gardenService: GardenService,
      private apiService: ApiService
    ) {}

  ngOnInit(): void {
    this.plantForm = this.fb.group({
      name: [''],
    });

    this.dateForm = this.fb.group({
      todayCheckbox: [false],
      datePlanted: [''],
    });
  }
isChecked(): boolean {
  return this.dateForm.get('todayCheckbox')?.value;
}

onSubmit() {
  let today: Date;
  today = new Date();
  if(this.dateForm.get('todayCheckbox')?.value == false && !this.dateForm.get('datePlanted')?.value) {
    this.errorMessage = 'Please select a date or choose Today.';
    return;
  }
  let plantedDate: Date = new Date(this.dateForm.get('datePlanted')?.value); 
  if(plantedDate > today) {
    this.errorMessage = 'The planted date cannot be in the future.';
    return;
  }
  this.errorMessage = null;

  if(this.selectedPlant) {
    const plantToAdd = {
      plantId: this.selectedPlant.externalId,
      datePlanted: (this.dateForm.get('todayCheckbox')?.value ? today : plantedDate).toISOString().split('T')[0]
    };
    console.log(this.gardenId, plantToAdd.plantId, plantToAdd.datePlanted);

    // Call the service to add the plant to the garden
  this.gardenService.addPlantToGarden(plantToAdd.plantId, this.gardenId, plantToAdd.datePlanted).subscribe({
      next: () => {
        this.plantAdded.emit(); // Notify parent component
        this.activeModal.close('Plant Added');
      },
      error: (error) => {
        console.error('Error adding plant to garden:', error);
        this.errorMessage = 'An error occurred while adding the plant. Please try again later.';
      }
    });
  }
}

searchForPlant() {
  const plantName = this.plantForm.get('name')?.value;

  if(!plantName || plantName.trim() === '' || plantName.length < 3 || !this.isValidPlantName(plantName.trim())) {
    this.errorMessage = 'Please enter a valid plant name (at least 3 characters).';
    return;
  }
  else {
    this.errorMessage = null;
  }
  this.isLoadingPlants = true;

  this.apiService.searchPlantsByName(plantName.trim()).subscribe({
    next: (data: PlantSearchDto[]) => {
      console.log('Plant search results:', data);
      this.plantData = data;
      this.isLoadingPlants = false;
      this.searchPerformed = true;

    },
    error: (error) => {
      console.error('Error fetching plant data:', error);
      this.errorMessage = 'An error occurred while searching for plants. Please try again later.';
      this.isLoadingPlants = false;
      this.searchPerformed = true;
    }
});


}

selectPlant(plant: PlantSearchDto) {
  if(this.Ids.has(plant.externalId)) {
    alert('This plant is already in your garden.');
    return;
  }
  this.errorMessage = null;
  this.selectedPlant = plant;
}

backToSearch() {
  this.selectedPlant = null;
  this.errorMessage = null;
  this.dateForm.reset({ todayCheckbox: false, plantedDate: '' });
}

private isValidPlantName(entry: string): boolean {
  return /^[A-Za-z\s\-]+$/.test(entry.trim());
}
}





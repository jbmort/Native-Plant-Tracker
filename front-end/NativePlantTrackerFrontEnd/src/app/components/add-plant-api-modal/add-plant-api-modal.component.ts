import { NgFor, NgIf } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { GardenService } from '../../services/garden.service';
import { PlantSearchDto } from '../../models/plant-search-dto';
import { ApiService } from '../../services/api.service';



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

  isLoadingPlants = true; // For a loading indicator while waiting for API response
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
      plantedDate: [''],
    });
  }


onSubmit() {
  let today: String;
  today = new Date().toLocaleDateString();
  if(this.dateForm.get('todayCheckbox')?.value == false && !this.dateForm.get('plantedDate')?.value) {
    this.errorMessage = 'Please select a date or choose Today.';
    return;
  }
  if(this.dateForm.get('datePlanted')?.value > today) {
    this.errorMessage = 'The planted date cannot be in the future.';
    return;
  }
  this.errorMessage = null;

  if(this.selectedPlant) {
    const plantToAdd = {
      plantId: this.selectedPlant.id,
      datePlanted: this.dateForm.get('todayCheckbox')?.value ? today : this.dateForm.get('plantedDate')?.value
    };
    
    // Call the service to add the plant to the garden
    this.apiService.getPlantById(this.selectedPlant.id).subscribe({
      next: () => {
        if(!(this.gardenId === 0)) {
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
        }else {
        this.errorMessage = 'Garden ID is invalid. Please go back and try again.';
        }
      },
      error: (error) => {
        console.error('Error fetching plant details:', error);
        this.errorMessage = 'An error occurred while retrieving plant details. Please try again later.';
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
  this.searchPerformed = true;

  this.apiService.searchPlantsByName(plantName.trim()).subscribe({
    next: (data: PlantSearchDto[]) => {
      console.log('Plant search results:', data);
      this.plantData = data;
      this.isLoadingPlants = false;
    },
    error: (error) => {
      console.error('Error fetching plant data:', error);
      this.errorMessage = 'An error occurred while searching for plants. Please try again later.';
      this.isLoadingPlants = false;
    }
  });


}

selectPlant(plant: PlantSearchDto) {
  if(this.Ids.has(plant.id)) {
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





import { NgFor, NgIf } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { GardenService } from '../../services/garden.service';
import { PlantSearchDto } from '../../models/plant-search-dto';


@Component({
  selector: 'app-add-plant-api-modal',
  standalone: true,
  imports: [NgIf, NgFor, ReactiveFormsModule],
  templateUrl: './add-plant-api-modal.component.html',
  styleUrl: './add-plant-api-modal.component.css'
})
export class AddPlantApiModalComponent implements OnInit {

  plantForm!: FormGroup;
  errorMessage: string | null = null;
  plantData: Array<PlantSearchDto> = [];

// This will be passed in from the GardenDetailComponent
  // @Input() plant: PlantDto | null = null;
  
  // To notify the parent component that a plant was added
  @Output() plantAdded = new EventEmitter<void>();

  isLoadingPlants = true; // For a loading indicator while waiting for API response

  constructor(
      public activeModal: NgbActiveModal,
      private fb: FormBuilder,
      private gardenService: GardenService,
    ) {}

  ngOnInit(): void {
    this.plantForm = this.fb.group({
      name: [''],
    });
  }


onSubmit() {
throw new Error('Method not implemented.');
}

searchForPlant() {
throw new Error('Method not implemented.');
}

selectPlant(_t19: any) {
throw new Error('Method not implemented.');
}

}




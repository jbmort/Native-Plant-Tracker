import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { PlantDto } from '../../models/plant-dto';
import { GardenService } from '../../services/garden.service'; 
import { NgFor, NgIf } from '@angular/common';
import { PlantType } from '../../models/plant-type';
import { PlantTypeDto } from '../../models/plant-type-dto';

@Component({
  selector: 'app-add-plant-modal',
  standalone: true,
  imports: [NgIf, NgFor, ReactiveFormsModule],
  templateUrl: './add-plant-modal.component.html',
  styleUrl: './add-plant-modal.component.css'
})

export class AddPlantModalComponent implements OnInit {

  plantForm!: FormGroup;
  errorMessage: string | null = null;
  showFlowerColor: boolean = false;
  showNewType: boolean = false;
  
  types: PlantType[] = [];
  
  // This will be passed in from the GardenDetailComponent
  @Input() gardenId!: number;
  @Input() plant: PlantDto | null = null;
  
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

    this.gardenService.getTypes().subscribe({
       next: (plantTypes: PlantType[]) => {
              this.types = plantTypes;
              if(this.types == null || this.types.length < 1){
                this.showNewType = true;
              }
       }
  })


    if (this.plant) {
      // EDIT MODE: Populate form with existing plant data
      this.plantForm = this.fb.group({
        plantID: [this.plant.id], 
        name: [this.plant.commonName],
        sciName: [this.plant.sciName],
        imageUrl: [this.plant.imageUrl],
        plantType: [this.plant.plantType],
        averageHeight: [this.plant.averageHeight],
        description: [this.plant.description],
        edible: [this.plant.edible],
        edibleParts: [this.plant.edibleParts],
        lightRequirement: [this.plant.lightRequirement],
        soil_moisture: [this.plant.soil_moisture],
        nativeZones: [this.plant.nativeZones],
        
      });
    } else {
      // ADD MODE: Create an empty form
      this.plantForm = this.fb.group({
        plantID: [null],
        name: ['', [Validators.required]],
        sciName: [''],
        description: [''],
        flowerColor: [null],
        plantType: [null],
        
      });
    }
    if(this.plant != null){
      this.checkName(this.plant.typeName)
    }

    this.plantForm.get('plantType')?.valueChanges.subscribe(selectedTypeId => {
      this.updateConditionalFields(selectedTypeId);
    });

    this.plantForm.get('newType')?.valueChanges.subscribe(TypeName => {
      this.checkName(TypeName)
      
    })
  }


  onSubmit(): void {
    if (this.plantForm.invalid) {
      return;
    }
    let selectedType: number;
    if(this.showNewType){
      let typeToAdd = new PlantTypeDto()
      typeToAdd.setName(this.plantForm.value.newType);
      typeToAdd.setValue(this.plantForm.value.newType.toUpperCase().replaceAll(" ", ""))
      this.gardenService.addType(typeToAdd).subscribe({
        next: (plantType) => {
          selectedType = plantType.id;
          this.sendData(selectedType, typeToAdd.getName());
        }
      })
    }
    else{
      selectedType = this.plantForm.value.plantType;
      const type = this.types.find(t => t.id == selectedType);
      let typeName: String = "";
      if (type?.name){
        typeName = type.name
      }

      this.sendData(selectedType, typeName);
    }
  }

  updateConditionalFields(selectedTypeId: number): void {
    const selectedType = this.types.find(t => t.id == selectedTypeId);
    const typeName = selectedType?.name || '';

    this.checkName(typeName)
  }

  addType() {
      this.showNewType = !this.showNewType;
  }

  sendData(type: number, name: String){
    this.errorMessage = null;
    if(this.plantForm.value.plantID === null){
        
    const plantData: PlantDto = {
        id: this.plantForm.value.plantID,
        common_name: this.plantForm.value.name,
        sci_name: this.plantForm.value.sciName,
        description: this.plantForm.value.description,
        type: type,
        typeName: name,
        flowerColor: this.plantForm.value.flowerColor,
    } 

    this.gardenService.addPlantToGarden(this.gardenId, plantData).subscribe({
      next: (newPlant) => {
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
      id: this.plantForm.value.plantID,
       common_name: this.plantForm.value.name,
       sci_name: this.plantForm.value.sciName,
       description: this.plantForm.value.description,
       type: type,
       typeName: name,
       flowerColor: this.plantForm.value.flowerColor,
     } 

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

  checkName(name: String){
    this.showFlowerColor = false;
    const typeName = name.toUpperCase()
     if (typeName.includes('FORB') || typeName.includes('FLOWER')) {
      this.showFlowerColor = true;
    } 
  }
}


import { Component, OnInit } from '@angular/core';
import { AddPlantModalComponent } from '../../components/add-plant-modal/add-plant-modal.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Plant } from '../../models/plant';
import { GardenService } from '../../services/garden.service';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { Garden } from '../../models/garden';
import { NgFor, NgIf } from '@angular/common';
import { AddGardenModalComponent } from '../../components/add-garden-modal/add-garden-modal.component';
import { PlantDto } from '../../models/plant-dto';


@Component({
  selector: 'app-garden-detail',
  standalone: true,
  imports: [NgIf, NgFor, RouterLink],
  templateUrl: './garden-detail.component.html',
  styleUrl: './garden-detail.component.css'
})
export class GardenDetailComponent implements OnInit{

  constructor(private modalService: NgbModal,
     private gardenService: GardenService,
     private route: ActivatedRoute,
     private router: Router,
    ){}

  gardenId: number = 0;
  garden: Garden | null = null;
  plantList: Array<PlantDto> = new Array();
  errorMessage: String | null = null;
  editMode: boolean = false;
  gardenAge: String = '';


  ngOnInit(): void {
      this.route.paramMap.subscribe(params => {
     
      const idString = params.get('id');

      if (idString) {
        this.gardenId = +idString;

        this.loadGarden(this.gardenId);
      }
    });
  }

  setGardenAge(): String{
    let date = new Date;
    if(this.garden != null ){
      date = new Date(this.garden.created_on)
    }
    
    if(this.garden != null){
    const currentDate = Date.now();

    const gardenEstab = date.getTime();
    const diff = currentDate - gardenEstab;
    let days = diff / (1000 * 60 * 60 * 24);
    if (days < 100){
      return Math.ceil(days) + " Days"
    }
    else {
      return (days/365).toFixed(2) + " Years"
    }
    }
    return 'Unknown';
  }

   toggleEditMode(): void {
    this.editMode = !this.editMode;
  }

  deletePlant(plantId: number): void {
    if (this.gardenId && plantId) {
      this.gardenService.deletePlantFromGarden(this.gardenId, plantId).subscribe({
        next: () => {
          console.log(`Plant ${plantId} deleted successfully.`);
          this.loadGardenData(this.gardenId); 
        },
        error: (err) => {
          console.error(`Error deleting plant ${plantId}:`, err);
          this.errorMessage = 'Could not delete plant.';
        }
      });
    }
  }

  loadGarden(gardenID: number){
    this.gardenService.getGardenById(gardenID).subscribe({
      next: (garden) => {
        this.garden = garden;
        this.errorMessage = null;
        this.gardenAge = this.setGardenAge()
        this.loadGardenData(this.gardenId);
      },
      error: (err) =>{
        this.errorMessage = 'Garden could not be loaded.'
      }
    })
  }

  loadGardenData(gardenID: number){
    this.gardenService.getGardenPlants(gardenID).subscribe({
      next: (plants) => {
        this.plantList = plants;
        console.log(plants)
        this.errorMessage = null;
      },
      error: (err) => {
        this.errorMessage = 'Plants could not be loaded for this garden';
      }
    })
  }

  openAddPlantModal(gardenID: number, plant: PlantDto | null): void {
      const modalRef = this.modalService.open(AddPlantModalComponent);
      modalRef.componentInstance.gardenId = gardenID;
      modalRef.componentInstance.plant = plant;
  
      modalRef.componentInstance.plantAdded.subscribe(() => {
        this.loadGardenData(this.gardenId);
      });
  
      modalRef.result.then(
        (result) => {
          console.log(`Plant Modal closed with: ${result}`);
        },
        (reason) => {
          console.log(`Plant Modal dismissed with: ${reason}`);
        }
      );
    }

    openEditGardenModal(): void {
        const modalRef = this.modalService.open(AddGardenModalComponent);
        modalRef.componentInstance.gardenToEdit = this.garden;
    
        modalRef.componentInstance.gardenCreated.subscribe(() => {
          this.loadGarden(this.gardenId);
        });
    
        // You can also handle the result when the modal is closed
        modalRef.result.then(
          (result) => {
            console.log(`Modal closed with: ${result}`);
          },
          (reason) => {
            console.log(`Modal dismissed with: ${reason}`);
          }
        );
      }

}

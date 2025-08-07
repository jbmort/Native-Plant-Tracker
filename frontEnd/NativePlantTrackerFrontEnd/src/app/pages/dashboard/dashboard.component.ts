import { Component, OnInit } from '@angular/core';
import { GardenService } from '../../services/garden.service'; 
import { AuthService } from '../../services/auth.service';
import { Garden } from '../../models/garden';
import { Router, RouterLink } from '@angular/router';
import { NgIf } from '@angular/common';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AddGardenModalComponent } from '../../components/add-garden-modal/add-garden-modal.component';


@Component({
  standalone: true,
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  imports: [RouterLink, NgIf],
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  gardens: Garden[] = [];
  username: string | null = null;
  totalPlants: number = 0;
  isLoading: boolean = true; 
  error: string | null = null;

  constructor(
    private gardenService: GardenService,
    private authService: AuthService,
    private modalService: NgbModal,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.loadDashboardData();
    this.username = this.authService.getUsernameFromToken();
  }

  loadDashboardData(): void {
    this.isLoading = true;
    this.gardenService.getGardensForCurrentUser().subscribe({
      next: (data: Garden[]) => {
        this.gardens = data;
        this.calculateStats();
        this.isLoading = false;
      },
      error: (err: any) => {
        console.error('Failed to load gardens', err);
        this.error = 'Failed to load gardens. Please try again later.';

        this.isLoading = false;
      }
    });
  }

  calculateStats(): void {
  
    this.totalPlants = 0;
    
    this.gardens.forEach(garden => {
    
      if (garden.plantList) {
        this.totalPlants += garden.plantList.length;
      }
    });
  }

  // A helper method to calculate years established
  getYearsEstablished(creationDate: string): number {
    const created = new Date(creationDate);
    const now = new Date();

    let years = now.getFullYear() - created.getFullYear();

    if (now.getMonth() < created.getMonth() || (now.getMonth() === created.getMonth() && now.getDate() < created.getDate())) {
      years--;
    }
    return Math.max(0, years); 
  }


  onDelete(gardenId: number): void {
    if (confirm('Are you sure you want to delete this garden?')) {
      this.gardenService.deleteGarden(gardenId).subscribe({
        next: () => {
          console.log('Garden deleted successfully');
          this.loadDashboardData(); 
        },
        error: (err) => {
          console.error('Error deleting garden:', err);
          this.error = 'Failed to delete garden.';
        }
      });
    }
  }

    
openAddGardenModal(): void {
    const modalRef = this.modalService.open(AddGardenModalComponent);

    modalRef.componentInstance.gardenCreated.subscribe(() => {
      this.loadDashboardData();
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

viewPlantReport(): void {
  this.router.navigate([''])
}
  
}

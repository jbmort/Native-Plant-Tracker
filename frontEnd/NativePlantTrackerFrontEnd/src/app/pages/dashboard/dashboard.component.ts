import { Component, OnInit } from '@angular/core';
import { GardenService } from '../../services/garden.service'; 
import { AuthService } from '../../services/auth.service';
import { Garden } from '../../models/garden';
import { Router, RouterLink } from '@angular/router';
import { NgFor, NgIf } from '@angular/common';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AddGardenModalComponent } from '../../components/add-garden-modal/add-garden-modal.component';
import { GenerateReportModalComponent } from '../../components/generate-report-modal/generate-report-modal.component';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';


@Component({
  standalone: true,
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  imports: [RouterLink, NgIf, NgFor, ReactiveFormsModule],
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  gardens: Garden[] = [];
  username: string | null = null;
  totalPlants: number = 0;
  isLoading: boolean = true; 
  error: string | null = null;
   searchBar!: FormGroup;
   searchError: String | null = null;
   filteredGardens: Garden[] = [];

  constructor(
    private gardenService: GardenService,
    private authService: AuthService,
    private modalService: NgbModal,
    private router: Router,
    private fb: FormBuilder
  ) { }

  ngOnInit(): void {
    this.loadDashboardData();
    this.username = this.authService.getUsernameFromToken();

      this.searchBar = this.fb.group({
      search: '',
    })
  }

  loadDashboardData(): void {
    this.isLoading = true;
    this.gardenService.getGardensForCurrentUser().subscribe({
      next: (data: Garden[]) => {
        this.gardens = data;
        this.calculateStats();
        this.isLoading = false;
        this.filteredGardens = data;
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
    
      if (garden.gardenPlants) {
        this.totalPlants += garden.gardenPlants.length;
      }
    });
  }

  // A helper method to calculate years established
  getYearsEstablished(creationDate: Date): string{
    const created = new Date(creationDate);
    const now = new Date();

    let years = now.getFullYear() - created.getFullYear();

    if (now.getMonth() < created.getMonth() || (now.getMonth() === created.getMonth() && now.getDate() < created.getDate())) {
      years--;
    }
    if(years == 0){
      const diff = Math.abs(now.getTime() - created.getTime()); 
      const days = diff / 1000 / 60 / 60 / 24;
      return Math.floor(days) + " Days";
    }
    return Math.max(0, years) + " Years" ; 
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
      },
      (reason) => {
      }
    );
  }

  openReportModal() {
      const modalRef = this.modalService.open(GenerateReportModalComponent);

      modalRef.result.then(
 (result) => {
        console.log(`Modal closed with result: ${result}`);
        
        // 3. Perform navigation based on the result
        if (result === 'GARDEN') {
          this.router.navigate(['/report/garden']);
        } else if (result === 'PLANT') {
          this.router.navigate(['/report/plant']);
        }
      });
}

viewPlantReport(): void {
  this.router.navigate(['plants/report'])
}


searchGardens(){
  if(this.searchBar.invalid){
    return
  }
  const searchTerm = this.searchBar.value.search.toLowerCase().trim();

  this.filteredGardens = this.gardens.filter((g) => {
    return g.name.trim().toLowerCase().includes(searchTerm)
  })
  if(this.filteredGardens.length < 1){
    this.searchError = "No gardens found with that name.";
    this.filteredGardens = this.gardens;
  }
  else{
    this.searchError = null;
  }
}

updateList(){
  const searchTerm = this.searchBar.value.search.toLowerCase().trim();

  this.filteredGardens = this.gardens.filter((g) => {
    return g.name.toLowerCase().trim().includes(searchTerm)
  })
  this.searchError = null
}

searchBlur(){
  if(this.searchBar.value.search.length < 1){
    this.filteredGardens = this.gardens;
  }
}

authenticated(){
  return this.authService.isAuthenticated()
}
  
}

import { ComponentFixture, fakeAsync, TestBed, tick} from '@angular/core/testing';
import { DashboardComponent } from './dashboard.component';
import { GardenService } from '../../services/garden.service';
import { AuthService } from '../../services/auth.service';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { of, throwError} from 'rxjs';
import { Garden } from '../../models/garden';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientTestingModule } from '@angular/common/http/testing'; 

// Mock gardens
const MOCK_GARDENS: Garden[] = [
  { id: 1, name: 'Front Yard Bed', description: 'Sunny spot', gardenPlants: [{
    id: 0,
    commonName: '',
    sciName: '',
    description: '',
    created_on: new Date(),
    gardens: [],
    flowerColor: null,
    type: 0
  }, {
    id: 0,
    commonName: '',
    sciName: '',
    description: '',
    created_on: new Date(),
    gardens: [],
    flowerColor: null,
    type: 0
  }], created_on: new Date() },
  { id: 2, name: 'Backyard Meadow', description: 'Shady area', gardenPlants: [{
    id: 0,
    commonName: '',
    sciName: '',
    description: '',
    created_on: new Date(),
    gardens: [],
    flowerColor: null,
    type: 0
  }], created_on: new Date() }
];

describe('DashboardComponent', () => {
  let component: DashboardComponent;
  let fixture: ComponentFixture<DashboardComponent>;
  let gardenServiceSpy: jasmine.SpyObj<GardenService>;
  let authServiceSpy: jasmine.SpyObj<AuthService>;

  beforeEach(async () => {
    gardenServiceSpy = jasmine.createSpyObj('GardenService', ['getGardensForCurrentUser', 'deleteGarden']);
    authServiceSpy = jasmine.createSpyObj('AuthService', ['getUsernameFromToken']);

    await TestBed.configureTestingModule({
      imports: [DashboardComponent, ReactiveFormsModule, HttpClientTestingModule],
      providers: [
      { provide: GardenService, useValue: gardenServiceSpy },
      { provide: AuthService, useValue: authServiceSpy },
      { provide: Router, useValue: {} },
      { provide: NgbModal, useValue: {} },
      { provide: ActivatedRoute, useValue: {paramMap: of({}) }}
      ]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DashboardComponent);
    component = fixture.componentInstance;
    authServiceSpy.getUsernameFromToken.and.returnValue('testuser');
  });

  it('should create', () => {
    // mock oninit
    gardenServiceSpy.getGardensForCurrentUser.and.returnValue(of([]));
    // trigger oninit
    fixture.detectChanges();

    expect(component).toBeTruthy();
  });


  // Positive test for loadDashboardData
  describe('when loadDashboardData is called successfully', () => {
    
    beforeEach(() => {
      // prep for api call on garden data
      gardenServiceSpy.getGardensForCurrentUser.and.returnValue(of(MOCK_GARDENS));
      // trigger oninit
      fixture.detectChanges(); 
    });
 

    it('should set isLoading to false', () => {
      // ASSERT
      expect(component.isLoading).toBe(false);
    });

    it('should populate gardens and filteredGardens with data', () => {
      // ASSERT
      expect(component.gardens.length).toBe(2);
      expect(component.filteredGardens.length).toBe(2);
      expect(component.gardens[0].name).toBe('Front Yard Bed');
      expect(component.filteredGardens[1].name).toBe('Backyard Meadow');

    });


    it('should calculate the total number of plants', () => {
      // ASSERT
      expect(component.totalPlants).toBe(3);
    });

    it('should set the error property to null', () => {
      // ASSERT
      expect(component.error).toBeNull();
    });

  // })
  });


  // Negative test for loadDashboardData to ensure error handling
  describe('when loadDashboardData fails', () => {
    
    beforeEach(() => {
      // Set up action for an error returning data from the back end
      gardenServiceSpy.getGardensForCurrentUser.and.returnValue(throwError(() => new Error('API Error')));
      
      // Trigger ngOninit
      fixture.detectChanges();
    });

    it('should set isLoading to false', () => {
      // ASSERT
      expect(component.isLoading).toBe(false);
    });

    it('should set the error message', () => {
      // ASSERT
      expect(component.error).toBe('Failed to load gardens. Please try again later.');
    });

    it('should not populate the gardens array', () => {
      // ASSERT
      expect(component.gardens.length).toBe(0);
    });

    it('should not populate the filtered gardens array', () => {
      // ASSERT
      expect(component.filteredGardens.length).toBe(0)
    })
  });

});

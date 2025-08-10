import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GardenReportComponent } from './garden-report.component';
import { GardenService } from '../../services/garden.service';

describe('GardenReportComponent', () => {
  let component: GardenReportComponent;
  let fixture: ComponentFixture<GardenReportComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GardenReportComponent, GardenService]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(GardenReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

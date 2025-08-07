import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PlantReportComponent } from './plant-report.component';

describe('PlantReportComponent', () => {
  let component: PlantReportComponent;
  let fixture: ComponentFixture<PlantReportComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PlantReportComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(PlantReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

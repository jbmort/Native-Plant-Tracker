import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddPlantApiModalComponent } from './add-plant-api-modal.component';

describe('AddPlantApiModalComponent', () => {
  let component: AddPlantApiModalComponent;
  let fixture: ComponentFixture<AddPlantApiModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddPlantApiModalComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AddPlantApiModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

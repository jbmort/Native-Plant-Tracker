import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddPlantModalComponent } from './add-plant-modal.component';

describe('AddPlantModalComponent', () => {
  let component: AddPlantModalComponent;
  let fixture: ComponentFixture<AddPlantModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddPlantModalComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AddPlantModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

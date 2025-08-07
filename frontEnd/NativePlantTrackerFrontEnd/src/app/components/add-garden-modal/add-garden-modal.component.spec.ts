import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddGardenModalComponent } from './add-garden-modal.component';

describe('AddGardenModalComponent', () => {
  let component: AddGardenModalComponent;
  let fixture: ComponentFixture<AddGardenModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddGardenModalComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AddGardenModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

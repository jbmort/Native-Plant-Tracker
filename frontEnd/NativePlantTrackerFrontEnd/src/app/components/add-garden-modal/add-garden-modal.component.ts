import { Component, EventEmitter, Input, input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { GardenDTO } from '../../models/garden-dto'; 
import { GardenService } from '../../services/garden.service';
import { NgIf } from '@angular/common';
import { Garden } from '../../models/garden';

@Component({
  selector: 'app-add-garden-modal',
  standalone: true,
  imports: [NgIf, ReactiveFormsModule],
  templateUrl: './add-garden-modal.component.html',
  styleUrl: './add-garden-modal.component.css'
})

export class AddGardenModalComponent implements OnInit{
  gardenForm!: FormGroup;
  errorMessage: string | null = null;

  @Input() gardenToEdit: Garden | null = null

  @Output() gardenCreated = new EventEmitter<void>();

  constructor(
    public activeModal: NgbActiveModal,
    private fb: FormBuilder,
    private gardenService: GardenService
  ) {}

  ngOnInit(): void {
    this.gardenForm = this.fb.group({
      name: ['', Validators.required],
      description: [''],
    });

    if(this.gardenToEdit != null){
       this.gardenForm = this.fb.group({
      name: [this.gardenToEdit.name, Validators.required],
      description: [this.gardenToEdit.description],
       });
    }
  }

  onSubmit(): void {
    if (this.gardenForm.invalid) {
      return;
    }

      this.errorMessage = null;
      const gardenData: GardenDTO = this.gardenForm.value;

    if(this.gardenToEdit == null){
      this.gardenService.createGarden(gardenData).subscribe({
        next: (newGarden) => {
          console.log('Garden created successfully:', newGarden);
          this.gardenCreated.emit();
          this.activeModal.close('Garden Created');
        },
        error: (err) => {
          console.error('Error creating garden:', err);
          this.errorMessage = 'Failed to create garden. Please try again.';
        }
      });
    }
    else if(this.gardenToEdit != null){
      this.gardenService.updateGarden(this.gardenToEdit.id, gardenData).subscribe({
          next: (updatedGarden) => { 
            this.gardenCreated.emit();
            console.log('Garden updated: ' + updatedGarden.name)
            this.activeModal.close('Garden Updated');
          },
          error: (err) => {
            this.errorMessage = 'Failed to update garden. Please try again.';
            console.error('Error updating garden', err)

          }
      })
    }
  }
}
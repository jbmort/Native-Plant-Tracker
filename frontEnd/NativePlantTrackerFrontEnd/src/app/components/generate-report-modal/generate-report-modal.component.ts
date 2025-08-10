import { NgIf } from '@angular/common';
import { Component, EventEmitter, Output } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';



@Component({
  selector: 'app-generate-report-modal',
  standalone: true,
  imports: [NgIf, ReactiveFormsModule],
  templateUrl: './generate-report-modal.component.html',
  styleUrl: './generate-report-modal.component.css'
})
export class GenerateReportModalComponent {

  reportResponse!: FormGroup;

  @Output() choiceMade = new EventEmitter<void>()

  constructor(
    private fb: FormBuilder,
    private router: Router,
    public activeModal: NgbActiveModal,
  ){}

   ngOnInit(): void {
    this.reportResponse = this.fb.group({
      responseType: [null, Validators.required]
    });
  }

   onSubmit(): void {
    if (this.reportResponse.invalid) {
      return;
    }
   const selectedType = this.reportResponse.value.responseType;
    
    this.activeModal.close(selectedType); 
    }

    
  }


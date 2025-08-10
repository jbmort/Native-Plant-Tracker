import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing'; 
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';



import { AuthService } from '../services/auth.service';

describe('AuthService', () => {
  let service: AuthService;
 

  beforeEach(() => {
    TestBed.configureTestingModule({
    imports: [HttpClientTestingModule],
  })
    TestBed.configureTestingModule({});
    service = TestBed.inject(AuthService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

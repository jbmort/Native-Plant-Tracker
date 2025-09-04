import { Injectable } from '@angular/core';
import {jwtDecode} from 'jwt-decode'; 
import { Router } from '@angular/router';
import { LoginCredentials, UserRegistration, JwtAuthResponse } from '../models/auth';
import { Observable, tap } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from '../environments/environment';


@Injectable({
  providedIn: 'root'
})
export class AuthService {
  
   private apiUrl = environment.apiUrl;

  constructor(private router: Router, private http: HttpClient) { }


  public isAuthenticated(): boolean {
    const token = this.getToken();
    if (!token) {
      return false;
    }

    // Decode the token to get its expiration date
    try {
      const decodedToken: { exp: number } = jwtDecode(token);
      
      const expirationDate = new Date(0);
      expirationDate.setUTCSeconds(decodedToken.exp);

      // Check if the token is expired
      return expirationDate.valueOf() > new Date().valueOf();
    } catch (error) {
      console.error("Error decoding token:", error);
      return false;
    }
  }


  public getUsernameFromToken(): string | null {
    const token = this.getToken();
    if (!token) {
      return null;
    }

    try {
      // Define an interface for the expected decoded token structure
      interface DecodedToken {
        sub: string;
      }

      // Decode the token using the library
      const decodedToken: DecodedToken = jwtDecode(token);

      return decodedToken.sub;
    } catch (error) {
      console.error("Could not decode token", error);
      return null;
    }
  }
  
 
  private getToken(): string | null {
    return localStorage.getItem('accessToken');
  }


  public logout(): void {
    localStorage.removeItem('accessToken');
    this.router.navigate([''])
  }

  register(registrationData: UserRegistration): Observable<any> {
    const registerUrl = `${this.apiUrl}/auth/register`;
    return this.http.post(registerUrl, registrationData, { responseType: 'text' });
  }

  login(credentials: LoginCredentials): Observable<JwtAuthResponse> {
    const loginUrl = `${this.apiUrl}/auth/login`;
    return this.http.post<JwtAuthResponse>(loginUrl, credentials).pipe(
      tap(response => {
        console.log('Login successful, storing token.');
        
        localStorage.setItem('accessToken', response.accessToken);

        this.router.navigate(['/dashboard']);
      })
    );
  }
}

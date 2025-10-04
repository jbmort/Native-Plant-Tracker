import { Injectable } from '@angular/core';
import { environment } from '../environments/environment';
import { PlantSearchDto } from '../models/plant-search-dto';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PlantDto } from '../models/plant-dto';


@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private envUrl = environment.apiUrl
  private searchUrl = `${this.envUrl}/search`;
  private plantUrl = `${this.envUrl}/plants`;

  constructor(private http: HttpClient) { }


  // Search plants by name
  searchPlantsByName(name: string): Observable <PlantSearchDto[]> {
    const url = `${this.searchUrl}?q=${encodeURIComponent(name)}`;
    return this.http.get<PlantSearchDto[]>(url);
  }

  // Get full plant details by ID
  getPlantById(id: number): Observable<PlantDto> {
    const url = `${this.plantUrl}/${id}`;
    return this.http.get<PlantDto>(url);
  }
}

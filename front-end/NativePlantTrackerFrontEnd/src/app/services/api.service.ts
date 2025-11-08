import { Injectable } from '@angular/core';
import { environment } from '../environments/environment';
import { PlantSearchDto } from '../models/plant-search-dto';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { PlantDto } from '../models/plant-dto';


@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private envUrl = environment.apiUrl
  private searchUrl = `${this.envUrl}/search`;
  private plantUrl = `${this.envUrl}/search/plant`;

  constructor(private http: HttpClient) { }


  // Search plants by name
  searchPlantsByName(name: string): Observable <PlantSearchDto[]> {
    const url = `${this.searchUrl}?q=${encodeURIComponent(name)}`;
    return this.http.get<PlantSearchDto[]>(url);
  }

  // Get full plant details by ID
  savePlant(id: number): Observable<boolean> {
    const url = `${this.plantUrl}/${id}`;
    let response: String = '';
    this.http.get<String>(url).subscribe(res => {
      response = res;

      if(response === "added to database" || response === "already exists in database") {

      return of(true);
    }
    return of(false);
    });
    
    console.log("Failed to add plant to database"  + response);
    return of(false);
  }


}

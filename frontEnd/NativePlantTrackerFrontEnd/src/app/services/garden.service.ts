import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs'; 
import { Garden } from '../models/garden';
import { GardenDTO } from '../models/garden-dto';
import { Plant } from '../models/plant';
import { PlantDto } from '../models/plant-dto';
import { PlantReport } from '../models/plant-report';
import { GardenReport } from '../models/garden-report';

@Injectable({
  providedIn: 'root'
})
export class GardenService {
  private apiUrl = 'http://localhost:8080/api/gardens';
  private reportUrl = 'http://localhost:8080/api/reports'

  constructor(private http: HttpClient) { }

// List of gardens for user
  getGardensForCurrentUser(): Observable<Garden[]> {
    return this.http.get<Garden[]>(this.apiUrl);
  }

//  specific garden details
  getGardenById(id: number): Observable<Garden> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.get<Garden>(url);
  }


  //  Creates a new garden for the current user.
  createGarden(gardenData: GardenDTO): Observable<Garden> {
    return this.http.post<Garden>(this.apiUrl, gardenData);
  }


  //  Updates an existing garden.
  updateGarden(id: number, gardenData: GardenDTO): Observable<Garden> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.put<Garden>(url, gardenData);
  }

 
  //  Deletes a garden by its ID.
  deleteGarden(id: number): Observable<void> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.delete<void>(url);
  }

  // Get plants for a specific garden
  getGardenPlants(id: number): Observable<Array<Plant>> {
    const url = `${this.apiUrl}/${id}/plants`;
    return this.http.get<Array<Plant>>(url);
  }

  // 7. POST (add) a new plant to a garden
  addPlantToGarden(id: number, plantData: PlantDto): Observable<Plant> {
    const url = `${this.apiUrl}/${id}/plants`;
    return this.http.post<Plant>(url, plantData)
  }

    // 8. PUT (update) a plant for a specific garden
  updatePlant(gardenId: number, plantId: number, plantData: PlantDto): Observable<Plant>{
    const url = `${this.apiUrl}/${gardenId}/${plantId}`;
    return this.http.put<Plant>(url, plantData)
  }

    // 9. DELETE a plant from a garden
  deletePlantFromGarden(gardenId: number, plantId: number): Observable<void>{
    const url = `${this.apiUrl}/${gardenId}/${plantId}`;
    return this.http.delete<void>(url)
  }

   // 10. GET all plants for the user
   getAllUserPlants(): Observable<Array<Plant>>{
    const url = `${this.apiUrl}/plants`;
    return this.http.get<Array<Plant>>(url);
   }

  // 11. GET a generated plant report object for the user
  getPlantReport(): Observable<Array<PlantReport>>{
    const url = `${this.reportUrl}/plant`;
    return this.http.get<Array<PlantReport>>(url)
  }

  // 12. GET a generated garden report object for the user
  getGardenReport(): Observable<Array<GardenReport>>{
    const url = `${this.reportUrl}/garden`;
    return this.http.get<Array<GardenReport>>(url);

  }


  // You would add more methods here for interacting with plants within a garden, e.g.:
  // getPlantsForGarden(gardenId: number): Observable<Plant[]> { ... }
}

export interface PlantDto {
    // id: number = 0;
    // common_name: String = '';
    // sci_name: String = '';
    // description: String = '';
    // flowerColor: String | null = null;
    // type: number = 0;
    // typeName: String = "";
  id: number; 

  commonName: string | null; 

  sciName: string; 

  imageUrl: string | null; 

  plantType: string; 

  averageHeight: number | null; 

  description: string | null; 

  // --- Edible Information ---
  edible: boolean; 
  edibleParts: string[]; 

  // --- Growth Requirements ---
  lightRequirement: string[]; 
  
  
  soil_moisture: string[];

  // --- Native Distribution ---
  nativeZones: string[];

}

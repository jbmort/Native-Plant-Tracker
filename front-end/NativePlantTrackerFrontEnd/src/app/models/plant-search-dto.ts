export interface PlantSearchDto {
    id: number;
    commonName: string | null;
    scientificName: string;
    imageUrl: string | null
    description: string | null;
    typeName: string | null;
}

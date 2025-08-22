import { GardenDTO } from './garden-dto';

describe('GardenDTO', () => {
  it('should create an instance', () => {
    const dto: GardenDTO = {
      name: '',
      description: ''
    }
    expect(dto).toBeTruthy();
  });
});

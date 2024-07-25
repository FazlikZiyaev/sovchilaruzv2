import { ILocation, NewLocation } from './location.model';

export const sampleWithRequiredData: ILocation = {
  id: 12491,
  lat: 23656.29,
  lon: 19305.47,
  country: 'In',
  city: 'Cheyenne',
  district: 'quiche equinox meanwhile',
};

export const sampleWithPartialData: ILocation = {
  id: 21106,
  lat: 19260.12,
  lon: 30883.68,
  country: 'Ir',
  city: 'Stammbury',
  district: 'ha studio',
  state: 'DELETED',
};

export const sampleWithFullData: ILocation = {
  id: 21323,
  lat: 23515.34,
  lon: 25432.06,
  country: 'Ga',
  city: 'Romaguerashire',
  district: 'rapidly bossy',
  ord: 11747,
  state: 'INACTIVE',
};

export const sampleWithNewData: NewLocation = {
  lat: 11710.71,
  lon: 18182.49,
  country: 'Be',
  city: 'Yuba City',
  district: 'correctly impressive zoot-suit',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

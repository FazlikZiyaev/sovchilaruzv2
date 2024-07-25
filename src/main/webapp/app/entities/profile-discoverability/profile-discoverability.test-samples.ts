import { IProfileDiscoverability, NewProfileDiscoverability } from './profile-discoverability.model';

export const sampleWithRequiredData: IProfileDiscoverability = {
  id: 18247,
};

export const sampleWithPartialData: IProfileDiscoverability = {
  id: 11137,
  minAge: 82,
};

export const sampleWithFullData: IProfileDiscoverability = {
  id: 25401,
  maritalStatus: 'DIVORCED_THROUGH_COURT',
  maxAge: 42,
  minAge: 41,
  showMyPhoto: false,
};

export const sampleWithNewData: NewProfileDiscoverability = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

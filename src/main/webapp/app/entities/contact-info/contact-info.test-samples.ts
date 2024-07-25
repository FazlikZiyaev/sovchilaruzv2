import { IContactInfo, NewContactInfo } from './contact-info.model';

export const sampleWithRequiredData: IContactInfo = {
  id: '3119df5b-e444-4f4f-89f7-c78cc5888732',
  type: 'TIKTOK',
  text: 'how abnormally',
};

export const sampleWithPartialData: IContactInfo = {
  id: '07bc1bd7-05ad-4b7a-8b01-4c6e385d66b8',
  type: 'INSTAGRAM',
  text: 'feminise ecstatic grateful',
  ord: 8682,
  state: 'ACTIVE',
};

export const sampleWithFullData: IContactInfo = {
  id: '642dd7a4-1636-4132-a055-c4bef5cfd03c',
  type: 'FACEBOOK',
  text: 'villainous cute',
  ord: 12077,
  state: 'ACTIVE',
};

export const sampleWithNewData: NewContactInfo = {
  type: 'TELEGRAM',
  text: 'pretzel terrific',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

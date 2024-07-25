import { IGenderTag, NewGenderTag } from './gender-tag.model';

export const sampleWithRequiredData: IGenderTag = {
  id: 'f1bd6332-9439-486d-b9cc-62203ab2857c',
  hashtag: 'superficial beyond or',
  gender: 'FEMALE',
};

export const sampleWithPartialData: IGenderTag = {
  id: 'e8bfe904-142d-47ce-b293-cba400686d8f',
  hashtag: 'lest makeover',
  gender: 'FEMALE',
};

export const sampleWithFullData: IGenderTag = {
  id: '7811fa26-993e-4596-9ee7-d816ffc69699',
  hashtag: 'hoist behind expand',
  gender: 'MALE',
  state: 'DELETED',
};

export const sampleWithNewData: NewGenderTag = {
  hashtag: 'excellence',
  gender: 'MALE',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

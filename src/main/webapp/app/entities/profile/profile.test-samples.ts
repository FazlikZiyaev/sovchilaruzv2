import dayjs from 'dayjs/esm';

import { IProfile, NewProfile } from './profile.model';

export const sampleWithRequiredData: IProfile = {
  id: '4b689049-4a3d-4af3-804d-250da96b7c18',
  gender: 'MALE',
  age: 58,
  height: 1047.89,
  weight: 693.71,
  education: 'COMPLETE_HIGHER_BACHELOR_EDUCATION',
  isHealthy: false,
  dateOfBirth: dayjs('2024-07-25'),
  placeOfBirth: 'incredible although',
  maritalStatus: 'WIDOW',
};

export const sampleWithPartialData: IProfile = {
  id: '57cd69f8-3f56-4c39-a626-cf6e214e5c0c',
  gender: 'MALE',
  age: 31,
  height: 28901.68,
  weight: 3445.75,
  education: 'PRIMARY_EDUCATION',
  workPlace: 'excluding specific',
  isHealthy: true,
  healthIssues: 'remaster',
  dateOfBirth: dayjs('2024-07-24'),
  placeOfBirth: 'wiry',
  maritalStatus: 'WIDOW',
  childPlanInFuture: 'NOT_SURE_YET',
  numOfMembersInFamily: 16,
  birthPositionInFamily: 15,
  hasOwnDwelling: true,
  wantToStudy: false,
  readyToRelocate: false,
  requirements: 'smudge',
};

export const sampleWithFullData: IProfile = {
  id: '6cab4dd9-4098-4a6c-a3e0-c584659ae865',
  gender: 'FEMALE',
  age: 79,
  height: 20747.89,
  weight: 5934.68,
  education: 'INCOMPLETE_HIGHER_BACHELOR_EDUCATION',
  profession: 'upbraid within',
  workPlace: 'slum indeed awkwardly',
  isHealthy: true,
  healthIssues: 'oof merchandise transfuse',
  dateOfBirth: dayjs('2024-07-24'),
  placeOfBirth: 'whose',
  maritalStatus: 'UNMARRIED',
  childPlanInFuture: 'NOT_SURE_YET',
  numOfMembersInFamily: 16,
  numOfChildrenInFamily: 19,
  birthPositionInFamily: 2,
  hasOwnDwelling: true,
  wantToStudy: false,
  wantToWork: true,
  readyToRelocate: false,
  knowledgeOfLanguages: 'yellowish',
  skills: 'whereas on because',
  bio: 'indeed',
  requirements: 'whether hence',
  profileState: 'CHECKING',
};

export const sampleWithNewData: NewProfile = {
  gender: 'FEMALE',
  age: 61,
  height: 32380.6,
  weight: 27549.75,
  education: 'SECONDARY_EDUCATION',
  isHealthy: false,
  dateOfBirth: dayjs('2024-07-25'),
  placeOfBirth: 'down',
  maritalStatus: 'DIVORCED_THROUGH_COURT',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

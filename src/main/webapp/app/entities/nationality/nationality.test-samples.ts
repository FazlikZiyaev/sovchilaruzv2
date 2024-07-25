import { INationality, NewNationality } from './nationality.model';

export const sampleWithRequiredData: INationality = {
  id: 'b4f3b95e-1107-4864-a458-0e2b1d358f49',
  name: 'catch unexpectedly who',
};

export const sampleWithPartialData: INationality = {
  id: '6a3e055f-71da-4dd6-a88a-7165f63b07da',
  name: 'unless',
  ord: 16725,
  state: 'ACTIVE',
};

export const sampleWithFullData: INationality = {
  id: '9c13a743-444b-493b-9b38-83fb112e79e6',
  name: 'hackwork sponge er',
  ord: 16540,
  state: 'ACTIVE',
};

export const sampleWithNewData: NewNationality = {
  name: 'as',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

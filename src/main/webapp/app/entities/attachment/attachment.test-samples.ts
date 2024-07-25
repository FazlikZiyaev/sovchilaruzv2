import { IAttachment, NewAttachment } from './attachment.model';

export const sampleWithRequiredData: IAttachment = {
  id: 'aa8816c5-e37c-49ed-b746-eed8f7301ab9',
  fileKey: 'beautifully',
};

export const sampleWithPartialData: IAttachment = {
  id: '25659179-fd24-497b-80d5-b36d47cdc995',
  fileKey: 'except',
};

export const sampleWithFullData: IAttachment = {
  id: 'a92a0b4a-3ec4-445f-93e1-a3cfe6871682',
  fileKey: 'pace through energy',
  state: 'ACTIVE',
};

export const sampleWithNewData: NewAttachment = {
  fileKey: 'absent',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

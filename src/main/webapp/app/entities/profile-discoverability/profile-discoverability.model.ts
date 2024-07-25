import { MaritalStatus } from 'app/entities/enumerations/marital-status.model';

export interface IProfileDiscoverability {
  id: number;
  maritalStatus?: keyof typeof MaritalStatus | null;
  maxAge?: number | null;
  minAge?: number | null;
  showMyPhoto?: boolean | null;
}

export type NewProfileDiscoverability = Omit<IProfileDiscoverability, 'id'> & { id: null };

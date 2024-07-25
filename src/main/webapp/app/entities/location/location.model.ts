import { EntityState } from 'app/entities/enumerations/entity-state.model';

export interface ILocation {
  id: number;
  lat?: number | null;
  lon?: number | null;
  country?: string | null;
  city?: string | null;
  district?: string | null;
  ord?: number | null;
  state?: keyof typeof EntityState | null;
}

export type NewLocation = Omit<ILocation, 'id'> & { id: null };

import { EntityState } from 'app/entities/enumerations/entity-state.model';

export interface INationality {
  id: string;
  name?: string | null;
  ord?: number | null;
  state?: keyof typeof EntityState | null;
}

export type NewNationality = Omit<INationality, 'id'> & { id: null };

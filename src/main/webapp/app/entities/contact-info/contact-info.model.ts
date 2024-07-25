import { IProfile } from 'app/entities/profile/profile.model';
import { InfoType } from 'app/entities/enumerations/info-type.model';
import { EntityState } from 'app/entities/enumerations/entity-state.model';

export interface IContactInfo {
  id: string;
  type?: keyof typeof InfoType | null;
  text?: string | null;
  ord?: number | null;
  state?: keyof typeof EntityState | null;
  profile?: Pick<IProfile, 'id'> | null;
}

export type NewContactInfo = Omit<IContactInfo, 'id'> & { id: null };

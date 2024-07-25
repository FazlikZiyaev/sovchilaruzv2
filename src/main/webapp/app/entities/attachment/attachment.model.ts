import { IProfile } from 'app/entities/profile/profile.model';
import { EntityState } from 'app/entities/enumerations/entity-state.model';

export interface IAttachment {
  id: string;
  fileKey?: string | null;
  state?: keyof typeof EntityState | null;
  profile?: Pick<IProfile, 'id'> | null;
}

export type NewAttachment = Omit<IAttachment, 'id'> & { id: null };

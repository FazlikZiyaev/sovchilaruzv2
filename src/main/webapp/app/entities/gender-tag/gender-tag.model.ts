import { IProfile } from 'app/entities/profile/profile.model';
import { Gender } from 'app/entities/enumerations/gender.model';
import { EntityState } from 'app/entities/enumerations/entity-state.model';

export interface IGenderTag {
  id: string;
  hashtag?: string | null;
  gender?: keyof typeof Gender | null;
  state?: keyof typeof EntityState | null;
  profiles?: Pick<IProfile, 'id'>[] | null;
}

export type NewGenderTag = Omit<IGenderTag, 'id'> & { id: null };

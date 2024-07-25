import dayjs from 'dayjs/esm';
import { ILocation } from 'app/entities/location/location.model';
import { IProfileDiscoverability } from 'app/entities/profile-discoverability/profile-discoverability.model';
import { INationality } from 'app/entities/nationality/nationality.model';
import { IGenderTag } from 'app/entities/gender-tag/gender-tag.model';
import { Gender } from 'app/entities/enumerations/gender.model';
import { Education } from 'app/entities/enumerations/education.model';
import { MaritalStatus } from 'app/entities/enumerations/marital-status.model';
import { ChildPlan } from 'app/entities/enumerations/child-plan.model';
import { ProfileState } from 'app/entities/enumerations/profile-state.model';

export interface IProfile {
  id: string;
  gender?: keyof typeof Gender | null;
  age?: number | null;
  height?: number | null;
  weight?: number | null;
  education?: keyof typeof Education | null;
  profession?: string | null;
  workPlace?: string | null;
  isHealthy?: boolean | null;
  healthIssues?: string | null;
  dateOfBirth?: dayjs.Dayjs | null;
  placeOfBirth?: string | null;
  maritalStatus?: keyof typeof MaritalStatus | null;
  childPlanInFuture?: keyof typeof ChildPlan | null;
  numOfMembersInFamily?: number | null;
  numOfChildrenInFamily?: number | null;
  birthPositionInFamily?: number | null;
  hasOwnDwelling?: boolean | null;
  wantToStudy?: boolean | null;
  wantToWork?: boolean | null;
  readyToRelocate?: boolean | null;
  knowledgeOfLanguages?: string | null;
  skills?: string | null;
  bio?: string | null;
  requirements?: string | null;
  profileState?: keyof typeof ProfileState | null;
  location?: Pick<ILocation, 'id'> | null;
  discoverability?: Pick<IProfileDiscoverability, 'id'> | null;
  nationality?: Pick<INationality, 'id'> | null;
  genderTags?: Pick<IGenderTag, 'id'>[] | null;
}

export type NewProfile = Omit<IProfile, 'id'> & { id: null };

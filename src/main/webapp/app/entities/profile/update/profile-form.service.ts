import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IProfile, NewProfile } from '../profile.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IProfile for edit and NewProfileFormGroupInput for create.
 */
type ProfileFormGroupInput = IProfile | PartialWithRequiredKeyOf<NewProfile>;

type ProfileFormDefaults = Pick<
  NewProfile,
  'id' | 'isHealthy' | 'hasOwnDwelling' | 'wantToStudy' | 'wantToWork' | 'readyToRelocate' | 'genderTags'
>;

type ProfileFormGroupContent = {
  id: FormControl<IProfile['id'] | NewProfile['id']>;
  gender: FormControl<IProfile['gender']>;
  age: FormControl<IProfile['age']>;
  height: FormControl<IProfile['height']>;
  weight: FormControl<IProfile['weight']>;
  education: FormControl<IProfile['education']>;
  profession: FormControl<IProfile['profession']>;
  workPlace: FormControl<IProfile['workPlace']>;
  isHealthy: FormControl<IProfile['isHealthy']>;
  healthIssues: FormControl<IProfile['healthIssues']>;
  dateOfBirth: FormControl<IProfile['dateOfBirth']>;
  placeOfBirth: FormControl<IProfile['placeOfBirth']>;
  maritalStatus: FormControl<IProfile['maritalStatus']>;
  childPlanInFuture: FormControl<IProfile['childPlanInFuture']>;
  numOfMembersInFamily: FormControl<IProfile['numOfMembersInFamily']>;
  numOfChildrenInFamily: FormControl<IProfile['numOfChildrenInFamily']>;
  birthPositionInFamily: FormControl<IProfile['birthPositionInFamily']>;
  hasOwnDwelling: FormControl<IProfile['hasOwnDwelling']>;
  wantToStudy: FormControl<IProfile['wantToStudy']>;
  wantToWork: FormControl<IProfile['wantToWork']>;
  readyToRelocate: FormControl<IProfile['readyToRelocate']>;
  knowledgeOfLanguages: FormControl<IProfile['knowledgeOfLanguages']>;
  skills: FormControl<IProfile['skills']>;
  bio: FormControl<IProfile['bio']>;
  requirements: FormControl<IProfile['requirements']>;
  profileState: FormControl<IProfile['profileState']>;
  location: FormControl<IProfile['location']>;
  discoverability: FormControl<IProfile['discoverability']>;
  nationality: FormControl<IProfile['nationality']>;
  genderTags: FormControl<IProfile['genderTags']>;
};

export type ProfileFormGroup = FormGroup<ProfileFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ProfileFormService {
  createProfileFormGroup(profile: ProfileFormGroupInput = { id: null }): ProfileFormGroup {
    const profileRawValue = {
      ...this.getFormDefaults(),
      ...profile,
    };
    return new FormGroup<ProfileFormGroupContent>({
      id: new FormControl(
        { value: profileRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      gender: new FormControl(profileRawValue.gender, {
        validators: [Validators.required],
      }),
      age: new FormControl(profileRawValue.age, {
        validators: [Validators.required, Validators.min(18), Validators.max(99)],
      }),
      height: new FormControl(profileRawValue.height, {
        validators: [Validators.required, Validators.min(0.0)],
      }),
      weight: new FormControl(profileRawValue.weight, {
        validators: [Validators.required, Validators.min(0.0)],
      }),
      education: new FormControl(profileRawValue.education, {
        validators: [Validators.required],
      }),
      profession: new FormControl(profileRawValue.profession, {
        validators: [Validators.maxLength(256)],
      }),
      workPlace: new FormControl(profileRawValue.workPlace, {
        validators: [Validators.minLength(1)],
      }),
      isHealthy: new FormControl(profileRawValue.isHealthy, {
        validators: [Validators.required],
      }),
      healthIssues: new FormControl(profileRawValue.healthIssues),
      dateOfBirth: new FormControl(profileRawValue.dateOfBirth, {
        validators: [Validators.required],
      }),
      placeOfBirth: new FormControl(profileRawValue.placeOfBirth, {
        validators: [Validators.required, Validators.minLength(1)],
      }),
      maritalStatus: new FormControl(profileRawValue.maritalStatus, {
        validators: [Validators.required],
      }),
      childPlanInFuture: new FormControl(profileRawValue.childPlanInFuture),
      numOfMembersInFamily: new FormControl(profileRawValue.numOfMembersInFamily, {
        validators: [Validators.min(1), Validators.max(20)],
      }),
      numOfChildrenInFamily: new FormControl(profileRawValue.numOfChildrenInFamily, {
        validators: [Validators.min(1), Validators.max(20)],
      }),
      birthPositionInFamily: new FormControl(profileRawValue.birthPositionInFamily, {
        validators: [Validators.min(1), Validators.max(20)],
      }),
      hasOwnDwelling: new FormControl(profileRawValue.hasOwnDwelling),
      wantToStudy: new FormControl(profileRawValue.wantToStudy),
      wantToWork: new FormControl(profileRawValue.wantToWork),
      readyToRelocate: new FormControl(profileRawValue.readyToRelocate),
      knowledgeOfLanguages: new FormControl(profileRawValue.knowledgeOfLanguages),
      skills: new FormControl(profileRawValue.skills, {
        validators: [Validators.maxLength(256)],
      }),
      bio: new FormControl(profileRawValue.bio, {
        validators: [Validators.maxLength(400)],
      }),
      requirements: new FormControl(profileRawValue.requirements, {
        validators: [Validators.maxLength(256)],
      }),
      profileState: new FormControl(profileRawValue.profileState),
      location: new FormControl(profileRawValue.location),
      discoverability: new FormControl(profileRawValue.discoverability),
      nationality: new FormControl(profileRawValue.nationality),
      genderTags: new FormControl(profileRawValue.genderTags ?? []),
    });
  }

  getProfile(form: ProfileFormGroup): IProfile | NewProfile {
    return form.getRawValue() as IProfile | NewProfile;
  }

  resetForm(form: ProfileFormGroup, profile: ProfileFormGroupInput): void {
    const profileRawValue = { ...this.getFormDefaults(), ...profile };
    form.reset(
      {
        ...profileRawValue,
        id: { value: profileRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ProfileFormDefaults {
    return {
      id: null,
      isHealthy: false,
      hasOwnDwelling: false,
      wantToStudy: false,
      wantToWork: false,
      readyToRelocate: false,
      genderTags: [],
    };
  }
}

import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IProfileDiscoverability, NewProfileDiscoverability } from '../profile-discoverability.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IProfileDiscoverability for edit and NewProfileDiscoverabilityFormGroupInput for create.
 */
type ProfileDiscoverabilityFormGroupInput = IProfileDiscoverability | PartialWithRequiredKeyOf<NewProfileDiscoverability>;

type ProfileDiscoverabilityFormDefaults = Pick<NewProfileDiscoverability, 'id' | 'showMyPhoto'>;

type ProfileDiscoverabilityFormGroupContent = {
  id: FormControl<IProfileDiscoverability['id'] | NewProfileDiscoverability['id']>;
  maritalStatus: FormControl<IProfileDiscoverability['maritalStatus']>;
  maxAge: FormControl<IProfileDiscoverability['maxAge']>;
  minAge: FormControl<IProfileDiscoverability['minAge']>;
  showMyPhoto: FormControl<IProfileDiscoverability['showMyPhoto']>;
};

export type ProfileDiscoverabilityFormGroup = FormGroup<ProfileDiscoverabilityFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ProfileDiscoverabilityFormService {
  createProfileDiscoverabilityFormGroup(
    profileDiscoverability: ProfileDiscoverabilityFormGroupInput = { id: null },
  ): ProfileDiscoverabilityFormGroup {
    const profileDiscoverabilityRawValue = {
      ...this.getFormDefaults(),
      ...profileDiscoverability,
    };
    return new FormGroup<ProfileDiscoverabilityFormGroupContent>({
      id: new FormControl(
        { value: profileDiscoverabilityRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      maritalStatus: new FormControl(profileDiscoverabilityRawValue.maritalStatus),
      maxAge: new FormControl(profileDiscoverabilityRawValue.maxAge, {
        validators: [Validators.min(18), Validators.max(99)],
      }),
      minAge: new FormControl(profileDiscoverabilityRawValue.minAge, {
        validators: [Validators.min(18), Validators.max(99)],
      }),
      showMyPhoto: new FormControl(profileDiscoverabilityRawValue.showMyPhoto),
    });
  }

  getProfileDiscoverability(form: ProfileDiscoverabilityFormGroup): IProfileDiscoverability | NewProfileDiscoverability {
    return form.getRawValue() as IProfileDiscoverability | NewProfileDiscoverability;
  }

  resetForm(form: ProfileDiscoverabilityFormGroup, profileDiscoverability: ProfileDiscoverabilityFormGroupInput): void {
    const profileDiscoverabilityRawValue = { ...this.getFormDefaults(), ...profileDiscoverability };
    form.reset(
      {
        ...profileDiscoverabilityRawValue,
        id: { value: profileDiscoverabilityRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ProfileDiscoverabilityFormDefaults {
    return {
      id: null,
      showMyPhoto: false,
    };
  }
}

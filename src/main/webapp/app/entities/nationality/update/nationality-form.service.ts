import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { INationality, NewNationality } from '../nationality.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts INationality for edit and NewNationalityFormGroupInput for create.
 */
type NationalityFormGroupInput = INationality | PartialWithRequiredKeyOf<NewNationality>;

type NationalityFormDefaults = Pick<NewNationality, 'id'>;

type NationalityFormGroupContent = {
  id: FormControl<INationality['id'] | NewNationality['id']>;
  name: FormControl<INationality['name']>;
  ord: FormControl<INationality['ord']>;
  state: FormControl<INationality['state']>;
};

export type NationalityFormGroup = FormGroup<NationalityFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class NationalityFormService {
  createNationalityFormGroup(nationality: NationalityFormGroupInput = { id: null }): NationalityFormGroup {
    const nationalityRawValue = {
      ...this.getFormDefaults(),
      ...nationality,
    };
    return new FormGroup<NationalityFormGroupContent>({
      id: new FormControl(
        { value: nationalityRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      name: new FormControl(nationalityRawValue.name, {
        validators: [Validators.required],
      }),
      ord: new FormControl(nationalityRawValue.ord),
      state: new FormControl(nationalityRawValue.state),
    });
  }

  getNationality(form: NationalityFormGroup): INationality | NewNationality {
    return form.getRawValue() as INationality | NewNationality;
  }

  resetForm(form: NationalityFormGroup, nationality: NationalityFormGroupInput): void {
    const nationalityRawValue = { ...this.getFormDefaults(), ...nationality };
    form.reset(
      {
        ...nationalityRawValue,
        id: { value: nationalityRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): NationalityFormDefaults {
    return {
      id: null,
    };
  }
}

import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IGenderTag, NewGenderTag } from '../gender-tag.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IGenderTag for edit and NewGenderTagFormGroupInput for create.
 */
type GenderTagFormGroupInput = IGenderTag | PartialWithRequiredKeyOf<NewGenderTag>;

type GenderTagFormDefaults = Pick<NewGenderTag, 'id' | 'profiles'>;

type GenderTagFormGroupContent = {
  id: FormControl<IGenderTag['id'] | NewGenderTag['id']>;
  hashtag: FormControl<IGenderTag['hashtag']>;
  gender: FormControl<IGenderTag['gender']>;
  state: FormControl<IGenderTag['state']>;
  profiles: FormControl<IGenderTag['profiles']>;
};

export type GenderTagFormGroup = FormGroup<GenderTagFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class GenderTagFormService {
  createGenderTagFormGroup(genderTag: GenderTagFormGroupInput = { id: null }): GenderTagFormGroup {
    const genderTagRawValue = {
      ...this.getFormDefaults(),
      ...genderTag,
    };
    return new FormGroup<GenderTagFormGroupContent>({
      id: new FormControl(
        { value: genderTagRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      hashtag: new FormControl(genderTagRawValue.hashtag, {
        validators: [Validators.required, Validators.minLength(1), Validators.maxLength(50)],
      }),
      gender: new FormControl(genderTagRawValue.gender, {
        validators: [Validators.required],
      }),
      state: new FormControl(genderTagRawValue.state),
      profiles: new FormControl(genderTagRawValue.profiles ?? []),
    });
  }

  getGenderTag(form: GenderTagFormGroup): IGenderTag | NewGenderTag {
    return form.getRawValue() as IGenderTag | NewGenderTag;
  }

  resetForm(form: GenderTagFormGroup, genderTag: GenderTagFormGroupInput): void {
    const genderTagRawValue = { ...this.getFormDefaults(), ...genderTag };
    form.reset(
      {
        ...genderTagRawValue,
        id: { value: genderTagRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): GenderTagFormDefaults {
    return {
      id: null,
      profiles: [],
    };
  }
}

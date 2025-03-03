import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IContactInfo, NewContactInfo } from '../contact-info.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IContactInfo for edit and NewContactInfoFormGroupInput for create.
 */
type ContactInfoFormGroupInput = IContactInfo | PartialWithRequiredKeyOf<NewContactInfo>;

type ContactInfoFormDefaults = Pick<NewContactInfo, 'id'>;

type ContactInfoFormGroupContent = {
  id: FormControl<IContactInfo['id'] | NewContactInfo['id']>;
  type: FormControl<IContactInfo['type']>;
  text: FormControl<IContactInfo['text']>;
  ord: FormControl<IContactInfo['ord']>;
  state: FormControl<IContactInfo['state']>;
  profile: FormControl<IContactInfo['profile']>;
};

export type ContactInfoFormGroup = FormGroup<ContactInfoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ContactInfoFormService {
  createContactInfoFormGroup(contactInfo: ContactInfoFormGroupInput = { id: null }): ContactInfoFormGroup {
    const contactInfoRawValue = {
      ...this.getFormDefaults(),
      ...contactInfo,
    };
    return new FormGroup<ContactInfoFormGroupContent>({
      id: new FormControl(
        { value: contactInfoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      type: new FormControl(contactInfoRawValue.type, {
        validators: [Validators.required],
      }),
      text: new FormControl(contactInfoRawValue.text, {
        validators: [Validators.required, Validators.maxLength(256)],
      }),
      ord: new FormControl(contactInfoRawValue.ord),
      state: new FormControl(contactInfoRawValue.state),
      profile: new FormControl(contactInfoRawValue.profile),
    });
  }

  getContactInfo(form: ContactInfoFormGroup): IContactInfo | NewContactInfo {
    return form.getRawValue() as IContactInfo | NewContactInfo;
  }

  resetForm(form: ContactInfoFormGroup, contactInfo: ContactInfoFormGroupInput): void {
    const contactInfoRawValue = { ...this.getFormDefaults(), ...contactInfo };
    form.reset(
      {
        ...contactInfoRawValue,
        id: { value: contactInfoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ContactInfoFormDefaults {
    return {
      id: null,
    };
  }
}

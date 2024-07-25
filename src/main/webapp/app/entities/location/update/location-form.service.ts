import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ILocation, NewLocation } from '../location.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ILocation for edit and NewLocationFormGroupInput for create.
 */
type LocationFormGroupInput = ILocation | PartialWithRequiredKeyOf<NewLocation>;

type LocationFormDefaults = Pick<NewLocation, 'id'>;

type LocationFormGroupContent = {
  id: FormControl<ILocation['id'] | NewLocation['id']>;
  lat: FormControl<ILocation['lat']>;
  lon: FormControl<ILocation['lon']>;
  country: FormControl<ILocation['country']>;
  city: FormControl<ILocation['city']>;
  district: FormControl<ILocation['district']>;
  ord: FormControl<ILocation['ord']>;
  state: FormControl<ILocation['state']>;
};

export type LocationFormGroup = FormGroup<LocationFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class LocationFormService {
  createLocationFormGroup(location: LocationFormGroupInput = { id: null }): LocationFormGroup {
    const locationRawValue = {
      ...this.getFormDefaults(),
      ...location,
    };
    return new FormGroup<LocationFormGroupContent>({
      id: new FormControl(
        { value: locationRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      lat: new FormControl(locationRawValue.lat, {
        validators: [Validators.required],
      }),
      lon: new FormControl(locationRawValue.lon, {
        validators: [Validators.required],
      }),
      country: new FormControl(locationRawValue.country, {
        validators: [Validators.required, Validators.minLength(2), Validators.maxLength(2)],
      }),
      city: new FormControl(locationRawValue.city, {
        validators: [Validators.required, Validators.minLength(3), Validators.maxLength(50)],
      }),
      district: new FormControl(locationRawValue.district, {
        validators: [Validators.required, Validators.minLength(3), Validators.maxLength(50)],
      }),
      ord: new FormControl(locationRawValue.ord),
      state: new FormControl(locationRawValue.state),
    });
  }

  getLocation(form: LocationFormGroup): ILocation | NewLocation {
    return form.getRawValue() as ILocation | NewLocation;
  }

  resetForm(form: LocationFormGroup, location: LocationFormGroupInput): void {
    const locationRawValue = { ...this.getFormDefaults(), ...location };
    form.reset(
      {
        ...locationRawValue,
        id: { value: locationRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): LocationFormDefaults {
    return {
      id: null,
    };
  }
}

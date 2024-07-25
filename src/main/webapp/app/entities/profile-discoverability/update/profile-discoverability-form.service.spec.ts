import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../profile-discoverability.test-samples';

import { ProfileDiscoverabilityFormService } from './profile-discoverability-form.service';

describe('ProfileDiscoverability Form Service', () => {
  let service: ProfileDiscoverabilityFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProfileDiscoverabilityFormService);
  });

  describe('Service methods', () => {
    describe('createProfileDiscoverabilityFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createProfileDiscoverabilityFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            maritalStatus: expect.any(Object),
            maxAge: expect.any(Object),
            minAge: expect.any(Object),
            showMyPhoto: expect.any(Object),
          }),
        );
      });

      it('passing IProfileDiscoverability should create a new form with FormGroup', () => {
        const formGroup = service.createProfileDiscoverabilityFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            maritalStatus: expect.any(Object),
            maxAge: expect.any(Object),
            minAge: expect.any(Object),
            showMyPhoto: expect.any(Object),
          }),
        );
      });
    });

    describe('getProfileDiscoverability', () => {
      it('should return NewProfileDiscoverability for default ProfileDiscoverability initial value', () => {
        const formGroup = service.createProfileDiscoverabilityFormGroup(sampleWithNewData);

        const profileDiscoverability = service.getProfileDiscoverability(formGroup) as any;

        expect(profileDiscoverability).toMatchObject(sampleWithNewData);
      });

      it('should return NewProfileDiscoverability for empty ProfileDiscoverability initial value', () => {
        const formGroup = service.createProfileDiscoverabilityFormGroup();

        const profileDiscoverability = service.getProfileDiscoverability(formGroup) as any;

        expect(profileDiscoverability).toMatchObject({});
      });

      it('should return IProfileDiscoverability', () => {
        const formGroup = service.createProfileDiscoverabilityFormGroup(sampleWithRequiredData);

        const profileDiscoverability = service.getProfileDiscoverability(formGroup) as any;

        expect(profileDiscoverability).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IProfileDiscoverability should not enable id FormControl', () => {
        const formGroup = service.createProfileDiscoverabilityFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewProfileDiscoverability should disable id FormControl', () => {
        const formGroup = service.createProfileDiscoverabilityFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});

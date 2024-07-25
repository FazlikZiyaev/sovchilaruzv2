import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../profile.test-samples';

import { ProfileFormService } from './profile-form.service';

describe('Profile Form Service', () => {
  let service: ProfileFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProfileFormService);
  });

  describe('Service methods', () => {
    describe('createProfileFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createProfileFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            gender: expect.any(Object),
            age: expect.any(Object),
            height: expect.any(Object),
            weight: expect.any(Object),
            education: expect.any(Object),
            profession: expect.any(Object),
            workPlace: expect.any(Object),
            isHealthy: expect.any(Object),
            healthIssues: expect.any(Object),
            dateOfBirth: expect.any(Object),
            placeOfBirth: expect.any(Object),
            maritalStatus: expect.any(Object),
            childPlanInFuture: expect.any(Object),
            numOfMembersInFamily: expect.any(Object),
            numOfChildrenInFamily: expect.any(Object),
            birthPositionInFamily: expect.any(Object),
            hasOwnDwelling: expect.any(Object),
            wantToStudy: expect.any(Object),
            wantToWork: expect.any(Object),
            readyToRelocate: expect.any(Object),
            knowledgeOfLanguages: expect.any(Object),
            skills: expect.any(Object),
            bio: expect.any(Object),
            requirements: expect.any(Object),
            profileState: expect.any(Object),
            location: expect.any(Object),
            discoverability: expect.any(Object),
            nationality: expect.any(Object),
            genderTags: expect.any(Object),
          }),
        );
      });

      it('passing IProfile should create a new form with FormGroup', () => {
        const formGroup = service.createProfileFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            gender: expect.any(Object),
            age: expect.any(Object),
            height: expect.any(Object),
            weight: expect.any(Object),
            education: expect.any(Object),
            profession: expect.any(Object),
            workPlace: expect.any(Object),
            isHealthy: expect.any(Object),
            healthIssues: expect.any(Object),
            dateOfBirth: expect.any(Object),
            placeOfBirth: expect.any(Object),
            maritalStatus: expect.any(Object),
            childPlanInFuture: expect.any(Object),
            numOfMembersInFamily: expect.any(Object),
            numOfChildrenInFamily: expect.any(Object),
            birthPositionInFamily: expect.any(Object),
            hasOwnDwelling: expect.any(Object),
            wantToStudy: expect.any(Object),
            wantToWork: expect.any(Object),
            readyToRelocate: expect.any(Object),
            knowledgeOfLanguages: expect.any(Object),
            skills: expect.any(Object),
            bio: expect.any(Object),
            requirements: expect.any(Object),
            profileState: expect.any(Object),
            location: expect.any(Object),
            discoverability: expect.any(Object),
            nationality: expect.any(Object),
            genderTags: expect.any(Object),
          }),
        );
      });
    });

    describe('getProfile', () => {
      it('should return NewProfile for default Profile initial value', () => {
        const formGroup = service.createProfileFormGroup(sampleWithNewData);

        const profile = service.getProfile(formGroup) as any;

        expect(profile).toMatchObject(sampleWithNewData);
      });

      it('should return NewProfile for empty Profile initial value', () => {
        const formGroup = service.createProfileFormGroup();

        const profile = service.getProfile(formGroup) as any;

        expect(profile).toMatchObject({});
      });

      it('should return IProfile', () => {
        const formGroup = service.createProfileFormGroup(sampleWithRequiredData);

        const profile = service.getProfile(formGroup) as any;

        expect(profile).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IProfile should not enable id FormControl', () => {
        const formGroup = service.createProfileFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewProfile should disable id FormControl', () => {
        const formGroup = service.createProfileFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});

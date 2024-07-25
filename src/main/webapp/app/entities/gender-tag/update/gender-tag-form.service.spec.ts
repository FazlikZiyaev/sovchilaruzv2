import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../gender-tag.test-samples';

import { GenderTagFormService } from './gender-tag-form.service';

describe('GenderTag Form Service', () => {
  let service: GenderTagFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GenderTagFormService);
  });

  describe('Service methods', () => {
    describe('createGenderTagFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createGenderTagFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            hashtag: expect.any(Object),
            gender: expect.any(Object),
            state: expect.any(Object),
            profiles: expect.any(Object),
          }),
        );
      });

      it('passing IGenderTag should create a new form with FormGroup', () => {
        const formGroup = service.createGenderTagFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            hashtag: expect.any(Object),
            gender: expect.any(Object),
            state: expect.any(Object),
            profiles: expect.any(Object),
          }),
        );
      });
    });

    describe('getGenderTag', () => {
      it('should return NewGenderTag for default GenderTag initial value', () => {
        const formGroup = service.createGenderTagFormGroup(sampleWithNewData);

        const genderTag = service.getGenderTag(formGroup) as any;

        expect(genderTag).toMatchObject(sampleWithNewData);
      });

      it('should return NewGenderTag for empty GenderTag initial value', () => {
        const formGroup = service.createGenderTagFormGroup();

        const genderTag = service.getGenderTag(formGroup) as any;

        expect(genderTag).toMatchObject({});
      });

      it('should return IGenderTag', () => {
        const formGroup = service.createGenderTagFormGroup(sampleWithRequiredData);

        const genderTag = service.getGenderTag(formGroup) as any;

        expect(genderTag).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IGenderTag should not enable id FormControl', () => {
        const formGroup = service.createGenderTagFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewGenderTag should disable id FormControl', () => {
        const formGroup = service.createGenderTagFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});

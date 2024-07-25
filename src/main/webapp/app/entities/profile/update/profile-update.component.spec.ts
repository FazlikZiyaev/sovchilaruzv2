import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ILocation } from 'app/entities/location/location.model';
import { LocationService } from 'app/entities/location/service/location.service';
import { IProfileDiscoverability } from 'app/entities/profile-discoverability/profile-discoverability.model';
import { ProfileDiscoverabilityService } from 'app/entities/profile-discoverability/service/profile-discoverability.service';
import { INationality } from 'app/entities/nationality/nationality.model';
import { NationalityService } from 'app/entities/nationality/service/nationality.service';
import { IGenderTag } from 'app/entities/gender-tag/gender-tag.model';
import { GenderTagService } from 'app/entities/gender-tag/service/gender-tag.service';
import { IProfile } from '../profile.model';
import { ProfileService } from '../service/profile.service';
import { ProfileFormService } from './profile-form.service';

import { ProfileUpdateComponent } from './profile-update.component';

describe('Profile Management Update Component', () => {
  let comp: ProfileUpdateComponent;
  let fixture: ComponentFixture<ProfileUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let profileFormService: ProfileFormService;
  let profileService: ProfileService;
  let locationService: LocationService;
  let profileDiscoverabilityService: ProfileDiscoverabilityService;
  let nationalityService: NationalityService;
  let genderTagService: GenderTagService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), ProfileUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(ProfileUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProfileUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    profileFormService = TestBed.inject(ProfileFormService);
    profileService = TestBed.inject(ProfileService);
    locationService = TestBed.inject(LocationService);
    profileDiscoverabilityService = TestBed.inject(ProfileDiscoverabilityService);
    nationalityService = TestBed.inject(NationalityService);
    genderTagService = TestBed.inject(GenderTagService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call location query and add missing value', () => {
      const profile: IProfile = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
      const location: ILocation = { id: 29936 };
      profile.location = location;

      const locationCollection: ILocation[] = [{ id: 23661 }];
      jest.spyOn(locationService, 'query').mockReturnValue(of(new HttpResponse({ body: locationCollection })));
      const expectedCollection: ILocation[] = [location, ...locationCollection];
      jest.spyOn(locationService, 'addLocationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ profile });
      comp.ngOnInit();

      expect(locationService.query).toHaveBeenCalled();
      expect(locationService.addLocationToCollectionIfMissing).toHaveBeenCalledWith(locationCollection, location);
      expect(comp.locationsCollection).toEqual(expectedCollection);
    });

    it('Should call ProfileDiscoverability query and add missing value', () => {
      const profile: IProfile = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
      const discoverability: IProfileDiscoverability = { id: 18039 };
      profile.discoverability = discoverability;

      const profileDiscoverabilityCollection: IProfileDiscoverability[] = [{ id: 20218 }];
      jest.spyOn(profileDiscoverabilityService, 'query').mockReturnValue(of(new HttpResponse({ body: profileDiscoverabilityCollection })));
      const additionalProfileDiscoverabilities = [discoverability];
      const expectedCollection: IProfileDiscoverability[] = [...additionalProfileDiscoverabilities, ...profileDiscoverabilityCollection];
      jest.spyOn(profileDiscoverabilityService, 'addProfileDiscoverabilityToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ profile });
      comp.ngOnInit();

      expect(profileDiscoverabilityService.query).toHaveBeenCalled();
      expect(profileDiscoverabilityService.addProfileDiscoverabilityToCollectionIfMissing).toHaveBeenCalledWith(
        profileDiscoverabilityCollection,
        ...additionalProfileDiscoverabilities.map(expect.objectContaining),
      );
      expect(comp.profileDiscoverabilitiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Nationality query and add missing value', () => {
      const profile: IProfile = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
      const nationality: INationality = { id: '4df3748a-2899-464c-ab3f-ad18527ea6ec' };
      profile.nationality = nationality;

      const nationalityCollection: INationality[] = [{ id: '4b0e71b6-bb2b-448e-abce-4c3e95b20db2' }];
      jest.spyOn(nationalityService, 'query').mockReturnValue(of(new HttpResponse({ body: nationalityCollection })));
      const additionalNationalities = [nationality];
      const expectedCollection: INationality[] = [...additionalNationalities, ...nationalityCollection];
      jest.spyOn(nationalityService, 'addNationalityToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ profile });
      comp.ngOnInit();

      expect(nationalityService.query).toHaveBeenCalled();
      expect(nationalityService.addNationalityToCollectionIfMissing).toHaveBeenCalledWith(
        nationalityCollection,
        ...additionalNationalities.map(expect.objectContaining),
      );
      expect(comp.nationalitiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call GenderTag query and add missing value', () => {
      const profile: IProfile = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
      const genderTags: IGenderTag[] = [{ id: '6d69cd17-2809-4baf-9c4a-f35c64e62fce' }];
      profile.genderTags = genderTags;

      const genderTagCollection: IGenderTag[] = [{ id: '8927f423-55d9-46e4-b7bb-eb0a47047dfb' }];
      jest.spyOn(genderTagService, 'query').mockReturnValue(of(new HttpResponse({ body: genderTagCollection })));
      const additionalGenderTags = [...genderTags];
      const expectedCollection: IGenderTag[] = [...additionalGenderTags, ...genderTagCollection];
      jest.spyOn(genderTagService, 'addGenderTagToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ profile });
      comp.ngOnInit();

      expect(genderTagService.query).toHaveBeenCalled();
      expect(genderTagService.addGenderTagToCollectionIfMissing).toHaveBeenCalledWith(
        genderTagCollection,
        ...additionalGenderTags.map(expect.objectContaining),
      );
      expect(comp.genderTagsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const profile: IProfile = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
      const location: ILocation = { id: 3990 };
      profile.location = location;
      const discoverability: IProfileDiscoverability = { id: 11619 };
      profile.discoverability = discoverability;
      const nationality: INationality = { id: '86b81238-b42e-4e60-979e-d9c7b93d5407' };
      profile.nationality = nationality;
      const genderTags: IGenderTag = { id: 'a4854052-4407-4857-8a1a-55ee67e99ff0' };
      profile.genderTags = [genderTags];

      activatedRoute.data = of({ profile });
      comp.ngOnInit();

      expect(comp.locationsCollection).toContain(location);
      expect(comp.profileDiscoverabilitiesSharedCollection).toContain(discoverability);
      expect(comp.nationalitiesSharedCollection).toContain(nationality);
      expect(comp.genderTagsSharedCollection).toContain(genderTags);
      expect(comp.profile).toEqual(profile);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProfile>>();
      const profile = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(profileFormService, 'getProfile').mockReturnValue(profile);
      jest.spyOn(profileService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ profile });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: profile }));
      saveSubject.complete();

      // THEN
      expect(profileFormService.getProfile).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(profileService.update).toHaveBeenCalledWith(expect.objectContaining(profile));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProfile>>();
      const profile = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(profileFormService, 'getProfile').mockReturnValue({ id: null });
      jest.spyOn(profileService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ profile: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: profile }));
      saveSubject.complete();

      // THEN
      expect(profileFormService.getProfile).toHaveBeenCalled();
      expect(profileService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProfile>>();
      const profile = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(profileService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ profile });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(profileService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareLocation', () => {
      it('Should forward to locationService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(locationService, 'compareLocation');
        comp.compareLocation(entity, entity2);
        expect(locationService.compareLocation).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareProfileDiscoverability', () => {
      it('Should forward to profileDiscoverabilityService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(profileDiscoverabilityService, 'compareProfileDiscoverability');
        comp.compareProfileDiscoverability(entity, entity2);
        expect(profileDiscoverabilityService.compareProfileDiscoverability).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareNationality', () => {
      it('Should forward to nationalityService', () => {
        const entity = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
        jest.spyOn(nationalityService, 'compareNationality');
        comp.compareNationality(entity, entity2);
        expect(nationalityService.compareNationality).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareGenderTag', () => {
      it('Should forward to genderTagService', () => {
        const entity = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
        jest.spyOn(genderTagService, 'compareGenderTag');
        comp.compareGenderTag(entity, entity2);
        expect(genderTagService.compareGenderTag).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});

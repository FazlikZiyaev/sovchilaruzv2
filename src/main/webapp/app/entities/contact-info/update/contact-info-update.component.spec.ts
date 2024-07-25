import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IProfile } from 'app/entities/profile/profile.model';
import { ProfileService } from 'app/entities/profile/service/profile.service';
import { ContactInfoService } from '../service/contact-info.service';
import { IContactInfo } from '../contact-info.model';
import { ContactInfoFormService } from './contact-info-form.service';

import { ContactInfoUpdateComponent } from './contact-info-update.component';

describe('ContactInfo Management Update Component', () => {
  let comp: ContactInfoUpdateComponent;
  let fixture: ComponentFixture<ContactInfoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let contactInfoFormService: ContactInfoFormService;
  let contactInfoService: ContactInfoService;
  let profileService: ProfileService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), ContactInfoUpdateComponent],
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
      .overrideTemplate(ContactInfoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ContactInfoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    contactInfoFormService = TestBed.inject(ContactInfoFormService);
    contactInfoService = TestBed.inject(ContactInfoService);
    profileService = TestBed.inject(ProfileService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Profile query and add missing value', () => {
      const contactInfo: IContactInfo = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
      const profile: IProfile = { id: '9830b4da-9656-4d53-acce-59d34173f02b' };
      contactInfo.profile = profile;

      const profileCollection: IProfile[] = [{ id: 'c79e142d-5dc3-4549-bd15-71ce24d38596' }];
      jest.spyOn(profileService, 'query').mockReturnValue(of(new HttpResponse({ body: profileCollection })));
      const additionalProfiles = [profile];
      const expectedCollection: IProfile[] = [...additionalProfiles, ...profileCollection];
      jest.spyOn(profileService, 'addProfileToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ contactInfo });
      comp.ngOnInit();

      expect(profileService.query).toHaveBeenCalled();
      expect(profileService.addProfileToCollectionIfMissing).toHaveBeenCalledWith(
        profileCollection,
        ...additionalProfiles.map(expect.objectContaining),
      );
      expect(comp.profilesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const contactInfo: IContactInfo = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
      const profile: IProfile = { id: '22e895ca-0c00-4909-9280-b73de0420414' };
      contactInfo.profile = profile;

      activatedRoute.data = of({ contactInfo });
      comp.ngOnInit();

      expect(comp.profilesSharedCollection).toContain(profile);
      expect(comp.contactInfo).toEqual(contactInfo);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IContactInfo>>();
      const contactInfo = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(contactInfoFormService, 'getContactInfo').mockReturnValue(contactInfo);
      jest.spyOn(contactInfoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contactInfo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: contactInfo }));
      saveSubject.complete();

      // THEN
      expect(contactInfoFormService.getContactInfo).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(contactInfoService.update).toHaveBeenCalledWith(expect.objectContaining(contactInfo));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IContactInfo>>();
      const contactInfo = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(contactInfoFormService, 'getContactInfo').mockReturnValue({ id: null });
      jest.spyOn(contactInfoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contactInfo: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: contactInfo }));
      saveSubject.complete();

      // THEN
      expect(contactInfoFormService.getContactInfo).toHaveBeenCalled();
      expect(contactInfoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IContactInfo>>();
      const contactInfo = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(contactInfoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contactInfo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(contactInfoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareProfile', () => {
      it('Should forward to profileService', () => {
        const entity = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
        jest.spyOn(profileService, 'compareProfile');
        comp.compareProfile(entity, entity2);
        expect(profileService.compareProfile).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});

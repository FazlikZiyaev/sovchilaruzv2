import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ProfileDiscoverabilityService } from '../service/profile-discoverability.service';
import { IProfileDiscoverability } from '../profile-discoverability.model';
import { ProfileDiscoverabilityFormService } from './profile-discoverability-form.service';

import { ProfileDiscoverabilityUpdateComponent } from './profile-discoverability-update.component';

describe('ProfileDiscoverability Management Update Component', () => {
  let comp: ProfileDiscoverabilityUpdateComponent;
  let fixture: ComponentFixture<ProfileDiscoverabilityUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let profileDiscoverabilityFormService: ProfileDiscoverabilityFormService;
  let profileDiscoverabilityService: ProfileDiscoverabilityService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), ProfileDiscoverabilityUpdateComponent],
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
      .overrideTemplate(ProfileDiscoverabilityUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProfileDiscoverabilityUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    profileDiscoverabilityFormService = TestBed.inject(ProfileDiscoverabilityFormService);
    profileDiscoverabilityService = TestBed.inject(ProfileDiscoverabilityService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const profileDiscoverability: IProfileDiscoverability = { id: 456 };

      activatedRoute.data = of({ profileDiscoverability });
      comp.ngOnInit();

      expect(comp.profileDiscoverability).toEqual(profileDiscoverability);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProfileDiscoverability>>();
      const profileDiscoverability = { id: 123 };
      jest.spyOn(profileDiscoverabilityFormService, 'getProfileDiscoverability').mockReturnValue(profileDiscoverability);
      jest.spyOn(profileDiscoverabilityService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ profileDiscoverability });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: profileDiscoverability }));
      saveSubject.complete();

      // THEN
      expect(profileDiscoverabilityFormService.getProfileDiscoverability).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(profileDiscoverabilityService.update).toHaveBeenCalledWith(expect.objectContaining(profileDiscoverability));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProfileDiscoverability>>();
      const profileDiscoverability = { id: 123 };
      jest.spyOn(profileDiscoverabilityFormService, 'getProfileDiscoverability').mockReturnValue({ id: null });
      jest.spyOn(profileDiscoverabilityService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ profileDiscoverability: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: profileDiscoverability }));
      saveSubject.complete();

      // THEN
      expect(profileDiscoverabilityFormService.getProfileDiscoverability).toHaveBeenCalled();
      expect(profileDiscoverabilityService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProfileDiscoverability>>();
      const profileDiscoverability = { id: 123 };
      jest.spyOn(profileDiscoverabilityService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ profileDiscoverability });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(profileDiscoverabilityService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});

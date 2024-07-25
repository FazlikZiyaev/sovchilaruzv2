import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { GenderTagService } from '../service/gender-tag.service';
import { IGenderTag } from '../gender-tag.model';
import { GenderTagFormService } from './gender-tag-form.service';

import { GenderTagUpdateComponent } from './gender-tag-update.component';

describe('GenderTag Management Update Component', () => {
  let comp: GenderTagUpdateComponent;
  let fixture: ComponentFixture<GenderTagUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let genderTagFormService: GenderTagFormService;
  let genderTagService: GenderTagService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), GenderTagUpdateComponent],
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
      .overrideTemplate(GenderTagUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(GenderTagUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    genderTagFormService = TestBed.inject(GenderTagFormService);
    genderTagService = TestBed.inject(GenderTagService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const genderTag: IGenderTag = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };

      activatedRoute.data = of({ genderTag });
      comp.ngOnInit();

      expect(comp.genderTag).toEqual(genderTag);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGenderTag>>();
      const genderTag = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(genderTagFormService, 'getGenderTag').mockReturnValue(genderTag);
      jest.spyOn(genderTagService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ genderTag });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: genderTag }));
      saveSubject.complete();

      // THEN
      expect(genderTagFormService.getGenderTag).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(genderTagService.update).toHaveBeenCalledWith(expect.objectContaining(genderTag));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGenderTag>>();
      const genderTag = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(genderTagFormService, 'getGenderTag').mockReturnValue({ id: null });
      jest.spyOn(genderTagService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ genderTag: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: genderTag }));
      saveSubject.complete();

      // THEN
      expect(genderTagFormService.getGenderTag).toHaveBeenCalled();
      expect(genderTagService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGenderTag>>();
      const genderTag = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(genderTagService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ genderTag });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(genderTagService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});

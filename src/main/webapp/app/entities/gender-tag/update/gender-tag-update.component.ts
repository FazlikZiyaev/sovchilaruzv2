import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { Gender } from 'app/entities/enumerations/gender.model';
import { EntityState } from 'app/entities/enumerations/entity-state.model';
import { IGenderTag } from '../gender-tag.model';
import { GenderTagService } from '../service/gender-tag.service';
import { GenderTagFormService, GenderTagFormGroup } from './gender-tag-form.service';

@Component({
  standalone: true,
  selector: 'jhi-gender-tag-update',
  templateUrl: './gender-tag-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class GenderTagUpdateComponent implements OnInit {
  isSaving = false;
  genderTag: IGenderTag | null = null;
  genderValues = Object.keys(Gender);
  entityStateValues = Object.keys(EntityState);

  editForm: GenderTagFormGroup = this.genderTagFormService.createGenderTagFormGroup();

  constructor(
    protected genderTagService: GenderTagService,
    protected genderTagFormService: GenderTagFormService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ genderTag }) => {
      this.genderTag = genderTag;
      if (genderTag) {
        this.updateForm(genderTag);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const genderTag = this.genderTagFormService.getGenderTag(this.editForm);
    if (genderTag.id !== null) {
      this.subscribeToSaveResponse(this.genderTagService.update(genderTag));
    } else {
      this.subscribeToSaveResponse(this.genderTagService.create(genderTag));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGenderTag>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(genderTag: IGenderTag): void {
    this.genderTag = genderTag;
    this.genderTagFormService.resetForm(this.editForm, genderTag);
  }
}

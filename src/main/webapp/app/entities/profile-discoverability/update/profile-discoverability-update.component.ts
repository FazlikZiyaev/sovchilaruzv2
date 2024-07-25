import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { MaritalStatus } from 'app/entities/enumerations/marital-status.model';
import { IProfileDiscoverability } from '../profile-discoverability.model';
import { ProfileDiscoverabilityService } from '../service/profile-discoverability.service';
import { ProfileDiscoverabilityFormService, ProfileDiscoverabilityFormGroup } from './profile-discoverability-form.service';

@Component({
  standalone: true,
  selector: 'jhi-profile-discoverability-update',
  templateUrl: './profile-discoverability-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ProfileDiscoverabilityUpdateComponent implements OnInit {
  isSaving = false;
  profileDiscoverability: IProfileDiscoverability | null = null;
  maritalStatusValues = Object.keys(MaritalStatus);

  editForm: ProfileDiscoverabilityFormGroup = this.profileDiscoverabilityFormService.createProfileDiscoverabilityFormGroup();

  constructor(
    protected profileDiscoverabilityService: ProfileDiscoverabilityService,
    protected profileDiscoverabilityFormService: ProfileDiscoverabilityFormService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ profileDiscoverability }) => {
      this.profileDiscoverability = profileDiscoverability;
      if (profileDiscoverability) {
        this.updateForm(profileDiscoverability);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const profileDiscoverability = this.profileDiscoverabilityFormService.getProfileDiscoverability(this.editForm);
    if (profileDiscoverability.id !== null) {
      this.subscribeToSaveResponse(this.profileDiscoverabilityService.update(profileDiscoverability));
    } else {
      this.subscribeToSaveResponse(this.profileDiscoverabilityService.create(profileDiscoverability));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProfileDiscoverability>>): void {
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

  protected updateForm(profileDiscoverability: IProfileDiscoverability): void {
    this.profileDiscoverability = profileDiscoverability;
    this.profileDiscoverabilityFormService.resetForm(this.editForm, profileDiscoverability);
  }
}

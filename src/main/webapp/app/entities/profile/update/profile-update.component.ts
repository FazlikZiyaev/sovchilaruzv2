import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ILocation } from 'app/entities/location/location.model';
import { LocationService } from 'app/entities/location/service/location.service';
import { IProfileDiscoverability } from 'app/entities/profile-discoverability/profile-discoverability.model';
import { ProfileDiscoverabilityService } from 'app/entities/profile-discoverability/service/profile-discoverability.service';
import { INationality } from 'app/entities/nationality/nationality.model';
import { NationalityService } from 'app/entities/nationality/service/nationality.service';
import { IGenderTag } from 'app/entities/gender-tag/gender-tag.model';
import { GenderTagService } from 'app/entities/gender-tag/service/gender-tag.service';
import { Gender } from 'app/entities/enumerations/gender.model';
import { Education } from 'app/entities/enumerations/education.model';
import { MaritalStatus } from 'app/entities/enumerations/marital-status.model';
import { ChildPlan } from 'app/entities/enumerations/child-plan.model';
import { ProfileState } from 'app/entities/enumerations/profile-state.model';
import { ProfileService } from '../service/profile.service';
import { IProfile } from '../profile.model';
import { ProfileFormService, ProfileFormGroup } from './profile-form.service';

@Component({
  standalone: true,
  selector: 'jhi-profile-update',
  templateUrl: './profile-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ProfileUpdateComponent implements OnInit {
  isSaving = false;
  profile: IProfile | null = null;
  genderValues = Object.keys(Gender);
  educationValues = Object.keys(Education);
  maritalStatusValues = Object.keys(MaritalStatus);
  childPlanValues = Object.keys(ChildPlan);
  profileStateValues = Object.keys(ProfileState);

  locationsCollection: ILocation[] = [];
  profileDiscoverabilitiesSharedCollection: IProfileDiscoverability[] = [];
  nationalitiesSharedCollection: INationality[] = [];
  genderTagsSharedCollection: IGenderTag[] = [];

  editForm: ProfileFormGroup = this.profileFormService.createProfileFormGroup();

  constructor(
    protected profileService: ProfileService,
    protected profileFormService: ProfileFormService,
    protected locationService: LocationService,
    protected profileDiscoverabilityService: ProfileDiscoverabilityService,
    protected nationalityService: NationalityService,
    protected genderTagService: GenderTagService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareLocation = (o1: ILocation | null, o2: ILocation | null): boolean => this.locationService.compareLocation(o1, o2);

  compareProfileDiscoverability = (o1: IProfileDiscoverability | null, o2: IProfileDiscoverability | null): boolean =>
    this.profileDiscoverabilityService.compareProfileDiscoverability(o1, o2);

  compareNationality = (o1: INationality | null, o2: INationality | null): boolean => this.nationalityService.compareNationality(o1, o2);

  compareGenderTag = (o1: IGenderTag | null, o2: IGenderTag | null): boolean => this.genderTagService.compareGenderTag(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ profile }) => {
      this.profile = profile;
      if (profile) {
        this.updateForm(profile);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const profile = this.profileFormService.getProfile(this.editForm);
    if (profile.id !== null) {
      this.subscribeToSaveResponse(this.profileService.update(profile));
    } else {
      this.subscribeToSaveResponse(this.profileService.create(profile));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProfile>>): void {
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

  protected updateForm(profile: IProfile): void {
    this.profile = profile;
    this.profileFormService.resetForm(this.editForm, profile);

    this.locationsCollection = this.locationService.addLocationToCollectionIfMissing<ILocation>(this.locationsCollection, profile.location);
    this.profileDiscoverabilitiesSharedCollection =
      this.profileDiscoverabilityService.addProfileDiscoverabilityToCollectionIfMissing<IProfileDiscoverability>(
        this.profileDiscoverabilitiesSharedCollection,
        profile.discoverability,
      );
    this.nationalitiesSharedCollection = this.nationalityService.addNationalityToCollectionIfMissing<INationality>(
      this.nationalitiesSharedCollection,
      profile.nationality,
    );
    this.genderTagsSharedCollection = this.genderTagService.addGenderTagToCollectionIfMissing<IGenderTag>(
      this.genderTagsSharedCollection,
      ...(profile.genderTags ?? []),
    );
  }

  protected loadRelationshipsOptions(): void {
    this.locationService
      .query({ filter: 'profile-is-null' })
      .pipe(map((res: HttpResponse<ILocation[]>) => res.body ?? []))
      .pipe(
        map((locations: ILocation[]) =>
          this.locationService.addLocationToCollectionIfMissing<ILocation>(locations, this.profile?.location),
        ),
      )
      .subscribe((locations: ILocation[]) => (this.locationsCollection = locations));

    this.profileDiscoverabilityService
      .query()
      .pipe(map((res: HttpResponse<IProfileDiscoverability[]>) => res.body ?? []))
      .pipe(
        map((profileDiscoverabilities: IProfileDiscoverability[]) =>
          this.profileDiscoverabilityService.addProfileDiscoverabilityToCollectionIfMissing<IProfileDiscoverability>(
            profileDiscoverabilities,
            this.profile?.discoverability,
          ),
        ),
      )
      .subscribe(
        (profileDiscoverabilities: IProfileDiscoverability[]) => (this.profileDiscoverabilitiesSharedCollection = profileDiscoverabilities),
      );

    this.nationalityService
      .query()
      .pipe(map((res: HttpResponse<INationality[]>) => res.body ?? []))
      .pipe(
        map((nationalities: INationality[]) =>
          this.nationalityService.addNationalityToCollectionIfMissing<INationality>(nationalities, this.profile?.nationality),
        ),
      )
      .subscribe((nationalities: INationality[]) => (this.nationalitiesSharedCollection = nationalities));

    this.genderTagService
      .query()
      .pipe(map((res: HttpResponse<IGenderTag[]>) => res.body ?? []))
      .pipe(
        map((genderTags: IGenderTag[]) =>
          this.genderTagService.addGenderTagToCollectionIfMissing<IGenderTag>(genderTags, ...(this.profile?.genderTags ?? [])),
        ),
      )
      .subscribe((genderTags: IGenderTag[]) => (this.genderTagsSharedCollection = genderTags));
  }
}

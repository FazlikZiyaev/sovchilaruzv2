import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IProfile } from 'app/entities/profile/profile.model';
import { ProfileService } from 'app/entities/profile/service/profile.service';
import { InfoType } from 'app/entities/enumerations/info-type.model';
import { EntityState } from 'app/entities/enumerations/entity-state.model';
import { ContactInfoService } from '../service/contact-info.service';
import { IContactInfo } from '../contact-info.model';
import { ContactInfoFormService, ContactInfoFormGroup } from './contact-info-form.service';

@Component({
  standalone: true,
  selector: 'jhi-contact-info-update',
  templateUrl: './contact-info-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ContactInfoUpdateComponent implements OnInit {
  isSaving = false;
  contactInfo: IContactInfo | null = null;
  infoTypeValues = Object.keys(InfoType);
  entityStateValues = Object.keys(EntityState);

  profilesSharedCollection: IProfile[] = [];

  editForm: ContactInfoFormGroup = this.contactInfoFormService.createContactInfoFormGroup();

  constructor(
    protected contactInfoService: ContactInfoService,
    protected contactInfoFormService: ContactInfoFormService,
    protected profileService: ProfileService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareProfile = (o1: IProfile | null, o2: IProfile | null): boolean => this.profileService.compareProfile(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contactInfo }) => {
      this.contactInfo = contactInfo;
      if (contactInfo) {
        this.updateForm(contactInfo);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const contactInfo = this.contactInfoFormService.getContactInfo(this.editForm);
    if (contactInfo.id !== null) {
      this.subscribeToSaveResponse(this.contactInfoService.update(contactInfo));
    } else {
      this.subscribeToSaveResponse(this.contactInfoService.create(contactInfo));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContactInfo>>): void {
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

  protected updateForm(contactInfo: IContactInfo): void {
    this.contactInfo = contactInfo;
    this.contactInfoFormService.resetForm(this.editForm, contactInfo);

    this.profilesSharedCollection = this.profileService.addProfileToCollectionIfMissing<IProfile>(
      this.profilesSharedCollection,
      contactInfo.profile,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.profileService
      .query()
      .pipe(map((res: HttpResponse<IProfile[]>) => res.body ?? []))
      .pipe(
        map((profiles: IProfile[]) => this.profileService.addProfileToCollectionIfMissing<IProfile>(profiles, this.contactInfo?.profile)),
      )
      .subscribe((profiles: IProfile[]) => (this.profilesSharedCollection = profiles));
  }
}

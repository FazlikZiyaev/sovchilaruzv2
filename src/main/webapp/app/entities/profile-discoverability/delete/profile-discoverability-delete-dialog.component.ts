import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IProfileDiscoverability } from '../profile-discoverability.model';
import { ProfileDiscoverabilityService } from '../service/profile-discoverability.service';

@Component({
  standalone: true,
  templateUrl: './profile-discoverability-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ProfileDiscoverabilityDeleteDialogComponent {
  profileDiscoverability?: IProfileDiscoverability;

  constructor(
    protected profileDiscoverabilityService: ProfileDiscoverabilityService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.profileDiscoverabilityService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

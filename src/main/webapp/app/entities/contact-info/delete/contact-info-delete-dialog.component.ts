import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IContactInfo } from '../contact-info.model';
import { ContactInfoService } from '../service/contact-info.service';

@Component({
  standalone: true,
  templateUrl: './contact-info-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ContactInfoDeleteDialogComponent {
  contactInfo?: IContactInfo;

  constructor(
    protected contactInfoService: ContactInfoService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.contactInfoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

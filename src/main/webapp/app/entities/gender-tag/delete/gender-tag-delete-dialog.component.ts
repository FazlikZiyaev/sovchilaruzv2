import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IGenderTag } from '../gender-tag.model';
import { GenderTagService } from '../service/gender-tag.service';

@Component({
  standalone: true,
  templateUrl: './gender-tag-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class GenderTagDeleteDialogComponent {
  genderTag?: IGenderTag;

  constructor(
    protected genderTagService: GenderTagService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.genderTagService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

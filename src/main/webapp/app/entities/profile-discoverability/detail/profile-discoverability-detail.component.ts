import { Component, Input } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IProfileDiscoverability } from '../profile-discoverability.model';

@Component({
  standalone: true,
  selector: 'jhi-profile-discoverability-detail',
  templateUrl: './profile-discoverability-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class ProfileDiscoverabilityDetailComponent {
  @Input() profileDiscoverability: IProfileDiscoverability | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  previousState(): void {
    window.history.back();
  }
}

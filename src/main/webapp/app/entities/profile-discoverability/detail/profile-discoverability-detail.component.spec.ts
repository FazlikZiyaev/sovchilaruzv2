import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ProfileDiscoverabilityDetailComponent } from './profile-discoverability-detail.component';

describe('ProfileDiscoverability Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProfileDiscoverabilityDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: ProfileDiscoverabilityDetailComponent,
              resolve: { profileDiscoverability: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ProfileDiscoverabilityDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load profileDiscoverability on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ProfileDiscoverabilityDetailComponent);

      // THEN
      expect(instance.profileDiscoverability).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});

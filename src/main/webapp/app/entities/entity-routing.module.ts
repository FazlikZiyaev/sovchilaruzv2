import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'profile',
        data: { pageTitle: 'sovchilaruzv2App.profile.home.title' },
        loadChildren: () => import('./profile/profile.routes'),
      },
      {
        path: 'nationality',
        data: { pageTitle: 'sovchilaruzv2App.nationality.home.title' },
        loadChildren: () => import('./nationality/nationality.routes'),
      },
      {
        path: 'contact-info',
        data: { pageTitle: 'sovchilaruzv2App.contactInfo.home.title' },
        loadChildren: () => import('./contact-info/contact-info.routes'),
      },
      {
        path: 'gender-tag',
        data: { pageTitle: 'sovchilaruzv2App.genderTag.home.title' },
        loadChildren: () => import('./gender-tag/gender-tag.routes'),
      },
      {
        path: 'location',
        data: { pageTitle: 'sovchilaruzv2App.location.home.title' },
        loadChildren: () => import('./location/location.routes'),
      },
      {
        path: 'profile-discoverability',
        data: { pageTitle: 'sovchilaruzv2App.profileDiscoverability.home.title' },
        loadChildren: () => import('./profile-discoverability/profile-discoverability.routes'),
      },
      {
        path: 'attachment',
        data: { pageTitle: 'sovchilaruzv2App.attachment.home.title' },
        loadChildren: () => import('./attachment/attachment.routes'),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}

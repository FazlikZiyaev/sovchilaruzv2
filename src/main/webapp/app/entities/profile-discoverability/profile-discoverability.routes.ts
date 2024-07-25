import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ProfileDiscoverabilityComponent } from './list/profile-discoverability.component';
import { ProfileDiscoverabilityDetailComponent } from './detail/profile-discoverability-detail.component';
import { ProfileDiscoverabilityUpdateComponent } from './update/profile-discoverability-update.component';
import ProfileDiscoverabilityResolve from './route/profile-discoverability-routing-resolve.service';

const profileDiscoverabilityRoute: Routes = [
  {
    path: '',
    component: ProfileDiscoverabilityComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProfileDiscoverabilityDetailComponent,
    resolve: {
      profileDiscoverability: ProfileDiscoverabilityResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProfileDiscoverabilityUpdateComponent,
    resolve: {
      profileDiscoverability: ProfileDiscoverabilityResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProfileDiscoverabilityUpdateComponent,
    resolve: {
      profileDiscoverability: ProfileDiscoverabilityResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default profileDiscoverabilityRoute;

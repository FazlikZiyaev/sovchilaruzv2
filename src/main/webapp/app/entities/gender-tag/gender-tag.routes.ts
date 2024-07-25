import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { GenderTagComponent } from './list/gender-tag.component';
import { GenderTagDetailComponent } from './detail/gender-tag-detail.component';
import { GenderTagUpdateComponent } from './update/gender-tag-update.component';
import GenderTagResolve from './route/gender-tag-routing-resolve.service';

const genderTagRoute: Routes = [
  {
    path: '',
    component: GenderTagComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: GenderTagDetailComponent,
    resolve: {
      genderTag: GenderTagResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: GenderTagUpdateComponent,
    resolve: {
      genderTag: GenderTagResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: GenderTagUpdateComponent,
    resolve: {
      genderTag: GenderTagResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default genderTagRoute;

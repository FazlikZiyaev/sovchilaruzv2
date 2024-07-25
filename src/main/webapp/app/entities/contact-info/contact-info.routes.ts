import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ContactInfoComponent } from './list/contact-info.component';
import { ContactInfoDetailComponent } from './detail/contact-info-detail.component';
import { ContactInfoUpdateComponent } from './update/contact-info-update.component';
import ContactInfoResolve from './route/contact-info-routing-resolve.service';

const contactInfoRoute: Routes = [
  {
    path: '',
    component: ContactInfoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ContactInfoDetailComponent,
    resolve: {
      contactInfo: ContactInfoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ContactInfoUpdateComponent,
    resolve: {
      contactInfo: ContactInfoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ContactInfoUpdateComponent,
    resolve: {
      contactInfo: ContactInfoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default contactInfoRoute;

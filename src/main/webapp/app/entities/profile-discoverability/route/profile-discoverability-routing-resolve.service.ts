import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProfileDiscoverability } from '../profile-discoverability.model';
import { ProfileDiscoverabilityService } from '../service/profile-discoverability.service';

export const profileDiscoverabilityResolve = (route: ActivatedRouteSnapshot): Observable<null | IProfileDiscoverability> => {
  const id = route.params['id'];
  if (id) {
    return inject(ProfileDiscoverabilityService)
      .find(id)
      .pipe(
        mergeMap((profileDiscoverability: HttpResponse<IProfileDiscoverability>) => {
          if (profileDiscoverability.body) {
            return of(profileDiscoverability.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default profileDiscoverabilityResolve;

import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IContactInfo } from '../contact-info.model';
import { ContactInfoService } from '../service/contact-info.service';

export const contactInfoResolve = (route: ActivatedRouteSnapshot): Observable<null | IContactInfo> => {
  const id = route.params['id'];
  if (id) {
    return inject(ContactInfoService)
      .find(id)
      .pipe(
        mergeMap((contactInfo: HttpResponse<IContactInfo>) => {
          if (contactInfo.body) {
            return of(contactInfo.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default contactInfoResolve;

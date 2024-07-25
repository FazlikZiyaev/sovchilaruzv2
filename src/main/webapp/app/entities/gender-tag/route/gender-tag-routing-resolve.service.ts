import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IGenderTag } from '../gender-tag.model';
import { GenderTagService } from '../service/gender-tag.service';

export const genderTagResolve = (route: ActivatedRouteSnapshot): Observable<null | IGenderTag> => {
  const id = route.params['id'];
  if (id) {
    return inject(GenderTagService)
      .find(id)
      .pipe(
        mergeMap((genderTag: HttpResponse<IGenderTag>) => {
          if (genderTag.body) {
            return of(genderTag.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default genderTagResolve;

import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IGenderTag, NewGenderTag } from '../gender-tag.model';

export type PartialUpdateGenderTag = Partial<IGenderTag> & Pick<IGenderTag, 'id'>;

export type EntityResponseType = HttpResponse<IGenderTag>;
export type EntityArrayResponseType = HttpResponse<IGenderTag[]>;

@Injectable({ providedIn: 'root' })
export class GenderTagService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/gender-tags');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(genderTag: NewGenderTag): Observable<EntityResponseType> {
    return this.http.post<IGenderTag>(this.resourceUrl, genderTag, { observe: 'response' });
  }

  update(genderTag: IGenderTag): Observable<EntityResponseType> {
    return this.http.put<IGenderTag>(`${this.resourceUrl}/${this.getGenderTagIdentifier(genderTag)}`, genderTag, { observe: 'response' });
  }

  partialUpdate(genderTag: PartialUpdateGenderTag): Observable<EntityResponseType> {
    return this.http.patch<IGenderTag>(`${this.resourceUrl}/${this.getGenderTagIdentifier(genderTag)}`, genderTag, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IGenderTag>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGenderTag[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getGenderTagIdentifier(genderTag: Pick<IGenderTag, 'id'>): string {
    return genderTag.id;
  }

  compareGenderTag(o1: Pick<IGenderTag, 'id'> | null, o2: Pick<IGenderTag, 'id'> | null): boolean {
    return o1 && o2 ? this.getGenderTagIdentifier(o1) === this.getGenderTagIdentifier(o2) : o1 === o2;
  }

  addGenderTagToCollectionIfMissing<Type extends Pick<IGenderTag, 'id'>>(
    genderTagCollection: Type[],
    ...genderTagsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const genderTags: Type[] = genderTagsToCheck.filter(isPresent);
    if (genderTags.length > 0) {
      const genderTagCollectionIdentifiers = genderTagCollection.map(genderTagItem => this.getGenderTagIdentifier(genderTagItem)!);
      const genderTagsToAdd = genderTags.filter(genderTagItem => {
        const genderTagIdentifier = this.getGenderTagIdentifier(genderTagItem);
        if (genderTagCollectionIdentifiers.includes(genderTagIdentifier)) {
          return false;
        }
        genderTagCollectionIdentifiers.push(genderTagIdentifier);
        return true;
      });
      return [...genderTagsToAdd, ...genderTagCollection];
    }
    return genderTagCollection;
  }
}

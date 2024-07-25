import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProfileDiscoverability, NewProfileDiscoverability } from '../profile-discoverability.model';

export type PartialUpdateProfileDiscoverability = Partial<IProfileDiscoverability> & Pick<IProfileDiscoverability, 'id'>;

export type EntityResponseType = HttpResponse<IProfileDiscoverability>;
export type EntityArrayResponseType = HttpResponse<IProfileDiscoverability[]>;

@Injectable({ providedIn: 'root' })
export class ProfileDiscoverabilityService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/profile-discoverabilities');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(profileDiscoverability: NewProfileDiscoverability): Observable<EntityResponseType> {
    return this.http.post<IProfileDiscoverability>(this.resourceUrl, profileDiscoverability, { observe: 'response' });
  }

  update(profileDiscoverability: IProfileDiscoverability): Observable<EntityResponseType> {
    return this.http.put<IProfileDiscoverability>(
      `${this.resourceUrl}/${this.getProfileDiscoverabilityIdentifier(profileDiscoverability)}`,
      profileDiscoverability,
      { observe: 'response' },
    );
  }

  partialUpdate(profileDiscoverability: PartialUpdateProfileDiscoverability): Observable<EntityResponseType> {
    return this.http.patch<IProfileDiscoverability>(
      `${this.resourceUrl}/${this.getProfileDiscoverabilityIdentifier(profileDiscoverability)}`,
      profileDiscoverability,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProfileDiscoverability>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProfileDiscoverability[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getProfileDiscoverabilityIdentifier(profileDiscoverability: Pick<IProfileDiscoverability, 'id'>): number {
    return profileDiscoverability.id;
  }

  compareProfileDiscoverability(o1: Pick<IProfileDiscoverability, 'id'> | null, o2: Pick<IProfileDiscoverability, 'id'> | null): boolean {
    return o1 && o2 ? this.getProfileDiscoverabilityIdentifier(o1) === this.getProfileDiscoverabilityIdentifier(o2) : o1 === o2;
  }

  addProfileDiscoverabilityToCollectionIfMissing<Type extends Pick<IProfileDiscoverability, 'id'>>(
    profileDiscoverabilityCollection: Type[],
    ...profileDiscoverabilitiesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const profileDiscoverabilities: Type[] = profileDiscoverabilitiesToCheck.filter(isPresent);
    if (profileDiscoverabilities.length > 0) {
      const profileDiscoverabilityCollectionIdentifiers = profileDiscoverabilityCollection.map(
        profileDiscoverabilityItem => this.getProfileDiscoverabilityIdentifier(profileDiscoverabilityItem)!,
      );
      const profileDiscoverabilitiesToAdd = profileDiscoverabilities.filter(profileDiscoverabilityItem => {
        const profileDiscoverabilityIdentifier = this.getProfileDiscoverabilityIdentifier(profileDiscoverabilityItem);
        if (profileDiscoverabilityCollectionIdentifiers.includes(profileDiscoverabilityIdentifier)) {
          return false;
        }
        profileDiscoverabilityCollectionIdentifiers.push(profileDiscoverabilityIdentifier);
        return true;
      });
      return [...profileDiscoverabilitiesToAdd, ...profileDiscoverabilityCollection];
    }
    return profileDiscoverabilityCollection;
  }
}

import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IProfileDiscoverability } from '../profile-discoverability.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../profile-discoverability.test-samples';

import { ProfileDiscoverabilityService } from './profile-discoverability.service';

const requireRestSample: IProfileDiscoverability = {
  ...sampleWithRequiredData,
};

describe('ProfileDiscoverability Service', () => {
  let service: ProfileDiscoverabilityService;
  let httpMock: HttpTestingController;
  let expectedResult: IProfileDiscoverability | IProfileDiscoverability[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ProfileDiscoverabilityService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a ProfileDiscoverability', () => {
      const profileDiscoverability = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(profileDiscoverability).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ProfileDiscoverability', () => {
      const profileDiscoverability = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(profileDiscoverability).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ProfileDiscoverability', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ProfileDiscoverability', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ProfileDiscoverability', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addProfileDiscoverabilityToCollectionIfMissing', () => {
      it('should add a ProfileDiscoverability to an empty array', () => {
        const profileDiscoverability: IProfileDiscoverability = sampleWithRequiredData;
        expectedResult = service.addProfileDiscoverabilityToCollectionIfMissing([], profileDiscoverability);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(profileDiscoverability);
      });

      it('should not add a ProfileDiscoverability to an array that contains it', () => {
        const profileDiscoverability: IProfileDiscoverability = sampleWithRequiredData;
        const profileDiscoverabilityCollection: IProfileDiscoverability[] = [
          {
            ...profileDiscoverability,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addProfileDiscoverabilityToCollectionIfMissing(profileDiscoverabilityCollection, profileDiscoverability);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ProfileDiscoverability to an array that doesn't contain it", () => {
        const profileDiscoverability: IProfileDiscoverability = sampleWithRequiredData;
        const profileDiscoverabilityCollection: IProfileDiscoverability[] = [sampleWithPartialData];
        expectedResult = service.addProfileDiscoverabilityToCollectionIfMissing(profileDiscoverabilityCollection, profileDiscoverability);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(profileDiscoverability);
      });

      it('should add only unique ProfileDiscoverability to an array', () => {
        const profileDiscoverabilityArray: IProfileDiscoverability[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const profileDiscoverabilityCollection: IProfileDiscoverability[] = [sampleWithRequiredData];
        expectedResult = service.addProfileDiscoverabilityToCollectionIfMissing(
          profileDiscoverabilityCollection,
          ...profileDiscoverabilityArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const profileDiscoverability: IProfileDiscoverability = sampleWithRequiredData;
        const profileDiscoverability2: IProfileDiscoverability = sampleWithPartialData;
        expectedResult = service.addProfileDiscoverabilityToCollectionIfMissing([], profileDiscoverability, profileDiscoverability2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(profileDiscoverability);
        expect(expectedResult).toContain(profileDiscoverability2);
      });

      it('should accept null and undefined values', () => {
        const profileDiscoverability: IProfileDiscoverability = sampleWithRequiredData;
        expectedResult = service.addProfileDiscoverabilityToCollectionIfMissing([], null, profileDiscoverability, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(profileDiscoverability);
      });

      it('should return initial array if no ProfileDiscoverability is added', () => {
        const profileDiscoverabilityCollection: IProfileDiscoverability[] = [sampleWithRequiredData];
        expectedResult = service.addProfileDiscoverabilityToCollectionIfMissing(profileDiscoverabilityCollection, undefined, null);
        expect(expectedResult).toEqual(profileDiscoverabilityCollection);
      });
    });

    describe('compareProfileDiscoverability', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareProfileDiscoverability(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareProfileDiscoverability(entity1, entity2);
        const compareResult2 = service.compareProfileDiscoverability(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareProfileDiscoverability(entity1, entity2);
        const compareResult2 = service.compareProfileDiscoverability(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareProfileDiscoverability(entity1, entity2);
        const compareResult2 = service.compareProfileDiscoverability(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

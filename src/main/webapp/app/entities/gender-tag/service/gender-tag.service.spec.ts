import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IGenderTag } from '../gender-tag.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../gender-tag.test-samples';

import { GenderTagService } from './gender-tag.service';

const requireRestSample: IGenderTag = {
  ...sampleWithRequiredData,
};

describe('GenderTag Service', () => {
  let service: GenderTagService;
  let httpMock: HttpTestingController;
  let expectedResult: IGenderTag | IGenderTag[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(GenderTagService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find('9fec3727-3421-4967-b213-ba36557ca194').subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a GenderTag', () => {
      const genderTag = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(genderTag).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a GenderTag', () => {
      const genderTag = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(genderTag).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a GenderTag', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of GenderTag', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a GenderTag', () => {
      const expected = true;

      service.delete('9fec3727-3421-4967-b213-ba36557ca194').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addGenderTagToCollectionIfMissing', () => {
      it('should add a GenderTag to an empty array', () => {
        const genderTag: IGenderTag = sampleWithRequiredData;
        expectedResult = service.addGenderTagToCollectionIfMissing([], genderTag);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(genderTag);
      });

      it('should not add a GenderTag to an array that contains it', () => {
        const genderTag: IGenderTag = sampleWithRequiredData;
        const genderTagCollection: IGenderTag[] = [
          {
            ...genderTag,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addGenderTagToCollectionIfMissing(genderTagCollection, genderTag);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a GenderTag to an array that doesn't contain it", () => {
        const genderTag: IGenderTag = sampleWithRequiredData;
        const genderTagCollection: IGenderTag[] = [sampleWithPartialData];
        expectedResult = service.addGenderTagToCollectionIfMissing(genderTagCollection, genderTag);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(genderTag);
      });

      it('should add only unique GenderTag to an array', () => {
        const genderTagArray: IGenderTag[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const genderTagCollection: IGenderTag[] = [sampleWithRequiredData];
        expectedResult = service.addGenderTagToCollectionIfMissing(genderTagCollection, ...genderTagArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const genderTag: IGenderTag = sampleWithRequiredData;
        const genderTag2: IGenderTag = sampleWithPartialData;
        expectedResult = service.addGenderTagToCollectionIfMissing([], genderTag, genderTag2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(genderTag);
        expect(expectedResult).toContain(genderTag2);
      });

      it('should accept null and undefined values', () => {
        const genderTag: IGenderTag = sampleWithRequiredData;
        expectedResult = service.addGenderTagToCollectionIfMissing([], null, genderTag, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(genderTag);
      });

      it('should return initial array if no GenderTag is added', () => {
        const genderTagCollection: IGenderTag[] = [sampleWithRequiredData];
        expectedResult = service.addGenderTagToCollectionIfMissing(genderTagCollection, undefined, null);
        expect(expectedResult).toEqual(genderTagCollection);
      });
    });

    describe('compareGenderTag', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareGenderTag(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = null;

        const compareResult1 = service.compareGenderTag(entity1, entity2);
        const compareResult2 = service.compareGenderTag(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };

        const compareResult1 = service.compareGenderTag(entity1, entity2);
        const compareResult2 = service.compareGenderTag(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };

        const compareResult1 = service.compareGenderTag(entity1, entity2);
        const compareResult2 = service.compareGenderTag(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

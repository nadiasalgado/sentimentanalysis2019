/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { SearchService } from 'app/entities/search/search.service';
import { ISearch, Search, Searchlang } from 'app/shared/model/search.model';

describe('Service Tests', () => {
    describe('Search Service', () => {
        let injector: TestBed;
        let service: SearchService;
        let httpMock: HttpTestingController;
        let elemDefault: ISearch;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(SearchService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Search(0, 'AAAAAAA', currentDate, currentDate, Searchlang.ENGLISH);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        lastSearch: currentDate.format(DATE_TIME_FORMAT),
                        slastSearch: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a Search', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        lastSearch: currentDate.format(DATE_TIME_FORMAT),
                        slastSearch: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        lastSearch: currentDate,
                        slastSearch: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Search(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Search', async () => {
                const returnedFromService = Object.assign(
                    {
                        search: 'BBBBBB',
                        lastSearch: currentDate.format(DATE_TIME_FORMAT),
                        slastSearch: currentDate.format(DATE_TIME_FORMAT),
                        language: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        lastSearch: currentDate,
                        slastSearch: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of Search', async () => {
                const returnedFromService = Object.assign(
                    {
                        search: 'BBBBBB',
                        lastSearch: currentDate.format(DATE_TIME_FORMAT),
                        slastSearch: currentDate.format(DATE_TIME_FORMAT),
                        language: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        lastSearch: currentDate,
                        slastSearch: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a Search', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});

import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISearch } from 'app/shared/model/search.model';

type EntityResponseType = HttpResponse<ISearch>;
type EntityArrayResponseType = HttpResponse<ISearch[]>;

@Injectable({ providedIn: 'root' })
export class SearchService {
    public resourceUrl = SERVER_API_URL + 'api/searches';

    constructor(protected http: HttpClient) {}

    create(search: ISearch): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(search);
        return this.http
            .post<ISearch>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(search: ISearch): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(search);
        return this.http
            .put<ISearch>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ISearch>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISearch[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(search: ISearch): ISearch {
        const copy: ISearch = Object.assign({}, search, {
            lastSearch: search.lastSearch != null && search.lastSearch.isValid() ? search.lastSearch.toJSON() : null,
            slastSearch: search.slastSearch != null && search.slastSearch.isValid() ? search.slastSearch.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.lastSearch = res.body.lastSearch != null ? moment(res.body.lastSearch) : null;
            res.body.slastSearch = res.body.slastSearch != null ? moment(res.body.slastSearch) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((search: ISearch) => {
                search.lastSearch = search.lastSearch != null ? moment(search.lastSearch) : null;
                search.slastSearch = search.slastSearch != null ? moment(search.slastSearch) : null;
            });
        }
        return res;
    }
}

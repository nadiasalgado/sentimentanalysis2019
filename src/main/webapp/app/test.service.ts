import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITest } from 'app/shared/model/test.model';

type EntityResponseType = HttpResponse<ITest>;

@Injectable({ providedIn: 'root' })
export class TestService {
    public resourceUrl = SERVER_API_URL + 'api/hello';
    public resourceUrl2 = SERVER_API_URL + 'api/hello/tnt';

    constructor(protected http: HttpClient) {}

    create(test: ITest) {
        return this.http.post<ITest>(this.resourceUrl, test);
    }
    testit(test: string) {
        return this.http.post<string>(this.resourceUrl2, test);
    }
}

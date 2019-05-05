import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ISearch } from 'app/shared/model/search.model';
import { SearchService } from './search.service';

@Component({
    selector: 'jhi-search-update',
    templateUrl: './search-update.component.html'
})
export class SearchUpdateComponent implements OnInit {
    search: ISearch;
    isSaving: boolean;
    lastSearch: string;
    slastSearch: string;

    constructor(protected searchService: SearchService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ search }) => {
            this.search = search;
            this.lastSearch = this.search.lastSearch != null ? this.search.lastSearch.format(DATE_TIME_FORMAT) : null;
            this.slastSearch = this.search.slastSearch != null ? this.search.slastSearch.format(DATE_TIME_FORMAT) : null;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.search.lastSearch = this.lastSearch != null ? moment(this.lastSearch, DATE_TIME_FORMAT) : null;
        this.search.slastSearch = this.slastSearch != null ? moment(this.slastSearch, DATE_TIME_FORMAT) : null;
        if (this.search.id !== undefined) {
            this.subscribeToSaveResponse(this.searchService.update(this.search));
        } else {
            this.subscribeToSaveResponse(this.searchService.create(this.search));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ISearch>>) {
        result.subscribe((res: HttpResponse<ISearch>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}

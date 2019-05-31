import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISearch } from 'app/shared/model/search.model';
import { AccountService } from 'app/core';
import { SearchService } from './search.service';

@Component({
    selector: 'jhi-search',
    templateUrl: './search.component.html'
})
export class SearchComponent implements OnInit, OnDestroy {
    searches: ISearch[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected searchService: SearchService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.searchService
            .query()
            .pipe(
                filter((res: HttpResponse<ISearch[]>) => res.ok),
                map((res: HttpResponse<ISearch[]>) => res.body)
            )
            .subscribe(
                (res: ISearch[]) => {
                    this.searches = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInSearches();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ISearch) {
        return item.id;
    }

    registerChangeInSearches() {
        this.eventSubscriber = this.eventManager.subscribe('searchListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}

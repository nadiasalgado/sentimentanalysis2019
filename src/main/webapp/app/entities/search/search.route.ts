import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Search } from 'app/shared/model/search.model';
import { SearchService } from './search.service';
import { SearchComponent } from './search.component';
import { SearchDetailComponent } from './search-detail.component';
import { SearchUpdateComponent } from './search-update.component';
import { SearchDeletePopupComponent } from './search-delete-dialog.component';
import { ISearch } from 'app/shared/model/search.model';

@Injectable({ providedIn: 'root' })
export class SearchResolve implements Resolve<ISearch> {
    constructor(private service: SearchService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISearch> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Search>) => response.ok),
                map((search: HttpResponse<Search>) => search.body)
            );
        }
        return of(new Search());
    }
}

export const searchRoute: Routes = [
    {
        path: '',
        component: SearchComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Searches'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: SearchDetailComponent,
        resolve: {
            search: SearchResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Searches'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: SearchUpdateComponent,
        resolve: {
            search: SearchResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Searches'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: SearchUpdateComponent,
        resolve: {
            search: SearchResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Searches'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const searchPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: SearchDeletePopupComponent,
        resolve: {
            search: SearchResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Searches'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

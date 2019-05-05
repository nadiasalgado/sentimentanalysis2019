import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISearch } from 'app/shared/model/search.model';
import { SearchService } from './search.service';

@Component({
    selector: 'jhi-search-delete-dialog',
    templateUrl: './search-delete-dialog.component.html'
})
export class SearchDeleteDialogComponent {
    search: ISearch;

    constructor(protected searchService: SearchService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.searchService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'searchListModification',
                content: 'Deleted an search'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-search-delete-popup',
    template: ''
})
export class SearchDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ search }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SearchDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.search = search;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/search', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/search', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}

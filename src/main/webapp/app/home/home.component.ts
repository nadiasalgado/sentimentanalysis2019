import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { JhiEventManager } from 'ng-jhipster';
import { TestService } from 'app/test.service';
import { filter, map } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { of } from 'rxjs';
import { delay, share } from 'rxjs/operators';
import { ITest } from 'app/shared/model/test.model';
import { Test } from 'app/shared/model/test.model';
import * as CanvasJS from 'canvasjs/dist/canvasjs.min';

import { LoginModalService, AccountService, Account } from 'app/core';

@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: ['home.scss']
})
export class HomeComponent implements OnInit {
    account: Account;
    modalRef: NgbModalRef;
    loading = false;

    constructor(
        private accountService: AccountService,
        private loginModalService: LoginModalService,
        private eventManager: JhiEventManager,
        protected testService: TestService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.accountService.identity().then((account: Account) => {
            this.account = account;
        });
        this.registerAuthenticationSuccess();
    }
    logText2(value: string, numm: number, typ: string): void {
        this.loading = true;
        if (value != null) {
            const tt: ITest = <ITest>{
                type: typ,
                num: numm,
                statText: value
            };
            this.testService
                .create(tt)
                .subscribe(({ VposCount, posCount, neuCount, negCount, VnegCount, VposTxt, posTxt, neuTxt, negTxt, VnegTxt }) => {
                    const chart = new CanvasJS.Chart('chartContainer', {
                        title: {
                            text: 'Chart of Sentiment Results'
                        },
                        data: [
                            {
                                type: 'column',
                                dataPoints: [
                                    {
                                        y: VposCount,
                                        label: 'Very Positive',
                                        click: e => {
                                            alert(VposTxt);
                                        }
                                    },
                                    {
                                        y: posCount,
                                        label: 'Positive',
                                        click: e => {
                                            alert(posTxt);
                                        }
                                    },
                                    {
                                        y: neuCount,
                                        label: 'Neutral',
                                        click: e => {
                                            alert(neuTxt);
                                        }
                                    },
                                    {
                                        y: negCount,
                                        label: 'Negative',
                                        click: e => {
                                            alert(negTxt);
                                        }
                                    },
                                    {
                                        y: VnegCount,
                                        label: 'Very Negative',
                                        click: e => {
                                            alert(VnegTxt);
                                        }
                                    }
                                ]
                            }
                        ]
                    });
                    chart.render();
                    this.loading = false;
                });
        } else {
            this.loading = false;
        }
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', message => {
            this.accountService.identity().then(account => {
                this.account = account;
            });
        });
    }

    isAuthenticated() {
        return this.accountService.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }
}

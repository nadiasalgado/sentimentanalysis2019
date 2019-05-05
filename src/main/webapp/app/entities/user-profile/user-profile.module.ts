import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Sentimentanalysis2019SharedModule } from 'app/shared';
import {
    UserProfileComponent,
    UserProfileDetailComponent,
    UserProfileUpdateComponent,
    UserProfileDeletePopupComponent,
    UserProfileDeleteDialogComponent,
    userProfileRoute,
    userProfilePopupRoute
} from './';

const ENTITY_STATES = [...userProfileRoute, ...userProfilePopupRoute];

@NgModule({
    imports: [Sentimentanalysis2019SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        UserProfileComponent,
        UserProfileDetailComponent,
        UserProfileUpdateComponent,
        UserProfileDeleteDialogComponent,
        UserProfileDeletePopupComponent
    ],
    entryComponents: [UserProfileComponent, UserProfileUpdateComponent, UserProfileDeleteDialogComponent, UserProfileDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Sentimentanalysis2019UserProfileModule {}

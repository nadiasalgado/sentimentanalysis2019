import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'search',
                loadChildren: './search/search.module#Sentimentanalysis2019SearchModule'
            },
            {
                path: 'user-profile',
                loadChildren: './user-profile/user-profile.module#Sentimentanalysis2019UserProfileModule'
            },
            {
                path: 'user-profile',
                loadChildren: './user-profile/user-profile.module#Sentimentanalysis2019UserProfileModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Sentimentanalysis2019EntityModule {}

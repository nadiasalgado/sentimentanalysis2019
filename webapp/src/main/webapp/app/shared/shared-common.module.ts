import { NgModule } from '@angular/core';

import { WebappSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [WebappSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [WebappSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class WebappSharedCommonModule {}

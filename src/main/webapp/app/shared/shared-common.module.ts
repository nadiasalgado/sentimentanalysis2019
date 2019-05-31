import { NgModule } from '@angular/core';

import { EntityTestSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [EntityTestSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [EntityTestSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class EntityTestSharedCommonModule {}

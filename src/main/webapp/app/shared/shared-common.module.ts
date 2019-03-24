import { NgModule } from '@angular/core';

import { Sentimentanalysis2019SharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [Sentimentanalysis2019SharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [Sentimentanalysis2019SharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class Sentimentanalysis2019SharedCommonModule {}

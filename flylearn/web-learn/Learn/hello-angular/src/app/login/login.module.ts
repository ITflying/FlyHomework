import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { SharedModule } from '../shared/shared.module';
import { BingImageService } from './bing-image.service';
import { LoginRoutingModule } from './login-routing.module';
import { LoginComponent } from './login.component';
import { RegisterDialogComponent } from './register-dialog/register-dialog.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

@NgModule({
  imports: [
    SharedModule,
    LoginRoutingModule,
    ReactiveFormsModule,
    BrowserAnimationsModule
  ],
  declarations: [
    LoginComponent,
    RegisterDialogComponent
    ],
  entryComponents: [RegisterDialogComponent],
  providers: [
    { provide: 'bingService', useClass: BingImageService }
  ]
})
export class LoginModule { }

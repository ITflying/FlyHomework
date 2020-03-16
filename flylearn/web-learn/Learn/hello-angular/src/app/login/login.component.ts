import {
  Component,
  Inject,
  trigger,
  state,
  style,
  transition,
  animate,
  OnDestroy
} from '@angular/core';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { MdlDialogService, MdlDialogReference } from '@angular-mdl/core';
import { Auth, Image } from '../domain/entities';
import { RegisterDialogComponent } from './register-dialog/register-dialog.component';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  animations: [
    trigger('loginState', [
      state('inactive', style({
        transform: 'scale(1)'
      })),
      state('active',   style({
        transform: 'scale(1.1)'
      })),
      transition('inactive => active', animate('100ms ease-in')),
      transition('active => inactive', animate('100ms ease-out'))
    ])
  ]
})
export class LoginComponent implements OnDestroy {
  photo = '/assets/login_default_bg.jpg';
  username = "";
  password = "";
  auth: Auth;
  slides: Image[] = [];
  subscription:Subscription
  loginBtnState:string = "inactive";
  
  constructor(@Inject('authService')private authService, 
              @Inject('bingService') private bingService,
              private dialogService: MdlDialogService,
              private router:Router) {
    // subscribe监听
    /*
    this.bingService.getImageUrl()
                    .subscribe((images: Image[]) => {
                                this.slides = [...images];
                                this.rotateImages(this.slides);
    });
    */
  }

  rotateImages(arr:Image[]){
    const length = arr.length;
    let i = 0;
    // 4s循环一次展示图片
    setInterval(() => {
      i = (i + 1) % length;
      this.photo = this.slides[i].contentUrl;
    }, 4000)
  }

  // 在页面销毁时也同时销毁观察者的订阅，使其不会一直跑在后台
  ngOnDestroy(){
    // this.subscription.unsubscribe();
  }

  toggleLoginState(state: boolean){
    this.loginBtnState = state ? 'active' : 'inactive';
  }

  confirm(username, password){
    alert("登陆结果为："+ this.authService.loginWithCredentials(this.username, this.password));
  }

  // 使用Rx技术
  onSubmit(){
    this.authService.loginWithCredentials(this.username, this.password)
                    .subscribe(auth => {
                      this.auth = Object.assign({}, auth);
                      if (!auth.hasError){
                        this.router.navigate(['todo']);
                      }
                    })
  }
  register($event: MouseEvent){
    let pDialog = this.dialogService.showCustomDialog({
      component: RegisterDialogComponent,
      isModal: true,
      styles: {'width': '350px'},
      clickOutsideToClose: true,
      enterTransitionDuration: 400,
      leaveTransitionDuration: 400
    });
    pDialog.map( (dialogReference: MdlDialogReference) => {
      console.log('dialog visible', dialogReference);
    });

  }

  // 使用Promise异步技术
  onSubmitPromise(formValue){
    console.log(formValue);
    this.authService.loginWithCredentials(formValue.login.username, formValue.login.password)
                .then(auth => {
                  let redirectUrl = (auth.redirectUrl === null)? '/': auth.redirectUrl;
                  if (!auth.hasError){
                    this.router.navigate([redirectUrl]);
                    localStorage.removeItem('redirectUrl');
                  }else{
                    this.auth = Object.assign({}, auth);
                  }
                })
  }

}

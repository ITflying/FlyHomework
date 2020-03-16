import {
  Component,
  ViewChild,
  HostListener,
  OnInit,
  Inject
} from '@angular/core';

import { Router, Route } from '@angular/router';
import {
  FormGroup,
  Validators,
  FormControl,
  FormBuilder
} from '@angular/forms';
import { MdlTextFieldComponent,MdlDialogReference } from '@angular-mdl/core';
import { Subscription } from 'rxjs/Subscription';

@Component({
  selector: 'app-register-dialog',
  templateUrl: './register-dialog.component.html',
  styleUrls: ['./register-dialog.component.css']
})
export class RegisterDialogComponent {
  @ViewChild('firstElement')private inputElement:MdlTextFieldComponent;
  // FormGroup为一组表单组件,Angular2 的FormControl中内置了常用的验证器（Validator）
  public form:FormGroup;
  public processingRegister = false;
  public statusMessage = '';
  private subscription: Subscription;
  
  constructor(
    private dialog:MdlDialogReference,
    private fb:FormBuilder,
    private router:Router,
    @Inject("authService")private authService
  ) { 
    this.form = fb.group({
      'username': new FormControl('', Validators.required),
      'passwords': fb.group({
          'password':new FormControl('',Validators.required),
          'repeatPassword':new FormControl('',Validators.required)
      },{Validators:this.passwordMatchValidator})
    });
    this.dialog.onHide().subscribe((auth) => {
      console.log("登陆窗口隐藏");
      if (auth){
        console.log("验证用户",auth);
      }
    })
    this.dialog.onVisible().subscribe( () => {
      this.inputElement.setFocus();
    });
  }

  passwordMatchValidator(group: FormGroup){
    this.statusMessage = '';
    let password = group.get('password').value;
    let confirm = group.get('repeatPassword').value;

    // pristine:A control is pristine if the user has not yet changed the value in the UI.
    // 如果账号密码输入框没有改变过
    if (password.pristine || confirm.pristine) {
      return null;
    }
    if(password===confirm) {
      return null;
    }
    return {'mismatch': true};
  }

  public register() {
    this.processingRegister = true;
    this.statusMessage = '你正在注册 ...';

    this.subscription = this.authService
      .register(
        this.form.get('username').value,
        this.form.get('passwords').get('password').value)
      .subscribe( auth => {
        this.processingRegister = false;
        this.statusMessage = '注册成功并马上跳转 ...';
        setTimeout( () => {
          this.dialog.hide(auth);
          this.router.navigate(['todo']);
        }, 500);
    }, err => {
      this.processingRegister = false;
      this.statusMessage = err.message;
    });
  }

  // 监听用户的Esc的键盘
  @HostListener('keydown.esc')
  public onEsc(): void {
    if(this.subscription !== undefined)
      this.subscription.unsubscribe();
    this.dialog.hide();
  }


}

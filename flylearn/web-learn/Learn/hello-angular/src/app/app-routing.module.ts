// Angular团队推荐把路由模块化，这样便于使业务逻辑和路由松耦合
import { NgModule } from "@angular/core";
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { AuthGuardService } from './core/auth-guard.service';

import { RxtestComponent } from './rxtest/rxtest.component';

const routes:Routes=[
    {
        path: '',
        redirectTo: 'login',
        pathMatch: 'full'
      },
      {
        path: 'login',
        component: LoginComponent
      },
      {
        path: 'todo',
        redirectTo: 'todo/ALL',
        canLoad: [AuthGuardService]
      },
      {
          path:"rx",
          component:RxtestComponent
      },
      {
        path: 'playground',
        loadChildren: 'app/playground/playground.module#PlaygroundModule',
      }
]
@NgModule({
    imports:[
        // 需要对路由做hash处理，这样打包之后的项目就可以使用了
        RouterModule.forRoot(routes, {useHash:true})
    ],
    exports:[
        RouterModule
    ]
})
export class AppRoutingModule{}

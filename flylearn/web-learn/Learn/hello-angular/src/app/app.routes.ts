import {Routes, RouterModule} from '@angular/router';

import { LoginComponent } from './login/login.component';
import { RxtestComponent } from './rxtest/rxtest.component';

export const routes:Routes = [
    {
        path:'',
        redirectTo:'login',
        pathMatch:'full'
    },
    {
        path:"todo",
        redirectTo:"todo/ALL"
    },
    {
        path:"rx",
        component:RxtestComponent
    }
]

export const routing = RouterModule.forRoot(routes);
import { NgModule } from "@angular/core";
import { Routes,RouterModule } from "@angular/router";

const routes:Routes=[
    {
        path:'',
        redirectTo:'/hero/dashboard',
        pathMatch:'full'
    },{
        path:'hero',
        redirectTo:'hero/ALL'
    }
]
@NgModule({
    imports:[
        // 对路径进行路由处理
        RouterModule.forRoot(routes, {useHash:true})
    ],
    exports:[
        RouterModule
    ]
})


export class AppRoutingModule {}
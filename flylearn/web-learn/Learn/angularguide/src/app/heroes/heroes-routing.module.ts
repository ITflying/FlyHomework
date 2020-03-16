import { NgModule } from "@angular/core";
import { Routes,RouterModule } from "@angular/router";

import { HeroesComponent } from "./heroes.component";
import { DashboardComponent } from "./dashboard/dashboard.component";
import { DetailComponent } from "./detail/detail.component";

const routes:Routes=[
    {
        path:"hero/heroes",
        component:HeroesComponent
    },
    {
        path:"hero/dashboard",
        component:DashboardComponent
    },
    {
        path:"hero/heroes/detail/:id",
        component:DetailComponent
    }
]

@NgModule({
    imports:[
        RouterModule.forChild(routes)
    ],
    exports:[
        RouterModule
    ]
})

export class HeroesRoutingModule {}
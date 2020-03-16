import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { FormsModule } from "@angular/forms";
import { RouterModule } from '@angular/router';

import { HeroesComponent } from './heroes.component';
import { HeroesService } from './heroes.service'
import { HttpModule } from "@angular/http";
import { DetailComponent } from './detail/detail.component';
import { MessageService } from './message/message.service';
import { DashboardComponent } from './dashboard/dashboard.component';
import { HeroSearchComponent } from './hero-search/hero-search.component'

@NgModule({
  declarations: [
    HeroesComponent,
    DetailComponent,
    DashboardComponent,
    HeroSearchComponent
  ],
  imports: [
    HttpModule,
    CommonModule,
    FormsModule,
    RouterModule
  ],
  providers: [
      {provide:'heroesService', useClass: HeroesService},
      {provide:'messageService', useClass: MessageService}
  ]
})
export class HeroesModule{ }

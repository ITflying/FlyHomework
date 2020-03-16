import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { HttpModule } from '@angular/http';

import { CoreModule } from "./core/core.module";
import { AppRoutingModule } from './app-routing.module';
import { MdlModule } from '@angular-mdl/core';

import { TodoModule } from './todo/todo.module';
import { LoginModule } from './login/login.module';


import { AppComponent } from './app.component';
import { RxtestComponent } from './rxtest/rxtest.component';

// 内存web服务
// import { InMemoryDbService, InMemoryWebApiModule } from "angular-in-memory-web-api";
// import { InMemoryTodoDbService } from './todo/todo-data';


@NgModule({
  declarations: [
    AppComponent,
    RxtestComponent,
  ],
  imports: [
    FormsModule,
    BrowserModule,
    HttpModule,
    HttpClientModule,
    // InMemoryWebApiModule.forRoot(InMemoryTodoDbService),
    CoreModule,
    AppRoutingModule,
    MdlModule,
    TodoModule,
    LoginModule,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

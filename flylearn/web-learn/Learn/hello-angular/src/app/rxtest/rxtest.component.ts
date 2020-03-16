import { Component, OnInit, OnDestroy } from '@angular/core';
import { Observable } from "rxjs/Observable";
import { Subscription } from "rxjs/Subscription";
import 'rxjs/add/observable/interval'

@Component({
  selector: 'app-rxtest',
  templateUrl: './rxtest.component.html',
  styleUrls: ['./rxtest.component.css']
})
export class RxtestComponent {
  clock = Observable.interval(1000).do(_=>console.log('observable created'));


  constructor() { 

  }


}

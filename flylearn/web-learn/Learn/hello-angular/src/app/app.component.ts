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

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  animations:[
    trigger('loginState',[
      state('inactive',style({
        transform:'scale(1)'
      })),
      state('active',style({
        transform:'scale(1.1)'
      })),
      transition('inactive => active',animate('100ms ease-in')),
      transition('active => inactive',animate('100ms ease-out')),
    ])
  ]
})
export class AppComponent {
  title = 'Angular Todo List';
}
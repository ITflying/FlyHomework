import { Component, OnInit, Inject } from '@angular/core';

@Component({
  selector: 'app-message',
  templateUrl: './message.component.html',
  styleUrls: ['./message.component.css']
})
export class MessageComponent implements OnInit {
  messages:string[] = [];
  constructor(@Inject("messageService")public messageService) { }

  ngOnInit() {
    this.messages = this.messageService.message;
  }

}

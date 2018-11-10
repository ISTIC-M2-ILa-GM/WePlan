import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent implements OnInit {
  @Input() email: string;
  @Input() password: string;

  constructor() { }

  ngOnInit() {
  }

}

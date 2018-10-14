import { Component, OnInit } from '@angular/core';
import { UserService } from './../../services/user.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  firstName = '';
  lastName = '';
  email = '';
  password = '';
  repeat = '';

  constructor(private userService: UserService) { }

  ngOnInit() {
    // for testing purpose only
    /*
    const user = this.userService.get();
    console.log(user);
    this.firstName = user.firstName;
    this.lastName = user.lastName;
    this.email = user.email;
    */
  }

  onSubmit = () => {
    console.log(`First name: ${this.firstName} Last name: ${this.lastName}`);
    console.log(`Email: ${this.email}`);
    console.log(`Password: ${this.password} Repeat: ${this.repeat}`);
  }
}

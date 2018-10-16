import { UserService } from './../../services/user.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserRequest } from '../../models/dto/user.request';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  firstName = '';
  lastName = '';
  email = '';
  password = '';
  repeat = '';

  constructor(private router: Router, private userService: UserService) { }

  ngOnInit = () => {
  }

  onSubmit = () => {
    const user: UserRequest = {
      firstName: this.firstName,
      lastName: this.lastName,
      email: this.email,
      password: this.password,
    };
    console.log(user);

    this.userService.post(user).subscribe(response => {
      console.log(response);
      console.log('Success !');
      this.router.navigate(['/login']);
    }, error => {
      console.error('Something went wrong while registering user.');
      console.error(error);
    });
  }
}

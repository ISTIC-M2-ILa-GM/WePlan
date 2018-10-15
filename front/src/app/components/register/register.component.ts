import { UserService } from './../../services/user.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

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
    const post_user = {
      firstName: this.firstName,
      lastName: this.lastName,
      email: this.email,
      password: this.password
    };
    // console.log(`First name: ${this.firstName} Last name: ${this.lastName}`);
    // console.log(`Email: ${this.email}`);
    // console.log(`Password: ${this.password} Repeat: ${this.repeat}`);
    this.userService.post(post_user).subscribe((o: Object) => {
      this.router.navigate(['/login']);
    }, error => {
      console.error('Something went wrong while registering user.');
      console.error(error);
    });
  }
}

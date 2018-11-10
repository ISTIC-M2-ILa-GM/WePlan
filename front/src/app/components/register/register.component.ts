import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {UserRequest} from '../../models/dto/user.request';
import {AuthService} from '../../services/auth.service';
import { MzToastService } from 'ngx-materialize';

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

  constructor(
    private router: Router,
    private authService: AuthService,
    private toastService: MzToastService
  ) { }

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

    this.authService.register(user).subscribe(response => {
      this.toastService.show('Successfully registered. PLease, login now.', 4000, 'green');
      console.log(response);
      this.router.navigate(['/login']);
    }, error => {
      this.toastService.show('Something went wrong :/', 2000, 'red');
      console.error(error);
    });
  }
}

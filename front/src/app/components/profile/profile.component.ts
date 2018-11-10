import { UserRequest } from './../../models/dto/user.request';
import { User } from './../../models/dto/user';
import { AuthService } from './../../services/auth.service';
import { Component, OnInit } from '@angular/core';
import { UserService } from './../../services/user.service';
import { MzToastService } from 'ngx-materialize';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  public user: any;

  constructor(
    private authService: AuthService,
    private userService: UserService,
    private toastService: MzToastService
  ) {
    this.user = { firstName: '', lastName: '', email: '' };
  }

  ngOnInit() {
    this.authService.check().then(user => {
      this.user = this.authService.getUser();
    }, error => {
      console.warn('You\'ve got nothing to do here, please login first.');
    });
  }

  onSubmit = () => {
    const user = {
      firstName: this.user.firstName,
      lastName: this.user.lastName,
      email: this.user.email
    };
    this.userService.patch(user).subscribe(truc => {
      this.toastService.show('Updated user details successfully !', 4000, 'green');
    });
  }
}

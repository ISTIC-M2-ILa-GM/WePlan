import {RouterModule} from '@angular/router';
import {NgModule} from '@angular/core';

import {NotFoundComponent} from '../components/not-found/not-found.component';
import {HomeComponent} from '../components/home/home.component';
import {LoginComponent} from '../components/login/login.component';
import {RegisterComponent} from '../components/register/register.component';
import {ProfileComponent} from '../components/profile/profile.component';
import {PreferenceComponent} from "../components/preference/preference.component";
import {EventComponent} from "../components/event/event.component";

const appRoutes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'profile', component: ProfileComponent },
  { path: 'preference', component: PreferenceComponent },
  { path: 'home', component: HomeComponent },
  { path: 'event', component: EventComponent },
  { path: '**', component: NotFoundComponent }
];

@NgModule({
  imports: [
    RouterModule.forRoot(
      appRoutes
    )
  ],
  exports: [
    RouterModule
  ]
})
export class RoutingModule {
}

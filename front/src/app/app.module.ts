// framwork + libraries related imports
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import { MaterializeComponentModule } from './materialize-css.module';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

// Application imports
import { AppComponent } from './app.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { SideNavComponent } from './components/side-nav/side-nav.component';
import { RoutingModule } from './routing/routing.module';
import { HomeComponent } from './components/home/home.component';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { LoginComponent } from './components/login/login.component';
// import { LoginFormComponent } from './forms/login-form/login-form.component';
import { RegisterComponent } from './components/register/register.component';
import { ProfileComponent } from './components/profile/profile.component';
import { UserService } from './services/user.service';
import { PreferenceComponent } from "./components/preference/preference.component";

@NgModule({
  declarations: [
    // Components (pages)
    AppComponent,
    NavbarComponent,
    SideNavComponent,
    HomeComponent,
    NotFoundComponent,
    LoginComponent,
    RegisterComponent,
    ProfileComponent,
    PreferenceComponent

    // Forms
    // LoginFormComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MaterializeComponentModule,
    RoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [
    UserService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

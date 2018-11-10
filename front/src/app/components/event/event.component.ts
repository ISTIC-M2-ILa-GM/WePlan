import {UserService} from './../../services/user.service';
import {AuthService} from './../../services/auth.service';
import {Component, OnInit} from '@angular/core';
import {EventService} from "../../services/event.service";
import {PageRequest} from "../../models/request/page.request";
import {Event} from "../../models/dto/event";
import {PageResponse} from "../../models/response/page.response";
import {User} from "../../models/dto/user";

@Component({
  selector: 'app-event',
  templateUrl: './event.component.html',
  styleUrls: ['./event.component.css']
})
export class EventComponent implements OnInit {

  events: Array<Event> = [];
  subscribedEvents: Array<Event> = [];
  currentPage: number;
  size: number;
  totalItems: number;
  user: User;

  constructor(
    private eventService: EventService,
    private userService: UserService,
    private authService: AuthService
  ) {
  }

  ngOnInit() {
    this.totalItems = 1;
    this.size = 10;
    this.authService.check().then((u: User) => this.getAllEvents(u, 1));
  }

  private getAllEvents(user: User, page: number) {
    this.currentPage = page;
    const pageRequest = new PageRequest();
    pageRequest.page = this.currentPage - 1;
    pageRequest.size = this.size;
    this.eventService.get(pageRequest).subscribe((r: PageResponse<Event>) => {
      this.events = r.results;
      this.size = r.size;
      this.totalItems = r.totalPages * r.size;
    });
    this.refreshEventUser(user);
  }

  private refreshEventUser(user: User) {
    this.user = user;
    this.subscribedEvents = user.events;
  }

  onPageChange(event) {
    this.getAllEvents(this.user, event);
  }

  isSubscribed(event: Event) {
    return this.subscribedEvents.map(e => e.id).includes(event.id);
  }

  toggleSubscribe(event: Event) {
    if (!this.isSubscribed(event)) {
      this.userService.addEventToCurrentUser(event.id).subscribe((u: any) => this.refreshEventUser(u));
    } else {
      this.userService.removeEventToCurrentUser(event.id).subscribe((u: any) => this.refreshEventUser(u));
    }
  }
}

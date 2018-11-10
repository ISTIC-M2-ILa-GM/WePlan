import {UserService} from './../../services/user.service';
import {AuthService} from './../../services/auth.service';
import {Component, OnInit} from '@angular/core';
import {EventService} from "../../services/event.service";
import {PageRequest} from "../../models/request/page.request";
import {Event} from "../../models/dto/event";
import {PageResponse} from "../../models/response/page.response";

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

  constructor(
    private eventService: EventService,
    private userService: UserService,
    private authService: AuthService
  ) { }

  ngOnInit() {
    this.totalItems = 1;
    this.size = 10;
    this.getAllEvents(1);
  }

  private getAllEvents(page: number) {
    this.currentPage = page;
    const pageRequest = new PageRequest();
    pageRequest.page = this.currentPage - 1;
    pageRequest.size = this.size;
    console.log("b" + JSON.stringify(pageRequest));
    this.eventService.get(pageRequest).subscribe((r: PageResponse<Event>) => {
      this.events = r.results;
      this.size = r.size;
      this.totalItems = r.totalPages * r.size;
    });
    this.refreshEventUser();
  }

  private refreshEventUser() {
    // TODO refactor userService without restService
    // this.subscribedEvents = r.events;
  }

  onPageChange(event) {
    this.getAllEvents(event);
  }

  isSubscribed(event: Event) {
    return this.subscribedEvents.map(e => e.id).includes(event.id);
  }

  toggleSubscribe(event: Event) {
    if (this.isSubscribed(event)) {
      this.userService.addEventToCurrentUser(event.id).subscribe(this.refreshEventUser);
    } else {
      this.userService.removeEventToCurrentUser(event.id).subscribe(this.refreshEventUser);
    }
  }
}

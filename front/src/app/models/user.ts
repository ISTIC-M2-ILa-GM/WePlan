export class User {
  firstName: string;
  lastName: string;
  email: string;
  password: string;

  constructor (first: string, last: string, email: string) {
    this.firstName = first;
    this.lastName = last;
    this.email = email;
  }
}

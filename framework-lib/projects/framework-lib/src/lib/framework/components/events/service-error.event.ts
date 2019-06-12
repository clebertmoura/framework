import { HttpErrorResponse } from '@angular/common/http';

export enum ServiceErrorType {
  GET, LIST, INSERT, UPDATE, DELETE
}

export class ServiceErrorEvent {

  constructor(public eventType: ServiceErrorType, public error: HttpErrorResponse) {
  }

}

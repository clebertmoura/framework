import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { ISpinnerState } from './spinner';

@Injectable()
export class SpinnerService {

  public spinnerSubject = new Subject<boolean>();

  // public spinner$ = this.spinnerSubject.asObservable();

  show() {
    this.spinnerSubject.next(true);
  }

  hide() {
    this.spinnerSubject.next(false);
  }
}

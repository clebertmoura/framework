import {Component, OnDestroy, OnInit} from '@angular/core';
import { Subscription, Observable } from 'rxjs';

import { SpinnerService } from './spinner.service';


@Component({
  selector: 'app-loading-spinner',
  templateUrl: './spinner.component.html',
  styleUrls: ['./spinner.component.css']
})

export class SpinnerComponent implements OnDestroy, OnInit {

  public show$: Observable<boolean>;

  constructor(private spinnerService: SpinnerService) { }

  ngOnInit() {
    this.show$ = this.spinnerService.spinnerSubject.asObservable();
  }

  ngOnDestroy() {
    this.spinnerService.spinnerSubject.complete();
  }
}

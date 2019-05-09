import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-loading-spinner-overlay',
  templateUrl: './loading-spinner.component.html',
  styleUrls: ['./loading-spinner.component.scss']
})
export class LoadingSpinnerComponent implements OnInit {

  @Input()
  label: string;

  constructor() { }

  ngOnInit() {
  }

}

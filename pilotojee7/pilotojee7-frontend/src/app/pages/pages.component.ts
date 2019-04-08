import { Component, OnDestroy, ChangeDetectorRef, ViewChild } from '@angular/core';
import { MatSidenav } from '@angular/material';
import { MediaMatcher } from '@angular/cdk/layout';

@Component({
  selector: 'app-pages',
  templateUrl: './pages.component.html',
  styleUrls: ['./pages.component.scss']
})
export class PagesComponent implements OnDestroy {

  @ViewChild('sidenav') public sidenav: MatSidenav;

  mobileQuery: MediaQueryList;

  title = 'pilotojee7-frontend';

  private _mobileQueryListener: () => void;

  constructor(changeDetectorRef: ChangeDetectorRef, media: MediaMatcher) {
    this.mobileQuery = media.matchMedia('(max-width: 767.98px)');
    this._mobileQueryListener = () => changeDetectorRef.detectChanges();
    this.mobileQuery.addListener(this._mobileQueryListener);
  }

  ngOnDestroy(): void {
    this.mobileQuery.removeListener(this._mobileQueryListener);
  }

  closeSidenav(): void {
    if (this.mobileQuery.matches) {
      this.sidenav.close();
    }
  }

}

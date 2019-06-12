import { Component, OnDestroy, ChangeDetectorRef, ViewChild, OnInit } from '@angular/core';
import { MatSidenav } from '@angular/material';
import { MediaMatcher } from '@angular/cdk/layout';
import { AuthService } from '../core/security/services/auth.service';

@Component({
  selector: 'app-pages',
  templateUrl: './pages.component.html',
  styleUrls: ['./pages.component.scss']
})
export class PagesComponent implements OnDestroy, OnInit {

  @ViewChild('sidenav') public sidenav: MatSidenav;

  mobileQuery: MediaQueryList;

  title = 'pilotojee7-frontend';

  private userFullName = '';

  private _mobileQueryListener: () => void;

  constructor(
    changeDetectorRef: ChangeDetectorRef,
    media: MediaMatcher,
    private authService: AuthService
  ) {
    this.mobileQuery = media.matchMedia('(max-width: 767.98px)');
    this._mobileQueryListener = () => changeDetectorRef.detectChanges();
    this.mobileQuery.addListener(this._mobileQueryListener);
  }

  ngOnInit(): void {
    this.authService.loadUserInfo().then(user => {
      if (user) {
        this.userFullName = user.fullName;
      }
    });
  }

  ngOnDestroy(): void {
    this.mobileQuery.removeListener(this._mobileQueryListener);
  }

  closeSidenav(): void {
    this.sidenav.close();
  }

  logout(): void {
    this.authService.logout();
  }

}
import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { Router } from '@angular/router';

import { constants } from './../../app.constants';
import { AuthService } from '../security/services/auth.service';
import { DataSharingService } from 'framework-lib';

@Component({
  selector: 'app-error',
  templateUrl: './error.component.html',
  styleUrls: ['./error.component.scss']
})
export class ErrorComponent implements OnInit, OnDestroy {

  private errorSubscription: Subscription;

  title = 'Erro';
  message = 'Ocorreu um erro inesperado.';
  showHomeOption = false;
  showLogoutOption = false;

  constructor(
    private router: Router,
    private dataService: DataSharingService,
    private authService: AuthService
  ) { }

  ngOnInit() {
    this.errorSubscription = this.dataService
      .onDataChange(constants.errorDataKey)
      .subscribe(data => {
        const error = data;
        this.updateError(error);
      });
  }

  private updateError(error: any): void {
    if (error) {
      this.message = error.message ? error.message : 'Ocorreu um erro inesperado.';
      this.title = error.title ? error.title : 'Erro';
      this.showHomeOption = error.showHomeOption;
      this.showLogoutOption = error.showLogoutOption;
    }
  }

  goToHome() {
    this.router.navigateByUrl('/');
  }

  logout(): void {
    this.authService.logout();
  }

  ngOnDestroy(): void {
    this.errorSubscription.unsubscribe();
  }

}

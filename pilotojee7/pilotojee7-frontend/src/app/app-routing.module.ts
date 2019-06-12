import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AppAuthGuard } from './keycloak/app.authguard';
import { ErrorComponent } from './core/errors/error.component';
import { constants } from './app.constants';

const routes: Routes = [
  { path: 'pages',
    loadChildren: './pages/pages.module#PagesModule',
    canActivate: [AppAuthGuard],
    data: {
      roles: [constants.loginRole]
    }
  },
  { path: '', redirectTo: 'pages', pathMatch: 'full', canActivate: [AppAuthGuard] },
  { path: 'error', component: ErrorComponent },
  { path: '**', redirectTo: 'pages' },
];

@NgModule({
  imports: [
    RouterModule.forRoot(
      routes,
      { enableTracing: true } // <-- debugging purposes only
    ),
  ],
  exports: [RouterModule],
  declarations: []
})
export class AppRoutingModule {}

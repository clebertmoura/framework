import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AppAuthGuard } from './keycloak/app.authguard';

const routes: Routes = [
  { path: 'pages', loadChildren: './pages/pages.module#PagesModule' },
  { path: '', redirectTo: 'pages', pathMatch: 'full',  canActivate: [AppAuthGuard] },
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

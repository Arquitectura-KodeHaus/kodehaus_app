import { bootstrapApplication } from '@angular/platform-browser';
import { importProvidersFrom } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { DashboardComponent } from './app/dashboard/dashboard.component';

bootstrapApplication(DashboardComponent, {
  providers: [importProvidersFrom(HttpClientModule)]
});

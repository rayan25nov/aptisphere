import { Routes } from '@angular/router';
import { Landing } from './pages/landing/landing';
import { AppShell } from './components/layout/app-shell/app-shell';

export const routes: Routes = [
  // 1. Landing page is the core base path route
  {
    path: '',
    component: Landing,
    pathMatch: 'full',
  },
  // 2. Protected paths wrapped underneath our AppShell layout component frame
  {
    path: '',
    component: AppShell,
    children: [
      // We will mount individual pages like dashboard here next!
    ],
  },
  // Global 404 Fallback reroutes home for now
  {
    path: '**',
    redirectTo: '',
  },
];

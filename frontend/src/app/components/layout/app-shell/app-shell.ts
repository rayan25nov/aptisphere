import { Component, signal, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet, RouterLink, RouterLinkActive } from '@angular/router';

// 1. Swap LucideIcon for LucideDynamicIcon
import {
  LucideDynamicIcon,
  LucideLayoutDashboard,
  LucideFileText,
  LucideHistory,
  LucideBarChart3,
  LucideUser,
  LucideShieldCheck,
  LucideUsers,
  LucideListChecks,
  LucidePlusCircle,
  LucideMenu,
  LucideX,
  LucideLogOut,
  LucideMoon,
  LucideSun,
} from '@lucide/angular';

type Role = 'admin' | 'candidate';
interface NavItem {
  label: string;
  to: string;
  icon: any;
}

@Component({
  selector: 'app-shell',
  standalone: true,
  // 2. Add LucideDynamicIcon here so Angular recognizes the template directive
  imports: [CommonModule, RouterOutlet, RouterLink, RouterLinkActive, LucideDynamicIcon],
  templateUrl: './app-shell.html',
})
export class AppShell implements OnInit {
  isOpen = signal(false);
  isDark = signal(false);
  role = signal<Role>('candidate');

  icons = {
    Menu: LucideMenu,
    X: LucideX,
    ShieldCheck: LucideShieldCheck,
    Moon: LucideMoon,
    Sun: LucideSun,
    LogOut: LucideLogOut,
  };

  candidateNav: NavItem[] = [
    { label: 'Dashboard', to: '/dashboard', icon: LucideLayoutDashboard },
    { label: 'Mock Tests', to: '/exams', icon: LucideFileText },
    { label: 'Attempt History', to: '/history', icon: LucideHistory },
    { label: 'Analytics', to: '/analytics', icon: LucideBarChart3 },
    { label: 'Profile', to: '/profile', icon: LucideUser },
  ];

  adminNav: NavItem[] = [
    { label: 'Dashboard', to: '/admin', icon: LucideLayoutDashboard },
    { label: 'Exams', to: '/admin/exams', icon: LucideFileText },
    { label: 'Question Bank', to: '/admin/questions', icon: LucideListChecks },
    { label: 'Create Exam', to: '/admin/create-exam', icon: LucidePlusCircle },
    { label: 'Users', to: '/admin/users', icon: LucideUsers },
    { label: 'Analytics', to: '/admin/analytics', icon: LucideBarChart3 },
  ];

  get currentNav(): NavItem[] {
    return this.role() === 'admin' ? this.adminNav : this.candidateNav;
  }

  ngOnInit() {}

  toggleMenu() {
    this.isOpen.update((v) => !v);
  }

  toggleTheme() {
    this.isDark.update((v) => !v);
    if (typeof document !== 'undefined') {
      document.documentElement.classList.toggle('dark', this.isDark());
    }
  }

  switchRole() {
    this.role.update((r) => (r === 'admin' ? 'candidate' : 'admin'));
  }

  handleLogout() {
    console.log('Logout clicked');
  }
}

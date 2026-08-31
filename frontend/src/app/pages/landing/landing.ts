// frontend/src/app/pages/landing/landing.component.ts
import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { RouterLink } from '@angular/router';
import {
  LucideDynamicIcon,
  LucideArrowRight,
  LucideBarChart3,
  LucideCheckCircle2,
  LucideClock,
  LucideListChecks,
  LucideShieldCheck,
  LucideSparkles,
  LucideStar,
  LucideTrophy,
  LucideUsers,
} from '@lucide/angular';

@Component({
  selector: 'app-landing',
  standalone: true,
  imports: [RouterLink, LucideDynamicIcon],
  templateUrl: './landing.html',
})
export class Landing implements OnInit {
  currentYear = new Date().getFullYear();

  // Expose icons directly to the HTML template
  icons = {
    ArrowRight: LucideArrowRight,
    Sparkles: LucideSparkles,
    Clock: LucideClock,
    Star: LucideStar,
  };

  features = [
    {
      icon: LucideClock,
      title: 'Realistic exam interface',
      desc: 'Timer, navigator, mark-for-review and auto-save — built to mirror real online assessments.',
    },
    {
      icon: LucideBarChart3,
      title: 'Deep analytics',
      desc: 'Section-wise accuracy, time per question, and improvement trends over time.',
    },
    {
      icon: LucideListChecks,
      title: 'Curated question bank',
      desc: '20,000+ questions across quant, reasoning, verbal, DI and GK.',
    },
    {
      icon: LucideTrophy,
      title: 'Track progress',
      desc: 'Your dashboard shows attempts, ranks, weak areas and recommended practice.',
    },
    {
      icon: LucideShieldCheck,
      title: 'Admin tooling',
      desc: 'Create exams, manage sections, add questions and monitor performance at scale.',
    },
    {
      icon: LucideUsers,
      title: 'Built for teams too',
      desc: 'Use AptiSphere for placement drives, internal hiring assessments and group practice.',
    },
  ];

  steps = [
    {
      icon: LucideCheckCircle2,
      title: 'Create your account',
      desc: 'Sign up free — no credit card required.',
    },
    {
      icon: LucideListChecks,
      title: 'Pick a mock test',
      desc: 'Filter by category, difficulty and duration.',
    },
    {
      icon: LucideBarChart3,
      title: 'Practice & improve',
      desc: 'Take the test, review your analytics, repeat.',
    },
  ];

  testimonials = [
    {
      name: 'Priya Patel',
      role: 'Final year, B.Tech',
      quote:
        'The section-wise analytics helped me identify exactly where I was losing marks. Cleared TCS in my second attempt.',
    },
    {
      name: 'Rahul Verma',
      role: 'MBA aspirant',
      quote:
        "Easily the cleanest exam interface I've used. The navigator and mark-for-review feel exactly like the real CAT mock.",
    },
    {
      name: 'Sneha Iyer',
      role: 'Placement Coordinator',
      quote: 'We run our entire campus placement drill on AptiSphere. Admin panel is a breeze.',
    },
  ];

  // Dummy helper array to render the 18 question squares in the hero preview
  questionGrid = Array.from({ length: 18 });
  // Dummy options array for mock questions
  mockOptions = ['50 km/h', '60 km/h', '70 km/h', '80 km/h'];

  constructor(private titleService: Title) {}

  ngOnInit() {
    // Replaces the TanStack Route head metadata rule for page titles
    this.titleService.setTitle('AptiSphere — Aptitude Mock Tests & Online Exam Platform');
  }

  // Pure function helper method mimicking the Stat sub-component logic
  getOptionChar(index: number): string {
    return String.fromCharCode(65 + index);
  }
}

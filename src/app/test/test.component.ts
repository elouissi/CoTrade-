import { Component } from '@angular/core';
import {NgForOf} from "@angular/common";

@Component({
  selector: 'app-test',
  standalone: true,
  imports: [
    NgForOf
  ],
  templateUrl: './test.component.html',
  styleUrl: './test.component.css'
})
export class TestComponent {
  tasks: string[] = []; // Tableau de tâches

  constructor() { }

  ngOnInit(): void {
    // Initialisation des données
    this.tasks = [
      'Apprendre Angular',
      'Créer un projet Angular',
      'Utiliser les services Angular'
    ];
    console.log('Les tâches ont été initialisées.');
  }

}

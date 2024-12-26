import { Component } from '@angular/core';
import {RouterLink, RouterOutlet} from '@angular/router';
import {TestComponent} from "./test/test.component";
import {HeaderComponent} from "./header/header.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterLink, HeaderComponent,],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',

})
export class AppComponent {

  title = 'huner-front';

}
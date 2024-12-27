import { Component } from '@angular/core';
import { RouterOutlet} from '@angular/router';
import {HeaderComponent} from "./header/header.component";
import {FlowbiteService} from "./services/flowbite.service";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, HeaderComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',

})
export class AppComponent {
  title = 'huner-front';


  constructor(private flowbiteService: FlowbiteService) {
  }

  ngOnInit(): void {
    this.flowbiteService.loadFlowbite(flowbite => {
      // Your custom code here
      console.log('Flowbite loaded', flowbite);
    });
  }
}






import { Component } from '@angular/core';
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    FormsModule
  ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {

  // @ts-ignore
  name : string="";
  counter:number=1;
  acrement(){
    this.counter ++ ;
  }
  deacrement(){
    this.counter -- ;
  }

}

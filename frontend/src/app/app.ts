import { Component } from '@angular/core';
import { BeneficioPainelComponent } from './components/beneficio-painel/beneficio-painel';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [BeneficioPainelComponent],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class AppComponent {
  title = 'frontend';
}

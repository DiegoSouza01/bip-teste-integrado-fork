import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { BeneficioService } from '../../services/beneficio';
import { Beneficio, TransferenciaDTO } from '../../models/beneficio';

@Component({
  selector: 'app-beneficio-painel',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './beneficio-painel.html',
  styleUrl: './beneficio-painel.css'
})
export class BeneficioPainelComponent implements OnInit {

  beneficios: Beneficio[] = [];
  transferencia: TransferenciaDTO = { fromId: 0, toId: 0, amount: 0 };
  mensagem: string = '';

  constructor(private service: BeneficioService) {}

  ngOnInit(): void {
    this.carregarBeneficios();
  }

  carregarBeneficios(): void {
    this.service.listarTodos().subscribe({
      next: (dados) => this.beneficios = dados,
      error: (err) => console.error('Erro ao buscar benefícios:', err)
    });
  }

  fazerTransferencia(): void {
    this.service.transferir(this.transferencia).subscribe({
      next: (resposta) => {
        this.mensagem = resposta;
        this.carregarBeneficios();
      },
      error: (err) => {
        this.mensagem = (err.error || 'Erro inesperado na transferência');
      }
    });
  }
}

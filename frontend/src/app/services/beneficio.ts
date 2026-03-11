import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Beneficio, TransferenciaDTO } from '../models/beneficio';

@Injectable({
  providedIn: 'root'
})
export class BeneficioService {

  private apiUrl = 'http://localhost:8080/api/beneficios';

  constructor(private http: HttpClient) { }


  listarTodos(): Observable<Beneficio[]> {
    return this.http.get<Beneficio[]>(this.apiUrl);
  }

  criar(beneficio: Beneficio): Observable<Beneficio> {
    return this.http.post<Beneficio>(this.apiUrl, beneficio);
  }

  deletar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }


  transferir(dto: TransferenciaDTO): Observable<string> {
return this.http.post(`${this.apiUrl}/transfer`, dto, { responseType: 'text' }) as Observable<string>;  }
}

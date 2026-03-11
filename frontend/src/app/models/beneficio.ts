export interface Beneficio {
  id?: number;
  nome: string;
  descricao: string;
  valor: number;
  ativo: boolean;
  version?: number;
}

export interface TransferenciaDTO {
  fromId: number;
  toId: number;
  amount: number;
}

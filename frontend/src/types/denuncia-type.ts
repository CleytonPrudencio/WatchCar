import type { ArtigoProps } from "./artigo-type";
import type { EnderecoProps } from "./endereco-type";
import type { OcorrenciaTypeProps } from "./ocorrencia-type";
import type { UserSimpleProps, UsuarioGestorProps } from "./user-type";
import type { VeiculoProps } from "./veiculo-type";

export interface DenunciaProps{

  idOcorrencia: number;
  data: string;
  descricaoOcorrencia: string;
  dataHoraOcorrencia: string;

  localOcorrencia: EnderecoProps;
  gestorSecurity: UsuarioGestorProps;

  denunciante: UserSimpleProps;
  localDaOcorrencia: EnderecoProps;
  veiculos: VeiculoProps[];
  artigo: ArtigoProps;

  tipoOcorrencia: OcorrenciaTypeProps; // 'FURTO', 'ROUBO', 'HOMICIDIO', 'ESTUPRO', 'OUTROS'
  statusOcorrencia: string;// 'PENDENTE', 'EM ANDAMENTO', 'CONCLUIDA', 'CANCELADA'

}

export type EtapaProps = {
  valor: number;
  avancar: boolean
}

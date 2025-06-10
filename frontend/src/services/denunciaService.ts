import type { DenunciaProps, EtapaProps } from '@/types/denuncia-type'

export const getEtapa = (denuncia: DenunciaProps, etapas: EtapaProps[]) => {
  if (!denuncia) {
    return
  }
  if (denuncia.denunciante) etapas[0].avancar = false
  if (denuncia.denunciante.name && denuncia.denunciante.email && denuncia.denunciante.cpf) {
    etapas[0].avancar = true
  }

  // Local da ocorrência
  if (denuncia.localDaOcorrencia) {
    etapas[1].avancar = false
    if (
      denuncia.localDaOcorrencia.cep &&
      denuncia.localDaOcorrencia.cep.length >= 8 &&
      denuncia.localDaOcorrencia.logradouro
    ) {
      etapas[1].avancar = true
    }
  }

  // Dados do veículo
  if (denuncia.veiculos && denuncia.veiculos.length > 0) {
    etapas[2].avancar = false
    if (
      denuncia.tipoOcorrencia &&
      denuncia.veiculos[0].placaVeiculo &&
      denuncia.veiculos[0].anoVeiculo &&
      denuncia.veiculos[0].marcaVeiculo &&
      denuncia.veiculos[0].modeloVeiculo &&
      denuncia.veiculos[0].corVeiculo
    ) {
      etapas[2].avancar = true
    }
  }

  // Descrição da ocorrência
  if (denuncia.dataOcorrencia) {
    etapas[3].avancar = false
    if (
      denuncia.dataOcorrencia &&
      /^\d{4}-\d{2}-\d{2}$/.test(denuncia.dataOcorrencia) &&
      denuncia.dataHoraOcorrencia &&
      /^\d{2}:\d{2}$/.test(denuncia.dataHoraOcorrencia) &&
      denuncia.descricaoOcorrencia && denuncia.descricaoOcorrencia.length >= 10
    ) {
      etapas[3].avancar = true
    }
  }
}

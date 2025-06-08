import type { DenunciaProps, EtapaProps } from '@/types/denuncia-type'

export const getEtapa = (denuncia: DenunciaProps, etapas: EtapaProps[]) => {
  if (!denuncia) {
    return
  }
  if (denuncia.denunciante) etapas[0].avancar = false
  if (denuncia.denunciante.name && denuncia.denunciante.email && denuncia.denunciante.cpf) {
    etapas[0].avancar = true
  }

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
}

import type { TipoOcorrenciaType } from '@/types/tipoOcorrencia'
import api from './api'

// Buscar todos os os tipos de ocorrências
export const findAll = async (): Promise<TipoOcorrenciaType[]> => {
  try {
    const response = await api.get<TipoOcorrenciaType[]>('/v1/tipos_ocorrencias')
    return response.data
  } catch (error) {
    console.error('Erro ao buscar todos os tipos de ocorrências:', error)
    throw error
  }
}
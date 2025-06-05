import type { TipoOcorrenciaType } from '@/types/tipoOcorrencia'
import api from './api'

// Buscar todos os os tipos de ocorrências
export const findAll = async (): Promise<TipoOcorrenciaType[]> => {
  try {
    const response = await api.get('/v1/tipos_ocorrencias');
    const data = response.data;
    if(data.success){
      return data.content;
    }
    throw new Error('Erro ao buscar os tipos de ocorrências')
  } catch (error) {
    console.error('Erro ao buscar todos os tipos de ocorrências:', error)
    throw error
  }
}
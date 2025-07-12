import type { OcorrenciaTypeProps } from '@/types/ocorrencia-type';
import { requestWatchCar } from '@/utils/requests';

// Buscar todos os os tipos de ocorrências
export const findAll = async (): Promise<OcorrenciaTypeProps[]> => {
  try {
    const response = await requestWatchCar().get<OcorrenciaTypeProps[]>('/api/tipos_ocorrencias');
    if (!response.data) {
      throw new Error('Nenhum tipo de ocorrência encontrado');
    }
    return response.data.content;
  } catch (error) {
    console.error('Erro ao buscar todos os tipos de ocorrências:', error)
    throw error
  }
}

import api from './api'

// Buscar todos os os tipos de ocorrências
export const findAll = async () => {
  try {
    const response = await api.get('/api/v1/ocorrencias', {})
    return response.data
  } catch (error) {
    console.error('Erro ao buscar todos os tipos de ocorrências:', error)
    throw error
  }
}
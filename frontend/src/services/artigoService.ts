// src/services/artigoService.ts
import { requestWatchCar } from '@/utils/requests'
import api from './api'
import type { ArtigoProps } from '@/types/artigo-type'

// URL base para os artigos
const url = '/api/artigos'

// Função para obter o token de autenticação
const getAuthToken = () => {
  return localStorage.getItem('authToken')
}

// Buscar todos os artigos
export const buscarArtigos = async (): Promise<ArtigoProps[]> => {
  try {
    const response = await requestWatchCar().get<ArtigoProps[]>(`${url}`)
    if(!response.data || !Array.isArray(response.data)) {
      throw new Error('Resposta inválida: dados não são um array')
    }
    return response.data as ArtigoProps[]
  } catch (error) {
    console.error('Erro ao buscar artigos:', error)
    throw error
  }
}

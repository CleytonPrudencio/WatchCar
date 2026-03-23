// src/services/cepService.ts
import api from './api'
import { toast } from 'vue3-toastify'

// Interface para dados do endereço
export interface EnderecoData {
  cep: string
  logradouro: string
  complemento?: string
  bairro: string
  localidade: string
  uf: string
  erro?: boolean
}

// Função para buscar endereço por CEP
export const buscarEnderecoPorCep = async (cep: string): Promise<EnderecoData | null> => {
  try {
    // Remove formatação do CEP
    const cleanCep = cep.replace(/[^0-9]/g, '')
    
    if (cleanCep.length !== 8) {
      toast.error('CEP deve ter 8 dígitos')
      return null
    }
    
    const response = await api.get(`/viacep/${cleanCep}`)
    
    if (response.data.erro) {
      toast.error('CEP não encontrado')
      return null
    }
    
    return {
      cep: response.data.cep,
      logradouro: response.data.logradouro,
      complemento: response.data.complemento,
      bairro: response.data.bairro,
      localidade: response.data.localidade,
      uf: response.data.uf
    }
  } catch (error: any) {
    console.error('Erro ao buscar CEP:', error)
    
    if (error.response?.data?.message) {
      toast.error(error.response.data.message)
    } else {
      toast.error('Erro ao buscar informações do CEP')
    }
    
    return null
  }
}

// Função para validar formato do CEP
export const validarFormatoCep = (cep: string): boolean => {
  const cepRegex = /^\d{5}-?\d{3}$/
  return cepRegex.test(cep)
}
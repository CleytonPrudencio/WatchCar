// src/services/userService.ts
import api from './api'
import { toast } from 'vue3-toastify'

// Interface para dados de cadastro
export interface RegisterData {
  nome: string
  email: string
  senha: string
  confirmSenha: string
  cpf: string
  telefone?: string
  tipoUsuario: string
  cep: string
  logradouro?: string
  numero?: string
  complemento?: string
  bairro?: string
  cidade?: string
  estado?: string
}

// Interface para resposta de erro
interface ErrorField {
  field: string
  message: string
}

interface ValidationError {
  message: string
  fields?: ErrorField[]
}

// Função para cadastrar usuário
export const cadastrarUsuario = async (userData: RegisterData) => {
  try {
    const response = await api.post('/users/register', userData)
    
    if (response.data.success) {
      toast.success(response.data.message || 'Usuário cadastrado com sucesso!')
      return response.data
    }
  } catch (error: any) {
    console.error('Erro no cadastro:', error)
    
    if (error.response?.data) {
      const errorData = error.response.data
      
      // Tratamento de erros de validação
      if (errorData.errors?.fields) {
        const fieldErrors = errorData.errors.fields
        fieldErrors.forEach((fieldError: ErrorField) => {
          toast.error(`${fieldError.field}: ${fieldError.message}`)
        })
      } else if (errorData.message) {
        toast.error(errorData.message)
      } else {
        toast.error('Erro ao cadastrar usuário')
      }
    } else {
      toast.error('Erro de conexão com o servidor')
    }
    
    throw error
  }
}

// Função para validar CPF
export const validarCpf = (cpf: string): boolean => {
  const cleanCpf = cpf.replace(/[^0-9]/g, '')
  
  if (cleanCpf.length !== 11) {
    return false
  }
  
  if (/^(\d)\1{10}$/.test(cleanCpf)) {
    return false
  }
  
  let sum = 0
  for (let i = 0; i < 9; i++) {
    sum += parseInt(cleanCpf.charAt(i)) * (10 - i)
  }
  let firstDigit = 11 - (sum % 11)
  if (firstDigit >= 10) firstDigit = 0
  
  sum = 0
  for (let i = 0; i < 10; i++) {
    sum += parseInt(cleanCpf.charAt(i)) * (11 - i)
  }
  let secondDigit = 11 - (sum % 11)
  if (secondDigit >= 10) secondDigit = 0
  
  return parseInt(cleanCpf.charAt(9)) === firstDigit && parseInt(cleanCpf.charAt(10)) === secondDigit
}

// Função para validar email
export const validarEmail = (email: string): boolean => {
  const emailRegex = /^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\.[A-Za-z]{2,})$/
  return emailRegex.test(email)
}

// Função para validar senha forte
export const validarSenha = (senha: string): boolean => {
  const senhaRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/
  return senhaRegex.test(senha)
}

// Função para formatar CPF
export const formatarCpf = (cpf: string): string => {
  const cleanCpf = cpf.replace(/[^0-9]/g, '')
  return cleanCpf.replace(/(\d{3})(\d{3})(\d{3})(\d{2})/, '$1.$2.$3-$4')
}

// Função para formatar CEP
export const formatarCep = (cep: string): string => {
  const cleanCep = cep.replace(/[^0-9]/g, '')
  return cleanCep.replace(/(\d{5})(\d{3})/, '$1-$2')
}

// Função para formatar telefone
export const formatarTelefone = (telefone: string): string => {
  const cleanPhone = telefone.replace(/[^0-9]/g, '')
  if (cleanPhone.length === 10) {
    return cleanPhone.replace(/(\d{2})(\d{4})(\d{4})/, '($1) $2-$3')
  } else if (cleanPhone.length === 11) {
    return cleanPhone.replace(/(\d{2})(\d{5})(\d{4})/, '($1) $2-$3')
  }
  return telefone
}
import type { EnderecoProps } from '@/types/endereco-type'
import type { ErrorType } from '@/types/erros-type'
import axios from 'axios'

export const getPerfil = (roles: string[]): string => {
  if (!roles || roles === undefined || roles.length === 0) {
    return 'Público'
  }
  const perfil = []
  for (const [key, val] of Object.entries(roles)) {
    switch (val) {
      case 'POLICIAL':
        perfil.push('Polícial')
        break
      case 'AGENTE DE SEGURANCA':
        perfil.push('Agente de Segurança')
        break
      case 'INVESTIGADOR':
        perfil.push('Investigador')
        break
      case 'GESTOR DE SEGURANCA PUBLICA':
        perfil.push('Gestor de Segurança Pública')
        break
      default:
        perfil.push('Público')
    }
  }
  return perfil.join(', ')
}

export const validations = (props: any, error: ErrorType): boolean => {
  for (const [key, val] of Object.entries(props)) {
    const value = props[key]
    switch (key) {
      case 'name':
        if (!validationName(value)) {
          error.name = 'name'
          error.message = 'Name must be at least 5 characters long'
        }
        break
      case 'cpf':
        if (!isValidCPF(value)) {
          error.name = 'cpf'
          error.message = 'CPF is not valid'
        }
        break
      case 'email':
        if (!validaEmail(value)) {
          error.name = 'email'
          error.message = 'Email is not valid'
        }
        break
      case 'password':
        if (value.length < 6) {
          error.name = 'password'
          error.message = 'Password must be at least 6 characters long'
        }
        break
      case 'delegacia':
        if (isNullOrEmpty(value) || value.length < 3) {
          error.name = 'delegacia'
          error.message = 'Delegacia is required'
        }
        break
      case 'distintivo':
        if (isNullOrEmpty(value) || value.length < 3) {
          error.name = 'distintivo'
          error.message = 'Distintivo is required'
        }
        break
      case 'ar':
        if (isNullOrEmpty(value) || value.length < 3) {
          error.name = 'ra'
          error.message = '(RA) is required'
        }
        break
      case 'departamento':
        if (isNullOrEmpty(value) || value.length < 3) {
          error.name = 'ra'
          error.message = 'Departamento is required'
        }
        break
      case 'cargo':
        if (isNullOrEmpty(value) || value.length < 3) {
          error.name = 'cargo'
          error.message = 'Cargo is required'
        }
        break
      case 'dataOcorrencia':
        if (isNullOrEmpty(value) || !/^\d{4}-\d{2}-\d{2}$/.test(value)) {
          error.name = 'dataOcorrencia'
          error.message = 'Data da ocorrência é obrigatória'
        }
        break
      case 'dataHoraOcorrencia':
        if (isNullOrEmpty(value) || !/^\d{2}:\d{2}$/.test(value)) {
          error.name = 'dataHoraOcorrencia'
          error.message = 'Hora da ocorrência é obrigatória'
        }
        break
      case 'descricaoOcorrencia':
        if (isNullOrEmpty(value) || value.length < 10) {
          error.name = 'descricaoOcorrencia'
          error.message =
            'Descrição da ocorrência é obrigatória e deve ter pelo menos 10 caracteres'
        }
        break
      default:
        error.name = ''
        error.message = ''
        delete props.error
    }
    if (error.name.length > 0) {
      break
    }
  }
  return error.name.length == 0
}

// Função para validar NOME
export const validationName = (name: string): boolean => {
  if (name === null || name === undefined || name.length < 5) {
    return false
  }
  return true
}

export function formatPlaca(placa: string): string {
  const valor = placa.toUpperCase()

  // Remove caracteres não alfanuméricos
  if (/^\d/.test(valor)) {
    return valor.replace(/^\d+/, '')
  }

  // Formato novo: ABC1234 -> ABC-1234
  if (/^[A-Z]{3}\d{2}.*$/.test(valor)) {
    // Modelo antigo: ABC1234 -> ABC-1234
    return valor.replace(/^([A-Z]{3})(\d{2})$/, '$1-$2')
  }
  return valor
}

export function formatAno(ano: string): string {
  const valor = parseInt(ano.replace(/\D/g, '')) // Remove caracteres não numéricos

  if (isNaN(valor)) {
    return '' // Retorna string vazia se não for um número válido
  }

  if (ano.length > 3 && valor < 1900) {
    return '1900' // Limita o ano mínimo a 1900
  }
  if (ano.length > 3 && valor > 2999) {
    return '2999' // Limita o ano máximo a 2999
  }
  return '' + valor
}

// Formatação do CEP
export function formatCEP(value: string): string {
  return value.replace(/\D/g, '').replace(/(\d{5})(\d)/, '$1-$2')
}

export const buscarEndereco = async (formData: EnderecoProps) => {
  if (formData.cep && formData.cep.length == 9) {
    const cep = replaceNumbers(formData.cep) // Remove caracteres não numéricos do CEP
    try {
      const response = await axios.get(`https://viacep.com.br/ws/${cep}/json/`)
      formData.logradouro = response.data.logradouro
      formData.bairro = response.data.bairro
      formData.cidade = response.data.localidade
      formData.estado = response.data.uf
    } catch (error) {
      formData.logradouro = ''
      formData.bairro = ''
      formData.cidade = ''
      formData.estado = ''
    }
  }
}

// Função para formatar CPF
export function formatCPF(value: string): string {
  return value
    .replace(/\D/g, '')
    .replace(/(\d{3})(\d)/, '$1.$2')
    .replace(/(\d{3})(\d)/, '$1.$2')
    .replace(/(\d{3})(\d{1,2})$/, '$1-$2')
}
export function isValidCPF(cpf: string): boolean {
  if (cpf === null || cpf === undefined || cpf.length < 10) return false
  cpf = cpf.replace(/[^\d]+/g, '')
  if (cpf.length !== 11 || /^(\d)\1+$/.test(cpf)) return false

  let sum = 0
  for (let i = 0; i < 9; i++) sum += parseInt(cpf.charAt(i)) * (10 - i)
  let rev = 11 - (sum % 11)
  if (rev === 10 || rev === 11) rev = 0
  if (rev !== parseInt(cpf.charAt(9))) return false

  sum = 0
  for (let i = 0; i < 10; i++) sum += parseInt(cpf.charAt(i)) * (11 - i)
  rev = 11 - (sum % 11)
  if (rev === 10 || rev === 11) rev = 0
  return rev === parseInt(cpf.charAt(10))
}

// Função para validar EMAIL
export const validaEmail = (email: string): boolean => {
  const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
  return re.test(String(email).toLowerCase())
}

// Função para validar SENHA
export const validaPassword = (passsword: string, confirmPassword: string): boolean => {
  if (passsword === undefined || confirmPassword === undefined || passsword !== confirmPassword) {
    return false
  }
  return true
}

const isNullOrEmpty = (value: string): boolean => {
  return value === null || value.trim() === '' || value === undefined
}
const isTextSize = (value: string, length: number): boolean => {
  return value.length == length
}

export const replaceNumbers = (value: string): string => {
  return value.replace(/\D/g, '')
}

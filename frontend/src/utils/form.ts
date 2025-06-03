import type { ErrorType } from '@/types/erros-type'
import PasswordResetModal from '@/views/components/PasswordResetModal.vue'

export const validations = (props: any, error: ErrorType): boolean => {
  for (const [key, val] of Object.entries(props)) {
    const value = val as string
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
      default:
        error.name = ''
        error.message = ''
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

// Função para formatar CPF
export function formatCPF(value: string): string {
  return value
    .replace(/\D/g, '')
    .replace(/(\d{3})(\d)/, '$1.$2')
    .replace(/(\d{3})(\d)/, '$1.$2')
    .replace(/(\d{3})(\d{1,2})$/, '$1-$2')
}
export function isValidCPF(cpf: string): boolean {
  if( cpf === null || cpf === undefined || cpf.length < 10) return false;
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

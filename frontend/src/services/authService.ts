// src/services/authService.ts
import api from './api'

// Função para obter o token de autenticação
const getAuthToken = () => {
  return localStorage.getItem('authToken')
}

// Função de login
export const login = async (cpf: string, password: string) => {
  const response = await api.post('/login', { cpf, password })
  return response.data
}

// Função de registro de usuário
export const register = async (
  username: string,
  password: string,
  email: string,
  cpf: string, // Adicionando cpf
  tipo: number, // Adicionando tipo
  delegacia?: string, // Campo opcional para 'delegacia', caso seja policial
  distintivo?: string, // Campo opcional para 'distintivo', caso seja policial
  ra?: string, // Campo opcional para 'RA', caso seja policial
  departamento?: string, // Campo opcional para 'departamento', caso seja gestor
  cargo?: string, // Campo opcional para 'cargo', caso seja gestor
) => {
  // Montando o corpo da requisição de acordo com o tipo de usuário
  const body: any = {
    username,
    password,
    email,
    cpf,
    tipo,
  }

  // Adicionando campos específicos dependendo do tipo de usuário
  if (tipo === 2 || tipo === 3 || tipo === 4) {
    // Se for Policial, Agente de Segurança ou Investigador
    body.delegacia = delegacia
    body.distintivo = distintivo
    body.ra = ra
  }

  if (tipo === 5) {
    // Se for Gestor de Segurança Pública
    body.departamento = departamento
    body.cargo = cargo
  }

  // Fazendo a requisição para o backend
  const response = await api.post('/register', body)
  return response.data
}

export const fetchUserData = async () => {
  const token = getAuthToken()

  if (token) {
    try {
      const response = await api.get('/user', {
        headers: {
          Authorization: 'Bearer ' + token,
        },
      })
      return response.data
    } catch (error) {
      console.error('Erro ao buscar dados do usuário:', error)
      throw error
    }
  } else {
    throw new Error('Token de autenticação não encontrado.')
  }
}

export const forgotPassword = async (emailCpf: string) => {
  try {
    const response = await api.post('/forgotPassword', { emailCpf })
    return response.data
  } catch (error) {
    console.error('Erro ao enviar email de redefinição de senha:', error)
    throw error
  }
}

export const resetPassword = async (token: string, novaSenha: string) => {
  try {
    const response = await api.post('/reset-password', { token, novaSenha })
    return response.data
  } catch (error) {
    console.error('Erro ao redefinir senha:', error)
    throw error
  }
}

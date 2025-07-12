import type { UserAgenteProps, UserSimpleProps, UsuarioGestorProps } from '@/types/user-type'
import { requestBackEnd, requestWatchCar, requestWatchCarWithToken } from '@/utils/requests'
import { CLIENT_ID, CLIENT_SECRET } from '@/utils/system'
import type { AxiosRequestConfig } from 'axios'

const url = '/api/users'

const findByEmail = async (email: string) => {
  const headers = {
    'Content-Type': 'application/json',
    Authorization: 'Basic ' + btoa(CLIENT_ID + ':' + CLIENT_SECRET),
  }
  const config: AxiosRequestConfig = {
    method: 'POST',
    url: `${url}/register`,
    headers,
    data: email,
  }
  return requestBackEnd(config)
}

export const findById = async (idUser: number, user: UsuarioGestorProps): Promise<UsuarioGestorProps> => {
  try {
    // Validação do ID
    if (!idUser || idUser <= 0) {
      return {} as UsuarioGestorProps;
    }
    // Fazendo a requisição
    const response = await requestWatchCarWithToken().get<UsuarioGestorProps>(`${url}/${idUser}`)
    // Validação da resposta
    if (!response.data) {
      throw new Error('Dados do usuário não encontrados na resposta')
    }
    const userResponse = response.data as UsuarioGestorProps;
    if(user){
      user.id = userResponse.id
      user.name = userResponse.name
      user.email = userResponse.email
      user.cpf = userResponse.cpf
      user.ativo = userResponse.ativo
      if (userResponse.roles && Array.isArray(userResponse.roles)) {
        user.roles = [...userResponse.roles];
      }
      if(userResponse.ra) {
        user.ra = userResponse.ra;
        user.delegacia = userResponse.delegacia;
        user.distintivo = userResponse.distintivo;
      }
      if(userResponse.departamento){
        user.departamento = userResponse.departamento;
        user.cargo = userResponse.cargo;
      }
    }
    return userResponse;
  } catch (error) {
    // Tratamento diferenciado de erros
    if (error instanceof Error) {
      // Se já for um Error, apenas ajustamos a mensagem
      throw new Error(`Falha ao buscar usuário ${idUser}: ${error.message}`)
    } else {
      // Para outros tipos de erros (não esperados)
      throw new Error(`Erro desconhecido ao buscar usuário ${idUser}`)
    }
  }
}

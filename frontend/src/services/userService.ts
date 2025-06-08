import type { UserAgenteProps, UserSimpleProps } from '@/types/user-type'
import { requestBackEnd, requestWatchCar } from '@/utils/requests'
import { CLIENT_ID, CLIENT_SECRET } from '@/utils/system'
import type { AxiosRequestConfig } from 'axios'
import { toast } from 'vue3-toastify'

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

export const findById = async (idUser: number, user: UserSimpleProps | undefined): Promise<UserAgenteProps> => {
  try {
    // Validação do ID
    if (!idUser || idUser <= 0) {
      return {} as UserAgenteProps;
    }
    // Fazendo a requisição
    const response = await requestWatchCar().get<UserAgenteProps>(`${url}/${idUser}`)
    // Validação da resposta
    if (!response.data) {
      throw new Error('Dados do usuário não encontrados na resposta')
    }
    const userResponse = response.data as UserAgenteProps;
    if(user){
      user.id = userResponse.id
      user.name = userResponse.name
      user.email = userResponse.email
      user.cpf = userResponse.cpf
      user.ativo = userResponse.ativo
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

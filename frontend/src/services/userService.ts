import { CLIENT_ID, CLIENT_SECRET } from '@/utils/system'
import api from './api'
import type { AxiosRequestConfig } from 'axios'
import { requestBackEnd } from '@/utils/requests'
import type { UserSimpleRequestProps } from '@/types/user-type'

const findByEmail = async (email: string) => {
  const headers = {
    'Content-Type': 'application/json',
    Authorization: 'Basic ' + btoa(CLIENT_ID + ':' + CLIENT_SECRET),
  }
  const config: AxiosRequestConfig = {
    method: 'POST',
    url: '/api/users/register',
    headers,
    data: email,
  }
  return requestBackEnd(config)
}

export const findById = async (idUser: number) => {
  const headers = {
    'Content-Type': 'application/json',
    Authorization: 'Basic ' + btoa(CLIENT_ID + ':' + CLIENT_SECRET),
  }
  const config: AxiosRequestConfig = {
    method: 'GET',
    url: `/api/users/${idUser}`,
    headers
  }
  return requestBackEnd(config)
}

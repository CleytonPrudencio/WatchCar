import axios, { type AxiosRequestConfig } from 'axios'
import * as authService from '../services/auth-service'
import { BASE_URL } from '../utils/system'

export function requestBackEnd(config: AxiosRequestConfig) {
  const headers = config.withCredentials
    ? {
        ...config.headers,
        Authorization: 'Bearer ' + authService.getAuth(),
      }
    : config.headers
  return axios({ ...config, headers, baseURL: BASE_URL })
}

export function requestWatchCar() {
  const headers = {
    'Content-Type': 'application/json'
  }
  const config: AxiosRequestConfig = {
    headers,
  }
  return axios.create({
    ...config,
    headers,
    baseURL: BASE_URL
  })
}

export function requestWatchCarWithToken() {
  const headers = {
    'Content-Type': 'application/json',
    Authorization: 'Bearer ' + authService.getAuth().token
  }
  const config: AxiosRequestConfig = {
    headers,
  }
  return axios.create({
    ...config,
    headers,
    baseURL: BASE_URL
  })
}

import * as accessTokenRepository from "@/localstorage/access-token-repository";
import { requestBackEnd } from '@/utils/requests';
import { CLIENT_ID, CLIENT_SECRET } from "@/utils/system";
import { type AxiosRequestConfig } from 'axios';

export const loginRequest = (loginData: any) => {

  const headers = {
    "Content-Type": "application/json",
    Authorization: "Basic " + btoa(CLIENT_ID + ":" + CLIENT_SECRET),
  };
  const config: AxiosRequestConfig = {
    method: "POST",
    url: "/api/auth/login",
    headers,
    data: loginData,
  };
  return requestBackEnd(config);
}

export const registerRequest = (data: any) => {
  const headers = {
    "Content-Type": "application/json",
    Authorization: "Basic " + btoa(CLIENT_ID + ":" + CLIENT_SECRET),
  };
  const config: AxiosRequestConfig = {
    method: "POST",
    url: "/api/users/register",
    headers,
    data: data,
  };
  return requestBackEnd(config);
}

export function getAccessToken() {
  return accessTokenRepository.get();
}

export function logout() {
  accessTokenRepository.remove();
}

export function saveAccessToken(token: string) {
  accessTokenRepository.save(token);
}

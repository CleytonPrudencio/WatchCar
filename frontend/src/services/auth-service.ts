import * as accessTokenRepository from "@/localstorage/access-token-repository";
import type { UserLoginDTO } from "@/types/auth-type";
import type { AuthProps } from "@/types/user-type";
import { requestBackEnd } from '@/utils/requests';
import { CLIENT_ID, CLIENT_SECRET } from "@/utils/system";
import { type AxiosRequestConfig } from 'axios';

export const loginRequest = (loginData: UserLoginDTO) => {
  const headers = {
    "Content-Type": "application/json"
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

export function getAuth(): AuthProps {
  return accessTokenRepository.getKeys();
}

export function logout() {
  accessTokenRepository.removeToken();
}

export function saveAccessToken(token: string) {
  accessTokenRepository.saveToken(token);
}

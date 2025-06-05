import QueryString from 'qs';
import type { CredentialsDTO } from '@/types/auth-type';
import axios, { type AxiosRequestConfig } from 'axios'
import { requestBackEnd } from '@/utils/requests';
import * as accessTokenRepository from "@/localstorage/access-token-repository";
import { CLIENT_ID, CLIENT_SECRET } from "@/utils/system";

export const loginRequest = (loginData: CredentialsDTO) => {

  const headers = {
    "Content-Type": "application/x-www-form-urlencoded",
    Authorization: "Basic " + btoa(CLIENT_ID + ":" + CLIENT_SECRET),
  };
  const requestBody = QueryString.stringify({
    ...loginData,
    grant_type: "password",
  });
  const config: AxiosRequestConfig = {
    method: "POST",
    url: "/oauth2/token",
    headers,
    data: requestBody,
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

import axios, { type AxiosRequestConfig } from "axios";
import * as authService from "../services/auth-service";
import { BASE_URL } from "../utils/system";

export function requestBackEnd(config: AxiosRequestConfig) {
  const headers = config.withCredentials
    ? {
        ...config.headers,
        Authorization: "Bearer " + authService.getAccessToken(),
      }
    : config.headers;
  return axios({ ...config, headers, baseURL: BASE_URL });
}

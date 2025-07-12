import type { AuthProps } from '@/types/user-type'
import { jwtDecode } from 'jwt-decode'
import { ROLES_KEY, TOKEN_KEY, USER_ID_KEY, USER_KEY } from '../utils/system'

// Armazena o usuário decodificado no localStorage
export function saveToken(token: string) {
  const decode = jwtDecode(token) // Decodifica o token JWT para verificar se está correto
  if (decode && decode.sub) {
    localStorage.setItem(USER_ID_KEY, decode.id)
    localStorage.setItem(USER_KEY, decode.name)
    localStorage.setItem(ROLES_KEY, JSON.stringify(decode.roles))
    localStorage.setItem(TOKEN_KEY, token ?? undefined)
  }
}

export function getKeys(): AuthProps {
  const code = parseInt(String(localStorage.getItem(USER_ID_KEY)))
  const token = String(localStorage.getItem(TOKEN_KEY));
  const user = String(localStorage.getItem(USER_KEY))
  const roles = localStorage.getItem(ROLES_KEY)
  return {id: code, name: user, roles: JSON.parse(roles), token: token ?? undefined};
}

// Retorna o usuário armazenado no localStorage
export function removeToken() {
  localStorage.removeItem(USER_ID_KEY)
  localStorage.removeItem(TOKEN_KEY)
  localStorage.removeItem(USER_KEY)
  localStorage.removeItem(ROLES_KEY)
  localStorage.clear();
}

import { jwtDecode } from 'jwt-decode'
import { ROLES_KEY, TOKEN_KEY, USER_KEY } from '../utils/system'
import type { AuthProps, UserProps } from '@/types/user-type'

// Armazena o usuário decodificado no localStorage
export function save(token: string) {
  const decode = jwtDecode(token) // Decodifica o token JWT para verificar se está correto
  if (decode && decode.sub) {
    localStorage.setItem(USER_KEY, decode.sub)
    localStorage.setItem(ROLES_KEY, JSON.stringify(decode.roles))
    localStorage.setItem(TOKEN_KEY, token)
  }
}

export function getKeys(): AuthProps {
  const token = String(localStorage.getItem(TOKEN_KEY))
  const user = String(localStorage.getItem(USER_KEY))
  const roles = localStorage.getItem(ROLES_KEY)
  return {name: user, roles: JSON.parse(roles), token: token};
}

// Retorna o usuário armazenado no localStorage
export function remove() {
  localStorage.removeItem(TOKEN_KEY)
  localStorage.removeItem(USER_KEY)
  localStorage.removeItem(ROLES_KEY)
}

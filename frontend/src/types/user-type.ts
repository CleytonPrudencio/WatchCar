import type { RoleProps } from './role-type'

export interface UserSimpleProps {
  id: number
  name: string
  cpf: string
  email: string
  password: string
  roles: RoleProps[]
  ativo: boolean
}

export interface UserAgenteProps extends UserSimpleProps {
  ra: string;
  distintivo: string;
  delegacia: string;
}

export interface UsuarioGestorProps extends UserAgenteProps {
  departamento: string;
  cargo: string;
}

export interface UserSimpleRequestProps {
  id: number
  name: string
  email: string
  roles: string[]
  ativo: boolean
}

export type AuthProps = {
  id: number
  name: string
  roles: string[]
  token: string | undefined
}

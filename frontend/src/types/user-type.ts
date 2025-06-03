import type { RoleProps } from './role-type'

export interface UsuarioSimpleProps {
  id: number
  name: string
  cpf: string
  email: string
  password: string
  roles: RoleProps[]
  ativo: boolean
}

export interface UsuarioAgenteProps extends UsuarioSimpleProps {
  ra: string;
  distintivo: string;
  delegacia: string;
}

export interface UsuarioGestorProps extends UsuarioAgenteProps {
  departamento: string;
  cargo: string;
}

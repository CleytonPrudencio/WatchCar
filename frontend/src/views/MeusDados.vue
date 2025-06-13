<script setup lang="ts">
import * as authService from '@/services/auth-service'
import * as userService from '@/services/userService'
import type { AuthProps, UserAgenteProps, UserSimpleProps } from '@/types/user-type'
import { onMounted, reactive, ref } from 'vue'
import { toast } from 'vue3-toastify'

const userAuth = reactive<AuthProps>(authService.getAccessToken()) // Obtém os dados do usuário autenticado
const formData = reactive<UserAgenteProps>({} as UserAgenteProps)

const showPassword = ref(false)
const isReadonly = ref(true) // Define os campos como readonly inicialmente

// Função para pegar dados do usuário e preencher o formulário
const carregarDados = async () => {
  userService.findById(userAuth.id, formData)
}

// Carregar os dados do usuário quando o componente for montado
carregarDados()
const containsError = (label: string) => {
  return formData[label].error
}

onMounted(() => {
  carregarDados()
})
</script>

<template lang="pug">
  .meus-dados
    h2.titulo Editar Meus Dados

    form(@submit.prevent="salvarDados", v-if="!carregando")
      div.erro(v-if="erro") {{ erro }}

      // Nome, CPF e E-mail são comuns a todos os perfis
      label(for="nome") Nome Completo
      input(type="text" id="nome" name="name" v-model="formData.name" :readonly="isReadonly" required)
      span.error-message(v-if="containsError('name')") {{ formData.error.message }}

      label(for="cpf") CPF
      input(type="text" id="cpf" name="cpf" v-model="formData.cpf" maxlength="14" :readonly="isReadonly" required @input="formatarCPF")
      span.error-message(v-if="containsError('cpf')") {{ formData.error.message }}

      label(for="email") E-mail
      input(type="email" id="email" name="email" v-model="formData.email" :readonly="isReadonly" required)
      span.error-message(v-if="containsError('email')") {{ formData.error.message }}

      // Exibir o perfil atual com destaque
      div.perfil-atual
        p Perfil Atual:
        span.profile-name {{ formData.roles[0].authority || 'Nenhum perfil selecionado' }}


      div.input-group(v-if="formData.departamento")
        label(for="departamento") Departamento
        input(type="text" id="departamento" name="departamento" v-model="formData.departamento" required)
        span.error-message(v-if="containsError('departamento')") {{ formData.error.message }}

      button(type="submit") Salvar

    .carregando(v-else) Carregando...
</template>

<style scoped>
.meus-dados {
  padding: 2rem;
  max-width: 800px;
  margin: 0 auto;
  background-color: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.titulo {
  text-align: center;
  color: #333;
  margin-bottom: 1.5rem;
  font-size: 1.8rem;
  font-weight: 600;
}

.input-group {
  margin-bottom: 1.5rem;
}

label {
  font-weight: 500;
  font-size: 1rem;
  color: #333;
  display: block;
  margin-bottom: 0.5rem;
}

input,
select {
  width: 100%;
  padding: 0.8rem;
  font-size: 1rem;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
}

input[readonly],
select[disabled] {
  background-color: #f1f1f1;
}

.error-message {
  color: red;
  font-size: 0.875rem;
  margin-top: 0.25rem;
}

.password-group {
  position: relative;
}

.password-wrapper {
  position: relative;
}

.password-wrapper input {
  width: 100%;
  padding-right: 2.5rem; /* espaço pro ícone */
  font-size: 1rem;
}

.toggle-icon {
  position: absolute;
  right: 0.8rem;
  top: 50%;
  transform: translateY(-50%);
  cursor: pointer;
  color: #888;
}

button {
  background-color: #42b983;
  color: white;
  cursor: pointer;
  padding: 0.8rem;
  width: 100%;
  font-size: 1.2rem;
  border: none;
  border-radius: 4px;
  transition: background-color 0.3s ease;
}

button:hover {
  background-color: #369f6f;
}

.carregando {
  text-align: center;
  font-size: 1.2rem;
  font-weight: 600;
  color: #888;
}

.descricao-perfil {
  margin-top: 1.5rem;
  padding: 1rem;
  background-color: #e9f7ec;
  border: 1px solid #d4f1d1;
  border-radius: 4px;
}

.descricao-perfil p {
  margin: 0;
  color: #333;
}

.descricao-perfil pstrong {
  font-weight: 600;
}

.perfil-atual {
  margin-bottom: 1.5rem;
  font-size: 1.1rem;
  font-weight: 500;
  margin-top: 5%;
  color: #444;

  /* Adicionando um fundo claro e suave */
  background-color: #f4f4f4;

  /* Centralizando o conteúdo */
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  /* Adicionando uma borda arredondada */
  border-radius: 8px;

  /* Sombra suave para dar mais destaque */
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);

  padding: 20px;
  text-align: center; /* Centralizando o texto */
}

/* Estilizando o texto dentro do perfil atual */
.perfil-atual p {
  margin-bottom: 10px; /* Deixando um espaço entre o texto */
  color: #333;
  font-weight: 400;
}

/* Estilizando o nome do perfil */
.profile-name {
  font-weight: 600;
  color: #42b983; /* Usando a cor de destaque */
  font-size: 1.2rem;
  margin-top: 10px;
}

/* Adicionando um ícone para o perfil (opcional) */
.perfil-atual i {
  margin-right: 8px;
  color: #42b983;
  font-size: 1.3rem;
}

.perfil-atual {
  margin-bottom: 1.5rem;
  font-size: 1.1rem;
  font-weight: 500;
  margin-top: 3%;
  color: #444;
  background-color: #f4f4f4;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  padding: 20px;
  text-align: center;
}

.profile-name {
  font-weight: 600;
  color: #42b983;
  font-size: 1.2rem;
  margin-top: 10px;
}

.descricao-perfil {
  margin-top: 1.5rem;
  padding: 1rem;
  background-color: #e9f7ec;
  border: 1px solid #d4f1d1;
  border-radius: 4px;
}

.descricao-perfil p {
  margin: 0;
  color: #333;
}

.descricao-perfil pstrong {
  font-weight: 600;
}

/* Aumentando a visibilidade do perfil Investigador */
.perfil-investigador {
  margin-top: 1rem;
  padding: 1rem;
  background-color: #d9f1f9;
  border: 1px solid #a8d8e7;
  border-radius: 4px;
}

.perfil-investigador p {
  color: #333;
}
</style>

<template lang="pug">
  section.container
    div.register-container
      h2 Cadastro
      form(@submit.prevent="handleRegister" id="register-user-form")

        div.input-group
          label Tipo de Usuário
          div.radio-buttons
            label(for="publico")
              input(type="radio" id="publico" name="publico" :value="1" v-model="role.idRole")
              | Cidadao
            label(for="policial")
              input(type="radio" id="policial" name="policial" :value="2" v-model="role.idRole")
              | Policial
            label(for="AgenteDeSeguranca")
              input(type="radio" id="AgenteDeSeguranca" :value="3" v-model="role.idRole")
              | Agente de Seguranca
            label(for="investigador")
              input(type="radio" id="investigador" :value="4" v-model="role.idRole")
              | Investigador
            label(for="gestorDeSegurancaPublica")
              input(type="radio" id="gestorDeSegurancaPublica" :value="5" v-model="role.idRole")
              | Gestor de Seguranca Publica

        div.input-group
          label(for="name") Nome Completo
          input(type="text" id="name" name="name" v-model="formData.name" @input="validateInputs" @blur="validations(formData, error)" required)
          span.error-message(v-if="error.name==='name'") {{error.message}}

        div.input-group
          label(for="cpf") CPF
          input(type="text" id="cpf" name="cpf" v-model="formData.cpf" @input="validateInputs" @blur="validations(formData, error)" maxlength="14" required)
          span.error-message(v-if="error.name==='cpf'") {{error.message}}

        div.input-group
          label(for="registerEmail") E-mail
          input(type="email" id="registerEmail" name="email" v-model="formData.email" @input="validateInputs" @blur="validations(formData, error)" required)
          span.error-message(v-if="error.name==='email'") {{error.message}}

        div.input-group(v-if="isPolicialOuSimilar")
          label(for="delegacia") Delegacia
          input(type="text" id="delegacia" name="delegacia" v-model="formData.delegacia" @blur="validations(formData, error)" required)
          span.error-message(v-if="error.name==='delegacia'") {{error.message}}

        div.input-group(v-if="isPolicialOuSimilar")
          label(for="badge") Distintivo
          input(type="text" id="badge" name="distintivo" v-model="formData.distintivo" @blur="validations(formData, error)" required)
          span.error-message(v-if="error.name==='distintivo'") {{error.message}}

        div.input-group(v-if="isPolicialOuSimilar")
          label(for="ra") RA (Registro de Atividade)
          input(type="text" id="ra" name="ra" v-model="formData.ra" @blur="validations(formData, error)" required)
          span.error-message(v-if="error.name==='ra'") {{error.message}}

        div.input-group(v-if="isGestor")
          label(for="departamento") Departamento
          input(type="text" id="departamento" name="departamento" v-model="formData.departamento" @blur="validations(formData, error)" required)
          span.error-message(v-if="error.name==='departamento'") {{error.message}}

        div.input-group(v-if="isGestor")
          label(for="cargo") Cargo
          input(type="text" id="cargo" name="cargo" v-model="formData.cargo" @blur="validations(formData, error)" required)
          span.error-message(v-if="error.name==='cargo'") {{error.message}}

        div.input-group.password-group
          label(for="registerPassword") Senha
          div.password-wrapper
            input(
              :type="showRegisterPassword ? 'text' : 'password'"
              id="registerPassword"
              name="password"
              @input="validateInputs"
              @blur="validations(formData, error)"
              required
            )
            span.toggle-icon(@click="showRegisterPassword = !showRegisterPassword")
              i(:class="showRegisterPassword ? 'fas fa-eye-slash' : 'fas fa-eye'")
            span.error-message(v-if="error.name==='password'") {{error.message}}

        div.input-group.password-group
          label(for="confirmPassword") Confirmar Senha
          div.password-wrapper
            input(
              :type="showConfirmPassword ? 'text' : 'password'"
              id="confirmPassword"
              name="confirmPassword"
              v-model="confirmPassword"
              @input="validations(formData, error)"
              required
            )
            span.toggle-icon(@click="showConfirmPassword = !showConfirmPassword")
              i(:class="showConfirmPassword ? 'fas fa-eye-slash' : 'fas fa-eye'")
            span.error-message(v-if="error.name==='confirmPassword'") {{error.message}}


        button(
          type="submit"
          ) Criar Conta

      RouterLink.center-link(to="/login") Já tem conta? Faça Login
</template>

<script setup lang="ts">
import { toast } from 'vue3-toastify'
import type { RoleProps } from '@/types/role-type'
import type { UsuarioGestorProps } from '@/types/user-type'
import { computed, reactive, ref } from 'vue'
import { formatCPF, validations, validaPassword,replaceNumbers } from '../utils/form'
import { useLoadingStore } from '@/stores/loadingStore'
import api from '../services/api'
import { useRouter } from 'vue-router'
import axios from 'axios'

const store = useLoadingStore()
const router = useRouter()

// Erros
const error = ref({ name: '', message: '' })

const role = reactive<RoleProps>({ idRole: 1 } as RoleProps)
const formData = reactive<UsuarioGestorProps>({ roles: [role] } as UsuarioGestorProps)

// Passowrd visibility
const showRegisterPassword = ref(false)
const confirmPassword = ref('')
const showConfirmPassword = ref(false)
const isPolicialOuSimilar = computed(() => [2, 3, 4].includes(Number(role.idRole)))
const isGestor = computed(() => Number(role.idRole) === 5)

const validateInputs = (event) => {
  var name = event.target.name
  var value = event.target.value
  formData[name] = value

  // Criando a formatação do CPF
  if (name === 'cpf') {
    formData.cpf = formatCPF(value)
  }
}

const handleRegister = async (event) => {
  event.preventDefault()
  store.startLoading() // Inicia o loading
  // Validações
  if (!validations(formData, error.value)) {
    store.stopLoading() // Para o loading quando a ação terminar
    toast.error(error.value.message)
    return
  }
  if (!validaPassword(String(confirmPassword.value), formData.password)) {
    error.value = { name: 'confirmPassword', message: 'As senhas não conferem' }
    store.stopLoading() // Para o loading quando a ação terminar
    toast.error(error.value.message)
    return
  }

  formData.cpf = replaceNumbers(formData.cpf) // Formata o CPF antes de enviar

  await api
    .post('/register', formData)
    .then(() => {
      toast.success('Cadastro realizado com sucesso!')
      store.stopLoading() // Para o loading quando a ação terminar
      router.push('/login')
    })
    .catch((errors) => {
      if (axios.isAxiosError(errors) && errors.response) {
        toast.error(errors.response.data.message || 'Erro ao registrar usuário')
      }else{
        toast.error('Erro ao registrar usuário')
      }
    })
    .finally(() => {
      store.stopLoading() // Para o loading quando a ação terminar
    })
}
</script>

<style scoped>
.container {
  display: flex;
  justify-content: center;
  gap: 2rem;
  padding: 2rem;
  background-color: #f9f9f9;
}

.register-container {
  background-color: white;
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 2rem;
  width: 100%;
  max-width: 100%;
}

h2 {
  text-align: center;
  color: #333;
  margin-bottom: 1.5rem;
}

.input-group {
  margin-bottom: 1rem;
}

.radio-buttons {
  display: flex;
  flex-wrap: wrap;
  gap: 1.5rem;
  margin-top: 10px;
}

.radio-buttons label {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  cursor: pointer;
  font-weight: 500;
}

.radio-buttons input[type='radio'] {
  width: 16px; /* Reduzir para um tamanho mais padrão */
  height: 16px; /* Reduzir para um tamanho mais padrão */
  margin: 0; /* Remover qualquer margem extra */
  border-radius: 50%; /* Manter a forma redonda */
  outline: none;
  cursor: pointer;
  transition: all 0.2s ease;
}

.radio-buttons input[type='radio']:checked {
  background-color: #42b983; /* Cor de fundo quando selecionado */
  border-color: #42b983; /* Cor de borda quando selecionado */
}

.radio-buttons input[type='radio']:checked::after {
  content: ''; /* Cria o círculo interno */
  display: block;
  width: 8px; /* Tamanho do círculo interno */
  height: 8px; /* Tamanho do círculo interno */
  background-color: #42b983; /* Cor do círculo interno */
  border-radius: 50%; /* Forma arredondada */
  margin: 4px; /* Centraliza o círculo interno dentro do botão de rádio */
}

input,
button {
  width: 100%;
  padding: 0.8rem;
  margin-top: 0.5rem;
  font-size: 1rem;
  border: 1px solid #ccc;
  border-radius: 4px;
}

button {
  background-color: #42b983;
  color: white;
  cursor: pointer;
}

button:hover {
  background-color: #369f6f;
}

RouterLink {
  display: block;
  text-align: center;
  color: #42b983;
  margin-top: 5px;
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
}

.toggle-icon {
  position: absolute;
  right: 0.8rem;
  top: 50%;
  transform: translateY(-50%);
  cursor: pointer;
  color: #888;
}

button:disabled {
  background-color: #dcdcdc;
  cursor: not-allowed;
}

.error-message {
  color: red;
  font-size: 0.875rem;
  margin-top: 0.25rem;
}

.input-group {
  margin-bottom: 1rem;
}

button:disabled {
  background-color: #dcdcdc;
  cursor: not-allowed;
}

.center-link {
  display: block;
  text-align: center;
  color: #42b983;
  margin-top: 1rem;
}
</style>

<template lang="pug">
  section.container
    div.login-container
      h2 Login
      RouterLink.center-link(to="/register") Se não tem conta, cadastre-se
      form(@submit.prevent="handleLogin")
        div.input-group
          label(for="cpf") CPF
          input(
            type="text"
            id="cpf"
            name="cpf"
            v-model="formData.cpf"
            @input="validateInputs"
            maxlength="14"
            required
          )
          span.error-message(v-if="error.name==='cpf'") {{error.message}}


        div.input-group.password-group
          label(for="loginPassword") Senha
          div.password-wrapper
            input(
              :type="showPassword ? 'text' : 'password'"
              id="loginPassword"
              name="password"
              v-model="formData.password"
              @input="validateInputs"
              @blur="validations(formData, error)"
              required
            )
            span.toggle-icon(@click="showPassword = !showPassword")
              i(:class="showPassword ? 'fas fa-eye-slash' : 'fas fa-eye'")
            span.error-message(v-if="error.name === 'password'") {{ error.message }}

        button(type="submit") Entrar
      span.forgot-password(@click="openForgotPasswordModal") Esqueci a senha

    ForgotPasswordModal(ref="forgotPasswordModal")
</template>

<script setup lang="ts">
import * as authService from '@/services/auth-service'
import { useLoadingStore } from '@/stores/loadingStore'
import ForgotPasswordModal from '@/views/components/ForgotPasswordModal.vue'
import axios from 'axios'
import { onMounted, reactive, ref } from 'vue'
import { toast } from 'vue3-toastify'
import { formatCPF, replaceNumbers, validations } from '../utils/form'
import router from '@/router'

const store = useLoadingStore()

interface LoginProps {
  cpf: string
  password: string
}

const formData = reactive<LoginProps>({} as LoginProps)
const error = ref({ name: '', message: '' })
const showPassword = ref(false)
const forgotPasswordModal = ref<InstanceType<typeof ForgotPasswordModal> | null>(null)

onMounted(() => {
  const msg = localStorage.getItem('loginMessage')
  if (msg) {
    toast.error(msg)
    localStorage.removeItem('loginMessage')
  }
})

const validateInputs = (event) => {
  var name = event.target.name.trim()
  var value = event.target.value.trim()
  formData[name] = value

  // Criando a formatação do CPF
  if (name === 'cpf') {
    formData.cpf = formatCPF(value)
  }
  error.value = { name: '', message: '' } // Reseta o erro ao validar os inputs
  if ((name === 'cpf' && value.length == 14) || (name === 'password' && value.length > 5)) {
    validations(formData, error.value)
  }
}

const openForgotPasswordModal = () => {
  if (forgotPasswordModal.value) {
    forgotPasswordModal.value.openModal() // Acessando o método openModal do componente filho
  }
}

// Função de login
const handleLogin = async (event) => {
  event.preventDefault()
  store.startLoading() // Inicia o loading
  // Validações
  if (!validations(formData, error.value)) {
    store.stopLoading() // Para o loading quando a ação terminar
    toast.error(error.value.message)
    return
  }

  await authService
    .loginRequest({ username: replaceNumbers(formData.cpf), password: formData.password })
    .then((response) => {
      authService.saveAccessToken(response.data.access_token)
      toast.success('Login realizado com sucesso!')
      window.dispatchEvent(new Event('storage')) // Dispara o evento de storage para atualizar o estado global
      router.push('/ocorrencias') // Redireciona para o dashboard após o login
      window.location.reload() // Recarrega a página para garantir que o estado seja atualizado
    })
    .catch((error) => {
      if (axios.isAxiosError(error) && error.response) {
        toast.error(error.response.data.error || 'Erro ao fazer o login')
      } else {
        toast.error('Erro ao fazer o login ', error.message || 'Erro desconhecido')
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

.login-container {
  background-color: white;
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 2rem;
  width: 100%;
  max-width: 500px;
}

h2 {
  text-align: center;
  color: #333;
  margin-bottom: 1.5rem;
}

.input-group {
  margin-bottom: 1rem;
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
  align-items: center;
  color: #42b983;
  margin-top: 1rem;
}

.center-link {
  display: block;
  text-align: center;
  color: #42b983;
  margin-top: 1rem;
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
  font-size: 0.875rem; /* Tamanho da fonte para a mensagem de erro */
  margin-top: 0.25rem;
}

.forgot-password {
  display: block;
  text-align: right;
  margin-top: 2rem;
  margin-bottom: 1rem;
  color: #42b983;
  font-size: 0.9rem;
  cursor: pointer;
  text-decoration: underline;
}

.forgot-password:hover {
  color: #369f6f;
}
</style>

// src/services/api.ts
import axios from 'axios'
import { toast } from 'vue3-toastify'
import router from '@/router'

const api = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL, // base da sua API
  headers: {
    'Content-Type': 'application/json',
  },
})

// Interceptor de resposta
api.interceptors.response.use(
  (response) => response,
  (error) => {
    console.log('erro 1', error)
    console.log('error.response.status ', error.response.status)
    console.log('error.response ', error.response)

    if (error.response && error.response.status === 401) {
      // Mostra o toast e redireciona após um breve delay
      toast.error('Sessão expirada. Faça login novamente.')

      // Limpa storage e redireciona após 1 segundo
      setTimeout(() => {
        localStorage.clear()
        router.push('/login')
      }, 1000)
    }

    return Promise.reject(error)
  },
)
export default api

import { getKeys } from '@/localstorage/access-token-repository'
import { TOKEN_KEY } from '@/utils/system'
import PasswordResetModal from '@/views/components/PasswordResetModal.vue'
import Grafico from '@/views/Grafico.vue'
import Ocorrencias from '@/views/Ocorrencias.vue'
import { createRouter, createWebHistory } from 'vue-router'
import Cadastro from '../views/Cadastro.vue'
import Denuncia from '../views/Denuncia.vue'
import Inicio from '../views/Inicio.vue'
import Login from '../views/Login.vue'
import MeusDados from '../views/MeusDados.vue'
import Sobre from '../views/Sobre.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'inicio',
      component: Inicio,
    },
    {
      path: '/login',
      name: 'login',
      component: Login,
    },
    {
      path: '/register',
      name: 'cadastro',
      component: Cadastro,
    },
    {
      path: '/sobre',
      name: 'sobre',
      component: Sobre,
    },
    {
      path: '/ocorrencias',
      name: 'Ocorrência',
      component: Ocorrencias,
    },
    {
      path: '/denuncia',
      name: 'denuncia',
      component: Denuncia,
    },
    {
      path: '/redefinir-senha',
      name: 'PasswordReset',
      component: PasswordResetModal,
    },
    {
      path: '/meus-dados',
      name: 'meus-dados',
      component: MeusDados,
      meta: { requiresAuth: true },
    },
    {
      path: '/grafico',
      name: 'grafico',
      component: Grafico,
      meta: { requiresAuth: true },
    },
  ],
})
router.beforeEach((to, from, next) => {
  const requiresAuth = to.meta.requiresAuth
  const token = localStorage.getItem(TOKEN_KEY)
  if (requiresAuth && !token) {
    return next({ name: 'login' })
  }
  next()
})

router.beforeEach(async (to, from, next) => {
  const token = localStorage.getItem(TOKEN_KEY)
  if (token) {
    try {
      const userData = getKeys() // Obtém os dados do usuário do localStorage
      //const userData = await fetchUserData() // Atualiza userName, userPerfil, userId
      localStorage.setItem('userName', userData.name)
      localStorage.setItem('userPerfil', userData.roles[0])
      localStorage.setItem('userId', '1')
      window.dispatchEvent(new Event('storage'))
    } catch (err) {
      console.error('Erro ao buscar dados do usuário', err)
      // Token inválido? Limpa e redireciona:
      localStorage.clear()
      return next('/login')
    }
  }

  next()
})

export default router

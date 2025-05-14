import { fileURLToPath, URL } from 'node:url'
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueJsx from '@vitejs/plugin-vue-jsx'
import vueDevTools from 'vite-plugin-vue-devtools'

// https://vite.dev/config/
export default defineConfig({
  plugins: [vue(), vueJsx(), vueDevTools()],
  build: {
    rollupOptions: {
      external: ['vue-chartjs', 'chart.js'], // Adicionando chart.js como externa
    },
  },
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url)),
      'chart.js': 'chart.js/dist/chart.min.js', // Certificando que o chart.js seja resolvido corretamente
    },
  },
  preview: {
    host: '0.0.0.0', // Permite acessar de qualquer IP
    allowedHosts: ['watchcar.onrender.com', 'localhost', 'watchcar.com.br', 'www.watchcar.com.br'], // Permite esse domínio específico
  },
})

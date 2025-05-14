<template lang="pug">
  .fullscreen-overlay(v-if="isVisible" @click.self="closeModal")
    .fullscreen-container
      button.close-button(@click="closeModal") X
      .content
        h2 {{ title }}
        .grafico(:class="fullscreenClass")
          Bar(v-if="chartType === 'artigos'" :data="dados" :options="options")
          Pie(v-if="chartType === 'status'" :data="dados" :options="options")
          Line(v-if="chartType === 'horarios'" :data="dados" :options="options")
          Pie(v-if="chartType === 'usuario'" :data="dados" :options="options")
</template>

<script setup lang="ts">
import { defineProps } from 'vue'
import { Bar, Pie, Line } from 'vue-chartjs'
import type { ChartData } from 'chart.js'

interface ModalProps {
  isVisible: boolean
  chartType: string
  title: string
  dados: ChartData
  options: any
  closeModal: () => void
}

defineProps<ModalProps>()
</script>
<style scoped>
/* Overlay para o fundo escuro */
.fullscreen-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.7); /* Fundo escuro com leve transparência */
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 999;
}

/* Container do modal */
.fullscreen-container {
  background-color: white;
  padding: 2rem;
  border-radius: 8px;
  position: relative;
  width: 80%; /* Largura do modal */
  height: 80vh; /* Altura do modal */
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
}

/* Conteúdo do modal, título e gráfico */
.content {
  display: flex;
  flex-direction: column;
  gap: 1rem; /* Espaço entre título e gráfico */
  height: 100%;
  align-items: center; /* Centraliza o conteúdo */
  justify-content: center; /* Centraliza verticalmente */
}

/* Estilo do título */
h2 {
  font-size: 1.8rem;
  text-align: center;
  margin-bottom: 1rem;
}

/* Estilo do botão de fechar */
.close-button {
  position: absolute;
  top: 10px;
  right: 10px;
  font-size: 2rem;
  cursor: pointer;
  color: #aaa; /* Cor cinza para o "X" */
  background: none;
  border: none;
  transition: color 0.3s;
}

.close-button:hover {
  color: #555; /* Cor mais escura no hover */
}

/* Estilo do gráfico, ocupando todo o espaço */
.grafico {
  width: 100%;
  height: 100%; /* O gráfico ocupa toda a altura disponível */
  display: flex;
  justify-content: center;
  align-items: center; /* Centraliza o gráfico */
}
</style>

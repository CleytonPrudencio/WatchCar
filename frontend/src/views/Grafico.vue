<template lang="pug">
  .graficos-container
    h2.titulo-grafico 📊 Estatísticas de Ocorrências
    .export-container
      button.btn-exportar(@click="gerarPDF") 📄 Exportar como PDF
      RelatorioPDF(ref="relatorioPDF")

    .graficos-row
      .grafico(:class="{ 'fullscreen': isFullscreen && fullscreenChart.value === 'artigos' }")
        Bar(:data="dadosArtigos" :options="opcoesBarra" v-bind:ref="chartRefs.artigos")
        button(@click="openModal('artigos', 'Artigos Mais Usados', dadosArtigos, opcoesBarra)") 🔍

      .grafico(:class="{ 'fullscreen': isFullscreen && fullscreenChart.value === 'status' }")
        Pie(:data="dadosStatus" :options="opcoesPizza" v-bind:ref="chartRefs.status")
        button(@click="openModal('status', 'Status das Ocorrências', dadosStatus, opcoesPizza)") 🔍

      .grafico(:class="{ 'fullscreen': isFullscreen && fullscreenChart.value === 'horarios' }")
        Line(:data="dadosHorario" :options="opcoesLinha" v-bind:ref="chartRefs.horarios")
        button(@click="openModal('horarios', 'Ocorrências por Hora', dadosHorario, opcoesLinha)") 🔍

      .grafico(:class="{ 'fullscreen': isFullscreen && fullscreenChart.value === 'usuario' }")
        Pie(:data="dadosUsuario" :options="opcoesUsuario" v-bind:ref="chartRefs.usuarios")
        button(@click="openModal('usuario', 'Usuários Anônimos e Outros', dadosUsuario, opcoesUsuario)") 🔍

    Modal(
      :isVisible="isModalVisible"
      :chartType="modalChartType"
      :title="modalTitle"
      :dados="modalDados"
      :options="modalOptions"
      :closeModal="closeModal"
    )
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { Bar, Pie, Line } from 'vue-chartjs'
import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  BarElement,
  ArcElement,
  LineElement,
  CategoryScale,
  LinearScale,
  PointElement,
  type ChartData,
} from 'chart.js'
import { obterTodasOcorrencias } from '@/services/ocorrenciasService'
import Modal from '@/views/components/Modal.vue'
import RelatorioPDF from '@/views/components/RelatorioPDF.vue'

const relatorioPDF = ref<InstanceType<typeof RelatorioPDF> | null>(null)

ChartJS.register(
  Title,
  Tooltip,
  Legend,
  BarElement,
  ArcElement,
  LineElement,
  CategoryScale,
  LinearScale,
  PointElement,
)
const chartRefs = {
  artigos: ref<any>(null),
  status: ref<any>(null),
  horarios: ref<any>(null),
  usuarios: ref<any>(null),
}

const todasOcorrencias = ref<Ocorrencia[]>([])

const dadosArtigos = ref<ChartData<'bar'>>({
  labels: [],
  datasets: [],
})
const dadosStatus = ref<ChartData<'pie'>>({
  labels: [],
  datasets: [],
})
const dadosHorario = ref<ChartData<'line'>>({
  labels: [],
  datasets: [],
})
const dadosUsuario = ref<ChartData<'pie'>>({
  labels: [],
  datasets: [],
})

// As opções dos gráficos
const opcoesBarra = {
  responsive: true,
  plugins: {
    legend: { display: false },
    title: { display: true, text: 'Artigos Mais Usados', font: { size: 20, weight: 'bold' } },
  },
}
const opcoesPizza = {
  responsive: true,
  plugins: {
    legend: { position: 'top' },
    title: { display: true, text: 'Status das Ocorrências', font: { size: 20, weight: 'bold' } },
  },
}
const opcoesLinha = {
  responsive: true,
  plugins: {
    legend: { display: false },
    title: { display: true, text: 'Ocorrências por Hora', font: { size: 20, weight: 'bold' } },
  },
}
const opcoesUsuario = {
  responsive: true,
  plugins: {
    legend: { position: 'top' },
    title: {
      display: true,
      text: 'Usuários Anônimos e Outros',
      font: { size: 20, weight: 'bold' },
    },
  },
}

interface Ocorrencia {
  artigoDescricao: string
  statusDenuncia: string
  horaOcorrencia: string
  usuarioNome: string
}

const isModalVisible = ref(false)
const modalChartType = ref('')
const modalTitle = ref('')
const modalDados = ref<ChartData | null>(null)
const modalOptions = ref<any>(null)

onMounted(async () => {
  const resultado = await obterTodasOcorrencias()
  todasOcorrencias.value = resultado
  montarGraficos()
})

function montarGraficos() {
  const artigos: Record<string, number> = {}
  const status: Record<string, number> = {}
  const horarios: Record<string, number> = {}
  const usuarios: Record<string, number> = {}

  todasOcorrencias.value.forEach((o) => {
    if (o.artigoDescricao) {
      artigos[o.artigoDescricao] = (artigos[o.artigoDescricao] || 0) + 1
    }
    if (o.statusDenuncia) {
      status[o.statusDenuncia] = (status[o.statusDenuncia] || 0) + 1
    }
    if (o.horaOcorrencia) {
      const hora = o.horaOcorrencia.slice(0, 2)
      horarios[hora] = (horarios[hora] || 0) + 1
    }
    if (o.usuarioNome) {
      usuarios[o.usuarioNome === 'Anônimo' ? 'Anônimo' : 'Outros'] =
        (usuarios[o.usuarioNome === 'Anônimo' ? 'Anônimo' : 'Outros'] || 0) + 1
    }
  })

  // Preenchendo os dados dos gráficos
  dadosArtigos.value = {
    labels: Object.keys(artigos),
    datasets: [{ label: 'Quantidade', data: Object.values(artigos), backgroundColor: '#4caf50' }],
  }
  dadosStatus.value = {
    labels: Object.keys(status),
    datasets: [
      {
        label: 'Ocorrências',
        data: Object.values(status),
        backgroundColor: ['#ffc107', '#28a745', '#6c757d'],
      },
    ],
  }
  dadosHorario.value = {
    labels: Object.keys(horarios).sort((a, b) => Number(a) - Number(b)),
    datasets: [
      {
        label: 'Ocorrências por Hora',
        data: Object.values(horarios),
        borderColor: '#007bff',
        fill: false,
      },
    ],
  }
  dadosUsuario.value = {
    labels: Object.keys(usuarios),
    datasets: [
      { label: 'Usuários', data: Object.values(usuarios), backgroundColor: ['#007bff', '#28a745'] },
    ],
  }
}

// Função para gerar o base64 da imagem do gráfico
function getChartBase64(chartKey: keyof typeof chartRefs): string {
  const chartComponent = chartRefs[chartKey].value

  if (chartComponent && chartComponent.chart) {
    return chartComponent.chart.toBase64Image()
  }
  console.warn(`Chart.js instance não encontrada para o gráfico "${chartKey}"`)
  return ''
}

function openModal(chartType: string, title: string, dados: ChartData, options: any) {
  modalChartType.value = chartType
  modalTitle.value = title
  modalDados.value = dados
  modalOptions.value = options
  isModalVisible.value = true
}

function closeModal() {
  isModalVisible.value = false
}

function gerarPDF() {
  if (relatorioPDF.value) {
    const chartArtigosBase64 = getChartBase64('artigos')
    const chartStatusBase64 = getChartBase64('status')
    const chartHorariosBase64 = getChartBase64('horarios')
    const chartUsuariosBase64 = getChartBase64('usuarios')

    relatorioPDF.value.gerarPDFPersonalizado({
      artigos: { labels: dadosArtigos.value.labels, data: dadosArtigos.value.datasets[0].data },
      status: { labels: dadosStatus.value.labels, data: dadosStatus.value.datasets[0].data },
      horarios: { labels: dadosHorario.value.labels, data: dadosHorario.value.datasets[0].data },
      usuarios: { labels: dadosUsuario.value.labels, data: dadosUsuario.value.datasets[0].data },
      charts: {
        artigos: chartArtigosBase64,
        status: chartStatusBase64,
        horarios: chartHorariosBase64,
        usuarios: chartUsuariosBase64,
      },
    })
  }
}
</script>

<style scoped>
.graficos-container {
  padding: 2rem;
  display: flex;
  flex-direction: column;
  gap: 2rem;
}

.graficos-row {
  display: flex;
  gap: 2rem;
  justify-content: space-between;
  flex-wrap: wrap;
}

.grafico {
  background: #fff;
  padding: 1rem;
  border-radius: 8px;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
  width: 45%; /* Ajuste o tamanho padrão */
  height: 350px;
  position: relative;
  transition: all 0.3s ease;
  display: flex;
  justify-content: center;
  align-items: center;
}

button {
  top: 10px;
  right: 10px;
  background: none;
  border: none;
  font-size: 1.5rem;
  cursor: pointer;
  color: #007bff;
}

button:hover {
  color: #0056b3;
}

.titulo-grafico {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 0.5rem;
  font-size: 2rem;
  font-weight: bold; /* Deixa o texto em negrito */
  margin-bottom: 1rem;
}

.grafico {
  background: #fff;
  padding: 1rem;
  border-radius: 8px;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
  width: 45%; /* Ajuste o tamanho padrão */
  height: 350px;
  position: relative; /* Torna o gráfico o contêiner de posicionamento */
  display: flex;
  justify-content: center; /* Centraliza horizontalmente */
  align-items: center; /* Centraliza verticalmente */
  transition: all 0.3s ease;
}

button {
  background: none;
  border: none;
  font-size: 1.5rem;
  cursor: pointer;
  color: #007bff;
}

/* Estilo específico para o botão de lupa dentro do gráfico */
.grafico button:first-of-type {
  position: absolute;
  top: 10px;
  right: 10px;
}

.export-container {
  display: flex;
  justify-content: center; /* Centraliza o botão horizontalmente */
  align-items: center; /* Centraliza o botão verticalmente, se necessário */
  margin-top: 1rem;
}

.export-container button {
  background-color: #a5d6a7; /* Verde suave */
  border: none;
  padding: 6px 12px;
  font-size: 0.9rem;
  border-radius: 4px;
  color: #2e7d32;
  cursor: pointer;
  font-weight: bold;
  transition: background-color 0.3s ease;
}

.export-container button:hover {
  background-color: #81c784;
}

.btn-exportar {
  background-color: #a5d6a7; /* verde suave */
  border: none;
  padding: 6px 12px;
  font-size: 0.9rem;
  border-radius: 4px;
  color: #2e7d32;
  cursor: pointer;
  font-weight: bold; /* Texto em negrito no botão */
  transition: background-color 0.3s ease;
}

.btn-exportar:hover {
  background-color: #81c784;
}
</style>

<template lang="pug">
  // Deixe vazio mesmo
</template>
<script setup lang="ts">
import { Chart } from 'chart.js' // Apenas importe Chart
import pdfMake from 'pdfmake/build/pdfmake'
import pdfFonts from 'pdfmake/build/vfs_fonts'
import logoBase64 from '@/types/logo-base64.ts'

pdfMake.vfs = pdfFonts.vfs

interface ChartData {
  labels: string[]
  data: number[]
}

interface DadosRelatorio {
  artigos: ChartData
  status: ChartData
  horarios: ChartData
  usuarios: ChartData
  charts: {
    artigos: Chart
    status: Chart
    horarios: Chart
    usuarios: Chart
  }
}
function gerarImagemGrafico(chart: Chart): string | null {
  try {
    const base64 = chart?.toBase64Image()
    if (base64 && base64.startsWith('data:image')) {
      return base64
    }
    console.error('Falha ao gerar imagem base64 do gráfico.')
    return null
  } catch (e) {
    console.error('Erro ao converter gráfico para base64:', e)
    return null
  }
}

defineExpose({
  gerarPDFPersonalizado,
})
function gerarPDFPersonalizado(dados: DadosRelatorio) {
  const docDefinition = {
    content: [
      {
        text: 'Atenção: Este sistema é um projeto de testes e não representa uma plataforma oficial de denúncias. As informações aqui inseridas são fictícias e utilizadas exclusivamente para fins acadêmicos e demonstrativos.',
        style: 'alerta',
        alignment: 'justify',
        margin: [0, 0, 0, 15],
      },
      { image: logoBase64, width: 120, alignment: 'center', margin: [0, 0, 0, 10] },
      { text: 'Relatório de Ocorrências', style: 'header' },

      // Artigos
      { text: 'Artigos Mais Usados', style: 'subheader' },
      {
        text: 'Esta seção mostra quais artigos da lei são mais recorrentes nas denúncias.',
        style: 'info',
      },
      {
        image: dados.charts.artigos,
        width: 300,
        height: 180,
        alignment: 'center',
        margin: [0, 10, 0, 10],
      },
      gerarTabela(dados.artigos),

      // Status
      { text: 'Status das Ocorrências', style: 'subheader' },
      {
        text: 'Distribuição dos status registrados (pendente, em andamento, resolvido, etc).',
        style: 'info',
      },
      {
        image: dados.charts.status,
        width: 300,
        height: 180,
        alignment: 'center',
        margin: [0, 10, 0, 10],
      },
      gerarTabela(dados.status),

      // Horários
      { text: 'Ocorrências por Hora', style: 'subheader' },
      {
        text: 'Mostra os horários mais comuns em que as ocorrências são registradas.',
        style: 'info',
      },
      {
        image: dados.charts.horarios,
        width: 300,
        height: 180,
        alignment: 'center',
        margin: [0, 10, 0, 10],
      },
      gerarTabela(dados.horarios),

      // Usuários
      { text: 'Usuários Anônimos e Outros', style: 'subheader' },
      {
        text: 'Análise entre denúncias feitas por usuários anônimos versus identificados.',
        style: 'info',
      },
      {
        image: dados.charts.usuarios,
        width: 300,
        height: 180,
        alignment: 'center',
        margin: [0, 10, 0, 10],
      },
      gerarTabela(dados.usuarios),
    ],
    styles: {
      header: {
        fontSize: 18,
        bold: true,
        alignment: 'center',
        margin: [0, 0, 0, 20],
      },
      alerta: {
        fontSize: 9,
        color: 'red',
        bold: true,
        alignment: 'center', // Centraliza a mensagem de alerta
      },
      subheader: {
        fontSize: 14,
        bold: true,
        margin: [0, 10, 0, 5],
        color: 'black', // Subtítulo em preto
        alignment: 'center', // Centraliza o título da seção
      },
      info: {
        fontSize: 11,
        italics: true,
        color: 'black', // Descrição em preto
        margin: [0, 0, 0, 10],
        alignment: 'center', // Centraliza a descrição
      },
      tableHeader: {
        bold: true,
        fillColor: '#f3f3f3', // Cabeçalho com fundo claro
        fontSize: 13,
        alignment: 'center', // Centraliza o texto do cabeçalho
        padding: [5, 10], // Maior espaçamento nas células
        border: [true, true, true, true], // Borda fechada
      },
      tableBody: {
        fontSize: 10,
        alignment: 'center', // Centraliza o conteúdo das células da tabela
        padding: [5, 10],
        border: [true, true, true, true], // Borda fechada
      },
      table: {
        fontSize: 10,
        alignment: 'center', // Centraliza o conteúdo da tabela
        border: [true, true, true, true], // Borda fechada
      },
    },
  }

  // Nome do arquivo com data e hora
  const nomeArquivo = `relatorio_ocorrencias_${formatarDataHora()}.pdf`

  pdfMake.createPdf(docDefinition).download(nomeArquivo)
}
// Função para formatar a data e hora
function formatarDataHora() {
  const data = new Date()
  const dia = String(data.getDate()).padStart(2, '0')
  const mes = String(data.getMonth() + 1).padStart(2, '0') // Janeiro é 0
  const ano = data.getFullYear()
  const hora = String(data.getHours()).padStart(2, '0')
  const minuto = String(data.getMinutes()).padStart(2, '0')

  return `${dia}${mes}${ano}_${hora}${minuto}`
}

function gerarTabela(chartData: ChartData) {
  const headers = [
    { text: 'Categoria', style: 'tableHeader' },
    { text: 'Quantidade', style: 'tableHeader' },
  ]
  const body = [headers, ...chartData.labels.map((label, i) => [label, chartData.data[i]])]

  return {
    table: {
      widths: [250, '*'], // Definindo o tamanho das colunas
      body: body,
    },
    layout: {
      hLineWidth: function (i: number, node: any) {
        return i === 0 ? 2 : 1 // Linha mais espessa no topo
      },
      vLineWidth: function (i: number, node: any) {
        return 1 // Linha de separação vertical
      },
      hLineColor: function (i: number, node: any) {
        return i === 0 ? '#000000' : '#CCCCCC' // Linha mais espessa e preta no topo
      },
      vLineColor: function (i: number, node: any) {
        return '#CCCCCC' // Cor das linhas verticais
      },
      paddingLeft: function (i: number, node: any) {
        return i === 0 ? 10 : 5 // Mais espaço para a primeira coluna
      },
      paddingRight: function (i: number, node: any) {
        return i === 1 ? 10 : 5 // Mais espaço para a segunda coluna
      },
    },
    margin: [0, 0, 0, 20],
    style: 'table', // Aplica o estilo de tabela
  }
}
</script>

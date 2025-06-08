<script setup lang="ts">
import * as artigoService from '@/services/artigoService'
import * as authService from '@/services/auth-service'
import * as denunciaService from '@/services/denunciaService'
import { enviarDenuncia as enviarDenunciaService } from '@/services/ocorrenciasService'
import * as userService from '@/services/userService'
import { useLoadingStore } from '@/stores/loadingStore'
import type { ArtigoProps } from '@/types/artigo-type'
import type { DenunciaProps, EtapaProps } from '@/types/denuncia-type'
import type { AuthProps, UserSimpleProps } from '@/types/user-type'
import { buscarEndereco, formatCEP, formatCPF, validations } from '@/utils/forms'
import { computed, onMounted, reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { toast } from 'vue3-toastify'

const store = useLoadingStore()
const router = useRouter()

const userAuth = reactive<AuthProps>(authService.getAccessToken()) // Obtém os dados do usuário autenticado
const usuarioForm = reactive<UserSimpleProps>({} as UserSimpleProps)
const etapas = reactive<EtapaProps[]>([
  { valor: 1, avancar: false },
  { valor: 2, avancar: false },
  { valor: 3, avancar: false },
  { valor: 4, avancar: false },
]) // Etapas do formulário
const denunciaForm = reactive<DenunciaProps>({
  denunciante: usuarioForm,
  localDaOcorrencia: { cep: '' },
} as DenunciaProps)

const artigos = reactive<ArtigoProps[]>([])

// Definindo os dados do formulário
const placa = ref('')
const ano = ref<number | null>(null) // ✅ esta é a correta
const marca = ref('')
const modelo = ref('')
const cor = ref('')
const horaOcorrencia = ref('')
const descricao = ref('')
const anonimo = ref(false)
const artigoSelecionadoId = ref(null)
const receberAlertas = ref(true) // valor padrão: sim
const anoAtual = new Date().getFullYear()
const anosDisponiveis = ref<number[]>([])

// Endereço
const errorEndereco = ref({ name: '', message: '' }) // Objeto para armazenar erros de validação de endereço

/*************************************************************
 *
 *                        Usuário
 *
 *************************************************************/
const errorUser = ref({ name: '', message: '' }) // Objeto para armazenar erros de validação
const usuarioLogado = ref(false) // Controla se o usuário está logado

// Busca os dados do usuário autenticado
const buscarUsuario = async () => {
  try {
    await userService.findById(userAuth.id, usuarioForm)
    denunciaForm.denunciante = usuarioForm // Atualiza o objeto denunciaForm com os dados do usuário
  } catch (error) {
    console.error('Erro ao buscar usuário:', error)
    toast.error('Erro ao buscar usuário. Verifique os dados e tente novamente.')
  }
}

const validateInputs = async (event: Event) => {
  const input = event.target as HTMLInputElement
  const name = input.name
  const value = input.value

  usuarioForm[name] = value // Atualiza o campo correspondente no objeto usuarioForm

  // Se for o nome, remove espaços extras
  if (name === 'cpf') {
    usuarioForm.cpf = formatCPF(value)
  }
  // Se for o CEP formata
  if (name === 'cep') {
    denunciaForm.localDaOcorrencia.cep = formatCEP(value)
    await buscarEndereco(denunciaForm.localDaOcorrencia) // Busca o endereço ao digitar o CEP
  }
  errorUser.value = { name: '', message: '' } // Reseta o erro ao validar os inputs
  validations(usuarioForm, errorUser.value)
  denunciaForm.denunciante = usuarioForm // Atualiza o objeto denunciaForm com os dados do usuário
  denunciaService.getEtapa(denunciaForm, etapas) // Atualiza a etapa com os dados do formulário

  // Verifica se a etapa atual pode avançar
  if(etapas[timeLine.value].avancar) {
      setTimeout(() => {
        const btn = document.querySelector('.btn-avancar') as HTMLButtonElement
        if (btn) btn.focus()
      }, 0)
    }
}

/************************************************************
 *
 *                Controle da etapa atual
 *
 ***********************************************************/
const timeLine = ref(0) // Referência para a linha do tempo
// Função que altera a etapa atual
const proximaEtapa = () => {
  if (timeLine.value < 5) {
    // Agora são 5 etapas, de 1 a 5
    timeLine.value++
  } else {
    enviarDenuncia()
  }
  denunciaService.getEtapa(denunciaForm, etapas) // Atualiza a etapa com os dados do formulário
}

// Função que volta à etapa anterior
const voltar = () => {
  if (timeLine.value > 0) {
    timeLine.value--
  }
  console.log('Linha de tempo:', timeLine.value)
  denunciaService.getEtapa(denunciaForm, etapas) // Atualiza a etapa com os dados do formulário
}

const progresso = computed(() => {
  // Total de 5 etapas: 0%, 25%, 50%, 75%, 100%
  return (etapas.filter((e) => e.avancar).length / 4) * 100 // Ajusta a porcentagem de acordo com a etapa
})

const carregarArtigos = async () => {
  try {
    const artigosData = await artigoService.buscarArtigos()
    artigosData.forEach((artigo) => {
      artigos.push({
        idArtigo: artigo.idArtigo,
        codArtigo: artigo.codArtigo,
        descricaoArtigo: artigo.descricaoArtigo,
        rubrica: artigo.rubrica,
      })
    })
  } catch (error) {
    toast.error('Erro ao carregar os artigos. Verifique a conexão e tente novamente.')
  }
}

/************************************************************
 *
 *                Denúncia
 *
 ***********************************************************/

// Função para enviar a denúncia
const enviarDenuncia = async () => {
  try {
    let idUsuario = null

    // Se o usuário não for anônimo e estiver logado, usa o id do usuário
    if (!anonimo.value && usuarioLogado.value) {
      idUsuario = usuarioForm.id
    } else if (anonimo.value) {
      // Se for anônimo, o id será 1
      idUsuario = 1
    }

    const denuncia = {
      idUsuario: usuarioForm.id || 1, // Se o usuário estiver logado, pega o id, caso contrário, usa null
      username: anonimo.value ? 'Anônimo' : usuarioForm.name,
    }
    store.startLoading() // Inicia o loading
    await enviarDenunciaService(denuncia)
    store.stopLoading() // Para o loading quando a ação terminar
    toast.success('Denúncia registrada com sucesso!')
    router.push({ name: 'inicio' })
  } catch (error) {
    store.stopLoading() // Para o loading quando a ação terminar
    toast.error('Erro ao registrar denúncia. Verifique os dados e tente novamente.')
  }
}

// Alterna a flag de anonimato
const toggleAnonimo = () => {
  if (anonimo.value) {
    receberAlertas.value = false
    etapas[0].valor = 2
  } else {
    etapas[0].valor = 1
  }
}

// Buscar dados do usuário assim que o componente for montado
onMounted(() => {
  buscarUsuario()
  carregarArtigos()
  //carregarTiposOcorrencia()
  denunciaService.getEtapa(denunciaForm, etapas)
  for (let ano = anoAtual; ano >= anoAtual - 10; ano--) {
    anosDisponiveis.value.push(ano)
  }
})

const mostrarAsteriscos = computed(() => {
  return String(usuarioForm.name).trim() !== ''
})
</script>

<template lang="pug">
.template
  .denuncia
    h1 Registrar Denúncia de Roubo ou Furto de Veículo timeLine
    | TimeLine: {{timeLine}}
    | Progresso: {{etapas[timeLine].valor}} - {{etapas[timeLine].avancar ? 'Avançar' : 'Não Avançar'}}

    // Linha do tempo
    .timeline
      .progress-line(:class="{ completed: progresso === 100 }")
        .fill(:style="{ width: progresso + '%' }")
      .step(:class="{ active: timeLine === 0, completed: etapas[0].avancar }")
        i.fas.fa-user
        span Dados Pessoais
      .step(:class="{ active: timeLine === 1, completed: etapas[1].avancar }")
        i.fas.fa-map-marker-alt
        span Local
      .step(:class="{ active: timeLine === 2, completed: etapas[2].avancar }")
        i.fas.fa-car
        span Veículo
      .step(:class="{ active: timeLine === 3, completed: etapas[3].avancar }")
        i.fas.fa-comment
        span Descrição
      .step(:class="{ active: etapas[timeLine].valor === 5 }")
        i.fas.fa-check-circle
        span Finalizar


    .line
    form(@submit.prevent="enviarDenuncia")
      template(v-if="timeLine === 0")
        .step-content(:class="{'active-step': timeLine === 0}")
          .input-anonimo
            label(for="anonimo") Denunciar de forma anônima
            .anonimo-checkbox
              input(type="checkbox" id="anonimo" v-model="anonimo" @change="toggleAnonimo")

          .input-group(v-if="!anonimo")
            label(for="username")
            | Nome
            span.text-danger(v-if="mostrarAsteriscos") *
            input(type="text" id="username" name="name" v-model="usuarioForm.name" @input="validateInputs" @blur="validations(usuarioForm, errorUser)" :disabled="anonimo || (usuarioLogado && !anonimo)" :readonly="usuarioLogado")
            span.error-message(v-if="errorUser.name==='name'") {{errorUser.message}}

          .input-group(v-if="!anonimo")
            label(for="cpf")
            | CPF
            span.text-danger(v-if="mostrarAsteriscos") *
            input(type="text" id="cpf" name="cpf" v-model="usuarioForm.cpf" @input="validateInputs" @blur="validations(usuarioForm, errorUser)" maxlength="14" :disabled="anonimo || (usuarioLogado && !anonimo)" :readonly="usuarioLogado")
            span.error-message(v-if="errorUser.name==='cpf'") {{errorUser.message}}

          .input-group(v-if="!anonimo")
            label(for="email")
            | E-mail
            span.text-danger(v-if="mostrarAsteriscos") *
            input(type="email" id="email" name="email" v-model="usuarioForm.email" @input="validateInputs" @blur="validations(usuarioForm, errorUser)" :disabled="anonimo || (usuarioLogado && !anonimo)" :readonly="usuarioLogado")
            span.error-message(v-if="errorUser.name==='email'") {{errorUser.message}}

      template(v-if="timeLine === 1")
        .step-content(:class="{'active-step': etapas[0].avancar}")
          .input-group
            label(for="cep")
            | CEP
            span.text-danger() *
            input(
              type="text"
              id="cep"
              name="cep"
              v-model="denunciaForm.localDaOcorrencia.cep"
              maxlength="9"
              placeholder="Digite o CEP"
              required
              @input="validateInputs"
            )
          .input-group
            label(for="logradouro") Logradouro
            input(type="text" id="logradouro" name="logradouro" v-model="denunciaForm.localDaOcorrencia.logradouro" @input="validateInputs" required :disabled="true")

          .input-group
            label(for="bairro") Bairro
            input(type="text" id="bairro" v-model="denunciaForm.localDaOcorrencia.bairro" required :disabled="true")

          .input-group
            label(for="cidade") Cidade
            input(type="text" id="cidade" v-model="denunciaForm.localDaOcorrencia.cidade" required :disabled="true")

          .input-group
            label(for="estado") Estado
            input(type="text" id="estado" v-model="denunciaForm.localDaOcorrencia.estado" required :disabled="true")

      template(v-if="timeLine === 2")
        .step-content(:class="{'active-step': etapas[1].avancar}")

          .input-group
            label(for="tipoOcorrenciaId")
            | Tipo de Ocorrência
            span.text-danger() *
            select(id="tipoOcorrenciaId" v-model="tipoOcorrencia" required)
              option(value="" disabled selected) Selecione o Tipo de Ocorrência
              option(v-for="tipo in artigos" :key="tipo.idArtigo" :value="tipo.idArtigo") {{ tipo.descricaoArtigo }}


          .input-group
            label(for="placa")
            | Placa do Veículo
            span.text-danger() *
            input(type="text" id="placa" v-model="placa" required)
          .input-group
            label(for="ano")
              | Ano do Veículo
              span.text-danger() *
            select(id="ano" v-model="ano" required)
              option(value="" disabled selected) Selecione o ano
              option(v-for="ano in anosDisponiveis" :key="ano" :value="ano") {{ ano }}

          .input-group
            label(for="marca")
            | Marca
            span.text-danger() *
            input(type="text" id="marca" v-model="marca" required)
          .input-group
            label(for="modelo")
            | Modelo
            span.text-danger() *
            input(type="text" id="modelo" v-model="modelo" required)
          .input-group
            label(for="cor")
            | Cor
            span.text-danger() *
            input(type="text" id="cor" v-model="cor" required)

      template(v-if="timeLine === 3")
        .step-content(:class="{'active-step': etapas[2].avancar}")
          .input-group
            label(for="dataOcorrencia")
            | Data da Ocorrência
            span.text-danger() *
            input(type="date" id="dataOcorrencia" v-model="dataOcorrencia" required)

          .input-group
            label(for="horaOcorrencia")
            | Hora da Ocorrência
            span.text-danger() *
            input(type="time" id="horaOcorrencia" v-model="horaOcorrencia" required)

          .input-group
            label(for="descricao")
            | Descrição
            span.text-danger() *
            textarea(id="descricao" v-model="descricao" required)

      template(v-if="etapas[timeLine].valor === 5")
        .step-content(:class="{'active-step': etapas[timeLine].valor === 5}")
          .termo-container
            h2 Termo de Envio de Denúncia
            p Ao prosseguir, você confirma que as informações fornecidas são verdadeiras e que entende as implicações legais da denúncia falsa.

          .input-alertas(v-if="!anonimo")
            label(for="receberAlertas") Deseja receber alertas por e-mail sobre sua denúncia?
            .alertas-checkbox
              input(type="checkbox" id="receberAlertas" v-model="receberAlertas")
              span Receber alertas por e-mail

    .botoes
      button.btn-voltar(type="button" @click="voltar" :disabled="timeLine === 0") Voltar
      button.btn-avancar(type="button" @click="proximaEtapa" :disabled="!etapas[timeLine].avancar") {{ etapas[timeLine].valor === 5 ? 'Enviar Denúncia' : 'Próxima Etapa' }}

</template>

<style scoped>
.denuncia {
  padding: 2rem;
  max-width: 1000px;
  margin: auto;
  font-family: 'Arial', sans-serif;
  background-color: #fefefe;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

h1 {
  text-align: center;
  margin-bottom: 2rem;
  color: #2c3e50;
}

/* Campos de formulário */
.input-group {
  margin-bottom: 1.2rem;
  margin-top: 10px;
}

.input-group input {
  margin-bottom: 1rem;
}

.input-anonimo {
  margin-bottom: 1.2rem;
  text-align: center;
}

label {
  display: block;
  margin-bottom: 0.4rem;
  font-weight: 500;
  color: #333;
}

input,
textarea,
select {
  width: 100%;
  padding: 0.7rem;
  font-size: 0.9rem;
  border: 1px solid #ccc;
  border-radius: 6px;
  transition: border-color 0.3s;
}

input:focus,
textarea:focus,
select:focus {
  outline: none;
  border-color: #28a745;
}

textarea {
  resize: vertical;
  min-height: 80px;
}

/* Botões */
.botoes {
  display: flex;
  justify-content: center;
  gap: 1rem;
  margin-top: 1.5rem;
}

button {
  padding: 0.6rem 1.2rem;
  font-size: 0.9rem;
  border: 2px solid #28a745;
  border-radius: 6px;
  cursor: pointer;
  background-color: white;
  color: #2c3e50;
  transition: all 0.3s ease;
}

button:hover,
button:focus {
  background-color: #28a745;
  color: white;
}

button:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

/* Estilo do campo anônimo */
.anonimo-checkbox {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 1rem;
}

.anonimo-checkbox input {
  margin-right: 8px;
}

/* Termo de aceite */
.termo-container {
  max-width: 600px;
  margin: 0 auto;
  text-align: center;
  background-color: #f8f8f8;
  padding: 2rem;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.termo-container h2 {
  font-size: 1.5rem;
  margin-bottom: 1rem;
}

.termo-container p {
  font-size: 1rem;
  line-height: 1.5;
  color: #333;
}

/* Alertas */
.input-alertas {
  margin-top: 1.2rem;
  text-align: center;
}

.alertas-checkbox {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 0.5rem;
}

.alertas-checkbox input[type='checkbox'] {
  width: 18px;
  height: 18px;
  margin-right: 8px;
  accent-color: #28a745;
  cursor: pointer;
}

.alertas-checkbox span {
  font-size: 0.95rem;
  color: #333;
}

.timeline {
  display: flex;
  justify-content: space-between;
  position: relative;
  margin: 40px 0;
  align-items: center;
  width: 100%;
}
.progress-line {
  position: absolute;
  top: 20%; /* A linha passa no meio dos ícones */
  left: 10%;
  right: 10%;
  height: 3px;
  background-color: #ccc; /* Linha de progresso cinza */
  z-index: 0;
  transition: width 0.4s ease;
  transform: translateY(-50%);
  width: 80%; /* Linha cinza ocupa toda a largura */
}

.progress-line .fill {
  background-color: green; /* Preenchimento verde */
  height: 3px;
  width: 0%; /* Inicialmente, o verde começa com 0% */
  transition: width 0.4s ease;
}

/* Estilo das etapas */
.step {
  position: relative;
  text-align: center;
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  z-index: 2;
}

.step i {
  background-color: #ccc;
  color: white;
  border-radius: 50%;
  padding: 10px;
  font-size: 1.2rem;
  margin-bottom: 8px;
  display: inline-block;
  transition: all 0.3s ease;
  position: absolute; /* Coloca o ícone no meio da linha */
  top: -15px; /* Distância da linha */
}

.step.active i {
  background-color: #28a745; /* Cor verde para a etapa ativa */
}

.step span {
  display: block;
  font-size: 0.9rem;
  color: #333;
  margin-top: 30px; /* Ajusta a distância do texto em relação ao ícone */
}

.step.completed i {
  background-color: #28a745; /* Cor verde para as etapas completadas */
}

.progress-line.completed {
  background-color: #28a745;
}

/* Remover o ícone de check para etapas completadas */
.step.completed::before {
  content: ''; /* Remover ícone de check */
}

.error-message {
  color: red;
  font-size: 0.875rem; /* Tamanho da fonte para a mensagem de erro */
  margin-top: 0.25rem;
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.text-danger {
  color: red;
  margin-left: 4px;
}
</style>

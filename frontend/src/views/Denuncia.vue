<script setup lang="ts">
import { buscarArtigos } from '@/services/artigoService'
import * as authService from '@/services/auth-service'
import { enviarDenuncia as enviarDenunciaService } from '@/services/ocorrenciasService'
import * as tipoOcorService from '@/services/tipoOcorrenciaService'
import * as userService from '@/services/userService'
import { useLoadingStore } from '@/stores/loadingStore'
import type { EnderecoProps } from '@/types/endereco-type'
import type { TipoOcorrenciaType } from '@/types/tipoOcorrencia'
import type { AuthProps, UserSimpleProps } from '@/types/user-type'
import { formatCEP, formatCPF, replaceNumbers, validations } from '@/utils/form'
import axios from 'axios'
import { computed, onMounted, reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { toast } from 'vue3-toastify'
const store = useLoadingStore()
const router = useRouter()

// Definindo os dados do formulário
const placa = ref('')
const ano = ref<number | null>(null) // ✅ esta é a correta
const marca = ref('')
const modelo = ref('')
const cor = ref('')
const horaOcorrencia = ref('')
const dataOcorrencia = ref('')
const descricao = ref('')
const anonimo = ref(false)
const tipoOcorrenciaList = ref<TipoOcorrenciaType[]>([])
const artigos = ref([])
const artigoSelecionadoId = ref(null)
const receberAlertas = ref(true) // valor padrão: sim
const anoAtual = new Date().getFullYear()
const anosDisponiveis = ref<number[]>([])
const formattedCep = ref('')

const formatCepInput = (event: Event) => {
  const input = event.target as HTMLInputElement
  const raw = input.value.replace(/\D/g, '') // Remove tudo que não é número

  // Formata com traço se possível
  if (raw.length <= 5) {
    formattedCep.value = raw
  } else {
    formattedCep.value = `${raw.slice(0, 5)}-${raw.slice(5, 8)}`
  }

  // Atualiza o CEP limpo para busca
  enderecoForm.cep = raw.slice(0, 8)
}
// Controle da etapa atual
const etapa = ref(1) // Etapa inicial 1, agora etapa 2 será para localização

// Definindo os dados do usuário
const userAuth = reactive<AuthProps>(authService.getAccessToken())
const usuarioForm = reactive<UserSimpleProps>({} as UserSimpleProps)
const errorUser = ref({ name: '', message: '' }) // Objeto para armazenar erros de validação

// Endereço
const enderecoForm = reactive<EnderecoProps>({} as EnderecoProps) // Objeto para armazenar os dados do endereço;
const errorEndereco = ref({ name: '', message: '' }) // Objeto para armazenar erros de validação de endereço

const logradouro = ref('')
const bairro = ref('')
const cidade = ref('')
const estado = ref('')
const progresso = computed(() => {
  // Total de 5 etapas: 0%, 25%, 50%, 75%, 100%
  return ((etapa.value - 1) / 4) * 100 // Ajusta a porcentagem de acordo com a etapa
})

// Função para buscar endereço usando o CEP
const buscarEndereco = async () => {
  if (enderecoForm.cep && enderecoForm.cep.length == 9) {
    store.startLoading() // Inicia o loading
    const cep = replaceNumbers(enderecoForm.cep) // Remove caracteres não numéricos do CEP
    try {
      const response = await axios.get(`https://viacep.com.br/ws/${cep}/json/`)
      enderecoForm.logradouro = response.data.logradouro || ''
      enderecoForm.bairro = response.data.bairro || ''
      enderecoForm.cidade = response.data.localidade || ''
      enderecoForm.estado = response.data.uf || ''
    } catch (error) {
      enderecoForm.logradouro = ''
      enderecoForm.bairro = ''
      enderecoForm.cidade = ''
      enderecoForm.estado = ''
      toast.error('Verifique o CEP.')
    }finally {
      store.stopLoading() // Para o loading quando a ação terminar
    }
  }
}

// Validação da etapa de localização
const etapa2Valida = computed(() => {
  return (
    enderecoForm.cep &&
    enderecoForm.logradouro &&
    enderecoForm.bairro
  )
})

const usuarioLogado = ref(false) // Controla se o usuário está logado
const carregarArtigos = async () => {
  try {
    const data = await buscarArtigos()
    artigos.value = data
  } catch (error) {
    toast.error('Erro ao carregar os artigos do Código Penal.' + error)
  }
}

const carregarTiposOcorrencia = async () => {
  try {
    tipoOcorrenciaList.value = await tipoOcorService.findAll()
  } catch (error) {
    toast.error('Erro ao carregar os tipos de ocorrência.\n' + error)
  }
}

const buscarUsuario = async () => {
  // verifica se o usuário está logado
  if (!userAuth.id) {
    // Não faz requisição se não houver token
    //console.warn('Token não encontrado. Usuário não está autenticado.')
    return
  }
  await userService
    .findById(userAuth.id)
    .then((response) => {
      const newUser = response.data as UserSimpleProps
      usuarioForm.id = newUser.id
      usuarioForm.name = newUser.name
      usuarioForm.cpf = newUser.cpf
      usuarioForm.email = newUser.email
      etapa.value = 2 // Se o usuário estiver logado, inicia na etapa 2
    })
    .catch((error) => {
      console.error('Erro ao buscar usuário:', error)
      toast.error('Erro ao buscar usuário. Verifique os dados e tente novamente.')
    })
}

const validateInputs = (event: Event) => {
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
    enderecoForm.cep = formatCEP(value)
    buscarEndereco() // Busca o endereço ao digitar o CEP
  }
  errorUser.value = { name: '', message: '' } // Reseta o erro ao validar os inputs
  validations(usuarioForm, errorUser.value)
}

// Controle da etapa atual

// Função que altera a etapa atual
const proximaEtapa = () => {
  if (etapa.value < 5) {
    // Agora são 5 etapas, de 1 a 5
    etapa.value++
  } else {
    enviarDenuncia()
  }
}

// Função que volta à etapa anterior
const voltar = () => {
  if (etapa.value > 1) {
    etapa.value--
  }
}

const etapa1Valida = computed(() => {
  if (anonimo.value) return true
  if (
    usuarioForm.name &&
    usuarioForm.cpf &&
    usuarioForm.email &&
    validations(usuarioForm, errorUser.value)
  ) {
    return true
  }
  return userAuth.id
})

// Validação da etapa 2
const etapa3Valida = computed(() => {
  return (
    placa.value.trim() !== '' &&
    ano.value !== null &&
    ano.value > 0 &&
    marca.value.trim() !== '' &&
    modelo.value.trim() !== '' &&
    cor.value.trim() !== '' &&
    artigoSelecionadoId.value !== null
  )
})

// Validação da etapa 3
const etapa4Valida = computed(() => {
  return horaOcorrencia.value.trim() !== '' && descricao.value.trim() !== ''
})

const podeAvancar = computed(() => {
  if (etapa.value === 1) return etapa1Valida.value
  if (etapa.value === 2) return etapa2Valida.value
  if (etapa.value === 3) return etapa3Valida.value
  if (etapa.value === 4) return etapa4Valida.value

  return true // etapa 4
})

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
      username: anonimo.value ? 'Anônimo' : usuarioForm.name
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
    etapa.value = 2
  } else {
    etapa.value = 1
  }
}

// Buscar dados do usuário assim que o componente for montado
onMounted(() => {
  buscarUsuario()
  carregarArtigos()
  carregarTiposOcorrencia()
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
    h1 Registrar Denúncia de Roubo ou Furto de Veículo

    // Linha do tempo
    .timeline
      .progress-line(:class="{ completed: progresso === 100 }")
        .fill(:style="{ width: progresso + '%' }")
      .step(:class="{ active: etapa === 1, completed: etapa > 1 }")
        i.fas.fa-user
        span Dados Pessoais
      .step(:class="{ active: etapa === 2, completed: etapa > 2 }")
        i.fas.fa-map-marker-alt
        span Local
      .step(:class="{ active: etapa === 3, completed: etapa > 3 }")
        i.fas.fa-car
        span Veículo
      .step(:class="{ active: etapa === 4, completed: etapa > 4 }")
        i.fas.fa-comment
        span Descrição
      .step(:class="{ active: etapa === 5 }")
        i.fas.fa-check-circle
        span Finalizar



    .line
    form(@submit.prevent="enviarDenuncia")
      template(v-if="etapa === 1")
        .step-content(:class="{'active-step': etapa === 1}")
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

      template(v-if="etapa === 2")
        .step-content(:class="{'active-step': etapa === 2}")
          .input-group
            label(for="cep")
            | CEP
            span.text-danger() *
            input(
              type="text"
              id="cep"
              name="cep"
              v-model="enderecoForm.cep"
              maxlength="9"
              placeholder="Digite o CEP"
              required
              @input="validateInputs"
              @blur="buscarEndereco"
            )
          .input-group
            label(for="logradouro") Logradouro
            input(type="text" id="logradouro" name="logradouro" v-model="enderecoForm.logradouro" @input="validateInputs" required :disabled="true")

          .input-group
            label(for="bairro") Bairro
            input(type="text" id="bairro" v-model="enderecoForm.bairro" required :disabled="true")

          .input-group
            label(for="cidade") Cidade
            input(type="text" id="cidade" v-model="enderecoForm.cidade" required :disabled="true")

          .input-group
            label(for="estado") Estado
            input(type="text" id="estado" v-model="enderecoForm.estado" required :disabled="true")

      template(v-if="etapa === 3")
        .step-content(:class="{'active-step': etapa === 3}")

          .input-group
            label(for="tipoOcorrenciaId")
            | Tipo de Ocorrência
            span.text-danger() *
            select(id="tipoOcorrenciaId" v-model="tipoOcorrencia" required)
              option(value="" disabled selected) Selecione o Tipo de Ocorrência
              option(v-for="tipo in tipoOcorrenciaList" :key="tipo.id" :value="tipo.id") {{ tipo.name }}


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

      template(v-if="etapa === 4")
        .step-content(:class="{'active-step': etapa === 4}")
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

      template(v-if="etapa === 5")
        .step-content(:class="{'active-step': etapa === 5}")
          .termo-container
            h2 Termo de Envio de Denúncia
            p Ao prosseguir, você confirma que as informações fornecidas são verdadeiras e que entende as implicações legais da denúncia falsa.

          .input-alertas(v-if="!anonimo")
            label(for="receberAlertas") Deseja receber alertas por e-mail sobre sua denúncia?
            .alertas-checkbox
              input(type="checkbox" id="receberAlertas" v-model="receberAlertas")
              span Receber alertas por e-mail

    .botoes
      button.btn-voltar(type="button" @click="voltar" :disabled="etapa === 1") Voltar
      button.btn-avancar(type="button" @click="proximaEtapa" :disabled="!podeAvancar") {{ etapa === 5 ? 'Enviar Denúncia' : 'Próxima Etapa' }}

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

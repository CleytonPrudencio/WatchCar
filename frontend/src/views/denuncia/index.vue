<template>
  <div class="container">
    <h2>Cadastro de Endereço</h2>
    <form @submit.prevent="handleSubmit">
      <div>
        <label>Nome</label>
        <input v-model="dataForm.name" type="text" campo="luanna" name="name" required @input="handleInputs" />
      </div>
      <div>
        <label>Rua</label>
        <input v-model="dataForm.local.rua" type="text" campo="local" name="rua" required @input="handleInputs" />
      </div>
      <div>
        <label>Número</label>
        <input
          v-model="dataForm.local.numero"
          type="text"
          name="numero"
          required
          @input="handleInputs"
        />
      </div>
      <div>
        <label>Complemento</label>
        <input
          v-model="dataForm.local.complemento"
          type="text"
          name="complemento"
          @input="handleInputs"
        />
      </div>
      <div>
        <label>Bairro</label>
        <input
          v-model="dataForm.local.bairro"
          type="text"
          name="bairro"
          required
          @input="handleInputs"
        />
      </div>
      <div>
        <label>Cidade</label>
        <input
          v-model="dataForm.local.cidade"
          type="text"
          name="cidade"
          required
          @input="handleInputs"
        />
      </div>
      <div>
        <label>Estado</label>
        <input
          v-model="dataForm.local.estado"
          type="text"
          name="estado"
          required
          @input="handleInputs"
        />
      </div>
      <div>
        <label>CEP</label>
        <input v-model="dataForm.local.cep" type="text" name="cep" required @input="handleInputs" />
      </div>
      <button type="submit">Salvar</button>
    </form>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue'
import { LocalProps } from '@/types/local'

interface DataForm {
  name: string
  local: LocalProps
}

export default defineComponent({
  name: 'CadastroEndereco',
  setup() {
    // Inicializa o estado reativo com a estrutura DataForm
    const dataForm = ref<DataForm>({
      name: '',
      local: {
        rua: '',
        numero: '',
        complemento: '',
        bairro: '',
        cidade: '',
        estado: '',
        cep: '',
      },
    })

    // Função genérica para lidar com inputs
    const handleInputs = (event: Event) => {
      const input = event.target as HTMLInputElement
      const { name, value} = input

      // Atualiza o campo correspondente
      if (name === 'name') {
        dataForm.value.name = value
      } else {
        // Atualiza os campos de local dinamicamente
        dataForm.value.local = {
          ...dataForm.value.local,
          [name]: value,
        }
      }
      console.log(`name: ${name}, value: ${value}`)      
      console.log(`campo: ${campo}.value: ${dataForm.value[campo]}`)
      
    }

    // Função para lidar com o envio do formulário
    const handleSubmit = () => {
      console.log('Dados do formulário:', dataForm.value)
      // Aqui você pode adicionar a lógica para enviar os dados ao backend
    }

    return {
      dataForm,
      handleInputs,
      handleSubmit,
    }
  },
})
</script>

<style scoped>
.container {
  max-width: 600px;
  margin: 0 auto;
  padding: 20px;
}
form {
  display: flex;
  flex-direction: column;
  gap: 15px;
}
div {
  display: flex;
  flex-direction: column;
}
label {
  font-weight: bold;
}
input {
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
}
button {
  padding: 10px;
  background-color: #28a745;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}
button:hover {
  background-color: #218838;
}
</style>

import type { VeiculoProps } from '@/types/veiculo-type';
import { formatPlaca } from '@/utils/forms';

export const validations = (veiculo: VeiculoProps) => {
  if (!veiculo) {
    return new Error('Preencha todos os campos obrigatórios!')
  }

  if (!veiculo.placaVeiculo) {
    veiculo.error = {name: 'placa', message: 'O campo placa é obrigatório.'}
    return;
  }

  if (veiculo.placaVeiculo) {
    const placa = veiculo.placaVeiculo = formatPlaca(veiculo.placaVeiculo);
    if(placa.length < 7){
      veiculo.error = {name: 'placa', message: 'Placa inválida! Deve conter 7 caracteres.'}
      return;
    }
  }

  if (!veiculo.anoVeiculo) {
    veiculo.error = {name: 'ano', message: 'Digite o ano do veículo!'}
    return;
  }else{
    const ano = veiculo.anoVeiculo;
    if (ano.toString().length !== 4 || isNaN(Number(ano)) || ano < 1000 || ano > 2999) {
      veiculo.error = {name: 'ano', message: 'Ano inválido!'}
      return;
    }
  }

  if (!veiculo.marcaVeiculo) {
    veiculo.error = {name: 'marca', message: 'Digite a marca do veículo!'}
    return;
  }
  if (!veiculo.modeloVeiculo) {
    veiculo.error = {name: 'modelo', message: 'Digite o modelo do veículo!'}
    return;
  }

  if (!veiculo.corVeiculo) {
    veiculo.error = {name: 'cor', message: 'Digite a cor do veículo!'}
    return;
  }else{
    const cor = veiculo.corVeiculo;
    if (cor.length < 3) {
      veiculo.error = {name: 'cor', message: 'Cor inválida! Deve conter no mínimo 3 caracteres.'}
      return;
    }
  }
  delete veiculo.error;
}

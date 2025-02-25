<template>
  <div class="main-container">
    <h1>TRANSFERÊNCIAS</h1>

    <div v-if="transferencias.length === 0">
      <p>Carregando transferências...</p>
    </div>

    <div v-else>
      <h2>Lista de Transferências</h2>
      <table class="tabela-transferencia">
        <thead>
          <tr>
            <th>Número</th>
            <th>Origem</th>
            <th>Destino</th>
            <th>Valor</th>
            <th>Taxa %</th>
            <th>Data da Transferência</th>
            <th>Data do Agendamento</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="transferencia in transferencias" :key="transferencia.id">
            <td>{{ transferencia.id }}</td>
            <td>{{ transferencia.contaOrigem }}</td>
            <td>{{ transferencia.contaDestino }}</td>
            <td>{{ transferencia.valor }}</td>
            <td>{{ transferencia.taxa }}</td>
            <td>{{ transferencia.dataTransferencia }}</td>
            <td>{{ transferencia.dataAgendamento }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>
  
<script setup>
import { ref } from 'vue';
import axios from 'axios';

const { transferencias } = useTransferencias();

function useTransferencias() {
  const transfers = ref([]);

  axios.get('http://localhost:8081/v1/api/transferencias')
    .then((response) => {
      transferencias.value = response.data.map((transferencia, index) => ({ id: index + 1, ...transferencia }));
    })
    .catch((error) => {
      console.error('Erro ao recuperar transferências:', error);
    });

  return { transferencias };
}
</script>
  
<style scoped>
.main-container {
  text-align: center;
  margin: 20px;
}

.tabela-transferencia {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}

.tabela-transferencia th,
.tabela-transferencia td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: left;
}

.tabela-transferencia th {
  background-color: #111;
  color: #fff;
}

.tabela-transferencia tbody tr:nth-child(even) {
  background-color: #f9f9f9;
}

.tabela-transferencia tbody tr:hover {
  background-color: #e5e5e5;
}
</style>
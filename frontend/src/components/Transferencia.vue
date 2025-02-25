<template>
  <div>
    <div class="form-container" :class="{ 'form-submitted': isFormSubmitted || errorMessage }">
      <transition name="fade" mode="out-in">
        <div :key="isFormSubmitted || errorMessage">
          <form v-if="!isFormSubmitted && !errorMessage" id="transfer-form" @submit.prevent="submitForm" :class="{ 'form-transition': !errorMessage }">
            <div class="input-container">
              <label for="contaOrigem">Conta de Origem</label>
              <input type="text" id="contaOrigem" name="contaOrigem" v-model="contaOrigem" placeholder="Digite a conta de origem" class="bottom-border" required />
            </div>
            <div class="input-container">
              <label for="contaDestino">Conta de Destino</label>
              <input type="text" id="contaDestino" name="contaDestino" v-model="contaDestino" placeholder="Digite a conta de destino" class="bottom-border" required />
            </div>
            <div class="input-container">
              <label for="valor">Valor</label>
              <input type="text" id="valor" name="valor" v-model="valor" placeholder="Digite o valor" class="bottom-border" @input="validateValor" required />
            </div>
            <div class="input-container">
              <label for="dataTransferencia">Data de Transferência</label>
              <input type="date" id="dataTransferencia" name="dataTransferencia" v-model="dataTransferencia" class="bottom-border" required />
            </div>
            <div class="input-container">
              <input type="submit" class="submit-btn" value="Agendar Transferência" />
            </div>
          </form>

          <div v-if="successMessage || errorMessage" class="message-container">
            <div v-if="successMessage" class="success-message-container">
              <p class="success-message">{{ successMessage }}</p>
              <button class="submit-btn-re" @click="resetForm">Realizar outra transferência</button>
            </div>

            <div v-if="errorMessage" class="error-message-container">
              <p class="error-message">{{ errorMessage }}</p>
              <button class="submit-btn-re" @click="resetForm">Tentar novamente</button>
            </div>
          </div>
        </div>
      </transition>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "Trasferencia",
  data() {
    return {
      contaOrigem: "",
      contaDestino: "",
      valor: "",
      dataTransferencia: "",
      isFormSubmitted: false,
      errorMessage: "",
      successMessage: "",
    };
  },
  methods: {
    submitForm() {
      this.errorMessage = "";
      this.successMessage = "";

      if (
        !this.contaOrigem ||
        !this.contaDestino ||
        !this.valor ||
        this.valor <= 0 ||
        !this.dataTransferencia
      ) {
        this.errorMessage = "Preencha todos os campos corretamente.";
        setTimeout(() => {
          this.errorMessage = "";
        }, 3000);
        return;
      }

      const dataTransferenciaObject = new Date(this.dataTransferencia);
      const day = dataTransferenciaObject.getUTCDate().toString().padStart(2, '0');
      const month = (dataTransferenciaObject.getUTCMonth() + 1).toString().padStart(2, '0');
      const year = dataTransferenciaObject.getUTCFullYear();

      const dataTransferenciaFormatada = `${day}/${month}/${year}`;

      const formData = {
        contaOrigem: this.contaOrigem,
        contaDestino: this.contaDestino,
        valor: parseFloat(this.valor.replace(',', '.')),
        dataTransferencia: dataTransferenciaFormatada,
      };

      axios
        .post("http://localhost:8081/v1/api/transferencias", formData)
        .then((response) => {
          this.isFormSubmitted = true;
          this.successMessage = response.data.message || "Transferência agendada com sucesso!";
        })
        .catch((error) => {
          if (error.response && error.response.data) {
            this.errorMessage = error.response.data;
          } else {
            this.errorMessage = "Erro ao registrar transferência.";
          }
        });
    },

    resetForm() {
      this.contaOrigem = "";
      this.contaDestino = "";
      this.valor = "";
      this.dataTransferencia = "";
      this.isFormSubmitted = false;
      this.errorMessage = "";
      this.successMessage = "";
    },

    validateAmount() {
      this.valor = this.valor.replace(/[^0-9,.]/g, '');

      const decimalCount = (this.valor.match(/\./g) || []).length;
      if (decimalCount > 1) {
        this.valor = this.valor.slice(0, -1);
      }
    },
  },
};
</script>

<style scoped>
.form-container {
  max-width: 300px;
  margin: 0 auto;
  height: auto;
}

.form-transition {
  transition: opacity 0.5s;
}

.message-container {
  text-align: center;
  margin-top: 20px;
  font-weight: bold;
  margin-top: 40px;
  margin-bottom: 80px;
  font-size: 18px;
}

.success-message-container {
  color: green;
}

.error-message-container {
  color: red;
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.5s;
}

.fade-enter,
.fade-leave-to {
  opacity: 0;
}

#transfer-form {
  max-width: 300px;
  margin: 0 auto;
  height: auto;
}

.input-container {
  display: flex;
  flex-direction: column;
  margin-bottom: 20px;
}

label {
  font-weight: bold;
  margin-bottom: 15px;
  color: #222;
  padding: 5px 10px;
  border-left: 4px solid #fc0303;
}

input.bottom-border {
  border: none;
  border-bottom: 1px solid #fc0303;
  padding: 5px 10px;
  width: 300px;
}

.submit-btn,
.submit-btn-re {
  background-color: #222;
  color: #ffff;
  font-weight: bold;
  border: 2px solid #222;
  padding: 10px;
  margin: 0 auto;
  cursor: pointer;
  transition: 0.5s;
}

.submit-btn-re {
  margin-top: 80px;
}

.submit-btn:hover,
.submit-btn-re:hover {
  background-color: red;
  color: #ffff;
}
</style>
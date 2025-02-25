🚀 Agendamento Transferência

O Agendamento Transferência é um sistema Full Stack desenvolvido para demonstrar a implementação de um fluxo completo 
de agendamento e gerenciamento de transferências financeiras, garantindo eficiência e organização nas transações.

✨ Tecnologias Utilizadas

# **Backend**
- ✅ Java 11
- ✅ Spring Boot
- ✅ Hibernate
- ✅ H2 Database (banco de dados em memória)


# **Frontend**
- ✅ Vue.js
- ✅ Axios

# **Outras Ferramentas**
- ✅ Docker

---

🛠️ Como Executar o Projeto

# 📌 1. Executar com Docker (Recomendado)

Certifique-se de ter o **Docker** instalado na sua máquina.

# Clone o repositório

git clone https://github.com/gc-softdev/agendamento-transferencia.git
cd agendamento-transferencia

# Suba os containers
docker-compose up

🍺 Aguarde até que os serviços estejam prontos.

🍒 Frontend disponível em: http://localhost:8080
🍒 Backend disponível em: http://localhost:8081


📌 2. Executar Manualmente (Alternativa Sem Docker)

# Backend
cd backend
mvn spring-boot:run

# Frontend
cd frontend

# Instalar dependências
npm install

# Executar o servidor de desenvolvimento
npm run serve

🍒 O frontend estará disponível em: http://localhost:8080

📡 Endpoints da API
📍 Obter todas as transferências

# Endpoint:
GET /v1/api/transferencias
Descrição: Retorna uma lista de todas as transferências agendadas.

🔹 Como testar?

Faça uma requisição GET para:

http://localhost:8081/v1/api/transferencias
(Use Postman, cURL ou seu cliente HTTP favorito).

📍 Agendar uma nova transferência

# Endpoint:
POST /v1/api/transferencias
Descrição: Permite agendar uma nova transferência.

Parâmetros do corpo (JSON):

{
"contaOrigem": "ContaOrigem123",
"contaDestino": "ContaDestino456",
"valor": 100.00,
"dataTransferencia": "25/02/2025"
}

🔹 Como testar?

Faça uma requisição POST para:

http://localhost:8081/v1/api/transferencias
(Inclua o JSON acima no corpo da requisição).

Se a solicitação for bem-sucedida, a API retornará os detalhes da transferência agendada.


📄 Documentação
Toda a documentação está centralizada neste README para facilitar o acesso.


🏆 Contribuições
Contribuições são bem-vindas! 

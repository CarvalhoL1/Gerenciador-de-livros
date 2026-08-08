# Sistema de Gerenciamento de Livros

Aplicação web para gerenciamento de livros pessoais, com backend em **Spring Boot** e frontend em **React**. O sistema permite que o usuário cadastre, acompanhe e organize suas leituras de forma simples e visual.

Este projeto é a migração de uma versão anterior em Java desktop (JavaFX), reestruturada como uma API REST consumida por um frontend web separado.

---

## Funcionalidades

- Cadastro de usuários
- Login com autenticação por token
- Cadastro de livros
- Edição de informações do livro diretamente na tabela (duplo clique para editar)
- Exclusão de livros
- Controle de progresso da leitura (porcentagem de conclusão)
- Definição de status do livro:
    - Quero Ler
    - Lendo
    - Pausado
    - Lido
    - Abandonado
- Alteração de nome e senha da conta
- Exclusão de conta (com confirmação de senha)
- Dados salvos em banco de dados **SQLite**

---

## Tecnologias Utilizadas

### Backend
- **Java**
- **Spring Boot** (Web, Validation)
- **SQLite** (banco de dados)
- **JDBC** (conexão com banco de dados)
- **jBCrypt** (hash seguro de senhas)

### Frontend
- **React** (Vite)
- **React Router** (navegação entre páginas)
- **Axios** (consumo da API)

---

## Estrutura do Projeto

### Backend

```
src/main/java/gerenciadorLivros
 ├─ controller     → Endpoints REST (Auth, Usuário, Livro)
 ├─ service        → Regras de negócio
 ├─ dados          → Repositórios (acesso ao banco via JDBC)
 ├─ dto            → Objetos de requisição/resposta da API
 ├─ model          → Entidades (Usuario, Livro)
 └─ db             → Conexão e criação do banco (SQLite)
```

### Frontend

```
src
 ├─ pages          → Telas (Login, Cadastro, Início, Alterar Nome/Senha)
 │   └─ components → Modais (Adicionar Livro, Alterar Nome, Alterar Senha)
 └─ api.js         → Configuração do Axios e injeção do token
```

---

## Autenticação

- A autenticação é feita por token (gerado no login e armazenado no `localStorage` do navegador). Toda requisição autenticada envia o token no header.
- O backend identifica o usuário logado a partir desse token, nenhum dado de identificação (email, ID) é enviado pelo frontend nas operações protegidas, evitando que um usuário acesse ou modifique dados de outro.

---

## Banco de Dados

O sistema utiliza **SQLite**, criando automaticamente o arquivo do banco na primeira execução.

### Tabelas principais

**usuarios**
- id
- nome
- email
- senha_hash

**livros**
- id
- id_usuario (relacionamento com usuário) 
- titulo
- descricao
- total_paginas
- pagina_atual
- status
- atualizado_em

---

## Principais Endpoints

| Método | Rota | Descrição |
|---|---|---|
| POST | `/auth/login` | Autentica o usuário e retorna o token |
| POST | `/auth/cadastro` | Cria uma nova conta |
| GET | `/usuarios/eu` | Retorna os dados do usuário logado |
| PUT | `/usuarios/nome` | Altera o nome do usuário logado |
| PUT | `/usuarios/senha` | Altera a senha do usuário logado |
| POST | `/usuarios/deletar` | Exclui a conta do usuário logado |
| GET | `/livros` | Lista os livros do usuário logado |
| POST | `/livros` | Cadastra um novo livro |
| PUT | `/livros/{id}` | Edita um livro do usuário logado |
| DELETE | `/livros/{id}` | Exclui um livro do usuário logado |

---

## Como Executar

### Backend

1. Clone o repositório:
   ```bash
   git clone https://github.com/CarvalhoL1/Gerenciador-de-livros
   ```
2. Abra o projeto backend na sua IDE
3. Certifique-se de ter o JDK instalado
4. Execute a classe principal (`App`)

O banco de dados SQLite será criado automaticamente na primeira execução.

### Frontend

1. Entre na pasta do frontend:
   ```bash
   cd frontend
   ```
2. Instale as dependências:
   ```bash
   npm install
   ```
3. Rode o servidor de desenvolvimento:
   ```bash
   npm run dev
   ```
4. Acesse `http://localhost:5173`

O backend precisa estar rodando em `http://localhost:8080` para o frontend conseguir se conectar.

---

## Migração desde a versão desktop

Este projeto foi reestruturado a partir de uma versão anterior em Java + JavaFX (desktop). As principais mudanças:

- As telas JavaFX (`ui`) foram substituídas por páginas React consumindo uma API REST
- O acesso direto ao banco a partir da camada de tela foi substituído por chamadas HTTP autenticadas
- A sessão do usuário, antes um campo estático (`Sessao.usuarioLogado`), passou a ser controlada por token, com suporte a múltiplos usuários simultâneos
- A lógica de negócio foi reorganizada em camadas (`controller` → `service` → `dados`), seguindo o padrão de uma aplicação Spring Boot
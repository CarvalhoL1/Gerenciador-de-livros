# Sistema de Gerenciamento de Livros

Aplicação desktop desenvolvida em **Java** com **JavaFX** para gerenciamento de livros pessoais. O sistema permite que o usuário cadastre, acompanhe e organize suas leituras de forma simples e visual.

---

## Funcionalidades

- Cadastro de usuários  
- Login com validação de credenciais  
- Cadastro de livros  
- Edição de informações do livro  
- Exclusão de livros  
- Controle de progresso da leitura (página atual)  
- Definição de status do livro:
     - Quero Ler  
     -  Lendo  
     -  Concluído  

- Interface gráfica estilizada com CSS  
- Dados salvos em banco de dados **SQLite**

---

## Tecnologias Utilizadas

- **Java 17+**
- **JavaFX**
- **SQLite**
- **JDBC**
- **FXML (Scene Builder)**
- **CSS (estilização da interface)**

---

## Estrutura do Projeto

```
src
 ├─ main
 │   ├─ java
 │   │   ├─ ui                → Telas e controllers JavaFX
 │   │   ├─ service           → Regras de negócio
 │   │   └─ db                → Conexão e criação do banco
 │   │
 │   └─ resources
 │       ├─ ui                → Arquivos FXML
 │       └─ css               → Arquivos de estilo
```

---

## Banco de Dados

O sistema utiliza **SQLite**, criando automaticamente o arquivo **base.db**


### Tabelas principais

**Usuários**
- id
- nome
- email
- senha_hash

**Livros**
- id
- titulo
- descricao
- total_paginas
- pagina_atual
- status
- usuario_id (relacionamento com usuário)

---

## Como Executar

1. Clone o repositório:

```bash
git clone https://github.com/seu-usuario/seu-repo.git
```

2. Abra o projeto na sua IDE

3. Certifique-se de ter:
   - JDK 17 ou superior
   - JavaFX configurado no projeto

4. Execute a classe principal (Main/App)

O banco de dados será criado automaticamente na primeira execução.

---

## Interface

A interface foi estilizada com CSS para oferecer uma experiência mais moderna e agradável:

- Tema escuro  
- Botões personalizados  
- Tabela estilizada  
- Campos com bordas arredondadas  

---

## Autor

**Lucas Carvalho**  
Estudante e desenvolvedor em formação, focado em aplicações Java e interfaces gráficas.

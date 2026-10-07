# 💆‍♀️ Maricelma Massoterapia — Sistema Módulo Backend

[![Java](https://img.shields.io/badge/Java-17-orange.svg?style=flat&logo=openjdk)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen.svg?style=flat&logo=springboot)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue.svg?style=flat&logo=postgresql)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-Containerized-2496ED.svg?style=flat&logo=docker)](https://www.docker.com/)
[![License](https://img.shields.io/badge/License-Proprietary-red.svg)](#)

API RESTful completa e comercial desenvolvida para automação operacional, controle de agendamentos, gestão financeira e controle de pacotes de tratamento de uma clínica de massoterapia.

---

## 🚀 Tecnologias Utilizadas

- **Linguagem & Framework:** Java 17, Spring Boot 3
- **Persistência de Dados:** Spring Data JPA, Hibernate, PostgreSQL 16
- **Segurança:** Spring Security, Token JWT (JSON Web Token), BCrypt Password Encoder
- **Infraestrutura:** Docker, Docker Compose
- **Documentação & Padrões:** DTOs (Java Records), Arquitetura em Camadas (Controller, Service, Repository)

---

## 📌 Principais Funcionalidades & Regras de Negócio

### 🔐 Autenticação e Controle de Acesso (RBAC)
- Autenticação *stateless* com Token JWT.
- Separação de permissões por nível de acesso:
    - `ADMIN`: Acesso total ao sistema, configurações de serviços, expediente e financeiro.
    - `PROFISSIONAL`: Gestão de agenda, realização de atendimentos e bloqueios de horários.
    - `CLIENTE`: Realização e acompanhamento de agendamentos.

### 📅 Agenda Inteligente & Validações
- **Prevenção de conflitos:** Impede agendamentos duplicados no mesmo intervalo de tempo.
- **Validação de Expediente:** Checa se a marcação está dentro dos dias e horários cadastrados em `HorarioFuncionamento`.
- **Bloqueios de Agenda:** Permite bloquear períodos específicos para folgas, almoço ou manutenção.

### 💆‍♂️ Gestão de Tratamentos & Abatimento de Pacotes
- Suporte a serviços avulsos ou pacotes de sessões (`PlanoTratamento`).
- Abatimento automático do saldo de sessões ao marcar um agendamento como **REALIZADO**.

### 📊 Módulo Financeiro Integrado
- Registro e categorização de despesas operacionais.
- Dashboard financeiro consolidando faturamento bruto, custos totais, lucro líquido e quantidade de atendimentos no período.

---

## 🛠️ Como Executar o Projeto

### Pré-requisitos
- [Docker](https://www.docker.com/) e [Docker Compose](https://docs.docker.com/compose/) instalados na máquina.

### 1. Clonar o repositório
```bash
git clone [https://github.com/jeffersonluizsants/Maricelma-Massoterapeuta0.1.git](https://github.com/jeffersonluizsants/Maricelma-Massoterapeuta0.1.git)
cd Maricelma-Massoterapeuta0.1/backend
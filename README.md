<div style="text-align: center;">
  <img src="https://img.shields.io/badge/Spring%20Boot-4.1.0-6DB33F?logo=springboot&logoColor=white&style=for-the-badge" alt="Spring Boot 4.1.0"/>
  <img src="https://img.shields.io/badge/Java%2025-ED8B00?logo=openjdk&logoColor=white&style=for-the-badge" alt="Java 25"/>
  <img src="https://img.shields.io/badge/Maven-C71A36?logo=apachemaven&logoColor=white&style=for-the-badge" alt="Maven"/>
  <img src="https://img.shields.io/badge/REST%20API-005571?logo=fastapi&logoColor=white&style=for-the-badge" alt="REST API"/>
</div>
<h1 style="text-align: center;">🔐 Coordinator Service</h1>
<p style="text-align: center;"><i>Fonte de verdade para segredos criptográficos em arquitetura de webhooks</i></p>

---

## 📋 Sobre

O **Coordinator Service** atua como a **autoridade central** de segredos HMAC na arquitetura distribuída de webhooks. Ele é responsável por:

- Gerar e armazenar segredos criptográficos de forma segura
- Expor o segredo atual para os serviços **Sender** e **Receiver**
- Permitir rotação manual de chaves via endpoint administrativo
- Manter histórico de versões para auditoria

---

## 🏗️ Arquitetura

```
┌─────────────────┐
│  Coordinator    │◄──── Sender (consulta segredo para assinar)
│   (Porta 8082)  │◄──── Receiver (consulta segredo para validar)
│                 │
│  • Gera v1, v2  │
│  • Rotação      │
│  • Histórico    │
└─────────────────┘
```

---

## 🚀 Tecnologias

| Tecnologia | Versão | Propósito |
|---|---|---|
| Spring Boot | 4.1.0 | Framework principal |
| Java | 25 | Linguagem |
| Maven | 3.9+ | Build e dependências |
| SecureRandom | Nativo | Geração criptográfica de segredos |

---

## ⚙️ Como executar

### Pré-requisitos
- Java 25
- Maven 3.9+

### Comandos
```bash
# Compilar
./mvnw clean package

# Executar
./mvnw spring-boot:run
# ou
java -jar target/coordinator-service-1.0.0.jar
```

O serviço sobe na porta **8082**.

---

## 🔌 Endpoints

### `GET /secrets/current`
Retorna o segredo e a versão atual.

**Resposta:**
```json
{
  "version": "v1",
  "secret": "aGVsbG8td29ybGQ..."
}
```

### `GET /secrets/{version}`
Busca um segredo específico pelo histórico.

### `POST /secrets/rotate`
Gera um novo segredo, promove o atual para histórico e retorna a nova versão.

**Resposta:**
```json
{
  "version": "v2",
  "secret": "bmV3LXNlY3JldC4uLg=="
}
```

---

## 🔒 Segurança

- Segredos gerados com `SecureRandom` (32 bytes, Base64)
- Armazenamento **apenas em memória** (volátil por design)
- Em produção na nuvem: seria provavelmente com AWS Secrets Manager, HashiCorp Vault ou Azure Key Vault

---

## 📁 Estrutura

```
coordinator-service/
├── src/main/java/br/com/danilodps/coordinator/
│   ├── CoordinatorApplication.java
│   ├── controller/
│   │   └── SecretController.java
│   ├── application/
│   │   └── SecretStoreService.java
│   └── domain/
│       └── SecretEntry.java
├── pom.xml
└── README.md
```

---

# Serviços envolvidos
## [sender service](https://github.com/Danilo-dps/sender-service)
## [receiver service](https://github.com/Danilo-dps/receiver-service)

<p style="text-align: center;">Desenvolvido com ☕ e 🔒</p>
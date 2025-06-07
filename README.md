# 📇 API de Gestão de Contactos

Uma aplicação **Spring Boot** para gestão de contactos pessoais e profissionais com API REST completa e documentação Swagger.

---

## 🚀 Funcionalidades

### ✨ Gestão de Utilizadores
- ✅ Criação de utilizadores com validação de email único
- 🔍 Busca por ID ou email
- ✏️ Edição de dados de utilizadores
- 🗑️ Eliminação de utilizadores (com cascata para contactos)

### 📞 Gestão de Contactos
- ✅ Associação de contactos a utilizadores
- 🔍 Listagem e busca de contactos
- ✏️ Edição de contactos existentes
- 🗑️ Eliminação de contactos
- 📋 Filtragem por utilizador

### 🛡️ Funcionalidades Técnicas
- 🔐 Validação robusta de dados com Bean Validation
- 🚨 Tratamento global de exceções com mensagens padronizadas
- 📚 Documentação automática com Swagger/OpenAPI
- 💾 Persistência com H2 Database (arquivo local)
- 🎯 API RESTful seguindo boas práticas

---

## 🔧 Tecnologias Utilizadas

- **Java 21** - Linguagem de programação
- **Spring Boot 3.5.0** - Framework principal
- **Spring Data JPA** - Persistência de dados
- **H2 Database** - Base de dados embebida
- **SpringDoc OpenAPI 2.8.8** - Documentação automática
- **Bean Validation** - Validação de dados
- **Maven** - Gestão de dependências

---

## 📋 Pré-requisitos

- **Java 21** ou superior
- **Maven 3.6** ou superior
- **Git** para clonar o repositório

---

## 🚀 Como Executar

### 1. Clonar o Repositório
```bash
git clone <url-do-repositorio>
cd esw-app-contactos
```

### 2. Compilar e Executar
```bash
cd app-contactos
mvn clean compile
mvn spring-boot:run
```

### 3. Verificar se a Aplicação Está a Funcionar
A aplicação estará disponível em: `http://localhost:8080`

#### Endpoints Principais:
- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **API Docs**: `http://localhost:8080/v3/api-docs`
- **Console H2**: `http://localhost:8080/h2-console`

---

## 📚 Documentação da API

### 🔗 Swagger UI
Aceda à interface interativa da API em:
```
http://localhost:8080/swagger-ui.html
```

### 📖 Endpoints Disponíveis

#### 👥 Utilizadores (`/api/utilizadores`)
```
POST   /api/utilizadores          - Criar utilizador
GET    /api/utilizadores          - Listar todos os utilizadores
GET    /api/utilizadores/{id}     - Obter utilizador por ID
GET    /api/utilizadores/email    - Obter utilizador por email
PUT    /api/utilizadores/{id}     - Atualizar utilizador
DELETE /api/utilizadores/{id}     - Eliminar utilizador
GET    /api/utilizadores/{id}/exists      - Verificar existência por ID
GET    /api/utilizadores/email/exists     - Verificar existência por email
```

#### 📞 Contactos (`/api/contactos`)
```
POST   /api/contactos                     - Criar contacto
GET    /api/contactos                     - Listar todos os contactos
GET    /api/contactos/{id}                - Obter contacto por ID
GET    /api/contactos/utilizador/{id}     - Listar contactos por utilizador
PUT    /api/contactos/{id}                - Atualizar contacto
DELETE /api/contactos/{id}                - Eliminar contacto
GET    /api/contactos/{id}/exists         - Verificar existência
```

### 📝 Exemplos de Uso

#### Criar um Utilizador
```bash
curl -X POST http://localhost:8080/api/utilizadores \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "João Silva",
    "email": "joao.silva@email.com"
  }'
```

#### Criar um Contacto
```bash
curl -X POST http://localhost:8080/api/contactos \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Maria Santos",
    "telefone": "+351 912 345 678",
    "utilizadorId": 1
  }'
```

---

## 🗄️ Base de Dados

### Configuração H2
- **URL**: `jdbc:h2:file:./data/contactos`
- **Utilizador**: `sa`
- **Password**: *(vazia)*
- **Console**: `http://localhost:8080/h2-console`

### Estrutura das Tabelas

#### `utilizadores`
| Campo | Tipo | Restrições |
|-------|------|------------|
| id | BIGINT | PK, AUTO_INCREMENT |
| nome | VARCHAR(100) | NOT NULL |
| email | VARCHAR(150) | NOT NULL, UNIQUE |

#### `contactos`
| Campo | Tipo | Restrições |
|-------|------|------------|
| id | BIGINT | PK, AUTO_INCREMENT |
| nome | VARCHAR(100) | NOT NULL |
| telefone | VARCHAR(20) | - |
| utilizador_id | BIGINT | FK, NOT NULL |

---

## 🛠️ Desenvolvimento

### Executar Testes
```bash
mvn test
```

### Compilar para Produção
```bash
mvn clean package
```

### Executar com Profile de Produção
```bash
java -jar target/app-contactos-0.0.1-SNAPSHOT.war --spring.profiles.active=prod
```

---

## 🐛 Resolução de Problemas

### ✅ Problemas Resolvidos

#### Swagger Error 500
**Problema**: Erro 500 ao aceder `/v3/api-docs`  
**Causa**: Incompatibilidade SpringDoc OpenAPI 2.5.0 com Spring Boot 3.5.0  
**Solução**: Atualizado para SpringDoc OpenAPI 2.8.8

#### Erros de Compilação Lombok
**Problema**: Métodos getters/setters não reconhecidos  
**Causa**: Processamento incorreto das anotações Lombok pelo IDE  
**Solução**: Implementação manual de getters/setters e padrões Builder

### 🔍 Script de Teste
Use o script de teste para verificar se tudo está a funcionar:
```bash
./test-swagger.sh
```

---

## 📄 Validações Implementadas

### Utilizadores
- **Nome**: Obrigatório, 2-100 caracteres
- **Email**: Obrigatório, formato válido, único, máximo 150 caracteres

### Contactos
- **Nome**: Obrigatório, 2-100 caracteres
- **Telefone**: Opcional, formato válido, 7-20 caracteres
- **Utilizador ID**: Obrigatório, deve existir

---

## 🚨 Tratamento de Erros

A API retorna respostas padronizadas para todos os erros:

```json
{
  "timestamp": "2025-06-08 01:20:03",
  "status": 404,
  "error": "Not Found",
  "message": "Utilizador com ID 999 não encontrado",
  "path": "/api/utilizadores/999",
  "traceId": "abc123-def456-ghi789"
}
```

### Códigos de Status HTTP
- **200** - Sucesso
- **201** - Criado com sucesso
- **204** - Eliminado com sucesso
- **400** - Dados inválidos
- **404** - Recurso não encontrado
- **409** - Conflito (email duplicado)
- **500** - Erro interno do servidor

---

## 📝 Formato de Commits

Este projeto utiliza **Conventional Commits**:

```
feat(api): adicionar endpoint de busca por email
fix(validation): corrigir validação de telefone
docs(readme): atualizar documentação da API
refactor(service): simplificar lógica de criação
test(controller): adicionar testes unitários
```

---

## 👥 Contribuição

1. Faça fork do projeto
2. Crie uma branch para a sua funcionalidade
3. Faça commit das suas alterações
4. Push para a branch
5. Abra um Pull Request

---

## 📜 Licença

Este projeto está sob a licença MIT. Consulte o ficheiro `LICENSE` para mais detalhes.

---

## 📞 Suporte

Para questões ou problemas:
- Crie uma issue no repositório
- Consulte a documentação Swagger
- Verifique os logs da aplicação em `app-contactos/app.log`

# 🚀 Guia de Início Rápido

Este guia permite que você execute a aplicação em **menos de 5 minutos**.

---

## ⚡ Execução Rápida

### 1. Pré-requisitos
- Java 21 instalado
- Maven instalado

### 2. Comandos Essenciais
```bash
# 1. Navegar para o diretório da aplicação
cd esw-app-contactos/app-contactos

# 2. Executar a aplicação
mvn spring-boot:run
```

### 3. Testar se Funciona
Aguarde aparecer a mensagem `Started AppContactosApplication` e aceda:

**🌐 Swagger UI**: http://localhost:8080/swagger-ui.html

---

## 🧪 Teste Automático

Use o script de teste para verificar tudo de uma vez:

```bash
cd esw-app-contactos
./test-swagger.sh
```

---

## 📱 Teste Manual Rápido

### Criar um Utilizador
```bash
curl -X POST http://localhost:8080/api/utilizadores \
  -H "Content-Type: application/json" \
  -d '{"nome": "João Silva", "email": "joao@email.com"}'
```

### Criar um Contacto
```bash
curl -X POST http://localhost:8080/api/contactos \
  -H "Content-Type: application/json" \
  -d '{"nome": "Maria", "telefone": "912345678", "utilizadorId": 1}'
```

### Listar Contactos
```bash
curl http://localhost:8080/api/contactos
```

---

## 🔧 Resolução de Problemas

### Porta 8080 ocupada?
```bash
# Encontrar processo usando a porta
lsof -i :8080
# Matar o processo
kill -9 <PID>
```

### Erro de compilação?
```bash
# Limpar e recompilar
mvn clean compile
```

### Swagger não funciona?
1. Verifique se a aplicação iniciou completamente
2. Aguarde 30 segundos após o startup
3. Aceda: http://localhost:8080/v3/api-docs

---

## 📍 Links Úteis

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Docs**: http://localhost:8080/v3/api-docs
- **Base de Dados H2**: http://localhost:8080/h2-console
- **Health Check**: http://localhost:8080/actuator/health

---

## 🛑 Para Parar a Aplicação

Pressione `Ctrl + C` no terminal onde a aplicação está a correr.

---

# Projeto Drogaria

Projeto com backend Spring Boot, frontend React e script SQL para criar o banco.

---

## Rodar backend (Spring Boot)

1. Configure seu banco de dados (MySQL, MariaDB, etc) e rode o script `database/schema.sql`.
2. No backend/src/main/resouces/application.properties  modifique a senha para sua senha de acesso do banco de dados
3. No terminal, vá para a pasta backend:

```bash
cd backend
./mvnw spring-boot:run



cd frontend
npm install
npm start

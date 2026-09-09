# ChefBot 🤖🍝

Assistente gastronômico baseado em regras, desenvolvido em Java.

O ChefBot utiliza processamento de linguagem baseado em palavras-chave, normalização de mensagens, regras compostas, controle de estado da conversa e fallback para interagir com o usuário e gerar recomendações gastronômicas personalizadas.

## 🎯 Objetivo

O projeto foi desenvolvido como atividade acadêmica para demonstrar a implementação de um chatbot baseado em regras.

Além das interações tradicionais de um restaurante, o ChefBot possui um fluxo personalizado que coleta informações do usuário e utiliza essas informações para recomendar uma receita.

## ✨ Funcionalidades

- Saudação e encerramento da conversa
- Consulta de horário de funcionamento
- Consulta de cardápio
- Simulação de reserva
- Consulta de localização
- Opções vegetarianas
- Consulta de sobremesas
- Consulta de bebidas
- Tratamento de alergias e restrições alimentares
- Recomendações gastronômicas personalizadas
- Normalização das mensagens
- Regras compostas com múltiplas condições
- Controle de estado da conversa
- Fallback para mensagens não reconhecidas
- Geração de PDF personalizado
- Envio automático da receita por e-mail com PDF anexado

## 🧠 Funcionamento

O ChefBot utiliza regras condicionais baseadas em palavras-chave.

As mensagens recebidas são normalizadas antes da análise, permitindo que variações de maiúsculas, minúsculas e acentuação sejam interpretadas.

O chatbot também possui estados de conversa para conduzir o usuário pelo fluxo de personalização da receita.

### 🔎 Regras baseadas em palavras-chave

O ChefBot identifica intenções a partir de palavras-chave e expressões presentes nas mensagens do usuário.

Entre as regras implementadas estão:

- Saudação
- Horários de funcionamento
- Cardápio
- Reservas
- Localização
- Opções vegetarianas
- Sobremesas
- Bebidas
- Recomendações de receitas
- Tratamento de alergias e restrições alimentares
- Fallback para mensagens não reconhecidas

O sistema também possui regras compostas, nas quais mais de uma condição é analisada para definir a recomendação.

### 🧩 Exemplo de regra composta

```text
Comida favorita: massa
País de interesse: Itália

Resultado:
Penne Italiano ao Molho de Tomate
```

### 🔄 Fluxo personalizado

1. Nome
2. Idade
3. Comida favorita
4. Alergia ou restrição alimentar
5. País que gostaria de conhecer
6. Recomendação de receita
7. Geração do PDF
8. Envio do PDF por e-mail

### 🧩 Controle de estado

Durante o fluxo de personalização, o chatbot mantém o estado da conversa para saber qual informação deve ser solicitada ao usuário em cada etapa.

O fluxo utiliza estados para controlar a coleta das informações do perfil gastronômico.

## 📄 Geração do PDF

Após a recomendação da receita, o ChefBot utiliza o Apache PDFBox para gerar uma ficha gastronômica personalizada.

O documento contém:

- Dados do usuário
- Preferência gastronômica
- Restrição alimentar
- País de interesse
- Receita recomendada
- Justificativa da recomendação
- Ingredientes
- Modo de preparo

Os PDFs gerados durante a execução são armazenados na pasta `receitas/`.

## 📧 Envio automático por e-mail

O ChefBot pode enviar automaticamente a receita personalizada por e-mail, utilizando SMTP do Gmail.

O PDF gerado é anexado à mensagem.

As credenciais não ficam armazenadas no código-fonte. O sistema utiliza variáveis de ambiente:

```text
CHEFBOT_EMAIL
CHEFBOT_EMAIL_PASSWORD
CHEFBOT_DESTINO
```

### Configuração

No PowerShell:

```powershell
$env:CHEFBOT_EMAIL="seuemail@gmail.com"
$env:CHEFBOT_EMAIL_PASSWORD="sua_senha_de_app"
$env:CHEFBOT_DESTINO="destinatario@gmail.com"
```

A senha utilizada deve ser uma senha de aplicativo do Google.

> Nunca coloque sua senha de aplicativo diretamente no código-fonte ou no GitHub.

## ▶️ Como executar

### 1. Compilar

Abra o terminal na pasta raiz do projeto e execute:

```powershell
javac -cp "lib\*" -d out src\com\chefbot\*.java
```

### 2. Configurar UTF-8 no Windows

```powershell
chcp 65001
```

### 3. Executar

```powershell
java -cp "out;lib\*" com.chefbot.Main
```

## 🧪 Testes

Foram realizados cinco testes funcionais:

| Teste | Funcionalidade |
|---|---|
| 01 | Saudação |
| 02 | Cardápio |
| 03 | Regra composta |
| 04 | Receita personalizada, geração de PDF e envio por e-mail |
| 05 | Fallback |

As evidências das execuções estão disponíveis na pasta `testes/`.

O relatório completo dos testes está disponível em:

```text
testes/testes.pdf
```

A pasta também contém os prints utilizados como evidência das execuções.

## 🛠️ Tecnologias

- Java 25 LTS
- Apache PDFBox 3.0.6
- Jakarta Mail
- Jakarta Activation
- Gmail SMTP
- Git
- GitHub
- Visual Studio Code

## 📁 Estrutura do projeto

```text
chefbot-rule-based/
│
├── .vscode/
│   └── settings.json
│
├── lib/
│   ├── pdfbox-app-3.0.6.jar
│   ├── jakarta.mail-2.0.4.jar
│   ├── jakarta.activation-api-2.1.3.jar
│   └── angus-activation-2.0.2.jar
│
├── receitas/
│
├── src/
│   └── com/
│       └── chefbot/
│           ├── Chatbot.java
│           ├── EmailService.java
│           ├── EstadoConversa.java
│           ├── Main.java
│           ├── Normalizador.java
│           ├── Receita.java
│           ├── ReceitaPdfService.java
│           ├── ReceitaService.java
│           ├── TesteNormalizador.java
│           └── Usuario.java
│
├── testes/
│   ├── 01-saudacao.png
│   ├── 02-cardapio.png
│   ├── 03-regra-composta.png
│   ├── 04-email-anexo.png
│   ├── 04-receita-pdf-email.png
│   ├── 05-fallback.png
│   └── testes.pdf
│
├── .gitignore
└── README.md
```

## 🔐 Segurança

O projeto não armazena senhas ou credenciais de e-mail no código-fonte.

As informações de autenticação são obtidas por meio de variáveis de ambiente.

Arquivos de configuração e credenciais locais são protegidos pelo `.gitignore`.

## 📌 Observações

O ChefBot foi desenvolvido com uma arquitetura simples e orientada a regras, adequada ao objetivo acadêmico do projeto.

A lógica de decisão é implementada de forma explícita, permitindo visualizar as condições utilizadas pelo chatbot e o resultado produzido por cada regra.

---

**ChefBot — Assistente Gastronômico** 🍝🤖
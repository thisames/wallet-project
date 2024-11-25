# Bitcoin Cold Wallet

## Projeto

Este projeto consiste no desenvolvimento de uma **carteira de Bitcoin fria** para dispositivos
Android, implementada em **Java**. A aplicação tem como objetivo fornecer funcionalidades básicas e
avançadas para o gerenciamento de Bitcoins de forma segura e prática, sem a necessidade de uma
conexão constante com a internet. Para a interação com a rede Bitcoin, utilizamos a biblioteca *
*bitcoinj**, que se conecta diretamente à rede Bitcoin por meio do protocolo P2P, dispensando a
dependência de um nó completo como o Bitcoin Core.

---

## Funcionalidades

### Funcionalidades principais:

- **Criação e gerenciamento de carteiras**
    - Geração de chaves privadas e públicas.
    - Armazenamento seguro das sementes (mnemonics) e possibilidade de exportação.

- **Exibição de saldo**
    - Consulta do saldo disponível na carteira com base no blockchain utilizando a integração com o
      bitcoinj.

- **Envio e recebimento de fundos**
    - Criação de transações Bitcoin.
    - Geração de QR codes para recebimento.
    - Digitalização de QR codes para envio de fundos.

- **Acompanhamento de preço**
    - Atualização em tempo real do valor do Bitcoin em diferentes moedas fiduciárias (ex.: USD, EUR,
      BRL).

- **Alertas de preço**
    - Configuração de notificações para variações no preço do Bitcoin.

- **Tela de configurações**
    - Personalização de opções da carteira.
    - Configuração manual de peers da rede Bitcoin para personalizar a sincronização.

### Funcionalidade extra:

- **Integração com Lightning Network**
    - Envio de transações via Lightning para redução de taxas e maior velocidade.
    - Configuração de nós Lightning diretamente na aplicação.

---

## Segurança

A aplicação prioriza a segurança e a experiência do usuário:

- As **chaves privadas** e **sementes** são armazenadas localmente, protegidas contra acessos não
  autorizados.
- A **biblioteca bitcoinj** permite interagir diretamente com a rede Bitcoin, garantindo
  sincronização eficiente e a criação de transações válidas.

---

## Tecnologias Utilizadas

- **Linguagem**: Java
- **Biblioteca Bitcoin**: [bitcoinj](https://github.com/bitcoinj/bitcoinj)
- **Plataforma**: Android

---

## Análise de Código Estático

utilizamos as seguintes ferramentas de análise estática de codigo:

- **Spotless**:
- **SonarLint**:
- **Android Lint**:

# Bitcoin Cold Wallet

## Project

This project involves the development of a **Bitcoin cold wallet** for Android devices, implemented
in **Java**. The application aims to provide basic and advanced features for secure and practical
Bitcoin management without requiring a constant internet connection. For interaction with the
Bitcoin network, we use the **bitcoinj** library, which connects directly to the Bitcoin network via
the P2P protocol, eliminating the need for a full node like Bitcoin Core.

---

## Features

### Main Features:

- **Wallet creation and management**
    - Generation of private and public keys.
    - Secure storage of mnemonics (seed phrases) with an export option.

- **Balance display**
    - Checking the available wallet balance based on the blockchain using bitcoinj integration.

- **Sending and receiving funds**
    - Creating Bitcoin transactions.
    - Generating QR codes for receiving funds.
    - Scanning QR codes to send funds.

- **Price tracking**
    - Real-time updates on Bitcoin prices in various fiat currencies (e.g., USD, EUR, BRL).

- **Price alerts**
    - Configuring notifications for Bitcoin price changes.

- **Settings screen**
    - Customizing wallet options.
    - Manually configuring Bitcoin network peers to personalize synchronization.

### Extra Feature:

- **Lightning Network Integration**
    - Sending transactions via Lightning to reduce fees and increase speed.
    - Configuring Lightning nodes directly within the application.

---

## Security

The application prioritizes both security and user experience:

- **Private keys** and **mnemonics** are stored locally, protected against unauthorized access.
- The **bitcoinj library** enables direct interaction with the Bitcoin network, ensuring efficient
  synchronization and valid transaction creation.

---

## Technologies Used

- **Language**: Java
- **Bitcoin Library**: [bitcoinj](https://github.com/bitcoinj/bitcoinj)
- **Platform**: Android

---

## Static Code Analysis

We use the following static code analysis tools:

- **Spotless**
- **SonarLint**
- **Android Lint**
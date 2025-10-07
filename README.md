# Sokoban em Java

## 🧩 Descrição

Este é um projeto em Java do clássico jogo **Sokoban**, desenvolvido para a unidade curricular de **Programação Orientada para Objetos**.  
O objetivo do jogo é mover caixas (ou paletes) para os locais de destino dentro de um armazém, utilizando a empilhadora do jogador.

Funcionalidades incluídas:  
- Níveis pré-definidos (`levels/`)  
- Interface gráfica simples  
- Movimentação da empilhadora e interação com caixas/paletes  
- Sistema de energia e interações com elementos especiais (Teletransporte, Status Messages, etc.)  
- Registo das melhores pontuações (Top 3) por nível  

---

## ⚠️ Dependência

Este projeto depende da biblioteca **[GraphPack_2023_2024_V1_0](https://github.com/pmcvz1/GraphPack_2023_2024_V1_0)**.  

Para executar corretamente:  
1. Clone ambos os repositórios:
```bash
git clone https://github.com/pmcvz1/GraphPack_2023_2024_V1_0.git
git clone https://github.com/pmcvz1/sokoban-game.git
```
2. Abra o Eclipse (ou outra IDE compatível)

3. Importe ambos os projetos:  
- File → Import → Existing Projects into Workspace
- Selecione GraphPack_2023_2024_V1_0 e Sokoban

4. Configure a dependência do Sokoban:
- Clique com o botão direito em Sokoban → Properties → Java Build Path → Projects → Add…
- Selecione GraphPack_2023_2024_V1_0

---

## ▶️ Como compilar e executar

1. Abra o projeto no Eclipse (com GraphPack configurado)

2. Localize a classe principal Main.java

3. Clique com o botão direito → Run As → Java Application

4. Jogue o Sokoban!

---

## 🛠 Funcionalidades implementadas
- Display de abertura com pedido de nome do jogador e validação

- Movimentação da empilhadora e interação com elementos do jogo
  
- Sistema de energia da empilhadora (empurrar consome energia)

- Teletransporte com comportamento personalizado

- Status Message na GUI indicando habilidades da empilhadora

- Classes abstratas (GameElement, PushableElement) para evitar repetição de código

- Atualização de top 3 prestações utilizando Comparable e Collections

- Validação de ficheiros de configuração de níveis

---

## 👥 Autores
Pedro Vaz – Nº111322 – idealização, codificação, testes

Rodrigo Diogo – Nº111516 – idealização, codificação, testes

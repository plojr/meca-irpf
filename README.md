# MeCa-IRPF-Cadastro

Status: em andamento.

## Resumo
Este módulo serve para fazer o cadastro das ordens, notas de corretagem, tickers etc para dar suporte aos outros módulos que o utilizam. Ele faz parte de uma série de módulos que auxiliam o investidor na hora de investir em ações na B3.

## Objetivo
O intuito deste módulo é permitir o que o investidor faça o cadastro de tudo que for necessário para, com o auxílio de outros módulos, ter os dados necessários para fazer a declaração no programa de imposto de renda da Receita Federal.

## Como devo lançar as notas de corretagem?
A minha sugestão é que você lance, preferencialmente, todas as suas notas de corretagem. Existe um tipo de caso onde isso não é preciso. É quando você está com sua posição de ações totalmente zerada, isto é, sem ter nenhuma ação em sua carteira. Neste caso, você pode começar a lançar as ações que comprar a partir de então, desde que você não queira saber dos dados de anos anteriores. Porém, se esse não for o seu caso, o custo das compras feitas de uma determinada ação no passado pode influenciar, ou não, no custo atual. Não influenciaria no custo atual dadas algumas condições bem específicas.
Com isso em mente, eu sugiro lançar todas as notas. Eu sei que é trabalhoso, mas é o cenário ideal para evitar cálculos errados.

## Detalhes técnicos
Neste projeto, estou utilizando o lombok. Por isso, você precisará instalá-lo no programa - Eclipse, Spring Tool Suite etc - que você utilizará para codificar neste projeto. Uma busca na internet por "instalar lombok no eclipse" resolverá facilmente esta tarefa.

Este projeto foi criado no Spring Tool Suite 4. O banco de dados que utilizarei no projeto será o H2, simplesmente para teste.
<jsp:include page="header.jsp" />
<title>Home</title>
</head>
<body>
<div class="container-fluid">
	<div class="row flex-nowrap">
		<jsp:include page="sidebar.jsp" />
		<div class="col py-3">
		<h2>MeCa-IRPF</h2>
<h3>Resumo</h3>
<p>Este projeto é para aqueles que investem na Bolsa de Valores no Brasil e precisam de dados para declarar no software da Receita Federal. Ele é um dos módulos de um projeto pessoal chamado MeCa.
</p>
<h3>Disclaimer</h3>
<p>Não usem esta aplicação como verdade absoluta. Eu estudei como calcular cada dado, porém erros podem acontecer. Por isso, sugiro que usem esta aplicação só para comparar com os seus cálculos. Não me responsabilizo por informações erradas que esta aplicação possa apresentar, já que ela estará em constante evolução. Caso encontre alguma diferença discrepante com seus próprios cálculos, e se estiver disposto/disposta a encontrar qual parte do código está com problema, sinta-se à vontade para corrigi-lo.
</p>
<p>Eu nunca fiz um cálculo manual para lançar estes dados no programa da Receita Federal e utilizo o algoritmo desta aplicação há mais de 5 anos. Nunca tive problemas com os dados declarados para a Receita Federal. Espero continuar assim. :-)
</p>
<h3>Objetivo</h3>
<p>O intuito desta aplicação é fornecer os dados que você precisa lançar no programa da Receita Federal, em "Bens e Direitos" e também na parte de "Renda Variável". Em "Bens e Direitos", serão lançados seus ativos, o CNPJ da empresa e a situação no dia 31/12. Em "Renda Variável", será possível lançar o lucro acumulado para as vendas em até 20.000,00, os prejuízos de cada mês, o lucro mensal - e também o imposto de renda que deverá pagar em cima dele - para vendas que ultrapassarem os 20.000,00 e também os lucros e prejuízos que obtiver nos day trade.
</p>
<p>Esta aplicação não contempla o lançamento dos dividendos e jscp. Isso é feito em outro módulo que codifiquei, o MeCa Dividendos.
</p>
<p>Esta aplicação não tem uma funcionalidade de converter os PDF das notas de corretagens para o banco de dados. Porém é meu intuito criar uma classe que converta o PDF da nota de corretagem, pelo menos no formato SINACOR, para os dados a serem inseridos no banco de dados.
</p>
<p>Esta aplicação contempla as bonificações, cisões, fusões e grupamentos. Eles devem e são levados em consideração nos cálculos.
</p>
<h3>Como devo lançar as notas de corretagem?</h3>
<p>A minha sugestão é que você lance, preferencialmente, todas as suas notas de corretagem. Existe um tipo de caso onde isso não é preciso. É quando você está com sua posição de ações totalmente zerada, isto é, sem ter nenhuma ação em sua carteira. Neste caso, você pode começar a lançar as ações que comprar a partir de então, desde que você não queira saber dos dados de anos anteriores. Porém, se esse não for o seu caso, o custo das compras feitas de uma determinada ação no passado pode influenciar, ou não, no custo atual. Não influenciaria no custo atual dadas algumas condições bem específicas.
Com isso em mente, eu sugiro lançar todas as notas. Eu sei que é trabalhoso, mas é o cenário ideal para evitar cálculos errados.
</p>
<h3>Instruções</h3>
<p>Neste projeto, estou utilizando o lombok. Por isso, você precisará instalá-lo no programa - Eclipse, Spring Tool Suite etc - que você utilizará para codificar neste projeto.
</p>
<h3>Detalhes técnicos</h3>
<p>Este projeto foi criado no Spring Tool Suite 4. O banco de dados que utilizarei no projeto será o H2, simplesmente para teste.</p>
<button class="btn" onclick="document.body.scrollTop = 0; document.documentElement.scrollTop = 0;">Ir para o topo</button>
		</div>
	</div>
</div>
</body>
</html>
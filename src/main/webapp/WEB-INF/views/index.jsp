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
<p>Este projeto � para aqueles que investem na Bolsa de Valores no Brasil e precisam de dados para declarar no software da Receita Federal. Ele � um dos m�dulos de um projeto pessoal chamado MeCa.
</p>
<h3>Disclaimer</h3>
<p>N�o usem esta aplica��o como verdade absoluta. Eu estudei como calcular cada dado, por�m erros podem acontecer. Por isso, sugiro que usem esta aplica��o s� para comparar com os seus c�lculos. N�o me responsabilizo por informa��es erradas que esta aplica��o possa apresentar, j� que ela estar� em constante evolu��o. Caso encontre alguma diferen�a discrepante com seus pr�prios c�lculos, e se estiver disposto/disposta a encontrar qual parte do c�digo est� com problema, sinta-se � vontade para corrigi-lo.
</p>
<p>Eu nunca fiz um c�lculo manual para lan�ar estes dados no programa da Receita Federal e utilizo o algoritmo desta aplica��o h� mais de 5 anos. Nunca tive problemas com os dados declarados para a Receita Federal. Espero continuar assim. :-)
</p>
<h3>Objetivo</h3>
<p>O intuito desta aplica��o � fornecer os dados que voc� precisa lan�ar no programa da Receita Federal, em "Bens e Direitos" e tamb�m na parte de "Renda Vari�vel". Em "Bens e Direitos", ser�o lan�ados seus ativos, o CNPJ da empresa e a situa��o no dia 31/12. Em "Renda Vari�vel", ser� poss�vel lan�ar o lucro acumulado para as vendas em at� 20.000,00, os preju�zos de cada m�s, o lucro mensal - e tamb�m o imposto de renda que dever� pagar em cima dele - para vendas que ultrapassarem os 20.000,00 e tamb�m os lucros e preju�zos que obtiver nos day trade.
</p>
<p>Esta aplica��o n�o contempla o lan�amento dos dividendos e jscp. Isso � feito em outro m�dulo que codifiquei, o MeCa Dividendos.
</p>
<p>Esta aplica��o n�o tem uma funcionalidade de converter os PDF das notas de corretagens para o banco de dados. Por�m � meu intuito criar uma classe que converta o PDF da nota de corretagem, pelo menos no formato SINACOR, para os dados a serem inseridos no banco de dados.
</p>
<p>Esta aplica��o contempla as bonifica��es, cis�es, fus�es e grupamentos. Eles devem e s�o levados em considera��o nos c�lculos.
</p>
<h3>Como devo lan�ar as notas de corretagem?</h3>
<p>A minha sugest�o � que voc� lance, preferencialmente, todas as suas notas de corretagem. Existe um tipo de caso onde isso n�o � preciso. � quando voc� est� com sua posi��o de a��es totalmente zerada, isto �, sem ter nenhuma a��o em sua carteira. Neste caso, voc� pode come�ar a lan�ar as a��es que comprar a partir de ent�o, desde que voc� n�o queira saber dos dados de anos anteriores. Por�m, se esse n�o for o seu caso, o custo das compras feitas de uma determinada a��o no passado pode influenciar, ou n�o, no custo atual. N�o influenciaria no custo atual dadas algumas condi��es bem espec�ficas.
Com isso em mente, eu sugiro lan�ar todas as notas. Eu sei que � trabalhoso, mas � o cen�rio ideal para evitar c�lculos errados.
</p>
<h3>Instru��es</h3>
<p>Neste projeto, estou utilizando o lombok. Por isso, voc� precisar� instal�-lo no programa - Eclipse, Spring Tool Suite etc - que voc� utilizar� para codificar neste projeto.
</p>
<h3>Detalhes t�cnicos</h3>
<p>Este projeto foi criado no Spring Tool Suite 4. O banco de dados que utilizarei no projeto ser� o H2, simplesmente para teste.</p>
<button class="btn" onclick="document.body.scrollTop = 0; document.documentElement.scrollTop = 0;">Ir para o topo</button>
		</div>
	</div>
</div>
</body>
</html>
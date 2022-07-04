<jsp:include page="header.jsp" />
<title>Home</title>
</head>
<body>
<div class="container-fluid">
	<div class="row flex-nowrap">
		<jsp:include page="sidebar.jsp" />
		<div class="col py-3">
		<h2>MeCa-IRPF-Cadastro</h2>
		<h3>Resumo</h3>
		<p>
		Este módulo é responsável por cadastrar as notas de corretagens, ordens e eventos corporativos necessários para outros módulos.
		</p>
		<h3>Menu</h3>
		<p>
		O menu "Básico" é onde serão cadastradas os tickers, notas de corretagem e as ordens.
		</p>
		<p>
		O menu "Eventos" é onde serão cadastrados os eventos corporativos, tais como, bonificação, grupamento, desdobramento, cisão e fusão.
		</p>
		<h3>Objetivo</h3>
		<p>
		O intuito deste módulo é permitir que o investidor cadastre as notas de corretagens, ordens e eventos corporativos que serão necessários para outros módulos.
		</p>
		<p>
		Os outros módulos são independentes entre si, porém todos dependem deste módulo por causa dos dados que ele fornece.
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
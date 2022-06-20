<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="header.jsp" />
<style>
th,td {
	text-align: center
}
</style>
<title>Dados para a Receita Federal</title>
</head>
<body>
<div class="container-fluid">
	<div class="row flex-nowrap">
		<jsp:include page="sidebar.jsp" />
		<div class="col py-3">
		<h3>Disclaimer</h3>
		<p>O local onde os dados serão lançados podem sofrer modificações. Estes menus, opções, grupos etc têm, como referência, o ano de 2022.
		Portanto, é necessário sempre verificar se o local continua o mesmo.</p>
		<h3>Instruções</h3>
		<p>Os dados da tabela 1 serão lançados em "Bens e Direitos". 
		Ao clicar em "Novo", você terá que preencher, <span style="font-weight: bold">para cada ticker</span>, o grupo, que é o 03 - "Participações societárias" e 
		o código, que é 01 - "Ações (inclusive as listadas em bolsa)".</p>
		<p>Você também precisará preencher o CNPJ e o custo total que pagou por estas ações, que será lançado em "Situação 31/12". O ano é o ano calendário, isto é, se você está lançando os dados em 2022, então o ano calendário é 2021.</p>
		<p>No campo "discriminação", não existe uma regra do texto a ser escrito. Uma sugestão é colocar o ticker e a quantidade de ações que você tem em cada corretora.</p>
			<table class="table table-bordered">
				<caption style="caption-side:bottom; text-align:center">Tabela 1</caption>
				<tr>
					<th>Ticker</th>
					<th>CNPJ</th>
					<th>Quantidade</th>
					<th>Custo total</th>
				</tr>
				<c:forEach items="${carteira}" var="itemCarteira">
				<tr>
					<td><c:out value="${itemCarteira.ticker}"></c:out></td>
					<td><c:out value="${itemCarteira.cnpj}"></c:out></td>
					<td><c:out value="${itemCarteira.quantidade}"></c:out></td>
					<td><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${itemCarteira.custoTotal}" /></td>
				</tr>
				</c:forEach>
			</table>
		<p>Os dados da tabela 2 são para lançar na parte de "day trade". No menu lateral, vá em "Renda Variável" -> "Operações comuns/Day trade".</p>
		<p>Irá aparecer a tela com os meses relativos ao ano calendário. A tabela 2 mostra todos os anos e não somente o ano calendário. Por isso,
		é necessário pegar somente os dados do ano calendário. Para cada mês na tabela 2, o valor do lucro ou prejuízo deve ser colocado no 
		mês correspondente no software da Receita Federal. Caso você tenha prejuízo acumulado relativo ao ano anterior, a Receita Federal não 
		permite que você acumule para o próximo ano. Portanto, o prejuízo acumulado em janeiro é zero.</p>
		<p>Última observação: o imposto deve ser pago até o último dia útil do mês subsequente. Portanto, lance as notas de corretagem assim 
		que for possível.</p>
		<table class="table table-bordered">
			<caption style="caption-side:bottom; text-align:center">Tabela 2</caption>
			<tr>
				<th>Ano</th>
				<th>Mês</th>
				<th>Lucro/Prejuízo</th>
				<th>Imposto a ser pago</th>
				<th>Prejuízo acumulado</th>
			</tr>
			<c:forEach items="${dayTrade}" var="dt">
				<tr>
					<td><c:out value="${dt.ano}"></c:out></td>
					<td><c:out value="${dt.mes}"></c:out></td>
					<td><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${dt.lucro}" /></td>
					<td><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${dt.imposto}" /></td>
					<td><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${dt.prejuizoAcumulado}" /></td>
				</tr>
			</c:forEach>
		</table>
		<p>Tabela 3</p>
		<table class="table table-bordered">
			<caption style="caption-side:bottom; text-align:center">Tabela 3</caption>
			<tr>
				<th>Ano</th>
				<th>Mês</th>
				<th>Lucro/Prejuízo</th>
				<th>Imposto a ser pago</th>
				<th>Prejuízo acumulado</th>
				<th>Venda mensal</th>
			</tr>
			<c:forEach items="${swingTrade}" var="st">
				<tr>
					<td><c:out value="${st.ano}"></c:out></td>
					<td><c:out value="${st.mes}"></c:out></td>
					<td>
						<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${st.lucro}" />
					</td>
					<td>
						<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${st.imposto}" />
					</td>
					<td>
						<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${st.prejuizoAcumulado}" />
					</td>
					<td>
						<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${st.venda}" />
					</td>
				</tr>
			</c:forEach>
		</table>
		
		<button class="btn" onclick="document.body.scrollTop = 0; document.documentElement.scrollTop = 0;">Ir para o topo</button>
		</div>
	</div>
</div>
</body>
</html>
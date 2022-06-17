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
		<p>Os dados da tabela 1 serão lançados em "Bens e Direitos". 
		Ao clicar em "Novo", você terá que preencher o grupo, que é o 03 - "Participações societárias" e 
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
		<p>Os dados da tabela 2 são para lançar na parte de "day trade".</p>
		<table class="table table-bordered">
			<caption style="caption-side:bottom; text-align:center">Tabela 2</caption>
			<tr>
				<th>Ano</th>
				<th>Mês</th>
				<th>Lucro/Prejuízo</th>
				<th>Imposto a ser pago</th>
			</tr>
			<c:forEach items="${dayTrade}" var="dt">
				<tr>
					<td><c:out value="${dt.ano}"></c:out></td>
					<td><c:out value="${dt.mes}"></c:out></td>
					<td><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${dt.lucro}" /></td>
					<td><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${dt.imposto}" /></td>
				</tr>
			</c:forEach>
		</table>
		<div>Prejuízo acumulado até o momento: R$ <fmt:formatNumber minFractionDigits="2" maxFractionDigits="2" value="${prejuizoAcumulado}" />.</div>
		</div>
	</div>
</div>
</body>
</html>
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
		<p>O local onde os dados ser�o lan�ados podem sofrer modifica��es. Estes menus, op��es, grupos etc t�m, como refer�ncia, o ano de 2022.
		Portanto, � necess�rio sempre verificar se o local continua o mesmo.</p>
		<h3>Instru��es</h3>
		<p>Os dados da tabela 1 ser�o lan�ados em "Bens e Direitos". 
		Ao clicar em "Novo", voc� ter� que preencher, <span style="font-weight: bold">para cada ticker</span>, o grupo, que � o 03 - "Participa��es societ�rias" e 
		o c�digo, que � 01 - "A��es (inclusive as listadas em bolsa)".</p>
		<p>Voc� tamb�m precisar� preencher o CNPJ e o custo total que pagou por estas a��es, que ser� lan�ado em "Situa��o 31/12". O ano � o ano calend�rio, isto �, se voc� est� lan�ando os dados em 2022, ent�o o ano calend�rio � 2021.</p>
		<p>No campo "discrimina��o", n�o existe uma regra do texto a ser escrito. Uma sugest�o � colocar o ticker e a quantidade de a��es que voc� tem em cada corretora.</p>
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
		<p>Os dados da tabela 2 s�o para lan�ar na parte de "day trade". No menu lateral, v� em "Renda Vari�vel" -> "Opera��es comuns/Day trade".</p>
		<p>Ir� aparecer a tela com os meses relativos ao ano calend�rio. A tabela 2 mostra todos os anos e n�o somente o ano calend�rio. Por isso,
		� necess�rio pegar somente os dados do ano calend�rio. Para cada m�s na tabela 2, o valor do lucro ou preju�zo deve ser colocado no 
		m�s correspondente no software da Receita Federal. Caso voc� tenha preju�zo acumulado relativo ao ano anterior, a Receita Federal n�o 
		permite que voc� acumule para o pr�ximo ano. Portanto, o preju�zo acumulado em janeiro � zero.</p>
		<p>�ltima observa��o: o imposto deve ser pago at� o �ltimo dia �til do m�s subsequente. Portanto, lance as notas de corretagem assim 
		que for poss�vel.</p>
		<table class="table table-bordered">
			<caption style="caption-side:bottom; text-align:center">Tabela 2</caption>
			<tr>
				<th>Ano</th>
				<th>M�s</th>
				<th>Lucro/Preju�zo</th>
				<th>Imposto a ser pago</th>
				<th>Preju�zo acumulado</th>
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
				<th>M�s</th>
				<th>Lucro/Preju�zo</th>
				<th>Imposto a ser pago</th>
				<th>Preju�zo acumulado</th>
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
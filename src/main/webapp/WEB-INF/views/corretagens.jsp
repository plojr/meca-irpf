<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="header.jsp" />
<style>
th,td {
	text-align: center
}
</style>
<title>Notas de corretagem</title>
</head>
<body>
<div class="container-fluid">
	<div class="row flex-nowrap">
		<jsp:include page="sidebar.jsp" />
		<div class="col py-3">
		<c:if test="${empty corretagens}">Não há dados.</c:if>
		<c:forEach items="${corretagens}" var="corretagem">
			<span>Nota de Corretagem</span>
			<div><label>Data:</label> <span><c:out value="${corretagem.date}"></c:out></span></div>
			<div><label>Valor líquido:</label> <span><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" 
						value="${corretagem.valorLiquido}" /></span></div>
			<span>Ordens</span>
			<table class="table table-bordered">
			<tr>
				<th>Tipo</th>
				<th>Quantidade</th>
				<th>Ticker</th>
				<th>Preço</th>
			</tr>
			<c:forEach items="${corretagem.ordens}" var="ordem">
			<tr>
				<c:if test="${ordem.tipo eq 'c'.charAt(0)}">
					<td><c:out value="Compra"></c:out></td>
				</c:if>
				<c:if test="${ordem.tipo eq 'v'.charAt(0)}">
					<td><c:out value="Venda"></c:out></td>
				</c:if>
				<td><c:out value="${ordem.quantidade}"></c:out></td>
				<td><c:out value="${ordem.ticker.codigo}"></c:out></td>
				<!-- td><c:out value="${ordem.preco}"></c:out></td-->
				<td><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${ordem.preco}" /></td>
			</tr>
			</c:forEach>
			</table>
			<br />
		</c:forEach>
		<a href="adicionar_corretagem" class="btn btn-primary">Adicionar Corretagem</a>
		<button class="btn" onclick="document.body.scrollTop = 0; document.documentElement.scrollTop = 0;">Ir para o topo</button>
		</div>
	</div>
</div>
</body>
</html>
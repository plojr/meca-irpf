<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="header.jsp" />
<style>
td {
	text-align: center
}
</style>
<title>Teste</title>
</head>
<body>
<div class="container-fluid">
	<div class="row flex-nowrap">
		<jsp:include page="sidebar.jsp" />
		<div class="col py-3">
			<h2>Nota de Corretagem</h2>
			<div><label>Data:</label> <span><c:out value="${notaDeCorretagem.date}"></c:out></span></div>
			<div><label>Valor líquido:</label> <span><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${notaDeCorretagem.valorLiquido}" /></span></div>
			<h3>Ordens</h3>
			<table border="1">
			<tr>
				<th>Tipo</th>
				<th>Quantidade</th>
				<th>Ticker</th>
				<th>Preço</th>
			</tr>
			<c:forEach items="${ordens}" var="ordem">
			<tr>
				<td><c:out value="${ordem.tipo}"></c:out></td>
				<td><c:out value="${ordem.quantidade}"></c:out></td>
				<td><c:out value="${ordem.ticker.codigo}"></c:out></td>
				<!-- td><c:out value="${ordem.preco}"></c:out></td-->
				<td><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${ordem.preco}" /></td>
			</tr>
			</c:forEach>
			</table>
		</div>
	</div>
</div>
</body>
</html>
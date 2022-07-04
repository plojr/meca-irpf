<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="header.jsp" />
<title>Adicionar ticker</title>
<style>
th, td {
	text-align: center
}
</style>
</head>
<body>
<div class="container-fluid">
	<div class="row flex-nowrap">
		<jsp:include page="sidebar.jsp" />
		<div class="col py-3">
			<form action="adicionar_ticker" method="post">
				<div class="form-group">
					<label for="codigo_id">Código</label>
					<input type="text" id="codigo_id" name="codigo" class="form-control" placeholder="Digite o código" required />
				</div>
				<div class="form-group">
					<label for="cnpj_id">CNPJ</label>
					<input type="text" name="cnpj" id="cnpj_id" class="form-control" placeholder="Digite o CNPJ">
				</div>
				<div class="form-group">
					<label for="bdr_id">BDR?</label>
					<input type=checkbox id="bdr_id" name="bdr" />
				</div>
				<br />
				<button type="submit" class="btn btn-primary btn-sm">Salvar</button>
			</form>
			<br />
			<h3>Tickers existentes</h3>
			<table class="table table-bordered">
				<tr>
					<th>Código</th>
					<th>CNPJ</th>
					<th>BDR?</th>
				<tr>
				<c:forEach items="${tickers}" var="ticker">
					<tr>
						<td><c:out value="${ticker.codigo}"></c:out></td>
						<td><c:out value="${ticker.cnpj}"></c:out></td>
						<td>
							<c:choose>
								<c:when test="${ticker.bdr}">Sim</c:when>
								<c:otherwise>Não</c:otherwise>
							</c:choose>
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
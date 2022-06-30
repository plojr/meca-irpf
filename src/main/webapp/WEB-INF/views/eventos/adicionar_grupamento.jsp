<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="../header.jsp" />
<title>Adicionar Grupamento</title>
<style>
th, td {
	text-align: center
}
</style>
</head>
<body>
<div class="container-fluid">
	<div class="row flex-nowrap">
		<jsp:include page="../sidebar.jsp" />
		<div class="col py-3">
			<form action="adicionar_grupamento" method="post">
				<div class="form-group">
					<label for="codigo_id">Código</label>
					<input type="text" id="codigo_id" name="codigo" class="form-control" placeholder="Digite o código" required />
				</div>
				<div class="form-group">
					<label for="data_id">Data do grupamento</label>
					<input type="date" id="data_id" name="data" class="form-control" required>
				</div>
				<div class="form-group">
					<label for="proporcao_id">Proporção</label>
					<input type="number" min="0.01" step="0.01" id="proporcao_id" name="proporcao" class="form-control" required
						   placeholder="Digite a proporção do grupamento Exemplo: se o grupamento for de 10:1, então digite 10" />
				</div>
				<br />
				<button type="submit" class="btn btn-primary btn-sm">Salvar</button>
			</form>
			<br />
			<h3>Grupamentos existentes</h3>
			<table class="table table-bordered">
				<tr>
					<th>Código</th>
					<th>Data do grupamento</th>
					<th>Proporção</th>
				<tr>
				<c:forEach items="${grupamentos}" var="grupamento">
					<tr>
						<td><c:out value="${grupamento.ticker1.codigo}"></c:out></td>
						<td><c:out value="${grupamento.dataEvento}"></c:out></td>
						<td><c:out value="${grupamento.proporcao}"></c:out></td>
					</tr>
				</c:forEach>
			</table>
			<button class="btn" onclick="document.body.scrollTop = 0; document.documentElement.scrollTop = 0;">Ir para o topo</button>
		</div>
	</div>
</div>
</body>
</html>
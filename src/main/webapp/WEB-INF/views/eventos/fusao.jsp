<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="header.jsp" />
<title>Adicionar Cisão</title>
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
			<form action="fusao" method="post">
				<div class="form-group">
					<label for="codigo_id">Código da empresa original</label>
					<input type="text" id="codigo_id" name="codigo" class="form-control" placeholder="Digite o código da empresa original" required />
				</div>
				<div class="form-group">
					<label for="codigo_id">Código da empresa cindida</label>
					<input type="text" id="codigo_cindida_id" name="codigo_cindida" class="form-control" placeholder="Digite o código da empresa cindida" required />
				</div>
				<div class="form-group">
					<label for="data_id">Data da Cisão</label>
					<input type="date" id="data_id" name="data" class="form-control" required>
				</div>
				<div class="form-group">
					<label for="parte_cindida_id">Parte cindida (em porcentagem)</label>
					<input type="number" min="0.00001" step="0.00001" id="parte_cindida_id" name="parte_cindida" class="form-control" required
						   placeholder="Digite, em porcentagem, a parte cindida" />
				</div>
				<div class="form-group">
					<label for="parte_cindida_id">Proporção</label>
					<input type="number" min="0.01" step="0.01" id="proporcao_id" name="proporcao" class="form-control" required
						   placeholder="Digite a proporção de ações que o investidor receberá da empresa cindida para cada ação da empresa original" />
				</div>
				<br />
				<button type="submit" class="btn btn-primary btn-sm">Salvar</button>
			</form>
			<br />
			<h3>Fusões existentes</h3>
			<table class="table table-bordered">
				<tr>
					<th>Código da empresa 1</th>
					<th>Código da empresa 2</th>
					<th>Data da fusão</th>
					<th>Proporção de ações</th>
					<th>Valor em dinheiro</th>
				<tr>
				<c:forEach items="${cisoes}" var="cisao">
					<tr>
						<td><c:out value="${cisao.ticker1.codigo}"></c:out></td>
						<td><c:out value="${cisao.ticker2.codigo}"></c:out></td>
						<td><c:out value="${cisao.dataEvento}"></c:out></td>
						<td><c:out value="${cisao.parteCindida}"></c:out></td>
						<td><c:out value="${cisao.proporcaoDeAcoes}"></c:out></td>
					</tr>
				</c:forEach>
			</table>
			<button class="btn" onclick="document.body.scrollTop = 0; document.documentElement.scrollTop = 0;">Ir para o topo</button>
		</div>
	</div>
</div>
</body>
</html>
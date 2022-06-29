<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="../header.jsp" />
<title>Adicionar Bonifica��o</title>
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
			<form action="adicionar_bonificacao" method="post">
				<div class="form-group">
					<label for="codigo_id">C�digo</label>
					<input type="text" id="codigo_id" name="codigo" class="form-control" placeholder="Digite o c�digo" required />
				</div>
				<div class="form-group">
					<label for="data_id">Data da Bonifica��o</label>
					<input type="date" id="data_id" name="data" class="form-control" required>
				</div>
				<div class="form-group">
					<label for="proporcao_id">Propor��o</label>
					<input type="number" min="0.01" step="0.01" id="proporcao_id" name="proporcao" class="form-control" required
						   placeholder="Digite a bonifica��o (lembrar de transformar a porcentagem em decimal)" />
				</div>
				<div class="form-group">
					<label for="preco_id">Pre�o</label>
					<input type="number" min="0.01" step="0.01" id="preco_id" name="preco" class="form-control" required
						   placeholder="Digite o custo por a��o da bonifica��o." />
				</div>
				<br />
				<button type="submit" class="btn btn-primary btn-sm">Salvar</button>
			</form>
			<br />
			<h3>Bonifica��es existentes</h3>
			<table class="table table-bordered">
				<tr>
					<th>C�digo</th>
					<th>Data da bonifica��o</th>
					<th>Propor��o</th>
					<th>Pre�o</th>
				<tr>
				<c:forEach items="${bonificacoes}" var="bonificacao">
					<tr>
						<td><c:out value="${bonificacao.ticker1.codigo}"></c:out></td>
						<td><c:out value="${bonificacao.dataEvento}"></c:out></td>
						<td><c:out value="${bonificacao.proporcao}"></c:out></td>
						<td><c:out value="${bonificacao.preco}"></c:out></td>
					</tr>
				</c:forEach>
			</table>
			<button class="btn" onclick="document.body.scrollTop = 0; document.documentElement.scrollTop = 0;">Ir para o topo</button>
		</div>
	</div>
</div>
</body>
</html>
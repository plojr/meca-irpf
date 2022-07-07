<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="header.jsp" />
<title>Adicionar Aquisi��o</title>
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
			<form action="aquisicao" method="post">
				<div class="form-group">
					<label for="codigo_empresa_compradora_id">C�digo da empresa compradora</label>
					<input type="text" id="codigo_empresa_compradora_id" name="codigo_empresa_compradora" class="form-control" 
						   placeholder="Digite o c�digo da empresa compradora" required />
				</div>
				<div class="form-group">
					<label for="codigo_empresa_adquirida_id">C�digo da segunda adquirida</label>
					<input type="text" id="codigo_empresa_adquirida_id" name="codigo_empresa_adquirida" class="form-control"
						   placeholder="Digite o c�digo da empresa adquirida" required />
				</div>
				<div class="form-group">
					<label for="data_id">Data da Aquisi��o</label>
					<input type="date" id="data_id" name="data" class="form-control" required>
				</div>
				<div class="form-group">
					<label for="proporcao_acoes_id">Propor��o de a��es</label>
					<input type="number" min="0.00001" step="0.00001" id="proporcao_acoes_id" name="proporcao_acoes" class="form-control" 
						   placeholder="Quantidade de a��es da empresa compradora que o investidor vai receber por a��o da empresa adquirida." />
				</div>
				<div class="form-group">
					<label for="preco_por_acao_id">Pre�o por a��o</label>
					<input type="number" min="0.00001" step="0.00001" id="preco_por_acao_id" name="preco_por_acao" class="form-control" 
						   placeholder="Pre�o por a��o que a empresa compradora poder� pagar por a��o da empresa adquirida." />
				</div>
				<br />
				<button type="submit" class="btn btn-primary btn-sm">Salvar</button>
			</form>
			<br />
			<h3>Aquisi��es existentes</h3>
			<table class="table table-bordered">
				<tr>
					<th>C�digo da empresa compradora</th>
					<th>C�digo da empresa adquirida</th>
					<th>Data da aquisi��o</th>
					<th>Propor��o de a��es</th>
					<th>Pre�o por a��o</th>
				<tr>
				<c:forEach items="${aquisicoes}" var="aquisicao">
					<tr>
						<td><c:out value="${aquisicao.ticker1.codigo}"></c:out></td>
						<td><c:out value="${aquisicao.ticker2.codigo}"></c:out></td>
						<td><c:out value="${aquisicao.dataEvento}"></c:out></td>
						<td><c:out value="${aquisicao.proporcaoDeAcoes}"></c:out></td>
						<td><c:out value="${aquisicao.precoPorAcao}"></c:out></td>
					</tr>
				</c:forEach>
			</table>
			<button class="btn" onclick="document.body.scrollTop = 0; document.documentElement.scrollTop = 0;">Ir para o topo</button>
		</div>
	</div>
</div>
</body>
</html>
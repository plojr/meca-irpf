<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="header.jsp" />
<title>Gerenciar Aquisição</title>
<style>
th, td {
	text-align: center
}
</style>
<script>
function toggleForm(index) {
	document.getElementById("codigo_empresa_compradora_" + index).disabled = !document.getElementById("codigo_empresa_compradora_" + index).disabled;
	document.getElementById("codigo_empresa_adquirida_" + index).disabled = !document.getElementById("codigo_empresa_adquirida_" + index).disabled;
	document.getElementById("data_" + index).disabled = !document.getElementById("data_" + index).disabled;
	document.getElementById("proporcao_acoes_" + index).disabled = !document.getElementById("proporcao_acoes_" + index).disabled;
	document.getElementById("preco_por_acao_" + index).disabled = !document.getElementById("preco_por_acao_" + index).disabled;
}
</script>
</head>
<body>
<div class="container-fluid">
	<div class="row flex-nowrap">
		<jsp:include page="sidebar.jsp" />
		<div class="col py-3">
			<form action="aquisicao" method="post">
				<div class="form-group">
					<label for="codigo_empresa_compradora_id">Código da empresa compradora</label>
					<input type="text" id="codigo_empresa_compradora_id" name="codigo_empresa_compradora" class="form-control" 
						   placeholder="Digite o código da empresa compradora" required />
				</div>
				<div class="form-group">
					<label for="codigo_empresa_adquirida_id">Código da segunda adquirida</label>
					<input type="text" id="codigo_empresa_adquirida_id" name="codigo_empresa_adquirida" class="form-control"
						   placeholder="Digite o código da empresa adquirida" required />
				</div>
				<div class="form-group">
					<label for="data_id">Data da Aquisição</label>
					<input type="date" id="data_id" name="data" class="form-control" required>
				</div>
				<div class="form-group">
					<label for="proporcao_acoes_id">Proporção de ações</label>
					<input type="number" min="0" step="0.00001" id="proporcao_acoes_id" name="proporcao_acoes" class="form-control" 
						   placeholder="Quantidade de ações da empresa compradora que o investidor vai receber por ação da empresa adquirida." />
				</div>
				<div class="form-group">
					<label for="preco_por_acao_id">Preço por ação</label>
					<input type="number" min="0" step="0.00001" id="preco_por_acao_id" name="preco_por_acao" class="form-control" 
						   placeholder="Preço por ação que a empresa compradora poderá pagar por ação da empresa adquirida." />
				</div>
				<br />
				<button type="submit" class="btn btn-primary btn-sm">Salvar</button>
			</form>
			<br />
			<h3>Aquisições existentes</h3>
			<form action="editar_aquisicao" method="post">
				<input type="hidden" name="quantidade" value="${aquisicoes.size()}" />
				<table class="table table-bordered">
					<tr>
						<th>Código da empresa compradora</th>
						<th>Código da empresa adquirida</th>
						<th>Data da aquisição</th>
						<th>Proporção de ações</th>
						<th>Preço por ação</th>
						<th>Editar</th>
					<tr>
					<c:forEach items="${aquisicoes}" var="aquisicao" varStatus="loop">
						<tr>
							<td>
							<input type="hidden" name="id_${loop.index}" value="${aquisicao.id}" />
							<input class="form-control" type="text" value="${aquisicao.ticker1.codigo}" disabled 
									id="codigo_empresa_compradora_${loop.index}" name="codigo_empresa_compradora_${loop.index}" />
							</td>
							<td>
							<input class="form-control" type="text" value="${aquisicao.ticker2.codigo}" disabled 
									id="codigo_empresa_adquirida_${loop.index}" name="codigo_empresa_adquirida_${loop.index}" />
							</td>
							<td>
							<input class="form-control" type="date" value="${aquisicao.dataEvento}" disabled id="data_${loop.index}" 
									name="data_${loop.index}" />
							</td>
							<td>
							<input class="form-control" type="number" min="0" step="0.00001" value="${aquisicao.proporcaoDeAcoes}" disabled 
									id="proporcao_acoes_${loop.index}"  name="proporcao_acoes_${loop.index}" />
							</td>
							<td>
							<input class="form-control" type="number" min="0" step="0.00001" value="${aquisicao.precoPorAcao}" disabled 
									id="preco_por_acao_${loop.index}" name="preco_por_acao_${loop.index}" />
							</td>
							<td>
							<input type="checkbox" id="editar_${loop.index}" name="editar_${loop.index}" onchange="toggleForm(${loop.index})"/>
							</td>
						</tr>
					</c:forEach>
				</table>
				<button type="submit" class="btn btn-primary btn-sm">Salvar alterações</button>
				<button class="btn" onclick="document.body.scrollTop = 0; document.documentElement.scrollTop = 0;">Ir para o topo</button>
			</form>
		</div>
	</div>
</div>
</body>
</html>
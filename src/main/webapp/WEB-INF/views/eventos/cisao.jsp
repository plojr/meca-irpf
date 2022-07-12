<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="header.jsp" />
<title>Gerenciar Cisão</title>
<style>
th, td {
	text-align: center
}
</style>
<script>
function toggleForm(index) {
	document.getElementById("codigo_empresa_original_" + index).disabled = !document.getElementById("codigo_empresa_original_" + index).disabled;
	document.getElementById("codigo_nova_empresa_" + index).disabled = !document.getElementById("codigo_nova_empresa_" + index).disabled;
	document.getElementById("data_" + index).disabled = !document.getElementById("data_" + index).disabled;
	document.getElementById("proporcao_" + index).disabled = !document.getElementById("proporcao_" + index).disabled;
	document.getElementById("parte_cindida_" + index).disabled = !document.getElementById("parte_cindida_" + index).disabled;
}
</script>
</head>
<body>
<div class="container-fluid">
	<div class="row flex-nowrap">
		<jsp:include page="sidebar.jsp" />
		<div class="col py-3">
			<form action="cisao" method="post">
				<div class="form-group">
					<label for="codigo_empresa_original_id">Código da empresa original</label>
					<input type="text" id="codigo_empresa_original_id" name="codigo_empresa_original" class="form-control" placeholder="Digite o código da empresa original" required />
				</div>
				<div class="form-group">
					<label for="codigo_nova_empresa_id">Código da nova empresa</label>
					<input type="text" id="codigo_nova_empresa_id" name="codigo_nova_empresa" class="form-control" placeholder="Digite o código da nova empresa" required />
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
					<label for="proporcao_id">Proporção</label>
					<input type="number" min="0.01" step="0.01" id="proporcao_id" name="proporcao" class="form-control" required
						   placeholder="Digite a proporção de ações que o investidor receberá da nova empresa para cada ação da empresa original" />
				</div>
				<br />
				<button type="submit" class="btn btn-primary btn-sm">Salvar</button>
			</form>
			<br />
			<h3>Cisões existentes</h3>
			<form action="editar_cisao" method="post">
				<input type="hidden" name="quantidade" value="${cisoes.size()}" />
				<table class="table table-bordered">
					<tr>
						<th>Código da empresa original</th>
						<th>Código da nova empresa</th>
						<th>Data da cisão</th>
						<th>Parte cindida %</th>
						<th>Proporção de ações</th>
					<tr>
					<c:forEach items="${cisoes}" var="cisao" varStatus="loop">
						<tr>
							<td>
							<input type="hidden" name="id_${loop.index}" value="${cisao.id}" />
							<input class="form-control" type="text" value="${cisao.ticker1.codigo}" disabled 
									id="codigo_empresa_original_${loop.index}" name="codigo_empresa_original_${loop.index}" />
							</td>
							<td>
							<input class="form-control" type="text" value="${cisao.ticker2.codigo}" disabled 
									id="codigo_nova_empresa_${loop.index}" name="codigo_nova_empresa_${loop.index}" />
							</td>
							<td>
							<input class="form-control" type="date" value="${cisao.dataEvento}" disabled id="data_${loop.index}" 
									name="data_${loop.index}" />
							</td>
							<td>
							<input class="form-control" type="number" min="0" step="0.00001" value="${cisao.parteCindida}" disabled 
									id="parte_cindida_${loop.index}"  name="parte_cindida_${loop.index}" />
							</td>
							<td>
							<input class="form-control" type="number" min="0" step="0.00001" value="${cisao.proporcaoDeAcoes}" disabled 
									id="proporcao_${loop.index}"  name="proporcao_${loop.index}" />
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
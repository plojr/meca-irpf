<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="header.jsp" />
<title>Gerenciar Desdobramento</title>
<style>
th, td {
	text-align: center
}
</style>
<script>
function toggleForm(index) {
	document.getElementById("codigo_" + index).disabled = !document.getElementById("codigo_" + index).disabled;
	document.getElementById("data_" + index).disabled = !document.getElementById("data_" + index).disabled;
	document.getElementById("proporcao_" + index).disabled = !document.getElementById("proporcao_" + index).disabled;
}
</script>
</head>
<body>
<div class="container-fluid">
	<div class="row flex-nowrap">
		<jsp:include page="sidebar.jsp" />
		<div class="col py-3">
			<form action="desdobramento" method="post">
				<div class="form-group">
					<label for="codigo_id">Código</label>
					<input type="text" id="codigo_id" name="codigo" class="form-control" placeholder="Digite o código" required />
				</div>
				<div class="form-group">
					<label for="data_id">Data do desdobramento</label>
					<input type="date" id="data_id" name="data" class="form-control" required>
				</div>
				<div class="form-group">
					<label for="proporcao_id">Proporção</label>
					<input type="number" min="0.01" step="0.01" id="proporcao_id" name="proporcao" class="form-control" required
						   placeholder="Digite a proporção do desdobramento Exemplo: se o desdobramento for de 1:10, então digite 10" />
				</div>
				<br />
				<button type="submit" class="btn btn-primary btn-sm">Salvar</button>
			</form>
			<br />
			<h3>Desdobramentos existentes</h3>
			<form action="editar_desdobramento" method="post">
				<input type="hidden" name="quantidade" value="${desdobramentos.size()}" />
				<table class="table table-bordered">
					<tr>
						<th>Código</th>
						<th>Data do desdobramento</th>
						<th>Proporção</th>
						<th>Editar</th>
					<tr>
					<c:forEach items="${desdobramentos}" var="desdobramento" varStatus="loop">
						<tr>
							<td>
								<input type="hidden" name="id_${loop.index}" value="${desdobramento.id}" />
								<input class="form-control" type="text" value="${desdobramento.ticker1.codigo}" disabled 
										id="codigo_${loop.index}" name="codigo_${loop.index}" />
							</td>
							<td>
								<input class="form-control" type="date" value="${desdobramento.dataEvento}" disabled 
									id="data_${loop.index}" name="data_${loop.index}" />
							</td>
							<td>
								<input class="form-control" type="number" min="0.00001" step="0.00001" value="${desdobramento.proporcao}" disabled 
									id="proporcao_${loop.index}"  name="proporcao_${loop.index}" />
							</td>
							<td>
								<input type="checkbox" id="editar_${loop.index}" name="editar_${loop.index}" onchange="toggleForm(${loop.index})" />
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
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="header.jsp" />
<title>Gerenciar Grupamento</title>
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
			<form action="grupamento" method="post">
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
			<form action="editar_grupamento" method="post">
				<input type="hidden" name="quantidade" value="${grupamentos.size()}" />
				<table class="table table-bordered">
					<tr>
						<th>Deletar</th>
						<th>Código</th>
						<th>Data do grupamento</th>
						<th>Proporção</th>
						<th>Editar</th>
					<tr>
					<c:forEach items="${grupamentos}" var="grupamento" varStatus="loop">
						<tr>
							<td>
								<a href=<c:out value="deletar_grupamento?id=${grupamento.id}"></c:out>>
									<img width="30" height="30" src="${pageContext.request.contextPath}/imagens/lixeira.png" alt="Lixeira">
								</a>
							</td>
							<td>
								<input type="hidden" name="id_${loop.index}" value="${grupamento.id}" />
								<input class="form-control" type="text" value="${grupamento.ticker1.codigo}" disabled 
										id="codigo_${loop.index}" name="codigo_${loop.index}" />
							</td>
							<td>
								<input class="form-control" type="date" value="${grupamento.dataEvento}" disabled 
									id="data_${loop.index}" name="data_${loop.index}" />
							</td>
							<td>
								<input class="form-control" type="number" min="0.00001" step="0.00001" value="${grupamento.proporcao}" disabled 
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
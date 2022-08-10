<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="header.jsp" />
<title>Gerenciar ticker</title>
<style>
th, td {
	text-align: center
}
</style>
<script>
function toggleForm(index) {
	document.getElementById("codigo_" + index).disabled = !document.getElementById("codigo_" + index).disabled;
	document.getElementById("cnpj_" + index).disabled = !document.getElementById("cnpj_" + index).disabled;
	document.getElementById("bdr_" + index).disabled = !document.getElementById("bdr_" + index).disabled;
}
</script>
</head>
<body>
<div class="container-fluid">
	<div class="row flex-nowrap">
		<jsp:include page="sidebar.jsp" />
		<div class="col py-3">
			<form action="ticker" method="post">
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
			<form action="editar_ticker" method="post">
			<input type="hidden" name="quantidade" value="${tickers.size()}" />
			<table class="table table-bordered">
				<tr>
					<th>Deletar</th>
					<th>Código</th>
					<th>CNPJ</th>
					<th>BDR?</th>
					<th>Editar</th>
				<tr>
				<c:forEach items="${tickers}" var="ticker" varStatus="loop">
					<tr>
						<td>
							<a href=<c:out value="deletar_ticker?id=${ticker.id}"></c:out>>
								<img width="30" height="30" src="${pageContext.request.contextPath}/imagens/lixeira.png" alt="Lixeira">
							</a>
						</td>
						<td>
							<input type="hidden" name="id_${loop.index}" value="${ticker.id}" />
							<input type="text" value="${ticker.codigo}" disabled id="codigo_${loop.index}" name="codigo_${loop.index}" />
						</td>
						<td>
							<input type="text" value="${ticker.cnpj}" disabled id="cnpj_${loop.index}" name="cnpj_${loop.index}"  />
						</td>
						<td>
							<select id="bdr_${loop.index}" name="bdr_${loop.index}" disabled >
							<c:choose>
								<c:when test="${ticker.bdr}">
									<option value="Sim" selected>Sim</option>
									<option value="Não">Não</option>
								</c:when>
								<c:otherwise>
									<option value="Sim">Sim</option>
									<option value="Não" selected>Não</option>
								</c:otherwise>
							</c:choose>
							</select>
						</td>
						<td><input type="checkbox" id="editar_${loop.index}" name="editar_${loop.index}" onchange="toggleForm(${loop.index})"/></td>
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
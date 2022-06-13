<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="header.jsp" />
<title>Editar ticker</title>
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
			<form action="editar_ticker" method="post">
			<input type="hidden" name="quantidade" value="${tickers.size()}" />
			<table class="table table-bordered">
				<tr>
					<th>Código</th>
					<th>CNPJ</th>
					<th>BDR?</th>
					<th>Editar</th>
				<tr>
				<c:forEach items="${tickers}" var="ticker" varStatus="loop">
					<tr>
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
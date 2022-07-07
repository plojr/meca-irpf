<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="header.jsp" />
<title>Adicionar Fus�o</title>
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
					<label for="codigo_id">C�digo da primeira empresa</label>
					<input type="text" id="codigo_1_id" name="codigo_1" class="form-control" placeholder="Digite o c�digo da primeira empresa" required />
				</div>
				<div class="form-group">
					<label for="codigo_id">C�digo da segunda empresa</label>
					<input type="text" id="codigo_2_id" name="codigo_2" class="form-control" placeholder="Digite o c�digo da segunda empresa" required />
				</div>
				<div class="form-group">
					<label for="codigo_id">C�digo da nova empresa</label>
					<input type="text" id="codigo_3_id" name="codigo_3" class="form-control" placeholder="Digite o c�digo da nova empresa" required />
				</div>
				<div class="form-group">
					<label for="data_id">Data da Fus�o</label>
					<input type="date" id="data_id" name="data" class="form-control" required>
				</div>
				<div class="form-group">
					<label for="proporcao_empresa_1_id">Propor��o da empresa 1</label>
					<input type="number" min="0.01" step="0.01" id="proporcao_empresa_2_id" name="proporcao_empresa_2" class="form-control" required
						   placeholder="Propor��o de a��es da empresa 1 que o investidor precisa ter para que ele ganhe 1 a��o da nova empresa." />
				</div>
				<div class="form-group">
					<label for="proporcao_empresa_2_id">Propor��o da empresa 2</label>
					<input type="number" min="0.01" step="0.01" id="proporcao_empresa_2_id" name="proporcao_empresa_2" class="form-control" required
						   placeholder="Propor��o de a��es da empresa 2 que o investidor precisa ter para que ele ganhe 1 a��o da nova empresa." />
				</div>
				<br />
				<button type="submit" class="btn btn-primary btn-sm">Salvar</button>
			</form>
			<br />
			<h3>Fus�es existentes</h3>
			<table class="table table-bordered">
				<tr>
					<th>C�digo da empresa 1</th>
					<th>C�digo da empresa 2</th>
					<th>C�digo da nova empresa</th>
					<th>Data da fus�o</th>
					<th>Propor��o de a��es da empresa 1</th>
					<th>Propor��o de a��es da empresa 2</th>
				<tr>
				<c:forEach items="${fusoes}" var="fusao">
					<tr>
						<td><c:out value="${fusao.ticker1.codigo}"></c:out></td>
						<td><c:out value="${fusao.ticker2.codigo}"></c:out></td>
						<td><c:out value="${fusao.tickerNovaEmpresa.codigo}"></c:out></td>
						<td><c:out value="${fusao.dataEvento}"></c:out></td>
						<td><c:out value="${fusao.proporcaoDeAcoesEmpresa1}"></c:out></td>
						<td><c:out value="${fusao.proporcaoDeAcoesEmpresa2}"></c:out></td>
					</tr>
				</c:forEach>
			</table>
			<button class="btn" onclick="document.body.scrollTop = 0; document.documentElement.scrollTop = 0;">Ir para o topo</button>
		</div>
	</div>
</div>
</body>
</html>
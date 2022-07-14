<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="header.jsp" />
<title>Gerenciar Fusão</title>
<style>
th, td {
	text-align: center
}
</style>
<script>
function toggleForm(index) {
	document.getElementById("codigo_1_" + index).disabled = !document.getElementById("codigo_1_" + index).disabled;
	document.getElementById("codigo_2_" + index).disabled = !document.getElementById("codigo_2_" + index).disabled;
	document.getElementById("codigo_nova_" + index).disabled = !document.getElementById("codigo_nova_" + index).disabled;
	document.getElementById("data_" + index).disabled = !document.getElementById("data_" + index).disabled;
	document.getElementById("proporcao_empresa_1_" + index).disabled = !document.getElementById("proporcao_empresa_1_" + index).disabled;
	document.getElementById("proporcao_empresa_2_" + index).disabled = !document.getElementById("proporcao_empresa_2_" + index).disabled;
}
</script>
</head>
<body>
<div class="container-fluid">
	<div class="row flex-nowrap">
		<jsp:include page="sidebar.jsp" />
		<div class="col py-3">
			<form action="fusao" method="post">
				<div class="form-group">
					<label for="codigo_1_id">Código da primeira empresa</label>
					<input type="text" id="codigo_1_id" name="codigo_1" class="form-control" placeholder="Digite o código da primeira empresa" required />
				</div>
				<div class="form-group">
					<label for="codigo_2_id">Código da segunda empresa</label>
					<input type="text" id="codigo_2_id" name="codigo_2" class="form-control" placeholder="Digite o código da segunda empresa" required />
				</div>
				<div class="form-group">
					<label for="codigo_nova_id">Código da nova empresa</label>
					<input type="text" id="codigo_nova_id" name="codigo_nova" class="form-control" placeholder="Digite o código da nova empresa" required />
				</div>
				<div class="form-group">
					<label for="data_id">Data da Fusão</label>
					<input type="date" id="data_id" name="data" class="form-control" required>
				</div>
				<div class="form-group">
					<label for="proporcao_empresa_1_id">Proporção da empresa 1</label>
					<input type="number" min="0.00001" step="0.00001" id="proporcao_empresa_1_id" name="proporcao_empresa_1" class="form-control" required
						   placeholder="Proporção de ações da empresa 1 que o investidor precisa ter para que ele ganhe 1 ação da nova empresa." />
				</div>
				<div class="form-group">
					<label for="proporcao_empresa_2_id">Proporção da empresa 2</label>
					<input type="number" min="0.00001" step="0.00001" id="proporcao_empresa_2_id" name="proporcao_empresa_2" class="form-control" required
						   placeholder="Proporção de ações da empresa 2 que o investidor precisa ter para que ele ganhe 1 ação da nova empresa." />
				</div>
				<br />
				<button type="submit" class="btn btn-primary btn-sm">Salvar</button>
			</form>
			<br />
			<h3>Fusões existentes</h3>
			<form action="editar_fusao" method="post">
				<input type="hidden" name="quantidade" value="${fusoes.size()}" />
				<table class="table table-bordered">
					<tr>
						<th>Código da empresa 1</th>
						<th>Código da empresa 2</th>
						<th>Código da nova empresa</th>
						<th>Data da fusão</th>
						<th>Proporção de ações da empresa 1</th>
						<th>Proporção de ações da empresa 2</th>
						<th>Editar</th>
					<tr>
					<c:forEach items="${fusoes}" var="fusao" varStatus="loop">
						<tr>
							<td>
							<input type="hidden" name="id_${loop.index}" value="${fusao.id}" />
							<input class="form-control" type="text" value="${fusao.ticker1.codigo}" disabled 
									id="codigo_1_${loop.index}" name="codigo_1_${loop.index}" />
							</td>
							<td>
							<input class="form-control" type="text" value="${fusao.ticker2.codigo}" disabled 
									id="codigo_2_${loop.index}" name="codigo_2_${loop.index}" />
							</td>
							<td>
							<input class="form-control" type="text" value="${fusao.tickerNovaEmpresa.codigo}" disabled 
									id="codigo_nova_${loop.index}" name="codigo_nova_${loop.index}" />
							</td>
							<td>
							<input class="form-control" type="date" value="${fusao.dataEvento}" disabled id="data_${loop.index}" 
									name="data_${loop.index}" />
							</td>
							<td>
							<input class="form-control" type="number" min="0.00001" step="0.00001" value="${fusao.proporcaoDeAcoesEmpresa1}" 
									disabled id="proporcao_empresa_1_${loop.index}"  name="proporcao_empresa_1_${loop.index}" />
							</td>
							<td>
							<input class="form-control" type="number" min="0.00001" step="0.00001" value="${fusao.proporcaoDeAcoesEmpresa2}" 
									disabled id="proporcao_empresa_2_${loop.index}" name="proporcao_empresa_2_${loop.index}" />
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
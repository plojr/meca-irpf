<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:include page="header.jsp" />
<script type="text/javascript">
var index = 1;
function removerLinha() {
	if(index == 1) return;
	// A variável index está com 1 a mais do que o último índice.
	// Por isso, o valor dela é diminuído em 1 antes das remoções dos inputs e labels.
	index = index - 1;
	// Remover os label/input de tipo, quantidade, ticker e preço.
	document.getElementById("geral_div"+index).remove();
	document.getElementById("tipo_label" + index).remove();
	document.getElementById("tipo_select" + index).remove();
	document.getElementById("quantidade_label" + index).remove();
	document.getElementById("quantidade_input" + index).remove();
	document.getElementById("ticker_label" + index).remove();
	document.getElementById("ticker_input" + index).remove();
	document.getElementById("preco_label" + index).remove();
	document.getElementById("preco_input" + index).remove();
	var sizeElement = document.getElementById("numero_de_ordens");
	// Atualizar o valor de index dentro do input
	sizeElement.value = index - 1;
}

function adicionarLinha() {
	var spanvar = document.getElementById("ordens");
	var geralDiv = document.createElement("div");
	geralDiv.innerHTML = "Ordem " + index;
	geralDiv.setAttribute("class", "form-group");
	geralDiv.setAttribute("id", "geral_div"+index);
	spanvar.appendChild(geralDiv);
	var tipoLabel = document.createElement("label");
	tipoLabel.innerHTML = "Tipo: ";
	tipoLabel.setAttribute("for", "tipo_select" + index);
	tipoLabel.setAttribute("id", "tipo_label" + index);

	var optionDefault = document.createElement("option");
	optionDefault.text = "Selecione o tipo da ordem";
	optionDefault.value = "";
	var optionCompra = document.createElement("option");
	optionCompra.text = "Compra";
	optionCompra.value = "c";
	var optionVenda = document.createElement("option");
	optionVenda.text = "Venda";
	optionVenda.value = "v";
	var tipoSelect = document.createElement("select");
	tipoSelect.setAttribute("name", "tipo" + index);
	tipoSelect.setAttribute("id", "tipo_select" + index);
	tipoSelect.setAttribute("class", "form-control");
	tipoSelect.required = true;
	tipoSelect.appendChild(optionDefault);
	tipoSelect.appendChild(optionCompra);
	tipoSelect.appendChild(optionVenda);
	spanvar.appendChild(tipoLabel);
	spanvar.appendChild(tipoSelect);
	
	var quantidadeLabel = document.createElement("label");
	quantidadeLabel.setAttribute("for", "quantidade_input" + index);
	quantidadeLabel.setAttribute("id", "quantidade_label" + index);
	quantidadeLabel.innerHTML = "Quantidade: ";
	var quantidadeInput = document.createElement("input");
	quantidadeInput.setAttribute("type", "number");
	quantidadeInput.setAttribute("min", 1);
	quantidadeInput.setAttribute("step", 1);
	quantidadeInput.setAttribute("name", "quantidade" + index);
	quantidadeInput.setAttribute("id", "quantidade_input" + index);
	quantidadeInput.setAttribute("class", "form-control");
	quantidadeInput.required = true;
	spanvar.appendChild(quantidadeLabel);
	spanvar.appendChild(quantidadeInput);
	
	var tickerLabel = document.createElement("label");
	tickerLabel.innerHTML = "Ticker: ";
	tickerLabel.setAttribute("for", "ticker_input" + index);
	tickerLabel.setAttribute("id", "ticker_label" + index);
	var tickerInput = document.createElement("input");
	tickerInput.setAttribute("type", "text");
	tickerInput.setAttribute("name", "ticker" + index);
	tickerInput.setAttribute("id", "ticker_input" + index);
	tickerInput.setAttribute("class", "form-control");
	tickerInput.required = true;
	spanvar.appendChild(tickerLabel);
	spanvar.appendChild(tickerInput);
	
	var precoLabel = document.createElement("label");
	precoLabel.innerHTML = "Preço: ";
	precoLabel.setAttribute("for", "preco_input" + index);
	precoLabel.setAttribute("id", "preco_label" + index);
	var precoInput = document.createElement("input");
	precoInput.setAttribute("type", "number");
	precoInput.setAttribute("min", "0.01");
	precoInput.setAttribute("step", "0.01");
	precoInput.setAttribute("name", "preco" + index);
	precoInput.setAttribute("id", "preco_input" + index);
	precoInput.setAttribute("class", "form-control");
	precoInput.required = true;
	spanvar.appendChild(precoLabel);
	spanvar.appendChild(precoInput);
	
	var sizeElement = document.getElementById("numero_de_ordens");
	sizeElement.value = index;
	index = index + 1;
}
function toggleCorretagemForm(index) {
	document.getElementById("data_" + index).disabled = !document.getElementById("data_" + index).disabled;
	document.getElementById("valor_" + index).disabled = !document.getElementById("valor_" + index).disabled;
}
function toggleOrdemForm(index) {
	document.getElementById("tipo_" + index).disabled = !document.getElementById("tipo_" + index).disabled;
	document.getElementById("quantidade_" + index).disabled = !document.getElementById("quantidade_" + index).disabled;
	document.getElementById("codigo_" + index).disabled = !document.getElementById("codigo_" + index).disabled;
	document.getElementById("preco_" + index).disabled = !document.getElementById("preco_" + index).disabled;
}
</script>
<title>Gerenciar nota de corretagem</title>
</head>
<body>
<div class="container-fluid">
	<div class="row flex-nowrap">
		<jsp:include page="sidebar.jsp" />
		<div class="col py-3">
			<form action="corretagem" method="post">
				<div class="form-group">
					<label for="data_id">Data da nota de corretagem</label>
					<input type="date" id="data_id" name="data" class="form-control" placeholder="Coloque a data da nota de corretagem" required />
				</div>
				<div class="form-group">
					<label for="valor_liquido_id">Valor líquido da nota de corretagem</label>
					<input type="number" name="valor_liquido" id="valor_liquido_id" class="form-control" value="0" step="0.01" required>
				</div>
				<br />
				<input type="button" name="adicionar_linha" class="btn btn-primary btn-sm" value="Adicionar uma ordem" onclick="adicionarLinha();">
				<small id="ordem_adicionar_ajuda" class="form-text text-muted">&lt;= Clique aqui para preencher uma nova ordem</small>
				<br />
				<span id="ordens"></span>
				<input type="hidden" id="numero_de_ordens" name="numero_de_ordens" value="0"/>
				<br />
				<input type="button" name="remover_linha" class="btn btn-primary btn-sm" value="Remover uma ordem" onclick="removerLinha();">
				<small id="ordem_remover_ajuda" class="form-text text-muted">&lt;= Clique aqui para remover o último campo de ordem</small>
				<br />
				<br />
				<button type="submit" class="btn btn-primary btn-sm">Salvar</button>
			</form>
			<br />
			<c:if test="${empty corretagens}">Não há dados.</c:if>
			<c:if test="${not empty corretagens}">
				<div>
				<hr />
				<span style="font-weight: bold;">Atenção!</span><br />Qualquer alteração e/ou deleção nas ordens e/ou notas de corretagem pode(m) ocasionar inconsistência na carteira.
				Portanto preste bastante atenção ao deletar ou editar suas notas de corretagem como também as ordens.
				Esta aplicação não trata as inconsistências.
				</div>
				<hr />
			</c:if>
			<c:forEach items="${corretagens}" var="corretagem">
				<form method="post" action="editar_corretagem">
					<span>Nota de Corretagem</span>
					<table class="table table-bordered">
					<tr>
						<th>Deletar</th>
						<th>Data</th>
						<th>Valor</th>
						<th>Editar</th>
					</tr>
					<tr>
						<td>
							<input type="hidden" name="id" value="${corretagem.id}" />
							<a href="deletar_corretagem?id=${corretagem.id}">
								<img width="30" height="30" src="${pageContext.request.contextPath}/imagens/lixeira.png" alt="Lixeira">
							</a>
						</td>
						<td><input class="form-control" type="date" value="${corretagem.data}" 
								id="data_${corretagem.id}" name="data_${corretagem.id}" disabled /></td>
						<td><input class="form-control" size="5" type="number" step="0.01" value="${corretagem.valorLiquido}" 
							disabled id="valor_${corretagem.id}" name="valor_${corretagem.id}" /></td>
						<td>
							<input type="checkbox" id="editar_${corretagem.id}" name="editar_${corretagem.id}" 
								onchange="toggleCorretagemForm(${corretagem.id})"/>
						</td>
					</tr>
					</table>
					<div>
						<input type="submit" value="Salvar alterações da nota de corretagem" class="btn btn-sm btn-primary" />
					</div>
				</form>
				<form method="post" action="editar_ordem">
					<span>Ordens</span>
					<table class="table table-bordered">
					<tr>
						<th>Deletar</th>
						<th>Tipo</th>
						<th>Quantidade</th>
						<th>Ticker</th>
						<th>Preço</th>
						<th>Editar</th>
					</tr>
					<c:forEach items="${corretagem.ordens}" var="ordem">
					<tr>
						<td>
							<input type="hidden" name="id_${ordem.id}" value="${ordem.id}" />
							<a href="deletar_ordem?id=${ordem.id}">
								<img width="30" height="30" src="${pageContext.request.contextPath}/imagens/lixeira.png" alt="Lixeira">
							</a>
						</td>
						<c:if test="${ordem.tipo eq 'c'.charAt(0)}">
							<td>
								<input class="form-control" type="text" value="Compra" disabled 
										id="tipo_${ordem.id}" name="tipo_${ordem.id}" />
							</td>
						</c:if>
						<c:if test="${ordem.tipo eq 'v'.charAt(0)}">
							<td>
								<input class="form-control" type="text" value="Venda" disabled 
										id="tipo_${ordem.id}" name="tipo_${ordem.id}" />
							</td>
						</c:if>
						<td>
							<input class="form-control" type="number" value="${ordem.quantidade}" disabled 
										id="quantidade_${ordem.id}" name="quantidade_${ordem.id}" />
						</td>
						<td>
							<input class="form-control" type="text" value="${ordem.ticker.codigo}" disabled 
										id="codigo_${ordem.id}" name="codigo_${ordem.id}" />
						</td>
						<td>
							<input class="form-control" type="number" step="0.01"
								value="<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${ordem.preco}" />" disabled 
								id="preco_${ordem.id}" name="preco_${ordem.id}" />
						</td>
						<td>
							<input type="checkbox" id="editar_${ordem.id}" name="editar_${ordem.id}" 
								onchange="toggleOrdemForm(${ordem.id})"/>
						</td>
					</tr>
					</c:forEach>
					</table>
					<button type="submit" class="btn btn-primary btn-sm">Salvar alterações da ordem</button>
				</form>
				<hr />
			</c:forEach>
			<button class="btn" onclick="document.body.scrollTop = 0; document.documentElement.scrollTop = 0;">Ir para o topo</button>
		</div>
	</div>
</div>
</body>
</html>
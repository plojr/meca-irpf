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
</script>
<title>Adicionar nota de corretagem</title>
</head>
<body>
<div class="container-fluid">
	<div class="row flex-nowrap">
		<jsp:include page="sidebar.jsp" />
		<div class="col py-3">
			<form action="adicionar_corretagem" method="post">
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
		</div>
	</div>
</div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script type="text/javascript">
var index = 1;

function removerLinha() {
	if(index == 1) return;
	// A variável index está com 1 a mais do que o último índice.
	// Por isso, o valor dela é diminuído em 1 antes das remoções dos inputs e labels.
	index = index - 1;
	// Remover os label/input de tipo, quantidade, ticker e preço.
	var tipoLabel = document.getElementById("tipo_label" + index);
	var tipoInput = document.getElementById("tipo_input" + index);
	var quantidadeLabel = document.getElementById("quantidade_label" + index);
	var quantidadeInput = document.getElementById("quantidade_input" + index);
	var tickerLabel = document.getElementById("ticker_label" + index);
	var tickerInput = document.getElementById("ticker_input" + index);
	var precoLabel = document.getElementById("preco_label" + index);
	var precoInput = document.getElementById("preco_input" + index);
	tipoLabel.remove();
	tipoInput.remove();
	quantidadeLabel.remove();
	quantidadeInput.remove();
	tickerLabel.remove();
	tickerInput.remove();
	precoLabel.remove();
	precoInput.remove();
	var spanvar = document.getElementById("ordens");
	// Isso foi feito para remover o último <br> da função adicionarLinha().
	spanvar.innerHTML = spanvar.innerHTML.substr(0, spanvar.innerHTML.length-4);
	var sizeElement = document.getElementById("numero_de_ordens");
	// Atualizar o valor de index dentro do input
	sizeElement.value = index;
}

function adicionarLinha() {
	var spanvar = document.getElementById("ordens");
	spanvar.innerHTML += "<br />";
	var tipoLabel = document.createElement("label");
	tipoLabel.innerHTML = "Tipo: ";
	tipoLabel.setAttribute("for", "tipo" + index);
	tipoLabel.setAttribute("id", "tipo_label" + index);
	var tipoInput = document.createElement("input");
	tipoInput.setAttribute("type", "text");
	tipoInput.setAttribute("name", "tipo" + index);
	tipoInput.setAttribute("id", "tipo_input" + index);
	tipoInput.required = true;
	spanvar.appendChild(tipoLabel);
	spanvar.appendChild(tipoInput);
	
	var quantidadeLabel = document.createElement("label");
	quantidadeLabel.setAttribute("for", "quantidade" + index);
	quantidadeLabel.setAttribute("id", "quantidade_label" + index);
	quantidadeLabel.innerHTML = "Quantidade: ";
	var quantidadeInput = document.createElement("input");
	quantidadeInput.setAttribute("type", "number");
	quantidadeInput.setAttribute("min", 100);
	quantidadeInput.setAttribute("step", 100);
	quantidadeInput.setAttribute("name", "quantidade" + index);
	quantidadeInput.setAttribute("id", "quantidade_input" + index);
	quantidadeInput.required = true;
	spanvar.appendChild(quantidadeLabel);
	spanvar.appendChild(quantidadeInput);
	
	var tickerLabel = document.createElement("label");
	tickerLabel.innerHTML = "Ticker: ";
	tickerLabel.setAttribute("for", "ticker" + index);
	tickerLabel.setAttribute("id", "ticker_label" + index);
	var tickerInput = document.createElement("input");
	tickerInput.setAttribute("type", "text");
	tickerInput.setAttribute("name", "ticker" + index);
	tickerInput.setAttribute("id", "ticker_input" + index);
	tickerInput.required = true;
	spanvar.appendChild(tickerLabel);
	spanvar.appendChild(tickerInput);
	
	var precoLabel = document.createElement("label");
	precoLabel.innerHTML = "Preço: ";
	precoLabel.setAttribute("for", "preco" + index);
	precoLabel.setAttribute("id", "preco_label" + index);
	var precoInput = document.createElement("input");
	precoInput.setAttribute("type", "number");
	precoInput.setAttribute("min", "0.01");
	precoInput.setAttribute("step", "0.01");
	precoInput.setAttribute("name", "preco" + index);
	precoInput.setAttribute("id", "preco_input" + index);
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
	<form action="adicionar_corretagem" method="post">
		<div>
			<label>Data da nota de corretagem</label>
			<input type="date" name="data" placeholder="Coloque a data da nota de corretagem" required />
		</div>
		<div>
			<label>Valor líquido da nota de corretagem</label>
			<input type="number" name="valor_liquido" value="0" step="0.01" required>
		</div>
		<br />
		<input type="button" name="adicionar_linha" value="Adicionar uma ordem" onclick="adicionarLinha();">
		<br />
		<span id="ordens"></span>
		<input type="hidden" id="numero_de_ordens" name="numero_de_ordens" value="0"/>
		<br />
		<input type="button" name="remover_linha" value="Remover uma ordem" onclick="removerLinha();">
		<br />
		<button type="submit">Salvar</button>
	</form>
</body>
</html>
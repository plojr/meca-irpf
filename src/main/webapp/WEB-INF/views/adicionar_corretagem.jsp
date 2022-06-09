<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script type="text/javascript">
var index = 1;
function adicionarLinha() {
	
	var spanvar = document.getElementById("ordens");
	spanvar.innerHTML += "<br />";
	var tipoLabel = document.createElement("label");
	tipoLabel.innerHTML = "Tipo: ";
	tipoLabel.setAttribute("for", "tipo" + index);
	var tipoInput = document.createElement("input");
	tipoInput.setAttribute("type", "text");
	tipoInput.setAttribute("name", "tipo" + index);
	tipoInput.required = true;
	spanvar.appendChild(tipoLabel);
	spanvar.appendChild(tipoInput);
	
	var quantidadeLabel = document.createElement("label");
	quantidadeLabel.setAttribute("for", "quantidade" + index);
	quantidadeLabel.innerHTML = "Quantidade: ";
	var quantidadeInput = document.createElement("input");
	quantidadeInput.setAttribute("type", "number");
	quantidadeInput.setAttribute("min", 100);
	quantidadeInput.setAttribute("step", 100);
	quantidadeInput.setAttribute("name", "quantidade" + index);
	quantidadeInput.required = true;
	spanvar.appendChild(quantidadeLabel);
	spanvar.appendChild(quantidadeInput);
	
	var tickerLabel = document.createElement("label");
	tickerLabel.innerHTML = "Ticker: ";
	tickerLabel.setAttribute("for", "ticker" + index);
	var tickerInput = document.createElement("input");
	tickerInput.setAttribute("type", "text");
	tickerInput.setAttribute("name", "ticker" + index);
	tickerInput.required = true;
	spanvar.appendChild(tickerLabel);
	spanvar.appendChild(tickerInput);
	
	var precoLabel = document.createElement("label");
	precoLabel.innerHTML = "Preço: ";
	precoLabel.setAttribute("for", "preco" + index);
	var precoInput = document.createElement("input");
	precoInput.setAttribute("type", "number");
	precoInput.setAttribute("min", "0.01");
	precoInput.setAttribute("step", "0.01");
	precoInput.setAttribute("name", "preco" + index);
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
		<br /><br />
		<button type="submit">Salvar</button>
	</form>
</body>
</html>
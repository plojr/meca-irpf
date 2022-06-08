<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<style>
td {
	text-align: center
}
</style>
<head>
<meta charset="UTF-8">
<title>Notas de corretagem</title>
</head>
<body>
	<c:forEach items="${corretagens}" var="corretagem">
		<span>Nota de Corretagem</span>
		<div><label>Data:</label> <span><c:out value="${corretagem.date}"></c:out></span></div>
		<div><label>Valor líquido:</label> <span><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${corretagem.valorLiquido}" /></span></div>
		<span>Ordens</span>
		<table border="1">
		<tr>
			<th>Tipo</th>
			<th>Quantidade</th>
			<th>Ticker</th>
			<th>Preço</th>
		</tr>
		<c:forEach items="${corretagem.ordens}" var="ordem">
		<tr>
			<c:if test="${ordem.tipo eq 'c'.charAt(0)}">
				<td><c:out value="Compra"></c:out></td>
			</c:if>
			<c:if test="${ordem.tipo eq 'v'.charAt(0)}">
				<td><c:out value="Venda"></c:out></td>
			</c:if>
			<td><c:out value="${ordem.quantidade}"></c:out></td>
			<td><c:out value="${ordem.ticker}"></c:out></td>
			<!-- td><c:out value="${ordem.preco}"></c:out></td-->
			<td><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${ordem.preco}" /></td>
		</tr>
		</c:forEach>
		</table>
		<br />
	</c:forEach>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Erro!</title>
</head>
<body>
	<p>Ops, algo deu errado.</p>
	<div><c:out value="${mensagemDeErro}"></c:out></div>
</body>
</html>
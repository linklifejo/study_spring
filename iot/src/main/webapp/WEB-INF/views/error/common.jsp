<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<div class='center'>
	<div class='txt-left'>
	<a href='<c:url value="/"/>'><img src='<c:url value="/"/>imgs/hanul.logo.png'></a>
	</div>
	<div class='txt-left'>
		<h3>내부 서버오류가 발생했습니다</h3>
		${msg}
		<p>관련 문의사항은 네이버 고객센터에 알려주시면 친절하게 안내해 드리겠습니다</p>
		<p>감사합니다.</p>
	</div>
</div>
</body>
</html>
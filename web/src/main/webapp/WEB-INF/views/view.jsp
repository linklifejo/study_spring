<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<a href='<c:url value="/"/>'>홈으로</a>
<hr>
<h3>View - [ Model객체사용 ]</h3>
<div>오늘: ${today }</div>
<h3>View - [ ModelAndView객체사용 ]</h3>
<div>현재: ${now }</div>
</body>
</html>
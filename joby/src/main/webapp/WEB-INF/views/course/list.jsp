<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h3>코스목록</h3>
<div class='btnSet'>
	<a href='new.co' class='btn-fill'>코스등록</a>
</div>
<table class='w-px600 tb-list'>
<thead>
	<tr><th class='w-px140'>코스명</th>
		<th class='w-px160'>산이름</th>
	</tr>
</thead>
<tbody>
<c:forEach items='${list}' var='vo'>
	<tr><td><a href='info.co?id=${vo.id}'>${vo.couname }</a></td>
		<td>${vo.locname }</td>
	</tr>
</c:forEach>
	
</tbody>
</table>

</body>
</html>
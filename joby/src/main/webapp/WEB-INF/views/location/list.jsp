<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h3>전국산목록</h3>
<div class='btnSet'>
	<a href='new.lo' class='btn-fill'>산 등록</a>
</div>
<table class='w-px600 tb-list'>
<thead>
	<tr><th class='w-px140'>산이름</th>
		<th>추가설명</th>
		<th class='w-px160'>위도</th>
		<th class='w-px160'>경도</th>
	</tr>
</thead>
<tbody>
<c:forEach items='${list}' var='vo'>
	<tr><td><a href='info.lo?id=${vo.id}'>${vo.locname }</a></td>
		<td>${vo.name_desc }</td>
		<td>${vo.latitude }</td>
		<td>${vo.longitude }</td>
	</tr>
</c:forEach>
	
</tbody>
</table>

</body>
</html>
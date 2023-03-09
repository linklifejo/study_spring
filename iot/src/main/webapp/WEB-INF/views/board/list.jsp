<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h3>방명록 목록</h3>

<div id='list-top' class='w-px1200'>
<ul>
	<!-- 로그인된 경우 새글쓰기 가능 -->
	<c:if test="${not empty loginInfo }">
	<li><a class='btn-fill' href='new.bo'>새글쓰기</a></li>
	</c:if>
</ul>

</div>

<table class='w-px1200 tb-list'>
<colgroup>
	<col width='100px'>
	<col>
	<col width='140px'>
	<col width='140px'>
</colgroup>
<tr><th>번호</th>
	<th>제목</th>
	<th>작성자</th>
	<th>작성일자</th>
</tr>
<c:forEach items='${page.list}' var='vo'>
<tr><td>${vo.no }</td>
	<td class='txt-left'>${vo.title }</td>
	<td>${vo.name }</td>
	<td>${vo.writedate }</td>
</tr>
</c:forEach>
</table>
</body>
</html>
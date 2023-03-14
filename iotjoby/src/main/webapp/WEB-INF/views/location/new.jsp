<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h3>전국산 등록</h3>
	<form method="post" action='insert.lo'>
	<table class='w-px600'>
	<colgroup>
		<col width="140px">
		<col>
	</colgroup>
	<tr><th>산이름</th>
		<td><input type='text' name='name' ></td>
	</tr>
	<tr><th>추가설명</th>
		<td><input type='text' name='name_desc' ></td>
	</tr>
	<tr><th>좌표</th>
		<td><input type='text' name='position' ></td>
	</tr>
	<tr><th>우편번호</th>
		<td><input type='text' name='post' ></td>
	</tr>
	</table>
	</form>

	<div class='btnSet'>
		<a class='btn-fill' onclick="$('form').submit()">저장</a>
		<a class='btn-empty' href='list.cu'>취소</a>
	</div>

</body>
</html>
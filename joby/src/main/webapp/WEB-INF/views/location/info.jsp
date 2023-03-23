<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h3>전국산 정보</h3>
	<table class='w-px600'>
	<colgroup>
		<col width="140px">
		<col>
	</colgroup>
	<tr><th>산이름</th>
		<td>${vo.locname }</td>
	</tr>
	<tr><th>추가설명</th>
		<td>${vo.name_desc }</td>
	</tr>
	<tr><th>위도</th>
		<td>${vo.latitude }</td>
	</tr>
	<tr><th>경도</th>
		<td>${vo.longitude }</td>
	</tr>
	<tr><th>주소</th>
		<td>${vo.address }</td>
	</tr>
	</table>

	<div class='btnSet'>
		<a class='btn-fill' href='list.lo'>전국산목록</a>
		<a class='btn-fill' href='modify.lo?id=${vo.id}'>정보수정</a>
		<a class='btn-empty' onclick='fn_delete()'>정보삭제</a>
	</div>

<script>
function fn_delete(){
	if( confirm('[ ${vo.locname} ] 정말 삭제?') ){
	 	location.href='delete.lo?id=${vo.id}'
	}
}
</script>

</body>
</html>
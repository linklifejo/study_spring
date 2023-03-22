<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h3>코스정보</h3>
	<table class='w-px600'>
	<colgroup>
		<col width="140px">
		<col>
	</colgroup>
	<tr><th>코스명</th>
		<td>${vo.name }</td>
	</tr>
	<tr><th>구분</th>
		<td>${vo.type }</td>
	</tr>
	<tr><th>산이름</th>
		<td>${vo.location_name }</td>
	</tr>
	</table>

	<div class='btnSet'>
		<a class='btn-fill' href='list.co'>고객목록</a>
		<a class='btn-fill' href='modify.co?id=${vo.id}'>정보수정</a>
		<a class='btn-empty' onclick='fn_delete()'>정보삭제</a>
	</div>

<script>
function fn_delete(){
	if( confirm('[ ${vo.name} ] 정말 삭제?') ){
	 	location.href='delete.co?id=${vo.id}'
	}
}
</script>

</body>
</html>
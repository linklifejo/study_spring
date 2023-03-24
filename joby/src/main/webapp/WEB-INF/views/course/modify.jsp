<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h3>코스정보수정</h3>
	<form method="post" action='update.co'>
	<table class='w-px600'>
	<colgroup>
		<col width="140px">
		<col>
	</colgroup>
	<tr><th>코스명</th>
		<td><input type='text' name='couname' value='${vo.couname }'></td>
	</tr>
	<tr><th>구분</th>
		<td>
			<label><input type='radio' name='type' value='1'>위험</label>
			<label><input type='radio' checked name='type' value='2'>A코스</label>
			<label><input type='radio' name='type' value='3'>B코스</label>
			<label><input type='radio' checked name='type' value='4'>C코스</label>
		</td>
	</tr>
	
	<tr><th>산 선택</th>
	<td><select class='w-px120' name='viewType'>
		<c:forEach items="${list}" var="vo">
		<option value='vo.location_id'>${vo.locname}</option>
		</c:forEach>
		</select>
	</td>
	</tr>
	</table>
	<input type='hidden' name='id' value='${vo.id}'>
	</form>

	<div class='btnSet'>
		<a class='btn-fill' onclick="$('form').submit()">저장</a>
		<a class='btn-empty' href='info.co?id=${vo.id}'>취소</a>
	</div>

<script>
function fn_delete(){
	if( confirm('[ ${vo.corname} ] 정말 삭제?') ){
	 	location.href='delete.co?id=${vo.id}'
	}
}
</script>

</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h3>전국산정보수정</h3>
	<form method="post" action='update.lo'>
	<table class='w-px600'>
	<colgroup>
		<col width="140px">
		<col>
	</colgroup>
	<tr><th>산이름</th>
		<td><input type='text' name='name' value='${vo.name }'></td>
	</tr>
	<tr><th>추가설명</th>
	
           <td><input type='text' name='name_desc' value='${vo.name_desc }'></td>
	
	</tr>
	<tr><th>좌표</th>
		<td><input type='text' name='position' value='${vo.position }'></td>
	</tr>
	<tr><th>우편코드</th>
		<td><input type='text' name='post' value='${vo.post }'></td>
	</tr>
	</table>
	<input type='hidden' name='no' value='${vo.no}'>
	</form>

	<div class='btnSet'>
		<a class='btn-fill' onclick="$('form').submit()">저장</a>
		<a class='btn-empty' href='info.lo?no=${vo.no}'>취소</a>
	</div>

<script>
function fn_delete(){
	if( confirm('[ ${vo.name} ] 정말 삭제?') ){
	 	location.href='delete.cu?no=${vo.no}'
	}
}
</script>

</body>
</html>
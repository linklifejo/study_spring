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
<h3>회원가입</h3>

<form method='post' action='joinRequest'>
<table border='1' style='width:400px'>
<tr><th style='width:140px'>성명</th>
	<td><input type='text' name='name'></td>
</tr>
<tr><th>성별</th>
	<td>
		<label><input type='radio' name='gender' checked value='남'>남</label>
		<label><input type='radio' name='gender' value='여'>여</label>
	</td>
</tr>
<tr><th>이메일</th>
	<td><input type='text' name='email'></td>
</tr>
</table>
<input type='submit' value='HttpServletRequest방식'>
<input type='submit' value='@RequestParam방식' 
			onclick="action='joinParam'">
<input type='submit' value='데이터객체방식' 
			onclick="action='joinData'">
<input type='submit' value='@PathVariable방식' 
			onclick="go_submit(this.form)">
			
</form>

<script>
function go_submit(f){
	f.action = 'joinPath/' 
			+ f.name.value + '/' + f.gender.value 
			+ '/' + f.email.value;
}

</script>
</body>
</html>
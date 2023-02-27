<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
table tr td{ text-align: left }
[name=address] { margin-top: 3px }
th span, p span { color:#ff0000; margin-right: 5px; }
p { margin: 10px auto;  text-align: right;}
</style>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
</head>
<body>
<h3>회원가입</h3>
<p class='w-px600'><span>*</span>는 필수입력항목입니다</p>
<form method='post' action='join'>
<table class='w-px600'>
<tr><th class='w-px140'><span>*</span>이름</th>
	<td><input type='text' name='name'> </td>
</tr>
<tr><th><span>*</span>아이디</th>
	<td><input type='text' name='id'></td>
</tr>
<tr><th><span>*</span>비밀번호</th>
	<td><input type='password' name='pw'></td>
</tr>
<tr><th><span>*</span>비밀번호 확인</th>
	<td><input type='password' name='pw_ck'></td>
</tr>
<tr><th><span>*</span>성별</th>
	<td><label>
			<input type='radio' name='gender' checked value='남'>남
		</label>
		<label>
			<input type='radio' name='gender' value='여'>여
		</label>
	</td>
</tr>
<tr><th><span>*</span>이메일</th>
	<td><input type='text' name='email'></td>
</tr>
<tr><th>프로필이미지</th>
	<td><label>
		<input type='file' name='file' accept="image/*" id='attach-file'>
		<a><i class="font-img-b fa-solid fa-file-circle-plus"></i></a>
		</label>
		<span id='preview'></span>
	</td>
</tr>
<tr><th>생년월일</th>
	<td><input type='text' name='birth' class='date' readonly>
		<a id='btn-delete'><i class="font-img-r fa-regular fa-circle-xmark"></i></a>
	</td>
</tr>
<tr><th>전화번호</th>
	<td><input type='text' name='phone' maxlength="13"></td>
</tr>
<tr><th>주소</th>
	<td><a class='btn-fill btn-post'>우편번호찾기</a>
		<input type='text' name='post' class='w-px60' readonly>
		<input type='text' name='address' class='full' readonly>
		<input type='text' name='address' class='full'>
	</td>
</tr>
</table>
</form>
<script>
/* 만13세이상 회원가입하도록 생년월일 선택 날짜를 제한  */
var today = new Date();
var endDay = new Date( today.getFullYear()-13, today.getMonth()
						, today.getDate()-1 );
var range = today.getFullYear() - 80 + ":" + endDay.getFullYear();						
$('.date').datepicker({
	maxDate: endDay,
	yearRange: range
});					
 
$('.date').change(function(){
	$(this).next().css('display', 'inline')
});
$('#btn-delete').click(function(){
	$('.date').val('');
	$(this).css('display', 'none')
});

$('.btn-post').on('click', function(){
	new daum.Postcode({
        oncomplete: function(data) {
        	console.log(data);
        	$('[name=post]').val( data.zonecode );
        	
        	var address
        		= data.userSelectedType=='R' 
        		? data.roadAddress : data.jibunAddress;
        	if( data.buildingName!='' )
        		address += ' ('+data.buildingName + ')';
        	
			$('[name=address]:eq(0)').val( address );
			//$('[name=address]').eq(0).val( address );
        	
        }
    }).open();
});
</script>

</body>
</html>




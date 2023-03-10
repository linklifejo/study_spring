<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
table td { text-align: left; }
</style>
</head>
<body>
<h3>방명록 안내</h3>
<table class='w-px1200'>
<colgroup>
	<col width='140px'>
	<col>
	<col width='140px'>
	<col width='140px'>
	<col width='100px'>
	<col width='100px'>
</colgroup>
<tr><th>제목</th>
	<td colspan='5'>${vo.title}</td>
</tr>
<tr><th>작성자</th>
	<td>${vo.name}</td>
	<th>작성일자</th>
	<td>${vo.writedate}</td>
	<th>조회수</th>
	<td>${vo.readcnt}</td>
</tr>
<tr><th>내용</th>
	<td colspan='5'>${fn: replace(vo.content, crlf, '<br>')}</td>
</tr>
<tr><th>첨부파일</th>
	<td colspan='5'>
	<c:forEach items='${vo.fileInfo}' var='f'>
	<div class='align'>
		<span>${f.filename}
<%-- 			<a href='download.bo?id=${f.id}'><i class="font-img-b fa-solid fa-file-arrow-down"></i></a> --%>
			<a class='download' data-file='${f.id}'><i class="font-img-b fa-solid fa-file-arrow-down"></i></a>
		</span>
		<span class='preview'></span>
	</div>
	</c:forEach>
	</td>
</tr>
</table>
<div class='btnSet'>
<a class='btn-fill' id='list'>목록으로</a>
<c:if test="${vo.writer eq loginInfo.id}">
<a class='btn-fill' id='modify'>정보수정</a>
<a class='btn-fill' id='delete'>정보삭제</a>
</c:if>
</div>

<form method='post' action='download.bo'>
<input type='hidden' name='file'>
<input type='hidden' name='id' value='${vo.id}'>
<input type='hidden' name='curPage' value='${page.curPage }'>
<input type='hidden' name='search' value='${page.search }'>
<input type='hidden' name='keyword' value='${page.keyword }'>
<input type='hidden' name='pageList' value='${page.pageList }'>
<input type='hidden' name='viewType' value='${page.viewType }'>
</form>

<script>
$('#list, #delete, #modify').click(function(){	
	$('form').attr('action', $(this).attr('id') + '.bo')
	if( $(this).attr('id')=='delete' ){
		if( confirm('정말 삭제?') ){
			$('form').submit();			
		}
	}else
		$('form').submit();
});

$('.download').click(function(){
	$('[name=file]').val( $(this).data('file') )
	$('form').submit();
});

<c:forEach items="${vo.fileInfo}" var='f' varStatus='state'>
if( isImage( '${f.filename}' ) ){
	$('.preview').eq( ${state.index}).html( '<img src="${f.filepath}">' )
}
</c:forEach>

</script>
</body>
</html>













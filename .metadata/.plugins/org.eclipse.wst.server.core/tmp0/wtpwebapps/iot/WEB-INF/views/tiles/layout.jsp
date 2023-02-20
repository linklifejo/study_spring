<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<c:choose>
<c:when test="${category eq 'cu'}"><c:set var='title' value='고객관리'/></c:when> 
<c:when test="${category eq 'hr'}"><c:set var='title' value='사원관리'/></c:when>
<c:when test="${category eq 'no'}"><c:set var='title' value='공지사항'/></c:when>
<c:when test="${category eq 'bo'}"><c:set var='title' value='방명록'/></c:when>
<c:when test="${category eq 'da'}"><c:set var='title' value='공공데이터'/></c:when>
<c:when test="${category eq 'vi'}"><c:set var='title' value='시각화'/></c:when>
</c:choose>


<title>지능형 IoT ${title}</title>
<link rel='stylesheet' type='text/css' 
		href='css/common.css?<%=new java.util.Date()%>'>
<!-- <script src='js/jquery-3.6.3.min.js'></script>		 -->
<script src='https://code.jquery.com/jquery-3.6.3.min.js'></script>
</head>
<body>
<!-- header부분 -->
<tiles:insertAttribute name="header"/>
<div id='container'>
<tiles:insertAttribute name="container"/>
</div>
<!-- footer부분 -->
<tiles:insertAttribute name="footer"/>
</body>
</html>
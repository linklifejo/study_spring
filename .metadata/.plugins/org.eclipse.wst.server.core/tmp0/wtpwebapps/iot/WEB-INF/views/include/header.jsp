<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<header>
	<nav>
		<ul>
			<li><a href='<c:url value="/"/>'><img src='imgs/hanul.logo.png'></a></li>
			<li><a ${category eq 'cu' ? "class='active'" : ''} href='list.cu'>고객관리</a></li>
			<li><a ${category eq 'hr' ? "class='active'" : ''} href='list.hr'>사원관리</a></li>
			<li><a>공지사항</a></li>
			<li><a>방명록</a></li>
			<li><a>공공데이터</a></li>
			<li><a>시각화</a></li>
		</ul>
	</nav>
</header>
<style>
header { 
	border-bottom: 1px solid #aaa;
	display: flex; align-items: center; justify-content: space-between;
	padding: 0 100px;
}
header nav ul { font-size: 18px; font-weight: bold; }
header ul { display: flex; }
header ul li:not(:first-child) { margin-left: 50px }
header nav a:hover, header nav a.active { color:#0730fa  }
</style>

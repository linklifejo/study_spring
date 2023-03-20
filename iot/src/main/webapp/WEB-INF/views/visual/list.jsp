<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
#tabs { display: flex; border-bottom: 1px solid #3367d6; overflow: hidden; }
#tabs li {
	width: 140px; line-height: 40px; color: #3367d6;
	border: 1px solid #3367d6; border-bottom: none; cursor: pointer;
}
#tabs li:not(:first-child) { border-left: none;  margin-left: 0 }
#tabs li.active { color: #fff; background-color: #3367d6; }
#tab-content { width: 1200px;  height: 550px; margin: 20px auto; }
</style>
<link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/c3/0.7.20/c3.min.css'>
</head>
<body>
<h3>사원정보분석</h3>
<div class='w-px1200' style='margin: 0 auto'>
	<ul id='tabs'>	
		<li>부서원수</li>
		<li>채용인원수</li>
	</ul>
</div>
<div id='tab-content'>
	<div id='graph'></div>
</div>
<script src="https://cdnjs.cloudflare.com/ajax/libs/c3/0.7.20/c3.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/d3/5.16.0/d3.min.js"></script>
<script>
$('#tabs li').click(function(){
	if( $(this).hasClass('active') ) return;
	$('#tabs li').removeClass();
	$(this).addClass('active');
	
	var idx = $(this).index();
	if( idx==0 )  department();//부서원수
	else if( idx==1 ) hirement(); //채용인원수
	
});

function hirement(){
	
}

function department(){
	
	make_chart();
}

function make_chart(){
	c3.generate({
		bindto: '#graph',
	    data: {
	        columns: [
	            ['부서원수', 30, 200, 100, 400, 150, 250],
	        ],
	        type: 'line',
	    },
	    size: { height: 450 }
	});
}

$(function(){
	$('#tabs li').eq(0).trigger( 'click' );
});

</script>

</body>
</html>
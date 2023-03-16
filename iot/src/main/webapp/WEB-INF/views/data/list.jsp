<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
.pharmacy, #list-top { width: 1200px }
.pharmacy td:nth-child(3) { text-align: left; }
#popup { width: 800px; height: 600px; }
</style>
</head>
<body>
<h3>공공데이터</h3>
<div class='btnSet api'>
	<a>약국정보</a>
	<a>유기동물정보</a>
</div>
<div id='list-top'>
	<ul class='common'>
		<li><select class='w-px100' id='pageList'>
			<c:forEach var='i' begin='1' end='5'>
			<option value='${10*i}'>${10*i}개씩</option>
			</c:forEach>
			</select>
		</li>
	</ul>
</div>
<div id='data-list'></div>
<div class='btnSet'>
	<div class='page-list'></div>
</div>
<div class='loading center'><img src='imgs/loading.gif'></div>
<div id='popup-background'></div>
<div id='popup' class='center'></div>

<script type="text/javascript" 
	src="//dapi.kakao.com/v2/maps/sdk.js?appkey=295364ba6aaf47ea28434befac53e135"></script>
<script>
$(window).scroll(function(){
	center( $('#popup'), $('#popup-background') );
});

$(window).resize(function(){
	center( $('#popup'), $('#popup-background') );
});

$('.api a').click(function(){
	$('.api a').removeClass();
	$(this).addClass('btn-fill');
	$('.api a').not( $(this) ).addClass('btn-empty');
	
	if( $(this).index()==0 ) pharmacy_list( 1 );
	else					 animal_list();
})

//유기동물정보조회
function animal_list(){
	$('#data-list').html('');
}

$('#pageList').change(function(){
	pageList = $(this).val();
	pharmacy_list(1);	
});

//약국정보조회
function pharmacy_list( page ){
	loading(true);
	var tag
	= "<table class='tb-list pharmacy'>"	
	+ "<thead><tr><th class='w-px300'>약국명</th>"
	+ "<th class='w-px160'>전화번호</th>"
	+ "<th>주소</th>"
	+ "</tr>"
	+ "</thead>"
	+ "</table>";	
	$('#data-list').html( tag );
	
	tag = '';
	$.ajax({
		data: {pageNo: page, rows:pageList},
		url: 'data/pharmacy',
		success: function( response ){
			console.log( response );
			$(response.item).each(function(){
				tag += "<tr><td><a class='map'>"+ this.yadmNm +"</a></td>"
					+  "<td>"+ (this.telno ? this.telno : '-') +"</td>"
					+  "<td>"+ this.addr +"</td>"
					+  "</tr>";	
			});
			$('#data-list table thead').append( tag );
			makePage( response.count, page );
			
			loading(false);
		},error: function(){
			loading(false);
		}
	});
}

var pageList = 10, blockPage = 10;
function makePage( totalList, curPage ){
	var tag = '';
	var totalPage = Math.ceil( totalList / pageList ); //24463 /10: 2447
	var totalBlock = Math.ceil( totalPage / blockPage ); //2447 /10 ; 245
	var curBlock = Math.ceil( curPage / blockPage );
	var endPage = curBlock * blockPage;		
	var beginPage = endPage - (blockPage-1);
	if( endPage > totalPage ) endPage = totalPage;
	
	if( curBlock > 1 ){
		tag += '<a data-page="1"><i class="fa-solid fa-angles-left"></i></a>'
			+  '<a data-page="'+ (beginPage-blockPage) +'"><i class="fa-solid fa-angle-left"></i></a>';
	}
	
	for(var no=beginPage; no<=endPage; no++){
		if( no==curPage ) tag += '<span>'+ no +'</span>';
		else              tag += '<a data-page="'+ no +'">'+ no +'</a>';
	}
	if( totalBlock > curBlock ){
		tag += '<a data-page="'+ (endPage+1) +'"><i class="fa-solid fa-angle-right"></i></a>'
			+  '<a data-page="'+ totalPage +'"><i class="fa-solid fa-angles-right"></i></a>';
	}
	
	$('.page-list').html( tag );
}

$(function(){
	$('.api a').eq(0).trigger('click');	
});

$(document).on('click', '.page-list a', function(){
	pharmacy_list( $(this).data('page') );
	
}).on('click', '.map', function(){
	$('#popup, #popup-background').css('display', 'block');
	
	//295364ba6aaf47ea28434befac53e135
	
}).on('click', '#popup-background', function(){
	$('#popup, #popup-background').css('display', 'none');
	
});
</script>
</body>
</html>
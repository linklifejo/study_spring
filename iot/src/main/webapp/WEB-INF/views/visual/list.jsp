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

.c3-chart, .c3-axis { font-size: 15px }
#legend { display: flex; justify-content: center; }
#legend li { display: flex; align-items: center; }
#legend li:not(:first-child) { margin-left: 30px }
.legend { width: 15px; height: 15px; margin-right: 5px }
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
	<div class='tab'>
		<label><input type='radio' name='graph' checked value='bar'>막대그래프</label>
		<label><input type='radio' name='graph' value='donut'>도넛그래프</label>
	</div>
	<div class='tab'>
		<label><input type='radio' name='unit' value='year' checked>년도별</label>
		<label><input type='radio' name='unit' value='month'>월별</label>
	</div>
	
	<div id='graph'></div>
	<ul id='legend'>
		<li><span class='legend'></span><span>0~9명</span></li>
		<li><span class='legend'></span><span>10~19명</span></li>
		<li><span class='legend'></span><span>20~29명</span></li>
		<li><span class='legend'></span><span>30~39명</span></li>
		<li><span class='legend'></span><span>40~49명</span></li>
		<li><span class='legend'></span><span>50명 이상</span></li>
	</ul>
</div>
<script src="https://cdnjs.cloudflare.com/ajax/libs/c3/0.7.20/c3.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/d3/5.16.0/d3.min.js"></script>
<script>
$('#tabs li').click(function(){
	if( $(this).hasClass('active') ) return;
	$('#tabs li').removeClass();
	$(this).addClass('active');
	
	var idx = $(this).index();
	$('.tab').css('display', 'none');
	$('.tab').eq(idx).css('display', 'block');
	if( idx==0 )  department();//부서원수
	else if( idx==1 ) hirement(); //채용인원수
	
});

//채용인원수 그래프
function hirement(){
	init();
	
	var unit = $('[name=unit]:checked').val();
	$.ajax({
		url: '<c:url value="/visual/hirement/"/>' + unit,
		success: function( response ){
			//console.log( response )
			var name = [ unit ], count = [ '채용인원수' ];
			$(response).each(function(idx, item){
				name.push( this.UNIT )
				count.push( this.COUNT )
			})
			make_chart_hirement( [ name, count] )
		}
	})
}

function make_chart_hirement( info ){
	console.log( info )
	c3.generate({
		bindto: '#graph',
		data: { columns: info, type: 'bar' }
	})
}



$('[name=unit]').change(function(){
	hirement();
})

//부서별사원수에 대한 그래프형태 변경(막대/도넛)
$('[name=graph]').change(function(){
	init();
	department();
});

function init(){
	$('#legend').css('display', 'none');
	$('#graph').empty();
}

function department(){
	init();
	$.ajax({
		url: '<c:url value="/visual/department"/>',
		success: function( response ){
// 			console.log( response)
			var count = [ '부서원수' ], name = [ '부서명' ], info = []; //['부서원수', 30, 200, 100, 400, 150, 250]
			$(response).each(function(){
				count.push( this.COUNT );
				name.push( this.DEPARTMENT_NAME );
				info.push( new Array( this.DEPARTMENT_NAME, this.COUNT)  )
			})
// 			console.log( 'count: ', count )
// 			console.log( 'name: ', name )
			
			if( $('[name=graph]:checked').val()=='bar')
				bar_chart( [ name, count ] ); 		//선/막대그래프
			else
 				donut_chart( info ); 				//파이/도넛그래프

			//[ ['data1', 30],   ['data2', 120], ]
		}
	});
}

function make_chart( info ){
	//1.선그래프(기본)
	//line_chart( info );
	
	//2.파이그래프
	//pie_chart( info );
	
	//3.도넛그래프
	//donut_chart( info );
	
	//4.막대그래프
	bar_chart( info );
}

var colors = [ '#00ad2e',  '#d406a0',  '#1a0acc',  '#eded07',  '#07edcb',  '#ed7505'
		,  '#05acfa',  '#c90446',  '#63050d',  '#f5836c',  '#7d92f0',  '#014a0b'
		,  '#8a6769',  '#e3e29d',  '#7bc7c1' ];

function bar_chart( info ){
	c3.generate({
		bindto: '#graph',
		data: { columns: info, type:'bar', x: '부서명', labels: true,
				color: function(color, data){
					//return colors[data.index];  //각 부서별 색상을 다르게
					return colors[ Math.floor(data.value/10) ] //사원수 범위별로 색상을 다르게
				}			
		}, //데이터값이 라벨로 보이게
		size: { height:450 },
		axis: {
			x: { type:'category' },
			y: { label:{ text:'부서원수', position: 'outer-middle' } },
		},
		bar: { width: 30 }, //막대굵기
		grid: { y: { show: true } }, //배경그리드 y축 보이게
		legend: { hide: true }, //범례 보이지않게
	})
	
	$('#legend').css('display', 'flex');
}

function donut_chart( info ){
	c3.generate({
		bindto: '#graph',
		data: { columns: info, type: 'donut' },
		size: { height: 450 },
		padding : { bottom: 50 },
		donut: {
			label: {
				format: function(v, r, id){
					return (r*100).toFixed(2) + '%('+ v +'명)';
				}
			},
			width: 100, 	//도넛의 두께
			title: '부서원수' //도넛 내부에 타이틀표현
		}
	})	
	$('#legend').css('display', 'none');
}

function pie_chart( info ){
	c3.generate({
		bindto: '#graph',
		data: { columns: info, type: 'pie' },
		size: { height: 450 },
		padding: { bottom:50 }, //차트와 범례간 여백
		pie: {
			label: {
				format: function(value, ratio, id){
					return (ratio*100).toFixed(1) + '%('+ value +'명)';
				}
			}
		}
	})	
}

function line_chart( info ){
	c3.generate({
		bindto: '#graph',
	    data: {
	        columns: info,
	        type: 'line',
	        x: '부서명',
	    },
	    axis: {
	    	x: { type: 'category' }
	    },
	    size: { height: 450 }
	});
}

$(function(){
	$('#tabs li').eq(0).trigger( 'click' );
	$('.legend').each(function(idx){
		$(this).css('background-color', colors[idx])
	})
});

</script>

</body>
</html>
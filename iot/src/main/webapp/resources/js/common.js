/**
 * 공통 함수
 */
 
$( function() {
	var today = new Date();
	var range = today.getFullYear()-100 + ':' + today.getFullYear();	
	
	$.datepicker.setDefaults({
		dateFormat: 'yy-mm-dd',
		dayNamesMin: [ '일', '월', '화', '수', '목', '금', '토' ],
		monthNamesShort: [ '1월', '2월', '3월', '4월', '5월', '6월'
					, '7월', '8월', '9월', '10월', '11월', '12월' ],
		 
		changeMonth: true,	
		changeYear: true,
		showMonthAfterYear: true,	
		maxDate: today,	
		yearRange: range,					
	});

    $( ".date" ).datepicker({
		
	});
    
}); 
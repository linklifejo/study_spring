/**
 * 공통 함수
 */
 
function emptyCheck(){
	var ok = true;
	$('.chk').each(function(){
		if( $(this).val()=='' ){
			var item = $(this).attr('placeholder');
			alert(item + ' 입력하세요!');
			$(this).focus();
			ok = false;
			return ok;
		}		
	});
	return ok;
} 
 
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

	//첨부파일 선택
    $('#attach-file').change(function(){
		console.log( this.files[0] );
		var attached = this.files[0];
		if( attached ){  //선택한 파일이 있는 경우
			
			if( $('#preview').length==1 ){ //미리보기할 태그가 있는 경우
				//해당 이미지파일 정보를 읽어서 화면에 img 태그로 만든다
				if( isImage( attached.name ) ){
					$('#preview').html('<img>');
					var reader = new FileReader();
					reader.onload = function( e ){						
						$('#preview img').attr('src', e.target.result );
					}
					reader.readAsDataURL( attached );
				}
				
			}
			
		}
	
	});
    
    
}); 

//이미지 파일인지 판단
function isImage( filename ){
	// abc.abc.txt, abc.png, abc.jpg, abc.PNG ...
	var ext = filename.substring( filename.lastIndexOf(".")+1 )
				.toLowerCase();
				
	var images = [ "jpg", "jpeg", "png", "bmp", "gif", "webp" ];
	return images.indexOf( ext ) == -1 ? false : true;			
}









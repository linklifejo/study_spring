/**
 * 회원정보 관련 입력값 체크처리
 */
 
var member = {
	tag_status: function( tag ){
		var name = tag.attr('name');
		if( name=='pw' )			return this.pw_status( tag.val() );
		else if( name=='pw_ck' )	return this.pw_ck_status( tag.val() );
	},
	
	pw_status: function( pw ){
		var reg = /[^a-zA-Z0-9]/g, upper = /[A-Z]/g, lower=/[a-z]/g, digit=/[0-9]/g;
		if( pw=='' ) return this.common.empty;
		else if( pw.match(this.space) )	return this.common.space;
		else if( reg.test(pw) )			return this.pw.invalid;
		else if( pw.length<5 )			return this.common.min;
		else if( pw.length>10 )			return this.common.max;
		else if( !upper.test(pw) || !lower.test(pw) 
					|| !digit.test(pw) ) return this.pw.lack;
		else 							return this.pw.valid;
	},
	
	space: /\s/g,
	
	pw_ck_status: function( pw_ck ){
		if( pw_ck=='' )	return this.common.empty;
		else if( pw_ck==$('[name=pw]').val() )  return this.pw.equal;
		else 									return this.pw.notEqual;
	},
	
	pw: {
		equal: { code:'valid', desc:'비밀번호가 일치함' },	
		notEqual: { code:'invalid', desc:'비밀번호가 일치하지 않습니다' },	
		lack: { code:'invalid', desc:'영문대/소문자,숫자 모두 포함해야 함' },	
		valid: { code:'valid', desc:'사용가능한 비밀번호' },	
		invalid: { code:'invalid', desc:'영문대/소문자,숫자만 입력' },
	},
	
	common: {
		empty: { code:'invalid', desc:'입력하세요' },
		space: { code:'invalid', desc:'공백없이 입력하세요' },
		min: { code:'invalid', desc:'5자이상 입력하세요' }, 
		max: { code:'invalid', desc:'10자이내 입력하세요' }, 
	} 
	
	
	
	
} 
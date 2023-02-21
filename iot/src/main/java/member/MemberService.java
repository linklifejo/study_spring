package member;

import java.util.HashMap;
import java.util.List;

public interface MemberService {
	//CRUD
	int member_join(MemberVO vo); //회원가입시 신규저장
	MemberVO member_myinfo(String id); //내정보보기(내프로필) 회원정보조회
	List<MemberVO> member_list(); //회원목록조회-관리자
	MemberVO member_login(HashMap<String, String> map); //로그인처리
	int member_update(MemberVO vo); //회원정보변경
	int member_delete(String id); //회원탈퇴
}

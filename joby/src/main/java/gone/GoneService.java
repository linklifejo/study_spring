package gone;

import java.util.List;

public interface GoneService {
	//CRUD
	int gone_insert(GoneVO vo);//방명록 새글저장
	GonePageVO gone_list(GonePageVO vo); //방명록 목록 조회
	GoneVO gone_info(int id); //선택한 방명록 글 조회
	int gone_read(int id); 	//선택한 방명록 글 조회수 변경	
	int gone_update(GoneVO vo); //선택한 방명록 정보수정저장
	int gone_delete(int id); //선택한 방명록 정보삭제
	
	GoneFileVO gone_file_info(int id); //첨부파일정보 조회
	List<GoneFileVO> gone_removed_file( String removed ); //삭제하려는 첨부파일정보 조회
	int gone_file_delete(String removed); //변경첨부/삭제한 파일정보 삭제
	
	int gone_comment_regist(GoneCommentVO vo); //댓글신규저장
	int gone_comment_update(GoneCommentVO vo); //댓글변경저장
	int gone_comment_delete(int id); //댓글삭제
	List<GoneCommentVO> gone_comment_list(int gone_id); //댓글목록조회
}

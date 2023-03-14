package board;

import java.util.List;

public interface BoardService {
	//CRUD
	int board_insert(BoardVO vo);//방명록 새글저장
	BoardPageVO board_list(BoardPageVO vo); //방명록 목록 조회
	BoardVO board_info(int id); //선택한 방명록 글 조회
	int board_read(int id); 	//선택한 방명록 글 조회수 변경	
	int board_update(BoardVO vo); //선택한 방명록 정보수정저장
	int board_delete(int id); //선택한 방명록 정보삭제
	
	BoardFileVO board_file_info(int id); //첨부파일정보 조회
	List<BoardFileVO> board_removed_file( String removed ); //삭제하려는 첨부파일정보 조회
	int board_file_delete(String removed); //변경첨부/삭제한 파일정보 삭제
	
	int board_comment_regist(BoardCommentVO vo); //댓글신규저장
	int board_comment_update(BoardCommentVO vo); //댓글변경저장
	int board_comment_delete(int id); //댓글삭제
	List<BoardCommentVO> board_comment_list(int board_id); //댓글목록조회
}

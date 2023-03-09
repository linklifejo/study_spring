package board;

public interface BoardService {
	//CRUD
	int board_insert(BoardVO vo);//방명록 새글저장
	BoardPageVO board_list(BoardPageVO vo); //방명록 목록 조회
	BoardVO board_info(int id); //선택한 방명록 글 조회
	int board_read(int id); 	//선택한 방명록 글 조회수 변경	
	int board_update(BoardVO vo); //선택한 방명록 정보수정저장
	int board_delete(int id); //선택한 방명록 정보삭제
	
	BoardFileVO board_file_info(int id); //첨부파일정보 조회
	
}

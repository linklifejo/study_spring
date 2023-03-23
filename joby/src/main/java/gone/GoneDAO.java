package gone;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class GoneDAO implements GoneService {
	@Autowired @Qualifier("bteam") private SqlSession sql;
	
	@Override
	public int gone_insert(GoneVO vo) {
		//방명록 글을 저장한 후
		int dml = sql.insert("go.insert", vo);
		//해당 글에 첨부된 파일이 있으면 파일정보를 저장한다
		if( dml > 0 && vo.getFileInfo() != null )
			sql.insert("go.fileInsert", vo);
		return dml;
	}

	@Override
	public GonePageVO gone_list(GonePageVO vo) {
		vo.setTotalList( sql.selectOne("go.totalCount", vo) );
		vo.setList( sql.selectList("go.list", vo) );
		return vo;
	}

	@Override
	public GoneVO gone_info(int id) {
		GoneVO vo = sql.selectOne("go.info", id);
		vo.setFileInfo( sql.selectList("go.fileList", id) );
		return vo;
	}

	@Override
	public int gone_read(int id) {
		return sql.update("go.read", id);
	}

	@Override
	public int gone_update(GoneVO vo) {
		if( vo.getFileInfo()!=null )
			sql.insert("go.fileInsert", vo);
		return sql.update("go.update", vo);
	}

	@Override
	public int gone_delete(int id) {
		return sql.delete("go.delete", id);
	}

	@Override
	public GoneFileVO gone_file_info(int id) {
		return sql.selectOne("go.fileInfo", id);
	}

	@Override
	public List<GoneFileVO> gone_removed_file(String removed) {
		return sql.selectList("go.fileRemoved", removed);
	}

	@Override
	public int gone_file_delete(String removed) {
		return sql.delete("go.fileDelete", removed);
	}

	@Override
	public int gone_comment_regist(GoneCommentVO vo) {
		return sql.insert("go.commentInsert", vo);
	}

	@Override
	public int gone_comment_update(GoneCommentVO vo) {
		return sql.update("go.commentUpdate", vo);
	}

	@Override
	public int gone_comment_delete(int id) {
		return sql.delete("go.commentDelete", id);
	}

	@Override
	public List<GoneCommentVO> gone_comment_list(int gone_id) {
		return sql.selectList("go.commentList", gone_id);
	}

}

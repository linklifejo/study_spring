package location;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class LocationDAO implements LocationService {
	@Autowired @Qualifier("bteam") private SqlSession sql;
	
	@Override
	public int location_insert(LocationVO vo) {
		return sql.insert("lo.insert", vo);
	}

	@Override
	public List<LocationVO> location_list() {
		return sql.selectList("lo.list");
	}

	@Override
	public LocationVO location_info(int id) {
		return sql.selectOne("lo.info", id);
	}

	@Override
	public int location_update(LocationVO vo) {
		return sql.update("lo.update", vo);
	}

	@Override
	public int location_delete(int id) {
		return sql.delete("lo.delete", id);
	}

}

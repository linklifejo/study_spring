package location;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationServiceImpl implements LocationService {
	@Autowired private LocationDAO dao;
	
	@Override
	public int location_insert(LocationVO vo) {
		return dao.location_insert(vo);
	}

	@Override
	public List<LocationVO> location_list() {
		return dao.location_list();
	}

	@Override
	public LocationVO location_info(int id) {
		return dao.location_info(id);
	}

	@Override
	public int location_update(LocationVO vo) {
		return dao.location_update(vo);
	}

	@Override
	public int location_delete(int id) {
		return dao.location_delete(id);
	}

}

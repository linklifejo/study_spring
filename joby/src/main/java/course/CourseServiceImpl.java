package course;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import location.LocationVO;

@Service
public class CourseServiceImpl implements CourseService {
	@Autowired private CourseDAO dao;
	
	@Override
	public int course_insert(CourseVO vo) {
		return dao.course_insert(vo);
	}

	@Override
	public List<CourseVO> course_list() {
		return dao.course_list();
	}

	@Override
	public CourseVO course_info(int id) {
		return dao.course_info(id);
	}

	@Override
	public int course_update(CourseVO vo) {
		return dao.course_update(vo);
	}

	@Override
	public int course_delete(int id) {
		return dao.course_delete(id);
	}

	@Override
	public List<LocationVO> location_list() {
		// TODO Auto-generated method stub
		return dao.location_list();
	}



}

package course;

import java.util.List;

public interface CourseService {
	//CRUD(Create, Read, Update, Delete)
	int course_insert(CourseVO vo);		
	List<CourseVO> course_list();		
	CourseVO course_info(int id);		
	int course_update(CourseVO vo);		
	int course_delete(int id); 			
}
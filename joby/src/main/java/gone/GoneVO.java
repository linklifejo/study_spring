package gone;

import java.sql.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GoneVO {
	private int id, readcnt, no, filecnt,location_id,course_id;
	private String title, content, type,member_id,name,locname,couname,gone_time,out_time;
	private Date writedate, gone_date;
	private List<GoneFileVO> fileInfo;
}

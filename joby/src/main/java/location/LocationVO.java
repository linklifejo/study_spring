package location;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LocationVO {
	private int id;
	private String type, locname, name_desc, post,address,latitude,longitude;

}

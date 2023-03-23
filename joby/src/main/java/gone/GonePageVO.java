package gone;

import java.util.List;

import common.PageVO;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GonePageVO extends PageVO {
	private List<GoneVO> list;
}

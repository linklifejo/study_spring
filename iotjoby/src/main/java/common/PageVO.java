package common;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PageVO {
	private String search, keyword, viewType="list"; //검색조건, 검색어, 보기형태
	private int pageList = 10; 		// 페이지당 보여질 목록 수	
	private int blockPage = 10; 	// 블럭당 보여질 페이지의 수
	private int totalList; 		//  총 목록수
	private int totalPage;  	// 총 페이지수 : 8 페이지 = 30 / 4 = 7 ... 2
	private int totalBlock;	 	// 총 블록수 : 3 블록 = 8 / 3 = 2 ...
	private int curPage = 1; 	// 현재 페이지번호
	// 각 페이지의 끝 목록번호 :  총 목록수 - (페이지번호-1) * 페이지당 보여질 목록수  
	// 각 페이지의 시작 목록번호 :  끝 목록번호 - (페이지당 보여질 목록수-1)
	private int endList, beginList;
	// 현재블록번호 : 페이지번호 / 블록당 보여질 페이지수
	private int curBlock;
	// 각 블럭의 끝 페이지번호 : 블록번호 * 블록당 보여질 페이지수
	// 각 블럭의 시작 페이지번호 : 끝 페이지번호 - (블럭당 보여질 페이지수-1)
	private int endPage, beginPage;
	
	public void setTotalList(int totalList) {
		this.totalList = totalList;
		// 총 페이지수 : 103 페이지 = 1024/ 10 = 102 ... 4
		totalPage = totalList / pageList;
		if( totalList % pageList > 0 ) ++totalPage;
		// 총 블록수 : 11 블록 = 103 / 10 = 10 ... 3
		totalBlock = totalPage / blockPage;
		if( totalPage % blockPage > 0) ++totalBlock;
		
		// 각 페이지의 끝 목록번호 :  총 목록수 - (페이지번호-1) * 페이지당 보여질 목록수  
		// 각 페이지의 시작 목록번호 :  끝 목록번호 - (페이지당 보여질 목록수-1)
		endList = totalList - (curPage-1) * pageList;
		beginList = endList - (pageList-1);
		
		// 현재블록번호 : 페이지번호 / 블록당 보여질 페이지수
		curBlock = curPage / blockPage;
		if( curPage % blockPage > 0) ++curBlock;
		
		// 각 블럭의 끝 페이지번호 : 블록번호 * 블록당 보여질 페이지수
		// 각 블럭의 시작 페이지번호 : 끝 페이지번호 - (블럭당 보여질 페이지수-1)
		endPage = curBlock * blockPage;		
		beginPage = endPage - (blockPage-1);
		
		if( totalPage < endPage ) endPage = totalPage;
	}
}

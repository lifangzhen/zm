package com.lun.mlm.model;

public class PageParam {

	public static final Integer PAGE_NUM=1;
	public static final Integer NUM_PER_PAGE=20;
	
	private Integer start;
	private Integer end;
	private Integer pageNum;
	private Integer numPerPage;
	
	
	public Integer getStart() {
		return start;
	}


	public void setStart(Integer start) {
		this.start = start;
	}


	public Integer getEnd() {
		return end;
	}


	public void setEnd(Integer end) {
		this.end = end;
	}


	public Integer getPageNum() {
		return pageNum;
	}


	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}


	public Integer getNumPerPage() {
		return numPerPage;
	}


	public void setNumPerPage(Integer numPerPage) {
		this.numPerPage = numPerPage;
	}


	public static int pageCount(int count, int numPerPage){
		int pageCount = 0;
		if(count%numPerPage==0){
			pageCount = count/numPerPage;
		}else{
			pageCount = count/numPerPage + 1;
		}
		return pageCount;
	}
}

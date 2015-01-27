package org.common.entity.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaginationView<T> {
	private final static int PER_PAGE = 10;
	private final static int CURRENT_PAGE = 1;
	
	private int perPage = 10;
	
	private int currentPage = 1;
	
	private int count;
	
	private List<T> paginationList;
	
	private Map<String,Object> filter = new HashMap<String,Object>();
	
	private Map<String,Object> data = new HashMap<String,Object>();
	
	public int getPerPage() {
		return perPage;
	}

	public void setPerPage(int perPage) {
		this.perPage = perPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalPage() {
		if(count==0){
			return 0;
		}else{
			if(count%perPage==0){
				return count/perPage;
			}else{
				return count/perPage+1;
			}
		}
	}

	public Map<String, Object> loadFilter() {
		if(perPage<0){
			perPage = PER_PAGE;
		}
		filter.put("limitNum", perPage);
		
		if(currentPage<0){
			currentPage = CURRENT_PAGE;
		}
		
		filter.put("offset",(currentPage-1)*perPage);
		return filter;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void setPaginationList(List<T> paginationList) {
		this.paginationList = paginationList;
	}
	
	public Map<String,Object> getPaginationDate(){
		data.put("count", count);
		data.put("list", paginationList);
		data.put("perPage", perPage);
		data.put("currentPage", currentPage);
		data.put("totalPage", getTotalPage());
		return data;
	}
}

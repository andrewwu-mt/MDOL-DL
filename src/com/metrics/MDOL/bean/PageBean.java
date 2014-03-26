package com.metrics.MDOL.bean;

import java.util.List;
@SuppressWarnings("rawtypes")
public class PageBean {
	
	private List list;
	private int totalRow;
	private int totalPage;
	private int pageSize;
	private int currentPage;
	private int pageIndex = 3;
	private int minIndex;
	private int maxIndex;

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalRow() {
		return totalRow;
	}

	public void setTotalRow(int totalRow) {
		this.totalRow = totalRow;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public static int countTotalPage(final int totalRow, final int pageSize) {// �p���`����
		return totalRow % pageSize == 0 ? totalRow / pageSize : totalRow
				/ pageSize + 1;
	}

	public static int countOffset(final int currentPage, final int pageSize) {
		return pageSize * (currentPage - 1);
	}

	public static int countCurrentPage(int page) {
		return (page == 0 ? 1 : page);
	}

	public static int countMinIndex(int currentPage, int pageIndex,
			int totalPage) {
		if (totalPage <= pageIndex * 2) {
			return 1;
		} else {
			if (currentPage <= pageIndex) {
				return 1;
			} else if (currentPage + pageIndex >= totalPage) {
				return totalPage - pageIndex * 2;
			} else {
				return currentPage - pageIndex;
			}
		}
	}

	public static int countMaxIndex(int currentPage, int pageIndex,
			int totalPage) {
		if (totalPage <= pageIndex * 2) {
			return totalPage;
		} else {
			if (currentPage <= pageIndex) {
				return pageIndex * 2;
			} else if (currentPage + pageIndex >= totalPage) {
				return totalPage;
			} else {
				return currentPage + pageIndex;
			}
		}
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getMinIndex() {
		return minIndex;
	}

	public void setMinIndex(int minIndex) {
		this.minIndex = minIndex;
	}

	public int getMaxIndex() {
		return maxIndex;
	}

	public void setMaxIndex(int maxIndex) {
		this.maxIndex = maxIndex;
	}
}

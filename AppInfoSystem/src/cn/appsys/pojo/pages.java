package cn.appsys.pojo;

/**
 * ��ҳ��
 * @author DELL
 *
 */
public class pages {
	private int totalCount;  //�ܼ�¼����
	private int currentPageNo; //��ǰҳ��
	private int totalPageCount; //��ҳ��
	private int showPageCount; //��ʾ����
	
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		if(totalCount > 0) {
			this.totalPageCount = totalCount%showPageCount == 0 ? totalCount/showPageCount : totalCount/showPageCount+1;
		}
		this.totalCount = totalCount;
	}
	public int getCurrentPageNo() {
		return currentPageNo;
	}
	public void setCurrentPageNo(int currentPageNo) {
		this.currentPageNo = currentPageNo;
	}
	public int getTotalPageCount() {
		return totalPageCount;
	}
	public void setTotalPageCount(int totalPageCount) {
		this.totalPageCount = totalPageCount;
	}
	public int getShowPageCount() {
		return showPageCount;
	}
	public void setShowPageCount(int showPageCount) {
		this.showPageCount = showPageCount;
	}

}

package app.component.pss.utils.excel;

import java.util.List;

public class PssExcelResultBean<T, E> {
	
	/** 处理结果：默认0000 */
	private String result = "0000";
	/** 主表类 */
	private T t;
	/** 明细表集合 */
	private List<E> listDetail;
	/** 总列数 */
	private int totalColumnNum;
	/** 验证信息 */
	private List<PssExcelImportMsg> listMsg;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public T getT() {
		return t;
	}

	public void setT(T t) {
		this.t = t;
	}

	public List<E> getListDetail() {
		return listDetail;
	}

	public void setListDetail(List<E> listDetail) {
		this.listDetail = listDetail;
	}

	public int getTotalColumnNum() {
		return totalColumnNum;
	}

	public void setTotalColumnNum(int totalColumnNum) {
		this.totalColumnNum = totalColumnNum;
	}

	public List<PssExcelImportMsg> getListMsg() {
		return listMsg;
	}

	public void setListMsg(List<PssExcelImportMsg> listMsg) {
		this.listMsg = listMsg;
	}
	
	
}

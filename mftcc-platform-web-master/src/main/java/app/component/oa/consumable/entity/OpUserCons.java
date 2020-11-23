package app.component.oa.consumable.entity;

import java.util.List;

/**
 * 封装与当前操作员有关的低值易耗品统计信息
 * @author LiuAo
 *
 */
public class OpUserCons {
	private String consAppItem;//当前操作员申领的资产名称概览（显示最新申领的两种资产）
	private Integer totalAppNum;//总申领数量
	private List<MfOaConsOperate> conAppList;//所有申领操作（已办理或已借出）
	
	public String getConsAppItem() {
		return consAppItem;
	}
	public void setConsAppItem(String consAppItem) {
		this.consAppItem = consAppItem;
	}
	public Integer getTotalAppNum() {
		return totalAppNum;
	}
	public void setTotalAppNum(Integer totalAppNum) {
		this.totalAppNum = totalAppNum;
	}
	public List<MfOaConsOperate> getConAppList() {
		return conAppList;
	}
	public void setConAppList(List<MfOaConsOperate> conAppList) {
		this.conAppList = conAppList;
	}
	
}

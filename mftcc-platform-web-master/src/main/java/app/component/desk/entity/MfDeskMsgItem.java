package app.component.desk.entity;
import app.base.BaseDomain;
/**
* Title: MfDeskMsgItem.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Sun Aug 27 10:57:59 CST 2017
* @version：1.0
**/
public class MfDeskMsgItem extends BaseDomain {
	private static final long serialVersionUID = 1L;
	private String msgId;// 个人预警中心消息ID
	private String msgTitle;// 消息标题
	private String msgImg;// 消息图标
	private String msgType;// 消息类型
	private int msgCount;// 消息数量
	private int sort;// 预警消息展示位置排序
	private String jumpUrl;// 跳转url
	private String isBase;// 是否是基础预警消息模板  1-是；0-否
	private String opNo;// 操作员
	private String isShowDialog;// 点击图标是跳转到相关页面还是弹出层显示  1-跳转页面；0-弹出层；
	private String menuNo;// 左侧菜单编号0-进件；1-签约；2-贷后；3查询；4-报表；5-办公；6-财务；7-设置
	private String regTime;// 注册时间
	private String lstModTime;// 最后修改时间
	private String useFlag;//是否被关注  1-被关注 ;0-未关注
	private String itemId; // msgId集合
	private String opNos;//操作员集合
	private String isAddOrSub;//1-增加；0-減少
	private String[] roleNoStr;//操作员拥有的角色数组
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public String getMsgTitle() {
		return msgTitle;
	}
	public void setMsgTitle(String msgTitle) {
		this.msgTitle = msgTitle;
	}
	public String getMsgImg() {
		return msgImg;
	}
	public void setMsgImg(String msgImg) {
		this.msgImg = msgImg;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public int getMsgCount() {
		return msgCount;
	}
	public void setMsgCount(int msgCount) {
		this.msgCount = msgCount;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public String getJumpUrl() {
		return jumpUrl;
	}
	public void setJumpUrl(String jumpUrl) {
		this.jumpUrl = jumpUrl;
	}
	public String getIsBase() {
		return isBase;
	}
	public void setIsBase(String isBase) {
		this.isBase = isBase;
	}
	public String getOpNo() {
		return opNo;
	}
	public void setOpNo(String opNo) {
		this.opNo = opNo;
	}
	public String getIsShowDialog() {
		return isShowDialog;
	}
	public void setIsShowDialog(String isShowDialog) {
		this.isShowDialog = isShowDialog;
	}
	public String getMenuNo() {
		return menuNo;
	}
	public void setMenuNo(String menuNo) {
		this.menuNo = menuNo;
	}
	public String getRegTime() {
		return regTime;
	}
	public void setRegTime(String regTime) {
		this.regTime = regTime;
	}
	public String getLstModTime() {
		return lstModTime;
	}
	public void setLstModTime(String lstModTime) {
		this.lstModTime = lstModTime;
	}
	public String getUseFlag() {
		return useFlag;
	}
	public void setUseFlag(String useFlag) {
		this.useFlag = useFlag;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getOpNos() {
		return opNos;
	}
	public void setOpNos(String opNos) {
		this.opNos = opNos;
	}
	public String getIsAddOrSub() {
		return isAddOrSub;
	}
	public void setIsAddOrSub(String isAddOrSub) {
		this.isAddOrSub = isAddOrSub;
	}
	public String[] getRoleNoStr() {
		return roleNoStr;
	}
	public void setRoleNoStr(String[] roleNoStr) {
		this.roleNoStr = roleNoStr;
	}
	
}
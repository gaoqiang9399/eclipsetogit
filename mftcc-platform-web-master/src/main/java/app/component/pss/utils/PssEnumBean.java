package app.component.pss.utils;

import java.util.HashMap;
import java.util.Map;

public class PssEnumBean {
	/**
	 * 类名： YES_OR_NO
	 * 描述：1: 是 与 0: 否
	 * @author hgx
	 * @date 2017-08-15 下午5:35:27
	 */
	public static enum YES_OR_NO{
		/***是***/
		YES("Y","1","是"),
		/***否***/
		NO("N","0","否");
		
		private String name;
		private String num;
		private String desc;
		
		private YES_OR_NO(String num, String desc){
			this(desc,num,desc);
		}
		
		private YES_OR_NO(String name, String num, String desc){
			this.name=name;
			this.num = num;
			this.desc = desc;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getNum() {
			return num;
		}
		public void setNum(String num) {
			this.num = num;
		}
		public String getDesc() {
			return desc;
		}
		public void setDesc(String desc) {
			this.desc = desc;
		}
		
		// 普通方法
		public static final String getTypeNum(String name) {
			return fromValue(name).num;
		}

		public static final String getTypeDesc(String num) {
			return fromValue(num).desc;
		}
		public static final String getTypeName(String num) {
			return fromValue(num).name;
		}

		public static final YES_OR_NO fromValue(String inputStr) {
			for (YES_OR_NO c : YES_OR_NO.values()) {
				if (c.name.equals(inputStr) || c.num.equals(inputStr)) {
					return c;
				}
			}
			throw new RuntimeException("KEMUTYPE枚举参数错误，输入参数：" + inputStr);
		}
		
		public static final Map<String, String> getMap() {
			Map<String, String> resMap = new HashMap<String, String>();
			for (YES_OR_NO c : YES_OR_NO.values()){
				resMap.put(c.num, c.desc);
			}
			return resMap;
		}
	}
	
	/**
	 * 类名：BILL_BUS_TYPE
	 * 描述：01-普通采购；02-盘盈；03-其他入库；04-成本调整；05-调拨单；06-采购退回；07-盘亏；08-其他出库；09-普通销售；10-销售退回；
	 * @author hgx
	 * @date 2017-08-16 下午4:35:27
	 */
	public static enum BILL_BUS_TYPE{
		/**普通采购*/
		TYPE1("01","普通采购"),
		/**盘盈*/
		TYPE2("02","盘盈"),
		/**其他入库*/
		TYPE3("03","其他入库"),
		/**成本调整*/
		TYPE4("04","成本调整"),
		/**调拨单*/
		TYPE5("05","调拨单"),
		/**采购退回*/
		TYPE6("06","采购退回"),
		/**盘亏*/
		TYPE7("07","盘亏"),
		/**其他出库*/
		TYPE8("08","其他出库"),
		/**普通销售*/
		TYPE9("09","普通销售"),
		/**销售退回*/
		TYPE10("10","销售退回");
		
		private String value;
		private String desc;
		
		private BILL_BUS_TYPE(String value, String desc){
			this.value = value;
			this.desc = desc;
		}

		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public String getDesc() {
			return desc;
		}
		public void setDesc(String desc) {
			this.desc = desc;
		}
		
	}
	
	/**
	 * 类名：BILL_BUS_DETAIL_TYPE
	 * 描述：01-采购退回（订单生成）；02-采购退回（直接新增）；03-采购退回（购货单生成）；04-销货退回（订单生成）；05-销货退回（直接新增）；06-销货退回（销货单生成）
	 * @author hgx
	 * @date 2017-12-19 下午6:35:27
	 */
	public static enum BILL_BUS_DETAIL_TYPE{
		/**采购退回（订单生成）*/
		TYPE1("01","采购退回（订单生成）"),
		/**采购退回（直接新增）*/
		TYPE2("02","采购退回（直接新增）"),
		/**采购退回（购货单生成）*/
		TYPE3("03","采购退回（购货单生成）"),
		/**销货退回（订单生成）*/
		TYPE4("04","销货退回（订单生成）"),
		/**销货退回（直接新增）*/
		TYPE5("05","销货退回（直接新增）"),
		/**销货退回（销货单生成）*/
		TYPE6("06","销货退回（销货单生成）");
		
		private String value;
		private String desc;
		
		private BILL_BUS_DETAIL_TYPE(String value, String desc){
			this.value = value;
			this.desc = desc;
		}

		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public String getDesc() {
			return desc;
		}
		public void setDesc(String desc) {
			this.desc = desc;
		}
		
	}
	
	/**
	 * 类名：IN_OUT_TYPE
	 * 描述：0-出库;1-入库;2-其他(成本调整单用)
	 * @author hgx
	 * @date 2017-08-16 下午4:35:27
	 */
	public static enum IN_OUT_TYPE{
		/** 出库 */
		TYPE0("0","出库"),
		/** 入库 */
		TYPE1("1","入库"),
		/** 其他 */
		TYPE2("2","其他");
		
		private String value;
		private String desc;
		
		private IN_OUT_TYPE(String value, String desc){
			this.value = value;
			this.desc = desc;
		}

		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public String getDesc() {
			return desc;
		}
		public void setDesc(String desc) {
			this.desc = desc;
		}
	}
	
	/**
	 * 类名：MAP
	 * 描述：Map返回key
	 * @author hgx
	 * @date 2017-08-16 下午1:35:27
	 */
	public static enum MAP{
		/** 返回code **/
		CODE("code"),
		/** 返回goodsId **/
		GOODSID("goodsId"),
		/** 返回storehouseId **/
		STOREHOUSEID("storehouseId"),
		/** 返回billId **/
		BILLID("billId"),
		/** 返回msg **/
		MSG("msg");
		
		private String value;
		
		private MAP(String value){
			this.value = value;
		}

		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		
	}
	
	/**
	 * 类名： STOCK_MSG
	 * 描述：仓库消息
	 * @author hgx
	 * @date 2017-08-16 上午11:35:27
	 */
	public static enum STOCK_MSG{
		
		/** 库存不足 **/
		STOCK_NOT_ENOUGH("库存不足"),
		/** 可用库存数量 **/
		STOCK_AVA_QUANTITY("可用库存数量");
		
		private String desc;
		
		private STOCK_MSG(String desc){
			this.desc = desc;
		}

		public String getDesc() {
			return desc;
		}
		public void setDesc(String desc) {
			this.desc = desc;
		}
		
	}
	
	/**
	 * 类名：BUY_BUSINESS_TYPE
	 * 描述：业务类别(01-购货 02-退货)
	 * @author yudongwei
	 * @date 2017-08-17 下午05:45:27
	 */
	public static enum BUY_BUSINESS_TYPE {
		/**
		 *购货
		 */
		BUY("01", "购货"),
		/**
		 *退货
		 */
		RETURN("02", "退货");
		
		private String value;
		private String desc;
		
		private BUY_BUSINESS_TYPE(String value, String desc){
			this.value = value;
			this.desc = desc;
		}

		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public String getDesc() {
			return desc;
		}
		public void setDesc(String desc) {
			this.desc = desc;
		}
	}
	
	/**
	 * 类名：BUY_ORDER_STATE
	 * 描述：购货订单状态(01-未入库 02-部分入库 03-全部入库)
	 * @author yudongwei
	 * @date 2017-08-18 上午09:45:27
	 */
	public static enum BUY_ORDER_STATE {
		/**
		 *未入库
		 */
		NONE("01", "未入库"),
		/**
		 *部分入库
		 */
		PART("02", "部分入库"),
		/**
		 *全部入库
		 */
		ALL("03", "全部入库");
		
		private String value;
		private String desc;
		
		private BUY_ORDER_STATE(String value, String desc){
			this.value = value;
			this.desc = desc;
		}

		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public String getDesc() {
			return desc;
		}
		public void setDesc(String desc) {
			this.desc = desc;
		}
	}
	
	/**
	 * 类名：ENABLED_STATUS
	 * 描述：启用状态(1-启用 0-关闭)
	 * @author yudongwei
	 * @date 2017-08-18 上午09:45:27
	 */
	public static enum ENABLED_STATUS {
		/**
		 *启用
		 */
		ENABLED("1", "启用"),
		/**
		 *关闭
		 */
		CLOSED("0", "关闭");
		
		private String value;
		private String desc;
		
		private ENABLED_STATUS(String value, String desc){
			this.value = value;
			this.desc = desc;
		}

		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public String getDesc() {
			return desc;
		}
		public void setDesc(String desc) {
			this.desc = desc;
		}
	}
	
	/**
	 * 类名：SALE_BUSINESS_TYPE
	 * 描述：业务类别(01-销货 02-退货)
	 * @author yudongwei
	 * @date 2017-09-29 下午05:45:27
	 */
	public static enum SALE_BUSINESS_TYPE {
		/**
		 *销货
		 */
		SALE("01", "销货"),
		/**
		 *退货
		 */
		RETURN("02", "退货");
		
		private String value;
		private String desc;
		
		private SALE_BUSINESS_TYPE(String value, String desc){
			this.value = value;
			this.desc = desc;
		}

		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public String getDesc() {
			return desc;
		}
		public void setDesc(String desc) {
			this.desc = desc;
		}
	}
	
	/**
	 * 类名：SALE_ORDER_STATE
	 * 描述：销货订单状态(01-未出库 02-部分出库 03-全部出库)
	 * @author yudongwei
	 * @date 2017-08-28 上午09:45:27
	 */
	public static enum SALE_ORDER_STATE {
		/**
		 *未出库
		 */
		NONE("01", "未出库"),
		/**
		 *部分出库
		 */
		PART("02", "部分出库"),
		/**
		 *全部出库
		 */
		ALL("03", "全部出库");
		
		private String value;
		private String desc;
		
		private SALE_ORDER_STATE(String value, String desc){
			this.value = value;
			this.desc = desc;
		}

		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public String getDesc() {
			return desc;
		}
		public void setDesc(String desc) {
			this.desc = desc;
		}
	}
	
	/**
	 * 类名：LOCK_STATUS
	 * 描述：锁定状态(1-锁定 0-解锁)
	 * @author yudongwei
	 * @date 2017-08-28 上午09:45:27
	 */
	public static enum LOCK_STATUS {
		/**
		 *锁定
		 */
		LOCKED("1", "锁定"),
		/**
		 *解锁
		 */
		UNLOCKED("0", "解锁");
		
		private String value;
		private String desc;
		
		private LOCK_STATUS(String value, String desc){
			this.value = value;
			this.desc = desc;
		}

		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public String getDesc() {
			return desc;
		}
		public void setDesc(String desc) {
			this.desc = desc;
		}
	}
	
	/**
	 * 类名：PAID_STATE
	 * 描述：付款状态(01-未付款 02-部分付款 03-全部付款)
	 * @author yudongwei
	 * @date 2017-08-18 上午09:45:27
	 */
	public static enum PAID_STATE {
		/**
		 *未付款
		 */
		NONE("01", "未付款"),
		/**
		 *部分付款
		 */
		PART("02", "部分付款"),
		/**
		 *全部付款
		 */
		ALL("03", "全部付款");
		
		private String value;
		private String desc;
		
		private PAID_STATE(String value, String desc){
			this.value = value;
			this.desc = desc;
		}

		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public String getDesc() {
			return desc;
		}
		public void setDesc(String desc) {
			this.desc = desc;
		}
	}
	
	/**
	 * 类名：RECEIVED_STATE
	 * 描述：收款状态(01-未收款 02-部分收款 03-全部收款)
	 * @author yudongwei
	 * @date 2017-08-18 上午09:45:27
	 */
	public static enum RECEIVED_STATE {
		/**
		 *未收款
		 */
		NONE("01", "未收款"),
		/**
		 *部分收款
		 */
		PART("02", "部分收款"),
		/**
		 *全部收款
		 */
		ALL("03", "全部收款");
		
		private String value;
		private String desc;
		
		private RECEIVED_STATE(String value, String desc){
			this.value = value;
			this.desc = desc;
		}

		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public String getDesc() {
			return desc;
		}
		public void setDesc(String desc) {
			this.desc = desc;
		}
	}
	
	/**
	 * 类名：REFUND_STATE
	 * 描述：退款状态(01-未退款 02-部分退款 03-全部退款)
	 * @author yudongwei
	 * @date 2017-08-18 上午09:45:27
	 */
	public static enum REFUND_STATE {
		/**
		 *未退款
		 */
		NONE("01", "未退款"),
		/**
		 *部分退款
		 */
		PART("02", "部分退款"),
		/**
		 *全部退款
		 */
		ALL("03", "全部退款");
		
		private String value;
		private String desc;
		
		private REFUND_STATE(String value, String desc){
			this.value = value;
			this.desc = desc;
		}

		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public String getDesc() {
			return desc;
		}
		public void setDesc(String desc) {
			this.desc = desc;
		}
	}
	
	/**
	 * 类名：CANCEL_TYPE
	 * 描述：1-预收冲应收；2-预付冲应付；3-应收冲应付；4-应收转应收；5-应付转应付；
	 * @author hgx
	 * @date 2017-09-27 下午5:35:27
	 */
	public static enum CANCEL_TYPE{
		/** 预收冲应收 */
		TYPE1("1","预收冲应收"),
		/** 预付冲应付 */
		TYPE2("2","预付冲应付"),
		/** 应收冲应付 */
		TYPE3("3","应收冲应付"),
		/** 应收转应收 */
		TYPE4("4","应收转应收"),
		/** 应付转应付 */
		TYPE5("5","应付转应付");
		
		private String value;
		private String desc;
		
		private CANCEL_TYPE(String value, String desc){
			this.value = value;
			this.desc = desc;
		}

		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public String getDesc() {
			return desc;
		}
		public void setDesc(String desc) {
			this.desc = desc;
		}
		
	}
	
	/**
	 * 类名：CANCEL_TYPE
	 * 描述：1-预收；2-应收；3-预付；4-应付；
	 * @author hgx
	 * @date 2017-09-27 下午5:35:27
	 */
	public static enum CANCEL_DETAIL_TYPE{
		/** 预收 */
		TYPE1("1","预收"),
		/** 应收 */
		TYPE2("2","应收"),
		/** 预付 */
		TYPE3("3","预付"),
		/** 应付 */
		TYPE4("4","应付");
		
		private String value;
		private String desc;
		
		private CANCEL_DETAIL_TYPE(String value, String desc){
			this.value = value;
			this.desc = desc;
		}

		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public String getDesc() {
			return desc;
		}
		public void setDesc(String desc) {
			this.desc = desc;
		}
		
	}
	
	/**
	 * 类名：SALE_ORDER_SCOPE
	 * 描述：销售订单范围(01-未采购完 02-已采购完)
	 * @author yudongwei
	 * @date 2017-10-28 上午09:45:27
	 */
	public static enum SALE_ORDER_SCOPE {
		/**
		 *未采购完
		 */
		NOT_BUY_FINISH("01", "未采购完"),
		/**
		 *已采购完
		 */
		BUY_FINISH("02", "已采购完");
		
		private String value;
		private String desc;
		
		private SALE_ORDER_SCOPE(String value, String desc){
			this.value = value;
			this.desc = desc;
		}

		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public String getDesc() {
			return desc;
		}
		public void setDesc(String desc) {
			this.desc = desc;
		}
	}
	/**
	 * 类名：PSS_BILL_TYPE
	 * 描述：订单种类
	 * @author huyanming
	 * @date 2017-08-28 上午09:45:27
	 */
	public static enum PSS_BILL_TYPE {
		/**
		 *购货订单
		 */
		GHDD("01", "购货订单"),
		/**
		 *购货单
		 */
		GHD("02", "购货单"),
		/**
		 *购货退货单
		 */
		GHT("03", "购货退货单"),
		/**
		 *销货订单
		 */
		XHDD("04", "销货订单"),
		/**
		 *销货单
		 */
		XHD("05", "销货单"),
		/**
		 *销货退货单
		 */
		XHT("06", "销货退货单"),
		/**
		 *调拨单
		 */
		DBD("07", "调拨单"),
		/**
		 *盘库单
		 */
		PKD("08", "盘库单"),
		/**
		 *其他入库单
		 */
		QTRK("09", "其他入库单"),
		/**
		 *其他出库单
		 */
		QTCK("10", "其他出库单"),
		/**
		 *付款单
		 */
		FKD("11", "付款单"),
		/**
		 *收款单
		 */
		SKD("12", "收款单"),
		/**
		 *核销单
		 */
		HXD("13", "核销单"),
		/**
		 *成本调整单
		 */
		CBT("14", "成本调整单"),
		/**
		 *其他收入单
		 */
		QTSR("15", "其他收入单"),
		/**
		 *其他支出单
		 */
		QTZC("16", "其他支出单");
		
		private String value;
		private String desc;
		
		private PSS_BILL_TYPE(String value, String desc){
			this.value = value;
			this.desc = desc;
		}

		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public String getDesc() {
			return desc;
		}
		public void setDesc(String desc) {
			this.desc = desc;
		}
	}
	
	/**
	 * 类名：CUS_GRADE
	 * 描述：客户登记(01-零售客户 02-批发客户 03-VIP客户 04-折扣等级一 05-折扣等级二)
	 * @author yudongwei
	 * @date 2017-12-08 上午09:45:27
	 */
	public static enum CUS_GRADE {
		/**
		 *零售客户
		 */
		RETAIL("01", "零售客户"),
		/**
		 *批发客户
		 */
		WHOLESALE("02", "批发客户"),
		/**
		 *VIP客户
		 */
		VIP("03", "VIP客户"),
		/**
		 *折扣等级一
		 */
		DISCOUNT1("04", "折扣等级一"),
		/**
		 *折扣等级二
		 */
		DISCOUNT2("05", "折扣等级二");
		
		private String value;
		private String desc;
		
		private CUS_GRADE(String value, String desc){
			this.value = value;
			this.desc = desc;
		}

		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public String getDesc() {
			return desc;
		}
		public void setDesc(String desc) {
			this.desc = desc;
		}
	}
	
	/**
	 * 类名：PSS_ACCOUNT_TYPE
	 * 描述：账户类别(01-现金 02-银行存款)
	 * @author hanyu
	 * @date 2017-12-08 上午09:45:27
	 */
	public static enum PSS_ACCOUNT_TYPE{
		/**
		 *现金
		 */
		CASH("01", "现金"),
		/**
		 *银行存款
		 */
		BANK_DEPOSIT("02", "银行存款");
		
		private String value;
		private String desc;
		
		private PSS_ACCOUNT_TYPE(String value, String desc){
			this.value = value;
			this.desc = desc;
		}

		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public String getDesc() {
			return desc;
		}
		public void setDesc(String desc) {
			this.desc = desc;
		}
	}
}

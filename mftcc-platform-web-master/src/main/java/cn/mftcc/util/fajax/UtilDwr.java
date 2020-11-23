package cn.mftcc.util.fajax;

import cn.mftcc.util.DateUtil;

/**
 * 从前台调用后台的工具类方法时，使用该DWR。
 * 不要随意添加方法。仅前台过于复杂或完全无法实现的，才通过此途径调用。
 * @author LiuYF
 *
 */
public class UtilDwr {
	
	public int[] getMonthsAndDays(String beginDate, String endDate) {
		return DateUtil.getMonthsAndDays(beginDate, endDate);
	}

}

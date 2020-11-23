// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   User.java

package app.base;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

public class User {

	public User() {
	}
	/**
	 * 机构号
	 * @param httpservletrequest
	 * @return
	 */
	public static String getOrgNo(HttpServletRequest httpservletrequest) {
		String s = (String) httpservletrequest.getSession().getAttribute("orgNo");
		return s == null?"":s.trim();
	}
	/**
	 * 机构名称
	 * @param httpservletrequest
	 * @return
	 */
	public static String getOrgName(HttpServletRequest httpservletrequest) {
		String s = (String) httpservletrequest.getSession().getAttribute("orgName");
		return s == null?"":s.trim();
	}
	/**
	 * 操作员regNo
	 * @param httpservletrequest
	 * @return
	 */
	public static String getRegNo(HttpServletRequest httpservletrequest) {
		String s = String.valueOf(httpservletrequest.getSession().getAttribute("regNo"));
		return s == null?"":s;
	}
	/**
	 * 操作员姓名
	 * @param httpservletrequest
	 * @return
	 */
	public static String getRegName(HttpServletRequest httpservletrequest) {
		String s = (String) httpservletrequest.getSession().getAttribute("regName");
		return s == null?"":s;
	}
	/**
	 * 操作员角色编号
	 * @param httpservletrequest
	 * @return
	 */
	public static String[] getRoleNo(HttpServletRequest httpservletrequest) {
		String[] s = (String[]) httpservletrequest.getSession().getAttribute("roleNo");
		return (String[]) (s == null?"":s);
	}
	
	/**
	 * 获取当前操作员的归属机构
	 * @param httpservletrequest
	 * @return
	 */
	public static String getBizType(HttpServletRequest httpservletrequest) {
		String s = (String) httpservletrequest.getSession().getAttribute("bizType");
		return s == null?"":s;
	}

	/**
	 * 
	 * 功能描述：取得系统营业时间
	 * @param httpservletrequest
	 * @return
	 * @author yangbj
	 * @date Nov 11, 2010
	 * @修改日志：
	 */
	public static String getSysDate(HttpServletRequest httpservletrequest){
		String s = (String)httpservletrequest.getSession().getAttribute("sysDate");
		return s == null?"":s;
	}
	//列表默认显示行数
	public static int getPageSize(){
		return 10;
	}

	/**
	 * 取得系统时间
	 * @return
	 */
	public static String getTime() {
		Date date = new Date();
		SimpleDateFormat simpledateformat = new SimpleDateFormat(
				"yyyyMMddHHmmss");
		return simpledateformat.format(date);
	}
	
	/**
	 * 取得系统时间HH:mm:ss
	 * @return
	 */
	public static String getTime1(){
		Date date = new Date();
		SimpleDateFormat simpledateformat = new SimpleDateFormat(
				"HH:mm:ss");
		return simpledateformat.format(date);
	}
	/**
	 * 取得系统日期yyyyMMdd
	 * @return
	 */
	public static String getCurrDate(){
		Date date = new Date();
		SimpleDateFormat simpledateformat = new SimpleDateFormat(
				"yyyyMMdd");
		return simpledateformat.format(date);
	}
	
	/**
	 * 取得系统日期年份
	 * @return
	 */
	public static String getCurrYear(HttpServletRequest httpservletrequest){
		String sysdate = (String)httpservletrequest.getSession().getAttribute("sysDate");
		if(sysdate==null||"".equals(sysdate)){
			Date date = new Date();
			SimpleDateFormat simpledateformat = new SimpleDateFormat(
					"yyyy");
			sysdate = simpledateformat.format(date);
		}else{
			sysdate = sysdate.substring(0, 4);
		}
		return sysdate;
	}
	
	/**
	 * 
	 * 
	 * 功能描述：数字格式化
	 * @author dhcc zhangfangrui
	 * @date Aug 8, 2009
	 * @param number
	 * 					数字
	 * @param precision
	 * 					精度
	 * @see
	 * @修改日志：
	 *
	 */
	public static String numFormat(double number,int precision){
		DecimalFormat myformat=null;
		if(precision==2){
		myformat = new DecimalFormat("###0.00");//使用系统默认的格式
		}else if(precision==1){
			myformat = new DecimalFormat("###0.0");//使用系统默认的格式	
		}else if(precision==4){
			myformat = new DecimalFormat("###0.0000");//使用系统默认的格式	
		}else if(precision==0){
			myformat = new DecimalFormat("###0");//使用系统默认的格式	
		}else {
		}
		return myformat.format(number);
	}
	
}

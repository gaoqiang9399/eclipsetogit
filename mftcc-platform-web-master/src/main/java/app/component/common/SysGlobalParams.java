package app.component.common;

import java.util.HashMap;
import java.util.Map;

import app.base.SpringUtil;
import app.component.sys.entity.MfSysCompanyMst;
import app.component.sys.feign.MfSysCompanyMstFeign;

/**
 * 系统公共变量的静态缓存类。
 * @date 2017年7月18日 17:56:33
 * @author LiuYF
 *
 */
public class SysGlobalParams {
	
	private static Map<String, Object> map;
	
	private SysGlobalParams() {
		
	}
	
	/**
	 * 公共的调用方法，根据封装进入map的实际情况进行获取。
	 * @param key
	 * @return
	 */
	public static Object get(String key) {
		if (map == null) {
			synchronized (SysGlobalParams.class) {
				if (map == null) {
					reloadParams(ParamTypeEnum.ALL);
				}
			}
		}
		
		return map.get(key);
	}
	
	/**
	 * 仅在业务修改相关数据的地方调用此方法重载缓存。
	 * @param type
	 */
	public static void reloadParams(ParamTypeEnum type) {
		switch (type) {
			case ALL:
				reloadParams(ParamTypeEnum.COMPANY);
				break;
			case COMPANY:
				// TODO 在此调用获取公司信息的方法，封装到map中。
//				map.putAll();
				setCompanyInfo();
				break;
			default:
				// 未传入指定的加载类型，do nothing
				break;
		}
	}

	public static enum ParamTypeEnum {
		/**
		 *ALL
		 */
		ALL,
		/**
		 * COMPANY
		 */
		COMPANY;
	}
	private static void setCompanyInfo(){
		MfSysCompanyMstFeign mfSysCompanyMstFeign = SpringUtil.getBean(MfSysCompanyMstFeign.class);
		MfSysCompanyMst mfSysCompanyMst = new MfSysCompanyMst();
		mfSysCompanyMst.setComNo("100000");
		mfSysCompanyMst = mfSysCompanyMstFeign.getById(mfSysCompanyMst);
		if (map == null) {
			map  = new HashMap<String, Object>();
		}
		map.put("COMPANY", mfSysCompanyMst);
	}
}

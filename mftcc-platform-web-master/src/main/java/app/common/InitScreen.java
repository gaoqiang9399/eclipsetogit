package app.common;

import java.util.ArrayList;
import java.util.List;

import com.core.domain.screen.OptionsList;

import app.component.sys.feign.SysInitScreenFeign;
import app.util.ServiceLocator;

/**
 * 应用项目跟展现层结合类 如果使用的话，就必须在项目中添加这个类，并根据项目的实际技术框架进行扩展。
 */
public class InitScreen {

	public InitScreen() {
		super();
	}

	public List<OptionsList> getOtherOption(String sqlOption) {
		List<OptionsList> returnList = new ArrayList<OptionsList>();
		try {
			SysInitScreenFeign sysInitScreenFeign = (SysInitScreenFeign) ServiceLocator
					.getBean(SysInitScreenFeign.class);
			returnList = sysInitScreenFeign.getOtherOption(sqlOption);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	public List<OptionsList> getOption(String sqlOption,Object obj) {
		List<OptionsList> returnList = new ArrayList<OptionsList>();
		
		try {
			SysInitScreenFeign sysInitScreenFeign = (SysInitScreenFeign) ServiceLocator
					.getBean(SysInitScreenFeign.class);
			returnList = sysInitScreenFeign.getOtherOption(sqlOption);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnList;
	}
}

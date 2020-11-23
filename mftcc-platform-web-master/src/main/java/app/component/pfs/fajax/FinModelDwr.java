package app.component.pfs.fajax;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import app.base.SpringUtil;
import app.component.pfs.entity.CusFinModel;
import app.component.pfs.entity.CusFinParm;
import app.component.pfs.entity.FinModelJson;
import app.component.pfs.feign.CusFinParmFeign;

/**
 * @author lenovo
 *
 */
public class FinModelDwr {
	private static Logger logger = LoggerFactory.getLogger(FinModelDwr.class);
	
	public String getFinModelList(String report_type, String acc_rule) {
		List<CusFinParm> fin_parm_list = null;
		List<CusFinModel> fin_model_list = null;
		try {
			CusFinParmFeign service = SpringUtil.getBean("cusFinParmFeign",CusFinParmFeign.class);
			fin_parm_list = service.getFinParmList(report_type, acc_rule);
			fin_model_list = service.getFinModelList(report_type, acc_rule);
			
		} catch (Exception e) {
			logger.error("getFinModelList方法发生异常", e);
		}
		
		FinModelJson json = new FinModelJson(fin_parm_list, fin_model_list);
		String jsonResult = json.toJson();
		
		fin_parm_list.clear();
		fin_model_list.clear();
		fin_parm_list = null;
		fin_model_list = null;
		json = null;
		
		return jsonResult;
	}
}

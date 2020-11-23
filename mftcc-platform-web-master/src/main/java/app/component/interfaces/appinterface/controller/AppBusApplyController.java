package app.component.interfaces.appinterface.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;

import app.component.app.entity.MfBusApply;
import app.component.interfaces.appinterface.feign.AppBusApplyFeign;

/**
 * 业务申请信息管理的Action类
 * 
 * @author zhang_dlei
 * @date 2017-06-15 下午5:58:30
 */
@Controller
@RequestMapping("/appBusApply")
public class AppBusApplyController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfSysKindBo
	@Autowired
	private AppBusApplyFeign appBusApplyFeign;
	// 全局变量

	/***
	 * 插入融资基本信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/appInsertAjax")
	@ResponseBody
	public Map<String, Object> appInsertAjax(MfBusApply busApply) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String appId = appBusApplyFeign.insert(busApply, request);
			dataMap.put("errorCode", "00000");
			dataMap.put("data", appId);
			dataMap.put("errorMsg", "成功");
		} catch (Exception e) {
//			logger.error("移动端插入融资信息出错", e);
			dataMap.put("errorCode", "99999");
			dataMap.put("errorMsg", e.getMessage());
		}
		return dataMap;
	}

	/***
	 * 通过appId或cusTel查询融资信息详情
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/appSelectAjax")
	@ResponseBody
	public Map<String, Object> appSelectAjax(String appId) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, Object> data = appBusApplyFeign.getById(appId);
			dataMap.put("errorCode", "00000");
			dataMap.put("data", data);
			dataMap.put("errorMsg", "成功");
		} catch (Exception e) {
//			logger.error("移动端查询融资信息出错", e);
			dataMap.put("errorCode", "99999");
			dataMap.put("errorMsg", e.getMessage());
		}
		return dataMap;
	}

	/***
	 * 融资基本信息列表
	 * @return
	 * @throws Exception
	 */
//	@RequestMapping(value = "/appFindByPageAjax")
//	@ResponseBody
	/*public Map<String, Object> appFindByPageAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try {
			Ipage ipage = this.getIpage();
			//异步传页面翻页参数
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);
			ipage.setPageSize(10);
			List<Map<String, Object>> list = appMfBusApplyFeign.findByPage(ipage, cusNo,cusTel, stage);
			dataMap.put("errorCode", "00000");
			dataMap.put("data", list);
			dataMap.put("errorMsg", "查询列表成功");
		} catch (Exception e) {
			logger.error("移动端获取融资信息列表出错",e);
			dataMap.put("errorCode", "99999");
			dataMap.put("errorMsg", e.getMessage());
		}
		return dataMap;
	}*/
}

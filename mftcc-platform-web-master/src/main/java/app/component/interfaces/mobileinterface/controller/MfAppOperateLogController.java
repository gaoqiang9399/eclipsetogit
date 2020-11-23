package app.component.interfaces.mobileinterface.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import app.component.interfaces.mobileinterface.entity.MfAppOperateLog;
import app.component.interfaces.mobileinterface.feign.MfAppOperateLogFeign;

/**
 * Title: MfAppOperateLogAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue Sep 12 17:20:20 CST 2017
 **/
@Controller
@RequestMapping("/mfAppOperateLog")
public class MfAppOperateLogController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfAppOperateLogBo
	@Autowired
	private MfAppOperateLogFeign mfAppOperateLogFeign;
	// 全局变量
	// 插入所需数据

	// 表单变量
	/**
	 * AJAX新增 (由于前台APP在退出后缓存取不到身份证和银行卡信息，目前前台值传递一个手机信息，通过客户号获取银行卡和身份证信息重新插入)
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertBatchAjax")
	@ResponseBody
	public Map<String, Object> insertBatchAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			List<MfAppOperateLog> list = new Gson().fromJson(ajaxData, new TypeToken<List<MfAppOperateLog>>() {
			}.getType());
			mfAppOperateLogFeign.insertBatch(list);
			dataMap.put("flag", "success");
			dataMap.put("msg", "新增成功");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

}

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

import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import app.component.interfaces.mobileinterface.entity.MfCallRecords;
import app.component.interfaces.mobileinterface.feign.MfCallRecordsFeign;
import cn.mftcc.util.StringUtil;

/**
 * Title: MfCallRecordsAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu Jul 20 10:28:02 CST 2017
 **/
@Controller
@RequestMapping("/mfCallRecords")
public class MfCallRecordsController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfCallRecordsBo
	@Autowired
	private MfCallRecordsFeign mfCallRecordsFeign;
	// 全局变量
	// 异步参数

	/**
	 * AJAX保存通讯录列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertListAjax")
	@ResponseBody
	public Map<String, Object> insertListAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Gson gson = new Gson();
			if (null != ajaxData || "" != ajaxData) {
				List<MfCallRecords> mfCallRecordsList = gson.fromJson(ajaxData, new TypeToken<List<MfCallRecords>>() {
				}.getType());
				mfCallRecordsFeign.insertList(mfCallRecordsList);
				dataMap.put("errorCode", "00000");
				dataMap.put("errorMsg", "保存通讯录成功");
			} else {
				dataMap.put("errorCode", "99999");
				dataMap.put("errorMsg", "请求参数为空");
			}
		} catch (Exception e) {
			// //logger.error("移动端插入通话记录信息出错",e);
			dataMap.put("errorCode", "111111");
			dataMap.put("errorMsg", e.getMessage());
		}
		return dataMap;
	}


	/**
	 * 根据客户号和手机号查询通话记录
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String cusNo, String phoneNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCallRecords mfCallRecords = new MfCallRecords();
		try {
			if (StringUtil.isNotEmpty(cusNo) && StringUtil.isNotEmpty(phoneNo)) {
				mfCallRecords.setCusNo(cusNo);
				mfCallRecords.setPhoneNo(phoneNo);
				mfCallRecords = mfCallRecordsFeign.getById(mfCallRecords);
				dataMap.put("data", mfCallRecords);
				dataMap.put("errorCode", "00000");
				dataMap.put("errorMsg", "成功");
			} else {
				dataMap.put("errorCode", "99999");
				dataMap.put("errorMsg", "请求参数为空");
			}
		} catch (Exception e) {
			// logger.error("移动端查询通讯录信息出错",e);
			dataMap.put("errorCode", "111111");
			dataMap.put("errorMsg", e.getMessage());
		}
		return dataMap;
	}

}

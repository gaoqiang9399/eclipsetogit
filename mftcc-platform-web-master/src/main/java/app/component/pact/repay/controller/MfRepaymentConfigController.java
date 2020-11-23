/**
 * Copyright (C) DXHM 版权所有
 * 文件名： MfRepaymentConfigAction.java
 * 包名： app.component.pact.repay.action
 * 说明：
 * @author 沈浩兵
 * @date 2018-1-23 下午3:51:19
 * @version V1.0
 */
package app.component.pact.repay.controller;

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

import app.component.doc.entity.DocBizManageParam;
import app.component.doc.entity.DocBizSceConfig;
import app.component.pact.repay.feign.MfRepaymentConfigFeign;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONObject;

/**
 * 类名： MfRepaymentConfigAction 描述：
 * 
 * @author 沈浩兵
 * @date 2018-1-23 下午3:51:19
 *
 *
 */
@Controller
@RequestMapping("/mfRepaymentConfig")
public class MfRepaymentConfigController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfRepaymentConfigFeign mfRepaymentConfigFeign;

	/**
	 * 
	 * 方法描述： 获得还款要件配置信息
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2018-1-23 下午4:05:10
	 */
	@RequestMapping(value = "/getRepaymentDocConfigAjax")
	@ResponseBody
	public Map<String, Object> getRepaymentDocConfigAjax() throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			// JSONObject json = new JSONObject();
			DocBizSceConfig docBizSceConfig = new DocBizSceConfig();
			dataMap = mfRepaymentConfigFeign.getRepaymentDocConfig(docBizSceConfig);
			// ajaxData = json.toString();
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("还款"));
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("还款"));
			throw e;
		}
		return dataMap;
	}

	/**
	 * 
	 * 方法描述： 初始化要件
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @param fincId
	 * @param scNo
	 * @date 2018-1-23 下午5:19:28
	 */
	@RequestMapping(value = "/initRepaymentDocAjax")
	@ResponseBody
	public Map<String, Object> initRepaymentDocAjax(String ajaxData, String fincId, String scNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			JSONObject json = new JSONObject();
			DocBizManageParam docBizManageParam = new DocBizManageParam();
			docBizManageParam.setRelNo(fincId);
			docBizManageParam.setDime(scNo);
			docBizManageParam.setScNo(scNo);
			mfRepaymentConfigFeign.initRepaymentDoc(docBizManageParam);
			ajaxData = json.toString();
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("还款"));
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("还款"));
			throw e;
		}
		return dataMap;
	}

}

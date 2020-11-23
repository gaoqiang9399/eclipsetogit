/**
 * Copyright (C) DXHM 版权所有
 * 文件名： mfBusPactWkfAction.java
 * 包名： app.component.app.action
 * 说明：
 * @author zhs
 * @date 2016-5-26 上午9:35:29
 * @version V1.0
 */
package app.component.pact.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;

import app.component.common.ViewUtil;
import app.component.pact.entity.MfBusPact;
import app.component.pact.feign.MfBusPactFeign;
import app.component.wkf.entity.Result;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONObject;

/**
 * 类名： mfBusPactWkfAction 描述：
 * 
 * @author zhs
 * @date 2016-5-26 上午9:35:29
 *
 *
 */
@Controller
@RequestMapping("/mfBusFincWkf")
public class MfBusFincWkfController extends BaseFormBean {

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfBusPactFeign mfBusPactFeign;

	/**
	 * 
	 * 方法描述： 进入审批视角（审批页面）
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author zhs
	 * @date 2016-5-26 上午10:26:55
	 */

	@RequestMapping(value = "/getViewPoint")
	public String getViewPoint(Model model, String pactId, String activityType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, String> map = new HashMap<String, String>();
		MfBusPact mfBusPact = new MfBusPact();
		mfBusPact.setPactId(pactId);
		mfBusPact = mfBusPactFeign.getById(mfBusPact);
		map.put("pactId", pactId);
		map.put("appId", mfBusPact.getAppId());
		map.put("vouType", mfBusPact.getVouType());
		map.put("pactSts", mfBusPact.getPactSts());
		ViewUtil.setViewPointParm(request, map);
		HashMap<String, String> otMap = new HashMap<String, String>();

		if ("signtask".equals(activityType)) {
			otMap.put("1", "同意");
			otMap.put("5", "不同意");
		} else {
			otMap.put("1", "同意");
			otMap.put("3", "退回上一环节");
			otMap.put("4", "退回初审");
			otMap.put("2", "否决");
		}

		model.addAttribute("pactId", pactId);
		model.addAttribute("query", "");
		return "/component/pact/wkf/WkfViewPoint";
	}

	/**
	 * 
	 * 方法描述： 审批提交（审批意见保存）
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author zhs
	 * @date 2016-5-26 上午10:53:17
	 */

	@RequestMapping(value = "/submitUpdate")
	public String submitUpdate(Model model, String pactId, String activityType) throws Exception {
		ActionContext.initialize(request, response);
		MfBusPact mfBusPact = new MfBusPact();
		mfBusPact.setPactId(pactId);
		mfBusPact = mfBusPactFeign.getById(mfBusPact);
		model.addAttribute("mfBusPact", mfBusPact);
		Result res = new Result();
		if (!res.isSuccess()) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("pactId", pactId);
//			ViewUtil.setViewPointParm(request, map);
			Map<String, String> otMap = new HashMap<String, String>();

			if ("signtask".equals(activityType)) {
				otMap.put("1", "同意");
				otMap.put("5", "不同意");
			} else {
				otMap.put("1", "同意");
				otMap.put("3", "退回上一环节");
				otMap.put("4", "退回初审");
				otMap.put("2", "否决");
			}
			model.addAttribute("jsonBean", JSONObject.fromObject(map).toString());
			model.addAttribute("otMap", otMap);
			model.addAttribute("query", "");
			return "input";
		}

		model.addAttribute("pactId", pactId);
		model.addAttribute("query", "");
		return "/component/pact/MfBusPact_List";

	}

	/**
	 * 
	 * 方法描述： 审批提交（审批意见保存）
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author zhs
	 * @date 2016-5-26 上午10:53:17
	 */
	@RequestMapping(value = "/submitUpdateAjax")
	@ResponseBody
	public Map<String, Object> submitUpdateAjax(String pactId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusPact mfBusPact = new MfBusPact();
		mfBusPact.setPactId(pactId);
		mfBusPact = mfBusPactFeign.getById(mfBusPact);
		Result res = new Result();
		try {
			// res =
			// mfBusPactWkfFeign.doCommit(taskId,pactId,opinionType,approvalOpinion,transition,User.getRegNo(this.getHttpRequest()),nextUser,mfBusPact);
			if (res.isSuccess()) {
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_COMMIT.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SUBMIT.getMessage());
			throw e;
		}
		return dataMap;

	}

}

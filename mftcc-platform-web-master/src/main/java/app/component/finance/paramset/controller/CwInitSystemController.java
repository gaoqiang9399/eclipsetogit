/**
 * Copyright (C) DXHM 版权所有
 * 文件名： CwInitSystemAction.java
 * 包名： app.component.finance.paramset.action
 * 说明：
 * @author Javelin
 * @date 2017-2-6 上午9:42:10
 * @version V1.0
 */
package app.component.finance.paramset.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.google.gson.Gson;

import app.base.User;
import app.component.finance.paramset.feign.CwInitSystemFeign;
import app.component.finance.util.CwPublicUtil;
import app.component.finance.util.R;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.PropertiesUtil;

/**
 * 类名： CwInitSystemAction 描述：财务初始化
 * 
 * @author Javelin
 * @date 2017-2-6 上午9:42:10
 */
@Controller
@RequestMapping("/cwInitSystem")
public class CwInitSystemController extends BaseFormBean {

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入cwComItemFeign
	@Autowired
	private CwInitSystemFeign cwInitSystemFeign;
	// 异步参数

	/**
	 * 方法描述： AJAX 财务重新初始化操作
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-2-6 上午9:52:43
	 */
	@RequestMapping(value = "/reInitSystemAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reInitSystemAjax() throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String finBooks = (String) request.getSession().getAttribute("finBooks");
			// Map<String, Object> formMap = new Gson().fromJson(ajaxData,
			// Map.class);
			String opNo = User.getRegNo(request);
			String opName = User.getRegName(request);

			Map<String, String> opMap = new HashMap<String, String>();
			opMap.put("opNo", opNo);
			opMap.put("opName", opName);

			Map<String, Object> objmap = new HashMap<String, Object>();
			objmap.put("opMap", opMap);
			R r = cwInitSystemFeign.doReInitSystem(objmap,finBooks);
			if(r.isOk()) {
				dataMap.put("flag", "success");
				// dataMap.put("msg", "重新初始化成功");
				dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("重新初始化"));
			}else {
				dataMap.put("flag", "error");
				dataMap.put("msg", r.getMsg());
			}

		} catch (Exception e) {
			dataMap.put("flag", "error");
			// dataMap.put("msg", "重新初始化失败");
			dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("重新初始化"));
			e.printStackTrace();
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述： AJAX 财务初始化操作
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-2-7 下午4:39:03
	 */
	@RequestMapping(value = "/cwSystemInitAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> cwSystemInitAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> formMap = CwPublicUtil.getMapByJson(ajaxData);
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			R r = cwInitSystemFeign.doCwSystemInit(formMap,finBooks);
			if(r.isOk()) {
				dataMap.put("flag", "success");
				// dataMap.put("msg", "重新初始化成功");
				dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("重新初始化"));
			}else {
				dataMap.put("flag", "error");
				dataMap.put("msg", r.getMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			// dataMap.put("msg", "重新初始化失败");
			dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("重新初始化"));
			e.printStackTrace();
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述：分步骤 初始化 initType： week：启用期间初始化 comtype：科目编码规则初始化 comitem：科目
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-2-20 下午2:19:16
	 */
	@RequestMapping(value = "/cwSetInitPramAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> cwSetInitPramAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> formMap = new Gson().fromJson(ajaxData, Map.class);
			formMap.put("regNo", User.getRegNo(request));
			formMap.put("regName", User.getRegName(request));
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			R r = cwInitSystemFeign.doCwSetInitPram(formMap,finBooks);
			if(r.isOk()) {
				dataMap.put("flag", "success");
				// dataMap.put("msg", "重新初始化成功");
				dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("重新初始化"));
			}else {
				dataMap.put("flag", "error");
				dataMap.put("msg", r.getMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			// dataMap.put("msg", "重新初始化失败");
			dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("重新初始化"));
			e.printStackTrace();
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述： 获取系统初始化标志，sysInit：系统初始 balInit：余额初始
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-2-8 上午10:59:19
	 */
	@RequestMapping(value = "/getSysInitFlagAjax")
	@ResponseBody
	public Map<String, Object> getSysInitFlagAjax() throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {

			// 读取配置文件，查看是否单独使用财务系统，“true”是单独使用独立系统，“false”不使用财务的独立系统
			String aloneUsedCw = PropertiesUtil.getWebServiceProperty("alone_used_cw");
			dataMap.put("aloneUsedCw", aloneUsedCw);
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			 R r = cwInitSystemFeign.getSysInitFlag(finBooks);
			if(r.isOk()) {
				Map<String, String> flagMap =(Map<String, String>) r.getResult();
				dataMap.put("data", flagMap);
				dataMap.put("flag", "success");
				// dataMap.put("msg", "重新初始化成功");
				dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("重新初始化"));
			}else {
				dataMap.put("flag", "error");
				dataMap.put("msg", r.getMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			// dataMap.put("msg", "重新初始化失败");
			dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("重新初始化"));
			e.printStackTrace();
			throw e;
		}
		return dataMap;
	}

	/**
	 * lzs 查看是否单独使用财务系统
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/cwAloneUseAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> cwAloneUseAjax() throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {

			// 读取配置文件，查看是否单独使用财务系统，“true”是单独使用独立系统，“false”不使用财务的独立系统
			String aloneUsedCw = PropertiesUtil.getWebServiceProperty("alone_used_cw");
			dataMap.put("aloneUsedCw", aloneUsedCw);
			dataMap.put("flag", "success");
		} catch (Exception e) {
			dataMap.put("flag", "error");
//			logger.error("获取是否单独使用财务系统出差了", e);
			e.printStackTrace();
			throw e;
		}
		return dataMap;
	}

}

package app.component.frontview.controller;

import app.base.User;
import app.component.common.BizPubParm.WKF_NODE;
import app.component.common.EntityUtil;
import app.component.frontview.entity.MfFrontAppformVO;
import app.component.frontview.entity.MfFrontKind;
import app.component.frontview.feign.MfFrontAppformFeign;
import app.component.frontview.feign.MfFrontKindFeign;
import app.component.frontview.util.UploadUtils;
import app.component.prdct.entity.MfSysKind;
import app.component.prdctinterface.PrdctInterfaceFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.util.PropertiesUtil;
import cn.mftcc.util.StringUtil;
import com.core.domain.screen.FormActive;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Title: MfFrontKindAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri Jun 23 17:08:22 CST 2017
 **/
@Controller
@RequestMapping("/mfFrontKind")
public class MfFrontKindController extends BaseFormBean {
	private static Pattern humpPattern = Pattern.compile("[A-Z]");
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfFrontKindBo
	@Autowired
	private MfFrontKindFeign mfFrontKindFeign;
	@Autowired
	private MfFrontAppformFeign mfFrontAppformFeign;
	// 外部组件接口
	@Autowired
	private PrdctInterfaceFeign prdctInterfaceFeign;

	/**
	 * 移动端产品管理 获取appkind展示
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getAppKindConfig")
	public String getAppKindConfig(Model model) throws Exception {
		MfFrontKind mfFrontKind = new MfFrontKind();
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("mfFrontKind", mfFrontKind));
		List<MfFrontKind> mfFrontKindList = (List<MfFrontKind>) mfFrontKindFeign.getAllListByPage(ipage).getResult();
		model.addAttribute("mfFrontKindList", mfFrontKindList);
		model.addAttribute("query", "");
		model.addAttribute("factorWebUrl", UploadUtils.getFactorWebUrl());
		model.addAttribute("projectName", PropertiesUtil.getSysParamsProperty("sys.project.name"));
		return "/component/frontview/AppKindConfig";
	}

	/**
	 * 获取所有pc端 启用 产品供app端选择
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAppKindListPage")
	public String getAppKindListPage(Model model) throws Exception {
		MfSysKind mfSysKind = new MfSysKind();
		// mfSysKind.setUseFlag("1");
		List<MfSysKind> mfSysKindList = (List<MfSysKind>) prdctInterfaceFeign.getSysKindList(mfSysKind);
		model.addAttribute("mfSysKindList", mfSysKindList);
		return "/component/frontview/AppKindListPage";
	}

	@RequestMapping(value = "/getKindsByPageAjax")
	@ResponseBody
	public Map<String, Object> getKindsByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfSysKind mfSysKind = new MfSysKind();
		try {
			mfSysKind.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfSysKind.setCriteriaList(mfSysKind, ajaxData);// 我的筛选
			// this.getRoleConditions(mfSysKind,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = prdctInterfaceFeign.findByPage(ipage, mfSysKind);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
			/**
			 * ipage.setResult(tableHtml); dataMap.put("ipage",ipage); 需要改进的方法
			 * dataMap.put("tableData",tableHtml);
			 */
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw new Exception("分页获取所有产品列表异常", e);
		}
		return dataMap;
	}

	/**
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String kindNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = mfFrontKindFeign.insert1(kindNo);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "添加失败,请联系管理员");
			throw e;
		}
		return dataMap;
	}

	/**
	 * 移动端产品要素管理，根据申请产品进件时的表单生成
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getAppKindConfigList")
	public String getAppKindConfigList(Model model, String kindNo) throws Exception{
		FormService formService = new FormService();
		MfSysKind mfSysKind = new MfSysKind();
		mfSysKind = prdctInterfaceFeign.getSysKindById(kindNo);
		String kindName = mfSysKind.getKindName();
//		vouType=mfSysKind.getVouTypeDef();
//		mfSysKind.setVouType(vouType);//表单显示默认的担保方式
//		busModel=mfSysKind.getBusModel();//业务模式
		
		//根据产品编号获取产品表单 并读取表单内的字段初始值展示出来
		String formId = prdctInterfaceFeign.getFormId(kindNo, WKF_NODE.apply_input, null, null, User.getRegNo(request));
		FormData formapply0007_query = formService.getFormData(formId);
		
		getObjValue(formapply0007_query, mfSysKind);
		FormActive[] formActives = formapply0007_query.getFormActives();
		for (int i =0;i<formActives.length;i++) {//把获取到字段名换成数据库名称返回即把驼峰命名换成'_'分割
			FormActive formActive = formActives[i];
			String fieldname = formActive.getFieldName();
			fieldname = propertyToColumn(fieldname);
			formActive.setFieldName(fieldname);
		}
		Map<String, Object> result =mfFrontAppformFeign.getFormDataActives(kindNo,formActives);
		List<MfFrontAppformVO> savedList = (List<MfFrontAppformVO>) result.get("saved");
		List<MfFrontAppformVO> unsavedList = (List<MfFrontAppformVO>) result.get("unsaved");
		model.addAttribute("savedList", savedList);
		model.addAttribute("unsavedList", unsavedList);
		model.addAttribute("formapply0007_query", formapply0007_query);
		model.addAttribute("query", "");
		return "/component/frontview/AppKindConfigList";
	}

	/**
	 * 驼峰格式转数据库字段
	 * 
	 * @param property
	 * @return
	 */
	private String propertyToColumn(String property) {
		if (property == null || property.isEmpty()) {
			return "";
		}
		Matcher matcher = humpPattern.matcher(property);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	/**
	 * formService放到bo层新建表单路径不对为null
	 * 根据产品编号新建进件产品表单xml（以进件产品时的表单为模板）,判断创建过返回存在表单，没有则新建表单；
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pcKindDesignAjax")
	@ResponseBody
	public Map<String, Object> pcKindDesignAjax(String kindNo) throws Exception {
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			if (StringUtil.isBlank(kindNo)) {
				throw new IllegalArgumentException("根据产品创建新表单，产品编号不能为空");
			}
			// Map<String, Object> result = new HashMap<String,Object>();
			MfFrontKind condition = new MfFrontKind();
			condition.setKindNo(kindNo);
			MfFrontKind mfFrontKind = mfFrontKindFeign.getById(condition);
			if (null == mfFrontKind) {
				throw new NullPointerException("前端交易产品信息中未查询到该产品");
			}
			if (StringUtil.isBlank(mfFrontKind.getKindFormId())) {// 为空新建表单
				// 根据产品编号获取产品表单 并读取表单内的字段初始值展示出来
				 String formId = prdctInterfaceFeign.getFormId(kindNo, WKF_NODE.apply_input, null, null, User.getRegNo(request));
				FormData formapply0007_query = formService.getFormData(formId);

				formId = formapply0007_query.getFormId() + "_front" + kindNo;
				formapply0007_query.setFormId(formId);
				formapply0007_query.setTitle("title");
				formService.saveForm(formapply0007_query, "insert");
				dataMap.put("formId", formId);
				mfFrontKind.setKindFormId(formId);
				mfFrontKindFeign.update(mfFrontKind);
			} else {// 取已存在表单
				dataMap.put("formId", mfFrontKind.getKindFormId());
			}
			dataMap.put("flag", "success");
		} catch (Exception e) {
			// logger.error("创建pc交易前端产品进件表单异常",e);
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw e;
		}
		return dataMap;
	}

	@RequestMapping(value = "/deleteFormIdAjax")
	@ResponseBody
	public Map<String, Object> deleteFormIdAjax(String kindNo) throws Exception {
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfFrontKind condition = new MfFrontKind();
			condition.setKindNo(kindNo);
			MfFrontKind mfFrontKind = mfFrontKindFeign.getById(condition);
			if (null != mfFrontKind && !StringUtil.isBlank(mfFrontKind.getKindFormId())) {
				FormData form = formService.getFormData(mfFrontKind.getKindFormId());
				form.setFormId(mfFrontKind.getKindFormId());
				formService.saveForm(form, "delete");
				mfFrontKind.setKindFormId("");
				mfFrontKindFeign.update(mfFrontKind);
			}
			dataMap.put("flag", "success");
		} catch (Exception e) {
			// logger.error("删除交易前端pc产品进件表单异常", e);
			dataMap.put("flag", "error");
			throw e;
		}
		return dataMap;
	}

	/**
	 * AJAX更新产品描述信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateContentAjax")
	@ResponseBody
	public Map<String, Object> updateContentAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, Object> parmMap = getMapByJson(ajaxData);
			String kindNo = (String) parmMap.get("kindNo");
			String str = (String) parmMap.get("content");
			String fileType = (String) parmMap.get("fileType");
			MfFrontKind mfFrontKind = new MfFrontKind();
			mfFrontKind.setKindNo(kindNo);
			mfFrontKind.setKindDesc(str);
			mfFrontKind.setFileType(fileType);
			mfFrontKind = mfFrontKindFeign.update(mfFrontKind);
			dataMap.put("flag", "success");
			dataMap.put("msg", "更新成功");
			dataMap.put("fileName", mfFrontKind.getKindIconPath());
		} catch (Exception e) {
			e.printStackTrace();
			// logger.error("更新产品描述异常", e);
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw e;
		}
		return dataMap;
	}

	/**
	 * 更新产品配置是否启用
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateUseFlagAjax")
	@ResponseBody
	public Map<String, Object> updateUseFlagAjax(String ajaxData) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Gson gson = new Gson();
			@SuppressWarnings("unchecked")
			Map<String, Object> parmMap = gson.fromJson(ajaxData, HashMap.class);
			MfFrontKind mfFrontKind = new MfFrontKind();
			String kindNo = (String) parmMap.get("kindNo");
			String mobileUse = (String) parmMap.get("mobileUse");
			String pcUse = (String) parmMap.get("pcUse");
			mfFrontKind.setKindNo(kindNo);
			mfFrontKind.setMobileUse(mobileUse);
			mfFrontKind.setPcUse(pcUse);
			mfFrontKindFeign.update(mfFrontKind);
			dataMap.put("flag", "success");
			dataMap.put("msg", "更新成功");
		} catch (Exception e) {
			e.printStackTrace();
			// logger.error("更新产品使用状态异常", e);
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw e;
		}
		return dataMap;
	}

	/**
	 * 更新当天放款次数
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updatePutoutCountDayAjax")
	@ResponseBody
	public Map<String, Object> updatePutoutCountDayAjax(String ajaxData) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Gson gson = new Gson();
			@SuppressWarnings("unchecked")
			Map<String, Object> parmMap = gson.fromJson(ajaxData, HashMap.class);
			MfFrontKind mfFrontKind = new MfFrontKind();
			int putoutCountDay = 0;
			String kindNo = (String) parmMap.get("kindNo");
			String putoutCount = (String) parmMap.get("putoutCountDay");
			if (StringUtil.isNotBlank(putoutCount)) {
				putoutCountDay = Integer.valueOf(putoutCount);
			}
			mfFrontKind.setKindNo(kindNo);
			mfFrontKind.setPutoutCountDay(putoutCountDay);
			mfFrontKindFeign.update(mfFrontKind);
			dataMap.put("flag", "success");
			dataMap.put("msg", "更新成功");
		} catch (Exception e) {
			e.printStackTrace();
			// logger.error("更新放款量当天放款次数异常", e);
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw e;
		}
		return dataMap;
	}

	/**
	 * 更新当天放款金额
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updatePutoutAmtDayAjax")
	@ResponseBody
	public Map<String, Object> updatePutoutAmtDayAjax(String ajaxData) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Gson gson = new Gson();
			@SuppressWarnings("unchecked")
			Map<String, Object> parmMap = gson.fromJson(ajaxData, HashMap.class);
			MfFrontKind mfFrontKind = new MfFrontKind();
			double putoutAmtDay = 0.0;
			String kindNo = (String) parmMap.get("kindNo");
			String putoutAmt = (String) parmMap.get("putoutAmtDay");
			if (StringUtil.isNotBlank(putoutAmt)) {
				putoutAmtDay = Double.valueOf(putoutAmt);
			}
			mfFrontKind.setKindNo(kindNo);
			mfFrontKind.setPutoutAmtDay(putoutAmtDay);
			mfFrontKindFeign.update(mfFrontKind);
			dataMap.put("flag", "success");
			dataMap.put("msg", "更新成功");
		} catch (Exception e) {
			e.printStackTrace();
			// logger.error("更新放款量当天放款金额异常", e);
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw e;
		}
		return dataMap;
	}

	/**
	 * 更新当月放款次数
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updatePutoutCountMonthAjax")
	@ResponseBody
	public Map<String, Object> updatePutoutCountMonthAjax(String ajaxData) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Gson gson = new Gson();
			@SuppressWarnings("unchecked")
			Map<String, Object> parmMap = gson.fromJson(ajaxData, HashMap.class);
			MfFrontKind mfFrontKind = new MfFrontKind();
			int putoutCountMonth = 0;
			String kindNo = (String) parmMap.get("kindNo");
			String putoutCount = (String) parmMap.get("putoutCountMonth");
			if (StringUtil.isNotBlank(putoutCount)) {
				putoutCountMonth = Integer.valueOf(putoutCount);
			}
			mfFrontKind.setKindNo(kindNo);
			mfFrontKind.setPutoutCountMonth(putoutCountMonth);
			mfFrontKindFeign.update(mfFrontKind);
			dataMap.put("flag", "success");
			dataMap.put("msg", "更新成功");
		} catch (Exception e) {
			e.printStackTrace();
			// logger.error("更新放款量当月放款次数异常", e);
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw e;
		}
		return dataMap;
	}

	/**
	 * 更新当月放款金额
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updatePutoutAmtMonthAjax")
	@ResponseBody
	public Map<String, Object> updatePutoutAmtMonthAjax(String ajaxData) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Gson gson = new Gson();
			Map<String, Object> parmMap = gson.fromJson(ajaxData, HashMap.class);
			MfFrontKind mfFrontKind = new MfFrontKind();
			double putoutAmtMonth = 0.0;
			String kindNo = (String) parmMap.get("kindNo");
			String putoutAmt = (String) parmMap.get("putoutAmtMonth");
			if (StringUtil.isNotBlank(putoutAmt)) {
				putoutAmtMonth = Double.valueOf(putoutAmt);
			}
			mfFrontKind.setKindNo(kindNo);
			mfFrontKind.setPutoutAmtMonth(putoutAmtMonth);
			mfFrontKindFeign.update(mfFrontKind);
			dataMap.put("flag", "success");
			dataMap.put("msg", "更新成功");
		} catch (Exception e) {
			e.printStackTrace();
			// logger.error("更新放款量当月放款金额异常", e);
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw e;
		}
		return dataMap;
	}

	/**
	 * AJAX获取产品描述信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getContentByKindNoAjax")
	@ResponseBody
	public Map<String, Object> getContentByKindNoAjax(String kindNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfFrontKind mfFrontKind = new MfFrontKind();
			mfFrontKind.setKindNo(kindNo);
			MfFrontKind mfKind = mfFrontKindFeign.getById(mfFrontKind);
			if (null != mfKind) {
				dataMap.put("flag", "success");
				dataMap.put("msg", "获取产品描述信息成功");
				dataMap.put("str", mfKind.getKindDesc());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", "未找到编号" + kindNo + "产品");
			}
		} catch (Exception e) {
			e.printStackTrace();
			// logger.error("获取产品描述信息异常，产品编号:{}", kindNo, e);
			dataMap.put("flag", "error");
			dataMap.put("msg", "获取产品描述信息失败");
			throw e;
		}
		return dataMap;
	}

	/**
	 * 打开产品描述页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getMfFrontKindDescription")
	public String getMfFrontKindDescription(Model model, String kindNo, String kindName) throws Exception {
		FormService formService = new FormService();
		MfFrontKind mfFrontKind = new MfFrontKind();
		mfFrontKind.setKindNo(kindNo);
		mfFrontKind = mfFrontKindFeign.getById(mfFrontKind);
		mfFrontKind.setKindName(kindName);
		FormData formfrontkinddescription0001 = formService.getFormData("frontkinddescription0001");
		getObjValue(formfrontkinddescription0001, mfFrontKind);
		model.addAttribute("mfFrontKind", mfFrontKind);
		model.addAttribute("formfrontkinddescription0001", formfrontkinddescription0001);
		model.addAttribute("query", "");
		return "/component/frontview/MfFrontKindDescription";
	}
	/////////////////////////////

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		return "/component/frontview/MfFrontKind_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfFrontKind mfFrontKind = new MfFrontKind();
		try {
			mfFrontKind.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfFrontKind.setCriteriaList(mfFrontKind, ajaxData);// 我的筛选
			// mfFrontKind.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfFrontKind,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfFrontKind", mfFrontKind));
			ipage = mfFrontKindFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * ajax 异步 单个字段或多个字段更新
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formfrontkind0002 = formService.getFormData("frontkind0002");
		getFormValue(formfrontkind0002, getMapByJson(ajaxData));
		MfFrontKind mfFrontKindJsp = new MfFrontKind();
		setObjValue(formfrontkind0002, mfFrontKindJsp);
		MfFrontKind mfFrontKind = mfFrontKindFeign.getById(mfFrontKindJsp);
		if (mfFrontKind != null) {
			try {
				mfFrontKind = (MfFrontKind) EntityUtil.reflectionSetVal(mfFrontKind, mfFrontKindJsp, getMapByJson(ajaxData));
				mfFrontKindFeign.update(mfFrontKind);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			} catch (Exception e) {
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", "新增失败");
				throw e;
			}
		} else {
			dataMap.put("flag", "error");
			dataMap.put("msg", "编号不存在,保存失败");
		}
		return dataMap;
	}

	/**
	 * AJAX更新保存
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formfrontkind0002 = formService.getFormData("frontkind0002");
			getFormValue(formfrontkind0002, getMapByJson(ajaxData));
			if (this.validateFormData(formfrontkind0002)) {
				MfFrontKind mfFrontKind = new MfFrontKind();
				setObjValue(formfrontkind0002, mfFrontKind);
				mfFrontKindFeign.update(mfFrontKind);
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw e;
		}
		return dataMap;
	}

	/**
	 * AJAX获取查看
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String kindNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formfrontkind0002 = formService.getFormData("frontkind0002");
		MfFrontKind mfFrontKind = new MfFrontKind();
		mfFrontKind.setKindNo(kindNo);
		mfFrontKind = mfFrontKindFeign.getById(mfFrontKind);
		getObjValue(formfrontkind0002, mfFrontKind, formData);
		if (mfFrontKind != null) {
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}

	/**
	 * Ajax异步删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String kindNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfFrontKind mfFrontKind = new MfFrontKind();
		mfFrontKind.setKindNo(kindNo);
		try {
			mfFrontKindFeign.delete(mfFrontKind);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw e;
		}
		return dataMap;
	}

	/**
	 * 新增页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formfrontkind0002 = formService.getFormData("frontkind0002");
		model.addAttribute("formfrontkind0002", formfrontkind0002);
		model.addAttribute("query", "");
		return "/component/frontview/MfFrontKind_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String kindNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formfrontkind0002 = formService.getFormData("frontkind0001");
		getFormValue(formfrontkind0002);
		MfFrontKind mfFrontKind = new MfFrontKind();
		mfFrontKind.setKindNo(kindNo);
		mfFrontKind = mfFrontKindFeign.getById(mfFrontKind);
		getObjValue(formfrontkind0002, mfFrontKind);
		model.addAttribute("mfFrontKind", mfFrontKind);
		model.addAttribute("formfrontkind0002", formfrontkind0002);
		model.addAttribute("query", "");
		return "/component/frontview/MfFrontKind_Detail";
	}

	/**
	 * 新增校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateInsert")
	public void validateInsert(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formfrontkind0002 = formService.getFormData("frontkind0002");
		getFormValue(formfrontkind0002);
		boolean validateFlag = this.validateFormData(formfrontkind0002);
	}

	/**
	 * 修改校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateUpdate")
	public void validateUpdate(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formfrontkind0002 = formService.getFormData("frontkind0002");
		getFormValue(formfrontkind0002);
		boolean validateFlag = this.validateFormData(formfrontkind0002);
	}

}

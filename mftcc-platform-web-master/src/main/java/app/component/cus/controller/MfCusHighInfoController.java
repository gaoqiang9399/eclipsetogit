package app.component.cus.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import config.YmlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;

import app.component.common.EntityUtil;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.entity.MfCusHighInfo;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.component.cus.feign.MfCusHighInfoFeign;
import app.component.cus.feign.MfCusTableFeign;
import app.component.nmd.entity.ParmDic;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.WaterIdUtil;

/**
 * Title: MfCusHighInfoAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue May 31 09:01:54 CST 2016
 **/
@Controller
@RequestMapping("/mfCusHighInfo")
public class MfCusHighInfoController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfCusHighInfoBo
	@Autowired
	private MfCusHighInfoFeign mfCusHighInfoFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;

	@Autowired
	private MfCusFormConfigFeign mfCusFormConfigFeign;
	@Autowired
	private MfCusTableFeign mfCusTableFeign;
	@Autowired
	private YmlConfig ymlConfig;
	// 全局变量

	// 异步参数
	// 表单变量

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model, String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		MfCusHighInfo mfCusHighInfo = new MfCusHighInfo();
		mfCusHighInfo.setCusNo(cusNo);
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("mfCusHighInfo", mfCusHighInfo));
		List<MfCusHighInfo> mfCusHighInfoList = (List<MfCusHighInfo>) mfCusHighInfoFeign.findByPage(ipage).getResult();
		model.addAttribute("mfCusHighInfoList", mfCusHighInfoList);
		model.addAttribute("query", "");
		return "/component/cus/MfCusHighInfo_List";
	}

	/**
	 * 
	 * 方法描述： 获得大表单中高管人信息列表
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2016-5-31 上午10:32:21
	 */
	@RequestMapping(value = "/getListPageBig")
	public String getListPageBig(Model model, String cusNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcushigh00001 = formService.getFormData("cushigh00001");
		try {
			MfCusHighInfo mfCusHighInfo = new MfCusHighInfo();
			mfCusHighInfo.setCusNo(cusNo);
			Ipage ipage = this.getIpage();
			ipage.setParams(this.setIpageParams("mfCusHighInfo", mfCusHighInfo));
			List<MfCusHighInfo> mfCusHighInfoList = (List<MfCusHighInfo>) mfCusHighInfoFeign.findByPage(ipage)
					.getResult();
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		model.addAttribute("formcushigh00001", formcushigh00001);
		model.addAttribute("query", "");
		return "/component/cus/MfCusHighInfo_ListBig";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusHighInfo mfCusHighInfo = new MfCusHighInfo();
		try {
			mfCusHighInfo.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCusHighInfo.setCriteriaList(mfCusHighInfo, ajaxData);// 我的筛选
			// this.getRoleConditions(mfCusHighInfo,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfCusHighInfo", mfCusHighInfo));
			// 自定义查询Bo方法
			ipage = mfCusHighInfoFeign.findByPage(ipage);
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
			throw e;
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
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, Object> map = getMapByJson(ajaxData);
			String formId = (String) map.get("formId");
			if (StringUtil.isEmpty(formId)) {
				formId = mfCusFormConfigFeign.getByCusType("base", "MfCusHighInfoAction").getAddModel();
			}
			FormData formcushigh00003 = formService.getFormData(formId);
			getFormValue(formcushigh00003, map);
			if (this.validateFormData(formcushigh00003)) {
				MfCusHighInfo mfCusHighInfo = new MfCusHighInfo();
				setObjValue(formcushigh00003, mfCusHighInfo);
				String infIntegrity = "";
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(mfCusHighInfo.getCusNo());
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				String cusName = mfCusCustomer.getCusName();
				mfCusHighInfo.setCusName(cusName);
				if("1".equals(mfCusHighInfo.getHighCusType())){
				//法人代表唯一，如果是法人代表则检测是否已经存在法人代表
					MfCusHighInfo paramEntity = new MfCusHighInfo();
					paramEntity.setHighCusType("1");
					paramEntity.setCusNo(mfCusHighInfo.getCusNo());
					List<MfCusHighInfo> listHigh = mfCusHighInfoFeign.findByEntity(paramEntity);
					if(listHigh.size()>0){
						//如果把添加的实际控制人改为主要管理人 无法继续添加实际控制人问题
						dataMap.put("flag", "error");
						Map<String,String> paramMap = new HashMap<>();
						CodeUtils cu = new CodeUtils();
						List highTypeList = cu.getCacheByKeyName("HIGH_TYPE");
						for (Object o : highTypeList) {
							ParmDic p = (ParmDic) o;
							if("1".equals(p.getOptCode())){
								paramMap.put("content", p.getOptName());
								break;
							}
						}
						dataMap.put("msg", MessageEnum.EXIST_INFORMATION_EVAL.getMessage(paramMap));
						return dataMap;
					}
				}
				
				mfCusHighInfoFeign.insert(mfCusHighInfo);
				
				// 更新客户信息完整度
				infIntegrity = mfCusCustomerFeign.updateInfIntegrity(mfCusHighInfo.getCusNo(), mfCusHighInfo.getRelNo());
				
				String tableId = "tablecushigh00001";
				MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusHighInfoAction");
				if (mfCusFormConfig != null && StringUtil.isNotEmpty(mfCusFormConfig.getListModelDef())) {
					tableId = "table" + mfCusFormConfig.getListModelDef();
				}
				
				String cusNo = mfCusHighInfo.getCusNo();
				String relNo = mfCusHighInfo.getRelNo();
				mfCusHighInfo = new MfCusHighInfo();
				mfCusHighInfo.setCusNo(cusNo);
				mfCusHighInfo.setRelNo(relNo);
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("mfCusHighInfo", mfCusHighInfo));
				JsonTableUtil jtu = new JsonTableUtil();
				@SuppressWarnings("unchecked")
				List<MfCusHighInfo> list = (List<MfCusHighInfo>) mfCusHighInfoFeign.findByPage(ipage).getResult();
				String tableHtml = jtu.getJsonStr(tableId, "tableTag", list, null, true);
				dataMap.put("htmlStr", tableHtml);
				dataMap.put("htmlStrFlag", "1");
				dataMap.put("infIntegrity", infIntegrity);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
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
		FormData formcushigh00002 = formService.getFormData("cushigh00002");
		getFormValue(formcushigh00002, getMapByJson(ajaxData));
		MfCusHighInfo mfCusHighInfoJsp = new MfCusHighInfo();
		setObjValue(formcushigh00002, mfCusHighInfoJsp);
		MfCusHighInfo mfCusHighInfo = mfCusHighInfoFeign.getById(mfCusHighInfoJsp);
		if (mfCusHighInfo != null) {
			try {
				mfCusHighInfo = (MfCusHighInfo) EntityUtil.reflectionSetVal(mfCusHighInfo, mfCusHighInfoJsp,
						getMapByJson(ajaxData));
				mfCusHighInfoFeign.update(mfCusHighInfo);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
				throw e;
			}
		} else {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SAVE_CONTENT.getMessage("编号不存在"));
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
		MfCusHighInfo mfCusHighInfo = new MfCusHighInfo();
		try {
			Map map = getMapByJson(ajaxData);
			String formId = (String) map.get("formId");
			if (StringUtil.isEmpty(formId)) {
				formId = mfCusFormConfigFeign.getByCusType("base", "MfCusHighInfoAction").getAddModel();
			}
			FormData formcushigh00003 = formService.getFormData(formId);
			getFormValue(formcushigh00003, map);
			if (this.validateFormData(formcushigh00003)) {
				mfCusHighInfo = new MfCusHighInfo();
				setObjValue(formcushigh00003, mfCusHighInfo);
				mfCusHighInfoFeign.update(mfCusHighInfo);

				String cusNo = mfCusHighInfo.getCusNo();
				mfCusHighInfo = new MfCusHighInfo();
				mfCusHighInfo.setCusNo(cusNo);
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("mfCusHighInfo", mfCusHighInfo));
				JsonTableUtil jtu = new JsonTableUtil();
				String tableHtml = jtu.getJsonStr("tablecushigh00001", "tableTag",
						(List<MfCusHighInfo>) mfCusHighInfoFeign.findByPage(ipage).getResult(), null, true);
				dataMap.put("htmlStr", tableHtml);
				dataMap.put("htmlStrFlag", "1");

				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
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
	public Map<String, Object> getByIdAjax(String highId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcushigh00002 = formService.getFormData("cushigh00002");
		MfCusHighInfo mfCusHighInfo = new MfCusHighInfo();
		mfCusHighInfo.setHighId(highId);
		mfCusHighInfo = mfCusHighInfoFeign.getById(mfCusHighInfo);
		getObjValue(formcushigh00002, mfCusHighInfo, formData);
		if (mfCusHighInfo != null) {
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}

	/**
	 * AJAX获取查看
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdForOneAjax")
	@ResponseBody
	public Map<String, Object> getByIdForOneAjax(String highId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcushigh00002 = formService.getFormData("cushigh00002");
		MfCusHighInfo mfCusHighInfo = new MfCusHighInfo();
		mfCusHighInfo.setHighId(highId);
		mfCusHighInfo = mfCusHighInfoFeign.getById(mfCusHighInfo);
		getObjValue(formcushigh00002, mfCusHighInfo, formData);
		if (mfCusHighInfo != null) {
			dataMap.put("flag", "success");
			// 高管人员类别
			String highCusType = mfCusHighInfo.getHighCusType();
			Map<String, String> map = new CodeUtils().getMapByKeyName("HIGH_TYPE");
			highCusType = map.get(highCusType);
			dataMap.put("highCusType", highCusType);
			// 最高学历
			String education = mfCusHighInfo.getEducation();
			map = new CodeUtils().getMapByKeyName("EDU");
			education = map.get(education);
			dataMap.put("education", education);
			//
			dataMap.put("mfCusHighInfo", mfCusHighInfo);
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
	public Map<String, Object> deleteAjax(String highId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusHighInfo mfCusHighInfo = new MfCusHighInfo();
		mfCusHighInfo.setHighId(highId);
		try {
			// JSONObject jb = JSONObject.fromObject(ajaxData);
			// mfCusHighInfo = (MfCusHighInfo)JSONObject.toBean(jb,
			// MfCusHighInfo.class);
			mfCusHighInfoFeign.delete(mfCusHighInfo);
			// getTableData();
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
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
	public String input(Model model, String cusNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfCusHighInfo mfCusHighInfo = new MfCusHighInfo();
		mfCusHighInfo.setCusNo(cusNo);
		mfCusHighInfo.setRelNo(cusNo);
		mfCusHighInfo.setHighId(WaterIdUtil.getWaterId());
		String cusName = "";
		String cusType = "";
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		cusName = mfCusCustomer.getCusName();
		cusType = mfCusCustomer.getCusType();
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(cusType, "MfCusHighInfoAction");
		String formId = "";
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// logger.error("客户类型为" + cusType +
			// "的MfCusHighInfoAction表单信息没有查询到");
		} else {
			FormData formcushigh00003 = formService.getFormData(formId);
			this.changeFormProperty(formcushigh00003, "cusNo", "initValue", mfCusHighInfo.getCusNo());
			this.changeFormProperty(formcushigh00003, "highId", "initValue", mfCusHighInfo.getHighId());
			if (formcushigh00003.getFormId() == null) {
				// logger.error("客户类型为" + cusType + "的MfCusHighInfoAction表单form"
				// + formId + ".xml文件不存在");
			} else {
				getFormValue(formcushigh00003);
			}
			model.addAttribute("formcushigh00003", formcushigh00003);
		}

		model.addAttribute("query", "");
		return "/component/cus/MfCusHighInfo_Insert";
	}

	/**
	 * 
	 * 方法描述： 业务新增客户信息表单页面
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author zhs
	 * @date 2017-9-25 下午5:10:59
	 */
	@RequestMapping(value = "/inputBiz")
	public String inputBiz(Model model, String cusNo, String relNo, String kindNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfCusHighInfo mfCusHighInfo = new MfCusHighInfo();
		mfCusHighInfo.setCusNo(cusNo);
		mfCusHighInfo.setRelNo(relNo);
		mfCusHighInfo.setHighId(WaterIdUtil.getWaterId());
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(kindNo, "MfCusHighInfoAction");
		String formId = "";
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// logger.error("产品类型为" + kindNo + "的MfCusHighInfoAction表单信息没有查询到");
		} else {
			FormData formcushigh00003 = formService.getFormData(formId);
			this.changeFormProperty(formcushigh00003, "cusNo", "initValue", mfCusHighInfo.getCusNo());
			this.changeFormProperty(formcushigh00003, "highId", "initValue", mfCusHighInfo.getHighId());
			if (formcushigh00003.getFormId() == null) {
				// logger.error("产品类型为" + kindNo + "的MfCusHighInfoAction表单form"
				// + formId + ".xml文件不存在");
			} else {
				getFormValue(formcushigh00003);
			}
			model.addAttribute("formcushigh00003", formcushigh00003);
		}

		model.addAttribute("query", "");
		return "/component/cus/MfCusHighInfo_Insert";
	}

	/***
	 * 新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formcushigh00002 = formService.getFormData("cushigh00002");
		getFormValue(formcushigh00002);
		MfCusHighInfo mfCusHighInfo = new MfCusHighInfo();
		setObjValue(formcushigh00002, mfCusHighInfo);
		mfCusHighInfoFeign.insert(mfCusHighInfo);
		getObjValue(formcushigh00002, mfCusHighInfo);
		this.addActionMessage(model, "保存成功");
		List<MfCusHighInfo> mfCusHighInfoList = (List<MfCusHighInfo>) mfCusHighInfoFeign.findByPage(this.getIpage())
				.getResult();
		model.addAttribute("formcushigh00002", formcushigh00002);
		model.addAttribute("mfCusHighInfoList", mfCusHighInfoList);
		model.addAttribute("query", "");
		return "/component/cus/MfCusHighInfo_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String highId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfCusHighInfo mfCusHighInfo = new MfCusHighInfo();
		mfCusHighInfo.setHighId(highId);
		mfCusHighInfo = mfCusHighInfoFeign.getById(mfCusHighInfo);

		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(mfCusHighInfo.getCusNo());
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
				"MfCusHighInfoAction");
		String formId = "";
		if (mfCusFormConfig == null) {

		} else {
			if ("1".equals(mfCusFormConfig.getShowType())) {
				formId = mfCusFormConfig.getShowModelDef();
			} else {
				formId = mfCusFormConfig.getAddModelDef();
			}
		}
		if (StringUtil.isEmpty(formId)) {
			// logger.error("客户类型为" + mfCusCustomer.getCusType() +
			// "的MfCusHighInfoAction表单信息没有查询到");
		} else {
			FormData formcushigh00003 = formService.getFormData(formId);
			if (formcushigh00003.getFormId() == null) {
				/*
				 * logger.error( "客户类型为" + mfCusCustomer.getCusType() +
				 * "的MfCusHighInfoAction表单form" + formId + ".xml文件不存在");
				 */
			} else {
				getFormValue(formcushigh00003);
				getObjValue(formcushigh00003, mfCusHighInfo);
			}
			model.addAttribute("formcushigh00003", formcushigh00003);
		}

		model.addAttribute("query", "");
		return "/component/cus/MfCusHighInfo_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String highId, String cusNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusHighInfo mfCusHighInfo = new MfCusHighInfo();
		mfCusHighInfo.setHighId(highId);
		mfCusHighInfoFeign.delete(mfCusHighInfo);
		return getListPage(model, cusNo);
	}

	@RequestMapping(value = "/listShowDetailAjax")
	@ResponseBody
	public Map<String, Object> listShowDetailAjax(String cusNo, String highId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String cusType = "";
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		cusType = mfCusCustomer.getCusType();

		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(cusType, "MfCusHighInfoAction");
		String formId = "";
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getShowModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// logger.error("客户类型为" + cusType +
			// "的MfCusHighInfoAction表单信息没有查询到");
		} else {
			Map<String, Object> formData = new HashMap<String, Object>();
			request.setAttribute("ifBizManger","3");
			dataMap = new HashMap<String, Object>();
			JsonFormUtil jsonFormUtil = new JsonFormUtil();
			FormData formcushigh00004 = formService.getFormData(formId);
			MfCusHighInfo mfCusHighInfo = new MfCusHighInfo();
			mfCusHighInfo.setHighId(highId);
			mfCusHighInfo = mfCusHighInfoFeign.getById(mfCusHighInfo);
			getObjValue(formcushigh00004, mfCusHighInfo, formData);
			String htmlStrCorp = jsonFormUtil.getJsonStr(formcushigh00004, "propertySeeTag", "");
			if (mfCusHighInfo != null) {
				dataMap.put("formHtml", htmlStrCorp);
				dataMap.put("flag", "success");
			} else {
				dataMap.put("msg", "获取详情失败");
				dataMap.put("flag", "error");
			}
			dataMap.put("formData", mfCusHighInfo);
		}
		return dataMap;
	}

	/**
	 * ajax 异步 单个字段或多个字段更新
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateByOneAjax")
	@ResponseBody
	public Map<String, Object> updateByOneAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusHighInfo mfCusHighInfo = new MfCusHighInfo();
		Map map = getMapByJson(ajaxData);
		String formId = (String) map.get("formId");
		FormData formcushigh00004 = formService.getFormData(formId);
		getFormValue(formcushigh00004, getMapByJson(ajaxData));
		MfCusHighInfo mfCusHighInfoNew = new MfCusHighInfo();
		setObjValue(formcushigh00004, mfCusHighInfoNew);
		mfCusHighInfo.setHighId(mfCusHighInfoNew.getHighId());
		mfCusHighInfo = mfCusHighInfoFeign.getById(mfCusHighInfo);
		if (mfCusHighInfo != null) {
			try {
				mfCusHighInfo = (MfCusHighInfo) EntityUtil.reflectionSetVal(mfCusHighInfo, mfCusHighInfoNew,
						getMapByJson(ajaxData));
				mfCusHighInfoFeign.update(mfCusHighInfo);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
				throw new Exception(e.getMessage());
			}
		} else {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SAVE_CONTENT.getMessage("编号不存在"));
		}
		return dataMap;
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
		FormData formcushigh00002 = formService.getFormData("cushigh00002");
		getFormValue(formcushigh00002);
		boolean validateFlag = this.validateFormData(formcushigh00002);
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
		FormData formcushigh00002 = formService.getFormData("cushigh00002");
		getFormValue(formcushigh00002);
		boolean validateFlag = this.validateFormData(formcushigh00002);
	}

	private void getTableData(String cusNo, String tableId) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		MfCusHighInfo mfCusHighInfo = new MfCusHighInfo();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		mfCusHighInfo.setCusNo(cusNo);
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("mfCusHighInfo", mfCusHighInfo));
		String tableHtml = jtu.getJsonStr(tableId, "tableTag",
				(List<MfCusHighInfo>) mfCusHighInfoFeign.findByPage(ipage).getResult(), null, true);
		dataMap.put("tableData", tableHtml);
	}

}

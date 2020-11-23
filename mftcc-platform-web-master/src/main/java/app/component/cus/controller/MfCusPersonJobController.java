package app.component.cus.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.core.domain.screen.FormActive;
import net.sf.json.JSONObject;
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
import app.component.cus.entity.MfCusFamilyInfo;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.entity.MfCusGoods;
import app.component.cus.entity.MfCusPersonJob;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.component.cus.feign.MfCusPersonJobFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;

/**
 * Title: MfCusPersonJobAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Jun 01 16:14:12 CST 2016
 **/
@Controller
@RequestMapping("/mfCusPersonJob")
public class MfCusPersonJobController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfCusPersonJobBo
	@Autowired
	private MfCusPersonJobFeign mfCusPersonJobFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	@Autowired
	private MfCusFormConfigFeign mfCusFormConfigFeign;

	// 异步参数
	// 表单变量
	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		return "/component/cus/MfCusPersonJob_List";
	}

	/**
	 * 
	 * 方法描述： 获得职业信息列表
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2016-6-1 下午6:02:05
	 */
	@RequestMapping(value = "/getListPageBig")
	public String getListPageBig(Model model, String cusNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formcusjob00001 = null;
		try {
			formcusjob00001 = formService.getFormData("cusjob00001");
			MfCusPersonJob mfCusPersonJob = new MfCusPersonJob();
			mfCusPersonJob.setCusNo(cusNo);
			Ipage ipage = this.getIpage();
			ipage.setParams(this.setIpageParams("mfCusPersonJob", mfCusPersonJob));
			List<MfCusPersonJob> mfCusPersonJobList = (List<MfCusPersonJob>) mfCusPersonJobFeign.findByPage(ipage)
					.getResult();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		model.addAttribute("formcusjob00001", formcusjob00001);
		model.addAttribute("query", "");
		return "/component/cus/MfCusPersonJob_ListBig";
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
		MfCusPersonJob mfCusPersonJob = new MfCusPersonJob();
		try {
			mfCusPersonJob.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCusPersonJob.setCriteriaList(mfCusPersonJob, ajaxData);// 我的筛选
			// this.getRoleConditions(mfCusPersonJob,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfCusPersonJob", mfCusPersonJob));
			ipage = mfCusPersonJobFeign.findByPage(ipage);
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
			String formId = map.get("formId").toString();
			FormData formcusjob00002 = formService.getFormData(formId);
			getFormValue(formcusjob00002, getMapByJson(ajaxData));
			if (this.validateFormData(formcusjob00002)) {
				MfCusPersonJob mfCusPersonJob = new MfCusPersonJob();
				setObjValue(formcusjob00002, mfCusPersonJob);
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(mfCusPersonJob.getCusNo());
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				String cusName = mfCusCustomer.getCusName();
				mfCusPersonJob.setCusName(cusName);
				mfCusPersonJobFeign.insert(mfCusPersonJob);

				String infIntegrity = mfCusCustomerFeign.updateInfIntegrity(mfCusPersonJob.getCusNo(),
						mfCusPersonJob.getRelNo());// 更新客户信息完整度
				JsonTableUtil jtu = new JsonTableUtil();
				String cusNo = mfCusPersonJob.getCusNo();
				String relNo = mfCusPersonJob.getRelNo();

				mfCusCustomer.setCusNo(mfCusPersonJob.getCusNo());
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
						"MfCusPersonJobAction");
				if (mfCusFormConfig == null) {

				} else {
					formId = mfCusFormConfig.getListModelDef();
				}

				mfCusPersonJob.setCusNo(cusNo);
				mfCusPersonJob.setRelNo(relNo);
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("mfCusPersonJob", mfCusPersonJob));
				String tableHtml = jtu.getJsonStr("table" + formId, "tableTag",
						(List<MfCusGoods>) mfCusPersonJobFeign.findByPage(ipage).getResult(), null, true);
				dataMap.put("htmlStr", tableHtml);
				dataMap.put("htmlStrFlag", "1");
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
				dataMap.put("flag", "success");
				dataMap.put("infIntegrity", infIntegrity);
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
		FormData formcusjob00002 = formService.getFormData("cusjob00002");
		getFormValue(formcusjob00002, getMapByJson(ajaxData));
		MfCusPersonJob mfCusPersonJobJsp = new MfCusPersonJob();
		setObjValue(formcusjob00002, mfCusPersonJobJsp);
		MfCusPersonJob mfCusPersonJob = mfCusPersonJobFeign.getById(mfCusPersonJobJsp);
		if (mfCusPersonJob != null) {
			try {
				mfCusPersonJob = (MfCusPersonJob) EntityUtil.reflectionSetVal(mfCusPersonJob, mfCusPersonJobJsp,
						getMapByJson(ajaxData));
				mfCusPersonJobFeign.update(mfCusPersonJob);
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
		MfCusPersonJob mfCusPersonJob = new MfCusPersonJob();
		try {
			Map map = getMapByJson(ajaxData);
			String formId = (String) map.get("formId");
			if (StringUtil.isEmpty(formId)) {
				formId = mfCusFormConfigFeign.getByCusType("base", "MfCusPersonJobAction").getAddModel();
			}
			FormData formcusjob00002 = formService.getFormData(formId);
			getFormValue(formcusjob00002, getMapByJson(ajaxData));
			if (this.validateFormData(formcusjob00002)) {
				setObjValue(formcusjob00002, mfCusPersonJob);
				String cusNo = mfCusPersonJob.getCusNo();
				mfCusPersonJobFeign.update(mfCusPersonJob);

				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(mfCusPersonJob.getCusNo());
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);

				String tableId = "tablecusjob00001";
				MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
						"MfCusPersonJobAction");
				if (mfCusFormConfig != null && StringUtil.isNotEmpty(mfCusFormConfig.getListModelDef())) {
					tableId = "table" + mfCusFormConfig.getListModelDef();
				}

				mfCusPersonJob = new MfCusPersonJob();
				mfCusPersonJob.setCusNo(cusNo);
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("mfCusPersonJob", mfCusPersonJob));
				JsonTableUtil jtu = new JsonTableUtil();
				String tableHtml = jtu.getJsonStr(tableId, "tableTag", (List<MfCusPersonJob>) mfCusPersonJobFeign.findByPage(ipage).getResult(), null, true);
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
	public Map<String, Object> getByIdAjax(String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcusjob00002 = formService.getFormData("cusjob00002");
		MfCusPersonJob mfCusPersonJob = new MfCusPersonJob();
		mfCusPersonJob.setId(id);
		mfCusPersonJob = mfCusPersonJobFeign.getById(mfCusPersonJob);
		getObjValue(formcusjob00002, mfCusPersonJob, formData);
		if (mfCusPersonJob != null) {
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
	public Map<String, Object> deleteAjax(String ajaxData, String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusPersonJob mfCusPersonJob = new MfCusPersonJob();
		mfCusPersonJob.setId(id);
		try {
			mfCusPersonJobFeign.delete(mfCusPersonJob);
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
	public String input(Model model, String cusNo, String relNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formcusjob00002 = null;
		MfCusPersonJob mfCusPersonJob = new MfCusPersonJob();
		mfCusPersonJob.setCusNo(cusNo);
		mfCusPersonJob.setRelNo(relNo);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
				"MfCusPersonJobAction");
		String formId = "";
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusGoodsAction表单信息没有查询到");
		} else {
			formcusjob00002 = formService.getFormData(formId);
			if (formcusjob00002.getFormId() == null) {
				// logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusGoodsAction表单form"+formId+".xml文件不存在");
			} else {
				getFormValue(formcusjob00002);
				mfCusPersonJob.setCusName(mfCusCustomer.getCusName());
				getObjValue(formcusjob00002, mfCusPersonJob);
			}
		}
		model.addAttribute("formcusjob00002", formcusjob00002);
		model.addAttribute("query", "");
		return "/component/cus/MfCusPersonJob_Insert";
	}

	@RequestMapping(value = "/inputBiz")
	public String inputBiz(Model model, String cusNo, String relNo, String kindNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formcusjob00002 = null;
		MfCusPersonJob mfCusPersonJob = new MfCusPersonJob();
		mfCusPersonJob.setCusNo(cusNo);
		mfCusPersonJob.setRelNo(relNo);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(kindNo, "MfCusPersonJobAction");
		String formId = "";
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// logger.error("产品类型为"+ kindNo +"的MfCusGoodsAction表单信息没有查询到");
		} else {
			formcusjob00002 = formService.getFormData(formId);
			if (formcusjob00002.getFormId() == null) {
				// logger.error("产品类型为"+ kindNo
				// +"的MfCusGoodsAction表单form"+formId+".xml文件不存在");
			} else {
				getFormValue(formcusjob00002);
				mfCusPersonJob.setCusName(mfCusCustomer.getCusName());
				getObjValue(formcusjob00002, mfCusPersonJob);
			}
		}
		model.addAttribute("formcusjob00002", formcusjob00002);
		model.addAttribute("query", "");
		return "/component/cus/MfCusPersonJob_Insert";
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
		FormData formcusjob00002 = formService.getFormData("cusjob00002");
		getFormValue(formcusjob00002);
		MfCusPersonJob mfCusPersonJob = new MfCusPersonJob();
		setObjValue(formcusjob00002, mfCusPersonJob);
		mfCusPersonJobFeign.insert(mfCusPersonJob);
		getObjValue(formcusjob00002, mfCusPersonJob);
		this.addActionMessage(model, "保存成功");
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("mfCusPersonJob", mfCusPersonJob));
		List<MfCusPersonJob> mfCusPersonJobList = (List<MfCusPersonJob>) mfCusPersonJobFeign.findByPage(ipage)
				.getResult();
		model.addAttribute("formcusjob00002", formcusjob00002);
		model.addAttribute("mfCusPersonJobList", mfCusPersonJobList);
		model.addAttribute("query", "");
		return "/component/cus/MfCusPersonJob_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formcusjob00002 = formService.getFormData("cusJobBase");
		getFormValue(formcusjob00002);
		MfCusPersonJob mfCusPersonJob = new MfCusPersonJob();
		mfCusPersonJob.setId(id);
		mfCusPersonJob = mfCusPersonJobFeign.getById(mfCusPersonJob);
		getObjValue(formcusjob00002, mfCusPersonJob);
		model.addAttribute("formcusJobBase", formcusjob00002);
		model.addAttribute("query", "");
		return "/component/cus/MfCusPersonJob_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String id) throws Exception {
		ActionContext.initialize(request, response);
		MfCusPersonJob mfCusPersonJob = new MfCusPersonJob();
		mfCusPersonJob.setId(id);
		mfCusPersonJobFeign.delete(mfCusPersonJob);
		return getListPage(model);
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
		FormData formcusjob00002 = formService.getFormData("cusjob00002");
		getFormValue(formcusjob00002);
		boolean validateFlag = this.validateFormData(formcusjob00002);
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
		FormData formcusjob00002 = formService.getFormData("cusjob00002");
		getFormValue(formcusjob00002);
		boolean validateFlag = this.validateFormData(formcusjob00002);
	}

	/**
	 * 
	 * 方法描述：
	 * 
	 * @throws Exception
	 *             void
	 * @author 沈浩兵
	 * @date 2016-6-1 下午6:06:56
	 */
	private void getTableData(String tableId, String cusNo, Map<String, Object> dataMap) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		MfCusPersonJob mfCusPersonJob = new MfCusPersonJob();
		mfCusPersonJob.setCusNo(cusNo);
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("mfCusPersonJob", mfCusPersonJob));
		String tableHtml = jtu.getJsonStr(tableId, "tableTag",
				(List<MfCusFamilyInfo>) mfCusPersonJobFeign.findByPage(ipage).getResult(), null, true);
		dataMap.put("htmlStr", tableHtml);
	}

	// 列表展示详情，单字段编辑
	@RequestMapping(value = "/listShowDetailAjax")
	@ResponseBody
	public Map<String, Object> listShowDetailAjax(String cusNo, String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
				"MfCusPersonJobAction");
		String formId = "";
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getShowModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusPersonJobAction表单信息没有查询到");
		} else {
			Map<String, Object> formData = new HashMap<String, Object>();
			request.setAttribute("ifBizManger", "3");
			JsonFormUtil jsonFormUtil = new JsonFormUtil();
			FormData formcusjob00003 = formService.getFormData(formId);
			MfCusPersonJob mfCusPersonJob = new MfCusPersonJob();
			mfCusPersonJob.setId(id);
			mfCusPersonJob = mfCusPersonJobFeign.getById(mfCusPersonJob);
			getObjValue(formcusjob00003, mfCusPersonJob, formData);
			String htmlStrCorp = jsonFormUtil.getJsonStr(formcusjob00003, "propertySeeTag", "");
			if (mfCusPersonJob != null) {
				dataMap.put("formHtml", htmlStrCorp);
				dataMap.put("flag", "success");
			} else {
				dataMap.put("msg", "获取详情失败");
				dataMap.put("flag", "error");
			}
			dataMap.put("formData", mfCusPersonJob);
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
	public Map<String, Object> updateByOneAjax(String ajaxData, String formId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusPersonJob mfCusPersonJob = new MfCusPersonJob();
		Map map = getMapByJson(ajaxData);
		formId = (String) map.get("formId");
		FormData formcusjob00003 = formService.getFormData(formId);
		getFormValue(formcusjob00003, getMapByJson(ajaxData));
		MfCusPersonJob mfCusPersonJobNew = new MfCusPersonJob();
		setObjValue(formcusjob00003, mfCusPersonJobNew);
		mfCusPersonJob.setId(mfCusPersonJobNew.getId());
		mfCusPersonJob = mfCusPersonJobFeign.getById(mfCusPersonJob);
		if (mfCusPersonJob != null) {
			try {
				mfCusPersonJob = (MfCusPersonJob) EntityUtil.reflectionSetVal(mfCusPersonJob, mfCusPersonJobNew,
						getMapByJson(ajaxData));
				mfCusPersonJobFeign.update(mfCusPersonJob);
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

}

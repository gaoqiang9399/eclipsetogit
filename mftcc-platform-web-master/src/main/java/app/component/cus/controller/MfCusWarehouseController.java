package app.component.cus.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import app.component.cus.entity.MfCusWarehouse;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.component.cus.feign.MfCusTableFeign;
import app.component.cus.feign.MfCusWarehouseFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;

/**
 * Title: MfCusWarehouseAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu Oct 13 15:24:44 CST 2016
 **/
@Controller
@RequestMapping("/mfCusWarehouse")
public class MfCusWarehouseController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfCusWarehouseBo
	@Autowired
	private MfCusWarehouseFeign mfCusWarehouseFeign;
	@Autowired
	private MfCusTableFeign mfCusTableFeign;
	@Autowired
	private MfCusFormConfigFeign mfCusFormConfigFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model, String cusNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfCusWarehouse mfCusWarehouse = new MfCusWarehouse();
		mfCusWarehouse.setCusNo(cusNo);
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("mfCusWarehouse", mfCusWarehouse));
		List<MfCusWarehouse> mfCusWarehouseList = (List<MfCusWarehouse>) mfCusWarehouseFeign.findByPage(ipage)
				.getResult();
		model.addAttribute("query", "");
		return "/component/cus/MfCusWarehouse_List";
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
		MfCusWarehouse mfCusWarehouse = new MfCusWarehouse();
		try {
			mfCusWarehouse.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCusWarehouse.setCriteriaList(mfCusWarehouse, ajaxData);// 我的筛选
			// this.getRoleConditions(mfCusWarehouse,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfCusWarehouse", mfCusWarehouse));
			ipage = mfCusWarehouseFeign.findByPage(ipage);
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
            Map <String, Object> map = getMapByJson(ajaxData) == null ? new HashMap() : getMapByJson(ajaxData);
            String formId = String.valueOf(map.get("formId"));
			FormData formcuswarehouse0001 = formService.getFormData(formId);
			getFormValue(formcuswarehouse0001, map);

			if (this.validateFormData(formcuswarehouse0001)) {
				MfCusWarehouse mfCusWarehouse = new MfCusWarehouse();
				setObjValue(formcuswarehouse0001, mfCusWarehouse);
				mfCusWarehouseFeign.insert(mfCusWarehouse);

				String infIntegrity = mfCusCustomerFeign.updateInfIntegrity(mfCusWarehouse.getCusNo(),
						mfCusWarehouse.getRelNo());// 更新客户信息完整度
				String cusNo = mfCusWarehouse.getCusNo();
				String relNo = mfCusWarehouse.getRelNo();
				mfCusWarehouse.setCusNo(cusNo);
				mfCusWarehouse.setRelNo(relNo);
				Ipage ipage = this.getIpage();
				JsonTableUtil jtu = new JsonTableUtil();
				ipage.setParams(this.setIpageParams("mfCusWarehouse", mfCusWarehouse));
                MfCusCustomer mfCusCustomer = new MfCusCustomer();
                mfCusCustomer.setCusNo(cusNo);
                mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
                MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
                        "MfCusWarehouseAction");
                String tableId = "tableCusWarehouseList";
                if (mfCusFormConfig != null) {
                    tableId = "table" + mfCusFormConfig.getListModelDef();
                }
                String tableHtml = jtu.getJsonStr(tableId, "tableTag",
                        (List<MfCusWarehouse>) mfCusWarehouseFeign.findByPage(ipage).getResult(), null, true);
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
		FormData formcuswarehouse0002 = formService.getFormData("cuswarehouse0002");
		getFormValue(formcuswarehouse0002, getMapByJson(ajaxData));
		MfCusWarehouse mfCusWarehouseJsp = new MfCusWarehouse();
		setObjValue(formcuswarehouse0002, mfCusWarehouseJsp);
		MfCusWarehouse mfCusWarehouse = mfCusWarehouseFeign.getById(mfCusWarehouseJsp);
		if (mfCusWarehouse != null) {
			try {
				mfCusWarehouse = (MfCusWarehouse) EntityUtil.reflectionSetVal(mfCusWarehouse, mfCusWarehouseJsp,
						getMapByJson(ajaxData));
				mfCusWarehouseFeign.update(mfCusWarehouse);
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
		MfCusWarehouse mfCusWarehouse = new MfCusWarehouse();
		try {
			Map map = getMapByJson(ajaxData);
			String formId = (String) map.get("formId");
			if (StringUtil.isEmpty(formId)) {
				formId = mfCusFormConfigFeign.getByCusType("base", "MfCusWarehouseAction").getAddModel();
			}
			FormData formcuswarehouse0001 = formService.getFormData(formId);
			getFormValue(formcuswarehouse0001, map);
			if (this.validateFormData(formcuswarehouse0001)) {
				setObjValue(formcuswarehouse0001, mfCusWarehouse);
				mfCusWarehouseFeign.update(mfCusWarehouse);

				String cusNo = mfCusWarehouse.getCusNo();
				mfCusWarehouse.setCusNo(cusNo);
				Ipage ipage = this.getIpage();
				JsonTableUtil jtu = new JsonTableUtil();
				ipage.setParams(this.setIpageParams("mfCusWarehouse", mfCusWarehouse));
                MfCusCustomer mfCusCustomer = new MfCusCustomer();
                mfCusCustomer.setCusNo(cusNo);
                mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
                MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
                        "MfCusWarehouseAction");
                String tableId = "tableCusWarehouseListBase";
                if (mfCusFormConfig != null) {
                    tableId = "table" + mfCusFormConfig.getListModelDef();
                }
				String tableHtml = jtu.getJsonStr(tableId, "tableTag",
						(List<MfCusWarehouse>) mfCusWarehouseFeign.findByPage(ipage).getResult(), null, true);
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
		FormData formcuswarehouse0002 = formService.getFormData("cuswarehouse0002");
		MfCusWarehouse mfCusWarehouse = new MfCusWarehouse();
		mfCusWarehouse.setId(id);
		mfCusWarehouse = mfCusWarehouseFeign.getById(mfCusWarehouse);
		getObjValue(formcuswarehouse0002, mfCusWarehouse, formData);
		if (mfCusWarehouse != null) {
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
	public Map<String, Object> deleteAjax(String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusWarehouse mfCusWarehouse = new MfCusWarehouse();
		mfCusWarehouse.setId(id);
		try {
			mfCusWarehouseFeign.delete(mfCusWarehouse);
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
		FormData formcuswarehouse0001 = null;
		MfCusWarehouse mfCusWarehouse = new MfCusWarehouse();
		mfCusWarehouse.setCusNo(cusNo);
		mfCusWarehouse.setRelNo(relNo);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
				"MfCusWarehouseAction");
		String formId = "";
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusWarehouseAction表单信息没有查询到");
		} else {
			formcuswarehouse0001 = formService.getFormData(formId);
			if (formcuswarehouse0001.getFormId() == null) {
				// logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusWarehouseAction表单form"+formId+".xml文件不存在");
			} else {
				getFormValue(formcuswarehouse0001);
				getObjValue(formcuswarehouse0001, mfCusWarehouse);
			}
		}
		model.addAttribute("formcuswarehouse0001", formcuswarehouse0001);
		model.addAttribute("query", "");
		return "/component/cus/MfCusWarehouse_Insert";
	}

	@RequestMapping(value = "/inputBiz")
	public String inputBiz(Model model, String cusNo, String relNo, String kindNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formcuswarehouse0001 = null;
		MfCusWarehouse mfCusWarehouse = new MfCusWarehouse();
		mfCusWarehouse.setCusNo(cusNo);
		mfCusWarehouse.setRelNo(relNo);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(kindNo, "MfCusWarehouseAction");
		String formId = "";
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// logger.error("产品类型为"+kindNo+"的MfCusWarehouseAction表单信息没有查询到");
		} else {
			formcuswarehouse0001 = formService.getFormData(formId);
			if (formcuswarehouse0001.getFormId() == null) {
				// logger.error("产品类型为"+kindNo+"的MfCusWarehouseAction表单form"+formId+".xml文件不存在");
			} else {
				getFormValue(formcuswarehouse0001);
				getObjValue(formcuswarehouse0001, mfCusWarehouse);
			}
		}
		model.addAttribute("formcuswarehouse0001", formcuswarehouse0001);
		model.addAttribute("query", "");
		return "/component/cus/MfCusWarehouse_Insert";
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
		FormData formcuswarehouse0002 = formService.getFormData("cuswarehouse0002");
		getFormValue(formcuswarehouse0002);
		MfCusWarehouse mfCusWarehouse = new MfCusWarehouse();
		setObjValue(formcuswarehouse0002, mfCusWarehouse);
		mfCusWarehouseFeign.insert(mfCusWarehouse);
		getObjValue(formcuswarehouse0002, mfCusWarehouse);
		this.addActionMessage(model, "保存成功");
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("mfCusWarehouse", mfCusWarehouse));
		List<MfCusWarehouse> mfCusWarehouseList = (List<MfCusWarehouse>) mfCusWarehouseFeign.findByPage(ipage)
				.getResult();
		model.addAttribute("formcuswarehouse0002", formcuswarehouse0002);
		model.addAttribute("mfCusWarehouseList", mfCusWarehouseList);
		model.addAttribute("query", "");
		return "/component/cus/MfCusWarehouse_Insert";
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
		MfCusWarehouse mfCusWarehouse = new MfCusWarehouse();
		FormData formcuswarehouse0001 = null;
		mfCusWarehouse.setId(id);
		mfCusWarehouse = mfCusWarehouseFeign.getById(mfCusWarehouse);

		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(mfCusWarehouse.getCusNo());
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
				"MfCusWarehouseAction");
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
			// logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusWarehouseAction表单信息没有查询到");
		} else {
			formcuswarehouse0001 = formService.getFormData(formId);
			if (formcuswarehouse0001.getFormId() == null) {
				// logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusWarehouseAction表单form"+formId+".xml文件不存在");
			} else {
				getFormValue(formcuswarehouse0001);
				getObjValue(formcuswarehouse0001, mfCusWarehouse);
			}
		}

		model.addAttribute("formcuswarehouse0001", formcuswarehouse0001);
		model.addAttribute("query", "");
		return "/component/cus/MfCusWarehouse_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String ajaxData, String id, String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		MfCusWarehouse mfCusWarehouse = new MfCusWarehouse();
		mfCusWarehouse.setId(id);
		mfCusWarehouseFeign.delete(mfCusWarehouse);
		return getListPage(model, cusNo);
	}

	// 列表展示详情，单字段编辑
	@RequestMapping(value = "/listShowDetailAjax")
	@ResponseBody
	public Map<String, Object> listShowDetailAjax(String id, String cusNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
				"MfCusWarehouseAction");
		String formId = "";
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getShowModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusWarehouseAction表单信息没有查询到");
			dataMap.put("msg", "获取详情失败");
			dataMap.put("flag", "error");
		} else {
			Map<String, Object> formData = new HashMap<String, Object>();
			request.setAttribute("ifBizManger", "3");
			JsonFormUtil jsonFormUtil = new JsonFormUtil();
			FormData formcuswarehouse0003 = formService.getFormData(formId);
			MfCusWarehouse mfCusWarehouse = new MfCusWarehouse();
			mfCusWarehouse.setId(id);
			mfCusWarehouse = mfCusWarehouseFeign.getById(mfCusWarehouse);
			getObjValue(formcuswarehouse0003, mfCusWarehouse, formData);
			String htmlStrCorp = jsonFormUtil.getJsonStr(formcuswarehouse0003, "propertySeeTag", "");
			if (mfCusWarehouse != null) {
				dataMap.put("formHtml", htmlStrCorp);
				dataMap.put("flag", "success");
			} else {
				dataMap.put("msg", "获取详情失败");
				dataMap.put("flag", "error");
			}
			dataMap.put("formData", mfCusWarehouse);
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
		MfCusWarehouse mfCusWarehouse = new MfCusWarehouse();
		Map map = getMapByJson(ajaxData);
		formId = (String) map.get("formId");
		FormData formcuswarehouse0003 = formService.getFormData(formId);
		getFormValue(formcuswarehouse0003, getMapByJson(ajaxData));
		MfCusWarehouse mfCusWarehouseNew = new MfCusWarehouse();
		setObjValue(formcuswarehouse0003, mfCusWarehouseNew);
		mfCusWarehouse.setId(mfCusWarehouseNew.getId());
		mfCusWarehouse = mfCusWarehouseFeign.getById(mfCusWarehouse);
		if (mfCusWarehouse != null) {
			try {
				mfCusWarehouse = (MfCusWarehouse) EntityUtil.reflectionSetVal(mfCusWarehouse, mfCusWarehouseNew,
						getMapByJson(ajaxData));
				mfCusWarehouseFeign.update(mfCusWarehouse);
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
		FormData formcuswarehouse0002 = formService.getFormData("cuswarehouse0002");
		getFormValue(formcuswarehouse0002);
		boolean validateFlag = this.validateFormData(formcuswarehouse0002);
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
		FormData formcuswarehouse0002 = formService.getFormData("cuswarehouse0002");
		getFormValue(formcuswarehouse0002);
		boolean validateFlag = this.validateFormData(formcuswarehouse0002);
	}

}

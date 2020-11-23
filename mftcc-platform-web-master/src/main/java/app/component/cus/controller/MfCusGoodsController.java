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
import app.component.cus.entity.MfCusGoods;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.component.cus.feign.MfCusGoodsFeign;
import app.component.cus.feign.MfCusTableFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;

/**
 * Title: MfCusGoodsAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu Mar 09 14:17:42 CST 2017
 **/
@Controller
@RequestMapping("/mfCusGoods")
public class MfCusGoodsController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfCusGoodsBo
	@Autowired
	private MfCusGoodsFeign mfCusGoodsFeign;
	@Autowired
	private MfCusTableFeign mfCusTableFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	@Autowired
	private MfCusFormConfigFeign mfCusFormConfigFeign;
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
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("query", "");
		return "/component/cus/MfCusGoods_List";
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
		MfCusGoods mfCusGoods = new MfCusGoods();
		try {
			mfCusGoods.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCusGoods.setCriteriaList(mfCusGoods, ajaxData);// 我的筛选
			// this.getRoleConditions(mfCusGoods,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfCusGoods", mfCusGoods));
			// 自定义查询Bo方法
			ipage = mfCusGoodsFeign.findByPage(ipage);
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
			FormData formcusgoods0001 = formService.getFormData(formId);
			getFormValue(formcusgoods0001, getMapByJson(ajaxData));
			if (this.validateFormData(formcusgoods0001)) {
				MfCusGoods mfCusGoods = new MfCusGoods();
				setObjValue(formcusgoods0001, mfCusGoods);

				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(mfCusGoods.getCusNo());
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				String cusName = mfCusCustomer.getCusName();
				mfCusGoods.setCusName(cusName);
				mfCusGoodsFeign.insert(mfCusGoods);

				String infIntegrity = mfCusCustomerFeign.updateInfIntegrity(mfCusGoods.getCusNo(),
						mfCusGoods.getRelNo());// 更新客户信息完整度
				JsonTableUtil jtu = new JsonTableUtil();
				String cusNo = mfCusGoods.getCusNo();
				String relNo = mfCusGoods.getRelNo();
				mfCusGoods = new MfCusGoods();
				mfCusGoods.setCusNo(cusNo);
				mfCusGoods.setRelNo(relNo);
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("mfCusGoods", mfCusGoods));
				String tableHtml = jtu.getJsonStr("tablecusgoods0001", "tableTag",
						(List<MfCusGoods>) mfCusGoodsFeign.findByPage(ipage).getResult(), null, true);
				dataMap.put("htmlStr", tableHtml);
				dataMap.put("htmlStrFlag", "1");
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
		FormData formcusgoods0002 = formService.getFormData("cusgoods0002");
		getFormValue(formcusgoods0002, getMapByJson(ajaxData));
		MfCusGoods mfCusGoodsJsp = new MfCusGoods();
		setObjValue(formcusgoods0002, mfCusGoodsJsp);
		MfCusGoods mfCusGoods = mfCusGoodsFeign.getById(mfCusGoodsJsp);
		if (mfCusGoods != null) {
			try {
				mfCusGoods = (MfCusGoods) EntityUtil.reflectionSetVal(mfCusGoods, mfCusGoodsJsp,
						getMapByJson(ajaxData));
				mfCusGoodsFeign.update(mfCusGoods);
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
		MfCusGoods mfCusGoods = new MfCusGoods();
		try {
			Map map = getMapByJson(ajaxData);
			String formId = (String) map.get("formId");
			if (StringUtil.isEmpty(formId)) {
				formId = mfCusFormConfigFeign.getByCusType("base", "MfCusGoodsAction").getAddModel();
			}
			FormData formcusgoods0001 = formService.getFormData(formId);
			getFormValue(formcusgoods0001, map);
			if (this.validateFormData(formcusgoods0001)) {
				mfCusGoods = new MfCusGoods();
				setObjValue(formcusgoods0001, mfCusGoods);
				mfCusGoodsFeign.update(mfCusGoods);

				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(mfCusGoods.getCusNo());
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);

				String tableId = "tablecusgoods0001";
				MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
						"MfCusGoodsAction");
				if (mfCusFormConfig != null && StringUtil.isNotEmpty(mfCusFormConfig.getListModelDef())) {
					tableId = "table" + mfCusFormConfig.getListModelDef();
				}

				String cusNo = mfCusGoods.getCusNo();
				mfCusGoods = new MfCusGoods();
				mfCusGoods.setCusNo(cusNo);
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("mfCusGoods", mfCusGoods));
				JsonTableUtil jtu = new JsonTableUtil();
				String tableHtml = jtu.getJsonStr(tableId, "tableTag",
						(List<MfCusGoods>) mfCusGoodsFeign.findByPage(ipage).getResult(), null, true);
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
	public Map<String, Object> getByIdAjax(String goodsId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcusgoods0002 = formService.getFormData("cusgoods0002");
		MfCusGoods mfCusGoods = new MfCusGoods();
		mfCusGoods.setGoodsId(goodsId);
		mfCusGoods = mfCusGoodsFeign.getById(mfCusGoods);
		getObjValue(formcusgoods0002, mfCusGoods, formData);
		if (mfCusGoods != null) {
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
	public Map<String, Object> getByIdForOneAjax(String goodsId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcusgoods0002 = formService.getFormData("cusgoods0002");
		MfCusGoods mfCusGoods = new MfCusGoods();
		mfCusGoods.setGoodsId(goodsId);
		mfCusGoods = mfCusGoodsFeign.getById(mfCusGoods);
		getObjValue(formcusgoods0002, mfCusGoods, formData);
		if (mfCusGoods != null) {
			dataMap.put("flag", "success");
			String saleArea = new CodeUtils().getMapByKeyName("").get(mfCusGoods.getSaleArea());
			dataMap.put("saleArea", saleArea);
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
	public Map<String, Object> deleteAjax(String goodsId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusGoods mfCusGoods = new MfCusGoods();
		mfCusGoods.setGoodsId(goodsId);
		try {
			mfCusGoodsFeign.delete(mfCusGoods);
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
	public String input(Model model,String cusNo,String relNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfCusGoods mfCusGoods = new MfCusGoods();
		mfCusGoods.setCusNo(cusNo);
		mfCusGoods.setRelNo(relNo);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
				"MfCusGoodsAction");
		String formId="";
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			//logger.error("客户类型为" + mfCusCustomer.getCusType() + "的MfCusGoodsAction表单信息没有查询到");
		} else {
			FormData formcusgoods0001 = formService.getFormData(formId);
			if (formcusgoods0001.getFormId() == null) {
				//logger.error("客户类型为" + mfCusCustomer.getCusType() + "的MfCusGoodsAction表单form" + formId + ".xml文件不存在");
			} else {
				getFormValue(formcusgoods0001);
				mfCusGoods.setCusName(mfCusCustomer.getCusName());
				getObjValue(formcusgoods0001, mfCusGoods);
			}
			model.addAttribute("formcusgoods0001", formcusgoods0001);
		}
		
		model.addAttribute("query", "");
		return "/component/cus/MfCusGoods_Insert";
	}

	/**
	 * 
	 * 方法描述： 业务新增客户信息表单页面
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author GuoJian
	 * @date 2017-9-26 11:03:15
	 */
	@RequestMapping(value = "/inputBiz")
	public String inputBiz(Model model,String cusNo,String relNo, String kindNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfCusGoods mfCusGoods = new MfCusGoods();
		mfCusGoods.setCusNo(cusNo);
		mfCusGoods.setRelNo(relNo);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(kindNo, "MfCusGoodsAction");
		String formId="";
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			//logger.error("产品类型为" + kindNo + "的MfCusGoodsAction表单信息没有查询到");
		} else {
			FormData formcusgoods0001 = formService.getFormData(formId);
			if (formcusgoods0001.getFormId() == null) {
				//logger.error("产品类型为" + kindNo + "的MfCusGoodsAction表单form" + formId + ".xml文件不存在");
			} else {
				getFormValue(formcusgoods0001);
				mfCusGoods.setCusName(mfCusCustomer.getCusName());
				getObjValue(formcusgoods0001, mfCusGoods);
			}
			model.addAttribute("formcusgoods0001", formcusgoods0001);
		}
		
		model.addAttribute("query", "");
		return "/component/cus/MfCusGoods_Insert";
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
		FormData formcusgoods0002 = formService.getFormData("cusgoods0002");
		getFormValue(formcusgoods0002);
		MfCusGoods mfCusGoods = new MfCusGoods();
		setObjValue(formcusgoods0002, mfCusGoods);
		mfCusGoodsFeign.insert(mfCusGoods);
		getObjValue(formcusgoods0002, mfCusGoods);
		this.addActionMessage(model, "保存成功");
		List<MfCusGoods> mfCusGoodsList = (List<MfCusGoods>) mfCusGoodsFeign.findByPage(this.getIpage()).getResult();
		model.addAttribute("formcusgoods0002", formcusgoods0002);
		model.addAttribute("mfCusGoodsList", mfCusGoodsList);
		model.addAttribute("query", "");
		return "/component/cus/MfCusGoods_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String goodsId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formcusgoods0001 = formService.getFormData("cusgoods0001");
		MfCusGoods mfCusGoods = new MfCusGoods();
		mfCusGoods.setGoodsId(goodsId);
		mfCusGoods = mfCusGoodsFeign.getById(mfCusGoods);
		String cusType = "";
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(mfCusGoods.getCusNo());
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		cusType = mfCusCustomer.getCusType();

		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(cusType, "MfCusGoodsAction");
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
		} else {
			formcusgoods0001 = formService.getFormData(formId);
			if (formcusgoods0001.getFormId() == null) {
			} else {
				getFormValue(formcusgoods0001);
				getObjValue(formcusgoods0001, mfCusGoods);
			}
		}
		model.addAttribute("formcusgoods0001", formcusgoods0001);
		model.addAttribute("query", "");
		return "/component/cus/MfCusGoods_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String goodsId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfCusGoods mfCusGoods = new MfCusGoods();
		mfCusGoods.setGoodsId(goodsId);
		mfCusGoodsFeign.delete(mfCusGoods);
		return getListPage(model);
	}

	// 列表展示详情，单字段编辑
	@RequestMapping(value = "/listShowDetailAjax")
	@ResponseBody
	public Map<String, Object> listShowDetailAjax(String cusNo,String goodsId) throws Exception {
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ActionContext.initialize(request, response);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
				"MfCusGoodsAction");
		String formId="";
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getShowModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			//logger.error("客户类型为" + mfCusCustomer.getCusType() + "的MfCusGoodsAction表单信息没有查询到");
			dataMap.put("msg", "获取详情失败");
			dataMap.put("flag", "error");
		} else {
			Map<String, Object> formData = new HashMap<String, Object>();
			request.setAttribute("ifBizManger","2");
			dataMap = new HashMap<String, Object>();
			JsonFormUtil jsonFormUtil = new JsonFormUtil();
			FormData formcusgoods0003 = formService.getFormData(formId);
			MfCusGoods mfCusGoods = new MfCusGoods();
			mfCusGoods.setGoodsId(goodsId);
			mfCusGoods = mfCusGoodsFeign.getById(mfCusGoods);
			getObjValue(formcusgoods0003, mfCusGoods, formData);
			String htmlStrCorp = jsonFormUtil.getJsonStr(formcusgoods0003, "propertySeeTag", "");
			if (mfCusGoods != null) {
				dataMap.put("formHtml", htmlStrCorp);
				dataMap.put("flag", "success");
			} else {
				dataMap.put("msg", "获取详情失败");
				dataMap.put("flag", "error");
			}
			dataMap.put("formData", mfCusGoods);
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
		MfCusGoods mfCusGoods = new MfCusGoods();
		Map map = getMapByJson(ajaxData);
		String formId = (String) map.get("formId");
		FormData formcusgoods0003 = formService.getFormData(formId);
		getFormValue(formcusgoods0003, getMapByJson(ajaxData));
		MfCusGoods mfCusGoodsNew = new MfCusGoods();
		setObjValue(formcusgoods0003, mfCusGoodsNew);
		mfCusGoods.setGoodsId(mfCusGoodsNew.getGoodsId());
		mfCusGoods = mfCusGoodsFeign.getById(mfCusGoods);
		if (mfCusGoods != null) {
			try {
				mfCusGoods = (MfCusGoods) EntityUtil.reflectionSetVal(mfCusGoods, mfCusGoodsNew,
						getMapByJson(ajaxData));
				mfCusGoodsFeign.update(mfCusGoods);
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
		FormData formcusgoods0002 = formService.getFormData("cusgoods0002");
		getFormValue(formcusgoods0002);
		boolean validateFlag = this.validateFormData(formcusgoods0002);
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
		FormData formcusgoods0002 = formService.getFormData("cusgoods0002");
		getFormValue(formcusgoods0002);
		boolean validateFlag = this.validateFormData(formcusgoods0002);
	}

}

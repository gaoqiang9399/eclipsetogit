package app.component.cus.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.component.auth.entity.MfCusCreditContractHis;
import app.component.cus.entity.MfCusBorrowerInfo;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusPersBaseInfo;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.entity.MfCusGoods;
import app.component.cus.feign.MfCusPersBaseInfoFeign;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import app.base.User;
import app.component.app.entity.MfBusApply;
import app.component.appinterface.AppInterfaceFeign;
import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.cus.feign.MfCusBorrowerInfoFeign;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.WaterIdUtil;

/**
 * Title: MfCusBorrowerInfoAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Mon Feb 05 16:03:52 CST 2018
 **/
@Controller
@RequestMapping("/mfCusBorrowerInfo")
public class MfCusBorrowerInfoController extends BaseFormBean {

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// fegin
	@Autowired
	private MfCusBorrowerInfoFeign mfCusBorrowerInfoFeign;
	@Autowired
	private MfCusFormConfigFeign mfCusFormConfigFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	@Autowired
	private AppInterfaceFeign appInterfaceFeign;
	@Autowired
	private MfCusPersBaseInfoFeign mfCusPersBaseInfoFeign;

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/cus/MfCusBorrowerInfo_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String ajaxData, String tableId,
			String tableType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusBorrowerInfo mfCusBorrowerInfo = new MfCusBorrowerInfo();
		try {
			mfCusBorrowerInfo.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCusBorrowerInfo.setCriteriaList(mfCusBorrowerInfo, ajaxData);// 我的筛选
			// mfCusBorrowerInfo.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfCusBorrowerInfo,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfCusBankAccManage", mfCusBorrowerInfo));
			ipage = mfCusBorrowerInfoFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
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
	@SuppressWarnings("unchecked")
	@RequestMapping("/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, Object> map = getMapByJson(ajaxData);
			String formId = map.get("formId").toString();
			FormData formcusBorrowerBase = formService.getFormData(formId);
			getFormValue(formcusBorrowerBase, getMapByJson(ajaxData));
			if (this.validateFormData(formcusBorrowerBase)) {
				MfCusBorrowerInfo mfCusBorrowerInfo = new MfCusBorrowerInfo();
				setObjValue(formcusBorrowerBase, mfCusBorrowerInfo);
				//通过证件号对共借人进行重复性校验
				List<MfCusBorrowerInfo>mfCusBorrowerInfoList = mfCusBorrowerInfoFeign.getAllBorrowerByCusNo(mfCusBorrowerInfo);
				if(null != mfCusBorrowerInfoList && mfCusBorrowerInfoList.size() >0)
				{
					for(MfCusBorrowerInfo mfCusBorrowerInfo1 : mfCusBorrowerInfoList)
					{
						if(mfCusBorrowerInfo1.getBorrowIdNum().equals(mfCusBorrowerInfo.getBorrowIdNum()))
						{
							dataMap.put("flag", "error");
							dataMap.put("msg", "共借人信息已存在！");
							return dataMap;
						}
					}
				}
				mfCusBorrowerInfoFeign.insert(mfCusBorrowerInfo);
				String infIntegrity = mfCusCustomerFeign.updateInfIntegrity(mfCusBorrowerInfo.getCusNo(),
						mfCusBorrowerInfo.getRelNo());// 更新客户信息完整度

				//将共同借款人添加到客户中
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				//根据共同借款人关联的客户号获得客户信息
				MfCusCustomer cusCustomer = new MfCusCustomer();
				cusCustomer.setCusNo(mfCusBorrowerInfo.getCusNo());
				cusCustomer = mfCusCustomerFeign.getById(cusCustomer);
				if (BizPubParm.CUS_TYPE_PERS.equals(cusCustomer.getCusBaseType())){
                    MfCusCustomer cusCustomer1 = new MfCusCustomer();
                    cusCustomer1.setIdNum(mfCusBorrowerInfo.getBorrowIdNum());
                    List<MfCusCustomer> mfCusCustomers = mfCusCustomerFeign.getByIdNum(cusCustomer1);
                    String cusNo = "";
                    MfCusBorrowerInfo cusBorrowerInfo = new MfCusBorrowerInfo();
                    cusBorrowerInfo.setId(mfCusBorrowerInfo.getId());
                    if(mfCusCustomers != null && mfCusCustomers.size() > 0){
                        cusCustomer1 = mfCusCustomers.get(0);
                        cusBorrowerInfo.setBorrowNo(cusCustomer1.getCusNo());
                    }else{
                        mfCusCustomer.setCusName(mfCusBorrowerInfo.getBorrowName());
                        mfCusCustomer.setIdType(mfCusBorrowerInfo.getBorrowIdType());
                        mfCusCustomer.setIdNum(mfCusBorrowerInfo.getBorrowIdNum());
                        mfCusCustomer.setCusTel(mfCusBorrowerInfo.getBorrowTel());
                        mfCusCustomer.setCommAddress(mfCusBorrowerInfo.getBorrowCommAddress());
                        mfCusCustomer.setCusType(cusCustomer.getCusType());
                        mfCusCustomer.setCusBaseType(cusCustomer.getCusBaseType());
                        mfCusCustomer.setCusMngNo(mfCusBorrowerInfo.getCurrentSessionRegNo());
                        mfCusCustomer.setCusMngName(mfCusBorrowerInfo.getCurrentSessionRegName());
                        mfCusCustomer.setCusBrNo(mfCusBorrowerInfo.getCurrentSessionOrgNo());
                        mfCusCustomer.setCusBrName(mfCusBorrowerInfo.getCurrentSessionOrgName());
                        mfCusCustomer.setCurrentSessionRegNo(mfCusBorrowerInfo.getCurrentSessionRegNo());
                        mfCusCustomer.setCurrentSessionRegName(mfCusBorrowerInfo.getCurrentSessionRegName());
                        mfCusCustomer.setCurrentSessionOrgNo(mfCusBorrowerInfo.getCurrentSessionOrgNo());
                        mfCusCustomer.setCurrentSessionOrgName(mfCusBorrowerInfo.getCurrentSessionOrgName());
                        mfCusCustomer = mfCusCustomerFeign.insert1(mfCusCustomer);

                        MfCusPersBaseInfo mfCusPersBaseInfo = new MfCusPersBaseInfo();
                        PropertyUtils.copyProperties(mfCusPersBaseInfo, mfCusCustomer);
                        mfCusPersBaseInfo.setBrithday(mfCusBorrowerInfo.getBorrowBirth());
                        mfCusPersBaseInfo.setAge(Integer.valueOf(mfCusBorrowerInfo.getBorrowAge()));
                        mfCusPersBaseInfo.setSex(mfCusBorrowerInfo.getBorrowSex());
                        mfCusPersBaseInfo.setCommAddress(mfCusBorrowerInfo.getBorrowCommAddress());
                        mfCusPersBaseInfo.setCusNo(mfCusCustomer.getCusNo());
                        mfCusPersBaseInfoFeign.insert(mfCusPersBaseInfo);
                        cusBorrowerInfo.setBorrowNo(mfCusCustomer.getCusNo());
                    }
					mfCusBorrowerInfoFeign.update(cusBorrowerInfo);
				}
				JsonTableUtil jtu = new JsonTableUtil();
				//  1212
				String cusNo = mfCusBorrowerInfo.getCusNo();
				String relNo = mfCusBorrowerInfo.getRelNo();
				Ipage ipage = this.getIpage();
				mfCusBorrowerInfo = new MfCusBorrowerInfo();
				mfCusBorrowerInfo.setCusNo(cusNo);
				mfCusBorrowerInfo.setRelNo(relNo);
				ipage.setParams(this.setIpageParams("mfCusBorrowerInfo", mfCusBorrowerInfo));
				
				// 动态表单Id
				String tableId = "tablecusBorrowerList";
				mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(mfCusBorrowerInfo.getCusNo());
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				MfCusFormConfig  mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusBorrowerInfoAction");				
				if(mfCusFormConfig != null){
					if(StringUtil.isNotEmpty(mfCusFormConfig.getListModelDef())){
						tableId = "table" + mfCusFormConfig.getListModelDef();
					}
				}
				
				String tableHtml = jtu.getJsonStr(tableId, "tableTag",
						(List<MfCusGoods>) mfCusBorrowerInfoFeign.findByPage(ipage).getResult(), null, true);
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
			dataMap.put("msg", "新增失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

	// 列表展示详情，单字段编辑
	@RequestMapping("/listShowDetailAjax")
	@ResponseBody
	public Map<String, Object> listShowDetailAjax(String cusNo, String relNo, String pageView, String id)
			throws Exception {
		Logger logger = LoggerFactory.getLogger(MfCusBorrowerInfoController.class);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> formData = new HashMap<String, Object>();
		FormService formService = new FormService();
		String formId = null;
		ActionContext.initialize(request, response);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = null;
		if ("busView".equals(pageView)) {
			MfBusApply mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(relNo);
			String kindNo = mfBusApply.getKindNo();
			mfCusFormConfig = mfCusFormConfigFeign.getByCusType(kindNo, "MfCusBorrowerInfoAction");
		} else {
			mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusBorrowerInfoAction");
		}
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getShowModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			 logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusBorrowerInfoAction表单信息没有查询到");
		} else {
			request.setAttribute("ifBizManger", "3");
			JsonFormUtil jsonFormUtil = new JsonFormUtil();
			FormData formcusBorrowerBase = formService.getFormData(formId);
			MfCusBorrowerInfo mfCusBorrowerInfo = new MfCusBorrowerInfo();
			mfCusBorrowerInfo.setId(id);
			mfCusBorrowerInfo = mfCusBorrowerInfoFeign.getById(mfCusBorrowerInfo);
			getObjValue(formcusBorrowerBase, mfCusBorrowerInfo, formData);
			String htmlStr = jsonFormUtil.getJsonStr(formcusBorrowerBase, "propertySeeTag", "");
			if (mfCusBorrowerInfo != null) {
				dataMap.put("formHtml", htmlStr);
				dataMap.put("flag", "success");
			} else {
				dataMap.put("msg", "获取详情失败");
				dataMap.put("flag", "error");
			}
			dataMap.put("formData", mfCusBorrowerInfo);
		}
		return dataMap;
	}

	/**
	 * ajax 异步 单个字段或多个字段更新
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData, String formId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map map = getMapByJson(ajaxData);
		formId = (String) map.get("formId");
		FormService formService = new FormService();
		FormData formcusBorrowerBase = formService.getFormData(formId);
		getFormValue(formcusBorrowerBase, getMapByJson(ajaxData));
		MfCusBorrowerInfo mfCusBorrowerInfoJsp = new MfCusBorrowerInfo();
		setObjValue(formcusBorrowerBase, mfCusBorrowerInfoJsp);
		MfCusBorrowerInfo mfCusBorrowerInfo = mfCusBorrowerInfoFeign.getById(mfCusBorrowerInfoJsp);
		if (mfCusBorrowerInfo != null) {
			try {
				mfCusBorrowerInfo = (MfCusBorrowerInfo) EntityUtil.reflectionSetVal(mfCusBorrowerInfo,
						mfCusBorrowerInfoJsp, getMapByJson(ajaxData));
				mfCusBorrowerInfoFeign.update(mfCusBorrowerInfo);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			} catch (Exception e) {
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", "新增失败");
				throw new Exception(e.getMessage());
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
	@RequestMapping("/updateAjax")
	@ResponseBody
	public Map<String,Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>();
		try{
			Map map = getMapByJson(ajaxData);
			String formId = (String)map.get("formId");
			if(StringUtil.isEmpty(formId)){
				formId = mfCusFormConfigFeign.getByCusType("base", "MfCusShedAction").getAddModel();
			}
			FormData formcusBorrowerBase = formService.getFormData(formId);

			getFormValue(formcusBorrowerBase,map);
			if(this.validateFormData(formcusBorrowerBase)){
				MfCusBorrowerInfo mfCusBorrowerInfo= new MfCusBorrowerInfo();
				setObjValue(formcusBorrowerBase, mfCusBorrowerInfo);
				mfCusBorrowerInfoFeign.update(mfCusBorrowerInfo);
				String  cusNo = mfCusBorrowerInfo.getCusNo();
				mfCusBorrowerInfo= new MfCusBorrowerInfo();
				mfCusBorrowerInfo.setCusNo(cusNo);
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("mfCusBorrowerInfo", mfCusBorrowerInfo));
				JsonTableUtil jtu = new JsonTableUtil();
				String  tableHtml = jtu.getJsonStr("tablecusBorrowerList","tableTag", (List<MfCusBorrowerInfo>)mfCusBorrowerInfoFeign.findByPage(ipage).getResult(), null,true);
				dataMap.put("htmlStr", tableHtml);
				dataMap.put("htmlStrFlag","1");

				dataMap.put("flag", "success");
				dataMap.put("msg",MessageEnum.SUCCEED_UPDATE.getMessage());
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}
	/*public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formcusBorrowerDetail = formService.getFormData("cusBorrowerDetail");
			getFormValue(formcusBorrowerDetail, getMapByJson(ajaxData));
			if (this.validateFormData(formcusBorrowerDetail)) {
				MfCusBorrowerInfo mfCusBorrowerInfo = new MfCusBorrowerInfo();
				setObjValue(formcusBorrowerDetail, mfCusBorrowerInfo);
				mfCusBorrowerInfoFeign.update(mfCusBorrowerInfo);
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw e;
		}
		return dataMap;
	}
*/
	/**
	 * AJAX获取查看
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String id) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcusBorrowerDetail = formService.getFormData("cusBorrowerDetail");
		MfCusBorrowerInfo mfCusBorrowerInfo = new MfCusBorrowerInfo();
		mfCusBorrowerInfo.setId(id);
		mfCusBorrowerInfo = mfCusBorrowerInfoFeign.getById(mfCusBorrowerInfo);
		getObjValue(formcusBorrowerDetail, mfCusBorrowerInfo, formData);
		if (mfCusBorrowerInfo != null) {
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
	@RequestMapping("/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String id, String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusBorrowerInfo mfCusBorrowerInfo = new MfCusBorrowerInfo();
		mfCusBorrowerInfo.setId(id);
		try {
			mfCusBorrowerInfoFeign.delete(mfCusBorrowerInfo);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
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
	@RequestMapping("/input")
	public String input(Model model, String cusNo, String relNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("scNo", BizPubParm.SCENCE_TYPE_DOC_BORROWER);
		MfCusBorrowerInfo mfCusBorrowerInfo = new MfCusBorrowerInfo();
		mfCusBorrowerInfo.setCusNo(cusNo);
		mfCusBorrowerInfo.setRelNo(relNo);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
				"MfCusBorrowerInfoAction");
		String formId = null;
		if (mfCusFormConfig != null) {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusBorrowerInfoAction表单信息没有查询到");
		} else {
			FormData formcusBorrowerBase = formService.getFormData(formId);
			if (formcusBorrowerBase.getFormId() == null) {
				// logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusBorrowerInfoAction表单form"+formId+".xml文件不存在");
			} else {
				getFormValue(formcusBorrowerBase);
				mfCusBorrowerInfo.setId(WaterIdUtil.getWaterId());
				mfCusBorrowerInfo.setCusName(mfCusCustomer.getCusName());
				mfCusBorrowerInfo.setCusNo(cusNo);
				mfCusBorrowerInfo.setOpName(User.getRegName(request));
				mfCusBorrowerInfo.setOpNo(User.getRegNo(request));
				getObjValue(formcusBorrowerBase, mfCusBorrowerInfo);
				dataMap.put("id", mfCusBorrowerInfo.getId());
				model.addAttribute("formcusBorrowerBase", formcusBorrowerBase);
			}
		}
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("query", "");
		model.addAttribute("cusNo", cusNo);
		model.addAttribute("cusBaseType", mfCusCustomer.getCusBaseType());
		
		return "/component/cus/MfCusBorrowerInfo_Insert";
	}

	/**
	 * 新增页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/inputBiz")
	public String inputBiz(Model model, String cusNo, String relNo, String kindNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		MfCusBorrowerInfo mfCusBorrowerInfo = new MfCusBorrowerInfo();
		mfCusBorrowerInfo.setCusNo(cusNo);
		mfCusBorrowerInfo.setRelNo(relNo);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(kindNo, "MfCusBorrowerInfoAction");
		String formId = null;
		if (mfCusFormConfig != null) {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// logger.error("产品类型为"+ kindNo
			// +"的MfCusBorrowerInfoAction表单信息没有查询到");
		} else {
			FormData formcusBorrowerBase = formService.getFormData(formId);
			if (formcusBorrowerBase.getFormId() == null) {
				// logger.error("产品类型为"+ kindNo
				// +"的MfCusBorrowerInfoAction表单form"+formId+".xml文件不存在");
			} else {
				getFormValue(formcusBorrowerBase);
				mfCusBorrowerInfo.setCusName(mfCusCustomer.getCusName());
				mfCusBorrowerInfo.setCusNo(cusNo);
				mfCusBorrowerInfo.setOpName(User.getRegName(request));
				mfCusBorrowerInfo.setOpNo(User.getRegNo(request));
				getObjValue(formcusBorrowerBase, mfCusBorrowerInfo);
				model.addAttribute("formcusBorrowerBase", formcusBorrowerBase);
			}
		}
		model.addAttribute("query", "");
		return "/component/cus/MfCusAssureOutside_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model, String id,String cusNo) throws Exception  {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formcusBorrowerBase = formService.getFormData("cusBorrowerBase");
		getFormValue(formcusBorrowerBase);
		MfCusBorrowerInfo mfCusBorrowerInfo = new MfCusBorrowerInfo();
		mfCusBorrowerInfo.setId(id);
		mfCusBorrowerInfo.setCusNo(cusNo);
		mfCusBorrowerInfo = mfCusBorrowerInfoFeign.getById(mfCusBorrowerInfo);
		getObjValue(formcusBorrowerBase, mfCusBorrowerInfo);
		model.addAttribute("formcusBorrowerBase", formcusBorrowerBase);
		model.addAttribute("query", "");
		return "/component/cus/MfCusBorrowerInfo_Detail";
	}

	/**
	 * 
	 * 方法描述： 共同借款人要件查看
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author zhs
	 * @date 2018-2-11 上午10:26:27
	 */
	@RequestMapping("/getDocById")
	public String getDocById() throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("scNo", BizPubParm.SCENCE_TYPE_DOC_BORROWER);
		return "/component/cus/MfCusBorrowerInfo_DocInfo";
	}

	/**
	 * 新增校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public void validateInsert() throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formcusBorrowerDetail = formService.getFormData("cusBorrowerDetail");
		getFormValue(formcusBorrowerDetail);
		boolean validateFlag = this.validateFormData(formcusBorrowerDetail);
	}

	/**
	 * 修改校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public void validateUpdate() throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formcusBorrowerDetail = formService.getFormData("cusBorrowerDetail");
		getFormValue(formcusBorrowerDetail);
		boolean validateFlag = this.validateFormData(formcusBorrowerDetail);
	}




	/***
	 * 列表数据查询
	 *
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/findApplyByCusNoAjax")
	@ResponseBody
	public Map<String, Object> findApplyByCusNoAjax(String cusNo) throws Exception {
		Map<String,Object>  dataMap  =  new HashMap<>();
		try{
			MfCusBorrowerInfo   mfCusBorrowerInfo  =  new MfCusBorrowerInfo();
			mfCusBorrowerInfo.setCusNo(cusNo);
			List<MfCusBorrowerInfo> mfCusBorrowerInfoList = mfCusBorrowerInfoFeign.getByCusNo(mfCusBorrowerInfo);
			JsonTableUtil jtu = new JsonTableUtil();
			String htmlStr = jtu.getJsonStr("tableapplyCusBorrowerList", "tableTag", mfCusBorrowerInfoList, null, true);
			if(mfCusBorrowerInfoList.size() > 0) {
				dataMap.put("tableHtml", htmlStr);
				dataMap.put("flag", "success");
			}else{
				dataMap.put("flag", "error");
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "查询失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}


	// 列表展示详情，单字段编辑
	@RequestMapping("/listShowDetailZmAjax")
	@ResponseBody
	public Map<String, Object> listShowDetailZmAjax(String cusNo, String relNo, String pageView, String id)
			throws Exception {
		Logger logger = LoggerFactory.getLogger(MfCusBorrowerInfoController.class);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> formData = new HashMap<String, Object>();
		FormService formService = new FormService();
		String formId = null;
		ActionContext.initialize(request, response);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = null;
		mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusBorrowerInfoAction");
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getShowModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusBorrowerInfoAction表单信息没有查询到");
		} else {
			request.setAttribute("ifBizManger", "3");
			JsonFormUtil jsonFormUtil = new JsonFormUtil();
			FormData formcusBorrowerBase = formService.getFormData(formId);
			MfCusBorrowerInfo mfCusBorrowerInfo = new MfCusBorrowerInfo();
			mfCusBorrowerInfo.setId(id);
			mfCusBorrowerInfo = mfCusBorrowerInfoFeign.getById(mfCusBorrowerInfo);
			getObjValue(formcusBorrowerBase, mfCusBorrowerInfo, formData);
			String htmlStr = jsonFormUtil.getJsonStr(formcusBorrowerBase, "propertySeeTag", "");
			if (mfCusBorrowerInfo != null) {
				dataMap.put("formHtml", htmlStr);
				dataMap.put("flag", "success");
			} else {
				dataMap.put("msg", "获取详情失败");
				dataMap.put("flag", "error");
			}
			dataMap.put("formData", mfCusBorrowerInfo);
		}
		return dataMap;
	}

}

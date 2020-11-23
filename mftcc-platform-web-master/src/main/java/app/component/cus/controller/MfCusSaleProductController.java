package app.component.cus.controller;

import app.base.User;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.entity.MfCusSaleProduct;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.component.cus.feign.MfCusSaleProductFeign;
import app.component.sysInterface.SysInterfaceFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.EntityUtil;
import cn.mftcc.util.MathExtend;
import cn.mftcc.util.StringUtil;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;
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

/**
 * Title: MfCusSaleProductController.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Fri Sep 28 10:44:29 CST 2018
 **/
@Controller
@RequestMapping(value = "/mfCusSaleProduct")
public class MfCusSaleProductController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfCusSaleProductFeign mfCusSaleProductFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	@Autowired
	private MfCusFormConfigFeign mfCusFormConfigFeign;
	@Autowired
	private SysInterfaceFeign sysInterfaceFeign;

	
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/cus/MfCusSaleProduct_List";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "/findByPageAjax")
	public Map<String, Object> findByPageAjax(String ajaxData, String tableId, String tableType, Integer pageNo,Integer pageSize) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusSaleProduct mfCusSaleProduct = new MfCusSaleProduct();
		try {
			mfCusSaleProduct.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfCusSaleProduct.setCriteriaList(mfCusSaleProduct, ajaxData);//我的筛选
			//mfCusSaleProduct.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfCusSaleProduct,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setPageSize(pageSize);
			ipage.setParams(this.setIpageParams("mfCusSaleProduct", mfCusSaleProduct));
			//自定义查询Feign方法
			ipage = mfCusSaleProductFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	
	/**
	 * AJAX新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, Object> map = getMapByJson(ajaxData);
			String formId = (String) map.get("formId");
			if (StringUtil.isEmpty(formId)) {
				formId = mfCusFormConfigFeign.getByCusType("base", "MfCusSaleProductAction").getAddModel();
			}
			FormData formcussaleproductDetail = formService.getFormData(formId);
			getFormValue(formcussaleproductDetail, map);
			if (this.validateFormData(formcussaleproductDetail)) {
				MfCusSaleProduct mfCusSaleProduct = new MfCusSaleProduct();
				setObjValue(formcussaleproductDetail, mfCusSaleProduct);
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(mfCusSaleProduct.getCusNo());
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				String cusName = mfCusCustomer.getCusName();
				mfCusSaleProductFeign.insert(mfCusSaleProduct);
				dataMap.put("mfCusSaleProduct", mfCusSaleProduct);
				String infIntegrity = mfCusCustomerFeign.updateInfIntegrity(mfCusSaleProduct.getCusNo(),
						mfCusSaleProduct.getCusNo());// 更新客户信息完整度

				String tableId = "tablecussaleproductBase";
				MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
						"MfCusSaleProductAction");
				if (mfCusFormConfig != null && StringUtil.isNotEmpty(mfCusFormConfig.getListModelDef())) {
					tableId = "table" + mfCusFormConfig.getListModelDef();
				}

				String cusNo = mfCusSaleProduct.getCusNo();
				mfCusSaleProduct = new MfCusSaleProduct();
				mfCusSaleProduct.setCusNo(cusNo);
				mfCusSaleProduct.setDelFlag("0");
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("mfCusSaleProduct", mfCusSaleProduct));
				JsonTableUtil jtu = new JsonTableUtil();
				@SuppressWarnings("unchecked")
				List<MfCusSaleProduct> list = (List<MfCusSaleProduct>) mfCusSaleProductFeign.findByPage(ipage)
						.getResult();
				String tableHtml = jtu.getJsonStr(tableId, "tableTag", list, null, true);
				dataMap.put("htmlStr", tableHtml);
				dataMap.put("htmlStrFlag", "1");
				dataMap.put("infIntegrity", infIntegrity);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
		}
		}catch(Exception e){
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw e;
		}
		return dataMap;
	}
	//列表展示详情，单字段编辑
	@RequestMapping(value = "/listShowDetailAjax")
	@ResponseBody
	public Map<String,Object> listShowDetailAjax(String cusNo,String saleId) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String,Object> dataMap=new HashMap<String,Object>();
		String cusType="";
		String formId="";
		String query="";
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		cusType=mfCusCustomer.getCusType();
		MfCusFormConfig  mfCusFormConfig = mfCusFormConfigFeign.getByCusType(cusType, "MfCusSaleProductAction");
		if(mfCusFormConfig == null){

		}else{
			formId = mfCusFormConfig.getShowModelDef();
		}
		if(StringUtil.isEmpty(formId)){
//			logger.error("客户类型为"+cusType+"的MfCusBankAccManageAction表单信息没有查询到");
			dataMap.put("msg", "获取详情失败");
			dataMap.put("flag", "error");
		}else{
			Map<String,Object> formData = new HashMap<String,Object>();
			request.setAttribute("ifBizManger", "3");
			JsonFormUtil jsonFormUtil = new JsonFormUtil();
			FormData formcussaleproductDetail = formService.getFormData(formId);
			MfCusSaleProduct mfCusSaleProduct = new MfCusSaleProduct();
			mfCusSaleProduct.setSaleId(saleId);
			mfCusSaleProduct = mfCusSaleProductFeign.getById(mfCusSaleProduct);
			getObjValue(formcussaleproductDetail, mfCusSaleProduct,formData);
			query = sysInterfaceFeign.getQueryResult(User.getRegNo(request));
			if(query == null){
				query = "";
			}
			String htmlStrCorp = jsonFormUtil.getJsonStr(formcussaleproductDetail,"propertySeeTag",query);
			if(mfCusSaleProduct!=null){
				dataMap.put("formHtml", htmlStrCorp);
				dataMap.put("flag", "success");
			}else{
				dataMap.put("msg", "获取详情失败");
				dataMap.put("flag", "error");
			}
			dataMap.put("formData", mfCusSaleProduct);
		}
		return dataMap;
	}
	/**
	 * ajax 异步 单个字段或多个字段更新
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcussaleproductDetail = formService.getFormData("cussaleproductDetail");
		getFormValue(formcussaleproductDetail, getMapByJson(ajaxData));
		MfCusSaleProduct mfCusSaleProductJsp = new MfCusSaleProduct();
		setObjValue(formcussaleproductDetail, mfCusSaleProductJsp);
		MfCusSaleProduct mfCusSaleProduct = mfCusSaleProductFeign.getById(mfCusSaleProductJsp);
		if(mfCusSaleProduct!=null){
			try{
				if(mfCusSaleProductJsp.getProductNum()!=null||mfCusSaleProductJsp.getProductPrice()!=null
						||mfCusSaleProductJsp.getCosRaw1()!=null||mfCusSaleProductJsp.getCosRaw2()!=null||mfCusSaleProductJsp.getCosRaw3()!=null||mfCusSaleProductJsp.getCosRaw4()!=null
						||mfCusSaleProductJsp.getCosLabour1()!=null||mfCusSaleProductJsp.getCosLabour2()!=null
						){
					mfCusSaleProduct = (MfCusSaleProduct)EntityUtil.reflectionSetVal(mfCusSaleProduct, mfCusSaleProductJsp, getMapByJson(ajaxData));
					mfCusSaleProduct = clacAmt(mfCusSaleProduct);
				}
				mfCusSaleProductFeign.update(mfCusSaleProduct);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			}catch(Exception e){
				dataMap.put("flag", "error");
				dataMap.put("msg", "新增失败");
				throw e;
			}
		}else{
			dataMap.put("flag", "error");
			dataMap.put("msg", "编号不存在,保存失败");
		}
		return dataMap;
	}

	public MfCusSaleProduct clacAmt(MfCusSaleProduct mfCusSaleProduct){
		 Integer productNum =mfCusSaleProduct.getProductNum() ;//销售数量
		 Double productPrice=mfCusSaleProduct.getProductPrice();//销售单价
		Double saleAmt =mfCusSaleProduct.getSaleAmt();//销售额
		Double profit = mfCusSaleProduct.getProfit();//销售毛利润
		if(productNum!=null&&productPrice!=null){
			saleAmt = productNum*productPrice;
			mfCusSaleProduct.setSaleAmt(saleAmt);
			profit = saleAmt;
		}

		Double cosRaw1 = mfCusSaleProduct.getCosRaw1();//金额预留字段
		Double cosRaw2  = mfCusSaleProduct.getCosRaw2();//
		Double cosRaw3  = mfCusSaleProduct.getCosRaw3();//
		Double cosRaw4  = mfCusSaleProduct.getCosRaw4();//
		Double cosLabour1 = mfCusSaleProduct.getCosLabour1();//;//金额预留字段
		Double cosLabour2 = mfCusSaleProduct.getCosLabour2();//
		if(cosRaw1!=null){
			profit = MathExtend.subtract(saleAmt,cosRaw1);
		}
		if(cosRaw2!=null){
			profit = MathExtend.subtract(profit,cosRaw2);
		}
		if(cosRaw3!=null){
			profit = MathExtend.subtract(profit,cosRaw3);
		}
		if(cosRaw4!=null){
			profit = MathExtend.subtract(profit,cosRaw4);
		}
		if(cosLabour1!=null){
			profit = MathExtend.subtract(profit,cosLabour1);
		}
		if(cosLabour2!=null){
			profit = MathExtend.subtract(profit,cosLabour2);
		}
		if(profit!=null&&saleAmt!=null){
			mfCusSaleProduct.setProfit(profit);
			Double profitRate = MathExtend.divide(profit,saleAmt,4)*100;
			mfCusSaleProduct.setProfitRate(profitRate);
		}
			return mfCusSaleProduct;
	}
	/**
	 * AJAX更新保存
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusSaleProduct mfCusSaleProduct = new MfCusSaleProduct();
		try{
			FormData formcussaleproductDetail = formService.getFormData("cussaleproductDetail");
			getFormValue(formcussaleproductDetail, getMapByJson(ajaxData));
			if(this.validateFormData(formcussaleproductDetail)){
				mfCusSaleProduct = new MfCusSaleProduct();
				setObjValue(formcussaleproductDetail, mfCusSaleProduct);
				mfCusSaleProductFeign.update(mfCusSaleProduct);
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw e;
		}
		return dataMap;
	}
	
	/**
	 * AJAX获取查看
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String saleId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formcussaleproductDetail = formService.getFormData("cussaleproductDetail");
		MfCusSaleProduct mfCusSaleProduct = new MfCusSaleProduct();
		mfCusSaleProduct.setSaleId(saleId);
		mfCusSaleProduct = mfCusSaleProductFeign.getById(mfCusSaleProduct);
		getObjValue(formcussaleproductDetail, mfCusSaleProduct,formData);
		if(mfCusSaleProduct!=null){
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}
	/**
	 * Ajax异步删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String saleId) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusSaleProduct mfCusSaleProduct = new MfCusSaleProduct();
		mfCusSaleProduct.setSaleId(saleId);
		try {
			mfCusSaleProductFeign.delete(mfCusSaleProduct);
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
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/input")
	public String input(Model model,String cusNo,String relNo) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formcussaleproductBase = null;
		MfCusSaleProduct mfCusSaleProduct = new MfCusSaleProduct();
		mfCusSaleProduct.setCusNo(cusNo);
		String cusType="";
		String formId="";
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		cusType=mfCusCustomer.getCusType();
		mfCusSaleProduct.setCusName(mfCusCustomer.getCusName());
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(cusType, "MfCusSaleProductAction");
		if(mfCusFormConfig == null){

		}else{
			formId = mfCusFormConfig.getAddModelDef();
		}
		if(StringUtil.isEmpty(formId)){
		}else{
			formcussaleproductBase = formService.getFormData(formId);
			if(formcussaleproductBase.getFormId() == null){
			}else{
				getFormValue(formcussaleproductBase);
				getObjValue(formcussaleproductBase, mfCusSaleProduct);
			}
		}
		Map<String,Object> dataMap = new HashMap<String,Object>();
		model.addAttribute("formcussaleproductBase", formcussaleproductBase);
		model.addAttribute("query", "");
		return "/component/cus/MfCusSaleProduct_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model,String saleId) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formcussaleproductBase = formService.getFormData("cussaleproductBase");
		getFormValue(formcussaleproductBase);
		MfCusSaleProduct mfCusSaleProduct = new MfCusSaleProduct();
		mfCusSaleProduct.setSaleId(saleId);
		mfCusSaleProduct = mfCusSaleProductFeign.getById(mfCusSaleProduct);
		getObjValue(formcussaleproductBase, mfCusSaleProduct);
		model.addAttribute("formcussaleproductBase", formcussaleproductBase);
		model.addAttribute("query", "");
		return "/component/cus/MfCusSaleProduct_Detail";
	}

	/**
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public void validateInsert() throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formcussaleproductDetail = formService.getFormData("cussaleproductDetail");
		getFormValue(formcussaleproductDetail);
		boolean validateFlag = this.validateFormData(formcussaleproductDetail);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public void validateUpdate() throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formcussaleproductDetail = formService.getFormData("cussaleproductDetail");
		getFormValue(formcussaleproductDetail);
		boolean validateFlag = this.validateFormData(formcussaleproductDetail);
	}
}

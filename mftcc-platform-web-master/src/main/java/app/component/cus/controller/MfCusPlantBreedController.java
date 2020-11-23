package app.component.cus.controller;
import app.base.User;
import app.component.common.EntityUtil;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.entity.MfCusPlantBreed;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.component.cus.feign.MfCusPlantBreedFeign;
import app.component.sysInterface.SysInterfaceFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
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
 * Title: MfCusPlantBreedController.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Fri Sep 28 10:50:05 CST 2018
 **/
@Controller
@RequestMapping(value = "/mfCusPlantBreed")
public class MfCusPlantBreedController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfCusPlantBreedFeign mfCusPlantBreedFeign;
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
		return "/component/cus/MfCusPlantBreed_List";
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
		MfCusPlantBreed mfCusPlantBreed = new MfCusPlantBreed();
		try {
			mfCusPlantBreed.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfCusPlantBreed.setCriteriaList(mfCusPlantBreed, ajaxData);//我的筛选
			//mfCusPlantBreed.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfCusPlantBreed,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setPageSize(pageSize);
			ipage.setParams(this.setIpageParams("mfCusPlantBreed", mfCusPlantBreed));
			//自定义查询Feign方法
			ipage = mfCusPlantBreedFeign.findByPage(ipage);
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
		try{
			Map<String, Object> map = getMapByJson(ajaxData);
			String formId = (String) map.get("formId");
			if (StringUtil.isEmpty(formId)) {
				formId = mfCusFormConfigFeign.getByCusType("base", "MfCusPlantBreedAction").getAddModel();
			}
			FormData formcusplantbreedDetail = formService.getFormData(formId);
			getFormValue(formcusplantbreedDetail, map);
			if (this.validateFormDataAnchor(formcusplantbreedDetail)) {
				MfCusPlantBreed mfCusPlantBreed = new MfCusPlantBreed();
				setObjValue(formcusplantbreedDetail, mfCusPlantBreed);
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(mfCusPlantBreed.getCusNo());
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				mfCusPlantBreedFeign.insert(mfCusPlantBreed);
				dataMap.put("mfCusPlantBreed", mfCusPlantBreed);
				String infIntegrity = mfCusCustomerFeign.updateInfIntegrity(mfCusPlantBreed.getCusNo(),
						mfCusPlantBreed.getCusNo());// 更新客户信息完整度

				String tableId = "tablecusplantbreedBase";
				MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
						"MfCusPlantBreedAction");
				if (mfCusFormConfig != null && StringUtil.isNotEmpty(mfCusFormConfig.getListModelDef())) {
					tableId = "table" + mfCusFormConfig.getListModelDef();
				}

				String cusNo = mfCusPlantBreed.getCusNo();
				mfCusPlantBreed = new MfCusPlantBreed();
				mfCusPlantBreed.setCusNo(cusNo);
				mfCusPlantBreed.setDelFlag("0");
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("mfCusPlantBreed", mfCusPlantBreed));
				JsonTableUtil jtu = new JsonTableUtil();
				@SuppressWarnings("unchecked")
				List<MfCusPlantBreed> list = (List<MfCusPlantBreed>) mfCusPlantBreedFeign.findByPage(ipage)
						.getResult();
				String tableHtml = jtu.getJsonStr(tableId, "tableTag", list, null, true);
				dataMap.put("htmlStr", tableHtml);
				dataMap.put("htmlStrFlag", "1");
				dataMap.put("infIntegrity", infIntegrity);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
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
	public Map<String,Object> listShowDetailAjax(String cusNo,String id) throws Exception {
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
		MfCusFormConfig  mfCusFormConfig = mfCusFormConfigFeign.getByCusType(cusType, "MfCusPlantBreedAction");
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
			MfCusPlantBreed mfCusPlantBreed = new MfCusPlantBreed();
			mfCusPlantBreed.setId(id);
			mfCusPlantBreed = mfCusPlantBreedFeign.getById(mfCusPlantBreed);
			if("2".equals(mfCusPlantBreed.getInfoType())){
				formId = formId+"breed";
			}
			if("3".equals(mfCusPlantBreed.getInfoType())){
				formId = formId+"manage";
			}
			FormData formcusplantbreedDetail = formService.getFormData(formId);
			getObjValue(formcusplantbreedDetail, mfCusPlantBreed,formData);
			query = sysInterfaceFeign.getQueryResult(User.getRegNo(request));
			if(query == null){
				query = "";
			}
			String htmlStrCorp = jsonFormUtil.getJsonStr(formcusplantbreedDetail,"propertySeeTag",query);
			if(mfCusPlantBreed!=null){
				dataMap.put("formHtml", htmlStrCorp);
				dataMap.put("flag", "success");
			}else{
				dataMap.put("msg", "获取详情失败");
				dataMap.put("flag", "error");
			}
			dataMap.put("formData", mfCusPlantBreed);
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
		FormData formcusplantbreedDetail = formService.getFormData("cusplantbreedDetail");
		getFormValue(formcusplantbreedDetail, getMapByJson(ajaxData));
		MfCusPlantBreed mfCusPlantBreedJsp = new MfCusPlantBreed();
		setObjValue(formcusplantbreedDetail, mfCusPlantBreedJsp);
		MfCusPlantBreed mfCusPlantBreed = mfCusPlantBreedFeign.getById(mfCusPlantBreedJsp);
		if(mfCusPlantBreed!=null){
			try{
				mfCusPlantBreed = (MfCusPlantBreed)EntityUtil.reflectionSetVal(mfCusPlantBreed, mfCusPlantBreedJsp, getMapByJson(ajaxData));
				mfCusPlantBreed = clacAmt(mfCusPlantBreed);
				mfCusPlantBreedFeign.update(mfCusPlantBreed);
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
	public MfCusPlantBreed clacAmt(MfCusPlantBreed mfCusPlantBreed){
		Double num =mfCusPlantBreed.getNum() ;//亩数/出栏数
		Double yield=mfCusPlantBreed.getYield();//亩产/单个重量
		Double price=mfCusPlantBreed.getPrice();//单价
		Double income =mfCusPlantBreed.getIncome();//收入
		Double profit = mfCusPlantBreed.getProfit();//种植毛收
		if(num!=null&&yield!=null&&price!=null){
			income = num*yield*price;
			mfCusPlantBreed.setIncome(income);
			profit = income;
		}
		Double seedPup = mfCusPlantBreed.getSeedPup();//种子/幼畜
		Double feiFeed = mfCusPlantBreed.getFeiFeed();//种子/幼畜
		Double pesticideCorn = mfCusPlantBreed.getPesticideCorn();//农药/玉米
		Double dripSoybean = mfCusPlantBreed.getDripSoybean();//滴灌费用/豆粕
		Double laborCost = mfCusPlantBreed.getLaborCost();//劳务成本
		Double otherFee =mfCusPlantBreed .getOtherFee();//其他费用
		Double totalCost0 = mfCusPlantBreed.getTotalCost();//总支出
		Double totalCost = 0.00;//种植/养殖成本
		if(seedPup!=null){
			totalCost = MathExtend.add(seedPup,totalCost0);
		}
		if(feiFeed!=null){
			totalCost = MathExtend.add(feiFeed,totalCost0);
		}
		if(pesticideCorn!=null){
			totalCost = MathExtend.add(pesticideCorn,totalCost0);
		}
		if(dripSoybean!=null){
			totalCost = MathExtend.add(dripSoybean,totalCost0);
		}
		if(laborCost!=null){
			totalCost = MathExtend.add(laborCost,totalCost0);
		}
		if(otherFee!=null){
			totalCost = MathExtend.add(otherFee,totalCost0);
		}
		if(income!=null&&totalCost!=null){
			mfCusPlantBreed.setTotalCost(totalCost);
			profit = MathExtend.subtract(income,totalCost);
			mfCusPlantBreed.setProfit(profit);
		}
		 Double premisesRental =mfCusPlantBreed.getPremisesRental();//经营场地租金
		 Double wages  =mfCusPlantBreed.getWages();//人员工资
		 Double waterElec =mfCusPlantBreed.getWaterElec();//水、电、气费用
		 Double fixedExp  =mfCusPlantBreed.getFixedExp();//固定支出
		Double totalProfit = profit;
		if(premisesRental!=null){
			totalProfit = MathExtend.subtract(totalProfit,premisesRental);
		}
		if(wages!=null){
			totalProfit = MathExtend.subtract(totalProfit,wages);
		}
		if(waterElec!=null){
			totalProfit = MathExtend.subtract(totalProfit,waterElec);
		}
		if(fixedExp!=null){
			totalProfit = MathExtend.subtract(totalProfit,fixedExp);
		}
		mfCusPlantBreed.setTotalProfit(totalProfit);
		return mfCusPlantBreed;
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
		MfCusPlantBreed mfCusPlantBreed = new MfCusPlantBreed();
		try{
			FormData formcusplantbreedBase = formService.getFormData("cusplantbreedBase");
			getFormValue(formcusplantbreedBase, getMapByJson(ajaxData));
			if(this.validateFormDataAnchor(formcusplantbreedBase)){
				mfCusPlantBreed = new MfCusPlantBreed();
				setObjValue(formcusplantbreedBase, mfCusPlantBreed);
				mfCusPlantBreedFeign.update(mfCusPlantBreed);
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("mfCusPlantBreed", mfCusPlantBreed));
				JsonTableUtil jtu = new JsonTableUtil();
				String tableHtml = jtu.getJsonStr("tablecusplantbreedBase", "tableTag",
						(List<MfCusPlantBreed>) mfCusPlantBreedFeign.findByPage(ipage).getResult(), null, true);
				dataMap.put("htmlStr", tableHtml);
				dataMap.put("htmlStrFlag", "1");
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
	public Map<String, Object> getByIdAjax(String id) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formcusplantbreedDetail = formService.getFormData("cusplantbreedDetail");
		MfCusPlantBreed mfCusPlantBreed = new MfCusPlantBreed();
		mfCusPlantBreed.setId(id);
		mfCusPlantBreed = mfCusPlantBreedFeign.getById(mfCusPlantBreed);
		getObjValue(formcusplantbreedDetail, mfCusPlantBreed,formData);
		if(mfCusPlantBreed!=null){
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
	public Map<String, Object> deleteAjax(String id) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusPlantBreed mfCusPlantBreed = new MfCusPlantBreed();
		mfCusPlantBreed.setId(id);
		try {
			mfCusPlantBreedFeign.delete(mfCusPlantBreed);
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
		FormData formcusplantbreedBase = null;
		MfCusPlantBreed mfCusPlantBreed = new MfCusPlantBreed();
		mfCusPlantBreed.setCusNo(cusNo);
		String cusType="";
		String formId="";
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		cusType=mfCusCustomer.getCusType();
		mfCusPlantBreed.setCusName(mfCusCustomer.getCusName());
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(cusType, "MfCusPlantBreedAction");
		if(mfCusFormConfig == null){

		}else{
			formId = mfCusFormConfig.getAddModelDef();
		}
		if(StringUtil.isEmpty(formId)){
		}else{
			formcusplantbreedBase = formService.getFormData(formId);
			if(formcusplantbreedBase.getFormId() == null){
			}else{
				getFormValue(formcusplantbreedBase);
				getObjValue(formcusplantbreedBase, mfCusPlantBreed);
			}
		}
		Map<String,Object> dataMap = new HashMap<String,Object>();
		model.addAttribute("formcusplantbreedBase", formcusplantbreedBase);
		model.addAttribute("query", "");
		return "/component/cus/MfCusPlantBreed_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model,String id) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formcusplantbreedBase = formService.getFormData("cusplantbreedBase");
		getFormValue(formcusplantbreedBase);
		MfCusPlantBreed mfCusPlantBreed = new MfCusPlantBreed();
		mfCusPlantBreed.setId(id);
		mfCusPlantBreed = mfCusPlantBreedFeign.getById(mfCusPlantBreed);
		getObjValue(formcusplantbreedBase, mfCusPlantBreed);
		model.addAttribute("formcusplantbreedBase", formcusplantbreedBase);
		model.addAttribute("query", "");
		return "/component/cus/MfCusPlantBreed_Detail";
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
		FormData formcusplantbreedDetail = formService.getFormData("cusplantbreedDetail");
		getFormValue(formcusplantbreedDetail);
		boolean validateFlag = this.validateFormData(formcusplantbreedDetail);
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
		FormData formcusplantbreedDetail = formService.getFormData("cusplantbreedDetail");
		getFormValue(formcusplantbreedDetail);
		boolean validateFlag = this.validateFormData(formcusplantbreedDetail);
	}
}

package app.component.cus.contract.controller;

import app.component.common.EntityUtil;
import app.component.cus.contract.entity.MfCusContract;
import app.component.cus.contract.feign.MfCusContractFeign;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.MathExtend;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.WaterIdUtil;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;
import org.apache.commons.lang.StringUtils;
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
 * 客户模块-合同订单
 */
@Controller
@RequestMapping("/mfCusContract")
public class MfCusContractController extends BaseFormBean {

	@Autowired
	private MfCusContractFeign mfCusContractFeign;

	@Autowired
	private MfCusFormConfigFeign mfCusFormConfigFeign;

	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	@Autowired
	private HttpServletRequest request;

	@Autowired
	private HttpServletResponse response;


	/**
	 * 新增页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model, String cusNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfCusContract mfCusContract = new MfCusContract();
		mfCusContract.setCusNo(cusNo);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusContractAction");
		FormData cusContractBase = formService.getFormData(mfCusFormConfig.getAddModelDef());
		this.changeFormProperty(cusContractBase, "cusNo", "initValue", mfCusContract.getCusNo());
		model.addAttribute("cusContractBase", cusContractBase);
		model.addAttribute("query", "");
		return "/component/cus/contract/MfCusContract_Insert";
	}

	/**
	 * AJAX新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, Object> map = getMapByJson(ajaxData);
			String formId = (String) map.get("formId");
			if (StringUtil.isEmpty(formId)) {
				formId = mfCusFormConfigFeign.getByCusType("base", "MfCusContractAction").getAddModel();
			}
			FormData cusContractBase = formService.getFormData(formId);
			getFormValue(cusContractBase, map);
			if (this.validateFormData(cusContractBase)) {
				MfCusContract mfCusContract = new MfCusContract();
				setObjValue(cusContractBase, mfCusContract);
				mfCusContract.setContractId(WaterIdUtil.getWaterId());
				MfCusContract mfCusContractTemp = mfCusContractFeign.getBySignYearAndCusNo(mfCusContract);
				if(null != mfCusContractTemp){
					dataMap.put("flag", "error");
					dataMap.put("msg", mfCusContractTemp.getSignYear()+"年数据已经存在!");
					return  dataMap;
				}
				mfCusContractFeign.insert(mfCusContract);
				// 更新客户信息完整度
				String infIntegrity = mfCusCustomerFeign.updateInfIntegrity(mfCusContract.getCusNo(),mfCusContract.getCusNo());
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(mfCusContract.getCusNo());
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				String tableId = "tablecusContractBaseList";
				MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusContractAction");
				if (mfCusFormConfig != null && StringUtil.isNotEmpty(mfCusFormConfig.getListModelDef())) {
					tableId = "table" + mfCusFormConfig.getListModelDef();
				}
				JsonTableUtil jtu = new JsonTableUtil();
				List<MfCusContract> list = mfCusContractFeign.getByCusNo(mfCusContract.getCusNo());
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
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 计算合同额增长率
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/calcGrowthRate")
	@ResponseBody
	public Map<String, Object> calcGrowthRate(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, Object> map = getMapByJson(ajaxData);
			String formId = (String) map.get("formId");
			if (StringUtil.isEmpty(formId)) {
				formId = mfCusFormConfigFeign.getByCusType("base", "MfCusContractAction").getAddModel();
			}
			FormData cusContractBase = formService.getFormData(formId);
			getFormValue(cusContractBase, map);
			MfCusContract mfCusContract = new MfCusContract();
			setObjValue(cusContractBase, mfCusContract);
			dataMap.put("flag", "success");
			dataMap.put("growthRate",  MathExtend.multiply(calcGrowthRate(mfCusContract),100));
			dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 *  计算合同额增长率
	 * @param mfCusContract
	 * @return
	 * @throws Exception
	 */
	private double calcGrowthRate(MfCusContract mfCusContract) throws Exception{
		MfCusContract mfCusContractCopy = mfCusContract;
		double growthRate = 0;
		if(null != mfCusContractCopy.getSignYear() && !"".equals(mfCusContractCopy.getSignYear())
				&& mfCusContractCopy.getContractAmt() >0){
			//获取上年度数据
			int year = Integer.valueOf(mfCusContractCopy.getSignYear());
			mfCusContractCopy.setSignYear(String.valueOf(year-1));
			MfCusContract mfCusContractTemp= mfCusContractFeign.getBySignYearAndCusNo(mfCusContractCopy);
			if(null != mfCusContractTemp){
				double growthAmt = MathExtend.subtract(mfCusContractCopy.getContractAmt(),mfCusContractTemp.getContractAmt());
				growthRate = MathExtend.divide(growthAmt,mfCusContractTemp.getContractAmt(),4);
			}
		}
		return growthRate;
	}

	/**
	 * 详情页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getDetailByAjax")
	@ResponseBody
	public Map<String, Object> getDetailByAjax(String contractId, String cusNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		request.setAttribute("ifBizManger", "3");
		Map<String, Object> dataMap = new HashMap<>();
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		String cusType = mfCusCustomer.getCusType();
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(cusType, "MfCusContractAction");
		String formId ="cusContractBaseDetail";
		if(StringUtils.isNotEmpty(mfCusFormConfig.getShowModelDef())){
			formId = mfCusFormConfig.getShowModelDef();
		}
		Map<String, Object> formData = new HashMap<>();
		this.setIfBizManger("3");
		JsonFormUtil jsonFormUtil = new JsonFormUtil();
		FormData cusContractBaseDetail = formService.getFormData(formId);
		MfCusContract mfCusContract = mfCusContractFeign.getById(contractId);
		getObjValue(cusContractBaseDetail, mfCusContract, formData);
		String htmlStr = jsonFormUtil.getJsonStr(cusContractBaseDetail, "propertySeeTag", "");
		dataMap.put("formHtml", htmlStr);
		dataMap.put("flag", "success");
		dataMap.put("formData", mfCusContract);
		return dataMap;
	}


	/**
	 * ajax 异步 单个字段或多个字段更新
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<>();
		FormData cusContractBase = formService.getFormData("cusContractBase");
		getFormValue(cusContractBase, getMapByJson(ajaxData));
		MfCusContract mfCusContractTemp = new MfCusContract();
		setObjValue(cusContractBase, mfCusContractTemp);
		MfCusContract mfCusContract = mfCusContractFeign.getById(mfCusContractTemp.getContractId());
		if (mfCusContract != null) {
			try {
				mfCusContract = (MfCusContract) EntityUtil.reflectionSetVal(mfCusContract, mfCusContractTemp,
						getMapByJson(ajaxData));
				mfCusContractFeign.update(mfCusContract);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			} catch (Exception e) {
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
	 * 修改页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String cusNo,String contractId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		MfCusContract mfCusContract = mfCusContractFeign.getById(contractId);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig  mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusContractAction");
		String formId="cusContractBase";
		if(mfCusFormConfig != null){
			formId = mfCusFormConfig.getAddModelDef();
		}
		FormData cusContractBase = formService.getFormData(formId);
		getFormValue(cusContractBase);
		getObjValue(cusContractBase, mfCusContract);
		model.addAttribute("cusContractBase", cusContractBase);
		model.addAttribute("query", "");
		return "/component/cus/contract/MfCusContract_Edit";
	}

	/**
	 * AJAX更新保存
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<>();
		MfCusContract mfCusContract = new MfCusContract();
		try{
			Map map = getMapByJson(ajaxData);
			String formId = (String)map.get("formId");
			FormData formcusContractBase = formService.getFormData(formId);
			getFormValue(formcusContractBase,map);
			if(this.validateFormData(formcusContractBase)){
				setObjValue(formcusContractBase, mfCusContract);
				MfCusContract mfCusContractTemp = mfCusContractFeign.getBySignYearAndCusNoExcludeSelf(mfCusContract);
				if(null != mfCusContractTemp){
					dataMap.put("flag", "error");
					dataMap.put("msg", mfCusContractTemp.getSignYear()+"年数据已经存在!");
					return  dataMap;
				}
				mfCusContractFeign.update(mfCusContract);
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(mfCusContract.getCusNo());
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				String tableId = "tablecusContractBaseList";
				MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusContractAction");
				if (mfCusFormConfig != null && StringUtil.isNotEmpty(mfCusFormConfig.getListModelDef())) {
					tableId = "table" + mfCusFormConfig.getListModelDef();
				}
				JsonTableUtil jtu = new JsonTableUtil();
				String tableHtml = jtu.getJsonStr(tableId, "tableTag",
						mfCusContractFeign.getByCusNo(mfCusContract.getCusNo()),null,true);
				dataMap.put("htmlStr", tableHtml);
				dataMap.put("htmlStrFlag", "1");
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

	/**
	 * Ajax异步删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String contractId) throws Exception{
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<>();
		try {
			mfCusContractFeign.deleteById(contractId);
			dataMap.put("flag", "success");
			dataMap.put("msg",MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.ERROR.getMessage());
			throw e;
		}
		return dataMap;
	}
}
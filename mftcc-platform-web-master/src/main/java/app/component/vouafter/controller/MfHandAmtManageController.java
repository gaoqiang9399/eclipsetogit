package app.component.vouafter.controller;

import app.component.app.entity.MfBusApply;
import app.component.appinterface.AppInterfaceFeign;
import app.component.calc.charge.feign.MfBusChargeFeeFeign;
import app.component.calc.fee.entity.MfBusChargeFee;
import app.component.common.EntityUtil;
import app.component.docinterface.DocInterfaceFeign;
import app.component.finance.manage.entity.CwCollectConfim;
import app.component.finance.manage.feign.CwCollectConfimFeign;
import app.component.pact.entity.MfBusFincApp;
import app.component.pact.entity.MfBusPact;
import app.component.pactinterface.PactInterfaceFeign;
import app.component.vouafter.entity.MfHandAmtManage;
import app.component.vouafter.feign.MfHandAmtManageFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.util.WaterIdUtil;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
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
 * Title: MfHandAmtManageController.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Tue Jul 07 19:03:05 CST 2020
 **/
@Controller
@RequestMapping(value = "/mfHandAmtManage")
public class MfHandAmtManageController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfHandAmtManageFeign mfHandAmtManageFeign;
	@Autowired
	private PactInterfaceFeign pactInterfaceFeign;
	@Autowired
	private AppInterfaceFeign appInterfaceFeign;
	@Autowired
	private CwCollectConfimFeign cwCollectConfimFeign;
	@Autowired
	private MfBusChargeFeeFeign mfBusChargeFeeFeign;

	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/vouafter/MfHandAmtManage_List";
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
		MfHandAmtManage mfHandAmtManage = new MfHandAmtManage();
		try {
			mfHandAmtManage.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfHandAmtManage.setCriteriaList(mfHandAmtManage, ajaxData);//我的筛选
			mfHandAmtManage.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfHandAmtManage,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setPageSize(pageSize);
			ipage.setParams(this.setIpageParams("mfHandAmtManage", mfHandAmtManage));
			//自定义查询Feign方法
			ipage = mfHandAmtManageFeign.findByPage(ipage);
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
		Map<String, Object> paramMap = getMapByJson(ajaxData);
		try {
			FormData formHandAmtManagebase = formService .getFormData((String)paramMap.get("formId"));
			getFormValue(formHandAmtManagebase, paramMap);
			if (this.validateFormData(formHandAmtManagebase)) {
				MfHandAmtManage mfHandAmtManage = new MfHandAmtManage();
				setObjValue(formHandAmtManagebase, mfHandAmtManage);
				mfHandAmtManageFeign.insert(mfHandAmtManage);
				dataMap.put("msg", "新增成功");
				dataMap.put("flag", "success");
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
		FormData formvouafterbase = formService.getFormData("vouafterbase");
		getFormValue(formvouafterbase, getMapByJson(ajaxData));
		MfHandAmtManage mfHandAmtManageJsp = new MfHandAmtManage();
		setObjValue(formvouafterbase, mfHandAmtManageJsp);
		MfHandAmtManage mfHandAmtManage = mfHandAmtManageFeign.getById(mfHandAmtManageJsp);
		if(mfHandAmtManage!=null){
			try{
				mfHandAmtManage = (MfHandAmtManage)EntityUtil.reflectionSetVal(mfHandAmtManage, mfHandAmtManageJsp, getMapByJson(ajaxData));
				mfHandAmtManageFeign.update(mfHandAmtManage);
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
		MfHandAmtManage mfHandAmtManage = new MfHandAmtManage();
		try{
			FormData formvouafterbase = formService.getFormData("HandAmtManagebase");
			getFormValue(formvouafterbase, getMapByJson(ajaxData));
			if(this.validateFormData(formvouafterbase)){
				mfHandAmtManage = new MfHandAmtManage();
				setObjValue(formvouafterbase, mfHandAmtManage);
				mfHandAmtManageFeign.update(mfHandAmtManage);
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
		FormData formvouafterbase = formService.getFormData("vouafterbase");
		MfHandAmtManage mfHandAmtManage = new MfHandAmtManage();
		mfHandAmtManage.setId(id);
		mfHandAmtManage = mfHandAmtManageFeign.getById(mfHandAmtManage);
		getObjValue(formvouafterbase, mfHandAmtManage,formData);
		if(mfHandAmtManage!=null){
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
		MfHandAmtManage mfHandAmtManage = new MfHandAmtManage();
		mfHandAmtManage.setId(id);
		try {
			mfHandAmtManageFeign.delete(mfHandAmtManage);
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
	public String input(Model model,String pactId) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		// 合同
		MfBusPact mfBusPact = new MfBusPact();
		mfBusPact.setPactId(pactId);
		mfBusPact = pactInterfaceFeign.getById(mfBusPact);
		// 申请
		MfBusApply mfBusApply = new MfBusApply();
        mfBusApply.setAppId(mfBusPact.getAppId());
        mfBusApply = appInterfaceFeign.getMfBusApply(mfBusApply);
        // 借据
        MfBusFincApp mfBusFincApp = new MfBusFincApp();
        mfBusFincApp.setAppId(mfBusApply.getAppId());
        mfBusFincApp = pactInterfaceFeign.getByIdNewFinc(mfBusFincApp);
        // 费用确认
		CwCollectConfim cwCollectConfim = new CwCollectConfim();
		cwCollectConfim.setPactId(pactId);
		cwCollectConfim = cwCollectConfimFeign.getById(cwCollectConfim);

		MfHandAmtManage mfHandAmtManage = new MfHandAmtManage();
		String id = WaterIdUtil.getWaterId();
        mfHandAmtManage.setId(id);
        mfHandAmtManage.setAppId(mfBusApply.getAppId());
        mfHandAmtManage.setAppName(mfBusApply.getAppName());
        mfHandAmtManage.setCusName(mfBusApply.getCusName());
        mfHandAmtManage.setCusNo(mfBusApply.getCusNo());
        mfHandAmtManage.setKindName(mfBusApply.getKindName());
        mfHandAmtManage.setKindNo(mfBusApply.getKindNo());
        mfHandAmtManage.setPactNo(mfBusPact.getPactNo());
        mfHandAmtManage.setPactId(mfBusPact.getPactId());
        mfHandAmtManage.setPactAmt(mfBusPact.getPactAmt());
		mfHandAmtManage.setFincId(mfBusFincApp.getFincId());
		mfHandAmtManage.setFincShowId(mfBusFincApp.getFincShowId());
		mfHandAmtManage.setProjectName(mfBusApply.getProjectName());
		//获取缴款通知书的账号信息
		MfBusChargeFee mfBusChargeFee = new MfBusChargeFee();
		mfBusChargeFee.setChargeId(cwCollectConfim.getChargeId());
		mfBusChargeFee = mfBusChargeFeeFeign.getById(mfBusChargeFee);

		mfHandAmtManage.setHandAmt(mfBusChargeFee.getHandAmt());
		mfHandAmtManage.setActualReceivedHandAmt(mfBusChargeFee.getHandAmt());
		mfHandAmtManage.setActualPayHandAmt(mfBusChargeFee.getHandAmt());
		mfHandAmtManage.setRefundHandAmt(0.00);
		FormData formHandAmtManagebase = formService.getFormData("HandAmtManagebase");
        getObjValue(formHandAmtManagebase, mfHandAmtManage);
		model.addAttribute("formHandAmtManagebase", formHandAmtManagebase);
		model.addAttribute("id", id);
        model.addAttribute("query", "");
        return "/component/vouafter/MfHandAmtManage_Insert";
    }
	/**
	 * 新增页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/initBusInfo")
	public String initBusInfo(Model model) throws Exception{
		ActionContext.initialize(request, response);
        FormService formService = new FormService();
        FormData formHandAmtManageinit = formService.getFormData("HandAmtManageinit");
        model.addAttribute("formHandAmtManageinit", formHandAmtManageinit);
        model.addAttribute("query", "");
		return "/component/vouafter/MfHandAmtManage_InitBusInfo";
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
		FormData formHandAmtManagebase = formService.getFormData("HandAmtManagebase");
		MfHandAmtManage mfHandAmtManage = new MfHandAmtManage();
		mfHandAmtManage.setId(id);
		mfHandAmtManage = mfHandAmtManageFeign.getById(mfHandAmtManage);
		getObjValue(formHandAmtManagebase, mfHandAmtManage);
		//节点要件关联编号
		model.addAttribute("id", id);
		model.addAttribute("formHandAmtManagebase", formHandAmtManagebase);
		model.addAttribute("query", "");
		return "/component/vouafter/MfHandAmtManage_Detail";
	}

	/**
	 * 列表打开页面请求
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getHandAmtManageListPage")
	public String getHandAmtManageListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/vouafter/MfHandAmtManage_SelectList";
	}

	/***
	 * 列表数据查询
	 *
	 * @param ajaxData
	 * @param pageNo
	 * @param tableId
	 * @param tableType
	 *
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/findHandAmtManageByPageAjax")
	@ResponseBody
	public Map<String, Object> findHandAmtManageByPageAjax(String ajaxData, Integer pageNo, Integer pageSize, String tableId,
											  String tableType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfHandAmtManage mfHandAmtManage = new MfHandAmtManage();
		try {
			mfHandAmtManage.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfHandAmtManage.setCriteriaList(mfHandAmtManage, ajaxData);// 我的筛选
			mfHandAmtManage.setCustomSorts(ajaxData);// 自定义排序
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);
			ipage.setParams(this.setIpageParams("mfHandAmtManage", mfHandAmtManage));
			ipage = mfHandAmtManageFeign.findHandAmtManageByPage(ipage);
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
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public void validateInsert() throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formvouafterbase = formService.getFormData("vouafterbase");
		getFormValue(formvouafterbase);
		boolean validateFlag = this.validateFormData(formvouafterbase);
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
		FormData formvouafterbase = formService.getFormData("vouafterbase");
		getFormValue(formvouafterbase);
		boolean validateFlag = this.validateFormData(formvouafterbase);
	}

}

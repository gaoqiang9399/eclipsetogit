package  app.component.pss.fund.controller;
import java.net.URLDecoder;
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
import com.core.struts.taglib.JsonTableUtil;

import app.component.pss.conf.entity.PssConfig;
import app.component.pss.conf.feign.PssConfigFeign;
import app.component.pss.fund.entity.PssBuySaleExpBill;
import app.component.pss.fund.feign.PssBuySaleExpBillFeign;
import app.component.pss.information.entity.PssSupplierInfo;
import app.component.pss.information.feign.PssSupplierInfoFeign;
import app.component.pss.utils.PssEnumBean;
import app.component.pss.utils.PssPublicUtil;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Title: PssBuySaleExpBillAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Thu Nov 09 15:53:42 CST 2017
 **/
@Controller
@RequestMapping("/pssBuySaleExpBill")
public class PssBuySaleExpBillController extends BaseFormBean{
	//注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private PssBuySaleExpBillFeign pssBuySaleExpBillFeign;
	
//	private MfCusCustomerFeign mfCusCustomerFeign;
	@Autowired
	private PssSupplierInfoFeign pssSupplierInfoFeign;
	@Autowired
	private PssConfigFeign pssConfigFeign;
	//全局变量
	//表单变量
	
	/**
	 * 列表有翻页
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request,response);
		JSONArray pssPaidStsedJsonArray = new CodeUtils().getJSONArrayByKeyName("PSS_PAID_STATE");
		this.getHttpRequest().setAttribute("pssPaidStsedJsonArray", pssPaidStsedJsonArray);
		model.addAttribute("query", "");
		return "/component/pss/fund/PssBuySaleExpBill_List";
	}
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception{
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssBuySaleExpBill pssBuySaleExpBill = new PssBuySaleExpBill();
		try {
			pssBuySaleExpBill.setCustomQuery(ajaxData);
			pssBuySaleExpBill.setCustomSorts(ajaxData);
			pssBuySaleExpBill.setCriteriaList(pssBuySaleExpBill, ajaxData);
			pssBuySaleExpBill.setAuditSts(PssEnumBean.YES_OR_NO.YES.getNum());//只查询已审核
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);
			ipage = pssBuySaleExpBillFeign.findByPage(ipage, pssBuySaleExpBill);
			JsonTableUtil jtu = new JsonTableUtil();
			@SuppressWarnings("rawtypes")
			String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		
		return dataMap;
	}
	/**
	 * 方法描述： 新增（查询）采购销售费用清单
	 * @return
	 * @throws Exception
	 * String
	 * @author Hgx
	 * @param jsonArr 
	 * @date 2017-11-09 下午04:56:06
	 */
	@RequestMapping(value = "/input")
	public String input(Model model,String sourceBillNo, String jsonArr) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
//		dataMap.put("buySaleExpNo", "BSE"+WaterIdUtil.getWaterId());
		jsonArr = URLDecoder.decode(jsonArr,"UTF-8");
		
		dataMap.put("jsonArr", jsonArr);
		dataMap.put("sourceBillNo", URLDecoder.decode(sourceBillNo, "UTF-8"));
//		dataMap.put("buysaleexpNo", pssBillnoConfFeign.getBillNo(PssEnumBean.PSS_BILL_TYPE.SKD.getValue(), "view"));
		FormData formpssbuysaleexpbill0002 = formService.getFormData("pssbuysaleexpbill0002");
		
		JSONObject json = new JSONObject();
		//支出类别
		JSONArray saleTypeListArray = new CodeUtils().getJSONArrayByKeyName("PSS_SALE_TYPE");
		JSONArray newClearAccTypeListArray = new JSONArray();
		JSONObject saleTypeObject = null;
		for(int i = 0;i < saleTypeListArray.size(); i++){
		 	saleTypeObject = new JSONObject();
			saleTypeObject.put("id", saleTypeListArray.getJSONObject(i).getString("optCode"));
			saleTypeObject.put("name", saleTypeListArray.getJSONObject(i).getString("optName"));
			newClearAccTypeListArray.add(saleTypeObject);
		}
		json.put("saleType", newClearAccTypeListArray);
		
		//供应商选择组件
		/*List<MfCusCustomer> mfCusCustomerList  = mfCusCustomerFeign.getAllCus("");
		JSONArray supplierArray = new JSONArray();
		JSONObject jsonObject = null;
		for (MfCusCustomer mfCusCustomer : mfCusCustomerList) {
		JSONObject 	jsonObject = new JSONObject();
			jsonObject.put("id", mfCusCustomer.getCusNo());
			jsonObject.put("name", mfCusCustomer.getCusName());
			supplierArray.add(jsonObject);
		}
		json.put("supp", supplierArray);*/
		PssSupplierInfo pssSupplierInfo = new PssSupplierInfo();
//		pssSupplierInfo.setEnabledStatus(PssEnumBean.YES_OR_NO.YES.getNum());
		List<PssSupplierInfo> pssSuppList = pssSupplierInfoFeign.getAll(pssSupplierInfo);
		JSONArray pssSuppArray = new JSONArray();
		JSONObject pssSuppObj = null;
		for(PssSupplierInfo psi:pssSuppList){
		 	pssSuppObj = new JSONObject();
			pssSuppObj.put("id", psi.getSupplierId());
			pssSuppObj.put("name", psi.getSupplierName());
			pssSuppArray.add(pssSuppObj);
		}
		json.put("supp", pssSuppArray);
		
		//系统配置信息
		PssConfig pssConfig = new PssConfig();
		List<PssConfig> pssConfigList = pssConfigFeign.getAll(pssConfig);
		if (pssConfigList != null && pssConfigList.size() > 0) {
			pssConfig = pssConfigList.get(0);
			if (pssConfig.getNumDecimalDigit() == null || "".equals(pssConfig.getNumDecimalDigit())) {
				pssConfig.setNumDecimalDigit("0");
			}
			if (pssConfig.getAmtDecimalDigit() == null || "".equals(pssConfig.getAmtDecimalDigit())) {
				pssConfig.setAmtDecimalDigit("2");
			}
			if (pssConfig.getDutyRate() == null) {
				pssConfig.setDutyRate(0.0);
			}
		} else {
			pssConfig.setNumDecimalDigit("0");
			pssConfig.setAmtDecimalDigit("2");
			pssConfig.setDutyRate(0.0);
		}
		json.put("pssConfig", pssConfig);
				
		String ajaxData = json.toString();
		
		model.addAttribute("formpssbuysaleexpbill0002", formpssbuysaleexpbill0002);
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("query", "");
		return "/component/pss/fund/PssBuySaleExpBill_Input";
	}
	/**
	 * 方法描述：采购销售费用清单新增（查询）初始化列表
	 * @return
	 * @throws Exception
	 * String
	 * @author Hgx
	 * @date 2017-11-10 上午11:20:06
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/findByPageForPopAjax")
	@ResponseBody
	public Map<String, Object> findByPageForPopAjax(String ajaxData,Integer pageNo,String jsonArr,String sourceBillNo,String tableId,String tableType) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssBuySaleExpBill pssBuySaleExpBill = new PssBuySaleExpBill();
		try {
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			
			List<PssBuySaleExpBill> pssBuySaleExpBillList = null;
			pssBuySaleExpBillList = PssPublicUtil.getMapByJsonObj(new PssBuySaleExpBill(), jsonArr);
			pssBuySaleExpBillList = PssPublicUtil.filterSepList(pssBuySaleExpBillList, "getSaleType");
			if(pssBuySaleExpBillList != null && pssBuySaleExpBillList.size() > 0){
				for (int i = 0; i < 5; i++) {
					pssBuySaleExpBill = new PssBuySaleExpBill();
					pssBuySaleExpBill.setSequence(pssBuySaleExpBillList.size() + 1);
					pssBuySaleExpBillList.add(pssBuySaleExpBill);
				}
			}else{
				//自定义查询Bo方法
				pssBuySaleExpBill.setSourceBillNo(sourceBillNo);
				ipage = pssBuySaleExpBillFeign.findByPageBySBNo(ipage, pssBuySaleExpBill);
				pssBuySaleExpBillList = (List<PssBuySaleExpBill>)ipage.getResult();
				/*pssBuySaleExpBillList = (List<PssBuySaleExpBill>) ipage.getResult();
				if (pssBuySaleExpBillList == null || pssBuySaleExpBillList.size() == 0) {
		ArrayList<PssBuySaleExpBill> 	pssBuySaleExpBillList = new ArrayList<PssBuySaleExpBill>();
				}
				for (int i = 0; i < 5; i++) {
		PssBuySaleExpBill 	pssBuySaleExpBill = new PssBuySaleExpBill();
					pssBuySaleExpBill.setSequence(i + 1);
					pssBuySaleExpBillList.add(pssBuySaleExpBill);
				}*/
			}
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, pssBuySaleExpBillList, ipage, true);
			ipage.setResult(tableHtml);
			ipage.setPageCounts(pssBuySaleExpBillList.size());
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			//Log.error("新增（查询）采购销售费用列表数据出错", e);
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
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
		FormData 	formpssbuysaleexpbill0002 = formService.getFormData("pssbuysaleexpbill0002");
			getFormValue(formpssbuysaleexpbill0002, getMapByJson(ajaxData));
			if(this.validateFormData(formpssbuysaleexpbill0002)){
		PssBuySaleExpBill pssBuySaleExpBill = new PssBuySaleExpBill();
				setObjValue(formpssbuysaleexpbill0002, pssBuySaleExpBill);
				pssBuySaleExpBillFeign.insert(pssBuySaleExpBill);
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
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
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		PssBuySaleExpBill pssBuySaleExpBill = new PssBuySaleExpBill();
		try{
		FormData 	formpssbuysaleexpbill0002 = formService.getFormData("pssbuysaleexpbill0002");
			getFormValue(formpssbuysaleexpbill0002, getMapByJson(ajaxData));
			if(this.validateFormData(formpssbuysaleexpbill0002)){
				pssBuySaleExpBill = new PssBuySaleExpBill();
				setObjValue(formpssbuysaleexpbill0002, pssBuySaleExpBill);
				pssBuySaleExpBillFeign.update(pssBuySaleExpBill);
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}
	
	/**
	 * AJAX获取查看
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String buySaleExpId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formpssbuysaleexpbill0002 = formService.getFormData("pssbuysaleexpbill0002");
		PssBuySaleExpBill pssBuySaleExpBill = new PssBuySaleExpBill();
		pssBuySaleExpBill.setBuySaleExpId(buySaleExpId);
		pssBuySaleExpBill = pssBuySaleExpBillFeign.getById(pssBuySaleExpBill);
		getObjValue(formpssbuysaleexpbill0002, pssBuySaleExpBill,formData);
		if(pssBuySaleExpBill!=null){
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
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String ajaxData,String buySaleExpId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		PssBuySaleExpBill pssBuySaleExpBill = new PssBuySaleExpBill();
		pssBuySaleExpBill.setBuySaleExpId(buySaleExpId);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			pssBuySaleExpBill = (PssBuySaleExpBill)JSONObject.toBean(jb, PssBuySaleExpBill.class);
			pssBuySaleExpBillFeign.delete(pssBuySaleExpBill);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

}

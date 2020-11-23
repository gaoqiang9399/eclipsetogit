package app.component.batchprint.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.component.model.entity.MfSysTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import com.google.gson.Gson;
import app.component.auth.feign.MfWorkFlowDyFormFeign;
import app.component.batchprint.MfUseMoneyQuery;
import app.component.batchprint.feign.MfBatchPrintFeign;
import app.component.model.entity.PageContent;
import app.component.model.entity.PageOfficePage;
import app.component.model.feign.MfSysTemplateFeign;
import app.component.model.feign.MfToPageOfficeFeign;
import app.util.toolkit.Ipage;
import config.YmlConfig;
import net.sf.json.JSONObject;

@Controller
public class MfBatchPrintController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfBatchPrintFeign mfBatchPrintFeign;
	@Autowired
	private MfWorkFlowDyFormFeign mfWorkFlowDyFormFeign;
	@Autowired
	private YmlConfig ymlConfig;
	@Autowired
	private MfSysTemplateFeign mfSysTemplateFeign;
	@Autowired
	private MfToPageOfficeFeign mfToPageOfficeFeign;
	
	@RequestMapping(value = "/batchPrint/useMoneyQuery/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request,
				response);
		return "/component/batchprint/MfUseMoney_List";
	}
	
	@RequestMapping(value = "/batchPrint/batchPrintEntrance")
	public String batchPrintEntrance(Model model,String printBizType,String fileNamePrefix,String pssBillsJson) throws Exception{
		ActionContext.initialize(request, response);
		model.addAttribute("printBizType", printBizType);
		model.addAttribute("fileNamePrefix", fileNamePrefix);
		model.addAttribute("pssBillsJson", pssBillsJson);
		return "/component/batchprint/batchPrintEntrace";
	}
	
	@RequestMapping(value = "/batchPrint/batchPrintAjax")
	@ResponseBody
	public Map<String, Object> batchPrintAjax(String printBizType,String bussinessId,String fileName) throws Exception{
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PageContent poCnt = null;
		PageOfficePage poPage = null;
		JSONObject jsonObject = new JSONObject();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
		String webPath = ymlConfig.getWebservice().get("webPath");
		if(webPath.startsWith("/")){
			basePath+=webPath;
		}else{
			basePath= basePath+"/"+webPath;
		}
		
		Map<String, String> poCntMap = null ;
		try{
			switch(printBizType){
				case "USEMONEYQUERY" : {			
					MfUseMoneyQuery mfUseMoneyQuery = new MfUseMoneyQuery();
					mfUseMoneyQuery.setFincId(bussinessId);
					mfUseMoneyQuery = mfBatchPrintFeign.getUseMoneyByFincId(mfUseMoneyQuery);
					poCntMap = new HashMap<String, String>();
					poCntMap.put("fincId", mfUseMoneyQuery.getFincId());
					poCntMap.put("cusNo", mfUseMoneyQuery.getCusNo());
					poCntMap.put("paymentAccId", mfUseMoneyQuery.getPaymentAccId());
					poCntMap.put("recNo", mfUseMoneyQuery.getRecNo());
					poCntMap.put("fileType", "1");
					poCntMap.put("readOnly", "1");
					poCntMap.put("saveBtn", "0");
					poCntMap.put("fileName", fileName);
					poCntMap.put("printBtn", "1");

					String poCntStr = mfToPageOfficeFeign.getPageContent(basePath, poCntMap);
					poCnt = JSON.parseObject(poCntStr, PageContent.class);
					poPage=PageOfficePage.getAddPO(poCnt, request);
					
					jsonObject = new JSONObject();
					jsonObject.put("pageFieldMap", poPage.getPoCnt().getPageFieldMap());
					jsonObject.put("pageTableMap", poPage.getPoCnt().getPageTableMapList());

					dataMap.put("jsonObject", jsonObject);
					break;
				}
				default:
			}
			//dataMap.put("poCnt", new Gson().toJson(poCntMap));
			
			/*jsonObject = new JSONObject();
			jsonObject.put("pageFieldMap", poPage.getPoCnt().getPageFieldMap());
			jsonObject.put("pageTableMap", poPage.getPoCnt().getPageTableMap());
			dataMap.put("jsonObject", jsonObject);*/
			
			dataMap.put("flag", true);
			dataMap.put("msg", "成功");
		}catch (Exception e) {
			e.printStackTrace();
			dataMap.put("success", false);
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	
	@RequestMapping(value = "/batchPrint/useMoneyQuery/findByPageAjax")
	@ResponseBody
	public Map<String, Object> useMoneyFindByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfUseMoneyQuery mfUseMoneyQuery = new MfUseMoneyQuery();
		try{
			mfUseMoneyQuery.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfUseMoneyQuery.setCustomSorts(ajaxData);// 自定义排序参数赋值
			mfUseMoneyQuery.setCriteriaList(mfUseMoneyQuery, ajaxData);// 我的筛选
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			
			ipage.setParams(this.setIpageParams("mfUseMoneyQuery", mfUseMoneyQuery));
			// 自定义查询Bo方法
			ipage = mfBatchPrintFeign.useMoneyFindByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List<?>) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		}catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	
}

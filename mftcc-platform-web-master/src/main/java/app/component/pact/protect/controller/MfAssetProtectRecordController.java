package app.component.pact.protect.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.component.cus.entity.MfBusAgencies;
import app.component.lawsuit.entity.MfLawsuit;
import app.component.lawsuit.feign.MfLawsuitFeign;
import app.component.pact.protect.entity.MfAssetProtectDizhai;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.domain.screen.FormActive;
import com.core.domain.screen.FormData;
import com.core.domain.screen.OptionsList;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;
import com.google.gson.Gson;

import app.base.User;
import app.component.collateral.entity.MfCollateralFormConfig;
import app.component.collateral.entity.PledgeBaseInfo;
import app.component.common.EntityUtil;
import app.component.pact.entity.MfBusPact;
import app.component.pact.protect.entity.MfAssetHandleInfo;
import app.component.pact.protect.entity.MfAssetProtectRecord;
import app.component.pact.protect.feign.MfAssetProtectRecordFeign;
import app.component.pactinterface.PactInterfaceFeign;
import app.component.wkf.entity.Result;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.util.JsonStrHandling;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.WaterIdUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Title: MfAssetProtectRecordAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri Aug 11 18:17:12 CST 2017
 **/
@Controller
@RequestMapping("/mfAssetProtectRecord")
public class MfAssetProtectRecordController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfAssetProtectRecordBo
	@Autowired
	private MfAssetProtectRecordFeign mfAssetProtectRecordFeign;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;
	@Autowired
	private PactInterfaceFeign pactInterfaceFeign;
	@Autowired
	private MfLawsuitFeign mfLawsuitFeign;
	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		CodeUtils codeUtils = new CodeUtils();
		// HANDLE_TYPE 处置方式
		JSONArray jsonArray = codeUtils.getJSONArrayByKeyName("HANDLE_TYPE");
		model.addAttribute("handleTypeJsonArray", jsonArray);
		// GEBT_ASSETS_STS
		jsonArray = codeUtils.getJSONArrayByKeyName("GEBT_ASSETS_STS");
		model.addAttribute("debtAssetsJsonArray", jsonArray);
		model.addAttribute("query", "");
		return "/component/pact/protect/MfAssetProtectRecord_List";
	}
	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPageForAssign")
	public String getListPageForAssign(Model model) throws Exception {
		ActionContext.initialize(request, response);
		return "/component/pact/protect/MfAssetProtectRecord_ListForAssign";
	}
	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/findByPageAjaxForAssign")
	@ResponseBody
	public Map<String, Object> findByPageAjaxForAssign(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusPact mfBusPact = new MfBusPact();
		try {
			mfBusPact.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfBusPact.setCriteriaList(mfBusPact, ajaxData);// 我的筛选
			mfBusPact.setCustomSorts(ajaxData);// 自定义排序
			// this.getRoleConditions(mfAssetProtectRecord,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfBusPact", mfBusPact));
			// 自定义查询Bo方法
			ipage = pactInterfaceFeign.getOverduePactIpage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			List<Map<String, String>> list =  (List<Map<String, String>>) ipage.getResult();
			for (int i = 0; i < list.size(); i++) {
				Map<String, String> map = list.get(i);
				if(StringUtil.isNotEmpty(map.get("ext10"))){
					map.put("ext1", "1");
				}else{
					map.put("ext1", "0");
				}
			}
			String tableHtml = jtu.getJsonStr(tableId, tableType, list, ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw new Exception(e.getMessage());
		}
		return dataMap;
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
			String ajaxData,Ipage ipage) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfAssetProtectRecord mfAssetProtectRecord = new MfAssetProtectRecord();
		try {
			mfAssetProtectRecord.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfAssetProtectRecord.setCriteriaList(mfAssetProtectRecord, ajaxData);// 我的筛选
			mfAssetProtectRecord.setCustomSorts(ajaxData);// 自定义排序
			// this.getRoleConditions(mfAssetProtectRecord,"1000000001");//记录级权限控制方法
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfAssetProtectRecord", mfAssetProtectRecord));
			// 自定义查询Bo方法
			ipage = mfAssetProtectRecordFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}
	@RequestMapping(value = "/assignTaskAjax")
	@ResponseBody
	public Map<String, Object> assignTaskAjax(String pactId, String opNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusPact mfBusPact = new MfBusPact();
		try {
			mfBusPact.setPactId(pactId);
			mfBusPact.setExt10(opNo);
			pactInterfaceFeign.updatePact(mfBusPact);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw new Exception(e.getMessage());
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
			FormData formassetprotect0002 = formService.getFormData("assetprotect0002");
			getFormValue(formassetprotect0002, getMapByJson(ajaxData));
			if (this.validateFormData(formassetprotect0002)) {
				MfAssetProtectRecord mfAssetProtectRecord = new MfAssetProtectRecord();
				setObjValue(formassetprotect0002, mfAssetProtectRecord);
				mfAssetProtectRecordFeign.insert(mfAssetProtectRecord);
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
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
		FormData formassetprotect0002 = formService.getFormData("assetprotect0002");
		getFormValue(formassetprotect0002, getMapByJson(ajaxData));
		MfAssetProtectRecord mfAssetProtectRecordJsp = new MfAssetProtectRecord();
		setObjValue(formassetprotect0002, mfAssetProtectRecordJsp);
		MfAssetProtectRecord mfAssetProtectRecord = mfAssetProtectRecordFeign.getById(mfAssetProtectRecordJsp);
		if (mfAssetProtectRecord != null) {
			try {
				mfAssetProtectRecord = (MfAssetProtectRecord) EntityUtil.reflectionSetVal(mfAssetProtectRecord,
						mfAssetProtectRecordJsp, getMapByJson(ajaxData));
				mfAssetProtectRecordFeign.update(mfAssetProtectRecord);
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
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formassetprotect0002 = formService.getFormData("assetprotect0002");
			getFormValue(formassetprotect0002, getMapByJson(ajaxData));
			if (this.validateFormData(formassetprotect0002)) {
				MfAssetProtectRecord mfAssetProtectRecord = new MfAssetProtectRecord();
				setObjValue(formassetprotect0002, mfAssetProtectRecord);
				mfAssetProtectRecordFeign.update(mfAssetProtectRecord);
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw new Exception(e.getMessage());
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
	public Map<String, Object> getByIdAjax(String protectId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formassetprotect0002 = formService.getFormData("assetprotect0002");
		MfAssetProtectRecord mfAssetProtectRecord = new MfAssetProtectRecord();
		mfAssetProtectRecord.setProtectId(protectId);
		mfAssetProtectRecord = mfAssetProtectRecordFeign.getById(mfAssetProtectRecord);
		getObjValue(formassetprotect0002, mfAssetProtectRecord, formData);
		if (mfAssetProtectRecord != null) {
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
	public Map<String, Object> deleteAjax(String protectId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfAssetProtectRecord mfAssetProtectRecord = new MfAssetProtectRecord();
		mfAssetProtectRecord.setProtectId(protectId);
		try {
			mfAssetProtectRecordFeign.delete(mfAssetProtectRecord);
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

	/**
	 * 新增页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formassetprotect0001 = formService.getFormData("assetprotect0001");
		FormData formassetbaseinfo = formService.getFormData("pledgeBase");
		Gson gson = new  Gson();
		// 获得初始化表单数据
		dataMap = mfAssetProtectRecordFeign.initInputData();
		
		String ajaxCollClassData = gson.toJson( dataMap.get("collClassJson"));
		String ajaxOverduePactData = gson.toJson( dataMap.get("overduePactJson"));
		MfAssetProtectRecord mfAssetProtectRecord = new MfAssetProtectRecord();
		String protectId = WaterIdUtil.getWaterId("protect");
		mfAssetProtectRecord.setProtectId(protectId);
		getObjValue(formassetprotect0001, mfAssetProtectRecord);
		PledgeBaseInfo pledgeBaseInfo = gson.fromJson(gson.toJson(dataMap.get("pledgeBaseInfo")), PledgeBaseInfo.class);
		getObjValue(formassetbaseinfo, pledgeBaseInfo);
		List<OptionsList> vouTypeList = gson.fromJson(gson.toJson(dataMap.get("vouTypeList")), List.class);
		changeFormProperty(formassetbaseinfo, "pledgeMethod", "optionArray", vouTypeList);
		String pledgeNoStr = (String) dataMap.get("pledgeNoStr");
		model.addAttribute("ajaxCollClassData", ajaxCollClassData);
		model.addAttribute("ajaxOverduePactData", ajaxOverduePactData);
		model.addAttribute("protectId", protectId);
		model.addAttribute("pledgeNoStr", pledgeNoStr);
		model.addAttribute("formassetprotect0001", formassetprotect0001);
		model.addAttribute("formassetbaseinfo", formassetbaseinfo);
		model.addAttribute("query", "");
		return "/component/pact/protect/MfAssetProtectRecord_Insert";
	}

	/**
	 * 
	 * 方法描述： 资产变更
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-8-14 下午4:10:21
	 */
	@RequestMapping(value = "/inputAssetChange")
	public String inputAssetChange(Model model, String protectId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formassetprotect0001 = formService.getFormData("assetprotect0001");
		FormData formassetbaseinfo = formService.getFormData("pledgeBase");
		JSONObject collClassJson = new JSONObject();
		JSONObject overduePactJson = new JSONObject();
		JSONObject cusJson = new JSONObject();
		// 获得初始化表单数据
		dataMap = mfAssetProtectRecordFeign.initAssetChangeData(protectId);
		collClassJson = (JSONObject) dataMap.get("collClassJson");
		overduePactJson = (JSONObject) dataMap.get("overduePactJson");
		cusJson = (JSONObject) dataMap.get("cusJson");
		String ajaxCollClassData = collClassJson.toString();
		String ajaxOverduePactData = overduePactJson.toString();
		String cusData = cusJson.toString();
		MfAssetProtectRecord mfAssetProtectRecord = (MfAssetProtectRecord) dataMap.get("mfAssetProtectRecord");
		getObjValue(formassetprotect0001, mfAssetProtectRecord);
		PledgeBaseInfo pledgeBaseInfo = (PledgeBaseInfo) dataMap.get("pledgeBaseInfo");
		getObjValue(formassetbaseinfo, pledgeBaseInfo);
		List<OptionsList> vouTypeList = (List<OptionsList>) dataMap.get("vouTypeList");
		changeFormProperty(formassetbaseinfo, "pledgeMethod", "optionArray", vouTypeList);
		String pledgeNoStr = (String) dataMap.get("pledgeNoStr");
		model.addAttribute("cusData", cusData);
		model.addAttribute("pledgeNoStr", pledgeNoStr);
		model.addAttribute("ajaxCollClassData", ajaxCollClassData);
		model.addAttribute("ajaxOverduePactData", ajaxOverduePactData);
		model.addAttribute("mfAssetProtectRecord", mfAssetProtectRecord);
		model.addAttribute("formassetbaseinfo", formassetbaseinfo);
		model.addAttribute("formassetprotect0001", formassetprotect0001);
		model.addAttribute("query", "");
		return "/component/pact/protect/MfAssetProtectRecord_InsertAssetChange";
	}

	/**
	 * 
	 * 方法描述：抵账资产信息保存
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-8-12 上午11:50:04
	 */
	@RequestMapping(value = "/insertAssetProtectAjax")
	@ResponseBody
	public Map<String, Object> insertAssetProtectAjax(String ajaxAppData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap = getMapByJson(ajaxAppData);
		try {
			FormData formassetprotect0001 = formService.getFormData("assetprotect0001");
			getFormValue(formassetprotect0001, getMapByJson(ajaxAppData));
			if (this.validateFormData(formassetprotect0001)) {
				MfAssetProtectRecord mfAssetProtectRecord = new MfAssetProtectRecord();
				setObjValue(formassetprotect0001, mfAssetProtectRecord);
				mfAssetProtectRecordFeign.insertAssetProtect(mfAssetProtectRecord);
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

	@RequestMapping(value = "/chaFengAjax")
	@ResponseBody
	public Map<String, Object> chaFengAjax(String ajaxAppData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap = getMapByJson(ajaxAppData);
		try {
			FormData formassetprotect0001 = formService.getFormData("assetchafeng");
			getFormValue(formassetprotect0001, getMapByJson(ajaxAppData));
			if (this.validateFormData(formassetprotect0001)) {
				MfAssetProtectRecord mfAssetProtectRecord = new MfAssetProtectRecord();
				setObjValue(formassetprotect0001, mfAssetProtectRecord);
				mfAssetProtectRecordFeign.updateAssetProtect(mfAssetProtectRecord);
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}
	@RequestMapping(value = "/saveDizhaiAjax")
	@ResponseBody
	public Map<String, Object> saveDizhaiAjax(String ajaxAppData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap = getMapByJson(ajaxAppData);
		try {
			FormData formassetprotect0001 = formService.getFormData("assetdizhai");
			getFormValue(formassetprotect0001, getMapByJson(ajaxAppData));
			if (this.validateFormData(formassetprotect0001)) {
				MfAssetProtectDizhai mfAssetProtectDizhai = new MfAssetProtectDizhai();
				setObjValue(formassetprotect0001, mfAssetProtectDizhai);
				mfAssetProtectRecordFeign.saveOrUpdateAssetDizhai(mfAssetProtectDizhai);
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}
	/**
	 * 获取授信产品支持的合作银行
	 * @param kindNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getLawsuitAjax")
	@ResponseBody
	public Map<String,Object> getLawsuitAjax(String ajaxData,String kindNo,int pageNo) throws Exception {
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap=new HashMap<String,Object>();
		try{
			MfLawsuit mfLawsuit = new MfLawsuit();
			mfLawsuit.setCustomQuery(ajaxData);//自定义查询参数赋值
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfLawsuit", mfLawsuit));
			ipage =mfLawsuitFeign.findByPage(ipage);
			dataMap.put("ipage",ipage);
		}catch(Exception e){
			e.printStackTrace();
		}
		return dataMap;
	}
	/**
	 * 
	 * 方法描述： 资产信息修改保存
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @param ajaxPledgeData 
	 * @date 2017-8-14 下午5:39:29
	 */
	@RequestMapping(value = "/updateAssetProtectAjax")
	@ResponseBody
	public Map<String, Object> updateAssetProtectAjax(String ajaxAppData, String ajaxPledgeData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap = getMapByJson(ajaxAppData);
		// mfAssetProtectRecord.setProtectId((String)dataMap.get("protectId"));
		try {
			/*
			 * mfAssetProtectRecord=mfAssetProtectRecordFeign.getById(mfAssetProtectRecord);
			 * if(mfAssetProtectRecord!=null){ dataMap.put("flag", "submit"); }else{ }
			 */
			FormData formassetprotect0002 = formService.getFormData("assetprotect0002");
			getFormValue(formassetprotect0002, getMapByJson(ajaxAppData));
			FormData formassetbaseinfo = formService.getFormData((String) getMapByJson(ajaxPledgeData).get("formId"));
			getFormValue(formassetbaseinfo, getMapByJson(ajaxPledgeData));
			if (this.validateFormData(formassetprotect0002) && this.validateFormData(formassetbaseinfo)) {
				MfAssetProtectRecord mfAssetProtectRecord = new MfAssetProtectRecord();
				PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
				setObjValue(formassetprotect0002, mfAssetProtectRecord);
				setObjValue(formassetbaseinfo, pledgeBaseInfo);
				mfAssetProtectRecordFeign.updateAssetProtect(mfAssetProtectRecord);
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

	/***
	 * 新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model,Ipage ipage) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formassetprotect0002 = formService.getFormData("assetprotect0002");
		getFormValue(formassetprotect0002);
		MfAssetProtectRecord mfAssetProtectRecord = new MfAssetProtectRecord();
		setObjValue(formassetprotect0002, mfAssetProtectRecord);
		mfAssetProtectRecordFeign.insert(mfAssetProtectRecord);
		getObjValue(formassetprotect0002, mfAssetProtectRecord);
		this.addActionMessage(model, "保存成功");
		ipage.setParams(this.setIpageParams("mfAssetProtectRecord", mfAssetProtectRecord));
		List<MfAssetProtectRecord> mfAssetProtectRecordList = (List<MfAssetProtectRecord>) mfAssetProtectRecordFeign
				.findByPage(ipage).getResult();
		model.addAttribute("formassetprotect0002", formassetprotect0002);
		model.addAttribute("mfAssetProtectRecord", mfAssetProtectRecord);
		model.addAttribute("mfAssetProtectRecordList", mfAssetProtectRecordList);
		model.addAttribute("query", "");
		return "/component/pact/protect/MfAssetProtectRecord_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String protectId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formassetprotect0001 = formService.getFormData("assetprotect0001");
		getFormValue(formassetprotect0001);
		dataMap = mfAssetProtectRecordFeign.getAssetProtectDetailData(protectId);

		MfAssetProtectRecord mfAssetProtectRecord = new MfAssetProtectRecord();
		mfAssetProtectRecord = (MfAssetProtectRecord) JsonStrHandling.handlingStrToBean(dataMap.get("mfAssetProtectRecord"),
				MfAssetProtectRecord.class);
		//mfAssetProtectRecord = (MfAssetProtectRecord) dataMap.get("mfAssetProtectRecord");
		getObjValue(formassetprotect0001, mfAssetProtectRecord);
		// 押品详情信息
		PledgeBaseInfo pledgeBaseInfo = (PledgeBaseInfo) JsonStrHandling.handlingStrToBean(dataMap.get("pledgeBaseInfo"),
				PledgeBaseInfo.class);
		MfCollateralFormConfig mfCollateralFormConfig = (MfCollateralFormConfig) JsonStrHandling.handlingStrToBean(dataMap.get("mfCollateralFormConfig"),
				MfCollateralFormConfig.class);
		//PledgeBaseInfo pledgeBaseInfo = (PledgeBaseInfo) dataMap.get("pledgeBaseInfo");
		//MfCollateralFormConfig mfCollateralFormConfig = (MfCollateralFormConfig) dataMap.get("mfCollateralFormConfig");
		FormData formassetbaseinfo = formService.getFormData(mfCollateralFormConfig.getShowModel());
		if (formassetbaseinfo.getFormId() == null) {
			formassetbaseinfo = formService.getFormData(mfCollateralFormConfig.getShowModelDef());
		}
		getObjValue(formassetbaseinfo, pledgeBaseInfo);
		String handleFormId = (String) dataMap.get("handleFormId");
		FormData formassethandle = formService.getFormData(handleFormId);
		getObjValue(formassethandle, mfAssetProtectRecord);
		MfAssetHandleInfo mfAssetHandleInfo = (MfAssetHandleInfo) JsonStrHandling.handlingStrToBean(dataMap.get("mfAssetHandleInfo"),
				MfAssetHandleInfo.class);
		String handleFlag = mfAssetProtectRecord.getAssetState();
		String handleType = mfAssetProtectRecord.getHandleType();
		if (mfAssetHandleInfo != null) {
			handleType = mfAssetHandleInfo.getHandleType();
			getObjValue(formassethandle, mfAssetHandleInfo);
		}
		String query = (String) dataMap.get("query");
		model.addAttribute("mfAssetProtectRecord", mfAssetProtectRecord);
		model.addAttribute("formassetbaseinfo", formassetbaseinfo);
		
		model.addAttribute("protectId", protectId);
		model.addAttribute("handleType", handleType);
		model.addAttribute("handleFlag", handleFlag);
		model.addAttribute("formassetprotect0001", formassetprotect0001);
		model.addAttribute("query", query);
		return "/component/pact/protect/MfAssetProtectRecord_Detail";
	}
	/**
	 * 查询
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/chaFengInput")
	public String chaFengInput(Model model, String protectId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formassetprotect0001 = formService.getFormData("assetchafeng");
		getFormValue(formassetprotect0001);
		MfAssetProtectRecord mfAssetProtectRecord = new MfAssetProtectRecord();
		mfAssetProtectRecord.setProtectId(protectId);
		mfAssetProtectRecord =mfAssetProtectRecordFeign.getById(mfAssetProtectRecord);
		mfAssetProtectRecord.setSeizureFlag("1");
		getObjValue(formassetprotect0001, mfAssetProtectRecord);
		model.addAttribute("mfAssetProtectRecord", mfAssetProtectRecord);
		model.addAttribute("protectId", protectId);
		model.addAttribute("formassetprotect0001", formassetprotect0001);
		model.addAttribute("query", "");
		return "/component/pact/protect/MfAssetProtectRecord_ChaFeng";
	}
	@RequestMapping(value = "/diZhaiInput")
	public String diZhaiInput(Model model, String protectId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formassetprotect0001 = formService.getFormData("assetdizhai");
		getFormValue(formassetprotect0001);
		MfAssetProtectRecord mfAssetProtectRecord = new MfAssetProtectRecord();
		mfAssetProtectRecord.setProtectId(protectId);
		mfAssetProtectRecord =mfAssetProtectRecordFeign.getById(mfAssetProtectRecord);
		mfAssetProtectRecord.setSeizureFlag("1");
		getObjValue(formassetprotect0001, mfAssetProtectRecord);
		MfAssetProtectDizhai mfAssetProtectDizhai = new MfAssetProtectDizhai();
		mfAssetProtectDizhai.setProtectId(protectId);
		mfAssetProtectDizhai = mfAssetProtectRecordFeign.getByDizhai(mfAssetProtectDizhai);
		getObjValue(formassetprotect0001, mfAssetProtectDizhai);
		model.addAttribute("mfAssetProtectRecord", mfAssetProtectRecord);
		model.addAttribute("protectId", protectId);
		model.addAttribute("formassetprotect0001", formassetprotect0001);
		model.addAttribute("query", "");
		return "/component/pact/protect/MfAssetProtectRecord_DiZhai";
	}
	@RequestMapping(value = "/jieFengInput")
	public String jieFengInput(Model model, String protectId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formassetprotect0001 = formService.getFormData("assetjiefeng");
		getFormValue(formassetprotect0001);
		MfAssetProtectRecord mfAssetProtectRecord = new MfAssetProtectRecord();
		mfAssetProtectRecord.setProtectId(protectId);
		mfAssetProtectRecord =mfAssetProtectRecordFeign.getById(mfAssetProtectRecord);
		mfAssetProtectRecord.setSeizureFlag("0");
		getObjValue(formassetprotect0001, mfAssetProtectRecord);
		model.addAttribute("mfAssetProtectRecord", mfAssetProtectRecord);
		model.addAttribute("protectId", protectId);
		model.addAttribute("formassetprotect0001", formassetprotect0001);
		model.addAttribute("query", "");
		return "/component/pact/protect/MfAssetProtectRecord_ChaFeng";
	}
	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String protectId) throws Exception {
		ActionContext.initialize(request, response);
		MfAssetProtectRecord mfAssetProtectRecord = new MfAssetProtectRecord();
		mfAssetProtectRecord.setProtectId(protectId);
		mfAssetProtectRecordFeign.delete(mfAssetProtectRecord);
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
		FormData formassetprotect0002 = formService.getFormData("assetprotect0002");
		getFormValue(formassetprotect0002);
		boolean validateFlag = this.validateFormData(formassetprotect0002);
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
		FormData formassetprotect0002 = formService.getFormData("assetprotect0002");
		getFormValue(formassetprotect0002);
		boolean validateFlag = this.validateFormData(formassetprotect0002);
	}

	/**
	 * 
	 * 方法描述： 提交资产保全审批
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-8-14 上午11:18:38
	 */
	@RequestMapping(value = "/submitAjax")
	@ResponseBody
	public Map<String, Object> submitAjax(String protectId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfAssetProtectRecord mfAssetProtectRecord = mfAssetProtectRecordFeign.submitProcess(protectId,User.getRegNo(request),User.getRegName(request),User.getOrgNo(request));
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("userRole", mfAssetProtectRecord.getApproveNodeName());
			paramMap.put("opNo", mfAssetProtectRecord.getApprovePartName());
			dataMap.put("msg", MessageEnum.SUCCEED_APPROVAL.getMessage(paramMap));
			dataMap.put("flag", "success");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}

	// 打开资产保全审批页面
	@RequestMapping(value = "/getViewApprove")
	public String getViewApprove(Model model,String protectId, String activityType,String hideOpinionType) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		JSONObject collClassJson = new JSONObject();
		JSONObject overduePactJson = new JSONObject();
		JSONObject cusJson = new JSONObject();
		FormData formassetapprove = formService.getFormData("assetapprove");
		dataMap = mfAssetProtectRecordFeign.initApproveData(protectId);
		MfAssetProtectRecord mfAssetProtectRecord = JsonStrHandling.handlingStrToBean(dataMap.get("mfAssetProtectRecord"), MfAssetProtectRecord.class) ;
		PledgeBaseInfo pledgeBaseInfo =JsonStrHandling.handlingStrToBean(dataMap.get("pledgeBaseInfo"), PledgeBaseInfo.class) ;
		MfCollateralFormConfig mfCollateralFormConfig = JsonStrHandling.handlingStrToBean(dataMap.get("mfCollateralFormConfig"), MfCollateralFormConfig.class) ;
		// String formId=mfCollateralFormConfig.getAddModel();
		FormData formassetbaseinfo = formService.getFormData(mfCollateralFormConfig.getAddModel());
		if (formassetbaseinfo.getFormId() == null) {
			formassetbaseinfo = formService.getFormData(mfCollateralFormConfig.getAddModelDef());
		}
		getObjValue(formassetapprove, mfAssetProtectRecord);
		getObjValue(formassetbaseinfo, pledgeBaseInfo);
		collClassJson = JSONObject.fromObject( dataMap.get("collClassJson"));
		overduePactJson = JSONObject.fromObject( dataMap.get("overduePactJson"));
		cusJson = JSONObject.fromObject( dataMap.get("cusJson"));
		String ajaxCollClassData = collClassJson.toString();
		String ajaxOverduePactData = overduePactJson.toString();
		String cusData = cusJson.toString();
		
		JSONArray vouTypeList = JSONArray.fromObject(dataMap.get("vouTypeList")) ;
		changeFormProperty(formassetbaseinfo, "pledgeMethod", "optionArray", vouTypeList);
		// 设置表单元素不可编辑
		FormActive[] list = formassetbaseinfo.getFormActives();
		for (int i = 0; i < list.length; i++) {
			FormActive formActive = list[i];
			formActive.setReadonly("1");
		}
		// 获得审批节点信息
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(protectId, null);
		// 处理审批意见类型
		Map<String, String> opinionTypeMap = new HashMap<String, String>();
		opinionTypeMap.put("hideOpinionType", hideOpinionType); // 隐藏审批类型
		String nodeNo = taskAppro.getActivityName();// 审批流程中当前审批节点编号
		opinionTypeMap.put("processDefinitionId", taskAppro.getProcessDefinitionId());// 流程id
		opinionTypeMap.put("nodeNo", nodeNo);// 当前节点编号
		List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType,
				taskAppro.getCouldRollback(), opinionTypeMap);
		this.changeFormProperty(formassetapprove, "opinionType", "optionArray", opinionTypeList);
		model.addAttribute("cusData", cusData);
		model.addAttribute("ajaxCollClassData", ajaxCollClassData);
		model.addAttribute("ajaxOverduePactData", ajaxOverduePactData);
		model.addAttribute("formassetapprove", formassetapprove);
		model.addAttribute("formassetbaseinfo", formassetbaseinfo);
		model.addAttribute("mfAssetProtectRecord", mfAssetProtectRecord);
		model.addAttribute("pactId", mfAssetProtectRecord.getPactId());
		model.addAttribute("protectId", protectId);
		model.addAttribute("query", "");
		return "/component/pact/protect/WkfAssetViewApprove";
	}

	/**
	 * 
	 * 方法描述： 资产保全审批意见保存提交
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @param protectId 
	 * @param activityType 
	 * @param taskId 
	 * @param transition 
	 * @param nextUser 
	 * @date 2017-8-14 上午10:38:02
	 */
	@RequestMapping(value = "/submitUpdateAjax")
	@ResponseBody
	public Map<String, Object> submitUpdateAjax(String ajaxData, String protectId, String activityType, String taskId, String transition, String nextUser) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfAssetProtectRecord mfAssetProtectRecord = new MfAssetProtectRecord();
			mfAssetProtectRecord.setProtectId(protectId);
			dataMap = getMapByJson(ajaxData);
			dataMap.put("activityType", activityType);
			dataMap.put("taskId", taskId);
			dataMap.put("transition", transition);
			dataMap.put("nextUser", nextUser);
			dataMap.put("regNo", User.getRegNo(request));
			dataMap.put("orgNo", User.getOrgNo(request));
			dataMap.put("regName", User.getRegName(request));
			String opinionType = String.valueOf(dataMap.get("opinionType"));
			String approvalOpinion = String.valueOf(dataMap.get("approvalOpinion"));
			Result res = mfAssetProtectRecordFeign.doCommit(dataMap);
			if (res.isSuccess()) {
				dataMap.put("flag", "success");
				if (res.isEndSts()) {
					dataMap.put("msg", res.getMsg());
				} else {
					dataMap.put("msg", res.getMsg());
				}
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.FAILED_SUBMIT.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}

}

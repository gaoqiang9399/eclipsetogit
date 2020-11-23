package app.component.assure.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.component.cus.entity.MfCusAssureCompany;
import app.component.pact.entity.MfBusFincApp;
import app.util.DataUtil;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.WaterIdUtil;
import config.YmlConfig;
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

import app.component.app.entity.MfBusApply;
import app.component.appinterface.AppInterfaceFeign;
import app.component.assure.entity.MfAssureInfo;
import app.component.assure.entity.MfBusApplyAssureInfo;
import app.component.assure.feign.MfAssureInfoFeign;
import app.component.authinterface.CreditApplyInterfaceFeign;
import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.cus.entity.MfCusType;
import app.component.cusinterface.CusInterfaceFeign;
import app.tech.upload.FeignSpringFormEncoder;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Title: MfAssureInfoAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu Jul 20 15:11:16 CST 2017
 **/
@Controller
@RequestMapping("/mfAssureInfo")
public class MfAssureInfoController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfAssureInfoBo
	@Autowired
	private MfAssureInfoFeign mfAssureInfoFeign;
	@Autowired
	private CusInterfaceFeign cusInterfaceFeign;
	@Autowired
	private CreditApplyInterfaceFeign creditApplyInterfaceFeign;
	@Autowired
	private AppInterfaceFeign appInterfaceFeign;
	@Autowired
	private YmlConfig ymlConfig;
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
		return "/component/assure/MfAssureInfo_List";
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
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfAssureInfo mfAssureInfo = new MfAssureInfo();
		try {
			mfAssureInfo.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfAssureInfo.setCriteriaList(mfAssureInfo, ajaxData);// 我的筛选
			// mfAssureInfo.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfAssureInfo,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = mfAssureInfoFeign.findByPage(ipage, mfAssureInfo);
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

	/**
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData, String appId, String isQuote, String entrFlag,
			String skipFlag,String extensionApplyId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = getMapByJson(ajaxData);
			String formId = (String)dataMap.get("formId");
			if(StringUtil.isBlank(formId)){
				formId = "assure0001";
			}
			FormData formassure0001 = formService.getFormData(formId);
			getFormValue(formassure0001, getMapByJson(ajaxData));
			if (this.validateFormDataAnchor(formassure0001)) {
				MfAssureInfo mfAssureInfo = new MfAssureInfo();
				setObjValue(formassure0001, mfAssureInfo);
				dataMap.put("appId", appId);
				dataMap.put("isQuote", isQuote);
				dataMap.put("entrFlag", entrFlag);
				dataMap.put("extensionApplyId", extensionApplyId);
				new FeignSpringFormEncoder().addParamsToBaseDomain(mfAssureInfo);
				dataMap.put("mfAssureInfo", mfAssureInfo);
				// 判断是否是担保公司担保
				MfCusType mfCusType = new MfCusType();
				mfCusType.setTypeNo(mfAssureInfo.getAssureCusType());
				mfCusType = cusInterfaceFeign.getMfCusTypeByTypeNo(mfCusType);
				if(mfCusType != null){
					if (BizPubParm.CUS_BASE_TYPE_DANBAO.equals(mfCusType.getBaseType())) {// 9担保公司的客户类型
						MfBusApply mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(appId);
						if(mfBusApply != null){
							Map<String, Object> resultMap = new HashMap<String, Object>();
							resultMap = creditApplyInterfaceFeign.checkCredit(mfBusApply,mfCusType.getBaseType(),mfAssureInfo.getAssureNo());
							if("error".equals((String)resultMap.get("flag"))){
								return resultMap;
							}
						}
					}
				}
				mfAssureInfo = mfAssureInfoFeign.insert(dataMap);
				dataMap.put("pledgeNo", mfAssureInfo.getId());
				dataMap.put("pledgeName", mfAssureInfo.getAssureName());
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SAVE.getMessage());
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
		FormData formassure0002 = formService.getFormData("assure0002");
		getFormValue(formassure0002, getMapByJson(ajaxData));
		MfAssureInfo mfAssureInfoJsp = new MfAssureInfo();
		setObjValue(formassure0002, mfAssureInfoJsp);
		MfAssureInfo mfAssureInfo = mfAssureInfoFeign.getById(mfAssureInfoJsp);
		if (mfAssureInfo != null) {
			try {
				mfAssureInfo = (MfAssureInfo) EntityUtil.reflectionSetVal(mfAssureInfo, mfAssureInfoJsp,
						getMapByJson(ajaxData));
				mfAssureInfoFeign.update(mfAssureInfo);
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
		MfAssureInfo mfAssureInfo = new MfAssureInfo();
		try {
			FormData formassure0002 = formService.getFormData("assure0002");
			getFormValue(formassure0002, getMapByJson(ajaxData));
			if (this.validateFormData(formassure0002)) {
				setObjValue(formassure0002, mfAssureInfo);
				mfAssureInfoFeign.update(mfAssureInfo);
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
	public Map<String, Object> getByIdAjax(String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formassure0002 = formService.getFormData("assure0002");
		MfAssureInfo mfAssureInfo = new MfAssureInfo();
		mfAssureInfo.setId(id);
		mfAssureInfo = mfAssureInfoFeign.getById(mfAssureInfo);
		getObjValue(formassure0002, mfAssureInfo, formData);
		if (mfAssureInfo != null) {
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
	@RequestMapping(value = "/getByIdDetailAjax")
	@ResponseBody
	public Map<String, Object> getByIdDetailAjax(String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();

		if(StringUtil.isEmpty(id)){
			dataMap.put("flag", "error");
			return dataMap;
		}
		MfAssureInfo mfAssureInfo = new MfAssureInfo();
		mfAssureInfo.setId(id);
		mfAssureInfo = mfAssureInfoFeign.getById(mfAssureInfo);
		if (mfAssureInfo != null) {
			if(StringUtil.isNotEmpty(mfAssureInfo.getLongEndDate())&&mfAssureInfo.getLongEndDate().length()==8){
				mfAssureInfo.setLongEndDate(mfAssureInfo.getLongEndDate().substring(0,4)+"-"+mfAssureInfo.getLongEndDate().substring(4,6)+"-"+mfAssureInfo.getLongEndDate().substring(6,8));
			}
			dataMap.put("flag", "success");
			dataMap.put("mfAssureInfo", mfAssureInfo);
		} else {
			dataMap.put("flag", "error");
		}
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
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfAssureInfo mfAssureInfo = new MfAssureInfo();
		mfAssureInfo.setId(id);
		try {
			mfAssureInfoFeign.delete(mfAssureInfo);
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
		FormData formassure0002 = formService.getFormData("assure0002");
		model.addAttribute("formassure0002", formassure0002);
		model.addAttribute("query", "");
		return "/component/assure/MfAssureInfo_Insert";
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
		FormData formassure0001 = formService.getFormData("assure0001");
		getFormValue(formassure0001);
		MfAssureInfo mfAssureInfo = new MfAssureInfo();
		mfAssureInfo.setId(id);
		mfAssureInfo = mfAssureInfoFeign.getById(mfAssureInfo);
		getObjValue(formassure0001, mfAssureInfo);
		model.addAttribute("formassure0001", formassure0001);
		model.addAttribute("query", "");
		return "/component/assure/MfAssureInfo_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String id) throws Exception {
		ActionContext.initialize(request, response);
		MfAssureInfo mfAssureInfo = new MfAssureInfo();
		mfAssureInfo.setId(id);
		mfAssureInfoFeign.delete(mfAssureInfo);
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
		FormData formassure0002 = formService.getFormData("assure0002");
		getFormValue(formassure0002);
		boolean validateFlag = this.validateFormData(formassure0002);
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
		FormData formassure0002 = formService.getFormData("assure0002");
		getFormValue(formassure0002);
		boolean validateFlag = this.validateFormData(formassure0002);
	}

	@RequestMapping(value = "/getMultiBusList")
	public String getMultiBusList(Model model, String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		MfAssureInfo mfAssureInfo = new MfAssureInfo();
		mfAssureInfo.setAssureNo(cusNo);
		mfAssureInfo.setQueryType("1");
		List<MfBusApplyAssureInfo> mfBusApplyAssureInfoList = mfAssureInfoFeign.getMultiBusList(mfAssureInfo);
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr( "tablebusapplyassurelist", "tableTag", mfBusApplyAssureInfoList, null, true);
		model.addAttribute("mfBusApplyAssureInfoList", mfBusApplyAssureInfoList);
		model.addAttribute("mfBusApplyAssureInfoListSize", mfBusApplyAssureInfoList.size());
		model.addAttribute("tableHtml", tableHtml);

		mfAssureInfo = new MfAssureInfo();
		mfAssureInfo.setAssureNo(cusNo);
		mfAssureInfo.setQueryType("2");
		List<MfBusApplyAssureInfo> mfBusApplyAssureInfoProjectList = mfAssureInfoFeign.getMultiBusList(mfAssureInfo);

		if(mfBusApplyAssureInfoProjectList != null && mfBusApplyAssureInfoProjectList.size() >0){
			// 保函金额总和
			Double appProjectAmt = 0d;
			for (MfBusApplyAssureInfo mbp : mfBusApplyAssureInfoProjectList) {
				appProjectAmt = DataUtil.add(appProjectAmt, mbp.getAppAmt(), 20);
			}
			appProjectAmt = DataUtil.retainDigit(appProjectAmt, 2);

			// 担保金额总和
			Double assureAmt = 0d;
			for (MfBusApplyAssureInfo mbp : mfBusApplyAssureInfoProjectList) {
				assureAmt = DataUtil.add(assureAmt, mbp.getAssureAmt(), 20);
			}
			assureAmt = DataUtil.retainDigit(assureAmt, 2);
			MfBusApplyAssureInfo mfBusApplyAssureInfo = new MfBusApplyAssureInfo();
			mfBusApplyAssureInfo.setAssureAmt(assureAmt);
			mfBusApplyAssureInfo.setAppAmt(appProjectAmt);
			mfBusApplyAssureInfo.setBreedName("总金额");
			mfBusApplyAssureInfoProjectList.add(mfBusApplyAssureInfo);
		}

		String projectTableHtml = jtu.getJsonStr( "tablebusapplyassurelist_GCDB", "tableTag", mfBusApplyAssureInfoProjectList, null, true);
		model.addAttribute("mfBusApplyAssureInfoProjectList", mfBusApplyAssureInfoProjectList);
		model.addAttribute("mfBusApplyAssureInfoProjectListSize", mfBusApplyAssureInfoProjectList.size());
		model.addAttribute("projectTableHtml", projectTableHtml);
		model.addAttribute("query", "");
		return "/component/assure/MfAssureInfo_multiBusList";
	}
	@RequestMapping(value = "/getAssureData")
	@ResponseBody
	public Map<String,Object> getAssureData() throws Exception {
		ActionContext.initialize(request, response);
		Map<String,Object> dataMap = new HashMap<String, Object>();
		MfAssureInfo mfAssureInfo = new MfAssureInfo();
		List<MfAssureInfo> mfAssureInfoList = mfAssureInfoFeign.getMfAssureInfoList(mfAssureInfo);
		JSONArray array = new JSONArray();
		if(mfAssureInfoList!=null&&mfAssureInfoList.size()>0){
			for (int i = 0; i < mfAssureInfoList.size(); i++) {
				JSONObject obj = new JSONObject();
				obj.put("id", mfAssureInfoList.get(i).getCusNo());
				obj.put("name", mfAssureInfoList.get(i).getCusName());
				array.add(obj);
			}
			dataMap.put("flag", "success");
			dataMap.put("mfAssureInfoList", mfAssureInfoList);
			dataMap.put("items", array.toString());
		}else{
			dataMap.put("flag", "error");
		}
		return dataMap;
	}
}

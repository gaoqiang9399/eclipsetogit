package app.component.pact.protect.controller;

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

import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.pact.entity.MfBusPact;
import app.component.pact.protect.entity.MfAssetHandleInfo;
import app.component.pact.protect.entity.MfAssetProtectRecord;
import app.component.pact.protect.feign.MfAssetHandleInfoFeign;
import app.util.JsonStrHandling;
import app.util.toolkit.Ipage;

/**
 * Title: MfAssetHandleInfoAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri Aug 11 18:19:17 CST 2017
 **/
@Controller
@RequestMapping("/mfAssetHandleInfo")
public class MfAssetHandleInfoController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfAssetHandleInfoFeign mfAssetHandleInfoFeign;

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
		return "component/pact/protect/MfAssetHandleInfo_List";
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
		MfAssetHandleInfo mfAssetHandleInfo = new MfAssetHandleInfo();
		try {
			mfAssetHandleInfo.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfAssetHandleInfo.setCriteriaList(mfAssetHandleInfo, ajaxData);// 我的筛选
			// mfAssetHandleInfo.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfAssetHandleInfo,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = mfAssetHandleInfoFeign.findByPage(ipage, mfAssetHandleInfo);
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
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String formId = (String) getMapByJson(ajaxData).get("formId");
			FormData formassethandle0001 = formService.getFormData(formId);
			getFormValue(formassethandle0001, getMapByJson(ajaxData));
			if (this.validateFormData(formassethandle0001)) {
				MfAssetHandleInfo mfAssetHandleInfo = new MfAssetHandleInfo();
				setObjValue(formassethandle0001, mfAssetHandleInfo);
				mfAssetHandleInfo = mfAssetHandleInfoFeign.insert(mfAssetHandleInfo);
				String assetHandleType = mfAssetHandleInfo.getHandleType();
				if (BizPubParm.ASSET_HANDLE_TYPE_AUCTION.equals(assetHandleType)) {
					formId = "assethandleauctiondetail";
				} else if (BizPubParm.ASSET_HANDLE_TYPE_LEASE.equals(assetHandleType)) {
					formId = "assethandleleasedetail";
				} else if (BizPubParm.ASSET_HANDLE_TYPE_COOPERATION.equals(assetHandleType)) {
					formId = "assethandlecooperationdetail";
				}else {
				}
				//query = "query";
				FormData formassethandledetail = formService.getFormData(formId);
				getObjValue(formassethandledetail, mfAssetHandleInfo);
				JsonFormUtil jsonFormUtil = new JsonFormUtil();
				String formDetailHtml = jsonFormUtil.getJsonStr(formassethandledetail, "propertySeeTag", "");
				dataMap.put("flag", "success");
				dataMap.put("formDetailHtml", formDetailHtml);
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
		FormData formassethandle0002 = formService.getFormData("assethandle0002");
		getFormValue(formassethandle0002, getMapByJson(ajaxData));
		MfAssetHandleInfo mfAssetHandleInfoJsp = new MfAssetHandleInfo();
		setObjValue(formassethandle0002, mfAssetHandleInfoJsp);
		MfAssetHandleInfo mfAssetHandleInfo = mfAssetHandleInfoFeign.getById(mfAssetHandleInfoJsp);
		if (mfAssetHandleInfo != null) {
			try {
				mfAssetHandleInfo = (MfAssetHandleInfo) EntityUtil.reflectionSetVal(mfAssetHandleInfo,
						mfAssetHandleInfoJsp, getMapByJson(ajaxData));
				mfAssetHandleInfoFeign.update(mfAssetHandleInfo);
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
			FormData formassethandle0002 = formService.getFormData("assethandle0002");
			getFormValue(formassethandle0002, getMapByJson(ajaxData));
			if (this.validateFormData(formassethandle0002)) {
				MfAssetHandleInfo mfAssetHandleInfo = new MfAssetHandleInfo();
				setObjValue(formassethandle0002, mfAssetHandleInfo);
				mfAssetHandleInfoFeign.update(mfAssetHandleInfo);
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
	public Map<String, Object> getByIdAjax(String assetHandleId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formassethandle0002 = formService.getFormData("assethandle0002");
		MfAssetHandleInfo mfAssetHandleInfo = new MfAssetHandleInfo();
		mfAssetHandleInfo.setAssetHandleId(assetHandleId);
		mfAssetHandleInfo = mfAssetHandleInfoFeign.getById(mfAssetHandleInfo);
		getObjValue(formassethandle0002, mfAssetHandleInfo, formData);
		if (mfAssetHandleInfo != null) {
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
	public Map<String, Object> deleteAjax(String assetHandleId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfAssetHandleInfo mfAssetHandleInfo = new MfAssetHandleInfo();
		mfAssetHandleInfo.setAssetHandleId(assetHandleId);
		try {
			mfAssetHandleInfoFeign.delete(mfAssetHandleInfo);
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
	 * @param protectId 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model, String protectId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formassethandle0001 = formService.getFormData("assethandleauction");
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfAssetHandleInfo mfAssetHandleInfo = new MfAssetHandleInfo();
		mfAssetHandleInfo.setProtectId(protectId);
		dataMap = mfAssetHandleInfoFeign.initInputData(mfAssetHandleInfo);
		MfAssetProtectRecord mfAssetProtectRecord = (MfAssetProtectRecord) dataMap.get("mfAssetProtectRecord");
		getObjValue(formassethandle0001, mfAssetProtectRecord);
		model.addAttribute("formassethandle0001", formassethandle0001);
		model.addAttribute("mfAssetHandleInfo", mfAssetHandleInfo);
		model.addAttribute("query", "");
		return "component/pact/protect/MfAssetHandleInfo_Insert";
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
		FormData formassethandle0002 = formService.getFormData("assethandle0002");
		getFormValue(formassethandle0002);
		MfAssetHandleInfo mfAssetHandleInfo = new MfAssetHandleInfo();
		setObjValue(formassethandle0002, mfAssetHandleInfo);
		mfAssetHandleInfoFeign.insert(mfAssetHandleInfo);
		getObjValue(formassethandle0002, mfAssetHandleInfo);
		this.addActionMessage(model, "保存成功");
		List<MfAssetHandleInfo> mfAssetHandleInfoList = (List<MfAssetHandleInfo>) mfAssetHandleInfoFeign
				.findByPage(this.getIpage(), mfAssetHandleInfo).getResult();
		model.addAttribute("formassethandle0002", formassethandle0002);
		model.addAttribute("mfAssetHandleInfo", mfAssetHandleInfo);
		model.addAttribute("mfAssetHandleInfoList", mfAssetHandleInfoList);
		model.addAttribute("query", "");
		return "component/pact/protect/MfAssetHandleInfo_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String assetHandleId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formassethandle0001 = formService.getFormData("assethandle0001");
		getFormValue(formassethandle0001);
		MfAssetHandleInfo mfAssetHandleInfo = new MfAssetHandleInfo();
		mfAssetHandleInfo.setAssetHandleId(assetHandleId);
		mfAssetHandleInfo = mfAssetHandleInfoFeign.getById(mfAssetHandleInfo);
		getObjValue(formassethandle0001, mfAssetHandleInfo);
		model.addAttribute("formassethandle0001", formassethandle0001);
		model.addAttribute("mfAssetHandleInfo", mfAssetHandleInfo);
		model.addAttribute("query", "");
		return "component/pact/protect/MfAssetHandleInfo_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String assetHandleId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfAssetHandleInfo mfAssetHandleInfo = new MfAssetHandleInfo();
		mfAssetHandleInfo.setAssetHandleId(assetHandleId);
		mfAssetHandleInfoFeign.delete(mfAssetHandleInfo);
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
		FormData formassethandle0002 = formService.getFormData("assethandle0002");
		getFormValue(formassethandle0002);
		boolean validateFlag = this.validateFormData(formassethandle0002);
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
		FormData formassethandle0002 = formService.getFormData("assethandle0002");
		getFormValue(formassethandle0002);
		boolean validateFlag = this.validateFormData(formassethandle0002);
	}

	/**
	 * 
	 * 方法描述：
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @param protectId 
	 * @date 2017-8-12 下午7:43:05
	 */
	@RequestMapping(value = "/getAssetHandleFornHtmlAjax")
	@ResponseBody
	public Map<String, Object> getAssetHandleFornHtmlAjax(String assetHandleType, String protectId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfAssetHandleInfo mfAssetHandleInfo = new MfAssetHandleInfo();
		try {
			String formId = "assethandle0001";
			if (BizPubParm.ASSET_HANDLE_TYPE_AUCTION.equals(assetHandleType)) {
				formId = "assethandleauction";
			} else if (BizPubParm.ASSET_HANDLE_TYPE_LEASE.equals(assetHandleType)) {
				formId = "assethandlelease";
			} else if (BizPubParm.ASSET_HANDLE_TYPE_COOPERATION.equals(assetHandleType)) {
				formId = "assethandlecooperation";
			}else {
			}
			FormData formassethandle0001 = formService.getFormData(formId);
			JsonFormUtil jsonFormUtil = new JsonFormUtil();
			mfAssetHandleInfo = new MfAssetHandleInfo();
			mfAssetHandleInfo.setProtectId(protectId);
			dataMap = mfAssetHandleInfoFeign.initInputData(mfAssetHandleInfo);
			MfAssetProtectRecord mfAssetProtectRecord = (MfAssetProtectRecord)JsonStrHandling.handlingStrToBean(dataMap.get("mfAssetProtectRecord"),MfAssetProtectRecord.class);
			if(mfAssetProtectRecord != null){
				
				mfAssetProtectRecord.setHandleType(assetHandleType);
			}
			getObjValue(formassethandle0001, mfAssetProtectRecord);
			String formHtml = jsonFormUtil.getJsonStr(formassethandle0001, "bootstarpTag", "");
			dataMap.put("flag", "success");
			dataMap.put("formId", formId);
			dataMap.put("formHtml", formHtml);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

}

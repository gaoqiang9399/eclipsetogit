package app.component.cus.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.mftcc.util.StringUtil;
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

import app.component.common.EntityUtil;
import app.component.cus.entity.MfCusType;
import app.component.cus.feign.MfCusTypeFeign;
import app.component.nmd.entity.ParmDic;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Title: MfCusTypeAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Mon Jun 05 17:06:11 CST 2017
 **/
@Controller
@RequestMapping("/mfCusType")
public class MfCusTypeController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfCusTypeBo
	@Autowired
	private MfCusTypeFeign mfCusTypeFeign;

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		return "/component/cus/MfCusType_List";
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
		MfCusType mfCusType = new MfCusType();
		try {
			mfCusType.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCusType.setCriteriaList(mfCusType, ajaxData);// 我的筛选
			// mfCusType.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfCusType,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfCusType", mfCusType));
			ipage = mfCusTypeFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
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
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formcustype0001 = formService.getFormData("custype0001");
			getFormValue(formcustype0001, getMapByJson(ajaxData));
			if (this.validateFormData(formcustype0001)) {
				MfCusType mfCusType = new MfCusType();
				setObjValue(formcustype0001, mfCusType);
				dataMap = mfCusTypeFeign.insert(mfCusType);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
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
		FormData formcustype0002 = formService.getFormData("custype0002");
		getFormValue(formcustype0002, getMapByJson(ajaxData));
		MfCusType mfCusTypeJsp = new MfCusType();
		setObjValue(formcustype0002, mfCusTypeJsp);
		MfCusType mfCusType = mfCusTypeFeign.getById(mfCusTypeJsp);
		if (mfCusType != null) {
			try {
				mfCusType = (MfCusType) EntityUtil.reflectionSetVal(mfCusType, mfCusTypeJsp, getMapByJson(ajaxData));
				mfCusTypeFeign.update(mfCusType);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			} catch (Exception e) {
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", "新增失败");
				throw e;
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
		MfCusType mfCusType = new MfCusType();
		try {
			FormData formcustype0002 = formService.getFormData("custype0002");
			getFormValue(formcustype0002, getMapByJson(ajaxData));
			if (this.validateFormData(formcustype0002)) {
				setObjValue(formcustype0002, mfCusType);
				mfCusTypeFeign.update(mfCusType);
                mfCusType = mfCusTypeFeign.getById(mfCusType);
				dataMap.put("flag", "success");
				dataMap.put("typeName", mfCusType.getTypeName());
				dataMap.put("useFlag", mfCusType.getUseFlag());
				dataMap.put("remark", mfCusType.getRemark());
				dataMap.put("typeNo", mfCusType.getTypeNo());
				dataMap.put("msg", "更新成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw e;
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
	public Map<String, Object> getByIdAjax(String ajaxData, String typeNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcustype0002 = formService.getFormData("custype0002");
		MfCusType mfCusType = new MfCusType();
		mfCusType.setTypeNo(typeNo);
		mfCusType = mfCusTypeFeign.getById(mfCusType);
		getObjValue(formcustype0002, mfCusType, formData);
		if (mfCusType != null) {
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
	public Map<String, Object> deleteAjax(String ajaxData, String typeNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusType mfCusType = new MfCusType();
		mfCusType.setTypeNo(typeNo);
		try {
			mfCusTypeFeign.delete(mfCusType);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述： 跳转客户类型新增表单
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author zhs
	 * @date 2017-3-21 上午10:33:42
	 */
	@RequestMapping(value = "/input")
	public String input(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formcustype0001 = formService.getFormData("custype0001");
		JSONObject json = new JSONObject();
		// 客户类型
		List<ParmDic> parmDiclist = (List<ParmDic>) new CodeUtils().getCacheByKeyName("CUS_SUB_TYPE");
		JSONArray subTypeArray = JSONArray.fromObject(parmDiclist);
		for (int i = 0; i < subTypeArray.size(); i++) {
			subTypeArray.getJSONObject(i).put("id", subTypeArray.getJSONObject(i).getString("optCode"));
			subTypeArray.getJSONObject(i).put("name", subTypeArray.getJSONObject(i).getString("optName"));
		}
		json.put("subType", subTypeArray);
		String ajaxData = json.toString();
		model.addAttribute("formcustype0001", formcustype0001);
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("query", "");
		return "/component/cus/MfCusType_Insert";
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
		FormData formcustype0002 = formService.getFormData("custype0002");
		getFormValue(formcustype0002);
		MfCusType mfCusType = new MfCusType();
		setObjValue(formcustype0002, mfCusType);
		mfCusTypeFeign.insert(mfCusType);
		getObjValue(formcustype0002, mfCusType);
		this.addActionMessage(model, "保存成功");
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("mfCusType", mfCusType));
		List<MfCusType> mfCusTypeList = (List<MfCusType>) mfCusTypeFeign.findByPage(ipage).getResult();
		model.addAttribute("formcustype0002", formcustype0002);
		model.addAttribute("mfCusTypeList", mfCusTypeList);
		model.addAttribute("query", "");
		return "/component/cus/MfCusType_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String ajaxData, String typeNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formcustype0002 = formService.getFormData("custype0002");
		getFormValue(formcustype0002);
		MfCusType mfCusType = new MfCusType();
		mfCusType.setTypeNo(typeNo);
		mfCusType = mfCusTypeFeign.getById(mfCusType);
		getObjValue(formcustype0002, mfCusType);
		/*
		 * JSONObject json = new JSONObject(); //客户明细类型 List<ParmDic>
		 * parmDiclist = (List<ParmDic>)new
		 * CodeUtils().getCacheByKeyName("CUS_SUB_TYPE"); JSONArray subTypeArray
		 * = JSONArray.fromObject(parmDiclist); for (int i = 0; i <
		 * subTypeArray.size(); i++) {
		 * subTypeArray.getJSONObject(i).put("id",subTypeArray.getJSONObject(i).
		 * getString("optCode"));
		 * subTypeArray.getJSONObject(i).put("name",subTypeArray.getJSONObject(i
		 * ).getString("optName")); } json.put("subType", subTypeArray);
		 * ajaxData = json.toString();
		 */
		model.addAttribute("formcustype0002", formcustype0002);
		model.addAttribute("query", "");
		model.addAttribute("ajaxData", ajaxData);
		return "/component/cus/MfCusType_Detail";
	}

	@RequestMapping(value = "/getCusTypeSetAjax")
	@ResponseBody
	public Map<String, Object> getCusTypeSetAjax(String typeNo) throws Exception {
		MfCusType mfCusType = new MfCusType();
		mfCusType.setTypeNo(typeNo);
		mfCusType = mfCusTypeFeign.getById(mfCusType);

		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("flag", "success");
		dataMap.put("cusTypeSet", mfCusType.getBaseType());

		return dataMap;
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String typeNo) throws Exception {
		ActionContext.initialize(request, response);
		MfCusType mfCusType = new MfCusType();
		mfCusType.setTypeNo(typeNo);
		mfCusTypeFeign.delete(mfCusType);
		return getListPage(model);
	}

	@RequestMapping(value = "/getUrlAjax")
	@ResponseBody
	public Map<String, Object> getUrlAjax(String cusNo,String cusBaseType,String parms) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusType mfCusType = new MfCusType();
		mfCusType = mfCusTypeFeign.getUrlByCusType(cusNo,cusBaseType,parms);
		if (mfCusType!=null&&StringUtil.isNotEmpty(mfCusType.getUrl())){
			dataMap.put("flag", "success");
			dataMap.put("url", mfCusType.getUrl());
		}else if (mfCusType==null||StringUtil.isEmpty(mfCusType.getUrl())){
			dataMap.put("flag", "none");
		}else {
		}
		return dataMap;
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
		FormData formcustype0002 = formService.getFormData("custype0002");
		getFormValue(formcustype0002);
		boolean validateFlag = this.validateFormData(formcustype0002);
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
		FormData formcustype0002 = formService.getFormData("custype0002");
		getFormValue(formcustype0002);
		boolean validateFlag = this.validateFormData(formcustype0002);
	}

}

package app.component.oa.consumable.controller;

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

import app.base.User;
import app.component.common.EntityUtil;
import app.component.oa.consumable.entity.MfOaCons;
import app.component.oa.consumable.entity.MfOaConsOperate;
import app.component.oa.consumable.feign.MfOaConsOperateFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONArray;

/**
 * Title: MfOaConsOperateAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Sat Dec 24 12:00:15 CST 2016
 **/
@Controller
@RequestMapping("/mfOaConsOperate")
public class MfOaConsOperateController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfOaConsOperateFeign mfOaConsOperateFeign;
	
	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("query", "");
		return "/component/oa/consumable/MfOaConsOperate_List";
	}

	/**
	 * 申领列表打开页面请求
	 * @param model 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getAppListPage")
	public String getAppListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		JSONArray consOperationStateJsonArray = new CodeUtils().getJSONArrayByKeyName("CONS_OPERATE_STATE");
		this.getHttpRequest().setAttribute("consOperationStateJsonArray", consOperationStateJsonArray);
		JSONArray consOperationTypeJsonArray = new CodeUtils().getJSONArrayByKeyName("CONS_OPERATE_TYPE");
		this.getHttpRequest().setAttribute("consOperationTypeJsonArray", consOperationTypeJsonArray);
		model.addAttribute("query", "");
		return "/component/oa/consumable/MfOaConsApp_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/findByPageAjax")
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfOaConsOperate mfOaConsOperate = new MfOaConsOperate();
			mfOaConsOperate.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfOaConsOperate.setCriteriaList(mfOaConsOperate, ajaxData);// 我的筛选
			// this.getRoleConditions(mfOaConsOperate,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfOaConsOperate", mfOaConsOperate));
			ipage = mfOaConsOperateFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
			/**
			 * ipage.setResult(tableHtml); dataMap.put("ipage",ipage); 需要改进的方法
			 * dataMap.put("tableData",tableHtml);
			 */
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/***
	 * 查询所有申请列表数据
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/findAppByPageAjax")
	public Map<String, Object> findAppByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfOaConsOperate mfOaConsOperate = new MfOaConsOperate();
			mfOaConsOperate.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfOaConsOperate.setCustomSorts(ajaxData);// 排序参数
			mfOaConsOperate.setCriteriaList(mfOaConsOperate, ajaxData);// 我的筛选
			// this.getRoleConditions(mfOaConsOperate,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfOaConsOperate", mfOaConsOperate));
			ipage = mfOaConsOperateFeign.findAppByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
			/**
			 * ipage.setResult(tableHtml); dataMap.put("ipage",ipage); 需要改进的方法
			 * dataMap.put("tableData",tableHtml);
			 */
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
	@ResponseBody
	@RequestMapping("/insertAjax")
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formconsoperate0002 = new FormService().getFormData("consoperate0002");
			getFormValue(formconsoperate0002, getMapByJson(ajaxData));
			if (this.validateFormData(formconsoperate0002)) {
				MfOaConsOperate mfOaConsOperate = new MfOaConsOperate();
				setObjValue(formconsoperate0002, mfOaConsOperate);
				mfOaConsOperateFeign.insert(mfOaConsOperate);
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
	@ResponseBody
	@RequestMapping("/updateAjaxByOne")
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formconsoperate0002 = new FormService().getFormData("consoperate0002");
		getFormValue(formconsoperate0002, getMapByJson(ajaxData));
		MfOaConsOperate mfOaConsOperateJsp = new MfOaConsOperate();
		setObjValue(formconsoperate0002, mfOaConsOperateJsp);
		MfOaConsOperate mfOaConsOperate = mfOaConsOperateFeign.getById(mfOaConsOperateJsp);
		if (mfOaConsOperate != null) {
			try {
				mfOaConsOperate = (MfOaConsOperate) EntityUtil.reflectionSetVal(mfOaConsOperate, mfOaConsOperateJsp,
						getMapByJson(ajaxData));
				mfOaConsOperateFeign.update(mfOaConsOperate);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
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
	 * AJAX更新保存
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/updateAjax")
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfOaConsOperate mfOaConsOperate = new MfOaConsOperate();
			FormData formconsoperate0002 = new FormService().getFormData("consoperate0002");
			getFormValue(formconsoperate0002, getMapByJson(ajaxData));
			if (this.validateFormData(formconsoperate0002)) {
				mfOaConsOperate = new MfOaConsOperate();
				Map<String, Object> mapByJson = getMapByJson(ajaxData);
				mfOaConsOperate.setOperateId((String) mapByJson.get("operateId"));
				mfOaConsOperate = mfOaConsOperateFeign.getById(mfOaConsOperate);
				if ("2".equals(mfOaConsOperate.getOperateType())) {// 操作类型为领用，获取办理数量
					String receiveNum = (String) mapByJson.get("receiveNum");
					mfOaConsOperate.setReceiveNum(Integer.parseInt(receiveNum));
					mfOaConsOperate.setOperateState("5");// 已办理
				}
				if ("3".equals(mfOaConsOperate.getOperateType())) {// 操作类型为借用
					mfOaConsOperate.setOperateState("7");// 已归还
				}
				// mfOaConsOperateFeign.update(mfOaConsOperate);
				mfOaConsOperate.setRegNo(User.getRegNo(request));
				mfOaConsOperate.setRegName(User.getRegName(request));
				mfOaConsOperateFeign.update(mfOaConsOperate);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
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
	@ResponseBody
	@RequestMapping("/getByIdAjax")
	public Map<String, Object> getByIdAjax(String operateType,String operateId,String query) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> formData = new HashMap<String, Object>();
		// 根据操作类型动态展示详情表单
		String formNo = "";
		char opType = operateType.charAt(0);
		switch (opType) {
		case '1':// 入库
			formNo = "consput0001";
			break;
		case '2':// 领用
			formNo = "consapp0003";
			break;
		case '3':// 借用
			formNo = "consapp0004";
			break;
		case '4':// 报损
			formNo = "consscrap0002";
			break;
		case '5':// 盘点
			formNo = "conscheck0002";
			break;
		default:
			break;
		}
		FormData formconsoperate0001 = new FormService().getFormData(formNo);
		MfOaConsOperate mfOaConsOperate = new MfOaConsOperate();
		mfOaConsOperate.setOperateId(operateId);
		mfOaConsOperate = mfOaConsOperateFeign.getById(mfOaConsOperate);
		if (mfOaConsOperate != null) {
			// 处理字典项
			CodeUtils codeUtils = new CodeUtils();
			Map<String, String> codeMap = codeUtils.getMapByKeyName("CONS_ADD_TYPE");
			mfOaConsOperate.setAddType(codeMap.get(mfOaConsOperate.getAddType()));
			codeMap = codeUtils.getMapByKeyName("CONS_AMORTIZE_TYPE");
			mfOaConsOperate.setAmortizeType(codeMap.get(mfOaConsOperate.getAmortizeType()));
			codeMap = codeUtils.getMapByKeyName("CONS_OPERATE_STATE");
			mfOaConsOperate.setOperateState(codeMap.get(mfOaConsOperate.getOperateState()));
			codeMap = codeUtils.getMapByKeyName("CONS_OPERATE_TYPE");
			mfOaConsOperate.setOperateType(codeMap.get(mfOaConsOperate.getOperateType()));
			getObjValue(formconsoperate0001, mfOaConsOperate, formData);
			// 处理对应资产信息，字典项等
			MfOaCons mfOaCons = mfOaConsOperate.getMfOaCons();
			codeMap = codeUtils.getMapByKeyName("CONS_UNIT");
			mfOaCons.setUnit(codeMap.get(mfOaCons.getUnit()));
			codeMap = codeUtils.getMapByKeyName("CONS_APP_TYPE");
			mfOaCons.setAppType(codeMap.get(mfOaCons.getAppType()));
			getObjValue(formconsoperate0001, mfOaCons, formData);
			JsonFormUtil jsonFormUtil = new JsonFormUtil();
			String htmlStr = jsonFormUtil.getJsonStr(formconsoperate0001, "bootstarpTag", query);
			dataMap.put("htmlStr", htmlStr);
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
	@ResponseBody
	@RequestMapping("/deleteAjax")
	public Map<String, Object> deleteAjax(String operateId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfOaConsOperate mfOaConsOperate = new MfOaConsOperate();
			mfOaConsOperate.setOperateId(operateId);
			mfOaConsOperateFeign.delete(mfOaConsOperate);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 新增申领页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/inputApp")
	public String inputApp(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormData formconsoperate0002 = new FormService().getFormData("consapp0002");
		MfOaConsOperate mfOaConsOperate = new MfOaConsOperate();
		mfOaConsOperate.setBrNo(User.getOrgNo(request));
		mfOaConsOperate.setBrName(User.getOrgName(request));
		mfOaConsOperate.setOpNo(User.getRegNo(request));
		mfOaConsOperate.setOpName(User.getRegName(request));
		model.addAttribute("formconsoperate0002", formconsoperate0002);
		model.addAttribute("mfOaConsOperate", mfOaConsOperate);
		model.addAttribute("query", "");
		return "/component/oa/consumable/MfOaConsApp_Insert";
	}

	/**
	 * 新增页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/input")
	public String input(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormData formconsoperate0002 = new FormService().getFormData("consapp0002");
		model.addAttribute("formconsoperate0002", formconsoperate0002);
		model.addAttribute("query", "");
		return "/component/oa/consumable/MfOaConsApp_Insert";
	}

	/***
	 * 新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insert")
	public String insert(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormData formconsoperate0002 = new FormService().getFormData("consoperate0002");
		getFormValue(formconsoperate0002);
		MfOaConsOperate mfOaConsOperate = new MfOaConsOperate();
		setObjValue(formconsoperate0002, mfOaConsOperate);
		mfOaConsOperateFeign.insert(mfOaConsOperate);
		getObjValue(formconsoperate0002, mfOaConsOperate);
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("mfOaConsOperate", mfOaConsOperate));
		List<MfOaConsOperate> mfOaConsOperateList = (List<MfOaConsOperate>) mfOaConsOperateFeign.findByPage(ipage).getResult();
		model.addAttribute("mfOaConsOperateList", mfOaConsOperateList);
		model.addAttribute("formconsoperate0002", formconsoperate0002);
		model.addAttribute("mfOaConsOperate", mfOaConsOperate);
		model.addAttribute("query", "");
		return "/component/oa/consumable/MfOaConsOperate_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(String operateType,String operateId,Model model) throws Exception {
		ActionContext.initialize(request, response);
		// 根据操作类型选择展示表单
		String formNo = "";
		char opType = operateType.charAt(0);
		switch (opType) {
		case '2':// 领用
			formNo = "consapp0001";
			break;
		case '3':// 借用
			formNo = "consborrow0001";
			break;
		case '4':// 报损
			formNo = "consscrap0002";
			break;
		default:
			break;
		}
		FormData formconsoperate0001 = new FormService().getFormData(formNo);
		MfOaConsOperate mfOaConsOperate = new MfOaConsOperate();
		mfOaConsOperate.setOperateId(operateId);
		mfOaConsOperate = mfOaConsOperateFeign.getById(mfOaConsOperate);
		if (mfOaConsOperate != null) {
			// 处理字典项
			CodeUtils codeUtils = new CodeUtils();
			Map<String, String> codeMap = codeUtils.getMapByKeyName("CONS_ADD_TYPE");
			mfOaConsOperate.setAddType(codeMap.get(mfOaConsOperate.getAddType()));
			codeMap = codeUtils.getMapByKeyName("CONS_AMORTIZE_TYPE");
			mfOaConsOperate.setAmortizeType(codeMap.get(mfOaConsOperate.getAmortizeType()));
			codeMap = codeUtils.getMapByKeyName("CONS_OPERATE_STATE");
			mfOaConsOperate.setOperateState(codeMap.get(mfOaConsOperate.getOperateState()));
			codeMap = codeUtils.getMapByKeyName("CONS_OPERATE_TYPE");
			mfOaConsOperate.setOperateType(codeMap.get(mfOaConsOperate.getOperateType()));
			getObjValue(formconsoperate0001, mfOaConsOperate);
			// 处理对应资产信息，字典项等
			MfOaCons mfOaCons = mfOaConsOperate.getMfOaCons();
			codeMap = codeUtils.getMapByKeyName("CONS_UNIT");
			mfOaCons.setUnit(codeMap.get(mfOaCons.getUnit()));
			codeMap = codeUtils.getMapByKeyName("CONS_APP_TYPE");
			mfOaCons.setAppType(codeMap.get(mfOaCons.getAppType()));
		}
		getFormValue(formconsoperate0001);
		getObjValue(formconsoperate0001, mfOaConsOperate);
		getObjValue(formconsoperate0001, mfOaConsOperate.getMfOaCons());
		model.addAttribute("formconsoperate0001", formconsoperate0001);
		model.addAttribute("mfOaConsOperate", mfOaConsOperate);
		model.addAttribute("query", "");
		return "/component/oa/consumable/MfOaConsOperate_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	public String delete(String operateId,Model model) throws Exception {
		ActionContext.initialize(request, response);
		MfOaConsOperate mfOaConsOperate = new MfOaConsOperate();
		mfOaConsOperate.setOperateId(operateId);
		mfOaConsOperateFeign.delete(mfOaConsOperate);
		return getListPage(model);
	}

	/**
	 * 新增校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/validateInsert")
	public void validateInsert() throws Exception {
		ActionContext.initialize(request, response);
		FormData formconsoperate0002 = new FormService().getFormData("consoperate0002");
		getFormValue(formconsoperate0002);
		boolean validateFlag = this.validateFormData(formconsoperate0002);
	}

	/**
	 * 修改校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/validateUpdate")
	public void validateUpdate() throws Exception {
		ActionContext.initialize(request, response);
		FormData formconsoperate0002 = new FormService().getFormData("consoperate0002");
		getFormValue(formconsoperate0002);
		boolean validateFlag = this.validateFormData(formconsoperate0002);
	}

}

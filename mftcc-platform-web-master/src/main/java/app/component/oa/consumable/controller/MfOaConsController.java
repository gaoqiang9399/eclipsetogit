package app.component.oa.consumable.controller;

import java.util.ArrayList;
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
import com.google.gson.Gson;

import app.base.User;
import app.component.common.EntityUtil;
import app.component.oa.consumable.entity.MfOaCons;
import app.component.oa.consumable.entity.MfOaConsClass;
import app.component.oa.consumable.entity.MfOaConsOperate;
import app.component.oa.consumable.feign.MfOaConsClassFeign;
import app.component.oa.consumable.feign.MfOaConsFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;
import net.sf.json.JSONArray;

/**
 * Title: MfOaConsAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Sat Dec 24 11:56:30 CST 2016
 **/
@Controller
@RequestMapping("/mfOaCons")
public class MfOaConsController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfOaConsFeign mfOaConsFeign;
	@Autowired
	private MfOaConsClassFeign mfOaConsClassFeign;
	
	
	@RequestMapping("/getConsView")
	public String getConsView(Model model) throws Exception {
		return "/component/oa/consumable/MfOaConsView";
	}

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		JSONArray consAppTypeJsonArray = new CodeUtils().getJSONArrayByKeyName("CONS_APP_TYPE");
		this.getHttpRequest().setAttribute("consAppTypeJsonArray", consAppTypeJsonArray);
		model.addAttribute("query", "");
		return "/component/oa/consumable/MfOaCons_List";
	}

	/**
	 * 列表打开选择页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getSelectListPage")
	public String getSelectListPage(String classNo,Model model) throws Exception {
		ActionContext.initialize(request, response);
		request.setAttribute("classNo", classNo);
		model.addAttribute("query", "");
		return "/component/oa/consumable/MfOaConsSelect_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/findByPageAjax")
	public Map<String, Object> findByPageAjax(String classNo,Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfOaCons mfOaCons = new MfOaCons();
			mfOaCons.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfOaCons.setCustomSorts(ajaxData);// 排序参数
			mfOaCons.setCriteriaList(mfOaCons, ajaxData);// 我的筛选
			// this.getRoleConditions(mfOaCons,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfOaCons", mfOaCons));
			if (classNo == null) {
				ipage = mfOaConsFeign.findByPage(ipage);
			} else {
				mfOaCons.setClassNo(classNo);
				ipage = mfOaConsFeign.findByPageAndClass(ipage);
			}
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
			/**
			 * ipage.setResult(tableHtml); dataMap.put("ipage",ipage); 需要改进的方法
			 * dataMap.put("tableData",tableHtml);
			 */
		} catch (Exception e) {
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
	public Map<String, Object> insertAjax(String ajaxData,String formId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, Object> mapByJson = getMapByJson(ajaxData);
			FormData formconsumable0002 = new FormService().getFormData("consumable0002");
			FormData formconsumable0003 = new FormService().getFormData(formId);
			getFormValue(formconsumable0002, mapByJson);
			getFormValue(formconsumable0003, mapByJson);
			if (this.validateFormData(formconsumable0002) && this.validateFormData(formconsumable0003)) {
				MfOaCons mfOaCons = new MfOaCons();
				MfOaConsOperate mfOaConsOperate = new MfOaConsOperate();
				setObjValue(formconsumable0002, mfOaCons);
				setObjValue(formconsumable0003, mfOaCons);
				setObjValue(formconsumable0002, mfOaConsOperate);
				setObjValue(formconsumable0003, mfOaConsOperate);
				Map<String ,Object> paramMap = new HashMap<String,Object>();
				paramMap.put("mfOaCons", mfOaCons);
				paramMap.put("mfOaConsOperate", mfOaConsOperate);
				// 插入低值易耗品插入一条操作记录到操作表
				mfOaConsFeign.insert(paramMap);
				// mfOaConsOperateBo.insert(mfOaConsOperate);

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
		FormData formconsumable0002 = new FormService().getFormData("consumable0002");
		getFormValue(formconsumable0002, getMapByJson(ajaxData));
		MfOaCons mfOaConsJsp = new MfOaCons();
		setObjValue(formconsumable0002, mfOaConsJsp);
		MfOaCons mfOaCons = mfOaConsFeign.getById(mfOaConsJsp);
		if (mfOaCons != null) {
			try {
				mfOaCons = (MfOaCons) EntityUtil.reflectionSetVal(mfOaCons, mfOaConsJsp, getMapByJson(ajaxData));
				mfOaConsFeign.update(mfOaCons);
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
	 * AJAX更新保存
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/updateAjax")
	public Map<String, Object> updateAjax(String ajaxData,String operateType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		// 将ajaxData转化为Map对象
		Map<String, Object> mapAjax = getMapByJson(ajaxData);
		String formId = (String) mapAjax.get("formId");
		FormData formconsumable0003 = new FormService().getFormData(formId);
		getFormValue(formconsumable0003, mapAjax);
		boolean insertFlag = true;
		try {
			MfOaConsOperate mfOaConsOperate = new MfOaConsOperate();
			MfOaCons mfOaCons = new MfOaCons();
			// 入库操作需要提交两个表单,首先对基本信息表单consumable0002进行验证
			if ("1".equals(operateType)) {
				FormData formconsumable0002 = new FormService().getFormData("consumable0002");
				getFormValue(formconsumable0002, mapAjax);
				if (this.validateFormData(formconsumable0002)) {
					setObjValue(formconsumable0002, mfOaConsOperate);
				} else {
					dataMap.put("flag", "error");
					dataMap.put("msg", this.getFormulavaliErrorMsg());
					insertFlag = false;
				}
			}
			if (insertFlag) {
				if (this.validateFormData(formconsumable0003)) {
					setObjValue(formconsumable0003, mfOaConsOperate);
					mfOaCons.setConsId(mfOaConsOperate.getConsNo());
					// 更新低值易耗品,同时插入一条操作记录
					Map<String ,Object> paramMap = new HashMap<String,Object>();
					paramMap.put("mfOaCons", mfOaCons);
					paramMap.put("mfOaConsOperate", mfOaConsOperate);
					// 插入低值易耗品插入一条操作记录到操作表
					mfOaConsFeign.update(paramMap);
					dataMap.put("flag", "success");
					dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
				} else {
					dataMap.put("flag", "error");
					dataMap.put("msg", this.getFormulavaliErrorMsg());
				}
			}
		} catch (Exception e) {
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
	public Map<String, Object> getByIdAjax(String consId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formconsumable0002 = new FormService().getFormData("consumable0002");
		MfOaCons mfOaCons = new MfOaCons();
		mfOaCons.setConsId(consId);
		mfOaCons = mfOaConsFeign.getById(mfOaCons);
		getObjValue(formconsumable0002, mfOaCons, formData);
		if (mfOaCons != null) {
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}

	/**
	 * AJAX获取查看(入库放大镜)
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/getConsByIdAjax")
	public Map<String, Object> getConsByIdAjax(String consId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> formData = new HashMap<String, Object>();
		MfOaCons mfOaCons = new MfOaCons();
		mfOaCons.setConsId(consId);
		mfOaCons = mfOaConsFeign.getById(mfOaCons);
		// CodeUtils codeUtils = new CodeUtils();
		// Map<String, String> codeMap = codeUtils.getMapByKeyName("CONS_UNIT");
		// mfOaCons.setUnit(codeMap.get(mfOaCons.getUnit()));
		// codeMap = codeUtils.getMapByKeyName("CONS_APP_TYPE");
		// mfOaCons.setAppType(codeMap.get(mfOaCons.getAppType()));
		if (mfOaCons != null) {
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
		}
		dataMap.put("mfOaCons", mfOaCons);
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
	public Map<String, Object> deleteAjax(String consId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfOaCons mfOaCons = new MfOaCons();
			mfOaCons.setConsId(consId);
			mfOaConsFeign.delete(mfOaCons);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			throw e;
		}
		return dataMap;
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
		MfOaCons mfOaCons = new MfOaCons();
		mfOaCons.setBrNo(User.getOrgNo(request));
		mfOaCons.setBrName(User.getOrgName(request));
		mfOaCons.setOpNo(User.getRegNo(request));
		mfOaCons.setOpName(User.getRegName(request));

		MfOaConsClass mfOaConsClass = new MfOaConsClass();
		List<MfOaConsClass> list = mfOaConsClassFeign.getAll(mfOaConsClass);
		List<Map<String, Object>> zTreeList = new ArrayList<Map<String, Object>>();
		for (MfOaConsClass moc : list) {
			Map<String, Object> mp = new HashMap<String, Object>();
			mp.put("id", moc.getClassId());
			if (StringUtil.isEmpty(moc.getSuperClassId())) {
				mp.put("pId", "0");
				mp.put("isParent", true);
			} else {
				mp.put("pId", moc.getSuperClassId());
			}
			mp.put("name", moc.getClassName());
			mp.put("appType", moc.getAppType());
			zTreeList.add(mp);
		}
		String ajaxData = new Gson().toJson(zTreeList);
		FormData formconsumable0002 = new FormService().getFormData("consumable0002");
		FormData formconsumable0003 = new FormService().getFormData("consumable0003");
		FormData formconsumable0004 = new FormService().getFormData("consumable0004");
		getObjValue(formconsumable0002, mfOaCons);
		model.addAttribute("ajaxData",ajaxData );
		model.addAttribute("formconsumable0002",formconsumable0002 );
		model.addAttribute("formconsumable0003",formconsumable0003 );
		model.addAttribute("formconsumable0004",formconsumable0004 );
		model.addAttribute("mfOaCons",mfOaCons );
		model.addAttribute("query","" );
		return "/component/oa/consumable/MfOaCons_Insert";
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
		FormData formconsumable0002 = new FormService().getFormData("consapp0002");
		MfOaCons mfOaCons = new MfOaCons();
		mfOaCons.setBrNo(User.getOrgNo(request));
		mfOaCons.setBrName(User.getOrgName(request));
		mfOaCons.setOpNo(User.getRegNo(request));
		mfOaCons.setOpName(User.getRegName(request));
		getObjValue(formconsumable0002, mfOaCons);
		model.addAttribute("formconsumable0002",formconsumable0002 );
		model.addAttribute("mfOaCons",mfOaCons );
		model.addAttribute("query","" );
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
		FormData formconsumable0002 = new FormService().getFormData("consumable0002");
		getFormValue(formconsumable0002);
		MfOaCons mfOaCons = new MfOaCons();
		setObjValue(formconsumable0002, mfOaCons);
		Map<String ,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("mfOaCons", mfOaCons);
		paramMap.put("mfOaConsOperate", null);
		// 插入低值易耗品插入一条操作记录到操作表
		mfOaConsFeign.update(paramMap);
		getObjValue(formconsumable0002, mfOaCons);
		this.addActionMessage(model, "保存成功");
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("mfOaCons", mfOaCons));
		List<MfOaCons> mfOaConsList = (List<MfOaCons>) mfOaConsFeign.findByPage(ipage).getResult();
		model.addAttribute("formconsumable0002",formconsumable0002 );
		model.addAttribute("mfOaConsList",mfOaConsList );
		model.addAttribute("mfOaCons",mfOaCons );
		model.addAttribute("query","" );
		return "/component/oa/consumable/MfOaCons_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(String consId,Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormData formconsumable0001 = new FormService().getFormData("consumable0001");
		getFormValue(formconsumable0001);
		MfOaCons mfOaCons = new MfOaCons();
		mfOaCons.setConsId(consId);
		mfOaCons = mfOaConsFeign.getById(mfOaCons);
		List<MfOaConsOperate> consOperateList = mfOaCons.getConsOperatesList();
		CodeUtils codeUtils = new CodeUtils();
		Map<String, String> codeMap = codeUtils.getMapByKeyName("CONS_UNIT");
		mfOaCons.setUnit(codeMap.get(mfOaCons.getUnit()));
		codeMap = codeUtils.getMapByKeyName("CONS_APP_TYPE");
		mfOaCons.setAppType(codeMap.get(mfOaCons.getAppType()));
		getObjValue(formconsumable0001, mfOaCons);
		model.addAttribute("mfOaCons",mfOaCons );
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr("tableconsoperate0001", "thirdTableTag",consOperateList, null, true);
		model.addAttribute("tableHtml",tableHtml );
		model.addAttribute("consOperateList",consOperateList );
		model.addAttribute("formconsumable0001",formconsumable0001 );
		model.addAttribute("query","" );
		return "/component/oa/consumable/MfOaCons_Detail";
	}

	/**
	 * ajax请求，根据classNo获取耗品列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/getConsByClassAjax")
	public Map<String, Object> getConsByClassAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		List<MfOaCons> consList1 = new ArrayList<MfOaCons>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfOaCons mfOaCons = new MfOaCons();
		mfOaCons.setClassNo(ajaxData);
		consList1 = mfOaConsFeign.getByClass(mfOaCons);
		if (!consList1.isEmpty()) {
			List<MfOaCons> consList = new ArrayList<MfOaCons>();// 数据字典处理后传到前台
			CodeUtils codeUtils = new CodeUtils();
			Map<String, String> codeMap1 = codeUtils.getMapByKeyName("CONS_UNIT");
			Map<String, String> codeMap2 = codeUtils.getMapByKeyName("CONS_APP_TYPE");
			for (MfOaCons cons : consList1) {
				cons.setUnit(codeMap1.get(cons.getUnit()));
				cons.setAppType(codeMap2.get(cons.getAppType()));
				consList.add(cons);
			}
			dataMap.put("consList", consList);
			dataMap.put("flag", "hasCons");
		} else {
			dataMap.put("flag", "noCons");
		}
		return dataMap;
	}

	/**
	 * 盘点
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/checkById")
	public String checkById(String consId,Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormData formconsumable0001 = new FormService().getFormData("conscheck0001");
		getFormValue(formconsumable0001);
		MfOaCons mfOaCons = new MfOaCons();
		mfOaCons.setConsId(consId);
		mfOaCons = mfOaConsFeign.getById(mfOaCons);
		// 获取当前操作员信息
		mfOaCons.setOpNo(User.getRegNo(request));
		mfOaCons.setOpName(User.getRegName(request));
		mfOaCons.setBrNo(User.getOrgNo(request));
		mfOaCons.setBrName(User.getOrgName(request));

		CodeUtils codeUtils = new CodeUtils();
		Map<String, String> codeMap = codeUtils.getMapByKeyName("CONS_UNIT");
		mfOaCons.setUnit(codeMap.get(mfOaCons.getUnit()));
		getObjValue(formconsumable0001, mfOaCons);
		model.addAttribute("mfOaCons",mfOaCons );
		model.addAttribute("formconsumable0001",formconsumable0001 );
		model.addAttribute("query","" );
		return "/component/oa/consumable/MfOaCons_Check";
	}

	/**
	 * 报损
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/scrapById")
	public String scrapById(String consId,Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormData formconsumable0001 = new FormService().getFormData("consscrap0001");
		getFormValue(formconsumable0001);
		MfOaCons mfOaCons = new MfOaCons();
		mfOaCons.setConsId(consId);
		mfOaCons = mfOaConsFeign.getById(mfOaCons);
		// 获取当前操作员信息
		mfOaCons.setOpNo(User.getRegNo(request));
		mfOaCons.setOpName(User.getRegName(request));
		mfOaCons.setBrNo(User.getOrgNo(request));
		mfOaCons.setBrName(User.getOrgName(request));

		CodeUtils codeUtils = new CodeUtils();
		Map<String, String> codeMap = codeUtils.getMapByKeyName("CONS_UNIT");
		mfOaCons.setUnit(codeMap.get(mfOaCons.getUnit()));
		getObjValue(formconsumable0001, mfOaCons);
		model.addAttribute("formconsumable0001",formconsumable0001 );
		model.addAttribute("mfOaCons",mfOaCons );
		model.addAttribute("query","" );
		return "/component/oa/consumable/MfOaCons_Scrap";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	public String delete(String consId,Model model) throws Exception {
		ActionContext.initialize(request, response);
		MfOaCons mfOaCons = new MfOaCons();
		mfOaCons.setConsId(consId);
		mfOaConsFeign.delete(mfOaCons);
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
		FormData formconsumable0002 = new FormService().getFormData("consumable0002");
		getFormValue(formconsumable0002);
		boolean validateFlag = this.validateFormData(formconsumable0002);
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
		FormData formconsumable0002 = new FormService().getFormData("consumable0002");
		getFormValue(formconsumable0002);
		boolean validateFlag = this.validateFormData(formconsumable0002);
	}

}

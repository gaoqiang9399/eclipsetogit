package app.component.prdct.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.component.collateralinterface.CollateralInterfaceFeign;
import com.core.domain.screen.OptionsList;
import com.core.struts.taglib.JsonFormUtil;
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

import app.component.calc.penalty.entity.MfSysPenaltyMain;
import app.component.calc.penaltyinterface.PenaltyInterfaceFeign;
import app.component.calcinterface.CalcInterfaceFeign;
import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.cusinterface.CusInterfaceFeign;
import app.component.modelinterface.ModelInterfaceFeign;
import app.component.nmd.entity.ParmDic;
import app.component.nmdinterface.NmdInterfaceFeign;
import app.component.pledgeinterface.PledgeInterfaceFeign;
import app.component.prdct.entity.MfSysKind;
import app.component.prdct.feign.MfSysKindFeign;
import app.component.rec.feign.CollectionPlanFeign;
import app.component.sys.entity.SysOrg;
import app.component.sys.entity.SysRole;
import app.component.sysInterface.SysInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Title: MfSysKindAction.java Description:
 *
 * @author:kaifa@dhcc.com.cn
 * @Mon May 23 10:52:43 CST 2016
 **/
@Controller
@RequestMapping("/mfSysKind")
public class MfSysKindController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfSysKindBo
	@Autowired
	private MfSysKindFeign mfSysKindFeign;
	@Autowired
	private CollectionPlanFeign collectionPlanFeign;

	// 调用组件接口
	@Autowired
	private CalcInterfaceFeign calcInterfaceFeign;
	@Autowired
	private NmdInterfaceFeign nmdInterfaceFeign;
	@Autowired
	private ModelInterfaceFeign modelInterfaceFeign;
	@Autowired
	private PledgeInterfaceFeign pledgeInterfaceFeign;
	@Autowired
	private SysInterfaceFeign sysInterfaceFeign;
	@Autowired
	private PenaltyInterfaceFeign penaltyInterfaceFeign;
	@Autowired
	private CusInterfaceFeign cusInterfaceFeign;
	@Autowired
	private CollateralInterfaceFeign collateralInterfaceFeign;

	// 全局变量

	// 异步参数
	public Map<String, String> phaseMap;

	// 表单变量

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
		return "/component/prdct/MfSysKind_List";
	}

	/**
	 * @author czk
	 * @Description: 选多个产品种类的页面 date 2016-12-21
	 */
	@RequestMapping(value = "/getPageForMutiSel")
	public String getPageForMutiSel(Model model, String busModel,String kindNo) throws Exception {
		ActionContext.initialize(request, response);
		MfSysKind mfSysKind = new MfSysKind();
		mfSysKind.setUseFlag("1");
		mfSysKind.setBusModel(busModel);
		List<MfSysKind> mfSysKindList = mfSysKindFeign.getSysKindList(mfSysKind);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("mfSysKindList", mfSysKindList);
		JSONObject jb = JSONObject.fromObject(dataMap);
		dataMap = jb;
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("kindNo", kindNo);
		model.addAttribute("query", "");
		return "/component/prdct/MfSysKind_MutiSel";
	}

	/**
	 *
	 * 方法描述： 根据业务模式展示可供选择的产品种类
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-4-20 下午3:06:54
	 */
	@RequestMapping(value = "/getPageForMutiSelByBusModel")
	public String getPageForMutiSelByBusModel(Model model, String busModel) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("busModel", busModel);
		List<MfSysKind> mfSysKindList = mfSysKindFeign.getSysKindListByBusModel(dataMap);
		dataMap.put("mfSysKindList", mfSysKindList);
		JSONObject jb = JSONObject.fromObject(dataMap);
		dataMap = jb;
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("query", "");
		return "/component/prdct/MfSysKind_MutiSel";
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
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfSysKind mfSysKind = new MfSysKind();
		try {
			mfSysKind.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfSysKind.setCriteriaList(mfSysKind, ajaxData);// 我的筛选
			// this.getRoleConditions(mfSysKind,"1000000001");//记录级权限控制方法
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfSysKind", mfSysKind));
			ipage = mfSysKindFeign.findByPage(ipage);
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
	 * 获取当前登陆用户可使用的产品列表数据查询
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageForConfigAjax")
	@ResponseBody
	public Map<String, Object> findByPageForConfigAjax(String ajaxData, int pageNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfSysKind mfSysKind = new MfSysKind();
		try {
			mfSysKind.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfSysKind.setCriteriaList(mfSysKind, ajaxData);// 我的筛选
			// this.getRoleConditions(mfSysKind,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = mfSysKindFeign.findByPageForConfig(ipage, mfSysKind);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	@RequestMapping(value = "/getByBusModelAjax")
	@ResponseBody
	public Map<String, Object> getByBusModelAjax(String busModel) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfSysKind mfSysKind = new MfSysKind();
		mfSysKind.setBusModel(busModel);
		try {
			List<MfSysKind> mfSysKindList = mfSysKindFeign.getSysKindList(mfSysKind);
			dataMap.put("kindList", mfSysKindList);
			dataMap.put("flag", "success");
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	@RequestMapping(value = "/getAllKindListAjax")
	@ResponseBody
	public Map<String, Object> getAllKindListAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfSysKind mfSysKind = new MfSysKind();
		try {
			List<MfSysKind> mfSysKindList = mfSysKindFeign.getSysKindList(mfSysKind);
			dataMap.put("kindList", mfSysKindList);
			dataMap.put("len", mfSysKindList.size());
			dataMap.put("flag", "success");
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 *
	 * 方法描述： 根据流程标识获得当前流程启用的最大版本的流程编号+版本号
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @param appWorkflowNo
	 * @date 2017-5-17 下午5:33:05
	 */
	@RequestMapping(value = "/getWorkflowIdAjax")
	@ResponseBody
	public Map<String, Object> getWorkflowIdAjax(String ajaxData, String appWorkflowNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String objectId = mfSysKindFeign.getWorkflowId(appWorkflowNo);
			dataMap.put("workflowId", objectId);
			dataMap.put("flag", "success");
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * @author czk
	 * @Description: 获得业务模式的列表 date 2016-8-4
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPageBusModel")
	public String getListPageBusModel(Model model, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		ParmDic parmDic = new ParmDic();
		parmDic.setKeyName("BUS_MODEL");
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<ParmDic> parmDiclist;
		try {
			parmDiclist = (List<ParmDic>) nmdInterfaceFeign.findAllParmDicByKeyName(parmDic);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		model.addAttribute("parmDiclist", parmDiclist);
		model.addAttribute("query", "");
		return "/component/prdct/MfSysBusModel_List";
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
		dataMap = getMapByJson(ajaxData);
		try {
			FormData formsyskind0003 = formService.getFormData("syskindinsert");
			getFormValue(formsyskind0003, getMapByJson(ajaxData));
			if (this.validateFormDataAnchor(formsyskind0003)) {
				MfSysKind mfSysKind = new MfSysKind();
				setObjValue(formsyskind0003, mfSysKind);
				if("1".equals(dataMap.get("addOrCopy").toString())){//产品复制
					String srcKindNo = dataMap.get("srcKindNo").toString();
					mfSysKind = mfSysKindFeign.copyKind(srcKindNo,mfSysKind);
				}else{//新增产品
					mfSysKind = mfSysKindFeign.insert(mfSysKind);
				}
				dataMap.put("mfSysKind", mfSysKind);
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
		FormData formsyskind0002 = formService.getFormData("syskind0002");
		getFormValue(formsyskind0002, getMapByJson(ajaxData));
		MfSysKind mfSysKindJsp = new MfSysKind();
		setObjValue(formsyskind0002, mfSysKindJsp);
		MfSysKind mfSysKind = mfSysKindFeign.getById(mfSysKindJsp);
		if (mfSysKind != null) {
			try {
				mfSysKind = (MfSysKind) EntityUtil.reflectionSetVal(mfSysKind, mfSysKindJsp, getMapByJson(ajaxData));
				mfSysKindFeign.update(mfSysKind);
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
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfSysKind mfSysKind = new MfSysKind();
		try {
			FormData formsyskind0003 = formService.getFormData("syskindedit");
			getFormValue(formsyskind0003, getMapByJson(ajaxData));
			if (this.validateFormData(formsyskind0003)) {
				mfSysKind = new MfSysKind();
				setObjValue(formsyskind0003, mfSysKind);
				mfSysKindFeign.update(mfSysKind);
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
	 *
	 * 方法描述： 更新产品关联的审批流程，业务审批流程、合同审批流程、放款审批流程
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-3-28 上午11:12:14
	 */
	@RequestMapping(value = "/updateWkfAjax")
	@ResponseBody
	public Map<String, Object> updateWkfAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfSysKind mfSysKind = new MfSysKind();
		try {
			dataMap = getMapByJson(ajaxData);
			mfSysKindFeign.updateWkf(dataMap);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw e;
		}
		return dataMap;
	}


	@RequestMapping(value = "/updateAjaxNew")
	@ResponseBody
	public Map<String, Object> updateAjaxNew(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfSysKind mfSysKind = new MfSysKind();
		try {
			FormData formsyskind0003 = formService.getFormData("kindset0002");
			getFormValue(formsyskind0003, getMapByJson(ajaxData));
			if (this.validateFormData(formsyskind0003)) {
				mfSysKind = new MfSysKind();
				setObjValue(formsyskind0003, mfSysKind);
				mfSysKindFeign.update(mfSysKind);
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
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String kindNo,String addOrCopy) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formsyskind0001 = formService.getFormData("syskindinsert");
		MfSysKind mfSysKind = new MfSysKind();
		mfSysKind.setKindNo(kindNo);
		mfSysKind = mfSysKindFeign.getById(mfSysKind);
		getObjValue(formsyskind0001, mfSysKind);

		CodeUtils cu = new CodeUtils();
		//复制产品时，产品模板列表
		List<OptionsList> srcKindList = new ArrayList<OptionsList>();
		List<ParmDic> parmDiclist = (List<ParmDic>) cu.getCacheByKeyName("KIND_NO");
		if(parmDiclist != null && parmDiclist.size()>0) {
			for (ParmDic parmDic : parmDiclist) {
				OptionsList s = new OptionsList();
				s.setOptionLabel(parmDic.getOptName());
				s.setOptionValue(parmDic.getOptCode());
				srcKindList.add(s);
			}
		}
		this.changeFormProperty(formsyskind0001, "srcKindNo", "optionArray", srcKindList);
		this.changeFormProperty(formsyskind0001, "srcKindNo", "initValue", kindNo);
		this.changeFormProperty(formsyskind0001, "addOrCopy", "initValue", addOrCopy);

		JsonFormUtil jsonFormUtil = new JsonFormUtil();
		String htmlStr = jsonFormUtil.getJsonStr(formsyskind0001, "bootstarpTag", "");
		if (mfSysKind != null) {
			dataMap.put("mfSysKind",mfSysKind);
			dataMap.put("htmlStr",htmlStr);
			dataMap.put("flag", "success");
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
	public Map<String, Object> deleteAjax(String kindNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfSysKind mfSysKind = new MfSysKind();
		mfSysKind.setKindNo(kindNo);
		try {
			mfSysKindFeign.delete(mfSysKind);
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
	 * 方法描述：跳转至产品新增页面
	 *
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/input")
	public String input(Model model,String busModel) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		JSONObject json = new JSONObject();
		CodeUtils cu = new CodeUtils();
		// 客户类型
		// JSONArray cusTypeArray =
		// JSONArray.fromObject(cusInterfaceFeign.getAllList());
		List<ParmDic> parmDiclist = (List<ParmDic>) cu.getCacheByKeyName("CUS_TYPE");
		JSONArray cusTypeArray = JSONArray.fromObject(parmDiclist);
		for (int i = 0; i < cusTypeArray.size(); i++) {
			/*cusTypeArray.getJSONObject(i).put("id",cusTypeArray.getJSONObject(i).getString("typeNo"));
			cusTypeArray.getJSONObject(i).put("name",cusTypeArray.getJSONObject(i).getString("typeName"));*/
			cusTypeArray.getJSONObject(i).put("id", cusTypeArray.getJSONObject(i).getString("optCode"));
			cusTypeArray.getJSONObject(i).put("name", cusTypeArray.getJSONObject(i).getString("optName"));
		}
		json.put("cusType", cusTypeArray);

		//子产品
		parmDiclist = (List<ParmDic>) cu.getCacheByKeyName("SUB_KIND_TYPE");
		JSONArray subKindArray = JSONArray.fromObject(parmDiclist);
		for (int i = 0; i < subKindArray.size(); i++) {
			subKindArray.getJSONObject(i).put("id", subKindArray.getJSONObject(i).getString("optCode"));
			subKindArray.getJSONObject(i).put("name", subKindArray.getJSONObject(i).getString("optName"));
		}
		json.put("subKind", subKindArray);

		// 角色
		SysRole sysRole = new SysRole ();
		JSONArray roleArray = JSONArray.fromObject(sysInterfaceFeign.getAllSysRole(sysRole));
		for (int i = 0; i < roleArray.size(); i++) {
			roleArray.getJSONObject(i).put("id", roleArray.getJSONObject(i).getString("roleNo"));
			roleArray.getJSONObject(i).put("name", roleArray.getJSONObject(i).getString("roleName"));
		}
		json.put("role", roleArray);
		// 部门
		SysOrg sysOrg = new SysOrg ();
		JSONArray orgArray = JSONArray.fromObject( sysInterfaceFeign.getAllOrgJson(sysOrg));
		for (int i = 0; i < orgArray.size(); i++) {
			orgArray.getJSONObject(i).remove("iconSkin");
		}
		json.put("org", orgArray);
		// 业务模式
		parmDiclist = (List<ParmDic>) cu.getCacheByKeyName("BUS_MODEL");

		JSONArray busModelArray = JSONArray.fromObject(parmDiclist);
		for (int i = 0; i < busModelArray.size(); i++) {
			busModelArray.getJSONObject(i).put("id", busModelArray.getJSONObject(i).getString("optCode"));
			busModelArray.getJSONObject(i).put("name", busModelArray.getJSONObject(i).getString("optName"));
		}
		json.put("busModel", busModelArray);
		// 贷款投向
		parmDiclist = (List<ParmDic>) cu.getCacheByKeyName("TRADE");
		JSONArray tradeArray = JSONArray.fromObject(parmDiclist);
		for (int i = 0; i < tradeArray.size(); i++) {
			tradeArray.getJSONObject(i).put("id", tradeArray.getJSONObject(i).getString("optCode"));
			tradeArray.getJSONObject(i).put("name", tradeArray.getJSONObject(i).getString("optName"));
		}
		json.put("trade", tradeArray);
		// 业务品种
		parmDiclist = (List<ParmDic>) cu.getCacheByKeyName("BUS_BREED");
		JSONArray breedArray = JSONArray.fromObject(parmDiclist);
		for (int i = 0; i < breedArray.size(); i++) {
			breedArray.getJSONObject(i).put("id", breedArray.getJSONObject(i).getString("optCode"));
			breedArray.getJSONObject(i).put("name", breedArray.getJSONObject(i).getString("optName"));
		}
		json.put("busBreed", breedArray);
		//资产类别
		JSONArray assetClassArray = JSONArray.fromObject(collateralInterfaceFeign.getCollateralClassJson());
		json.put("assetClass", assetClassArray);

		String ajaxData = json.toString();

		//复制产品时，产品模板列表
		List<OptionsList> srcKindList = new ArrayList<OptionsList>();
		parmDiclist = (List<ParmDic>) cu.getCacheByKeyName("KIND_NO");
		if(parmDiclist != null && parmDiclist.size()>0) {
			for (ParmDic parmDic : parmDiclist) {
				OptionsList s = new OptionsList();
				s.setOptionLabel(parmDic.getOptName());
				s.setOptionValue(parmDic.getOptCode());
				srcKindList.add(s);
			}
		}
		FormData formsyskind0001 = formService.getFormData("syskindinsert");
		this.changeFormProperty(formsyskind0001, "srcKindNo", "optionArray", srcKindList);
		model.addAttribute("formsyskind0001", formsyskind0001);
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("query", "");

		return "/component/prdct/MfSysKind_Insert";

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
		FormData formsyskind0002 = formService.getFormData("syskind0002");
		getFormValue(formsyskind0002);
		MfSysKind mfSysKind = new MfSysKind();
		setObjValue(formsyskind0002, mfSysKind);
		mfSysKindFeign.insert(mfSysKind);
		getObjValue(formsyskind0002, mfSysKind);
		this.addActionMessage(model, "保存成功");
		ipage.setParams(this.setIpageParams("mfSysKind", mfSysKind));
		List<MfSysKind> mfSysKindList = (List<MfSysKind>) mfSysKindFeign.findByPage(ipage)
				.getResult();
		model.addAttribute("formsyskind0002", formsyskind0002);
		model.addAttribute("mfSysKindList", mfSysKindList);
		model.addAttribute("query", "");
		return "/component/prdct/MfSysKind_List";
	}

	/**
	 * @author czk
	 * @Description: 动产质押业务新增方法 date 2016-8-4
	 * @return
	 * @throws Exception
	 */

	@RequestMapping(value = "/insert1")
	public String insert1(Model model, String ajaxData,Ipage ipage) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formsyskind0003 = formService.getFormData("syskind0003");
		getFormValue(formsyskind0003);
		MfSysKind mfSysKind = new MfSysKind();
		setObjValue(formsyskind0003, mfSysKind);
		mfSysKindFeign.insert(mfSysKind);
		getObjValue(formsyskind0003, mfSysKind);
		this.addActionMessage(model, "保存成功");
		ipage.setParams(this.setIpageParams("mfSysKind", mfSysKind));
		List<MfSysKind> mfSysKindList = (List<MfSysKind>) mfSysKindFeign.findByPage(ipage)
				.getResult();
		model.addAttribute("formsyskind0003", formsyskind0003);
		model.addAttribute("mfSysKindList", mfSysKindList);
		model.addAttribute("query", "");
		return "/component/prdct/MfSysKind_List";
	}

	/**
	 * 产品设置编辑页面
	 *
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getById")
	public String getById(Model model, String kindNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formsyskind0003 = formService.getFormData("syskindedit");
		getFormValue(formsyskind0003);
		MfSysKind mfSysKind = new MfSysKind();
		mfSysKind.setKindNo(kindNo);
		mfSysKind = mfSysKindFeign.getById(mfSysKind);
		JSONObject json = new JSONObject();
		CodeUtils cu = new CodeUtils();
		// 客户类型
		String newCusType = "";
		Map<String, String> dicMap = cu.getMapByKeyName("CUS_SUB_TYPE");
		String[] cusTypes = mfSysKind.getCusType().split("\\|");
		for (String cusTypeTmp : cusTypes) {
			if (dicMap.containsKey(cusTypeTmp)) {
				newCusType = newCusType + cusTypeTmp + "|";
			}
		}
		mfSysKind.setCusType(newCusType);
		getObjValue(formsyskind0003, mfSysKind);

		// JSONArray cusTypeArray =
		// JSONArray.fromObject(cusInterfaceFeign.getAllList());
		List<ParmDic> parmDiclist = (List<ParmDic>) cu.getCacheByKeyName("CUS_SUB_TYPE");
		JSONArray cusTypeArray = JSONArray.fromObject(parmDiclist);
		for (int i = 0; i < cusTypeArray.size(); i++) {
			// cusTypeArray.getJSONObject(i).put("id",cusTypeArray.getJSONObject(i).getString("typeNo"));
			// cusTypeArray.getJSONObject(i).put("name",cusTypeArray.getJSONObject(i).getString("typeName"));
			cusTypeArray.getJSONObject(i).put("id", cusTypeArray.getJSONObject(i).getString("optCode"));
			cusTypeArray.getJSONObject(i).put("name", cusTypeArray.getJSONObject(i).getString("optName"));
		}
		json.put("cusType", cusTypeArray);

		// 角色
		SysRole sysRole = new SysRole ();
		sysRole.setOpNoType(BizPubParm.OP_NO_TYPE1);
		JSONArray roleArray = JSONArray.fromObject(sysInterfaceFeign.getAllSysRole(sysRole));
		for (int i = 0; i < roleArray.size(); i++) {
			roleArray.getJSONObject(i).put("id", roleArray.getJSONObject(i).getString("roleNo"));
			roleArray.getJSONObject(i).put("name", roleArray.getJSONObject(i).getString("roleName"));
		}
		json.put("role", roleArray);

		// 部门
		SysOrg sysOrg = new SysOrg ();
		sysOrg.setOpNoType(BizPubParm.OP_NO_TYPE1);
		JSONArray orgArray = JSONArray.fromObject( sysInterfaceFeign.getAllOrgJson(sysOrg));;
		for (int i = 0; i < orgArray.size(); i++) {
			orgArray.getJSONObject(i).remove("iconSkin");
		}
		json.put("org", orgArray);

		// 业务模式
		parmDiclist = (List<ParmDic>) cu.getCacheByKeyName("BUS_MODEL");

		JSONArray busModelArray = JSONArray.fromObject(parmDiclist);
		for (int i = 0; i < busModelArray.size(); i++) {
			busModelArray.getJSONObject(i).put("id", busModelArray.getJSONObject(i).getString("optCode"));
			busModelArray.getJSONObject(i).put("name", busModelArray.getJSONObject(i).getString("optName"));
		}
		json.put("busModel", busModelArray);

		// 贷款投向
		parmDiclist = (List<ParmDic>) cu.getCacheByKeyName("TRADE");
		JSONArray tradeArray = JSONArray.fromObject(parmDiclist);
		for (int i = 0; i < tradeArray.size(); i++) {
			tradeArray.getJSONObject(i).put("id", tradeArray.getJSONObject(i).getString("optCode"));
			tradeArray.getJSONObject(i).put("name", tradeArray.getJSONObject(i).getString("optName"));
		}
		json.put("trade", tradeArray);
		String ajaxData = json.toString();

		model.addAttribute("formsyskind0003", formsyskind0003);
		model.addAttribute("formsyskind0003", formsyskind0003);
		model.addAttribute("mfSysKind", mfSysKind);
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("query", "");
		return "/component/prdct/MfSysKind_Detail";
	}

	/**
	 * 查询新
	 *
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")

	@RequestMapping(value = "/getByIdNew")
	public String getByIdNew(Model model, String kindNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formsyskind0003 = formService.getFormData("kindset0002");
		getFormValue(formsyskind0003);
		MfSysKind mfSysKind = new MfSysKind();
		mfSysKind.setKindNo(kindNo);
		mfSysKind = mfSysKindFeign.getById(mfSysKind);
		getObjValue(formsyskind0003, mfSysKind);

		// 角色
		SysRole sysRole = new SysRole ();
		sysRole.setOpNoType(BizPubParm.OP_NO_TYPE1);
		JSONArray roleArray = JSONArray.fromObject(sysInterfaceFeign.getAllSysRole(sysRole));
		for (int i = 0; i < roleArray.size(); i++) {
			roleArray.getJSONObject(i).put("id", roleArray.getJSONObject(i).getString("roleNo"));
			roleArray.getJSONObject(i).put("name", roleArray.getJSONObject(i).getString("roleName"));
		}
		JSONObject json = new JSONObject();
		json.put("role", roleArray);

		// 部门
		SysOrg sysOrg = new SysOrg ();
		sysOrg.setOpNoType(BizPubParm.OP_NO_TYPE1);
		JSONArray orgArray = JSONArray.fromObject( sysInterfaceFeign.getAllOrgJson(sysOrg));
		for (int i = 0; i < orgArray.size(); i++) {
			orgArray.getJSONObject(i).remove("iconSkin");
		}
		json.put("org", orgArray);

		// 业务模式
		ParmDic parmDic = new ParmDic();
		parmDic.setKeyName("BUS_MODEL");
		List<ParmDic> parmDiclist = (List<ParmDic>) nmdInterfaceFeign.findAllParmDicByKeyName(parmDic);
		JSONArray busModelArray = JSONArray.fromObject(parmDiclist);
		for (int i = 0; i < busModelArray.size(); i++) {
			busModelArray.getJSONObject(i).put("id", busModelArray.getJSONObject(i).getString("optCode"));
			busModelArray.getJSONObject(i).put("name", busModelArray.getJSONObject(i).getString("optName"));
		}
		json.put("busModel", busModelArray);

		// 贷款投向
		parmDic.setKeyName("TRADE");
		parmDiclist = (List<ParmDic>) nmdInterfaceFeign.findAllParmDicByKeyName(parmDic);
		JSONArray tradeArray = JSONArray.fromObject(parmDiclist);
		for (int i = 0; i < tradeArray.size(); i++) {
			tradeArray.getJSONObject(i).put("id", tradeArray.getJSONObject(i).getString("optCode"));
			tradeArray.getJSONObject(i).put("name", tradeArray.getJSONObject(i).getString("optName"));
		}
		json.put("trade", tradeArray);
		String ajaxData = json.toString();

		List<SysOrg> orgList = sysInterfaceFeign.getAllOrg();
		String brNoName = mfSysKindFeign.getBrNoName(orgList, mfSysKind);

		model.addAttribute("formsyskind0003", formsyskind0003);
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("brNoName", brNoName);
		model.addAttribute("query", "");
		return "MfSysKind_Detail_New";
	}

	/**
	 * 删除 eturn
	 *
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String kindNo) throws Exception {

		ActionContext.initialize(request, response);
		MfSysKind mfSysKind = new MfSysKind();
		mfSysKind.setKindNo(kindNo);
		mfSysKindFeign.delete(mfSysKind);
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
		FormData formsyskind0002 = formService.getFormData("syskind0002");
		getFormValue(formsyskind0002);
		boolean validateFlag = this.validateFormData(formsyskind0002);
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
		FormData formsyskind0002 = formService.getFormData("syskind0002");
		getFormValue(formsyskind0002);
		boolean validateFlag = this.validateFormData(formsyskind0002);
	}

	/**
	 *
	 * 方法描述： 获取所有可用的产品种类
	 *
	 * @return String
	 * @author zhs
	 * @date 2016-6-1 下午3:03:27
	 */
	@RequestMapping(value = "/getPrdctListByPage")
	public String getPrdctListByPage(Model model, String ajaxData) throws Exception {
		MfSysKind mfSysKind = new MfSysKind();
		mfSysKind.setUseFlag("1");
		Ipage ipage = this.getIpage();
		List<MfSysKind> mfSysKindList = (List<MfSysKind>) mfSysKindFeign.getPrdctListByPage(ipage, mfSysKind)
				.getResult();
		model.addAttribute("mfSysKindList", mfSysKindList);
		model.addAttribute("query", "");
		return "/component/prdct/MfSysKind_prdctList";
	}

	/**
	 *
	 * 方法描述： 跳转产品设置页面
	 *
	 * @return StringmfSysKind
	 * @author 沈浩兵
	 * @date 2017-3-23 下午4:29:02
	 */
	@RequestMapping(value = "/mfKindConfig")
	public String mfKindConfig(Model model) throws Exception {
		ActionContext.initialize(request, response);
		JSONObject json = new JSONObject();
		// 产品列表
		MfSysKind mfSysKind = new MfSysKind();
		List<MfSysKind> mfSysKindList = mfSysKindFeign.getKindListForConfig(mfSysKind);
		JSONArray array = new JSONArray();
		array = JSONArray.fromObject(mfSysKindList);
		json.put("mfSysKindList", array);
		// 产品费用配置信息
		Map<String, Object> dataMap = mfSysKindFeign.getFeeItemMap(mfSysKindList);
		json.put("feeMap", JSONObject.fromObject(dataMap));

		// 业务流程信息
		dataMap = mfSysKindFeign.getBusWkfMap(mfSysKindList);
		json.put("kindNodeMap", JSONObject.fromObject(dataMap.get("kindNodeMap")));
		json.put("kindNodeConfigMap", JSONObject.fromObject(dataMap.get("kindNodeConfigMap")));
		// 审批流程信息
		dataMap = mfSysKindFeign.getApprovalWkfMap(mfSysKindList);
		json.put("approvalWkfMap", JSONObject.fromObject(dataMap));

		/*// 业务模板
		dataMap = mfSysKindFeign.getTemplateModelMap(mfSysKindList);
		json.put("templateMap", JSONObject.fromObject(dataMap));*/
		// 催收配置
		dataMap = collectionPlanFeign.getRecallConfigMap(mfSysKindList);
		json.put("recDataMap", JSONObject.fromObject(dataMap));
		// 要件配置
		dataMap = mfSysKindFeign.getDocConfigMap(mfSysKindList);
		json.put("docMap", JSONObject.fromObject(dataMap));

		// 核算配置
		dataMap = mfSysKindFeign.getCalcConfigMap(mfSysKindList);
		json.put("repayTypeMap", JSONObject.fromObject(dataMap.get("repayTypeMap")));
		// 利率类型
		List<ParmDic> rateTypeList = (List<ParmDic>) new CodeUtils().getCacheByKeyName("RATE_TYPE");
		json.put("rateTypeList", JSONArray.fromObject(rateTypeList));
		// 计息方式
		List<ParmDic> icTypeList = (List<ParmDic>) new CodeUtils().getCacheByKeyName("IC_TYPE");
		json.put("icTypeList", JSONArray.fromObject(icTypeList));
		// 提前还款利息
		List<ParmDic> preRepayIntsTypeList = (List<ParmDic>) new CodeUtils()
				.getCacheByKeyName("PRE_REPAY_INTEREST_ACCOUT_TYPE");
		json.put("preRepayIntsTypeList", JSONArray.fromObject(preRepayIntsTypeList));
		// 利率展示位数
		List<ParmDic> rateDigitsList = (List<ParmDic>) new CodeUtils().getCacheByKeyName("RATE_DECIMAL_DIGITS");
		json.put("rateDigitsList", JSONArray.fromObject(rateDigitsList));
		// 利息收息方式
		List<ParmDic> instCollectTypeList = (List<ParmDic>) new CodeUtils().getCacheByKeyName("INTEREST_COLLECT_TYPE");
		json.put("instCollectTypeList", JSONArray.fromObject(instCollectTypeList));
		// 费用收取方式
		List<ParmDic> feeCollectWayList = (List<ParmDic>) new CodeUtils().getCacheByKeyName("FEE_COLLECT_WAY");
		json.put("feeCollectWayList", JSONArray.fromObject(feeCollectWayList));
		//还款顺序
		Map<String, Object> repayDataMap=mfSysKindFeign.getRepaymentOrderTypeList();
		json.put("repaymentOrderTypeStr", repayDataMap.get("repaymentOrderTypeStr"));
		String ajaxData = json.toString();
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("query", "");
		return "/component/prdct/MfKindConfig";
	}

	/**
	 *
	 * 方法描述： 更新产品配置各模块的启用禁用标志
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author zhs
	 * @date 2017-4-19 上午11:39:43
	 */
	@RequestMapping(value = "/updateUseFlagAjax")
	@ResponseBody
	public Map<String, Object> updateUseFlagAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		MfSysKind mfSysKind = new MfSysKind();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			mfSysKind = (MfSysKind) JSONObject.toBean(jb, MfSysKind.class);
			mfSysKindFeign.update(mfSysKind);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 产品种类:常规设置 高级设置（产品核算配置参数更新）
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateKindCalcConfigAjax")
	@ResponseBody
	public Map<String, Object> updateKindCalcConfigAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfSysKind mfSysKind = new MfSysKind();
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			mfSysKind = (MfSysKind) JSONObject.toBean(jb, MfSysKind.class);
			dataMap = mfSysKindFeign.updateKindCalcConfig(mfSysKind);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 违约金修改
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateMfSysPenaltyAjax")
	@ResponseBody
	public Map<String, Object> updateMfSysPenaltyAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfSysPenaltyMain mfSysPenaltyMain = new MfSysPenaltyMain();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			mfSysPenaltyMain = (MfSysPenaltyMain) JSONObject.toBean(jb, MfSysPenaltyMain.class);
			penaltyInterfaceFeign.updateOrInsert(mfSysPenaltyMain);

			dataMap.put("mfSysPenaltyMain", mfSysPenaltyMain);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 *
	 * 方法描述：产品设置--还款方式
	 *
	 * @return String
	 * @author zhs
	 * @date 2017-7-10 下午2:09:03
	 */
	@RequestMapping(value = "/getKindObjectForSelect")
	public String getKindObjectForSelect(Model model, String kindNo,String keyName) throws Exception {
		ActionContext.initialize(request,response);
		MfSysKind mfSysKind = new MfSysKind();
		mfSysKind.setKindNo(kindNo);
		mfSysKind = mfSysKindFeign.getById(mfSysKind);
		Map<String,Object> dataMap = new HashMap<String,Object>();
		if("TRADE".equals(keyName)){
			String[] objects=mfSysKind.getFincUse().split("\\|");
			for(String type:objects){
				dataMap.put(type, "");
			}
		}else if("ROLE_NO".equals(keyName)){
			String[] objects=mfSysKind.getRoleNo().split("\\|");
			for(String type:objects){
				dataMap.put(type, "");
			}
		}else if("REPAY_TYPE".equals(keyName)){
			String[] objects=mfSysKind.getRepayType().split("\\|");
			for(String type:objects){
				dataMap.put(type, "");
			}
		}else {
		}
		List<ParmDic> fincUseList = (List<ParmDic>) new CodeUtils().getCacheByKeyName(keyName);
		JSONArray jsonArray = JSONArray.fromObject(fincUseList);
		for(int index=0;index<jsonArray.size();index++){
			jsonArray.getJSONObject(index).put("id", jsonArray.getJSONObject(index).getString("optCode"));
			jsonArray.getJSONObject(index).put("name", jsonArray.getJSONObject(index).getString("optName"));
			jsonArray.getJSONObject(index).put("useFlag", "0");
			if(dataMap.containsKey(jsonArray.getJSONObject(index).getString("optCode"))){
				jsonArray.getJSONObject(index).put("useFlag", "1");
			}
		}
		String ajaxData = jsonArray.toString();
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("query", "");
		return "/component/prdct/MfKindNode_List";
	}

	/**
	 * 方法描述： 获取产品下支持的担保方式
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-7-13 下午5:05:27
	 */
	@RequestMapping(value = "/getVouTypeSelectByNoAjax")
	@ResponseBody
	public Map<String, Object> getVouTypeSelectByNoAjax(String kindNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<ParmDic> vouTypeList = (List<ParmDic>) new CodeUtils().getCacheByKeyName("VOU_TYPE");
		JSONArray vouTypeArray = new JSONArray();
		JSONObject object = null;
		MfSysKind mfSysKind = new MfSysKind();
		mfSysKind.setKindNo(kindNo);
		mfSysKind = mfSysKindFeign.getById(mfSysKind);
		String vouType = "";
		if (mfSysKind != null) {
			vouType = mfSysKind.getVouType();
		}
		String vouTypeArr[] = vouType.split("\\|");
		Map<String,String> strMap = new HashMap<String,String>();
		if(vouTypeArr.length>0){
			for(String str:vouTypeArr){
				strMap.put(str,str);
			}
		}
		for (ParmDic dic : vouTypeList) {
			if (mfSysKind != null && strMap.get(dic.getOptCode())==null) {
				continue;
			}
			object = new JSONObject();
			object.put("id", dic.getOptCode());
			object.put("name", dic.getOptName());
			vouTypeArray.add(object);
		}
		dataMap.put("items", vouTypeArray);
		return dataMap;
	}


	@RequestMapping(value = "/getVouTypeOtherSelectByNoAjax")
	@ResponseBody
	public Map<String, Object> getVouTypeOtherSelectByNoAjax(String kindNo,String vouTypeOwner) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<ParmDic> vouTypeList = (List<ParmDic>) new CodeUtils().getCacheByKeyName("VOU_TYPE");
		JSONArray vouTypeArray = new JSONArray();
		JSONObject object = null;
		MfSysKind mfSysKind = new MfSysKind();
		mfSysKind.setKindNo(kindNo);
		mfSysKind = mfSysKindFeign.getById(mfSysKind);
		String vouType = "";
		if (mfSysKind != null) {
			vouType = mfSysKind.getVouType();
		}
		for (ParmDic dic : vouTypeList) {
			if(mfSysKind != null && StringUtil.isNotEmpty(vouType) && vouType.indexOf(dic.getOptCode()) == -1){
				continue;
			}
			if(vouTypeOwner.equals(dic.getOptCode()) || "1".equals(dic.getOptCode())){
				continue;
			}
			object = new JSONObject();
			object.put("id", dic.getOptCode());
			object.put("name", dic.getOptName());
			vouTypeArray.add(object);
		}
		dataMap.put("items", vouTypeArray);
		return dataMap;
	}

	@RequestMapping(value = "/getVouTypeOtherSelectByNoNewAjax")
	@ResponseBody
	public Map<String, Object> getVouTypeOtherSelectByNoNewAjax(String kindNo,String vouTypeOwner) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<ParmDic> vouTypeList = (List<ParmDic>) new CodeUtils().getCacheByKeyName("VOU_TYPE");
		MfSysKind mfSysKind = new MfSysKind();
		mfSysKind.setKindNo(kindNo);
		mfSysKind = mfSysKindFeign.getById(mfSysKind);
		String vouType = "";
		if (mfSysKind != null) {
			vouType = mfSysKind.getVouType();
		}
		List<ParmDic> vouTypeResultList   =  new ArrayList<>();
		for (ParmDic dic : vouTypeList) {
			if(mfSysKind != null && StringUtil.isNotEmpty(vouType) && vouType.indexOf(dic.getOptCode()) == -1){
				continue;
			}
			if("1".equals(dic.getOptCode())){
				continue;
			}
			vouTypeResultList.add(dic);
		}
		dataMap.put("parmDicList",vouTypeResultList);
		return dataMap;
	}

	@RequestMapping(value = "/getByIdAjaxNew")
	@ResponseBody
	public Map<String, Object> getByIdAjaxNew(String kindNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();

		MfSysKind mfSysKind = new MfSysKind();
		mfSysKind.setKindNo(kindNo);
		mfSysKind = mfSysKindFeign.getById(mfSysKind);
		if (mfSysKind != null) {
			dataMap.put("mfSysKind",mfSysKind);
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
		}
		return dataMap;
	}



	/*********************************************************************************************
	 *										新版产品设置 begin
	 **********************************************************************************************/
	/**
	 *
	 * 方法描述： 跳转至产品配置页面
	 * @return
	 * @throws Exception
	 * String
	 * @author zhs
	 * @date 2018-2-24 上午10:32:20
	 */
	@RequestMapping(value = "/toKindConfig")
	public String toKindConfig(Model model) throws Exception{
		ActionContext.initialize(request, response);
		MfSysKind mfSysKind = new MfSysKind();
		List<MfSysKind> mfSysKindList =  mfSysKindFeign.getSysKindList(mfSysKind);
		model.addAttribute("mfSysKindList", mfSysKindList);
		model.addAttribute("query", "");
		return "/component/prdct/MfSysKind_kindConfig";
	}

	/**
	 *
	 * 方法描述：
	 * @return
	 * @throws Exception
	 * String
	 * @author zhs
	 * @date 2018-2-24 上午10:32:15
	 */
	@RequestMapping(value = "/getKindListAjax")
	@ResponseBody
	public Map<String, Object> getKindListAjax(String kindName) throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try{
			MfSysKind mfSysKind = new MfSysKind();
			mfSysKind.setKindName(kindName);//模糊搜索使用
			List<MfSysKind> mfSysKindList =  mfSysKindFeign.getSysKindListForPrdct(mfSysKind);
			dataMap.put("mfSysKindList",mfSysKindList);
			dataMap.put("flag", "success");
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.ERROR_SELECT.getMessage());
			throw e;
		}
		return dataMap;
	}
	/**
	 *
	 * 方法描述： 跳转至产品设置页面
	 * @return
	 * @throws Exception
	 * String
	 * @author zhs
	 * @date 2018-2-6 上午11:12:48
	 */
	@RequestMapping(value = "/getKindConfig")
	public String getKindConfig(Model model,String kindNo) throws Exception{
		ActionContext.initialize(request, response);
		JSONObject json = new JSONObject();
		List<ParmDic> parmDicList  = new CodeUtils().getCacheByKeyName("KIND_CONFIG_TYPE");
		json.put("configTypeList", JSONArray.fromObject(parmDicList));
		String ajaxData = json.toString();
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("configTypeList", JSONArray.fromObject(parmDicList));
		model.addAttribute("kindNo", kindNo);
		return "/component/prdct/MfSysKindConfig";
	}

	/**
	 *
	 * 方法描述： 根据产品编号和配置类型获取产品的某一项配置
	 * @return
	 * @throws Exception
	 * String
	 * @author zhs
	 * @date 2018-2-7 下午5:44:57
	 */
	@RequestMapping(value = "/getConfigByKindNoAjax")
	@ResponseBody
	public Map<String, Object> getConfigByKindNoAjax(String kindNo,String configType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = mfSysKindFeign.getConfigByKindNo(kindNo,configType);
			if(dataMap!=null){
				dataMap.put("flag", "success");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("获取产品配置信息"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_OPERATION.getMessage("获取产品配置信息"));
			throw e;
		}
		return dataMap;
	}


	/**
	 *
	 * 方法描述： 跳转至产品公共配置页面
	 * @return
	 * @throws Exception
	 * String
	 * @author zhs
	 * @date 2018-3-9 下午4:39:26
	 */
	@RequestMapping(value = "/toPubConfig")
	public String toPubConfig(Model model) throws Exception{
		ActionContext.initialize(request, response);
//		JSONObject json = new JSONObject();
//		String configTypeStr ="审批配置-approval,核算配置-calc,费用配置-fee,模板配置-template";
//		List<String> configTypeList =Arrays.asList(configTypeStr.split(","));
//		json.put("configTypeList", JSONArray.fromObject(configTypeList));
//		String ajaxData = json.toString();
//		model.addAttribute("ajaxData", ajaxData);

		JSONObject json = new JSONObject();
		List<ParmDic> parmDicList = new CodeUtils().getCacheByKeyName("PUB_CONFIG_TYPE");
		json.put("configTypeList", JSONArray.fromObject(parmDicList));
		String ajaxData = json.toString();
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("query", "");
		return "/component/prdct/MfSysKind_pubConfig";
	}

	/**
	 *
	 * 方法描述： 获取产品的公共配置信息
	 * @return
	 * @throws Exception
	 * String
	 * @author zhs
	 * @date 2018-2-24 上午10:56:40
	 */
	@RequestMapping(value = "/getKindPubConfigAjax")
	@ResponseBody
	public Map<String, Object> getKindPubConfigAjax(String configType) throws Exception{
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String,Object>();
		try{
			dataMap =  mfSysKindFeign.getKindPubConfig(configType);
			if(dataMap!=null){
				dataMap.put("flag", "success");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("获取产品配置信息"));
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.ERROR_SELECT.getMessage());
			throw e;
		}
		return dataMap;
	}

	/*********************************************************************************************
	 *										新版产品设置 end
	 **********************************************************************************************/
	/**
	 *@描述 获得授信配置信息
	 *@参数
	 *@返回值
	 *@创建人  shenhaobing
	 *@创建时间  2019/4/10
	 *@修改人和其它信息
	 */
	@RequestMapping(value = "/getCreditConfigByKindNoAjax")
	@ResponseBody
	public Map<String, Object> getCreditConfigByKindNoAjax(String kindNo,String creditModel,String configType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = mfSysKindFeign.getCreditConfigByKindNo(kindNo,creditModel,configType);
			if(dataMap!=null){
				dataMap.put("flag", "success");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("获取产品配置信息"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_OPERATION.getMessage("获取产品配置信息"));
			throw e;
		}
		return dataMap;
	}

}

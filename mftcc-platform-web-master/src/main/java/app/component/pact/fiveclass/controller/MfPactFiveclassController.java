package app.component.pact.fiveclass.controller;

import app.base.User;
import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.common.ViewUtil;
import app.component.cus.entity.MfCusCustomer;
import app.component.cusinterface.CusInterfaceFeign;
import app.component.nmd.entity.ParmDic;
import app.component.pact.entity.MfBusFincApp;
import app.component.pact.entity.MfBusPact;
import app.component.pact.feign.MfBusFincAppFeign;
import app.component.pact.feign.MfBusPactFeign;
import app.component.pact.fiveclass.entity.FiveClassAndPact;
import app.component.pact.fiveclass.entity.MfFiveclassSummaryApply;
import app.component.pact.fiveclass.entity.MfPactFiveclass;
import app.component.pact.fiveclass.feign.MfPactFiveclassFeign;
import app.component.prdctinterface.PrdctInterfaceFeign;
import app.component.wkf.entity.Result;
import app.component.wkf.feign.WorkflowDwrFeign;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.tech.upload.FeignSpringFormEncoder;
import app.util.JsonStrHandling;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.MathExtend;
import cn.mftcc.util.StringUtil;
import com.core.domain.screen.FormData;
import com.core.domain.screen.OptionsList;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import com.dhcc.workflow.api.task.Task;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;
import config.YmlConfig;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: MfPactFiveclassAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri Mar 10 13:38:56 CST 2017
 **/
@Controller
@RequestMapping("/mfPactFiveclass")
public class MfPactFiveclassController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfPactFiveclassBo
	@Autowired
	private MfPactFiveclassFeign mfPactFiveclassFeign;
	@Autowired
	private MfBusPactFeign mfBusPactFeign;
	@Autowired
	private CusInterfaceFeign cusInterfaceFeign;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;
	@Autowired
	private MfBusFincAppFeign mfBusFincAppFeign;
	@Autowired
	private PrdctInterfaceFeign prdctInterfaceFeign;
	@Autowired
	private WorkflowDwrFeign workflowDwrFeign;
	@Autowired
	private YmlConfig ymlConfig;
	/**
	 * 批量进行五级分类操作
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/batchFiveclass")
	public Map<String, Object> batchFiveclass(Model model, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);

		// WebServiceResultBean rb = new WebServiceResultBean();
		//
		// rb.setErrorCode("000000");
		// rb.setErrorMessage("success");

		Map<String, Object> dataMap = new HashMap<String, Object>();
		// 先更新五级分类表中的信息
		List<MfBusPact> list = mfBusPactFeign.listAllSync();
		mfPactFiveclassFeign.updateByBatch(list);
		// 再同步未同步到五级分类信息表中的合同
		List<MfBusPact> list2 = mfBusPactFeign.listAllNotSync();
		mfPactFiveclassFeign.insertByBatch(list2);
		dataMap.put("flag", "success");
		dataMap.put("errorCode", "000000");
		dataMap.put("errorMessage", "success");

		return dataMap;
	}

	/**
	 * 人工调整历史列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListHisPage")
	public String getListHisPage(Model model, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("query", "");
		return "/component/pact/fiveclass/MfFiveclassSummaryApply_List";
	}

	/**
	 * 人工调整历史
	 * 
	 * @param fincId
	 * @param pageSize
	 * @param pageNo
	 * @param tableId
	 * @param tableType
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/listHis")
	public Map<String, Object> listHis(Model model, String ajaxData, String fincId, Integer pageSize, Integer pageNo,
			String tableId, String tableType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfPactFiveclass mfPactFiveclass = new MfPactFiveclass();
		mfPactFiveclass.setCustomQuery(ajaxData);// 自定义查询参数赋值
		mfPactFiveclass.setCriteriaList(mfPactFiveclass, ajaxData);// 我的筛选
		mfPactFiveclass.setFincId(fincId);
		Ipage ipage = this.getIpage();
		ipage.setPageSize(pageSize);
		ipage.setPageNo(pageNo);// 异步传页面翻页参数
		ipage = mfPactFiveclassFeign.listHisByFincId(ipage, mfPactFiveclass);
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
		ipage.setResult(tableHtml);
		dataMap.put("ipage", ipage);
		return dataMap;
	}

	/**
	 * 人工调整历史详情
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hisView")
	public String hisView(Model model, String fiveclassId) throws Exception {

		ActionContext.initialize(request, response);
		MfPactFiveclass mfPactFiveclass = new MfPactFiveclass();
		mfPactFiveclass.setFiveclassId(fiveclassId);
		mfPactFiveclass = mfPactFiveclassFeign.getHisById(mfPactFiveclass);
		// 更改登记日期的格式
		String regTime = mfPactFiveclass.getRegTime();
		if (regTime != null && !"".equals(regTime)) {
			mfPactFiveclass.setRegTime(DateUtil.getShowDateTime(regTime));
		}
		// 更改客户经理调整日期的格式
		String classDate = mfPactFiveclass.getClassDate();
		if (classDate != null && !"".equals(classDate)) {
			mfPactFiveclass.setClassDate(DateUtil.getShowDateTime(classDate));
		}
		// 更改公司认定日期的格式
		String confirmDate = mfPactFiveclass.getConfirmDate();
		if (confirmDate != null && !"".equals(confirmDate)) {
			mfPactFiveclass.setConfirmDate(DateUtil.getShowDateTime(confirmDate));
		}
		model.addAttribute("mfPactFiveclass", mfPactFiveclass);
		model.addAttribute("query", "");
		return "/component/pact/fiveclass/MfPactFiveclass_HisView";
	}

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
		return "/component/pact/fiveclass/MfPactFiveclass_List";
	}

	/**
	 * 五级分类与合同列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getFiveClassAndPactListPage")
	public String getFiveClassAndPactListPage(Model model, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		JSONArray classMethodJsonArray = new CodeUtils().getJSONArrayByKeyName("CLASS_METHOD");
		model.addAttribute("classMethodJsonArray", classMethodJsonArray);
		JSONArray appStsJsonArray = new CodeUtils().getJSONArrayByKeyName("APP_STS");
		model.addAttribute("appStsJsonArray", appStsJsonArray);
		JSONArray fiveStsJsonArray = new CodeUtils().getJSONArrayByKeyName("FIVE_STS");
		model.addAttribute("fiveStsJsonArray", fiveStsJsonArray);
		model.addAttribute("query", "");
		return "/component/pact/fiveclass/MFiveclassAndPact_List";
	}
	/**
	 * 五级分类与合同列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getFiveClassAndPactListPageForBatch")
	public String getFiveClassAndPactListPageForBatch(Model model, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		JSONArray classMethodJsonArray = new CodeUtils().getJSONArrayByKeyName("CLASS_METHOD");
		model.addAttribute("classMethodJsonArray", classMethodJsonArray);
		JSONArray appStsJsonArray = new CodeUtils().getJSONArrayByKeyName("APP_STS");
		model.addAttribute("appStsJsonArray", appStsJsonArray);
		JSONArray fiveStsJsonArray = new CodeUtils().getJSONArrayByKeyName("FIVE_STS");
		model.addAttribute("fiveStsJsonArray", fiveStsJsonArray);
		model.addAttribute("query", "");
		return "/component/pact/fiveclass/MFiveclassAndPact_ListForBatch";
	}
	/**
	 * 五级分类暂存列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/fiveclassStorage")
	@ResponseBody
	public Map<String, Object> fiveclassStorage(String ajaxData)  {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<MfPactFiveclass> list = new ArrayList<MfPactFiveclass>();
		JSONArray json = JSONArray.fromObject(ajaxData);
		for (int i = 0; i < json.size(); i++) {
			MfPactFiveclass mfPactFiveclass = JsonStrHandling.handlingStrToBean(json.get(i), MfPactFiveclass.class);
			list.add(mfPactFiveclass);
		}
		try {
			mfPactFiveclassFeign.fiveclassStorage(list);
			dataMap.put("flag", "success");
			dataMap.put("msg", "暂存成功");
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "暂存失败");
			e.printStackTrace();
		}
		return dataMap;
	}
	/**
	 * 加载五级分类状态
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getFiveclassStsAjax")
	@ResponseBody
	public Map<String, Object> getFiveclassStsAjax(String fincId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfPactFiveclass mfPactFiveclass = new MfPactFiveclass();
		mfPactFiveclass.setFincId(fincId);
		mfPactFiveclass = mfPactFiveclassFeign.getByFincId(mfPactFiveclass);
		if (mfPactFiveclass != null) {
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
		}
		dataMap.put("mfPactFiveclass", mfPactFiveclass);
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
			String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfPactFiveclass mfPactFiveclass = new MfPactFiveclass();
		try {
			mfPactFiveclass.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfPactFiveclass.setCriteriaList(mfPactFiveclass, ajaxData);// 我的筛选
			// this.getRoleConditions(mfPactFiveclass,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = mfPactFiveclassFeign.findByPage(ipage, mfPactFiveclass);
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
	 * 列表数据查询五级分类和合同信息
	 * 
	 * @param tableId
	 * @param tableType
	 * @param pageSize
	 * @param pageNo
	 * @param ipage 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findFiveClassAndPactByPageAjax")
	@ResponseBody
	public Map<String, Object> findFiveClassAndPactByPageAjax(String ajaxData, String tableId, String tableType,
			Integer pageSize, Integer pageNo, Ipage ipage) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FiveClassAndPact fiveClassAndPact = new FiveClassAndPact();
		try {
			fiveClassAndPact.setCustomQuery(ajaxData);// 自定义查询参数赋值
			fiveClassAndPact.setCriteriaList(fiveClassAndPact, ajaxData);// 我的筛选
			fiveClassAndPact.setCustomSorts(ajaxData);
			// this.getRoleConditions(mfPactFiveclass,"1000000001");//记录级权限控制方法
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setParams(this.setIpageParams("fiveClassAndPact", fiveClassAndPact));
			// 自定义查询Bo方法
			ipage = mfPactFiveclassFeign.findFiveClassAndPactByPage(ipage);
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
	 * 人工认定列表数据查询五级分类和合同信息
	 * 
	 * @param tableId
	 * @param tableType
	 * @param pageSize
	 * @param pageNo
	 * @param ipage 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findFiveClassAndPactByPageAjaxForBatch")
	@ResponseBody
	public Map<String, Object> findFiveClassAndPactByPageAjaxForBatch(String ajaxData, String tableId, String tableType,
			Integer pageSize, Integer pageNo, Ipage ipage) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FiveClassAndPact fiveClassAndPact = new FiveClassAndPact();
		try {
			fiveClassAndPact.setCustomQuery(ajaxData);// 自定义查询参数赋值
			fiveClassAndPact.setCriteriaList(fiveClassAndPact, ajaxData);// 我的筛选
			fiveClassAndPact.setCustomSorts(ajaxData);
			// this.getRoleConditions(mfPactFiveclass,"1000000001");//记录级权限控制方法
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setParams(this.setIpageParams("fiveClassAndPact", fiveClassAndPact));
			// 自定义查询Bo方法
			ipage = mfPactFiveclassFeign.findFiveClassAndPactByPageForBatch(ipage);
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
	 * 人工手动首次分类
	 * 
	 * @param pactId
	 * @param fincId
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData, String pactId, String fincId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formfiveclass0002 = formService.getFormData("fiveclass0002");
			getFormValue(formfiveclass0002, getMapByJson(ajaxData));
			if (this.validateFormData(formfiveclass0002)) {
				MfPactFiveclass mfPactFiveclass = new MfPactFiveclass();
				setObjValue(formfiveclass0002, mfPactFiveclass);
				mfPactFiveclass.setPactId(pactId);
				mfPactFiveclass.setFincId(fincId);
				mfPactFiveclass = mfPactFiveclassFeign.insert(mfPactFiveclass);
				dataMap.put("flag", "success");
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("userRole", mfPactFiveclass.getApproveNodeName());
				paramMap.put("opNo", mfPactFiveclass.getApprovePartName());
				if (!"".equals(mfPactFiveclass.getApproveNodeName()) && mfPactFiveclass.getApproveNodeName() != null) {
					dataMap.put("msg", MessageEnum.SUCCEED_APPROVAL.getMessage(paramMap));
				} else {
					dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("分类"));
				}

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
		FormData formfiveclass0002 = formService.getFormData("fiveclass0002");
		getFormValue(formfiveclass0002, getMapByJson(ajaxData));
		MfPactFiveclass mfPactFiveclassJsp = new MfPactFiveclass();
		setObjValue(formfiveclass0002, mfPactFiveclassJsp);
		MfPactFiveclass mfPactFiveclass = mfPactFiveclassFeign.getById(mfPactFiveclassJsp);
		if (mfPactFiveclass != null) {
			try {
				mfPactFiveclass = (MfPactFiveclass) EntityUtil.reflectionSetVal(mfPactFiveclass, mfPactFiveclassJsp,
						getMapByJson(ajaxData));
				mfPactFiveclassFeign.update(mfPactFiveclass);
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
	 * 客户经理调整
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData, String pactId, String fincId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formfiveclass0002 = formService.getFormData("fiveclass0002");
			getFormValue(formfiveclass0002, getMapByJson(ajaxData));
			if (this.validateFormData(formfiveclass0002)) {
				MfPactFiveclass mfPactFiveclass = new MfPactFiveclass();
				setObjValue(formfiveclass0002, mfPactFiveclass);
				mfPactFiveclass.setPactId(pactId);
				mfPactFiveclass.setFincId(fincId);
				mfPactFiveclass = mfPactFiveclassFeign.update(mfPactFiveclass);
				dataMap.put("flag", "success");
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("userRole", mfPactFiveclass.getApproveNodeName());
				paramMap.put("opNo", mfPactFiveclass.getApprovePartName());
				if (!"".equals(mfPactFiveclass.getApproveNodeName()) && mfPactFiveclass.getApproveNodeName() != null) {
					dataMap.put("msg", MessageEnum.SUCCEED_APPROVAL.getMessage(paramMap));
				} else {
					dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("调整"));
				}
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
	 * AJAX更新保存
	 * 
	 * @return
	 * @throws Exception
	 * 
	 * @RequestMapping(value = "/processSubmitAjax")
	 * @ResponseBody public Map<String, Object> processSubmitAjax(String ajaxData)
	 *               throws Exception { FormService formService = new FormService();
	 *               ActionContext.initialize(request, response); Map<String,
	 *               Object> dataMap = new HashMap<String, Object>();
	 *               MfPactFiveclass mfPactFiveclass = new MfPactFiveclass(); try{
	 *               FormData formfiveclass0002 =
	 *               formService.getFormData("fiveclass0002");
	 *               getFormValue(formfiveclass0002, getMapByJson(ajaxData));
	 *               if(this.validateFormData(formfiveclass0002)){ MfPactFiveclass
	 *               mfPactFiveclass = new MfPactFiveclass();
	 *               setObjValue(formfiveclass0002, mfPactFiveclass);
	 *               mfPactFiveclass = mfPactFiveclassFeign.update(mfPactFiveclass);
	 *               dataMap.put("flag", "success");
	 *               if(!"".equals(mfPactFiveclass.getApproveNodeName()) &&
	 *               mfPactFiveclass.getApproveNodeName() != null){
	 *               dataMap.put("msg", "审批提交到
	 *               "+mfPactFiveclass.getApproveNodeName()+" 岗位下的
	 *               "+mfPactFiveclass.getApprovePartName()+" 进行审批"); }else{
	 *               dataMap.put("msg", "修改成功"); } }else{ dataMap.put("flag",
	 *               "error"); dataMap.put("msg",this.getFormulavaliErrorMsg()); }
	 *               }catch(Exception e){ e.printStackTrace(); dataMap.put("flag",
	 *               "error");
	 *               dataMap.put("msg",MessageEnum.FAILED_UPDATE.getMessage());
	 *               throw e; } return dataMap; }
	 */

	/**
	 * AJAX获取查看
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String fiveclassId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formfiveclass0002 = formService.getFormData("fiveclass0002");
		MfPactFiveclass mfPactFiveclass = new MfPactFiveclass();
		mfPactFiveclass.setFiveclassId(fiveclassId);
		mfPactFiveclass = mfPactFiveclassFeign.getById(mfPactFiveclass);
		getObjValue(formfiveclass0002, mfPactFiveclass, formData);
		if (mfPactFiveclass != null) {
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
	public Map<String, Object> deleteAjax(String fiveclassId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfPactFiveclass mfPactFiveclass = new MfPactFiveclass();
		mfPactFiveclass.setFiveclassId(fiveclassId);
		try {
			mfPactFiveclassFeign.delete(mfPactFiveclass);
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
	 * 新增页面
	 * 
	 * @param pactId
	 * @param fincId
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model, String pactId, String fincId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);

		MfBusPact mfBusPact = new MfBusPact();
		mfBusPact.setPactId(pactId);
		mfBusPact = mfBusPactFeign.getById(mfBusPact);
		// 金额格式化
		mfBusPact.setFincAmt(MathExtend.moneyStr(mfBusPact.getPactAmt()));
		// 担保方式
		mfBusPact.setVouType(prdctInterfaceFeign.getTypeNameByTypeValue(mfBusPact.getVouType(), "VOU_TYPE"));

		MfPactFiveclass mfPactFiveclass = new MfPactFiveclass();
		MfBusFincApp mfBusFincApp = new MfBusFincApp();
		mfBusFincApp.setFincId(fincId);
		mfBusFincApp = mfBusFincAppFeign.getById(mfBusFincApp);
		CodeUtils cu = new CodeUtils();
		mfBusFincApp.setRepayType(cu.getMapByKeyName("REPAY_TYPE").get(mfBusFincApp.getRepayType()));
		String endDate = mfPactFiveclassFeign.getEndDateByFincId(fincId);
		if (endDate != null && "".equals(endDate)) {
			endDate = DateUtil.getStr(endDate);
			int result = DateUtil.getIntervalDays(endDate, DateUtil.getStr(DateUtil.getDate()));
			mfPactFiveclass.setOverDate(result);
		}
		FormData formfiveclass0002 = formService.getFormData("fiveclass0002");
		getFormValue(formfiveclass0002);
		mfPactFiveclass.setClassDate(DateUtil.getDate());
		mfPactFiveclass.setRegTime(DateUtil.getDate());
		getObjValue(formfiveclass0002, mfPactFiveclass);
		mfPactFiveclass.setFiveclassShow(cu.getMapByKeyName("FIVE_STS").get(mfPactFiveclass.getFiveclass()));
		mfPactFiveclass
				.setSystemFiveclassShow(cu.getMapByKeyName("FIVE_STS").get(mfPactFiveclass.getSystemFiveclass()));
		String cusNo = mfBusPact.getCusNo();
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);
		model.addAttribute("formfiveclass0002", formfiveclass0002);
		model.addAttribute("mfCusCustomer", mfCusCustomer);
		model.addAttribute("mfBusPact", mfBusPact);
		model.addAttribute("mfBusFincApp", mfBusFincApp);
		model.addAttribute("pactId", pactId);
		model.addAttribute("fincId", fincId);
		model.addAttribute("query", "");
		return "/component/pact/fiveclass/MfPactFiveclass_Insert";
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
		FormData formfiveclass0002 = formService.getFormData("fiveclass0002");
		getFormValue(formfiveclass0002);
		MfPactFiveclass mfPactFiveclass = new MfPactFiveclass();
		setObjValue(formfiveclass0002, mfPactFiveclass);
		mfPactFiveclassFeign.insert(mfPactFiveclass);
		getObjValue(formfiveclass0002, mfPactFiveclass);
		this.addActionMessage(model, "保存成功");
		List<MfPactFiveclass> mfPactFiveclassList = (List<MfPactFiveclass>) mfPactFiveclassFeign
				.findByPage(this.getIpage(), mfPactFiveclass).getResult();
		model.addAttribute("formfiveclass0002", formfiveclass0002);
		model.addAttribute("mfPactFiveclass", mfPactFiveclass);
		model.addAttribute("mfPactFiveclassList", mfPactFiveclassList);
		model.addAttribute("query", "");
		return "/component/pact/fiveclass/MfPactFiveclass_Insert";
	}

	/**
	 * 查询(客户经理调整页面)
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String fiveclassId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formfiveclass0002 = formService.getFormData("fiveclass0002");
		getFormValue(formfiveclass0002);
		MfPactFiveclass mfPactFiveclass = new MfPactFiveclass();
		mfPactFiveclass.setFiveclassId(fiveclassId);
		mfPactFiveclass = mfPactFiveclassFeign.getById(mfPactFiveclass);
		String classDate = mfPactFiveclass.getClassDate();
		if (classDate == null || "".equals(classDate)) {
			mfPactFiveclass.setClassDate(DateUtil.getDate());
		}
		getObjValue(formfiveclass0002, mfPactFiveclass);
		String pactId = mfPactFiveclass.getPactId();
		String fincId = mfPactFiveclass.getFincId();
		String cusNo = mfPactFiveclass.getCusNo();
		MfBusPact mfBusPact = new MfBusPact();
		mfBusPact.setPactId(pactId);
		mfBusPact = mfBusPactFeign.getById(mfBusPact);
		// 担保方式
		mfBusPact.setVouType(prdctInterfaceFeign.getTypeNameByTypeValue(mfBusPact.getVouType(), "VOU_TYPE"));

		String endDate = mfPactFiveclassFeign.getEndDateByFincId(fincId);
		if (StringUtil.isNotEmpty(endDate)) {
			endDate = DateUtil.getStr(endDate);
			int result = DateUtil.getIntervalDays(endDate, DateUtil.getStr(DateUtil.getDate()));
			mfPactFiveclass.setOverDate(result);
		}
		CodeUtils cu = new CodeUtils();
		mfPactFiveclass.setFiveclassShow(cu.getMapByKeyName("FIVE_STS").get(mfPactFiveclass.getFiveclass()));
		mfPactFiveclass
				.setSystemFiveclassShow(cu.getMapByKeyName("FIVE_STS").get(mfPactFiveclass.getSystemFiveclass()));
		// 金额格式化
		mfBusPact.setFincAmt(MathExtend.moneyStr(mfBusPact.getPactAmt()));
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);
		MfBusFincApp mfBusFincApp = new MfBusFincApp();
		mfBusFincApp.setFincId(fincId);
		mfBusFincApp = mfBusFincAppFeign.getById(mfBusFincApp);
		mfBusFincApp.setRepayType(cu.getMapByKeyName("REPAY_TYPE").get(mfBusFincApp.getRepayType()));
		model.addAttribute("formfiveclass0002", formfiveclass0002);
		model.addAttribute("mfBusPact", mfBusPact);
		model.addAttribute("mfCusCustomer", mfCusCustomer);
		model.addAttribute("mfBusFincApp", mfBusFincApp);
		model.addAttribute("mfPactFiveclass", mfPactFiveclass);
		model.addAttribute("query", "");
		return "/component/pact/fiveclass/MfPactFiveclass_Detail";
	}

	/**
	 * 查询(公司认定页面)
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getFiveclassById")
	public String getFiveclassById(Model model, String fiveclassId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formfiveclass0004 = formService.getFormData("fiveclass0004");
		getFormValue(formfiveclass0004);
		MfPactFiveclass mfPactFiveclass = new MfPactFiveclass();
		mfPactFiveclass.setFiveclassId(fiveclassId);
		mfPactFiveclass = mfPactFiveclassFeign.getById(mfPactFiveclass);
		getObjValue(formfiveclass0004, mfPactFiveclass);
		String pactId = mfPactFiveclass.getPactId();
		String cusNo = mfPactFiveclass.getCusNo();
		MfBusPact mfBusPact = new MfBusPact();
		mfBusPact.setPactId(pactId);
		mfBusPact = mfBusPactFeign.getById(mfBusPact);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);
		model.addAttribute("formfiveclass0004", formfiveclass0004);
		model.addAttribute("mfCusCustomer", mfCusCustomer);
		model.addAttribute("mfPactFiveclass", mfPactFiveclass);
		model.addAttribute("mfBusPact", mfBusPact);
		model.addAttribute("query", "");
		return "/component/pact/fiveclass/MfPactFiveclass_Confirm";
	}

	/**
	 * 公司认定
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/confirmAjax")
	@ResponseBody
	public Map<String, Object> confirmAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formfiveclass0004 = formService.getFormData("fiveclass0004");
			getFormValue(formfiveclass0004, getMapByJson(ajaxData));
			if (this.validateFormData(formfiveclass0004)) {
				MfPactFiveclass mfPactFiveclass = new MfPactFiveclass();
				setObjValue(formfiveclass0004, mfPactFiveclass);
				mfPactFiveclass.setFiveclassSts("5");// 公司已认定
				mfPactFiveclassFeign.confirmAjax(mfPactFiveclass);
				dataMap.put("flag", "success");
				dataMap.put("msg", "认定成功");
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
	 * 根据合同号查询五级分类信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByPactId")
	public String getByPactId(Model model, String fincId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formfiveclass0002 = formService.getFormData("fiveclass0002");
		getFormValue(formfiveclass0002);
		MfPactFiveclass mfPactFiveclass = new MfPactFiveclass();
		mfPactFiveclass.setFincId(fincId);
		mfPactFiveclass = mfPactFiveclassFeign.getByFincId(mfPactFiveclass);
		getObjValue(formfiveclass0002, mfPactFiveclass);
		model.addAttribute("formfiveclass0002", formfiveclass0002);
		model.addAttribute("mfPactFiveclass", mfPactFiveclass);
		model.addAttribute("query", "");
		return "/component/pact/fiveclass/MfPactFiveclass_Detail";
	}

	/**
	 * 根据id查询五级分类信息
	 * 
	 * @param activityType
	 * 
	 * @return
	 * @throws Exception
	 * 
	 * @RequestMapping(value = "/getFiveclass") public String getFiveclass(Model
	 *                       model, String ajaxData) throws Exception{ FormService
	 *                       formService = new FormService();
	 *                       ActionContext.initialize(request,response); FormData
	 *                       formfiveclassapp0001 =
	 *                       formService.getFormData("fiveclassapp0001");
	 *                       getFormValue(formfiveclassapp0001); MfPactFiveclass
	 *                       mfPactFiveclass = new MfPactFiveclass();
	 *                       mfPactFiveclass.setFiveclassId(fiveclassId);
	 *                       mfPactFiveclass =
	 *                       mfPactFiveclassFeign.getById(mfPactFiveclass);
	 *                       getObjValue(formfiveclassapp0001, mfPactFiveclass);
	 *                       String pactId = mfPactFiveclass.getPactId(); String
	 *                       cusNo = mfPactFiveclass.getCusNo(); mfBusPact = new
	 *                       MfBusPact(); mfBusPact.setPactId(pactId); mfBusPact =
	 *                       mfBusPactFeign.getById(mfBusPact); MfCusCustomer
	 *                       mfCusCustomer = new MfCusCustomer();
	 *                       mfCusCustomer.setCusNo(cusNo); mfCusCustomer =
	 *                       cusInterfaceFeign.getById(mfCusCustomer);
	 *                       model.addAttribute("formfiveclass0001",
	 *                       formfiveclass0001); model.addAttribute("query", "");
	 *                       return "MfPactFiveclass_View"; }
	 */

	@RequestMapping(value = "/getViewPoint")
	public String getViewPoint(Model model, String fiveclassId, String activityType, String hideOpinionType) throws Exception {

		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formfiveclassapp0001 = formService.getFormData("fiveclassapp0001");
		getFormValue(formfiveclassapp0001);
		MfPactFiveclass mfPactFiveclass = new MfPactFiveclass();
		mfPactFiveclass.setFiveclassId(fiveclassId);
		mfPactFiveclass = mfPactFiveclassFeign.getById(mfPactFiveclass);
		getObjValue(formfiveclassapp0001, mfPactFiveclass);
		String pactId = mfPactFiveclass.getPactId();
		String cusNo = mfPactFiveclass.getCusNo();
		MfBusPact mfBusPact = new MfBusPact();
		mfBusPact.setPactId(pactId);
		mfBusPact = mfBusPactFeign.getById(mfBusPact);
		if(StringUtil.isNotEmpty(mfPactFiveclass.getFincId())){
			String endDate = mfPactFiveclassFeign.getEndDateByFincId(mfPactFiveclass.getFincId());
			if (StringUtil.isNotEmpty(endDate)) {
				endDate = DateUtil.getStr(endDate);
				int result = DateUtil.getIntervalDays(endDate, DateUtil.getStr(DateUtil.getDate()));
				mfPactFiveclass.setOverDate(result);
			}
		}
		// 金额格式化
		mfBusPact.setFincAmt(MathExtend.moneyStr(mfBusPact.getPactAmt()));

		CodeUtils codeUtils = new CodeUtils();
		Map<String, ParmDic> repayTypeMap = new CodeUtils().getMapObjByKeyName("REPAY_TYPE");
		//还款方式
		String repayTypeStr = "";
		if (mfPactFiveclass.getFincId() != null) {
			MfBusFincApp mfBusFincApp = new MfBusFincApp();
			mfBusFincApp.setFincId(mfPactFiveclass.getFincId());
			mfBusFincApp = mfBusFincAppFeign.getById(mfBusFincApp);
			model.addAttribute("mfBusFincApp", mfBusFincApp);
			repayTypeStr = repayTypeMap.get(mfBusFincApp.getRepayType()).getOptName();
		}

		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);

		String scNo = BizPubParm.SCENCE_TYPE_DOC_EXAM;
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("cusNo", cusNo);
		dataMap.put("fiveclassId", mfPactFiveclass.getFiveclassId());
	
		dataMap.put("scNo", scNo);
		dataMap.put("pactId", pactId);
		ViewUtil.setViewPointParm(request, dataMap);
		// 获得审批节点信息
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(mfPactFiveclass.getFiveclassId(), null);
		
		// 处理审批意见类型
		Map<String, String> opinionTypeMap = new HashMap<String, String>();
		opinionTypeMap.put("hideOpinionType", hideOpinionType); // 隐藏审批类型
		String nodeNo = taskAppro.getActivityName();// 审批流程中当前审批节点编号
		opinionTypeMap.put("processDefinitionId", taskAppro.getProcessDefinitionId());// 流程id
		opinionTypeMap.put("nodeNo", nodeNo);// 当前节点编号
		List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType,
				taskAppro.getCouldRollback(), opinionTypeMap);
		this.changeFormProperty(formfiveclassapp0001, "opinionType", "optionArray", opinionTypeList);
		//担保方式
		Map<String, ParmDic> vouTypeMap = new CodeUtils().getMapObjByKeyName("VOU_TYPE");
		String vouTypeStr = vouTypeMap.get(mfBusPact.getVouType()).getOptName();
		//当前五级分类
		Map<String, ParmDic> fiveStsMap = new CodeUtils().getMapObjByKeyName("FIVE_STS");
		String fiveclassStr = fiveStsMap.get(mfPactFiveclass.getFiveclass()).getOptName();
		//系统初分
		String systemFiveclassStr = "";
		if ("".equals(mfPactFiveclass.getSystemFiveclass())){

		}
		else{
			systemFiveclassStr = fiveStsMap.get(mfPactFiveclass.getSystemFiveclass()).getOptName();
		}
		Task task= wkfInterfaceFeign.getTask(mfPactFiveclass.getFiveclassId(),null);
		//获得当前审批岗位前面审批过得岗位信息
		JSONArray befNodesjsonArray = new JSONArray();
		befNodesjsonArray = wkfInterfaceFeign.getBefNodes1(task.getId(), User.getRegNo(request));
		request.setAttribute("befNodesjsonArray", befNodesjsonArray);

		model.addAttribute("repayTypeStr", repayTypeStr);
		model.addAttribute("vouTypeStr", vouTypeStr);
		model.addAttribute("fiveclassStr", fiveclassStr);
		model.addAttribute("systemFiveclassStr", systemFiveclassStr);
		model.addAttribute("formfiveclassapp0001", formfiveclassapp0001);
		model.addAttribute("mfCusCustomer", mfCusCustomer);
		model.addAttribute("mfBusPact", mfBusPact);
		model.addAttribute("mfPactFiveclass", mfPactFiveclass);
		model.addAttribute("fiveclassId", fiveclassId);
		model.addAttribute("scNo", scNo);
		model.addAttribute("query", "");
		return "/component/pact/fiveclass/MfPactFiveclass_ViewPoint";
	}

	/**
	 * 审批流程提交
	 * 
	 * @param taskId
	 * @param activityType
	 * @param fiveclassId
	 * @param opinionType
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/submitUpdate")
	@ResponseBody
	public Map<String, Object> submitUpdate(Model model, String ajaxData, String taskId, String activityType,
			String fiveclassId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formfiveclassapp0001 = formService.getFormData("fiveclassapp0001");
		getFormValue(formfiveclassapp0001, getMapByJson(ajaxData));
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfPactFiveclass mfPactFiveclass = new MfPactFiveclass();
		setObjValue(formfiveclassapp0001, mfPactFiveclass);
		mfPactFiveclass.setConfirmDate(mfPactFiveclass.getRegTime());
		mfPactFiveclass.setConfirmReason(mfPactFiveclass.getApprovalOpinion());
		if (taskId != null) {
			if (taskId.indexOf(",") != -1) {
				taskId = taskId.substring(0, taskId.indexOf(","));
			}
		}
		dataMap = (HashMap<String, Object>) getMapByJson(ajaxData);
		dataMap.put("activityType", activityType);
		dataMap.put("orgNo", User.getOrgNo(request));
		String opinionType = String.valueOf(dataMap.get("opinionType"));
		String approvalOpinion = String.valueOf(dataMap.get("approvalOpinion"));
		String transition = workflowDwrFeign.findNextTransition(taskId);
		transition = request.getParameter("transition");
	//	String transition = list.get(0).getName();
		dataMap.put("taskId", taskId);
		dataMap.put("appNo", fiveclassId);
		dataMap.put("opinionType", opinionType);
		dataMap.put("approvalOpinion", approvalOpinion);
		dataMap.put("transition", transition);
		dataMap.put("opNo", User.getRegNo(this.request));
		dataMap.put("nextUser",mfPactFiveclass.getNextUser());
		new FeignSpringFormEncoder().addParamsToBaseDomain(mfPactFiveclass);  
		dataMap.put("mfPactFiveclass", mfPactFiveclass);
		Result res = mfPactFiveclassFeign.doCommit(dataMap);

		if (!res.isSuccess()) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SUBMIT.getMessage());
		} else {
			dataMap.put("flag", "success");
			dataMap.put("msg", res.getMsg());
		}
		return dataMap;
	}

	/**
	 * 审批历史
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/fiveclassApprovalHis")
	public String fiveclassApprovalHis(Model model,String appNo) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("appNo", appNo);
		model.addAttribute("query", "");
		return "/component/pact/fiveclass/MfPactFiveclass_ApprovalHis";
	}

	/**
	 * 根据id查询五级分类详情
	 * @param pactId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/fiveclassView")
	public String fiveclassView(Model model, String fincId) throws Exception {
		ActionContext.initialize(request, response);

		MfPactFiveclass mfPactFiveclass = new MfPactFiveclass();
		mfPactFiveclass.setFincId(fincId);

		List<MfPactFiveclass> mfPactFiveclassList = mfPactFiveclassFeign.getFiveAndHisList(mfPactFiveclass);

		mfPactFiveclass = mfPactFiveclassFeign.getById(mfPactFiveclass);

		MfBusFincApp mfbusfincApp = new MfBusFincApp();
		mfbusfincApp.setFincId(fincId);
		mfbusfincApp = mfBusFincAppFeign.getById(mfbusfincApp);
		mfbusfincApp.setPutoutAmtFormat(MathExtend.moneyStr(mfbusfincApp.getPutoutAmt() / 10000));// 金额格式化

		model.addAttribute("mfPactFiveclass", mfPactFiveclass);
		model.addAttribute("mfbusfincApp", mfbusfincApp);
		model.addAttribute("mfPactFiveclassList", mfPactFiveclassList);
		model.addAttribute("query", "");

		return "/component/pact/fiveclass/MfPactFiveclass_View";
	}

	/**
	 * 根据id查询五级分类详情
	 * 
	 * @return
	 * @throws Exception
	 * 
	 * @RequestMapping(value = "/getFiveclass") public String getFiveclass(Model
	 *                       model, String ajaxData) throws Exception{ FormService
	 *                       formService = new FormService();
	 *                       ActionContext.initialize(request,response); FormData
	 *                       formfiveclass0003 =
	 *                       formService.getFormData("fiveclass0003");
	 *                       getFormValue(formfiveclass0003); MfPactFiveclass
	 *                       mfPactFiveclass = new MfPactFiveclass();
	 *                       mfPactFiveclass.setFiveclassId(fiveclassId);
	 *                       mfPactFiveclass =
	 *                       mfPactFiveclassFeign.getById(mfPactFiveclass); Date
	 *                       date = null; //更改登记日期的格式 String regTime =
	 *                       mfPactFiveclass.getRegTime(); if(regTime != null &&
	 *                       !"".equals(regTime)){ date =
	 *                       DateUtil.parseEightStrToDate(regTime); regTime =
	 *                       DateUtil.parseDateToTenStr(date);
	 *                       mfPactFiveclass.setRegTime(regTime); } //更改客户经理调整日期的格式
	 *                       String classDate = mfPactFiveclass.getClassDate();
	 *                       if(classDate != null && !"".equals(classDate)){ date =
	 *                       DateUtil.parseEightStrToDate(classDate); classDate =
	 *                       DateUtil.parseDateToTenStr(date);
	 *                       mfPactFiveclass.setClassDate(classDate); }
	 *                       getObjValue(formfiveclass0003, mfPactFiveclass); String
	 *                       pactId = mfPactFiveclass.getPactId(); String cusNo =
	 *                       mfPactFiveclass.getCusNo(); mfBusPact = new
	 *                       MfBusPact(); mfBusPact.setPactId(pactId); mfBusPact =
	 *                       mfBusPactFeign.getById(mfBusPact); MfCusCustomer
	 *                       mfCusCustomer = new MfCusCustomer();
	 *                       mfCusCustomer.setCusNo(cusNo); mfCusCustomer =
	 *                       cusInterfaceFeign.getById(mfCusCustomer);
	 *                       model.addAttribute("formfiveclass0001",
	 *                       formfiveclass0001); model.addAttribute("query", "");
	 *                       return "/MfPactFiveclass_View"; }
	 */

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String fiveclassId) throws Exception {
		ActionContext.initialize(request, response);
		MfPactFiveclass mfPactFiveclass = new MfPactFiveclass();
		mfPactFiveclass.setFiveclassId(fiveclassId);
		mfPactFiveclassFeign.delete(mfPactFiveclass);
		return getListPage(model);
	}
	/**
	 *打开批量五级分类人工认定页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/batchApply")
	public String batchApply(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FiveClassAndPact fiveClassAndPact = new FiveClassAndPact();
		String projectName =ymlConfig.getSysParams().get("sys.project.name");
		List<FiveClassAndPact> fiveClassAndPactList = mfPactFiveclassFeign.findFiveClassAndPactByPageForApply(fiveClassAndPact);
		Ipage ipage = this.getIpage();
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr("tablefiveandpactbatchapply", "thirdTableTag", fiveClassAndPactList, ipage, true);
		FormService formService = new FormService();
		FormData formfiveclassApplyBase = formService.getFormData("fiveclassApplyBase");
		getFormValue(formfiveclassApplyBase);
		MfFiveclassSummaryApply mfFiveclassSummaryApply = new MfFiveclassSummaryApply();
		if(fiveClassAndPactList!=null){
			Integer sumCount = fiveClassAndPactList.size();//汇总笔数
			Double sumAmt=0.00;//汇总金额
			Integer normalCnt = 0;//正常类笔数
			Integer focusCnt = 0;//关注类笔数
			Integer secondaryCnt = 0;//次级类笔数
			Integer suspiciousCnt = 0;//可疑类笔数
			Integer lossCnt = 0;//损失类笔数
			for (int i = 0; i < fiveClassAndPactList.size(); i++) {
				fiveClassAndPact = fiveClassAndPactList.get(i);
				sumAmt = MathExtend.add(sumAmt, Double.parseDouble(fiveClassAndPact.getPutoutAmt()));
				if(BizPubParm.FIVE_STS_1TH.equals(fiveClassAndPact.getNowFiveclass())){
					normalCnt++;
				}else if(BizPubParm.FIVE_STS_2TH.equals(fiveClassAndPact.getNowFiveclass())){
					focusCnt++;
				}else if(BizPubParm.FIVE_STS_3TH.equals(fiveClassAndPact.getNowFiveclass())){
					secondaryCnt++;
				}else if(BizPubParm.FIVE_STS_4TH.equals(fiveClassAndPact.getNowFiveclass())){
					suspiciousCnt++;
				}else if(BizPubParm.FIVE_STS_5TH.equals(fiveClassAndPact.getNowFiveclass())){
					lossCnt++;
				}else {
				}
			}
			mfFiveclassSummaryApply.setSumCount(sumCount);
			mfFiveclassSummaryApply.setSumAmt(sumAmt);
			mfFiveclassSummaryApply.setNormalCnt(normalCnt);
			mfFiveclassSummaryApply.setFocusCnt(focusCnt);
			mfFiveclassSummaryApply.setSecondaryCnt(secondaryCnt);
			mfFiveclassSummaryApply.setSuspiciousCnt(suspiciousCnt);
			mfFiveclassSummaryApply.setLossCnt(lossCnt);
		}
		getObjValue(formfiveclassApplyBase, mfFiveclassSummaryApply);
		model.addAttribute("formfiveclassApplyBase",formfiveclassApplyBase);
		JSONObject json = new JSONObject();
		json.put("tableHtml",tableHtml);
		String ajaxData = json.toString();
		model.addAttribute("query","");
		model.addAttribute("ajaxData",ajaxData);
		return "/component/pact/fiveclass/MfFiveclassSummaryApply_Insert";
	}
	/**
	 *打开阶段五级分类人工认定页面
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/stageApply")
	public String stageApply(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FiveClassAndPact fiveClassAndPact = new FiveClassAndPact();
		List<FiveClassAndPact> fiveClassAndPactList = mfPactFiveclassFeign.findFiveClassAndPactByPageForApply(fiveClassAndPact);
		Ipage ipage = this.getIpage();
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr("tablefiveandpactbatchapply", "thirdTableTag", fiveClassAndPactList, ipage, true);
		FormService formService = new FormService();
		FormData formfiveclassApplyBase = formService.getFormData("fiveclassApplyStageBase");
		getFormValue(formfiveclassApplyBase);
		MfFiveclassSummaryApply mfFiveclassSummaryApply = new MfFiveclassSummaryApply();
		if(fiveClassAndPactList!=null){
			Integer sumCount = fiveClassAndPactList.size();//汇总笔数
			Double sumAmt=0.00;//汇总金额
			Integer normalCnt = 0;//正常类笔数
			Integer focusCnt = 0;//关注类笔数
			Integer secondaryCnt = 0;//次级类笔数
			Integer suspiciousCnt = 0;//可疑类笔数
			Integer lossCnt = 0;//损失类笔数
			for (int i = 0; i < fiveClassAndPactList.size(); i++) {
				fiveClassAndPact = fiveClassAndPactList.get(i);
				sumAmt = MathExtend.add(sumAmt, Double.parseDouble(fiveClassAndPact.getPutoutAmt()));
				if(BizPubParm.FIVE_STS_1TH.equals(fiveClassAndPact.getNowFiveclass())){
					normalCnt++;
				}else if(BizPubParm.FIVE_STS_2TH.equals(fiveClassAndPact.getNowFiveclass())){
					focusCnt++;
				}else if(BizPubParm.FIVE_STS_3TH.equals(fiveClassAndPact.getNowFiveclass())){
					secondaryCnt++;
				}else if(BizPubParm.FIVE_STS_4TH.equals(fiveClassAndPact.getNowFiveclass())){
					suspiciousCnt++;
				}else if(BizPubParm.FIVE_STS_5TH.equals(fiveClassAndPact.getNowFiveclass())){
					lossCnt++;
				}else {
				}
			}
			mfFiveclassSummaryApply.setSumCount(sumCount);
			mfFiveclassSummaryApply.setSumAmt(sumAmt);
			mfFiveclassSummaryApply.setNormalCnt(normalCnt);
			mfFiveclassSummaryApply.setFocusCnt(focusCnt);
			mfFiveclassSummaryApply.setSecondaryCnt(secondaryCnt);
			mfFiveclassSummaryApply.setSuspiciousCnt(suspiciousCnt);
			mfFiveclassSummaryApply.setLossCnt(lossCnt);
		}
		getObjValue(formfiveclassApplyBase, mfFiveclassSummaryApply);
		model.addAttribute("formfiveclassApplyStageBase",formfiveclassApplyBase);
		JSONObject json = new JSONObject();
		json.put("tableHtml",tableHtml);
		String ajaxData = json.toString();
		model.addAttribute("query","");
		model.addAttribute("ajaxData",ajaxData);
		return "/component/pact/fiveclass/MfFiveclassSummaryApply_InsertForStage";
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
		FormData formfiveclass0002 = formService.getFormData("fiveclass0002");
		getFormValue(formfiveclass0002);
		boolean validateFlag = this.validateFormData(formfiveclass0002);
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
		FormData formfiveclass0002 = formService.getFormData("fiveclass0002");
		getFormValue(formfiveclass0002);
		boolean validateFlag = this.validateFormData(formfiveclass0002);
	}

}

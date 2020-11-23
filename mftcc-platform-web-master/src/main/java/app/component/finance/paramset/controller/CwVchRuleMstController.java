package app.component.finance.paramset.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.component.nmd.entity.ParmDic;
import app.component.nmdinterface.NmdInterfaceFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import com.google.gson.Gson;

import app.component.common.EntityUtil;
import app.component.finance.paramset.entity.CwProofWords;
import app.component.finance.paramset.entity.CwVchRuleDetail;
import app.component.finance.paramset.entity.CwVchRuleDetailPlate;
import app.component.finance.paramset.entity.CwVchRuleMst;
import app.component.finance.paramset.entity.CwVchRuleMstPlate;
import app.component.finance.paramset.feign.CwVchRuleMstFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;

/**
 * Title: CwVchRuleMstAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Mar 08 14:53:10 CST 2017
 **/
@Controller
@RequestMapping("/cwVchRuleMst")
public class CwVchRuleMstController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入cwComItemFeign
	@Autowired
	private CwVchRuleMstFeign cwVchRuleMstFeign;
	@Autowired
	private NmdInterfaceFeign nmdInterfaceFeign;
	// 全局变量
	private String query;
	private FormService formService = new FormService();

	private Gson gson = new Gson();

	public CwVchRuleMstController() {
		query = "";
	}

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/finance/paramset/CwVchRuleMst_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CwVchRuleMst cwVchRuleMst = new CwVchRuleMst();
		try {
			cwVchRuleMst.setCustomQuery(ajaxData);// 自定义查询参数赋值
			cwVchRuleMst.setCriteriaList(cwVchRuleMst, ajaxData);// 我的筛选
			// this.getRoleConditions(cwVchRuleMst,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);
			// 自定义查询Bo方法
			Map<String, Object> params = new HashMap<>();
			params.put("cwVchRuleMst", cwVchRuleMst);
			ipage.setParams(params);
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			ipage = cwVchRuleMstFeign.findByPage(ipage,finBooks);
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
	@RequestMapping(value = "/insertAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String finBooks = (String) request.getSession().getAttribute("finBooks");
		try {
			FormData formcwvchrulemst0002 = formService.getFormData("cwvchrulemst0002");
			getFormValue(formcwvchrulemst0002, getMapByJson(ajaxData));
			if (this.validateFormData(formcwvchrulemst0002)) {
				CwVchRuleMst cwVchRuleMst = new CwVchRuleMst();
				setObjValue(formcwvchrulemst0002, cwVchRuleMst);
				cwVchRuleMstFeign.insert(cwVchRuleMst,finBooks);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			} else {
				dataMap.put("flag", "error");
				// dataMap.put("msg",this.getFormulavaliErrorMsg());
				dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
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
	@RequestMapping(value = "/updateAjaxByOne", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcwvchrulemst0002 = formService.getFormData("cwvchrulemst0002");
		getFormValue(formcwvchrulemst0002, getMapByJson(ajaxData));
		CwVchRuleMst cwVchRuleMstJsp = new CwVchRuleMst();
		setObjValue(formcwvchrulemst0002, cwVchRuleMstJsp);
		String finBooks = (String) request.getSession().getAttribute("finBooks");
		CwVchRuleMst cwVchRuleMst = cwVchRuleMstFeign.getById(cwVchRuleMstJsp,finBooks);
		if (cwVchRuleMst != null) {
			try {
				cwVchRuleMst = (CwVchRuleMst) EntityUtil.reflectionSetVal(cwVchRuleMst, cwVchRuleMstJsp,
						getMapByJson(ajaxData));
				cwVchRuleMstFeign.update(cwVchRuleMst,finBooks);
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
	@RequestMapping(value = "/updateAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String finBooks = (String) request.getSession().getAttribute("finBooks");
			FormData formcwvchrulemst0002 = formService.getFormData("cwvchrulemst0002");
			getFormValue(formcwvchrulemst0002, getMapByJson(ajaxData));
			if (this.validateFormData(formcwvchrulemst0002)) {
				CwVchRuleMst cwVchRuleMst = new CwVchRuleMst();
				setObjValue(formcwvchrulemst0002, cwVchRuleMst);
				cwVchRuleMstFeign.update(cwVchRuleMst,finBooks);
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
	@RequestMapping(value = "/getByIdAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getByIdAjax(String traceNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String finBooks = (String) request.getSession().getAttribute("finBooks");
		FormData formcwvchrulemst0002 = formService.getFormData("cwvchrulemst0002");
		CwVchRuleMst cwVchRuleMst = new CwVchRuleMst();
		cwVchRuleMst.setTraceNo(traceNo);
		cwVchRuleMst = cwVchRuleMstFeign.getById(cwVchRuleMst,finBooks);
		getObjValue(formcwvchrulemst0002, cwVchRuleMst, formData);
		if (cwVchRuleMst != null) {
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
	@RequestMapping(value = "/deleteAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAjax(String traceNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CwVchRuleMst cwVchRuleMst = new CwVchRuleMst();
		cwVchRuleMst.setTraceNo(traceNo);
		try {
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			cwVchRuleMstFeign.delete(cwVchRuleMst,finBooks);
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
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormData formcwvchrulemst0002 = formService.getFormData("cwvchrulemst0002");
		model.addAttribute("formcwvchrulemst0002", formcwvchrulemst0002);
		model.addAttribute("query", query);
		return "/component/finance/paramset/CwVchRuleMst_Insert";
	}

	/***
	 * 新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormData formcwvchrulemst0002 = formService.getFormData("cwvchrulemst0002");
		String finBooks = (String) request.getSession().getAttribute("finBooks");
		getFormValue(formcwvchrulemst0002);
		CwVchRuleMst cwVchRuleMst = new CwVchRuleMst();
		setObjValue(formcwvchrulemst0002, cwVchRuleMst);
		cwVchRuleMstFeign.insert(cwVchRuleMst,finBooks);
		getObjValue(formcwvchrulemst0002, cwVchRuleMst);
//		this.addActionMessage("保存成功");
		Ipage ipage = this.getIpage();
		// 自定义查询Bo方法
		Map<String, Object> params = new HashMap<>();
		params.put("cwVchRuleMst", cwVchRuleMst);
		ipage.setParams(params);
		List<CwVchRuleMst> cwVchRuleMstList = (List<CwVchRuleMst>) cwVchRuleMstFeign.findByPage(ipage,finBooks).getResult();
		model.addAttribute("cwVchRuleMst", cwVchRuleMst);
		model.addAttribute("cwVchRuleMstList", cwVchRuleMstList);
		model.addAttribute("formcwvchrulemst0002", formcwvchrulemst0002);
		model.addAttribute("query", query);
		return "/component/finance/paramset/CwVchRuleMst_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String traceNo) throws Exception {
		ActionContext.initialize(request, response);
		FormData formcwvchrulemst0001 = formService.getFormData("cwvchrulemst0001");
		String finBooks = (String) request.getSession().getAttribute("finBooks");
		getFormValue(formcwvchrulemst0001);
		CwVchRuleMst cwVchRuleMst = new CwVchRuleMst();
		cwVchRuleMst.setTraceNo(traceNo);
		cwVchRuleMst = cwVchRuleMstFeign.getById(cwVchRuleMst,finBooks);
		getObjValue(formcwvchrulemst0001, cwVchRuleMst);
		model.addAttribute("cwVchRuleMst", cwVchRuleMst);
		model.addAttribute("formcwvchrulemst0001", formcwvchrulemst0001);
		model.addAttribute("query", query);
		return "/component/finance/paramset/CwVchRuleMst_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(String traceNo) throws Exception {
		ActionContext.initialize(request, response);
		CwVchRuleMst cwVchRuleMst = new CwVchRuleMst();
		cwVchRuleMst.setTraceNo(traceNo);
		String finBooks = (String)request.getSession().getAttribute("finBooks");
		cwVchRuleMstFeign.delete(cwVchRuleMst,finBooks);
		return getListPage();
	}

	/**
	 * 新增校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateInsert")
	public void validateInsert(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormData formcwvchrulemst0002 = formService.getFormData("cwvchrulemst0002");
		getFormValue(formcwvchrulemst0002);
		boolean validateFlag = this.validateFormData(formcwvchrulemst0002);
		model.addAttribute("formcwvchrulemst0002", formcwvchrulemst0002);
		model.addAttribute("query", query);
	}

	/**
	 * 修改校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateUpdate")
	public void validateUpdate(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormData formcwvchrulemst0002 = formService.getFormData("cwvchrulemst0002");
		getFormValue(formcwvchrulemst0002);
		boolean validateFlag = this.validateFormData(formcwvchrulemst0002);
		model.addAttribute("formcwvchrulemst0002", formcwvchrulemst0002);
		model.addAttribute("query", query);
	}

	/**
	 * 
	 * 方法描述： 跳转到新增页面
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author lzshuai
	 * @date 2017-3-9 下午2:04:41
	 */
	@RequestMapping(value = "/goAddPage")
	public String goAddPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		try {
			// 查询模板主表的内容
			CwVchRuleMst bean = new CwVchRuleMst();
			// List<CwVchRuleMst> listbean =
			// cwVchRuleMstFeign.getCwvchRuleMstData(bean);
			List<CwVchRuleMstPlate> plageList = cwVchRuleMstFeign.getCwRulePlageList();// 获取业务类型
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			List<CwProofWords> proofWordList = cwVchRuleMstFeign.getProofWordsList(finBooks);// 获取凭证字列表

			Map<String, Object> mapObject = cwVchRuleMstFeign.getMapObject();
			@SuppressWarnings("unchecked")
			List<Map<String, String>> listmap = (List<Map<String, String>>) mapObject.get("jytypeMap");
			@SuppressWarnings("unchecked")
			List<Map<String, String>> adaptmap = (List<Map<String, String>>) mapObject.get("adaptMap");// 适用交易
			List<Map<String, String>> bdaptmap = (List<Map<String, String>>) mapObject.get("bdaptMap");// 适用交易
			List<Map<String, String>> remap = cwVchRuleMstFeign.getCwPayItems();// 获取业务凭证中
			//List<CwVchRuleDetailPlate> detailPlateslist =  (List<CwVchRuleDetailPlate>) mapObject.get("detailPlateslist");
            ParmDic parmDic = new ParmDic();
            parmDic.setKeyName("CW_VOUCHER_BODY");
            List dicList = nmdInterfaceFeign.findAllParmDicByKeyName(parmDic);

			String priceTaxType = cwVchRuleMstFeign.getMonPriceTaxShow(finBooks);
			String jsonlist = gson.toJson(plageList);
			String wordlist = gson.toJson(proofWordList);
			String listmapJson = gson.toJson(listmap);
			String adaptlistmapJson = gson.toJson(adaptmap);
			String bdaptlistmapJson = gson.toJson(bdaptmap);
			String jsonremap = gson.toJson(remap);
			//String detailPlateslistJson = gson.toJson(detailPlateslist);
			
			model.addAttribute("jsonlist", jsonlist);
			model.addAttribute("wordlist", wordlist);
			model.addAttribute("listmapJson", listmapJson);
			model.addAttribute("adaptlistmapJson", adaptlistmapJson);
			model.addAttribute("bdaptlistmapJson", bdaptlistmapJson);// 业务类型
			model.addAttribute("paymap", jsonremap);// 取值数据
			model.addAttribute("priceTaxType", priceTaxType);// 价税类型
			model.addAttribute("voucherBody", gson.toJson(dicList));// 凭证记账主体
			//model.addAttribute("detailPlateslistJson",detailPlateslistJson);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return "/component/finance/paramset/CwVchRuleMst_goAddPage";
	}

	/**
	 * 
	 * 方法描述：保存业务新增的数据
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author lzshuai
	 * @date 2017-3-10 下午6:55:16
	 */
	@RequestMapping(value = "/addRuleDataAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addRuleDataAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String finBooks = (String) request.getSession().getAttribute("finBooks");
		try {
			Map<String, Object> formMap = new Gson().fromJson(ajaxData, Map.class);
			String result = cwVchRuleMstFeign.addCwVchRuleMstData(formMap,finBooks);

			
			  //添加成功后，要加载数据
			/*Ipage ipage = this.getIpage(); 
			JsonTableUtil jtu = new JsonTableUtil(); 
			CwVchRuleMst stu = new CwVchRuleMst();
			  
			String tableHtml = jtu.getJsonStr("tablecwvchrulemst0001","tableTag",
			(List<CwVchRuleMst>)cwVchRuleMstFeign.findByPage(ipage,stu).getResult(), null,true);
			dataMap.put("htmlStr", tableHtml);
			dataMap.put("htmlStrFlag","1");*/
			 
			if ("1111".equals(result)) {
				dataMap.put("flag", "fail");
				// dataMap.put("msg","适用交易必须选择");
				dataMap.put("msg", MessageEnum.NOT_FORM_EMPTY.getMessage("适用交易"));

			} else if ("2222".equals(result)) {
				dataMap.put("flag", "fail");
				// dataMap.put("msg","该业务中已经存在适用交易，请勿重复提交！");
				dataMap.put("msg", MessageEnum.EXIST_INFORMATION_EVAL.getMessage("你选择的业务中适用交易"));

			} else if ("3333".equals(result)) {
				dataMap.put("flag", "fail");
				dataMap.put("msg", MessageEnum.FIRST_OPERATION.getMessage("选择交易类型"));

			} else {

				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dataMap;
	}

	/**
	 * 
	 * 方法描述：业务记账规则详细
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author lzshuai
	 * @date 2017-3-13 上午11:43:32
	 */
	@RequestMapping(value = "/getRuleMstDetail")
	public String getRuleMstDetail(Model model, String traceNo) throws Exception {
		ActionContext.initialize(request, response);
		String finBooks = (String) request.getSession().getAttribute("finBooks");
		CwVchRuleDetail cwVchdetail = new CwVchRuleDetail();
		cwVchdetail.setTraceNo(traceNo);

		CwVchRuleMst cwVchRuleMst = new CwVchRuleMst();
		cwVchRuleMst.setTraceNo(traceNo);
		
		CwVchRuleMst idBean = cwVchRuleMstFeign.getById(cwVchRuleMst,finBooks);
		List<CwVchRuleMstPlate> plageList = cwVchRuleMstFeign.getCwRulePlageList();// 获取业务类型
		List<CwVchRuleDetail> rulelist = cwVchRuleMstFeign.getCwVchRuleDetailList(cwVchdetail,finBooks);
		List<CwProofWords> proofWordList = cwVchRuleMstFeign.getProofWordsList(finBooks);// 凭证字内容

		Map<String, Object> mapObject = cwVchRuleMstFeign.getMapObject();
		@SuppressWarnings("unchecked")
		List<Map<String, String>> listmap = (List<Map<String, String>>) mapObject.get("jytypeMap");
		@SuppressWarnings("unchecked")
		List<Map<String, String>> adaptmap = (List<Map<String, String>>) mapObject.get("adaptMap");// 适用交易
		List<Map<String, String>> bdaptmap = (List<Map<String, String>>) mapObject.get("bdaptMap");// 适用交易
		List<Map<String, String>> remap = cwVchRuleMstFeign.getCwPayItems();// 获取业务凭证中
																			// 取值的问题

		String priceTaxType = cwVchRuleMstFeign.getMonPriceTaxShow(finBooks);

		String wordlist = gson.toJson(proofWordList);
		String rulelistgson = gson.toJson(rulelist);
		String beangson = gson.toJson(idBean);
		String listmapJson = gson.toJson(listmap);
		String jsonlist = gson.toJson(plageList);
		String jsonremap = gson.toJson(remap);
		String adaptlistmapJson = gson.toJson(adaptmap);
		String bdaptlistmapJson = gson.toJson(bdaptmap);

		model.addAttribute("jsonlist", jsonlist);// 获取业务类型
		model.addAttribute("wordlist", wordlist);
		model.addAttribute("rulelist", rulelistgson);
		model.addAttribute("beangson", beangson);
		model.addAttribute("traceNo", traceNo);
		model.addAttribute("listmapJson", listmapJson);// 业务数据
		model.addAttribute("paymap", jsonremap);// 取值数据
		model.addAttribute("adaptlistmapJson", adaptlistmapJson);
		model.addAttribute("bdaptlistmapJson", bdaptlistmapJson);

		model.addAttribute("priceTaxType", priceTaxType);// 价税类型

		return "/component/finance/paramset/CwVchRuleMst_getRuleMstDetail";
	}

	/**
	 * 
	 * 方法描述： 修改业务记账的详情
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author lzshuai
	 * @date 2017-3-14 下午8:14:57
	 */
	@RequestMapping(value = "/updateRuleDataAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateRuleDataAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			@SuppressWarnings("unchecked")
			Map<String, Object> formMap = new Gson().fromJson(ajaxData, Map.class);
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			String result = cwVchRuleMstFeign.updateCwVchRuleMstData(formMap,finBooks);
			if ("1111".equals(result)) {
				dataMap.put("flag", "fail");
				// dataMap.put("msg","适用交易必须选择");
				dataMap.put("msg", MessageEnum.NOT_FORM_EMPTY.getMessage("适用交易"));

			} else if ("2222".equals(result)) {
				dataMap.put("flag", "fail");
				// dataMap.put("msg","该业务中已经存在适用交易，请勿重复提交！");
				dataMap.put("msg", MessageEnum.EXIST_INFORMATION_EVAL.getMessage("你选择的业务中适用交易"));

			} else {
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dataMap;
	}

	/**
	 * 
	 * 方法描述： 获取模版内容
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author lzshuai
	 * @date 2017-9-2 下午4:27:22
	 */
	@RequestMapping(value = "/getRulePlateAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getRulePlateAjax(String txCode) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String finBooks = (String) request.getSession().getAttribute("finBooks");
			dataMap.put("txCode", txCode);
			dataMap = cwVchRuleMstFeign.getRulePlateAjax(dataMap,finBooks);
			/*if ("1111".equals(result)) {
				dataMap.put("flag", "fail");
				dataMap.put("msg", MessageEnum.NOT_FORM_EMPTY.getMessage("适用交易"));

			} else {
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			}*/
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "fail");
			dataMap.put("msg", MessageEnum.NOT_FORM_EMPTY.getMessage("适用交易"));
		}

		return dataMap;
	}

	/**
	 * 
	 * 方法描述： 根据交易类型获取取产品内容
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author lzshuai
	 * @date 2017-9-2 下午4:28:03
	 */
	@RequestMapping(value = "/dochangeProductAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> dochangeProductAjax(String txCode, String txType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String finBooks = (String) request.getSession().getAttribute("finBooks");
			dataMap.put("txCode", txCode);
			dataMap.put("txType", txType);
			Map<String,Object> result = cwVchRuleMstFeign.dochangeProduct(dataMap,finBooks);
			dataMap.putAll(result);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.NOT_FORM_EMPTY.getMessage("适用交易"));
			e.printStackTrace();
		}
		return dataMap;
	}

}

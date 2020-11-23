package app.component.finance.account.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import app.base.User;
import app.component.common.EntityUtil;
import app.component.finance.account.entity.CwComItem;
import app.component.finance.account.entity.CwRelation;
import app.component.finance.account.feign.CwComItemFeign;
import app.component.finance.cwtools.feign.CwToolsFeign;
import app.component.finance.paramset.entity.CwSysParam;
import app.component.finance.util.CWEnumBean;
import app.component.finance.util.CwPublicUtil;
import app.component.finance.util.R;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;

/**
 * Title: CwComItemAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Mon Dec 26 15:52:06 CST 2016
 **/
@Controller
@RequestMapping("/cwComItem")
public class CwComItemController extends BaseFormBean {

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入cwComItemFeign
	@Autowired
	private CwComItemFeign cwComItemFeign;
	@Autowired
	private CwToolsFeign cwToolsFeign;
	// 全局变量
	private String query;
	// 表单变量
	private FormService formService = new FormService();

	public CwComItemController() {
		query = "";
	}

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model, String accType) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("accType", accType);
		return "/component/finance/account/CwComItem_List";
	}

	/**
	 * 科目列表放大镜打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListAllPage")
	public String getListAllPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/finance/account/CwComItem_ListAll";
	}

	/**
	 * 
	 * 方法描述： 科目编码设置后列表页面请求
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 张丽
	 * @date 2017-2-7 上午10:36:18
	 */
	@RequestMapping(value = "/getAccnoListPage")
	public String getAccnoListPage(Model model, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("accLength", ajaxData);
		return "/component/finance/account/CwAccLength_List";
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
			String ajaxData, String accType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			CwComItem cwComItem = new CwComItem();
			cwComItem.setCustomQuery(ajaxData);// 自定义查询参数赋值
			cwComItem.setCriteriaList(cwComItem, ajaxData);// 我的筛选

			cwComItem.setAccType(accType);
			// this.getRoleConditions(cwComItem,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);
			// 自定义查询Bo方法
			
			Map<String, Object> params = new HashMap<>();
			params.put("cwComItem", cwComItem);
			ipage.setParams(params);
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			ipage = cwComItemFeign.findByPage(ipage,finBooks);
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
	 * 科目编号设置列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPage1Ajax")
	@ResponseBody
	public Map<String, Object> findByPage1Ajax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			// Map<String, String> formMap = CwPublicUtil.getMapByJson(ajaxData);
			// this.getRoleConditions(cwComItem,"1000000001");//记录级权限控制方法
			Map<String, String> formMap = new HashMap<String, String>();
			formMap.put("accLength", ajaxData);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);
			Map<String, Object> params = new HashMap<>();
			params.put("formMap", formMap);
			ipage.setParams(params);
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			ipage = cwComItemFeign.getAccLengthNData(ipage,finBooks);
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
	@RequestMapping(value = "/insertAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formaccount0002 = formService.getFormData("account0002");
			getFormValue(formaccount0002, getMapByJson(ajaxData));
			if (this.validateFormData(formaccount0002)) {
				CwComItem cwComItem = new CwComItem();
				setObjValue(formaccount0002, cwComItem);
				cwComItem.setOpNo(User.getRegNo(request));
				cwComItem.setOpName(User.getRegName(request));
				String finBooks = (String)request.getSession().getAttribute("finBooks");
				R r = cwComItemFeign.insert(cwComItem,finBooks);
				if(r.isOk()) {
					dataMap.put("flag", "success");
					dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
				}else {
					dataMap.put("flag", "error");
					dataMap.put("msg", r.getMsg());
				}
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {

			dataMap.put("flag", "error");
			// dataMap.put("msg", "新增一级科目失败");
			dataMap.put("msg", MessageEnum.FAILED_SAVE.getMessage());
			e.printStackTrace();
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
		FormData formaccount0002 = formService.getFormData("account0002");
		getFormValue(formaccount0002, getMapByJson(ajaxData));
		CwComItem cwComItemJsp = new CwComItem();
		setObjValue(formaccount0002, cwComItemJsp);
		String finBooks = (String)request.getSession().getAttribute("finBooks");
		CwComItem cwComItem = cwComItemFeign.getById(cwComItemJsp,finBooks);
		if (cwComItem != null) {
			try {
				cwComItem = (CwComItem) EntityUtil.reflectionSetVal(cwComItem, cwComItemJsp, getMapByJson(ajaxData));
				cwComItemFeign.update(cwComItem,finBooks);
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
			CwComItem cwComItem = new CwComItem();
			FormData formaccount0002 = formService.getFormData("account0002");
			getFormValue(formaccount0002, getMapByJson(ajaxData));
			if (this.validateFormData(formaccount0002)) {
				cwComItem = new CwComItem();
				setObjValue(formaccount0002, cwComItem);
				String finBooks = (String)request.getSession().getAttribute("finBooks");
				R r = cwComItemFeign.update(cwComItem,finBooks);
				if(r.isOk()) {
					dataMap.put("flag", "success");
					dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
				}else {
					dataMap.put("flag", "error");
					dataMap.put("msg", r.getMsg());
				}

				// 更新成功后，要加载数据
				/*
				 * Ipage ipage = this.getIpage(); JsonTableUtil jtu = new JsonTableUtil();
				 * CwComItem stu = new CwComItem(); stu.setAccType(cwComItem.getAccType());
				 * String tableHtml = jtu.getJsonStr("tableaccount0001","tableTag",
				 * (List<CwComItem>)cwComItemFeign.findByPage(ipage, stu).getResult(),
				 * null,true); dataMap.put("htmlStr", tableHtml);
				 * dataMap.put("htmlStrFlag","1");
				 */

				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());

			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
			e.printStackTrace();
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
	public Map<String, Object> getByIdAjax(String accNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formaccount0002 = formService.getFormData("account0002");
		CwComItem cwComItem = new CwComItem();
		cwComItem.setAccNo(accNo);
		String finBooks = (String) request.getSession().getAttribute("finBooks");
		cwComItem = cwComItemFeign.getById(cwComItem,finBooks);
		getObjValue(formaccount0002, cwComItem, formData);
		if (cwComItem != null) {
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
	public Map<String, Object> deleteAjax(String accNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CwComItem cwComItem = new CwComItem();
		cwComItem.setAccNo(accNo);
		try {
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			R r = cwComItemFeign.delete(cwComItem,finBooks);
			if(r.isOk()) {
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg", r.getMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			e.printStackTrace();
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
	public String input(Model model, String accType) throws Exception {
		ActionContext.initialize(request, response);
		FormData formaccount0002 = formService.getFormData("account0002");
		CwComItem cwComItem = new CwComItem();
		cwComItem.setAccLvl("1");
		cwComItem.setShowAccLvl("一级科目");
		cwComItem.setAccType(accType);
		getObjValue(formaccount0002, cwComItem);
		model.addAttribute("formaccount0002", formaccount0002);
		model.addAttribute("query", query);
		return "/component/finance/account/CwComItem_Insert";
	}

	/**
	 * 科目编号长度设置页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/inputAccLength")
	public String inputAccLength(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormData formaccLength0001 = formService.getFormData("accLength0001");
		CwComItem cwComItem = new CwComItem();
		getObjValue(formaccLength0001, cwComItem);
		model.addAttribute("formaccLength0001", formaccLength0001);
		model.addAttribute("query", query);
		return "/component/finance/account/CwAccLength_Insert";
	}

	/**
	 * 保存科目编号长度设置数据
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/inputAccLengthAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> inputAccLengthAjax(FormData formaccLength0001, CwComItem cwComItem, String ajaxData)
			throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> formMap = CwPublicUtil.getMapByJson(ajaxData);
			/*
			 * formaccLength0001 = formService.getFormData("accLength0001");
			 * getFormValue(formaccLength0001, getMapByJson(ajaxData));
			 */
			if (this.validateFormData(formaccLength0001)) {
				setObjValue(formaccLength0001, cwComItem);
				CwSysParam cwSysParam1 = new CwSysParam();
				cwSysParam1.setPvalue(formMap.get("accLength"));
				String finBooks = (String)request.getSession().getAttribute("finBooks");
				R r = cwComItemFeign.doInputAccnoLength(cwSysParam1,finBooks);
				if(r.isOk()) {
					dataMap.put("flag", "success");
					dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
				}else {
					dataMap.put("flag", "error");
					dataMap.put("msg", r.getMsg());
				}
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {

			dataMap.put("flag", "error");
			// dataMap.put("msg", "科目编码长度设置失败");
			dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("科目编码长度设置"));
			e.printStackTrace();
			throw e;
		}
		return dataMap;
	}

	/***
	 * 新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormData formaccount0002 = formService.getFormData("account0002");
		getFormValue(formaccount0002);
		CwComItem cwComItem = new CwComItem();
		setObjValue(formaccount0002, cwComItem);
		cwComItem.setOpNo(User.getRegNo(request));
		cwComItem.setOpName(User.getRegName(request));
		String finBooks = (String)request.getSession().getAttribute("finBooks");
		cwComItemFeign.insert(cwComItem,finBooks);
		getObjValue(formaccount0002, cwComItem);
		Ipage ipage=new Ipage();
		ipage.setPageNo(1);
		ipage.setPageSize(15);
		
		Map<String, Object> params = new HashMap<>();
		params.put("cwComItem", cwComItem);
		ipage.setParams(params);
		List<CwComItem> cwComItemList = (List<CwComItem>) cwComItemFeign.findByPage(ipage,finBooks).getResult();
		model.addAttribute("formaccount0002", formaccount0002);
		model.addAttribute("cwComItem", cwComItem);
		model.addAttribute("cwComItemList", cwComItemList);
		model.addAttribute("query", query);
		return "/component/finance/account/CwComItem_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String accNo, String accLvl) throws Exception {
		ActionContext.initialize(request, response);
		String[] numStr = new String[] { "一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一" };
		FormData formaccount0001 = formService.getFormData("account0001");
		getFormValue(formaccount0001);
		CwComItem cwComItem = new CwComItem();
		cwComItem.setAccNo(accNo);
		String finBooks = (String)request.getSession().getAttribute("finBooks");
		cwComItem = cwComItemFeign.getById(cwComItem,finBooks);
		List<CwRelation> listCwRelation = cwComItemFeign.getCwRelationData(cwComItem,finBooks);

		String listbean = new Gson().toJson(listCwRelation);

		Integer lvlint = Integer.parseInt(accLvl);
		Integer acclvlInt = lvlint - 1;
		String kemuLvl = numStr[acclvlInt] + "级科目";
		cwComItem.setAccLvlName(kemuLvl);// 显示的科目级别
		getObjValue(formaccount0001, cwComItem);
		model.addAttribute("formaccount0001", formaccount0001);
		model.addAttribute("cwComItem", cwComItem);
		model.addAttribute("acclvlInt", lvlint);
		model.addAttribute("kemuLvl", kemuLvl);
		model.addAttribute("listbean", listbean);
		model.addAttribute("query", query);
		return "/component/finance/account/CwComItem_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String accNo) throws Exception {
		ActionContext.initialize(request, response);
		CwComItem cwComItem = new CwComItem();
		cwComItem.setAccNo(accNo);
		String finBooks = (String)request.getSession().getAttribute("finBooks");
		cwComItemFeign.delete(cwComItem,finBooks);
		model.addAttribute("cwComItem", cwComItem);
		// TODO
		return getListPage(null, accNo);
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
		FormData formaccount0002 = formService.getFormData("account0002");
		getFormValue(formaccount0002);
		boolean validateFlag = this.validateFormData(formaccount0002);
		model.addAttribute("formaccount0002", formaccount0002);
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
		FormData formaccount0002 = formService.getFormData("account0002");
		getFormValue(formaccount0002);
		boolean validateFlag = this.validateFormData(formaccount0002);
		model.addAttribute("formaccount0002", formaccount0002);
		model.addAttribute("query", query);
	}

	/**
	 * 新增二级科目及以上的新增页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addAccountById")
	public String addAccountById(Model model, String accNo, String accLvl) throws Exception {
		ActionContext.initialize(request, response);
		try {
			String[] numStr = new String[] { "一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一" };
			FormData formaccount0003 = formService.getFormData("account0003");

			CwComItem cwComItem = new CwComItem();
			cwComItem.setAccNo(accNo);
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			cwComItem = cwComItemFeign.getcwComItemById(cwComItem,finBooks);
			cwComItem.setUpAccno(accNo);
			Integer lvlint = Integer.parseInt(accLvl);
			Integer acclvlInt = lvlint + 1;
			// cwComItem.setAccLvl();
			String kemuLvl = numStr[lvlint] + "级科目";
			cwComItem.setAccLvlName(kemuLvl);// 几级科目
			cwComItem.setAccLvl(String.valueOf(acclvlInt));// 本次新增的科目
			// 只支持十级
			if (lvlint > 10) {
				lvlint = 10;
			}
			String pvalueType = cwComItem.getPvalueType();

			getObjValue(formaccount0003, cwComItem);
			model.addAttribute("formaccount0003", formaccount0003);
			model.addAttribute("cwComItem", cwComItem);
			model.addAttribute("acclvlInt", acclvlInt);
			model.addAttribute("kemuLvl", kemuLvl);
			model.addAttribute("accNo", accNo);
			model.addAttribute("pvalueType", pvalueType);
			model.addAttribute("query", query);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return "/component/finance/account/CwComItem_Insert2up";
	}

	/**
	 * 
	 * 方法描述： 新增二级科目及以上
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 刘争帅
	 * @date 2016-12-30 下午3:55:41
	 */
	@RequestMapping(value = "/insertAjax2upKm", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> insertAjax2upKm(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formaccount0003 = formService.getFormData("account0003");
			getFormValue(formaccount0003, getMapByJson(ajaxData));
			if (this.validateFormData(formaccount0003)) {
				CwComItem cwComItem = new CwComItem();
				setObjValue(formaccount0003, cwComItem);
				cwComItem.setOpNo(User.getRegNo(request));
				cwComItem.setOpName(User.getRegName(request));
				String finBooks = (String)request.getSession().getAttribute("finBooks");
				R r = cwComItemFeign.insertComItem2Up(cwComItem,finBooks);
				if(r.isOk()) {
					dataMap.put("flag", "success");
					dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
				}else {
					dataMap.put("flag", "error");
					dataMap.put("msg", r.getMsg());
				}
				// 添加成功后，要加载数据
				/*
				 * Ipage ipage = this.getIpage(); JsonTableUtil jtu = new JsonTableUtil();
				 * CwComItem stu = new CwComItem(); stu.setAccType(cwComItem.getAccType());
				 * String tableHtml = jtu.getJsonStr("tableaccount0001","tableTag",
				 * (List<CwComItem>)cwComItemFeign.findByPage(ipage, stu).getResult(),
				 * null,true); dataMap.put("htmlStr", tableHtml);
				 * dataMap.put("htmlStrFlag","1");
				 */

			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			// dataMap.put("msg", "新增科目失败");
			dataMap.put("msg", MessageEnum.FAILED_SAVE.getMessage());
			e.printStackTrace();
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述： 获取科目树弹窗数据
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-2-15 上午11:50:18
	 */
	@RequestMapping(value = "/getComItemListForTreeAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getComItemListForTreeAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> formMap = CwPublicUtil.getMapByJson(ajaxData);
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			R r = cwComItemFeign.getAllComItemListForTree(formMap,finBooks);
			if(r.isOk()) {
				dataMap.put("items", r.getResult());
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_QUERY.getMessage());
			}else {
				dataMap.put("flag", "error");
				dataMap.put("msg", r.getMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			// dataMap.put("msg", "新增科目失败");
			dataMap.put("msg", MessageEnum.FAILED_SAVE.getMessage());
			e.printStackTrace();
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述： 获取科目缓存数据
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-2-15 上午11:54:38
	 */
	@RequestMapping(value = "/getComCacheListAjax")
	@ResponseBody
	public Map<String, Object> getComCacheListAjax(String accType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			accType = StringUtil.isEmpty(accType) ? "0" : accType;
			List<CwComItem> list = new ArrayList<CwComItem>(); 
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			list.addAll(cwToolsFeign.getCwComItemCache(finBooks));
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				CwComItem cwComItem = (CwComItem) iterator.next();
				// 过滤非一级
				if ("1".equals(accType) && !"1".equals(cwComItem.getAccLvl())) {
					iterator.remove();
				}
				// 过滤非明细
				if ("2".equals(accType) && CWEnumBean.YES_OR_NO.SHI.getNum().equals(cwComItem.getSubAccYn())) {
					iterator.remove();
				}
				// 过滤 未挂辅助核算
				if ("3".equals(accType) && CWEnumBean.YES_OR_NO.FOU.getNum().equals(cwComItem.getSeqCtl())) {
					iterator.remove();
				}
			}
			dataMap.put("items", list);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
		} catch (Exception e) {
			dataMap.put("flag", "error");
			// dataMap.put("msg", "新增科目失败");
			dataMap.put("msg", MessageEnum.FAILED_SAVE.getMessage());
			e.printStackTrace();
			throw e;
		}
		return dataMap;
	}

	/**
	 * 
	 * 方法描述： 科目检查
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 刘争帅
	 * @date 2017-2-4 下午3:09:05
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/accountCheck")
	public String accountCheck(Model model, String accNo,String accLvl) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ActionContext.initialize(request, response);
		try {
			// Map<String, String> formMap = CwPublicUtil.getMapByJson(ajaxData);
			Map<String, String> temap = new HashMap<String, String>();
			temap.put("accNo", accNo);
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			 R r = cwComItemFeign.doAccountCheck(temap,finBooks);
			if(r.isOk()) {
				Map<String, Object> resultMap=(Map<String, Object>) r.getResult();
				List<Map<String, String>> pzlistMap = (List<Map<String, String>>) resultMap.get("pzManger");
				List<Map<String, String>> jylistMap = (List<Map<String, String>>) resultMap.get("jiaoyiManger");
				List<Map<String, String>> reprotlistMap = (List<Map<String, String>>) resultMap.get("reportData");

				String pzjson = new Gson().toJson(pzlistMap);
				dataMap.put("pzlistMap", pzjson);
				String jyjson = new Gson().toJson(jylistMap);
				dataMap.put("jylistMap", jyjson);
				String reportjson = new Gson().toJson(reprotlistMap);
				dataMap.put("reportlistMap", reportjson);
				dataMap.put("flag", "success");
				model.addAttribute("pzlistMap", pzjson);
				model.addAttribute("jylistMap", jyjson);
				model.addAttribute("reportlistMap", reportjson);
				model.addAttribute("pzlistMap_flag", "success");
			}else {
				dataMap.put("flag", "error");
				dataMap.put("msg", r.getMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			// dataMap.put("msg", "新增科目失败");
			dataMap.put("msg", MessageEnum.FAILED_SAVE.getMessage());
			e.printStackTrace();
			throw e;
		}
		return "/component/finance/account/CwComItem_accountCheck";
	}
}

package app.component.risk.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.component.risk.entity.RiskItem;
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
import app.component.risk.feign.RiskItemFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;

/**
 * Title: RiskItemAction.java Description:
 * 
 * @author:@dhcc.com.cn
 * @Tue Feb 23 06:42:31 GMT 2016
 **/
@Controller
@RequestMapping("/riskItem")
public class RiskItemController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入RiskItemBo
	@Autowired
	private RiskItemFeign riskItemFeign;
	// 全局变量

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model, String pageStr) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("pageStr", pageStr);
		return "/component/risk/RiskItem_List";
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
			String ajaxData, String pageStr) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		RiskItem riskItem = new RiskItem();
		try {
			riskItem.setCustomQuery(ajaxData);// 自定义查询参数赋值
			riskItem.setCriteriaList(riskItem, ajaxData);// 我的筛选
			riskItem.setPageStr(pageStr);
			// this.getRoleConditions(riskItem,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setParams(this.setIpageParams("riskItem",riskItem));
			// 自定义查询Bo方法
			ipage = riskItemFeign.findByPage(ipage);
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
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData, String pageStr) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formrisk0002 = null;
			if ("risk".equals(pageStr)) {
				formrisk0002 = formService.getFormData("risk0002");
			} else {
				formrisk0002 = formService.getFormData("cusacc0001");
			}
			getFormValue(formrisk0002, getMapByJson(ajaxData));
			if (this.validateFormData(formrisk0002)) {
				RiskItem riskItem = new RiskItem();
				setObjValue(formrisk0002, riskItem);
				if ("cus".equals(pageStr)) {
					riskItem.setRiskPreventClass("1");
				} else if ("pro".equals(pageStr)) {
					riskItem.setRiskPreventClass("2");
				}else if("type".equals(pageStr)){
					riskItem.setRiskPreventClass("1");
				}else {
				}
				String itemKey = riskItem.getItemKey();
				RiskItem item = new RiskItem();
				item.setItemKey(itemKey);
				List<RiskItem> list = riskItemFeign.getRiskItemList(item);
				if (list != null && list.size() > 0) {
					dataMap.put("flag", "error");
					dataMap.put("msg", MessageEnum.EXIST_INFORMATION_EVAL.getMessage("方案属性标识"));
				} else {
					riskItemFeign.insert(riskItem);
					dataMap.put("flag", "success");
					dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
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
		FormData formrisk0002 = formService.getFormData("risk0002");
		getFormValue(formrisk0002, getMapByJson(ajaxData));
		RiskItem riskItemJsp = new RiskItem();
		setObjValue(formrisk0002, riskItemJsp);
		RiskItem riskItem = riskItemFeign.getById(riskItemJsp);
		if (riskItem != null) {
			try {
				riskItem = (RiskItem) EntityUtil.reflectionSetVal(riskItem, riskItemJsp, getMapByJson(ajaxData));
				riskItemFeign.update(riskItem);
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
	public Map<String, Object> updateAjax(String ajaxData, String pageStr) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		RiskItem riskItem = new RiskItem();
		try {
			FormData formrisk0002 = null;
			if ("risk".equals(pageStr)) {
				formrisk0002 = formService.getFormData("risk0002");
			} else {
				formrisk0002 = formService.getFormData("cusacc0001");
			}
			getFormValue(formrisk0002, getMapByJson(ajaxData));
			if (this.validateFormData(formrisk0002)) {

				riskItem = new RiskItem();
				setObjValue(formrisk0002, riskItem);
				if ("cus".equals(pageStr)) {
					riskItem.setRiskPreventClass("1");
				} else if ("pro".equals(pageStr)) {
					riskItem.setRiskPreventClass("2");
				}else {
				}
				riskItemFeign.update(riskItem);
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
	public Map<String, Object> getByIdAjax(String itemNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formrisk0002 = formService.getFormData("risk0002");
		RiskItem riskItem = new RiskItem();
		riskItem.setItemNo(itemNo);
		riskItem = riskItemFeign.getById(riskItem);
		getObjValue(formrisk0002, riskItem, formData);
		if (riskItem != null) {
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
	public Map<String, Object> deleteAjax(String itemNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		RiskItem riskItem = new RiskItem();
		riskItem.setItemNo(itemNo);
		try {
			riskItemFeign.delete(riskItem);
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
	public String input(Model model, String pageStr) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		String riskItemNo = this.riskItemFeign.getRiskItemNo();
		RiskItem riskItem = new RiskItem();
		riskItem.setItemNo(riskItemNo);
		FormData formrisk0002 = null;
		if ("risk".equals(pageStr)) {
			formrisk0002 = formService.getFormData("risk0002");
		} else {
			formrisk0002 = formService.getFormData("cusacc0001");
		}
		getObjValue(formrisk0002, riskItem);
		model.addAttribute("formrisk0002", formrisk0002);
		model.addAttribute("query", "");
		model.addAttribute("pageStr", pageStr);
		return "/component/risk/RiskItem_Insert";
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
		FormData formrisk0002 = formService.getFormData("risk0002");
		getFormValue(formrisk0002);
		RiskItem riskItem = new RiskItem();
		setObjValue(formrisk0002, riskItem);
		riskItemFeign.insert(riskItem);
		getObjValue(formrisk0002, riskItem);
		this.addActionMessage(model, "保存成功");
		List<RiskItem> riskItemList = (List<RiskItem>) riskItemFeign.findByPage(this.getIpage()).getResult();
		model.addAttribute("riskItemList", riskItemList);
		model.addAttribute("formrisk0002", formrisk0002);
		model.addAttribute("query", "");
		return "/component/risk/RiskItem_Detail";
	}

	@RequestMapping(value = "/update")
	public String update(Model model, String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formrisk0002 = formService.getFormData("risk0002");
		getFormValue(formrisk0002);
		RiskItem riskItem = new RiskItem();
		setObjValue(formrisk0002, riskItem);
		riskItemFeign.update(riskItem);
		getObjValue(formrisk0002, riskItem);
		this.addActionMessage(model, "保存成功");
		List<RiskItem> riskItemList = (List<RiskItem>) riskItemFeign.findByPage(this.getIpage()).getResult();
		model.addAttribute("riskItemList", riskItemList);
		model.addAttribute("formrisk0002", formrisk0002);
		model.addAttribute("query", "");
		return "/component/risk/RiskItem_Detail";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String pageStr ,String itemNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formrisk0002 = null;
		if ("risk".equals(pageStr)) {
			formrisk0002  = formService.getFormData("risk0002");
		} else {
			formrisk0002 = formService.getFormData("cusacc0001");
		}
		getFormValue(formrisk0002);
		RiskItem riskItem = new RiskItem();
		riskItem.setItemNo(itemNo);
		riskItem = riskItemFeign.getById(riskItem);
		getObjValue(formrisk0002, riskItem);
		model.addAttribute("itemNo", itemNo);
		model.addAttribute("formrisk0002", formrisk0002);
		model.addAttribute("query", "");
		model.addAttribute("pageStr", pageStr);
		return "/component/risk/RiskItem_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String itemNo, String pageStr) throws Exception {
		ActionContext.initialize(request, response);
		RiskItem riskItem = new RiskItem();
		riskItem.setItemNo(itemNo);
		riskItemFeign.delete(riskItem);
		return getListPage(model, pageStr);
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
		FormData formrisk0002 = formService.getFormData("risk0002");
		getFormValue(formrisk0002);
		RiskItem riskItem = new RiskItem();
		setObjValue(formrisk0002, riskItem);
		boolean validateFlag = this.validateFormData(formrisk0002);
		if ((riskItem.getItemSql() != null && !"".equals(riskItem.getItemSql()))
				|| (riskItem.getItemJavaClass() != null && !"".equals(riskItem.getItemJavaClass()))) {

		} else {
			this.addActionError(model, "方案sql语句,Java类至少选择一个!");
		}
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
		FormData formrisk0002 = formService.getFormData("risk0002");
		getFormValue(formrisk0002);
		RiskItem riskItem = new RiskItem();
		setObjValue(formrisk0002, riskItem);
		boolean validateFlag = this.validateFormData(formrisk0002);
		if ((riskItem.getItemSql() != null && !"".equals(riskItem.getItemSql()))
				|| (riskItem.getItemJavaClass() != null && !"".equals(riskItem.getItemJavaClass()))) {

		} else {
			this.addActionError(model, "方案sql语句,Java类至少选择一个!");
		}
	}

}

package app.component.cus.controller;

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

import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.cus.entity.MfCusTable;
import app.component.cus.feign.MfCusTableFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONArray;

/**
 * Title: MfCusTableAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu Jul 21 14:17:01 CST 2016
 **/
@Controller
@RequestMapping("/mfCusTable")
public class MfCusTableController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfCusTableBo
	@Autowired
	private MfCusTableFeign mfCusTableFeign;
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
	public String getListPage(Model model, String cusNo, String dataFullFlag, Double weight) throws Exception {
		ActionContext.initialize(request, response);
		MfCusTable mfCusTable = new MfCusTable();
		mfCusTable.setCusNo(cusNo);
		mfCusTable.setDataFullFlag(dataFullFlag);
		mfCusTable.setWeight(weight);
		List<MfCusTable> mfCusTableList = mfCusTableFeign.getList(mfCusTable);
		model.addAttribute("query", "");
		model.addAttribute("mfCusTableList", mfCusTableList);
		return "/component/cus/MfCusTable_List";
	}

	@RequestMapping(value = "/getPageUpdateStas")
	public String getPageUpdateStas(Model model, String ajaxData, String relNo, String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		String cusType = request.getParameter("cusType");
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusTable mfCusTable = new MfCusTable();
		mfCusTable.setCusNo(relNo);
		if (cusNo.equals(relNo)) {
			mfCusTable.setCusType(cusType);

			dataMap.put("pageView", "cusView");
		} else {
			dataMap.put("pageView", "busView");
		}
		List<MfCusTable> mfCusTableList = mfCusTableFeign.getList(mfCusTable);
		ajaxData = JSONArray.fromObject(mfCusTableList).toString();
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("relNo", relNo);
		model.addAttribute("query", "");
		return "/component/cus/MfCusTable_UpdateStas";
	}

	@RequestMapping(value = "/getListAjax")
	@ResponseBody
	public Map<String, Object> getListAjax(String delFlag, String relNo, String cusNo, String dataFullFlag,
			Double weight) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfCusTable mfCusTable = new MfCusTable();
			mfCusTable.setCusNo(relNo);
			mfCusTable.setDataFullFlag(dataFullFlag);
			mfCusTable.setDelFlag(delFlag);
			mfCusTable.setWeight(weight);
			if (cusNo.equals(relNo)) {
				mfCusTable.setCusType(request.getParameter("cusType"));
			}
			List<MfCusTable> mfCusTableList = mfCusTableFeign.getList(mfCusTable);
			dataMap.put("flag", "success");
			dataMap.put("cusTableList", mfCusTableList);
		} catch (Exception e) {
			dataMap.put("flag", "error");
		}

		return dataMap;
	}

	/**
	 * @author admin
	 * @Description: 验证客户表单信息是否已经填写完整 date 2016-7-25
	 * @return
	 */
	@RequestMapping(value = "/checkCusInfoIsFull")
	public Map<String, Object> checkCusInfoIsFull(Model model, String ajaxData, String cusNo) throws Exception {
		MfCusTable mfCusTable = new MfCusTable();
		mfCusTable.setCusNo(cusNo);
		mfCusTable.setDataFullFlag("0");
		mfCusTable.setWeight(BizPubParm.WEIGHT);
		// 获得表单尚未填写的表单
		List<MfCusTable> mfCusTableList = mfCusTableFeign.getList(mfCusTable);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if (mfCusTableList.size() == 0) {
			dataMap.put("fullFlag", "1");
		} else {
			dataMap.put("fullFlag", "0");
		}
		return dataMap;
	}

	/**
	 * @author czk
	 * @Description: 检查客户必填表单信息是否填写完整。 date 2017-1-3
	 */
	@RequestMapping(value = "/checkCusInfoMustIsFull")
	@ResponseBody
	public Map<String, Object> checkCusInfoMustIsFull(Model model, String cusNo) throws Exception {
		MfCusTable mfCusTable = new MfCusTable();
		mfCusTable.setCusNo(cusNo);
		mfCusTable.setDataFullFlag("0");
		mfCusTable.setWeight(BizPubParm.WEIGHT);
		mfCusTable.setIsMust("1");
		// 获得表单尚未填写的表单
		List<MfCusTable> mfCusTableList = mfCusTableFeign.getList(mfCusTable);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if (mfCusTableList.size() == 0) {// 必填的表单全部已经填写
			dataMap.put("fullFlag", "1");
		} else {
			StringBuffer infoName = new StringBuffer();
			for (int i = 0; i < mfCusTableList.size(); i++) {
				infoName.append(mfCusTableList.get(i).getTableDes());
				if (i < mfCusTableList.size() - 1) {
					infoName.append("、");
				}
			}
			String info = infoName.toString();
			dataMap.put("fullFlag", "0");
			dataMap.put("infoName", info);
		}
		return dataMap;
	}

	@RequestMapping(value = "/updateDelFlagAjax")
	@ResponseBody
	public Map<String, Object> updateDelFlagAjax(String cusNo, String delFlag, Double weight, String tableName)
			throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfCusTable mfCusTable = new MfCusTable();
			mfCusTable.setCusNo(cusNo);
			mfCusTable.setDelFlag(delFlag);
			mfCusTable.setTableName(tableName);
			mfCusTable.setWeight(weight);
			mfCusTableFeign.update(mfCusTable);
			dataMap.put("flag", "success");
		} catch (Exception e) {
			dataMap.put("flag", "error");
		}
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
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusTable mfCusTable = new MfCusTable();
		try {
			mfCusTable.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCusTable.setCriteriaList(mfCusTable, ajaxData);// 我的筛选
			// this.getRoleConditions(mfCusTable,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfCusTable", mfCusTable));
			ipage = mfCusTableFeign.findByPage(ipage);
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
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formcus0002 = formService.getFormData("cus0002");
			getFormValue(formcus0002, getMapByJson(ajaxData));
			if (this.validateFormData(formcus0002)) {
				MfCusTable mfCusTable = new MfCusTable();
				setObjValue(formcus0002, mfCusTable);
				mfCusTableFeign.insert(mfCusTable);
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
		FormData formcus0002 = formService.getFormData("cus0002");
		getFormValue(formcus0002, getMapByJson(ajaxData));
		MfCusTable mfCusTableJsp = new MfCusTable();
		setObjValue(formcus0002, mfCusTableJsp);
		MfCusTable mfCusTable = mfCusTableFeign.getById(mfCusTableJsp);
		if (mfCusTable != null) {
			try {
				mfCusTable = (MfCusTable) EntityUtil.reflectionSetVal(mfCusTable, mfCusTableJsp,
						getMapByJson(ajaxData));
				mfCusTableFeign.update(mfCusTable);
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
		MfCusTable mfCusTable = new MfCusTable();
		try {
			FormData formcus0002 = formService.getFormData("cus0002");
			getFormValue(formcus0002, getMapByJson(ajaxData));
			if (this.validateFormData(formcus0002)) {
				setObjValue(formcus0002, mfCusTable);
				mfCusTableFeign.update(mfCusTable);
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
	public Map<String, Object> getByIdAjax(String cusNo, String tableName) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcus0002 = formService.getFormData("cus0002");
		MfCusTable mfCusTable = new MfCusTable();
		mfCusTable.setCusNo(cusNo);
		mfCusTable.setTableName(tableName);
		mfCusTable = mfCusTableFeign.getById(mfCusTable);
		getObjValue(formcus0002, mfCusTable, formData);
		if (mfCusTable != null) {
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
	public Map<String, Object> deleteAjax(String cusNo, String tableName) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusTable mfCusTable = new MfCusTable();
		mfCusTable.setCusNo(cusNo);
		mfCusTable.setTableName(tableName);
		try {
			mfCusTableFeign.delete(mfCusTable);
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
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formcus0002 = formService.getFormData("cus0002");
		model.addAttribute("formcus0002", formcus0002);
		model.addAttribute("query", "");
		return "/component/cus/MfCusTable_Insert";
	}

	/***
	 * 新增
	 * 
	 * @param ipage
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model, Ipage ipage) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formcus0002 = formService.getFormData("cus0002");
		getFormValue(formcus0002);
		MfCusTable mfCusTable = new MfCusTable();
		setObjValue(formcus0002, mfCusTable);
		mfCusTableFeign.insert(mfCusTable);
		getObjValue(formcus0002, mfCusTable);
		this.addActionMessage(model, "保存成功");
		ipage.setParams(this.setIpageParams("mfCusTable", mfCusTable));
		List<MfCusTable> mfCusTableList = (List<MfCusTable>) mfCusTableFeign.findByPage(ipage).getResult();
		model.addAttribute("formcus0002", formcus0002);
		model.addAttribute("mfCusTableList", mfCusTableList);
		model.addAttribute("query", "");
		return "/component/cus/MfCusTable_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String ajaxData, String cusNo, String tableName) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formcus0001 = formService.getFormData("cus0001");
		getFormValue(formcus0001);
		MfCusTable mfCusTable = new MfCusTable();
		mfCusTable.setCusNo(cusNo);
		mfCusTable.setTableName(tableName);
		mfCusTable = mfCusTableFeign.getById(mfCusTable);
		getObjValue(formcus0001, mfCusTable);
		model.addAttribute("formcus0001", formcus0001);
		model.addAttribute("query", "");
		return "/component/cus/MfCusTable_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String cusNo, String tableName, String dataFullFlag, Double weight)
			throws Exception {
		ActionContext.initialize(request, response);
		MfCusTable mfCusTable = new MfCusTable();
		mfCusTable.setCusNo(cusNo);
		mfCusTable.setTableName(tableName);
		mfCusTableFeign.delete(mfCusTable);
		return getListPage(model, cusNo, dataFullFlag, weight);
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
		FormData formcus0002 = formService.getFormData("cus0002");
		getFormValue(formcus0002);
		boolean validateFlag = this.validateFormData(formcus0002);
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
		FormData formcus0002 = formService.getFormData("cus0002");
		getFormValue(formcus0002);
		boolean validateFlag = this.validateFormData(formcus0002);
	}

}

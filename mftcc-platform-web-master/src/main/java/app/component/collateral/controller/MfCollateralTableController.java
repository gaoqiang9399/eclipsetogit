package app.component.collateral.controller;

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

import app.component.collateral.entity.MfCollateralTable;
import app.component.collateral.feign.MfCollateralTableFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Title: MfCollateralTableController.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu Mar 09 16:08:58 CST 2017
 **/
@Controller
@RequestMapping("/mfCollateralTable")
public class MfCollateralTableController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfCollateralTableFeign mfCollateralTableFeign;

	/**
	 * 获取列表数据
	 * 
	 * @return
	 * @throws Exception
	 */
	private void getTableData(Map<String, Object> dataMap, String tableId, MfCollateralTable mfCollateralTable)
			throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "tableTag", mfCollateralTableFeign.getAll(mfCollateralTable), null,
				true);
		dataMap.put("tableData", tableHtml);
	}

	/**
	 * 列表有翻页
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formdlcollateraltab0002 = formService.getFormData("dlcollateraltab0002");
		MfCollateralTable mfCollateralTable = new MfCollateralTable();
		Ipage ipage = this.getIpage();
		@SuppressWarnings("unchecked")
		List<MfCollateralTable> mfCollateralTableList = (List<MfCollateralTable>) mfCollateralTableFeign
				.findByPage(ipage, mfCollateralTable).getResult();
		model.addAttribute("mfCollateralTableList", mfCollateralTableList);
		model.addAttribute("formdlcollateraltab0002", formdlcollateraltab0002);
		model.addAttribute("query", "");
		return "/component/collateral/MfCollateralTable_List";
	}

	/**
	 * 列表全部无翻页
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListAll")
	public String getListAll(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formdlcollateraltab0002 = formService.getFormData("dlcollateraltab0002");
		MfCollateralTable mfCollateralTable = new MfCollateralTable();
		List<MfCollateralTable> mfCollateralTableList = mfCollateralTableFeign.getAll(mfCollateralTable);
		model.addAttribute("query", "");
		model.addAttribute("formdlcollateraltab0002", formdlcollateraltab0002);
		model.addAttribute("mfCollateralTableList", mfCollateralTableList);
		return "/component/collateral/MfCollateralTable_List";
	}

	/**
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData, String tableId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formdlcollateraltab0002 = formService.getFormData("dlcollateraltab0002");
			getFormValue(formdlcollateraltab0002, getMapByJson(ajaxData));
			if (this.validateFormData(formdlcollateraltab0002)) {
				MfCollateralTable mfCollateralTable = new MfCollateralTable();
				setObjValue(formdlcollateraltab0002, mfCollateralTable);
				mfCollateralTableFeign.insert(mfCollateralTable);
				getTableData(dataMap, tableId, mfCollateralTable);// 获取列表
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
	 * AJAX更新保存
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData, String tableId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formdlcollateraltab0002 = formService.getFormData("dlcollateraltab0002");
			getFormValue(formdlcollateraltab0002, getMapByJson(ajaxData));
			if (this.validateFormData(formdlcollateraltab0002)) {
				MfCollateralTable mfCollateralTable = new MfCollateralTable();
				setObjValue(formdlcollateraltab0002, mfCollateralTable);
				mfCollateralTableFeign.update(mfCollateralTable);
				getTableData(dataMap, tableId, mfCollateralTable);// 获取列表
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
	@RequestMapping("/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String collateralNo, String tableName) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formdlcollateraltab0002 = formService.getFormData("dlcollateraltab0002");
		MfCollateralTable mfCollateralTable = new MfCollateralTable();
		mfCollateralTable.setCollateralNo(collateralNo);
		mfCollateralTable.setTableName(tableName);
		mfCollateralTable = mfCollateralTableFeign.getById(mfCollateralTable);
		getObjValue(formdlcollateraltab0002, mfCollateralTable, formData);
		if (mfCollateralTable != null) {
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
	@RequestMapping("/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String ajaxData, String collateralNo, String tableName, String tableId)
			throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCollateralTable mfCollateralTable = new MfCollateralTable();
		mfCollateralTable.setCollateralNo(collateralNo);
		mfCollateralTable.setTableName(tableName);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			mfCollateralTable = (MfCollateralTable) JSONObject.toBean(jb, MfCollateralTable.class);
			mfCollateralTableFeign.delete(mfCollateralTable);
			getTableData(dataMap, tableId, mfCollateralTable);// 获取列表
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
	 * 押品点击加号出现页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getPageUpdateStas")
	public String getPageUpdateStas(Model model, String collateralNo) throws Exception {
		ActionContext.initialize(request, response);
		MfCollateralTable mfCollateralTable = new MfCollateralTable();
		mfCollateralTable.setCollateralNo(collateralNo);
		List<MfCollateralTable> mfCollateralTableList = mfCollateralTableFeign.getList(mfCollateralTable);
		String ajaxData = JSONArray.fromObject(mfCollateralTableList).toString();
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("query", "");
		return "/component/collateral/MfCollateralTable_UpdateStas";
	}

	/**
	 * 押品点击加号出现页面-ajax查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListAjax")
	@ResponseBody
	public Map<String, Object> getListAjax(String collateralNo, String dataFullFlag, String delFlag) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfCollateralTable mfCollateralTable = new MfCollateralTable();
			mfCollateralTable.setCollateralNo(collateralNo);
			mfCollateralTable.setDataFullFlag(dataFullFlag);
			mfCollateralTable.setDelFlag(delFlag);
			List<MfCollateralTable> mfCollateralTableList = mfCollateralTableFeign.getList(mfCollateralTable);
			dataMap.put("flag", "success");
			dataMap.put("collateralTableList", mfCollateralTableList);
		} catch (Exception e) {
			dataMap.put("flag", "error");
		}

		return dataMap;
	}

	/**
	 * 押品新增模块删除小模块-ajax更新
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateDelFlagAjax")
	@ResponseBody
	public Map<String, Object> updateDelFlagAjax(String collateralNo, String delFlag, String tableName)
			throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfCollateralTable mfCollateralTable = new MfCollateralTable();
			mfCollateralTable.setCollateralNo(collateralNo);
			mfCollateralTable.setDelFlag(delFlag);
			mfCollateralTable.setTableName(tableName);
			mfCollateralTableFeign.update(mfCollateralTable);
			dataMap.put("flag", "success");
		} catch (Exception e) {
			dataMap.put("flag", "error");
		}
		return dataMap;
	}

}

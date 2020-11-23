package app.component.msgconf.controller;

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

import app.component.msgconf.entity.MfMsgVar;
import app.component.msgconf.feign.MfMsgVarFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.WaterIdUtil;
import net.sf.json.JSONObject;

/**
 * Title: MfMsgVarAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Jul 05 15:23:19 CST 2017
 **/
@Controller
@RequestMapping("/mfMsgVar")
public class MfMsgVarController extends BaseFormBean {
	// 注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfMsgVarFeign mfMsgVarFeign;
	// 全局变量
	// 表单变量

	/**
	 * 获取列表数据
	 * 
	 * @param dataMap
	 * @param tableId
	 * 
	 * @return
	 * @throws Exception
	 */
	private void getTableData(List<MfMsgVar> list, Map<String,Object> dataMap, String tableId) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "thirdTableTag", list, null, true);
		dataMap.put("tableData", tableHtml);
	}

	private void getTableData(MfMsgVar mfMsgVar, String tableId, Map<String,Object> dataMap) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "tableTag", mfMsgVarFeign.getAll(mfMsgVar), null, true);
		dataMap.put("tableData", tableHtml);
	}

	/**
	 * 列表有翻页
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
	/*	FormData formmsgconf0002 = formService.getFormData("msgconf0002");
		MfMsgVar mfMsgVar = new MfMsgVar();
		Ipage ipage = this.getIpage();
		List<MfMsgVar> mfMsgVarList = (List<MfMsgVar>) mfMsgVarFeign.findByPage(ipage, mfMsgVar).getResult();
		model.addAttribute("formmsgconf0002", formmsgconf0002);
		model.addAttribute("mfMsgVarList", mfMsgVarList);*/
		model.addAttribute("query", "");
		return "/component/msgconf/MfMsgVar_List";
	}

	/**
	 * 列表有翻页
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getInsertPage")
	public String getInsertPage(Model model, String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formmsgconf0002 = formService.getFormData("msgconf0002");
		model.addAttribute("formmsgconf0002", formmsgconf0002);
		model.addAttribute("query", "");
		return "/component/msgconf/MfMsgVar_Insert";
	}

	/**
	 * 列表有翻页
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getDetailPage")
	public String getDetailPage(Model model, String varId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formmsgconf0002 = formService.getFormData("msgconf0002");
		MfMsgVar mfMsgVar = new MfMsgVar();
		mfMsgVar.setVarId(varId);
		mfMsgVar = mfMsgVarFeign.getById(mfMsgVar);
		getObjValue(formmsgconf0002, mfMsgVar);
		model.addAttribute("formmsgconf0002", formmsgconf0002);
		model.addAttribute("query", "");
		return "/component/msgconf/MfMsgVar_Detail";
	}

	/**
	 * 列表有翻页
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
		MfMsgVar mfMsgVar = new MfMsgVar();
		mfMsgVar.setCustomQuery(ajaxData);
		mfMsgVar.setCriteriaList(mfMsgVar, ajaxData);
		Ipage ipage = this.getIpage();
		ipage.setPageSize(pageSize);
		ipage.setPageNo(pageNo);
		ipage.setParams(this.setIpageParams("mfMsgVar",mfMsgVar));
		ipage = mfMsgVarFeign.findByPage(ipage);
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
		ipage.setResult(tableHtml);
		dataMap.put("ipage", ipage);
		return dataMap;
	}

	/**
	 * 
	 * 方法描述：
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-2-10 上午10:14:17
	 */
	@RequestMapping(value = "/updateFlagAjax")
	@ResponseBody
	public Map<String, Object> updateFlagAjax(String ajaxData,String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			JSONObject jobj = JSONObject.fromObject(ajaxData);
			FormData formmsgconf0002 = formService.getFormData("msgconf0002");
			getFormValue(formmsgconf0002, jobj);
			MfMsgVar mfMsgVar = new MfMsgVar();
			setObjValue(formmsgconf0002, mfMsgVar);
			int count = mfMsgVarFeign.updateFlag(mfMsgVar);
			if (count > 0) {
				mfMsgVar = mfMsgVarFeign.getById(mfMsgVar);
				ArrayList<MfMsgVar> mfMsgVarList = new ArrayList<MfMsgVar>();
				mfMsgVarList.add(mfMsgVar);
				getTableData(mfMsgVarList, dataMap,tableId);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
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
	 * 列表全部无翻页
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListAll")
	public String getListAll(Model model, String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formmsgconf0002 = formService.getFormData("msgconf0002");
		MfMsgVar mfMsgVar = new MfMsgVar();
		List<MfMsgVar> mfMsgVarList = mfMsgVarFeign.getAll(mfMsgVar);
		model.addAttribute("formmsgconf0002", formmsgconf0002);
		model.addAttribute("mfMsgVarList", mfMsgVarList);
		model.addAttribute("query", "");
		return "/component/msgconf/MfMsgVar_List";
	}

	/**
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData,String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String varId = WaterIdUtil.getWaterId();
		try {
			FormData formmsgconf0002 = formService.getFormData("msgconf0002");
			getFormValue(formmsgconf0002, getMapByJson(ajaxData));
			if (this.validateFormData(formmsgconf0002)) {
				MfMsgVar mfMsgVar = new MfMsgVar();
				setObjValue(formmsgconf0002, mfMsgVar);
				mfMsgVar.setVarId(varId);
				mfMsgVar.setFlag("0");
				mfMsgVarFeign.insert(mfMsgVar);
				getTableData(mfMsgVar, tableId, dataMap);// 获取列表
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw new Exception(e.getMessage());
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
	public Map<String, Object> updateAjax(String ajaxData,String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formmsgconf0002 = formService.getFormData("msgconf0002");
			getFormValue(formmsgconf0002, getMapByJson(ajaxData));
			if (this.validateFormData(formmsgconf0002)) {
				MfMsgVar mfMsgVar = new MfMsgVar();
				setObjValue(formmsgconf0002, mfMsgVar);
				mfMsgVarFeign.update(mfMsgVar);
				getTableData(mfMsgVar, tableId, dataMap);// 获取列表
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw new Exception(e.getMessage());
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
	public Map<String, Object> getByIdAjax(String ajaxData,String varId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formmsgconf0002 = formService.getFormData("msgconf0002");
		MfMsgVar mfMsgVar = new MfMsgVar();
		mfMsgVar.setVarId(varId);
		mfMsgVar = mfMsgVarFeign.getById(mfMsgVar);
		getObjValue(formmsgconf0002, mfMsgVar, formData);
		if (mfMsgVar != null) {
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
	public Map<String, Object> deleteAjax(String varId,String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfMsgVar mfMsgVar = new MfMsgVar();
		try {
			mfMsgVar.setVarId(varId);
			mfMsgVarFeign.delete(mfMsgVar);
			getTableData(mfMsgVar, tableId, dataMap);// 获取列表
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

}

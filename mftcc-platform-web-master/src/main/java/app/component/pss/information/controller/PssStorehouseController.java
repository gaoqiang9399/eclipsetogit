package app.component.pss.information.controller;

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

import app.base.User;
import app.component.pss.information.entity.PssFreightSpace;
import app.component.pss.information.entity.PssStorehouse;
import app.component.pss.information.feign.PssStorehouseFeign;
import app.component.pss.utils.PssPublicUtil;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.WaterIdUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Title: PssStorehouseAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Aug 16 18:28:15 CST 2017
 **/
@Controller
@RequestMapping("/pssStorehouse")
public class PssStorehouseController extends BaseFormBean {
	// 注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private PssStorehouseFeign pssStorehouseFeign;
	// 全局变量
	// 表单变量

	/**
	 * 获取列表数据
	 * 
	 * @return
	 * @throws Exception
	 */
	private void getTableData(String tableId, PssStorehouse pssStorehouse) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "tableTag", pssStorehouseFeign.getAll(pssStorehouse), null, true);
		Map<String, Object> dataMap = new HashMap<String, Object>();
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
		ActionContext.initialize(request, response);
		model.addAttribute("query", "");
		return "/component/pss/information/PssStorehouse_List";
	}

	@RequestMapping(value = "/getInputPage")
	public String getInputPage(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssStorehouse pssStorehouse = new PssStorehouse();
		pssStorehouse.setStorehouseId(WaterIdUtil.getWaterId());
		pssStorehouse.setFlag("0");
		FormData formpssstorehouse0002 = formService.getFormData("pssstorehouse0002");
		getObjValue(formpssstorehouse0002, pssStorehouse);

		JSONObject json = new JSONObject();
		CodeUtils codeUtils = new CodeUtils();
		JSONArray enabledStatusListArray = codeUtils.getJSONArrayByKeyName("PSS_ENABLED_STATUS");
		for (int i = 0; i < enabledStatusListArray.size(); i++) {
			JSONObject enabledStatus = enabledStatusListArray.getJSONObject(i);
			enabledStatus.put("id", enabledStatus.getString("optCode"));
			enabledStatus.put("name", enabledStatus.getString("optName"));
		}
		json.put("enabledStatus", enabledStatusListArray);
		String ajaxData = json.toString();

		model.addAttribute("formpssstorehouse0002", formpssstorehouse0002);
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("query", "");
		return "/component/pss/information/PssStorehouse_Input";
	}

	@RequestMapping(value = "/getDetailPage")
	public String getDetailPage(Model model, String storehouseId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formpssstorehouse0002 = formService.getFormData("pssstorehouse0002");
		PssStorehouse pssStorehouse = new PssStorehouse();
		pssStorehouse.setStorehouseId(storehouseId);
		pssStorehouse = pssStorehouseFeign.getById(pssStorehouse);
		getObjValue(formpssstorehouse0002, pssStorehouse);

		JSONObject json = new JSONObject();
		CodeUtils codeUtils = new CodeUtils();
		JSONArray enabledStatusListArray = codeUtils.getJSONArrayByKeyName("PSS_ENABLED_STATUS");
		for (int i = 0; i < enabledStatusListArray.size(); i++) {
			JSONObject enabledStatus = enabledStatusListArray.getJSONObject(i);
			enabledStatus.put("id", enabledStatus.getString("optCode"));
			enabledStatus.put("name", enabledStatus.getString("optName"));
		}
		json.put("enabledStatus", enabledStatusListArray);
		String ajaxData = json.toString();

		model.addAttribute("formpssstorehouse0002", formpssstorehouse0002);
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("query", "");
		return "/component/pss/information/PssStorehouse_Detail";
	}

	@RequestMapping(value = "/getListPageAjax")
	@ResponseBody
	public Map<String, Object> getListPageAjax(String ajaxData,Integer pageSize,Integer pageNo,String tableId,String tableType) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssStorehouse pssStorehouse = new PssStorehouse();
		pssStorehouse.setCustomQuery(ajaxData);// 自定义查询参数赋值
		pssStorehouse.setCustomSorts(ajaxData);// 自定义排序参数赋值
		pssStorehouse.setCriteriaList(pssStorehouse, ajaxData);// 我的筛选
		Ipage ipage = this.getIpage();
		ipage.setPageSize(pageSize);
		ipage.setPageNo(pageNo);// 异步传页面翻页参数
		ipage.setParams(this.setIpageParams("pssStorehouse", pssStorehouse));
		List<PssStorehouse> pssStorehouseList = (List<PssStorehouse>) pssStorehouseFeign.findByPage(ipage).getResult();
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
		// 处理需要隐藏的列
		// Map<String, String> map = new HashMap<String, String>();
		// tableHtml = DealTableUtil.dealTabStr(tableHtml, map);
		ipage.setResult(tableHtml);
		dataMap.put("ipage", ipage);
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
		FormData formpssstorehouse0002 = formService.getFormData("pssstorehouse0002");
		PssStorehouse pssStorehouse = new PssStorehouse();
		List<PssStorehouse> pssStorehouseList = pssStorehouseFeign.getAll(pssStorehouse);
		model.addAttribute("formpssstorehouse0002", formpssstorehouse0002);
		model.addAttribute("pssStorehouseList", pssStorehouseList);
		model.addAttribute("query", "");
		return "/component/pss/information/PssStorehouse_List";
	}

	/**
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData,String tableId,String pssFreightSpacesJson) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		//HttpServletRequest request = request;
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formpssstorehouse0002 = formService.getFormData("pssstorehouse0002");
			getFormValue(formpssstorehouse0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpssstorehouse0002)) {
				PssStorehouse pssStorehouse = new PssStorehouse();
				setObjValue(formpssstorehouse0002, pssStorehouse);
				pssStorehouse.setRegOpName(User.getRegName(request));
				pssStorehouse.setRegOpNo(User.getRegNo(request));
				pssStorehouse.setRegBrName(User.getOrgName(request));
				pssStorehouse.setRegBrNo(User.getOrgNo(request));
				pssStorehouse.setFlag("1");
				pssStorehouse.setDisplayFlag("1");

				List<PssFreightSpace> pssFreightSpaceList = PssPublicUtil.getMapByJsonObj(new PssFreightSpace(),
						pssFreightSpacesJson);
				pssStorehouseFeign.insert(pssStorehouse, pssFreightSpaceList);
				getTableData(tableId,pssStorehouse);// 获取列表
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
	public Map<String, Object> updateAjax(String ajaxData,String pssFreightSpacesJson,String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssStorehouse pssStorehouse = new PssStorehouse();
		try {
			FormData formpssstorehouse0002 = formService.getFormData("pssstorehouse0002");
			getFormValue(formpssstorehouse0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpssstorehouse0002)) {
				pssStorehouse = new PssStorehouse();
				setObjValue(formpssstorehouse0002, pssStorehouse);

				List<PssFreightSpace> pssFreightSpaceList = PssPublicUtil.getMapByJsonObj(new PssFreightSpace(),
						pssFreightSpacesJson);
				pssStorehouseFeign.update(pssStorehouse, pssFreightSpaceList);
				getTableData(tableId,pssStorehouse);// 获取列表
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

	@RequestMapping(value = "/updateFlagAjax")
	@ResponseBody
	public Map<String, Object> updateFlagAjax(String ajaxData,String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			JSONObject jobj = JSONObject.fromObject(ajaxData);
			FormData formpssstorehouse0002 = formService.getFormData("pssstorehouse0002");
			getFormValue(formpssstorehouse0002, jobj);
			PssStorehouse pssStorehouse = new PssStorehouse();
			setObjValue(formpssstorehouse0002, pssStorehouse);
			int count = pssStorehouseFeign.updateFlag(pssStorehouse);
			if (count > 0) {
				pssStorehouse = pssStorehouseFeign.getById(pssStorehouse);
				ArrayList<PssStorehouse> pssStorehouseList = new ArrayList<PssStorehouse>();
				pssStorehouseList.add(pssStorehouse);
				getTableData(pssStorehouseList,tableId);
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

	private void getTableData(List<PssStorehouse> list,String tableId) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "thirdTableTag", list, null, true);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("tableData", tableHtml);
	}

	/**
	 * AJAX获取查看
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String storehouseId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formpssstorehouse0002 = formService.getFormData("pssstorehouse0002");
		PssStorehouse pssStorehouse = new PssStorehouse();
		pssStorehouse.setStorehouseId(storehouseId);
		pssStorehouse = pssStorehouseFeign.getById(pssStorehouse);
		getObjValue(formpssstorehouse0002, pssStorehouse, formData);
		if (pssStorehouse != null) {
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
	
	// public Map<String, Object> deleteAjax(String ajaxData) throws Exception{
	
	// ActionContext.initialize(request,
	// response);
	// Map<String, Object> dataMap = new HashMap<String, Object>();
	// pssStorehouse = new PssStorehouse();
	// pssStorehouse.setStorehouseId(storehouseId);
	// try {
	// JSONObject jb = JSONObject.fromObject(ajaxData);
	// pssStorehouse = (PssStorehouse)JSONObject.toBean(jb,
	// PssStorehouse.class);
	// pssStorehouse.
	// pssStorehouseFeign.delete(pssStorehouse);
	// getTableData();//获取列表
	// dataMap.put("flag", "success");
	// dataMap.put("msg", "成功");
	// } catch (Exception e) {
	// System.out.println(e.getMessage());
	// dataMap.put("flag", "error");
	// dataMap.put("msg", "失败");
	// throw new Exception(e.getMessage());
	// }
	// return dataMap;
	// }
	@RequestMapping(value="/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String storehouseId,String tableId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssStorehouse pssStorehouse = new PssStorehouse();
		pssStorehouse.setStorehouseId(storehouseId);
		pssStorehouse.setDisplayFlag("0");
		try {
			pssStorehouseFeign.updateDisplayFlag(pssStorehouse);
			getTableData(tableId,pssStorehouse);// 获取列表
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

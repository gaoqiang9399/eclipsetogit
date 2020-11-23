package app.component.pms.controller;

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
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;

import app.base.cache.feign.BusiCacheFeign;
import app.component.pms.entity.PmsDataRang;
import app.component.pms.feign.PmsDataRangFeign;
import app.component.pmsinterface.PmsInterfaceFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/pmsDataRang")
public class PmsDataRangController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private PmsDataRangFeign pmsDataRangFeign;
	@Autowired
	private PmsInterfaceFeign pmsInterfaceFeign;
	@Autowired
	private BusiCacheFeign busiCacheFeign;

	/**
	 * 打开数据权限编辑页面
	 */
	@RequestMapping(value = "/input")
	public String input(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formpmsdatarangadd = formService.getFormData("pmsdatarangadd");
		model.addAttribute("formpmsdatarangadd", formpmsdatarangadd);
		model.addAttribute("query", "");
		return "/component/pms/PmsDataRang_input";
	}

	@RequestMapping(value = "/findByPage")
	public String findByPage(Model model, String ajaxData) throws Exception {
		//FormService formService = new FormService();
		ActionContext.initialize(request, response);
		PmsDataRang pmsDataRang = new PmsDataRang();

		this.getIpage().setParams(this.setIpageParams("pmsDataRang", pmsDataRang));
		List<PmsDataRang> pmsDataRangList = (List<PmsDataRang>) pmsDataRangFeign
				.findByPage(this.getIpage()).getResult();
		model.addAttribute("pmsDataRangList", pmsDataRangList);
		model.addAttribute("query", "");
		return "/component/pms/PmsDataRang_List";
	}

	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("query", "");
		return "getListPage";
	}

	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData) throws Exception {
		//FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PmsDataRang pmsDataRang = new PmsDataRang();
		try {
			pmsDataRang.setCustomQuery(ajaxData);// 自定义查询参数赋值
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			
			ipage.setParams(this.setIpageParams("pmsDataRang", pmsDataRang));
			ipage = pmsDataRangFeign.findByPage(ipage);
			// 返回相应的HTML方法
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
	 * 借口测试
	 */
	@RequestMapping(value = "/getById")
	public Map<String, Object> getById(Model model, String ajaxData) throws Exception {
		//FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String[] ArrayRole = new String[1];
		ArrayRole[0] = "372";
		dataMap.put("data", pmsInterfaceFeign.getPmsDataRangRoleByRole(ArrayRole));
		model.addAttribute("dataMap", dataMap);
		return dataMap;
	}

	/**
	 * 
	 * 方法描述： 打开数据权限编辑页面
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-7-3 上午11:04:51
	 */
	@RequestMapping(value = "/getEditById")
	public String getEditById(Model model, String funNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		//Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formpmsdatarangadd = formService.getFormData("pmsdatarangadd");
		getFormValue(formpmsdatarangadd);
		PmsDataRang pmsDataRang = new PmsDataRang();
		pmsDataRang.setFunNo(funNo);
		pmsDataRang = pmsDataRangFeign.getById(pmsDataRang);
		getObjValue(formpmsdatarangadd, pmsDataRang);
		model.addAttribute("formpmsdatarangadd", formpmsdatarangadd);
		model.addAttribute("query", "");
		return "/component/pms/PmsDataRang_edit";
	}

	@RequestMapping(value = "/inputAjax")
	@ResponseBody
	public Map<String, Object> inputAjax(String query) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PmsDataRang pmsDataRang = new PmsDataRang();
		FormData formpms0001 = formService.getFormData("pms0001");
		getObjValue(formpms0001, pmsDataRang);
		JsonFormUtil jfu = new JsonFormUtil();
		String formHtml = jfu.getJsonStr(formpms0001, "bigFormTag", query);
		dataMap.put("formHtml", formHtml);
		return dataMap;
	}

	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData,String query) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formpms0001 = formService.getFormData("pms0001");
			getFormValue(formpms0001, getMapByJson(ajaxData));
			if (this.validateFormData(formpms0001)) {
				PmsDataRang pmsDataRang = new PmsDataRang();
				setObjValue(formpms0001, pmsDataRang);
				pmsDataRangFeign.insert(pmsDataRang);
				JsonFormUtil jfu = new JsonFormUtil();
				String formHtml = jfu.getJsonStr(formpms0001, "bigFormTag", query);
				dataMap.put("formHtml", formHtml);
				dataMap.put("flag", "success");
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
	 * 方法描述： 刷新缓存
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-7-4 下午7:00:48
	 */
	@RequestMapping(value = "/refreshDataRangAjax")
	@ResponseBody
	public Map<String, Object> refreshDataRangAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			busiCacheFeign.initPmsDataRangCache();
			dataMap.put("msg", MessageEnum.SUCCEED_REFRESH.getMessage());
			dataMap.put("flag", "success");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}

	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String funNo,String query) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PmsDataRang pmsDataRang = new PmsDataRang();
		FormData formpms0001 = formService.getFormData("pms0001");
		pmsDataRang.setFunNo(funNo);
		pmsDataRang = pmsDataRangFeign.getById(pmsDataRang);
		getObjValue(formpms0001, pmsDataRang);
		JsonFormUtil jfu = new JsonFormUtil();
		String formHtml = jfu.getJsonStr(formpms0001, "bigFormTag", query);
		dataMap.put("formHtml", formHtml);
		if (pmsDataRang != null) {
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
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
		PmsDataRang pmsDataRang = new PmsDataRang();
		try {
			FormData formpmsdatarangadd = formService.getFormData("pmsdatarangadd");
			getFormValue(formpmsdatarangadd, getMapByJson(ajaxData));
			if (this.validateFormData(formpmsdatarangadd)) {
				setObjValue(formpmsdatarangadd, pmsDataRang);
				pmsDataRangFeign.update(pmsDataRang);
				List<PmsDataRang> list = new ArrayList<PmsDataRang>();
				list.add(pmsDataRang);
				getTableData(list,tableId);// 获取列表
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

	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String funNo) throws Exception {
		//FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PmsDataRang pmsDataRang = new PmsDataRang();
		pmsDataRang.setFunNo(funNo);
		try {
			pmsDataRangFeign.delete(pmsDataRang);
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

	private void getTableData(List<PmsDataRang> list,String tableId) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "tableTag", list, null, true);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("tableData", tableHtml);
	}

	@RequestMapping(value = "/insert")
	public Map<String, Object> insert(Model model,String ajaxData) throws Exception {
		//FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PmsDataRang pmsDataRang = new PmsDataRang();
		JSONObject jb = (JSONObject) JSONArray.fromObject(ajaxData).get(0);
		pmsDataRang = (PmsDataRang) JSONObject.toBean(jb, PmsDataRang.class);
		pmsDataRangFeign.insert(pmsDataRang);
		dataMap.put("flag", "success");
		dataMap.put("data", pmsDataRang);
		return dataMap;
	}

	@RequestMapping(value = "/update")
	public Map<String, Object> update(Model model, String ajaxData) throws Exception {
	//	FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PmsDataRang pmsDataRang = new PmsDataRang();
		JSONObject jb = (JSONObject) JSONArray.fromObject(ajaxData).get(0);
		pmsDataRang = (PmsDataRang) JSONObject.toBean(jb, PmsDataRang.class);
		pmsDataRangFeign.update(pmsDataRang);
		dataMap.put("flag", "success");
		dataMap.put("data", pmsDataRang);
		return dataMap;
	}

	@RequestMapping(value = "/delete")
	public Map<String, Object> delete(Model model, String ajaxData) throws Exception {
		//FormService formService = new FormService();
		ActionContext.initialize(request, response);
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PmsDataRang pmsDataRang = new PmsDataRang();
		JSONObject jb = (JSONObject) JSONArray.fromObject(ajaxData).get(0);
		pmsDataRang = (PmsDataRang) JSONObject.toBean(jb, PmsDataRang.class);
		pmsDataRangFeign.delete(pmsDataRang);
		dataMap.put("flag", "success");
		return dataMap;
	}
}

package app.component.pms.controller;

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

import app.component.common.EntityUtil;
import app.component.pms.entity.PmsDataSub;
import app.component.pms.feign.PmsDataSubFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONArray;

/**
 * Title: PmsDataSubAction.java Description:
 * 
 * @author:@dhcc.com.cn
 * @Wed Sep 21 02:27:39 GMT 2016
 **/
@Controller
@RequestMapping("/pmsDataSub")
public class PmsDataSubController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入PmsDataSubBo
	@Autowired
	private PmsDataSubFeign pmsDataSubFeign;
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
	public String getListPage(Model model) throws Exception {

		ActionContext.initialize(request, response);
		model.addAttribute("query", "");
		return "/component/pms/PmsDataSub_List";
	}

	@RequestMapping(value = "/getListPageForConf")
	public String getListPageForConf(Model model, String funNo) throws Exception {
		ActionContext.initialize(request, response);
		
		model.addAttribute("query", "");
		model.addAttribute("funNo",funNo);
		return "/component/pms/PmsDataSub_List_Conf";
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
		//FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PmsDataSub pmsDataSub = new PmsDataSub();
		try {
			pmsDataSub.setCustomQuery(ajaxData);// 自定义查询参数赋值
			pmsDataSub.setCriteriaList(pmsDataSub, ajaxData);// 我的筛选
			// this.getRoleConditions(pmsDataSub,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = pmsDataSubFeign.findByPage(ipage, pmsDataSub);
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

	@RequestMapping(value = "/findByPageForConfAjax")
	@ResponseBody
	public Map<String, Object> findByPageForConfAjax(String funNo) throws Exception {
		//FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PmsDataSub pmsDataSub = new PmsDataSub();
		try {
			pmsDataSub.setFunNo(funNo);
			// 自定义查询Bo方法
			List<PmsDataSub> list = pmsDataSubFeign.findByPageForConf(pmsDataSub);
			JSONArray jsonarray = JSONArray.fromObject(list);
			for (int i = 0; i < jsonarray.size(); i++) {
				jsonarray.getJSONObject(i).put("id", jsonarray.getJSONObject(i).getString("pmsLv"));
				jsonarray.getJSONObject(i).put("name", jsonarray.getJSONObject(i).getString("pmsName"));
				if (!"0".equals(jsonarray.getJSONObject(i).getString("funNo"))) {
					jsonarray.getJSONObject(i).put("checked", true);
				} else {

				}
				// jsonarray.getJSONObject(i).put("pId",);
			}
			dataMap.put("zNodes", jsonarray);
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
			FormData formpms2002 = formService.getFormData("pms2002");
			getFormValue(formpms2002, getMapByJson(ajaxData));
			if (this.validateFormData(formpms2002)) {
				PmsDataSub pmsDataSub = new PmsDataSub();
				setObjValue(formpms2002, pmsDataSub);
				pmsDataSubFeign.insert(pmsDataSub);
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

	@RequestMapping(value = "/insertConfAjax")
	@ResponseBody
	public Map<String, Object> insertConfAjax(String ajaxData,String funNo) throws Exception {
		//FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			pmsDataSubFeign.insertConf(ajaxData, funNo);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SAVE.getMessage());
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
		FormData formpms2002 = formService.getFormData("pms2002");
		getFormValue(formpms2002, getMapByJson(ajaxData));
		PmsDataSub pmsDataSubJsp = new PmsDataSub();
		setObjValue(formpms2002, pmsDataSubJsp);
		PmsDataSub pmsDataSub = pmsDataSubFeign.getById(pmsDataSubJsp);
		if (pmsDataSub != null) {
			try {
				pmsDataSub = (PmsDataSub) EntityUtil.reflectionSetVal(pmsDataSub, pmsDataSubJsp,
						getMapByJson(ajaxData));
				pmsDataSubFeign.update(pmsDataSub);
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
		PmsDataSub pmsDataSub = new PmsDataSub();
		try {
			FormData formpms2002 = formService.getFormData("pms2002");
			getFormValue(formpms2002, getMapByJson(ajaxData));
			if (this.validateFormData(formpms2002)) {
				pmsDataSub = new PmsDataSub();
				setObjValue(formpms2002, pmsDataSub);
				pmsDataSubFeign.update(pmsDataSub);
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
	public Map<String, Object> getByIdAjax(String pmsLv,String funNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formpms2002 = formService.getFormData("pms2002");
		PmsDataSub pmsDataSub = new PmsDataSub();
		pmsDataSub.setPmsLv(pmsLv);
		pmsDataSub.setFunNo(funNo);
		pmsDataSub = pmsDataSubFeign.getById(pmsDataSub);
		getObjValue(formpms2002, pmsDataSub, formData);
		if (pmsDataSub != null) {
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
	public Map<String, Object> deleteAjax(String pmsLv,String funNo) throws Exception {
		//FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PmsDataSub pmsDataSub = new PmsDataSub();
		pmsDataSub.setPmsLv(pmsLv);
		pmsDataSub.setFunNo(funNo);
		try {
			pmsDataSubFeign.delete(pmsDataSub);
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
		FormData formpms2002 = formService.getFormData("pms2002");
		model.addAttribute("formpms2002", formpms2002);
		model.addAttribute("query", "");
		return "/component/pms/PmsDataSub_Insert";
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
		FormData formpms2002 = formService.getFormData("pms2002");
		getFormValue(formpms2002);
		PmsDataSub pmsDataSub = new PmsDataSub();
		setObjValue(formpms2002, pmsDataSub);
		pmsDataSubFeign.insert(pmsDataSub);
		getObjValue(formpms2002, pmsDataSub);
		this.addActionMessage(model, "保存成功");
		List<PmsDataSub> pmsDataSubList = (List<PmsDataSub>) pmsDataSubFeign.findByPage(this.getIpage(), pmsDataSub).getResult();
		model.addAttribute("formpms2002", formpms2002);
		model.addAttribute("pmsDataSubList", pmsDataSubList);
		model.addAttribute("query", "");
		return "/component/pms/PmsDataSub_Detail";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String pmsLv,String funNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formpms2001 = formService.getFormData("pms2001");
		getFormValue(formpms2001);
		PmsDataSub pmsDataSub = new PmsDataSub();
		pmsDataSub.setPmsLv(pmsLv);
		pmsDataSub.setFunNo(funNo);
		pmsDataSub = pmsDataSubFeign.getById(pmsDataSub);
		getObjValue(formpms2001, pmsDataSub);
		model.addAttribute("formpms2001", formpms2001);
		model.addAttribute("query", "");
		return "/component/pms/PmsDataSub_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String pmsLv,String funNo) throws Exception {
		//FormService formService = new FormService();
		ActionContext.initialize(request, response);
		PmsDataSub pmsDataSub = new PmsDataSub();
		pmsDataSub.setPmsLv(pmsLv);
		pmsDataSub.setFunNo(funNo);
		pmsDataSubFeign.delete(pmsDataSub);
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
		FormData formpms2002 = formService.getFormData("pms2002");
		getFormValue(formpms2002);
		boolean validateFlag = this.validateFormData(formpms2002);
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
		FormData formpms2002 = formService.getFormData("pms2002");
		getFormValue(formpms2002);
		boolean validateFlag = this.validateFormData(formpms2002);
	}
}

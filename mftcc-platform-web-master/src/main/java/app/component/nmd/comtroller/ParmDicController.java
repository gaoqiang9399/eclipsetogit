package app.component.nmd.comtroller;

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

import app.base.cache.feign.BusiCacheFeign;
import app.component.nmd.entity.ParmDic;
import app.component.nmd.feign.ParmDicFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONObject;

/**
 * Title: ParmDicAction.java Description:
 * 
 * @author:jiangyunxin@dhcc.com.cn
 * @Thu Apr 10 09:11:38 GMT 2014
 **/
@Controller
@RequestMapping("/parmDic")
public class ParmDicController extends BaseFormBean {

	// 页面传值

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入ParmDicBo
	@Autowired
	private ParmDicFeign parmDicFeign;
	@Autowired
	private BusiCacheFeign busiCacheFeign;

	/***
	 * 列表数据查询的异步方法
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			ParmDic parmDic = new ParmDic();
			JSONObject jb = JSONObject.fromObject(ajaxData);
			parmDic = (ParmDic) JSONObject.toBean(jb, ParmDic.class);
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);
			ipage.setParams(this.setIpageParams("parmDic", parmDic));
			ipage = parmDicFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
			dataMap.put("flag", "success");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
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
			FormData formnmd0002 = formService.getFormData("nmd0002");
			getFormValue(formnmd0002, getMapByJson(ajaxData));
			if (this.validateFormData(formnmd0002)) {
				ParmDic parmDic = new ParmDic();
				setObjValue(formnmd0002, parmDic);
				if (parmDicFeign.getById(parmDic) != null) {
					dataMap.put("flag", "error");
					dataMap.put("msg", MessageEnum.EXIST_INFORMATION_EVAL.getMessage());
				} else {
					parmDicFeign.insert(parmDic);
					dataMap.put("flag", "success");
					dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
					// tableId = "tablenmd0002";
					// keyName = parmDic.getKeyName();
					getTableDataIpage(dataMap, parmDic.getKeyName(), "tablenmd0002");
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
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertForSelectAjax")
	@ResponseBody
	public Map<String, Object> insertForSelectAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formnmd0004 = formService.getFormData("nmd0004");
			getFormValue(formnmd0004, getMapByJson(ajaxData));

			if (this.validateFormData(formnmd0004)) {
				ParmDic parmDic = new ParmDic();
				setObjValue(formnmd0004, parmDic);
				if (parmDicFeign.getById(parmDic) != null) {
					dataMap.put("flag", "error");
					dataMap.put("msg", MessageEnum.EXIST_INFORMATION_EVAL.getMessage());
				} else {
					ParmDic parmDic2=parmDicFeign.insertForSelect(parmDic);
					dataMap.put("flag", "success");
					dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
					String tableId = "tablenmd0002";
					String keyName = parmDic.getKeyName();
					dataMap.put("parmDicInfo", parmDic2);
					getTableDataIpage(dataMap, keyName, tableId);
					// 刷新缓存
					busiCacheFeign.refreshAllParmDic();
				}
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			// logger.error("选择组件新增数据字典项失败", e);
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
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
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formnmd0002 = formService.getFormData("nmd0002");
			getFormValue(formnmd0002, getMapByJson(ajaxData));
			if (this.validateFormData(formnmd0002)) {
				ParmDic parmDic = new ParmDic();
				setObjValue(formnmd0002, parmDic);
				parmDicFeign.update(parmDic);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
				String tableId = "tablenmd0002";
				String keyName = parmDic.getKeyName();
				getTableDataIpage(dataMap, keyName, tableId);
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

	private void getTableDataIpage(Map<String, Object> dataMap, String keyName, String tableId) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		ParmDic parmDic = new ParmDic();
		parmDic.setKeyName(keyName);
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("parmDic", parmDic));
		String tableHtml = jtu.getJsonStr(tableId, "tableTag",
				(List) parmDicFeign.findByPage(ipage).getResult(), ipage, true);
		ipage.setResult(tableHtml);
		dataMap.put("ipage", ipage);
	}

	/**
	 * Ajax异步删除
	 * 
	 * @param keyName
	 * @param optCode
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String ajaxData, String keyName, String optCode) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ParmDic parmDic = new ParmDic();
		parmDic.setKeyName(keyName);
		parmDic.setOptCode(optCode);
		try {
			parmDicFeign.del(parmDic);
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
	 * 分页查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPage")
	public String findByPage(Model model, String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formnmd3013 = formService.getFormData("nmd3013");
		ParmDic parmDic = new ParmDic();
		getFormValue(formnmd3013);
		setObjValue(formnmd3013, parmDic);
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("parmDic", parmDic));
		List parmDicList = (List) parmDicFeign.findByPage(ipage).getResult();
		model.addAttribute("formnmd3013", formnmd3013);
		model.addAttribute("parmDic", parmDic);
		model.addAttribute("parmDicList", parmDicList);
		model.addAttribute("query", "");
		return "component/nmd/ParmDic_List";
	}

	/**
	 * 分页查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageForManage")
	public String findByPageForManage(Model model, String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formnmd3017 = formService.getFormData("nmd3017");
		ParmDic parmDic = new ParmDic();
		getFormValue(formnmd3017);
		setObjValue(formnmd3017, parmDic);
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("parmDic", parmDic));
		List parmDicList = (List) parmDicFeign.findByPage(ipage).getResult();
		model.addAttribute("formnmd3017", formnmd3017);
		model.addAttribute("parmDic", parmDic);
		model.addAttribute("parmDicList", parmDicList);
		model.addAttribute("query", "");
		return "component/nmd/ParmDic_ListForManage";
	}

	/**
	 * 分页查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageViewForManage")
	public String findByPageViewForManage(Model model, String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formnmd3017 = formService.getFormData("nmd3017");
		ParmDic parmDic = new ParmDic();
		getFormValue(formnmd3017);
		setObjValue(formnmd3017, parmDic);
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("parmDic", parmDic));
		List parmDicList = (List) parmDicFeign.findByPage(ipage).getResult();
		model.addAttribute("formnmd3017", formnmd3017);
		model.addAttribute("parmDic", parmDic);
		model.addAttribute("parmDicList", parmDicList);
		model.addAttribute("query", "");
		return "component/nmd/ParmDic_ListViewForManage";
	}

	/**
	 * 分页查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageView")
	public String findByPageView(Model model, String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formnmd3013 = formService.getFormData("nmd3013");
		ParmDic parmDic = new ParmDic();
		getFormValue(formnmd3013);
		setObjValue(formnmd3013, parmDic);
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("parmDic", parmDic));
		List parmDicList = (List) parmDicFeign.findByPage(ipage).getResult();
		model.addAttribute("formnmd3013", formnmd3013);
		model.addAttribute("parmDic", parmDic);
		model.addAttribute("parmDicList", parmDicList);
		model.addAttribute("query", "");
		return "component/nmd/ParmDic_ListView";
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
		FormData formnmd3014 = formService.getFormData("nmd3014");
		getFormValue(formnmd3014);
		this.changeFormProperty(formnmd3014, "keyName", "readonly", "1");
		model.addAttribute("formnmd3014", formnmd3014);
		model.addAttribute("query", "");
		return "component/nmd/ParmDic_Insert";
	}

	/**
	 * 新增页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/inputForManage")
	public String inputForManage(Model model, String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formnmd3018 = formService.getFormData("nmd3018");
		getFormValue(formnmd3018);
		this.changeFormProperty(formnmd3018, "keyName", "readonly", "1");
		model.addAttribute("formnmd3018", formnmd3018);
		model.addAttribute("query", "");
		return "component/nmd/ParmDic_InsertForManage";
	}

	/**
	 * 选择组件新增数据字典页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/inputForSelect")
	public String inputForSelect(Model model, String keyName) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formnmd0004 = formService.getFormData("nmd0004");
		getFormValue(formnmd0004);
		ParmDic parmDic = new ParmDic();
		parmDic.setKeyName(keyName);
		setObjValue(formnmd0004, parmDic);
		this.changeFormProperty(formnmd0004, "keyName", "readonly", "1");
		model.addAttribute("formnmd0004", formnmd0004);
		model.addAttribute("query", "");
		return "component/nmd/ParmDicAction_inputForSelect";
	}

	/**
	 * 新增保存操作
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formnmd3014 = formService.getFormData("nmd3014");
		FormData formnmd3013 = formService.getFormData("nmd3013");
		getFormValue(formnmd3014);
		getFormValue(formnmd3013);
		ParmDic parmDic = new ParmDic();
		setObjValue(formnmd3014, parmDic);
		parmDicFeign.insert(parmDic);
		// getObjValue(formnmd3014, parmDic);
		String keyName = parmDic.getKeyName();
		this.addActionMessage(model, "操作成功");
		parmDic.setKeyName(keyName);

		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("parmDic", parmDic));
		List parmDicList = (List) parmDicFeign.findByPage(ipage).getResult();
		model.addAttribute("formnmd3014", formnmd3014);
		model.addAttribute("formnmd3013", formnmd3013);
		model.addAttribute("parmDicList", parmDicList);
		model.addAttribute("query", "");
		return "component/nmd/ParmDic_List";

	}

	/**
	 * 新增保存操作
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertForManage")
	public String insertForManage(Model model, String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formnmd3018 = formService.getFormData("nmd3018");
		FormData formnmd3017 = formService.getFormData("nmd3017");
		getFormValue(formnmd3018);
		getFormValue(formnmd3017);
		ParmDic parmDic = new ParmDic();
		setObjValue(formnmd3018, parmDic);
		parmDicFeign.insert(parmDic);
		String keyName = parmDic.getKeyName();
		this.addActionMessage(model, "操作成功");
		parmDic.setKeyName(keyName);

		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("parmDic", parmDic));
		List parmDicList = (List) parmDicFeign.findByPage(ipage).getResult();
		model.addAttribute("formnmd3017", formnmd3017);
		model.addAttribute("parmDicList", parmDicList);
		model.addAttribute("formnmd3018", formnmd3018);
		model.addAttribute("query", "");
		return "component/nmd/ParmDic_ListForManage";

	}

	/**
	 * 修改保存操作
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update")
	public String update(Model model, String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formnmd3014 = formService.getFormData("nmd3014");
		getFormValue(formnmd3014);
		ParmDic parmDic = new ParmDic();
		setObjValue(formnmd3014, parmDic);
		parmDicFeign.update(parmDic);
		getObjValue(formnmd3014, parmDic);
		this.changeFormProperty(formnmd3014, "keyName", "readonly", "1");
		this.changeFormProperty(formnmd3014, "optCode", "readonly", "1");
		model.addAttribute("formnmd3014", formnmd3014);
		model.addAttribute("query", "");
		return "component/nmd/ParmDic_Detail";
	}

	/**
	 * 删除操作
	 * @param optCode 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delForManage")
	public String delForManage(Model model, String keyName, String optCode) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formnmd3017 = formService.getFormData("nmd3017");
		ParmDic parmDic = new ParmDic();
		parmDic.setKeyName(keyName);
		parmDic.setOptCode(optCode);
		parmDicFeign.del(parmDic);
		this.addActionMessage(model, "删除成功");
		parmDic.setKeyName(keyName);
		getObjValue(formnmd3017, parmDic);
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("parmDic", parmDic));
		List parmDicList = (List) parmDicFeign.findByPage(ipage).getResult();
		model.addAttribute("formnmd3017", formnmd3017);
		model.addAttribute("parmDicList", parmDicList);
		model.addAttribute("query", "");
		return "component/nmd/ParmDic_ListForManage";
	}

	/**
	 * 删除操作
	 * @param optCode 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/del")
	public String del(Model model, String keyName, String optCode) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formnmd3013 = formService.getFormData("nmd3013");
		getFormValue(formnmd3013);
		ParmDic parmDic = new ParmDic();
		parmDic.setKeyName(keyName);
		parmDic.setOptCode(optCode);
		parmDicFeign.del(parmDic);
		this.addActionMessage(model, "删除成功");
		parmDic.setKeyName(keyName);
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("parmDic", parmDic));
		List parmDicList = (List) parmDicFeign.findByPage(ipage).getResult();
		model.addAttribute("parmDic", parmDic);
		model.addAttribute("parmDicList", parmDicList);
		model.addAttribute("query", "");
		return "component/nmd/ParmDic_List";
	}

	/**
	 * 查询操作
	 * @param optCode 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdForManage")
	public String getByIdForManage(Model model, String keyName, String optCode) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formnmd3018 = formService.getFormData("nmd3018");
		ParmDic parmDic = new ParmDic();
		parmDic.setKeyName(keyName);
		parmDic.setOptCode(optCode);
		parmDic = parmDicFeign.getById(parmDic);
		getObjValue(formnmd3018, parmDic);
		this.changeFormProperty(formnmd3018, "keyName", "readonly", "1");
		this.changeFormProperty(formnmd3018, "optCode", "readonly", "1");
		model.addAttribute("formnmd3018", formnmd3018);
		model.addAttribute("query", "");
		return "component/nmd/ParmDic_DetailForManage";
	}

	/**
	 * 查询操作
	 * @param optCode 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String keyName, String optCode) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formnmd3014 = formService.getFormData("nmd3014");
		ParmDic parmDic = new ParmDic();
		parmDic.setKeyName(keyName);
		parmDic.setOptCode(optCode);
		parmDic = parmDicFeign.getById(parmDic);
		getObjValue(formnmd3014, parmDic);
		this.changeFormProperty(formnmd3014, "keyName", "readonly", "1");
		this.changeFormProperty(formnmd3014, "optCode", "readonly", "1");
		model.addAttribute("formnmd3014", formnmd3014);
		model.addAttribute("query", "");
		return "component/nmd/ParmDic_Detail";
	}

	/**
	 * 查询操作
	 * @param optCode 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdView")
	public String getByIdView(Model model, String keyName, String optCode) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formnmd3014 = formService.getFormData("nmd3014");
		ParmDic parmDic = new ParmDic();
		parmDic.setKeyName(keyName);
		parmDic.setOptCode(optCode);
		parmDic = parmDicFeign.getById(parmDic);
		getObjValue(formnmd3014, parmDic);
		String query = "query";
		model.addAttribute("formnmd3014", formnmd3014);
		model.addAttribute("query", query);
		return "component/nmd/ParmDic_DetailView";
	}

	/**
	 * 新增保存操作校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateInsert")
	public void validateInsert(Model model) {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formnmd3014 = formService.getFormData("nmd3014");
		getFormValue(formnmd3014);
		validateFormData(formnmd3014);
		ParmDic parmDic = new ParmDic();
		setObjValue(formnmd3014, parmDic);
		if (null != parmDicFeign.getById(parmDic)) {
			this.addActionError(model,"该选项已存在！");
		}

	}

	/**
	 * 新增保存操作校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateInsertForManage")
	public void validateInsertForManage(Model model) {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formnmd3018 = formService.getFormData("nmd3018");
		getFormValue(formnmd3018);
		validateFormData(formnmd3018);
		ParmDic parmDic = new ParmDic();
		setObjValue(formnmd3018, parmDic);
		if (null != parmDicFeign.getById(parmDic)) {
			this.addActionError(model,"该选项已存在！");
		}

	}

	/**
	 * 修改保存操作校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateUpdate")
	public void validateUpdate(Model model) {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formnmd3014 = formService.getFormData("nmd3014");
		getFormValue(formnmd3014);
		validateFormData(formnmd3014);

	}

	/**
	 * 清空按钮缓存
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/removeallForParmDicForManage")
	public String removeallForParmDicForManage(Model model, String keyName) throws Exception {
		try {
			// MBaseCache.getCache().reloadParmDic();
			this.addActionMessage(model, "清空按钮缓存成功!");
		} catch (Exception e) {
			e.printStackTrace();
			this.addActionError(model,"清空按钮缓存失败!");
		}

		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formnmd3017 = formService.getFormData("nmd3017");
		ParmDic parmDic = new ParmDic();
		getFormValue(formnmd3017);
		setObjValue(formnmd3017, parmDic);
		parmDic.setKeyName(keyName);
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("parmDic", parmDic));
		List parmDicList = (List) parmDicFeign.findByPage(ipage).getResult();
		model.addAttribute("formnmd3017", formnmd3017);
		model.addAttribute("query", "");
		return "list";
	}

	/**
	 * 清空按钮缓存
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/removeallForParmDic")
	public String removeallForParmDic(Model model, String keyName) throws Exception {
		try {
			// MBaseCache.getCache().reloadParmDic();
			this.addActionMessage(model, "清空按钮缓存成功!");
		} catch (Exception e) {
			e.printStackTrace();
			this.addActionError(model,"清空按钮缓存失败!");
		}

		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formnmd3013 = formService.getFormData("nmd3013");
		ParmDic parmDic = new ParmDic();
		getFormValue(formnmd3013);
		setObjValue(formnmd3013, parmDic);
		parmDic.setKeyName(keyName);
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("parmDic", parmDic));
		List parmDicList = (List) parmDicFeign.findByPage(ipage).getResult();
		model.addAttribute("formnmd3013", formnmd3013);
		model.addAttribute("query", "");
		return "list";
	}

	@ResponseBody
	@RequestMapping(value = "/getListForKeyName")
	public Map<String, Object> getListForKeyName(Model model, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			CodeUtils codeUtils = new CodeUtils();
			@SuppressWarnings("unchecked")
			List<Object> list = (List<Object>) codeUtils.getCacheByKeyName(jb.getString("keyName"));
			dataMap.put("parmListData", list);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			throw e;
		}
		return dataMap;
	}

}

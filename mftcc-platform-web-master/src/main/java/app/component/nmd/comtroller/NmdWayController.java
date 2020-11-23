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
import com.google.gson.JsonObject;

import app.base.cacheinterface.BusiCacheInterface;
import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.nmd.entity.NmdWay;
import app.component.nmd.feign.NmdWayFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONArray;

/**
 * Title: NmdWayAction.java Description:
 * 
 * @author:@dhcc.com.cn
 * @Tue Apr 05 03:32:05 GMT 2016
 **/
@Controller
@RequestMapping("/nmdWay")
public class NmdWayController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入NmdWayBo
	@Autowired
	private NmdWayFeign nmdWayFeign;
	@Autowired
	private BusiCacheInterface busiCacheInterface;

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
		return "component/nmd/NmdWay_List";
	}

	@RequestMapping(value = "/getListAll")
	public String getListAll(Model model) throws Exception {
		ActionContext.initialize(request, response);
		String ajaxData = BizPubParm.nmdWay;
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("query", "");
		return "component/nmd/IndustryAndArea";
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
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		NmdWay nmdWay = new NmdWay();
		try {
			//nmdWay.setCustomQuery(ajaxData);// 自定义查询参数赋值
			//nmdWay.setCriteriaList(nmdWay, ajaxData);// 我的筛选
			// this.getRoleConditions(nmdWay,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = nmdWayFeign.findByPage(ipage, nmdWay);
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
			FormData formnmd2002 = formService.getFormData("nmd2002");
			getFormValue(formnmd2002, getMapByJson(ajaxData));
			if (this.validateFormData(formnmd2002)) {
				NmdWay nmdWay = new NmdWay();
				setObjValue(formnmd2002, nmdWay);
				nmdWayFeign.insert(nmdWay);
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
		FormData formnmd2002 = formService.getFormData("nmd2002");
		getFormValue(formnmd2002, getMapByJson(ajaxData));
		NmdWay nmdWayJsp = new NmdWay();
		setObjValue(formnmd2002, nmdWayJsp);
		NmdWay nmdWay = nmdWayFeign.getById(nmdWayJsp);
		if (nmdWay != null) {
			try {
				nmdWay = (NmdWay) EntityUtil.reflectionSetVal(nmdWay, nmdWayJsp, getMapByJson(ajaxData));
				nmdWayFeign.update(nmdWay);
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
		NmdWay nmdWay = new NmdWay();
		try {
			FormData formnmd2002 = formService.getFormData("nmd2002");
			getFormValue(formnmd2002, getMapByJson(ajaxData));
			if (this.validateFormData(formnmd2002)) {
				setObjValue(formnmd2002, nmdWay);
				nmdWayFeign.update(nmdWay);
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
	public Map<String, Object> getByIdAjax(String wayNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formnmd2002 = formService.getFormData("nmd2002");
		NmdWay nmdWay = new NmdWay();
		nmdWay.setWayNo(wayNo);
		nmdWay = nmdWayFeign.getById(nmdWay);
		getObjValue(formnmd2002, nmdWay, formData);
		if (nmdWay != null) {
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
	public Map<String, Object> deleteAjax(String wayNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		NmdWay nmdWay = new NmdWay();
		nmdWay.setWayNo(wayNo);
		try {
			nmdWayFeign.delete(nmdWay);
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
		FormData formnmd2002 = formService.getFormData("nmd2002");
		model.addAttribute("formnmd2002", formnmd2002);
		model.addAttribute("query", "");
		return "component/nmd/NmdWay_Insert";
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
		FormData formnmd2002 = formService.getFormData("nmd2002");
		getFormValue(formnmd2002);
		NmdWay nmdWay = new NmdWay();
		setObjValue(formnmd2002, nmdWay);
		nmdWayFeign.insert(nmdWay);
		getObjValue(formnmd2002, nmdWay);
		this.addActionMessage(model, "保存成功");
		List<NmdWay> nmdWayList = (List<NmdWay>) nmdWayFeign.findByPage(this.getIpage(), nmdWay).getResult();
		model.addAttribute("formnmd2002", formnmd2002);
		model.addAttribute("nmdWay", nmdWay);
		model.addAttribute("nmdWayList", nmdWayList);
		model.addAttribute("query", "");
		return "component/nmd/NmdWay_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String wayNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formnmd2001 = formService.getFormData("nmd2001");
		getFormValue(formnmd2001);
		NmdWay nmdWay = new NmdWay();
		nmdWay.setWayNo(wayNo);
		nmdWay = nmdWayFeign.getById(nmdWay);
		getObjValue(formnmd2001, nmdWay);
		model.addAttribute("formnmd2001", formnmd2001);
		model.addAttribute("nmdWay", nmdWay);
		model.addAttribute("query", "");
		return "component/nmd/NmdWay_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String wayNo) throws Exception {
		ActionContext.initialize(request, response);
		NmdWay nmdWay = new NmdWay();
		nmdWay.setWayNo(wayNo);
		nmdWayFeign.delete(nmdWay);
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
		FormData formnmd2002 = formService.getFormData("nmd2002");
		getFormValue(formnmd2002);
		boolean validateFlag = this.validateFormData(formnmd2002);
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
		FormData formnmd2002 = formService.getFormData("nmd2002");
		getFormValue(formnmd2002);
		boolean validateFlag = this.validateFormData(formnmd2002);
	}
	
	/*********************************************************************************/
	@RequestMapping(value = "/getNmdWayByLev")
	public String getNmdWayByLev(Model model,String lev) throws Exception{
		List nmdWayList = null;
		String key="nmd_way_show2"; //默认展示两级
		if("3".equals(lev)) {
			key="nmd_way_show3";
		}else if("4".equals(lev)) {
			key="nmd_way_show4";
		}else {
		}
		nmdWayList = busiCacheInterface.getNmdWayList(key);
		model.addAttribute("ajaxData", JSONArray.fromObject(nmdWayList));
		return "component/nmd/NmdWayForSelect";
	}

	

}

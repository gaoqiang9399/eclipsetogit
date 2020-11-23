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

import app.component.collateral.entity.MfReceivablesDisputedAppHis;
import app.component.collateral.feign.MfReceivablesDisputedAppHisFeign;
import app.component.common.EntityUtil;
import app.util.toolkit.Ipage;

/**
 * Title: MfReceivablesDisputedAppHisController.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Mon May 15 18:31:05 CST 2017
 **/
@Controller
@RequestMapping("/mfReceivablesDisputedAppHis")
public class MfReceivablesDisputedAppHisController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfReceivablesDisputedAppHisFeign mfReceivablesDisputedAppHisFeign;

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/collateral/MfReceivablesDisputedAppHis_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/findByPageAjax")
	@ResponseBody public Map<String, Object> findByPageAjax(String ajaxData, Integer pageNo, Integer pageSize, String tableId,
			String tableType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfReceivablesDisputedAppHis mfReceivablesDisputedAppHis = new MfReceivablesDisputedAppHis();
		try {
			mfReceivablesDisputedAppHis.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfReceivablesDisputedAppHis.setCriteriaList(mfReceivablesDisputedAppHis, ajaxData);// 我的筛选
			// mfReceivablesDisputedAppHis.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfReceivablesDisputedAppHis,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);
			ipage.setPageSize(pageSize);// 异步传页面翻页参数
			// 自定义查询Feign方法
			ipage = mfReceivablesDisputedAppHisFeign.findByPage(ipage, mfReceivablesDisputedAppHis);
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
	@RequestMapping("/insertAjax")
	@ResponseBody public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formdispapprohis0002 = formService.getFormData("dispapprohis0002");
			getFormValue(formdispapprohis0002, getMapByJson(ajaxData));
			if (this.validateFormData(formdispapprohis0002)) {
				MfReceivablesDisputedAppHis mfReceivablesDisputedAppHis = new MfReceivablesDisputedAppHis();
				setObjValue(formdispapprohis0002, mfReceivablesDisputedAppHis);
				mfReceivablesDisputedAppHisFeign.insert(mfReceivablesDisputedAppHis);
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
	@RequestMapping("/updateAjaxByOne")
	@ResponseBody public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formdispapprohis0002 = formService.getFormData("dispapprohis0002");
		getFormValue(formdispapprohis0002, getMapByJson(ajaxData));
		MfReceivablesDisputedAppHis mfReceivablesDisputedAppHisJsp = new MfReceivablesDisputedAppHis();
		setObjValue(formdispapprohis0002, mfReceivablesDisputedAppHisJsp);
		MfReceivablesDisputedAppHis mfReceivablesDisputedAppHis = mfReceivablesDisputedAppHisFeign
				.getById(mfReceivablesDisputedAppHisJsp);
		if (mfReceivablesDisputedAppHis != null) {
			try {
				mfReceivablesDisputedAppHis = (MfReceivablesDisputedAppHis) EntityUtil.reflectionSetVal(
						mfReceivablesDisputedAppHis, mfReceivablesDisputedAppHisJsp, getMapByJson(ajaxData));
				mfReceivablesDisputedAppHisFeign.update(mfReceivablesDisputedAppHis);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			} catch (Exception e) {
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", "新增失败");
				throw e;
			}
		} else {
			dataMap.put("flag", "error");
			dataMap.put("msg", "编号不存在,保存失败");
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
	@ResponseBody public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formdispapprohis0002 = formService.getFormData("dispapprohis0002");
			getFormValue(formdispapprohis0002, getMapByJson(ajaxData));
			if (this.validateFormData(formdispapprohis0002)) {
				MfReceivablesDisputedAppHis mfReceivablesDisputedAppHis = new MfReceivablesDisputedAppHis();
				setObjValue(formdispapprohis0002, mfReceivablesDisputedAppHis);
				mfReceivablesDisputedAppHisFeign.update(mfReceivablesDisputedAppHis);
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
	@ResponseBody public Map<String, Object> getByIdAjax(String disputedAppHisId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formdispapprohis0002 = formService.getFormData("dispapprohis0002");
		MfReceivablesDisputedAppHis mfReceivablesDisputedAppHis = new MfReceivablesDisputedAppHis();
		mfReceivablesDisputedAppHis.setDisputedAppHisId(disputedAppHisId);
		mfReceivablesDisputedAppHis = mfReceivablesDisputedAppHisFeign.getById(mfReceivablesDisputedAppHis);
		getObjValue(formdispapprohis0002, mfReceivablesDisputedAppHis, formData);
		if (mfReceivablesDisputedAppHis != null) {
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
	public Map<String, Object> deleteAjax(String ajaxData,String disputedAppHisId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfReceivablesDisputedAppHis mfReceivablesDisputedAppHis = new MfReceivablesDisputedAppHis();
		mfReceivablesDisputedAppHis.setDisputedAppHisId(disputedAppHisId);
		try {
			mfReceivablesDisputedAppHisFeign.delete(mfReceivablesDisputedAppHis);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
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
	@RequestMapping("/input")
	public String input(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formdispapprohis0002 = formService.getFormData("dispapprohis0002");
		model.addAttribute("formdispapprohis0002", formdispapprohis0002);
		model.addAttribute("query", "");
		return "/component/collateral/MfReceivablesDisputedAppHis_Insert";
	}

	/***
	 * 新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/insert")
	public String insert(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formdispapprohis0002 = formService.getFormData("dispapprohis0002");
		getFormValue(formdispapprohis0002);
		MfReceivablesDisputedAppHis mfReceivablesDisputedAppHis = new MfReceivablesDisputedAppHis();
		setObjValue(formdispapprohis0002, mfReceivablesDisputedAppHis);
		mfReceivablesDisputedAppHisFeign.insert(mfReceivablesDisputedAppHis);
		getObjValue(formdispapprohis0002, mfReceivablesDisputedAppHis);
		this.addActionMessage(model, "保存成功");
		List<MfReceivablesDisputedAppHis> mfReceivablesDisputedAppHisList = (List<MfReceivablesDisputedAppHis>) mfReceivablesDisputedAppHisFeign
				.findByPage(this.getIpage(), mfReceivablesDisputedAppHis).getResult();
		model.addAttribute("mfReceivablesDisputedAppHisList", mfReceivablesDisputedAppHisList);
		model.addAttribute("formdispapprohis0002", formdispapprohis0002);
		model.addAttribute("query", "");
		return "/component/collateral/MfReceivablesDisputedAppHis_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model,String disputedAppHisId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formdispapprohis0001 = formService.getFormData("dispapprohis0001");
		getFormValue(formdispapprohis0001);
		MfReceivablesDisputedAppHis mfReceivablesDisputedAppHis = new MfReceivablesDisputedAppHis();
		mfReceivablesDisputedAppHis.setDisputedAppHisId(disputedAppHisId);
		mfReceivablesDisputedAppHis = mfReceivablesDisputedAppHisFeign.getById(mfReceivablesDisputedAppHis);
		getObjValue(formdispapprohis0001, mfReceivablesDisputedAppHis);
		model.addAttribute("formdispapprohis0001", formdispapprohis0001);
		model.addAttribute("query", "");
		return "/component/collateral/MfReceivablesDisputedAppHis_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	public String delete(String disputedAppHisId) throws Exception {
		ActionContext.initialize(request, response);
		MfReceivablesDisputedAppHis mfReceivablesDisputedAppHis = new MfReceivablesDisputedAppHis();
		mfReceivablesDisputedAppHis.setDisputedAppHisId(disputedAppHisId);
		mfReceivablesDisputedAppHisFeign.delete(mfReceivablesDisputedAppHis);
		return getListPage();
	}

}

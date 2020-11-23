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

import app.component.collateral.entity.MfReceivablesTransferApproHis;
import app.component.collateral.feign.MfReceivablesTransferApproHisFeign;
import app.component.common.EntityUtil;
import app.util.toolkit.Ipage;

/**
 * Title: MfReceivablesTransferApproHisController.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu May 11 11:24:37 CST 2017
 **/
@Controller
@RequestMapping("/mfReceivablesTransferApproHis")
public class MfReceivablesTransferApproHisController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfReceivablesTransferApproHisFeign mfReceivablesTransferApproHisFeign;

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/collateral/MfReceivablesTransferApproHis_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(String ajaxData, Integer pageNo, Integer pageSize, String tableId,
			String tableType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfReceivablesTransferApproHis mfReceivablesTransferApproHis = new MfReceivablesTransferApproHis();
		try {
			mfReceivablesTransferApproHis.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfReceivablesTransferApproHis.setCriteriaList(mfReceivablesTransferApproHis, ajaxData);// 我的筛选
			// mfReceivablesTransferApproHis.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfReceivablesTransferApproHis,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);
			ipage.setPageSize(pageSize);// 异步传页面翻页参数
			// 自定义查询Feign方法
			ipage = mfReceivablesTransferApproHisFeign.findByPage(ipage, mfReceivablesTransferApproHis);
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
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formtranappro0002 = formService.getFormData("tranappro0002");
			getFormValue(formtranappro0002, getMapByJson(ajaxData));
			if (this.validateFormData(formtranappro0002)) {
				MfReceivablesTransferApproHis mfReceivablesTransferApproHis = new MfReceivablesTransferApproHis();
				setObjValue(formtranappro0002, mfReceivablesTransferApproHis);
				mfReceivablesTransferApproHisFeign.insert(mfReceivablesTransferApproHis);
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
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formtranappro0002 = formService.getFormData("tranappro0002");
		getFormValue(formtranappro0002, getMapByJson(ajaxData));
		MfReceivablesTransferApproHis mfReceivablesTransferApproHisJsp = new MfReceivablesTransferApproHis();
		setObjValue(formtranappro0002, mfReceivablesTransferApproHisJsp);
		MfReceivablesTransferApproHis mfReceivablesTransferApproHis = mfReceivablesTransferApproHisFeign
				.getById(mfReceivablesTransferApproHisJsp);
		if (mfReceivablesTransferApproHis != null) {
			try {
				mfReceivablesTransferApproHis = (MfReceivablesTransferApproHis) EntityUtil.reflectionSetVal(
						mfReceivablesTransferApproHis, mfReceivablesTransferApproHisJsp, getMapByJson(ajaxData));
				mfReceivablesTransferApproHisFeign.update(mfReceivablesTransferApproHis);
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
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formtranappro0002 = formService.getFormData("tranappro0002");
			getFormValue(formtranappro0002, getMapByJson(ajaxData));
			if (this.validateFormData(formtranappro0002)) {
				MfReceivablesTransferApproHis mfReceivablesTransferApproHis = new MfReceivablesTransferApproHis();
				setObjValue(formtranappro0002, mfReceivablesTransferApproHis);
				mfReceivablesTransferApproHisFeign.update(mfReceivablesTransferApproHis);
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
	@ResponseBody
	public Map<String, Object> getByIdAjax(String ajaxData, String tranApproHisId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formtranappro0002 = formService.getFormData("tranappro0002");
		MfReceivablesTransferApproHis mfReceivablesTransferApproHis = new MfReceivablesTransferApproHis();
		mfReceivablesTransferApproHis.setTranApproHisId(tranApproHisId);
		mfReceivablesTransferApproHis = mfReceivablesTransferApproHisFeign.getById(mfReceivablesTransferApproHis);
		getObjValue(formtranappro0002, mfReceivablesTransferApproHis, formData);
		if (mfReceivablesTransferApproHis != null) {
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
	public Map<String, Object> deleteAjax(String ajaxData, String tranApproHisId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfReceivablesTransferApproHis mfReceivablesTransferApproHis = new MfReceivablesTransferApproHis();
		mfReceivablesTransferApproHis.setTranApproHisId(tranApproHisId);
		try {
			mfReceivablesTransferApproHisFeign.delete(mfReceivablesTransferApproHis);
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
		FormData formtranappro0002 = formService.getFormData("tranappro0002");
		model.addAttribute("formtranappro0002", formtranappro0002);
		model.addAttribute("query", "");
		return "/component/collateral/MfReceivablesTransferApproHis_Insert";
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
		FormData formtranappro0002 = formService.getFormData("tranappro0002");
		getFormValue(formtranappro0002);
		MfReceivablesTransferApproHis mfReceivablesTransferApproHis = new MfReceivablesTransferApproHis();
		setObjValue(formtranappro0002, mfReceivablesTransferApproHis);
		mfReceivablesTransferApproHisFeign.insert(mfReceivablesTransferApproHis);
		getObjValue(formtranappro0002, mfReceivablesTransferApproHis);
		this.addActionMessage(model, "保存成功");
		List<MfReceivablesTransferApproHis> mfReceivablesTransferApproHisList = (List<MfReceivablesTransferApproHis>) mfReceivablesTransferApproHisFeign
				.findByPage(this.getIpage(), mfReceivablesTransferApproHis).getResult();
		model.addAttribute("formtranappro0002", formtranappro0002);
		model.addAttribute("mfReceivablesTransferApproHisList", mfReceivablesTransferApproHisList);
		model.addAttribute("query", "");
		return "/component/collateral/MfReceivablesTransferApproHis_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model, String tranApproHisId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formtranappro0001 = formService.getFormData("tranappro0001");
		getFormValue(formtranappro0001);
		MfReceivablesTransferApproHis mfReceivablesTransferApproHis = new MfReceivablesTransferApproHis();
		mfReceivablesTransferApproHis.setTranApproHisId(tranApproHisId);
		mfReceivablesTransferApproHis = mfReceivablesTransferApproHisFeign.getById(mfReceivablesTransferApproHis);
		getObjValue(formtranappro0001, mfReceivablesTransferApproHis);
		model.addAttribute("formtranappro0001", formtranappro0001);
		model.addAttribute("query", "");
		return "/component/collateral/MfReceivablesTransferApproHis_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	public String delete(String tranApproHisId) throws Exception {
		ActionContext.initialize(request, response);
		MfReceivablesTransferApproHis mfReceivablesTransferApproHis = new MfReceivablesTransferApproHis();
		mfReceivablesTransferApproHis.setTranApproHisId(tranApproHisId);
		mfReceivablesTransferApproHisFeign.delete(mfReceivablesTransferApproHis);
		return getListPage();
	}

}

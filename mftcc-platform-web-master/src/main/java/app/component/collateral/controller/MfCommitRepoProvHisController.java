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

import app.component.collateral.entity.MfCommitRepoProvHis;
import app.component.collateral.feign.MfCommitRepoProvHisFeign;
import app.component.common.EntityUtil;
import app.util.toolkit.Ipage;

/**
 * Title: MfCommitRepoProvHisController.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri May 05 20:49:02 CST 2017
 **/
@Controller
@RequestMapping("/mfCommitRepoProvHis")
public class MfCommitRepoProvHisController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfCommitRepoProvHisFeign mfCommitRepoProvHisFeign;

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/collateral/MfCommitRepoProvHis_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findByPageAjax")
	@ResponseBody public Map<String, Object> findByPageAjax(String ajaxData, Integer pageNo, Integer pageSize, String tableId,
			String tableType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCommitRepoProvHis mfCommitRepoProvHis = new MfCommitRepoProvHis();
		try {
			mfCommitRepoProvHis.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCommitRepoProvHis.setCriteriaList(mfCommitRepoProvHis, ajaxData);// 我的筛选
			// mfCommitRepoProvHis.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfCommitRepoProvHis,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);
			ipage.setPageSize(pageSize);// 异步传页面翻页参数
			// 自定义查询Feign方法
			ipage = mfCommitRepoProvHisFeign.findByPage(ipage, mfCommitRepoProvHis);
			JsonTableUtil jtu = new JsonTableUtil();
			@SuppressWarnings("rawtypes")
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
			FormData formcommropehis0002 = formService.getFormData("commropehis0002");
			getFormValue(formcommropehis0002, getMapByJson(ajaxData));
			if (this.validateFormData(formcommropehis0002)) {
				MfCommitRepoProvHis mfCommitRepoProvHis = new MfCommitRepoProvHis();
				setObjValue(formcommropehis0002, mfCommitRepoProvHis);
				mfCommitRepoProvHisFeign.insert(mfCommitRepoProvHis);
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
		FormData formcommropehis0002 = formService.getFormData("commropehis0002");
		getFormValue(formcommropehis0002, getMapByJson(ajaxData));
		MfCommitRepoProvHis mfCommitRepoProvHisJsp = new MfCommitRepoProvHis();
		setObjValue(formcommropehis0002, mfCommitRepoProvHisJsp);
		MfCommitRepoProvHis mfCommitRepoProvHis = mfCommitRepoProvHisFeign.getById(mfCommitRepoProvHisJsp);
		if (mfCommitRepoProvHis != null) {
			try {
				mfCommitRepoProvHis = (MfCommitRepoProvHis) EntityUtil.reflectionSetVal(mfCommitRepoProvHis,
						mfCommitRepoProvHisJsp, getMapByJson(ajaxData));
				mfCommitRepoProvHisFeign.update(mfCommitRepoProvHis);
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
			FormData formcommropehis0002 = formService.getFormData("commropehis0002");
			getFormValue(formcommropehis0002, getMapByJson(ajaxData));
			if (this.validateFormData(formcommropehis0002)) {
				MfCommitRepoProvHis mfCommitRepoProvHis = new MfCommitRepoProvHis();
				setObjValue(formcommropehis0002, mfCommitRepoProvHis);
				mfCommitRepoProvHisFeign.update(mfCommitRepoProvHis);
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
	@ResponseBody public Map<String, Object> getByIdAjax(String commRopeHistoryId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcommropehis0002 = formService.getFormData("commropehis0002");
		MfCommitRepoProvHis mfCommitRepoProvHis = new MfCommitRepoProvHis();
		mfCommitRepoProvHis.setCommRopeHistoryId(commRopeHistoryId);
		mfCommitRepoProvHis = mfCommitRepoProvHisFeign.getById(mfCommitRepoProvHis);
		getObjValue(formcommropehis0002, mfCommitRepoProvHis, formData);
		if (mfCommitRepoProvHis != null) {
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
	public Map<String, Object> deleteAjax(String ajaxData, String commRopeHistoryId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCommitRepoProvHis mfCommitRepoProvHis = new MfCommitRepoProvHis();
		mfCommitRepoProvHis.setCommRopeHistoryId(commRopeHistoryId);
		try {
			mfCommitRepoProvHisFeign.delete(mfCommitRepoProvHis);
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
		FormData formcommropehis0002 = formService.getFormData("commropehis0002");
		model.addAttribute("formcommropehis0002", formcommropehis0002);
		model.addAttribute("query", "");
		return "/component/collateral/MfCommitRepoProvHis_Insert";
	}

	/***
	 * 新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insert")
	public String insert(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formcommropehis0002 = formService.getFormData("commropehis0002");
		getFormValue(formcommropehis0002);
		MfCommitRepoProvHis mfCommitRepoProvHis = new MfCommitRepoProvHis();
		setObjValue(formcommropehis0002, mfCommitRepoProvHis);
		mfCommitRepoProvHisFeign.insert(mfCommitRepoProvHis);
		getObjValue(formcommropehis0002, mfCommitRepoProvHis);
		this.addActionMessage(model, "保存成功");
		@SuppressWarnings("unchecked")
		List<MfCommitRepoProvHis> mfCommitRepoProvHisList = (List<MfCommitRepoProvHis>) mfCommitRepoProvHisFeign
				.findByPage(this.getIpage(), mfCommitRepoProvHis).getResult();
		model.addAttribute("mfCommitRepoProvHisList", mfCommitRepoProvHisList);
		model.addAttribute("formcommropehis0002", formcommropehis0002);
		model.addAttribute("query", "");
		return "/component/collateral/MfCommitRepoProvHis_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model, String commRopeHistoryId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formcommropehis0001 = formService.getFormData("commropehis0001");
		getFormValue(formcommropehis0001);
		MfCommitRepoProvHis mfCommitRepoProvHis = new MfCommitRepoProvHis();
		mfCommitRepoProvHis.setCommRopeHistoryId(commRopeHistoryId);
		mfCommitRepoProvHis = mfCommitRepoProvHisFeign.getById(mfCommitRepoProvHis);
		getObjValue(formcommropehis0001, mfCommitRepoProvHis);
		model.addAttribute("formcommropehis0001", formcommropehis0001);
		model.addAttribute("query", "");
		return "/component/collateral/MfCommitRepoProvHis_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	public String delete(String commRopeHistoryId) throws Exception {
		ActionContext.initialize(request, response);
		MfCommitRepoProvHis mfCommitRepoProvHis = new MfCommitRepoProvHis();
		mfCommitRepoProvHis.setCommRopeHistoryId(commRopeHistoryId);
		mfCommitRepoProvHisFeign.delete(mfCommitRepoProvHis);
		return getListPage();
	}

}

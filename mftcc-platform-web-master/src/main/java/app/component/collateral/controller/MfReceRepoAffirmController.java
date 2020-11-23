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

import app.component.collateral.entity.MfReceRepoAffirm;
import app.component.collateral.feign.MfReceRepoAffirmFeign;
import app.component.common.EntityUtil;
import app.util.toolkit.Ipage;

/**
 * Title: MfReceRepoAffirmController.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri May 12 15:06:35 CST 2017
 **/
@Controller
@RequestMapping("/mfReceRepoAffirm")
public class MfReceRepoAffirmController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfReceRepoAffirmFeign mfReceRepoAffirmFeign;

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/collateral/MfReceRepoAffirm_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(String ajaxData, Integer pageNo, Integer pageSize, String tableId,
			String tableType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfReceRepoAffirm mfReceRepoAffirm = new MfReceRepoAffirm();
		try {
			mfReceRepoAffirm.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfReceRepoAffirm.setCriteriaList(mfReceRepoAffirm, ajaxData);// 我的筛选
			// mfReceRepoAffirm.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfReceRepoAffirm,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);
			ipage.setPageSize(pageSize);// 异步传页面翻页参数
			// 自定义查询Feign方法
			ipage = mfReceRepoAffirmFeign.findByPage(ipage, mfReceRepoAffirm);
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
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formrepoaffirm0002 = formService.getFormData("repoaffirm0002");
			getFormValue(formrepoaffirm0002, getMapByJson(ajaxData));
			if (this.validateFormData(formrepoaffirm0002)) {
				MfReceRepoAffirm mfReceRepoAffirm = new MfReceRepoAffirm();
				setObjValue(formrepoaffirm0002, mfReceRepoAffirm);
				mfReceRepoAffirmFeign.insert(mfReceRepoAffirm);
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
		FormData formrepoaffirm0002 = formService.getFormData("repoaffirm0002");
		getFormValue(formrepoaffirm0002, getMapByJson(ajaxData));
		MfReceRepoAffirm mfReceRepoAffirmJsp = new MfReceRepoAffirm();
		setObjValue(formrepoaffirm0002, mfReceRepoAffirmJsp);
		MfReceRepoAffirm mfReceRepoAffirm = mfReceRepoAffirmFeign.getById(mfReceRepoAffirmJsp);
		if (mfReceRepoAffirm != null) {
			try {
				mfReceRepoAffirm = (MfReceRepoAffirm) EntityUtil.reflectionSetVal(mfReceRepoAffirm, mfReceRepoAffirmJsp,
						getMapByJson(ajaxData));
				mfReceRepoAffirmFeign.update(mfReceRepoAffirm);
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
			FormData formrepoaffirm0002 = formService.getFormData("repoaffirm0002");
			getFormValue(formrepoaffirm0002, getMapByJson(ajaxData));
			if (this.validateFormData(formrepoaffirm0002)) {
				MfReceRepoAffirm mfReceRepoAffirm = new MfReceRepoAffirm();
				setObjValue(formrepoaffirm0002, mfReceRepoAffirm);
				mfReceRepoAffirmFeign.update(mfReceRepoAffirm);
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
	public Map<String, Object> getByIdAjax(String ajaxData, String repoAffirmId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formrepoaffirm0002 = formService.getFormData("repoaffirm0002");
		MfReceRepoAffirm mfReceRepoAffirm = new MfReceRepoAffirm();
		mfReceRepoAffirm.setRepoAffirmId(repoAffirmId);
		mfReceRepoAffirm = mfReceRepoAffirmFeign.getById(mfReceRepoAffirm);
		getObjValue(formrepoaffirm0002, mfReceRepoAffirm, formData);
		if (mfReceRepoAffirm != null) {
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
	public Map<String, Object> deleteAjax(String ajaxData, String repoAffirmId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfReceRepoAffirm mfReceRepoAffirm = new MfReceRepoAffirm();
		mfReceRepoAffirm.setRepoAffirmId(repoAffirmId);
		try {
			mfReceRepoAffirmFeign.delete(mfReceRepoAffirm);
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
	public String input(Model model, String busPleId, String appId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formrepoaffirm0002 = formService.getFormData("repoaffirm0002");
		MfReceRepoAffirm mfReceRepoAffirm = mfReceRepoAffirmFeign.getReceRepoAffirmInit(busPleId, appId);
		getObjValue(formrepoaffirm0002, mfReceRepoAffirm);
		model.addAttribute("formrepoaffirm0002", formrepoaffirm0002);
		model.addAttribute("query", "");
		return "/component/collateral/MfReceRepoAffirm_Insert";
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
		FormData formrepoaffirm0002 = formService.getFormData("repoaffirm0002");
		getFormValue(formrepoaffirm0002);
		MfReceRepoAffirm mfReceRepoAffirm = new MfReceRepoAffirm();
		setObjValue(formrepoaffirm0002, mfReceRepoAffirm);
		mfReceRepoAffirmFeign.insert(mfReceRepoAffirm);
		getObjValue(formrepoaffirm0002, mfReceRepoAffirm);
		this.addActionMessage(model, "保存成功");
		List<MfReceRepoAffirm> mfReceRepoAffirmList = (List<MfReceRepoAffirm>) mfReceRepoAffirmFeign
				.findByPage(this.getIpage(), mfReceRepoAffirm).getResult();
		model.addAttribute("mfReceRepoAffirmList", mfReceRepoAffirmList);
		model.addAttribute("formrepoaffirm0002", formrepoaffirm0002);
		model.addAttribute("query", "");
		return "/component/collateral/MfReceRepoAffirm_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model, String repoAffirmId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formrepoaffirm0001 = formService.getFormData("repoaffirm0001");
		getFormValue(formrepoaffirm0001);
		MfReceRepoAffirm mfReceRepoAffirm = new MfReceRepoAffirm();
		mfReceRepoAffirm.setRepoAffirmId(repoAffirmId);
		mfReceRepoAffirm = mfReceRepoAffirmFeign.getById(mfReceRepoAffirm);
		getObjValue(formrepoaffirm0001, mfReceRepoAffirm);
		model.addAttribute("formrepoaffirm0001", formrepoaffirm0001);
		model.addAttribute("query", "");
		return "/component/collateral/MfReceRepoAffirm_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	public String delete(String repoAffirmId) throws Exception {
		ActionContext.initialize(request, response);
		MfReceRepoAffirm mfReceRepoAffirm = new MfReceRepoAffirm();
		mfReceRepoAffirm.setRepoAffirmId(repoAffirmId);
		mfReceRepoAffirmFeign.delete(mfReceRepoAffirm);
		return getListPage();
	}

}

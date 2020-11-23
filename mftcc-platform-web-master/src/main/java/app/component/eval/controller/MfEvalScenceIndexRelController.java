package app.component.eval.controller;

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
import app.component.eval.entity.MfEvalScenceIndexRel;
import app.component.eval.feign.MfEvalScenceIndexRelFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONObject;

/**
 * Title: MfEvalScenceIndexRelController.java Description:
 * 
 * @author:@dhcc.com.cn
 * @Thu Mar 17 06:46:26 GMT 2016
 **/
@Controller
@RequestMapping("/mfEvalScenceIndexRel")
public class MfEvalScenceIndexRelController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfEvalScenceIndexRelFeign mfEvalScenceIndexRelFeign;

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/eval/MfEvalScenceIndexRel_List";
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
		MfEvalScenceIndexRel mfEvalScenceIndexRel = new MfEvalScenceIndexRel();
		try {
			mfEvalScenceIndexRel.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfEvalScenceIndexRel.setCriteriaList(mfEvalScenceIndexRel, ajaxData);// 我的筛选
			// this.getRoleConditions(mfEvalScenceIndexRel,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);
			ipage.setPageSize(pageSize);// 异步传页面翻页参数
			// 自定义查询Feign方法
			ipage = mfEvalScenceIndexRelFeign.findByPage(ipage, mfEvalScenceIndexRel);
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
			FormData formScenceIndexRel0001 = formService.getFormData("ScenceIndexRel0001");
			JSONObject jb = JSONObject.fromObject(ajaxData);
			getFormValue(formScenceIndexRel0001, jb);
			if (this.validateFormData(formScenceIndexRel0001)) {
				MfEvalScenceIndexRel mfEvalScenceIndexRel = new MfEvalScenceIndexRel();
				setObjValue(formScenceIndexRel0001, mfEvalScenceIndexRel);
				mfEvalScenceIndexRel = mfEvalScenceIndexRelFeign.insert(mfEvalScenceIndexRel);
				dataMap.put("entityData", mfEvalScenceIndexRel);
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
	@RequestMapping("/updateAjaxByOne")
	@ResponseBody public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formScenceIndexRel0001 = formService.getFormData("ScenceIndexRel0001");
		getFormValue(formScenceIndexRel0001, getMapByJson(ajaxData));
		MfEvalScenceIndexRel mfEvalScenceIndexRelJsp = new MfEvalScenceIndexRel();
		setObjValue(formScenceIndexRel0001, mfEvalScenceIndexRelJsp);
		MfEvalScenceIndexRel mfEvalScenceIndexRel = mfEvalScenceIndexRelFeign.getById(mfEvalScenceIndexRelJsp);
		if (mfEvalScenceIndexRel != null) {
			try {
				mfEvalScenceIndexRel = (MfEvalScenceIndexRel) EntityUtil.reflectionSetVal(mfEvalScenceIndexRel, mfEvalScenceIndexRelJsp,
						getMapByJson(ajaxData));
				mfEvalScenceIndexRelFeign.update(mfEvalScenceIndexRel);
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
	@RequestMapping("/updateAjax")
	@ResponseBody public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formScenceIndexRel0001 = formService.getFormData("ScenceIndexRel0001");
			JSONObject jb = JSONObject.fromObject(ajaxData);
			getFormValue(formScenceIndexRel0001, jb);
			if (this.validateFormData(formScenceIndexRel0001)) {
				MfEvalScenceIndexRel mfEvalScenceIndexRel = new MfEvalScenceIndexRel();
				setObjValue(formScenceIndexRel0001, mfEvalScenceIndexRel);
				mfEvalScenceIndexRelFeign.update(mfEvalScenceIndexRel);
				dataMap.put("entityData", mfEvalScenceIndexRel);
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
	@RequestMapping("/getByIdAjax")
	@ResponseBody public Map<String, Object> getByIdAjax(String indexNo, String scenceNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formScenceIndexRel0001 = formService.getFormData("ScenceIndexRel0001");
		MfEvalScenceIndexRel mfEvalScenceIndexRel = new MfEvalScenceIndexRel();
		mfEvalScenceIndexRel.setIndexNo(indexNo);
		mfEvalScenceIndexRel.setScenceNo(scenceNo);
		mfEvalScenceIndexRel = mfEvalScenceIndexRelFeign.getById(mfEvalScenceIndexRel);
		getObjValue(formScenceIndexRel0001, mfEvalScenceIndexRel, formData);
		if (mfEvalScenceIndexRel != null) {
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
	public Map<String, Object> deleteAjax(String indexNo, String scenceNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfEvalScenceIndexRel mfEvalScenceIndexRel = new MfEvalScenceIndexRel();
		try {
			mfEvalScenceIndexRel.setIndexNo(indexNo);
			mfEvalScenceIndexRel.setScenceNo(scenceNo);
			mfEvalScenceIndexRelFeign.delete(mfEvalScenceIndexRel);
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
	@RequestMapping("/input")
	public String input(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formScenceIndexRel0001 = formService.getFormData("ScenceIndexRel0001");
		model.addAttribute("formScenceIndexRel0001", formScenceIndexRel0001);
		model.addAttribute("query", "");
		return "/component/eval/MfEvalScenceIndexRel_Insert";
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
		FormData formScenceIndexRel0001 = formService.getFormData("ScenceIndexRel0001");
		getFormValue(formScenceIndexRel0001);
		MfEvalScenceIndexRel mfEvalScenceIndexRel = new MfEvalScenceIndexRel();
		setObjValue(formScenceIndexRel0001, mfEvalScenceIndexRel);
		mfEvalScenceIndexRelFeign.insert(mfEvalScenceIndexRel);
		getObjValue(formScenceIndexRel0001, mfEvalScenceIndexRel);
		this.addActionMessage(model, "保存成功");
		List<MfEvalScenceIndexRel> mfEvalScenceIndexRelList = (List<MfEvalScenceIndexRel>) mfEvalScenceIndexRelFeign
				.findByPage(this.getIpage(), mfEvalScenceIndexRel).getResult();
		model.addAttribute("mfEvalScenceIndexRelList", mfEvalScenceIndexRelList);
		model.addAttribute("formScenceIndexRel0001", formScenceIndexRel0001);
		model.addAttribute("query", "");
		return "/component/eval/MfEvalScenceIndexRel_Detail";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model,String indexNo, String scenceNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formScenceIndexRel0001 = formService.getFormData("ScenceIndexRel0001");
		getFormValue(formScenceIndexRel0001);
		MfEvalScenceIndexRel mfEvalScenceIndexRel = new MfEvalScenceIndexRel();
		mfEvalScenceIndexRel.setIndexNo(indexNo);
		mfEvalScenceIndexRel.setScenceNo(scenceNo);
		mfEvalScenceIndexRel = mfEvalScenceIndexRelFeign.getById(mfEvalScenceIndexRel);
		getObjValue(formScenceIndexRel0001, mfEvalScenceIndexRel);
		model.addAttribute("query", "");
		return "/component/eval/MfEvalScenceIndexRel_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	public String delete(String indexNo, String scenceNo) throws Exception {
		ActionContext.initialize(request, response);
		MfEvalScenceIndexRel mfEvalScenceIndexRel = new MfEvalScenceIndexRel();
		mfEvalScenceIndexRel.setIndexNo(indexNo);
		mfEvalScenceIndexRel.setScenceNo(scenceNo);
		mfEvalScenceIndexRelFeign.delete(mfEvalScenceIndexRel);
		return getListPage();
	}

}

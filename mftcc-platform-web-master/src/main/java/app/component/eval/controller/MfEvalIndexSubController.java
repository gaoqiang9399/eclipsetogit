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
import com.google.gson.Gson;

import app.component.common.EntityUtil;
import app.component.eval.entity.MfEvalIndexSub;
import app.component.eval.feign.MfEvalIndexSubFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONObject;

/**
 * Title: MfEvalIndexSubAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue Mar 07 16:03:02 CST 2017
 **/
@Controller
@RequestMapping("/mfEvalIndexSub")
public class MfEvalIndexSubController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfEvalIndexSubBo
	@Autowired
	private MfEvalIndexSubFeign mfEvalIndexSubFeign;

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		return "/component/eval/MfEvalIndexSub_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfEvalIndexSub mfEvalIndexSub = new MfEvalIndexSub();
		try {
			mfEvalIndexSub.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfEvalIndexSub.setCriteriaList(mfEvalIndexSub, ajaxData);// 我的筛选
			// this.getRoleConditions(mfEvalIndexSub,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = mfEvalIndexSubFeign.findByPage(ipage, mfEvalIndexSub);
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
	 * 
	 * 方法描述： 获得评级指标子项列表html代码
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-3-7 下午8:23:03
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getEvalSubListHtmlAjax")
	@ResponseBody
	public Map<String, Object> getEvalSubListHtmlAjax(String ajaxData, Integer pageSize, Integer pageNo,
			String propertyId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfEvalIndexSub mfEvalIndexSub = new MfEvalIndexSub();
		try {
			mfEvalIndexSub.setPropertyId(propertyId);
			// 自定义查询Bo方法
			List<MfEvalIndexSub> mfEvalIndexSubList = mfEvalIndexSubFeign.getMfEvalIndexSubList(mfEvalIndexSub);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr("tableevalsub0001", "tableTag",mfEvalIndexSubList, null, true);
			dataMap.put("flag", "success");
			dataMap.put("tableHtml", tableHtml);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 
	 * 方法描述： 根据评级指标编号获得指标子项
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-3-8 上午9:31:37
	 */
	@RequestMapping(value = "/getEvalSubListAjax")
	@ResponseBody
	public Map<String, Object> getEvalSubListAjax(String ajaxData, String propertyId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfEvalIndexSub mfEvalIndexSub = new MfEvalIndexSub();
		try {
			// 获得评级指标子项
			mfEvalIndexSub.setPropertyId(propertyId);
			List<MfEvalIndexSub> mfEvalIndexSubList = mfEvalIndexSubFeign.getMfEvalIndexSubList(mfEvalIndexSub);
			dataMap.put("flag", "success");
			dataMap.put("subList", mfEvalIndexSubList);
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
			FormData formevalsub0002 = formService.getFormData("evalsub0002");
			JSONObject jb = JSONObject.fromObject(ajaxData);
			getFormValue(formevalsub0002, jb);
			if (this.validateFormData(formevalsub0002)) {
				MfEvalIndexSub mfEvalIndexSub = new MfEvalIndexSub();
				setObjValue(formevalsub0002, mfEvalIndexSub);
				MfEvalIndexSub evalIndexSub = new MfEvalIndexSub();
				evalIndexSub = mfEvalIndexSubFeign.getById(mfEvalIndexSub);
				if (evalIndexSub != null) {
					mfEvalIndexSubFeign.update(mfEvalIndexSub);
					dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
				} else {
					mfEvalIndexSub = mfEvalIndexSubFeign.insert(mfEvalIndexSub);
					dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
				}
				dataMap.put("mfEvalIndexSub", mfEvalIndexSub);
				dataMap.put("flag", "success");
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
		FormData formevalsub0002 = formService.getFormData("evalsub0002");
		getFormValue(formevalsub0002, getMapByJson(ajaxData));
		MfEvalIndexSub mfEvalIndexSubJsp = new MfEvalIndexSub();
		setObjValue(formevalsub0002, mfEvalIndexSubJsp);
		MfEvalIndexSub mfEvalIndexSub = mfEvalIndexSubFeign.getById(mfEvalIndexSubJsp);
		if (mfEvalIndexSub != null) {
			try {
				mfEvalIndexSub = (MfEvalIndexSub) EntityUtil.reflectionSetVal(mfEvalIndexSub, mfEvalIndexSubJsp,
						getMapByJson(ajaxData));
				mfEvalIndexSubFeign.update(mfEvalIndexSub);
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
		try {
			FormData formevalsub0002 = formService.getFormData("evalsub0002");
			getFormValue(formevalsub0002, getMapByJson(ajaxData));
			if (this.validateFormData(formevalsub0002)) {
				MfEvalIndexSub mfEvalIndexSub = new MfEvalIndexSub();
				setObjValue(formevalsub0002, mfEvalIndexSub);
				mfEvalIndexSubFeign.update(mfEvalIndexSub);
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
	public Map<String, Object> getByIdAjax(String subId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formevalsub0002 = formService.getFormData("evalsub0002");
		MfEvalIndexSub mfEvalIndexSub = new MfEvalIndexSub();
		mfEvalIndexSub.setSubId(subId);
		mfEvalIndexSub = mfEvalIndexSubFeign.getById(mfEvalIndexSub);
		getObjValue(formevalsub0002, mfEvalIndexSub, formData);
		if (mfEvalIndexSub != null) {
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
	public Map<String, Object> deleteAjax(String subId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfEvalIndexSub mfEvalIndexSub = new MfEvalIndexSub();
		mfEvalIndexSub.setSubId(subId);
		try {
			mfEvalIndexSubFeign.delete(mfEvalIndexSub);
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
	public String input(Model model, String propertyId, String propertyNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formevalsub0002 = formService.getFormData("evalsub0002");
		MfEvalIndexSub mfEvalIndexSub = new MfEvalIndexSub();
		mfEvalIndexSub.setPropertyId(propertyId);
		mfEvalIndexSub.setPropertyNo(propertyNo);
		//mfEvalIndexSub.setSubNo(propertyNo);
		getObjValue(formevalsub0002, mfEvalIndexSub);
		model.addAttribute("formevalsub0002", formevalsub0002);
		model.addAttribute("query", "");
		return "/component/eval/MfEvalIndexSub_Insert";
	}

	/***
	 * 新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formevalsub0002 = formService.getFormData("evalsub0002");
		getFormValue(formevalsub0002);
		MfEvalIndexSub mfEvalIndexSub = new MfEvalIndexSub();
		setObjValue(formevalsub0002, mfEvalIndexSub);
		mfEvalIndexSubFeign.insert(mfEvalIndexSub);
		getObjValue(formevalsub0002, mfEvalIndexSub);
		this.addActionMessage(model, "保存成功");
		List<MfEvalIndexSub> mfEvalIndexSubList = (List<MfEvalIndexSub>) mfEvalIndexSubFeign
				.findByPage(this.getIpage(), mfEvalIndexSub).getResult();
		model.addAttribute("formevalsub0002", formevalsub0002);
		model.addAttribute("mfEvalIndexSubList", mfEvalIndexSubList);
		model.addAttribute("query", "");
		return "/component/eval/MfEvalIndexSub_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String subId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formevalsub0002 = formService.getFormData("evalsub0002");
		getFormValue(formevalsub0002);
		MfEvalIndexSub mfEvalIndexSub = new MfEvalIndexSub();
		mfEvalIndexSub.setSubId(subId);
		mfEvalIndexSub = mfEvalIndexSubFeign.getById(mfEvalIndexSub);
		getObjValue(formevalsub0002, mfEvalIndexSub);
		model.addAttribute("formevalsub0002", formevalsub0002);
		model.addAttribute("query", "");
		return "/component/eval/MfEvalIndexSub_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String subId) throws Exception {
		ActionContext.initialize(request, response);
		MfEvalIndexSub mfEvalIndexSub = new MfEvalIndexSub();
		mfEvalIndexSub.setSubId(subId);
		mfEvalIndexSubFeign.delete(mfEvalIndexSub);
		return getListPage(model);
	}
	/**
	 * 
	 * 方法描述： 保存子项信息
	 * @param ajaxData
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2018年5月31日 下午10:40:02
	 */
	@RequestMapping(value = "/insertSubAjax")
	@ResponseBody
	public Map<String, Object> insertSubAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formevalsub0002 = formService.getFormData("evalsub0002");
			getFormValue(formevalsub0002, getMapByJson(ajaxData));
			if (this.validateFormData(formevalsub0002)) {
				MfEvalIndexSub mfEvalIndexSub = new MfEvalIndexSub();
				setObjValue(formevalsub0002, mfEvalIndexSub);
				List<MfEvalIndexSub> mfEvalIndexSubList = mfEvalIndexSubFeign.insertIndexSubs(mfEvalIndexSub);
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
				dataMap.put("mfEvalIndexSub", mfEvalIndexSub);
				dataMap.put("flag", "success");
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

}

package app.component.frontview.controller;

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
import app.component.frontview.entity.MfAppSeting;
import app.component.frontview.feign.MfAppSetingFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONObject;

/**
 * Title: MfAppSetingAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue Oct 24 12:56:14 CST 2017
 **/
@Controller
@RequestMapping("/mfAppSeting")
public class MfAppSetingController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfAppSetingBo
	@Autowired
	private MfAppSetingFeign mfAppSetingFeign;

	/**
	 * 产品种类:常规设置 高级设置（产品核算配置参数更新）
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAppSettingAjax")
	@ResponseBody
	public Map<String, Object> updateAppSettingAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfAppSeting mfAppSeting = new MfAppSeting();
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			mfAppSeting = (MfAppSeting) JSONObject.toBean(jb, MfAppSeting.class);
			mfAppSetingFeign.update(mfAppSeting);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		return "MfAppSeting_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfAppSeting mfAppSeting = new MfAppSeting();
		try {
			mfAppSeting.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfAppSeting.setCriteriaList(mfAppSeting, ajaxData);// 我的筛选
			// mfAppSeting.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfAppSeting,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = mfAppSetingFeign.findByPage(ipage, mfAppSeting);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw new Exception(e.getMessage());
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
			FormData formappseting0002 = formService.getFormData("appseting0002");
			getFormValue(formappseting0002, getMapByJson(ajaxData));
			if (this.validateFormData(formappseting0002)) {
				MfAppSeting mfAppSeting = new MfAppSeting();
				setObjValue(formappseting0002, mfAppSeting);
				mfAppSetingFeign.insert(mfAppSeting);
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
			throw new Exception(e.getMessage());
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
		FormData formappseting0002 = formService.getFormData("appseting0002");
		getFormValue(formappseting0002, getMapByJson(ajaxData));
		MfAppSeting mfAppSetingJsp = new MfAppSeting();
		setObjValue(formappseting0002, mfAppSetingJsp);
		MfAppSeting mfAppSeting = mfAppSetingFeign.getById(mfAppSetingJsp);
		if (mfAppSeting != null) {
			try {
				mfAppSeting = (MfAppSeting) EntityUtil.reflectionSetVal(mfAppSeting, mfAppSetingJsp, getMapByJson(ajaxData));
				mfAppSetingFeign.update(mfAppSeting);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			} catch (Exception e) {
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", "新增失败");
				throw new Exception(e.getMessage());
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
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formappseting0002 = formService.getFormData("appseting0002");
			getFormValue(formappseting0002, getMapByJson(ajaxData));
			if (this.validateFormData(formappseting0002)) {
				MfAppSeting mfAppSeting = new MfAppSeting();
				setObjValue(formappseting0002, mfAppSeting);
				mfAppSetingFeign.update(mfAppSeting);
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
			throw new Exception(e.getMessage());
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
	public Map<String, Object> getByIdAjax(String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formappseting0002 = formService.getFormData("appseting0002");
		MfAppSeting mfAppSeting = new MfAppSeting();
		mfAppSeting.setId(id);
		mfAppSeting = mfAppSetingFeign.getById(mfAppSeting);
		getObjValue(formappseting0002, mfAppSeting, formData);
		if (mfAppSeting != null) {
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
	public Map<String, Object> deleteAjax(String id) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfAppSeting mfAppSeting = new MfAppSeting();
		mfAppSeting.setId(id);
		try {
			mfAppSetingFeign.delete(mfAppSeting);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw new Exception(e.getMessage());
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
		FormData formappseting0002 = formService.getFormData("appseting0002");
		model.addAttribute("formappseting0002", formappseting0002);
		model.addAttribute("query", "");
		return "MfAppSeting_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formappseting0002 = formService.getFormData("appseting0001");
		getFormValue(formappseting0002);
		MfAppSeting mfAppSeting = new MfAppSeting();
		mfAppSeting.setId(id);
		mfAppSeting = mfAppSetingFeign.getById(mfAppSeting);
		getObjValue(formappseting0002, mfAppSeting);
		model.addAttribute("formappseting0002", formappseting0002);
		model.addAttribute("query", "");
		return "MfAppSeting_Detail";
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
		FormData formappseting0002 = formService.getFormData("appseting0002");
		getFormValue(formappseting0002);
		boolean validateFlag = this.validateFormData(formappseting0002);
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
		FormData formappseting0002 = formService.getFormData("appseting0002");
		getFormValue(formappseting0002);
		boolean validateFlag = this.validateFormData(formappseting0002);
	}

}

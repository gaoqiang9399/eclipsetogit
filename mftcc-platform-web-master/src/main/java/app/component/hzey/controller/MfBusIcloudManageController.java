package app.component.hzey.controller;

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
import app.component.hzey.entity.MfBusIcloudManage;
import app.component.hzey.feign.MfBusIcloudManageFeign;
import app.util.toolkit.Ipage;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Title: MfBusIcloudManageAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Jul 19 15:07:00 CST 2017
 **/
@Controller
@RequestMapping("/mfBusIcloudManage")
public class MfBusIcloudManageController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfBusIcloudManageBo
	@Autowired
	private MfBusIcloudManageFeign mfBusIcloudManageFeign;
	// 全局变量

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		JSONArray jsonArray = new JSONArray();
		// 0、未绑定；1、已绑定；2、已下发；3、已绑手机 4、已修改密码 5、已锁定手机',
		String[] array = { "未绑定", "已绑定", "已下发", "已绑手机", "已修改密码 ", "已锁定手机" };
		for (int i = 0; i < array.length; i++) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("optName", array[i]);
			jsonObj.put("optCode", String.valueOf(i));
			jsonArray.add(jsonObj);
		}
		model.addAttribute("jsonArray", jsonArray);
		return "/component/hzey/MfBusIcloudManage_List";
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
			String ajaxData,Ipage ipage) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusIcloudManage mfBusIcloudManage = new MfBusIcloudManage();
		try {
			mfBusIcloudManage.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfBusIcloudManage.setCriteriaList(mfBusIcloudManage, ajaxData);// 我的筛选
			// mfBusIcloudManage.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfBusIcloudManage,"1000000001");//记录级权限控制方法
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfBusIcloudManage", mfBusIcloudManage));
			// 自定义查询Bo方法
			ipage = mfBusIcloudManageFeign.findByPage(ipage);
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
			FormData formhzey0002 = formService.getFormData("hzey0002");
			getFormValue(formhzey0002, getMapByJson(ajaxData));
			if (this.validateFormData(formhzey0002)) {
				MfBusIcloudManage mfBusIcloudManage = new MfBusIcloudManage();
				setObjValue(formhzey0002, mfBusIcloudManage);
				mfBusIcloudManageFeign.insert(mfBusIcloudManage);
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
	 * 修改密码操作
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
		FormData formhzey0002 = formService.getFormData("hzey0002");
		getFormValue(formhzey0002, getMapByJson(ajaxData));
		MfBusIcloudManage mfBusIcloudManageJsp = new MfBusIcloudManage();
		setObjValue(formhzey0002, mfBusIcloudManageJsp);
		MfBusIcloudManage mfBusIcloudManage = mfBusIcloudManageFeign.getById(mfBusIcloudManageJsp);
		if (mfBusIcloudManage != null) {
			try {
				mfBusIcloudManage = (MfBusIcloudManage) EntityUtil.reflectionSetVal(mfBusIcloudManage,
						mfBusIcloudManageJsp, getMapByJson(ajaxData));
				mfBusIcloudManage.setStatus("4");
				mfBusIcloudManageFeign.update(mfBusIcloudManage);
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
			FormData formhzey0002 = formService.getFormData("hzey0002");
			getFormValue(formhzey0002, getMapByJson(ajaxData));
			if (this.validateFormData(formhzey0002)) {
				MfBusIcloudManage mfBusIcloudManage = new MfBusIcloudManage();
				setObjValue(formhzey0002, mfBusIcloudManage);
				mfBusIcloudManageFeign.update(mfBusIcloudManage);
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
	public Map<String, Object> getByIdAjax(String icloudId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formhzey0002 = formService.getFormData("hzey0002");
		MfBusIcloudManage mfBusIcloudManage = new MfBusIcloudManage();
		mfBusIcloudManage.setIcloudId(icloudId);
		mfBusIcloudManage = mfBusIcloudManageFeign.getById(mfBusIcloudManage);
		getObjValue(formhzey0002, mfBusIcloudManage, formData);
		if (mfBusIcloudManage != null) {
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}

	// 修改密码
	/**
	 * Ajax异步解除绑定
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateCloudPwd")
	@ResponseBody
	public Map<String, Object> updateCloudPwd(Model model, String icloudId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusIcloudManage mfBusIcloudManage = new MfBusIcloudManage();
		mfBusIcloudManage.setIcloudId(icloudId);
		try {
			mfBusIcloudManageFeign.unBinding(mfBusIcloudManage);
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
	 * Ajax异步解除绑定
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/unBindAjax")
	@ResponseBody
	public Map<String, Object> unBindAjax(String icloudId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusIcloudManage mfBusIcloudManage = new MfBusIcloudManage();
		mfBusIcloudManage.setIcloudId(icloudId);
		try {
			mfBusIcloudManageFeign.unBinding(mfBusIcloudManage);
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
	 * Ajax异步解除绑定
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateCifInfoAjax")
	@ResponseBody
	public Map<String, Object> updateCifInfoAjax(String icloudId, String cusNo, String cusName) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusIcloudManage mfBusIcloudManage = new MfBusIcloudManage();
		mfBusIcloudManage.setIcloudId(icloudId);
		mfBusIcloudManage.setCusNo(cusNo);
		mfBusIcloudManage.setCusName(cusName);
		try {
			mfBusIcloudManageFeign.updateCifInfo(mfBusIcloudManage);
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
	 * Ajax异步获得cloufPwd
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getIcloudPwdAjax")
	@ResponseBody
	public Map<String, Object> getIcloudPwdAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String pwd = "";
		try {
			pwd = mfBusIcloudManageFeign.getIcloudPwd();
			dataMap.put("pwd", pwd);
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
	 * Ajax异步删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String icloudId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusIcloudManage mfBusIcloudManage = new MfBusIcloudManage();
		mfBusIcloudManage.setIcloudId(icloudId);
		try {
			mfBusIcloudManageFeign.delete(mfBusIcloudManage);
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
		FormData formhzey0002 = formService.getFormData("hzey0002");
		model.addAttribute("formhzey0002", formhzey0002);
		model.addAttribute("query", "");
		return "/component/hzey/MfBusIcloudManage_Insert";
	}

	/**
	 * 更新页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toUpdate")
	public String toUpdate(Model model, String icloudId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formhzeyupdate0001 = formService.getFormData("hzeyupdate0001");
		// 查询并且页面传值
		MfBusIcloudManage mfBusIcloudManage = new MfBusIcloudManage();
		mfBusIcloudManage.setIcloudId(icloudId);
		mfBusIcloudManage = mfBusIcloudManageFeign.getById(mfBusIcloudManage);
		getObjValue(formhzeyupdate0001, mfBusIcloudManage);
		model.addAttribute("mfBusIcloudManage", mfBusIcloudManage);
		model.addAttribute("formhzeyupdate0001", formhzeyupdate0001);
		model.addAttribute("query", "");
		return "/component/hzey/MfBusIcloudManage_Update";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String icloudId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formhzey0002 = formService.getFormData("hzey0001");
		getFormValue(formhzey0002);
		MfBusIcloudManage mfBusIcloudManage = new MfBusIcloudManage();
		mfBusIcloudManage.setIcloudId(icloudId);
		mfBusIcloudManage = mfBusIcloudManageFeign.getById(mfBusIcloudManage);
		getObjValue(formhzey0002, mfBusIcloudManage);
		model.addAttribute("mfBusIcloudManage", mfBusIcloudManage);
		model.addAttribute("formhzey0002", formhzey0002);
		model.addAttribute("query", "");
		return "/component/hzey/MfBusIcloudManage_Detail";
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
		FormData formhzey0002 = formService.getFormData("hzey0002");
		getFormValue(formhzey0002);
		boolean validateFlag = this.validateFormData(formhzey0002);
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
		FormData formhzey0002 = formService.getFormData("hzey0002");
		getFormValue(formhzey0002);
		boolean validateFlag = this.validateFormData(formhzey0002);
	}

}

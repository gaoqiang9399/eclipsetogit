package app.component.model.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.component.model.feign.MfNewTagColumnConfigFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import app.component.model.entity.MfTagColumnConfig;
import app.component.model.feign.MfTagColumnConfigFeign;
import app.util.toolkit.Ipage;

/**
 * Title: MfTagColumnConfigAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri Jan 05 15:14:28 CST 2018
 **/
@Controller
@RequestMapping("/mfTagColumnConfig")
public class MfTagColumnConfigController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfTagColumnConfigBo
	@Autowired
	private MfTagColumnConfigFeign mfTagColumnConfigFeign;
	@Autowired
	private MfNewTagColumnConfigFeign mfNewTagColumnConfigFeign;
	// 使用pageoffice的版本标识 0：老版本 1：新版本
	@Value("${mftcc.pageoffice.office_version:0}")
	private String pageOfficeSts;
	// 全局变量
	// 异步参数
	// 表单变量

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model,String keyNo) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("query", "");
		model.addAttribute("keyNo", keyNo);
		return "/component/model/MfTagColumnConfig_List";
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
			String keyNo,String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfTagColumnConfig mfTagColumnConfig = new MfTagColumnConfig();
		try {
			mfTagColumnConfig.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfTagColumnConfig.setCriteriaList(mfTagColumnConfig, ajaxData);// 我的筛选
			// mfTagColumnConfig.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfTagColumnConfig,"1000000001");//记录级权限控制方法
			mfTagColumnConfig.setKeyNo(keyNo);
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfTagColumnConfig",mfTagColumnConfig));
			if("0".equals(pageOfficeSts)){
				ipage = mfTagColumnConfigFeign.findByPage(ipage);
			}else if("1".equals(pageOfficeSts)){
				ipage =mfNewTagColumnConfigFeign.findByPage(ipage);
			}else {
			}
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
			FormData formtagColumnConfig0001 = formService.getFormData("tagColumnConfig0001");
			getFormValue(formtagColumnConfig0001, getMapByJson(ajaxData));
			if (this.validateFormData(formtagColumnConfig0001)) {
				MfTagColumnConfig mfTagColumnConfig = new MfTagColumnConfig();
				setObjValue(formtagColumnConfig0001, mfTagColumnConfig);
				if("0".equals(pageOfficeSts)){
					mfTagColumnConfigFeign.insert(mfTagColumnConfig);
				}else if("1".equals(pageOfficeSts)){
					mfNewTagColumnConfigFeign.insert(mfTagColumnConfig);
				}else {
				}
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
		FormData formtagColumnConfig0002 = formService.getFormData("tagColumnConfig0002");
		getFormValue(formtagColumnConfig0002, getMapByJson(ajaxData));
		MfTagColumnConfig mfTagColumnConfigJsp = new MfTagColumnConfig();
		setObjValue(formtagColumnConfig0002, mfTagColumnConfigJsp);
		MfTagColumnConfig mfTagColumnConfig = new MfTagColumnConfig();
		if("0".equals(pageOfficeSts)){
			mfTagColumnConfig = mfTagColumnConfigFeign.getById(mfTagColumnConfigJsp);
		}else if("1".equals(pageOfficeSts)){
			mfTagColumnConfig = mfNewTagColumnConfigFeign.getById(mfTagColumnConfigJsp);
		}else {
		}
		if (mfTagColumnConfig != null) {
			try {
				mfTagColumnConfig = (MfTagColumnConfig) EntityUtil.reflectionSetVal(mfTagColumnConfig,
						mfTagColumnConfigJsp, getMapByJson(ajaxData));
				if("0".equals(pageOfficeSts)){
					mfTagColumnConfigFeign.update(mfTagColumnConfig);
				}else if("1".equals(pageOfficeSts)){
					mfNewTagColumnConfigFeign.update(mfTagColumnConfig);
				}else {
				}
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
			FormData formtagColumnConfig0002 = formService.getFormData("tagColumnConfig0002");
			getFormValue(formtagColumnConfig0002, getMapByJson(ajaxData));
			if (this.validateFormData(formtagColumnConfig0002)) {
				MfTagColumnConfig mfTagColumnConfig = new MfTagColumnConfig();
				setObjValue(formtagColumnConfig0002, mfTagColumnConfig);
				if("0".equals(pageOfficeSts)){
					mfTagColumnConfigFeign.update(mfTagColumnConfig);
				}else if("1".equals(pageOfficeSts)){
					mfNewTagColumnConfigFeign.update(mfTagColumnConfig);
				}else {
				}
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
	public Map<String, Object> getByIdAjax(String configId ) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formtagColumnConfig0002 = formService.getFormData("tagColumnConfig0002");
		MfTagColumnConfig mfTagColumnConfig = new MfTagColumnConfig();
		mfTagColumnConfig.setConfigId(configId);
		if("0".equals(pageOfficeSts)){
			mfTagColumnConfig = mfTagColumnConfigFeign.getById(mfTagColumnConfig);
		}else if("1".equals(pageOfficeSts)){
			mfTagColumnConfig = mfNewTagColumnConfigFeign.getById(mfTagColumnConfig);
		}else {
		}
		getObjValue(formtagColumnConfig0002, mfTagColumnConfig, formData);
		if (mfTagColumnConfig != null) {
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
	public Map<String, Object> deleteAjax(String configId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfTagColumnConfig mfTagColumnConfig = new MfTagColumnConfig();
		mfTagColumnConfig.setConfigId(configId);
		try {
			if("0".equals(pageOfficeSts)){
				mfTagColumnConfigFeign.delete(mfTagColumnConfig);
			}else if("1".equals(pageOfficeSts)){
				mfNewTagColumnConfigFeign.delete(mfTagColumnConfig);
			}else {
			}
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
	public String input(Model model,String keyNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formtagColumnConfig0001 = formService.getFormData("tagColumnConfig0001");
		MfTagColumnConfig mfTagColumnConfig = new MfTagColumnConfig();
		mfTagColumnConfig.setKeyNo(keyNo);
		getObjValue(formtagColumnConfig0001, mfTagColumnConfig);
		model.addAttribute("formtagColumnConfig0001", formtagColumnConfig0001);
		model.addAttribute("query", "");
		return "/component/model/MfTagColumnConfig_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String configId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formtagColumnConfig0001 = formService.getFormData("tagColumnConfig0001");
		getFormValue(formtagColumnConfig0001);
		MfTagColumnConfig mfTagColumnConfig = new MfTagColumnConfig();
		mfTagColumnConfig.setConfigId(configId);
		if("0".equals(pageOfficeSts)){
			mfTagColumnConfig = mfTagColumnConfigFeign.getById(mfTagColumnConfig);
		}else if("1".equals(pageOfficeSts)){
			mfTagColumnConfig = mfNewTagColumnConfigFeign.getById(mfTagColumnConfig);
		}else {
		}
		getObjValue(formtagColumnConfig0001, mfTagColumnConfig);
		model.addAttribute("formtagColumnConfig0001", formtagColumnConfig0001);
		model.addAttribute("query", "");
		return "/component/model/MfTagColumnConfig_Detail";
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
		FormData formtagColumnConfig0002 = formService.getFormData("tagColumnConfig0002");
		getFormValue(formtagColumnConfig0002);
		boolean validateFlag = this.validateFormData(formtagColumnConfig0002);
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
		FormData formtagColumnConfig0002 = formService.getFormData("tagColumnConfig0002");
		getFormValue(formtagColumnConfig0002);
		boolean validateFlag = this.validateFormData(formtagColumnConfig0002);
	}

}

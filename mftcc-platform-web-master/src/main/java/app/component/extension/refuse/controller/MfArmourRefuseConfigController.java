package app.component.extension.refuse.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.mftcc.util.StringUtil;
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
import app.component.extension.refuse.entity.MfArmourRefuseConfig;
import app.component.extension.refuse.feign.MfArmourRefuseConfigFeign;
import app.util.toolkit.Ipage;
import net.sf.json.JSONArray;

/**
 * Title: MfArmourRefuseConfigAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Jan 03 15:38:12 CST 2018
 **/
@Controller
@RequestMapping("/mfArmourRefuseConfig")
public class MfArmourRefuseConfigController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfArmourRefuseConfigBo
	@Autowired
	private MfArmourRefuseConfigFeign mfArmourRefuseConfigFeign;

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		return "MfArmourRefuseConfig_List";
	}

	/**
	 * @Description:获取所有内容
	 * @return
	 * @throws Exception
	 * @author: 李伟
	 * @date: 2018-1-2 下午5:21:41
	 */
	@RequestMapping(value = "/getListAllAjax")
	@ResponseBody
	public Map<String, Object> getListAllAjax(String opinionType) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ActionContext.initialize(request, response);
		MfArmourRefuseConfig mfArmourRefuseConfig = new MfArmourRefuseConfig();
		mfArmourRefuseConfig.setUseFlag("1");
		mfArmourRefuseConfig.setType(opinionType);
		dataMap.put("items", JSONArray.fromObject(mfArmourRefuseConfigFeign.getAll(mfArmourRefuseConfig)).toString());
		return dataMap;
	}

	@RequestMapping(value = "/getListByTypeAjax")
	@ResponseBody
	public Map<String, Object> getListByTypeAjax(String levFlag,String uplev,String type) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ActionContext.initialize(request, response);
		MfArmourRefuseConfig mfArmourRefuseConfig = new MfArmourRefuseConfig();
		mfArmourRefuseConfig.setUseFlag("1");
		mfArmourRefuseConfig.setType(type);
		mfArmourRefuseConfig.setLevFlag(levFlag);
		if(StringUtil.isNotEmpty(uplev)){
			//支持父节点多选的情况
			List<String> uplevList = Arrays.asList(uplev.split("\\|"));
			mfArmourRefuseConfig.setUplevList(uplevList);
		}
		List<MfArmourRefuseConfig> mfArmourRefuseConfigList = mfArmourRefuseConfigFeign.getListByTypeAjax(mfArmourRefuseConfig);
		dataMap.put("items", JSONArray.fromObject(mfArmourRefuseConfigList));
		return dataMap;
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
		MfArmourRefuseConfig mfArmourRefuseConfig = new MfArmourRefuseConfig();
		try {
			mfArmourRefuseConfig.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfArmourRefuseConfig.setCriteriaList(mfArmourRefuseConfig, ajaxData);// 我的筛选
			// mfArmourRefuseConfig.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfArmourRefuseConfig,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = mfArmourRefuseConfigFeign.findByPage(ipage, mfArmourRefuseConfig);
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
			FormData formMfArmourRefuseConfig0002 = formService.getFormData("MfArmourRefuseConfig0002");
			getFormValue(formMfArmourRefuseConfig0002, getMapByJson(ajaxData));
			if (this.validateFormData(formMfArmourRefuseConfig0002)) {
				MfArmourRefuseConfig mfArmourRefuseConfig = new MfArmourRefuseConfig();
				setObjValue(formMfArmourRefuseConfig0002, mfArmourRefuseConfig);
				mfArmourRefuseConfigFeign.insert(mfArmourRefuseConfig);
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
		FormData formMfArmourRefuseConfig0002 = formService.getFormData("MfArmourRefuseConfig0002");
		getFormValue(formMfArmourRefuseConfig0002, getMapByJson(ajaxData));
		MfArmourRefuseConfig mfArmourRefuseConfigJsp = new MfArmourRefuseConfig();
		setObjValue(formMfArmourRefuseConfig0002, mfArmourRefuseConfigJsp);
		MfArmourRefuseConfig mfArmourRefuseConfig = mfArmourRefuseConfigFeign.getById(mfArmourRefuseConfigJsp);
		if (mfArmourRefuseConfig != null) {
			try {
				mfArmourRefuseConfig = (MfArmourRefuseConfig) EntityUtil.reflectionSetVal(mfArmourRefuseConfig, mfArmourRefuseConfigJsp, getMapByJson(ajaxData));
				mfArmourRefuseConfigFeign.update(mfArmourRefuseConfig);
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
			FormData formMfArmourRefuseConfig0002 = formService.getFormData("MfArmourRefuseConfig0002");
			getFormValue(formMfArmourRefuseConfig0002, getMapByJson(ajaxData));
			if (this.validateFormData(formMfArmourRefuseConfig0002)) {
				MfArmourRefuseConfig mfArmourRefuseConfig = new MfArmourRefuseConfig();
				setObjValue(formMfArmourRefuseConfig0002, mfArmourRefuseConfig);
				mfArmourRefuseConfigFeign.update(mfArmourRefuseConfig);
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
		FormData formMfArmourRefuseConfig0002 = formService.getFormData("MfArmourRefuseConfig0002");
		MfArmourRefuseConfig mfArmourRefuseConfig = new MfArmourRefuseConfig();
		mfArmourRefuseConfig.setId(id);
		mfArmourRefuseConfig = mfArmourRefuseConfigFeign.getById(mfArmourRefuseConfig);
		getObjValue(formMfArmourRefuseConfig0002, mfArmourRefuseConfig, formData);
		if (mfArmourRefuseConfig != null) {
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
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfArmourRefuseConfig mfArmourRefuseConfig = new MfArmourRefuseConfig();
		mfArmourRefuseConfig.setId(id);
		try {
			mfArmourRefuseConfigFeign.delete(mfArmourRefuseConfig);
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
		FormData formMfArmourRefuseConfig0002 = formService.getFormData("MfArmourRefuseConfig0002");
		model.addAttribute("formMfArmourRefuseConfig0002", formMfArmourRefuseConfig0002);
		model.addAttribute("query", "");
		return "MfArmourRefuseConfig_Insert";
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
		FormData formMfArmourRefuseConfig001 = formService.getFormData("MfArmourRefuseConfig001");
		getFormValue(formMfArmourRefuseConfig001);
		MfArmourRefuseConfig mfArmourRefuseConfig = new MfArmourRefuseConfig();
		mfArmourRefuseConfig.setId(id);
		mfArmourRefuseConfig = mfArmourRefuseConfigFeign.getById(mfArmourRefuseConfig);
		getObjValue(formMfArmourRefuseConfig001, mfArmourRefuseConfig);
		model.addAttribute("mfArmourRefuseConfig", mfArmourRefuseConfig);
		model.addAttribute("formMfArmourRefuseConfig001", formMfArmourRefuseConfig001);
		model.addAttribute("query", "");
		return "MfArmourRefuseConfig_Detail";
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
		FormData formMfArmourRefuseConfig0002 = formService.getFormData("MfArmourRefuseConfig0002");
		getFormValue(formMfArmourRefuseConfig0002);
		boolean validateFlag = this.validateFormData(formMfArmourRefuseConfig0002);
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
		FormData formMfArmourRefuseConfig0002 = formService.getFormData("MfArmourRefuseConfig0002");
		getFormValue(formMfArmourRefuseConfig0002);
		boolean validateFlag = this.validateFormData(formMfArmourRefuseConfig0002);
	}

}

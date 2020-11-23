package app.component.importexcel.controller;

import app.component.common.EntityUtil;
import app.component.importexcel.entity.MfImpotCheckConfig;
import app.component.importexcel.feign.MfImpotCheckConfigFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: MfImpotCheckConfigAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Sat May 27 17:08:34 CST 2017
 **/
@Controller
@RequestMapping("/mfImpotCheckConfig")
public class MfImpotCheckConfigController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfImpotCheckConfigBo
	@Autowired
	private MfImpotCheckConfigFeign mfImpotCheckConfigFeign;

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		return "/component/eval/MfImpotCheckConfig_List";
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
		MfImpotCheckConfig mfImpotCheckConfig = new MfImpotCheckConfig();
		try {
			mfImpotCheckConfig.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfImpotCheckConfig.setCriteriaList(mfImpotCheckConfig, ajaxData);// 我的筛选
			// mfImpotCheckConfig.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfImpotCheckConfig,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = mfImpotCheckConfigFeign.findByPage(ipage, mfImpotCheckConfig);
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
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formgradecard0002 = formService.getFormData("gradecard0002");
			getFormValue(formgradecard0002, getMapByJson(ajaxData));
			if (this.validateFormData(formgradecard0002)) {
				MfImpotCheckConfig mfImpotCheckConfig = new MfImpotCheckConfig();
				setObjValue(formgradecard0002, mfImpotCheckConfig);
				List<MfImpotCheckConfig> mfImpotCheckConfigList = null;
						//mfImpotCheckConfigFeign.getEvalGradeCardList(mfImpotCheckConfig);
				if(mfImpotCheckConfigList!=null&&mfImpotCheckConfigList.size()>0){
					dataMap.put("flag", "error");
					dataMap.put("msg", MessageEnum.EXIST_INFORMATION_EVAL.getMessage("该评分卡别名"));
				}else{
					mfImpotCheckConfig = mfImpotCheckConfigFeign.insert(mfImpotCheckConfig);
					dataMap.put("flag", "success");
					dataMap.put("mfImpotCheckConfig", mfImpotCheckConfig);
					dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
				}
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
	@RequestMapping(value = "/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formgradecard0002 = formService.getFormData("gradecard0002");
		getFormValue(formgradecard0002, getMapByJson(ajaxData));
		MfImpotCheckConfig mfImpotCheckConfigJsp = new MfImpotCheckConfig();
		setObjValue(formgradecard0002, mfImpotCheckConfigJsp);
		MfImpotCheckConfig mfImpotCheckConfig = mfImpotCheckConfigFeign.getById(mfImpotCheckConfigJsp);
		if (mfImpotCheckConfig != null) {
			try {
				mfImpotCheckConfig = (MfImpotCheckConfig) EntityUtil.reflectionSetVal(mfImpotCheckConfig, mfImpotCheckConfigJsp,
						getMapByJson(ajaxData));
				mfImpotCheckConfigFeign.update(mfImpotCheckConfig);
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
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formgradecard0003 = formService.getFormData("gradecard0003");
			getFormValue(formgradecard0003, getMapByJson(ajaxData));
			if (this.validateFormData(formgradecard0003)) {
				MfImpotCheckConfig mfImpotCheckConfig = new MfImpotCheckConfig();
				setObjValue(formgradecard0003, mfImpotCheckConfig);
				mfImpotCheckConfigFeign.update(mfImpotCheckConfig);
				dataMap.put("mfImpotCheckConfig", mfImpotCheckConfig);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
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
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String configId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formgradecard0002 = formService.getFormData("gradecard0002");
		MfImpotCheckConfig mfImpotCheckConfig = new MfImpotCheckConfig();
		mfImpotCheckConfig.setConfigId(configId);
		mfImpotCheckConfig = mfImpotCheckConfigFeign.getById(mfImpotCheckConfig);
		getObjValue(formgradecard0002, mfImpotCheckConfig, formData);
		if (mfImpotCheckConfig != null) {
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
		MfImpotCheckConfig mfImpotCheckConfig = new MfImpotCheckConfig();
		mfImpotCheckConfig.setConfigId(configId);
		try {
			mfImpotCheckConfigFeign.delete(mfImpotCheckConfig);
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
	@RequestMapping(value = "/input")
	public String input(Model model, String evalScenceNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formgradecard0002 = formService.getFormData("gradecard0002");
		MfImpotCheckConfig mfImpotCheckConfig = new MfImpotCheckConfig();
		/*MfImpotCheckConfig mfImpotCheckConfig = new MfImpotCheckConfig();
		mfImpotCheckConfig.setEvalScenceNo(evalScenceNo);
		EvalScenceConfig evalScenceConfig = new EvalScenceConfig();
		evalScenceConfig.setEvalScenceNo(evalScenceNo);
		evalScenceConfig = evalScenceConfigFeign.getById(evalScenceConfig);
		mfImpotCheckConfig.setEvalScenceName(evalScenceConfig.getEvalScenceName());*/
		getObjValue(formgradecard0002, mfImpotCheckConfig);
		model.addAttribute("formgradecard0002", formgradecard0002);
		model.addAttribute("query", "");
		return "/component/eval/MfImpotCheckConfig_Insert";
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
		FormData formgradecard0002 = formService.getFormData("gradecard0002");
		getFormValue(formgradecard0002);
		MfImpotCheckConfig mfImpotCheckConfig = new MfImpotCheckConfig();
		setObjValue(formgradecard0002, mfImpotCheckConfig);
		mfImpotCheckConfigFeign.insert(mfImpotCheckConfig);
		getObjValue(formgradecard0002, mfImpotCheckConfig);
		this.addActionMessage(model, "保存成功");
		List<MfImpotCheckConfig> mfImpotCheckConfigList = (List<MfImpotCheckConfig>) mfImpotCheckConfigFeign
				.findByPage(this.getIpage(), mfImpotCheckConfig).getResult();
		model.addAttribute("mfImpotCheckConfigList", mfImpotCheckConfigList);
		model.addAttribute("formgradecard0002", formgradecard0002);
		model.addAttribute("query", "");
		return "/component/eval/MfImpotCheckConfig_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String ajaxData,String configId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formgradecard0003 = formService.getFormData("gradecard0003");
		getFormValue(formgradecard0003);
		MfImpotCheckConfig mfImpotCheckConfig = new MfImpotCheckConfig();
		mfImpotCheckConfig.setConfigId(configId);
		mfImpotCheckConfig = mfImpotCheckConfigFeign.getById(mfImpotCheckConfig);
		getObjValue(formgradecard0003, mfImpotCheckConfig);
		model.addAttribute("formgradecard0003", formgradecard0003);
		model.addAttribute("query", "");
		return "/component/eval/MfImpotCheckConfig_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String ajaxData,String configId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfImpotCheckConfig mfImpotCheckConfig = new MfImpotCheckConfig();
		mfImpotCheckConfig.setConfigId(configId);
		mfImpotCheckConfigFeign.delete(mfImpotCheckConfig);
		return getListPage(model);
	}

}

package app.component.model.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.component.prdct.entity.MfKindNodeTemplate;
import app.component.prdct.feign.MfKindNodeTemplateFeign;
import cn.mftcc.util.StringUtil;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import app.base.User;
import app.component.common.EntityUtil;
import app.component.model.entity.MfTemplateModelRel;
import app.component.model.feign.MfSysTemplateFeign;
import app.component.model.feign.MfTemplateModelRelFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;

/**
 * Title: MfTemplateModelRelAction.java Description:
 *
 * @author:kaifa@dhcc.com.cn
 * @Tue Nov 22 11:17:10 CST 2016
 **/
@Controller
@RequestMapping("/mfTemplateModelRel")
public class MfTemplateModelRelController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfTemplateModelRelBo
	@Autowired
	private MfTemplateModelRelFeign mfTemplateModelRelFeign;
	@Autowired
	private MfSysTemplateFeign mfSysTemplateFeign;
	@Autowired
	private MfKindNodeTemplateFeign mfKindNodeTemplateFeign;
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
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		String templateNo = request.getParameter("templateNo");
		String templateType = request.getParameter("templateType");
		model.addAttribute("query", "");
		model.addAttribute("templateNo", templateNo);
		model.addAttribute("templateType", templateType);
		return "/component/model/MfTemplateModelRel_List";
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
											  String ajaxData,String templateNo,String templateType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfTemplateModelRel mfTemplateModelRel = new MfTemplateModelRel();
		try {
			mfTemplateModelRel.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfTemplateModelRel.setCriteriaList(mfTemplateModelRel, ajaxData);// 我的筛选
			// this.getRoleConditions(mfTemplateModelRel,"1000000001");//记录级权限控制方法
			mfTemplateModelRel.setTemplateType(templateType);
			mfTemplateModelRel.setTemplateNo(templateNo);
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfTemplateModelRel", mfTemplateModelRel));
			// 自定义查询Bo方法
			ipage = mfTemplateModelRelFeign.findByPage(ipage);
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
			FormData formmodelrel0002 = formService.getFormData("modelrel0002");
			getFormValue(formmodelrel0002, getMapByJson(ajaxData));
			if (this.validateFormData(formmodelrel0002)) {
				MfTemplateModelRel mfTemplateModelRel = new MfTemplateModelRel();
				setObjValue(formmodelrel0002, mfTemplateModelRel);
				mfTemplateModelRelFeign.insert(mfTemplateModelRel);
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
	 *
	 * 方法描述： 文档保存后插入保存信息
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @param modelNo
	 * @param filename
	 * @param saveFileName
	 * @param filesavepath
	 * @param templateNo
	 * @date 2016-9-8 下午6:53:25
	 */
	@ResponseBody
	@RequestMapping(value = "/insertTemplateModelRel")
	public Map<String, Object> insertTemplateModelRel(Model model, String relNo, String modelNo, String filename, String saveFileName, String filesavepath, String templateNo, String templateType) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {

			dataMap.put("regNo", User.getRegNo(request));
			dataMap.put("regName", User.getRegName(request));
			dataMap.put("orgNo", User.getOrgNo(request));
			dataMap.put("orgName", User.getOrgName(request));

			dataMap.put("relNo", relNo);
			dataMap.put("modelNo", modelNo);
			dataMap.put("fileName", filename);
			dataMap.put("saveFileName", saveFileName);
			dataMap.put("filesavepath", filesavepath);
			dataMap.put("templateNo", templateNo);
			dataMap.put("templateType", templateType);
			mfTemplateModelRelFeign.insertTemplateModelRel(dataMap);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
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
		FormData formmodelrel0002 = formService.getFormData("modelrel0002");
		getFormValue(formmodelrel0002, getMapByJson(ajaxData));
		MfTemplateModelRel mfTemplateModelRelJsp = new MfTemplateModelRel();
		setObjValue(formmodelrel0002, mfTemplateModelRelJsp);
		MfTemplateModelRel mfTemplateModelRel = mfTemplateModelRelFeign.getById(mfTemplateModelRelJsp);
		if (mfTemplateModelRel != null) {
			try {
				mfTemplateModelRel = (MfTemplateModelRel) EntityUtil.reflectionSetVal(mfTemplateModelRel,
						mfTemplateModelRelJsp, getMapByJson(ajaxData));
				mfTemplateModelRelFeign.update(mfTemplateModelRel);
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
			FormData formmodelrel0002 = formService.getFormData("modelrel0002");
			getFormValue(formmodelrel0002, getMapByJson(ajaxData));
			if (this.validateFormData(formmodelrel0002)) {
				MfTemplateModelRel mfTemplateModelRel = new MfTemplateModelRel();
				setObjValue(formmodelrel0002, mfTemplateModelRel);
				mfTemplateModelRelFeign.update(mfTemplateModelRel);
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
	public Map<String, Object> getByIdAjax(String serialNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formmodelrel0002 = formService.getFormData("modelrel0002");
		MfTemplateModelRel mfTemplateModelRel = new MfTemplateModelRel();
		mfTemplateModelRel.setSerialNo(serialNo);
		mfTemplateModelRel = mfTemplateModelRelFeign.getById(mfTemplateModelRel);
		getObjValue(formmodelrel0002, mfTemplateModelRel, formData);
		if (mfTemplateModelRel != null) {
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
	public Map<String, Object> deleteAjax(String serialNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfTemplateModelRel mfTemplateModelRel = new MfTemplateModelRel();
		mfTemplateModelRel.setSerialNo(serialNo);
		try {
			mfTemplateModelRelFeign.delete(mfTemplateModelRel);
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
	public String input(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formmodelrel0002 = formService.getFormData("modelrel0002");
		model.addAttribute("formmodelrel0002", formmodelrel0002);
		model.addAttribute("query", "");
		return "/component/model/MfTemplateModelRel_Insert";
	}

	/***
	 * 新增
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formmodelrel0002 = formService.getFormData("modelrel0002");
		getFormValue(formmodelrel0002);
		MfTemplateModelRel mfTemplateModelRel = new MfTemplateModelRel();
		setObjValue(formmodelrel0002, mfTemplateModelRel);
		mfTemplateModelRelFeign.insert(mfTemplateModelRel);
		getObjValue(formmodelrel0002, mfTemplateModelRel);
		this.addActionMessage(model, "保存成功");
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("mfTemplateModelRel", mfTemplateModelRel));
		List<MfTemplateModelRel> mfTemplateModelRelList = (List<MfTemplateModelRel>) mfTemplateModelRelFeign
				.findByPage(ipage).getResult();
		model.addAttribute("formmodelrel0002", formmodelrel0002);
		model.addAttribute("mfTemplateModelRelList", mfTemplateModelRelList);
		model.addAttribute("query", "");
		return "/component/model/MfTemplateModelRel_Insert";
	}

	/**
	 * 查询
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String serialNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formmodelrel0001 = formService.getFormData("modelrel0001");
		getFormValue(formmodelrel0001);
		MfTemplateModelRel mfTemplateModelRel = new MfTemplateModelRel();
		mfTemplateModelRel.setSerialNo(serialNo);
		mfTemplateModelRel = mfTemplateModelRelFeign.getById(mfTemplateModelRel);
		getObjValue(formmodelrel0001, mfTemplateModelRel);
		model.addAttribute("formmodelrel0001", formmodelrel0001);
		model.addAttribute("query", "");
		return "/component/model/MfTemplateModelRel_Detail";
	}

	/**
	 *
	 * 方法描述： 获得是否保存过文档模板
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @param modelNo
	 * @param templateNo
	 * @date 2016-9-8 下午10:00:58
	 */
	@ResponseBody
	@RequestMapping(value = "/getIfSaveModleInfo")
	public Map<String, Object> getIfSaveModleInfo(Model model, String relNo, String modelNo, String templateNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfTemplateModelRel mfTemplateModelRel = new MfTemplateModelRel();
			mfTemplateModelRel.setRelNo(relNo);
			mfTemplateModelRel.setModelNo(modelNo);
			mfTemplateModelRel.setTemplateNo(templateNo);
			List<MfTemplateModelRel> mfTemplateModelRelList = mfTemplateModelRelFeign.getTemplateModelRelList(mfTemplateModelRel);

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
	 *
	 * 方法描述： 获得是否保存过文档及保存的文档信息
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @param templateNo
	 * @date 2017-8-7 上午10:01:00
	 */
	@ResponseBody
	@RequestMapping(value = "/getIfSaveTemplateInfo")
	public Map<String,Object> getIfSaveTemplateInfo(Model model, String relNo, String templateNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String realFilePath = request.getSession().getServletContext().getRealPath("/").split("webapps")[0];
			MfTemplateModelRel mfTemplateModelRel = new MfTemplateModelRel();
			mfTemplateModelRel.setRelNo(relNo);
			mfTemplateModelRel.setTemplateNo(templateNo);
			dataMap = mfTemplateModelRelFeign.getIfSaveTemplateInfo(mfTemplateModelRel, realFilePath);
			dataMap.put("opName", User.getRegName(request));
			dataMap.put("orgNo", User.getOrgNo(request));
			dataMap.put("orgName", User.getOrgName(request));
			dataMap.put("userId", User.getRegNo(request));
			dataMap.put("templateNo", templateNo);
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
	 * 删除
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String serialNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfTemplateModelRel mfTemplateModelRel = new MfTemplateModelRel();
		mfTemplateModelRel.setSerialNo(serialNo);
		mfTemplateModelRelFeign.delete(mfTemplateModelRel);
		return getListPage(model);
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
		FormData formmodelrel0002 = formService.getFormData("modelrel0002");
		getFormValue(formmodelrel0002);
		boolean validateFlag = this.validateFormData(formmodelrel0002);
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
		FormData formmodelrel0002 = formService.getFormData("modelrel0002");
		getFormValue(formmodelrel0002);
		boolean validateFlag = this.validateFormData(formmodelrel0002);
	}

	/**
	 *
	 * 方法描述： 查询产品配置下的文档模板
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author lwq
	 * @param templateType
	 * @date 2017-9-27 上午11:34:22
	 */
	@ResponseBody
	@RequestMapping(value = "/getTemplateConfigInfo")
	public Map<String,Object> getTemplateConfigInfo(Model model, String modelNo, String templateType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfKindNodeTemplate mfKindNodeTemplate = new MfKindNodeTemplate();
			mfKindNodeTemplate.setKindNo(modelNo);
			mfKindNodeTemplate.setTemplateType(templateType);
			List<MfKindNodeTemplate> nodeTemplateList = mfKindNodeTemplateFeign.getNodeTemplateList(mfKindNodeTemplate);
			if (nodeTemplateList == null || nodeTemplateList.size() < 1) {
				dataMap.put("flag", "error");
				String templateName = "";
				if("10".equals(templateType)){
					templateName = "放款凭证";
				}else if("9".equals(templateType)){
					templateName = "还款凭证";
				}else {
				}
				dataMap.put("msg", MessageEnum.FIRST_OPERATION.getMessage("配置此产品的"+templateName+"文档"));
			} else {
				String ajaxData = new Gson().toJson(nodeTemplateList);
				dataMap.put("flag", "success");
				dataMap.put("ajaxData", ajaxData);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			throw e;
		}

		return dataMap;
	}

	/**
	 *@desc 空白模板打印记录那个操作员打印的信息
	 *@author lwq
	 *@date 2018/9/27 18:06
	 *@parm [model, ajaxdata]
	 *@return java.util.Map<java.lang.String,java.lang.Object>
	 **/
	@ResponseBody
	@RequestMapping(value = "/insertTemplateModelRelAjax")
	public Map<String, Object> insertTemplateModelRelAjax(Model model, String ajaxdata) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = new Gson().fromJson(ajaxdata,new TypeToken<Map<String, Object>>() {}.getType());
			dataMap.put("regNo", User.getRegNo(request));
			dataMap.put("regName", User.getRegName(request));
			dataMap.put("orgNo", User.getOrgNo(request));
			dataMap.put("orgName", User.getOrgName(request));
			dataMap.put("relNo", StringUtil.KillNull((String) dataMap.get("relNo")));
			mfTemplateModelRelFeign.insertTemplateModelRel(dataMap);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
			throw e;
		}
		return dataMap;
	}

}

package app.component.pledge.controller;

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

import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.pledge.entity.MfPledgeDynamicForm;
import app.component.pledge.feign.MfPledgeDynamicFormFeign;
import app.component.prdct.entity.MfSysKind;
import app.component.prdctinterface.PrdctInterfaceFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import net.sf.json.JSONObject;

/**
 * Title: MfPledgeDynamicFormAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu Nov 17 15:37:26 CST 2016
 **/
@Controller
@RequestMapping("/mfPledgeDynamicForm")
public class MfPledgeDynamicFormAction extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入cwComItemFeign
	@Autowired
	private MfPledgeDynamicFormFeign mfPledgeDynamicFormFeign;
	@Autowired
	private PrdctInterfaceFeign prdctInterfaceFeign;

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		return "/component/pledge/MfPledgeDynamicForm_List";
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
			String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfPledgeDynamicForm mfPledgeDynamicForm = new MfPledgeDynamicForm();
		try {
			mfPledgeDynamicForm.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfPledgeDynamicForm.setCriteriaList(mfPledgeDynamicForm, ajaxData);// 我的筛选
			// this.getRoleConditions(mfPledgeDynamicForm,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);
			// 自定义查询Feign方法
			ipage = mfPledgeDynamicFormFeign.findByPage(ipage, mfPledgeDynamicForm);
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
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formpledy0002 = formService.getFormData("pledy0002");
			getFormValue(formpledy0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpledy0002)) {
				MfPledgeDynamicForm mfPledgeDynamicForm = new MfPledgeDynamicForm();
				setObjValue(formpledy0002, mfPledgeDynamicForm);
				mfPledgeDynamicFormFeign.insert(mfPledgeDynamicForm);
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
	@RequestMapping(value = "/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formpledy0002 = formService.getFormData("pledy0002");
		getFormValue(formpledy0002, getMapByJson(ajaxData));
		MfPledgeDynamicForm mfPledgeDynamicFormJsp = new MfPledgeDynamicForm();
		setObjValue(formpledy0002, mfPledgeDynamicFormJsp);
		MfPledgeDynamicForm mfPledgeDynamicForm = mfPledgeDynamicFormFeign.getById(mfPledgeDynamicFormJsp);
		if (mfPledgeDynamicForm != null) {
			try {
				mfPledgeDynamicForm = (MfPledgeDynamicForm) EntityUtil.reflectionSetVal(mfPledgeDynamicForm,
						mfPledgeDynamicFormJsp, getMapByJson(ajaxData));
				mfPledgeDynamicFormFeign.update(mfPledgeDynamicForm);
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
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formpledy0002 = formService.getFormData("pledy0002");
			getFormValue(formpledy0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpledy0002)) {
				MfPledgeDynamicForm mfPledgeDynamicForm = new MfPledgeDynamicForm();
				setObjValue(formpledy0002, mfPledgeDynamicForm);
				mfPledgeDynamicFormFeign.update(mfPledgeDynamicForm);
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
	 * AJAX更新保存
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateStsAjax")
	@ResponseBody
	public Map<String, Object> updateStsAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			JSONObject jobj = JSONObject.fromObject(ajaxData);
			FormData formpledy0002 = formService.getFormData("pledy0002");
			getFormValue(formpledy0002, jobj);
			MfPledgeDynamicForm mfPledgeDynamicForm = new MfPledgeDynamicForm();
			setObjValue(formpledy0002, mfPledgeDynamicForm);
			mfPledgeDynamicFormFeign.updateSts(mfPledgeDynamicForm);
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
	 * 
	 * 方法描述： 打开表单
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2016-11-17 下午8:27:18
	 */
	@RequestMapping(value = "/getDesignForm")
	@ResponseBody
	public Map<String, Object> getDesignForm(String pleFormNo, String formtype) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> dataMapTmp = new HashMap<String, Object>();
		try {
			MfPledgeDynamicForm mfPledgeDynamicForm = new MfPledgeDynamicForm();
			mfPledgeDynamicForm.setPleFormNo(pleFormNo);
			mfPledgeDynamicForm = mfPledgeDynamicFormFeign.getById(mfPledgeDynamicForm);
			if ("add".equals(formtype)) {
				if ("".equals(mfPledgeDynamicForm.getAddFormId()) | mfPledgeDynamicForm.getAddFormId() == null) {
					dataMap.put("flag", "0");
					dataMap.put("formid_new", mfPledgeDynamicForm.getMotherAddFormId());
				} else {
					dataMap.put("flag", "1");
					dataMap.put("formid_new", mfPledgeDynamicForm.getAddFormId());
				}
			} else {
				if ("".equals(mfPledgeDynamicForm.getDetailFormId()) | mfPledgeDynamicForm.getDetailFormId() == null) {
					dataMap.put("flag", "0");
					dataMap.put("formid_new", mfPledgeDynamicForm.getMotherDetailFormId());
				} else {
					dataMap.put("flag", "1");
					dataMap.put("formid_new", mfPledgeDynamicForm.getDetailFormId());
				}
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
	 * 方法描述： 设置表单
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2016-11-17 下午6:24:35
	 */
	@RequestMapping(value = "/doCopyForm")
	@ResponseBody
	public Map<String, Object> doCopyForm(String formid_old, String formtype, String increId, String formid_new,
			String pleFormNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> dataMapTmp = new HashMap<String, Object>();
		try {
			// 复制母版表单
			FormData form = formService.getFormData(formid_old);
			formid_new = formid_old + "_" + formtype + increId;
			form.setFormId(formid_new);
			formService.saveForm(form, "insert");
			// 更新数据
			MfPledgeDynamicForm mfPledgeDynamicForm = new MfPledgeDynamicForm();
			mfPledgeDynamicForm.setPleFormNo(pleFormNo);
			mfPledgeDynamicForm.setLstModTime(DateUtil.getDateTime());
			if ("add".equals(formtype)) {
				mfPledgeDynamicForm.setAddFormId(formid_new);
			} else {
				mfPledgeDynamicForm.setDetailFormId(formid_new);
			}
			mfPledgeDynamicFormFeign.update(mfPledgeDynamicForm);
			dataMap.put("flag", "success");
			dataMap.put("formid_new", formid_new);
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
	 * 方法描述：重置表单
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2016-11-17 下午5:51:32
	 */
	@RequestMapping(value = "/doRestoreForm")
	@ResponseBody
	public Map<String, Object> doRestoreForm(String pleFormNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData form = new FormData();

			MfPledgeDynamicForm mfPledgeDynamicForm = new MfPledgeDynamicForm();
			mfPledgeDynamicForm.setPleFormNo(pleFormNo);
			mfPledgeDynamicForm = mfPledgeDynamicFormFeign.getById(mfPledgeDynamicForm);
			// 如果为空，没有配置表单
			if (mfPledgeDynamicForm == null) {
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.ERROR_NULL_FORM.getMessage());
			} else {
				// 先删除，再复制
				if (!"".equals(mfPledgeDynamicForm.getAddFormId()) && mfPledgeDynamicForm.getAddFormId() != null) {

					form = formService.getFormData(mfPledgeDynamicForm.getAddFormId());
					form.setFormId(mfPledgeDynamicForm.getAddFormId());
					formService.saveForm(form, "delete");

					form = formService.getFormData(mfPledgeDynamicForm.getMotherAddFormId());
					form.setFormId(mfPledgeDynamicForm.getAddFormId());
					formService.saveForm(form, "insert");
				}
				if (!"".equals(mfPledgeDynamicForm.getDetailFormId())
						&& mfPledgeDynamicForm.getDetailFormId() != null) {
					form = formService.getFormData(mfPledgeDynamicForm.getDetailFormId());
					form.setFormId(mfPledgeDynamicForm.getDetailFormId());
					formService.saveForm(form, "delete");

					form = formService.getFormData(mfPledgeDynamicForm.getMotherDetailFormId());
					form.setFormId(mfPledgeDynamicForm.getDetailFormId());
					formService.saveForm(form, "insert");
				}
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("重置表单"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("重置表单"));
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
	public Map<String, Object> getByIdAjax(String pleFormNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formpledy0002 = formService.getFormData("pledy0002");
		MfPledgeDynamicForm mfPledgeDynamicForm = new MfPledgeDynamicForm();
		mfPledgeDynamicForm.setPleFormNo(pleFormNo);
		mfPledgeDynamicForm = mfPledgeDynamicFormFeign.getById(mfPledgeDynamicForm);
		getObjValue(formpledy0002, mfPledgeDynamicForm, formData);
		if (mfPledgeDynamicForm != null) {
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}

	/**
	 * 
	 * 方法描述： 获得该押品动态表单是否被使用
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2016-11-21 上午10:14:46
	 */
	@RequestMapping(value = "/getPleFormIfUsed")
	@ResponseBody
	public Map<String, Object> getPleFormIfUsed(String pleFormNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfSysKind mfSysKind = new MfSysKind();
		mfSysKind.setPleFormNo(pleFormNo);
		List<MfSysKind> list = prdctInterfaceFeign.getSysKindList(mfSysKind);
		if (list != null) {
			if (list.size() > 0) {
				dataMap.put("flag", "1");
			} else {
				dataMap.put("flag", "0");
			}
		} else {
			dataMap.put("flag", "0");
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
	public Map<String, Object> deleteAjax(String pleFormNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfPledgeDynamicForm mfPledgeDynamicForm = new MfPledgeDynamicForm();
		mfPledgeDynamicForm.setPleFormNo(pleFormNo);
		try {
			mfPledgeDynamicFormFeign.delete(mfPledgeDynamicForm);
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
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formpledy0002 = formService.getFormData("pledy0002");
		MfPledgeDynamicForm mfPledgeDynamicForm = new MfPledgeDynamicForm();
		mfPledgeDynamicForm.setMotherAddFormId(BizPubParm.pledge_form_id_add);
		mfPledgeDynamicForm.setMotherDetailFormId(BizPubParm.pledge_form_id_detail);
		getObjValue(formpledy0002, mfPledgeDynamicForm);
		model.addAttribute("mfPledgeDynamicForm", mfPledgeDynamicForm);
		model.addAttribute("formpledy0002", formpledy0002);
		model.addAttribute("query", "");
		return "/component/pledge/MfPledgeDynamicForm_Insert";
	}

	/***
	 * 新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formpledy0002 = formService.getFormData("pledy0002");
		getFormValue(formpledy0002);
		MfPledgeDynamicForm mfPledgeDynamicForm = new MfPledgeDynamicForm();
		setObjValue(formpledy0002, mfPledgeDynamicForm);
		mfPledgeDynamicFormFeign.insert(mfPledgeDynamicForm);
		getObjValue(formpledy0002, mfPledgeDynamicForm);
		this.addActionMessage(model, "保存成功");
		List<MfPledgeDynamicForm> mfPledgeDynamicFormList = (List<MfPledgeDynamicForm>) mfPledgeDynamicFormFeign
				.findByPage(this.getIpage(), mfPledgeDynamicForm).getResult();

		model.addAttribute("mfPledgeDynamicForm", mfPledgeDynamicForm);
		model.addAttribute("mfPledgeDynamicFormList", mfPledgeDynamicFormList);
		model.addAttribute("formpledy0002", formpledy0002);
		model.addAttribute("query", "");
		return "/component/pledge/MfPledgeDynamicForm_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String pleFormNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formpledy0002 = formService.getFormData("pledy0002");
		getFormValue(formpledy0002);
		MfPledgeDynamicForm mfPledgeDynamicForm = new MfPledgeDynamicForm();
		mfPledgeDynamicForm.setPleFormNo(pleFormNo);
		mfPledgeDynamicForm = mfPledgeDynamicFormFeign.getById(mfPledgeDynamicForm);
		getObjValue(formpledy0002, mfPledgeDynamicForm);

		model.addAttribute("mfPledgeDynamicForm", mfPledgeDynamicForm);
		model.addAttribute("formpledy0002", formpledy0002);
		model.addAttribute("query", "");
		return "/component/pledge/MfPledgeDynamicForm_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(String pleFormNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		MfPledgeDynamicForm mfPledgeDynamicForm = new MfPledgeDynamicForm();
		mfPledgeDynamicForm.setPleFormNo(pleFormNo);
		mfPledgeDynamicFormFeign.delete(mfPledgeDynamicForm);
		return getListPage();
	}

	/**
	 * 新增校验
	 * 
	 * @return
	 * @throws Exception
	 */
	public void validateInsert() throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formpledy0002 = formService.getFormData("pledy0002");
		getFormValue(formpledy0002);
		Boolean validateFlag = this.validateFormData(formpledy0002);
	}

	/**
	 * 修改校验
	 * 
	 * @return
	 * @throws Exception
	 */
	public void validateUpdate() throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formpledy0002 = formService.getFormData("pledy0002");
		getFormValue(formpledy0002);
		Boolean validateFlag = this.validateFormData(formpledy0002);
	}

}

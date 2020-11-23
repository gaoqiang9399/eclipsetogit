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
import app.component.nmd.entity.ParmDic;
import app.component.nmdinterface.NmdInterfaceFeign;
import app.component.pledge.entity.MfPledgeClass;
import app.component.pledge.feign.MfPledgeClassFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;

/**
 * Title: MfPledgeClassAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue Sep 20 11:18:22 CST 2016
 **/
@Controller
@RequestMapping("/mfPledgeClass")
public class MfPledgeClassController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入cwComItemFeign
	@Autowired
	private MfPledgeClassFeign mfPledgeClassFeign;
	@Autowired
	private NmdInterfaceFeign nmdInterfaceFeign;

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/pledge/MfPledgeClass_List";
	}

	/**
	 * 
	 * 方法描述： 押品登记时跳转到弹框选择押品类型
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2016-9-22 上午9:43:15
	 */
	@RequestMapping(value = "/getAllPledgeClassList")
	public String getAllPledgeClassList() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/pledge/getAllPledgeClassList";
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
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ParmDic parmDic = new ParmDic();
		try {
			parmDic.setCustomQuery(ajaxData);// 自定义查询参数赋值
			parmDic.setCriteriaList(parmDic, ajaxData);// 我的筛选
			// this.getRoleConditions(mfPledgeClass,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);
			// 自定义查询Feign方法
			parmDic.setKeyName("GAGE_TYPE");
			// parmDic.setSts("1");
			ipage = nmdInterfaceFeign.findByPage(ipage, parmDic);
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

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectByPageAjax")
	@ResponseBody
	public Map<String, Object> selectByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ParmDic parmDic = new ParmDic();
		try {
			parmDic.setCustomQuery(ajaxData);// 自定义查询参数赋值
			parmDic.setCriteriaList(parmDic, ajaxData);// 我的筛选
			// this.getRoleConditions(mfPledgeClass,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Feign方法
			parmDic.setKeyName("GAGE_TYPE");
			parmDic.setSts("1");
			ipage = nmdInterfaceFeign.findByPage(ipage, parmDic);
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
	 * 方法描述： 获得押品类别列表
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2016-9-20 上午11:23:41
	 */
	@RequestMapping(value = "/getAllList")
	public String getAllList() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/pledge/getAllList";
	}

	/**
	 * 
	 * 方法描述： 复制母版表单
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2016-9-20 下午4:52:17
	 */
	@RequestMapping(value = "/doCopyForm")
	@ResponseBody
	public Map<String, Object> doCopyForm(String classNo, String formtype, String formid_old, String formid_new, String formtitle) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> dataMapTmp = new HashMap<String, Object>();
		ParmDic parmDicTmp = new ParmDic();
		try {
			// 获得字典项信息
			ParmDic parmDic = new ParmDic();
			parmDic.setKeyName("GAGE_TYPE");
			parmDic.setOptCode(classNo);
			parmDicTmp = nmdInterfaceFeign.getParmDicById(parmDic);

			MfPledgeClass mfPledgeClass = new MfPledgeClass();
			mfPledgeClass.setClassNo(classNo);
			mfPledgeClass = mfPledgeClassFeign.getById(mfPledgeClass);
			// 如果为空，插入押品动态表单配置表中
			if (mfPledgeClass == null) {
				if ("add".equals(formtype)) {
					formid_old = BizPubParm.pledge_class_form_add;
				} else {
					formid_old = BizPubParm.pledge_class_form_detail;
				}
				// 复制母版表单
				FormData form = formService.getFormData(formid_old);
				formid_new = formid_old + "_" + classNo + formtype;
				form.setFormId(formid_new);
				form.setTitle(formtitle);
				formService.saveForm(form, "insert");

				dataMapTmp.put("formtype", formtype);
				dataMapTmp.put("formid_new", formid_new);
				mfPledgeClass = new MfPledgeClass();
				mfPledgeClass = mfPledgeClassFeign.insertCopeFormId(parmDicTmp, dataMapTmp);
			} else {
				String motherAddFormId = mfPledgeClass.getMotherAddFormId();
				String motherDetailFormId = mfPledgeClass.getMotherDetailFormId();
				mfPledgeClass = new MfPledgeClass();
				mfPledgeClass.setClassNo(classNo);
				FormData form = new FormData();
				if ("add".equals(formtype)) {
					form = formService.getFormData(motherAddFormId);
					formid_new = motherAddFormId + "_" + classNo + formtype;
					;
					mfPledgeClass.setAddFormId(formid_new);
				} else {
					form = formService.getFormData(motherDetailFormId);
					formid_new = motherDetailFormId + "_" + classNo + formtype;
					;
					mfPledgeClass.setDetailFormId(formid_new);
				}
				// 复制母版表单
				form.setFormId(formid_new);
				form.setTitle(form.getTitle());
				formService.saveForm(form, "insert");
				mfPledgeClassFeign.update(mfPledgeClass);
			}

			dataMap.put("flag", "success");
			dataMap.put("formid_new", formid_new);
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
	 * 
	 * 方法描述： 还原表单
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2016-9-20 下午4:52:17
	 */
	@RequestMapping(value = "/doRestoreForm")
	@ResponseBody
	public Map<String, Object> doRestoreForm(String classNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData form = new FormData();

			MfPledgeClass mfPledgeClass = new MfPledgeClass();
			mfPledgeClass.setClassNo(classNo);
			mfPledgeClass = mfPledgeClassFeign.getById(mfPledgeClass);
			// 如果为空，没有配置表单
			if (mfPledgeClass == null) {
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.ERROR_NULL_FORM.getMessage());
			} else {
				// 先删除，再复制
				if (!"".equals(mfPledgeClass.getAddFormId()) && mfPledgeClass.getAddFormId() != null) {

					form = formService.getFormData(mfPledgeClass.getAddFormId());
					form.setFormId(mfPledgeClass.getAddFormId());
					formService.saveForm(form, "delete");

					form = formService.getFormData(mfPledgeClass.getMotherAddFormId());
					form.setFormId(mfPledgeClass.getAddFormId());
					form.setTitle(mfPledgeClass.getClassName());
					formService.saveForm(form, "insert");
				}
				if (!"".equals(mfPledgeClass.getDetailFormId()) && mfPledgeClass.getDetailFormId() != null) {
					form = formService.getFormData(mfPledgeClass.getDetailFormId());
					form.setFormId(mfPledgeClass.getDetailFormId());
					formService.saveForm(form, "delete");

					form = formService.getFormData(mfPledgeClass.getMotherDetailFormId());
					form.setFormId(mfPledgeClass.getDetailFormId());
					form.setTitle(mfPledgeClass.getClassName());
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
	 * 
	 * 方法描述： 根据押品类别获得押品类别信息
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2016-9-20 下午5:10:11
	 */
	@RequestMapping(value = "/getPleClasByIdAjax")
	@ResponseBody
	public Map<String, Object> getPleClasByIdAjax(String classNo, String formtype) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfPledgeClass mfPledgeClass = new MfPledgeClass();
			mfPledgeClass.setClassNo(classNo);
			mfPledgeClass = mfPledgeClassFeign.getById(mfPledgeClass);
			if (mfPledgeClass != null) {
				dataMap.put("flag", "1");
				if ("add".equals(formtype)) {
					dataMap.put("formid_new", mfPledgeClass.getAddFormId());
				} else {
					dataMap.put("formid_new", mfPledgeClass.getDetailFormId());
				}
				dataMap.put("formtitle", mfPledgeClass.getClassName());
			} else {
				dataMap.put("flag", "0");
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
	 * 方法描述： 根据选择的押品类别，查询押品类别信息
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2016-9-22 上午10:49:43
	 */
	@RequestMapping(value = "/getPleClassByClassNoAjax")
	@ResponseBody
	public Map<String, Object> getPleClassByClassNoAjax(String classNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> dataMapTmp = new HashMap<String, Object>();
		ParmDic parmDicTmp = new ParmDic();
		try {
			MfPledgeClass mfPledgeClass = new MfPledgeClass();
			mfPledgeClass.setClassNo(classNo);
			mfPledgeClass = mfPledgeClassFeign.getById(mfPledgeClass);
			if (mfPledgeClass != null) {
				dataMap.put("flag", "1");
				if (!"".equals(mfPledgeClass.getAddFormId()) && mfPledgeClass.getAddFormId() != null) {
					dataMap.put("formid_new", mfPledgeClass.getAddFormId());
				} else {
					dataMap.put("formid_new", mfPledgeClass.getMotherAddFormId());
				}
				dataMap.put("formid_old", mfPledgeClass.getMotherAddFormId());
			} else {
				dataMap.put("flag", "0");
				dataMap.put("formid_new", BizPubParm.pledge_class_form_add);
				dataMap.put("formid_old", BizPubParm.pledge_class_form_add);
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
			FormData formpledgeclass0002 = formService.getFormData("pledgeclass0002");
			getFormValue(formpledgeclass0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpledgeclass0002)) {
				MfPledgeClass mfPledgeClass = new MfPledgeClass();
				setObjValue(formpledgeclass0002, mfPledgeClass);
				mfPledgeClassFeign.insert(mfPledgeClass);
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
		FormData formpledgeclass0002 = formService.getFormData("pledgeclass0002");
		getFormValue(formpledgeclass0002, getMapByJson(ajaxData));
		MfPledgeClass mfPledgeClassJsp = new MfPledgeClass();
		setObjValue(formpledgeclass0002, mfPledgeClassJsp);
		MfPledgeClass mfPledgeClass = mfPledgeClassFeign.getById(mfPledgeClassJsp);
		if (mfPledgeClass != null) {
			try {
				mfPledgeClass = (MfPledgeClass) EntityUtil.reflectionSetVal(mfPledgeClass, mfPledgeClassJsp,
						getMapByJson(ajaxData));
				mfPledgeClassFeign.update(mfPledgeClass);
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
			FormData formpledgeclass0002 = formService.getFormData("pledgeclass0002");
			getFormValue(formpledgeclass0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpledgeclass0002)) {
				MfPledgeClass mfPledgeClass = new MfPledgeClass();
				setObjValue(formpledgeclass0002, mfPledgeClass);
				mfPledgeClassFeign.update(mfPledgeClass);
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
	public Map<String, Object> getByIdAjax(String classNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formpledgeclass0002 = formService.getFormData("pledgeclass0002");
		MfPledgeClass mfPledgeClass = new MfPledgeClass();
		mfPledgeClass.setClassNo(classNo);
		mfPledgeClass = mfPledgeClassFeign.getById(mfPledgeClass);
		getObjValue(formpledgeclass0002, mfPledgeClass, formData);
		if (mfPledgeClass != null) {
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
	public Map<String, Object> deleteAjax(String classNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfPledgeClass mfPledgeClass = new MfPledgeClass();
		mfPledgeClass.setClassNo(classNo);
		try {
			mfPledgeClassFeign.delete(mfPledgeClass);
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
		FormData formpledgeclass0002 = formService.getFormData("pledgeclass0002");
		model.addAttribute("formpledgeclass0002", formpledgeclass0002);
		model.addAttribute("query", "");
		return "/component/pledge/MfPledgeClass_Insert";
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
		FormData formpledgeclass0002 = formService.getFormData("pledgeclass0002");
		getFormValue(formpledgeclass0002);
		MfPledgeClass mfPledgeClass = new MfPledgeClass();
		setObjValue(formpledgeclass0002, mfPledgeClass);
		mfPledgeClassFeign.insert(mfPledgeClass);
		getObjValue(formpledgeclass0002, mfPledgeClass);
		this.addActionMessage(model,"保存成功");
		List<MfPledgeClass> mfPledgeClassList = (List<MfPledgeClass>) mfPledgeClassFeign.findByPage(this.getIpage(), mfPledgeClass)
				.getResult();
		model.addAttribute("mfPledgeClass", mfPledgeClass);
		model.addAttribute("mfPledgeClassList", mfPledgeClassList);
		model.addAttribute("formpledgeclass0002", formpledgeclass0002);
		model.addAttribute("query", "");
		return "/component/pledge/MfPledgeClass_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String classNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formpledgeclass0001 = formService.getFormData("pledgeclass0001");
		getFormValue(formpledgeclass0001);
		MfPledgeClass mfPledgeClass = new MfPledgeClass();
		mfPledgeClass.setClassNo(classNo);
		mfPledgeClass = mfPledgeClassFeign.getById(mfPledgeClass);
		getObjValue(formpledgeclass0001, mfPledgeClass);
		model.addAttribute("mfPledgeClass", mfPledgeClass);
		model.addAttribute("formpledgeclass0001", formpledgeclass0001);
		model.addAttribute("query", "");
		return "/component/pledge/MfPledgeClass_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(String classNo) throws Exception {
		ActionContext.initialize(request, response);
		MfPledgeClass mfPledgeClass = new MfPledgeClass();
		mfPledgeClass.setClassNo(classNo);
		mfPledgeClassFeign.delete(mfPledgeClass);
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
		FormData formpledgeclass0002 = formService.getFormData("pledgeclass0002");
		getFormValue(formpledgeclass0002);
		Boolean validateFlag = this.validateFormData(formpledgeclass0002);
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
		FormData formpledgeclass0002 = formService.getFormData("pledgeclass0002");
		getFormValue(formpledgeclass0002);
		Boolean validateFlag = this.validateFormData(formpledgeclass0002);
	}

}

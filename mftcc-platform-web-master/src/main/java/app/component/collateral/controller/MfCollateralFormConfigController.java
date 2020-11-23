package app.component.collateral.controller;

import java.util.ArrayList;
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

import app.component.collateral.entity.MfCollateralFormConfig;
import app.component.collateral.feign.MfCollateralFormConfigFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;
import net.sf.json.JSONObject;

/**
 * Title: MfCollateralFormConfigController.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu Mar 09 16:11:42 CST 2017
 **/

@Controller
@RequestMapping("/mfCollateralFormConfig")
public class MfCollateralFormConfigController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfCollateralFormConfigFeign mfCollateralFormConfigFeign;

	/**
	 * 获取列表数据
	 * 
	 * @return
	 * @throws Exception
	 */

	private void getTableData(Map<String, Object> dataMap, String tableId,
			MfCollateralFormConfig mfCollateralFormConfig) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "tableTag",
				mfCollateralFormConfigFeign.getAll(mfCollateralFormConfig), null, true);
		dataMap.put("tableData", tableHtml);
	}

	/**
	 * 列表有翻页
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formdlcollateralcfg0002 = formService.getFormData("dlcollateralcfg0002");
		MfCollateralFormConfig mfCollateralFormConfig = new MfCollateralFormConfig();
		Ipage ipage = this.getIpage();
		@SuppressWarnings("unchecked")
		List<MfCollateralFormConfig> mfCollateralFormConfigList = (List<MfCollateralFormConfig>) mfCollateralFormConfigFeign
				.findByPage(ipage).getResult();
		model.addAttribute("mfCollateralFormConfigList", mfCollateralFormConfigList);
		model.addAttribute("formdlcollateralcfg0002", formdlcollateralcfg0002);
		model.addAttribute("query", "");
		return "/component/collateral/MfCollateralFormConfig_List";
	}

	/**
	 * 列表全部无翻页
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListAll")
	public String getListAll(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formdlcollateralcfg0002 = formService.getFormData("dlcollateralcfg0002");
		MfCollateralFormConfig mfCollateralFormConfig = new MfCollateralFormConfig();
		List<MfCollateralFormConfig> mfCollateralFormConfigList = mfCollateralFormConfigFeign
				.getAll(mfCollateralFormConfig);
		model.addAttribute("mfCollateralFormConfigList", mfCollateralFormConfigList);
		model.addAttribute("formdlcollateralcfg0002", formdlcollateralcfg0002);
		model.addAttribute("query", "");
		return "/component/collateral/MfCollateralFormConfig_List";
	}

	/**
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insertAjax")
	@ResponseBody public Map<String, Object> insertAjax(String ajaxData, String tableId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formdlcollateralcfg0002 = formService.getFormData("dlcollateralcfg0002");
			getFormValue(formdlcollateralcfg0002, getMapByJson(ajaxData));
			if (this.validateFormData(formdlcollateralcfg0002)) {
				MfCollateralFormConfig mfCollateralFormConfig = new MfCollateralFormConfig();
				setObjValue(formdlcollateralcfg0002, mfCollateralFormConfig);
				mfCollateralFormConfigFeign.insert(mfCollateralFormConfig);
				getTableData(dataMap, tableId, mfCollateralFormConfig);// 获取列表
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
	 * AJAX更新保存
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateAjax")
	@ResponseBody public Map<String, Object> updateAjax(String ajaxData, String tableId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formdlcollateralcfg0002 = formService.getFormData("dlcollateralcfg0002");
			getFormValue(formdlcollateralcfg0002, getMapByJson(ajaxData));
			if (this.validateFormData(formdlcollateralcfg0002)) {
				MfCollateralFormConfig mfCollateralFormConfig = new MfCollateralFormConfig();
				setObjValue(formdlcollateralcfg0002, mfCollateralFormConfig);
				mfCollateralFormConfigFeign.update(mfCollateralFormConfig);
				getTableData(dataMap, tableId, mfCollateralFormConfig);// 获取列表
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
	@ResponseBody public Map<String, Object> getByIdAjax(String id) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formdlcollateralcfg0002 = formService.getFormData("dlcollateralcfg0002");
		MfCollateralFormConfig mfCollateralFormConfig = new MfCollateralFormConfig();
		mfCollateralFormConfig.setId(id);
		mfCollateralFormConfig = mfCollateralFormConfigFeign.getById(mfCollateralFormConfig);
		getObjValue(formdlcollateralcfg0002, mfCollateralFormConfig, formData);
		if (mfCollateralFormConfig != null) {
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
	@ResponseBody public Map<String, Object> deleteAjax(String ajaxData, String tableId, String id) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCollateralFormConfig mfCollateralFormConfig = new MfCollateralFormConfig();
		mfCollateralFormConfig.setId(id);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			mfCollateralFormConfig = (MfCollateralFormConfig) JSONObject.toBean(jb, MfCollateralFormConfig.class);
			mfCollateralFormConfigFeign.delete(mfCollateralFormConfig);
			getTableData(dataMap, tableId, mfCollateralFormConfig);// 获取列表
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
	 * 
	 * 方法描述： 获得押品类别列表
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2016-9-20 上午11:23:41
	 */
	@RequestMapping("/getAllList")
	public String getAllList(Model model, String id) throws Exception {
		ActionContext.initialize(request, response);
		/*
		 * parmDiclist = new ArrayList<ParmDic>(); parmDic = new ParmDic(); try
		 * { parmDic.setKeyName("GAGE_TYPE"); parmDiclist =
		 * (List<ParmDic>)nmdInterfaceFeign.findAllParmDicByKeyName(parmDic); }
		 * catch (Exception e) { e.printStackTrace(); dataMap.put("flag",
		 * "error"); dataMap.put("msg", e.getMessage()); throw e; }
		 */
		model.addAttribute("id", id);
		return "/component/collateral/MfCollateralFormConfig_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findByPageAjax")
	@ResponseBody public Map<String, Object> findByPageAjax(String ajaxData, Integer pageNo, Integer pageSize, String tableId,
			String tableType, String id) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCollateralFormConfig mfCollateralFormConfig = new MfCollateralFormConfig();
		try {
			mfCollateralFormConfig.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCollateralFormConfig.setCriteriaList(mfCollateralFormConfig, ajaxData);// 我的筛选
			mfCollateralFormConfig.setId(id);
			// this.getRoleConditions(mfPledgeClass,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);
			ipage.setPageSize(pageSize);// 异步传页面翻页参数
			
			ipage.setParams(this.setIpageParams("mfCollateralFormConfig", mfCollateralFormConfig));
			// 自定义查询Feign方法
			ipage = mfCollateralFormConfigFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			@SuppressWarnings("rawtypes")
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

	@RequestMapping("/updateStsAjax")
	@ResponseBody public Map<String, Object> updateStsAjax(String ajaxData, String id,String tableId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCollateralFormConfig mfCollateralFormConfig = new MfCollateralFormConfig();
		try {
			// Map map = getMapByJson(ajaxData);
			JSONObject jobj = JSONObject.fromObject(ajaxData);
			id = (String) jobj.get("id");
			if (StringUtil.isEmpty(id)) {
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
				return dataMap;
			}

			mfCollateralFormConfig.setId(id);

			String useFlag = (String) jobj.get("useFlag");
			if (useFlag != null) {
				mfCollateralFormConfig.setUseFlag(useFlag);
				if ("0".equals(useFlag)) {
					MfCollateralFormConfig temp = new MfCollateralFormConfig();
					temp.setId((String) jobj.get("id"));
					temp = mfCollateralFormConfigFeign.getById(temp);
					if ("1".equals(temp.getIsBase())) {
						dataMap.put("flag", "error");
						dataMap.put("msg", MessageEnum.NOT_FORM_BASIS.getMessage());
						return dataMap;
					}
				}
			}
			String isBase = (String) jobj.get("isBase");
			if (isBase != null) {
				mfCollateralFormConfig.setIsBase(isBase);
				if ("1".equals(isBase)) {
					mfCollateralFormConfig.setUseFlag("1");
				}
			}
			mfCollateralFormConfig.setIsMust((String) jobj.get("isMust"));
			mfCollateralFormConfigFeign.update(mfCollateralFormConfig);

			mfCollateralFormConfig = mfCollateralFormConfigFeign.getById(mfCollateralFormConfig);
			List<MfCollateralFormConfig> mfCollateralFormConfigList = new ArrayList<MfCollateralFormConfig>();
			mfCollateralFormConfigList.add(mfCollateralFormConfig);
			getTableDataList(mfCollateralFormConfigList,tableId,dataMap);

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

	private void getTableDataList(List<MfCollateralFormConfig> list, String tableId, Map<String, Object> dataMap)
			throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "tableTag", list, null, true);
		dataMap.put("tableData", tableHtml);
	}

	/**
	 * @author czk
	 * @Description: 判断某客户类型的某个表单是否存在，如果不存在，则生成表单xml，并将子表单xml返回。 date 2016-11-21
	 */
	@RequestMapping("/checkAndCreateFormAjax")
	@ResponseBody public Map<String, Object> checkAndCreateFormAjax(String optCode, String id) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		try {
			String formIdThis = "";
			MfCollateralFormConfig mfCollateralFormConfig = new MfCollateralFormConfig();
			mfCollateralFormConfig.setId(id);
			mfCollateralFormConfig = mfCollateralFormConfigFeign.getById(mfCollateralFormConfig);
			if ("1".equals(optCode)) {// 表示是新增表单
				if (mfCollateralFormConfig.getAddModel().equals(mfCollateralFormConfig.getAddModelDef())) {
					mfCollateralFormConfig.setAddModelDef(
							mfCollateralFormConfig.getAddModel() + "_add" + mfCollateralFormConfig.getFormType());
					FormData form = formService.getFormData(mfCollateralFormConfig.getAddModel());
					form.setFormId(mfCollateralFormConfig.getAddModelDef());
					form.setTitle("title");
					formService.saveForm(form, "insert");
					MfCollateralFormConfig mfCollateralFormCon = new MfCollateralFormConfig();
					mfCollateralFormCon.setId(id);
					mfCollateralFormCon.setAddModelDef(mfCollateralFormConfig.getAddModelDef());
					mfCollateralFormConfigFeign.update(mfCollateralFormCon);
				}
				formIdThis = mfCollateralFormConfig.getAddModelDef();
			} else if ("2".equals(optCode)) {// 表示是展示表单
				if (mfCollateralFormConfig.getShowModel().equals(mfCollateralFormConfig.getShowModelDef())) {
					mfCollateralFormConfig.setShowModelDef(
							mfCollateralFormConfig.getShowModel() + "_show" + mfCollateralFormConfig.getFormType());
					FormData form = formService.getFormData(mfCollateralFormConfig.getShowModel());
					form.setFormId(mfCollateralFormConfig.getShowModelDef());
					form.setTitle("title");
					formService.saveForm(form, "insert");
					MfCollateralFormConfig mfCollateralFormCon = new MfCollateralFormConfig();
					mfCollateralFormCon.setId(id);
					mfCollateralFormCon.setShowModelDef(mfCollateralFormConfig.getShowModelDef());
					mfCollateralFormConfigFeign.update(mfCollateralFormCon);
				}
				formIdThis = mfCollateralFormConfig.getShowModelDef();
			} else if ("3".equals(optCode)) {// 表示是列表详情表单
				if (mfCollateralFormConfig.getListModel().equals(mfCollateralFormConfig.getListModelDef())) {
					mfCollateralFormConfig.setListModelDef(
							mfCollateralFormConfig.getListModel() + "_list" + mfCollateralFormConfig.getFormType());
					FormData form = formService.getFormData(mfCollateralFormConfig.getListModel());
					form.setFormId(mfCollateralFormConfig.getListModelDef());
					form.setTitle("title");
					formService.saveForm(form, "insert");
					MfCollateralFormConfig mfCollateralFormCon = new MfCollateralFormConfig();
					mfCollateralFormCon.setId(id);
					mfCollateralFormCon.setListModelDef(mfCollateralFormConfig.getListModelDef());
					mfCollateralFormConfigFeign.update(mfCollateralFormCon);
				}
				formIdThis = mfCollateralFormConfig.getListModelDef();
			}else {
			}
			dataMap.put("flag", "success");
			dataMap.put("formId", formIdThis);

		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SAVE.getMessage());
		}
		return dataMap;
	}

	/**
	 * @author czk
	 * @Description: 将子表单重置成模板。 date 2016-9-26
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/MfCollateralFormConfigActionAjax_resetFormAjax")
	@ResponseBody
	public Map<String, Object> resetFormAjax(String id) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		try {
			// 每个表单都有新增表单，当showType为1时，还会有展示表单；showtype为1表示信息以表单形式展示，为2表示以列表形式展示
			String[] ids = id.split(",");
			for (int i = 0; i < ids.length; i++) {
				boolean updateFlag = false;// 是否需要执行数据更新。

				MfCollateralFormConfig mfCollateralFormConfig = new MfCollateralFormConfig();
				mfCollateralFormConfig.setId(ids[i]);
				mfCollateralFormConfig = mfCollateralFormConfigFeign.getById(mfCollateralFormConfig);

				MfCollateralFormConfig mfCollateralFormCon = new MfCollateralFormConfig();
				mfCollateralFormCon.setId(ids[i]);
				// 先删除子表单，再将子表单编号的字段和母表单设置一致
				if (mfCollateralFormConfig.getAddModel().equals(mfCollateralFormConfig.getAddModelDef())) {// 子表单和模板一致

				} else {// 子表单和模板不一致时，删除子表单
					FormData form = formService.getFormData(mfCollateralFormConfig.getAddModelDef());
					form.setFormId(mfCollateralFormConfig.getAddModelDef());
					formService.saveForm(form, "delete");
					mfCollateralFormCon.setAddModelDef(mfCollateralFormConfig.getAddModel());
					updateFlag = true;
				}
				if ("1".equals(mfCollateralFormConfig.getShowType())) {
					if (mfCollateralFormConfig.getShowModel().equals(mfCollateralFormConfig.getShowModelDef())) {

					} else {
						FormData form = formService.getFormData(mfCollateralFormConfig.getShowModelDef());
						form.setFormId(mfCollateralFormConfig.getShowModelDef());
						formService.saveForm(form, "delete");
						mfCollateralFormCon.setShowModelDef(mfCollateralFormConfig.getShowModel());
						updateFlag = true;
					}
				} else if ("2".equals(mfCollateralFormConfig.getShowType())) {
					if (mfCollateralFormConfig.getListModel().equals(mfCollateralFormConfig.getListModelDef())) {

					} else {
						FormData form = formService.getFormData(mfCollateralFormConfig.getListModelDef());
						form.setFormId(mfCollateralFormConfig.getListModelDef());
						formService.saveForm(form, "delete");
						mfCollateralFormCon.setListModelDef(mfCollateralFormConfig.getListModel());
						updateFlag = true;
					}
				}else {
				}
				if (updateFlag) {
					mfCollateralFormConfigFeign.update(mfCollateralFormCon);
				}
			}

			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());

		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SAVE.getMessage());
		}
		return dataMap;
	}

}

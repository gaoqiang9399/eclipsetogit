package app.component.pact.fiveclass.controller;

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
import app.component.nmd.entity.ParmDic;
import app.component.nmdinterface.NmdInterfaceFeign;
import app.component.pact.fiveclass.entity.MfFiveclassModel;
import app.component.pact.fiveclass.feign.MfFiveclassModelFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONObject;

/**
 * Title: MfFiveclassModelAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri Mar 10 12:26:58 CST 2017
 **/
@Controller
@RequestMapping("/mfFiveclassModel")
public class MfFiveclassModelController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfFiveclassModelBo
	@Autowired
	private MfFiveclassModelFeign mfFiveclassModelFeign;
	@Autowired
	private NmdInterfaceFeign nmdInterfaceFeign;

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("query", "");
		return "component/pact/fiveclass/MfFiveclassModel_List";
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
		MfFiveclassModel mfFiveclassModel = new MfFiveclassModel();
		try {
			mfFiveclassModel.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfFiveclassModel.setCriteriaList(mfFiveclassModel, ajaxData);// 我的筛选
			mfFiveclassModel.setCustomSorts(ajaxData);
			// this.getRoleConditions(mfFiveclassModel,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfFiveclassModel",mfFiveclassModel));
			// 自定义查询Bo方法
			ipage = mfFiveclassModelFeign.findByPage(ipage);
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
			FormData formfiveclass0001 = formService.getFormData("fiveclass0001");
			getFormValue(formfiveclass0001, getMapByJson(ajaxData));
			if (this.validateFormData(formfiveclass0001)) {
				MfFiveclassModel mfFiveclassModel = new MfFiveclassModel();
				setObjValue(formfiveclass0001, mfFiveclassModel);
				List<MfFiveclassModel> list = mfFiveclassModelFeign.listByMfFiveclassModel(mfFiveclassModel);
				if (list.size() > 0) {
					dataMap.put("flag", "error");
					dataMap.put("msg", "系统中已经有该类型的模型");
				} else {
					mfFiveclassModelFeign.insert(mfFiveclassModel);
					dataMap.put("flag", "success");
					dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
				}
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
		FormData formfiveclass0002 = formService.getFormData("fiveclass0002");
		getFormValue(formfiveclass0002, getMapByJson(ajaxData));
		MfFiveclassModel mfFiveclassModelJsp = new MfFiveclassModel();
		setObjValue(formfiveclass0002, mfFiveclassModelJsp);
		MfFiveclassModel mfFiveclassModel = mfFiveclassModelFeign.getById(mfFiveclassModelJsp);
		if (mfFiveclassModel != null) {
			try {
				mfFiveclassModel = (MfFiveclassModel) EntityUtil.reflectionSetVal(mfFiveclassModel, mfFiveclassModelJsp,
						getMapByJson(ajaxData));
				mfFiveclassModelFeign.update(mfFiveclassModel);
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
			FormData formfiveclass0001 = formService.getFormData("fiveclass0001");
			getFormValue(formfiveclass0001, getMapByJson(ajaxData));
			if (this.validateFormData(formfiveclass0001)) {
				MfFiveclassModel mfFiveclassModel = new MfFiveclassModel();
				setObjValue(formfiveclass0001, mfFiveclassModel);
				mfFiveclassModelFeign.update(mfFiveclassModel);
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
	 * @author guoxy
	 * @Description: 选择担保方式 date 2017-03-13
	 */
	@RequestMapping(value = "/getVouTypeForMutiSel")
	public String getVouTypeForMutiSel(Model model, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		ParmDic parmDic = new ParmDic();
		parmDic.setKeyName("VOU_TYPE");
		List parmDiclist = nmdInterfaceFeign.findAllParmDicByKeyName(parmDic);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("vouTypelist", parmDiclist);
		JSONObject jb = JSONObject.fromObject(dataMap);
		dataMap = jb;
		model.addAttribute("parmDiclist", parmDiclist);
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("query", "");
		return "component/pact/fiveclass/getVouTypeForMutiSel";
	}

	/**
	 * @author guoxy
	 * @Description: 选择还款方式 date 2017-03-13
	 */
	@RequestMapping(value = "/getRepayTypeForMutiSel")
	public String getRepayTypeForMutiSel(Model model, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		ParmDic parmDic = new ParmDic();
		parmDic.setKeyName("REPAY_TYPE");
		List parmDiclist = nmdInterfaceFeign.findAllParmDicByKeyName(parmDic);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("repayTypelist", parmDiclist);
		JSONObject jb = JSONObject.fromObject(dataMap);
		dataMap = jb;
		model.addAttribute("parmDiclist", parmDiclist);
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("query", "");
		return "component/pact/fiveclass/getRepayTypeForMutiSel";
	}

	/**
	 * AJAX获取查看
	 * 
	 * @param modelId
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String modelId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formfiveclass0002 = formService.getFormData("fiveclass0002");
		MfFiveclassModel mfFiveclassModel = new MfFiveclassModel();
		mfFiveclassModel.setModelId(modelId);
		mfFiveclassModel = mfFiveclassModelFeign.getById(mfFiveclassModel);
		getObjValue(formfiveclass0002, mfFiveclassModel, formData);
		if (mfFiveclassModel != null) {
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
	public Map<String, Object> deleteAjax(String modelId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfFiveclassModel mfFiveclassModel = new MfFiveclassModel();
		mfFiveclassModel.setModelId(modelId);
		try {
			mfFiveclassModelFeign.delete(mfFiveclassModel);
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
		FormData formfiveclass0001 = formService.getFormData("fiveclass0001");
		model.addAttribute("formfiveclass0001", formfiveclass0001);
		model.addAttribute("query", "");
		return "component/pact/fiveclass/MfFiveclassModel_Insert";
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
		FormData formfiveclass0002 = formService.getFormData("fiveclass0002");
		getFormValue(formfiveclass0002);
		MfFiveclassModel mfFiveclassModel = new MfFiveclassModel();
		setObjValue(formfiveclass0002, mfFiveclassModel);
		mfFiveclassModelFeign.insert(mfFiveclassModel);
		getObjValue(formfiveclass0002, mfFiveclassModel);
		this.addActionMessage(model, "保存成功");
		List<MfFiveclassModel> mfFiveclassModelList = (List<MfFiveclassModel>) mfFiveclassModelFeign.findByPage(this.getIpage()).getResult();
		model.addAttribute("formfiveclass0002", formfiveclass0002);
		model.addAttribute("mfFiveclassModel", mfFiveclassModel);
		model.addAttribute("mfFiveclassModelList", mfFiveclassModelList);
		model.addAttribute("query", "");
		return "component/pact/fiveclass/MfFiveclassModel_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String modelId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formfiveclass0001 = formService.getFormData("fiveclass0001");
		getFormValue(formfiveclass0001);
		MfFiveclassModel mfFiveclassModel = new MfFiveclassModel();
		mfFiveclassModel.setModelId(modelId);
		mfFiveclassModel = mfFiveclassModelFeign.getById(mfFiveclassModel);
		getObjValue(formfiveclass0001, mfFiveclassModel);
		model.addAttribute("formfiveclass0001", formfiveclass0001);
		model.addAttribute("mfFiveclassModel", mfFiveclassModel);
		model.addAttribute("query", "");
		return "component/pact/fiveclass/MfFiveclassModel_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String modelId) throws Exception {
		ActionContext.initialize(request, response);
		MfFiveclassModel mfFiveclassModel = new MfFiveclassModel();
		mfFiveclassModel.setModelId(modelId);
		mfFiveclassModelFeign.delete(mfFiveclassModel);
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
		FormData formfiveclass0002 = formService.getFormData("fiveclass0002");
		getFormValue(formfiveclass0002);
		boolean validateFlag = this.validateFormData(formfiveclass0002);
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
		FormData formfiveclass0002 = formService.getFormData("fiveclass0002");
		getFormValue(formfiveclass0002);
		boolean validateFlag = this.validateFormData(formfiveclass0002);
	}

}

package app.component.collateral.controller;

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
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;

import app.component.collateral.entity.FairInfo;
import app.component.collateral.entity.MfCollateralFormConfig;
import app.component.collateral.entity.PledgeBaseInfo;
import app.component.collateral.feign.FairInfoFeign;
import app.component.collateral.feign.MfCollateralFormConfigFeign;
import app.component.collateral.feign.PledgeBaseInfoFeign;
import app.component.common.EntityUtil;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.WaterIdUtil;
import net.sf.json.JSONObject;

/**
 * Title: FairInfoController.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Mar 15 13:16:46 CST 2017
 **/
@Controller
@RequestMapping("/fairInfo")
public class FairInfoController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private FairInfoFeign fairInfoFeign;
	@Autowired
	private PledgeBaseInfoFeign pledgeBaseInfoFeign;
	@Autowired
	private MfCollateralFormConfigFeign mfCollateralFormConfigFeign;

	/**
	 * 获取列表数据
	 * 
	 * @return
	 * @throws Exception
	 */
	private void getTableData(Map<String, Object> dataMap, String tableId, FairInfo fairInfo) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "tableTag", fairInfoFeign.getAll(fairInfo), null, true);
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
		FormData formdlcollateralfair0002 = formService.getFormData("dlcollateralfair0002");
		FairInfo fairInfo = new FairInfo();
		Ipage ipage = this.getIpage();
		@SuppressWarnings("unchecked")
		List<FairInfo> fairInfoList = (List<FairInfo>) fairInfoFeign.findByPage(ipage, fairInfo).getResult();
		model.addAttribute("fairInfoList", fairInfoList);
		model.addAttribute("formdlcollateralfair0002", formdlcollateralfair0002);
		model.addAttribute("query", "");
		return "/component/collateral/FairInfo_List";
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
		FormData formdlcollateralfair0002 = formService.getFormData("dlcollateralfair0002");
		FairInfo fairInfo = new FairInfo();
		List<FairInfo> fairInfoList = fairInfoFeign.getAll(fairInfo);
		model.addAttribute("fairInfoList", fairInfoList);
		model.addAttribute("formdlcollateralfair0002", formdlcollateralfair0002);
		model.addAttribute("query", "");
		return "/component/collateral/FairInfo_List";
	}

	/**
	 * 校验是否重复保险编号
	 */

	@RequestMapping("/validateFairNoAjax")
	@ResponseBody
	public Map<String, Object> validateFairNoAjax(String ajaxData) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if (ajaxData.isEmpty()) {
			dataMap.put("result", "1");
		} else {
			String validateFairNo = fairInfoFeign.validateFairNo(ajaxData);
			if ("0".equals(validateFairNo)) {
				dataMap.put("result", "0");
			} else {
				dataMap.put("result", "1");
			}
		}
		return dataMap;
	}

	/**
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String fairId = WaterIdUtil.getWaterId();
		try {
			FormData formdlfairinfo0002 = formService.getFormData("dlfairinfo0002");
			getFormValue(formdlfairinfo0002, getMapByJson(ajaxData));
			if (this.validateFormData(formdlfairinfo0002)) {
				FairInfo fairInfo = new FairInfo();
				setObjValue(formdlfairinfo0002, fairInfo);
				fairInfo.setFairId(fairId);
				fairInfoFeign.insert(fairInfo);

				// getTableData();//获取列表

				// PledgeBase pledgeBase = new PledgeBase();
				// pledgeBase.setPledgeId(fairInfo.getCollateralId());
				// pledgeBase = pledgeBaseFeign.getById(pledgeBase);

				/*
				 * 当query为"query"或者ifBizManger为"0"时，解析的表单中不可单字段编辑；
				 * 当ifBizManger为"1"或""时，解析的表单中设置的可编辑的字段可以单字段编辑；
				 * 当ifBizManger为"2"时，解析的表单中所有非只读的字段可以单字段编辑；
				 */
				request.setAttribute("ifBizManger", "3");
				// 获得基本信息的展示表单ID，并将表单解析
				MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign
						.getByPledgeImPawnType(fairInfo.getClassId(), "FairInfoAction", "");
				String formId = "";
				if (mfCollateralFormConfig == null) {

				} else {
					formId = mfCollateralFormConfig.getShowModelDef();
				}
				FormData formdlfairinfo0004 = formService.getFormData(formId);
				if (formdlfairinfo0004.getFormId() == null) {
					// logger.error("押品类型为" +
					// mfCollateralFormConfig.getFormType() +
					// "的FairInfoController表单form" + formId
					// + ".xml文件不存在");
				}
				getFormValue(formdlfairinfo0004);
				getObjValue(formdlfairinfo0004, fairInfo);
				JsonFormUtil jsonFormUtil = new JsonFormUtil();
				String htmlStr = jsonFormUtil.getJsonStr(formdlfairinfo0004, "propertySeeTag", "");

				dataMap.put("htmlStr", htmlStr);
				dataMap.put("htmlStrFlag", "1");

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
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData, String tableId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formdlcollateralfair0002 = formService.getFormData("dlcollateralfair0002");
			getFormValue(formdlcollateralfair0002, getMapByJson(ajaxData));
			if (this.validateFormData(formdlcollateralfair0002)) {
				FairInfo fairInfo = new FairInfo();
				setObjValue(formdlcollateralfair0002, fairInfo);
				fairInfoFeign.update(fairInfo);
				getTableData(dataMap, tableId, fairInfo);// 获取列表
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
	@ResponseBody
	public Map<String, Object> getByIdAjax(String fairId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formdlcollateralfair0002 = formService.getFormData("dlcollateralfair0002");
		FairInfo fairInfo = new FairInfo();
		fairInfo.setFairId(fairId);
		fairInfo = fairInfoFeign.getById(fairInfo);
		getObjValue(formdlcollateralfair0002, fairInfo, formData);
		if (fairInfo != null) {
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
	@ResponseBody
	public Map<String, Object> deleteAjax(String ajaxData, String fairId, String tableId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FairInfo fairInfo = new FairInfo();
		fairInfo.setFairId(fairId);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			fairInfo = (FairInfo) JSONObject.toBean(jb, FairInfo.class);
			fairInfoFeign.delete(fairInfo);
			getTableData(dataMap, tableId, fairInfo);// 获取列表
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
	@RequestMapping("/input")
	public String input(Model model, String collateralNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();

		PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
		pledgeBaseInfo.setPledgeNo(collateralNo);
		pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
		String classId = pledgeBaseInfo.getClassId();

		MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign.getByPledgeImPawnType(classId,
				"FairInfoAction", "");
		String formId = null;
		if (mfCollateralFormConfig == null) {

		} else {
			formId = mfCollateralFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// logger.error("押品类型为" + mfCollateralFormConfig.getFormType() +
			// "的FairInfoController表单信息没有查询到");
		} else {
			FairInfo fairInfo = new FairInfo();
			fairInfo.setCollateralId(collateralNo);
			fairInfo.setClassId(classId);
			FormData formdlfairinfo0002 = formService.getFormData(formId);
			if (formdlfairinfo0002.getFormId() == null) {
				// logger.error("押品类型为" + mfCollateralFormConfig.getFormType() +
				// "的FairInfoController表单form" + formId
				// + ".xml文件不存在");
			} else {
				getFormValue(formdlfairinfo0002);
				getObjValue(formdlfairinfo0002, fairInfo);
				model.addAttribute("formdlfairinfo0002", formdlfairinfo0002);
			}
		}

		model.addAttribute("query", "");
		return "/component/collateral/FairInfo_Insert";
	}

	/**
	 * ajax 异步 单个字段或多个字段更新
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap = getMapByJson(ajaxData);
		String collateralNo = (String) dataMap.get("collateralId");

		PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
		pledgeBaseInfo.setPledgeNo(collateralNo);
		pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
		String classId = pledgeBaseInfo.getClassId();
		String formId = "";
		if (StringUtil.isEmpty(formId)) {
			formId = mfCollateralFormConfigFeign.getByPledgeImPawnType(classId, "FairInfoAction", "")
					.getShowModelDef();
		} else {
			if (formId.indexOf("form") == -1) {
			} else {
				formId = formId.substring(4);
			}
		}
		FormData formdlfairinfo0005 = formService.getFormData(formId);
		getFormValue(formdlfairinfo0005, getMapByJson(ajaxData));
		FairInfo fairInfoJsp = new FairInfo();
		setObjValue(formdlfairinfo0005, fairInfoJsp);
		FairInfo fairInfo = fairInfoFeign.getById(fairInfoJsp);
		dataMap = new HashMap<String, Object>();
		if (fairInfo != null) {
			try {
				fairInfo = (FairInfo) EntityUtil.reflectionSetVal(fairInfo, fairInfoJsp, getMapByJson(ajaxData));
				fairInfoFeign.update(fairInfo);
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
	 * 方法描述： 插入公证信息并且进行流程的提交
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author YuShuai
	 * @date 2017-6-2 下午7:52:28
	 */
	@RequestMapping("/insertAndDocommit")
	@ResponseBody
	public Map<String, Object> insertAndDocommit(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String fairId = WaterIdUtil.getWaterId();
		try {
			FormData formdlfairinfo0002 = formService.getFormData("dlfairinfo0002");
			getFormValue(formdlfairinfo0002, getMapByJson(ajaxData));
			if (this.validateFormData(formdlfairinfo0002)) {
				FairInfo fairInfo = new FairInfo();
				setObjValue(formdlfairinfo0002, fairInfo);
				fairInfo.setFairId(fairId);
				Result result = fairInfoFeign.insertDocommit(fairInfo);
				if (result == null) {
					dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
				} else {
					dataMap.put("msg", result.getMsg());

				}
				dataMap.put("flag", "success");
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

}

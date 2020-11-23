package app.component.auth.controller;

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

import app.component.auth.entity.MfCusPorductCredit;
import app.component.auth.feign.MfCusPorductCreditFeign;
import app.component.common.EntityUtil;
import app.util.DataUtil;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.WaterIdUtil;

/**
 * Title: MfCusPorductCreditAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Mon Feb 27 10:45:47 CST 2017
 **/
@Controller
@RequestMapping("/mfCusPorductCredit")
public class MfCusPorductCreditController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfCusPorductCreditBo
	@Autowired
	private MfCusPorductCreditFeign mfCusPorductCreditFeign;
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
		return "/component/auth/MfCusPorductCredit_List";
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
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusPorductCredit mfCusPorductCredit = new MfCusPorductCredit();
		try {
			mfCusPorductCredit.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCusPorductCredit.setCriteriaList(mfCusPorductCredit, ajaxData);// 我的筛选
			// this.getRoleConditions(mfCusPorductCredit,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = mfCusPorductCreditFeign.findByPage(ipage, mfCusPorductCredit);
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
			FormData formcreditproduct0002 = formService.getFormData("creditproduct0002");
			getFormValue(formcreditproduct0002, getMapByJson(ajaxData));
			if (this.validateFormData(formcreditproduct0002)) {
				MfCusPorductCredit mfCusPorductCredit = new MfCusPorductCredit();
				setObjValue(formcreditproduct0002, mfCusPorductCredit);
				mfCusPorductCreditFeign.insert(mfCusPorductCredit);
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
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertNewAjax")
	@ResponseBody
	public Map<String, Object> insertNewAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formcreditproduct0003 = formService.getFormData("creditproduct0003");
			getFormValue(formcreditproduct0003, getMapByJson(ajaxData));
			if (this.validateFormData(formcreditproduct0003)) {
				MfCusPorductCredit mfCusPorductCredit = new MfCusPorductCredit();
				setObjValue(formcreditproduct0003, mfCusPorductCredit);

				mfCusPorductCredit.setId(WaterIdUtil.getWaterId());
				mfCusPorductCredit.setRegTime(DateUtil.getDateTime());
				mfCusPorductCredit.setCreditBal(mfCusPorductCredit.getCreditAmt());
				mfCusPorductCreditFeign.insert(mfCusPorductCredit);

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
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcreditproduct0002 = formService.getFormData("creditproduct0002");
		getFormValue(formcreditproduct0002, getMapByJson(ajaxData));
		MfCusPorductCredit mfCusPorductCreditJsp = new MfCusPorductCredit();
		setObjValue(formcreditproduct0002, mfCusPorductCreditJsp);
		MfCusPorductCredit mfCusPorductCredit = mfCusPorductCreditFeign.getById(mfCusPorductCreditJsp);
		if (mfCusPorductCredit != null) {
			try {
				mfCusPorductCredit = (MfCusPorductCredit) EntityUtil.reflectionSetVal(mfCusPorductCredit,
						mfCusPorductCreditJsp, getMapByJson(ajaxData));
				mfCusPorductCreditFeign.update(mfCusPorductCredit);
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
			FormData formcreditproduct0002 = formService.getFormData("creditproduct0002");
			getFormValue(formcreditproduct0002, getMapByJson(ajaxData));
			if (this.validateFormData(formcreditproduct0002)) {
				MfCusPorductCredit mfCusPorductCredit = new MfCusPorductCredit();
				setObjValue(formcreditproduct0002, mfCusPorductCredit);
				mfCusPorductCreditFeign.update(mfCusPorductCredit);
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
	@RequestMapping(value = "/updateNewAjax")
	@ResponseBody
	public Map<String, Object> updateNewAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formcreditproduct0003 = formService.getFormData("creditproduct0003");
			getFormValue(formcreditproduct0003, getMapByJson(ajaxData));
			if (this.validateFormData(formcreditproduct0003)) {
				MfCusPorductCredit mfCusPorductCredit = new MfCusPorductCredit();
				setObjValue(formcreditproduct0003, mfCusPorductCredit);
				mfCusPorductCredit.setCreditBal(mfCusPorductCredit.getCreditAmt());// 授信余额 = 授信额度
				mfCusPorductCreditFeign.updateById(mfCusPorductCredit);

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
	@RequestMapping(value = "/updateNewApproveAjax")
	@ResponseBody
	public Map<String, Object> updateNewApproveAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formcreditproduct0003 = formService.getFormData("creditproduct0004");
			getFormValue(formcreditproduct0003, getMapByJson(ajaxData));
			if (this.validateFormData(formcreditproduct0003)) {
				//校验：保证金比例，利率只能大于或等于不能低于上个环节
				MfCusPorductCredit mfCusPorductCredit = new MfCusPorductCredit();
				setObjValue(formcreditproduct0003, mfCusPorductCredit);
				
				MfCusPorductCredit mfCusPorductCreditOld = mfCusPorductCreditFeign.getById(mfCusPorductCredit);
				
				Double depositRateNew = mfCusPorductCredit.getDepositRate();
				Double depositRateOld = mfCusPorductCreditOld.getDepositRate();
				Double creditRateNew = mfCusPorductCredit.getCreditRate();
				Double creditRateOld = mfCusPorductCreditOld.getCreditRate();
				
				if(DataUtil.sub(depositRateNew, depositRateOld, 2) < 0){
					dataMap.put("flag", "error");
					dataMap.put("msg", "保证金比例不能小于申请保证金比例(上个环节审批保证金比例)");
					return dataMap;
				}
				
				if(DataUtil.sub(creditRateNew, creditRateOld, 2) < 0){
					dataMap.put("flag", "error");
					dataMap.put("msg", "授信利率不能小于申请授信利率(上个环节审批授信利率)");
					return dataMap;
				}
				
				mfCusPorductCreditFeign.updateById(mfCusPorductCredit);

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
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String ajaxData, String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcreditproduct0002 = formService.getFormData("creditproduct0002");
		MfCusPorductCredit mfCusPorductCredit = new MfCusPorductCredit();
		mfCusPorductCredit.setId(id);
		mfCusPorductCredit = mfCusPorductCreditFeign.getById(mfCusPorductCredit);
		getObjValue(formcreditproduct0002, mfCusPorductCredit, formData);
		if (mfCusPorductCredit != null) {
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
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String ajaxData, String id) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusPorductCredit mfCusPorductCredit = new MfCusPorductCredit();
		mfCusPorductCredit.setId(id);
		try {
			mfCusPorductCreditFeign.delete(mfCusPorductCredit);
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
		FormData formcreditproduct0002 = formService.getFormData("creditproduct0002");
		model.addAttribute("formcreditproduct0002", formcreditproduct0002);
		model.addAttribute("query", "");
		return "/component/auth/MfCusPorductCredit_Insert";
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
		FormData formcreditproduct0002 = formService.getFormData("creditproduct0002");
		getFormValue(formcreditproduct0002);
		MfCusPorductCredit mfCusPorductCredit = new MfCusPorductCredit();
		setObjValue(formcreditproduct0002, mfCusPorductCredit);
		mfCusPorductCreditFeign.insert(mfCusPorductCredit);
		getObjValue(formcreditproduct0002, mfCusPorductCredit);
		this.addActionMessage(model, "保存成功");
		List<MfCusPorductCredit> mfCusPorductCreditList = (List<MfCusPorductCredit>) mfCusPorductCreditFeign
				.findByPage(this.getIpage(), mfCusPorductCredit).getResult();
		model.addAttribute("formcreditproduct0002", formcreditproduct0002);
		model.addAttribute("query", "");
		return "/component/auth/MfCusPorductCredit_Insert";
	}

	/**
	 * 查询
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String ajaxData, String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formcreditproduct0001 = formService.getFormData("creditproduct0001");
		getFormValue(formcreditproduct0001);
		MfCusPorductCredit mfCusPorductCredit = new MfCusPorductCredit();
		mfCusPorductCredit.setId(id);
		mfCusPorductCredit = mfCusPorductCreditFeign.getById(mfCusPorductCredit);
		getObjValue(formcreditproduct0001, mfCusPorductCredit);
		model.addAttribute("formcreditproduct0001", formcreditproduct0001);
		model.addAttribute("query", "");
		return "/component/auth/MfCusPorductCredit_Detail";
	}

	/**
	 * 查询
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdNew")
	public String getByIdNew(Model model, String ajaxData, String id) throws Exception {
		ActionContext.initialize(request, response);

		MfCusPorductCredit mfCusPorductCredit = new MfCusPorductCredit();
		mfCusPorductCredit.setId(id);
		mfCusPorductCredit = mfCusPorductCreditFeign.getById(mfCusPorductCredit);

		FormService formService = new FormService();
		FormData formcreditproduct0003 = formService.getFormData("creditproduct0003");
		getObjValue(formcreditproduct0003, mfCusPorductCredit);

		model.addAttribute("formcreditproduct0003", formcreditproduct0003);
		model.addAttribute("query", "");

		return "/hm/auth/MfCusPorductCredit_DetailNew";
	}
	
	/**
	 * 查询
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdNewApprove")
	public String getByIdNewApprove(Model model, String ajaxData, String id) throws Exception {
		ActionContext.initialize(request, response);

		MfCusPorductCredit mfCusPorductCredit = new MfCusPorductCredit();
		mfCusPorductCredit.setId(id);
		mfCusPorductCredit = mfCusPorductCreditFeign.getById(mfCusPorductCredit);

		FormService formService = new FormService();
		FormData formcreditproduct0003 = formService.getFormData("creditproduct0004");
		getObjValue(formcreditproduct0003, mfCusPorductCredit);

		model.addAttribute("formcreditproduct0003", formcreditproduct0003);
		model.addAttribute("query", "");

		return "/hm/auth/MfCusPorductCredit_DetailNewApprove";
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String ajaxData, String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfCusPorductCredit mfCusPorductCredit = new MfCusPorductCredit();
		mfCusPorductCredit.setId(id);
		mfCusPorductCreditFeign.delete(mfCusPorductCredit);
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
		FormData formcreditproduct0002 = formService.getFormData("creditproduct0002");
		getFormValue(formcreditproduct0002);
		boolean validateFlag = this.validateFormData(formcreditproduct0002);
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
		FormData formcreditproduct0002 = formService.getFormData("creditproduct0002");
		getFormValue(formcreditproduct0002);
		boolean validateFlag = this.validateFormData(formcreditproduct0002);
	}

	/**
	 * 方法描述：查询授信产品信息
	 * 
	 * @param ajaxData
	 * @param creditAppId
	 * @param creditProductBatchId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCreditProductDetailListHtmlAjax")
	@ResponseBody
	public Map<String, Object> getCreditProductDetailListHtmlAjax(String ajaxData, String creditAppId,
			String creditProductBatchId, String tableId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusPorductCredit mfCusPorductCredit = new MfCusPorductCredit();
		try {
			if (StringUtil.isBlank(creditAppId) && StringUtil.isBlank(creditProductBatchId)) {
				throw new Exception("产品授信查询失败！");
			}

			String htmlStr = "";
			mfCusPorductCredit.setCreditAppId(creditAppId);
			mfCusPorductCredit.setCreditProductBatchId(creditProductBatchId);
			List<MfCusPorductCredit> mfCusPorductCreditList = mfCusPorductCreditFeign.getMfCusPorductCreditList(mfCusPorductCredit);
			JsonTableUtil jtu = new JsonTableUtil();
			if(StringUtil.isBlank(tableId)){
				tableId = "tablecreditproduct0002";
			}
			htmlStr = jtu.getJsonStr(tableId, "tableTag", mfCusPorductCreditList, null, true);
			dataMap.put("flag", "success");
			dataMap.put("htmlStr", htmlStr);
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
		}
		return dataMap;
	}

	/**
	 * 方法描述：查询产品授信总额度及校验
	 * 
	 * @param ajaxData
	 * @param creditAppId
	 * @param creditProductBatchId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getProductAmtSumAndChkAjax")
	@ResponseBody
	public Map<String, Object> getProductAmtSumAndChkAjax(String ajaxData, String creditAppId, String creditProductBatchId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusPorductCredit mfCusPorductCredit = new MfCusPorductCredit();
		try {
			if (StringUtil.isBlank(creditAppId) && StringUtil.isBlank(creditProductBatchId)) {
				throw new Exception("产品授信列表刷新失败！");
			}
			mfCusPorductCredit.setCreditAppId(creditAppId);
			mfCusPorductCredit.setCreditProductBatchId(creditProductBatchId);
			List<MfCusPorductCredit> mfCusPorductCreditList = mfCusPorductCreditFeign
					.getMfCusPorductCreditList(mfCusPorductCredit);
			if (null == mfCusPorductCreditList || mfCusPorductCreditList.isEmpty()) {
				dataMap.put("flag", "error");
				dataMap.put("msg", "未添加产品授信明细！");
				return dataMap;
			}

			// 计算产品授信总额度，校验 同一个产品+基地不允许多次录入
			Map<String, String> map = new HashMap<String, String>();
			Double productAmtSum = 0D;
			Double productAmtMax = 0D;
			for (MfCusPorductCredit mcpc : mfCusPorductCreditList) {
				String kindNo = mcpc.getKindNo();
				String baseType = mcpc.getBaseType();

				if (StringUtil.isBlank(baseType)) {
					baseType = "base";
				}
				if (StringUtil.isNotBlank(map.get(kindNo)) && baseType.equals(map.get(kindNo))) {
					dataMap.put("flag", "error");
					dataMap.put("msg", "同一个产品+基地不允许多次录入！");
					return dataMap;
				}
				map.put(kindNo, baseType);
				productAmtSum = DataUtil.add(productAmtSum, mcpc.getCreditAmt(), 20);
				if (productAmtMax < mcpc.getCreditAmt()) {
					productAmtMax = mcpc.getCreditAmt();
				}
			}
			productAmtSum = DataUtil.retainDigit(productAmtSum, 2);

			dataMap.put("flag", "success");
			dataMap.put("productAmtSum", productAmtSum);
			dataMap.put("productAmtMax", productAmtMax);
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
		}
		return dataMap;
	}
	
	@RequestMapping(value = "/getMfCusCreditProListAjax")
	@ResponseBody
	public Map<String, Object> getMfCusCreditProListAjax(String creditAppId,String tableId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusPorductCredit mfCusPorductCredit = new MfCusPorductCredit();
		mfCusPorductCredit.setCreditAppId(creditAppId);
		List<MfCusPorductCredit> mfCusPorductCreditList = mfCusPorductCreditFeign.getByCreditAppId(mfCusPorductCredit);
		if(mfCusPorductCreditList != null && mfCusPorductCreditList.size()>0){
			dataMap.put("ifHasList", 1);
		}else{
			dataMap.put("ifHasList", 0);
		}
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "tableTag", mfCusPorductCreditList, null, true);
		dataMap.put("htmlStr", tableHtml);
		return dataMap;
	}
   
}

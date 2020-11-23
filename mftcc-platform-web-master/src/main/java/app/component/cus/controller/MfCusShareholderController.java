package app.component.cus.controller;

import app.base.SpringUtil;
import app.component.common.EntityUtil;
import app.component.cus.entity.MfCusCorpBaseInfo;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.entity.MfCusShareholder;
import app.component.cus.feign.MfCusCorpBaseInfoFeign;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.component.cus.feign.MfCusShareholderFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.MathExtend;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.WaterIdUtil;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;
import config.YmlConfig;
import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: MfCusShareholderAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Mon May 23 11:31:15 CST 2016
 **/
@Controller
@RequestMapping("/mfCusShareholder")
public class MfCusShareholderController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfCusShareholderBo
	@Autowired
	private MfCusShareholderFeign mfCusShareholderFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	@Autowired
	private MfCusFormConfigFeign mfCusFormConfigFeign;
	@Autowired
	private MfCusCorpBaseInfoFeign mfCusCorpBaseInfoFeign;

	// 全局变量

	@SuppressWarnings("unchecked")
	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model, String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		MfCusShareholder mfCusShareholder = new MfCusShareholder();
		mfCusShareholder.setCusNo(cusNo);
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("mfCusShareholder", mfCusShareholder));
		List<MfCusShareholder> mfCusShareholderList = (List<MfCusShareholder>) mfCusShareholderFeign.findByPage(ipage)
				.getResult();
		model.addAttribute("query", "");
		model.addAttribute("mfCusShareholderList", mfCusShareholderList);
		return "/component/cus/MfCusShareholder_List";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getListPageBig")
	public String getListPageBig(Model model, String cusNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formcussha00002 = null;
		// List<MfCusShareholder> mfCusShareholderlist = new
		// ArrayList<MfCusShareholder>();
		try {
			formcussha00002 = formService.getFormData("cussha00002");
			MfCusShareholder mfCusShareholder = new MfCusShareholder();
			mfCusShareholder.setCusNo(cusNo);
			Ipage ipage = this.getIpage();
			ipage.setParams(this.setIpageParams("mfCusShareholder", mfCusShareholder));
			List<MfCusShareholder> mfCusShareholderList = (List<MfCusShareholder>) mfCusShareholderFeign
					.findByPage(ipage).getResult();
			model.addAttribute("mfCusShareholderList", mfCusShareholderList);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		model.addAttribute("formcussha00002", formcussha00002);
		model.addAttribute("query", "");
		return "/component/cus/MfCusShareholder_ListBig";
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
		MfCusShareholder mfCusShareholder = new MfCusShareholder();
		try {
			mfCusShareholder.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCusShareholder.setCriteriaList(mfCusShareholder, ajaxData);// 我的筛选
			// this.getRoleConditions(mfCusShareholder,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfCusShareholder", mfCusShareholder));
			ipage = mfCusShareholderFeign.findByPage(ipage);
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
	@RequestMapping(value = "/checkPushCapitalScaleAjax")
	@ResponseBody
	public Map<String, Object> checkPushCapitalScaleAjax(String number, String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			if ("".equals(number)) {
				number = "0";
			}
			Double num = Double.parseDouble(number);
			Map<String, String> paramMap = new HashMap<String, String>();
			if (num > 100) {
				dataMap.put("flag", "error");
				paramMap.put("timeOne", "股权比例");
				paramMap.put("timeTwo", "100%");
				dataMap.put("msg", MessageEnum.NOT_FORM_TIME.getMessage(paramMap));
				return dataMap;
			} else {
				MfCusShareholder mfCusShareholder = new MfCusShareholder();
				mfCusShareholder.setCusNo(cusNo);
				mfCusShareholder.setDelFlag("0");
				List<MfCusShareholder> list = (List<MfCusShareholder>) mfCusShareholderFeign
						.getByCusNo(mfCusShareholder);
				Double sum = num;
				for (MfCusShareholder m : list) {
					sum += m.getPushCapitalScale();
				}
				if (sum > 100) {
					dataMap.put("flag", "error");
					paramMap.put("timeOne", "所有股东股权比例之和");
					paramMap.put("timeTwo", "100%");
					dataMap.put("msg", MessageEnum.NOT_FORM_TIME.getMessage(paramMap));
					return dataMap;
				}

			}
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 计算出资比例
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/calculateAmountAjax")
	@ResponseBody
	public Map<String, Object> calculateAmountAjax(String pushCapitalAmt, String registeredCapital) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String pushCapitalScale = MathExtend.divide(MathExtend.multiply(pushCapitalAmt, "100"), registeredCapital, 2);
			if (Double.valueOf(pushCapitalScale) > 100) {
				pushCapitalScale = "100";
			}
			dataMap.put("pushCapitalScale", pushCapitalScale);
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "计算股权比例失败！");
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
			Map<String, Object> map = getMapByJson(ajaxData);
			String formId = (String) map.get("formId");
			if (StringUtil.isEmpty(formId)) {
				formId = mfCusFormConfigFeign.getByCusType("base", "MfCusShareholderAction").getAddModel();
			}
			FormData formcussha00003 = formService.getFormData(formId);
			getFormValue(formcussha00003, map);
			if (this.validateFormData(formcussha00003)) {
				String infIntegrity = "";
				MfCusShareholder mfCusShareholder = new MfCusShareholder();
				setObjValue(formcussha00003, mfCusShareholder);

				Double a = mfCusShareholderFeign.getShareholderByCusNo(mfCusShareholder);
				if ((mfCusShareholder.getPushCapitalScale() + a) > 100) {
					dataMap.put("flag", "error");
					dataMap.put("msg", "股权比例已超出100%,剩余" + (100 - a) + "%");
				} else {
					MfCusCustomer mfCusCustomer = new MfCusCustomer();
					mfCusCustomer.setCusNo(mfCusShareholder.getCusNo());
					mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
					MfCusShareholder tempShareholder = new MfCusShareholder();
					tempShareholder.setCusNo(mfCusCustomer.getCusNo());
					tempShareholder.setIdNum(mfCusShareholder.getIdNum());
					tempShareholder.setShareholderType(mfCusShareholder.getShareholderType());
					List<MfCusShareholder> tempList = mfCusShareholderFeign.findByPage2(tempShareholder);
					if(tempList.size()>0){
						dataMap.put("flag", "error");
						dataMap.put("msg", "该股东已存在");
					}else{
						mfCusShareholderFeign.insert(mfCusShareholder);

						// 更新客户信息完整度
						infIntegrity = mfCusCustomerFeign.updateInfIntegrity(mfCusShareholder.getCusNo(),
								mfCusShareholder.getRelNo());

						String tableId = "tableCusShareHolderList";
						MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
								"MfCusShareholderAction");
						if (mfCusFormConfig != null && StringUtil.isNotEmpty(mfCusFormConfig.getListModelDef())) {
							tableId = "table" + mfCusFormConfig.getListModelDef();
						}

						String cusNo = mfCusShareholder.getCusNo();
						String relNo = mfCusShareholder.getRelNo();
						mfCusShareholder = new MfCusShareholder();
						mfCusShareholder.setCusNo(cusNo);
						mfCusShareholder.setRelNo(relNo);
						Ipage ipage = this.getIpage();
						ipage.setParams(this.setIpageParams("mfCusShareholder", mfCusShareholder));
						@SuppressWarnings("unchecked")
						List<MfCusShareholder> list = (List<MfCusShareholder>) mfCusShareholderFeign.findByPage(ipage)
								.getResult();
						JsonTableUtil jtu = new JsonTableUtil();
						String tableHtml = jtu.getJsonStr(tableId, "tableTag", list, null, true);
						dataMap.put("htmlStr", tableHtml);
						dataMap.put("htmlStrFlag", "1");
						dataMap.put("infIntegrity", infIntegrity);
						dataMap.put("flag", "success");
						dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
					}

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

	@SuppressWarnings("unchecked")
	private void getTableData(String cusNo, String tableId, Map<String, Object> dataMap) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		MfCusShareholder mfCusShareholder = new MfCusShareholder();
		mfCusShareholder.setCusNo(cusNo);
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("mfCusShareholder", mfCusShareholder));
		String tableHtml = jtu.getJsonStr(tableId, "tableTag",
				(List<MfCusShareholder>) mfCusShareholderFeign.findByPage(ipage).getResult(), null, true);
		dataMap.put("tableData", tableHtml);
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
		FormData formcussha00002 = formService.getFormData("cussha00002");
		getFormValue(formcussha00002, getMapByJson(ajaxData));
		MfCusShareholder mfCusShareholderJsp = new MfCusShareholder();
		setObjValue(formcussha00002, mfCusShareholderJsp);
		MfCusShareholder mfCusShareholder = mfCusShareholderFeign.getById(mfCusShareholderJsp);
		if (mfCusShareholder != null) {
			try {
				mfCusShareholder = (MfCusShareholder) EntityUtil.reflectionSetVal(mfCusShareholder, mfCusShareholderJsp,
						getMapByJson(ajaxData));
				mfCusShareholderFeign.update(mfCusShareholder);
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
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusShareholder mfCusShareholder = new MfCusShareholder();
		try {
			Map<String, Object> map = getMapByJson(ajaxData);
			String formId = (String) map.get("formId");
			if (StringUtil.isEmpty(formId)) {
				formId = mfCusFormConfigFeign.getByCusType("base", "MfCusShareholderAction").getAddModel();
			}
			FormData formcussha00003 = formService.getFormData(formId);
			getFormValue(formcussha00003, map);
			if (this.validateFormData(formcussha00003)) {
				setObjValue(formcussha00003, mfCusShareholder);
				Double a = mfCusShareholderFeign.getShareholderByCusNo(mfCusShareholder);
				if ((mfCusShareholder.getPushCapitalScale() + a) > 100) {
					dataMap.put("flag", "error");
					dataMap.put("msg", "股权比例已超出100%,剩余" + (100 - a) + "%");
					return dataMap;
				} else {
					MfCusShareholder tempShareholder = new MfCusShareholder();
					tempShareholder.setShareholderType(mfCusShareholder.getShareholderType());
					tempShareholder.setIdNum(mfCusShareholder.getIdNum());
					List<MfCusShareholder> tempList = mfCusShareholderFeign.findByPage2(tempShareholder);
					if(null != tempList && tempList.size() == 1 && !tempList.get(0).getShareholderId().equals(mfCusShareholder.getShareholderId())){
						dataMap.put("flag", "error");
						dataMap.put("msg", "该股东已存在");
						return dataMap;
					}
					mfCusShareholderFeign.update(mfCusShareholder);
					MfCusCustomer mfCusCustomer = new MfCusCustomer();
					mfCusCustomer.setCusNo(mfCusShareholder.getCusNo());
					mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);

					String tableId = "tableCusShareHolderList";
					MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
							"MfCusShareholderAction");
					if (mfCusFormConfig != null && StringUtil.isNotEmpty(mfCusFormConfig.getListModelDef())) {
						tableId = "table" + mfCusFormConfig.getListModelDef();
					}
					MfCusShareholder param = new MfCusShareholder();
					param.setCusNo(mfCusShareholder.getCusNo());
					Ipage ipage = this.getIpage();
					JsonTableUtil jtu = new JsonTableUtil();
					ipage.setParams(this.setIpageParams("mfCusShareholder", param));
					String tableHtml = jtu.getJsonStr(tableId, "tableTag",
							(List<MfCusShareholder>) mfCusShareholderFeign.findByPage(ipage).getResult(), null, true);
					dataMap.put("htmlStr", tableHtml);
					dataMap.put("htmlStrFlag", "1");

					dataMap.put("flag", "success");
					dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
				}
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
	public Map<String, Object> getByIdAjax(String shareholderId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcussha00002 = formService.getFormData("cussha00002");
		MfCusShareholder mfCusShareholder = new MfCusShareholder();
		mfCusShareholder.setShareholderId(shareholderId);
		mfCusShareholder = mfCusShareholderFeign.getById(mfCusShareholder);
		getObjValue(formcussha00002, mfCusShareholder, formData);
		if (mfCusShareholder != null) {
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
	public Map<String, Object> deleteAjax(String shareholderId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusShareholder mfCusShareholder = new MfCusShareholder();
		mfCusShareholder.setShareholderId(shareholderId);
		try {
			mfCusShareholderFeign.delete(mfCusShareholder);
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
	public String input(Model model, String cusNo) throws Exception {
		Logger logger = LoggerFactory.getLogger(MfCusShareholderController.class);
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfCusShareholder mfCusShareholder = new MfCusShareholder();
		mfCusShareholder.setCusNo(cusNo);
		mfCusShareholder.setRelNo(cusNo);
		String cusName = "";
		String cusType = "";
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		if (mfCusCustomer != null) {
			cusName = mfCusCustomer.getCusName();
			cusType = mfCusCustomer.getCusType();
		}
		// 处理企业客户相关信息
		MfCusCorpBaseInfo mfCusCorpBaseInfo = new MfCusCorpBaseInfo();
		mfCusCorpBaseInfo.setCusNo(cusNo);
		mfCusCorpBaseInfo = mfCusCorpBaseInfoFeign.getById(mfCusCorpBaseInfo);
		if(mfCusCorpBaseInfo != null){
			mfCusShareholder.setPushCapitalDate(mfCusCorpBaseInfo.getSetupDate());
			model.addAttribute("registeredCapital", new DecimalFormat("#.00").format(mfCusCorpBaseInfo.getRegisteredCapital()));
		}
		mfCusShareholder.setCusName(cusName);
		mfCusShareholder.setShareholderId(WaterIdUtil.getWaterId("sha"));
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(cusType, "MfCusShareholderAction");
		String formId = "cusShareholderInfoBase";
		FormData formcussha00003 = null;
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			logger.error("客户类型为" + cusType + "的MfCusShareholderAction表单信息没有查询到");
		} else {
			formcussha00003 = formService.getFormData(formId);
			if (formcussha00003.getFormId() == null) {
				logger.error("客户类型为" + cusType + "的MfCusShareholderAction表单form" + formId + ".xml文件不存在");
			} else {
				getFormValue(formcussha00003);
				getObjValue(formcussha00003, mfCusShareholder);
			}
		}
		// 返回出资方式json
		JSONArray map = new CodeUtils().getJSONArrayByKeyName("PUSH_CAPITAL_TYPE");
		String items = map.toString().replaceAll("optName", "name").replace("optCode", "id");
		model.addAttribute("formcussha00003", formcussha00003);
		model.addAttribute("ajaxData", items);
		model.addAttribute("query", "");
		YmlConfig ymlConfig = (YmlConfig)SpringUtil.getBean(YmlConfig.class);
		String sysProjectName = ymlConfig.getSysParams().get("sys.project.name");
		model.addAttribute("sysProjectName", sysProjectName);
		return "/component/cus/MfCusShareholder_Insert";
	}

	/**
	 * 
	 * 方法描述： 业务新增客户信息表单页面
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author zhs
	 * @date 2017-9-23 上午10:33:48
	 */
	@RequestMapping(value = "/inputBiz")
	public String inputBiz(Model model, String cusNo, String kindNo, String relNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfCusShareholder mfCusShareholder = new MfCusShareholder();
		mfCusShareholder.setCusNo(cusNo);
		mfCusShareholder.setRelNo(relNo);
		String cusName = "";
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		cusName = mfCusCustomer.getCusName();
		mfCusShareholder.setCusName(cusName);
		mfCusShareholder.setShareholderId(WaterIdUtil.getWaterId("sha"));
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(kindNo, "MfCusShareholderAction");
		FormData formcussha00003 = null;
		String formId = "";
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// logger.error("产品类型为"+kindNo+"的MfCusShareholderAction表单信息没有查询到");
		} else {
			formcussha00003 = formService.getFormData(formId);
			if (formcussha00003.getFormId() == null) {
				// logger.error("产品类型为"+kindNo+"的MfCusShareholderAction表单form"+formId+".xml文件不存在");
			} else {
				getFormValue(formcussha00003);
				getObjValue(formcussha00003, mfCusShareholder);
			}
		}
		// 返回出资方式json
		JSONArray map = new CodeUtils().getJSONArrayByKeyName("PUSH_CAPITAL_TYPE");
		String items = map.toString().replaceAll("optName", "name").replace("optCode", "id");
		String ajaxData = items;
		model.addAttribute("formcussha00003", formcussha00003);
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("query", "");
		return "/component/cus/MfCusShareholder_Insert";
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
		FormData formcussha00002 = formService.getFormData("cussha00002");
		getFormValue(formcussha00002);
		MfCusShareholder mfCusShareholder = new MfCusShareholder();
		setObjValue(formcussha00002, mfCusShareholder);
		mfCusShareholderFeign.insert(mfCusShareholder);
		getObjValue(formcussha00002, mfCusShareholder);
		this.addActionMessage(model, "保存成功");
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("mfCusShareholder", mfCusShareholder));
		List<MfCusShareholder> mfCusShareholderList = (List<MfCusShareholder>) mfCusShareholderFeign.findByPage(ipage)
				.getResult();
		model.addAttribute("formcussha00002", formcussha00002);
		model.addAttribute("mfCusShareholderList", mfCusShareholderList);
		model.addAttribute("query", "");
		return "/component/cus/MfCusShareholder_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String shareholderId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfCusShareholder mfCusShareholder = new MfCusShareholder();
		mfCusShareholder.setShareholderId(shareholderId);
		mfCusShareholder = mfCusShareholderFeign.getById(mfCusShareholder);
		FormData formcussha00003 = null;
		String cusType = "";
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(mfCusShareholder.getCusNo());
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		cusType = mfCusCustomer.getCusType();

		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(cusType, "MfCusShareholderAction");
		String formId = "";
		if (mfCusFormConfig == null) {

		} else {
			if ("1".equals(mfCusFormConfig.getShowType())) {
				formId = mfCusFormConfig.getShowModelDef();
			} else {
				formId = mfCusFormConfig.getAddModelDef();
			}
		}
		if (StringUtil.isEmpty(formId)) {
			// logger.error("客户类型为"+cusType+"的MfCusShareholderAction表单信息没有查询到");
		} else {
			formcussha00003 = formService.getFormData(formId);
			if (formcussha00003.getFormId() == null) {
				// logger.error("客户类型为"+cusType+"的MfCusShareholderAction表单form"+formId+".xml文件不存在");
			} else {
				getFormValue(formcussha00003);
				getObjValue(formcussha00003, mfCusShareholder);
			}
		}
		// 返回出资方式json
		JSONArray map = new CodeUtils().getJSONArrayByKeyName("PUSH_CAPITAL_TYPE");
		String items = map.toString().replaceAll("optName", "name").replace("optCode", "id");
		String ajaxData = items;
		model.addAttribute("formcussha00003", formcussha00003);
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("query", "");
		return "/component/cus/MfCusShareholder_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String shareholderId, String cusNo) throws Exception {
		ActionContext.initialize(request, response);

		MfCusShareholder mfCusShareholder = new MfCusShareholder();
		mfCusShareholder.setShareholderId(shareholderId);
		mfCusShareholderFeign.delete(mfCusShareholder);
		return getListPage(model, cusNo);
	}

	@RequestMapping(value = "/getTableDataAjax")
	@ResponseBody
	public Map<String, Object> getTableDataAjax(String ajaxData, String cusNo, String tableId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		getTableData(cusNo, tableId, dataMap);
		return dataMap;
	}

	// 列表展示详情，单字段编辑
	@RequestMapping(value = "/listShowDetailAjax")
	@ResponseBody
	public Map<String, Object> listShowDetailAjax(String cusNo, String shareholderId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String cusType = "";
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		cusType = mfCusCustomer.getCusType();
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(cusType, "MfCusShareholderAction");
		String formId = null;
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getShowModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// logger.error("客户类型为"+cusType+"的MfCusShareholderAction表单信息没有查询到");
		} else {
			Map<String, Object> formData = new HashMap<String, Object>();
			request.setAttribute("ifBizManger", "3");
			JsonFormUtil jsonFormUtil = new JsonFormUtil();
			FormData formcussha00004 = formService.getFormData(formId);
			MfCusShareholder mfCusShareholder = new MfCusShareholder();
			mfCusShareholder.setShareholderId(shareholderId);
			mfCusShareholder = mfCusShareholderFeign.getById(mfCusShareholder);
			getObjValue(formcussha00004, mfCusShareholder, formData);
			String htmlStrCorp = jsonFormUtil.getJsonStr(formcussha00004, "propertySeeTag", "");
			if (mfCusShareholder != null) {
				dataMap.put("formHtml", htmlStrCorp);
				dataMap.put("flag", "success");
			} else {
				dataMap.put("msg", "获取详情失败");
				dataMap.put("flag", "error");
			}
			dataMap.put("formData", mfCusShareholder);
		}
		return dataMap;
	}

	/**
	 * ajax 异步 单个字段或多个字段更新
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateByOneAjax")
	@ResponseBody
	public Map<String, Object> updateByOneAjax(String ajaxData, String formId) throws Exception {
		ActionContext.initialize(request, response);

		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> map = getMapByJson(ajaxData);

		formId = (String) map.get("formId");
		FormService formService = new FormService();
		FormData formcussha00002 = formService.getFormData(formId);
		getFormValue(formcussha00002, map);

		MfCusShareholder mfCusShareholderNew = new MfCusShareholder();
		setObjValue(formcussha00002, mfCusShareholderNew);

		MfCusShareholder mfCusShareholder = new MfCusShareholder();
		mfCusShareholder.setShareholderId(mfCusShareholderNew.getShareholderId());
		mfCusShareholder = mfCusShareholderFeign.getById(mfCusShareholder);
		if (mfCusShareholder != null) {
			try {
				mfCusShareholder = (MfCusShareholder) EntityUtil.reflectionSetVal(mfCusShareholder, mfCusShareholderNew,
						map);
				mfCusShareholderFeign.update(mfCusShareholder);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
				throw new Exception(e.getMessage());
			}
		} else {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SAVE_CONTENT.getMessage("编号不存在"));
		}
		return dataMap;
	}

	/**
	 * 新增校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/validateInsert")
	public void validateInsert(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formcussha00002 = formService.getFormData("cussha00002");
		getFormValue(formcussha00002);
		boolean validateFlag = this.validateFormData(formcussha00002);
	}

	/**
	 * 修改校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/validateUpdate")
	public void validateUpdate(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formcussha00002 = formService.getFormData("cussha00002");
		getFormValue(formcussha00002);
		boolean validateFlag = this.validateFormData(formcussha00002);
	}

}

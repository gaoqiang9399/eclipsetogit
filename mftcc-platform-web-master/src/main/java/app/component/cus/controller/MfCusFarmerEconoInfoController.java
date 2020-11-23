package app.component.cus.controller;

import app.component.app.entity.MfBusApply;
import app.component.appinterface.AppInterfaceFeign;
import app.component.common.EntityUtil;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFarmerEconoInfo;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFarmerEconoInfoFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;
import config.YmlConfig;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
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
 * Title: MfCusFarmerEconoInfoAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Aug 16 10:29:42 CST 2017
 **/
@Controller
@RequestMapping("/mfCusFarmerEconoInfo")
public class MfCusFarmerEconoInfoController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfCusFarmerEconoInfoBo
	@Autowired
	private MfCusFarmerEconoInfoFeign mfCusFarmerEconoInfoFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	@Autowired
	private AppInterfaceFeign appInterfaceFeign;
	// 全局变量
	@Autowired
	private MfCusFormConfigFeign mfCusFormConfigFeign;
	//全局变量
	private String query = "";//初始化query为空
	// 异步参数
	// 表单变量
	@Autowired
	private YmlConfig ymlConfig;

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
		return "/component/cus/MfCusFarmerEconoInfo_List";
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
		MfCusFarmerEconoInfo mfCusFarmerEconoInfo = new MfCusFarmerEconoInfo();
		try {
			mfCusFarmerEconoInfo.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCusFarmerEconoInfo.setCriteriaList(mfCusFarmerEconoInfo, ajaxData);// 我的筛选
			// this.getRoleConditions(mfCusFarmerEconoInfo,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfCusFarmerEconoInfo", mfCusFarmerEconoInfo));
			// 自定义查询Bo方法
			ipage = mfCusFarmerEconoInfoFeign.findByPage(ipage);
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
	public Map<String, Object> insertAjax(String ajaxData, String query) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, Object> map = getMapByJson(ajaxData);
			String formId = (String) map.get("formId");
			if (StringUtil.isEmpty(formId)) {
				formId = mfCusFormConfigFeign.getByCusType("base", "MfCusFarmerEconoInfoAction").getAddModel();
			}
			FormData formcusEconoBase = formService.getFormData(formId);
			getFormValue(formcusEconoBase, getMapByJson(ajaxData));
			if (this.validateFormData(formcusEconoBase)) {
				MfCusFarmerEconoInfo mfCusFarmerEconoInfo = new MfCusFarmerEconoInfo();
				setObjValue(formcusEconoBase, mfCusFarmerEconoInfo);
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				String cusNo = mfCusFarmerEconoInfo.getCusNo();
				mfCusCustomer.setCusNo(cusNo);
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				mfCusFarmerEconoInfo.setCusName(mfCusCustomer.getCusName());

				mfCusFarmerEconoInfoFeign.insert(mfCusFarmerEconoInfo);
				String infIntegrity = mfCusCustomerFeign.updateInfIntegrity(mfCusFarmerEconoInfo.getCusNo(),
						mfCusFarmerEconoInfo.getRelNo());// 更新资料完整度
				String detailFormId = mfCusFormConfigFeign.getByCusType("base", "MfCusFarmerEconoInfoAction")
						.getShowModel();
				if (mfCusFarmerEconoInfo.getCusNo().equals(mfCusFarmerEconoInfo.getRelNo())) {
					detailFormId = mfCusFormConfigFeign
							.getByCusType(mfCusCustomer.getCusType(), "MfCusFarmerEconoInfoAction").getShowModelDef();
				} else {
					MfBusApply mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(mfCusFarmerEconoInfo.getRelNo());
					detailFormId = mfCusFormConfigFeign
							.getByCusType(mfBusApply.getKindNo(), "MfCusFarmerEconoInfoAction").getShowModelDef();
				}
				if (StringUtil.isEmpty(detailFormId)) {
					// logger.error("MfCusFarmerEconoInfoAction的detailFormId找不到");
				}
				/*FormData formcusEconoDetail = formService.getFormData(detailFormId);
				getObjValue(formcusEconoDetail, mfCusFarmerEconoInfo);
*/
				this.getHttpRequest().setAttribute("ifBizManger", "3");


				String tableId = "cusEconoList";
				MfCusFormConfig  mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusFarmerEconoInfoAction");
				if(mfCusFormConfig != null){
					if(StringUtil.isNotEmpty(mfCusFormConfig.getListModelDef())){
						tableId = mfCusFormConfig.getListModelDef();
					}
				}
				String showType =mfCusFormConfig.getShowType();
				mfCusFarmerEconoInfo = new MfCusFarmerEconoInfo();
				mfCusFarmerEconoInfo.setCusNo(cusNo);
				String htmlStr = null;
				if("1".equals(showType)){
					JsonFormUtil jsonFormUtil = new JsonFormUtil();
					mfCusFarmerEconoInfo = mfCusFarmerEconoInfoFeign.getById(mfCusFarmerEconoInfo);
					formId = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusFarmerEconoInfoAction").getShowModelDef();
					FormData formcusEconoDetail = formService.getFormData(formId);
					getFormValue(formcusEconoDetail);
					getObjValue(formcusEconoDetail, mfCusFarmerEconoInfo);
					htmlStr = jsonFormUtil.getJsonStr(formcusEconoDetail, "propertySeeTag", "");
				}else{
					Ipage ipage = this.getIpage();
					JsonTableUtil jtu = new JsonTableUtil();
					ipage.setParams(this.setIpageParams("mfCusFarmerEconoInfo", mfCusFarmerEconoInfo));
					htmlStr = jtu.getJsonStr("table"+tableId, "tableTag", (List<MfCusFarmerEconoInfo>) mfCusFarmerEconoInfoFeign.findByPage(ipage).getResult(), null, true);
				}

				dataMap.put("infIntegrity", infIntegrity);
				dataMap.put("mfCusFarmerEconoInfo", mfCusFarmerEconoInfo);
				dataMap.put("htmlStr", htmlStr);
				dataMap.put("DataFullFlag", "1");

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
		//原来formId为写死状态，修改为从ajaxData中获取
		Map map = getMapByJson(ajaxData);
		String formId = (String) map.get("formId");
		FormData formcusEconoDetail = formService.getFormData(formId);
		//FormData formcusEconoDetail = formService.getFormData("cusEconoDetail");
		getFormValue(formcusEconoDetail, getMapByJson(ajaxData));
		MfCusFarmerEconoInfo mfCusFarmerEconoInfoJsp = new MfCusFarmerEconoInfo();
		setObjValue(formcusEconoDetail, mfCusFarmerEconoInfoJsp);
		MfCusFarmerEconoInfo mfCusFarmerEconoInfo = mfCusFarmerEconoInfoFeign.getById(mfCusFarmerEconoInfoJsp);
		if (mfCusFarmerEconoInfo != null) {
			try {
				mfCusFarmerEconoInfo = (MfCusFarmerEconoInfo) EntityUtil.reflectionSetVal(mfCusFarmerEconoInfo,
						mfCusFarmerEconoInfoJsp, getMapByJson(ajaxData));
				mfCusFarmerEconoInfoFeign.update(mfCusFarmerEconoInfo);
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
		MfCusFarmerEconoInfo mfCusFarmerEconoInfo = new MfCusFarmerEconoInfo();
		try {
			FormData formcusEconoDetail = formService.getFormData("cusEconoDetail");
			getFormValue(formcusEconoDetail, getMapByJson(ajaxData));
			if (this.validateFormData(formcusEconoDetail)) {
				mfCusFarmerEconoInfo = new MfCusFarmerEconoInfo();
				setObjValue(formcusEconoDetail, mfCusFarmerEconoInfo);
				mfCusFarmerEconoInfoFeign.update(mfCusFarmerEconoInfo);

				String formId = "";
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(mfCusFarmerEconoInfo.getCusNo());
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				MfCusFormConfig  mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusFarmerEconoInfoAction");
				if(mfCusFormConfig == null){

				}else{
					formId = mfCusFormConfig.getShowModelDef();
				}
				if(formcusEconoDetail.getFormId() == null){
					//log.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusPersBaseInfoAction表单form"+formId+".xml文件不存在");
				}
				getFormValue(formcusEconoDetail);
				getObjValue(formcusEconoDetail, mfCusCustomer);
				getObjValue(formcusEconoDetail, mfCusFarmerEconoInfo);
				this.getHttpRequest().setAttribute("ifBizManger", "3");
				JsonFormUtil jsonFormUtil = new JsonFormUtil();
				String htmlStr = jsonFormUtil.getJsonStr(formcusEconoDetail,"propertySeeTag",query);

				dataMap.put("htmlStr", htmlStr);
				dataMap.put("htmlStrFlag","1");
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
	public Map<String, Object> getByIdAjax(String farmerEconoInfoId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcusEconoDetail = formService.getFormData("cusEconoDetail");
		MfCusFarmerEconoInfo mfCusFarmerEconoInfo = new MfCusFarmerEconoInfo();
		mfCusFarmerEconoInfo.setFarmerEconoInfoId(farmerEconoInfoId);
		mfCusFarmerEconoInfo = mfCusFarmerEconoInfoFeign.getById(mfCusFarmerEconoInfo);
		getObjValue(formcusEconoDetail, mfCusFarmerEconoInfo, formData);
		if (mfCusFarmerEconoInfo != null) {
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
	public Map<String, Object> deleteAjax(String farmerEconoInfoId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusFarmerEconoInfo mfCusFarmerEconoInfo = new MfCusFarmerEconoInfo();
		mfCusFarmerEconoInfo.setFarmerEconoInfoId(farmerEconoInfoId);
		try {
			mfCusFarmerEconoInfoFeign.delete(mfCusFarmerEconoInfo);
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
	 * @throws Exception===
	 */
	@RequestMapping(value = "/input")
	public String input(Model model, String cusNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);

		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
				"MfCusFarmerEconoInfoAction");
		String formId = null;
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// logger.error("客户类型为" + mfCusCustomer.getCusType() +
			// "的MfCusFarmerEconoInfoAction表单信息没有查询到");
		} else {
			FormData formcusEconoBase = formService.getFormData(formId);
			if (formcusEconoBase.getFormId() == null) {
				/*
				 * logger.error("客户类型为" + mfCusCustomer.getCusType() +
				 * "的MfCusFarmerEconoInfoAction表单form" + formId + ".xml文件不存在");
				 */
			} else {
				MfCusFarmerEconoInfo mfCusFarmerEconoInfo = new MfCusFarmerEconoInfo();
				mfCusFarmerEconoInfo.setCusNo(cusNo);
				mfCusFarmerEconoInfo.setRelNo(cusNo);
				mfCusFarmerEconoInfo.setCusName(mfCusCustomer.getCusName());
				getFormValue(formcusEconoBase);
				getObjValue(formcusEconoBase, mfCusFarmerEconoInfo);
			}
			model.addAttribute("formcusEconoBase", formcusEconoBase);
		}
		String projectName= ymlConfig.getSysParams().get("sys.project.name");
		model.addAttribute("projectName", projectName);
		model.addAttribute("query", "");
		return "/component/cus/MfCusFarmerEconoInfo_Insert";
	}

	/**
	 * 
	 * 方法描述： 业务新增客户信息表单页面
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author zhs
	 * @date 2017-9-26 下午2:32:09
	 */
	@RequestMapping(value = "/inputBiz")
	public String inputBiz(Model model, String cusNo, String kindNo, String relNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);

		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(kindNo, "MfCusFarmerEconoInfoAction");
		String formId = null;
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// logger.error("产品类型为" + kindNo +
			// "的MfCusFarmerEconoInfoAction表单信息没有查询到");
		} else {
			FormData formcusEconoBase = formService.getFormData(formId);
			if (formcusEconoBase.getFormId() == null) {
				// logger.error("产品类型为" + kindNo +
				// "的MfCusFarmerEconoInfoAction表单form" + formId + ".xml文件不存在");
			} else {
				MfCusFarmerEconoInfo mfCusFarmerEconoInfo = new MfCusFarmerEconoInfo();
				mfCusFarmerEconoInfo.setCusNo(cusNo);
				mfCusFarmerEconoInfo.setRelNo(relNo);
				getFormValue(formcusEconoBase);
				getObjValue(formcusEconoBase, mfCusCustomer.getCusName());
				getObjValue(formcusEconoBase, mfCusFarmerEconoInfo);
			}
			model.addAttribute("formcusEconoBase", formcusEconoBase);
		}

		model.addAttribute("query", "");
		return "/component/cus/MfCusFarmerEconoInfo_Insert";
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
		FormData formcusEconoDetail = formService.getFormData("cusEconoDetail");
		getFormValue(formcusEconoDetail);
		MfCusFarmerEconoInfo mfCusFarmerEconoInfo = new MfCusFarmerEconoInfo();
		setObjValue(formcusEconoDetail, mfCusFarmerEconoInfo);
		mfCusFarmerEconoInfoFeign.insert(mfCusFarmerEconoInfo);
		getObjValue(formcusEconoDetail, mfCusFarmerEconoInfo);
		this.addActionMessage(model, "保存成功");
		List<MfCusFarmerEconoInfo> mfCusFarmerEconoInfoList = (List<MfCusFarmerEconoInfo>) mfCusFarmerEconoInfoFeign
				.findByPage(this.getIpage()).getResult();
		model.addAttribute("formcusEconoDetail", formcusEconoDetail);
		model.addAttribute("mfCusFarmerEconoInfoList", mfCusFarmerEconoInfoList);
		model.addAttribute("query", "");
		return "/component/cus/MfCusFarmerEconoInfo_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 * @author GuoJian
	 * @data 2017-8-16 11:10:09
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String cusNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        mfCusCustomer.setCusNo(cusNo);
        mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
        MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
                "MfCusFarmerEconoInfoAction");
        String formId = "cusEconoBase";
        if (mfCusFormConfig == null) {

        } else {
            formId = mfCusFormConfig.getAddModelDef();
        }
		FormData formcusEconoBase = formService.getFormData(formId);
		getFormValue(formcusEconoBase);
		MfCusFarmerEconoInfo mfCusFarmerEconoInfo = new MfCusFarmerEconoInfo();
		mfCusFarmerEconoInfo.setCusNo(cusNo);
		mfCusFarmerEconoInfo = mfCusFarmerEconoInfoFeign.getById(mfCusFarmerEconoInfo);
		getObjValue(formcusEconoBase, mfCusFarmerEconoInfo);
		model.addAttribute("formcusEconoBase", formcusEconoBase);
		model.addAttribute("query", "");
		return "/component/cus/MfCusFarmerEconoInfo_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String farmerEconoInfoId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfCusFarmerEconoInfo mfCusFarmerEconoInfo = new MfCusFarmerEconoInfo();
		mfCusFarmerEconoInfo.setFarmerEconoInfoId(farmerEconoInfoId);
		mfCusFarmerEconoInfoFeign.delete(mfCusFarmerEconoInfo);
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
		FormData formcusEconoDetail = formService.getFormData("cusEconoDetail");
		getFormValue(formcusEconoDetail);
		boolean validateFlag = this.validateFormData(formcusEconoDetail);
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
		FormData formcusEconoDetail = formService.getFormData("cusEconoDetail");
		getFormValue(formcusEconoDetail);
		boolean validateFlag = this.validateFormData(formcusEconoDetail);
	}


    // 列表展示详情，单字段编辑
    @RequestMapping(value = "/listShowDetailAjax")
    @ResponseBody
    public Map<String, Object> listShowDetailAjax(String farmerEconoInfoId,String cusNo) throws Exception {
        FormService formService = new FormService();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        ActionContext.initialize(request, response);
        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        mfCusCustomer.setCusNo(cusNo);
        mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
        MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
                "MfCusFarmerEconoInfoAction");
        String formId = "";
        if (mfCusFormConfig == null) {

        } else {
            formId = mfCusFormConfig.getShowModelDef();
        }
        if (StringUtil.isEmpty(formId)) {
            // logger.error("客户类型为" + mfCusCustomer.getCusType() +
            // "的MfCusGuaranteeOuterAction表单信息没有查询到");
            dataMap.put("msg", "获取详情失败");
            dataMap.put("flag", "error");
        } else {
            Map<String, Object> formData = new HashMap<String, Object>();
            request.setAttribute("ifBizManger","3");
            dataMap = new HashMap<String, Object>();
            JsonFormUtil jsonFormUtil = new JsonFormUtil();
            FormData formcusEconoDetail = formService.getFormData(formId);
            MfCusFarmerEconoInfo mfCusFarmerEconoInfo = new MfCusFarmerEconoInfo();
            mfCusFarmerEconoInfo.setFarmerEconoInfoId(farmerEconoInfoId);
            mfCusFarmerEconoInfo = mfCusFarmerEconoInfoFeign.getById(mfCusFarmerEconoInfo);
            getObjValue(formcusEconoDetail, mfCusFarmerEconoInfo, formData);
            String htmlStrCorp = jsonFormUtil.getJsonStr(formcusEconoDetail, "propertySeeTag", "");
            if (mfCusFarmerEconoInfo != null) {
                dataMap.put("formHtml", htmlStrCorp);
                dataMap.put("flag", "success");
            } else {
                dataMap.put("msg", "获取详情失败");
                dataMap.put("flag", "error");
            }
            dataMap.put("formData", mfCusFarmerEconoInfo);
        }
        return dataMap;
    }
}

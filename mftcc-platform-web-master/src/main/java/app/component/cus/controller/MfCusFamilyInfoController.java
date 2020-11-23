package app.component.cus.controller;

import app.component.auth.entity.MfCusCreditContract;
import app.component.auth.feign.MfCusCreditContractFeign;
import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusPersBaseInfo;
import app.component.cus.entity.MfCusPersonJob;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.entity.MfCusFamilyInfo;
import app.component.cus.entity.MfCusHighInfo;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusPersBaseInfoFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.component.cus.feign.MfCusPersonJobFeign;
import app.component.cus.feign.MfCusFamilyInfoFeign;
import app.component.sys.entity.MfUserPermission;
import app.component.sys.entity.SysUser;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
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
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: MfCusFamilyInfoAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Jun 01 16:17:46 CST 2016
 **/
@Controller
@RequestMapping("/mfCusFamilyInfo")
public class MfCusFamilyInfoController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfCusFamilyInfoBo
	@Autowired
	private MfCusFamilyInfoFeign mfCusFamilyInfoFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	@Autowired
	private MfCusFormConfigFeign mfCusFormConfigFeign;
	@Autowired
	private MfCusPersBaseInfoFeign mfCusPersBaseInfoFeign;
	@Autowired
	private YmlConfig ymlConfig;
	@Autowired
	private MfCusCreditContractFeign   mfCusCreditContractFeign;
	@Autowired
	private MfCusPersonJobFeign mfCusPersonJobFeign;

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
		return "/component/cus/MfCusFamilyInfo_List";

	}

	/**
	 * 
	 * 方法描述： 获得客户社会关系信息列表
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2016-6-1 下午5:57:48
	 */
	@RequestMapping(value = "/getListPageBig")
	public String getListPageBig(Model model, String cusNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formcusfam00001 = null;
		List<MfCusFamilyInfo> mfCusFamilyInfoList = new ArrayList<MfCusFamilyInfo>();
		try {
			formcusfam00001 = formService.getFormData("cusfam00001");
			MfCusFamilyInfo mfCusFamilyInfo = new MfCusFamilyInfo();
			mfCusFamilyInfo.setCusNo(cusNo);
			Ipage ipage = this.getIpage();
			ipage.setParams(this.setIpageParams("mfCusFamilyInfo", mfCusFamilyInfo));
			mfCusFamilyInfoList = (List<MfCusFamilyInfo>) mfCusFamilyInfoFeign.findByPage(ipage).getResult();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		model.addAttribute("formcusfam00001", formcusfam00001);
		model.addAttribute("query", "");
		return "/component/cus/MfCusFamilyInfo_ListBig";
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
		MfCusFamilyInfo mfCusFamilyInfo = new MfCusFamilyInfo();
		try {
			mfCusFamilyInfo.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCusFamilyInfo.setCriteriaList(mfCusFamilyInfo, ajaxData);// 我的筛选
			// this.getRoleConditions(mfCusFamilyInfo,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfCusFamilyInfo", mfCusFamilyInfo));
			ipage = mfCusFamilyInfoFeign.findByPage(ipage);
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
	public Map<String, Object> insertAjax(String ajaxData, String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, Object> map = getMapByJson(ajaxData);
			String formId = map.get("formId").toString();
			FormData formcusfam00002 = formService.getFormData(formId);
			getFormValue(formcusfam00002, getMapByJson(ajaxData));
			if (this.validateFormData(formcusfam00002)) {
				MfCusFamilyInfo mfCusFamilyInfo = new MfCusFamilyInfo();
				String cusNo = (String) map.get("cusNo");
				mfCusFamilyInfo.setCusNo(cusNo);
				setObjValue(formcusfam00002, mfCusFamilyInfo);

				/*判断该证件号码是否与客户本人相同*/
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(mfCusFamilyInfo.getCusNo());
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				if(mfCusCustomer.getIdNum()!=null && mfCusCustomer.getIdNum().equals(mfCusFamilyInfo.getIdNum())){
					dataMap.put("flag", "error");
					dataMap.put("msg", "证件号码不能与当前客户证件号码一致");
					return dataMap;
				}

				/*判断该证件号码是否已存在该客户的社会关系中   刘美好*/
				String idNum = mfCusFamilyInfo.getIdNum();
				if(StringUtil.isNotEmpty(idNum)){
                    MfCusFamilyInfo temp = new MfCusFamilyInfo();
                    temp.setCusNo(mfCusFamilyInfo.getCusNo());
                    temp.setIdNum(idNum);
                    temp.setIdType(mfCusFamilyInfo.getIdType());
                    List<MfCusFamilyInfo> tempList = mfCusFamilyInfoFeign.getFamilyList(temp);
                    if(tempList.size()>0){
                        dataMap.put("flag", "error");
                        dataMap.put("msg", "证件号码已经存在该客户的社会关系中");
                        return dataMap;
                    }
                }
				/*社会关系中是否已存在判断结束*/

				//判断社会关系是否可关联客户
				if(StringUtil.isNotEmpty(idNum)){
					MfCusCustomer cusCustomer = new MfCusCustomer();
					cusCustomer.setIdNum(idNum);
					List<MfCusCustomer> cusList = mfCusCustomerFeign.getByIdNum(cusCustomer);
					if(cusList.size()>0){
						mfCusFamilyInfo.setAnotherCusNo(cusList.get(0).getCusNo());
						mfCusFamilyInfo.setIsAnotherCus("1");
					}
				}

				String relNo = mfCusFamilyInfo.getRelNo();
				String cusName = mfCusCustomer.getCusName();
				mfCusFamilyInfo.setCusName(cusName);
				mfCusFamilyInfo.setId(WaterIdUtil.getWaterId("fam"));
				mfCusFamilyInfo.setDelFlag(BizPubParm.YES_NO_N);
				mfCusFamilyInfoFeign.insert(mfCusFamilyInfo);

				getTableData(cusNo, relNo, tableId, dataMap);

				String infIntegrity = mfCusCustomerFeign.updateInfIntegrity(cusNo, mfCusFamilyInfo.getRelNo());// 更新客户信息完整度
				mfCusFamilyInfo = new MfCusFamilyInfo();
				mfCusFamilyInfo.setCusNo(cusNo);
				mfCusFamilyInfo.setRelNo(relNo);
				Ipage ipage = this.getIpage();
				JsonTableUtil jtu = new JsonTableUtil();
				ipage.setParams(this.setIpageParams("mfCusFamilyInfo", mfCusFamilyInfo));
				String tableHtml = jtu.getJsonStr("tablecusfam00001", "tableTag",
						(List<MfCusHighInfo>) mfCusFamilyInfoFeign.findByPage(ipage).getResult(), null, true);
				dataMap.put("htmlStr", tableHtml);
				dataMap.put("htmlStrFlag", "1");
				dataMap.put("infIntegrity", infIntegrity);
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
		FormData formcusfam00002 = formService.getFormData("cusfam00002");
		getFormValue(formcusfam00002, getMapByJson(ajaxData));
		MfCusFamilyInfo mfCusFamilyInfoJsp = new MfCusFamilyInfo();
		setObjValue(formcusfam00002, mfCusFamilyInfoJsp);
		MfCusFamilyInfo mfCusFamilyInfo = mfCusFamilyInfoFeign.getById(mfCusFamilyInfoJsp);
		if (mfCusFamilyInfo != null) {
			try {
				mfCusFamilyInfo = (MfCusFamilyInfo) EntityUtil.reflectionSetVal(mfCusFamilyInfo, mfCusFamilyInfoJsp,
						getMapByJson(ajaxData));
				mfCusFamilyInfoFeign.update(mfCusFamilyInfo);
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
	public Map<String, Object> updateAjax(String ajaxData, String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);

		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusFamilyInfo mfCusFamilyInfo = new MfCusFamilyInfo();
		try {
			Map map = getMapByJson(ajaxData);
			String formId = (String) map.get("formId");
			if (StringUtil.isEmpty(formId)) {
				formId = mfCusFormConfigFeign.getByCusType("base", "MfCusFamilyInfoAction").getAddModel();
			}
			FormData formcusfam00002 = formService.getFormData(formId);
			getFormValue(formcusfam00002, map);

			if (this.validateFormData(formcusfam00002)) {

				setObjValue(formcusfam00002, mfCusFamilyInfo);
				mfCusFamilyInfoFeign.update(mfCusFamilyInfo);

				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(mfCusFamilyInfo.getCusNo());
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				 tableId = "tablecusfam00001";
				MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
						"MfCusFamilyInfoAction");
				if (mfCusFormConfig != null && StringUtil.isNotEmpty(mfCusFormConfig.getListModelDef())) {
					tableId = "table" + mfCusFormConfig.getListModelDef();
				}
				String cusNo = mfCusFamilyInfo.getCusNo();
				String relNo = mfCusFamilyInfo.getRelNo();
				mfCusFamilyInfo = new MfCusFamilyInfo();
				mfCusFamilyInfo.setCusNo(cusNo);
				mfCusFamilyInfo.setRelNo(relNo);
				Ipage ipage = this.getIpage();
				JsonTableUtil jtu = new JsonTableUtil();
				ipage.setParams(this.setIpageParams("mfCusFamilyInfo", mfCusFamilyInfo));
				String tableHtml = jtu.getJsonStr(tableId, "tableTag",
						(List<MfCusHighInfo>) mfCusFamilyInfoFeign.findByPage(ipage).getResult(), null, true);
				dataMap.put("htmlStr", tableHtml);
				dataMap.put("htmlStrFlag", "1");

				getTableData(cusNo, relNo, tableId, dataMap);

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
	public Map<String, Object> getByIdAjax(String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcusfam00002 = formService.getFormData("cusfam00002");
		MfCusFamilyInfo mfCusFamilyInfo = new MfCusFamilyInfo();
		mfCusFamilyInfo.setId(id);
		mfCusFamilyInfo = mfCusFamilyInfoFeign.getById(mfCusFamilyInfo);
		getObjValue(formcusfam00002, mfCusFamilyInfo, formData);
		if (mfCusFamilyInfo != null) {
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
	public Map<String, Object> deleteAjax(String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusFamilyInfo mfCusFamilyInfo = new MfCusFamilyInfo();
		mfCusFamilyInfo.setId(id);
		try {
			// JSONObject jb = JSONObject.fromObject(ajaxData);
			// mfCusFamilyInfo = (MfCusFamilyInfo)JSONObject.toBean(jb,
			// MfCusFamilyInfo.class);
			mfCusFamilyInfoFeign.delete(mfCusFamilyInfo);
			// getTableData();
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
	public String input(Model model, String relNo, String cusNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		JSONObject json = new JSONObject();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("scNo", BizPubParm.SCENCE_TYPE_DOC_BORROWER);
		MfCusFamilyInfo mfCusFamilyInfo = new MfCusFamilyInfo();
		mfCusFamilyInfo.setCusNo(cusNo);
		mfCusFamilyInfo.setRelNo(cusNo);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),"MfCusFamilyInfoAction");
		String formId = "";
		FormData formcusfam00002 = null;
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusFamilyInfo表单信息没有查询到");
		} else {
              formcusfam00002 = formService.getFormData(formId);
			if (formcusfam00002.getFormId() == null) {
				//logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusFamilyInfo表单form"+formId+".xml文件不存在");
			} else {
				getFormValue(formcusfam00002);
				mfCusFamilyInfo.setCusName(mfCusCustomer.getCusName());
				// 个人客户查询性别，自动给配偶指定性别。
				if (BizPubParm.CUS_TYPE_PERS.equals(mfCusCustomer.getCusBaseType())) {
					MfCusPersBaseInfo mfCusPersBaseInfo = new MfCusPersBaseInfo();
					mfCusPersBaseInfo.setCusNo(mfCusCustomer.getCusNo());
					MfCusPersBaseInfo persBaseInfo = mfCusPersBaseInfoFeign.getById(mfCusPersBaseInfo);
					if (persBaseInfo != null) {
						String baseSex = persBaseInfo.getSex();
						if ("0".equals(baseSex)) {
							baseSex = "1";
						} else if ("1".equals(baseSex)) {
							baseSex = "0";
						} else {
							baseSex = "3";
						}
						String marrige = persBaseInfo.getMarrige();
						json.put("marrige",marrige);
						json.put("baseSex", baseSex);
					}
				}
				getObjValue(formcusfam00002, mfCusFamilyInfo);
			}
		}
		String projectName= ymlConfig.getSysParams().get("sys.project.name");
        model.addAttribute("projectName", projectName);
		JSONArray cusPersRelType = mfCusFamilyInfoFeign.getCusPersRelStr(mfCusFamilyInfo);
		json.put("cusPersRelType", cusPersRelType);
		String ajaxData = json.toString();
		model.addAttribute("formcusfam00002", formcusfam00002);
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("cusNo", cusNo);
		model.addAttribute("query", "");
		return "/component/cus/MfCusFamilyInfo_Insert";
	}

	@RequestMapping(value = "/inputBiz")
	public String inputBiz(Model model, String cusNo, String relNo, String kindNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formcusfam00002 = null;
		MfCusFamilyInfo mfCusFamilyInfo = new MfCusFamilyInfo();
		mfCusFamilyInfo.setCusNo(cusNo);
		mfCusFamilyInfo.setRelNo(relNo);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(kindNo, "MfCusFamilyInfoAction");
		String formId = "";
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// logger.error("产品类型为"+ kindNo +"的MfCusFamilyInfo表单信息没有查询到");
		} else {
			formcusfam00002 = formService.getFormData(formId);
			if (formcusfam00002.getFormId() == null) {
				// logger.error("产品类型为"+ kindNo
				// +"的MfCusFamilyInfo表单form"+formId+".xml文件不存在");
			} else {
				getFormValue(formcusfam00002);
				mfCusFamilyInfo.setCusName(mfCusCustomer.getCusName());
				getObjValue(formcusfam00002, mfCusFamilyInfo);
			}
		}
		model.addAttribute("formcusfam00002", formcusfam00002);
		model.addAttribute("query", "");
		return "/component/cus/MfCusFamilyInfo_Insert";
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
		FormData formcusfam00002 = formService.getFormData("cusfam00002");
		getFormValue(formcusfam00002);
		MfCusFamilyInfo mfCusFamilyInfo = new MfCusFamilyInfo();
		setObjValue(formcusfam00002, mfCusFamilyInfo);
		mfCusFamilyInfoFeign.insert(mfCusFamilyInfo);
		getObjValue(formcusfam00002, mfCusFamilyInfo);
		this.addActionMessage(model, "保存成功");
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("mfCusFamilyInfo", mfCusFamilyInfo));
		List<MfCusFamilyInfo> mfCusFamilyInfoList = (List<MfCusFamilyInfo>) mfCusFamilyInfoFeign
				.findByPage(this.getIpage()).getResult();
		model.addAttribute("formcusfam00002", formcusfam00002);
		model.addAttribute("mfCusFamilyInfoList", mfCusFamilyInfoList);
		model.addAttribute("query", "");
		return "/component/cus/MfCusFamilyInfo_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formcusfam00001 = formService.getFormData("cusRelationBase");
		getFormValue(formcusfam00001);
		JSONObject json = new JSONObject();
		MfCusFamilyInfo mfCusFamilyInfo = new MfCusFamilyInfo();
		mfCusFamilyInfo.setId(id);
		mfCusFamilyInfo = mfCusFamilyInfoFeign.getById(mfCusFamilyInfo);
		getObjValue(formcusfam00001, mfCusFamilyInfo);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(mfCusFamilyInfo.getCusNo());
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		if (BizPubParm.CUS_TYPE_PERS.equals(mfCusCustomer.getCusBaseType())) {
			MfCusPersBaseInfo mfCusPersBaseInfo = new MfCusPersBaseInfo();
			mfCusPersBaseInfo.setCusNo(mfCusCustomer.getCusNo());
			MfCusPersBaseInfo persBaseInfo = mfCusPersBaseInfoFeign.getById(mfCusPersBaseInfo);
			if (persBaseInfo != null) {
				String baseSex = persBaseInfo.getSex();
				if ("0".equals(baseSex)) {
					baseSex = "1";
				} else if ("1".equals(baseSex)) {
					baseSex = "0";
				} else {
					baseSex = "3";
				}
				String marrige = persBaseInfo.getMarrige();
				json.put("marrige",marrige);
				json.put("baseSex", baseSex);
			}
		}
		JSONArray cusPersRelType = mfCusFamilyInfoFeign.getCusPersRelStr(mfCusFamilyInfo);
		json.put("cusPersRelType", cusPersRelType);
		String ajaxData = json.toString();
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("formcusRelationBase", formcusfam00001);
		model.addAttribute("query", "");
		return "/component/cus/MfCusFamilyInfo_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfCusFamilyInfo mfCusFamilyInfo = new MfCusFamilyInfo();
		mfCusFamilyInfo.setId(id);
		mfCusFamilyInfoFeign.delete(mfCusFamilyInfo);
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
		FormData formcusfam00002 = formService.getFormData("cusfam00002");
		getFormValue(formcusfam00002);
		boolean validateFlag = this.validateFormData(formcusfam00002);
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
		FormData formcusfam00002 = formService.getFormData("cusfam00002");
		getFormValue(formcusfam00002);
		boolean validateFlag = this.validateFormData(formcusfam00002);
	}

	/**
	 * 
	 * 方法描述：
	 * 
	 * @throws Exception
	 *             void
	 * @author 沈浩兵
	 * @date 2016-6-1 下午5:59:45
	 */
	private void getTableData(String cusNo, String relNo, String tableId, Map<String, Object> dataMap)
			throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		MfCusFamilyInfo mfCusFamilyInfo = new MfCusFamilyInfo();
		mfCusFamilyInfo.setCusNo(cusNo);
		mfCusFamilyInfo.setRelNo(relNo);
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("mfCusFamilyInfo", mfCusFamilyInfo));
		String tableHtml = jtu.getJsonStr(tableId, "tableTag",
				(List<MfCusFamilyInfo>) mfCusFamilyInfoFeign.findByPage(ipage).getResult(), null, true);
		dataMap.put("tableData", tableHtml);
	}

	// 列表展示详情，单字段编辑
	@RequestMapping(value = "/listShowDetailAjax")
	@ResponseBody
	public Map<String, Object> listShowDetailAjax(String cusNo, String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
				"MfCusFamilyInfoAction");
		String formId = "";
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getShowModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusFamilyInfoAction表单信息没有查询到");
		} else {
			Map<String, Object> formData = new HashMap<String, Object>();
			request.setAttribute("ifBizManger", "3");
			JsonFormUtil jsonFormUtil = new JsonFormUtil();
			FormData formcusfam00003 = formService.getFormData(formId);
			MfCusFamilyInfo mfCusFamilyInfo = new MfCusFamilyInfo();
			mfCusFamilyInfo.setId(id);
			mfCusFamilyInfo = mfCusFamilyInfoFeign.getById(mfCusFamilyInfo);
			getObjValue(formcusfam00003, mfCusFamilyInfo, formData);
			String htmlStrCorp = jsonFormUtil.getJsonStr(formcusfam00003, "propertySeeTag", "");
			if (mfCusFamilyInfo != null) {
				dataMap.put("formHtml", htmlStrCorp);
				dataMap.put("flag", "success");
			} else {
				dataMap.put("msg", "获取详情失败");
				dataMap.put("flag", "error");
			}
			dataMap.put("formData", mfCusFamilyInfo);
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
	public Map<String, Object> updateByOneAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusFamilyInfo mfCusFamilyInfo = new MfCusFamilyInfo();
		Map map = getMapByJson(ajaxData);
		String formId = (String) map.get("formId");
		FormData formcusfam00003 = formService.getFormData(formId);
		getFormValue(formcusfam00003, getMapByJson(ajaxData));
		MfCusFamilyInfo mfCusFamilyInfoNew = new MfCusFamilyInfo();
		setObjValue(formcusfam00003, mfCusFamilyInfoNew);
		mfCusFamilyInfo.setId(mfCusFamilyInfoNew.getId());
		mfCusFamilyInfo = mfCusFamilyInfoFeign.getById(mfCusFamilyInfo);
		if (mfCusFamilyInfo != null) {
			try {
				mfCusFamilyInfo = (MfCusFamilyInfo) EntityUtil.reflectionSetVal(mfCusFamilyInfo, mfCusFamilyInfoNew,
						getMapByJson(ajaxData));
				mfCusFamilyInfoFeign.update(mfCusFamilyInfo);
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
	 * 
	 * <p>Title: findListByPage</p>  
	 * <p>Description:电核名称的数据源 </p>  
	 * @param pageNo
	 * @param pageSize
	 * @param cusNo
	 * @return
	 * @throws Exception  
	 * @author zkq  
	 * @date 2018年8月22日 下午4:44:21
	 */
	@RequestMapping(value = "/findListByPage")
	@ResponseBody
	public Map<String, Object> findListByPage(int pageNo,String cusNo,String pactId) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusFamilyInfo mfCusFamilyInfo = new MfCusFamilyInfo();
		
		MfCusCreditContract   cusCreditContract  = new   MfCusCreditContract();
		cusCreditContract.setPactId(pactId);
		MfCusCreditContract contract = mfCusCreditContractFeign.getById(cusCreditContract);
		if(contract != null){
			pactId = contract.getCreditAppId();
		}
		try {
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			mfCusFamilyInfo.setCusNo(cusNo);
			mfCusFamilyInfo.setRelative("1");
			mfCusFamilyInfo.setCreditAppId(pactId);
			ipage.setParams(this.setIpageParams("mfCusFamilyInfo",mfCusFamilyInfo ));
			ipage = mfCusFamilyInfoFeign.findListByPage(ipage);
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
	 * AJAX获取查看
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjaxZm")
	@ResponseBody
	public Map<String, Object> getByIdAjaxZm(String id) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusFamilyInfo mfCusFamilyInfo = new MfCusFamilyInfo();
		mfCusFamilyInfo.setId(id);
		mfCusFamilyInfo = mfCusFamilyInfoFeign.getById(mfCusFamilyInfo);
		if (mfCusFamilyInfo != null) {
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", mfCusFamilyInfo);
		return dataMap;
	}

	/**
	 * 查询插入的社会关系是不是已存在的客户
	 * @param idNum
	 * @return
	 */
	@RequestMapping(value = "/getByIdNum")
	@ResponseBody
	public Map<String,Object> getByIdNum(String idNum) throws Exception{
		Map<String ,Object> dataMap = new HashMap<>();
        MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setIdNum(idNum);
		try {
			List<MfCusCustomer> mfCusCustomerTem = mfCusCustomerFeign.getByIdNum(mfCusCustomer);
			if(mfCusCustomerTem.size()>0){//存在
				MfCusPersBaseInfo mfCusPersBaseInfo = new MfCusPersBaseInfo();
				mfCusPersBaseInfo.setCusNo(mfCusCustomerTem.get(0).getCusNo());
				mfCusPersBaseInfo = mfCusPersBaseInfoFeign.getById(mfCusPersBaseInfo);
				//查找工作单位
				MfCusPersonJob mfCusPersonJob = new MfCusPersonJob();
				mfCusPersonJob.setCusNo(mfCusCustomerTem.get(0).getCusNo());
				List<MfCusPersonJob> mfCusPersonJobList = mfCusPersonJobFeign.findMfCusPersonJobByPage(mfCusPersonJob);
				if(mfCusPersonJobList.size()>0){
					dataMap.put("personJob",mfCusPersonJobList.get(0).getWorkUnit());
				}else{
					dataMap.put("personJob","");
				}
				dataMap.put("flag", "success");
				dataMap.put("customer",mfCusPersBaseInfo);
			}else{//不存在
				dataMap.put("flag", "success");
				dataMap.put("customer","0");
			}
		}catch (Exception e){
			dataMap.put("flag", "error");
             throw e;
		}



		return dataMap;
	}
}

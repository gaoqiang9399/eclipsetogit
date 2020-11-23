package app.component.pledge.controller;

import app.base.User;
import app.component.assure.entity.MfAssureInfo;
import app.component.collateral.entity.EvalInfo;
import app.component.collateral.entity.MfCollateralClass;
import app.component.collateral.entity.MfCollateralFormConfig;
import app.component.collateral.entity.PledgeBaseInfo;
import app.component.collateral.feign.MfCollateralFormConfigFeign;
import app.component.collateral.feign.PledgeBaseInfoFeign;
import app.component.collateral.feign.MfBusCollateralRelFeign;
import app.component.collateral.feign.MfCollateralTableFeign;
import app.component.collateral.feign.MfCollateralClassFeign;
import app.component.collateralinterface.CollateralInterfaceFeign;
import app.component.common.ApplyEnum;
import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusType;
import app.component.cus.feign.MfCusCorpBaseInfoFeign;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.component.cus.feign.MfCusPersBaseInfoFeign;
import app.component.cusinterface.CusInterfaceFeign;
import app.component.nmd.entity.ParmDic;
import app.component.pledge.entity.MfHighGuaranteeContract;
import app.component.pledge.entity.MfHighGuaranteeContractSub;
import app.component.pledge.feign.MfHighGuaranteeContractFeign;
import app.component.pledge.feign.MfHighGuaranteeContractSubFeign;
import app.component.prdct.entity.MfKindForm;
import app.component.prdctinterface.PrdctInterfaceFeign;
import app.component.wkf.feign.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.tech.upload.FeignSpringFormEncoder;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;
import com.core.domain.screen.FormData;
import com.core.domain.screen.OptionsList;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
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
 * Title: MfHighGuaranteeContractAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu Jun 21 09:31:02 CST 2018
 **/
@Controller
@RequestMapping("/mfHighGuaranteeContract")
public class MfHighGuaranteeContractController extends BaseFormBean {
	private static final long serialVersionUID = 9196454891709523438L;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfHighGuaranteeContractFeign mfHighGuaranteeContractFeign;
	@Autowired
	private CusInterfaceFeign cusInterfaceFeign;
	@Autowired
	private CollateralInterfaceFeign collateralInterfaceFeign;
	@Autowired
	private MfHighGuaranteeContractSubFeign mfHighGuaranteeContractSubFeign;
	@Autowired
	private PledgeBaseInfoFeign pledgeBaseInfoFeign;
	@Autowired
	private MfCollateralFormConfigFeign mfCollateralFormConfigFeign;
	@Autowired
	private MfCollateralTableFeign mfCollateralTableFeign;
	@Autowired
	private MfCusPersBaseInfoFeign mfCusPersBaseInfoFeign;
	@Autowired
	private MfCusFormConfigFeign mfCusFormConfigFeign;
	@Autowired
	private MfCusCorpBaseInfoFeign mfCusCorpBaseInfoFeign;
	@Autowired
	private PrdctInterfaceFeign prdctInterfaceFeign;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;
	@Autowired
	private MfBusCollateralRelFeign mfBusCollateralRelFeign;
	@Autowired
	private MfCollateralClassFeign mfCollateralClassFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		JSONArray highGrtTypeJsonArray = new CodeUtils().getJSONArrayByKeyName("HIGH_GRT_CONTRACT_TYPE");//合同类型
		model.addAttribute("highGrtTypeJsonArray", highGrtTypeJsonArray);
		JSONArray highGrtStsJsonArray = new CodeUtils().getJSONArrayByKeyName("HIGH_GRT_CONTRACT_STS");//合同状态
		model.addAttribute("highGrtStsJsonArray", highGrtStsJsonArray);
		return "/component/pledge/MfHighGuaranteeContract_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Ipage ipage,Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object>  dataMap = new HashMap<String, Object>();
		MfHighGuaranteeContract mfHighGuaranteeContract = new MfHighGuaranteeContract();
		try {
			mfHighGuaranteeContract.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfHighGuaranteeContract.setCriteriaList(mfHighGuaranteeContract, ajaxData);// 我的筛选
			mfHighGuaranteeContract.setCustomSorts(ajaxData);//自定义排序
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfHighGuaranteeContract", mfHighGuaranteeContract));
			// 自定义查询Bo方法
			ipage = mfHighGuaranteeContractFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
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
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormService formService = new FormService();
			FormData formhighGrtContractInput = formService.getFormData("highGrtContractInput");
			getFormValue(formhighGrtContractInput, getMapByJson(ajaxData));
			if (this.validateFormData(formhighGrtContractInput)) {
				MfHighGuaranteeContract mfHighGuaranteeContract = new MfHighGuaranteeContract();
				setObjValue(formhighGrtContractInput, mfHighGuaranteeContract);
				//检查showId是否已存在
				MfHighGuaranteeContract mfHighGuaranteeContract1 = new MfHighGuaranteeContract();
				mfHighGuaranteeContract1.setShowId(mfHighGuaranteeContract.getShowId());
				List<MfHighGuaranteeContract> list = mfHighGuaranteeContractFeign.findList(mfHighGuaranteeContract1);
				if(list.size()>0){
					dataMap.put("flag", "error");
					dataMap.put("msg", "展示合同号["+mfHighGuaranteeContract1.getShowId()+"]已存在，请重新输入");
				}else{
					mfHighGuaranteeContract=mfHighGuaranteeContractFeign.insert(mfHighGuaranteeContract);
					dataMap.put("highGrtContractId",mfHighGuaranteeContract.getHighGrtContractId());
					dataMap.put("msg", "最高额担保合同保存成功");
					dataMap.put("flag", "success");
				}
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw e;
		}
		return dataMap;
	}
	@RequestMapping(value = "/getViewPoint")
	public String getViewPoint(Model model, String highGrtContractId, String activityType) throws Exception {
		FormService formService = new FormService();
		FormData formhighGrtContractDetail = formService.getFormData("highGrtContractApprove");
		getFormValue(formhighGrtContractDetail);
		MfHighGuaranteeContract mfHighGuaranteeContract = new MfHighGuaranteeContract();
		mfHighGuaranteeContract.setHighGrtContractId(highGrtContractId);
		mfHighGuaranteeContract = mfHighGuaranteeContractFeign.getById(mfHighGuaranteeContract);
		getObjValue(formhighGrtContractDetail, mfHighGuaranteeContract);
		// 获得审批节点信息
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(highGrtContractId, null);
		// 处理审批意见类型
		List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType,
				taskAppro.getCouldRollback(), null);
		this.changeFormProperty(formhighGrtContractDetail, "opinionType", "optionArray", opinionTypeList);
		model.addAttribute("formhighGrtContractDetail",formhighGrtContractDetail);
		model.addAttribute("mfHighGuaranteeContract",mfHighGuaranteeContract);
		model.addAttribute("highGrtContractId",highGrtContractId);
		model.addAttribute("query","");
		return "/component/pledge/MfHighGuaranteeContract_ViewPoint";
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
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		FormData formhighGrtContractDetail = formService.getFormData("highGrtContractDetail");
		getFormValue(formhighGrtContractDetail, getMapByJson(ajaxData));
		MfHighGuaranteeContract mfHighGuaranteeContractJsp = new MfHighGuaranteeContract();
		setObjValue(formhighGrtContractDetail, mfHighGuaranteeContractJsp);
		MfHighGuaranteeContract mfHighGuaranteeContract = mfHighGuaranteeContractFeign.getById(mfHighGuaranteeContractJsp);
		if (mfHighGuaranteeContract != null) {
			try {
				mfHighGuaranteeContract = (MfHighGuaranteeContract) EntityUtil.reflectionSetVal(mfHighGuaranteeContract,
						mfHighGuaranteeContractJsp, getMapByJson(ajaxData));
				//检查showId是否已存在
				MfHighGuaranteeContract mfHighGuaranteeContract1 = new MfHighGuaranteeContract();
				mfHighGuaranteeContract1.setShowId(mfHighGuaranteeContract.getShowId());
				List<MfHighGuaranteeContract> list = mfHighGuaranteeContractFeign.findList(mfHighGuaranteeContract1);
				if(list.size()>0){
					mfHighGuaranteeContract1 = list.get(0);
					if(!mfHighGuaranteeContract.getHighGrtContractId().equals(mfHighGuaranteeContract1.getHighGrtContractId())){
						dataMap.put("flag", "error");
						dataMap.put("msg", "展示合同号["+mfHighGuaranteeContract1.getShowId()+"]已存在，请重新输入");
						return dataMap;
					}
				}
				mfHighGuaranteeContractFeign.update(mfHighGuaranteeContract);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			} catch (Exception e) {
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
	 * Ajax异步删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String highGrtContractId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfHighGuaranteeContract mfHighGuaranteeContract = new MfHighGuaranteeContract();
		mfHighGuaranteeContract.setHighGrtContractId(highGrtContractId);
		try {
			Map<String,Object> checkResult = mfHighGuaranteeContractFeign.delete(mfHighGuaranteeContract);
			if("0000".equals(checkResult.get("code"))){
				dataMap.put("flag", "success");
				dataMap.put("msg", "成功");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg", checkResult.get("msg"));
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw e;
		}
		return dataMap;
	}

	/**
	 * Ajax提交
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/commitAjax")
	@ResponseBody
	public Map<String, Object> commitAjax(String highGrtContractId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfHighGuaranteeContract mfHighGuaranteeContract = new MfHighGuaranteeContract();
		mfHighGuaranteeContract.setHighGrtContractId(highGrtContractId);
		try {
			mfHighGuaranteeContract = mfHighGuaranteeContractFeign.commit(mfHighGuaranteeContract);
            dataMap.put("flag", "success");
            dataMap.put("msg", "提交成功");
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
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
	public String input(Model model,String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formhighGrtContractInput = formService.getFormData("highGrtContractInput");
		if(StringUtil.isNotEmpty(cusNo)){//如果客户编号不为空，则查询客户信息，并初始赋值
			MfCusCustomer mfCusCustomer = new MfCusCustomer();
			mfCusCustomer.setCusNo(cusNo);
			mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
			if(mfCusCustomer!=null){
				MfHighGuaranteeContract mfHighGuaranteeContract = new MfHighGuaranteeContract();
				mfHighGuaranteeContract.setCusNo(mfCusCustomer.getCusNo());
				mfHighGuaranteeContract.setCusName(mfCusCustomer.getCusName());
				getObjValue(formhighGrtContractInput,mfHighGuaranteeContract);
			}
		}
		model.addAttribute("formhighGrtContractInput",formhighGrtContractInput);
		model.addAttribute("query","");
		return "/component/pledge/MfHighGuaranteeContract_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model,String highGrtContractId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formhighGrtContractDetail = formService.getFormData("highGrtContractDetail");
		getFormValue(formhighGrtContractDetail);
		MfHighGuaranteeContract mfHighGuaranteeContract = new MfHighGuaranteeContract();
		mfHighGuaranteeContract.setHighGrtContractId(highGrtContractId);
		mfHighGuaranteeContract = mfHighGuaranteeContractFeign.getById(mfHighGuaranteeContract);
		getObjValue(formhighGrtContractDetail, mfHighGuaranteeContract);
		model.addAttribute("formhighGrtContractDetail",formhighGrtContractDetail);
		String type = mfHighGuaranteeContract.getType();
		Map<String, String> map = new CodeUtils().getMapByKeyName("HIGH_GRT_CONTRACT_TYPE");
		mfHighGuaranteeContract.setTypeName(map.get(type));
		String contractSts = mfHighGuaranteeContract.getContractSts();
		Map<String, String> map2 = new CodeUtils().getMapByKeyName("HIGH_GRT_CONTRACT_STS");
		mfHighGuaranteeContract.setContractStsName(map2.get(contractSts));
		model.addAttribute("mfHighGuaranteeContract",mfHighGuaranteeContract);
		//未提交、审批中，可以编辑
		if("1".equals(mfHighGuaranteeContract.getContractSts())){
			request.setAttribute("ifBizManger", "3");
			model.addAttribute("query","");
		}else{
			model.addAttribute("query","query");
		}
		return "/component/pledge/MfHighGuaranteeContract_Detail";
	}

	/**
	 * 查询
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
    @ResponseBody
	public Map<String,Object> getByIdAjax(Model model,String highGrtContractId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String,Object> dataMap = Maps.newHashMap();
		MfHighGuaranteeContract mfHighGuaranteeContract = new MfHighGuaranteeContract();
		mfHighGuaranteeContract.setHighGrtContractId(highGrtContractId);
		mfHighGuaranteeContract = mfHighGuaranteeContractFeign.getById(mfHighGuaranteeContract);
		Map<String, String> dicMap = new CodeUtils().getMapByKeyName("HIGH_GRT_CONTRACT_TYPE");
		mfHighGuaranteeContract.setTypeName(dicMap.get(mfHighGuaranteeContract.getType()));
		dataMap.put("flag","success");
		dataMap.put("highGrtContract",mfHighGuaranteeContract);
		return dataMap;
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
		FormData formhighGrtContractInput = formService.getFormData("highGrtContractInput");
		getFormValue(formhighGrtContractInput);
		boolean validateFlag = this.validateFormData(formhighGrtContractInput);
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
		FormData formhighGrtContractInput = formService.getFormData("highGrtContractInput");
		getFormValue(formhighGrtContractInput);
		boolean validateFlag = this.validateFormData(formhighGrtContractInput);
	}

	/**
	 *
	 * 方法描述： 获得最高额合同明细记录
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author haoyang
	 * @date 2018-11-6
	 */
	@RequestMapping(value = "/getSubListAjax")
	@ResponseBody
	public Map<String, Object> getSubListAjax(String highGrtContractId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String tableHtml = "";
		MfHighGuaranteeContract mfHighGuaranteeContract = new MfHighGuaranteeContract();
		mfHighGuaranteeContract.setHighGrtContractId(highGrtContractId);
		//根据ID查最高额担保合同
		mfHighGuaranteeContract = mfHighGuaranteeContractFeign.getById(mfHighGuaranteeContract);
		if(mfHighGuaranteeContract!=null){
			MfHighGuaranteeContractSub mfHighGuaranteeContractSub = new MfHighGuaranteeContractSub();
			mfHighGuaranteeContractSub.setHighGrtContractId(highGrtContractId);
			mfHighGuaranteeContractSub.setRelType("2");
			JsonTableUtil jtu = new JsonTableUtil();
			String tableId = "tablehighGrtContractSubList_pledge";//默认显示 抵质押列表
			if("1".equals(mfHighGuaranteeContract.getType())){//担保合同
				tableId = "tablehighGrtContractSubList_assure";//担保人列表
				mfHighGuaranteeContractSub.setRelType("1");
			}
			List<Map<String,Object>> list = mfHighGuaranteeContractSubFeign.getSubList(mfHighGuaranteeContractSub);
			tableHtml = jtu.getJsonStr(tableId, "tableTag", list, null, true);
			dataMap.put("tableData", tableHtml);
		}else{
			dataMap.put("flag","error");
			dataMap.put("msg","未找到最高额担保合同信息");
		}
		return dataMap;
	}

	/**
	 *
	 * 方法描述： 押品新增页面
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author haoyang
	 * @param highGrtContractId
	 * @date 2017-4-28 上午11:51:59
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/insertSub")
	public String insertSub(Model model, String highGrtContractId) throws Exception {
		ActionContext.initialize(request, response);
		MfHighGuaranteeContract mfHighGuaranteeContract = new MfHighGuaranteeContract();
		mfHighGuaranteeContract.setHighGrtContractId(highGrtContractId);
		mfHighGuaranteeContract = mfHighGuaranteeContractFeign.getById(mfHighGuaranteeContract);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String entrFlag = "highGrtContract";
		String entrance = "credit";
		if(mfHighGuaranteeContract!=null){
			String cusNo = mfHighGuaranteeContract.getCusNo();
			Gson gson = new Gson();
			FormService formService = new FormService();
			String formId = null;
			JSONObject json = new JSONObject();
			//查询客户信息
			MfCusCustomer mfCusCustomer = cusInterfaceFeign.getCusByCusNo(cusNo);
			dataMap.put("cusNo", mfCusCustomer.getCusNo());
			dataMap.put("cusName", mfCusCustomer.getCusName());
			FormData formdlpledgebaseinfo0004 = null;
			String vouType = "2";
			if("1".equals(mfHighGuaranteeContract.getType())){//担保合同
				vouType = "2";//保证
			}else if("2".equals(mfHighGuaranteeContract.getType())){//抵押合同
				vouType = "3";//抵押
			}else if("3".equals(mfHighGuaranteeContract.getType())){//质押合同
				vouType = "4";//质押
			}else {
			}
			model.addAttribute("vouType", vouType);
			if (BizPubParm.VOU_TYPE_2.equals(vouType)) {// 如果保证方式，担保登记时默认展示保证信息登记表单
				String[] vouTypeArray = new String[]{vouType};
				initAssureInfoForm(dataMap, vouTypeArray);
				formdlpledgebaseinfo0004 = (FormData) dataMap.get("formdlpledgebaseinfo0004");
				// 保证方式为企业法人担保
				JSONArray cusRelComArray = new JSONArray();
				// 保证方式为自然人担保
				JSONArray cusRelPersArray = new JSONArray();
				List<ParmDic> cusPersRelList = (List<ParmDic>) new CodeUtils().getCacheByKeyName("CUS_PERS_REL");
				JSONArray cusPersRelArray = JSONArray.fromObject(cusPersRelList);
				for (int i = 0; i < cusPersRelArray.size(); i++) {
					JSONObject cusRelJSONObject = new JSONObject();
					cusRelJSONObject.put("id", cusPersRelArray.getJSONObject(i).getString("optCode"));
					cusRelJSONObject.put("name", cusPersRelArray.getJSONObject(i).getString("optName"));
					String remark = cusPersRelArray.getJSONObject(i).getString("remark");
					if ("1".equals(remark)) {// 1-企业法人担保
						cusRelComArray.add(cusRelJSONObject);
					} else {// 自然人担保
						cusRelPersArray.add(cusRelJSONObject);
					}
				}
				if (cusRelComArray.size() > 0) {// 杭州微溪(为企业法人担保时，和保证人的关系，下拉框改成：法人，股东，其他)
					json.put("cusRelComType", cusRelComArray);
					dataMap.put("changeCusRelFlag", "1");
				} else {
					json.put("cusRelComType", cusRelPersArray);
					dataMap.put("changeCusRelFlag", "0");
				}
				json.put("cusRelPersType", cusRelPersArray);
			} else {// 如果不是保证担保方式，担保登记时默认展示押品登记表单
				String brNo = User.getOrgNo(request);
				String opNo = User.getRegNo(request);
				dataMap = mfBusCollateralRelFeign.getCollateralFormInfo(cusNo, "", entrFlag, entrance,vouType,brNo,opNo);
				json.put("collClass", dataMap.get("collClass"));
				// 获得押品动态表单
				MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign.getByPledgeImPawnType(dataMap.get("classId").toString(), "PledgeBaseInfoAction", "");
				if (mfCollateralFormConfig != null) {
					formId = mfCollateralFormConfig.getAddModelDef();
					String classId = dataMap.get("classId").toString();
					model.addAttribute("classId", classId);
					formdlpledgebaseinfo0004 = formService.getFormData(formId);
					if (formdlpledgebaseinfo0004.getFormId() == null) {
						formdlpledgebaseinfo0004 = formService.getFormData(mfCollateralFormConfig.getAddModel());
					}
					getFormValue(formdlpledgebaseinfo0004);
					getObjValue(formdlpledgebaseinfo0004, dataMap);
				}

				String vouTypeListS = gson.toJson(dataMap.get("vouTypeList"));
				List<OptionsList> vouTypeListL = (List<OptionsList>) gson.fromJson(vouTypeListS, List.class);
				List<OptionsList> vouTypeList =new ArrayList<OptionsList>();
				for (int i = 0; i < vouTypeListL.size(); i++) {
					String eS = gson.toJson(vouTypeListL.get(i));
					eS = eS.replaceAll("optionLabel", "optionlabel");
					OptionsList e = gson.fromJson(eS, OptionsList.class);
					if(vouType.equals(e.getOptionValue())){
						vouTypeList.add(e);
					}
				}

				this.changeFormProperty(formdlpledgebaseinfo0004, "pledgeMethod", "optionArray", vouTypeList);
				this.changeFormProperty(formdlpledgebaseinfo0004, "assetProperty", "initValue", "2");//业务过程中新增的默认就是客户押品
				dataMap.remove("vouTypeList");
			}
			String ajaxData = json.toString();
			model.addAttribute("highGrtContractId",highGrtContractId);
			model.addAttribute("highGrtContractType",mfHighGuaranteeContract.getType());
			model.addAttribute("dataMap", dataMap);
			model.addAttribute("ajaxData", ajaxData);
			model.addAttribute("formdlpledgebaseinfo0004", formdlpledgebaseinfo0004);
			model.addAttribute("formId", formId);
			model.addAttribute("cusNo", cusNo);
			model.addAttribute("entrance", entrance);
			model.addAttribute("query", "");
			model.addAttribute("classFirstNo", "A,B,C,D");
		}
		return "/component/pledge/MfHighGuaranteeContract_addSub";
	}

	/**
	 *
	 * 方法描述： 最高额担保合同押品信息保存
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author haoyang
	 * @date 2018-11-7
	 */
	@RequestMapping("/insertSubAjax")
	@ResponseBody
	public Map<String, Object> insertSubAjax(String ajaxData, String highGrtContractId,String type,String isQuote,String entrFlag,String collateralType) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			if(StringUtil.isEmpty(collateralType)){
				collateralType = "pledge";
			}
			dataMap = getMapByJson(ajaxData);
			dataMap.put("highGrtContractId",highGrtContractId);
			dataMap.put("highGrtContractType",type);
			if("1".equals(type)){
				//担保合同：保存保证人等信息
				String formId = (String)dataMap.get("formId");
				if(StringUtil.isBlank(formId)){
					formId = "assure0001";
				}
				FormData formassure0001 = formService.getFormData(formId);
				getFormValue(formassure0001, getMapByJson(ajaxData));
				if (this.validateFormDataAnchor(formassure0001)) {
					MfAssureInfo mfAssureInfo = new MfAssureInfo();
					setObjValue(formassure0001, mfAssureInfo);
					dataMap.put("isQuote", isQuote);
					dataMap.put("entrFlag", entrFlag);
					new FeignSpringFormEncoder().addParamsToBaseDomain(mfAssureInfo);
					dataMap.put("mfAssureInfo", mfAssureInfo);
					// 判断是否是担保公司担保
					MfCusType mfCusType = new MfCusType();
					mfCusType.setTypeNo(mfAssureInfo.getAssureCusType());
					mfCusType = cusInterfaceFeign.getMfCusTypeByTypeNo(mfCusType);
					dataMap = mfHighGuaranteeContractSubFeign.insertSub(dataMap);
					Map<String,Object> mfAssureInfoMap = (Map<String,Object>)dataMap.get("mfAssureInfo");
					dataMap.put("pledgeNo", mfAssureInfoMap.get("id"));
					dataMap.put("pledgeName", mfAssureInfoMap.get("assureName"));
					dataMap.put("flag", "success");
					dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
				}else {
					dataMap.put("flag", "error");
					dataMap.put("msg", this.getFormulavaliErrorMsg());
				}
			}else{//抵质押合同
					String classId = String.valueOf(dataMap.get("classId"));
					String pledgeShowNo = String.valueOf(dataMap.get("pledgeShowNo"));
					// 检查押品展示号重复
					PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
					pledgeBaseInfo.setPledgeShowNo(pledgeShowNo);
					pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
					if (pledgeBaseInfo != null && !"1".equals(isQuote)) {
						dataMap.put("flag", "error");
						dataMap.put("msg", MessageEnum.ERROR_PLEDGESHOWNO_REPETITION.getMessage());
						return dataMap;
					}
					MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign.getByPledgeImPawnType(classId,"PledgeBaseInfoAction", "");
					String formId = "";
					if (mfCollateralFormConfig != null) {
						formId = mfCollateralFormConfig.getAddModelDef();
					}
					FormData formdlcollateralbase0004 = formService.getFormData(formId);
					if (formdlcollateralbase0004.getFormId() == null) {
						formId = mfCollateralFormConfig.getAddModel();
						formdlcollateralbase0004 = formService.getFormData(formId);
					}
					getFormValue(formdlcollateralbase0004, getMapByJson(ajaxData));
					if (this.validateFormDataAnchor(formdlcollateralbase0004) ) {
						pledgeBaseInfo = new PledgeBaseInfo();
						setObjValue(formdlcollateralbase0004, pledgeBaseInfo);
						MfCollateralClass mfCollateralClass = new MfCollateralClass();
						mfCollateralClass.setClassId(classId);
						mfCollateralClass = mfCollateralClassFeign.getById(mfCollateralClass);
						pledgeBaseInfo.setClassFirstNo(mfCollateralClass.getClassFirstNo());
						pledgeBaseInfo.setClassSecondName(mfCollateralClass.getClassSecondName());
						dataMap.put("isQuote", isQuote);
						dataMap.put("entrFlag", entrFlag);
						dataMap.put("collateralType", collateralType);
						EvalInfo evalInfo = new EvalInfo();
						setObjValue(formdlcollateralbase0004, evalInfo);
						if (evalInfo.getEvalDate() != null && !"".equals(evalInfo.getEvalDate())) {
							dataMap.put("evalInfo", evalInfo);
						}
						String opNo = User.getRegNo(request);
						dataMap.put("pledgeBaseInfo", pledgeBaseInfo);
						dataMap.put("opNo", opNo);
						dataMap = mfHighGuaranteeContractSubFeign.insertSub(dataMap);
						dataMap.put("flag", "success");
						dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
					} else {
						dataMap.put("flag", "error");
						dataMap.put("msg", this.getFormulavaliErrorMsg());
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
	 * Ajax异步删除
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteSubRelAjax")
	@ResponseBody
	public Map<String, Object> deleteSubRelAjax(String subId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String,Object> checkResult = mfHighGuaranteeContractSubFeign.deleteSubRel(subId);
			return checkResult;
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw e;
		}
	}

	/**
	 * Ajax异步删除
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/refreshHighGrtContract")
	@ResponseBody
	public Map<String, Object> refreshHighGrtContract(String highGrtContractId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfHighGuaranteeContract mfHighGuaranteeContract = new MfHighGuaranteeContract();
			mfHighGuaranteeContract.setHighGrtContractId(highGrtContractId);
			mfHighGuaranteeContract = mfHighGuaranteeContractFeign.refreshHighGrtContract(mfHighGuaranteeContract);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
			dataMap.put("mfHighGuaranteeContract",mfHighGuaranteeContract);
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw e;
		}
		return dataMap;
	}
	private void initAssureInfoForm(Map<String, Object> dataMap, String[] vouTypeArray) throws Exception {
		FormService formService = new FormService();
		MfAssureInfo mfAssureInfo = new MfAssureInfo();
		mfAssureInfo.setCusNo(StringUtil.KillBlankAndTrim((String) dataMap.get("cusNo"), ""));
		mfAssureInfo.setCusName(StringUtil.KillBlankAndTrim((String) dataMap.get("cusName"), ""));

		MfKindForm mfKindForm  = prdctInterfaceFeign.getInitKindForm("base", "assureInfoAdd", ApplyEnum.BUDGET_DEFFLAG_TYPE.DEFFLAG1.getCode());
		String formId="";
		if(mfKindForm!=null){
			formId = mfKindForm.getAddModel();
		}
		FormData formdlpledgebaseinfo0004 = formService.getFormData(formId);
		getObjValue(formdlpledgebaseinfo0004, mfAssureInfo);
		List<OptionsList> vouTypeList = new ArrayList<OptionsList>();
		Map<String, String> dicMap = new CodeUtils().getMapByKeyName("VOU_TYPE");
		OptionsList op = new OptionsList();
		op.setOptionLabel(dicMap.get(BizPubParm.VOU_TYPE_2));
		op.setOptionValue(BizPubParm.VOU_TYPE_2);
		vouTypeList.add(op);
		for (int i = 0; i < vouTypeArray.length; i++) {
			if (!BizPubParm.VOU_TYPE_1.equals(vouTypeArray[i])) {
				if (BizPubParm.VOU_TYPE_2.equals(vouTypeArray[i])) {
					continue;
				}
				op = new OptionsList();
				op.setOptionLabel(dicMap.get(vouTypeArray[i]));
				op.setOptionValue(vouTypeArray[i]);
				vouTypeList.add(op);
			}
		}
		this.changeFormProperty(formdlpledgebaseinfo0004, "pledgeMethod", "optionArray", vouTypeList);
		// 保证方式
		List<ParmDic> assureTypeList = (List<ParmDic>) new CodeUtils().getCacheByKeyName("ASSURE_TYPE");
		if (assureTypeList != null && assureTypeList.size() > 0) {
			this.changeFormProperty(formdlpledgebaseinfo0004, "assureType", "initValue",
					assureTypeList.get(0).getOptCode());
		}
		JsonFormUtil jsonFormUtil = new JsonFormUtil();
		String formHtml = jsonFormUtil.getJsonStr(formdlpledgebaseinfo0004, "bootstarpTag", "");
		dataMap.put("formHtml", formHtml);
		dataMap.put("formdlpledgebaseinfo0004", formdlpledgebaseinfo0004);
		// 客户类型
		List<ParmDic> dicList = (List<ParmDic>) new CodeUtils().getCacheByKeyName("CUS_TYPE");
		String corpCusTypeStr = "";
		String perCusTypeStr = "";
		for (ParmDic parmObj : dicList) {
			//企业法人担保出企业客户和担保公司客户
			if (parmObj.getOptCode().startsWith(BizPubParm.CUS_BASE_TYPE_CORP) || parmObj.getOptCode().startsWith(BizPubParm.CUS_BASE_TYPE_DANBAO)) {
				corpCusTypeStr = corpCusTypeStr + parmObj.getOptCode() + ",";
			} else if(parmObj.getOptCode().startsWith(BizPubParm.CUS_BASE_TYPE_PERSON)){//个人客户
				perCusTypeStr = perCusTypeStr + parmObj.getOptCode() + ",";
			}else {
			}
		}
		if (perCusTypeStr.length() > 1) {
			dataMap.put("perCusTypeStr", perCusTypeStr.substring(0, perCusTypeStr.length() - 1));
		}
		if (corpCusTypeStr.length() > 1) {
			dataMap.put("corpCusTypeStr", corpCusTypeStr.substring(0, corpCusTypeStr.length() - 1));
		}
		// 个人证件类型
		dicList = (List<ParmDic>) new CodeUtils().getCacheByKeyName("PERS_ID_TYPE");
		String perIdTypeStr = "";
		for (ParmDic parmObj : dicList) {
			perIdTypeStr = perIdTypeStr + parmObj.getOptCode() + ",";
		}
		dataMap.put("perIdTypeStr", perIdTypeStr);
		// 企业证件类型
		dicList = (List<ParmDic>) new CodeUtils().getCacheByKeyName("CORP_ID_TYPE");
		String corpIdTypeStr = "";
		for (ParmDic parmObj : dicList) {
			corpIdTypeStr = corpIdTypeStr + parmObj.getOptCode() + ",";
		}
		dataMap.put("perIdTypeStr", perIdTypeStr.substring(0, perIdTypeStr.length() - 1));
		dataMap.put("corpIdTypeStr", corpIdTypeStr.substring(0, corpIdTypeStr.length() - 1));
	}

	/**
	 * 弹层选择 最高额担保合同
	 * @param ajaxData
	 * @param cusBaseType
	 * @param pageNo
	 * @param removeCusId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageForSelectAjax")
	@ResponseBody
	public Map<String, Object> findByPageForSelectAjax(String ajaxData, String cusNo, Integer pageNo) throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfHighGuaranteeContract mfHighGuaranteeContract = new MfHighGuaranteeContract();
		try {
			mfHighGuaranteeContract.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfHighGuaranteeContract.setCustomSorts(ajaxData);
			mfHighGuaranteeContract.setCriteriaList(mfHighGuaranteeContract, ajaxData);// 我的筛选
			mfHighGuaranteeContract.setCusNo(cusNo);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfHighGuaranteeContract",mfHighGuaranteeContract));
			// 自定义查询Feign方法
			ipage = mfHighGuaranteeContractFeign.findByPage(ipage);
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
	 * AJAX新增担保信息（保存 业务-最高额担保合同的关联关系）
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertCollateralAjax")
	@ResponseBody
	public Map<String, Object> insertCollateralAjax(String ajaxData, String appId, String isQuote, String entrFlag,
										  String skipFlag,String extensionApplyId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = getMapByJson(ajaxData);
			String formId = (String)dataMap.get("formId");
			if(StringUtil.isBlank(formId)){
				formId = "highGrtContract0001";
			}
			FormData formhighGrtContract0001 = formService.getFormData(formId);
			getFormValue(formhighGrtContract0001, getMapByJson(ajaxData));
			MfHighGuaranteeContract mfHighGuaranteeContract = new MfHighGuaranteeContract();
			setObjValue(formhighGrtContract0001, mfHighGuaranteeContract);
			mfHighGuaranteeContract = mfHighGuaranteeContractFeign.getById(mfHighGuaranteeContract);
			dataMap.put("appId", appId);
			dataMap.put("isQuote", isQuote);
			dataMap.put("entrFlag", entrFlag);
			dataMap.put("extensionApplyId", extensionApplyId);
			new FeignSpringFormEncoder().addParamsToBaseDomain(mfHighGuaranteeContract);
			dataMap.put("mfHighGuaranteeContract", mfHighGuaranteeContract);

			Map<String,Object> resultMap = mfHighGuaranteeContractFeign.insertCollateral(dataMap);
			if("success".equals(resultMap.get("flag").toString())){
				JSONObject jsonObject = JSONObject.fromObject(resultMap.get("mfHighGuaranteeContract"));
				mfHighGuaranteeContract = (MfHighGuaranteeContract)JSONObject.toBean(jsonObject,MfHighGuaranteeContract.class);

				dataMap.put("pledgeNo", mfHighGuaranteeContract.getHighGrtContractId());
				dataMap.put("pledgeName", mfHighGuaranteeContract.getName());
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			}else{
				dataMap.put("flag","error");
				dataMap.put("msg",resultMap.get("msg"));
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SAVE.getMessage());
			throw e;
		}
		return dataMap;
	}
}

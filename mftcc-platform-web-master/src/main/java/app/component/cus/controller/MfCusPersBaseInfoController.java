package  app.component.cus.controller;

import app.component.common.EntityUtil;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusPersBaseInfo;
import app.component.cus.entity.MfCusPersonJob;
import app.component.cus.entity.MfCusTable;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.entity.MfCusPersonDebtInfo;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusTableFeign;
import app.component.cus.feign.MfCusPersBaseInfoFeign;
import app.component.cus.feign.MfCusPersonDebtInfoFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.component.cus.feign.MfCusPersonJobFeign;
import app.component.nmd.entity.NmdArea;
import app.component.nmd.entity.ParmDic;
import app.component.nmd.feign.NmdAreaFeign;
import app.component.nmd.feign.ParmDicFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.StringUtil;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.google.gson.Gson;
import config.YmlConfig;
import org.apache.commons.beanutils.PropertyUtils;
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
 * Title: MfCusPersBaseInfoAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Mon May 30 08:58:15 CST 2016
 **/
@Controller
@RequestMapping("/mfCusPersBaseInfo")
public class MfCusPersBaseInfoController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	//注入MfCusPersBaseInfoBo
	@Autowired
	private MfCusPersBaseInfoFeign mfCusPersBaseInfoFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	@Autowired
	private MfCusFormConfigFeign mfCusFormConfigFeign;
	@Autowired
	private MfCusTableFeign mfCusTableFeign;
	@Autowired
	private ParmDicFeign parmDicFeign;
	@Autowired
	private YmlConfig ymlConfig;
	@Autowired
	private NmdAreaFeign nmdAreaFeign;
	@Autowired
	private MfCusPersonJobFeign mfCusPersonJobFeign;
	//全局变量
	private String query = "";//初始化query为空
	//异步参数
	//表单变量
	@Autowired
	private MfCusPersonDebtInfoFeign    mfCusPersonDebtInfoFeign;
	
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		return "/component/cus/MfCusPersBaseInfo_List";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusPersBaseInfo mfCusPersBaseInfo = new MfCusPersBaseInfo();
		try {
			mfCusPersBaseInfo.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfCusPersBaseInfo.setCriteriaList(mfCusPersBaseInfo, ajaxData);//我的筛选
			//this.getRoleConditions(mfCusPersBaseInfo,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfCusPersBaseInfo", mfCusPersBaseInfo));
			ipage = mfCusPersBaseInfoFeign.findByPage(ipage);
			/*JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);*/
			dataMap.put("ipage",ipage);
			/**
			 	ipage.setResult(tableHtml);
				dataMap.put("ipage",ipage);
				需要改进的方法
				dataMap.put("tableData",tableHtml);
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
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
			Map<String, Object> map = getMapByJson(ajaxData);
			String formId = (String)map.get("formId");
			if(StringUtil.isEmpty(formId)){
				formId = mfCusFormConfigFeign.getByCusType("base", "MfCusPersBaseInfoAction").getAddModel();
			}
		FormData 	formcusper00002 = formService.getFormData(formId);
			getFormValue(formcusper00002, getMapByJson(ajaxData));
			if(this.validateFormData(formcusper00002)){
		MfCusPersBaseInfo mfCusPersBaseInfo = new MfCusPersBaseInfo();
				setObjValue(formcusper00002, mfCusPersBaseInfo);
				MfCusPersBaseInfo mfCusPersBaseInfoTmp = new MfCusPersBaseInfo();
				mfCusPersBaseInfoTmp= mfCusPersBaseInfoFeign.getById(mfCusPersBaseInfo);
				if(mfCusPersBaseInfoTmp == null){
					mfCusPersBaseInfoFeign.insert(mfCusPersBaseInfo);
				}else{
					mfCusPersBaseInfoFeign.update(mfCusPersBaseInfo);
				}
				dataMap.put("commAddress",mfCusPersBaseInfo.getCommAddress());
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.ERROR_INSERT.getMessage());
			throw e;
		}
		return dataMap;
	}
	/**
	 * ajax 异步 单个字段或多个字段更新
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map map = getMapByJson(ajaxData);
		String cusNo = (String) map.get("cusNo");
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		String defendperiod=mfCusCustomer.getDefendperiod();
		String formId = (String) map.get("formId");
		FormData formcusper00002 = formService.getFormData(formId);
		getFormValue(formcusper00002, getMapByJson(ajaxData));
		MfCusPersBaseInfo mfCusPersBaseInfoJsp = new MfCusPersBaseInfo();
		setObjValue(formcusper00002, mfCusPersBaseInfoJsp);
		setObjValue(formcusper00002, mfCusCustomer);
		MfCusPersBaseInfo mfCusPersBaseInfo = mfCusPersBaseInfoFeign.getById(mfCusPersBaseInfoJsp);
		if(mfCusPersBaseInfo!=null){
			try{
				mfCusPersBaseInfo = (MfCusPersBaseInfo)EntityUtil.reflectionSetVal(mfCusPersBaseInfo, mfCusPersBaseInfoJsp, getMapByJson(ajaxData));
				PropertyUtils.copyProperties(mfCusCustomer, mfCusPersBaseInfo);
				mfCusCustomer.setContactsTel(mfCusPersBaseInfo.getCusTel());
				mfCusPersBaseInfoFeign.update(mfCusPersBaseInfo);
				mfCusCustomer.setDefendperiod(defendperiod);
				mfCusCustomerFeign.update(mfCusCustomer);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			}catch(Exception e){
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg",MessageEnum.ERROR_INSERT.getMessage());
				throw e;
			}
		}else{
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_SAVE_CONTENT.getMessage("编号不存在"));
		}
		return dataMap;
	}
	/**
	 * AJAX更新保存
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusPersBaseInfo mfCusPersBaseInfo = new MfCusPersBaseInfo();
		try{
			Map map = getMapByJson(ajaxData);
			String formId = (String)map.get("formId");
			if(StringUtil.isEmpty(formId)){
				formId = mfCusFormConfigFeign.getByCusType("base", "MfCusPersBaseInfoAction").getAddModel();
			}
		FormData 	formcusper00002 = formService.getFormData(formId);
			getFormValue(formcusper00002, getMapByJson(ajaxData));
			if(this.validateFormData(formcusper00002)){
				setObjValue(formcusper00002, mfCusPersBaseInfo);
				mfCusPersBaseInfo.setRegTime(DateUtil.getDate());
				mfCusPersBaseInfoFeign.update(mfCusPersBaseInfo);
				
				//还有在新增页面的信息没有获取到  需要再get一次
				mfCusPersBaseInfo = mfCusPersBaseInfoFeign.getById(mfCusPersBaseInfo);
				
				MfCusTable mfCusTable = new MfCusTable();
				MfCusCustomer mfCusCustomer = new MfCusCustomer();			
				mfCusCustomer.setCusNo(mfCusPersBaseInfo.getCusNo());
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				setObjValue(formcusper00002, mfCusCustomer);
				mfCusCustomer.setContactsTel(mfCusPersBaseInfo.getCusTel());
				mfCusCustomer.setClassifyType("3");
				mfCusCustomerFeign.update(mfCusCustomer);

				mfCusTable.setCusNo(mfCusPersBaseInfo.getCusNo());
				mfCusTable.setCusType(mfCusCustomer.getCusType());
				mfCusTable.setDataFullFlag("1");
				mfCusTable.setTableName("mf_cus_pers_base_info");
				mfCusTableFeign.update(mfCusTable);
				String infIntegrity = mfCusCustomerFeign.updateInfIntegrity(mfCusCustomer.getCusNo(),mfCusCustomer.getCusNo());//更新客户信息完整度
				//获得基本信息的展示表单ID，并将表单解析
//				formId = mfCusTableFeign.getByCusNo(mfCusCorpBaseInfo.getCusNo(), "MfCusCorpBaseInfoAction").getShowModelDef();
				MfCusFormConfig  mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusPersBaseInfoAction");				
				if(mfCusFormConfig == null){
					
				}else{
					formId = mfCusFormConfig.getShowModelDef();
				}
		FormData formcusper00003 = formService.getFormData(formId);
				if(formcusper00003.getFormId() == null){
					//log.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusPersBaseInfoAction表单form"+formId+".xml文件不存在");
				}
				getFormValue(formcusper00003);
				getObjValue(formcusper00003, mfCusCustomer);
				getObjValue(formcusper00003, mfCusPersBaseInfo);

				this.getHttpRequest().setAttribute("ifBizManger", "3");
				JsonFormUtil jsonFormUtil = new JsonFormUtil();
				String htmlStr = jsonFormUtil.getJsonStr(formcusper00003,"propertySeeTag",query);

				dataMap.put("htmlStr", htmlStr);
				dataMap.put("commAddress", mfCusPersBaseInfo.getCommAddress());
				dataMap.put("cusTel", mfCusPersBaseInfo.getCusTel());
				dataMap.put("idNum", mfCusPersBaseInfo.getIdNum());
				dataMap.put("cusName", mfCusPersBaseInfo.getCusName());
				dataMap.put("htmlStrFlag","1");	
				dataMap.put("mfCusCustomer", mfCusCustomer);
				dataMap.put("flag", "success");
				dataMap.put("infIntegrity",infIntegrity);
				dataMap.put("msg",MessageEnum.SUCCEED_UPDATE.getMessage());
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}
	


	/**
	 * AJAX获取查看
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String cusNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formcusper00002 = formService.getFormData("cusper00002");
		MfCusPersBaseInfo mfCusPersBaseInfo = new MfCusPersBaseInfo();
		mfCusPersBaseInfo.setCusNo(cusNo);
		mfCusPersBaseInfo = mfCusPersBaseInfoFeign.getById(mfCusPersBaseInfo);
		getObjValue(formcusper00002, mfCusPersBaseInfo,formData);
        //查找工作单位
		MfCusPersonJob mfCusPersonJob = new MfCusPersonJob();
		mfCusPersonJob.setCusNo(cusNo);
		List<MfCusPersonJob> mfCusPersonJobList = mfCusPersonJobFeign.findMfCusPersonJobByPage(mfCusPersonJob);
		if(mfCusPersonJobList.size()>0){
			dataMap.put("personJob",mfCusPersonJobList.get(0).getWorkUnit());
		}else{
			dataMap.put("personJob","");
		}
		dataMap.put("mfCusPersBaseInfo", mfCusPersBaseInfo);
		if(mfCusPersBaseInfo!=null){
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}
	/**
	 * Ajax异步删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String cusNo) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusPersBaseInfo mfCusPersBaseInfo = new MfCusPersBaseInfo();
		mfCusPersBaseInfo.setCusNo(cusNo);
		try {
			mfCusPersBaseInfoFeign.delete(mfCusPersBaseInfo);
			dataMap.put("flag", "success");
			dataMap.put("msg",MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.ERROR.getMessage());
			throw e;
		}
		return dataMap;
	}
	@RequestMapping(value = "/identityCheck")
	@ResponseBody
	public Map<String, Object> identityCheck(String idNum,String cusName,String thirdFlag,String cusNo) throws Exception{
		 ActionContext.initialize(request, response);
		 Map<String,Object> dataMap = new HashMap<String, Object>(); 
		 dataMap = mfCusPersBaseInfoFeign.identityCheck(idNum, cusName,thirdFlag,cusNo);
		return dataMap;
	}
	/**
	 * 新增页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model,String cusNo) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData 	formcusper00002 = null;
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		
		MfCusFormConfig  mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusPersBaseInfoAction");
		String formId="";
		String projectName= ymlConfig.getSysParams().get("sys.project.name");
		if(mfCusFormConfig == null){
			
		}else{
			formId = mfCusFormConfig.getAddModelDef();
		}
		if(StringUtil.isEmpty(formId)){
			//			logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusCorpBaseInfoAction表单信息没有查询到");
		}else{
		 	formcusper00002 = formService.getFormData(formId);
			if(formcusper00002.getFormId() == null){
				//			logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusCorpBaseInfoAction表单form"+formId+".xml文件不存在");
			}else{
		        MfCusPersBaseInfo mfCusPersBaseInfo = new MfCusPersBaseInfo();
				mfCusPersBaseInfo.setCusNo(cusNo);
				mfCusPersBaseInfo = mfCusPersBaseInfoFeign.getById(mfCusPersBaseInfo);
				mfCusPersBaseInfo.setCusMngName(mfCusCustomer.getCusMngName());
				//			logger.error("个人客户基本信息表单展示，信息来源：{},推荐者编号：{}，推荐者名称：{}",mfCusPersBaseInfo.getInfoOffer(),mfCusPersBaseInfo.getRecommenderNo(),mfCusPersBaseInfo.getRecommenderName());


                CodeUtils cu = new CodeUtils();
                Map<String, ParmDic> cusTypeMapObj = cu.getMapObjByKeyName("IS_OPEN_DEBT_CUS_REL");
                if (cusTypeMapObj != null) {
                    ParmDic parmDic = cusTypeMapObj.get("1");
                    if (parmDic != null) {
                        //判断客户是否有负债信息
                        MfCusPersonDebtInfo  mfCusPersonDebtInfo  =  new MfCusPersonDebtInfo();
                        mfCusPersonDebtInfo.setCusNo(cusNo);
                        List<MfCusPersonDebtInfo> mfCusPersonDebtInfos = mfCusPersonDebtInfoFeign.getCusDebtSum(mfCusPersonDebtInfo);
                        if(mfCusPersonDebtInfos != null && mfCusPersonDebtInfos.size() > 0){
                            mfCusPersBaseInfo.setIfHasDebt("1");
                        }else{
                            mfCusPersBaseInfo.setIfHasDebt("0");
                        }
                    }
                }



                getFormValue(formcusper00002);
				getObjValue(formcusper00002, mfCusCustomer);
				getObjValue(formcusper00002, mfCusPersBaseInfo);
			}
		}


		model.addAttribute("projectName", projectName);
		model.addAttribute("formcusper00002", formcusper00002);
		model.addAttribute("query", "");
		return "/component/cus/MfCusPersBaseInfo_Insert";
	}

	/***
	 * 新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData  formcusper00002 = formService.getFormData("cusper00002");
		 getFormValue(formcusper00002);
		MfCusPersBaseInfo  mfCusPersBaseInfo = new MfCusPersBaseInfo();
		 setObjValue(formcusper00002, mfCusPersBaseInfo);
		 mfCusPersBaseInfoFeign.insert(mfCusPersBaseInfo);
		 getObjValue(formcusper00002, mfCusPersBaseInfo);
		 this.addActionMessage(model, "保存成功");
		 Ipage ipage = this.getIpage();
		 ipage.setParams(this.setIpageParams("mfCusPersBaseInfo", mfCusPersBaseInfo)); 
		 List<MfCusPersBaseInfo> mfCusPersBaseInfoList = (List<MfCusPersBaseInfo>)mfCusPersBaseInfoFeign.findByPage(this.getIpage()).getResult();
		model.addAttribute("formcusper00002", formcusper00002);
		model.addAttribute("query", "");
		return "/component/cus/MfCusPersBaseInfo_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String cusNo) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		String formId = "";
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
				"MfCusPersBaseInfoAction");
		if (mfCusFormConfig == null) {
			formId ="cusPersonBase";
		} else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		FormData  formcusper00001 = formService.getFormData(formId);
		 getFormValue(formcusper00001);
		MfCusPersBaseInfo  mfCusPersBaseInfo = new MfCusPersBaseInfo();
		mfCusPersBaseInfo.setCusNo(cusNo);
		 mfCusPersBaseInfo = mfCusPersBaseInfoFeign.getById(mfCusPersBaseInfo);
		 getObjValue(formcusper00001, mfCusPersBaseInfo);
		model.addAttribute("formcusPersonBase", formcusper00001);
		model.addAttribute("query", "");
		return "/component/cus/MfCusPersBaseInfo_Detail";
	}


	/**
	 * 获取是否本地客户
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getIfLocalAjax")
    @ResponseBody
	public Map<String,Object> getIfLocalAjax(Model model, String areaNo) throws Exception {
        Map<String,Object> dataMap = new HashMap<String,Object>();
        NmdArea nmdArea = new NmdArea();
        nmdArea.setAreaNo(areaNo);
        dataMap =  nmdAreaFeign.getIfLocal(nmdArea);
        dataMap.put("flag","success");
		return dataMap;
	}
	/**
	 * 删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String cusNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		MfCusPersBaseInfo mfCusPersBaseInfo = new MfCusPersBaseInfo();
		mfCusPersBaseInfo.setCusNo(cusNo);
		mfCusPersBaseInfoFeign.delete(mfCusPersBaseInfo);
		return getListPage(model);
	}
	/**
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateInsert")
	public void validateInsert(Model model) throws Exception{
		FormService formService = new FormService();
		 ActionContext.initialize(request, response);
		FormData  formcusper00002 = formService.getFormData("cusper00002");
		 getFormValue(formcusper00002);
		 boolean validateFlag = this.validateFormData(formcusper00002);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateUpdate")
	public void validateUpdate(Model model) throws Exception{
		FormService formService = new FormService();
		 ActionContext.initialize(request, response);
		FormData  formcusper00002 = formService.getFormData("cusper00002");
		 getFormValue(formcusper00002);
		 boolean validateFlag = this.validateFormData(formcusper00002);
	}
	
	
	@RequestMapping(value = "/getCusPersBaseInfoAjax")
	@ResponseBody
	public Map<String, Object> getCusPersBaseInfoAjax(String cusNo) throws Exception{
		 ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try {
			dataMap = mfCusPersBaseInfoFeign.getCusPersInfo(cusNo);
			dataMap.put("flag", "success");
			dataMap.put("msg",MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.ERROR.getMessage());
			throw e;
		}
	return dataMap;
	}
	/**
	 * 贷款类别细类选择组件
	 * 
	 * @param model
	 * @param type 集团内、外客户
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCategories")
	public String getCategories(Model model,String type) throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		ParmDic parmDic = new ParmDic();
		parmDic.setKeyName("CATEGORIED");
		List<ParmDic> list = new ArrayList<ParmDic>();
		list = parmDicFeign.findlist(parmDic);
		/*if(type.equals("1")){//集团外客户
			for(int i =5;i<list.size();i++){
				dataMap.put("CATEGORIED"+i, list.get(i));
			}
		}else{
			for(int i =0;i<5;i++){
				dataMap.put("CATEGORIED"+i, list.get(i));
			}
		}*/
		List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
		if("1".equals(type)){//集团外客户
			for (int i =4;i<list.size();i++) {
				Map<String, String> data = new HashMap<String, String>();
				data.put("id", list.get(i).getOptCode());
				data.put("name",list.get(i).getOptName());
				dataList.add(data);
			}
		}else{
			for(int i =0;i<4;i++){
				Map<String, String> data = new HashMap<String, String>();
				data.put("id", list.get(i).getOptCode());
				data.put("name",list.get(i).getOptName());
				dataList.add(data);
			}
		}
		model.addAttribute("ajaxData", new Gson().toJson(dataList));
		model.addAttribute("showAllFlag", "1");
		model.addAttribute("query", "");
		model.addAttribute("isStepLoading", "false");

		return "/component/nmd/IndustryAndArea";
	}
	
}

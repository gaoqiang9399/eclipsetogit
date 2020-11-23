package  app.component.cus.controller;
import app.common.AreaZip;
import app.component.common.EntityUtil;
import app.component.cus.cusgroup.entity.MfCusGroup;
import app.component.cus.cusgroup.feign.MfCusGroupFeign;
import app.component.cus.entity.MfCusCorpBaseInfo;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.entity.MfCusTable;
import app.component.cus.feign.MfCusCorpBaseInfoFeign;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.component.cus.feign.MfCusTableFeign;
import app.component.cus.relation.entity.Child;
import app.component.nmd.entity.ParmDic;
import app.component.nmd.feign.ParmDicFeign;
import app.util.JsonStrHandling;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.StringUtil;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;
import com.google.gson.Gson;
import config.YmlConfig;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
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
 * Title: MfCusCorpBaseInfoAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Sat May 21 15:25:30 CST 2016
 **/
@Controller
@RequestMapping("/mfCusCorpBaseInfo")
public class MfCusCorpBaseInfoController extends BaseFormBean{
	@Autowired  
	private HttpServletRequest request;
	@Autowired  
	private HttpServletResponse response;
	@Autowired 
	private MfCusCorpBaseInfoFeign mfCusCorpBaseInfoFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	@Autowired
	private MfCusFormConfigFeign mfCusFormConfigFeign;
	@Autowired
	private MfCusTableFeign mfCusTableFeign;
	@Autowired
	private MfCusGroupFeign mfCusGroupFeign;
	@Autowired
	private ParmDicFeign parmDicFeign;
	@Autowired
	private YmlConfig ymlConfig;
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request,response);
		return "/component/cus/MfCusCorpBaseInfo_List";
	}
	
	/**
	 * 方法描述：选择客户法人身份证弹窗 
	 * @return MfCusCorpBaseInfo_ListForSelectIdNum
	 * @throws Exception
	 * String
	 * @author lcl
	 * @date 2017-1-18 上午11:47:57
	 */
	@RequestMapping(value = "/getListPageForSelectIdNum")
	public String getListPageForSelectIdNum(Model model) throws Exception {
		ActionContext.initialize(request,response);
		return "/component/cus/MfCusCorpBaseInfo_ListForSelectIdNum";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String,Object> findByPageAjax(String ajaxData,String legalIdType,int pageNo,String tableId,String tableType) throws Exception {
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>();  
		MfCusCorpBaseInfo  mfCusCorpBaseInfo = new MfCusCorpBaseInfo();
		try {
			mfCusCorpBaseInfo.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfCusCorpBaseInfo.setCriteriaList(mfCusCorpBaseInfo, ajaxData);//我的筛选
			//this.getRoleConditions(mfCusCorpBaseInfo,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			if(legalIdType == null ||"".equals(legalIdType)){	
				
			}else{
				mfCusCorpBaseInfo.setLegalIdType(legalIdType);				
			}
			//自定义查询Feign方法
			ipage.setParams(this.setIpageParams("mfCusCorpBaseInfo", mfCusCorpBaseInfo));
			ipage = mfCusCorpBaseInfoFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
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
	public Map<String,Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		String query="";
		Map<String,Object> dataMap = new HashMap<String, Object>();  
		try{//新增时使用00002表单，纵向，每行2个字段，显示时使用00004表单，纵向，每行4个字段
			Map map = getMapByJson(ajaxData);
			String formId = (String)map.get("formId");
			if(StringUtil.isEmpty(formId)){
				formId = mfCusFormConfigFeign.getByCusType("base", "MfCusCorpBaseInfoAction").getAddModel();
			}
			FormData formcuscorp00002 = formService.getFormData(formId);
			if(formcuscorp00002.getFormId() == null){
//				logger.error("MfCusCorpBaseInfoAction表单的form"+formId+".xml文件不存在");
			}
			getFormValue(formcuscorp00002, map);
			if(this.validateFormDataAnchor(formcuscorp00002)){
				MfCusCorpBaseInfo mfCusCorpBaseInfo = new MfCusCorpBaseInfo();
				setObjValue(formcuscorp00002, mfCusCorpBaseInfo);//表单数据放到基本信息对象里面
				MfCusCorpBaseInfo mfCusCorpBaseInfoTmp = new MfCusCorpBaseInfo();
				mfCusCorpBaseInfoTmp = mfCusCorpBaseInfoFeign.getById(mfCusCorpBaseInfo);
				if(mfCusCorpBaseInfoTmp==null){
					mfCusCorpBaseInfo = mfCusCorpBaseInfoFeign.insert(mfCusCorpBaseInfo);
				}else{
					mfCusCorpBaseInfo = mfCusCorpBaseInfoFeign.update(mfCusCorpBaseInfo);
				}
				
				// 结束日期显示处理
				String endDate = mfCusCorpBaseInfoFeign.dealEndDate(mfCusCorpBaseInfo.getEndDate(),"2");
				mfCusCorpBaseInfo.setEndDate(endDate);
				
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(mfCusCorpBaseInfo.getCusNo());
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				
				
				this.getHttpRequest().setAttribute("ifBizManger", "3");
				//获得基本信息的展示表单ID，并将表单解析
//				formId = mfCusTableFeign.getByCusNo(mfCusCorpBaseInfo.getCusNo(), "MfCusCorpBaseInfoAction").getShowModelDef();
				MfCusFormConfig  mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusCorpBaseInfoAction");				
				if(mfCusFormConfig == null){
					
				}else{
					formId = mfCusFormConfig.getShowModelDef();
				}
				FormData formcuscorp00004 = formService.getFormData(formId);
				if(formcuscorp00004.getFormId() == null){
//					logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusCorpBaseInfoAction表单form"+formId+".xml文件不存在");
				}
				getFormValue(formcuscorp00004);
				getObjValue(formcuscorp00004, mfCusCorpBaseInfo);
				JsonFormUtil jsonFormUtil = new JsonFormUtil();
				String htmlStr = jsonFormUtil.getJsonStr(formcuscorp00004,"propertySeeTag",query);
				dataMap.put("htmlStr", htmlStr);
				dataMap.put("htmlStrFlag","1");
				
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
	public Map<String,Object> updateAjaxByOne(String ajaxData) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		Map<String,Object> parmMap = getMapByJson(ajaxData);
		String cusNo = (String) parmMap.get("cusNo");
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		Map map = getMapByJson(ajaxData);
		String formId = (String) map.get("formId");		
		
		FormData formcuscorp00004 = formService.getFormData(formId);
		getFormValue(formcuscorp00004, parmMap);
		MfCusCorpBaseInfo mfCusCorpBaseInfoJsp = new MfCusCorpBaseInfo();
		setObjValue(formcuscorp00004, mfCusCorpBaseInfoJsp);
		MfCusCorpBaseInfo mfCusCorpBaseInfo = mfCusCorpBaseInfoFeign.getById(mfCusCorpBaseInfoJsp);
		if(mfCusCorpBaseInfo!=null){
			try{
				mfCusCorpBaseInfo = (MfCusCorpBaseInfo)EntityUtil.reflectionSetVal(mfCusCorpBaseInfo, mfCusCorpBaseInfoJsp, getMapByJson(ajaxData));
				
				if(parmMap.containsKey("wayClass")
						||parmMap.containsKey("assetSum")
						||parmMap.containsKey("bussIncome")
						||parmMap.containsKey("empCnt")
						){
					//如果有修改到行业分类，资产总额，营业收入，从业人数中的其中一项就需要修改企业规模
					String assetSum = String.valueOf(mfCusCorpBaseInfo.getAssetSum());
					assetSum = "null".equals(assetSum) ? "" : assetSum;
					String bussIncome = String.valueOf(mfCusCorpBaseInfo.getBussIncome());
					bussIncome = "null".equals(bussIncome) ? "" : bussIncome;
					String empCnt = String.valueOf(mfCusCorpBaseInfo.getEmpCnt());
					empCnt = "null".equals(empCnt) ? "" : empCnt;
					Map<String,Object> resMap=mfCusCorpBaseInfoFeign.getMatchProjectSize(mfCusCorpBaseInfo.getWayClass(), assetSum, bussIncome, empCnt);
					mfCusCorpBaseInfo.setProjSize(resMap.get("proSizeNo").toString());
					
				}
				
				mfCusCorpBaseInfoFeign.update(mfCusCorpBaseInfo);
				mfCusCustomer = new MfCusCustomer();
				mfCusCustomer = (MfCusCustomer)EntityUtil.reflectionSetVal(mfCusCustomer, mfCusCorpBaseInfoJsp, getMapByJson(ajaxData));
				if(mfCusCustomer!=null){
					mfCusCustomerFeign.update(mfCusCustomer);
				}
			
				
//				//当更改公司从业人数时，同步更新员工信息和公司概况员工人数。
//				if(mapData.containsKey("empCnt")){
//					mfCusStaff = new MfCusStaff();
//					mfCusStaff.setCusNo(mfCusCorpBaseInfo.getCusNo());
//					mfCusStaff = mfCusStaffFeign.getById(mfCusStaff);
//					if(mfCusStaff!=null){
//						mfCusStaff.setStaffSum(mfCusCorpBaseInfo.getEmpCnt());
//						mfCusStaffFeign.update(mfCusStaff);
//					}
//					
//					mfCusCorpState = new MfCusCorpState();
//					mfCusCorpState.setCusNo(mfCusCorpBaseInfo.getCusNo());
//					mfCusCorpState = mfCusCorpStateFeign.getById(mfCusCorpState);
//					if(mfCusCorpState!=null){
//						mfCusCorpState.setEmployeeNum(mfCusCorpBaseInfo.getEmpCnt());
//						mfCusCorpStateFeign.update(mfCusCorpState);
//					}
//				}
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
	public Map<String,Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>();  
		String query="";
		try{
			Map map = getMapByJson(ajaxData);
			String formId = (String)map.get("formId");
			if(StringUtil.isEmpty(formId)){
				formId = mfCusFormConfigFeign.getByCusType("base", "MfCusCorpBaseInfoAction").getAddModel();
			}
			FormData formcuscorp00002 = formService.getFormData(formId);
			getFormValue(formcuscorp00002, map);
			if(this.validateFormDataAnchor(formcuscorp00002)){
				MfCusCorpBaseInfo mfCusCorpBaseInfo = new MfCusCorpBaseInfo();
				setObjValue(formcuscorp00002, mfCusCorpBaseInfo);
				mfCusCorpBaseInfo.setRegTime(DateUtil.getDateTime());
				mfCusCorpBaseInfoFeign.update(mfCusCorpBaseInfo);

				MfCusCustomer upBean = new MfCusCustomer();
				setObjValue(formcuscorp00002, upBean);
				if(upBean != null){
					upBean.setRegTime(null);
					upBean.setRegDate(null);
					mfCusCustomerFeign.update(upBean);
					upBean = null;
				}
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				MfCusCustomer mfCusCustomerTmp = new MfCusCustomer();
				mfCusCustomer.setCusNo(mfCusCorpBaseInfo.getCusNo());
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				if("2".equals(mfCusCorpBaseInfo.getIfGroup())){//判断是否是集团客户，忘集团中加入数据
					MfCusGroup mfCusGroup = new MfCusGroup();
					mfCusGroup.setGroupNo(mfCusCorpBaseInfo.getCusNo());
					mfCusGroup.setGroupName(mfCusCorpBaseInfo.getCusName());
					mfCusGroup.setIdType(mfCusCustomer.getIdType());
					mfCusGroup.setIdNum(mfCusCustomer.getIdNum());
					mfCusGroup.setContactsName(mfCusCorpBaseInfo.getContactsName());
					mfCusGroup.setContactsTel(mfCusCorpBaseInfo.getContactsTel());
					mfCusGroupFeign.insert(mfCusGroup);
					MfCusTable mfCusTable = new MfCusTable();
					mfCusTable.setCusNo(mfCusCustomer.getCusNo());
					mfCusTable.setDataFullFlag("1");
					mfCusTable.setTableName("mf_cus_group");
					mfCusTableFeign.update(mfCusTable);
				}
				MfCusTable mfCusTable = new MfCusTable();
				
				setObjValue(formcuscorp00002, mfCusCustomerTmp);
				Map<String,Object> parmMap = new HashMap<String, Object>(); 
				parmMap.put("mfCusCustomerTmp", mfCusCustomerTmp);
				parmMap.put("mfCusCustomer", mfCusCustomer);
				//mfCusCustomer = mfCusCustomerFeign.updateCustomter(parmMap);
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				//将邮政编码传到页面
				mfCusCustomer.setPostalCode(mfCusCustomerTmp.getPostalCode());
				mfCusTable.setCusNo(mfCusCorpBaseInfo.getCusNo());
				mfCusTable.setCusType(mfCusCustomer.getCusType());
				mfCusTable.setDataFullFlag("1");
				mfCusTable.setTableName("mf_cus_corp_base_info");
				mfCusTableFeign.update(mfCusTable);
				String infIntegrity = mfCusCustomerFeign.updateInfIntegrity(mfCusCorpBaseInfo.getCusNo(),mfCusCorpBaseInfo.getCusNo());
				//获得基本信息的展示表单ID，并将表单解析
//				formId = mfCusTableFeign.getByCusNo(mfCusCorpBaseInfo.getCusNo(), "MfCusCorpBaseInfoAction").getShowModelDef();
				MfCusFormConfig  mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusCorpBaseInfoAction");				
				if(mfCusFormConfig == null){
					
				}else{
					formId = mfCusFormConfig.getShowModelDef();
				}
				FormData formcuscorp00004 = formService.getFormData(formId);
				if(formcuscorp00004.getFormId() == null){
//					logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusCorpBaseInfoAction表单form"+formId+".xml文件不存在");
				}
				getFormValue(formcuscorp00004);
				Child child = mfCusCorpBaseInfoFeign.getLoanUseById(mfCusCorpBaseInfo);
				if(child != null){
					mfCusCorpBaseInfo.setWayClassName(child.getName());
				}else{
					
				}
				// 结束日期显示处理
				if(StringUtil.isNotBlank(mfCusCorpBaseInfo.getEndDate())){
					String endDate = mfCusCorpBaseInfoFeign.dealEndDate(mfCusCorpBaseInfo.getEndDate(),"2");
					mfCusCorpBaseInfo.setEndDate(endDate);
				}
				
				// 当前客户修改为普通客户
				MfCusCustomer updateMfCusCustomer = new MfCusCustomer();
				updateMfCusCustomer.setCusNo(mfCusCustomer.getCusNo());
				updateMfCusCustomer.setClassifyType("3");
				mfCusCustomerFeign.update(updateMfCusCustomer);
				getObjValue(formcuscorp00004, mfCusCustomer);
				getObjValue(formcuscorp00004, mfCusCorpBaseInfo);

				this.getHttpRequest().setAttribute("ifBizManger", "3");
				JsonFormUtil jsonFormUtil = new JsonFormUtil();
				String htmlStr = jsonFormUtil.getJsonStr(formcuscorp00004,"propertySeeTag",query);
				
				dataMap.put("htmlStr", htmlStr);
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
	 * 方法描述： 基本信息展示表单更新行政区划编号
	 * @return
	 * @throws Exception
	 * String
	 * @author 谢静霞
	 * @date 2017-7-13 下午2:43:10
	 */
	@RequestMapping(value = "/updateCareaAjax")
	@ResponseBody
	public Map<String,Object> updateCareaAjax(String cusNo,String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>();  
		MfCusCorpBaseInfo mfCusCorpBaseInfo = new MfCusCorpBaseInfo();
		try{
			mfCusCorpBaseInfo.setCareaProvice(ajaxData);
			mfCusCorpBaseInfo.setCusNo(cusNo);
			mfCusCorpBaseInfoFeign.update(mfCusCorpBaseInfo);
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}
	
	@RequestMapping(value = "/wayClassSelectPge")
	public String wayClassSelectPge(Model model)throws Exception{
//		wayClassSelectPge.jsp
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		ActionContext.initialize(request,response);
		JSONArray loanUse=mfCusCorpBaseInfoFeign.getLoanUse();
		dataMap=new HashMap<String,Object>();
		dataMap.put("ajaxData", loanUse);
	    model.addAttribute("dataMap", dataMap);
		return "/component/cus/wayClassSelectPge";
	}
	
	/**
	 * AJAX获取查看
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String,Object> getByIdAjax(String cusNo) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String,Object> dataMap = new HashMap<String, Object>();  
		FormData formcuscorp00002 = formService.getFormData("cuscorp00002");
		MfCusCorpBaseInfo mfCusCorpBaseInfo = new MfCusCorpBaseInfo();
		mfCusCorpBaseInfo.setCusNo(cusNo);
		mfCusCorpBaseInfo = mfCusCorpBaseInfoFeign.getById(mfCusCorpBaseInfo);
		getObjValue(formcuscorp00002, mfCusCorpBaseInfo,formData);
		if(mfCusCorpBaseInfo!=null){
			dataMap.put("flag", "success");
			dataMap.put("mfCusCorpBaseInfo", mfCusCorpBaseInfo);
		}else{
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}
	
	
	/**
	 * 方法描述： 
	 * @return
	 * @throws Exception
	 * String
	 * @author lcl
	 * @date 2017-1-11 下午5:28:07
	 */
	@RequestMapping(value = "/getCusByIdAjax")
	@ResponseBody
	public Map<String,Object> getCusByIdAjax(String cusNo) throws Exception {
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		if (mfCusCustomer != null) {
			dataMap.put("flag", "success");
			dataMap.put("cusInfo", mfCusCustomer);
		} else {
			dataMap.put("flag", "error");
		}
		return dataMap;
	}
	/**
	 * Ajax异步删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String,Object> deleteAjax(String cusNo) throws Exception{
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>();  
		MfCusCorpBaseInfo mfCusCorpBaseInfo = new MfCusCorpBaseInfo();
		mfCusCorpBaseInfo.setCusNo(cusNo);
		try {
			mfCusCorpBaseInfoFeign.delete(mfCusCorpBaseInfo);
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
	 * 根据行业分类，资产总额，营业收入，从业人员匹配企业规模    1-大型企业 2-中型企业 3-小型企业 4-微型企业 5-其他
	 * @param wayNo  行业分类
	 * @param assetSum 资产总额
	 * @param bussIncome 营业收入
	 * @param empCnt 从业人员
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getMatchProjectSizeAjax")
	@ResponseBody
	public Map<String, Object> getMatchProjectSizeAjax(String wayNo,String assetSum,String bussIncome,String empCnt)throws Exception{
		Map<String,Object> dataMap=new HashMap<String,Object>();
		dataMap=mfCusCorpBaseInfoFeign.getMatchProjectSize(wayNo, assetSum, bussIncome, empCnt);
		return dataMap;
	}
	/**
	 * 新增页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model,String cusNo) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		String formId="";
		String projectName= ymlConfig.getSysParams().get("sys.project.name");
		MfCusFormConfig  mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusCorpBaseInfoAction");
		if(mfCusFormConfig == null){
			
		}else{
			formId = mfCusFormConfig.getAddModelDef();
		}
		if(StringUtil.isEmpty(formId)){
//			logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusCorpBaseInfoAction表单信息没有查询到");
		}else{
			FormData formcuscorp00002 = formService.getFormData(formId);
			if(formcuscorp00002.getFormId() == null){
//				logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusCorpBaseInfoAction表单form"+formId+".xml文件不存在");
			}else{
				MfCusCorpBaseInfo mfCusCorpBaseInfo = new MfCusCorpBaseInfo();
				mfCusCorpBaseInfo.setCusNo(cusNo);
				mfCusCorpBaseInfo = mfCusCorpBaseInfoFeign.getById(mfCusCorpBaseInfo);
				mfCusCorpBaseInfo.setSetupDate(mfCusCorpBaseInfo.getBeginDate());

				// 结束日期显示处理
//				String endDate = mfCusCorpBaseInfoFeign.dealEndDate(mfCusCorpBaseInfo.getEndDate(),"2");
//				mfCusCorpBaseInfo.setEndDate(endDate);
//				
				//mfCusCorpBaseInfo.setProjSize("1");
				mfCusCorpBaseInfo.setRegisteredType("13");//中通企业客户默认注册类型为企业法人
//				mfCusCorpBaseInfo.setCountryOrigin("CHN");//国别默认中国
				getFormValue(formcuscorp00002);
				getObjValue(formcuscorp00002, mfCusCustomer);
				getObjValue(formcuscorp00002, mfCusCorpBaseInfo);
			}
			model.addAttribute("formcuscorp00002", formcuscorp00002);
			model.addAttribute("cusNo", cusNo);
			model.addAttribute("query", "");
			
			JSONObject json = new JSONObject();
			MfCusGroup mfCusGroup = new MfCusGroup();
			List<MfCusGroup> mfCusGroupListReal = mfCusGroupFeign.getCusGroupListReal(mfCusGroup);
			JSONArray mfCusGroupArray = new JSONArray();
			JSONObject mfCusGroupObj = null;
			for(MfCusGroup mcg:mfCusGroupListReal){
				mfCusGroupObj = new JSONObject();
				mfCusGroupObj.put("id", mcg.getIdNum());
				mfCusGroupObj.put("name", mcg.getGroupName());
				mfCusGroupArray.add(mfCusGroupObj);
			}
			json.put("mfCusGroupArray", mfCusGroupArray);
			String ajaxData = json.toString();
			model.addAttribute("ajaxData", ajaxData);
			
		}
		model.addAttribute("projectName", projectName);
		//政策通道字典项
		/*JSONArray map = new CodeUtils().getJSONArrayByKeyName("POLICY_CHANNEL");
		String items = map.toString().replaceAll("optName", "name").replace("optCode", "id");
		model.addAttribute("policyChannel", items);*/
		return "/component/cus/MfCusCorpBaseInfo_Insert";
	}
	
	
	/***
	 * 新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		 FormData formcuscorp00002 = formService.getFormData("cuscorp00002");
		 getFormValue(formcuscorp00002);
		 MfCusCorpBaseInfo mfCusCorpBaseInfo = new MfCusCorpBaseInfo();
		 setObjValue(formcuscorp00002, mfCusCorpBaseInfo);
		 mfCusCorpBaseInfoFeign.insert(mfCusCorpBaseInfo);
		 getObjValue(formcuscorp00002, mfCusCorpBaseInfo);
		 this.addActionMessage(null, "保存成功");
		 Ipage ipage = this.getIpage();
		 ipage.setParams(this.setIpageParams("mfCusCorpBaseInfo", mfCusCorpBaseInfo));
		 List<MfCusCorpBaseInfo> mfCusCorpBaseInfoList = (List<MfCusCorpBaseInfo>)mfCusCorpBaseInfoFeign.findByPage(ipage).getResult();
		 model.addAttribute("mfCusCorpBaseInfoList", mfCusCorpBaseInfoList);
		 model.addAttribute("formcuscorp00002", formcuscorp00002);
		 model.addAttribute("query", "");
		return "/component/cus/MfCusCorpBaseInfo_Insert";
	}
	
	/**
	 * 获取行业分类的层级关系
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getLoanUseAjax")
	@ResponseBody
	public Map<String, Object>  getLoanUseAjax() throws Exception{
		Map<String,Object> dataMap=new HashMap<String,Object>();
		JSONArray loanUse=mfCusCorpBaseInfoFeign.getLoanUse();
		dataMap=new HashMap<String,Object>();
		dataMap.put("loanUse", loanUse);
		return dataMap;
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model,String cusNo) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		//新增时使用00002表单，纵向，每行2个字段，显示时使用00004表单，纵向，每行4个字段
		String formId="";
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig  mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusCorpBaseInfoAction");
		if(mfCusFormConfig == null){

		}else{
			if("1".equals(mfCusFormConfig.getShowType())){
				formId = mfCusFormConfig.getAddModelDef();
			}else{
				formId = mfCusFormConfig.getShowModelDef();
			}
		}
		if(StringUtil.isEmpty(formId)){
//			logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusCorpBaseInfoAction表单信息没有查询到");
		}else{
			FormData formcuscorp00004 = formService.getFormData(formId);
			if(formcuscorp00004.getFormId() == null){
//				logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusCorpBaseInfoAction表单form"+formId+".xml文件不存在");
			}else{
				getFormValue(formcuscorp00004);
				MfCusCorpBaseInfo mfCusCorpBaseInfo = new MfCusCorpBaseInfo();
				mfCusCorpBaseInfo.setCusNo(cusNo);
				mfCusCorpBaseInfo = mfCusCorpBaseInfoFeign.getById(mfCusCorpBaseInfo);
				getObjValue(formcuscorp00004, mfCusCorpBaseInfo);
			}
			model.addAttribute("formcuscorp00004", formcuscorp00004);
			model.addAttribute("query", "");
		}


		return "/component/cus/MfCusCorpBaseInfo_Detail";
	}
	/**
	 * 删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model,String cusNo) throws Exception {
		ActionContext.initialize(request,response);
		MfCusCorpBaseInfo mfCusCorpBaseInfo = new MfCusCorpBaseInfo();
		mfCusCorpBaseInfo.setCusNo(cusNo);
		mfCusCorpBaseInfoFeign.delete(mfCusCorpBaseInfo);
		return getListPage(model);
	}
	/**
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	public void validateInsert() throws Exception{
		 ActionContext.initialize(request, response);
		 FormService formService = new FormService();
		 FormData formcuscorp00002 = formService.getFormData("cuscorp00002");
		 getFormValue(formcuscorp00002);
		 boolean validateFlag = this.validateFormDataAnchor(formcuscorp00002);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	public void validateUpdate() throws Exception{
		FormService formService = new FormService();
		 ActionContext.initialize(request, response);
		 FormData formcuscorp00002 = formService.getFormData("cuscorp00002");
		 getFormValue(formcuscorp00002);
		 boolean validateFlag = this.validateFormDataAnchor(formcuscorp00002);
	}
	
	@RequestMapping(value = "/checkAjax")
	@ResponseBody
	public Map<String, String> checkAjax(String cusNo,String cusName,String nodeNo) throws Exception{
		 ActionContext.initialize(request, response);
		 Map<String,String> dataMap = new HashMap<>();
		 dataMap = mfCusCorpBaseInfoFeign.checkinfo(cusNo, cusName,nodeNo);
		 //基本信息联网核查单独处理结果
		 if("netWork".equals(nodeNo) && "success".equals(dataMap.get("flag"))){
			 String corpBaseStr = dataMap.get("mfCusCorpBaseInfo");
			 if(StringUtil.isNotEmpty(corpBaseStr)){
				 Map<String,String> map = JSONObject.fromObject(corpBaseStr);
				 dataMap.putAll(map);
			 }
		 }
		if(null!= dataMap.get("commAddress")){
			String zip = AreaZip.getZip(dataMap.get("commAddress"));
			if(StringUtils.isNotEmpty(zip)){
				dataMap.put("postalCode",zip);
			}
		}
		 return dataMap;
	}
	
	@RequestMapping(value = "/getCusListAjax")
	@ResponseBody
	public Map<String,Object> getCusListAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		String query="";
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		Map<String,Object> parmMap = new HashMap<String, Object>();
		MfCusCorpBaseInfo mfCusCorpBaseInfo = new MfCusCorpBaseInfo();

//		String parm = "opSts=1";
		String parm = "";
		parmMap.put("query",query);
		parmMap.put("parm",parm);
		parmMap.put("ajaxData",ajaxData);
		parmMap.put("mfCusCorpBaseInfo",mfCusCorpBaseInfo);
		try {
			JSONArray cusList =  mfCusCorpBaseInfoFeign.getCusListAjax(parmMap);
			dataMap.put("data",cusList);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	
	@RequestMapping(value = "/getLegalPersonAjax")
	@ResponseBody
	public Map<String,Object> getLegalPersonAjax(String ajaxData,int pageNo,String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String,Object> dataMap = new HashMap<String, Object>();  
		MfCusCorpBaseInfo mfCusCorpBaseInfo = new MfCusCorpBaseInfo();
		try {
			mfCusCorpBaseInfo.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfCusCorpBaseInfo.setCriteriaList(mfCusCorpBaseInfo, ajaxData);//我的筛选
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			mfCusCorpBaseInfo.setCusNo(cusNo);				
			//自定义查询Feign方法
			ipage.setParams(this.setIpageParams("mfCusCorpBaseInfo", mfCusCorpBaseInfo));
			ipage = mfCusCorpBaseInfoFeign.getLegalPerson(ipage);
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
		List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
		if("1".equals(type)){//集团外客户
			for (int i =4;i<list.size();i++) {
				Map<String, String> data = new HashMap<String, String>();
				data.put("id", list.get(i).getOptCode());
				data.put("name",list.get(i).getOptName());
				dataList.add(data);
			}
		}else{//集团内客户
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

	/**
	 * 列表打开页面请求（客户简称、监管方式）
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPageForShort")
	public String getListPageForShort(Model model) throws Exception {
		ActionContext.initialize(request, response);
		return "/hm/cus/MfCusCorpBaseInfo_List";
	}

	/**
	 * 列表数据查询（客户简称、监管方式）
	 *
	 * @param ajaxData
	 * @param pageNo
	 * @param tableId
	 * @param tableType
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageForShortAjax")
	@ResponseBody
	public Map<String, Object> findByPageForShortAjax(Ipage ipage, String ajaxData, int pageNo, String tableId,
			String tableType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCorpBaseInfo mfCusCorpBaseInfo = new MfCusCorpBaseInfo();
		try {
			mfCusCorpBaseInfo.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCusCorpBaseInfo.setCriteriaList(mfCusCorpBaseInfo, ajaxData);// 我的筛选
			mfCusCorpBaseInfo.setCustomSorts(ajaxData);// 自定义排序参数赋值
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Feign方法
			ipage.setParams(this.setIpageParams("mfCusCorpBaseInfo", mfCusCorpBaseInfo));
			ipage = mfCusCorpBaseInfoFeign.findByPageForShort(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			@SuppressWarnings("rawtypes")
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
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
	 * 
	 * @param model
	 * @param cusNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdForShort")
	public String getByIdForShort(Model model, String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();

		MfCusCorpBaseInfo mfCusCorpBaseInfo = new MfCusCorpBaseInfo();
		mfCusCorpBaseInfo.setCusNo(cusNo);
		mfCusCorpBaseInfo = mfCusCorpBaseInfoFeign.getById(mfCusCorpBaseInfo);

		FormData formmfCusCorpBaseInfo0001 = formService.getFormData("mfCusCorpBaseInfo0001");
		getObjValue(formmfCusCorpBaseInfo0001, mfCusCorpBaseInfo);

		model.addAttribute("formmfCusCorpBaseInfo0001", formmfCusCorpBaseInfo0001);
		model.addAttribute("query", "");

		return "/hm/cus/MfCusCorpBaseInfo_Detail";
	}

	/**
	 * AJAX更新保存
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateForShortAjax")
	@ResponseBody
	public Map<String, Object> updateForShortAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		try {
			FormData formmfCusCorpBaseInfo0001 = formService.getFormData("mfCusCorpBaseInfo0001");
			getFormValue(formmfCusCorpBaseInfo0001, getMapByJson(ajaxData));
			if (this.validateFormDataAnchor(formmfCusCorpBaseInfo0001)) {
				MfCusCorpBaseInfo mfCusCorpBaseInfo = new MfCusCorpBaseInfo();
				setObjValue(formmfCusCorpBaseInfo0001, mfCusCorpBaseInfo);

				mfCusCorpBaseInfoFeign.updateForShort(mfCusCorpBaseInfo);

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
	 * 根据企业名称模糊查询获取名称列表
	 * @param cusName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getNameListByCusName")
	@ResponseBody
	public  Map<String, Object> getNameListByCusName(String cusName) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("flag","success");
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("cusName","小米科技有限公司");
		jsonObject.put("idNum","9113040010552154XX");
		jsonArray.add(jsonObject);
		jsonObject = new JSONObject();
		jsonObject.put("cusName","小米科技有限公司22222111111111");
		jsonObject.put("idNum","9113040010552154XK");
		jsonArray.add(jsonObject);
		resultMap.put("nameList",jsonArray);
		return resultMap;
	}
}

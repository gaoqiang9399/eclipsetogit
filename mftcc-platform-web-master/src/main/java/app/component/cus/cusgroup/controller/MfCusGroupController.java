package  app.component.cus.cusgroup.controller;
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

import app.component.auth.entity.MfCusCreditContract;
import app.component.auth.feign.MfCusCreditContractFeign;
import app.component.busviewinterface.BusViewInterfaceFeign;
import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.cus.cusgroup.entity.MfCusGroup;
import app.component.cus.cusgroup.feign.MfCusGroupFeign;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusTable;
import app.component.cus.entity.MfCusType;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusTableFeign;
import app.component.cus.feign.MfCusTypeFeign;
import app.component.rules.entity.NumberBigBean;
import app.component.rulesinterface.RulesInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.MathExtend;
import cn.mftcc.util.WaterIdUtil;
import config.YmlConfig;

/**
 * Title: MfCusGroupAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Mon Dec 05 15:36:35 CST 2016
 **/
@Controller
@RequestMapping("/mfCusGroup")
public class MfCusGroupController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	//注入MfCusGroupBo
	@Autowired
	private MfCusGroupFeign mfCusGroupFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	@Autowired
	private BusViewInterfaceFeign busViewInterfaceFeign;
	@Autowired
	private RulesInterfaceFeign rulesInterfaceFeign;
	@Autowired
	private MfCusTableFeign mfCusTableFeign;
	@Autowired
	private MfCusTypeFeign mfCusTypeFeign;
	@Autowired
	private MfCusCreditContractFeign mfCusCreditContractFeign;
	@Autowired
	private YmlConfig ymlConfig;
	//全局变量
	//异步参数
	//表单变量
	
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */

	
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model,String busEntrance) throws Exception {
		ActionContext.initialize(request,
				response);
		model.addAttribute("query", "");
		String projectName= ymlConfig.getSysParams().get("sys.project.name");
		request.setAttribute("projectName", projectName);
		return "/component/cus/cusgroup/MfCusGroup_List";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */

	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(int pageNo,String tableId, String tableType, String ajaxData) throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusGroup mfCusGroup = new MfCusGroup();
		try {
			mfCusGroup.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfCusGroup.setCriteriaList(mfCusGroup, ajaxData);//我的筛选
			mfCusGroup.setCustomSorts(ajaxData);// 自定义排序
			//this.getRoleConditions(mfCusGroup,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfCusGroup", mfCusGroup));
			ipage = mfCusGroupFeign.findByPage(ipage);
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

	@ResponseBody
	@RequestMapping(value = "/getCusGroupList")
	public Map<String, Object> getPactList(int pageNo, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfCusGroup mfCusGroup = new MfCusGroup();
			mfCusGroup.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfCusGroup.setCriteriaList(mfCusGroup, ajaxData);//我的筛选
			mfCusGroup.setCustomSorts(ajaxData);// 自定义排序

			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfCusGroup", mfCusGroup));
			ipage = mfCusGroupFeign.findByPage(ipage);
			ipage.setParamsStr(ajaxData);
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
		FormData formcusgroup0002 = formService.getFormData("cusgroup0002");
			getFormValue(formcusgroup0002, getMapByJson(ajaxData));
			if(this.validateFormData(formcusgroup0002)){
		MfCusGroup mfCusGroup = new MfCusGroup();
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		MfCusTable mfCusTable = new MfCusTable();
		MfCusType mfCusType = new MfCusType();
		mfCusType.setBaseType(BizPubParm.CUS_BASE_TYPE_GROUP);//集团管理
		mfCusType =mfCusTypeFeign.getByBaseType(mfCusType);
		if(mfCusType==null || "null".equals(mfCusType)){
			dataMap.put("flag", "error");
			dataMap.put("msg","集团客户类型不存在！");
		}else{
			
			setObjValue(formcusgroup0002, mfCusGroup);
			mfCusCustomer.setCusName(mfCusGroup.getGroupName());
			mfCusCustomer.setIdNum(mfCusGroup.getIdNum());
			mfCusCustomer.setIdType("B");
			mfCusCustomer.setCusSubType("2");
			mfCusCustomer.setCusBaseType("1");
			mfCusCustomer.setCusType(mfCusType.getTypeNo());
			mfCusCustomer.setContactsName(mfCusGroup.getContactsName());
			mfCusCustomer.setContactsTel(mfCusGroup.getContactsTel());
			mfCusCustomer = mfCusCustomerFeign.insert1(mfCusCustomer);
			mfCusGroup.setGroupNo(mfCusCustomer.getCusNo());
			mfCusGroupFeign.insert(mfCusGroup);
			mfCusTable.setCusNo(mfCusCustomer.getCusNo());
			mfCusTable.setDataFullFlag("1");
			mfCusTable.setTableName("mf_cus_group");
			mfCusTableFeign.update(mfCusTable);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
		}
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
		FormData formcusgroup0002 = formService.getFormData("cusgroup0002");
		getFormValue(formcusgroup0002, getMapByJson(ajaxData));
		MfCusGroup mfCusGroupJsp = new MfCusGroup();
		setObjValue(formcusgroup0002, mfCusGroupJsp);
		MfCusGroup mfCusGroup = mfCusGroupFeign.getById(mfCusGroupJsp);
		if(mfCusGroup!=null){
			try{
				mfCusGroup = (MfCusGroup)EntityUtil.reflectionSetVal(mfCusGroup, mfCusGroupJsp, getMapByJson(ajaxData));
				mfCusGroupFeign.update(mfCusGroup);
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
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
		FormData 	formcusgroup0002 = formService.getFormData("cusgroup0002");
			getFormValue(formcusgroup0002, getMapByJson(ajaxData));
			if(this.validateFormData(formcusgroup0002)){
				MfCusGroup mfCusGroup = new MfCusGroup();
				setObjValue(formcusgroup0002, mfCusGroup);
				mfCusGroupFeign.update(mfCusGroup);
				dataMap.put("flag", "success");
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
	 * @param groupNo 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String ajaxData, String groupNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formcusgroup0002 = formService.getFormData("cusgroup0002");
		MfCusGroup mfCusGroup = new MfCusGroup();
		mfCusGroup.setGroupNo(groupNo);
		mfCusGroup = mfCusGroupFeign.getById(mfCusGroup);
		getObjValue(formcusgroup0002, mfCusGroup,formData);
		if(mfCusGroup!=null){
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}
	/**
	 * Ajax异步删除
	 * @param groupNo 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String ajaxData, String groupNo) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusGroup mfCusGroup = new MfCusGroup();
		mfCusGroup.setGroupNo(groupNo);
		try {
			mfCusGroupFeign.delete(mfCusGroup);
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
	 * 新增页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData formcusgroup0002 = formService.getFormData("cusgroup0002");
		model.addAttribute("formcusgroup0002", formcusgroup0002);
		model.addAttribute("query", "");
		return "/component/cus/cusgroup/MfCusGroup_Insert";
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
		FormData  formcusgroup0002 = formService.getFormData("cusgroup0002");
		 getFormValue(formcusgroup0002);
		MfCusGroup  mfCusGroup = new MfCusGroup();
		 setObjValue(formcusgroup0002, mfCusGroup);
		 mfCusGroupFeign.insert(mfCusGroup);
		 getObjValue(formcusgroup0002, mfCusGroup);
		 this.addActionMessage(model, "保存成功");
		 List<MfCusGroup> mfCusGroupList = (List<MfCusGroup>)mfCusGroupFeign.findByPage(this.getIpage()).getResult();
		model.addAttribute("formcusgroup0002", formcusgroup0002);
		model.addAttribute("mfCusGroupList", mfCusGroupList);
		model.addAttribute("query", "");
		return "/component/cus/cusgroup/MfCusGroup_Insert";
	}
	/**
	 * 查询
	 * @param groupNo 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String ajaxData, String groupNo) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData  formcusgroup0001 = formService.getFormData("cusgroup0001");
		 getFormValue(formcusgroup0001);
		MfCusGroup  mfCusGroup = new MfCusGroup();
		mfCusGroup.setGroupNo(groupNo);
		 mfCusGroup = mfCusGroupFeign.getById(mfCusGroup);
		 getObjValue(formcusgroup0001, mfCusGroup);
		model.addAttribute("formcusgroup0001", formcusgroup0001);
		model.addAttribute("query", "");
		model.addAttribute("mfCusGroup",mfCusGroup);
		return "/component/cus/cusgroup/MfCusGroup_Detail";
	}
	/**
	 * 集团客户视角<br> 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/cusGroupView")
	public String cusGroupView(Model model, String groupNo,String busEntrance) throws Exception {
		ActionContext.initialize(request, response);
		try {
			String cusNo = groupNo;
			MfCusCustomer mfCusCustomer = new MfCusCustomer();
			mfCusCustomer.setCusNo(cusNo);
			mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);

			String cusSubTypeName = new CodeUtils().getMapByKeyName("CUS_TYPE").get(mfCusCustomer.getCusType());
			String generalClass = "cus";
			//String busClass = mfCusCustomer.getCusBaseType();
			//获取集团客户的授信额度
			MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
			mfCusCreditContract = mfCusCreditContractFeign.getNewestCusCreditContrac(cusNo);
			if(mfCusCreditContract!=null){
				model.addAttribute("creditSum", MathExtend.moneyStr(mfCusCreditContract.getCreditSum()));
				double usedAmt = MathExtend.subtract(mfCusCreditContract.getCreditSum(), mfCusCreditContract.getAuthBal());
				model.addAttribute("usedAmt", MathExtend.moneyStr(usedAmt));
			}
			Map<String, String> parmMap = new HashMap<String, String>();
			//parmMap.put("busClass", busClass);
			parmMap.put("cusType", mfCusCustomer.getCusType());
			parmMap.put("cusNo", cusNo);
			parmMap.put("operable", "operable");// 底部显示待完善信息块
			parmMap.put("cusSubTypeName", cusSubTypeName);
			parmMap.put("docParm", "cusNo=" + cusNo + "&relNo=" + cusNo + "&scNo=" + BizPubParm.SCENCE_TYPE_DOC_CUS+ "&cusType=" + mfCusCustomer.getCusType());
			Map<String, Object> cusViewMap = busViewInterfaceFeign.getCommonViewMap(generalClass, busEntrance, parmMap);

			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("cusNo", cusNo);
			dataMap.putAll(cusViewMap);
			model.addAttribute("dataMap", dataMap);
			model.addAttribute("param", parmMap);
			model.addAttribute("cusNo", cusNo);
			model.addAttribute("cusType", mfCusCustomer.getCusType());
			model.addAttribute("busEntrance", busEntrance);
			model.addAttribute("query", "");
		} catch (Exception e) {
			 //logger.error("获取集团客户详情视角失败", e);
			throw e;
		}
		return "/component/cus/commonview/MfCusCustomer_ComView";
	}
	/**
	 * 检查是否存在集团客户类型
	 * 集团管理
	 * @return
	 */
	@RequestMapping(value = "/checkCusTypeAjax")
	@ResponseBody
	public Map<String,Object> checkCusTypeAjax(String cusNo) throws Exception{
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String,Object>();
		MfCusTable mfCusTable = new MfCusTable();
		MfCusType mfCusType = new MfCusType();
		mfCusType.setBaseType(BizPubParm.CUS_BASE_TYPE_GROUP);//集团管理
		mfCusType =mfCusTypeFeign.getByBaseType(mfCusType);
		if(mfCusType==null || "null".equals(mfCusType)){
			dataMap.put("flag", "error");
			dataMap.put("msg","未配置集团客户类型！");
		}else{
			dataMap.put("flag", "success");
		}
		return dataMap;
	}
	/**
	 * 获取视角所需对象信息（头部最基础的信息）
	 * 集团管理
	 * @return
	 */
	@RequestMapping(value = "/getByIdForViewAjax")
	@ResponseBody
	public Map<String,Object> getByIdForViewAjax(String cusNo) throws Exception{
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String,Object>();
		Map<String,String> cusInfoMap=this.transViewBean(cusNo);
		dataMap.put("cusInfo", cusInfoMap);
		return dataMap;
	}
	/**
	 * 将实体对象转换为主视图页面需要的参数对象
	 * @param 客户号
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/transViewBean")
	@ResponseBody
	public Map<String,String> transViewBean(String cusNo) throws Exception{
		MfCusCustomer mfCusCustomer=new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer=mfCusCustomerFeign.getById(mfCusCustomer);
		Map<String,String> cusInfoMap=new HashMap<String,String>();
		if(mfCusCustomer!=null){
			//名称
			cusInfoMap.put("cusName", mfCusCustomer.getCusName());
			cusInfoMap.put("idNum", mfCusCustomer.getIdNum());
			//基本类型
			cusInfoMap.put("cusBaseType", mfCusCustomer.getCusType().substring(0,1));
			cusInfoMap.put("cusType", mfCusCustomer.getCusType());
			cusInfoMap.put("uId", mfCusCustomer.getCusNo());//业务编号
			//获取客户类型汉字
			MfCusType mfCusType=new MfCusType();
			mfCusType.setTypeNo(mfCusCustomer.getCusType());
			mfCusType=mfCusTypeFeign.getById(mfCusType);
			cusInfoMap.put("cusNameRate", mfCusType!=null?mfCusType.getTypeName():"未知");
			//对接人联系方式
			cusInfoMap.put("contactsTel", mfCusCustomer.getContactsTel());
			//对接人姓名
			cusInfoMap.put("contactsName", mfCusCustomer.getContactsName());
			//资料完整度
			cusInfoMap.put("infIntegrity", mfCusCustomer.getInfIntegrity());
			//是否上传头像图片
			cusInfoMap.put("ifUploadHead", mfCusCustomer.getIfUploadHead());
			//头像图片路径
			cusInfoMap.put("headImg", mfCusCustomer.getHeadImg());
			
		}
		return cusInfoMap;
	}
	@RequestMapping(value = "/getCusGroupAjax")
	@ResponseBody
	public Map<String, Object> getCusGroupAjax(int pageNo, String cusType, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数

			MfCusGroup mfCusGroup = new MfCusGroup();
			if (ajaxData != null) {
				mfCusGroup.setCustomQuery(ajaxData);// 自定义查询参数赋值
			}
			ipage.setParams(this.setIpageParams("mfCusGroup", mfCusGroup));
			ipage = mfCusGroupFeign.getCusGroupList(ipage);

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
	 * 删除
	 * @param groupNo 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String ajaxData, String groupNo,String busEntrance) throws Exception {
		ActionContext.initialize(request,
				response);
		MfCusGroup mfCusGroup = new MfCusGroup();
		mfCusGroup.setGroupNo(groupNo);
		mfCusGroupFeign.delete(mfCusGroup);
		return getListPage(model,busEntrance);
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
		FormData  formcusgroup0002 = formService.getFormData("cusgroup0002");
		 getFormValue(formcusgroup0002);
		 boolean validateFlag = this.validateFormData(formcusgroup0002);
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
		FormData  formcusgroup0002 = formService.getFormData("cusgroup0002");
		 getFormValue(formcusgroup0002);
		 boolean validateFlag = this.validateFormData(formcusgroup0002);
	}
	
}

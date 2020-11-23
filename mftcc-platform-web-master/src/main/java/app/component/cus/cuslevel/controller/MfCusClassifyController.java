package app.component.cus.cuslevel.controller;
import app.base.User;
import app.component.app.entity.MfBusApply;
import app.component.appinterface.AppInterfaceFeign;
import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.cus.cuslevel.entity.MfCusClassify;
import app.component.cus.cuslevel.entity.MfCusClassifyAdjustHistory;
import app.component.cus.cuslevel.feign.MfCusClassifyAdjustHistoryFeign;
import app.component.cus.cuslevel.feign.MfCusClassifyFeign;
import app.component.cus.entity.MfCusCorpBaseInfo;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusPersBaseInfo;
import app.component.cus.feign.MfCusCorpBaseInfoFeign;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusPersBaseInfoFeign;
import app.component.nmd.entity.ParmDic;
import app.component.wkf.entity.Result;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.util.JsonStrHandling;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.MathExtend;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.WaterIdUtil;
import com.core.domain.screen.FormData;
import com.core.domain.screen.OptionsList;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;
import config.YmlConfig;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
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
 * Title: MfCusClassifyAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Fri Jun 24 10:03:33 CST 2016
 **/
@Controller
@RequestMapping("/mfCusClassify")
public class MfCusClassifyController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	//注入MfCusClassifyBo
	@Autowired
	private MfCusClassifyFeign mfCusClassifyFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	@Autowired
	private MfCusCorpBaseInfoFeign mfCusCorpBaseInfoFeign;
	@Autowired
	private AppInterfaceFeign appInterfaceFeign;
	@Autowired
	private MfCusClassifyAdjustHistoryFeign mfCusClassifyAdjustHistoryFeign;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;
	@Autowired
	private YmlConfig ymlConfig;
	@Autowired
	private MfCusPersBaseInfoFeign mfCusPersBaseInfoFeign;

	
	
	/**
	 * 列表打开页面请求
	 * @param cusNo 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model, String cusNo) throws Exception {
		ActionContext.initialize(request,response);
		//客户信息
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		//最新的客户分类
		MfCusClassify mfCusClassify = new MfCusClassify();
		mfCusClassify.setCusNo(cusNo);
		List<MfCusClassify> mfCusClassifyList = mfCusClassifyFeign.getNewByCusNo(mfCusClassify);
	   if(mfCusClassifyList.size() >0){
		   mfCusClassify = mfCusClassifyList.get(0);
	   }else{
		   Map<String,String>  dicMap = new CodeUtils().getMapByKeyName("CLASSIFY_TYPE");
		   mfCusClassify.setRankType(mfCusCustomer.getClassifyType());
		   mfCusClassify.setRankTypeName(dicMap.get(mfCusCustomer.getClassifyType()));
	   }
		mfCusClassifyList = mfCusClassifyFeign.getByCusNo(mfCusClassify);

		String headImg = mfCusCustomer.getHeadImg();
		String cusBaseType = mfCusCustomer.getCusBaseType();
		if("2".equals(cusBaseType)){// 个人客户 获取个人客户基本信息
			MfCusPersBaseInfo mfCusPersBaseInfo = new MfCusPersBaseInfo();
			mfCusPersBaseInfo.setCusNo(cusNo);
			mfCusPersBaseInfo = mfCusPersBaseInfoFeign.getById(mfCusPersBaseInfo);
			if(mfCusPersBaseInfo!=null){
				mfCusCustomer.setPostalCode(mfCusPersBaseInfo.getPostalCode());
			}
		}else {
			MfCusCorpBaseInfo mfCusCorpBaseInfo = new MfCusCorpBaseInfo();
			mfCusCorpBaseInfo.setCusNo(cusNo);
			mfCusCorpBaseInfo = mfCusCorpBaseInfoFeign.getById(mfCusCorpBaseInfo);
			if(mfCusCorpBaseInfo!=null){
				mfCusCustomer.setPostalCode(mfCusCorpBaseInfo.getPostalCode());
			}
		}
		model.addAttribute("ifBizManger", "2");
		model.addAttribute("mfCusCustomer", mfCusCustomer);
		model.addAttribute("headImg", headImg);
		model.addAttribute("mfCusClassifyList", mfCusClassifyList);
		model.addAttribute("mfCusClassify", mfCusClassify);
		model.addAttribute("cusNo", cusNo);
		model.addAttribute("opNo", User.getRegNo(request));
		model.addAttribute("cusMngNo", mfCusCustomer.getCusMngNo());
		model.addAttribute("query", "");
		return "/component/cus/cuslevel/MfCusClassify_List";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusClassify mfCusClassify = new MfCusClassify();
		try {
			mfCusClassify.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfCusClassify.setCriteriaList(mfCusClassify, ajaxData);//我的筛选
			//this.getRoleConditions(mfCusClassify,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage = mfCusClassifyFeign.findByPage(ipage, mfCusClassify);
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
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
		MfCusClassify 	mfCusClassify = new MfCusClassify();
			mfCusClassify=(MfCusClassify)JSONObject.toBean(JSONObject.fromObject(ajaxData),MfCusClassify.class);
			mfCusClassifyFeign.insert(mfCusClassify);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
		 	/*formcusclassify0001 = formService.getFormData("cusclassify0001");
			getFormValue(formcusclassify0001, getMapByJson(ajaxData));
			if(this.validateFormData(formcusclassify0001)){*/
//		MfCusClassify //mfCusClassify = new MfCusClassify();
				//setObjValue(formcusclassify0001, mfCusClassify);
				
				//dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			/*}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}*/
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.ERROR_INSERT.getMessage());
			throw e;
		}
		return dataMap;
	}
	
	/**
	 * @author czk
	 * @Description:新增或更新客户分类
	 * date 2016-12-3
	 */
	@RequestMapping(value = "/insertOrUpdateAjax")
	@ResponseBody
	public Map<String, Object> insertOrUpdateAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
			FormData formcusclassify0001 = formService.getFormData("cusclassify0001");
			getFormValue(formcusclassify0001, getMapByJson(ajaxData));
			MfCusClassify 	mfCusClassify = new MfCusClassify();
			MfCusClassify 	_mfCusClassify = new MfCusClassify();
			setObjValue(formcusclassify0001, mfCusClassify);
//			//客户分类时，检查是否有正在审批的客户分类审批
//			Map<String,Object> map = mfCusClassifyAdjustHistoryFeign.startApproveFlow(mfCusClassify);
//			if("error".equals(map.get("flag"))) {
//				dataMap.put("flag", "error");
//				dataMap.put("msg", map.get("msg"));
//				return dataMap;
//			}
			if("1".equals(mfCusClassify.getClassifyType())){
			//如果修改之后修改为黑名单客户，则走审批流程
				mfCusClassify.setBlockType("0");
				Map<String,Object> returnMap = mfCusClassifyAdjustHistoryFeign.startApproveFlow(mfCusClassify);
				if("error".equals(returnMap.get("flag"))){
					dataMap.put("flag", "error");
					dataMap.put("msg",returnMap.get("msg"));
				}else{
					if("1".equals(returnMap.get("isApprove"))){//如果审批流程开启
						dataMap.put("flag", "approve");
						dataMap.put("msg", MessageEnum.SUCCEED_APPROVAL.getMessage(returnMap));
					}else{
						_mfCusClassify = (MfCusClassify)JsonStrHandling.handlingStrToBean( returnMap.get("mfCusClassify"), MfCusClassify.class);
						dataMap.put("flag", "success");
						dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage());
					}
				}
			}else{
			//如果修改之后不是黑名单客户，则先判断修改前是否时是黑名单
				List<MfCusClassify> list= mfCusClassifyFeign.getNewByCusNo(mfCusClassify);
				if(list!=null && list.size()>0){
					if("1".equals(list.get(0).getClassifyType())){
						mfCusClassify.setBlockType("1");
						//如果修改之前是黑名单，则同样走审批流程
						Map<String,Object> returnMap = mfCusClassifyAdjustHistoryFeign.startApproveFlow(mfCusClassify);
						if("error".equals(returnMap.get("flag"))){
							dataMap.put("flag", "error");
							dataMap.put("msg",returnMap.get("msg"));
						}else{
							if("1".equals(returnMap.get("isApprove"))){//如果审批流程开启
								dataMap.put("flag", "approve");
								dataMap.put("msg", MessageEnum.SUCCEED_APPROVAL.getMessage(returnMap));
							}else{
								_mfCusClassify = (MfCusClassify)JsonStrHandling.handlingStrToBean( returnMap.get("mfCusClassify"), MfCusClassify.class);
								dataMap.put("flag", "success");
								dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage());
							}
						}
					} else if ("6".equals(list.get(0).getClassifyType())){
							if("5".equals(mfCusClassify.getClassifyType())){
								mfCusClassify.setBlockType("0");
								//如果修改之前是黑名单，则同样走审批流程
								Map<String,Object> returnMap = mfCusClassifyAdjustHistoryFeign.startApproveFlow(mfCusClassify);
								if("error".equals(returnMap.get("flag"))){
									dataMap.put("flag", "error");
									dataMap.put("msg",returnMap.get("msg"));
								}else{
									if("1".equals(returnMap.get("isApprove"))){//如果审批流程开启
										dataMap.put("flag", "approve");
										dataMap.put("msg", MessageEnum.SUCCEED_APPROVAL.getMessage(returnMap));
									}else{
										_mfCusClassify = (MfCusClassify)JsonStrHandling.handlingStrToBean( returnMap.get("mfCusClassify"), MfCusClassify.class);
										dataMap.put("flag", "success");
										dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage());
									}
								}
							}
					}else{
						//修改之前也不是黑名单客户，则直接修改客户分类，不再进行审批
						_mfCusClassify = mfCusClassifyFeign.insertOrUpdate(mfCusClassify);
						dataMap.put("flag", "success");
						dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage());
					}

				}else{
				//修改之前也不是黑名单客户，则直接修改客户分类，不再进行审批
					_mfCusClassify = mfCusClassifyFeign.insertOrUpdate(mfCusClassify);
					dataMap.put("flag", "success");
					dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage());
				}
			}
			dataMap.put("mfCusClassify", _mfCusClassify);
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_OPERATION.getMessage());
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
		FormData formcusclassify0002 = formService.getFormData("cusclassify0002");
		getFormValue(formcusclassify0002, getMapByJson(ajaxData));
		MfCusClassify mfCusClassifyJsp = new MfCusClassify();
		setObjValue(formcusclassify0002, mfCusClassifyJsp);
		MfCusClassify mfCusClassify = mfCusClassifyFeign.getById(mfCusClassifyJsp);
		if(mfCusClassify!=null){
			try{
				mfCusClassify = (MfCusClassify)EntityUtil.reflectionSetVal(mfCusClassify, mfCusClassifyJsp, getMapByJson(ajaxData));
				mfCusClassifyFeign.update(mfCusClassify);
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
		FormData 	formcusclassify0001 = formService.getFormData("cusclassify0001");
			getFormValue(formcusclassify0001, getMapByJson(ajaxData));
			if(this.validateFormData(formcusclassify0001)){
				MfCusClassify mfCusClassify = new MfCusClassify();
				setObjValue(formcusclassify0001, mfCusClassify);
				mfCusClassifyFeign.update(mfCusClassify);
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
	 * @param classifyInfoId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String ajaxData, String classifyInfoId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formcusclassify0002 = formService.getFormData("cusclassify0002");
		MfCusClassify mfCusClassify = new MfCusClassify();
		mfCusClassify.setClassifyInfoId(classifyInfoId);
		mfCusClassify = mfCusClassifyFeign.getById(mfCusClassify);
		getObjValue(formcusclassify0002, mfCusClassify,formData);
		if(mfCusClassify!=null){
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}
	/**
	 * Ajax异步删除
	 * @param classifyInfoId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String ajaxData, String classifyInfoId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusClassify mfCusClassify = new MfCusClassify();
		mfCusClassify.setClassifyInfoId(classifyInfoId);
		try {
			mfCusClassifyFeign.delete(mfCusClassify);
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
		FormData formcusclassify0002 = formService.getFormData("cusclassify0002");
		model.addAttribute("formcusclassify0002", formcusclassify0002);
		model.addAttribute("query", "");
		return "/component/cus/cuslevel/MfCusClassify_Insert";
	}
	/**
	 * 
	 * 方法描述： 打开客户分类配置页面
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @date 2017-3-20 下午5:05:16
	 */
	@RequestMapping(value = "/configInput")
	public String configInput(Model model, String ajaxData) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData formclassifyconfig00001 = formService.getFormData("classifyconfig00001");
		model.addAttribute("formclassifyconfig00001", formclassifyconfig00001);
		model.addAttribute("query", "");
		return "/component/cus/cuslevel/MfCusClassify_ConfigInsert";
	}
	/**
	 * 
	 * 方法描述： 客户分类配置新增保存
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @date 2017-3-20 下午5:25:36
	 */
	@RequestMapping(value = "/configInsertAjax")
	@ResponseBody
	public Map<String, Object> configInsertAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
		FormData 	formclassifyconfig00001 = formService.getFormData("classifyconfig00001");
			dataMap = getMapByJson(ajaxData);
			getFormValue(formclassifyconfig00001, getMapByJson(ajaxData));
			if(this.validateFormData(formclassifyconfig00001)){
				resultMap = mfCusClassifyFeign.insertCusClassify(dataMap);
				dataMap.put("remark", resultMap.get("remark"));
				dataMap.put("optName", resultMap.get("optName"));
				dataMap.put("optCode", resultMap.get("optCode"));
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage());
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",MessageEnum.FAILED_OPERATION.getMessage());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_OPERATION.getMessage());
			throw e;
		}
		return dataMap;
	}
	/**
	 * 
	 * 方法描述： 跳转到客户分类编辑
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @param optCode 
	 * @date 2017-3-21 上午9:59:34
	 */
	@RequestMapping(value = "/configEdit")
	public String configEdit(Model model, String ajaxData, String optCode) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData formclassifyconfig00001 = formService.getFormData("classifyconfig00001");
		ParmDic  parmDic = new ParmDic();
		CodeUtils cu = new CodeUtils();
		Map<String,ParmDic> cusTypeMapObj = cu.getMapObjByKeyName("CLASSIFY_TYPE");
		parmDic = cusTypeMapObj.get(optCode);
		getObjValue(formclassifyconfig00001, parmDic);
		model.addAttribute("formclassifyconfig00001", formclassifyconfig00001);
		model.addAttribute("query", "");
		return "/component/cus/cuslevel/MfCusClassify_ConfigEdit";
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
		FormData  formcusclassify0002 = formService.getFormData("cusclassify0002");
		 getFormValue(formcusclassify0002);
		MfCusClassify  mfCusClassify = new MfCusClassify();
		 setObjValue(formcusclassify0002, mfCusClassify);
		 mfCusClassifyFeign.insert(mfCusClassify);
		 getObjValue(formcusclassify0002, mfCusClassify);
		 this.addActionMessage(model, "保存成功");
		 List<MfCusClassify> mfCusClassifyList = (List<MfCusClassify>)mfCusClassifyFeign.findByPage(this.getIpage(), mfCusClassify).getResult();
		model.addAttribute("formcusclassify0002", formcusclassify0002);
		model.addAttribute("mfCusClassifyList", mfCusClassifyList);
		model.addAttribute("query", "");
		return "/component/cus/cuslevel/MfCusClassify_Insert";
	}
	/**
	 * 查询
	 * @param cusNo 
	 * @param classifyInfoId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String ajaxData, String cusNo, String classifyInfoId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData  formcusclassify0001 = formService.getFormData("cusclassify0001");
		 getFormValue(formcusclassify0001);
		MfCusClassify  mfCusClassify = new MfCusClassify();
		 mfCusClassify.setCusNo(cusNo);
		 mfCusClassify.setClassifyInfoId(classifyInfoId);
		 mfCusClassify = mfCusClassifyFeign.getById(mfCusClassify);
		 getObjValue(formcusclassify0001, mfCusClassify);
		model.addAttribute("formcusclassify0001", formcusclassify0001);
		model.addAttribute("query", "");
		return "/component/cus/cuslevel/MfCusClassify_DetailById";
	}
	
	@RequestMapping(value = "/getByCusNo")
	public String getByCusNo(Model model, String ajaxData, String cusNo) throws Exception{
		FormService formService = new FormService();
		 ActionContext.initialize(request,response);
		 Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData  formcusclassify0001 = formService.getFormData("cusclassify0001");
		 getFormValue(formcusclassify0001);
		MfCusClassify  mfCusClassify = new MfCusClassify();
		 mfCusClassify.setCusNo(cusNo);
		 List<MfCusClassify> mfCusClassifyList = mfCusClassifyFeign.getNewByCusNo(mfCusClassify);
		 
		 MfCusCustomer mfCusCustomer = new MfCusCustomer();
		 mfCusCustomer.setCusNo(cusNo);
		 mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		 if(StringUtil.isEmpty(mfCusCustomer.getCusLevelName())){
			 mfCusCustomer.setCusLevelName("未评级");
			 mfCusCustomer.setCusLevelId("0");
		 }
		 if(mfCusClassifyList.size() > 0){
			 mfCusClassify = mfCusClassifyList.get(0);
		 }
		 mfCusClassify.setCusName(mfCusCustomer.getCusName());
		 //mfCusClassify.setMergerReason(null);
		getObjValue(formcusclassify0001, mfCusCustomer);
		getObjValue(formcusclassify0001, mfCusClassify);
		MfBusApply  mfBusApplyTmp = new MfBusApply();
		mfBusApplyTmp.setCusNo(mfCusClassify.getCusNo());
		List<MfBusApply> mfBusApplyList = appInterfaceFeign.getMultiBusList(mfBusApplyTmp);
		dataMap.put("moreCount", mfBusApplyList.size());
		if(mfBusApplyList.size()>0){
			double applyAmt =0.00;
			 for(int i=0;i<mfBusApplyList.size();i++){
				 mfBusApplyTmp = mfBusApplyList.get(i);
				 applyAmt = applyAmt+mfBusApplyTmp.getAppAmt();
			 }
			dataMap.put("totleAmt", MathExtend.moneyStr(applyAmt/10000));
		}else{
			dataMap.put("totleAmt", "0.00");
		}
		String projectName= ymlConfig.getSysParams().get("sys.project.name");
		request.setAttribute("projectName", projectName);
		model.addAttribute("formcusclassify0001", formcusclassify0001);
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("query", "");
		model.addAttribute("cusNo",cusNo);
		return "/component/cus/cuslevel/MfCusClassify_Detail";
	}
	/**
	 * 删除
	 * @param classifyInfoId 
	 * @param cusNo 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String ajaxData, String classifyInfoId, String cusNo) throws Exception {
		ActionContext.initialize(request,
				response);
		MfCusClassify mfCusClassify = new MfCusClassify();
		mfCusClassify.setClassifyInfoId(classifyInfoId);
		mfCusClassifyFeign.delete(mfCusClassify);
		return getListPage(model, cusNo);
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
		FormData  formcusclassify0002 = formService.getFormData("cusclassify0002");
		 getFormValue(formcusclassify0002);
		 boolean validateFlag = this.validateFormData(formcusclassify0002);
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
		FormData  formcusclassify0002 = formService.getFormData("cusclassify0002");
		 getFormValue(formcusclassify0002);
		 boolean validateFlag = this.validateFormData(formcusclassify0002);
	}
	
	
	/**
	 * 
	 * 方法描述： 打开黑名单审批页面
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author YXY
	 * @date 2017-2-15 下午2:51:41
	 */
	@RequestMapping(value = "/getViewPoint")
	public String getViewPoint(Model model,String taskId, String flowWaterId,String activityType,String hideOpinionType) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		model.addAttribute("flowWaterId", flowWaterId);
		//创建一个唯一标识
		model.addAttribute("sortFlowWaterId", "sort"+flowWaterId);
		//获取审批实体并为表单赋值
		FormData formcusClassifyApprove0001 = formService.getFormData("cusClassifyApprove0001");
		MfCusClassifyAdjustHistory mfCusClassifyAdjustHistory = mfCusClassifyAdjustHistoryFeign.getNewByFlowWaterId(flowWaterId);
		getObjValue(formcusClassifyApprove0001, mfCusClassifyAdjustHistory);
		model.addAttribute("mfCusClassifyAdjustHistory", mfCusClassifyAdjustHistory);
		
		//客户信息
		String cusNo = mfCusClassifyAdjustHistory.getCusNo();

		// 获得审批节点信息
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(flowWaterId, null);
		// 处理审批意见类型
		Map<String, String> opinionTypeMap = new HashMap<String, String>();
		opinionTypeMap.put("hideOpinionType", hideOpinionType); // 隐藏审批类型
		String nodeNo = taskAppro.getActivityName();// 审批流程中当前审批节点编号
		opinionTypeMap.put("processDefinitionId", taskAppro.getProcessDefinitionId());// 流程id
		opinionTypeMap.put("nodeNo", nodeNo);// 当前节点编号
		List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType,
				taskAppro.getCouldRollback(), opinionTypeMap);

		this.changeFormProperty(formcusClassifyApprove0001, "opinionType", "optionArray", opinionTypeList);
		model.addAttribute("formcusClassifyApprove0001", formcusClassifyApprove0001);
		model.addAttribute("mfCusClassifyAdjustHistory", mfCusClassifyAdjustHistory);
		model.addAttribute("activityType", activityType);
		model.addAttribute("taskId", taskId);
		model.addAttribute("cusNo", cusNo);

		model.addAttribute("query", "");
		return "component/cus/cuslevel/classify_aprove_viewPoint";
	}
	
	/**
	 * 
	 * 方法描述：黑名单提交审批
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author YXY
	 * @date 2017-2-15 下午2:51:41
	 */
	@ResponseBody
	@RequestMapping(value = "/approveSubmitAjax")
	public Map<String, Object> approveSubmitAjax(String flowWaterId, String ajaxData, String taskId,
			String transition, String nextUser) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = getMapByJson(ajaxData);
			MfCusClassifyAdjustHistory mfCusClassifyAdjustHistory = mfCusClassifyAdjustHistoryFeign.getNewByFlowWaterId(flowWaterId);
			String opinionType = String.valueOf(dataMap.get("opinionType"));
			String approvalOpinion = String.valueOf(dataMap.get("approvalOpinion"));
			mfCusClassifyAdjustHistory.setApprovalOpinion(approvalOpinion);
			mfCusClassifyAdjustHistory.setOpinionType(opinionType);
			
			Result res = mfCusClassifyAdjustHistoryFeign.doCommit(taskId, transition, nextUser, mfCusClassifyAdjustHistory);
			if (res.isSuccess()) {
				dataMap.put("flag", "success");
				if (res.isEndSts()) {
					dataMap.put("msg", res.getMsg());
				} else {
					dataMap.put("msg", res.getMsg());
				}
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.FAILED_SUBMIT.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}

	//add by lance zhao 20180827 客户自动分类
	@RequestMapping(value = "/cusAutoClassify")
	@ResponseBody
	public Map<String, String> cusAutoClassify(String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, String> dataMap = new HashMap<String, String>();
		dataMap.put("cusno",cusNo);
		try {
			String rankTypeName = "潜在客户";
			//调用规则引擎,根据规则对客户进行分类；具体分类参考数据字典CLASSIFY_TYPE
			String retStr = mfCusClassifyFeign.cusAutoClassify(dataMap);
			//更新客户类型
			if (StringUtils.isNotBlank(retStr)&&!"error".equals(retStr)) {
				CodeUtils cu = new CodeUtils();
				Map<String, String> classIfyMap = cu.getMapByKeyName("CLASSIFY_TYPE");
				if (classIfyMap.containsKey(retStr)){
					rankTypeName = classIfyMap.get(retStr);
				}else{
					dataMap.put("flag", "error");
					dataMap.put("msg", "自动分类失败！请检查自动分类检查项或自动分类规则");
					return dataMap;
				}
				MfCusCustomer cusCustomer = new MfCusCustomer();
				cusCustomer = new MfCusCustomer();
				cusCustomer.setCusNo(cusNo);
				cusCustomer.setClassifyType(retStr);
				mfCusCustomerFeign.update(cusCustomer);
				MfCusClassify cusClassify = new MfCusClassify();
				cusClassify.setCusNo(cusNo);
				cusClassify.setRankType(retStr);
				cusClassify.setClassifyType(retStr);
				cusClassify.setRankTypeName(rankTypeName);
				cusClassify.setIsHis("0");
				cusClassify.setMergerWay("1");
				mfCusClassifyFeign.insert(cusClassify);
				dataMap.put("flag", "success");
				dataMap.put("rankType",retStr);
				dataMap.put("rankTypeName",rankTypeName);
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg", "自动分类失败！请检查自动分类检查项或自动分类规则");
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
}

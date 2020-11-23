package  app.component.rec.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.component.nmd.entity.ParmDic;
import app.component.pact.feign.MfBusFincAppFeign;
import app.component.pact.repay.entity.MfPreRepayApply;
import app.component.pact.repay.feign.MfRepaymentFeign;
import app.component.prdct.entity.MfKindFlow;
import config.YmlConfig;
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
import com.google.gson.Gson;

import app.base.User;
import app.component.appinterface.AppInterfaceFeign;
import app.component.calc.core.entity.MfRepayAmt;
import app.component.calccoreinterface.CalcRepaymentInterfaceFeign;
import app.component.common.BizPubParm;
import app.component.cus.entity.MfCusCustomer;
import app.component.cusinterface.CusInterfaceFeign;
import app.component.pact.entity.MfBusFincApp;
import app.component.pact.entity.MfBusPact;
import app.component.pactinterface.PactInterfaceFeign;
import app.component.prdct.entity.MfKindForm;
import app.component.prdctinterface.PrdctInterfaceFeign;
import app.component.rec.entity.RecallBase;
import app.component.rec.feign.RecallBaseFeign;
import app.component.sys.entity.SysUser;
import app.component.sys.feign.SysOrgFeign;
import app.component.sys.feign.SysUserFeign;
import app.component.sysextendinterface.SysExtendInterfaceFeign;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.MathExtend;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.WaterIdUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Title: RecallBaseAction.java
 * Description:
 * @author:@dhcc.com.cn
 * @Tue Mar 15 09:24:17 GMT 2016
 **/
@Controller
@RequestMapping(value="/recallBase")
public class RecallBaseController extends BaseFormBean{
	//注入业务层
	@Autowired
	private RecallBaseFeign recallBaseFeign;
	@Autowired
	private AppInterfaceFeign appInterfaceFeign;
	@Autowired
	private CusInterfaceFeign cusInterfaceFeign;
	@Autowired
	private PactInterfaceFeign pactInterfaceFeign;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;
	@Autowired
	private SysOrgFeign sysOrgFeign;
	@Autowired
	private SysExtendInterfaceFeign sysExtendInterfaceFeign;
	@Autowired
	private CalcRepaymentInterfaceFeign calcRepaymentInterfaceFeign;
	@Autowired
	private PrdctInterfaceFeign prdctInterfaceFeign;
	@Autowired
	private SysUserFeign sysUserFeign;
	@Autowired
	private YmlConfig ymlConfig;
	@Autowired
	private MfBusFincAppFeign mfBusFincAppFeign;
	@Autowired
	private MfRepaymentFeign mfRepaymentFeign;
	
	//全局变量
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	/**
	 * 获取列表数据
	 * @param recallBase 
	 * @return
	 * @throws Exception
	 */
	private void getTableData(String tableId, RecallBase recallBase,Map<String,Object> dataMap) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String  tableHtml = jtu.getJsonStr(tableId,"tableTag", recallBaseFeign.getAll(recallBase), null,true);
		dataMap.put("tableData",tableHtml);
	}
	/**
	 * 列表有翻页
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request,
				response);
		return "component/rec/RecallBase_ListForPers";
	}

	/**
	 * 列表有翻页
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getListPageForSend")
	public String getListPageForSend(Model model) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formrec0007= formService.getFormData("rec0007");
		model.addAttribute("formrec0007", formrec0007);
		return "component/rec/RecallBase_ListSend";
	}
	
	/***
	 * 标准例子
	 * 列表数据查询的异步方法
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value="/findByPageAjax")
	public Map<String,Object> findByPageAjax(String recallSts,String ajaxData,String fincId,Integer pageNo, Integer pageSize,String tableId,String tableType,Ipage ipage) throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String,Object> dataMap = new HashMap<String, Object>();  
		RecallBase recallBase = new RecallBase();
		try {
			if(!StringUtil.isEmpty(recallSts)){
				recallBase.setRecallSts(recallSts);
			}
			String scopeType = "0";
			//取出ajax数据
			Gson gson = new Gson();
			JSONArray jsonArray = gson.fromJson(ajaxData, JSONArray.class);
			if(jsonArray.get(0) instanceof JSONArray){
				JSONArray jsonArraySub = jsonArray.getJSONArray(0);
				for (int i = 0; i < jsonArraySub.size(); i++) {
					JSONObject jsonObj = (JSONObject)jsonArraySub.get(i);
					if("scopeType".equals((String)jsonObj.get("condition")) && 
							StringUtil.isNotEmpty((String)jsonObj.get("value"))){
						scopeType = (String)jsonObj.get("value");
						if("0".equals(scopeType)){//全部
						}else if ("1".equals(scopeType)) {//当日到期
							recallBase.setScopeType(scopeType);
						}else if ("2".equals(scopeType)) {//超期
							recallBase.setScopeType(scopeType);
						}else {
						}
					}
				}
			}
			recallBase.setCustomQuery(ajaxData);//自定义查询参数赋值
			if("0".equals(scopeType)){
				recallBase.setCriteriaList(recallBase, ajaxData);//我的筛选
			}
			recallBase.setCustomSorts(ajaxData);
			recallBase.setFincId(fincId);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setParams(this.setIpageParams("recallBase", recallBase));
			ipage = recallBaseFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	/***
	 * 标准例子
	 * 列表数据查询的异步方法
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/findByPageUserTaskAjax")
	public Map<String,Object> findByPageUserTaskAjax(String recallSts,String ajaxData,Integer pageNo, Integer pageSize,String tableId,String tableType) throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String,Object> dataMap = new HashMap<String, Object>();  
		RecallBase recallBase = new RecallBase();
		
		try {
			if(!StringUtil.isEmpty(recallSts)){
				recallBase.setRecallSts(recallSts);
			}
			recallBase.setCustomQuery(ajaxData);//自定义查询参数赋值
			recallBase.setCriteriaList(recallBase, ajaxData);//我的筛选
			//this.getRoleConditions(appProject,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage.setParams(this.setIpageParams("recallBase", recallBase));
			ipage = recallBaseFeign.findByPageUserTask(ipage);
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
	 * 列表全部无翻页
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getListAll")
	public String getListAll(Model model) throws Exception {
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		FormData formrec0009 = formService.getFormData("rec0009");
		RecallBase recallBase = new RecallBase();
		List<RecallBase> recallBaseList = recallBaseFeign.getAll(recallBase);
		model.addAttribute("formrec0009", formrec0009);
		model.addAttribute("recallBaseList", recallBaseList);
		return "/component/rec/RecallBase_ListForPers";
	}
	/**
	 * AJAX新增
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/insertAjax")
	public Map<String,Object> insertAjax(String ajaxData,String tableId) throws Exception {
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>();  
		try{
			FormData formrec0009 = formService.getFormData("rec0009");
			getFormValue(formrec0009, getMapByJson(ajaxData));
			if(this.validateFormData(formrec0009)){
				RecallBase recallBase = new RecallBase();
				setObjValue(formrec0009, recallBase);
				recallBase.setRecallSts(BizPubParm.RECALL_STS_2);
				recallBase.setMustCompleteDays(Integer.parseInt(Long.toString(DateUtil.getDaysBetween(recallBase.getRecallDate(), recallBase.getRecallEndDate()))));
				recallBase.setStartRecallWay(BizPubParm.START_RECALL_WAY_2);
				recallBase.setRecallType(BizPubParm.RECALL_TYPE_2);
				MfBusPact mfBusPact = new MfBusPact();
                mfBusPact.setPactId(recallBase.getPactId());
                mfBusPact = pactInterfaceFeign.getById(mfBusPact);
                recallBase.setAppId(mfBusPact.getAppId());
				if(recallBase.getRecallEndDate().compareTo(recallBase.getRecallDate()) >= 0){
					recallBaseFeign.insert(recallBase);
					getTableData(tableId,recallBase,dataMap);//获取列表
					dataMap.put("flag", "success");
					dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
				}else{
					dataMap.put("flag", "error");
					dataMap.put("msg", "计划开始日期不能大于计划完成日期");
				}
				
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw e;
		}
		return dataMap;
	}
	
	@ResponseBody
	@RequestMapping(value="/insertForSimuAjax")
	public Map<String,Object> insertForSimuAjax(String ajaxData,String tableId) throws Exception {
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>();  
		MfKindForm mfKindForm = new MfKindForm();
		mfKindForm.setNodeNo("recallAdd");
		String formId = prdctInterfaceFeign.getMfkindForm(mfKindForm).getAddModel();
		try{
			FormData formrec0013 = formService.getFormData(formId);
			getFormValue(formrec0013, getMapByJson(ajaxData));
			if(this.validateFormData(formrec0013)){
				RecallBase recallBase = new RecallBase();
				setObjValue(formrec0013, recallBase);
				recallBase.setRecallSts(BizPubParm.RECALL_STS_9);
				recallBase.setMustCompleteDays(1);
				recallBase.setStartRecallWay(BizPubParm.START_RECALL_WAY_2);
				recallBase.setRecallType(BizPubParm.RECALL_TYPE_2);
				//java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMdd");
				recallBase.setRecallExeDate(DateUtil.getDate("yyyyMMdd"));
				recallBase.setRecallEndDate(DateUtil.getDate("yyyyMMdd"));
				recallBase.setRecallDate(DateUtil.getDate("yyyyMMdd"));
				recallBase.setMustCompleteDate(DateUtil.getDate("yyyyMMdd"));
				recallBase.setRecallDate(DateUtil.getDate("yyyyMMdd"));
                MfBusPact mfBusPact = new MfBusPact();
                mfBusPact.setPactId(recallBase.getPactId());
                mfBusPact = pactInterfaceFeign.getById(mfBusPact);
                recallBase.setAppId(mfBusPact.getAppId());
				recallBaseFeign.insertForSimu(recallBase);
				getTableData(tableId,recallBase,dataMap);//获取列表
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());				
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw e;
		}
		return dataMap;
	}
	
	/**
	 * AJAX更新保存
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/updateAjax")
	public Map<String,Object> updateAjax(String tableId,String ajaxData) throws Exception {
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>();  
		RecallBase recallBase = new RecallBase();
		try{
			FormData formrec0009 = formService.getFormData("rec0009");
			getFormValue(formrec0009, getMapByJson(ajaxData));
			if(this.validateFormData(formrec0009)){
				recallBase = new RecallBase();
				setObjValue(formrec0009, recallBase);
				recallBaseFeign.update(recallBase);
				getTableData(tableId,recallBase,dataMap);//获取列表
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
	 * AJAX更新保存,B面催收任务进行提交
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/updateTelAjax")
	public Map<String,Object> updateTelAjax(String ajaxData,String taskNo) throws Exception {
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>();  
		RecallBase recallBase = new RecallBase();
		try{
			FormData formrec0006 = formService.getFormData("rec0006");
			getFormValue(formrec0006, getMapByJson(ajaxData));
			if(this.validateFormData(formrec0006)){
				recallBase = new RecallBase();
				setObjValue(formrec0006, recallBase);
				recallBase.setTaskNo(taskNo);
				recallBaseFeign.updateTel(recallBase);
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
	 * AJAX提交操作
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/submitAjax")
	public Map<String,Object> submitAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		try{
			FormData formrec0006 = formService.getFormData("rec0006");
			getFormValue(formrec0006, getMapByJson(ajaxData));
			if(this.validateFormDataAnchor(formrec0006)){
				RecallBase recallBase = new RecallBase();
				setObjValue(formrec0006, recallBase);
				//验证该任务有没有处理过，如果已被处理过，任务已关闭，页面给出提示，如果没有被处理，继续处理该催收任务，修改时间、状态
				RecallBase recallBaseTmp = recallBaseFeign.getById(recallBase);
				if(BizPubParm.RECALL_STS_9.equals(recallBaseTmp.getRecallSts())){
					dataMap.put("flag", "error");
					dataMap.put("msg",MessageEnum.NO_TASK_EXIST.getMessage());
				}else{
					recallBaseFeign.submit(recallBase);
					dataMap.put("flag", "success");
					dataMap.put("msg",MessageEnum.SUCCEED_UPDATE.getMessage());
				}
				
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
	@ResponseBody
	@RequestMapping(value="/getByIdAjax")
	public Map<String,Object> getByIdAjax(String taskNo) throws Exception {
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String,Object> dataMap = new HashMap<String, Object>();  
		FormData formrec0009 = formService.getFormData("rec0009");
		RecallBase recallBase = new RecallBase();
		recallBase.setTaskNo(taskNo);
		recallBase = recallBaseFeign.getById(recallBase);
		getObjValue(formrec0009, recallBase,formData);
		if(recallBase!=null){
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}
	
	@RequestMapping(value="/getByIdForInfo")
	public String getByIdForInfo(Model model,String taskNo,String taskId,String moveBack) throws Exception {
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		FormData formrec0006 = formService.getFormData("rec0006");
		RecallBase recallBase = new RecallBase();
		recallBase.setTaskNo(taskNo);
		recallBase = recallBaseFeign.getById(recallBase);
		getObjValue(formrec0006,recallBase);
		model.addAttribute("formrec0006", formrec0006);
		model.addAttribute("taskId", taskId);
		String  recallWayList = recallBase.getRecallWay().replace("|","");
		String recallWayName="";
		if (recallWayList.length()>1){
			String[]recallWay1 = recallWayList.split("|");
			for (int i = 0; i <recallWay1.length ; i++) {
				Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RECALL_WAY");
				recallWayName=recallWayName+rateTypeMap.get(recallWay1[i]).getOptName()+",";
			}
		}else{
			Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RECALL_WAY");
			recallWayName=recallWayName+rateTypeMap.get(recallWayList).getOptName();
		}
		//String a=recallWayName.substring(0,recallWayName.length()-1);
		recallBase.setRecallWay(recallWayName);
		getObjValue(formrec0006,recallBase);
		model.addAttribute("query", "");
		model.addAttribute("moveBack", moveBack);
		return "component/rec/RecallBase_Detail";
	}
	
	/**
	 * AJAX获取查看
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/getByIdAjaxForPers")
	public Map<String,Object> getByIdAjaxForPers(String taskNo,String query) throws Exception {
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>();  
		FormData formrec0009 = formService.getFormData("rec0009");
		RecallBase recallBase = new RecallBase();
		recallBase.setTaskNo(taskNo);
		recallBase = recallBaseFeign.getById(recallBase);
		getObjValue(formrec0009,recallBase);
		JsonFormUtil jfu = new JsonFormUtil();
		String  formHtml = jfu.getJsonStr(formrec0009,"bigFormTag",query);
		dataMap.put("formHtml",formHtml);
		
		return dataMap;
	}
	/**
	 * Ajax异步删除
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/deleteAjax")
	public Map<String,Object> deleteAjax(String taskNo,String tableId) throws Exception{
		ActionContext.initialize(request,
				response);
		Map<String,Object> dataMap = new HashMap<String, Object>();  
		RecallBase recallBase = new RecallBase();
		recallBase.setTaskNo(taskNo);
		try {
			//JSONObject jb = JSONObject.fromObject(ajaxData);
			//recallBase = (RecallBase)JSONObject.toBean(jb, RecallBase.class);
			recallBaseFeign.delete(recallBase);
			getTableData(tableId,recallBase,dataMap);//获取列表
			dataMap.put("flag", "success");
			dataMap.put("msg",MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw e;
		}
		return dataMap;
	}
	
	/**
	 * 查看
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getById")
	public String getById(Model model,String taskNo,String recallSts) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formrec0011 = formService.getFormData("rec0011");
		FormData formrec0012 = formService.getFormData("rec0011_noReply");
		RecallBase recallBase = new RecallBase();
		recallBase.setTaskNo(taskNo);
		recallBase = recallBaseFeign.getById(recallBase);

		/*20190310yxl 增加催收催收详情复利利息等信息*/
        Map<String,Object> dataMap = new HashMap<String, Object>();
        MfBusFincApp mfBusFincApp=new MfBusFincApp();
        String fincId=recallBase.getFincId();
        mfBusFincApp.setFincId(fincId);
        mfBusFincApp = pactInterfaceFeign.getFincAppById(mfBusFincApp);


        Map<String,String> fincMap = new HashMap<String,String>();
        fincMap.put("fincId", fincId);
        fincMap.put("date", DateUtil.getDate("yyyyMMdd"));
        List<MfRepayAmt> mfRepayAmtList = calcRepaymentInterfaceFeign.getCurTermYingShouAmtList(fincMap);
        //逾期利息
        Double yuqilixi= 0.00;
        //复利利息
        Double fulilixi=0.00;
        if(mfRepayAmtList.size() > 0){
            for(MfRepayAmt mfRepayAmt : mfRepayAmtList){
                if("2".equals(mfRepayAmt.getHeJiFlag())){
                    yuqilixi= Double.parseDouble(mfRepayAmt.getYuQiLiXi());
                    fulilixi= Double.parseDouble(mfRepayAmt.getFuLiLiXi());
                }
            }
        }
        //金额格式化
        dataMap.put("putoutAmt",mfBusFincApp.getPutoutAmt());//放款金额
        dataMap.put("loanBal",mfBusFincApp.getLoanBal());//放款余额
        dataMap.put("yuqilixi",yuqilixi);//逾期利息
        dataMap.put("fulilixi",fulilixi);//复利利息
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
        //日期格式化
        dataMap.put("putoutAppDate", DateUtil.parseDateToTenStr(sdf.parse(mfBusFincApp.getPutoutAppDate())));//放款日期
        dataMap.put("intstEndDate", DateUtil.parseDateToTenStr(sdf.parse(mfBusFincApp.getIntstEndDate())));//到期日期

        model.addAttribute("query", "");
		String  recallWayList = recallBase.getRecallWay().replace("|","");
		String recallWayName="";
		if (recallWayList.length()>1){
			String[]recallWay1 = recallWayList.split("|");
			for (int i = 0; i <recallWay1.length ; i++) {
				Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RECALL_WAY");
				recallWayName=recallWayName+rateTypeMap.get(recallWay1[i]).getOptName()+",";
			}
		}else{
			Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RECALL_WAY");
			recallWayName=recallWayName+rateTypeMap.get(recallWayList).getOptName();
		}
		//String a=recallWayName.substring(0,recallWayName.length()-1);
		recallBase.setRecallWay(recallWayName);
		if(!"9".equals(recallSts)){
            getObjValue(formrec0012, dataMap);
            getObjValue(formrec0012, recallBase);
			model.addAttribute("formrec0012", formrec0012);
		}else{
            getObjValue(formrec0011, dataMap);
            getObjValue(formrec0011, recallBase);
			model.addAttribute("formrec0012",formrec0011);
		}
		model.addAttribute("recallSts",recallSts);
		model.addAttribute("query","");
		return "component/rec/RecallBase_DetailInfo";
	}

	/**
	 * c面中催收指派 指派经办人
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/selectdUserToTaskAjax")
	public Map<String,Object> selectdUserToTaskAjax(String beanAjaxData,String ajaxData)throws Exception{
		ActionContext.initialize(request, response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		RecallBase recallBase = new RecallBase();
		try{
			recallBase = new RecallBase();
			JSONObject jo = JSONObject.fromObject(beanAjaxData);
			recallBase=(RecallBase) JSONObject.toBean(jo, RecallBase.class);
			
			recallBase.setCustomQuery(ajaxData);//自定义查询参数赋值
			recallBase.setCriteriaList(recallBase, ajaxData);//我的筛选
			/**
			if(ArrayUtils.isEmpty(recallBase.getTaskNoArray())){
				recallBase.setTaskNoStr(null);
			}
			**/
			if(recallBase.getTaskNoStr()!=null && !"".equals(recallBase.getTaskNoStr())){
				recallBase.setTaskNoArray(recallBase.getTaskNoStr().split(","));
			}else{
				recallBase.setTaskNoArray(null);
			}
			
			recallBaseFeign.updateMgr(recallBase);
			dataMap.put("flag", "success");
			dataMap.put("msg", "更新成功");
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw e;
		}
		
		return dataMap;
	}
	
	/**
	 * 指派页面new
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/toSendList")
	public String toSendList(Model model) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formrec0007= formService.getFormData("rec0007");
		model.addAttribute("formrec0007", formrec0007);
		return "component/rec/RecallBase_toSendList";
	}
	/**
	 * 获取催收页面
	 * @return
	 */
	@RequestMapping(value="/getInputInfo")
	public String getInputInfo(){
		return "component/rec/RecallBase_InsertForCont";
	}
	
	
	@RequestMapping(value="/input")
	public String input(Model model,String pactId,String cusNo,String fincId,String query)throws Exception {
		ActionContext.initialize(request, response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		FormService formService = new FormService();
		FormData formrec0009= formService.getFormData("rec0009");
		MfKindForm mfKindForm = new MfKindForm();
		mfKindForm.setNodeNo("recallAdd");
		String formId = prdctInterfaceFeign.getMfkindForm(mfKindForm).getAddModel();
		FormData formrec0013= formService.getFormData(formId);
		RecallBase recallBase = new RecallBase();
		recallBase.setPactId(pactId);
		//recallBase.setCusName(cusName);
		//recallBase.setAppName(appName);
		recallBase.setCusNo(cusNo);
		
		Map<String,String> fincMap = new HashMap<String,String>();
		fincMap.put("fincId", fincId);
		fincMap.put("date", DateUtil.getDate("yyyyMMdd"));
		List<MfRepayAmt> mfRepayAmtList = calcRepaymentInterfaceFeign.getCurTermYingShouAmtList(fincMap);
		if(mfRepayAmtList.size() > 0){
			for(MfRepayAmt mfRepayAmt : mfRepayAmtList){
				if("2".equals(mfRepayAmt.getHeJiFlag())){
					recallBase.setRecallUnpayAmt1(Double.parseDouble(mfRepayAmt.getBenJin()));
					recallBase.setRecallUnpayAmt2(Double.parseDouble(mfRepayAmt.getLiXi()));
					recallBase.setBrcContAmt(Double.parseDouble(mfRepayAmt.getFaXi()) + Double.parseDouble(mfRepayAmt.getWeiYueJin()));
					recallBase.setRecallAmt(Double.parseDouble(mfRepayAmt.getBenJin()) + Double.parseDouble(mfRepayAmt.getLiXi()) + Double.parseDouble(mfRepayAmt.getFaXi()) + Double.parseDouble(mfRepayAmt.getWeiYueJin()));
				}
			}
		}
		//金额格式化
		dataMap.put("recallUnpayAmt1", MathExtend.moneyStr(recallBase.getRecallUnpayAmt1()));// 应还本金
		dataMap.put("recallUnpayAmt2", MathExtend.moneyStr(recallBase.getRecallUnpayAmt2()));// 应还利息
		dataMap.put("brcContAmt", MathExtend.moneyStr(recallBase.getBrcContAmt()));// 违约金
		dataMap.put("recallAmt", MathExtend.moneyStr(recallBase.getRecallAmt()));// 催收总额
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);
		recallBase.setCusName(mfCusCustomer.getCusName());
		if(mfCusCustomer.getContactsName()!=null) {
            recallBase.setCusContactName(mfCusCustomer.getContactsName());
        }
		if(mfCusCustomer.getContactsTel()!=null) {
            recallBase.setCusTel(mfCusCustomer.getContactsTel());
        }
		
		MfBusPact mfBusPact = new MfBusPact();
		mfBusPact.setPactId(pactId);
		mfBusPact = pactInterfaceFeign.getById(mfBusPact);
		recallBase.setAppName(mfBusPact.getAppName());
		recallBase.setConNo(mfBusPact.getPactNo());
		MfBusFincApp mfBusFincApp = new MfBusFincApp();
		mfBusFincApp.setFincId(fincId);
		mfBusFincApp = pactInterfaceFeign.getFincAppById(mfBusFincApp);
		recallBase.setFincId(fincId);
		recallBase.setFincShowId(mfBusFincApp.getFincShowId());
		/**
		 * 
		 * 应还本金、应还利息、违约金、催收总金额将来需要按照账务系统计算
		 */
		
		recallBase.setRecallDesc(""+recallBase.getCusName()+",项目名称为:"+recallBase.getAppName()+"的贷款," 
				+"合同号:"+recallBase.getConNo()+",已经逾期,其中应还本金为:"+MathExtend.moneyStr(recallBase.getRecallUnpayAmt1())
				+",应还利息:"+MathExtend.moneyStr(recallBase.getRecallUnpayAmt2())+",违约金为:"+MathExtend.moneyStr(recallBase.getBrcContAmt())
				+",总计应催收金额为:"+MathExtend.moneyStr(recallBase.getRecallAmt())+",请尽快催收.");
		String recallId = WaterIdUtil.getWaterId();
		recallBase.setRecallId(recallId);
		getFormValue(formrec0009);
		getObjValue(formrec0009,recallBase);
		getFormValue(formrec0013);
		getObjValue(formrec0013,recallBase);
		//新增表单方式 1-供产品使用 2-供铁甲网使用
		String typeShow = recallBaseFeign.getRecallHistoryTypeShow();
		
		model.addAttribute("pactId", pactId);
		model.addAttribute("cusNo", cusNo);
		model.addAttribute("fincId", fincId);
		
		model.addAttribute("recallBase", recallBase);
		model.addAttribute("mfCusCustomer", mfCusCustomer);
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("formrec0009", formrec0009);
		model.addAttribute("formrec0013", formrec0013);
		model.addAttribute("typeShow", typeShow);
		model.addAttribute("query", "");
		model.addAttribute("sysProjectName", ymlConfig.getSysParams().get("sys.project.name"));

		if("2".equals(typeShow)){
			return "component/rec/RecallBaseArmour_Insert";
		}else{
			return "component/rec/RecallBase_Insert";
		}
	}
	
	@RequestMapping(value="/inputForSimu")
	public String inputForSimu(Model model,String conNo,String cusNo,String fincId)throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		MfKindForm mfKindForm = new MfKindForm();
		mfKindForm.setNodeNo("recallAdd");
		String formId = prdctInterfaceFeign.getMfkindForm(mfKindForm).getAddModel();
		FormData formrec0013= formService.getFormData(formId);
		RecallBase recallBase = new RecallBase();
//		recallBase.setConNo(conNo);
		//recallBase.setCusName(cusName);
		//recallBase.setAppName(appName);
		recallBase.setCusNo(cusNo);
		
		Map<String,String> fincMap = new HashMap<String,String>();
		fincMap.put("fincId", fincId);
		fincMap.put("date", DateUtil.getDate("yyyyMMdd"));
		List<MfRepayAmt> mfRepayAmtList = calcRepaymentInterfaceFeign.getCurTermYingShouAmtList(fincMap);
		if(mfRepayAmtList.size() > 0){
			for(MfRepayAmt mfRepayAmt : mfRepayAmtList){
				if("2".equals(mfRepayAmt.getHeJiFlag())){
					recallBase.setRecallUnpayAmt1(Double.parseDouble(mfRepayAmt.getBenJin()));
					recallBase.setRecallUnpayAmt2(Double.parseDouble(mfRepayAmt.getLiXi()));
					recallBase.setBrcContAmt(Double.parseDouble(mfRepayAmt.getFaXi()) + Double.parseDouble(mfRepayAmt.getWeiYueJin()));
					recallBase.setRecallAmt(Double.parseDouble(mfRepayAmt.getBenJin()) + Double.parseDouble(mfRepayAmt.getLiXi()) + Double.parseDouble(mfRepayAmt.getFaXi()) + Double.parseDouble(mfRepayAmt.getWeiYueJin()));
				}
			}
		}
		
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);
		recallBase.setCusName(mfCusCustomer.getCusName());
		if(mfCusCustomer.getContactsName()!=null) {
            recallBase.setCusContactName(mfCusCustomer.getContactsName());
        }
		if(mfCusCustomer.getContactsTel()!=null) {
            recallBase.setCusTel(mfCusCustomer.getContactsTel());
        }
		
		MfBusPact mfBusPact = new MfBusPact();
		mfBusPact.setPactNo(conNo);
		mfBusPact = pactInterfaceFeign.getById(mfBusPact);
		recallBase.setAppName(mfBusPact.getAppName());
		recallBase.setConNo(mfBusPact.getPactNo());
		MfBusFincApp mfBusFincApp = new MfBusFincApp();
		mfBusFincApp.setFincId(fincId);
		mfBusFincApp = pactInterfaceFeign.getFincAppById(mfBusFincApp);
		recallBase.setFincId(fincId);
		recallBase.setFincShowId(mfBusFincApp.getFincShowId());
		/**
		 * 
		 * 应还本金、应还利息、违约金、催收总金额将来需要按照账务系统计算
		 */
		
		getFormValue(formrec0013);
		getObjValue(formrec0013,recallBase);
		
		
		model.addAttribute("formrec0013", formrec0013);
		return "component/rec/RecallBase_ListForSimu";
	}
	
	@ResponseBody
	@RequestMapping(value="/delegateRecallAjax")
	public Map<String,Object> delegateRecallAjax(String taskNo) throws Exception {
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		try {
			RecallBase recallBase = new RecallBase();
			recallBase.setTaskNo(taskNo);
			recallBaseFeign.delegateRecall(recallBase);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		}catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw e;
		}
		return dataMap;
	}
	
	@ResponseBody
	@RequestMapping(value="/findDealByPageAjax")
	public Map<String,Object> findDealByPageAjax(String ajaxData,Integer pageNo, Integer pageSize,String tableId,String tableType,String recallSts) throws Exception {
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		try{
			RecallBase recallBase = new RecallBase();
			recallBase.setRecallSts(recallSts);
			recallBase.setCustomQuery(ajaxData);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setParams(this.setIpageParams("recallBase", recallBase));
			ipage = recallBaseFeign.findDealByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
			
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	
	@RequestMapping(value="/showMgrNameDialog")
	public String showMgrNameDialog(Model model) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formrec0007= formService.getFormData("rec0007");
		SysUser sysUser = new SysUser();
		sysUser.setOpNoType(BizPubParm.OP_NO_TYPE1);
		List<SysUser> sysUserList = sysUserFeign.getAllUserList(sysUser);
		JSONObject json = new JSONObject();
		JSONArray sysUserArray = JSONArray.fromObject(sysUserList);
		for (int i = 0; i < sysUserArray.size(); i++) {
			sysUserArray.getJSONObject(i).put("id",
					sysUserArray.getJSONObject(i).getString("opNo"));
			sysUserArray.getJSONObject(i).put("name",
					sysUserArray.getJSONObject(i).getString("opName"));
		}
		json.put("sysUser", sysUserArray);
		String ajaxData = json.toString();
		model.addAttribute("formrec0007", formrec0007);
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("query", "");
	
		return "component/rec/RecallBase_ShowMgrNameDialog";
	}
	
	@RequestMapping(value="/getViewPoint")
	public String getViewPoint(Model model,String taskNo) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formrec0010 = formService.getFormData("rec0010");
		RecallBase recallBase = new RecallBase();
		recallBase.setTaskNo(taskNo);
		recallBase = recallBaseFeign.getById(recallBase);
		getFormValue(formrec0010);
		getObjValue(formrec0010,recallBase);
		model.addAttribute("formrec0010", formrec0010);
		
		return "component/rec/RecallBase_WkfDetail";
	}
	
	@ResponseBody
	@RequestMapping(value="/submitUpdateAjax")
	public Map<String,Object> submitUpdateAjax(String ajaxData) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		try{
			FormData formrec0010 = formService.getFormData("rec0010");
			getFormValue(formrec0010, getMapByJson(ajaxData));
			//approvalOpinion = (String)getMapByJson(ajaxData).get("approvalOpinion");
			if(this.validateFormData(formrec0010)){
				RecallBase recallBase = new RecallBase();
				setObjValue(formrec0010, recallBase);
				recallBaseFeign.submit(recallBase);
				//recallBaseFeign.update(recallBase);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "保存失败");
			throw e;
		}
		return dataMap;
	}
	
	@RequestMapping(value="/openConSelectTableList")
	public String openConSelectTableList() throws Exception{
		ActionContext.initialize(request,response);
		return "component/rec/RecallBase_OpenChooseCon";
	}
	
	@RequestMapping(value="/openConSelectListForSimu")
	public String openConSelectListForSimu() throws Exception{
		ActionContext.initialize(request,response);
		return "component/rec/RecallBase_OpenChooseConForSimu";
	}
	
	@RequestMapping(value="/getListPageBySelf")
	public String getListPageBySelf() throws Exception {
		ActionContext.initialize(request,
				response);
		return "component/rec/RecallBase_ListForPersBySelf";
	}
	
	@RequestMapping(value="/getSysOrgListPage")
	public String getSysOrgListPage(Model model) throws Exception {
		ActionContext.initialize(request,response);
		/**
		net.sf.json.JSONArray dicArray =  sysOrgBo.getAllOrgJson();
		ajaxData = dicArray.toString();
		**/
		JSONArray orgArray = JSONArray.fromObject(sysExtendInterfaceFeign.getAllOrgs());
		JSONArray userArray = JSONArray.fromObject(sysExtendInterfaceFeign.getAllUsers());
 		for( int i=0;i<orgArray.size();i++){
			orgArray.getJSONObject(i).put("id", orgArray.getJSONObject(i).getString("brNo"));
			orgArray.getJSONObject(i).put("name", orgArray.getJSONObject(i).getString("brName"));
			orgArray.getJSONObject(i).put("pId","0");
			orgArray.getJSONObject(i).put("open",false);	
			orgArray.getJSONObject(i).put("isParent",true);
		}
 		for( int i=0;i<userArray.size();i++){
 			userArray.getJSONObject(i).put("id", userArray.getJSONObject(i).getString("opNo"));
 			userArray.getJSONObject(i).put("name", userArray.getJSONObject(i).getString("opName"));
 			userArray.getJSONObject(i).put("pId",userArray.getJSONObject(i).getString("brNo"));
 			userArray.getJSONObject(i).put("open",true);
 			orgArray.add(userArray.getJSONObject(i));
		}	
		String ajaxData= orgArray.toString();
		model.addAttribute("ajaxData", ajaxData);
		return "component/rec/RecallBase_ChooseUser";
	}
	@RequestMapping(value="/getQueryEntranceListPage")
	public String getQueryEntranceListPage() throws Exception {
		ActionContext.initialize(request,response);
		JSONArray recallWallJsonArray = new CodeUtils().getJSONArrayByKeyName("RECALL_WAY");
		this.getHttpRequest().setAttribute("recallWallJsonArray", recallWallJsonArray);
		return "component/rec/RecallBase_QueryEntrance";
	}
	
	
	/**
	 * 
	 * 方法描述： 获取合同主体催收详情页面信息
	 * @return
	 * @throws Exception
	 * String
	 * @author zhs
	 * @param pactId 
	 * @param cusNo 
	 * @date 2017-4-12 下午3:46:01
	 */
	@RequestMapping(value="/getRecallInfo")
	public String getRecallInfo(Model model, String pactId, String cusNo,String fincId) throws Exception {
		ActionContext.initialize(request,
				response);
		ActionContext.initialize(request, response);
		RecallBase recallBase = new RecallBase();
		recallBase.setPactId(pactId);
		recallBase.setCusNo(cusNo);
		recallBase.setRecallSts(BizPubParm.RECALL_STS_9);
		List<RecallBase> recallBaseList = recallBaseFeign.getAll(recallBase);
		CodeUtils cu = new CodeUtils();
		Map<String,String> map = cu.getMapByKeyName("RECALL_WAY");
		if(recallBaseList!=null && recallBaseList.size()>0){
			for(RecallBase recallObj:recallBaseList){
				recallObj.setRecallWay(changeRecallWay(recallObj.getRecallWay(),map));
				recallObj.setRecallExeDate(DateUtil.getStr(recallObj.getRecallExeDate()));
				recallObj.setRecallDate(DateUtil.getStr(recallObj.getRecallDate()));
			}
		}
		//最新的一笔催收任务
		recallBase.setRecallSts(BizPubParm.RECALL_STS_2);
		List<RecallBase> resList = recallBaseFeign.getAll(recallBase);
		if(resList!=null && resList.size()>0){
			recallBase = resList.get(0);
			recallBase.setRecallWay(changeRecallWay(recallBase.getRecallWay(),map));
		}
		// 获取催收历史展示方式 1-表单展示(供产品使用) 2-列表展示(供铁甲网使用)
		String typeShow = recallBaseFeign.getRecallHistoryTypeShow();
		
		model.addAttribute("recallBase", recallBase);
		model.addAttribute("recallBaseList", recallBaseList);
		model.addAttribute("pactId", pactId);
		model.addAttribute("cusNo", cusNo);
		model.addAttribute("fincId", fincId);
		model.addAttribute("typeShow", typeShow);
		
		if("2".equals(typeShow)){
			return "component/rec/RecallHistoryArmourList";
		}else{
			return "component/rec/RecallBase_getRecallInfo";
		}
	}

	/******************
	 * 对多种回执类型进行转换
	 * @param recallWay
	 * @param map
	 * @return
	 */

	public String  changeRecallWay(String recallWay,Map<String,String> map){
		StringBuffer result=new  StringBuffer();
		if(StringUtil.isNotBlank(recallWay)){
			String[] ways=recallWay.split("\\|");
			for(int i=0;i<ways.length;i++){
				result.append(map.get(ways[i]));
				result.append("|");
			}
		}
		if(result.length()>0){
			result.deleteCharAt(result.length()-1);
		}
		return result.toString();
	}




	
	/**
	 * @Description:催收历史详情(铁甲网) 
	 * @return
	 * @throws Exception
	 * @author: 李伟
	 * @date: 2017-12-7 下午6:12:13
	 */
	@RequestMapping(value="/getRecallArmourBase")
	public String getRecallArmourBase(Model model,String taskNo)throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		MfKindForm mfKindForm = new MfKindForm();
		mfKindForm.setNodeNo("recallDetail");
		String formId = prdctInterfaceFeign.getMfkindForm(mfKindForm).getAddModel();
		FormData formrec0013= formService.getFormData(formId);
		RecallBase recallBase = new RecallBase();
		recallBase.setTaskNo(taskNo);
		recallBase = recallBaseFeign.getById(recallBase);
		getFormValue(formrec0013);
		getObjValue(formrec0013,recallBase);
		String recallId = recallBase.getRecallId();
		model.addAttribute("formrec0013", formrec0013);
		model.addAttribute("recallId", recallId);
		return "component/rec/RecallBase_Detail_Armour";
	}

	/*20190304yxl新增催收登记*/
	@RequestMapping(value = "/collectionHandlAdd")
	public String collectionHandlAdd(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formcollectionhandleadd = formService.getFormData("collectionhandleadd");

		MfPreRepayApply mfPreRepayApply = new MfPreRepayApply();
		//mfPreRepayApply.setPenaltyAmt(0.00);
		mfPreRepayApply.setOpName(User.getRegName(request));
		mfPreRepayApply.setBrName(User.getOrgName(request));
		model.addAttribute("formcollectionhandleadd", formcollectionhandleadd);
		model.addAttribute("query", "");
		return "component/rec/RecallBase_CollectionHandleAdd";
	}
	/*20190304yxl异步加载所有符合条件的借据*/
	@RequestMapping(value = "/findLoanAfterByPageAjax")
	@ResponseBody
	public Map<String, Object> getPreRepayFincListAjax(String ajaxData, Integer pageSize, Integer pageNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			MfBusFincApp mfBusFincApp = new MfBusFincApp();
			mfBusFincApp.setCustomQuery(ajaxData);
			mfBusFincApp.setCriteriaList(mfBusFincApp, ajaxData);// 我的筛选
			ipage.setParams(this.setIpageParams("mfBusFincApp", mfBusFincApp));
			ipage = mfBusFincAppFeign.findLoanAfterByPage1(ipage);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}
	/*20190304yxl选择借据后数据回显*/
	@RequestMapping(value = "/getRecallBaseAjax")
	@ResponseBody
	public Map<String, Object> getRecallBaseAjax(String ajaxData, String fincId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusFincApp mfBusFincApp = new MfBusFincApp();
		mfBusFincApp.setFincId(fincId);
		mfBusFincApp = pactInterfaceFeign.getFincAppById(mfBusFincApp);
		//String pactId,String cusNo
		//合同编号
		String pactId =mfBusFincApp.getPactId();
		//客户编号
		String cusNo=mfBusFincApp.getCusNo();

		RecallBase recallBase = new RecallBase();
		recallBase.setPactId(pactId);
		//recallBase.setCusName(cusName);
		//recallBase.setAppName(appName);
		recallBase.setCusNo(cusNo);

		Map<String,String> fincMap = new HashMap<String,String>();
		fincMap.put("fincId", fincId);
		fincMap.put("date", DateUtil.getDate("yyyyMMdd"));
		List<MfRepayAmt> mfRepayAmtList = calcRepaymentInterfaceFeign.getCurTermYingShouAmtList(fincMap);
		//逾期利息
		Double yuqilixi= 0.00;
		//复利利息
		Double fulilixi=0.00;
		if(mfRepayAmtList.size() > 0){
			for(MfRepayAmt mfRepayAmt : mfRepayAmtList){
				if("2".equals(mfRepayAmt.getHeJiFlag())){
					recallBase.setRecallUnpayAmt1(Double.parseDouble(mfRepayAmt.getBenJin()));
					recallBase.setRecallUnpayAmt2(Double.parseDouble(mfRepayAmt.getLiXi()));
					recallBase.setBrcContAmt(Double.parseDouble(mfRepayAmt.getFaXi()) + Double.parseDouble(mfRepayAmt.getWeiYueJin()));
					recallBase.setRecallAmt(Double.parseDouble(mfRepayAmt.getBenJin()) + Double.parseDouble(mfRepayAmt.getLiXi()) + Double.parseDouble(mfRepayAmt.getFaXi()) + Double.parseDouble(mfRepayAmt.getWeiYueJin()));
					yuqilixi= Double.parseDouble(mfRepayAmt.getYuQiLiXi());
					fulilixi= Double.parseDouble(mfRepayAmt.getFuLiLiXi());
				}
			}
		}
		//金额格式化
		dataMap.put("recallUnpayAmt1", MathExtend.moneyStr(recallBase.getRecallUnpayAmt1()));// 应还本金
		dataMap.put("recallUnpayAmt2", MathExtend.moneyStr(recallBase.getRecallUnpayAmt2()));// 应还利息
		dataMap.put("brcContAmt", MathExtend.moneyStr(recallBase.getBrcContAmt()));// 违约金
		dataMap.put("recallAmt", MathExtend.moneyStr(recallBase.getRecallAmt()));// 催收总额
		dataMap.put("putoutAmt",MathExtend.moneyStr(mfBusFincApp.getPutoutAmt()));//放款金额
		dataMap.put("loanBal",MathExtend.moneyStr(mfBusFincApp.getLoanBal()));//放款余额
		dataMap.put("yuqilixi",MathExtend.moneyStr(yuqilixi));//逾期利息
		dataMap.put("fulilixi",MathExtend.moneyStr(fulilixi));//复利利息
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		//日期格式化
		dataMap.put("putoutAppDate", DateUtil.parseDateToTenStr(sdf.parse(mfBusFincApp.getPutoutAppDate())));//放款日期
		dataMap.put("intstEndDate", DateUtil.parseDateToTenStr(sdf.parse(mfBusFincApp.getIntstEndDate())));//到期日期
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);
		recallBase.setCusName(mfCusCustomer.getCusName());
		if(mfCusCustomer.getContactsName()!=null) {
            recallBase.setCusContactName(mfCusCustomer.getContactsName());
        }
		if(mfCusCustomer.getContactsTel()!=null) {
            recallBase.setCusTel(mfCusCustomer.getContactsTel());
        }

		MfBusPact mfBusPact = new MfBusPact();
		mfBusPact.setPactId(pactId);
		mfBusPact = pactInterfaceFeign.getById(mfBusPact);
		recallBase.setAppName(mfBusPact.getAppName());
		recallBase.setConNo(mfBusPact.getPactNo());

		recallBase.setFincId(fincId);
		recallBase.setFincShowId(mfBusFincApp.getFincShowId());
		/**
		 *
		 * 应还本金、应还利息、违约金、催收总金额将来需要按照账务系统计算
		 */
		//20190310 yxl 取消任务描述
		/*recallBase.setRecallDesc(""+recallBase.getCusName()+",项目名称为:"+recallBase.getAppName()+"的贷款,"
				+"合同号:"+recallBase.getConNo()+",已经逾期,其中应还本金为:"+MathExtend.moneyStr(recallBase.getRecallUnpayAmt1())
				+",应还利息:"+MathExtend.moneyStr(recallBase.getRecallUnpayAmt2())+",违约金为:"+MathExtend.moneyStr(recallBase.getBrcContAmt())
				+",总计应催收金额为:"+MathExtend.moneyStr(recallBase.getRecallAmt())+",请尽快催收.");*/
		String recallId = WaterIdUtil.getWaterId();
		recallBase.setRecallId(recallId);

		//新增表单方式 1-供产品使用 2-供铁甲网使用
		String typeShow = recallBaseFeign.getRecallHistoryTypeShow();

		dataMap.put("pactId", pactId);
		dataMap.put("cusNo", cusNo);
		dataMap.put("fincId", fincId);

		dataMap.put("recallBase", recallBase);
		dataMap.put("mfCusCustomer", mfCusCustomer);

		dataMap.put("typeShow", typeShow);
		dataMap.put("query", "");
		dataMap.put("mfBusFincApp",mfBusFincApp);
		dataMap.put("sysProjectName", ymlConfig.getSysParams().get("sys.project.name"));


		return dataMap;


	}
	/*20190304yxl催收登记保存*/
	@ResponseBody
	@RequestMapping(value="/insertForSimuAjax1")
	public Map<String,Object> insertForSimuAjax1(String ajaxData,String tableId) throws Exception {
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>();
		MfKindForm mfKindForm = new MfKindForm();
		mfKindForm.setNodeNo("recallAdd");
		String formId = "collectionhandleadd";
		try{
			FormData formcollectionhandleadd = formService.getFormData(formId);
			getFormValue(formcollectionhandleadd, getMapByJson(ajaxData));
			if(this.validateFormDataAnchor(formcollectionhandleadd)){
				RecallBase recallBase = new RecallBase();
				setObjValue(formcollectionhandleadd, recallBase);
				recallBase.setRecallSts(BizPubParm.RECALL_STS_9);
				recallBase.setMustCompleteDays(1);
				recallBase.setStartRecallWay(BizPubParm.START_RECALL_WAY_2);
				recallBase.setRecallType(BizPubParm.RECALL_TYPE_2);
				//java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMdd");
				recallBase.setRecallExeDate(DateUtil.getDate("yyyyMMdd"));
				recallBase.setRecallEndDate(DateUtil.getDate("yyyyMMdd"));
				recallBase.setRecallDate(DateUtil.getDate("yyyyMMdd"));
				recallBase.setMustCompleteDate(DateUtil.getDate("yyyyMMdd"));
				recallBase.setRecallDate(DateUtil.getDate("yyyyMMdd"));
				MfBusPact mfBusPact = new MfBusPact();
				mfBusPact.setPactId(recallBase.getPactId());
				mfBusPact = pactInterfaceFeign.getById(mfBusPact);
				recallBase.setAppId(mfBusPact.getAppId());
				recallBaseFeign.insertForSimu1(recallBase);
				getTableData(tableId,recallBase,dataMap);//获取列表
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw e;
		}
		return dataMap;
	}
	/*20190304yxl新增催收指派*/
	@RequestMapping(value = "/collectionReassignment")
	public String collectionReassignment(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formcollectionreasg = formService.getFormData("collectionreasg");

		MfPreRepayApply mfPreRepayApply = new MfPreRepayApply();
		//mfPreRepayApply.setPenaltyAmt(0.00);
		mfPreRepayApply.setOpName(User.getRegName(request));
		mfPreRepayApply.setBrName(User.getOrgName(request));
		model.addAttribute("formcollectionreasg", formcollectionreasg);
		model.addAttribute("query", "");
		return "component/rec/RecallBase_CollectionReasg";
	}
	/*20190304yxl新增催收指派 异步保存*/
	@ResponseBody
	@RequestMapping(value="/insertAjax1")
	public Map<String,Object> insertAjax1(String ajaxData,String tableId) throws Exception {
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>();
		try{
			FormData formcollectionreasg = formService.getFormData("collectionreasg");
			getFormValue(formcollectionreasg, getMapByJson(ajaxData));
			if(this.validateFormDataAnchor(formcollectionreasg)){
				RecallBase recallBase = new RecallBase();
				setObjValue(formcollectionreasg, recallBase);
				recallBase.setRecallSts(BizPubParm.RECALL_STS_2);
				recallBase.setMustCompleteDays(Integer.parseInt(Long.toString(DateUtil.getDaysBetween(recallBase.getRecallDate(), recallBase.getRecallEndDate()))));
				recallBase.setStartRecallWay(BizPubParm.START_RECALL_WAY_2);
				recallBase.setRecallType(BizPubParm.RECALL_TYPE_2);
				MfBusPact mfBusPact = new MfBusPact();
				mfBusPact.setPactId(recallBase.getPactId());
				mfBusPact = pactInterfaceFeign.getById(mfBusPact);
				recallBase.setAppId(mfBusPact.getAppId());
				if(recallBase.getRecallEndDate().compareTo(recallBase.getRecallDate()) >= 0){
					recallBaseFeign.insert1(recallBase);
					getTableData(tableId,recallBase,dataMap);//获取列表
					dataMap.put("flag", "success");
					dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
				}else{
					dataMap.put("flag", "error");
					dataMap.put("msg", "计划开始日期不能大于计划完成日期");
				}

			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw e;
		}
		return dataMap;
	}
	/*201920307yxl条件查询催收任务*/
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value="/findByPageAjax1")
	public Map<String,Object> findByPageAjax1(String recallSts,String ajaxData,String fincId,Integer pageNo, Integer pageSize,String tableId,String tableType,Ipage ipage) throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String,Object> dataMap = new HashMap<String, Object>();
		RecallBase recallBase = new RecallBase();
		try {
			if(!StringUtil.isEmpty(recallSts)){
				recallBase.setRecallSts(recallSts);
			}
			String scopeType = "0";
			//取出ajax数据
			Gson gson = new Gson();
			JSONArray jsonArray = gson.fromJson(ajaxData, JSONArray.class);
			if(jsonArray.get(0) instanceof JSONArray){
				JSONArray jsonArraySub = jsonArray.getJSONArray(0);
				for (int i = 0; i < jsonArraySub.size(); i++) {
					JSONObject jsonObj = (JSONObject)jsonArraySub.get(i);
					if("scopeType".equals((String)jsonObj.get("condition")) &&
							StringUtil.isNotEmpty((String)jsonObj.get("value"))){
						scopeType = (String)jsonObj.get("value");
						if("0".equals(scopeType)){//全部
						}else if ("1".equals(scopeType)) {//当日到期
							recallBase.setScopeType(scopeType);
						}else if ("2".equals(scopeType)) {//超期
							recallBase.setScopeType(scopeType);
						}else {
						}
					}
				}
			}
			recallBase.setCustomQuery(ajaxData);//自定义查询参数赋值
			if("0".equals(scopeType)){
				recallBase.setCriteriaList(recallBase, ajaxData);//我的筛选
			}
			recallBase.setCustomSorts(ajaxData);
			recallBase.setFincId(fincId);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setParams(this.setIpageParams("recallBase", recallBase));
			ipage = recallBaseFeign.findByPage1(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	
}

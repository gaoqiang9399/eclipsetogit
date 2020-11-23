package  app.component.examine.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.base.User;
import app.component.examine.entity.MfExamineHis;
import app.component.examine.feign.MfExamineHisFeign;
import app.component.nmd.entity.ParmDic;
import app.tech.oscache.CodeUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.xmlbeans.impl.regex.REUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import app.component.common.EntityUtil;
import app.component.examine.entity.MfExamineDetail;
import app.component.examine.feign.MfExamineDetailFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;

/**
 * Title: MfExamineDetailAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Wed Feb 15 08:40:52 CST 2017
 **/
@Controller
@RequestMapping(value="/MfExamineDetailController")
public class MfExamineDetailController extends BaseFormBean{
	private static Logger logger = LoggerFactory.getLogger(MfExamineDetailController.class);
	//注入MfExamineDetailBo
	@Autowired
	private MfExamineDetailFeign mfExamineDetailFeign;
	
	
	private Map<String,Object> dataMap;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfExamineHisFeign mfExamineHisFeign;
	
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request,
				response);
		return "MfExamineDetail_List";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/findByPageAjax")
	public Map<String,Object> findByPageAjax(String ajaxData,int pageNo,String tableId,String tableType) throws Exception {
		ActionContext.initialize(request,
				response);
		dataMap = new HashMap<String, Object>(); 
		MfExamineDetail mfExamineDetail = new MfExamineDetail();
		try {
			mfExamineDetail.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfExamineDetail.setCriteriaList(mfExamineDetail, ajaxData);//我的筛选
			//this.getRoleConditions(mfExamineDetail,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage = mfExamineDetailFeign.findByPage(ipage, mfExamineDetail);
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
	@ResponseBody
	@RequestMapping(value="/insertAjax")
	public Map<String,Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,
				response);
		dataMap = new HashMap<String, Object>(); 
		FormService formService = new FormService();
		try{
			FormData formexamdet0002 = formService.getFormData("examdet0002");
			getFormValue(formexamdet0002, getMapByJson(ajaxData));
			if(this.validateFormData(formexamdet0002)){
				MfExamineDetail mfExamineDetail = new MfExamineDetail();
				setObjValue(formexamdet0002, mfExamineDetail);
				mfExamineDetailFeign.insert(mfExamineDetail);
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
	 * 
	 * 方法描述： 插入检查情况详情数据
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @date 2017-2-15 上午8:55:33
	 */
	@ResponseBody
	@RequestMapping(value="/insertDetailAjax")
	public Map<String,Object> insertDetailAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,
				response);
		dataMap = new HashMap<String, Object>(); 
		try{
			dataMap = getMapByJson(ajaxData);
			mfExamineDetailFeign.insertDetail(dataMap);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
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
	@ResponseBody
	@RequestMapping(value="/updateAjaxByOne")
	public Map<String,Object> updateAjaxByOne(String ajaxData) throws Exception{
		ActionContext.initialize(request,
				response);
		dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		FormData formexamdet0002 = formService.getFormData("examdet0002");
		getFormValue(formexamdet0002, getMapByJson(ajaxData));
		MfExamineDetail mfExamineDetailJsp = new MfExamineDetail();
		setObjValue(formexamdet0002, mfExamineDetailJsp);
		MfExamineDetail mfExamineDetail = mfExamineDetailFeign.getById(mfExamineDetailJsp);
		if(mfExamineDetail!=null){
			try{
				mfExamineDetail = (MfExamineDetail)EntityUtil.reflectionSetVal(mfExamineDetail, mfExamineDetailJsp, getMapByJson(ajaxData));
				mfExamineDetailFeign.update(mfExamineDetail);
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
	@ResponseBody
	@RequestMapping(value="/updateAjax")
	public Map<String,Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		dataMap = new HashMap<String, Object>(); 
		MfExamineDetail mfExamineDetail = new MfExamineDetail();
		try{
			FormData formexamdet0002 = formService.getFormData("examdet0002");
			getFormValue(formexamdet0002, getMapByJson(ajaxData));
			if(this.validateFormData(formexamdet0002)){
				mfExamineDetail = new MfExamineDetail();
				setObjValue(formexamdet0002, mfExamineDetail);
				mfExamineDetailFeign.update(mfExamineDetail);
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
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/getByIdAjax")
	public Map<String,Object> getByIdAjax(String detailId) throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String ,Object > formData = new HashMap<String ,Object>();
		FormService formService = new FormService();
		dataMap = new HashMap<String, Object>(); 
		FormData formexamdet0002 = formService.getFormData("examdet0002");
		MfExamineDetail mfExamineDetail = new MfExamineDetail();
		mfExamineDetail.setDetailId(detailId);
		mfExamineDetail = mfExamineDetailFeign.getById(mfExamineDetail);
		getObjValue(formexamdet0002, mfExamineDetail,formData);
		if(mfExamineDetail!=null){
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
	@ResponseBody
	@RequestMapping(value="/deleteAjax")
	public Map<String,Object> deleteAjax(String detailId) throws Exception{
		ActionContext.initialize(request,
				response);
		dataMap = new HashMap<String, Object>(); 
		MfExamineDetail mfExamineDetail = new MfExamineDetail();
		mfExamineDetail.setDetailId(detailId);
		try {
			mfExamineDetailFeign.delete(mfExamineDetail);
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
	@RequestMapping(value="/input")
	public String input(Model model) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formexamdet0002 = formService.getFormData("examdet0002");
		model.addAttribute("formexamdet0002", formexamdet0002);
		return "MfExamineDetail_Insert";
	}
	/***
	 * 新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/insert")
	public String insert(Model model) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		 FormData formexamdet0002 = formService.getFormData("examdet0002");
		 getFormValue(formexamdet0002);
		 MfExamineDetail mfExamineDetail = new MfExamineDetail();
		 setObjValue(formexamdet0002, mfExamineDetail);
		 mfExamineDetailFeign.insert(mfExamineDetail);
		 getObjValue(formexamdet0002, mfExamineDetail);
		 this.addActionMessage(model,"保存成功");
		 List<MfExamineDetail> mfExamineDetailList = (List<MfExamineDetail>)mfExamineDetailFeign.findByPage(this.getIpage(), mfExamineDetail).getResult();
		 model.addAttribute("mfExamineDetail", mfExamineDetail);
		 model.addAttribute("mfExamineDetailList", mfExamineDetailList);
		return "MfExamineDetail_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getById")
	public String getById(Model model,String detailId) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formexamdet0001 = formService.getFormData("examdet0001");
		 getFormValue(formexamdet0001);
		 MfExamineDetail mfExamineDetail = new MfExamineDetail();
		mfExamineDetail.setDetailId(detailId);
		 mfExamineDetail = mfExamineDetailFeign.getById(mfExamineDetail);
		 getObjValue(formexamdet0001, mfExamineDetail);
		 model.addAttribute("mfExamineDetail", mfExamineDetail);
		return "MfExamineDetail_Detail";
	}
	/**
	 * 删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public String delete(String detailId) throws Exception {
		ActionContext.initialize(request,
				response);
		MfExamineDetail mfExamineDetail = new MfExamineDetail();
		mfExamineDetail.setDetailId(detailId);
		mfExamineDetailFeign.delete(mfExamineDetail);
		return getListPage();
	}
	/**
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/validateInsert")
	public void validateInsert() throws Exception{
		 ActionContext.initialize(request, response);
		 FormService formService = new FormService();
		 FormData formexamdet0002 = formService.getFormData("examdet0002");
		 getFormValue(formexamdet0002);
		 boolean validateFlag = this.validateFormData(formexamdet0002);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/validateUpdate")
	public void validateUpdate() throws Exception{
		 ActionContext.initialize(request, response);
		 FormService formService = new FormService();
		 FormData formexamdet0002 = formService.getFormData("examdet0002");
		 getFormValue(formexamdet0002);
		 boolean validateFlag = this.validateFormData(formexamdet0002);
	}
	/**
	 * @Description 跳转到贷后检查登记列表页面
	 * @Author zhaomingguang
	 * @DateTime 2019/9/25 9:19
	 * @Param [model]
	 * @return java.lang.String
	 */
	@RequestMapping(value = "/getExamineRecordListPage")
	public String getExamineRecordListPage(Model model){
		ActionContext.initialize(request, response);
		try {
			//检查主体
			JSONArray examineDataObjJsonArray = new CodeUtils().getJSONArrayByKeyName("EXAMINE_DATA_OBJ");
			model.addAttribute("examineDataObjJsonArray", examineDataObjJsonArray);
			//检查结果
			JSONArray riskLevelJsonArray = new CodeUtils().getJSONArrayByKeyName("EXAM_RISK_LEVEL");
			model.addAttribute("riskLevelJsonArray", riskLevelJsonArray);
			//五级分类
			JSONArray fiveClassJsonArray = new CodeUtils().getJSONArrayByKeyName("FIVE_STS");
			model.addAttribute("fiveClassJsonArray", fiveClassJsonArray);
			//是否逾期
			JSONArray isOverdueJsonArray = new CodeUtils().getJSONArrayByKeyName("YES_NO");
			model.addAttribute("isOverdueJsonArray", isOverdueJsonArray);
			//检查状态
			JSONArray examStsJsonArray = new CodeUtils().getJSONArrayByKeyName("EXAM_STS");
			model.addAttribute("examStsJsonArray", examStsJsonArray);

			model.addAttribute("query", "");
		}catch (Exception e){
			logger.error("跳转到贷后检查登记列表页面"+e.getMessage(),e);
		}
		return "component/examine/MfExamineDetail_examineRecordList";
	}

	/**
	 * @Description 跳转到实地核查登记列表页面
	 * @Param [model]
	 * @return java.lang.String
	 */
	@RequestMapping(value = "/getAuditListPage")
	public String getAuditListPage(Model model){
		ActionContext.initialize(request, response);
		try {
			//检查主体
			JSONArray examineDataObjJsonArray = new CodeUtils().getJSONArrayByKeyName("EXAMINE_DATA_OBJ");
			model.addAttribute("examineDataObjJsonArray", examineDataObjJsonArray);
			//检查结果
			JSONArray riskLevelJsonArray = new CodeUtils().getJSONArrayByKeyName("EXAM_RISK_LEVEL");
			model.addAttribute("riskLevelJsonArray", riskLevelJsonArray);
			//五级分类
			JSONArray fiveClassJsonArray = new CodeUtils().getJSONArrayByKeyName("FIVE_STS");
			model.addAttribute("fiveClassJsonArray", fiveClassJsonArray);
			//是否逾期
			JSONArray isOverdueJsonArray = new CodeUtils().getJSONArrayByKeyName("YES_NO");
			model.addAttribute("isOverdueJsonArray", isOverdueJsonArray);
			//检查状态
			JSONArray examStsJsonArray = new CodeUtils().getJSONArrayByKeyName("EXAM_STS");
			model.addAttribute("examStsJsonArray", examStsJsonArray);

			model.addAttribute("query", "");
		}catch (Exception e){
			logger.error("跳转到实地核查登记列表页面"+e.getMessage(),e);
		}
		return "component/examine/MfExamineHis_auditList";
	}

	/**
	 * @Description 获取实地核查登记列表数据
	 * @Param [ajaxData, pageNo, tableId, tableType]
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 */
	@RequestMapping(value = "/findAuditByPageAjax")
	@ResponseBody
	public Map<String,Object> findAuditByPageAjax(String ajaxData,int pageNo,String tableId,String tableType){
		ActionContext.initialize(request, response);
		dataMap = new HashMap<String, Object>();
		MfExamineHis mfExamineHis = new MfExamineHis();
		try {
			mfExamineHis.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfExamineHis.setCriteriaList(mfExamineHis, ajaxData);//我的筛选
			mfExamineHis.setExamineType("2");//实地核查
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setParams(setIpageParams("mfExamineHis",mfExamineHis));
			//自定义查询Bo方法
			ipage = mfExamineHisFeign.findByPageByApp(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
		} catch (Exception e) {
			logger.error("获取贷后检查登记列表数据"+e.getMessage(),e);
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
		}
		return dataMap;
	}

	/**
	 * @Description 获取贷后检查登记列表数据
	 * @Author zhaomingguang
	 * @DateTime 2019/9/25 9:32
	 * @Param [ajaxData, pageNo, tableId, tableType]
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 */
	@RequestMapping(value = "/findExamineRecordByPageAjax")
	@ResponseBody
	public Map<String,Object> findExamineRecordByPageAjax(String ajaxData,int pageNo,String tableId,String tableType){
		ActionContext.initialize(request, response);
		dataMap = new HashMap<String, Object>();
		MfExamineHis mfExamineHis = new MfExamineHis();
		try {
			mfExamineHis.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfExamineHis.setCriteriaList(mfExamineHis, ajaxData);//我的筛选
			mfExamineHis.setExamineType("0");
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setParams(setIpageParams("mfExamineHis",mfExamineHis));
			//自定义查询Bo方法
			ipage = mfExamineHisFeign.findByPageByApp(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
		} catch (Exception e) {
			logger.error("获取贷后检查登记列表数据"+e.getMessage(),e);
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
		}
		return dataMap;
	}

	/**
	 * @Description 跳转到新增贷后检查详情页面
	 * @Author zhaomingguang
	 * @DateTime 2019/9/26 15:41
	 * @Param [model]
	 * @return java.lang.String
	 */
	@RequestMapping(value = "/addExamineDetail")
	public String addExamineDetail(Model model){
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		try {
			String formId = "examineDetail";
			FormData formapply0007_query = formService.getFormData(formId);
			model.addAttribute("formapply0007_query", formapply0007_query);
			model.addAttribute("query", "");
		}catch (Exception e){
			logger.error("跳转到新增贷后检查详情页面"+e.getMessage(),e);
		}
		return "component/examine/MfExamineDetail_examineRecordInsert";
	}

	/**
	 * @Description 贷后检查情况新增信息保存
	 * @Author zhaomingguang
	 * @DateTime 2019/9/26 15:50
	 * @Param [ajaxData]
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 */
	@RequestMapping(value = "/insertForExamineDetailAjax")
	@ResponseBody
	public Map<String,Object> insertForExamineDetailAjax(String ajaxData){
		Map<String,Object> dataMap = new HashMap<>();
		try {
			Map<String,Object> paraMap = getMapByJson(ajaxData);
			paraMap.put("regName",User.getRegName(request));
			paraMap.put("regNo",User.getRegNo(request));
			Map<String,Object> resultMap = mfExamineDetailFeign.insertForExamineDetailAjax(paraMap);
			dataMap.putAll(resultMap);
		}catch (Exception e){
			logger.error("贷后检查情况新增信息保存"+e.getMessage(),e);
			dataMap.put("flag","error");
			dataMap.put("msg",e.getMessage());
		}
		return dataMap;
	}

	/**
	 * @Description 跳转到贷后检查情况详情页面
	 * @Author zhaomingguang
	 * @DateTime 2019/9/26 17:13
	 * @Param [examHisId]
	 * @return java.lang.String
	 */
	@RequestMapping(value = "getExamineDetailPage")
	public String getExamineDetailPage(String examHisId,Model model){
		model.addAttribute("query","");
		model.addAttribute("examHisId",examHisId);
		return "component/examine/MfExamineDetail_examineRecordDetail";
	}

	/**
	 * @Description 获取贷后检查详情数据
	 * @Author zhaomingguang
	 * @DateTime 2019/9/26 18:51
	 * @Param [ajaxData, pageNo, tableId, tableType]
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 */
	@RequestMapping(value = "/findExamineDetailByAjax")
	@ResponseBody
	public Map<String,Object> findExamineDetailByAjax(String ajaxData,int pageNo,
													  String tableId,String tableType,String examHisId){
		ActionContext.initialize(request, response);
		dataMap = new HashMap<String, Object>();
		try {
			Ipage ipage = this.getIpage();
			Map<String,Object> paraMap = new HashMap<>();
			paraMap.put("examHisId",examHisId);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setParams(paraMap);
			//自定义查询Bo方法
			ipage = mfExamineDetailFeign.findExamineDetailByAjax(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
		} catch (Exception e) {
			logger.error("获取贷后检查详情数据"+e.getMessage(),e);
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
		}
		return dataMap;
	}
	
	
}

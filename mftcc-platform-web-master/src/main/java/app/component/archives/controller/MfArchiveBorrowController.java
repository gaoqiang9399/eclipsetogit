package app.component.archives.controller;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.base.User;
import app.component.archives.entity.ArchiveInfoMain;
import app.component.archives.feign.ArchiveBorrowFeign;
import app.component.archives.feign.ArchiveInfoMainFeign;
import app.component.common.EntityUtil;
import app.component.archives.entity.MfArchiveBorrow;
import app.component.pact.repay.entity.MfPreRepayApply;
import app.component.pact.repay.entity.MfPrepaymentBean;
import app.component.sys.entity.SysUser;
import app.component.sys.feign.SysUserFeign;
import app.component.wkf.entity.Result;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.WaterIdUtil;
import com.core.domain.screen.FormData;
import com.core.domain.screen.OptionsList;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;
import com.google.gson.Gson;
import com.itextpdf.text.log.SysoCounter;
import net.sf.json.JSONArray;
import org.apache.jasper.tagplugins.jstl.core.ForEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Title: MfArchiveBorrowController.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Wed Aug 15 09:33:43 CST 2018
 **/
@Controller
@RequestMapping("/mfArchiveBorrow")
public class MfArchiveBorrowController extends BaseFormBean{
	//private static final long serialVersionUID = 9196454891709523438L;
	@Autowired
	private ArchiveBorrowFeign archiveBorrowFeign;
	@Autowired
	private SysUserFeign sysUserFeign;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;
	@Autowired
	private ArchiveInfoMainFeign archiveInfoMainFeign;
	/**
	 * 获取列表数据
	 * @return
	 * @throws Exception
	 */
	private void getTableData(String tableId,MfArchiveBorrow	mfArchiveBorrow) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String  tableHtml = jtu.getJsonStr(tableId,"tableTag", archiveBorrowFeign.getAll(mfArchiveBorrow), null,true);
		Map<String,String>dataMap = new HashMap<String,String>();
		dataMap.put("tableData",tableHtml);
	}
   //显示审批页面
	@RequestMapping(value = "/getPreRepayApproval")
	public String getPreRepayApproval(Model model,String borrowId, String activityType,String hideOpinionType) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(borrowId, null);// 当前审批节点task
		String scNo = taskAppro.getActivityName();// 要件场景号，使用审批节点具体编号
		String nodeNo = taskAppro.getActivityName();// 审批流程中当前审批节点编号
		FormData formprerepayapplyadd = formService.getFormData("mfarchiveborrowApproval");
		MfArchiveBorrow	mfArchiveBorrow = new MfArchiveBorrow();
		mfArchiveBorrow.setBorrowId(borrowId);
		mfArchiveBorrow = archiveBorrowFeign.getById(mfArchiveBorrow);
		getObjValue(formprerepayapplyadd, mfArchiveBorrow);
		this.changeFormProperty(formprerepayapplyadd, "appTime", "initValue", mfArchiveBorrow.getApplyTime());
		// 处理审批意见类型
		// 处理审批意见类型
		Map<String, String> opinionTypeMap = new HashMap<String, String>();
		opinionTypeMap.put("hideOpinionType", hideOpinionType); // 隐藏审批类型
		List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType,
				taskAppro.getCouldRollback(), opinionTypeMap);
		this.changeFormProperty(formprerepayapplyadd, "opinionType", "optionArray", opinionTypeList);
		// 获得当前审批岗位前面审批过得岗位信息
		JSONArray befNodesjsonArray = new JSONArray();
		befNodesjsonArray = wkfInterfaceFeign.getBefNodes(borrowId, null);
		request.setAttribute("scNo", scNo);
		request.setAttribute("nodeNo", nodeNo);
		request.setAttribute("befNodesjsonArray", befNodesjsonArray);
		model.addAttribute("formmfarchiveborrowApproval", formprerepayapplyadd);
		model.addAttribute("mfArchiveBorrow",mfArchiveBorrow);
		model.addAttribute("query", "");
		return "component/archives/MfArchiveBorrow_approve";
	}


	//审批提交
	@RequestMapping(value = "/submitUpdateAjax")
	@ResponseBody
	public Map<String, Object> submitUpdateAjax(String ajaxData, String taskId, String appNo, String transition, String nextUser) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map map = getMapByJson(ajaxData);
		String formId = (String) map.get("formId");
		FormData formprerepayapplyadd = formService.getFormData(formId);
		getFormValue(formprerepayapplyadd, map);
		MfPreRepayApply mfPreRepayApply = new MfPreRepayApply();
		setObjValue(formprerepayapplyadd, mfPreRepayApply);
		Result res;
		try {
			dataMap = getMapByJson(ajaxData);
			dataMap.put("orgNo", User.getOrgNo(request));
			String opinionType = String.valueOf(dataMap.get("opinionType"));
			String approvalOpinion = String.valueOf(dataMap.get("approvalOpinion"));
			res = archiveBorrowFeign.doCommit(taskId, appNo, opinionType, approvalOpinion, transition,
					User.getRegNo(this.getHttpRequest()), nextUser, mfPreRepayApply);
			if (res.isSuccess()) {
				dataMap.put("flag", "success");
				if (res.isEndSts()) {
					dataMap.put("msg", res.getMsg());
				} else {
					dataMap.put("msg", res.getMsg());
				}
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SUBMIT.getMessage());
			throw e;
		}
		return dataMap;
	}

	//信息登记
	@RequestMapping(value = "/addregister")
	@ResponseBody
	public Map<String, Object> addregister(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		int date=0;
		try{
			FormData formmfarchiveborrowbase = formService.getFormData("mfarchiveborrowRegister");
			getFormValue(formmfarchiveborrowbase, getMapByJson(ajaxData));
			if(this.validateFormData(formmfarchiveborrowbase)){
				MfArchiveBorrow	mfArchiveBorrow = new MfArchiveBorrow();
				setObjValue(formmfarchiveborrowbase, mfArchiveBorrow);
				String ds=mfArchiveBorrow.getBorrowingTime();
				String ds2=mfArchiveBorrow.getRevertPlanDate();
				date= DateUtil.compareEightDate(ds,ds2);
				mfArchiveBorrow.setBorrowSts("4");
				if(date!=1) {
					archiveBorrowFeign.update(mfArchiveBorrow);
					dataMap.put("flag", "success");
					dataMap.put("msg", "登记成功");
				}else{
					dataMap.put("flag", "error");
					dataMap.put("msg", "登记失败，借阅日期不能晚于计划归还日期");
				}
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			dataMap.put("flag", "error");
			dataMap.put("msg", "登记失败");
			throw e;
		}
		return dataMap;
	}

	//登记页面
	@RequestMapping(value = "/register")
	public String register(Model model,String borrowId) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formmfarchiveborrowbase = formService.getFormData("mfarchiveborrowRegister");
		MfArchiveBorrow	mfArchiveBorrow = new MfArchiveBorrow();
		mfArchiveBorrow.setBorrowId(borrowId);
		mfArchiveBorrow.setBorrowingTime(User.getCurrDate());
		MfArchiveBorrow getinfo=archiveBorrowFeign.getById(mfArchiveBorrow);
		mfArchiveBorrow.setBorrowerName(getinfo.getApplyUserName());
		mfArchiveBorrow.setBorrowingPurposes(getinfo.getApplyCause());
		if(getinfo.getApplyUserNo()!=""&&getinfo.getApplyUserNo()!=null){
			mfArchiveBorrow.setBorrowerNo(getinfo.getApplyUserNo());
		}
		getObjValue(formmfarchiveborrowbase, mfArchiveBorrow);
		model.addAttribute("formmfarchiveborrowRegister", formmfarchiveborrowbase);
		model.addAttribute("query", "");
		return "component/archives/MfArchiveBorrow_register";
	}
	//归还页面
	@RequestMapping(value = "/revert")
	public String revert(Model model,String borrowId) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formmfarchiveborrowbase = formService.getFormData("mfarchiveborrowrevert");
		MfArchiveBorrow	mfArchiveBorrow = new MfArchiveBorrow();
        mfArchiveBorrow.setBorrowId(borrowId);
        mfArchiveBorrow.setRevertActualTime(User.getCurrDate());
        MfArchiveBorrow getinfo=archiveBorrowFeign.getById(mfArchiveBorrow);
        mfArchiveBorrow.setRevertName(getinfo.getBorrowerName());
        if(getinfo.getBorrowerNo()!=""&&getinfo.getBorrowerNo()!=null){
			mfArchiveBorrow.setRevertNo(getinfo.getBorrowerNo());
		}
		getObjValue(formmfarchiveborrowbase, mfArchiveBorrow);
		model.addAttribute("formmfarchiveBorrowRevert", formmfarchiveborrowbase);
		model.addAttribute("query", "");
		return "component/archives/MfArchiveBorrow_revert";
	}

    //归还
    @RequestMapping(value = "/insertrevert")
    @ResponseBody
    public Map<String, Object> insertrevert(String ajaxData) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request,response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        int date=0;
        try{
            FormData formmfarchiveborrowbase = formService.getFormData("mfarchiveborrowrevert");
            getFormValue(formmfarchiveborrowbase, getMapByJson(ajaxData));
            if(this.validateFormData(formmfarchiveborrowbase)){
                MfArchiveBorrow	mfArchiveBorrow = new MfArchiveBorrow();
                setObjValue(formmfarchiveborrowbase, mfArchiveBorrow);
				String ds=DateUtil.getShowDateTime(User.getCurrDate());
				String ds1=DateUtil.getShowDateTime(mfArchiveBorrow.getRevertActualTime());
				date= DateUtil.compareDate(ds,ds1);
				if(date==1||date==0){
					mfArchiveBorrow.setBorrowSts("5");
					mfArchiveBorrow.setArchiveno("7");
					archiveBorrowFeign.update(mfArchiveBorrow);
					dataMap.put("flag", "success");
					dataMap.put("msg", "归还成功");
				}else{
					dataMap.put("flag", "error");
					dataMap.put("msg","实际归还时间不得晚于当前时间");
				}
            }else{
                dataMap.put("flag", "error");
                dataMap.put("msg",this.getFormulavaliErrorMsg());
            }
        }catch(Exception e){
            dataMap.put("flag", "error");
            dataMap.put("msg", "归还失败");
            throw e;
        }
        return dataMap;
    }


    /**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request,response);
		CodeUtils codeUtils = new CodeUtils();
		JSONArray cusTypeJsonArray = codeUtils.getJSONArrayByKeyName("borrow_sts");
		this.getHttpRequest().setAttribute("cusTypeJsonArray", cusTypeJsonArray);
		model.addAttribute("query", "");
		return "component/archives/MfArchiveBorrow_List";
	}
	/**
	 * 列表全部无翻页
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListAll")
	public String getListAll(Model model, String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData formdl_archive_main02 = formService.getFormData("tablemfarchiveborrowlist");
		MfArchiveBorrow	mfArchiveBorrow = new MfArchiveBorrow();
		List<MfArchiveBorrow> archiveInfoMainList = archiveBorrowFeign.getAll(mfArchiveBorrow);
		model.addAttribute("formdl_archive_main02", formdl_archive_main02);
		model.addAttribute("archiveInfoMainList", archiveInfoMainList);
		model.addAttribute("query", "");
		return "component/archives/MfArchiveBorrow_List";
	}

	//选择组件合同查詢
	@RequestMapping(value = "/getarchiveInfo")
	@ResponseBody
	public Map<String, Object> getarchiveInfo(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ArchiveInfoMain archiveInfoMain = new ArchiveInfoMain();
		try {
			archiveInfoMain.setCustomQuery(ajaxData);
			archiveInfoMain.setCustomSorts(ajaxData);
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setParams(this.setIpageParams("archiveInfoMain", archiveInfoMain));
			//自定义查询Bo方法
			ipage = archiveBorrowFeign.getarchive(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId, tableType, (List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("success", false);
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfArchiveBorrow	mfArchiveBorrow = new MfArchiveBorrow();
		try {
			mfArchiveBorrow.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfArchiveBorrow.setCriteriaList(mfArchiveBorrow, ajaxData);//我的筛选
			mfArchiveBorrow.setCustomSorts(ajaxData);//自定义排序
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setPageSize(pageSize);//异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfArchiveBorrow",mfArchiveBorrow));
			//自定义查询Bo方法
			ipage = archiveBorrowFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	/**
	 * AJAX申请新增
	 * @return
	 * @throws Exception archiveInfoMain
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData,String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try{
			FormData formmfarchiveborrowbase = formService.getFormData("mfarchiveborrowbase");
			getFormValue(formmfarchiveborrowbase, getMapByJson(ajaxData));
			if(this.validateFormData(formmfarchiveborrowbase)){
				MfArchiveBorrow	mfArchiveBorrow = new MfArchiveBorrow();
				mfArchiveBorrow.setBorrowId(WaterIdUtil.getWaterId());
				setObjValue(formmfarchiveborrowbase, mfArchiveBorrow);
				//默认申请中
				mfArchiveBorrow.setBorrowSts("1");
				//Archiveno sql条件中的业务处理 需默认设置
				mfArchiveBorrow.setArchiveno("1");
				//添加系统默认字段
				mfArchiveBorrow.setLstModTime(DateUtil.getDateTime());
                mfArchiveBorrow.setRegTime(DateUtil.getDateTime());
				mfArchiveBorrow.setOpNo(User.getRegNo(request));
				mfArchiveBorrow.setOpName(User.getRegName(request));
				mfArchiveBorrow.setBrName(User.getOrgName(request));
				mfArchiveBorrow.setBrNo(User.getOrgNo(request));
				archiveBorrowFeign.insert(mfArchiveBorrow);
				mfArchiveBorrow=archiveBorrowFeign.submitProcess(mfArchiveBorrow);
				archiveBorrowFeign.updatear(mfArchiveBorrow);
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("userRole", mfArchiveBorrow.getApproveNodeName());
				paramMap.put("opNo", mfArchiveBorrow.getApprovePartName());
				dataMap.put("msg", MessageEnum.SUCCEED_APPROVAL.getMessage(paramMap));
				dataMap.put("flag", "success");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
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
	public  Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception{
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfArchiveBorrow	mfArchiveBorrow = new MfArchiveBorrow();
		FormService formService = new FormService();
		FormData formmfarchiveborrowbase = formService.getFormData("mfarchiveborrowbase");
		getFormValue(formmfarchiveborrowbase, getMapByJson(ajaxData));
		MfArchiveBorrow mfArchiveBorrowJsp = new MfArchiveBorrow();
		setObjValue(formmfarchiveborrowbase, mfArchiveBorrowJsp);
		mfArchiveBorrow = archiveBorrowFeign.getById(mfArchiveBorrowJsp);
		if(mfArchiveBorrow!=null){
			try{
				mfArchiveBorrow = (MfArchiveBorrow)EntityUtil.reflectionSetVal(mfArchiveBorrow, mfArchiveBorrowJsp, getMapByJson(ajaxData));
				archiveBorrowFeign.update(mfArchiveBorrow);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			}catch(Exception e){
				dataMap.put("flag", "error");
				dataMap.put("msg", "新增失败");
				throw e;
			}
		}else{
			dataMap.put("flag", "error");
			dataMap.put("msg", "编号不存在,保存失败");
		}
		return dataMap;
	}
	/**
	 * AJAX更新保存
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjax")
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfArchiveBorrow	mfArchiveBorrow = new MfArchiveBorrow();
		try{
			FormService formService = new FormService();
			FormData formmfarchiveborrowbase = formService.getFormData("mfarchiveborrowbase");
			getFormValue(formmfarchiveborrowbase, getMapByJson(ajaxData));
			if(this.validateFormData(formmfarchiveborrowbase)){
				mfArchiveBorrow = new MfArchiveBorrow();
				setObjValue(formmfarchiveborrowbase, mfArchiveBorrow);
				archiveBorrowFeign.update(mfArchiveBorrow);
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
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
	public Map<String, Object> getByIdAjax(String borrowId) throws Exception {
		ActionContext.initialize(request,response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		FormData formmfarchiveborrowbase = formService.getFormData("mfarchiveborrowbase");
		MfArchiveBorrow	mfArchiveBorrow = new MfArchiveBorrow();
		mfArchiveBorrow.setBorrowId(borrowId);
		mfArchiveBorrow = archiveBorrowFeign.getById(mfArchiveBorrow);
		getObjValue(formmfarchiveborrowbase, mfArchiveBorrow,formData);
		if(mfArchiveBorrow!=null){
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
	public Map<String, Object> deleteAjax(String borrowId) throws Exception{
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfArchiveBorrow	mfArchiveBorrow = new MfArchiveBorrow();
		mfArchiveBorrow.setBorrowId(borrowId);
		try {
			archiveBorrowFeign.delete(mfArchiveBorrow);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
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
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formmfarchiveborrowbase = formService.getFormData("mfarchiveborrowbase");
		MfArchiveBorrow	mfArchiveBorrow = new MfArchiveBorrow();
		mfArchiveBorrow.setApplyTime(DateUtil.getDateTime());//获取系统当前时间
		getObjValue(formmfarchiveborrowbase, mfArchiveBorrow);
		model.addAttribute("formmfarchiveborrowbase", formmfarchiveborrowbase);
		model.addAttribute("query", "");
		return "component/archives/MfArchiveBorrow_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(String borrowId) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formmfarchiveborrowdetail = formService.getFormData("mfarchiveborrowdetail");
		 getFormValue(formmfarchiveborrowdetail);
		MfArchiveBorrow	mfArchiveBorrow = new MfArchiveBorrow();
		mfArchiveBorrow.setBorrowId(borrowId);
		 mfArchiveBorrow = archiveBorrowFeign.getById(mfArchiveBorrow);
		 getObjValue(formmfarchiveborrowdetail, mfArchiveBorrow);
		return "MfArchiveBorrow_Detail";
	}

	/**
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	public void validateInsert() throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formmfarchiveborrowbase = formService.getFormData("mfarchiveborrowbase");
		 getFormValue(formmfarchiveborrowbase);
		 boolean validateFlag = this.validateFormData(formmfarchiveborrowbase);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	public void validateUpdate() throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formmfarchiveborrowbase = formService.getFormData("mfarchiveborrowbase");
		 getFormValue(formmfarchiveborrowbase);
		 boolean validateFlag = this.validateFormData(formmfarchiveborrowbase);
	}
}

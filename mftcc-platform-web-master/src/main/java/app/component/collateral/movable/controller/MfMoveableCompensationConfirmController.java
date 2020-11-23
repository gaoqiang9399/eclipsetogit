package  app.component.collateral.movable.controller;
import java.io.File;
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

import app.component.collateral.entity.MfCollateralFormConfig;
import app.component.collateral.entity.PledgeBaseInfo;
import app.component.collateral.entity.PledgeBaseInfoBill;
import app.component.collateral.feign.MfCollateralFormConfigFeign;
import app.component.collateral.feign.PledgeBaseInfoFeign;
import app.component.collateral.movable.entity.MfMoveableCompensationConfirm;
import app.component.collateral.movable.feign.MfMoveableCompensationConfirmFeign;
import app.component.common.EntityUtil;
import app.component.finance.util.CustomException;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.WaterIdUtil;

/**
 * Title: MfMoveableCompensationConfirmAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Thu Jun 15 18:43:41 CST 2017
 **/
@Controller
@RequestMapping("/mfMoveableCompensationConfirm")
public class MfMoveableCompensationConfirmController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	//注入MfMoveableCompensationConfirmBo
	@Autowired
	private MfMoveableCompensationConfirmFeign mfMoveableCompensationConfirmFeign;
	@Autowired
	private MfCollateralFormConfigFeign mfCollateralFormConfigFeign;
	@Autowired
	private PledgeBaseInfoFeign pledgeBaseInfoFeign;
	//全局变量
	//异步参数
	//表单变量
	
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		 return "/component/collateral/movable/MfMoveableCompensationConfirm_List";
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
		MfMoveableCompensationConfirm mfMoveableCompensationConfirm = new MfMoveableCompensationConfirm();
		try {
			mfMoveableCompensationConfirm.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfMoveableCompensationConfirm.setCriteriaList(mfMoveableCompensationConfirm, ajaxData);//我的筛选
			//mfMoveableCompensationConfirm.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfMoveableCompensationConfirm,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage = mfMoveableCompensationConfirmFeign.findByPage(ipage, mfMoveableCompensationConfirm);
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
		FormData 	formmovablecompensationconf0002 = formService.getFormData("movablecompensationconf0002");
			getFormValue(formmovablecompensationconf0002, getMapByJson(ajaxData));
			if(this.validateFormData(formmovablecompensationconf0002)){
		MfMoveableCompensationConfirm mfMoveableCompensationConfirm = new MfMoveableCompensationConfirm();
				setObjValue(formmovablecompensationconf0002, mfMoveableCompensationConfirm);
				mfMoveableCompensationConfirmFeign.insert(mfMoveableCompensationConfirm);
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
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
		FormData formmovablecompensationconf0002 = formService.getFormData("movablecompensationconf0002");
		getFormValue(formmovablecompensationconf0002, getMapByJson(ajaxData));
		MfMoveableCompensationConfirm mfMoveableCompensationConfirmJsp = new MfMoveableCompensationConfirm();
		setObjValue(formmovablecompensationconf0002, mfMoveableCompensationConfirmJsp);
		MfMoveableCompensationConfirm mfMoveableCompensationConfirm = mfMoveableCompensationConfirmFeign.getById(mfMoveableCompensationConfirmJsp);
		if(mfMoveableCompensationConfirm!=null){
			try{
				mfMoveableCompensationConfirm = (MfMoveableCompensationConfirm)EntityUtil.reflectionSetVal(mfMoveableCompensationConfirm, mfMoveableCompensationConfirmJsp, getMapByJson(ajaxData));
				mfMoveableCompensationConfirmFeign.update(mfMoveableCompensationConfirm);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			}catch(Exception e){
				e.printStackTrace();
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
	 * ajax 上传货物详情
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/export")
	public String export(Model model, String ajaxData) throws Exception{
		 return "/component/collateral/movable/MfMoveableCompensationConfirm_export";
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
		FormData 	formmovablecompensationconf0002 = formService.getFormData("movablecompensationconf0002");
			getFormValue(formmovablecompensationconf0002, getMapByJson(ajaxData));
			if(this.validateFormData(formmovablecompensationconf0002)){
		MfMoveableCompensationConfirm mfMoveableCompensationConfirm = new MfMoveableCompensationConfirm();
				setObjValue(formmovablecompensationconf0002, mfMoveableCompensationConfirm);
				mfMoveableCompensationConfirmFeign.update(mfMoveableCompensationConfirm);
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw e;
		}
		return dataMap;
	}

	/**
	 * AJAX获取查看
	 * @param confirmId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String ajaxData, String confirmId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formmovablecompensationconf0002 = formService.getFormData("movablecompensationconf0002");
		MfMoveableCompensationConfirm mfMoveableCompensationConfirm = new MfMoveableCompensationConfirm();
		mfMoveableCompensationConfirm.setConfirmId(confirmId);
		mfMoveableCompensationConfirm = mfMoveableCompensationConfirmFeign.getById(mfMoveableCompensationConfirm);
		getObjValue(formmovablecompensationconf0002, mfMoveableCompensationConfirm,formData);
		if(mfMoveableCompensationConfirm!=null){
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}
	/**
	 * Ajax异步删除
	 * @param confirmId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String ajaxData, String confirmId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfMoveableCompensationConfirm mfMoveableCompensationConfirm = new MfMoveableCompensationConfirm();
		mfMoveableCompensationConfirm.setConfirmId(confirmId);
		try {
			mfMoveableCompensationConfirmFeign.delete(mfMoveableCompensationConfirm);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw e;
		}
		return dataMap;
	}
	/**
	 * 新增页面
	 * @param busPleId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model, String busPleId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData formmovablecompensationconf0002 = formService.getFormData("movablecompensationconf0002");
		MfMoveableCompensationConfirm mfMoveableCompensationConfirm = new MfMoveableCompensationConfirm();
		mfMoveableCompensationConfirm.setBusPleId(busPleId);
		mfMoveableCompensationConfirm= mfMoveableCompensationConfirmFeign.input(mfMoveableCompensationConfirm);
		getObjValue(formmovablecompensationconf0002, mfMoveableCompensationConfirm);
		model.addAttribute("formmovablecompensationconf0002", formmovablecompensationconf0002);
		model.addAttribute("query", "");
		 return "/component/collateral/movable/MfMoveableCompensationConfirm_Insert";
	}
	/**
	 * 货物明细新增页面
	 * @param pledgeNo 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/billInfoinput")
	public String billInfoinput(Model model, String ajaxData, String pledgeNo) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
	PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
	pledgeBaseInfo.setPledgeNo(pledgeNo);
	pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
	String classId = pledgeBaseInfo.getClassId();
	
	MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign.getByPledgeImPawnType(classId,"PledgeBaseInfoBillAction","");
	String formId="";
	if(mfCollateralFormConfig == null){
	}else{
		formId = mfCollateralFormConfig.getAddModelDef();
	}
	if(StringUtil.isEmpty(formId)){
//		Log.error("押品类型为"+mfCollateralFormConfig.getFormType()+"的PledgeBaseInfoBillAction表单信息没有查询到");
	}else{
		PledgeBaseInfoBill pledgeBaseInfoBill = new PledgeBaseInfoBill();
		pledgeBaseInfoBill.setPledgeNo(pledgeNo);
		String pledgeBillNo=WaterIdUtil.getWaterId();
		pledgeBaseInfoBill.setPledgeBillNo(pledgeBillNo);
		pledgeBaseInfoBill.setClassId(classId);
		FormData formdlpledgebaseinfobill0002 = formService.getFormData(formId);
		if(formdlpledgebaseinfobill0002.getFormId() == null){
//			Log.error("押品类型为"+mfCollateralFormConfig.getFormType()+"的PledgeBaseInfoBillAction表单form"+formId+".xml文件不存在");
		}else{
			getFormValue(formdlpledgebaseinfobill0002);
			getObjValue(formdlpledgebaseinfobill0002,pledgeBaseInfoBill);
			model.addAttribute("formdlpledgebaseinfobill0002", formdlpledgebaseinfobill0002);
		}
	}
	
		model.addAttribute("query", "");
	 return "/component/collateral/movable/MfMoveableCompensationConfirm_billInfoInsert";}
	/***
	 * 新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData  formmovablecompensationconf0002 = formService.getFormData("movablecompensationconf0002");
		 getFormValue(formmovablecompensationconf0002);
		MfMoveableCompensationConfirm  mfMoveableCompensationConfirm = new MfMoveableCompensationConfirm();
		 setObjValue(formmovablecompensationconf0002, mfMoveableCompensationConfirm);
		 mfMoveableCompensationConfirmFeign.insert(mfMoveableCompensationConfirm);
		 getObjValue(formmovablecompensationconf0002, mfMoveableCompensationConfirm);
		 this.addActionMessage(model, "保存成功");
		 List<MfMoveableCompensationConfirm> mfMoveableCompensationConfirmList = (List<MfMoveableCompensationConfirm>)mfMoveableCompensationConfirmFeign.findByPage(this.getIpage(), mfMoveableCompensationConfirm).getResult();
		model.addAttribute("mfMoveableCompensationConfirmList", mfMoveableCompensationConfirmList);
		model.addAttribute("formmovablecompensationconf0002", formmovablecompensationconf0002);
		model.addAttribute("query", "");
		 return "/component/collateral/movable/MfMoveableCompensationConfirm_Insert";
	}
	/**
	 * 查询
	 * @param confirmId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String ajaxData, String confirmId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData  formmovablecompensationconf0001 = formService.getFormData("movablecompensationconf0001");
		 getFormValue(formmovablecompensationconf0001);
		MfMoveableCompensationConfirm  mfMoveableCompensationConfirm = new MfMoveableCompensationConfirm();
		mfMoveableCompensationConfirm.setConfirmId(confirmId);
		 mfMoveableCompensationConfirm = mfMoveableCompensationConfirmFeign.getById(mfMoveableCompensationConfirm);
		 getObjValue(formmovablecompensationconf0001, mfMoveableCompensationConfirm);
		model.addAttribute("formmovablecompensationconf0001", formmovablecompensationconf0001);
		model.addAttribute("query", "");
		 return "/component/collateral/movable/MfMoveableCompensationConfirm_Detail";
	}
	/**
	 * 删除
	 * @param confirmId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String ajaxData, String confirmId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		MfMoveableCompensationConfirm mfMoveableCompensationConfirm = new MfMoveableCompensationConfirm();
		mfMoveableCompensationConfirm.setConfirmId(confirmId);
		mfMoveableCompensationConfirmFeign.delete(mfMoveableCompensationConfirm);
		return getListPage(model);
	}
}

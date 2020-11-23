package  app.component.collateral.movable.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.component.collateral.entity.MfBusCollateralRel;
import app.component.collateral.feign.MfBusCollateralRelFeign;
import app.component.collateral.movable.entity.MfMoveableClaimGoodsApply;
import app.component.collateral.movable.feign.MfMoveableClaimGoodsApplyFeign;
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

import app.component.collateral.movable.entity.MfMoveableClaimGoodsAffirm;
import app.component.collateral.movable.feign.MfMoveableClaimGoodsAffirmFeign;
import app.component.common.EntityUtil;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;

/**
 * Title: MfMoveableClaimGoodsAffirmAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Mon Jun 12 20:12:27 CST 2017
 **/
@Controller
@RequestMapping("/mfMoveableClaimGoodsAffirm")
public class MfMoveableClaimGoodsAffirmController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	//注入MfMoveableClaimGoodsAffirmBo
	@Autowired
	private MfMoveableClaimGoodsAffirmFeign mfMoveableClaimGoodsAffirmFeign;
	@Autowired
	private MfBusCollateralRelFeign mfBusCollateralRelFeign;
	@Autowired
	private MfMoveableClaimGoodsApplyFeign mfMoveableClaimGoodsApplyFeign;
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
		 return "/component/collateral/movable/MfMoveableClaimGoodsAffirm_List";
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
		MfMoveableClaimGoodsAffirm mfMoveableClaimGoodsAffirm = new MfMoveableClaimGoodsAffirm();
		try {
			mfMoveableClaimGoodsAffirm.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfMoveableClaimGoodsAffirm.setCriteriaList(mfMoveableClaimGoodsAffirm, ajaxData);//我的筛选
			//mfMoveableClaimGoodsAffirm.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfMoveableClaimGoodsAffirm,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage = mfMoveableClaimGoodsAffirmFeign.findByPage(ipage, mfMoveableClaimGoodsAffirm);
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
	public Map<String, Object> insertAjax(String ajaxData,String pledgeBills) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
			FormData formclaimaffirm0002 = formService.getFormData("claimaffirm0002");
			getFormValue(formclaimaffirm0002, getMapByJson(ajaxData));
			if(this.validateFormData(formclaimaffirm0002)){
				MfMoveableClaimGoodsAffirm mfMoveableClaimGoodsAffirm = new MfMoveableClaimGoodsAffirm();
				setObjValue(formclaimaffirm0002, mfMoveableClaimGoodsAffirm);
				mfMoveableClaimGoodsAffirm.setPledgeBills(pledgeBills);
				mfMoveableClaimGoodsAffirmFeign.insert(mfMoveableClaimGoodsAffirm);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("提货确认"));
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
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
		FormData formclaimaffirm0002 = formService.getFormData("claimaffirm0002");
		getFormValue(formclaimaffirm0002, getMapByJson(ajaxData));
		MfMoveableClaimGoodsAffirm mfMoveableClaimGoodsAffirmJsp = new MfMoveableClaimGoodsAffirm();
		setObjValue(formclaimaffirm0002, mfMoveableClaimGoodsAffirmJsp);
		MfMoveableClaimGoodsAffirm mfMoveableClaimGoodsAffirm = mfMoveableClaimGoodsAffirmFeign.getById(mfMoveableClaimGoodsAffirmJsp);
		if(mfMoveableClaimGoodsAffirm!=null){
			try{
				mfMoveableClaimGoodsAffirm = (MfMoveableClaimGoodsAffirm)EntityUtil.reflectionSetVal(mfMoveableClaimGoodsAffirm, mfMoveableClaimGoodsAffirmJsp, getMapByJson(ajaxData));
				mfMoveableClaimGoodsAffirmFeign.update(mfMoveableClaimGoodsAffirm);
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
		FormData 	formclaimaffirm0002 = formService.getFormData("claimaffirm0002");
			getFormValue(formclaimaffirm0002, getMapByJson(ajaxData));
			if(this.validateFormData(formclaimaffirm0002)){
		MfMoveableClaimGoodsAffirm mfMoveableClaimGoodsAffirm = new MfMoveableClaimGoodsAffirm();
				setObjValue(formclaimaffirm0002, mfMoveableClaimGoodsAffirm);
				mfMoveableClaimGoodsAffirmFeign.update(mfMoveableClaimGoodsAffirm);
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
	 * @param affirmId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String ajaxData, String affirmId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formclaimaffirm0002 = formService.getFormData("claimaffirm0002");
		MfMoveableClaimGoodsAffirm mfMoveableClaimGoodsAffirm = new MfMoveableClaimGoodsAffirm();
		mfMoveableClaimGoodsAffirm.setAffirmId(affirmId);
		mfMoveableClaimGoodsAffirm = mfMoveableClaimGoodsAffirmFeign.getById(mfMoveableClaimGoodsAffirm);
		getObjValue(formclaimaffirm0002, mfMoveableClaimGoodsAffirm,formData);
		if(mfMoveableClaimGoodsAffirm!=null){
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}
	/**
	 * Ajax异步删除
	 * @param affirmId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String ajaxData, String affirmId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfMoveableClaimGoodsAffirm mfMoveableClaimGoodsAffirm = new MfMoveableClaimGoodsAffirm();
		mfMoveableClaimGoodsAffirm.setAffirmId(affirmId);
		try {
			mfMoveableClaimGoodsAffirmFeign.delete(mfMoveableClaimGoodsAffirm);
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
	 * @param cusNo 
	 * @param busPleId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model, String cusNo, String busPleId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData formclaimaffirm0002 = formService.getFormData("claimaffirm0002");
		MfMoveableClaimGoodsAffirm mfMoveableClaimGoodsAffirm = new MfMoveableClaimGoodsAffirm();
		mfMoveableClaimGoodsAffirm=mfMoveableClaimGoodsAffirmFeign.getInitClaimGoodsAffirm(busPleId, cusNo);
		MfBusCollateralRel mfBusCollateralRel = new MfBusCollateralRel();
		mfBusCollateralRel.setBusCollateralId(busPleId);
		mfBusCollateralRel = mfBusCollateralRelFeign.getById(mfBusCollateralRel);
		Double ableOutValue = 0.00;
		if (mfBusCollateralRel!=null&&mfBusCollateralRel.getReceTransBal()!=null){
			ableOutValue = mfBusCollateralRel.getReceTransBal();
		}
		if (mfBusCollateralRel!=null&&mfBusCollateralRel.getReceTransBal()!=null){
			ableOutValue = mfBusCollateralRel.getReceTransBal();
		}
		mfMoveableClaimGoodsAffirm.setAbleOutValue(ableOutValue);
		MfMoveableClaimGoodsApply mfMoveableClaimGoodsApply = new MfMoveableClaimGoodsApply();
		mfMoveableClaimGoodsApply.setClaimId(mfMoveableClaimGoodsAffirm.getClaimId());
		mfMoveableClaimGoodsApply = mfMoveableClaimGoodsApplyFeign.getById(mfMoveableClaimGoodsApply);
		mfMoveableClaimGoodsAffirm.setClaimPledge(mfMoveableClaimGoodsApply.getClaimPledge());
		mfMoveableClaimGoodsAffirm.setAffirmClaimGoodsAmt(mfMoveableClaimGoodsApply.getClaimGoodsAmt());
		mfMoveableClaimGoodsAffirm.setAffirmGoodsNum(mfMoveableClaimGoodsApply.getGoodsNum());
		getObjValue(formclaimaffirm0002, mfMoveableClaimGoodsAffirm);
		model.addAttribute("formclaimaffirm0002", formclaimaffirm0002);
		model.addAttribute("mfMoveableClaimGoodsAffirm", mfMoveableClaimGoodsAffirm);
		model.addAttribute("query", "");
		 return "/component/collateral/movable/MfMoveableClaimGoodsAffirm_Insert";
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
		FormData  formclaimaffirm0002 = formService.getFormData("claimaffirm0002");
		 getFormValue(formclaimaffirm0002);
		MfMoveableClaimGoodsAffirm  mfMoveableClaimGoodsAffirm = new MfMoveableClaimGoodsAffirm();
		 setObjValue(formclaimaffirm0002, mfMoveableClaimGoodsAffirm);
		 mfMoveableClaimGoodsAffirmFeign.insert(mfMoveableClaimGoodsAffirm);
		 getObjValue(formclaimaffirm0002, mfMoveableClaimGoodsAffirm);
		 this.addActionMessage(model, "保存成功");
		 List<MfMoveableClaimGoodsAffirm> mfMoveableClaimGoodsAffirmList = (List<MfMoveableClaimGoodsAffirm>)mfMoveableClaimGoodsAffirmFeign.findByPage(this.getIpage(), mfMoveableClaimGoodsAffirm).getResult();
		model.addAttribute("formclaimaffirm0002", formclaimaffirm0002);
		model.addAttribute("mfMoveableClaimGoodsAffirmList", mfMoveableClaimGoodsAffirmList);
		model.addAttribute("query", "");
		 return "/component/collateral/movable/MfMoveableClaimGoodsAffirm_Insert";
	}
	/**
	 * 查询
	 * @param affirmId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String ajaxData, String affirmId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData  formclaimaffirm0001 = formService.getFormData("claimaffirm0001");
		 getFormValue(formclaimaffirm0001);
		MfMoveableClaimGoodsAffirm  mfMoveableClaimGoodsAffirm = new MfMoveableClaimGoodsAffirm();
		mfMoveableClaimGoodsAffirm.setAffirmId(affirmId);
		 mfMoveableClaimGoodsAffirm = mfMoveableClaimGoodsAffirmFeign.getById(mfMoveableClaimGoodsAffirm);
		 getObjValue(formclaimaffirm0001, mfMoveableClaimGoodsAffirm);
		model.addAttribute("formclaimaffirm0001", formclaimaffirm0001);
		model.addAttribute("query", "");
		 return "/component/collateral/movable/MfMoveableClaimGoodsAffirm_Detail";
	}
	/**
	 * 删除
	 * @param affirmId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String ajaxData, String affirmId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		MfMoveableClaimGoodsAffirm mfMoveableClaimGoodsAffirm = new MfMoveableClaimGoodsAffirm();
		mfMoveableClaimGoodsAffirm.setAffirmId(affirmId);
		mfMoveableClaimGoodsAffirmFeign.delete(mfMoveableClaimGoodsAffirm);
		return getListPage(model);
	}
}

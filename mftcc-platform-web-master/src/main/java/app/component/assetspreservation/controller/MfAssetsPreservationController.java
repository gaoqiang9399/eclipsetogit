package  app.component.assetspreservation.controller;

import app.component.assetspreservation.entity.MfAssetsPreservation;
import app.component.assetspreservation.feign.MfAssetsPreservationFeign;
import app.component.collateral.entity.MfCollateralClass;
import app.component.collateral.entity.PledgeBaseInfo;
import app.component.collateral.feign.PledgeBaseInfoFeign;
import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.lawsuit.entity.MfLawsuit;
import app.component.lawsuit.feign.MfLawsuitFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.util.WaterIdUtil;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;
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
 * Title: MfAssetsPreservationController.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Sat Sep 22 17:39:41 CST 2018
 **/
@Controller
@RequestMapping(value = "/mfAssetsPreservation")
public class MfAssetsPreservationController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfAssetsPreservationFeign mfAssetsPreservationFeign;
	@Autowired
	private PledgeBaseInfoFeign pledgeBaseInfoFeign;
	@Autowired
	private MfLawsuitFeign mfLawsuitFeign;

	
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/assetspreservation/MfAssetsPreservation_List";
	}
	/**
	 * @方法描述： 根据案件Id获取列表信息
	 * @param model
	 * @param caseId
	 * @return
	 * @throws Exception
	 * String
	 * @author 仇招
	 * @date 2018年9月29日 上午9:43:03
	 */
	@RequestMapping(value = "/getListPageByCaseId")
	public String getListPageByCaseId(Model model ,String caseId) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("caseId", caseId);
		return "/component/assetspreservation/MfAssetsPreservation_CaseList";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "/findByPageAjax")
	public Map<String, Object> findByPageAjax(String ajaxData, String tableId, String tableType, Integer pageNo,Integer pageSize,String caseId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfAssetsPreservation mfAssetsPreservation = new MfAssetsPreservation();
		try {
			mfAssetsPreservation.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfAssetsPreservation.setCriteriaList(mfAssetsPreservation, ajaxData);//我的筛选
			mfAssetsPreservation.setCustomSorts(ajaxData);//自定义排序
			mfAssetsPreservation.setCaseId(caseId);
			//this.getRoleConditions(mfAssetsPreservation,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setPageSize(pageSize);
			ipage.setParams(this.setIpageParams("mfAssetsPreservation", mfAssetsPreservation));
			//自定义查询Feign方法
			ipage = mfAssetsPreservationFeign.findByPage(ipage);
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
	 * AJAX新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/assetsPreservationAjax")
	@ResponseBody
	public Map<String, Object> assetsPreservationAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
			FormData formassetspreservationbase = formService.getFormData("assetspreservationbase");
			getFormValue(formassetspreservationbase, getMapByJson(ajaxData));
			if(this.validateFormDataAnchor(formassetspreservationbase)){
				MfAssetsPreservation mfAssetsPreservation = new MfAssetsPreservation();
				setObjValue(formassetspreservationbase, mfAssetsPreservation);
				if(BizPubParm.INVOLVEMENT_STS_5.equals(mfAssetsPreservation.getInvolvementSts())){
					mfAssetsPreservation.setInvolvementSts(BizPubParm.INVOLVEMENT_STS_2);
				}
				mfAssetsPreservationFeign.insert(mfAssetsPreservation);
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
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
	 * 
	 * @方法描述： 根据押品编号获取资产保全记录
	 * @param pledgeNo
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 仇招
	 * @date 2018年9月26日 下午4:51:10
	 */
	@RequestMapping("/getListByPledgeNoAjax")
	@ResponseBody
	public Map<String, Object> getListByPledgeNoAjax(String pledgeNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
			MfAssetsPreservation mfAssetsPreservation = new MfAssetsPreservation();
			mfAssetsPreservation.setPledgeNo(pledgeNo);
			List<MfAssetsPreservation> mfAssetsPreservationList = mfAssetsPreservationFeign.getListByPledgeNo(mfAssetsPreservation);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr("tableassetspreservationrecordlist", "thirdTableTag",
					mfAssetsPreservationList, null, true);
			dataMap.put("tableHtml", tableHtml);
			dataMap.put("size", mfAssetsPreservationList.size());
			dataMap.put("flag", "success");
		}catch(Exception e){
			dataMap.put("flag", "error");
			dataMap.put("msg", "根据押品编号获取资产保全记录失败");
		}
		return dataMap;
	}
	@RequestMapping("/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
			FormData formassetspreservationbase = formService.getFormData("assetspreservationbaseadd");
			getFormValue(formassetspreservationbase, getMapByJson(ajaxData));
			if(this.validateFormDataAnchor(formassetspreservationbase)){
				MfAssetsPreservation mfAssetsPreservation = new MfAssetsPreservation();
				setObjValue(formassetspreservationbase, mfAssetsPreservation);

				/**
				 * 处理抵债报错问题--插入押品信息
				 *
				 */
				{
					PledgeBaseInfo	pledgeBaseInfo = new PledgeBaseInfo();
					String pledgeNo = WaterIdUtil.getWaterId();
					MfLawsuit mfLawsuit = new MfLawsuit();
					mfLawsuit.setCaseId(mfAssetsPreservation.getCaseId());
					mfLawsuit = mfLawsuitFeign.getById(mfLawsuit);
					pledgeBaseInfo.setCaseId(mfAssetsPreservation.getCaseId());
					pledgeBaseInfo.setClassId("17061114550593210");
					pledgeBaseInfo.setCusName(mfLawsuit.getCusName());
					pledgeBaseInfo.setCusNo(mfLawsuit.getCusNo());
					pledgeBaseInfo.setClassFirstNo("B");
					pledgeBaseInfo.setPledgeNo(pledgeNo);
					pledgeBaseInfo.setPledgeName(mfAssetsPreservation.getPledgeName());
					String ifPrivateAssets = "1";//自有资产
					if(ifPrivateAssets != null && BizPubParm.YES_NO_Y.equals(ifPrivateAssets)){//自有资产
						pledgeBaseInfo.setAssetProperty("1");
					}
					pledgeBaseInfoFeign.insert(pledgeBaseInfo);
					mfAssetsPreservation.setPledgeNo(pledgeBaseInfo.getPledgeNo());
				}
				mfAssetsPreservationFeign.insert(mfAssetsPreservation);
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
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
	@RequestMapping("/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formassetspreservationbase = formService.getFormData("assetspreservationbase");
		getFormValue(formassetspreservationbase, getMapByJson(ajaxData));
		MfAssetsPreservation mfAssetsPreservationJsp = new MfAssetsPreservation();
		setObjValue(formassetspreservationbase, mfAssetsPreservationJsp);
		MfAssetsPreservation mfAssetsPreservation = mfAssetsPreservationFeign.getById(mfAssetsPreservationJsp);
		if(mfAssetsPreservation!=null){
			try{
				mfAssetsPreservation = (MfAssetsPreservation)EntityUtil.reflectionSetVal(mfAssetsPreservation, mfAssetsPreservationJsp, getMapByJson(ajaxData));
				mfAssetsPreservationFeign.update(mfAssetsPreservation);
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
	@RequestMapping("/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfAssetsPreservation mfAssetsPreservation = new MfAssetsPreservation();
		try{
			FormData formassetspreservationbase = formService.getFormData("assetspreservationbase");
			getFormValue(formassetspreservationbase, getMapByJson(ajaxData));
			if(this.validateFormData(formassetspreservationbase)){
				mfAssetsPreservation = new MfAssetsPreservation();
				setObjValue(formassetspreservationbase, mfAssetsPreservation);
				mfAssetsPreservationFeign.update(mfAssetsPreservation);
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
	@RequestMapping("/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String id) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formassetspreservationbase = formService.getFormData("assetspreservationbase");
		MfAssetsPreservation mfAssetsPreservation = new MfAssetsPreservation();
		mfAssetsPreservation.setId(id);
		mfAssetsPreservation = mfAssetsPreservationFeign.getById(mfAssetsPreservation);
		getObjValue(formassetspreservationbase, mfAssetsPreservation,formData);
		if(mfAssetsPreservation!=null){
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
	@RequestMapping("/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String id) throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfAssetsPreservation mfAssetsPreservation = new MfAssetsPreservation();
		mfAssetsPreservation.setId(id);
		try {
			mfAssetsPreservationFeign.delete(mfAssetsPreservation);
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
	@RequestMapping("/input")
	public String input(Model model) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formassetspreservationbase = formService.getFormData("assetspreservationbaseadd");
		model.addAttribute("formassetspreservationbase", formassetspreservationbase);
		model.addAttribute("query", "");
		return "/component/assetspreservation/MfAssetsPreservation_Insert";
	}
	/**
	 * @方法描述： 查封，解封，抵债
	 * @param model
	 * @return
	 * @throws Exception
	 * String
	 * @author 仇招
	 * @date 2018年9月25日 下午4:49:42
	 */
	@RequestMapping("/assetsPreservation")
	public String assetsPreservation(Model model,String id,String involvementSts) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formassetspreservationbase = formService.getFormData("assetspreservationbase");
		MfAssetsPreservation mfAssetsPreservation = new MfAssetsPreservation();
		mfAssetsPreservation.setId(id);
		mfAssetsPreservation = mfAssetsPreservationFeign.getById(mfAssetsPreservation);
		if(mfAssetsPreservation != null){
			mfAssetsPreservation.setInvolvementSts(involvementSts);
			mfAssetsPreservation.setState("");
			mfAssetsPreservation.setId("");
			if(BizPubParm.INVOLVEMENT_STS_2.equals(involvementSts)){
				mfAssetsPreservation.setStartDate("");
				mfAssetsPreservation.setEndDate("");
			}
		}
		/*PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
		pledgeBaseInfo.setPledgeNo(mfAssetsPreservation.getPledgeNo());
		pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
		if(pledgeBaseInfo != null){
			mfAssetsPreservation.setCertificateNameShow(pledgeBaseInfo.getCertificateName());
		}*/
		getObjValue(formassetspreservationbase, mfAssetsPreservation);
		model.addAttribute("formassetspreservationbase", formassetspreservationbase);
		model.addAttribute("query", "");
		return "/component/assetspreservation/MfAssetsPreservation_AssetsPreservation";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model,String id) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formassetspreservationdetail = formService.getFormData("assetspreservationdetail");
		getFormValue(formassetspreservationdetail);
		MfAssetsPreservation mfAssetsPreservation = new MfAssetsPreservation();
		mfAssetsPreservation.setId(id);
		mfAssetsPreservation = mfAssetsPreservationFeign.getById(mfAssetsPreservation);
		getObjValue(formassetspreservationdetail, mfAssetsPreservation);
		model.addAttribute("formassetspreservationdetail", formassetspreservationdetail);
		model.addAttribute("query", "");
		return "/component/assetspreservation/MfAssetsPreservation_Detail";
	}

	/**
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public void validateInsert() throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formassetspreservationbase = formService.getFormData("assetspreservationbase");
		getFormValue(formassetspreservationbase);
		boolean validateFlag = this.validateFormData(formassetspreservationbase);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public void validateUpdate() throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formassetspreservationbase = formService.getFormData("assetspreservationbase");
		getFormValue(formassetspreservationbase);
		boolean validateFlag = this.validateFormData(formassetspreservationbase);
	}
	// 列表展示详情，单字段编辑
	@RequestMapping(value = "/listShowDetailAjax")
	@ResponseBody
	public Map<String, Object> listShowDetailAjax(String id) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> formData = new HashMap<String, Object>();
		JsonFormUtil jsonFormUtil = new JsonFormUtil();
		FormData formassetspreservationdetail = new FormService().getFormData("assetspreservationdetail");
		MfAssetsPreservation mfAssetsPreservation = new MfAssetsPreservation();
		mfAssetsPreservation.setId(id);
		mfAssetsPreservation = mfAssetsPreservationFeign.getById(mfAssetsPreservation);
		getObjValue(formassetspreservationdetail, mfAssetsPreservation, formData);

		String htmlStrCorp = jsonFormUtil.getJsonStr(formassetspreservationdetail, "propertySeeTag", "query");
		dataMap.put("formHtml", htmlStrCorp);
		dataMap.put("flag", "success");
		dataMap.put("formData", mfAssetsPreservation);
		return dataMap;
	}
}

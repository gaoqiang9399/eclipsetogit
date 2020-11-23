package  app.component.assetsmanage.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.component.assetsdailymanage.entity.MfAssetsDailyManage;
import app.component.assetsdailymanage.feign.MfAssetsDailyManageFeign;
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

import app.base.User;
import app.component.assetsmanage.entity.MfAssetsDisposal;
import app.component.assetsmanage.entity.MfAssetsManage;
import app.component.assetsmanage.feign.MfAssetsDisposalFeign;
import app.component.assetsmanage.feign.MfAssetsManageFeign;
import app.component.collateral.entity.PledgeBaseInfo;
import app.component.collateral.feign.PledgeBaseInfoFeign;
import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.util.MathExtend;

/**
 * Title: MfAssetsManageController.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Wed Sep 26 20:22:28 CST 2018
 **/
@Controller
@RequestMapping(value = "/mfAssetsManage")
public class MfAssetsManageController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfAssetsManageFeign mfAssetsManageFeign;
	@Autowired
	private PledgeBaseInfoFeign pledgeBaseInfoFeign;
	@Autowired
	private MfAssetsDisposalFeign mfAssetsDisposalFeign;
	@Autowired
	private MfAssetsDailyManageFeign mfAssetsDailyManageFeign;


	
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/assetsmanage/MfAssetsManage_List";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "/findByPageAjax")
	public Map<String, Object> findByPageAjax(String ajaxData, String tableId, String tableType, Integer pageNo,Integer pageSize) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfAssetsManage mfAssetsManage = new MfAssetsManage();
		try {
			mfAssetsManage.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfAssetsManage.setCriteriaList(mfAssetsManage, ajaxData);//我的筛选
			mfAssetsManage.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfAssetsManage,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setPageSize(pageSize);
			ipage.setParams(this.setIpageParams("mfAssetsManage", mfAssetsManage));
			//自定义查询Feign方法
			ipage = mfAssetsManageFeign.findByPage(ipage);
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
	@RequestMapping("/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
			FormData formAssetsManageBase = formService.getFormData("AssetsManageBase");
			getFormValue(formAssetsManageBase, getMapByJson(ajaxData));
			if(this.validateFormData(formAssetsManageBase)){
				MfAssetsManage mfAssetsManage = new MfAssetsManage();
				setObjValue(formAssetsManageBase, mfAssetsManage);
				mfAssetsManageFeign.insert(mfAssetsManage);
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
		FormData formAssetsManageBase = formService.getFormData("AssetsManageBase");
		getFormValue(formAssetsManageBase, getMapByJson(ajaxData));
		MfAssetsManage mfAssetsManageJsp = new MfAssetsManage();
		setObjValue(formAssetsManageBase, mfAssetsManageJsp);
		MfAssetsManage mfAssetsManage = mfAssetsManageFeign.getById(mfAssetsManageJsp);
		if(mfAssetsManage!=null){
			try{
				mfAssetsManage = (MfAssetsManage)EntityUtil.reflectionSetVal(mfAssetsManage, mfAssetsManageJsp, getMapByJson(ajaxData));
				mfAssetsManageFeign.update(mfAssetsManage);
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
		MfAssetsManage mfAssetsManage = new MfAssetsManage();
		try{
			FormData formAssetsManageBase = formService.getFormData("AssetsManageBase");
			getFormValue(formAssetsManageBase, getMapByJson(ajaxData));
			if(this.validateFormData(formAssetsManageBase)){
				mfAssetsManage = new MfAssetsManage();
				setObjValue(formAssetsManageBase, mfAssetsManage);
				mfAssetsManageFeign.update(mfAssetsManage);
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
	public Map<String, Object> getByIdAjax(String assetsManageId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formAssetsManageBase = formService.getFormData("AssetsManageBase");
		MfAssetsManage mfAssetsManage = new MfAssetsManage();
		mfAssetsManage.setAssetsManageId(assetsManageId);
		mfAssetsManage = mfAssetsManageFeign.getById(mfAssetsManage);
		getObjValue(formAssetsManageBase, mfAssetsManage,formData);
		if(mfAssetsManage!=null){
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
	public Map<String, Object> deleteAjax(String assetsManageId) throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfAssetsManage mfAssetsManage = new MfAssetsManage();
		mfAssetsManage.setAssetsManageId(assetsManageId);
		try {
			mfAssetsManageFeign.delete(mfAssetsManage);
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
		FormData formAssetsManageBase = formService.getFormData("AssetsManageBase");
		MfAssetsManage mfAssetsManage = new MfAssetsManage();
		mfAssetsManage.setOpName(User.getRegName(request));
		getObjValue(formAssetsManageBase, mfAssetsManage);
		model.addAttribute("formAssetsManageBase", formAssetsManageBase);
		model.addAttribute("query", "");
		return "/component/assetsmanage/MfAssetsManage_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model,String assetsManageId) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formAssetsManageDetail = formService.getFormData("AssetsManageDetail");
		getFormValue(formAssetsManageDetail);
		MfAssetsManage mfAssetsManage = new MfAssetsManage();
		mfAssetsManage.setAssetsManageId(assetsManageId);
		mfAssetsManage = mfAssetsManageFeign.getById(mfAssetsManage);
		PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
		if(mfAssetsManage != null){
			mfAssetsManage.setAssessAmtStr(MathExtend.moneyStr(mfAssetsManage.getAssessAmt()));
			mfAssetsManage.setDebtAmtStr(MathExtend.moneyStr(mfAssetsManage.getDebtAmt()));
			CodeUtils cu = new CodeUtils();
			Map<String,String> assetsStsMap = cu.getMapByKeyName("ASSETS_STS");
			mfAssetsManage.setAssetsStsShow(assetsStsMap.get(mfAssetsManage.getAssetsSts()));
			pledgeBaseInfo.setPledgeNo(mfAssetsManage.getPledgeNo());
			pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
			model.addAttribute("pledgeBaseInfo", pledgeBaseInfo);
			JsonTableUtil jtu = new JsonTableUtil();
			MfAssetsDisposal mfAssetsDisposal = new MfAssetsDisposal();
			mfAssetsDisposal.setAssetsManageId(assetsManageId);
			MfAssetsDailyManage mfAssetsDailyManage = new MfAssetsDailyManage();
			mfAssetsDailyManage.setAssetsManageId(assetsManageId);
			List<MfAssetsDailyManage> dailyManagelist = mfAssetsDailyManageFeign.getListByAssetsManageId(mfAssetsDailyManage);
			String dailyManageHtml = jtu.getJsonStr("tableassetsdailymanageList", "tableTag",
					dailyManagelist, null, true);

			mfAssetsDisposal.setHandleType(BizPubParm.ASSET_HANDLE_TYPE_AUCTION);//显示拍卖
			List<MfAssetsDisposal> mfAssetsDisposalList = mfAssetsDisposalFeign.getByAssetsManageId(mfAssetsDisposal);

			mfAssetsDisposal.setHandleType(BizPubParm.ASSET_HANDLE_TYPE_NEGOTIATE);//显示协议处置
			List<MfAssetsDisposal> mfAssetNegotiateList = mfAssetsDisposalFeign.getByAssetsManageId(mfAssetsDisposal);
			mfAssetsDisposalList.addAll(mfAssetNegotiateList);
			String auctionHtml = jtu.getJsonStr("tableAssetsDisposalList", "tableTag",
					mfAssetsDisposalList, null, true);

			mfAssetsDisposal.setHandleType(BizPubParm.ASSET_HANDLE_TYPE_LEASE);//显示租赁
			List<MfAssetsDisposal> mfAssetLeaseList = mfAssetsDisposalFeign.getByAssetsManageId(mfAssetsDisposal);
			String leaseHtml = jtu.getJsonStr("tableAssetsDisposalList", "tableTag",
					mfAssetLeaseList, null, true);

			mfAssetsDisposal.setHandleType(null);//显示全部
			List<MfAssetsDisposal> allList = mfAssetsDisposalFeign.getByAssetsManageId(mfAssetsDisposal);
			String allHtml = jtu.getJsonStr("tableAssetsDisposalList", "tableTag",
					allList, null, true);
			model.addAttribute("allHtml", allHtml);
			model.addAttribute("auctionHtml", auctionHtml);
			model.addAttribute("leaseHtml", leaseHtml);
			model.addAttribute("dailyManageHtml", dailyManageHtml);
			Map<String,String> appStsMap = cu.getMapByKeyName("APP_STS");
			if(mfAssetsDisposalList != null && mfAssetsDisposalList.size() > 0){
				model.addAttribute("appStsShow", appStsMap.get(mfAssetsDisposalList.get(0).getApplySts()));
				model.addAttribute("appSts", mfAssetsDisposalList.get(0).getApplySts());
			}
		}

//		request.setAttribute("ifBizManger", "3");
		getObjValue(formAssetsManageDetail, mfAssetsManage);
		JsonFormUtil jsonFormUtil = new JsonFormUtil();
		String detailHtml = jsonFormUtil.getJsonStr(formAssetsManageDetail, "propertySeeTag", "");
		model.addAttribute("assetsManageId", assetsManageId);
		model.addAttribute("mfAssetsManage", mfAssetsManage);
		model.addAttribute("detailHtml", detailHtml);
		model.addAttribute("query", "");
		return "/component/assetsmanage/MfAssetsManage_Detail";
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
		FormData formAssetsManageBase = formService.getFormData("AssetsManageBase");
		getFormValue(formAssetsManageBase);
		boolean validateFlag = this.validateFormData(formAssetsManageBase);
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
		FormData formAssetsManageBase = formService.getFormData("AssetsManageBase");
		getFormValue(formAssetsManageBase);
		boolean validateFlag = this.validateFormData(formAssetsManageBase);
	}
}

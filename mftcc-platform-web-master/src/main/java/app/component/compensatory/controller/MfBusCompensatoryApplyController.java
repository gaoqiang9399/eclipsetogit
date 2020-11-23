package  app.component.compensatory.controller;

import app.base.User;
import app.component.common.BizPubParm;
import app.component.compensatory.entity.MfBusCompensatoryApply;
import app.component.compensatory.entity.MfBusCompensatoryApplyDetail;
import app.component.compensatory.entity.MfBusCompensatoryConfirm;
import app.component.compensatory.feign.MfBusCompensatoryApplyDetailFeign;
import app.component.compensatory.feign.MfBusCompensatoryApplyFeign;
import app.component.compensatory.feign.MfBusCompensatoryConfirmFeign;
import app.component.lawsuit.entity.MfLawsuit;
import app.component.nmd.entity.ParmDic;
import app.component.pact.entity.MfBusFincApp;
import app.component.pact.entity.MfBusPact;
import app.component.pact.feign.MfBusFincAppFeign;
import app.component.pact.feign.MfBusPactFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.PropertiesUtil;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.WaterIdUtil;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import config.YmlConfig;
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
 * Title: MfBusCompensatoryApplyAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Wed May 09 18:57:02 CST 2018
 **/
@Controller
@RequestMapping("/mfBusCompensatoryApply")
public class MfBusCompensatoryApplyController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfBusCompensatoryApplyFeign mfBusCompensatoryApplyFeign;
	@Autowired
	private MfBusFincAppFeign mfBusFincAppFeign;
	@Autowired
	private MfBusCompensatoryApplyDetailFeign mfBusCompensatoryApplyDetailFeign;
	@Autowired
	private YmlConfig ymlConfig;
	@Autowired
	private MfBusPactFeign mfBusPactFeign;
	@Autowired
	private MfBusCompensatoryConfirmFeign mfBusCompensatoryConfirmFeign;
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/input")
	public String input(Model model,String fincId) throws Exception {//input
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formcompensatoryApply0001 = formService.getFormData("compensatoryApply0001");
		MfBusFincApp mfBusFincApp = new MfBusFincApp();
		mfBusFincApp.setFincId(fincId);
		mfBusFincApp = mfBusFincAppFeign.getById(mfBusFincApp);
		mfBusFincApp = mfBusFincAppFeign.disProcessDataForFincShow(mfBusFincApp);
		MfBusCompensatoryApply mfBusCompensatoryApply = new MfBusCompensatoryApply();
		mfBusCompensatoryApply.setFincId(fincId);
		mfBusCompensatoryApply = mfBusCompensatoryApplyFeign.getByFincId(mfBusCompensatoryApply);
		if(mfBusCompensatoryApply.getCompensatoryId() == null || "".equals(mfBusCompensatoryApply.getCompensatoryId())){
			mfBusCompensatoryApply.setCompensatoryId(WaterIdUtil.getWaterId());
		}
		//获得代偿详情
		MfBusCompensatoryApplyDetail mfBusCompensatoryApplyDetail = new MfBusCompensatoryApplyDetail();
		mfBusCompensatoryApplyDetail.setFincId(fincId);
		mfBusCompensatoryApplyDetail.setPactId(mfBusFincApp.getPactId());
		List<MfBusCompensatoryApplyDetail> mfBusCompensatoryApplyDetailList = mfBusCompensatoryApplyDetailFeign.findByPageAjax(mfBusCompensatoryApplyDetail);
		double feeSum = 0.0;
		for(int i = 0;i<mfBusCompensatoryApplyDetailList.size(); i++) {
			MfBusCompensatoryApplyDetail detail = mfBusCompensatoryApplyDetailList. get(i);
			feeSum += detail.getCompensatoryPrcp();
		}
		mfBusCompensatoryApply.setCompensatoryFeeSum(feeSum);
		getObjValue(formcompensatoryApply0001, mfBusCompensatoryApply);
		// 获取该客户授信过的产品种类
		mfBusFincApp.setOpNo(User.getRegNo(request));
		mfBusFincApp.setOpName(User.getRegName(request));
		mfBusFincApp.setBrNo(User.getOrgNo(request));
		mfBusFincApp.setBrName(User.getOrgName(request));
		getObjValue(formcompensatoryApply0001, mfBusFincApp);
        model.addAttribute("formcompensatoryApply0001", formcompensatoryApply0001);
		model.addAttribute("query", "");
		model.addAttribute("projectName", ymlConfig.getSysParams().get("sys.project.name"));
		model.addAttribute("mfBusCompensatoryApply", mfBusCompensatoryApply);
		model.addAttribute("mfBusCompensatoryApplyDetailList", mfBusCompensatoryApplyDetailList);
		return "/component/compensatory/MfBusCompensatoryApply_Detail";
	}

	/**
	 * 保存代偿申请信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insertAjax")
	@ResponseBody
	public Map<String,Object> insertAjax(String ajaxData,String ajaxDataList)throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		try {
			Map<String, Object> map = getMapByJson(ajaxData);
			String formId = map.get("formId").toString();
			FormData formcompensatoryApply0001 = formService.getFormData(formId);
			getFormValue(formcompensatoryApply0001, getMapByJson(ajaxData));
			if(this.validateFormData(formcompensatoryApply0001)){
				MfBusCompensatoryApply mfBusCompensatoryApply = new MfBusCompensatoryApply();
				setObjValue(formcompensatoryApply0001, mfBusCompensatoryApply);
				mfBusCompensatoryApply.setCompensatoryDetailListStr(ajaxDataList);
				mfBusCompensatoryApply = mfBusCompensatoryApplyFeign.insertCompensatoryApply(mfBusCompensatoryApply);
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("userRole", mfBusCompensatoryApply.getApproveNodeName());
				paramMap.put("opNo", mfBusCompensatoryApply.getApprovePartName());
				dataMap.put("msg", MessageEnum.SUCCEED_APPROVAL.getMessage(paramMap));
				dataMap.put("flag", "success");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "保存失败");
			throw new Exception(e.getMessage());
		}
		
		return dataMap;
	}
	
	@RequestMapping("/getCompensatoryType")
	@ResponseBody
	public Map<String,Object> getCompensatoryType(String fincId)throws Exception{
		Map<String,Object> dataMap = new HashMap<String, Object>();
		MfBusCompensatoryApply mfBusCompensatoryApply = new MfBusCompensatoryApply();
		mfBusCompensatoryApply.setFincId(fincId);
		MfBusCompensatoryConfirm mfBusCompensatoryConfirm = new MfBusCompensatoryConfirm();
		mfBusCompensatoryConfirm.setFincId(fincId);
		List<MfBusCompensatoryConfirm> mfBusCompensatoryConfirmList = mfBusCompensatoryConfirmFeign.getByFincId(mfBusCompensatoryConfirm);
		if(mfBusCompensatoryConfirmList!=null && mfBusCompensatoryConfirmList.size()>0){
			mfBusCompensatoryConfirm = mfBusCompensatoryConfirmList.get(0);
			dataMap.put("appSts", "7");
			dataMap.put("compensatoryId", mfBusCompensatoryConfirm.getCompensatoryId());
			dataMap.put("flag", "success");
            return dataMap;
		}
		List<MfBusCompensatoryApply> mfBusCompensatoryApplyList = mfBusCompensatoryApplyFeign.getCompensatoryList(mfBusCompensatoryApply);
		if (mfBusCompensatoryApplyList != null && mfBusCompensatoryApplyList.size()>0) {
			mfBusCompensatoryApply = mfBusCompensatoryApplyList.get(0);
			dataMap.put("appSts", mfBusCompensatoryApply.getAppSts());
			dataMap.put("compensatoryId", mfBusCompensatoryApply.getCompensatoryId());
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag","error");
		}
		return dataMap;
	}

	/**
	 * 列表打开页面请求
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("query", "");
		return "/component/compensatory/MfCusCompensatoryApply_List";
	}
	/**
	 * 列表打开页面请求
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListConfirmPage")
	public String getListConfirmPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("query", "");
		return "/component/compensatory/MfCusCompensatoryConfirm_List";
	}

	/***
	 * 列表数据查询
	 *
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/findByPageAjax")
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
											  String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusFincApp mfBusFincApp = new MfBusFincApp();
		try {
			mfBusFincApp.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfBusFincApp.setCustomSorts(ajaxData);// 自定义排序参数赋值
			mfBusFincApp.setCriteriaList(mfBusFincApp, ajaxData);// 我的筛选
			// mfBusFincApp.setPactSts(pactSts);
			// this.getRoleConditions(mfBusPact,"1000000001");//记录级权限控制方法
			String isOpen = PropertiesUtil.getCloudProperty("cloud.loanafter_filter");
			// 默认关闭
			if ("1".equals(isOpen)) {// 开启
				String roleNo = PropertiesUtil.getCloudProperty("cloud.loanafter_roleno");
				if (StringUtil.isNotEmpty(roleNo)) {
					String[] roleNoArray = User.getRoleNo(request);
					for (String role : roleNoArray) {
						if (roleNo.equals(role)) {
							mfBusFincApp.setOverdueSts(BizPubParm.OVERDUE_STS_1);
						}
					}
				}
			}
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfBusFincApp", mfBusFincApp));
			ipage = mfBusFincAppFeign.getCompensatoryApply(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/***
	 * 列表数据查询
	 *
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/findConfirmByPageAjax")
	public Map<String, Object> findConfirmByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
											  String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusCompensatoryApply mfBusCompensatoryApply = new MfBusCompensatoryApply();
		try {
			mfBusCompensatoryApply.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfBusCompensatoryApply.setCustomSorts(ajaxData);// 自定义排序参数赋值
			mfBusCompensatoryApply.setCriteriaList(mfBusCompensatoryApply, ajaxData);// 我的筛选
			mfBusCompensatoryApply.setAppSts("3");
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfBusCompensatoryApply", mfBusCompensatoryApply));
			ipage = mfBusCompensatoryApplyFeign.findConfirmByPageAjax(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	@RequestMapping(value = "/getLawsuitInitAjax")
	@ResponseBody
	public Map<String,Object> getLawsuitInitAjax(String ajaxData,String kindNo,int pageNo) throws Exception {
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap=new HashMap<String,Object>();
		try{
			MfBusCompensatoryApply mfBusCompensatoryApply = new MfBusCompensatoryApply();
			mfBusCompensatoryApply.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfBusCompensatoryApply.setAppSts("3");
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfBusCompensatoryApply", mfBusCompensatoryApply));
			ipage =mfBusCompensatoryApplyFeign.getLawsuitInitAjax(ipage);
			dataMap.put("ipage",ipage);
		}catch(Exception e){
			e.printStackTrace();
		}
		return dataMap;
	}
}

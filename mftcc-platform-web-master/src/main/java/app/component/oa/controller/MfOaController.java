package app.component.oa.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.component.model.entity.MfTemplateModelRel;
import app.component.model.feign.MfTemplateModelRelFeign;
import app.component.oa.lawyer.feign.MfOaLawyerFeign;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;

import app.base.User;
import app.component.common.BizPubParm;
import app.component.nmd.feign.WorkCalendarFeign;
import app.component.oa.accredit.feign.MfOaAccreditFeign;
import app.component.oa.adjustment.feign.MfOaAdjustmentFeign;
import app.component.oa.archive.feign.MfOaArchivesBaseFeign;
import app.component.oa.badge.feign.MfOaBadgeFeign;
import app.component.oa.banknote.feign.MfOaBankNoteFeign;
import app.component.oa.becomequalified.feign.MfOaBecomeQualifiedFeign;
import app.component.oa.budget.entity.MfOaBudgetMst;
import app.component.oa.budget.feign.MfOaBudgetMstFeign;
import app.component.oa.changemoney.feign.MfOaCounttransFeign;
import app.component.oa.communication.entity.MfOaInternalCommunication;
import app.component.oa.communication.feign.MfOaInternalCommunicationFeign;
import app.component.oa.consumable.entity.MfOaCons;
import app.component.oa.consumable.entity.MfOaConsOperate;
import app.component.oa.consumable.feign.MfOaConsFeign;
import app.component.oa.consumable.feign.MfOaConsOperateFeign;
import app.component.oa.debtexpense.entity.MfOaDebtexpense;
import app.component.oa.debtexpense.feign.MfOaDebtexpenseFeign;
import app.component.oa.dimission.feign.MfOaDimissionFeign;
import app.component.oa.entrymanagement.feign.MfOaEntryManagementFeign;
import app.component.oa.expense.feign.MfOaExpenseFeign;
import app.component.oa.filesign.entity.MfOaFileCountersign;
import app.component.oa.filesign.feign.MfOaFileCountersignFeign;
import app.component.oa.fulltopart.feign.MfOaFullToPartFeign;
import app.component.oa.human.feign.MfOaHumanResourcesFeign;
import app.component.oa.leave.entity.MfOaLeave;
import app.component.oa.leave.feign.MfOaLeaveFeign;
import app.component.oa.mattersreported.feign.MfOaMattersReportedFeign;
import app.component.oa.notice.entity.MfOaNotice;
import app.component.oa.notice.feign.MfOaNoticeFeign;
import app.component.oa.opencount.feign.MfOaOpeningFeign;
import app.component.oa.trainingneeds.feign.MfOaTrainingNeedsFeign;
import app.component.query.entity.MfQueryItem;
import app.component.queryinterface.QueryInterfaceFeign;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONArray;
@Controller
@RequestMapping("/mfOa")
public class MfOaController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfOaNoticeFeign mfOaNoticeFeign;
	@Autowired
	private MfOaDebtexpenseFeign mfOaDebtexpenseFeign;
	@Autowired
	private MfOaConsFeign mfOaConsFeign;
	@Autowired
	private MfOaConsOperateFeign mfOaConsOperateFeign;
	@Autowired
	private MfOaAccreditFeign mfOaAccreditFeign;
	@Autowired
	private MfOaBadgeFeign mfOaBadgeFeign;
	@Autowired
	private MfOaBankNoteFeign mfOaBankNoteFeign;
	@Autowired
	private MfOaCounttransFeign mfOaCounttransFeign;
	@Autowired
	private MfOaBudgetMstFeign mfOaBudgetMstFeign;
	@Autowired
	private MfOaFileCountersignFeign mfOaFileCountersignFeign;
	@Autowired
	private MfOaOpeningFeign mfOaOpeningFeign;
	@Autowired
	private MfOaExpenseFeign mfOaExpenseFeign;
	@Autowired
	private MfOaInternalCommunicationFeign mfOaInternalCommunicationFeign;
	@Autowired
	private MfOaArchivesBaseFeign mfOaArchivesBaseFeign;
	@Autowired
	private MfOaLeaveFeign mfOaLeaveFeign;
	@Autowired
	private MfOaHumanResourcesFeign mfOaHumanResourcesFeign;
	@Autowired
	private MfOaMattersReportedFeign mfOaMattersReportedFeign;
	@Autowired
	private WorkCalendarFeign workCalendarFeign;
	@Autowired
	private MfOaEntryManagementFeign mfOaEntryManagementFeign;
	@Autowired
	private MfOaBecomeQualifiedFeign mfOaBecomeQualifiedFeign;
	@Autowired
	private MfOaAdjustmentFeign mfOaAdjustmentFeign;
	@Autowired
	private MfOaDimissionFeign mfOaDimissionFeign;
	@Autowired
	private MfOaFullToPartFeign mfOaFullToPartFeign;
	@Autowired
	private MfOaTrainingNeedsFeign mfOaTrainingNeedsFeign;
	@Autowired
	private QueryInterfaceFeign queryInterfaceFeign;
	@Autowired
	private MfOaLawyerFeign mfOaLawyerFeign;
	@Autowired
	private MfTemplateModelRelFeign mfTemplateModelRelFeign;

	
	@ResponseBody
	@RequestMapping("/findCountAjax")
	public Map<String, Object> findCountAjax() throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try {
			Map<String, Object> countMap = new HashMap<String,Object>();
			// 测试流程先注释掉其他代码，待完成后恢复
			MfOaNotice mfOaNotice = new MfOaNotice();
			//只在通知公告中查询数量
			mfOaNotice.setNoticeType("通知公告");
			MfOaLeave mfOaLeave = new MfOaLeave();
			mfOaLeave.setOpNo(User.getRegNo(this.request));
			countMap.put("notice", mfOaNoticeFeign.getNoticeCount(mfOaNotice));
			countMap.put("leave", mfOaLeaveFeign.getOaLeaveCount(mfOaLeave));
			countMap.put("chapter",mfOaBadgeFeign.getBadgeCount());
			countMap.put("chaptermanage",mfOaBadgeFeign.getBadgeCountManage());
			countMap.put("transapply", mfOaCounttransFeign.getCount());
			countMap.put("openaccount", mfOaOpeningFeign.getCount());
			MfOaDebtexpense mfOaDebtexpense = new MfOaDebtexpense();
			mfOaDebtexpense.setOpNo(User.getRegNo(this.request));
			countMap.put("borrow", mfOaDebtexpenseFeign.getOaDebtexpenseCount(mfOaDebtexpense));
			countMap.put("reimbursement", 0);
			countMap.put("schedule", 0);
			MfOaCons mfOaCons = new MfOaCons();
			MfOaConsOperate mfOaConsOperate = new MfOaConsOperate();
			countMap.put("consumable", mfOaConsOperateFeign.getOaConsAppCount(mfOaConsOperate));
			countMap.put("consumablemanage", mfOaConsFeign.getOaConsCount(mfOaCons));
			countMap.put("schedule",workCalendarFeign.getScheduleCountBySelf(User.getRegNo(this.request)));
			countMap.put("personnel", mfOaArchivesBaseFeign.getCount());
			countMap.put("humanresources", mfOaHumanResourcesFeign.getCount());
			countMap.put("mattersreported", mfOaMattersReportedFeign.getCount());
			countMap.put("hosting", mfOaAccreditFeign.getCount(User.getRegNo(this.request)));
			countMap.put("stiff", mfOaBankNoteFeign.getCount());
			MfOaBudgetMst mfOaBudgetMst = new MfOaBudgetMst();
			countMap.put("budget", mfOaBudgetMstFeign.getDataCount(mfOaBudgetMst));
			countMap.put("filecountersign", mfOaFileCountersignFeign.getDataCount(new MfOaFileCountersign()));
			countMap.put("entryManagement", mfOaEntryManagementFeign.getCount());
			countMap.put("becomeQualified", mfOaBecomeQualifiedFeign.getCount());
			countMap.put("adjustment", mfOaAdjustmentFeign.getCount());
			countMap.put("dimission", mfOaDimissionFeign.getCount());
			countMap.put("fullToPart", mfOaFullToPartFeign.getCount());
			countMap.put("trainingNeeds", mfOaTrainingNeedsFeign.getCount());
			countMap.put("reimbursement", mfOaExpenseFeign.getCount());
			MfOaInternalCommunication communication=new MfOaInternalCommunication();
			communication.setMessageAcceptOpNo(User.getRegNo(this.request));
			communication.setReadSts(BizPubParm.YES_NO_N);
			countMap.put("communication", mfOaInternalCommunicationFeign.getCount(communication));
			// 获取律师个数
			countMap.put("lawyer",mfOaLawyerFeign.getCount());
			MfTemplateModelRel mfTemplateModelRel = new  MfTemplateModelRel ();
			mfTemplateModelRel.setTemplateType("13");
			countMap.put("blankTemplatePrint",mfTemplateModelRelFeign.getMfTemplateModelRelCnt(mfTemplateModelRel));
			dataMap.put("countMap", countMap);
			dataMap.put("flag", "success");
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.ERROR_SELECT.getMessage());
			throw e;
		}
    	
		return dataMap;
    }
	/**
	 * 
	 * 方法描述： 跳转至oa入口页面
	 * @return
	 * @throws Exception
	 * String
	 * @author zhs
	 * @date 2017-6-27 上午9:26:55
	 */
	@RequestMapping("/getEntrance")
	public String getEntrance(Model model) throws Exception{
		ActionContext.initialize(request, response);
		Map<String,Object> dataMap = new  HashMap<String,Object>();
		//获取当前操作员的我的关注列表
		MfQueryItem mfQueryItem = new MfQueryItem();
		mfQueryItem.setOpNo(User.getRegNo(this.request));
		mfQueryItem.setFuncType("2");
		mfQueryItem.setIsBase("0");
		mfQueryItem.setAttentionFlag("1");
		List<MfQueryItem> mfQueryItemList = queryInterfaceFeign.getOaAttentionList(mfQueryItem);
		JSONArray jsonArray = JSONArray.fromObject(mfQueryItemList);
		dataMap.put("mfQueryItemList", jsonArray);
		String ajaxData = jsonArray.toString();
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("mfQueryItemList", mfQueryItemList);
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("query", "");
		return "/component/oa/MfOaEntrance";
	}
	
}

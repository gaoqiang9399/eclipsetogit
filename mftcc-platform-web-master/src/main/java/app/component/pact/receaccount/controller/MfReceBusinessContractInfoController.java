package app.component.pact.receaccount.controller;

import app.base.User;
import app.component.app.entity.MfBusAppKind;
import app.component.appinterface.AppInterfaceFeign;
import app.component.common.BizPubParm;
import app.component.cus.entity.MfCusCustomer;
import app.component.cusinterface.CusInterfaceFeign;
import app.component.nmd.entity.ParmDic;
import app.component.pact.entity.MfBusFincApp;
import app.component.pact.entity.MfBusPact;
import app.component.pact.feign.MfBusPactFeign;
import app.component.pact.receaccount.entity.MfBusFincAppMain;
import app.component.pact.receaccount.entity.MfBusReceTransfer;
import app.component.pact.receaccount.entity.MfReceBusinessContractInfo;
import app.component.pact.receaccount.feign.MfBusFincAppMainFeign;
import app.component.pact.receaccount.feign.MfBusReceTransferFeign;
import app.component.pact.receaccount.feign.MfReceBusinessContractInfoFeign;
import app.component.prdctinterface.PrdctInterfaceFeign;
import app.component.wkf.entity.Result;
import app.component.wkfBusInterface.WkfBusInterfaceFeign;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.StringUtil;
import com.core.domain.screen.FormData;
import com.core.domain.screen.OptionsList;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;
import net.sf.json.JSONArray;
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
 * Title: MfBusFincAppMainController.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri Aug 11 18:19:17 CST 2017
 **/
@Controller
@RequestMapping("/mfReceBusinessContractInfo")
public class MfReceBusinessContractInfoController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfReceBusinessContractInfoFeign mfReceBusinessContractInfoFeign;
	@Autowired
	private CusInterfaceFeign cusInterfaceFeign;

	/**
	 *
	 * 方法描述 跳转至应收账款商务合同新增页面
	 * @param [model, cusNo]
	 * @return java.lang.String
	 * @author zhs
	 * @date 2018/8/31 11:51
	 */
	@RequestMapping(value = "/input")
	public String input(Model model, String cusNo) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);

		FormData formreceBusContractInfo = formService.getFormData("receBusinessContractInfoBase");
		getFormValue(formreceBusContractInfo);
		getObjValue(formreceBusContractInfo, mfCusCustomer);

		model.addAttribute("cusNo", cusNo);
		model.addAttribute("formreceBusContractInfo", formreceBusContractInfo);
		model.addAttribute("query", "");
		return "/component/pact/receaccount/MfReceBusinessContractInfo_Insert";
	}


	@RequestMapping("/getRecePactEndDateAjax")
	@ResponseBody
	public Map<String, Object> getRecePactEndDateAjax(String recePactBeginDate,String termType,String term) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String recePactEndDate="";
			dataMap.put("recePactEndDate","");
			if(StringUtil.isNotEmpty(term) && StringUtil.isNotEmpty(recePactBeginDate)){
				int intTerm=Integer.valueOf(term);
				recePactBeginDate=DateUtil.getYYYYMMDD(recePactBeginDate);
				if(BizPubParm.TERM_TYPE_MONTH.equals(termType)){//融资期限类型为月
					recePactEndDate=DateUtil.addMonth(recePactBeginDate, intTerm);
				}else{//期限类型为日
					recePactEndDate=DateUtil.addDay(recePactBeginDate, intTerm);
				}
				dataMap.put("recePactEndDate",DateUtil.getShowDateTime(recePactEndDate));
			}
			dataMap.put("flag", "success");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}

		return dataMap;
	}

	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String,Object> insertAjax(String ajaxData) throws  Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map map = getMapByJson(ajaxData);
			String formId = (String)map.get("formId");
			String opNo = User.getRegNo(request);
			FormData formrecetransBase = formService.getFormData(formId);
			getFormValue(formrecetransBase, getMapByJson(ajaxData));
			if (this.validateFormData(formrecetransBase)) {
				MfReceBusinessContractInfo mfReceBusinessContractInfo = new MfReceBusinessContractInfo();
				setObjValue(formrecetransBase, mfReceBusinessContractInfo);
				dataMap = mfReceBusinessContractInfoFeign.insert(mfReceBusinessContractInfo);
				dataMap.put("flag", "success");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			throw e;
		}
		return dataMap;
	}

	@RequestMapping("/getReceBusContractInfoAjax")
	@ResponseBody
	public Map<String, Object> getReceBusContractInfoAjax(String recePactId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfReceBusinessContractInfo mfReceBusinessContractInfo = new MfReceBusinessContractInfo();
			mfReceBusinessContractInfo.setRecePactId(recePactId);
			mfReceBusinessContractInfo = mfReceBusinessContractInfoFeign.getById(mfReceBusinessContractInfo);
			if (mfReceBusinessContractInfo != null) {
				//处理商务合同日和到期日
				if(StringUtil.isNotEmpty(mfReceBusinessContractInfo.getRecePactBeginDate())){
					mfReceBusinessContractInfo.setRecePactBeginDate(DateUtil.getShowDateTime(mfReceBusinessContractInfo.getRecePactBeginDate()));
				}
				if(StringUtil.isNotEmpty(mfReceBusinessContractInfo.getRecePactEndDate())){
					mfReceBusinessContractInfo.setRecePactEndDate(DateUtil.getShowDateTime(mfReceBusinessContractInfo.getRecePactEndDate()));
				}
				//处理期限展示值
				// 期限月
				if (BizPubParm.TERM_TYPE_MONTH.equals(mfReceBusinessContractInfo.getTermType())) {
					dataMap.put("termShow",mfReceBusinessContractInfo.getTerm() + "个月");
				} else if (BizPubParm.TERM_TYPE_DAY.equals(mfReceBusinessContractInfo.getTermType())) {
					dataMap.put("termShow",mfReceBusinessContractInfo.getTerm() + "天");
				}else {
				}

				dataMap.put("flag", "success");
				dataMap.put("mfReceBusinessContractInfo", mfReceBusinessContractInfo);
			} else {
				dataMap.put("flag", "error");
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

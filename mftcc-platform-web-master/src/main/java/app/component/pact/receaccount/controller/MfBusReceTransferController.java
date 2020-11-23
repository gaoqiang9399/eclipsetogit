package app.component.pact.receaccount.controller;

import app.base.User;
import app.component.common.BizPubParm;
import app.component.pact.entity.MfBusPact;
import app.component.pact.feign.MfBusPactFeign;
import app.component.pact.receaccount.entity.MfBusReceBaseInfo;
import app.component.pact.receaccount.entity.MfBusReceTransfer;
import app.component.pact.receaccount.entity.MfBusReceTransferMain;
import app.component.pact.receaccount.feign.MfBusReceBaseInfoFeign;
import app.component.pact.receaccount.feign.MfBusReceTransferFeign;
import app.component.pact.receaccount.feign.MfBusReceTransferMainFeign;
import app.component.prdct.entity.MfKindFlow;
import app.component.prdctinterface.PrdctInterfaceFeign;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import com.google.gson.JsonObject;
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
 * Title: MfBusReceTransferController.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri Aug 11 18:19:17 CST 2017
 **/
@Controller
@RequestMapping("/mfBusReceTransfer")
public class MfBusReceTransferController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfBusReceTransferFeign mfBusReceTransferFeign;
	@Autowired
	private MfBusPactFeign mfBusPactFeign;
	@Autowired
	private PrdctInterfaceFeign prdctInterfaceFeign;
	@Autowired
	private MfBusReceBaseInfoFeign mfBusReceBaseInfoFeign;
	@Autowired
	private MfBusReceTransferMainFeign mfBusReceTransferMainFeign;

	/**
	 *
	 * 方法描述 获取合同下的账款转让列表
	 * @param
	 * @return
	 * @author zhs
	 * @date 2018/8/16 9:50
	 */
	@RequestMapping(value = "/getReceTransList")
	public String getReceTransList(Model model, String pactId) throws Exception{
		ActionContext.initialize(request, response);
		MfBusPact mfBusPact = new MfBusPact();
		mfBusPact.setPactId(pactId);
		mfBusPact = mfBusPactFeign.getById(mfBusPact);
		MfBusReceTransfer mfBusReceTransfer = new MfBusReceTransfer();
		mfBusReceTransfer.setAppId(mfBusPact.getAppId());
		mfBusReceTransfer.setTransSts("0");
		List<MfBusReceTransfer> mfBusReceTransferList = mfBusReceTransferFeign.getUnSubmitTransferList(mfBusReceTransfer);
		model.addAttribute("transMainId", "");
		if(mfBusReceTransferList.size()>0){
			model.addAttribute("transMainId", mfBusReceTransferList.get(0).getTransMainId());
		}
		model.addAttribute("mfBusReceTransferList", mfBusReceTransferList);
		model.addAttribute("appId", mfBusPact.getAppId());
		model.addAttribute("pactId", mfBusPact.getPactId());
		model.addAttribute("cusNo", mfBusPact.getCusNo());
		return "/component/pact/receaccount/MfBusReceTransfer_List";
	}

	/**
	 *
	 * 方法描述 账款转让详情页面
	 * @param [model, pactId, transMainId]
	 * @return java.lang.String
	 * @author zhs
	 * @date 2018/8/17 10:05
	 */
	@RequestMapping(value = "/getReceTransDetail")
	public String getReceTransDetail(Model model,String pactId,String transMainId) throws Exception{
		ActionContext.initialize(request, response);
		MfBusPact mfBusPact = new MfBusPact();
		mfBusPact.setPactId(pactId);
		mfBusPact = mfBusPactFeign.getById(mfBusPact);
		//获取转让主信息
		MfBusReceTransferMain mfBusReceTransferMain = new MfBusReceTransferMain();
		mfBusReceTransferMain.setTransMainId(transMainId);
		mfBusReceTransferMain = mfBusReceTransferMainFeign.getById(mfBusReceTransferMain);
		//账款列表
		MfBusReceTransfer mfBusReceTransfer = new MfBusReceTransfer();
		mfBusReceTransfer.setTransMainId(transMainId);
		List<MfBusReceTransfer> mfBusReceTransferList = mfBusReceTransferFeign.getReceTransferList(mfBusReceTransfer);
		model.addAttribute("mfBusReceTransferList", mfBusReceTransferList);
		model.addAttribute("appId", mfBusPact.getAppId());
		model.addAttribute("pactId", mfBusPact.getPactId());
		model.addAttribute("cusNo", mfBusPact.getCusNo());
		model.addAttribute("transMainId",transMainId);
		model.addAttribute("transSts",mfBusReceTransferMain.getTransSts());
		model.addAttribute("query","");
		if(!"0".equals(mfBusReceTransferMain.getTransSts())){
			model.addAttribute("query","query");
		}
		return "/component/pact/receaccount/MfBusReceTransfer_Detail";
	}


	@RequestMapping(value = "/input")
	public String input(Model model, String pactId,String transMainId) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		MfBusPact mfBusPact = new MfBusPact();
		mfBusPact.setPactId(pactId);
		mfBusPact = mfBusPactFeign.getById(mfBusPact);
		FormData formrecetransBase = formService.getFormData("plCertiReceTransBase");
		getFormValue(formrecetransBase);
		getObjValue(formrecetransBase, mfBusPact);
		//获取这笔业务下未转让的应收账款
		MfBusReceBaseInfo mfBusReceBaseInfo = new MfBusReceBaseInfo();
		mfBusReceBaseInfo.setAppId(mfBusPact.getAppId());
		List<MfBusReceBaseInfo> receBaseInfoList = mfBusReceBaseInfoFeign.getUnTransReceList(mfBusReceBaseInfo);
		JSONArray receItems = JSONArray.fromObject(receBaseInfoList);
		for (int i = 0; i < receItems.size(); i++) {
			receItems.getJSONObject(i).put("id", receItems.getJSONObject(i).getString("receId"));
			receItems.getJSONObject(i).put("name", receItems.getJSONObject(i).getString("receNo"));
		}
		model.addAttribute("receItems", receItems);
		model.addAttribute("appId", mfBusPact.getAppId());
		model.addAttribute("cusNo", mfBusPact.getCusNo());
		model.addAttribute("formrecetransBase", formrecetransBase);
		model.addAttribute("query", "");
		model.addAttribute("transMainId", transMainId);
		return "/component/pact/receaccount/MfBusReceTransfer_Insert";
	}


	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String,Object> insertAjax(String ajaxData, String appId,String transMainId) throws  Exception{
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
				MfBusReceTransfer mfBusReceTransfer = new MfBusReceTransfer();
				setObjValue(formrecetransBase, mfBusReceTransfer);
				mfBusReceTransfer.setTransMainId(transMainId);
				dataMap = mfBusReceTransferFeign.insert(mfBusReceTransfer);

				//重新查询列表回显在页面
				MfBusReceTransfer mfBusReceTransferT = new MfBusReceTransfer();
				mfBusReceTransferT.setAppId(appId);
				mfBusReceTransferT.setTransSts("0");
				List<MfBusReceTransfer> mfBusReceTransferList = mfBusReceTransferFeign.getUnSubmitTransferList(mfBusReceTransferT);
				JsonTableUtil jtu = new JsonTableUtil();
				String htmlStr = jtu.getJsonStr("tablereceTransBaseList", "tableTag", mfBusReceTransferList, null, true);
				dataMap.put("htmlStr", htmlStr);
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


}

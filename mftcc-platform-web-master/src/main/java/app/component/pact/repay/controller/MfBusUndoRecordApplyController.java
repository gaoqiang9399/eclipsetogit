package app.component.pact.repay.controller;

import app.component.calc.core.entity.MfBusUndoApply;
import app.component.calc.core.entity.MfRepayHistoryUndo;
import app.component.pact.repay.feign.MfBusUndoRecordApplyFeign;
import app.util.JsonStrHandling;
import app.util.toolkit.Ipage;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
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
 * Title: MfBusUndoRecordAction.java
 * Description:还款撤销相关业务处理
 * @author:kaifa@dhcc.com.cn
 **/
@Controller
@RequestMapping("/mfBusUndoRecordApply")
public class MfBusUndoRecordApplyController extends BaseFormBean{
	private static final long serialVersionUID = 9156454891709532438L;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfBusUndoRecordApplyFeign mfBusUndoRecordApplyFeign;
	/**
	 * 列表打开页面请求MfBusUndoRecordApplyAction_getMfBusUndoRecord
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getMfBusUndoRecord")
	public String getMfBusUndoRecord(Model model,String fincId,String repayId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formundorecordapply = formService.getFormData("undorecordapply");
		MfBusUndoApply mfBusUndoApply = new MfBusUndoApply();
		mfBusUndoApply.setFincId(fincId);
		mfBusUndoApply.setRepayId(repayId);
		Map<String,Object> dataMap=mfBusUndoRecordApplyFeign.getMfBusUndoApplybyFincInfo(mfBusUndoApply);
        mfBusUndoApply=(MfBusUndoApply)JsonStrHandling.handlingStrToBean(dataMap.get("mfBusUndoApply"), MfBusUndoApply.class);// 参数实体;
		getObjValue(formundorecordapply, mfBusUndoApply);
		model.addAttribute("formundorecordapply", formundorecordapply);
		model.addAttribute("query", "");
		return "component/pact/repay/MfBusUndoRecord";
	}
	/**
	 * AJAX新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String,Object> dataMap = new HashMap<String, Object>();
		try{
			FormService formService = new FormService();
			FormData formundorecordapply = formService.getFormData("undorecordapply");
			getFormValue(formundorecordapply, getMapByJson(ajaxData));
			if(this.validateFormData(formundorecordapply)){
				MfBusUndoApply mfBusUndoApply = new MfBusUndoApply();
				setObjValue(formundorecordapply, mfBusUndoApply);
				dataMap = mfBusUndoRecordApplyFeign.doDealMfBusUndoApplyInfo(mfBusUndoApply);
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "还款撤销失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

	/**
	 * 还款撤销历史信息
	 * 
	 * @return
	 * @author WangChao
	 * @throws Exception
	 * @date 2018-3-27 上午11:54:30
	 */
	public String getMfRepayRevokeHistoryListAjax() throws Exception {

		return "";
	}

}

package app.component.thirdRecord.controller;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import app.component.channel.fund.entity.MfFundChannelRepayHistory;
import app.component.thirdRecord.feign.MfFundChannelRepayHistoryFeign;
import cn.mftcc.common.MessageEnum;

@Controller
@RequestMapping("/mfFundChannelRepayHistory")
public class MfFundChannelRepayHistoryController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfFundChannelRepayHistoryFeign mfFundChannelRepayHistoryFeign;
	/**
	 * 还款操作
	 * @param ajaxData
	 * @param formData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/saveRepayAjax")
	@ResponseBody
	public Map<String,Object> saveRepayAjax(String ajaxData) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		try{
			dataMap = getMapByJson(ajaxData);
			String formId = (String)dataMap.get("formId");
			FormData formrecourseApply0001 = formService.getFormData(formId);
			getFormValue(formrecourseApply0001, getMapByJson(ajaxData));
			if(this.validateFormData(formrecourseApply0001)){
				MfFundChannelRepayHistory mfFundChannelRepayHistory = new MfFundChannelRepayHistory();
				setObjValue(formrecourseApply0001, mfFundChannelRepayHistory);
				mfFundChannelRepayHistoryFeign.insert(mfFundChannelRepayHistory);
				dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage());
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
	
}

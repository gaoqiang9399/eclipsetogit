package  app.component.interfaces.appinterface.controller;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;

import app.component.cus.entity.MfUniqueVal;
import app.component.cusinterface.CusInterfaceFeign;
import cn.mftcc.common.MessageEnum;

/**
 * 校验身份证和手机号唯一
 * 
 * @author MaHao
 * @date 2017-8-16 下午4:12:44
 */
@Controller
@RequestMapping("/dingUniqueVal")
public class DingUniqueValController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	//注入MfUniqueValBo
	@Autowired
	private CusInterfaceFeign cusInterfaceFeign;
	//全局变量
	/**
	 * 
	 * 方法描述： 验证录入手机号码唯一性{@link app.component.cus.action.MfUniqueValAction#doCheckUniqueAjax()}
	 * @return
	 * @throws Exception
	 * String
	 * @author MaHao
	 */
	@RequestMapping(value = "/doCheckUniqueAjax")
	@ResponseBody
	public Map<String, Object> doCheckUniqueAjax(String unVal, String relationId, String unType, String cusNoExclude, String saveType) throws Exception{
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfUniqueVal mfUniqueVal = new MfUniqueVal();
		mfUniqueVal.setUnVal(unVal);
		//mfUniqueVal.setTabName(tabName);
		mfUniqueVal.setRelationId(relationId);
		mfUniqueVal.setUnType(unType);
		mfUniqueVal.setCusNo(cusNoExclude);
		try {
			String msg = cusInterfaceFeign.doCheckUniqueAjax(mfUniqueVal,saveType);
			if("0".equals(msg)){
				dataMap.put("flag", "0");
			}else{
				dataMap.put("flag", "1");
			}
			dataMap.put("msg", msg);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.ERROR.getMessage());
			throw e;
		}
		return dataMap;
	}
	
}

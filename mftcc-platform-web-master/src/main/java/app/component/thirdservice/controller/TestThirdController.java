package app.component.thirdservice.controller;

import app.base.SpringUtil;
import app.base.User;
import app.component.app.entity.MfBusApply;
import app.component.appinterface.AppInterfaceFeign;
import app.component.common.BizPubParm;
import app.component.cus.entity.MfCusCustomer;
import app.component.cusinterface.CusInterfaceFeign;
import app.component.pact.entity.MfBusFincApp;
import app.component.pactinterface.PactInterfaceFeign;
import app.component.paph.entity.ApiReturnRecord;
import app.component.paphinterface.PaphInterfaceFeign;
import app.component.thirdservice.feign.MfCusChannelFeign;
import app.tech.oscache.CodeUtils;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.HttpClientUtil;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.WaterIdUtil;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.google.gson.Gson;
import config.YmlConfig;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping(value = "/testThird")
public class TestThirdController extends BaseFormBean{

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfCusChannelFeign mfCusChannelFeign;

	@RequestMapping("/testThirdPage")
	public String testThirdPage(String cusNo, String appId, String ajaxData) throws Exception {
		return "testThirdPage";
	}
	/**
	 * 查询同盾的认证报告
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/testThirdAjax",produces = "application/json;charset=utf-8")
	public String testThirdAjax(MultipartFile file) throws Exception {
		try {
			if (!file.isEmpty()) {
				byte[] bs = file.getBytes();
				String s = new String(bs,"UTF-8");
				Map<String,String> map = new HashMap<>();
				map.put("json", s);
				String result = mfCusChannelFeign.doSubmitCusInfo(map);
				return result;
			}else{
				return "文件不能为空";
			}
		}catch (Exception e) {
			return "错误:"+e.getMessage();
		}

	}
}

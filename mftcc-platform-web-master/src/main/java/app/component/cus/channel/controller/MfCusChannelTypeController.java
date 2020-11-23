package  app.component.cus.channel.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.google.gson.Gson;

import app.component.cus.channel.feign.MfCusChannelTypeFeign;
import app.component.cus.channelType.entity.MfCusChannelType;
import app.component.nmd.entity.ParLoanuse;


/**
 * Title: MfCusChannelTypeAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Thu Dec 07 14:51:12 CST 2017
 **/
@Controller
@RequestMapping("/MfCusChannelType")
public class MfCusChannelTypeController extends BaseFormBean{
	private static final long serialVersionUID = 9196454891709523438L;
	@Autowired  
	private HttpServletRequest request;
	@Autowired  
	private HttpServletResponse response;
	@Autowired
	private MfCusChannelTypeFeign mfCusChannelTypeFeign;
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request,response);
		model.addAttribute("query", "");
		return "/component/cus/channel/MfCusChannelType_List";
	}
	
	/**
	 * 
	 *<p>Description: 查询所有的客户渠道</p> 
	 *@return
	 *@throws Exception
	 *@author 周凯强
	 *@date 2018年7月10日下午4:52:39
	 */
	@RequestMapping("/getAllByUplev")
	public String getAllByUplev(Model  model) throws Exception {
		ActionContext.initialize(request,response);
		List<MfCusChannelType> mfCusChannelTypeList = mfCusChannelTypeFeign.getAllByUplev();
			List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
			for (MfCusChannelType obj : mfCusChannelTypeList) {
				Map<String, String> data = new HashMap<String, String>();
				data.put("id", obj.getChannelNo());
				data.put("name", obj.getChannelName());
				data.put("pId", obj.getUplev());
                dataList.add(data);
			}
			model.addAttribute("ajaxData", new Gson().toJson(dataList));
			model.addAttribute("showAllFlag", "1");
			model.addAttribute("query", "");
			model.addAttribute("isStepLoading", "false");
	        return "component/cus/ChannelType";
	}
}

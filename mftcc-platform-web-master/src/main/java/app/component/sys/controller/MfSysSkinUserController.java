package app.component.sys.controller;

import app.base.User;
import app.component.common.EntityUtil;
import app.component.common.SysGlobalParams;
import app.component.nmd.entity.ParmDic;
import app.component.sys.entity.MfSysSkinUser;
import app.component.sys.feign.MfSysSkinUserFeign;
import app.tech.oscache.CodeUtils;
import app.tech.upload.FeignSpringFormEncoder;
import app.tech.upload.ImageUtil;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import feign.Feign;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: MfSysSkinUserController.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Wed May 03 15:16:02 CST 2017
 **/
@Controller
@RequestMapping("/mfSysSkinUser")
public class MfSysSkinUserController extends BaseFormBean{
	@Autowired
	private MfSysSkinUserFeign mfSysSkinUserFeign;
	@Autowired
	private HttpServletRequest request;
	@Autowired  
	private HttpServletResponse response;

	
   
	
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getSkinList")
	public String getSkinList(Model model) throws Exception {
		ActionContext.initialize(request,response);
		MfSysSkinUser mfSysSkinUser = new MfSysSkinUser();
		mfSysSkinUser.setOpNo(User.getRegNo(request));
		mfSysSkinUser = mfSysSkinUserFeign.getById(mfSysSkinUser);
		String curSkin = "default";
		if(mfSysSkinUser!=null){
			curSkin = mfSysSkinUser.getSkin();
		}
		List<ParmDic> dicList = (List<ParmDic>)new CodeUtils().getCacheByKeyName("SYSTEM_SKIN");
		JSONArray dicListArray = JSONArray.fromObject(dicList);
		for (int i = 0; i < dicListArray.size(); i++) {
			if(curSkin.equals(dicListArray.getJSONObject(i).getString("optCode"))){
				dicListArray.getJSONObject(i).put("curFlag","1");
			}else{
				dicListArray.getJSONObject(i).put("curFlag","0");
			}
		}
		model.addAttribute("skinArray",dicListArray);
		return "/component/sys/MfSysSkinUser_List";
	}
	@RequestMapping(value = "/updateSystemSkinAjax")
	@ResponseBody
	public Map<String,Object> updateSystemSkinAjax(String skin) throws Exception{
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfSysSkinUser mfSysSkinUser = new MfSysSkinUser();
		try {
			mfSysSkinUser.setSkin(skin);
			mfSysSkinUser.setOpNo(User.getRegNo(request));
			mfSysSkinUser.setOpName(User.getRegName(request));
			dataMap = mfSysSkinUserFeign.update(mfSysSkinUser);
			request.getSession().setAttribute("skinSuffix", "");
			if(!"default".equals(skin)){
				request.getSession().setAttribute("skinSuffix", "_"+skin);
			}

			dataMap.put("flag","success");
		}catch (Exception e){
			e.printStackTrace();
			dataMap.put("flag","error");
			dataMap.put("msg",MessageEnum.FAILED_OPERATION.getMessage("更换皮肤"));
		}
		return dataMap;
	}
}

package app.component.pms.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.mftcc.common.MessageEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.core.struts.ActionContext;

import app.component.pms.feign.PmsEntranceFeign;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/pmsEntrance")
public class PmsEntranceController {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private PmsEntranceFeign pmsEntranceFeign;
	

	@RequestMapping(value = "/getAll")
	@ResponseBody
	public Map<String,Object> getAll(String ajaxData) throws Exception{
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String,Object>();
		String json = pmsEntranceFeign.getAll();
		dataMap.put("json",json);
		return dataMap;
	}

	@RequestMapping(value = "/insert")
	@ResponseBody
	public Map<String,Object> insert(Model model) throws Exception{
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap.put("query","");
		return dataMap;
	}

	@RequestMapping(value = "/save")
	@ResponseBody
	public Map<String,Object> save(Object json) throws Exception{
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String,Object>();
		try {
			JSONObject obj = JSONArray.fromObject(json).getJSONObject(0);
			if(obj.get("pmsNo")==null){
				String pmsNo = pmsEntranceFeign.insert(obj);
				dataMap.put("type","insert");
				dataMap.put("pmsNo",pmsNo);
			}else{
				pmsEntranceFeign.update(obj);
				dataMap.put("type","update");
			}
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_SAVE.getMessage());
			throw e;
		}
		return dataMap;
	}
	
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Map<String,Object> delete(Object json) throws Exception{
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String,Object>();
		try {
			pmsEntranceFeign.delete(JSONArray.fromObject(json).getJSONObject(0));
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_DELETE.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_DELETE.getMessage());
			throw e;
		}
		return dataMap;
	}
	

}

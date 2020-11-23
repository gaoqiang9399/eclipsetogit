package app.component.pms.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.core.struts.ActionContext;

import app.component.pms.feign.PmsViewpointFeign;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/pmsViewpoint")
public class PmsViewpointController {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private PmsViewpointFeign pmsViewpointFeign;

	{
		ActionContext.initialize(request, response);
	}

	@RequestMapping(value = "/getAll")
	@ResponseBody
	public JSONArray getAll(Model model, String ajaxData) throws Exception{
		JSONArray jsonArray = pmsViewpointFeign.getAll();
		model.addAttribute("json",jsonArray);
		model.addAttribute("query", "");
		return jsonArray;
	}

	@RequestMapping(value = "/getChildrens")
	public String getChildrens(Model model, String ajaxData) throws Exception{
		JSONObject jsonObj = JSONObject.fromObject(ajaxData);
		String json = pmsViewpointFeign.getChildrens(jsonObj);
		model.addAttribute("json",json);
		model.addAttribute("query", "");
		return json;
	}

	@RequestMapping(value = "/insert")
	public Object insert(Model model,String ajaxData) throws Exception{
		JSONObject json = JSONObject.fromObject(ajaxData);
		model.addAttribute("json",json);
		model.addAttribute("query", "");
		return json;
	}

	@RequestMapping(value = "/save")
	public Object save(Model model, String optSts,String json) throws Exception{
		JSONObject obj = JSONArray.fromObject(json).getJSONObject(0);
		JSONObject json1 = new JSONObject();
		if("new".equals(optSts)){
			if(pmsViewpointFeign.findById(obj.getString("viewpointMenuNo"))>0){
				((JSONObject) json1).put("type","update");
			}else{
				pmsViewpointFeign.insert(obj);
			}
		}else{
			pmsViewpointFeign.update(obj);
		}
		model.addAttribute("json1",json1);
		model.addAttribute("query", "");
		return json;
	}

	@RequestMapping(value = "/update")
	public Object update(Model model, Object json) throws Exception{
		JSONObject obj = JSONArray.fromObject(json).getJSONObject(0);
		pmsViewpointFeign.update(obj);
		//model.addAttribute("", );
		model.addAttribute("query", "");
		return json;
	}

	@RequestMapping(value = "/updateSeq")
	public Object updateSeq(Model model, Object json) throws Exception{
		JSONObject obj = JSONArray.fromObject(json).getJSONObject(0);
		pmsViewpointFeign.updateSeq(obj);
		model.addAttribute("query", "");
		return json;
	}

	@RequestMapping(value = "/delete")
	public Object delete(Model model, Object json) throws Exception{
		pmsViewpointFeign.delete(JSONArray.fromObject(json).getJSONObject(0));
		model.addAttribute("json", json);
		model.addAttribute("query", "");
		return json;
	}

	@RequestMapping(value = "/getById")
	public Object getById(Model model) throws Exception{
		Object json = new JSONObject();
		model.addAttribute("json",json);
		model.addAttribute("query", "");
		return json;
	}

}
